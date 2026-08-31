# A0125 — Economia Metabólica: Cortar Madeira

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
Ponte SURVIVAL_FORESTRY_BRIDGE/METABOLIC, 4 ranks, 1 PP/rank. −3% por rank da exhaustion positiva da quebra manual classificada como Silvicultura, até −12%; teto de 30%.

## Boundary
Classificador de madeira/árvore define a ação; o custo vem exclusivamente do FoodData exhaustion realmente debitado para a mesma action_id.

## Exclusões
Automação, bulk/tree-felling derivado sem novo débito, animação e durabilidade não criam custo. Bloco recolocado não é excluído por origem se a ação manual paga o mesmo custo real.

## Chat 2
Usar receipt causal de quebra manual e classificador explícito. Nunca fabricar custo para distinguir árvore natural de bloco colocado.