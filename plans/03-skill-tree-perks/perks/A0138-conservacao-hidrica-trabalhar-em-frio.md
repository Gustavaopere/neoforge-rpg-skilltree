# A0138 — Conservação Hídrica: Trabalhar Em Frio

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM SERVIÇOS CANÔNICOS/TWR.**

## Contrato
SURVIVAL/ACCLIMATION_COLD_HYDRATION, 4 ranks, 1 PP/rank. Sob `ADVERSE_COLD` + carga, −3% por rank somente no HYDRATION causal da mesma ação, até −12%; teto 30%.

## Boundary
Cold Sweat = estado read-only; TWR 3.0.4 = owner de HYDRATION; BodyCostResolver = correlação action_id.

## Exclusões
Não inventar surcharge hídrico de frio, não alterar temperatura e não usar polling de thirst.

## Chat 2
Uma aplicação por action_id, só com receipt hídrico real.