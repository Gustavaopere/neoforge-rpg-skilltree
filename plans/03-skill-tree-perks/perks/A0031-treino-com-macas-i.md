# A0031 — Treino com Maças I

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-8185-a66e-dfad8d451880`.

## Contrato canônico

- 3 ranks; +3% dano MACE/rank, máximo +9%.
- Gate: nível 8 + `combat:mace` ≥60 + gateway `combat_mace`.
- Maça vanilla: identidade exata `minecraft:mace` é receipt canônico do Minecraft.
- Armas externas: somente capability/categoria `mace` Epic Fight 21.17.3.1 ou mapping versionado explícito.
- `combat:mace`: +10 uma única vez por tipo hostil inédito persistido em `DiscoveryProgress`; 60 = 6 tipos.
- Sem classificação segura, fail-closed; não usar tag paralela, nome, material, dano ou semelhança com martelo.

## Evidência runtime

- `NotionCombatPerkRules` aplica A0031 a `WeaponFamily.MACE`.
- `A0021A0040EpicFightHooks.family(...)` agora prefere categoria Epic Fight e, fora do provider, aceita somente a identidade exata `minecraft:mace`.
- A tag paralela `rpgskilltree:maces` foi removida do runtime e do datapack.
- `A0021A0040MasteryPolicy`/`MasteryHooks` agora usam `DiscoveryProgress`: +10 uma única vez por tipo hostil inédito, sem XP repetível por hit.

## Pendências Chat 2 resolvidas

- **P-A0031-01 — RESOLVIDA NO CÓDIGO:** removido o classificador por tag; vanilla usa `Items.MACE`, externos somente provider-native.
- **P-A0031-02 — RESOLVIDA NO CÓDIGO:** `combat:mace` migrou de +3 XP/hit para +10 por tipo hostil inédito; repeat hit = 0.

## Provider→árvore

- Black Arcana `ARCANE_BACKLASH` e companion-owned damage Mobstein dão 0 Mastery/dano de perk em nome do jogador.
- Volcanoes/Enshrouded não classificam MACE e não fornecem receipt MARTIAL.
- Ataques diretos do jogador contra entidades desses providers continuam cobertos universalmente quando o receipt MACE é válido.

## Handoff Chat 3

Validar provider-present Epic Fight MACE, fallback exato `minecraft:mace`, unknown/external sem category em fail-closed, discovery persistente/deduplicada, 6 tipos→60 e 8 tipos→80, lifecycle/multiplayer e regressão contra HAMMER/SCYTHE. O Chat 2 não executou a bateria final.
