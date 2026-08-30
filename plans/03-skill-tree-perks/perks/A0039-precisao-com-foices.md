# A0039 — Precisão com Foices

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** PRESENTE no resolver crítico, condicionada à família SCYTHE segura.
- **Notion:** `3c569db9-f0db-81a5-9f5f-e5b382c32741`.

## Contrato canônico

- A0037 ≥1.
- +3% chance crítica SCYTHE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- Backlash terminal/secundário e companion-owned damage são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(SCYTHE)` mapeia A0039.
- `A0021A0040EpicFightHooks` usa o critical service/root action canônico para famílias não-DAGGER.
- A resolução de família ainda depende da correção `P-A0037-01`.

## Provider→árvore

Black Arcana `ARCANE_BACKLASH` nunca entra no crítico; Mobstein companions não recebem autoria do dono. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Pendência Chat 2

Revalidar deduplicação/provenance depois da remoção da tag SCYTHE; nenhuma bridge nova.
