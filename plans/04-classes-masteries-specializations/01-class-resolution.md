# Classes Plan — Deterministic Class Resolution

**Goal:** resolver identidades de classe a partir de estado canônico, sem depender da ordem dos eventos.

**Estado atual:** PARCIAL — a fronteira pura/read-only de resolução está materializada e testada, mas a autoridade live continua deliberadamente bloqueada até existir metadado canônico suficiente para construir `InvestmentState` a partir da progressão real sem inventar pesos nem duplicar o sistema legado.

- [x] Definir algoritmo para classes puras, híbridas e provider identities.
  - `ArchetypeResolver` aplica ordenação determinística por `specificity_score`, score efetivamente representado, prioridade e ID estável.
  - `ClassResolutionQueryService` fecha a fronteira de consulta e rejeita IDs de arquétipo duplicados antes da resolução.
- [ ] Consumir somente snapshot canônico de perks/masteries.
  - A query aceita somente um `InvestmentState` já construído, mas a progressão live ainda não expõe contribuição canônica suficiente dos nós/masteries para produzi-lo fielmente.
  - **Fail-closed:** `ClassResolutionRuntime` não recebe `ServerPlayer`, não lê estado persistido e não sintetiza pesos/tags ausentes.
- [x] Permitir múltiplas identidades quando o design admitir.
  - `EmergentClassResolution` expõe classe primária e identidades secundárias ordenadas, sem persistir uma segunda fonte de verdade.
- [x] Definir precedência apenas quando semanticamente necessária.
  - A precedência é estável e data-driven; empates terminam em ID estável, portanto a ordem de carga/datapack não altera o resultado.
- [ ] Recalcular após compra, respec, mastery threshold e reload.
  - O catálogo de arquétipos já é reloadable e a query sempre lê o snapshot corrente do `ArchetypeCatalog`.
  - O disparo live após mutações permanece bloqueado pelo mesmo pré-requisito de construção canônica de `InvestmentState`.

## Evidência do ciclo estrutural

- TDD RED: PR #284 / `RPG Skill Tree CI` #2434 falhou em `Core tests` antes da existência de `ClassResolutionQueryService`.
- A query é determinística para a mesma build mesmo quando a coleção de definições chega em ordem inversa.
- Múltiplas identidades mantêm ordenação determinística e catálogo vazio produz resolução vazia.
- Definições conflitantes com o mesmo ID são rejeitadas no boundary.
- Existe facade runtime catalog-backed (`ClassResolutionRuntime`) sem qualquer leitura/mutação de jogador.

**Acceptance:** PARCIAL. A propriedade determinística da resolução pura está coberta; o acceptance live só poderá ser fechado quando a camada canônica de progressão fornecer o `InvestmentState` fiel e puder acionar recomputação após compra, respec, mastery threshold e reload sem depender do sistema de perks como implementação paralela.
