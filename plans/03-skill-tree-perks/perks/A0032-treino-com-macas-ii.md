# A0032 — Treino com Maças II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** PRESENTE, condicionada à classificação MACE segura de A0031.
- **Notion:** `3c569db9-f0db-81d7-8dcf-c065f2787d91`.

## Contrato canônico

- A0031 ≥2.
- +2% de velocidade/ritmo efetivo com maças por rank, máximo +6%.
- Epic Fight 21.17.3.1 `ModifyAttackSpeedEvent` somente quando o moveset usa cadência server-authoritative.
- Sem cadence hook estável, a parcela fica inativa; não substituir por stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(MACE)` mapeia A0032.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus provider-native.
- O adapter depende da resolução de família MACE; portanto herda `P-A0031-01` até a tag paralela ser removida.

## Provider→árvore

- Volcanoes, Enshrouded e Black Arcana não são providers de cadence MACE.
- Mobstein companions não herdam a cadence do dono.
- Nenhuma bridge nova é necessária.

## Pendência Chat 2

A0032 não exige implementação própria nova, mas só pode ser marcada confirmada após `P-A0031-01` preservar MACE com classificação provider-native/exata.
