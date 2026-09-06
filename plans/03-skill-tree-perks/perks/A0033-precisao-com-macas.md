# A0033 — Precisão com Maças

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359**.
- **Notion:** `3c569db9-f0db-8155-a97c-f4db0a54c59a`.

## Contrato canônico

- A0031 ≥1.
- +3% chance crítica MACE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- `ARCANE_BACKLASH`, proc terminal/secundário e dano de ally/bodyguard Mobstein são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(MACE)` mapeia A0033.
- `A0021A0040EpicFightHooks` reutiliza `A0001A0020RuntimeState.critical()` e correlaciona CriticalHitEvent/Epic Fight para evitar segunda rolagem.
- A resolução MACE usa category/capability Epic Fight ou identidade exata `minecraft:mace`; sem tag paralela.

## Provider→árvore

Black Arcana conserva authority de Backlash; Mobstein conserva ownership de companion damage. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Fechamento Chat 3

Crítico canônico, provenance e família MACE segura foram revalidados sem segundo pipeline. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.
