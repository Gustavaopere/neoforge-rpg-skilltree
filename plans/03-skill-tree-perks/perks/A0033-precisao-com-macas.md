# A0033 — Precisão com Maças

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** PRESENTE no resolver crítico, condicionada à família MACE segura.
- **Notion:** `3c569db9-f0db-8155-a97c-f4db0a54c59a`.

## Contrato canônico

- A0031 ≥1.
- +3% chance crítica MACE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- `ARCANE_BACKLASH`, proc terminal/secundário e dano de ally/bodyguard Mobstein são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(MACE)` mapeia A0033.
- `A0021A0040EpicFightHooks` reutiliza `A0001A0020RuntimeState.critical()` e correlaciona CriticalHitEvent/Epic Fight para evitar segunda rolagem.
- A classificação MACE ainda herda a pendência da tag paralela A0031.

## Provider→árvore

Black Arcana conserva authority de Backlash; Mobstein conserva ownership de companion damage. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Pendência Chat 2

Revalidar provenance/root action após `P-A0031-01`; não criar bridge específica para os providers retroauditados.
