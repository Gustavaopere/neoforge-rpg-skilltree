# A0123 — Economia Metabólica: Minerar

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
Ponte SURVIVAL_MINING_BRIDGE/METABOLIC, 4 ranks, 1 PP/rank. −3% por rank sobre exhaustion positiva realmente debitada pela quebra manual classificada como MINING, até −12%; teto METABOLIC de 30% por evento.

## Boundary
A quebra manual possui custo corporal real no pipeline vanilla. `BodyCostResolver` deve correlacionar a action_id da quebra ao `Player#causeFoodExhaustion` efetivamente emitido e aplicar a economia antes do débito final.

## Exclusões
Máquinas/automação sem débito do jogador não entram. Vein mining/tree-felling/bulk break derivado não multiplica receipts. Não inferir custo por dureza, duração ou desgaste.

## Chat 2
Instrumentar receipt server-authoritative, uma resolução por action_id e classificador MINING explícito. Bridge PP conta para no máximo um threshold puro quando whitelistado.