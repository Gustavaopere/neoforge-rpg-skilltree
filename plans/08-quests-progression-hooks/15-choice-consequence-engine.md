# 08.15 — Choice & Consequence Engine — SIM/NÃO/ANTES/DEPOIS

## Goal
Fornecer um runtime declarativo para branching e failure-forward sem scripts duplicados por missão.

## Quest/beat definition
Cada beat relevante pode declarar:
- discovery conditions;
- preconditions;
- alternate entry conditions;
- prior-event reconciliation;
- choices;
- choice availability/explanation;
- immediate mutations;
- scheduled consequences;
- failure-forward routes;
- actor-death fallbacks;
- pre-resolved objective handling;
- resolution state;
- reward issuance key;
- cleanup/idempotency key.

## Resolution taxonomy
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
- [ ] Deterministic mutation bundle com commit atômico quando necessário.
- [ ] One-shot reward issuance.
- [ ] Transformação de beat sem apagar histórico anterior.
- [ ] Abandono não implica apagar consequências já causadas.
- [ ] Quest deadline somente quando design explícito; não usar timers invisíveis indiscriminadamente.

## Acceptance
Boss morto antes da missão gera rota `PRE_RESOLVED`/alternativa sem respawn artificial nem reward duplicado. NPC essencial morto troca para fallback ou perda legítima de informação, nunca soft-lock silencioso.