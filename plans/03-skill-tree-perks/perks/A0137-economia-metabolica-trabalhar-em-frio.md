# A0137 — Economia Metabólica: Trabalhar Em Frio

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM SERVIÇOS CANÔNICOS.**

## Contrato
SURVIVAL/ACCLIMATION_COLD, 4 ranks, 1 PP/rank. Sob `ADVERSE_COLD` + ≥1 carga, −3% por rank somente no METABOLIC real de ação física elegível, até −12%; teto 30%.

## Boundary
Cold Sweat 2.4.2 fornece estado read-only; BodyCostResolver fornece o custo real e AcclimationLedger a carga.

## Exclusões
Não alterar temperatura/thresholds, não usar ICE damage/bioma/WORLD como substituto e não inventar surcharge de frio.

## Chat 2
Mesmo contrato causal de A0135, com lane COLD independente.