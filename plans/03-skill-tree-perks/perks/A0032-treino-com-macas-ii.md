# A0032 — Treino com Maças II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-81d7-8dcf-c065f2787d91`.

## Contrato canônico

- A0031 ≥2.
- +2% de velocidade/ritmo efetivo com maças por rank, máximo +6%.
- Epic Fight 21.17.3.1 `ModifyAttackSpeedEvent` somente quando o moveset usa cadência server-authoritative.
- Sem cadence hook estável, a parcela fica inativa; não substituir por stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(MACE)` mapeia A0032.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus provider-native.
- A resolução MACE foi endurecida pelo Chat 2: categoria/capability Epic Fight ou identidade exata `minecraft:mace`; a tag paralela foi removida.

## Provider→árvore

- Volcanoes, Enshrouded e Black Arcana não são providers de cadence MACE.
- Mobstein companions não herdam a cadence do dono.
- Nenhuma bridge nova é necessária.

## Fechamento Chat 2

A dependência técnica de `P-A0031-01` foi removida no runtime. A0032 não ganhou mecânica alternativa: continua usando exclusivamente o cadence hook aprovado. Chat 3 deve validar ranks, provider-present/absent, classificação MACE e ausência de aplicação em famílias desconhecidas. O Chat 2 não executou a bateria final.
