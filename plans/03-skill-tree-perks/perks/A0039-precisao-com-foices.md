# A0039 — Precisão com Foices

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359**.
- **Notion:** `3c569db9-f0db-81a5-9f5f-e5b382c32741`.

## Contrato canônico

- A0037 ≥1.
- +3% chance crítica SCYTHE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- Backlash terminal/secundário e companion-owned damage são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(SCYTHE)` mapeia A0039.
- `A0021A0040EpicFightHooks` usa o critical service/root action canônico para famílias não-DAGGER.
- A resolução SCYTHE é category/capability provider-native; não existe fallback por tag/nome/hoe.

## Provider→árvore

Black Arcana `ARCANE_BACKLASH` nunca entra no crítico; Mobstein companions não recebem autoria do dono. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Fechamento Chat 3

Deduplicação/provenance e uma única resolução crítica por root foram revalidadas sobre a família SCYTHE endurecida. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.
