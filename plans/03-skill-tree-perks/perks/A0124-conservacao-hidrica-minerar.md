# A0124 — Conservação Hídrica: Minerar

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM ADAPTER TWR.**

## Contrato
SURVIVAL/MINING_HYDRATION, 4 ranks, 1 PP/rank. −3% por rank somente no parcel HYDRATION causal da mesma quebra manual MINING, até −12%; teto HYDRATION de 30%.

## Boundary
Reutilizar a action_id/receipt METABOLIC de A0123 e aceitar apenas o parcel efetivamente emitido pelo adapter Thirst Was Reclaimed 3.0.4 após a resolução metabólica.

## Exclusões
Não reduzir desidratação basal, calor subterrâneo, temperatura ou custos de máquina. Create/Oritech 1.2.11 não entram sem débito corporal do jogador.

## Chat 2
Implementar correlação METABOLIC→HYDRATION sem escrita direta em thirst e sem polling. Ausência de receipt hídrico desativa somente o benefício daquele evento.