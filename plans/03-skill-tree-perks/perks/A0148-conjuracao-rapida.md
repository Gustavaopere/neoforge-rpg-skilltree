# A0148 — Conjuração Rápida

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL POR CAST TEMPORAL.**

## Contrato
ARCANE/CASTING, 4 ranks, 1 PP/rank. +2% de velocidade de conjuração por rank; `tempo_ajustado = tempo_nativo / (1 + 0,02 × rank)`, até speed ×1,08.

## Boundary
Iron's 3.16.3 expõe `CAST_TIME_REDUCTION`/cast temporal; outros providers só entram se houver estágio server-authoritative modificável. Pisos/caps/arredondamento nativos vencem.

## Exclusões
Magia instantânea, cooldown, attack speed, duração de efeito e animação cosmética não são CAST_SPEED.

## Chat 2
Uma contribuição por action_id antes do início/commit do cast; provider/spell sem tempo real fica inativo.