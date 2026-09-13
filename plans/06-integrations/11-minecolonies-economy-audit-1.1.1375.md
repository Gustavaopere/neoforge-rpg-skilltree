# 06.11 — Auditoria MineColonies Economy V1 — 1.1.1375

**Data:** 2026-09-05  
**Provider:** MineColonies `1.1.1375-1.21.1-snapshot`  
**CurseForge file:** `8765939`  
**Commit upstream auditado:** `a8022f703d80be3a0931f0d6cc34b229563ef713`  
**Loader/runtime:** NeoForge 1.21.1 / Java 21

## 1. Resultado

A build instalada expõe API pública suficiente para uma V1 econômica **read-only no provider e autoritativa no nosso servidor**, mas não expõe seam público seguro para cobrança transacional de construção nem para injetar uma aba no Town Hall.

Decisões:

- `ECONOMY_CORE=APROVADO`;
- `MINT_RETIRE_LEDGER=APROVADO`;
- `MINECOLONIES_READ_ADAPTER=APROVADO`;
- `NATIVE_PERMISSION_GATE=APROVADO`;
- `COLONY_DELETE_LIFECYCLE=APROVADO`;
- `NETWORK_INTENTS=APROVADO`;
- `CONSTRUCTION_CHARGE=FAIL_CLOSED`;
- `TOWN_HALL_UI=FAIL_CLOSED`.

Nenhuma conclusão abaixo autoriza mixin, patch em classe `core`, override invasivo de XML do namespace MineColonies ou substituição do pipeline Builder/work-order/materials/logistics.

## 2. Fontes obrigatórias verificadas

A modlist/guia do projeto e o Notion convergem para:

- MineColonies `1.1.1375-1.21.1-snapshot`;
- Structurize `1.0.832-1.21.1-snapshot`;
- BlockUI `1.0.199-1.21.1-snapshot`;
- Domum Ornamentum `1.0.223-snapshot`;
- Multi-Piston `1.2.51-1.21.1-snapshot`.

Addons ativos relevantes:

- MineColonies Compatibility 3.56;
- MineColonies Let's Do 2.1;
- MineColonies Tweaks 3.33.

Compatibility/Tweaks foram publicados contra uma baseline MineColonies anterior e, por isso, são risco de acoplamento de runtime; nenhum deles recebe authority monetária.

## 3. Boundary de versão

A integração Economy aceita apenas a string auditada:

```text
1.1.1375-1.21.1-snapshot
```

Provider ausente ou versão divergente => integração Economy desabilitada/fail-closed. O RPG core continua carregável sem MineColonies.

## 4. Identidade da colônia

`IColony.getID()` não é tratado como identidade monetária durável. IDs nativos podem ser reciclados após exclusão/recriação.

O provider lookup usa um fingerprint composto:

```text
dimensionId
nativeColonyId
ownerUuid
townHallPos
```

Esse fingerprint resolve para um `EconomyColonyKey(UUID)` persistido pelo nosso mod.

Regras:

- o UUID econômico não é fornecido pelo cliente;
- fingerprint divergente para o mesmo `(dimension,id)` falha fechado;
- `ColonyDeletedModEvent` remove o binding live e arquiva a identidade anterior;
- uma colônia recriada não herda saldo da colônia excluída.

O commit auditado também contém tratamento para colônias em mundos dinamicamente carregados onde `colony.getWorld()` pode ser `null`; o lifecycle econômico resolve o servidor via NeoForge, não pela referência de world do provider.

## 5. Authority e permissões

MineColonies permanece authority para:

- existência/identidade provider-side da colônia;
- owner/officer/permissions;
- citizens/jobs;
- buildings/levels;
- Warehouse;
- work orders;
- Builder/material requests/logística;
- research e demais sistemas nativos.

A administração econômica usa a permissão pública:

```text
Action.MANAGE_HUTS
```

com `IPermissions.hasPermission(player, Action.MANAGE_HUTS)`.

Cliente envia intents; saldo, capacidade, price index, economy UUID, autorização e resultado são resolvidos no servidor.

## 6. Capacidade econômica Q

A V1 usa somente sinais públicos e bounded:

```text
Qbase = baseQ
      + adultEmployedCitizens * workerWeight
      + builtLevelPoints * buildingLevelWeight

Q = Qbase * boundedWarehouseMultiplier
```

Leituras auditadas:

- `IColony.getCitizenManager()`;
- `ICitizenManager.getCitizens()`;
- `ICitizenData.getJob()`;
- `ICitizenData.getWorkBuilding()`;
- `ICitizen.isChild()`;
- `IColony.getServerBuildingManager()`;
- `getBuildings()`;
- `IBuilding.getBuildingLevel()` / built state;
- `getWareHouses()`.

Não contam como produção automaticamente:

- itens estocados em AE2;
- throughput bruto de Create;
- simples presença de itens em inventário;
- addons sem causalidade econômica explícita.

Isso evita inflar `Q` por estoque, duplicação ou telemetria sem atribuição.

## 7. Modelo monetário e conservação

```text
effectiveSupply = issuedSupply - retiredSupply
allocatedSupply = treasuryBalance + reservedBalance + activeCirculation
```

Invariante V1 **exata**:

```text
effectiveSupply == allocatedSupply
```

Não existe bucket implícito ou moeda “desaparecida” na V1. Qualquer estado onde a igualdade não seja satisfeita é inválido e falha antes de ser publicado/persistido.

Mint V1:

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

`reservedBalance` e `activeCirculation` estão modelados, mas transfers que movimentariam valor para esses buckets ainda não têm mutation executável na V1.

Aritmética monetária usa `long` e `Math.addExact/subtractExact`. Overflow falha fechado.

## 8. Price index e settlement

O índice usa pressão monetária `M/Q`, com bounds e convergência gradual.

Propriedades:

- `M == Q` => referência ~100;
- `M > Q` => pressão inflacionária;
- `M < Q` => pressão deflacionária limitada pelo floor;
- `beta` controla elasticidade;
- movimentos por settlement são bounded e assimétricos;
- settlement não cria/destrói moeda;
- settlement não cria Mastery.

O scheduler é periódico, bounded e round-robin. Reinício do servidor apenas reancora a cadência; restart-spam não acelera a convergência do price index.

## 9. Ledger, idempotência e política de crescimento

Toda mutation aplicada possui:

```text
transactionId
causalKey
kind
amount
source
counterparty
gameTime
resulting monetary state
metadata
```

Repetição de `transactionId` ou `causalKey` retorna `DUPLICATE` sem segunda mutation.

### Retenção V1

`SavedData` precisa de política explícita de crescimento. A V1 usa retenção **bounded e fail-closed**, sem pruning:

```text
MAX_RETAINED_TRANSACTIONS = 4096 por economia
```

Regras:

- todos os IDs/causal keys das mutations aplicadas permanecem retidos exatamente;
- histórico persistido acima do limite é rejeitado no load;
- quando o limite é atingido, novas `MINT/RETIRE` retornam `RETENTION_LIMIT_REACHED` e não alteram estado;
- duplicates continuam sendo reconhecidos mesmo com a capacidade cheia;
- nenhum ID antigo é removido, portanto pruning não reabre replay;
- settlement/read/snapshot continuam disponíveis.

Compaction/epoch rotation não é inferido silenciosamente nesta V1. Se necessário no futuro, exige protocolo e migration explícitos que provem que packets antigos não se tornam reaplicáveis.

## 10. Persistência

Store server-global via `SavedData` no Overworld, schema inicial `1`.

Persistidos:

- economy state;
- ledger/audit history bounded;
- native bindings/fingerprints;
- archived identities necessárias ao lifecycle.

Regras:

- codec NBT explícito e tipado;
- campo ausente/tipo errado falha fechado;
- schema futuro desconhecido falha fechado;
- UUID/kind/metadata inválido falha fechado;
- save/load reconcilia state com tail do ledger;
- replay indexes são reconstruídos após restart;
- corrupção não vira saldo padrão silenciosamente.

## 11. Networking

Network protocol é versionado independentemente do schema econômico/disk schema.

V1:

```text
EconomySnapshotRequestPayload       C2S
EconomySnapshotPayload              S2C
EconomyMintPreflightPayload         C2S
EconomyMintPreflightResultPayload   S2C
EconomyMintPayload                  C2S
EconomyRetirePayload                C2S
```

O wire context contém apenas lookup provider-side `(dimension,id)`. O servidor recalcula fingerprint completo, permission e economy binding.

O client cache é read-only.

`MINT/RETIRE` têm ainda um teto técnico por packet (`Integer.MAX_VALUE`) separado da política monetária. Saldos continuam `long`.

## 12. Lifecycle MineColonies

Seam público auditado:

```text
IMinecoloniesAPI.getEventBus()
EventBus.subscribe(...)
ColonyDeletedModEvent
```

A deleção arquiva o binding monetário. O handler é registrado somente depois do exact-version gate.

Provider-present GameTests usam o namespace `rpgskilltree` e exercitam grafo real do MineColonies sem tornar o provider obrigatório na lane provider-free.

## 13. Construção — FAIL_CLOSED

A auditoria de `BuildRequestMessage`, `AbstractBuilding.requestUpgrade()` e work-order flow mostra criação/execução de BUILD/UPGRADE no pipeline nativo. O evento público encontrado para construção é posterior à conclusão e não fornece preflight transacional cancelável adequado.

Consequência:

- `CONSTRUCTION_CHARGE` permanece `UNSUPPORTED_KIND`;
- `REFUND` permanece `UNSUPPORTED_KIND`;
- o runtime do nosso mod não intercepta `BuildRequestMessage` nem `requestWorkOrder`;
- nenhum pagamento monetário substitui recursos, Builder, Warehouse, Couriers ou work orders.

Só habilitar cobrança quando existir seam público pré-irreversível ou contrato upstream equivalente com causalidade/idempotência comprovadas.

## 14. Town Hall / BlockUI — FAIL_CLOSED

`com.minecolonies.core.client.gui.townhall.AbstractWindowTownHall` registra as páginas conhecidas diretamente no construtor usando classes `core.client.gui.townhall`.

Não foi encontrado registry/API pública para anexar uma nova página econômica ao Town Hall nessa build.

Rejeitados:

- mixin em `AbstractWindowTownHall`;
- override de `minecolonies:gui/townhall/...`;
- redirect de `registerButton`;
- substituição da GUI nativa.

V1 entrega networking/cache necessários, mas não afirma que existe aba econômica visível no Town Hall.

## 15. Addons e deduplicação

Compatibility 3.56, Let's Do 2.1 e Tweaks 3.33 não recebem authority sobre:

- supply;
- treasury;
- price index;
- idempotency;
- economy UUID;
- mutation ledger.

Integrações futuras com produção/import/export precisam attribution segura e não podem contar o mesmo output em dois providers.

## 16. Escopo fora da V1

- moeda física;
- wallets/salários/consumo dos cidadãos;
- tax runtime completo;
- FX/inter-colony trade;
- Banco/Tesouro como building custom;
- construção monetariamente cobrada;
- UI injetada no Town Hall.

Esses itens exigem ciclo próprio de auditoria/design/API antes de se tornarem executáveis.
