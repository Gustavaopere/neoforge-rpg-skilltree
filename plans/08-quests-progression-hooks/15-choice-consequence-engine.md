# 08.15 — Choice & Consequence Engine — SIM/NÃO/ANTES/DEPOIS

## Goal
Fornecer um runtime declarativo para branching e failure-forward sem scripts duplicados por missão.

## Quest/beat definition
Cada beat relevante pode declarar:
- eligibility conditions;
- discovery conditions;
- discovery channels;
- preconditions;
- offer conditions;
- alternate entry conditions;
- prior-event reconciliation;
- choices;
- choice availability/explanation;
- immediate mutations;
- scheduled consequences;
- autonomous progression policy quando o mundo puder avançar sem o jogador;
- invalidation/expiry conditions;
- retrospective discovery hooks;
- journal visibility policy;
- failure-forward routes;
- actor-death fallbacks;
- resurrection/identity-continuity reconciliation quando aplicável;
- pre-resolved objective handling;
- resolution state;
- reward issuance key;
- cleanup/idempotency key.

O lifecycle completo de oportunidade/descoberta é especificado em `25-opportunity-discovery-lifecycle.md`. Não reduzir `LOCKED/ELIGIBLE`, `UNKNOWN/RUMORED/DISCOVERED/CONFIRMED`, oferta/engajamento e resolução a um único status.

## Resolution taxonomy
- `UNRESOLVED`
- `SUCCESS`
- `SUCCESS_WITH_COST`
- `PARTIAL_SUCCESS`
- `FAILURE`
- `PRODUCTIVE_FAILURE`
- `ABANDONED`
- `RESOLVED_BY_OTHERS`
- `PRE_RESOLVED`
- `OBSOLETE`
- `TRANSFORMED`

## Entregas
- [ ] Boolean/typed condition tree com AND/OR/NOT e comparadores cronológicos.
- [ ] `BEFORE/AFTER/NEVER/EVER` sobre ledger.
- [ ] Choice availability baseada em knowledge, relationships, factions, laws e provider queries.
- [ ] Opportunity/discovery/engagement/resolution como eixos ortogonais.
- [ ] Conteúdo `UNKNOWN` não aparece no journal apenas por ser `ELIGIBLE`.
- [ ] Recusa explícita distinguível de nunca receber oferta; abandono distinguível de simplesmente não agir.
- [ ] Deterministic mutation bundle com commit atômico quando necessário.
- [ ] One-shot reward issuance.
- [ ] Transformação de beat sem apagar histórico anterior.
- [ ] Abandono não implica apagar consequências já causadas.
- [ ] Quest deadline somente quando design explícito; não usar timers invisíveis indiscriminadamente.
- [ ] NPC retornado após morte deve consultar `26-death-resurrection-identity-continuity.md`; corpo vivo não satisfaz automaticamente gates de memória/identidade.

## Acceptance
Boss morto antes da missão gera rota `PRE_RESOLVED`/alternativa sem respawn artificial nem reward duplicado. NPC essencial morto troca para fallback ou perda legítima de informação, nunca soft-lock silencioso.

Se uma oportunidade estava `ELIGIBLE + UNKNOWN` e foi resolvida por terceiros, o jogador pode descobrir somente a consequência posterior sem receber retroativamente a missão original. Se um NPC necessário retorna via Mobstein, a rota é reavaliada por continuidade corporal/memória/identidade, e não por simples `alive=true`.
