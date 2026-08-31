# A0116 — Conservação Hídrica: Correr

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL com adapter TWR versionado.**

## Contrato
Ramo SURVIVAL/HYDRATION, 4 ranks. −3% por rank, até −12%, somente sobre a parcela hídrica atribuída à corrida; teto HYDRATION compartilhado 30%/evento.

## Provider real
Thirst Was Reclaimed 3.0.4 é owner. O provider deriva sua exhaustion hídrica da variação de FoodData exhaustion e depois aplica seus modificadores. A0116 não é cópia de A0115: `BodyCostResolver` deve levar a mesma action-id até o adapter TWR e reduzir somente a contribuição HYDRATION aceita pelo provider.

## Proibições
Não escrever na barra de sede; não estimar por polling/delta de thirst; Thirst Was Fixed 2.1.5 é compat/fix, não owner paralelo. A0115 e A0116 podem compor apenas em lanes tipadas distintas.

## Chat 2
Testar múltiplas actions no mesmo tick, modificadores ambientais TWR, ausência do mod e deduplicação causal.