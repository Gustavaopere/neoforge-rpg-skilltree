# A0038 — Treino com Foices II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** PRESENTE via cadence Epic Fight, condicionada à classificação SCYTHE segura.
- **Notion:** `3c569db9-f0db-81d5-b2c4-ca1fdde18212`.

## Contrato canônico

- A0037 ≥2.
- +2% velocidade/ritmo efetivo SCYTHE por rank, máximo +6%.
- `ModifyAttackSpeedEvent` somente quando o moveset/provider usa cadence server-authoritative.
- Sem hook estável, a parcela fica inativa; não converter em stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(SCYTHE)` mapeia A0038.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus às famílias do lote.
- A segurança depende da classificação SCYTHE corrigida em `P-A0037-01`.

## Provider→árvore

Volcanoes, Enshrouded e Black Arcana não fornecem cadence SCYTHE. Companions Mobstein não herdam A0038 do dono.

## Pendência Chat 2

Nenhuma nova além de `P-A0037-01`; revalidar cadence após a correção de família.
