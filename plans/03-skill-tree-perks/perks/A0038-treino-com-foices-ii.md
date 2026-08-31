# A0038 — Treino com Foices II

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação cosmética.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252 após classificação SCYTHE segura.
- **Notion:** `3c569db9-f0db-81d5-b2c4-ca1fdde18212`.

## Contrato canônico

- A0037 ≥2.
- +2% velocidade/ritmo efetivo SCYTHE por rank, máximo +6%.
- `ModifyAttackSpeedEvent` somente quando o moveset/provider usa cadence server-authoritative.
- Sem hook estável, a parcela fica inativa; não converter em stamina, movimento, dano ou edição de animação.

## Evidência runtime

- `NotionCombatPerkRules.rhythmBonus(SCYTHE)` mapeia A0038.
- `A0021A0040EpicFightHooks.onAttackSpeed(...)` aplica o bônus provider-native às famílias classificadas.
- A família SCYTHE da PR #252 depende exclusivamente da capability/categoria Epic Fight ou mapping explícito; nenhum fallback por tag/nome/aparência permanece.

## Provider→árvore

Volcanoes, Enshrouded e Black Arcana não fornecem cadence SCYTHE. Companions Mobstein não herdam A0038 do dono.

## Pendência Chat 2 / resolução Chat 3

A dependência `P-A0037-01` foi encerrada. Nenhuma pendência própria bloqueante permanece para A0038; movesets sem cadence hook seguro continuam fail-closed por contrato.

## Validação Chat 3 — PR #252

- Classificação SCYTHE revalidada após remoção do tag paralelo.
- `RPG Skill Tree CI` #2806: JUnit 5, NeoForge GameTests, build e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: contrato A0038 validado; apta ao merge da PR #252.
