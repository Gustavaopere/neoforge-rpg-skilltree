# A0146 — Reserva Arcana

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL POR PROVIDER DE MANA.**

## Contrato
ARCANE/MANA, 5 ranks, 1 PP/rank. +2% por rank no máximo de cada reservatório MANA elegível, até +10%, mantendo pools de providers separados.

## Boundary
Iron's 3.16.3 expõe `MAX_MANA`; Ars Nouveau entra apenas por API/atributo seguro equivalente. Cada provider recebe modificador independente.

## Exclusões
Não somar/converter pools e não transformar Source/Soul Energy/HP em mana. Rank up não refila; rank down só faz clamp se valor atual exceder novo máximo.

## Chat 2
Preservar valor absoluto atual em compra/respec/relog/troca de equipamento e sincronizar cada pool pelo owner nativo.