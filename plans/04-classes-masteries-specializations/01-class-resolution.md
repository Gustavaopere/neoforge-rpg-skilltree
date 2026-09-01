# Classes Plan — Deterministic Class Resolution

**Goal:** resolver identidades de classe a partir de estado canônico, sem depender da ordem dos eventos.

**Estado atual:** PARCIAL AVANÇADO — a autoridade live de classes baseada em `ProgressionState + ClassRuleCatalog` já reconcilia deterministicamente após mutações e reload. A fronteira emergente `InvestmentState`/`ArchetypeResolver` agora também consegue projetar contribuição de nodes a partir de metadata canônica explícita; permanece aberta somente a semântica automática de contribuição de Mastery, que pertence ao fechamento do Stage 04.03 e não será inventada neste subplano.

- [x] Definir algoritmo para classes puras, híbridas e provider identities.
  - `ArchetypeResolver` aplica ordenação determinística por `specificity_score`, score efetivamente representado, prioridade e ID estável na fronteira de arquétipos.
  - `ClassResolutionQueryService` fecha a fronteira de consulta e rejeita IDs de arquétipo duplicados antes da resolução.
  - A autoridade live usa `ClassRuleCatalog` + `ProgressionService.reconcileAutomaticClasses/reconcilePaidClasses`, incluindo requisitos de domínio/final triad, nodes, Mastery e disponibilidade de provider.
- [ ] Consumir somente snapshot canônico de perks/masteries.
  - [x] Nodes comprados são projetados diretamente de `ProgressionState.passiveNodes()`.
  - [x] A contribution metadata de nodes vem somente das tags explícitas dos recursos `data/rpgskilltree/skills/`.
  - [x] Contrato explícito: cada rank comprado vale `1` ponto para cada tag `rpgskilltree:domain/<ProgressionDomain>` declarada; `rpgskilltree:domain/core` é neutra.
  - [x] `SkillInvestmentMetadataParser` não usa ID, posição ou topologia para inferir domínio; tag de domínio desconhecida invalida o reload.
  - [x] `ClassInvestmentMetadataCatalog` publica metadata vinculada à mesma revisão da skill tree; `ClassResolutionRuntime.resolveCanonical` rejeita revisão divergente.
  - [x] Node comprado sem metadata falha fechado e impede resolução emergente incompleta.
  - [ ] A conversão automática de thresholds de Mastery em tags/pesos de arquétipo continua deliberadamente ausente. `MasteryInvestmentMetadata` existe como contrato explícito, mas a fonte canônica/semântica deve ser definida no Stage 04.03.
  - Isso não bloqueia a autoridade live de classes em `ClassRuleCatalog`; impede apenas que a fronteira emergente invente semântica de Mastery ou vire uma segunda fonte de verdade.
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
- PR #365 adiciona o contrato `CanonicalInvestmentProjection`, fail-closed por node sem metadata, metadata revisionada e validação dos recursos canônicos. O checkpoint de teste isolado desta PR foi cancelado por pushes subsequentes; portanto não é registrado como RED observado.

**Acceptance:** PARCIAL somente pela dependência de Stage 04.03. A projeção canônica de investimento de nodes está materializada sem heurísticas e sem nova autoridade persistente. O único item ainda aberto neste subplano é a fonte/semântica canônica de contribuição de Mastery para a fronteira emergente; a autoridade live de classes já permanece funcional e determinística por `ClassRuleCatalog`.
