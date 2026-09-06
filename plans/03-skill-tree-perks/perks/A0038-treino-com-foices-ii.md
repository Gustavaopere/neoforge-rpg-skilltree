# A0038 — Treino com Foices II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359**.
- **Notion:** `3c569db9-f0db-81d5-b2c4-ca1fdde18212`.

## Contrato canônico

- A0037 ≥2.
- +2% velocidade/ritmo efetivo SCYTHE por rank, máximo +6%.
- `ModifyAttackSpeedEvent` somente quando o moveset/provider usa cadence server-authoritative.
- Sem hook estável, a parcela fica inativa; não converter em stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(SCYTHE)` mapeia A0038.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus às famílias do lote.
- A resolução SCYTHE depende exclusivamente de category/capability Epic Fight ou mapping explícito; a tag paralela foi removida.

## Provider→árvore

Volcanoes, Enshrouded e Black Arcana não fornecem cadence SCYTHE. Companions Mobstein não herdam A0038 do dono.

## Fechamento Chat 3

Cadence provider-native, ranks e fail-closed para armas não classificadas foram revalidados sem fallback semântico alternativo. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.
