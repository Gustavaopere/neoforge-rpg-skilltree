# 19.10 — Testes, performance e fail-soft

## Pure/JUnit

- crisis state machine;
- thermal demand;
- graph allocation;
- fuel accounting;
- priorities/rationing;
- exposure progression.

## GameTests/provider

- Generator Core inventory consumption;
- network connect/disconnect;
- building endpoint;
- MineColonies citizen exposure;
- Cold Sweat integration;
- Create integration;
- save/reload during crisis.

## Performance

Cenários: centenas de buildings/endpoints, network split, many citizens, severity changes. Recompute por revision/components, não tick-global. Medir antes de fixar budgets finais.

## Fail-soft matrix

- sem MineColonies;
- sem Cold Sweat;
- sem Create;
- sem climate provider;
- com cada provider individualmente;
- combinação completa.

Ausência reduz funcionalidades correspondentes sem `ClassNotFoundException` ou save loss.

## Blueprint gate

Central Térmica níveis 1–5 deve passar Stage 14 preview/export/BOM/provider checks.

## Acceptance

Dedicated server core-only inicia; stack completo funciona em provider-present tests; crise grande não introduz varredura O(cidadãos×prédios) por tick.