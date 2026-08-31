# A0127 — Economia Metabólica: Lutar Corpo a Corpo

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
Ponte SURVIVAL_MARTIAL_BRIDGE/METABOLIC, 4 ranks, 1 PP/rank. −3% por rank sobre o FoodData exhaustion realmente debitado por ataque melee elegível que acerte, até −12%; teto de 30%.

## Boundary
Minecraft 1.21.1 adiciona exhaustion no caminho de ataque bem-sucedido. Epic Fight 21.17.3.1 pode classificar/identificar ações integradas; `BodyCostResolver` correlaciona o outcome válido ao receipt corporal da mesma action_id.

## Exclusões
Ataque no vazio, DoT, proc derivado, reflexão, summon ou callback duplicado não criam nova economia. Stamina Epic Fight permanece recurso separado.

## Chat 2
Capturar o receipt no caminho pós-hit válido, deduplicar por action/outcome e manter bridge PP sem dupla contagem.