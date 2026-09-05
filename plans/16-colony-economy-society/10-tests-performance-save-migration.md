# 16.10 — Testes, performance e migração

## Invariantes automatizadas

- soma de transferências conserva moeda fora de source/sink explícito;
- receipt ID impede replay;
- saldo e debt são domínios distintos;
- citizen entity reload não cria nova carteira;
- warehouse transport não cria sale;
- tax/subsidy/price resolver é determinístico por revision;
- classes sociais são derivadas e recalculáveis.

## Camadas

- pure/JUnit: ledger, prices, payroll, tax, leases, wealth, debt;
- GameTests: MineColonies bridge quando provider presente, inventories/shops, lifecycle;
- provider-present matrix para MineColonies;
- dedicated-server core-only sem classloading opcional;
- client evidence para telas econômicas.

## Performance

Economia avança por eventos e `economic period`, não por cada cidadão a cada tick. Agregações pesadas usam batches bounded e checkpoints. Medir cenários com população grande antes de estabelecer budgets finais.

## Save/migration

Persistência versionada para accounts, contracts e compacted journal. Unknown actor/contract IDs entram em quarantine/recovery; dinheiro nunca é apagado para “consertar” save. Migração precisa provar conservation antes/depois.

## Gate

Stage fecha com fixtures de crash/retry, save/load, provider absence/presence, high-population simulation, build/JAR e dedicated-server smoke.

## Acceptance

A economia permanece consistente depois de restart, atualização e falha intermediária, sem crescimento ilimitado do journal.