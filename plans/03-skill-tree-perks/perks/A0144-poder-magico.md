# A0144 — Poder Mágico

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
Tronco ARCANE/POWER, 5 ranks, 1 PP/rank. +2% de MAGIC_POWER por rank, até +10%, sobre outcomes mágicos diretos elegíveis; aplicação universal ocorre uma vez antes de escola/especialização.

## Boundary
Iron's 3.16.3 expõe `SPELL_POWER` e eventos de dano/cura; Ars Nouveau 5.13.1 entra por adapter explícito de outcome. Adapter deve preservar provider_id, action/cast_id, owner_player e outcome_id.

## Exclusões
Não inferir magia por partícula/nome/dimensão/DamageSource genérico. Summons, rituais persistentes e outcomes derivados exigem contrato próprio de ownership.

## Chat 2
Deduplicar outcome e aplicar uma única camada universal de baixa magnitude.