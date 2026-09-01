# A0033 — Precisão com Maças

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-8155-a97c-f4db0a54c59a`.

## Contrato canônico

- A0031 ≥1.
- +3% chance crítica MACE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- `ARCANE_BACKLASH`, proc terminal/secundário e dano de ally/bodyguard Mobstein são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(MACE)` mapeia A0033.
- `A0021A0040EpicFightHooks` reutiliza `A0001A0020RuntimeState.critical()` e correlaciona CriticalHitEvent/Epic Fight para evitar segunda rolagem.
- A resolução MACE foi endurecida: category/capability Epic Fight ou identidade exata `minecraft:mace`; sem tag paralela.

## Provider→árvore

Black Arcana conserva authority de Backlash; Mobstein conserva ownership de companion damage. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Fechamento Chat 2

A dependência técnica da classificação MACE insegura foi resolvida sem criar novo crítico ou bridge específica. Chat 3 deve revalidar uma única resolução/root, provenance, coexistência com A0062/A0063 e provider-present/absent. O Chat 2 não executou a bateria final.
