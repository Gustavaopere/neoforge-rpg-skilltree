# A0039 — Precisão com Foices

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252 após correção da família SCYTHE.
- **Notion:** `3c569db9-f0db-81a5-9f5f-e5b382c32741`.

## Contrato canônico

- A0037 ≥1.
- +3% chance crítica SCYTHE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- Backlash terminal/secundário e companion-owned damage são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(SCYTHE)` mapeia A0039.
- `A0021A0040EpicFightHooks` usa o critical service/root action canônico para famílias não-DAGGER.
- SCYTHE agora só é resolvida por capability/categoria Epic Fight ou mapping explícito; nenhum classificador paralelo permanece.
- O lote preserva o serviço crítico compartilhado e a correlação por root action, sem segunda rolagem específica para A0039.

## Provider→árvore

Black Arcana `ARCANE_BACKLASH` nunca entra no crítico; Mobstein companions não recebem autoria do dono. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Pendência Chat 2 / resolução Chat 3

A dependência `P-A0037-01` foi encerrada. Deduplicação/provenance foi revalidada no pipeline canônico; nenhuma bridge nova é necessária.

## Validação Chat 3 — PR #252

- Classificação SCYTHE e critical/root action foram revalidados após a remoção do tag paralelo.
- `RPG Skill Tree CI` #2806: Core, JUnit 5, GameTests, runtime validations, build e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: contrato A0039 validado; apta ao merge da PR #252.
