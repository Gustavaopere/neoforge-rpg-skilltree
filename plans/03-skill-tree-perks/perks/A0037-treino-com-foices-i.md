# A0037 — Treino com Foices I

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359**.
- **Notion:** `3c569db9-f0db-8188-abff-fde5db20381a`.

## Contrato canônico

- 3 ranks; +3% dano SCYTHE/rank, máximo +9%.
- Gate: nível 8 + `combat:scythe` ≥60 + gateway `combat_scythe`.
- Somente capability/categoria `scythe` Epic Fight 21.17.3.1 ou mapping versionado explícito.
- `combat:scythe`: +10 uma única vez por tipo hostil inédito em `DiscoveryProgress`; 60 = 6 tipos.
- Sem classificação segura, fail-closed; foice de combate não é enxada vanilla por nome/aparência.

## Evidência runtime

- `NotionCombatPerkRules` mapeia A0037 a `WeaponFamily.SCYTHE`.
- `A0021A0040EpicFightHooks` e `A0021A0040MasteryHooks` aceitam SCYTHE somente por categoria/capability Epic Fight; não existe fallback vanilla/tag.
- A tag paralela `rpgskilltree:scythes` foi removida do datapack.
- `A0021A0040MasteryPolicy`/`MasteryHooks` usam discovery finita +10 por tipo hostil inédito; repeat hit = 0.

## Pendências resolvidas

- **P-A0037-01 — RESOLVIDA:** removido/desativado o classificador por tag; unknown = fail-closed.
- **P-A0037-02 — RESOLVIDA:** `combat:scythe` usa `DiscoveryProgress`, +10/tipo; 6 tipos→60.

## Provider→árvore

Weapons of Miracles/Epic Fight Compat só participam quando a capability final classifica `scythe`. Backlash e companions Mobstein dão 0 Mastery/autoria; Volcanoes/Enshrouded não classificam foice.

## Fechamento Chat 3

Provider-present/absent, ausência de fallback por hoe/nome/tag e discovery finita foram revalidados. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.
