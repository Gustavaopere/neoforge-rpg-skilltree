# 06.11 — Auditoria técnica MineColonies Economy

**Data:** 2026-09-05  
**Minecraft:** 1.21.1  
**Loader:** NeoForge  
**Java:** 21  
**MineColonies instalado:** `1.1.1375-1.21.1-snapshot`  
**Tag upstream auditada:** `v1.21.1-1.1.1375-snapshot`  
**Commit upstream auditado:** `a8022f703d80be3a0931f0d6cc34b229563ef713`  
**Spec:** `plans/06-integrations/11-minecolonies-economy.md`

## Resultado executivo

A auditoria permite implementar com segurança as Fases 1 e 2 do plano e define os boundaries necessários para a Fase 3. A Fase 4 (`CONSTRUCTION_CHARGE`) permanece **fail-closed** nesta build: não foi encontrado evento/API pública cancelável antes da criação do work order; os eventos públicos de construção são pós-conclusão. Introduzir mixin em `BuildRequestMessage` seria uma nova dependência arquitetural e não é autorizado por esta auditoria.

Decisões V1:

- saldo/ledger virtual é a única authority monetária;
- MineColonies continua authority de colônia, cidadãos, jobs, buildings, research, permissions, work orders e logística;
- moeda física não é authority e não será criada na V1;
- nenhuma mutation econômica concede Mastery;
- settlement é periódico/bounded, nunca por cidadão/tick;
- construction charge fica desabilitado até existir seam pré-irreversível aprovado;
- addon Compatibility/Tweaks/Let's Do não recebe authority monetária.

## 1. Identidade econômica e lifecycle

### Evidência provider

`IColony` expõe:

- `int getID()`;
- `ResourceKey<Level> getDimension()`;
- `IPermissions getPermissions()`;
- managers de cidadãos, buildings e research.

O ID numérico **não é permanente**. `ColonyList#getNextColonyID()` reutiliza posições removidas por meio de `nullIndices`. O record `ColonyId` contém somente `int id + dimension`.

A build expõe `ColonyDeletedModEvent`, carregando a `IColony` deletada.

### Decisão

Criar identidade própria imutável:

```text
EconomyColonyKey = UUID
NativeColonyBinding = (dimension, nativeColonyId, ownerUuid, townHallPos)
```

O native ID é apenas binding atual. Nunca é chave de saldo.

Persistência mínima por economia:

```text
schemaVersion
economyUuid
nativeDimension
nativeColonyId
ownerUuid
townHallPos
currencyId
createdGameTime
lastSeenGameTime
archived
archivedGameTime
state
ledger
```

Regras:

1. `ColonyDeletedModEvent` arquiva a binding e congela novas mutations.
2. World/chunk unload não arquiva.
3. Reuso de `(dimension,id)` nunca revive economia arquivada.
4. Binding incompatível com fingerprint persistido falha fechado.
5. Nome da colônia/moeda é display metadata; não participa da identidade.
6. `currencyId = rpgskilltree:colony/<economy-uuid>` e nunca muda por rename.

## 2. Permissions

`IPermissions` expõe owner/officer e `hasPermission(Player, Action)`. `Action.MANAGE_HUTS` é o gate já usado por mensagens administrativas de building/GUI do MineColonies.

Decisão:

- intents econômicos administrativos usam `Action.MANAGE_HUTS` server-side;
- não criar rank paralelo;
- ações futuras excepcionalmente destrutivas podem exigir owner-only somente se a spec for alterada explicitamente;
- o cliente nunca envia decisão de autorização pronta, apenas intent.

## 3. Inputs read-only para capacidade econômica Q

### Cidadãos/jobs

APIs auditadas:

- `ICitizenManager#getCitizens()`;
- `getCurrentCitizenCount()`;
- `ICitizenData#getJob()`;
- `getWorkBuilding()`;
- `isIdleAtJob()` / `getJobStatus()`;
- `isChild()` na hierarquia de cidadão.

V1 usa somente trabalhadores adultos com job e work building válidos. Não varrer entity AI nem inventário.

### Buildings/logística

APIs auditadas:

- `IRegisteredStructureManager#getBuildings()`;
- `getWareHouses()`;
- `ICommonBuilding#getBuildingLevel()`;
- `getBuildingType()`;
- `getPosition()`;
- `IBuilding#isBuilt()` / `isPendingConstruction()`.

V1 usa somente buildings construídos e soma níveis com cap. Warehouse entra apenas como multiplicador logístico bounded. Inventário/throughput não entra em Q.

### Research

`IResearchManager`/árvore local permitem consulta, porém nenhuma pesquisa econômica específica foi aprovada. Contribuição de research em Q = `0` na V1. Não inferir research por nome exibido.

## 4. Fórmula V1 de Q

A fórmula deve ser simples, monotônica, auditável e config-driven.

Defaults iniciais de calibração:

```text
adultWorkers = count(!isChild && job != null && workBuilding != null)
builtLevelPoints = sum(clamp(buildingLevel, 1, 5)) para buildings construídos
warehouseCount = count(warehouses construídos)

Q_raw = baseQ
      + workerWeight * adultWorkers
      + buildingLevelWeight * builtLevelPoints

logisticsMultiplier = 1 + warehouseBonus * min(warehouseCount, warehouseCap)
Q = max(minQ, round(Q_raw * logisticsMultiplier))
```

Defaults determinísticos para fixtures iniciais:

```text
baseQ = 2
workerWeight = 2
buildingLevelWeight = 1
warehouseBonus = 0.10
warehouseCap = 2
minQ = 1
```

Fixture de calibração mínima: 5 trabalhadores adultos + 8 pontos de níveis construídos + 0 warehouse => `Q = 20`, alinhado ao cenário de acceptance do plano.

Esses valores são defaults server-side, não constantes de domínio imutáveis.

## 5. Dinheiro ativo, equilíbrio e inflação

Enquanto não existem wallets/coins físicas reconciliadas, a V1 usa:

```text
M_effective = issuedSupply - retiredSupply
M = M_effective
M_equilibrium = Q
P_money = M / max(Q, minQ)
TargetPriceIndex = clamp(100 * pow(P_money, beta), minPriceIndex, maxPriceIndex)
```

Caso `M_effective == 0`, usar o piso de price index configurado, sem divisão por zero.

Convergência:

```text
delta = clamp(targetIndex - oldIndex, -maxStepDown, maxStepUp)
newIndex = clamp(oldIndex + delta, minPriceIndex, maxPriceIndex)
```

Defaults de teste/balanceamento inicial:

```text
beta = 0.50
minPriceIndex = 50
maxPriceIndex = 500
maxStepUp = 5
maxStepDown = 3
```

A configuração poderá ajustar esses valores; tests fixam os defaults para garantir determinismo.

## 6. Authority monetária e conservação

V1 = **ledger virtual autoritativo**.

Mint:

```text
issuedSupply += amount
treasuryBalance += amount
```

Retire V1:

```text
require treasuryBalance >= amount
treasuryBalance -= amount
retiredSupply += amount
```

Conservação mínima:

```text
effectiveSupply = issuedSupply - retiredSupply
effectiveSupply >= treasuryBalance + reservedBalance + activeCirculation
```

Enquanto wallets externas não existem, `activeCirculation` começa em `0`; transfers futuros não podem criar moeda.

Todo command possui `transactionId + causalKey`. Repetição da mesma identidade retorna o resultado já aplicado ou `DUPLICATE`, sem segunda mutation.

Aritmética monetária usa `long` com `Math.addExact/subtractExact` e falha fechada em overflow.

## 7. Persistência e migration

O repositório não possui hoje uma camada server-global reutilizável de `SavedData`. A economia introduzirá uma store própria server-side, preferencialmente `SavedData` no Overworld para existir exatamente uma vez por servidor.

Schema inicial: `1`.

Policy:

- codec explícito;
- migrations `N -> N+1` puras e testáveis;
- unknown/newer schema falha fechado para mutations e emite diagnóstico;
- save/load não depende de scan de chunks/inventários;
- ledger mantém índice de transaction IDs/causal keys suficiente para idempotência após restart;
- arquivo corrompido não deve criar saldo padrão silenciosamente para uma binding já conhecida.

## 8. Networking

Seguir padrão atual de `ModNetworking`:

- `RegisterPayloadHandlersEvent`;
- snapshot S2C;
- intents C2S;
- validação completa no server handler;
- bump da network version quando novos payloads entrarem.

Payloads planejados V1:

```text
EconomySnapshotRequestPayload C2S
EconomySnapshotPayload S2C
EconomyMintPreflightPayload C2S
EconomyMintPreflightResultPayload S2C
EconomyMintPayload C2S
EconomyRetirePayload C2S
```

Cada mutation inclui UUID de transaction/intent para replay rejection.

## 9. Construction/upgrade hook — resultado da auditoria

`BuildRequestMessage` resolve o building real server-side. Em `BUILD`, se `isPendingConstruction()` já for true, a mesma ação cancela o work order; caso contrário chama `building.requestUpgrade(...)`.

`AbstractBuilding#requestUpgrade(...)` valida condições e cria `WorkOrderType.BUILD` ou `UPGRADE` via `requestWorkOrder(...)`.

Eventos públicos encontrados:

- `BuildingAddedModEvent`;
- `BuildingConstructionModEvent`;
- `BuildingRemovedModEvent`;
- `BuildingUpgradedModEvent`.

`BuildingConstructionModEvent` é disparado quando o work order de construção **termina**. Não é um preflight cancelável.

Conclusão:

- não há seam público comprovado para cobrar antes da criação do work order;
- cobrança no evento de conclusão viola causalidade e pode cobrar após materiais/trabalho;
- interceptar `BuildRequestMessage` exigiria mixin/injeção em classe interna;
- o mod atual não usa mixins nesse boundary;
- **Fase 4 permanece DISABLED / FAIL-CLOSED na V1**;
- nenhuma cobrança/refund de construção será implementada nesta entrega.

## 10. Addons instalados

Modlist/Notion confirmam:

- MineColonies Compatibility `1.21.1-3.56`;
- MineColonies Tweaks `1.21.1-3.33`;
- MineColonies Let's Do `1.21.1-2.1`.

Compatibility 3.56 declara compat de conteúdo/ferramentas e target MineColonies 1.1.1368. Tweaks 3.33 altera ferramentas/config/GUI de cidadãos e também target 1.1.1368. Let's Do 2.1 é bridge MineColonies↔família Let's Do para conteúdo culinário/agro.

Nenhum deles é authority monetária ou fornece pipeline econômico substituto. V1 não intercepta internals desses addons.

## 11. Mastery

Economia não produz Mastery por:

- settlement;
- mint/retire;
- tax policy;
- crescimento de Q;
- building count;
- warehouse/logística;
- packets/UI.

Qualquer Mastery futura exige evento causal de jogador e contrato próprio; não faz parte de 06.11 V1.

## 12. Gates do plano — estado após auditoria

- [x] API/código real MineColonies `1.1.1375-1.21.1-snapshot` auditado no commit `a8022f703d80be3a0931f0d6cc34b229563ef713`.
- [x] Colony identity/lifecycle auditados; native ID reciclável, economy UUID próprio obrigatório; deleção via `ColonyDeletedModEvent`.
- [x] Permissions owner/officer auditadas; `Action.MANAGE_HUTS` será authority administrativa.
- [x] Hooks read-only para cidadãos/jobs/buildings/warehouse/research auditados.
- [x] Fórmula V1 de Q definida apenas com sinais provider-native bounded.
- [x] Hook transacional de construção auditado; resultado: nenhum seam público pre-irreversível seguro, então Phase 4 fail-closed.
- [x] Authority monetária definida: ledger virtual server-side único.
- [x] Schema/migration policy definidos.
- [x] Curva inflacionária possui defaults e fixtures determinísticos para calibração TDD.
- [x] Addons instalados auditados quanto a authority/sobreposição econômica.
- [x] Mastery por tick/throughput explicitamente proibida.

## 13. Escopo implementável após esta auditoria

**Aprovado para implementação nesta branch:**

1. Fase 1 — Economy Core.
2. Fase 2 — MineColonies read-only adapter + binding/lifecycle.
3. Networking/snapshot/preflight server-authoritative necessário à Fase 3.
4. Superfície administrativa econômica somente se houver seam de UI comprovado sem assumir internals frágeis do Town Hall.

**Não aprovado nesta branch sem nova auditoria/design:**

- mixin em `BuildRequestMessage`/`AbstractBuilding`;
- `CONSTRUCTION_CHARGE`/refund;
- moeda física;
- wallet de cidadão;
- salário/consumo individual;
- FX/comércio intercolônia;
- research/building Banco custom.
