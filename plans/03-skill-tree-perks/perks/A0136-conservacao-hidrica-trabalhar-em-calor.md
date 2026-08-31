# A0136 — Conservação Hídrica: Trabalhar Em Calor

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM SERVIÇOS CANÔNICOS/TWR.**

## Contrato
SURVIVAL/ACCLIMATION_HOT_HYDRATION, 4 ranks, 1 PP/rank. Sob `ADVERSE_HOT` + carga, −3% por rank somente no HYDRATION causal da mesma ação física, até −12%; teto 30%.

## Boundary
Cold Sweat classifica estado; TWR 3.0.4 emite receipt hídrico; BodyCostResolver correlaciona a mesma action_id após resolução METABOLIC.

## Exclusões
Não reduzir sede basal/ambiental, temperatura ou FIRE damage; não inventar surcharge térmico.

## Chat 2
Separar canais METABOLIC/HYDRATION e aplicar uma vez apenas quando o receipt TWR existir.