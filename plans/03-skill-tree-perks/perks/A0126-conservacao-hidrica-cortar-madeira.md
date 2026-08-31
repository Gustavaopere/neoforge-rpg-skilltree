# A0126 — Conservação Hídrica: Cortar Madeira

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM ADAPTER TWR.**

## Contrato
SURVIVAL/FORESTRY_HYDRATION, 4 ranks, 1 PP/rank. −3% por rank somente no HYDRATION causal da mesma ação manual de Silvicultura, até −12%; teto de 30%.

## Boundary
Mesma action_id de A0125 + receipt HYDRATION emitido pelo adapter versionado do Thirst Was Reclaimed 3.0.4 após eficiência metabólica.

## Exclusões
Sem polling, sem escrita direta em thirst, sem custo hídrico artificial por tree-felling ou automação e sem redução de clima/temperatura.

## Chat 2
Preservar separação METABOLIC/HYDRATION e uma aplicação por action_id.