# A0032 — Treino com Maças II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252 após a classificação MACE segura de A0031.
- **Notion:** `3c569db9-f0db-81d7-8dcf-c065f2787d91`.

## Contrato canônico

- A0031 ≥2.
- +2% de velocidade/ritmo efetivo com maças por rank, máximo +6%.
- Epic Fight 21.17.3.1 `ModifyAttackSpeedEvent` somente quando o moveset usa cadência server-authoritative.
- Sem cadence hook estável, a parcela fica inativa; não substituir por stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(MACE)` mapeia A0032.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus provider-native.
- A resolução de família MACE foi corrigida na PR #252: capability/categoria Epic Fight primeiro; fallback NeoForge somente para `Items.MACE`; tag paralela removida.

## Provider→árvore

- Volcanoes, Enshrouded e Black Arcana não são providers de cadence MACE.
- Mobstein companions não herdam a cadence do dono.
- Nenhuma bridge nova é necessária.

## Pendência Chat 2 / resolução Chat 3

A dependência de `P-A0031-01` foi encerrada. Nenhuma pendência própria bloqueante permanece para A0032.

## Validação Chat 3 — PR #252

- Classificação MACE revalidada sem tag paralela.
- Cadence continua limitada ao `ModifyAttackSpeedEvent` provider-native; ausência de hook continua fail-closed.
- `RPG Skill Tree CI` #2879 (run `33463430832`, HEAD `4813b2fd`): JUnit 5, **NeoForge JUnit adapter tests**, NeoForge GameTests, build e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #114 (run `33463430893`, HEAD `4813b2fd`): **GREEN**.
- Resultado: contrato A0032 validado; apta ao merge da PR #252.
