# 06.11 — MineColonies Economy

> **Status:** PLANEJADO — não implementar hooks de MineColonies antes da auditoria técnica da versão instalada.
>
> **Minecraft:** 1.21.1  
> **Loader:** NeoForge  
> **Java:** 21  
> **MineColonies instalado:** `1.1.1375-1.21.1-snapshot` segundo a modlist/Notion reconciliados em 2026-08-30.  
> **Nota de versão:** documentação histórica do projeto ainda pode citar builds anteriores; a implementação deve validar a API real da build instalada antes de escolher mixins, eventos, capabilities ou chamadas internas.

## Objetivo

Adicionar ao RPG Skill Tree uma camada econômica opcional por colônia integrada ao MineColonies, com:

- moeda própria da colônia;
- Tesouro;
- oferta monetária e circulação;
- inflação/deflação baseada em moeda versus capacidade econômica;
- impostos;
- custos econômicos para serviços e obras sem substituir os custos materiais nativos;
- emissão e retirada de moeda;
- UI econômica no Town Hall;
- Banco/Tesouro como expansão posterior de midgame;
- base preparada para salários, comércio entre colônias e câmbio em fases futuras.

O sistema deve transformar a colônia em uma economia administrável sem assumir a autoridade de cidadãos, jobs, builders, warehouse, couriers, research, construction ou logistics do MineColonies.

## Princípios obrigatórios

### 1. MineColonies continua authority da colônia

MineColonies permanece autoridade de:

- identidade da colônia;
- cidadãos;
- profissões/jobs;
- buildings;
- construção e upgrade;
- requests de materiais;
- Warehouse/Couriers/logística;
- research;
- happiness e demais estados nativos.

O RPG Skill Tree não cria uma segunda colônia, segundo Warehouse, segundo pipeline de construção nem uma segunda IA de cidadão.

### 2. Custo econômico não substitui custo material

Uma obra/upgrade continua exigindo os materiais e o fluxo nativo do MineColonies.

Quando houver hook seguro, a economia adiciona uma autorização financeira paralela:

`pedido de obra -> checagem econômica -> pipeline nativo MineColonies`

Se o custo econômico falhar, a obra não deve consumir materiais parcialmente por causa do RPG Skill Tree.

Se não existir hook transacional seguro na versão instalada, essa integração permanece fail-closed em vez de usar cancelamento frágil pós-consumo.

### 3. Estado econômico é server-authoritative

Nenhuma decisão monetária pode ser client-authoritative.

UI apenas consulta snapshots e envia intents validados no servidor.

### 4. Uma mutação, um ledger

Toda alteração de moeda deve passar por um único serviço canônico e gerar uma entrada de ledger idempotente.

É proibido alterar simultaneamente item físico + saldo persistido por caminhos independentes.

### 5. Sem geração gratuita acidental

Emissão monetária é uma ação econômica explícita, auditável e sujeita às regras deste plano.

Duplicação por:

- chunk reload;
- reconnect;
- restart;
- reprocessamento de evento;
- double-click de UI;
- packet replay;
- cancelamento de obra;
- refund duplicado;
- quebra/recolocação de bloco;

é bug bloqueante.

---

# Arquitetura de domínio

## `ColonyEconomyState`

Persistência por identidade estável da colônia.

Campos mínimos planejados:

```text
colonyId
currencyId
currencyDisplayName
issuedSupply
retiredSupply
treasuryBalance
reservedBalance
activeCirculation
priceIndex
inflationRate
baseEconomicCapacity
currentEconomicCapacity
taxPolicy
lastSettlementTick
schemaVersion
```

### Invariantes

```text
issuedSupply >= 0
retiredSupply >= 0
treasuryBalance >= 0
reservedBalance >= 0
activeCirculation >= 0
retiredSupply <= issuedSupply
```

A soma dos buckets monetários deve reconciliar com a oferta monetária efetiva segundo o modelo escolhido na implementação.

O serviço deve fornecer auditoria para detectar divergência e falhar fechado em mutations que fariam o ledger perder conservação.

## `CurrencyId`

A moeda deve nascer vinculada a uma colônia, mesmo que a V1 ainda não tenha câmbio.

Identidade sugerida:

```text
rpgskilltree:colony/<colony-uuid-or-stable-id>
```

O nome exibido pode ser configurável, mas o ID interno não muda quando o jogador renomeia a colônia ou a moeda.

Isso preserva compatibilidade futura com:

- múltiplas colônias;
- moedas diferentes;
- exportação/importação;
- câmbio;
- reservas estrangeiras.

## `ColonyEconomyLedger`

Cada mutação monetária deve possuir uma identidade de transação estável.

Campos mínimos:

```text
transactionId
colonyId
kind
amount
source
counterparty
causalKey
gameTime
resultingTreasury
resultingSupply
metadata
```

Kinds iniciais:

- `MINT`;
- `RETIRE`;
- `TREASURY_DEPOSIT`;
- `TREASURY_WITHDRAWAL`;
- `TAX`;
- `CONSTRUCTION_CHARGE`;
- `REFUND`;
- `ADMIN_ADJUSTMENT`.

`causalKey` é obrigatório para eventos replayáveis.

---

# Modelo monetário

## Conceitos separados

O sistema não deve tratar todo dinheiro como uma única variável.

### Oferta emitida

Quantidade total criada pelo emissor:

```text
M_issued
```

### Moeda retirada/desmonetizada

Moeda destruída de forma permanente:

```text
M_retired
```

### Oferta efetiva

```text
M_effective = M_issued - M_retired
```

### Reserva/Tesouro

Moeda mantida em reserva ou pelo governo colonial.

### Circulação ativa

Moeda economicamente ativa no período atual.

A V1 pode começar com uma aproximação conservadora baseada em buckets controlados pelo sistema. Não inferir circulação real de itens em inventários arbitrários sem um hook seguro e bounded.

---

# Capacidade econômica

A inflação não depende apenas do número absoluto de moedas.

A variável central é a relação entre dinheiro e capacidade econômica real da colônia.

Definir:

```text
Q = currentEconomicCapacity
M = activeMoneyMetric
```

A capacidade econômica deve ser derivada apenas de sinais verificáveis do MineColonies e nunca por heurística baseada em nome de bloco.

Fontes candidatas, sujeitas à auditoria da API instalada:

- população adulta/ativa;
- buildings funcionais por categoria;
- nível dos buildings;
- capacidade produtiva de jobs ativos;
- Warehouse/logística disponível;
- pesquisas econômicas relevantes;
- estado operacional dos buildings.

Não usar automaticamente inventário total do Warehouse como `Q`, porque estoque acumulado não equivale a capacidade produtiva.

## Baseline

Cada colônia possui um nível de equilíbrio monetário derivado da capacidade econômica:

```text
M_equilibrium = f(Q)
```

A função exata será calibrada na implementação com testes determinísticos e config server-side.

---

# Índice de preços e inflação

## Pressão monetária

```text
P_money = M / M_equilibrium
```

`P_money = 1` representa equilíbrio aproximado.

## Curva de preços

Curva inicial proposta:

```text
TargetPriceIndex = 100 * pow(P_money, beta)
```

com limites server-configuráveis.

`beta` não deve ser congelado neste documento como valor final de balanceamento.

Objetivo de design:

- pequenas emissões sobre crescimento real causam inflação pequena;
- emissão muito acima da capacidade econômica causa inflação forte;
- a curva não explode instantaneamente por ruído de um único tick.

## Convergência gradual

O índice não salta diretamente para o alvo.

Atualização periódica:

```text
newIndex = oldIndex + clamp(targetIndex - oldIndex, -maxStepDown, maxStepUp)
```

ou equivalente determinístico.

Isso impede que emitir moeda durante uma GUI faça todos os preços mudarem de forma abrupta no mesmo tick.

## Deflação

Retirada de moeda ou forte crescimento de capacidade pode reduzir o índice.

A deflação deve ter piso configurável para impedir preços zerados e exploits de arredondamento.

---

# Formação de preços

Todo preço econômico usa:

```text
nominalPrice = roundPrice(basePrice * priceIndex / 100)
```

Categorias iniciais:

- alimentação/abastecimento, quando houver transação econômica própria e hook seguro;
- taxas administrativas;
- custos econômicos de construção/upgrade;
- serviços coloniais explicitamente integrados.

`basePrice` é data-driven/configurável.

## Regra de arredondamento

Nenhum preço positivo pode virar `0` por arredondamento.

Usar unidade mínima monetária ou `max(1, rounded)` na V1, salvo se a implementação introduzir subdivisão de moeda formal.

---

# Town Hall — Economia

Na primeira fase o Town Hall é a interface administrativa da economia.

A UI deve exibir no mínimo:

- nome da moeda;
- oferta emitida;
- oferta efetiva;
- Tesouro;
- reservas;
- circulação ativa/estimada;
- capacidade econômica;
- índice de preços;
- inflação do último período;
- alíquota/política tributária;
- receita tributária recente;
- gastos recentes;
- alertas de inconsistência econômica.

A UI é read-only para métricas e envia intents para ações administrativas.

Ação de emissão deve mostrar preflight antes de confirmar:

```text
Oferta atual
Oferta após emissão
Índice atual
Índice-alvo estimado
Risco inflacionário
```

A simulação/preflight é estritamente read-only.

---

# Emissão monetária

## V1

Enquanto o Banco físico não existir, a emissão é uma ação administrativa do Town Hall, sujeita a gate de progressão/config.

Possíveis gates:

- nível mínimo do Town Hall;
- research MineColonies/RPG específico;
- permissão do owner/officer conforme contrato real da API;
- cooldown administrativo para evitar spam de packets.

Não inventar o gate final sem auditar a API e o design de research da versão instalada.

## Emissão

Fluxo planejado:

```text
player intent
-> permission check
-> economy state load
-> precondition validation
-> idempotent transaction key
-> MINT ledger entry
-> supply mutation
-> snapshot publication
```

Emissão não cria capacidade econômica automaticamente.

---

# Retirada de circulação e desmonetização

Duas operações diferentes:

## Reserva

Move moeda ativa para reserva/Tesouro sem destruí-la.

## Retirada permanente

`RETIRE` reduz a oferta efetiva e destrói a unidade monetária correspondente dentro do pipeline econômico.

Não implementar destruição de item físico sem reconciliação transacional entre item e ledger.

---

# Impostos

A V1 deve suportar uma política tributária simples e auditável.

Separar:

- **alíquota**: percentual de política;
- **valor nominal**: resultado indexado pelo nível de preços quando aplicável.

Não aplicar imposto em todo movimento de item do MineColonies. Isso seria caro, frágil e semanticamente errado.

Taxar apenas eventos econômicos explicitamente integrados.

Toda cobrança:

```text
transaction -> tax calculation -> single ledger mutation -> treasury
```

Sem double tax por dois listeners diferentes.

---

# Construção e upgrades

## Objetivo

Adicionar custo econômico sem substituir o pipeline de materiais.

Exemplo de design:

```text
Upgrade Bakery II -> III
Materiais: definidos e consumidos pelo MineColonies
Custo econômico base: 12 C
Custo nominal: 12 C * priceIndex
```

## Gate técnico obrigatório

Antes de implementar, auditar na build `1.1.1375-1.21.1-snapshot`:

1. qual é o ponto autoritativo em que um build/upgrade é aceito;
2. se existe evento cancelável antes da criação/consumo de requests;
3. como identificar de forma estável colony/building/upgrade level;
4. como detectar cancelamento legítimo;
5. como executar refund exatamente uma vez;
6. como evitar cobrar preview, repair ou ações não equivalentes a upgrade.

Sem essa prova, `CONSTRUCTION_CHARGE` fica fail-closed.

---

# Banco/Tesouro físico — fase posterior

Não bloquear a V1 pela necessidade de um novo building.

## Progressão proposta

```text
Town Hall básico
-> economia administrativa
-> research Sistema Monetário
-> Banco/Tesouro
-> política monetária avançada
```

O Banco físico poderá concentrar:

- cunhagem;
- retirada/desmonetização;
- reservas;
- histórico econômico;
- políticas monetárias;
- posteriormente câmbio e reservas estrangeiras.

Antes de criar building custom, auditar:

- registration de buildings na versão instalada;
- BlockUI;
- schematics/style packs;
- research unlock;
- citizen/job requirement, se houver;
- compatibilidade com snapshot do MineColonies e addons atuais.

---

# Salários e economia dos cidadãos — fase posterior

Não incluir salários individuais na primeira implementação do core.

Fase posterior poderá adicionar:

- saldo/carteira do cidadão;
- salário por período/trabalho;
- consumo;
- impostos sobre transações;
- efeitos econômicos sobre felicidade apenas se houver hook legítimo.

MineColonies continua authority da entidade e estado do cidadão.

Não criar loop por tick de pagamento/consumo para cada cidadão se um settlement periódico bounded resolver a mesma necessidade.

---

# Comércio entre colônias e câmbio — fase futura

A identidade de moeda já deve permitir evolução para:

- moedas por colony ID;
- taxa de câmbio;
- exportações/importações;
- reservas estrangeiras;
- balança comercial.

Nada disso é requisito da V1.

YAGNI: não implementar FX engine antes do core monetário estar validado.

---

# Moeda física vs. saldo virtual

Decisão de implementação deve ser tomada após auditoria técnica.

## Opção A — saldo virtual autoritativo

Vantagens:

- ledger forte;
- fácil idempotência;
- menos dupes;
- melhor para serviços e impostos.

Risco:

- menos presença física no mundo.

## Opção B — item físico autoritativo

Vantagens:

- moeda tangível;
- pode circular em inventários.

Riscos:

- duplicação;
- drops;
- death;
- automation;
- containers externos;
- chunk movement;
- dificuldade de medir circulação real.

## Direção recomendada

V1: ledger/saldo autoritativo com representação física somente quando houver conversão transacional segura.

Nunca usar simultaneamente item e saldo como autoridades independentes.

---

# Anti-abuso e segurança

Obrigatório testar:

- duplicate packet;
- packet replay;
- reconnect durante transação;
- server restart durante settlement;
- chunk unload;
- cancelamento de construção;
- cancelamento/reenvio do mesmo upgrade;
- owner/officer sem permissão;
- colony inexistente/removida;
- valor negativo;
- integer overflow;
- emissão acima de limite configurado;
- preço que arredonda para zero;
- refund duplicado;
- ledger inconsistente;
- provider MineColonies ausente.

Provider ausente deve deixar todo adapter MineColonies inativo sem quebrar o RPG Core.

---

# Boundaries planejados

Os nomes finais podem ser ajustados ao padrão real do código, mas as responsabilidades devem permanecer separadas.

```java
interface ColonyEconomyQueryService {
    Optional<ColonyEconomySnapshot> query(String colonyId);
    EconomyPreflight simulateMint(String colonyId, long amount);
}
```

```java
interface ColonyEconomyMutationService {
    TransactionResult mint(MintCommand command);
    TransactionResult retire(RetireCommand command);
    TransactionResult charge(ChargeCommand command);
    TransactionResult refund(RefundCommand command);
}
```

```java
interface MineColoniesEconomyAdapter {
    Optional<ColonyEconomicInputs> queryEconomicInputs(String colonyId);
    AuthorizationDecision authorizeConstructionCharge(ConstructionContext context);
}
```

O adapter externo não escreve storage econômico diretamente.

---

# Persistência

O estado deve ser:

- versionado;
- server-side;
- migrável;
- idempotente em load/save;
- recuperável sem scan global de inventários/chunks.

Se a colônia for deletada, a política de retenção/arquivamento econômico deve ser explícita; não reutilizar automaticamente o saldo antigo caso um ID possa ser reciclado.

---

# Settlement periódico

Não recalcular toda a economia por tick.

Usar settlement bounded em intervalo configurado, por exemplo um período econômico discreto.

Settlement poderá:

1. consultar inputs do MineColonies;
2. recalcular `Q`;
3. calcular índice-alvo;
4. convergir o índice atual;
5. fechar métricas do período;
6. publicar snapshot.

O intervalo exato é parâmetro de implementação, não contrato congelado deste plano.

---

# Configuração server-side

Data/config mínima:

- feature toggle;
- preços-base por categoria;
- limites de emissão;
- parâmetros da curva inflacionária;
- velocidade de convergência;
- piso/teto de price index;
- policy de impostos;
- período de settlement;
- gates administrativos.

Config inválida deve falhar com diagnóstico claro e preservar última revisão válida quando aplicável.

---

# Observabilidade

Adicionar métricas/diagnóstico para:

- supply;
- treasury;
- circulação;
- `Q`;
- price index;
- inflação;
- mutations por kind;
- duplicate transaction rejection;
- adapter availability;
- settlement duration.

Não logar spam por cidadão/tick.

---

# Fases de implementação

## Fase 1 — Economy Core

Entregáveis:

- domínio monetário;
- persistence;
- ledger;
- query/mutation services;
- price index;
- inflation model;
- settlement;
- config;
- testes unitários.

Sem dependência de MineColonies no core.

## Fase 2 — MineColonies read-only adapter

Entregáveis:

- detectar provider/version;
- query de colony identity;
- query bounded dos inputs necessários a `Q`;
- testes com provider ausente/presente;
- nenhuma mutação MineColonies.

## Fase 3 — Town Hall Economy UI

Entregáveis:

- snapshot network;
- tela/aba econômica;
- preflight read-only;
- autorização server-side;
- mint/retire intents.

## Fase 4 — Construction Economy Gate

Somente após auditoria do hook da build instalada.

Entregáveis:

- cobrança antes do pipeline irreversível;
- idempotência;
- cancellation/refund;
- testes de upgrade/build.

## Fase 5 — Banco/Tesouro

Entregáveis:

- building/research/schematics;
- migração das ações monetárias avançadas do Town Hall para o Banco;
- compatibilidade com colony permissions.

## Fase 6 — Citizen Economy

Entregáveis futuros:

- salários;
- consumo;
- impostos econômicos reais;
- bounded settlement por cidadão/job.

## Fase 7 — Inter-colony Economy

Entregáveis futuros:

- comércio;
- FX;
- reservas;
- import/export ledger.

---

# Testes obrigatórios

## Unitários

- conservação monetária;
- emissão;
- retirada;
- ledger idempotente;
- replay rejection;
- price index determinístico;
- curva com supply abaixo/no/acima do equilíbrio;
- convergência gradual;
- piso/teto;
- arredondamento;
- settlement sem mutação quando inputs não mudam;
- schema migration.

## Integração MineColonies

- provider ausente -> adapter fail-soft/inativo;
- provider presente -> colony identity correta;
- owner/officer permission quando aplicável;
- query de capacidade bounded;
- construção cobrada uma vez;
- cancelamento/refund uma vez;
- upgrade repetido/replay não duplica cobrança;
- materiais continuam sendo authority do MineColonies.

## NeoForge/GameTests

Quando a fase correspondente existir:

- save/reload;
- restart-safe transaction identity;
- dedicated server;
- duas colônias com estados monetários separados;
- emissão em uma colônia não altera outra;
- price index separado por colônia;
- remover/recriar contexto não duplica supply.

## Acceptance econômico

Cenário mínimo:

```text
Colônia pequena
M_equilibrium ~= 20
M ~= 20
price index ~= 100
```

Emitir moeda sem crescimento de `Q` deve aumentar o target price index.

Expandir capacidade econômica sem nova emissão deve reduzir a pressão monetária relativa.

O comportamento exato é validado por fixtures determinísticas, não por percepção manual.

---

# Gates antes da implementação

- [ ] Auditar API/código real de MineColonies `1.1.1375-1.21.1-snapshot`.
- [ ] Identificar colony ID estável e lifecycle.
- [ ] Identificar permissions owner/officer.
- [ ] Identificar hooks read-only para população/buildings/jobs/research necessários a `Q`.
- [ ] Definir fórmula de `Q` com sinais disponíveis de verdade.
- [ ] Validar hook transacional de construção/upgrade antes de qualquer charge.
- [ ] Definir autoridade final entre saldo virtual e item físico.
- [ ] Definir schema persistente e migration policy.
- [ ] Calibrar curva inflacionária em testes.
- [ ] Validar integração com addons MineColonies instalados.
- [ ] Confirmar que nenhuma ação econômica concede Mastery por tick/throughput.

---

# Fora de escopo da primeira entrega

- empréstimos e juros;
- bancos privados;
- mercado de ações;
- dívida pública complexa;
- câmbio intercolônia;
- salário individual detalhado;
- rastreamento de cada coin em qualquer inventário do modpack;
- alteração do algoritmo nativo de builders/couriers;
- substituição de materials requests por dinheiro.

---

# Resultado esperado

A economia deve produzir decisões reais:

- emitir moeda para financiar expansão acelera liquidez, mas pressiona preços se `Q` não crescer;
- aumentar produção/população/logística amplia a capacidade econômica e permite uma oferta monetária maior;
- impostos financiam o Tesouro sem criar dinheiro;
- retirada/desmonetização reduz pressão monetária;
- construção continua materialmente dependente do MineColonies;
- o jogador administra uma colônia como sistema produtivo e monetário, não apenas como sequência de huts.

A identidade central do sistema é:

> **mais moeda sem mais economia reduz o poder de compra; mais economia permite sustentar mais moeda.**

Esse princípio deve permanecer verdadeiro independentemente do balanceamento numérico final.
