# A0031 — Treino com Maças I

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252; `P-A0031-01` e `P-A0031-02` resolvidas pelo Chat 3.
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
- `A0021A0040EpicFightHooks.family(...)` usa primeiro a capability/categoria Epic Fight e, sem provider classification, aceita somente `Items.MACE` como fallback vanilla exato.
- `A0021A0040MasteryHooks` usa o mesmo critério: HAMMER/MACE/SCYTHE provider-native e fallback NeoForge somente para `minecraft:mace`.
- Os recursos `rpgskilltree:maces` e `rpgskilltree:scythes` foram removidos da PR #252; busca de código não encontra mais `MACES`, `SCYTHES` nem os tags paralelos.
- `A0021A0040MasteryPolicy.forConfirmedDirectHit(...)` concede 0 XP repetível para MACE; `forDistinctHostileTypeDiscovery(...)` concede +10 uma única vez por tipo hostil inédito e grava discovery key `mastery/combat:mace/entity_type/<id>`.

## Pendências Chat 2 / resolução Chat 3

- **P-A0031-01 — RESOLVIDA:** tag `rpgskilltree:maces` removida. `minecraft:mace` permanece por identidade exata; externos exigem provider-native/mapping versionado.
- **P-A0031-02 — RESOLVIDA:** Mastery repetível de 3 XP/hit removida; `combat:mace` usa discovery finita +10/tipo hostil, hits repetidos = 0 XP.

## Provider→árvore

- Black Arcana `ARCANE_BACKLASH` e companion-owned damage Mobstein dão 0 Mastery/dano de perk em nome do jogador.
- Volcanoes/Enshrouded não classificam MACE e não fornecem receipt MARTIAL.
- Ataques diretos do jogador contra entidades desses providers continuam cobertos universalmente quando o receipt MACE é válido.

## Validação Chat 3 — PR #252

- Regressões de Mastery confirmam +10 por tipo hostil distinto, replay-safe, e 0 XP por hit repetido.
- `RPG Skill Tree CI` #2879 (run `33463430832`, HEAD `4813b2fd`): JUnit 5, **NeoForge JUnit adapter tests**, NeoForge GameTests, validações de runtime/dados, build, JAR e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #114 (run `33463430893`, HEAD `4813b2fd`): **GREEN**.
- Resultado: contrato canônico A0031 validado; apta ao merge da PR #252.
