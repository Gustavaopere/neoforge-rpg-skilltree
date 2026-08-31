# A0121 — Economia Metabólica: Escalar

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
Ramo SURVIVAL/METABOLISM_MOBILITY, 4 ranks, 1 PP/rank. Reduziria 3% por rank somente a parcela METABOLIC positiva e causal de uma escalada legítima, até 12%, dentro do teto canônico de 30% por evento.

## Boundary
ParCool 4.0.0.2 + Epic ParCool 21.0.0 podem classificar a ação, mas não foi provado débito de FoodData exhaustion causado pela escalada. O único boundary aceito é `METABOLIC_CLIMB` emitido por provider corporal real para a mesma action_id.

## Exclusões
Não criar exhaustion artificial, não converter Stamina em fome e não usar tempo/distância escalando como aproximação.

## Chat 2
Manter aquisição desabilitada, allocation legado com 0 PP para Gate B e reembolsável. Implementar somente se surgir provider versionado de `METABOLIC_CLIMB`; sem receipt causal, fail-closed.