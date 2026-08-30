# A0031 — Treino com Maças I

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA; `P-A0031-01` e `P-A0031-02` abertas.
- **Notion:** `3c569db9-f0db-8185-a66e-dfad8d451880`.

## Contrato canônico

- 3 ranks; +3% dano MACE/rank, máximo +9%.
- Gate: nível 8 + `combat:mace` ≥60 + gateway `combat_mace`.
- Maça vanilla: identidade exata `minecraft:mace` é receipt canônico do Minecraft.
- Armas externas: somente capability/categoria `mace` Epic Fight 21.17.3.1 ou mapping versionado explícito.
- `combat:mace`: +10 uma única vez por tipo hostil inédito persistido em `DiscoveryProgress`; 60 = 6 tipos.
- Sem classificação segura, fail-closed; não usar tag paralela, nome, material, dano ou semelhança com martelo.

## Evidência runtime

- `NotionCombatPerkRules` já aplica A0031 a `WeaponFamily.MACE`.
- `A0021A0040EpicFightHooks.family(...)` prefere categoria Epic Fight, mas ainda cai em `rpgskilltree:maces`.
- `maces.json` contém somente `minecraft:mace`; a identidade pode ser resolvida diretamente sem tag paralela.
- `A0021A0040MasteryPolicy` ainda concede 3 XP por hit confirmado para `combat:mace`.

## Pendências Chat 2

- **P-A0031-01:** remover a tag `rpgskilltree:maces` como classificador. Preservar `minecraft:mace` por identidade exata; externos somente provider-native/mapping seguro.
- **P-A0031-02:** substituir Mastery 3 XP/hit por `DiscoveryProgress` +10 por tipo hostil inédito; hits repetidos = 0 XP; gate60 = 6 tipos.

## Provider→árvore

- Black Arcana `ARCANE_BACKLASH` e companion-owned damage Mobstein dão 0 Mastery/dano de perk em nome do jogador.
- Volcanoes/Enshrouded não classificam MACE e não fornecem receipt MARTIAL.
- Ataques diretos do jogador contra entidades desses providers continuam cobertos universalmente quando o receipt MACE é válido.
