# A0135 — Economia Metabólica: Trabalhar Em Calor

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM SERVIÇOS CANÔNICOS.**

## Contrato
SURVIVAL/ACCLIMATION_HOT, 4 ranks, 1 PP/rank. Sob `ADVERSE_HOT` + ≥1 carga do AcclimationLedger, −3% por rank somente no METABOLIC real de ação física elegível, até −12%; teto 30%.

## Boundary
Cold Sweat 2.4.2 fornece apenas estado corporal read-only; `BodyCostResolver` fornece o receipt da ação e `AcclimationLedger` a carga. Não é necessário existir surcharge metabólico de calor.

## Exclusões
Não alterar temperatura, BODY/WORLD/CORE/thresholds, não inventar exhaustion por estar quente e não usar FIRE damage como substituto.

## Chat 2
Implementar adapter Cold Sweat read-only + ledger server-authoritative + receipt causal; uma aplicação por action_id.