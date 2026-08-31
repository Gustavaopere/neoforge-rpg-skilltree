# A0037 — Treino com Foices I

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252; `P-A0037-01` e `P-A0037-02` resolvidas pelo Chat 3.
- **Notion:** `3c569db9-f0db-8188-abff-fde5db20381a`.

## Contrato canônico

- 3 ranks; +3% dano SCYTHE/rank, máximo +9%.
- Gate: nível 8 + `combat:scythe` ≥60 + gateway `combat_scythe`.
- Somente capability/categoria `scythe` Epic Fight 21.17.3.1 ou mapping versionado explícito.
- `combat:scythe`: +10 uma única vez por tipo hostil inédito em `DiscoveryProgress`; 60 = 6 tipos.
- Sem classificação segura, fail-closed; foice de combate não é enxada vanilla por nome/aparência.

## Evidência runtime

- `NotionCombatPerkRules` mapeia A0037 a `WeaponFamily.SCYTHE`.
- `A0021A0040EpicFightHooks.family(...)` e `A0021A0040MasteryHooks` aceitam SCYTHE somente pela capability/categoria provider-native Epic Fight; não existe fallback vanilla ou tag paralelo.
- O recurso `rpgskilltree:scythes` foi removido na PR #252 e busca de código não encontra mais `SCYTHES`/tag paralelo.
- `A0021A0040MasteryPolicy.forConfirmedDirectHit(...)` concede 0 XP repetível para SCYTHE; discovery finita concede +10 uma vez por tipo hostil inédito em `mastery/combat:scythe/entity_type/<id>`.

## Pendências Chat 2 / resolução Chat 3

- **P-A0037-01 — RESOLVIDA:** tag paralela SCYTHE removida; categoria Epic Fight/mapping explícito ou fail-closed.
- **P-A0037-02 — RESOLVIDA:** 3 XP/hit substituído por `DiscoveryProgress` +10 por tipo hostil inédito; hits repetidos = 0; gate60 = 6 tipos.

## Provider→árvore

Weapons of Miracles/Epic Fight Compat só participam quando a capability final classifica `scythe`. Backlash e companions Mobstein dão 0 Mastery/autoria; Volcanoes/Enshrouded não classificam foice.

## Validação Chat 3 — PR #252

- Contratos JUnit confirmam Mastery SCYTHE finita, replay-safe e não-farmável.
- Busca de código confirma ausência do classificador paralelo.
- `RPG Skill Tree CI` #2806: Core, JUnit 5, NeoForge GameTests, runtime/data validations, build, JAR e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: contrato A0037 validado; apta ao merge da PR #252.
