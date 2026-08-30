# A0037 — Treino com Foices I

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA; `P-A0037-01` e `P-A0037-02` abertas.
- **Notion:** `3c569db9-f0db-8188-abff-fde5db20381a`.

## Contrato canônico

- 3 ranks; +3% dano SCYTHE/rank, máximo +9%.
- Gate: nível 8 + `combat:scythe` ≥60 + gateway `combat_scythe`.
- Somente capability/categoria `scythe` Epic Fight 21.17.3.1 ou mapping versionado explícito.
- `combat:scythe`: +10 uma única vez por tipo hostil inédito em `DiscoveryProgress`; 60 = 6 tipos.
- Sem classificação segura, fail-closed; foice de combate não é enxada vanilla por nome/aparência.

## Evidência runtime

- `NotionCombatPerkRules` mapeia A0037 a `WeaponFamily.SCYTHE`.
- `A0021A0040EpicFightHooks.family(...)` e `A0021A0040MasteryHooks` ainda possuem fallback `rpgskilltree:scythes`; o tag atual está vazio, mas continua sendo um classificador paralelo indevido.
- `A0021A0040MasteryPolicy` concede 3 XP por hit para `combat:scythe`.

## Pendências Chat 2

- **P-A0037-01:** remover/desativar a tag paralela SCYTHE. Categoria Epic Fight/mapping explícito ou fail-closed.
- **P-A0037-02:** substituir 3 XP/hit por `DiscoveryProgress` +10 por tipo hostil inédito; hits repetidos = 0; gate60 = 6 tipos.

## Provider→árvore

Weapons of Miracles/Epic Fight Compat só participam quando a capability final classifica `scythe`. Backlash e companions Mobstein dão 0 Mastery/autoria; Volcanoes/Enshrouded não classificam foice.
