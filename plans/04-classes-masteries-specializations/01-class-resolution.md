# Classes Plan — Deterministic Class Resolution

**Goal:** resolver identidades de classe a partir de estado canônico, sem depender da ordem dos eventos.

**Estado atual:** PARCIAL — a autoridade live de classes baseada em `ProgressionState + ClassRuleCatalog` já reconcilia determinísticamente após mutações e reload; a fronteira histórica de arquétipos baseada em `InvestmentState` continua deliberadamente read-only até existir metadado canônico suficiente para projetar contribuições sem inventar pesos nem duplicar a autoridade live.

- [x] Definir algoritmo para classes puras, híbridas e provider identities.
  - `ArchetypeResolver` aplica ordenação determinística por `specificity_score`, score efetivamente representado, prioridade e ID estável na fronteira de arquétipos.
  - `ClassResolutionQueryService` fecha a fronteira de consulta e rejeita IDs de arquétipo duplicados antes da resolução.
  - A autoridade live usa `ClassRuleCatalog` + `ProgressionService.reconcileAutomaticClasses/reconcilePaidClasses`, incluindo requisitos de domínio/final triad, nodes, Mastery e disponibilidade de provider.
- [ ] Consumir somente snapshot canônico de perks/masteries.
  - A query de arquétipos aceita somente um `InvestmentState` já construído, mas a progressão live ainda não expõe contribution metadata suficiente dos nós/masteries para produzi-lo fielmente.
  - **Fail-closed:** `ClassResolutionRuntime` não recebe `ServerPlayer`, não lê estado persistido e não sintetiza pesos/tags ausentes.
  - Isso não bloqueia a autoridade live de classes em `ClassRuleCatalog`; impede apenas promover o protótipo `InvestmentState` a uma segunda fonte de verdade.
- [x] Permitir múltiplas identidades quando o design admitir.
  - `EmergentClassResolution` expõe classe primária e identidades secundárias ordenadas, sem persistir uma segunda fonte de verdade.
- [x] Definir precedência apenas quando semanticamente necessária.
  - A precedência é estável e data-driven; empates terminam em ID estável, portanto a ordem de carga/datapack não altera o resultado.
- [x] Recalcular após compra, respec, mastery threshold e reload.
  - `PlayerProgressionRuntime.purchaseNode`, `respecNode` e `awardMasteryAndDiscoveries` passam pelo mesmo `reconcileDerivedState` canônico.
  - `PlayerProgressionEvents` reconcilia login/respawn; sync direcionado de login não é processado novamente pelo hook de datapack.
  - `ProgressionDatapackEvents` usa `OnDatapackSyncEvent` somente para reload global (`event.getPlayer() == null`) e reconcilia exatamente os `getRelevantPlayers()` depois de o datapack terminar de sincronizar.
  - O hook é auto-registrado no GAME bus por `@EventBusSubscriber`; não há registro manual duplicado.
  - A reconciliação continua usando `ProgressionService.reconcileAutomaticClasses/reconcilePaidClasses`, portanto reload não cria uma segunda mutation path.

## Evidência dos ciclos estruturais

- TDD RED inicial: PR #284 / `RPG Skill Tree CI` #2434 falhou em `Core tests` antes da existência de `ClassResolutionQueryService`.
- A query de arquétipos é determinística para a mesma build mesmo quando a coleção de definições chega em ordem inversa.
- Múltiplas identidades mantêm ordenação determinística e catálogo vazio produz resolução vazia.
- Definições conflitantes com o mesmo ID são rejeitadas no boundary.
- Existe facade runtime catalog-backed (`ClassResolutionRuntime`) sem qualquer leitura/mutação de jogador.
- TDD RED do reload: PR #324 / `RPG Skill Tree CI` #2756 falhou na compilação do teste porque `ProgressionDatapackEvents` ainda não existia.
- GREEN funcional do reload: PR #324 / `RPG Skill Tree CI` #2766 passou JUnit 5, NeoForge GameTests, validadores, build, verificação do JAR e dedicated-server smoke.

**Acceptance:** PARCIAL. A autoridade live de classes já recompõe o estado derivado após compra, respec, Mastery e reload sem depender da ordem de eventos. O acceptance completo continua bloqueado somente na projeção canônica de contribution metadata para a fronteira `InvestmentState`/`ArchetypeResolver`; até isso existir, essa fronteira permanece read-only e não compete com `ClassRuleCatalog`.
