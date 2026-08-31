# A0147 — Fluxo Arcano

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL POR PROVIDER DE MANA.**

## Contrato
ARCANE/MANA, 5 ranks, 1 PP/rank. +3% por rank sobre regeneração MANA nativa positiva, até +15%; taxa nativa 0 permanece 0.

## Boundary
Iron's 3.16.3 expõe `MANA_REGEN`; Ars Nouveau exige adapter seguro equivalente. Multiplicar a taxa já calculada, nunca criar pulso próprio.

## Exclusões
Não ignorar pausas/bloqueios do provider e não regenerar Source/Soul Energy/sangue/energia tecnológica.

## Chat 2
Provider sem hook de regen fica inativo; preservar regras nativas e zero autoritativo.