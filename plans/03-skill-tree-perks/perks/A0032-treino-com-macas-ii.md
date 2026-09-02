# A0032 — Treino com Maças II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359**.
- **Notion:** `3c569db9-f0db-81d7-8dcf-c065f2787d91`.

## Contrato canônico

- A0031 ≥2.
- +2% de velocidade/ritmo efetivo com maças por rank, máximo +6%.
- Epic Fight 21.17.3.1 `ModifyAttackSpeedEvent` somente quando o moveset usa cadência server-authoritative.
- Sem cadence hook estável, a parcela fica inativa; não substituir por stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(MACE)` mapeia A0032.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus provider-native.
- A resolução MACE é category/capability Epic Fight ou identidade exata `minecraft:mace`; a tag paralela foi removida.

## Provider→árvore

- Volcanoes, Enshrouded e Black Arcana não são providers de cadence MACE.
- Mobstein companions não herdam a cadence do dono.
- Nenhuma bridge nova é necessária.

## Fechamento Chat 3

Cadence provider-native e a família MACE endurecida foram revalidadas sem fallback semântico alternativo. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.
