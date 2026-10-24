# Índice incremental da modlist certificada e organizada

> A autoridade física mais recente disponível continua sendo `modlist(1).txt`, com **587 entradas top-level** incluindo o modloader. Em **02/10/2026**, o usuário confirmou duas adições posteriores a esse snapshot — **Cold Sweat: Altitude** e **Create: Bionics** — elevando o inventário conhecido do perfil para **589 mods**, enquanto um novo dump físico ainda não está disponível. A tabela numerada abaixo permanece congelada nas 587 posições físicas verificadas; as duas adições pós-snapshot ficam registradas separadamente até que JAR, metadata, SHA e ordem possam ser re-fetched. Entradas sem dossier ou sem projeto correspondente permanecem explícitas como exceções, em vez de serem omitidas ou receberem dados físicos inventados.
>
> Em **03/10/2026**, o usuário determinou uma nova alteração de estado-alvo: **adicionar Create: AeroWarptics 1.3.0 e remover Create Aeronautics: Alcubierre 1.2.6**. Como não existe dump físico posterior à ordem, a troca é registrada abaixo como **solicitada/pós-snapshot**, sem falsificar presença ou ausência física. Se ambas as ações tiverem sido executadas localmente, o efeito líquido na contagem é zero; isso só será certificado no próximo inventário.
>
> Para projetos CurseForge confirmados, a pasta representa o conjunto completo das categorias temáticas atuais, ordenadas alfabeticamente e separadas por ` + `. Categorias de loader, versão do Minecraft e ambiente não entram no caminho. Quando o artefato físico não possui projeto CurseForge inequívoco, usa-se a pasta estrutural `Sem projeto CurseForge confirmado`, que **não** equivale a uma categoria temática oficial.
>
> `Última edição` registra a última alteração documental significativa anterior à movimentação estrutural, normalizada para `America/Sao_Paulo` (UTC-03:00). A movimentação em si não altera esse histórico.
>
> A posição de cada linha é obtida por correspondência exata com o JAR top-level instalado na autoridade física. Dependências embarcadas em `META-INF/jarjar/` ou `META-INF/jars/` não recebem linhas próprias.

## Adições pós-snapshot físico — 02/10/2026

> Estas entradas foram confirmadas pelo usuário como adicionadas **depois** de `modlist(1).txt`. Os filenames/versões abaixo são os artefatos upstream atuais usados como alvo documental; **não são promovidos a autoridade física** até novo dump do perfil. Por isso não recebem número físico nem `✅-`.

| Nome / projeto | Artefato upstream atual / alvo documental | Versão | Pasta / categorias | Dossier | Estado físico |
|---|---|---:|---|---|---|
| Cold Sweat: Altitude | `coldsweat_altitude-0.7.0.jar` | `0.7.0` | `Addons + Adventure and RPG + Biomes + Dimensions + Utility & QoL` | `cold-sweat-altitude v0.7.0.md` | Adição confirmada pelo usuário; JAR/SHA/metadata/ordem aguardam novo snapshot |
| Create: Bionics | `createbionics-2.5.0.jar` | `2.5.0` | `Addons + Create + Mobs + Player Transport + Utility & QoL` | `create-bionics v2.5.0.md` | Adição confirmada pelo usuário; JAR/SHA/metadata/ordem aguardam novo snapshot |

**Create Aeronautics: Automated Logistics não aumenta a contagem.** Apesar de ter sido citado junto das novas adições, ele já consta na autoridade física de 16/09/2026 como `create_aeronautics_automated_logistics-0.6.2.jar` / `0.6.2` e já ocupa uma linha da tabela física de 587 entradas.

## Alterações solicitadas pós-snapshot — 03/10/2026

> Estas mudanças representam o **estado-alvo solicitado**, não uma nova autoridade física. A tabela de 587 entradas permanece intacta como snapshot histórico até novo dump.

| Ação | Nome / projeto | Artefato / versão de referência | Pasta / dossier | Estado |
|---|---|---|---|---|
| Adicionar | Create: AeroWarptics | `aerowarptics-1.3.0.jar` / `1.3.0` | `Create + Player Transport/✅-create-aerowarptics v1.3.0.md` | Solicitação registrada; JAR/SHA/metadata/ordem e smoke test pendentes |
| Remover | Create Aeronautics: Alcubierre | `alcubierre-1.2.6.jar` / `1.2.6` | `Addons + Create/✅-alcubierre v1.2.6.md` | Remoção solicitada; ausência física pendente de novo snapshot; dossier histórico preservado |

### Boundary da substituição
AeroWarptics substitui **parcialmente** Alcubierre no domínio de warp de airships. Ele não fornece a mecânica de **Antigravity Drive** documentada no Alcubierre; portanto essa capacidade é perdida com a remoção, salvo provider alternativo explícito. Não há migração 1:1 conhecida para blocks, controller destinations, configs ou state persistente.

### Observação de estabilidade
A release pública AeroWarptics 1.3.0 é NeoForge 1.21.1 e depende de Create, Create Aeronautics e GeckoLib. O source oficial já possui trabalho 1.3.1 não publicado com correções de force-loading/save hangs, launch clearance e large/multi-sub-level ships; por isso a integração permanece pendente de smoke test em cópia de mundo antes de ser tratada como segura.

## Exceções estruturais e bloqueios — 29/09/2026

- **#001 — NeoForge:** entrada física atual `neoforge-21.1.250 (modloader)`. O loader não possui projeto CurseForge de mod correspondente; o dossier é mantido em `Sem projeto CurseForge confirmado/✅-neoforge.md`.
- **#218 — Destroy 0.4.3:** o binário 1.21.1 é o port `NHblock714/Destroy`; o projeto CurseForge localizado corresponde ao original da Petrolpark e não é tratado como equivalente ao port. O dossier é mantido em `Sem projeto CurseForge confirmado/✅-destroy v0.4.3.md`.
- **#219 — Dimensional Sable 1.0.5:** o projeto oficial é distribuído no Modrinth e projetos CurseForge dependentes apontam para essa distribuição externa. O dossier é mantido em `Sem projeto CurseForge confirmado/✅-dimensional-sable v1.0.5.md`.
- **#272 — Factory Construction Registry Probe:** a autoridade física confirma `factory_construction_registry_probe-0.1.0.jar`, mod id `factory_construction_registry_probe`, runtime `0.1.0`. Não existe dossier certificado correspondente; a entrada permanece na tabela física com dossier ausente, sem criação retroativa de documentação.
- **Create Enchantment Industry Plus:** não está na modlist física atual. O dossier histórico permanece documentado e é organizado separadamente em `Create + Technology/✅-create-enchantment-industry-plus.md`, conforme as categorias atuais do projeto correto no CurseForge.

## Modlist física completa e organização documental

| Nº | Nome / projeto | Arquivo JAR instalado | Versão instalada | Pasta / categorias | Última edição |
|---:|---|---|---|---|---|
| 001 | NeoForge | `neoforge-21.1.250 (modloader)` | `21.1.250` | `Sem projeto CurseForge confirmado` | 29/09/2026 - 02:33 |
| 002 | A Good Place | `a_good_place-1.21-1.2.5-neoforge.jar` | `1.21-1.2.5` | `Cosmetic + Magic + Utility & QoL` | 22/09/2026 - 13:39 |
| 003 | Ace's Spell Utils | `aces_spell_utils-1.2.7.2-1.21.1.jar` | `1.2.7.2-1.21.1` | `Addons + API and Library` | 22/09/2026 - 13:39 |
| 004 | Acolyte : Iron's Spells 'n Spellbooks Addon | `acolyte-1.0.3.jar` | `1.0.3` | `Adventure and RPG + Magic + Mobs + Structures` | 22/09/2026 - 13:39 |
| 005 | Additional Attributes | `additional_attributes-1.21.1-1.2.2.jar` | `1.2.2` | `Miscellaneous` | 22/09/2026 - 13:40 |
| 006 | Advanced Core Info (ACI) | `AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar` | `1.1.0` | `API and Library` | 23/10/2026 - 02:19 |
| 007 | Advanced Loot Info (ALI) | `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar` | `2.1.0` | `Addons + API and Library + Map and Information` | 22/10/2026 - 22:49 |
| 008 | Advancement Plaques [Neo/Forge] | `AdvancementPlaques-1.21.1-neoforge-1.6.8.jar` | `1.6.8` | `Cosmetic + Map and Information + Miscellaneous + Utility & QoL` | 22/09/2026 - 13:40 |
| 009 | SnackPirate's Aeromancy Additions | `aero_additions-1.2.8.jar` | `1.2.8` | `Addons + Magic` | 22/09/2026 - 13:40 |
| 010 | Aeronautics Camera Sync | `aero_cam_sync-1.4.0.jar` | `1.4.0` | `Addons + Cosmetic + Create` | 22/09/2026 - 13:40 |
| 011 | Aeronautics Player Tilt | `aero_player_tilt-0.2.0.jar` | `0.2.0` | `Addons + Cosmetic + Create` | 23/10/2026 - 00:21 |
| 012 | Create Aeronautics and Connected Fluid Vessel Compatability | `aeroconnectedfluidvessel-1.0.0.jar` | `1.0.0` | `Create + Utility & QoL` | 18/09/2026 - 15:31 |
| 013 | Copycats+ aeronautics weight | `aerocopycats-1.1.1.jar` | `1.1.1` (distribuição/filename); metadata interna `1.1.0` | `Addons + Create + Utility & QoL` | 18/09/2026 - 15:31 |
| 014 | Create Aeronautics: Compatibility | `aeronauticscompat-1.1.3.jar` | `1.1.3` | `Addons + Create + Utility & QoL` | 18/09/2026 - 15:31 |
| 015 | Create: AeroStar Northstar Comp | `AeroStar-1.0.1.jar` | `1.0.1` | `Addons + Create` | 29/09/2026 - 02:57 |
| 016 | Create: Aeroworks | `aeroworks-1.5.0.jar` | `1.5.0` | `Addons + Create + Technology + Utility & QoL` | 18/09/2026 - 15:31 |
| 017 | AI Improvements: Performance Tuning | `AI-Improvements-1.21-0.5.3.jar` | `0.5.3` | `Mobs + Server Utility` | 18/09/2026 - 15:31 |
| 018 | Create Aeronautics: Alcubierre | `alcubierre-1.2.6.jar` | `1.2.6` | `Addons + Create` | 18/09/2026 - 15:31 |
| 019 | Alex's Caves Continued | `alexscaves-1.0.10-neoforge+1.21.1.jar` | `1.0.10` | `World Gen` | 22/10/2026 - 21:57 |
| 020 | Alex's Delight | `alexsdelight-1.6.jar` | `1.6` | `Addons + Food` | 18/09/2026 - 15:31 |
| 021 | Alex's Mobs Continued | `alexsmobs-2.1.13-neoforge+1.21.1.jar` | `2.1.13` | `Mobs` | 22/10/2026 - 19:50 |
| 022 | Almost Unified | `almostunified-neoforge-1.21.1-1.4.2.jar` | `1.21.1-1.4.2` | `API and Library + KubeJS + Ores and Resources + Processing + Server Utility` | 18/09/2026 - 15:31 |
| 023 | Alshanex's Familiars | `alshanex_familiars-1.21.1_v4.0.3.jar` | `1.21.1_v4.0.3` | `Addons + Magic + Mobs` | 22/10/2026 - 22:01 |
| 024 | Alternate Current | `alternate_current-mc1.21-1.9.0.jar` | `1.9.0` | `Redstone + Server Utility + Technology + Utility & QoL` | 19/09/2026 - 12:06 |
| 025 | AmbientSounds 6 | `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar` | `6.3.8` | `Miscellaneous` | 23/10/2026 - 15:22 |
| 026 | Amendments | `amendments-1.21-2.1.10-neoforge.jar` | `1.21-2.1.10` | `Cosmetic + Utility & QoL` | 22/10/2026 - 22:00 |
| 027 | Amplified Nether | `Amplified_Nether_26.2_v1.2.16.jar` | `1.2.16` | `Biomes + Dimensions + World Gen` | 19/09/2026 - 12:06 |
| 028 | Animal Husbandry | `AnimalHusbandry-neoforge-0.4.1.jar` | `0.4.1` | `Farming + Genetics + Mobs` | 19/09/2026 - 12:06 |
| 029 | Create: Apokinetics | `apokinetics-1.0.6.jar` | `1.0.6` | `Addons + Automation + Create + Magic + Technology` | 19/09/2026 - 12:06 |
| 030 | Apotheosis | `Apotheosis-1.21.1-8.8.0.jar` | `8.8.0` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 19/09/2026 - 12:06 |
| 031 | Apotheotic Creation | `apotheoticcreation-2.0.0.jar` | `2.0.0` | `Automation + Create + Storage + Utility & QoL` | 19/09/2026 - 12:06 |
| 032 | Apothic Category Compat | `apothic_compat-2.0.2.jar` | `2.0.2` | `Addons + Armor, Tools, and Weapons + Bug Fixes` | 19/09/2026 - 12:06 |
| 033 | Apothic Compats | `apothic_compats-0.2.4.2.jar` | `0.2.4.2` | `Addons + Armor, Tools, and Weapons` | 23/10/2026 - 15:31 |
| 034 | Apothic Attributes | `ApothicAttributes-1.21.1-2.10.1.jar` | `2.10.1` | `Adventure and RPG + API and Library` | 19/09/2026 - 16:04 |
| 035 | Apothic Enchanting | `ApothicEnchanting-1.21.1-1.6.2.jar` | `1.6.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 16:04 |
| 036 | Apothic Spawners | `ApothicSpawners-1.21.1-1.4.0.jar` | `1.4.0` | `Automation + Utility & QoL` | 19/09/2026 - 16:04 |
| 037 | AppleSkin | `appleskin-neoforge-mc1.21-3.0.9.jar` | `3.0.9+mc1.21` | `Food + Map and Information` | 19/09/2026 - 16:05 |
| 038 | Apprentice's Codex | `apprentice_codex-0.9.7.1+mc1.21.1.jar` | `0.9.7.1` | `Addons + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 16:05 |
| 039 | Architectury API | `architectury-13.0.11-neoforge.jar` | `13.0.11` | `API and Library` | 19/09/2026 - 16:05 |
| 040 | Ars Additions | `ars_additions-1.21.1-21.3.0.jar` | `1.21.1-21.3.0` | `Addons + Magic + Storage + Utility & QoL` | 19/09/2026 - 16:05 |
| 041 | Ars Controle | `ars_controle-1.21.1-1.6.16.jar` | `1.21.1-1.6.16` | `Addons + Magic` | 19/09/2026 - 16:05 |
| 042 | Ars Creo | `ars_creo-1.21.1-5.4.0.jar` | `5.4.0` | `Magic + Technology` | 19/09/2026 - 16:05 |
| 043 | Ars Elemancy | `ars_elemancy-1.21.1-1.18.3.jar` | `1.18.3` | `Addons + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 16:06 |
| 044 | Ars Elemental | `ars_elemental-1.21.1-0.7.10.1.jar` | `0.7.10.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 17:09 |
| 045 | Ars Hex Unity | `ars_hex-1.21.1-5.0.4b.jar` | `5.0.4b` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 17:09 |
| 046 | Ars 'n' Spells | `ars_n_spells-3.3.4.jar` | `3.3.4` | `Addons + Adventure and RPG + Magic + Utility & QoL` | 22/10/2026 - 19:45 |
| 047 | Ars Nouveau | `ars_nouveau-1.21.1-5.13.1.jar` | `5.13.1` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Magic + Mobs` | 19/09/2026 - 17:09 |
| 048 | Ars Polymorphia | `ars_polymorphia-1.0.3.jar` | `1.0.3` | `Addons + Magic + Storage + Utility & QoL` | 19/09/2026 - 17:10 |
| 049 | Ars Sable | `ars_sable-1.21.1-1.1.2.jar` | `1.1.2` | `Addons + API and Library + Magic + Utility & QoL` | 19/09/2026 - 17:11 |
| 050 | Ars Technica | `ars_technica-1.21.1-2.7.6.jar` | `2.7.6` | `Armor, Tools, and Weapons + Magic + Technology + Utility & QoL` | 23/09/2026 - 00:58 |
| 051 | Ars Nouveau: Two-Way Portals (with immersive portal support) | `ars_two_way_portals-2.0.0.jar` | `2.0.0` | `Addons` | 19/09/2026 - 17:10 |
| 052 | Ars Zero | `ars_zero-1.21.1-2.0.2.jar` | `2.0.2` | `Addons + Cosmetic + Magic + Technology` | 19/09/2026 - 17:10 |
| 053 | Ars Nouveau's Flavors & Delight | `arsdelight-2.2.2.jar` | `2.2.2` | `Addons + Farming + Food + Magic` | 19/09/2026 - 17:10 |
| 054 | Sophisticated Backpacks: Ars Compat \| Sophisticated Backpacks Ars-Nouveau Compatibility | `arssophisticatedcompat-0.3.0.jar` | `0.3.0` | `Magic + Storage + Utility & QoL` | 19/09/2026 - 18:06 |
| 055 | Artifacts | `artifacts-neoforge-13.2.5.jar` | `13.2.5` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Structures` | 19/09/2026 - 18:06 |
| 056 | Asterism Arcanum \| Addon for Iron's Spells and Spellbooks | `asterismarcanum-1.21.1-0.1.0.jar` | `1.21.1-0.1.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 18:06 |
| 057 | Athena | `athena-neoforge-1.21.1-4.0.6.jar` | `4.0.6` | `API and Library + Utility & QoL` | 19/09/2026 - 18:06 |
| 058 | Atlas API | `atlas_api-1.21.1-1.2.0.jar` | `1.21.1-1.2.0` | `API and Library` | 19/09/2026 - 18:06 |
| 059 | AttributeFix | `attributefix-neoforge-1.21.1-21.1.3.jar` | `21.1.3` | `Bug Fixes + Utility & QoL` | 19/09/2026 - 18:06 |
| 060 | Azimuth API | `azimuth-1.4.8.jar` | `1.4.8` | `Addons + API and Library + Create` | 18/09/2026 - 10:38 |
| 061 | AzureLib | `azurelib-neo-1.21.1-3.1.11.jar` | `3.1.11` | `API and Library` | 22/10/2026 - 22:26 |
| 062 | Create: Backpack Pixel | `backpack_pixel-1.2.0-neoforge-1.21.1.jar` | `1.2.0` | `Addons + Armor, Tools, and Weapons + Create + Storage + Technology` | 19/09/2026 - 18:06 |
| 063 | Backported Spells: Iron's Spells x Vanilla Backport | `backportedspellbooks-0.1.2.jar` | `0.1.2 (distribuição/filename); metadata runtime 0.1.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 18:23 |
| 064 | Balm | `balm-neoforge-1.21.1-21.0.65.jar` | `21.0.65` | `API and Library` | 19/09/2026 - 21:35 |
| 065 | Battle Arts - API | `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar` | `21.17.7` | `Addons + API and Library` | 19/09/2026 - 21:35 |
| 066 | BCLib: New Dawn | `bclib-21.0.26.jar` | `21.0.26` | `API and Library + Biomes + Utility & QoL + World Gen` | 22/10/2026 - 19:46 |
| 067 | Create: Bells & Whistles | `bellsandwhistles-0.4.7-1.21.1.jar` | `0.4.7-1.21.1` | `Cosmetic + Create + Player Transport + Technology + Utility & QoL` | 19/09/2026 - 21:35 |
| 068 | Better Advanced Tooltips | `better-advanced-tooltips-2101.1.0-build.5.jar` | `2101.1.0-build.5` | `API and Library + Map and Information + Utility & QoL` | 19/09/2026 - 21:35 |
| 069 | Better Library | `better_lib-neoforge-1.21.1-1.0.111.jar` | `1.0.111` | `API and Library + Server Utility + Utility & QoL` | 19/09/2026 - 21:35 |
| 070 | Better Modlist | `better_modlist-21.1.1.jar` | `21.1.1` | `Cosmetic + Utility & QoL` | 23/10/2026 - 02:01 |
| 071 | BetterEnd: New Dawn | `BetterEnd-21.0.34.jar` | `21.0.34` | `Biomes + Cosmetic + Mobs + Structures + World Gen` | 23/10/2026 - 02:21 |
| 072 | Better Fps - Render Distance | `betterfpsdist-1.21.1-6.1.jar` | `6.1` | `Cosmetic + Miscellaneous` | 19/09/2026 - 21:46 |
| 073 | Epic Fight - Better lock on | `betterlockon-2.0.8-neoforge.jar` | `2.0.8-neoforge` | `Addons + Adventure and RPG` | 19/09/2026 - 21:35 |
| 074 | BetterNether: New Dawn | `BetterNether-21.0.26.jar` | `21.0.26` | `Biomes + Cosmetic + Mobs + Structures + World Gen` | 23/10/2026 - 01:20 |
| 075 | Create: Big Contraptions | `bigcontraptions-neoforge-1.0.jar` | `1.0` | `Create + Utility & QoL` | 19/09/2026 - 23:37 |
| 076 | Biolith | `biolith-neoforge-3.0.14.jar` | `3.0.14` | `Biomes + World Gen` | 19/09/2026 - 23:37 |
| 077 | Create: Bits 'n' Bobs | `bits_n_bobs-2.3.5.jar` | `2.3.5` | `Addons + Cosmetic + Create + Utility & QoL` | 18/09/2026 - 10:11 |
| 078 | BjornLib | `bjornlib-neoforge-1.0.88-1.21.1.jar` | `1.0.88` | `API and Library` | 19/09/2026 - 23:37 |
| 079 | Bosses'Rise - Epic Souls like boss fights | `block_factorys_bosses-2.1.2-neo-1.21.1.jar` | `2.1.2` | `Adventure and RPG + Armor, Tools, and Weapons + Mobs + Structures + World Gen` | 19/09/2026 - 23:37 |
| 080 | BlockUI | `blockui-1.0.212-1.21.1-snapshot.jar` | `1.0.212-1.21.1-snapshot` | `API and Library` | 18/09/2026 - 08:51 |
| 081 | Bloodlines - A Vampirism Addon | `bloodlines-1.21-3.0.9.jar` | `1.21-3.0.9` | `Magic` | 19/09/2026 - 23:37 |
| 082 | Bosses of Mass Destruction [Forge \| NeoForge] | `BOMD-NeoForge-1.21-1.3.3.jar` | `1.3.3` | `Mobs + Structures` | 19/09/2026 - 23:37 |
| 083 | Bookshelf | `bookshelf-neoforge-1.21.1-21.1.81.jar` | `21.1.81` | `API and Library + Miscellaneous + Server Utility` | 19/09/2026 - 23:37 |
| 084 | Born in Chaos | `born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar` | `1.7.6` | `Adventure and RPG + Armor, Tools, and Weapons + Mobs + Structures + World Gen` | 29/09/2026 - 23:37 |
| 085 | Butchercraft | `butchercraft-2.6.5.jar` | `2.6.5` | `Food` | 23/09/2026 - 00:58 |
| 086 | Caelus API | `caelus-neoforge-7.0.1+1.21.1.jar` | `7.0.1+1.21.1` | `API and Library` | 23/09/2026 - 00:58 |
| 087 | Cataclysm: Spellbooks | `cataclysm_spellbooks-1.1.14-1.21.jar` | `1.1.14-1.21` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 23/09/2026 - 00:58 |
| 088 | Cataclysm x YUNG's Better Nether Fortresses Compat | `cataclysmfortresses-1.21.1-NeoForge.jar` | `1.21.1` | `Addons + Bug Fixes` | 23/09/2026 - 00:58 |
| 089 | Create Big Cannons: Advanced Technologies | `cbc_at_Neoforge_1.21.1_0.1.4c.jar` | `0.1.4c-1.21.1` | `Armor, Tools, and Weapons + Create` | 23/09/2026 - 00:58 |
| 090 | CBC: Advanced technologies crash fix | `cbcatfix-1.21.1-neoforge-1.0.1.jar` | `1.0.1 (distribuição/filename); metadata runtime 1.0.0` | `Addons + Create + Technology` | 23/10/2026 - 00:27 |
| 091 | CERBON's API [Forge \| Fabric \| NeoForge] | `CerbonsAPI-NeoForge-1.21-1.3.0.jar` | `1.3.0` | `API and Library` | 23/09/2026 - 00:58 |
| 092 | Chipped | `chipped-neoforge-1.21.1-4.0.2.jar` | `4.0.2` | `Cosmetic` | 23/09/2026 - 01:02 |
| 093 | Chunky (Forge/NeoForge) | `Chunky-NeoForge-1.4.23.jar` | `1.4.23` | `Dimensions + Server Utility + World Gen` | 23/09/2026 - 01:02 |
| 094 | Citadel | `citadel-2.7.1-1.21.1.jar` | `2.7.1` | `API and Library + Miscellaneous` | 23/09/2026 - 01:13 |
| 095 | Climbable Ropes for Create Aeronautics | `climbable_ropes-2.1.3.jar` | `2.1.3` | `Addons + Create` | 23/10/2026 - 01:17 |
| 096 | Clockwork | `clockwork-neoforge-1.21.1-1.1.4.jar` | `1.1.4` | `Cosmetic + Mobs + Technology` | 23/09/2026 - 01:02 |
| 097 | Cloth Config API (Fabric/Forge/NeoForge) | `cloth-config-15.0.140-neoforge.jar` | `15.0.140` | `API and Library` | 23/09/2026 - 01:02 |
| 098 | Clumps | `Clumps-neoforge-1.21.1-19.0.0.1.jar` | `19.0.0.1` | `Server Utility + Storage + Utility & QoL` | 23/09/2026 - 01:02 |
| 099 | Cobweb | `cobweb-neoforge-1.21-1.4.0.jar` | `1.4.0` | `API and Library` | 23/09/2026 - 01:02 |
| 100 | CodeChicken Lib 1.8.+ | `CodeChickenLib-1.21.1-4.6.1.529.jar` | `4.6.1.529` | `API and Library` | 23/09/2026 - 01:02 |
| 101 | Codxlib | `codxlib-1.6.0-neoforge+1.21.1.jar` | `1.6.0` | `Utility & QoL` | 23/09/2026 - 01:02 |
| 102 | Cold Sweat | `ColdSweat-2.4.3.1.jar` | `2.4.3.1` | `Adventure and RPG + Armor, Tools, and Weapons + Utility & QoL` | 23/10/2026 - 13:51 |
| 103 | Collective | `collective-1.21.1-8.39.jar` | `8.39` | `API and Library` | 23/10/2026 - 00:40 |
| 104 | Companions! | `companions-neoforge-1.21.1-1.3.4.jar` | `1.3.4` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs + Structures` | 23/09/2026 - 01:13 |
| 105 | Sinytra Connector | `connector-2.0.0-beta.17+1.21.1-full.jar` | `2.0.0-beta.17+1.21.1` | `Technology` | 22/09/2026 - 22:34 |
| 106 | Connector Extras | `ConnectorExtras-1.12.1+1.21.1.jar` | `1.12.1+1.21.1` | `Addons + API and Library + Technology + Utility & QoL` | 22/09/2026 - 22:34 |
| 107 | Continuity | `continuity-3.0.0+1.21.neoforge.jar` | `3.0.0+1.21.neoforge` | `API and Library + Cosmetic` | 22/09/2026 - 22:34 |
| 108 | Controlling | `Controlling-neoforge-1.21.1-19.0.5.jar` | `19.0.5` | `Cosmetic + Utility & QoL` | 22/09/2026 - 22:34 |
| 109 | Create: Copycats+ | `copycats-3.0.9+mc.1.21.1-neoforge.jar` | `3.0.9+mc.1.21.1-neoforge` | `Cosmetic + Create + Utility & QoL` | 22/09/2026 - 22:35 |
| 110 | CorgiLib | `Corgilib-NeoForge-1.21.1-5.0.0.9.jar` | `5.0.0.9` | `API and Library` | 22/09/2026 - 22:35 |
| 111 | Cosmetic Armor Reworked | `cosmeticarmorreworked-1.21.1-v1-neoforge.jar` | `1.21.1-v1-neoforge` | `Cosmetic` | 22/09/2026 - 22:35 |
| 112 | Customizable Player Models OSC Compat | `cpm-osc-compat-1.7.2.jar` | `1.7.2` | `Addons + API and Library + Cosmetic` | 22/09/2026 - 23:33 |
| 113 | CraftedCore | `craftedcore-5.8.2.jar` | `5.8.2` | `API and Library` | 22/09/2026 - 23:33 |
| 114 | Crash Assistant | `CrashAssistant-neoforge-1.20.6-1.21.4-1.11.12.jar` | `1.11.12` | `Bug Fixes + Miscellaneous + Performance + Server Utility + Utility & QoL` | 22/09/2026 - 23:33 |
| 115 | Create: Quality Of Life | `Create Quality of Life-1.21.1-1.6.3-fix1.jar` | `1.6.3-fix1` | `Create + Utility & QoL` | 22/09/2026 - 23:33 |
| 116 | Create | `create-1.21.1-6.0.10.jar` | `6.0.10` | `Armor, Tools, and Weapons + Cosmetic + Redstone + Technology` | 22/09/2026 - 23:33 |
| 117 | Create Aeronautics | `create-aeronautics-bundled-1.21.1-1.3.2.jar` | `1.3.2` | `Adventure and RPG + Automation + Player Transport + Redstone + Technology` | 22/09/2026 - 23:34 |
| 118 | Create: Central Kitchen | `create-central-kitchen-2.6.0.jar` | `2.6.0` | `Addons + Create + Food + Technology` | 22/09/2026 - 23:34 |
| 119 | Create Confectionery | `create-confectionery1.21.1_v1.1.3b.jar` | `1.1.3b (distribuição/filename); metadata runtime 1.1.3.` | `Addons + Cosmetic + Create + Food + MCreator` | 22/09/2026 - 23:34 |
| 120 | Create: Enchantment Industry | `create-enchantment-industry-2.5.3b.jar` | `2.5.3b` | `Addons + Create + Storage + Technology` | 22/09/2026 - 23:34 |
| 121 | Create Guardian Beam Defense | `Create-Guardian-Beam-Defense-1.3.7.1b-1.21.1-neoforge.jar` | `1.3.7.1b` | `Addons + Armor, Tools, and Weapons + Create + Technology` | 22/09/2026 - 23:34 |
| 122 | Create: Gunsmithing | `create-gunsmithing-1.21.1-1.4.9.jar` | `1.4.9` | `Addons + Create` | 23/09/2026 - 00:34 |
| 123 | Create: Integrated Farming | `create-integrated-farming-1.4.1c.jar` | `1.4.1c` | `Addons + Automation + Create + Farming + Technology` | 23/09/2026 - 08:03 |
| 124 | Create: New Age | `create-new-age-1.2.0+neoforge-mc1.21.1.jar` | `1.2.0+mc1.21.1` | `Addons + Create + Technology` | 23/09/2026 - 08:03 |
| 125 | Create Stuff 'N Additions | `create-stuff-additions1.21.1_v2.1.4b.jar` | `2.1.4b (distribuição); metadata interna 2.1.4.` | `Addons + Armor, Tools, and Weapons + Create + MCreator + Technology` | 23/09/2026 - 08:03 |
| 126 | Create Utilities J | `Create-Utilities-J-1.21.1-0.3.4+1.21.1.jar` | `0.3.4+1.21.1` | `Automation + Create` | 23/09/2026 - 08:03 |
| 127 | Create Aero Radars | `create_aero_radar-0.1.1-1.21.1.jar` | `0.1.1-1.21.1` | `Create + Technology` | 23/09/2026 - 08:03 |
| 128 | Create Aeronautics: Automated Logistics | `create_aeronautics_automated_logistics-0.6.2.jar` | `0.6.2` | `Addons + Automation + Create` | 23/09/2026 - 08:03 |
| 129 | Create Aeronautics: FTB Chunks | `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar` | `1.2.0` | `Create` | 23/09/2026 - 08:03 |
| 130 | Create Aeronautics: Throwable Rope Connector | `create_aeronautics_throwable_rope_connector-0.4.3.jar` | `0.4.3` | `Addons + Create + Utility & QoL` | 23/09/2026 - 08:03 |
| 131 | create aeronautics：toolgun | `create_aeronautics_toolgun-0.3.6.jar` | `0.3.6` | `Utility & QoL` | 23/09/2026 - 08:03 |
| 132 | Create Aeronautics: Transmission & Linkage | `create_aeronautics_transmission_linkage-0.2.8.jar` | `0.2.8` | `Create + Technology` | 23/09/2026 - 08:34 |
| 133 | Create: Aquatic Ambitions | `create_aquatic_ambitions-1.21.1-2.0.4.jar` | `2.0.4` | `Armor, Tools, and Weapons + Automation + Create + Ores and Resources` | 23/09/2026 - 08:34 |
| 134 | Create: Blocks & Bogies | `create_bb-1.0.8-1.21.1.jar` | `1.0.8` | `Addons + Create + Player Transport + Technology` | 23/09/2026 - 08:34 |
| 135 | Create: Blaze Burner Fuels | `create_blaze_burner_fuels-1.0.2-neoforge-1.21.1.jar` | `1.0.2` | `Automation + Create + Energy + Ores and Resources + Processing` | 23/09/2026 - 08:34 |
| 136 | Create: Cold Sweat | `create_cold_sweat-1.1.2.jar` | `1.1.2` | `Addons + Create + Utility & QoL` | 23/09/2026 - 08:34 |
| 137 | Create: Colony Logistics | `create_colony_logistics-1.3.3.jar` | `1.3.3` | `Addons + Create` | 23/09/2026 - 08:34 |
| 138 | Create: Connected | `create_connected-1.3.3-mc1.21.1.jar` | `1.3.3-mc1.21.1` | `Addons + Automation + Create + Utility & QoL` | 23/09/2026 - 08:34 |
| 139 | Create: Arm-made Cuisine | `create_cuisine-1.0.0-mc1.21.1-neoforge.jar` | `1.0.0` | `Automation + Create + Food` | 23/09/2026 - 08:35 |
| 140 | Create: Curios Backtank | `create_curios_backtank-neoforge-1.21.1-1.0.1.jar` | `1.0.1` | `Addons + Armor, Tools, and Weapons + Automation + Create` | 23/10/2026 - 02:18 |
| 141 | Create: Deep Dark | `create_deep_dark-3.0.2-neoforge-1.21.1.jar` | `3.0.2` | `Adventure and RPG + Armor, Tools, and Weapons + Biomes + Create + Ores and Resources` | 23/09/2026 - 08:35 |
| 142 | Create: Factory | `create_factory-0.7b-1.21.1.jar` | `0.7b-1.21.1` | `Automation + Create + Food` | 23/09/2026 - 10:19 |
| 143 | Create: Fantasizing Again | `create_fantasizing-1.21.1-1.2.0-b3.jar` | `1.2.0-b3` | `Addons + Create + Utility & QoL` | 23/09/2026 - 10:19 |
| 144 | Create : New Furnitures | `create_furnitures-1.1.2-neoforge-1.21.1.jar` | `1.1.2` | `Addons + Cosmetic + Create + Structures + Utility & QoL` | 23/09/2026 - 10:19 |
| 145 | Create Jetpack | `create_jetpack-forge-5.2.1.jar` | `5.2.1` | `Addons + Armor, Tools, and Weapons + Technology` | 23/09/2026 - 10:19 |
| 146 | Create: Curios Jetpack & Backtank | `create_jetpack_curios-1.2.0-neoforge-1.21.1.jar` | `1.2.0` | `Armor, Tools, and Weapons + Create + Player Transport + Technology + Utility & QoL` | 23/09/2026 - 10:19 |
| 147 | Create Mechanical Spawner | `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar` | `1.3.2-6.0.10 (distribuição); metadata interna 1.3.1-6.0.10` | `Automation + Create + KubeJS + Mobs + Skyblock` | 23/09/2026 - 10:19 |
| 148 | Create: More Features | `create_mf-0.1.3-neoforge-1.21.1.jar` | `0.1.3` | `Addons + Automation + Create + Ores and Resources` | 23/09/2026 - 10:19 |
| 149 | Create: Mobile Packages | `create_mobile_packages-1.21.1-0.7.7.jar` | `0.7.7` | `Automation + Create + Storage` | 23/09/2026 - 10:20 |
| 150 | Create More Automation | `create_more_automation-0.5.2-neoforge-1.21.1.jar` | `0.5.2` | `Automation + Create + Technology` | 23/09/2026 - 10:20 |
| 151 | Create Optical | `create_optical-0.4.2.jar` | `0.4.2` | `Addons + Create + Technology` | 23/09/2026 - 10:20 |
| 152 | Create: Pattern Schematics | `create_pattern_schematics-2.0.10.jar` | `2.0.10` | `Automation + Create + Structures + Utility & QoL` | 23/09/2026 - 11:22 |
| 153 | Create: Pillager Arise | `create_pillagers_arise NeoForge 1.21.1-132.36.jar` | `132.36` | `Create + Mobs + Structures + World Gen` | 23/09/2026 - 11:22 |
| 154 | Create: Radars | `create_radar-0.4.9.4-1.21.1.jar` | `0.4.9.4-1.21.1` | `Addons + Armor, Tools, and Weapons + Create + Map and Information + Technology` | 23/09/2026 - 11:22 |
| 155 | Create: Ratatouille | `create_ratatouille-1.21.1-1.4.0.jar` | `1.4.0` | `Addons + Automation + Create + Farming + Food` | 23/09/2026 - 11:23 |
| 156 | Create Recycle Everything (Continued) | `create_recycle_everything-2.1.0.jar` | `2.1.0 (distribuição); metadata interna 1.1` | `Addons + Create + Ores and Resources + Processing + Utility & QoL` | 23/09/2026 - 11:23 |
| 157 | Create: Rock & Stone | `create_rns-1.3.1-1.21.1-6.jar` | `1.3.1-1.21.1-6` | `Addons + Automation + Create + Ores and Resources + Processing` | 23/09/2026 - 11:23 |
| 158 | Create Stuff & Additions Curios Jetpacks | `create_sa_curios_jetpacks-neoforge-1.21.1-1.2.4.jar` | `1.2.4 (distribuição); metadata interna 1.2.22` | `Addons + Create` | 23/09/2026 - 11:23 |
| 159 | Create Stuff & Netherite Additions | `create_sna-1.2-neoforge-1.21.1.jar` | `1.2` | `Addons + Armor, Tools, and Weapons + Create + MCreator + Player Transport` | 23/09/2026 - 11:23 |
| 160 | Create: Sophisticated Backpacks Compat | `create_sophback_compat-1.0.jar` | `1.0 (distribuição); metadata interna vazia` | `Addons + Create + Processing` | 23/09/2026 - 11:23 |
| 161 | Create: Stats & Power | `create_stats-1.13.1.jar` | `1.13.1` | `Addons + Create` | 23/10/2026 - 00:27 |
| 162 | Create Deep Seas | `create_submarine-2.2.4.jar` | `2.2.4` | `Addons + Create` | 23/10/2026 - 13:52 |
| 163 | Create : Misc & Things | `create_things_and_misc-4.1.1-neoforge-1.21.1.jar` | `4.1.1` | `Addons + Armor, Tools, and Weapons + Create + MCreator + Technology` | 23/09/2026 - 12:37 |
| 164 | Create: Tweaked Controllers | `create_tweaked_controllers-1.21.1-1.2.7.jar` | `1.21.1-1.2.7` | `Addons + Create + Redstone + Technology` | 23/09/2026 - 12:37 |
| 165 | Create: Ultimate Factory | `create_ultimate_factory-2.2.4-neoforge-1.21.1.jar` | `2.2.4` | `Create + Farming + Processing + Server Utility + Technology` | 23/09/2026 - 12:38 |
| 166 | Create: Wizardry | `create_wizardry-1.21.1-0.5.1-pre1.jar` | `1.21.1-0.5.1-pre1` | `Addons + Create + Magic + Technology` | 23/09/2026 - 12:38 |
| 167 | Create Crafts & Additions | `createaddition-1.7.1.jar` | `1.7.1` | `Addons + Energy + Energy, Fluid, and Item Transport` | 23/09/2026 - 12:38 |
| 168 | Create: Additional Logistics | `createadditionallogistics-1.21.1-1.4.5.jar` | `1.4.5` | `Create` | 23/09/2026 - 12:38 |
| 169 | Create: Addon Compatibility | `createaddoncompatibility-neoforge-1.21.1-1.0.0.jar` | `1.0.0` | `Addons + Bug Fixes + Create + Utility & QoL` | 23/09/2026 - 12:38 |
| 170 | Create Aeronautics: Copycat Wing | `CreateAeronauticsCopycatWing-1.21.1-1.0.4.jar` | `1.0.4` | `Addons + API and Library + Create` | 23/09/2026 - 12:38 |
| 171 | Create Aeronautics x Curios API Compat | `createaeronauticscurios-neoforge-1.21.1-2.2.jar` | `2.2 (distribuição); metadata interna 2.0` | `Addons + Create` | 23/09/2026 - 12:38 |
| 172 | Create Aeronautics: Harness | `CreateAeronauticsHarness-1.21.1-1.0.1.jar` | `1.0.1` | `Addons + Create + Player Transport + Technology` | 23/09/2026 - 14:26 |
| 173 | Create Better FPS | `createbetterfps-1.21.1-1.1.4.jar` | `1.1.4` | `Addons + Create + Performance + Utility & QoL` | 23/09/2026 - 14:26 |
| 174 | Create Big Cannons | `createbigcannons-5.11.7+mc.1.21.1.jar` | `5.11.7` | `Addons + Armor, Tools, and Weapons + Create + Technology` | 23/09/2026 - 14:26 |
| 175 | Create: Chromatic Return | `createchromaticreturn-1.0.4-neoforge-1.21.1.jar` | `1.0.4 (distribuição/filename); metadata interna 1.0.0` | `Addons + Armor, Tools, and Weapons + Create + Energy, Fluid, and Item Transport + MCreator` | 23/09/2026 - 14:26 |
| 176 | Create Cobblestone | `createcobblestone-1.5.0+neoforge-1.21.1-153.jar` | `1.5.0+neoforge-1.21.1-153` | `Addons + Automation + Create + Performance + Technology` | 23/09/2026 - 14:26 |
| 177 | CreateColonies | `createcolonies-2.0.6.jar` | `2.0.6` | `Addons + Adventure and RPG + Automation + Create` | 23/09/2026 - 14:27 |
| 178 | Create Contraption Terminals | `createcontraptionterminals-1.21-1.4.0.jar` | `1.4.0` | `Create + Storage + Utility & QoL` | 23/09/2026 - 14:27 |
| 179 | Create: Cyber Goggles | `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar` | `8.6.3` | `Create + Utility & QoL` | 23/09/2026 - 14:27 |
| 180 | Create Deco | `createdeco-2.1.3.jar` | `2.1.3` | `Addons + Cosmetic + Create` | 23/09/2026 - 14:27 |
| 181 | Create Diesel Generators | `createdieselgenerators-1.21.1-1.3.15.jar` | `1.21.1-1.3.15` | `Addons + Create + Processing + Technology` | 23/09/2026 - 14:27 |
| 182 | Create: Dragons Plus | `CreateDragonsPlus-1.11.8b.jar` | `1.11.8b` | `API and Library + Create + Energy, Fluid, and Item Transport + Processing + Technology` | 23/09/2026 - 23:50 |
| 183 | Create: Enchantable Machinery | `createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar` | `3.6.0` | `Addons + Create + Technology + Utility & QoL` | 23/09/2026 - 23:51 |
| 184 | Create: Ender Transmission | `createendertransmission-2.1.1-1.21.1.jar` | `2.1.1-1.21.1` | `Addons + Technology` | 23/09/2026 - 23:51 |
| 185 | Create: Fast SchematicCannon | `CreateFastSchematicCannon-2.6.1-neoforge-1.21.1.jar` | `2.6.1-neoforge` | `Addons + Bug Fixes + Create` | 23/09/2026 - 23:51 |
| 186 | Create: Filters Anywhere | `createfiltersanywhere-1.21.1-2.6.0.jar` | `2.6.0` | `Addons + Create` | 23/09/2026 - 23:51 |
| 187 | Create: Fishery Industry | `createfisheryindustry-5.1.2.jar` | `5.1.2` | `Addons + Create` | 23/09/2026 - 23:51 |
| 188 | Create: Food | `createfood-neoforge-1.21.1-2.7.1.jar` | `2.7.1` | `Automation + Create + Food` | 23/09/2026 - 23:51 |
| 189 | Create Goggles (Create Plus) | `creategoggles-1.21.1-6.1.1-[NEOFORGE].jar` | `6.1.1` | `Addons + Armor, Tools, and Weapons + Create` | 23/09/2026 - 23:51 |
| 190 | Create JEI Compat | `createjeicompat-1.0.3.jar` | `1.0.3` | `Create` | 23/09/2026 - 23:51 |
| 191 | Create:LazyTick | `CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar` | `2.6.25-6.0.10` | `Addons + Bug Fixes + Create` | 22/10/2026 - 22:55 |
| 192 | Create: Liquid Fuel | `createliquidfuel-3.0.0-1.21.1.jar` | `3.0.0-1.21.1` | `Addons + API and Library` | 24/09/2026 - 00:20 |
| 193 | Create: Mechanical Companion | `createmechanicalcompanion-1.9-neoforge-1.21.1.jar` | `1.9 (artefato/publicação); metadata runtime vazia` | `Addons + Adventure and RPG + Create + Mobs + Technology` | 24/09/2026 - 00:20 |
| 194 | Create: Metallurgy | `createmetallurgy-1.0.3-1.21.1.jar` | `1.0.3` | `Addons + Automation + Create + Processing + Technology` | 24/09/2026 - 00:20 |
| 195 | Create: Metalwork | `createmetalwork-2.0.0.jar` | `2.0.0` | `Automation + Create + Energy, Fluid, and Item Transport + Processing` | 24/09/2026 - 00:20 |
| 196 | Create Missiles | `createmissiles-1.0.3+neoforge-1.21.1.jar` | `1.0.3` | `Addons + Create + Map and Information + Structures + Technology` | 24/09/2026 - 00:20 |
| 197 | Create Nuclear | `createnuclear-1.3.2-beta.3-neoforge.jar` | `1.3.2-beta.3` | `Addons + Create + Energy + Ores and Resources + Technology` | 24/09/2026 - 00:20 |
| 198 | Create: Ornithopter Glider | `createornithopterglider-1.2.0-1.21.1.jar` | `1.2.0-1.21.1 (runtime/filename); publicação 1.2.0+1.21.1` | `Addons + Armor, Tools, and Weapons + Create + Player Transport + Utility & QoL` | 23/10/2026 - 15:15 |
| 199 | Create: Prismatic Shine | `createprism-1.2.2.jar` | `1.2.2` | `Addons + Create` | 24/09/2026 - 00:20 |
| 200 | Create Propulsion: Simulated | `createpropulsion-1.1.5.jar` | `1.1.5` | `Addons + Create + Player Transport + Technology` | 24/09/2026 - 00:20 |
| 201 | Create : radiologistics | `CreateRadiologistics-1.1.1.jar` | `1.1.1` | `Create` | 24/09/2026 - 00:20 |
| 202 | Create: Schematic Checker | `createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar` | `2.27.45-6.0` | `Addons + Bug Fixes + Create` | 24/09/2026 - 00:44 |
| 203 | Create : Teleporters | `createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar` | `2.0.2b (filename/publicação); metadata runtime 2.0.2` | `Create + Energy, Fluid, and Item Transport + Technology` | 22/10/2026 - 19:52 |
| 204 | Create: Transmission! | `createtransmission-1.2.2+neoforge-create6-1.21.1.jar` | `1.2.2+neoforge-create6-1.21.1` | `Addons + Cosmetic + Create + Technology + Utility & QoL` | 24/09/2026 - 00:44 |
| 205 | Create Ultimine | `createultimine-1.21.1-neoforge-1.3.2.jar` | `1.3.2` | `Addons + Create + Utility & QoL` | 24/09/2026 - 00:44 |
| 206 | Create : Creating Space | `creatingspace-1.21.1-1.7.22.jar` | `1.7.22` | `Create + Dimensions + Player Transport + Technology + World Gen` | 24/10/2026 - 01:34 |
| 207 | CreativeCore | `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar` | `2.13.46` | `API and Library` | 24/09/2026 - 00:44 |
| 208 | Crystal Chronicles - Iron's Spells 'n Spellbooks | `crystal_chronicles-0.1.3-alpha.jar` | `0.1.3-alpha` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Dimensions + Magic` | 24/09/2026 - 00:44 |
| 209 | Cuisine Delight | `cuisinedelight-1.2.10.jar` | `1.2.10` | `Food + Utility & QoL` | 24/09/2026 - 00:44 |
| 210 | Cupboard | `cupboard-1.21.1-4.1.jar` | `4.1` | `Utility & QoL` | 24/09/2026 - 00:44 |
| 211 | Curios API | `curios-neoforge-9.5.1+1.21.1.jar` | `9.5.1+1.21.1` | `Adventure and RPG + API and Library + Armor, Tools, and Weapons` | 24/09/2026 - 00:44 |
| 212 | Customizable Player Models | `CustomPlayerModels-1.21-0.6.27a.jar` | `0.6.27a` | `Cosmetic` | 24/09/2026 - 01:22 |
| 213 | Cyclops Core | `cyclopscore-1.21.1-neoforge-1.30.0.jar` | `1.30.0` | `API and Library + Server Utility` | 23/10/2026 - 00:42 |
| 214 | Deeper and Darker: Spellbooks | `darkermagic-1.3.3-1.21.1-ver.b.jar` | `1.3.3-1.21.1` | `Addons + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 01:22 |
| 215 | Deeper and Darker | `deeperdarker-neoforge-1.21.1-1.4.1.jar` | `1.4.1` | `Biomes + Dimensions + Mobs + Ores and Resources + Structures` | 24/09/2026 - 01:22 |
| 216 | VanillaBackport X Farmer's Delight Compat | `Delightful-Backport-1.0-1.21.1-neoforge.jar` | `1.0` | `Addons + Food` | 24/09/2026 - 01:22 |
| 217 | Create: Design n' Decor | `Design-n-Decor-1.21.1-2.2b.jar` | `2.2b` | `Addons + Cosmetic + Create + Storage + Utility & QoL` | 24/09/2026 - 01:22 |
| 218 | Destroy | `destroy-1.21.1-0.4.3.jar` | `0.4.3` | `Sem projeto CurseForge confirmado` | 20/09/2026 - 22:25 |
| 219 | Dimensional Sable | `dimensional_sable-1.0.5.jar` | `1.0.5` | `Sem projeto CurseForge confirmado` | 20/09/2026 - 22:25 |
| 220 | Discerning The Eldritch | `discerning_the_eldritch-1.4.4-1.21.jar` | `1.4.4-1.21` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 01:22 |
| 221 | Dis-Enchanting Table | `disenchanting_table-merged-1.21.1-5.0.2.jar` | `5.0.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Ores and Resources + Technology` | 24/09/2026 - 01:22 |
| 222 | Distant Horizons: A Level of Detail mod | `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar` | `3.2.0-b` | `Utility & QoL` | 24/09/2026 - 10:44 |
| 223 | Create: Dreams n' Desires | `DnDesires-1.21.1-2.3a-BETA.jar` | `2.3a-BETA` | `Armor, Tools, and Weapons + Automation + Create + Utility & QoL + World Gen` | 24/09/2026 - 10:44 |
| 224 | Domum Ornamentum | `domum-ornamentum-1.0.236-snapshot-main.jar` | `1.0.236-snapshot` | `API and Library + Cosmetic` | 24/09/2026 - 10:44 |
| 225 | Dreamless Spells and Spellbooks | `dreamless_spells-1.1.9.jar` | `1.1.9` | `Addons + Armor, Tools, and Weapons + Magic + Ores and Resources` | 24/09/2026 - 10:44 |
| 226 | Dynamic Trees - BetterEnd | `dtbetterend-1.21.1-2.2.0.jar` | `2.2.0` | `Addons + World Gen` | 24/09/2026 - 10:44 |
| 227 | Dynamic Trees - BetterNether | `dtbetternether-1.21.1-2.2.0.jar` | `2.2.0` | `Addons + World Gen` | 24/09/2026 - 10:44 |
| 228 | Dynamic Trees - Oh The Biomes We've Gone | `dtbwg-1.1.0-BETA02.jar` | `1.1.0-BETA02` | `Addons + Cosmetic + World Gen` | 24/09/2026 - 10:44 |
| 229 | Dynamic Trees - Quark | `dtquark-2.6.1.jar` | `2.6.1` | `Addons + Cosmetic + World Gen` | 24/09/2026 - 10:44 |
| 230 | Dynamic Trees - Terralith | `dtterralith-1.3.0.jar` | `1.3.0` | `Addons + Biomes + World Gen` | 24/09/2026 - 10:44 |
| 231 | Dynamic Trees - VanillaBackport | `dtvanillabackport-1.21.1-1.7.0.jar` | `1.7.0` | `Addons + World Gen` | 22/10/2026 - 23:21 |
| 232 | Dynamic RPG Resource Bars | `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar` | `0.7.1` | `Adventure and RPG + API and Library + Cosmetic + Magic + Utility & QoL` | 24/09/2026 - 11:14 |
| 233 | Dynamic Brightness | `DynamicBrightness-neoforge-1.3.1.jar` | `1.3.1` | `Utility & QoL` | 24/09/2026 - 11:14 |
| 234 | Dynamic Trees Addon Lib | `DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar` | `0.2.0-BETA03` | `Addons + API and Library + Cosmetic` | 24/09/2026 - 11:14 |
| 235 | Dynamic Trees | `dynamictrees-neoforge-1.21.1-1.7.2.jar` | `1.7.2` | `Biomes + Cosmetic + Farming + World Gen` | 24/09/2026 - 11:14 |
| 236 | Dynamic Trees Plus | `DynamicTreesPlus-neoforge-1.21.1-1.3.2.jar` | `1.3.2` | `Addons + Biomes + Cosmetic + Farming + World Gen` | 24/09/2026 - 11:14 |
| 237 | Create: Dynamic Village | `dynamicvillage-0.9-1.21.1.jar` | `0.9` | `Cosmetic + Create + Structures + Technology + World Gen` | 24/09/2026 - 11:14 |
| 238 | Easy Model Entities | `easy_model_entities-neoforge-1.21.1-2.4.0.jar` | `2.4.0 do JAR; metadata runtime sem versão` | `Adventure and RPG + API and Library + Automation + Mobs + Utility & QoL` | 22/10/2026 - 22:49 |
| 239 | Easy NPC: Core | `easy_npc-neoforge-1.21.1-7.12.1.jar` | `7.12.1 do JAR; metadata runtime sem versão` | `Adventure and RPG + API and Library` | 22/10/2026 - 18:52 |
| 240 | Easy NPC | `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` | `7.12.1` | `Adventure and RPG + API and Library` | 22/10/2026 - 18:52 |
| 241 | Easy NPC: Config UI | `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` | `7.12.1` | `Utility & QoL` | 23/10/2026 - 01:19 |
| 242 | Ecliptic Seasons: MultiMod Patch | `Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.1.jar` | `0.32.1` | `Addons + Utility & QoL` | 24/09/2026 - 14:02 |
| 243 | Ecliptic Seasons | `EclipticSeasons-1.21.1-neoforge-0.15.0-rc-3-1.jar` | `0.15.0-rc-3-1` | `Adventure and RPG + Cosmetic + Farming + Utility & QoL` | 22/10/2026 - 23:19 |
| 244 | Ecliptic Seasons : Bundles | `EclipticSeasons-Bundles-0.18.0.2.jar` | `0.18.0.2` | `Addons + Utility & QoL` | 24/09/2026 - 14:02 |
| 245 | Epic Fight x Iron's Spells: Enhanced Animations | `efiscompat-3.1.0.jar` | `3.1.0` | `Cosmetic + Miscellaneous` | 24/09/2026 - 14:02 |
| 246 | Eidolon : Repraised | `eidolon_repraised-1.21.1-0.5.0.2.jar` | `0.5.0.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 22/10/2026 - 22:49 |
| 247 | EMF Compat: Core | `emf_compat_core_1.21.1_2.0.0.jar` | `2.0.0` | `API and Library + Cosmetic` | 22/10/2026 - 22:26 |
| 248 | EMF Compat: Create | `emf_compat_create_1.21.1_2.0.0.jar` | `2.0.0` | `Bug Fixes + Cosmetic` | 24/09/2026 - 14:02 |
| 249 | EMF Compat: Iron's Spells 'n Spellbooks | `emf_compat_iron_spells_1.21.1_2.0.0.jar` | `2.0.0` | `Addons + Bug Fixes` | 24/09/2026 - 14:02 |
| 250 | Enchantment Descriptions | `enchdesc-neoforge-1.21.1-21.1.11.jar` | `21.1.11` | `Cosmetic + Magic + Map and Information + Utility & QoL` | 24/09/2026 - 14:02 |
| 251 | Ender's Delight | `endersdelight-1.3.1.jar` | `1.3.1` | `Addons + Farming + Food` | 24/09/2026 - 14:02 |
| 252 | Enhanced AI | `enhancedai-4.2.3.0.jar` | `4.2.3.0` | `Mobs` | 23/10/2026 - 01:41 |
| 253 | Enhanced Boss Bars (Fabric/Forge/NeoForge) | `enhancedbossbars-1.0.0.jar` | `1.0.0` | `Adventure and RPG + Cosmetic + Miscellaneous + Mobs` | 24/10/2026 - 01:01 |
| 254 | [EMF] Entity Model Features [Fabric & Forge] | `entity_model_features-3.3.5-1.21-neoforge.jar` | `3.3.5` | `Cosmetic + Mobs + Utility & QoL` | 23/10/2026 - 14:33 |
| 255 | [ESF] Entity Sound Features | `entity_sound_features-0.8.2-1.21-neoforge.jar` | `0.8.2` | `Cosmetic + Mobs + Utility & QoL` | 24/09/2026 - 16:02 |
| 256 | [ETF] Entity Texture Features - [Fabric & Forge] | `entity_texture_features-7.2.1-1.21-neoforge.jar` | `7.2.1` | `Cosmetic + Utility & QoL` | 22/10/2026 - 22:49 |
| 257 | Entity Culling Fabric/Forge | `entityculling-neoforge-1.10.5-mc1.21.1.jar` | `1.10.5` | `Miscellaneous + Utility & QoL` | 29/09/2026 - 02:57 |
| 258 | EpheroLib | `EpheroLib-1.21.1-NEO-FORGE-1.2.0.jar` | `1.2.0` | `API and Library` | 24/09/2026 - 16:02 |
| 259 | Epic Fight: Curios Compat 4.0 | `Epic Fight x Curios Compat 2.2.jar` | `2.2 (distribuição/filename); metadata runtime 1.4` | `Addons + Bug Fixes + Cosmetic` | 24/10/2026 - 01:00 |
| 260 | Epic Fight | `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar` | `21.17.3.1` | `Adventure and RPG + API and Library + Armor, Tools, and Weapons` | 24/09/2026 - 16:02 |
| 261 | Epic Fight - Epic-API | `epic_api-21.3.1.jar` | `21.3.1` | `API and Library` | 24/09/2026 - 16:02 |
| 262 | Minecolonies: Epicfied (Epic Colonies) | `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar` | `21.0.8` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Cosmetic` | 22/10/2026 - 22:49 |
| 263 | Epic Fight - Mod Compat | `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` | `1.1.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Mobs` | 24/09/2026 - 16:58 |
| 264 | Epic Fight - Pehkui FIX | `epicfightpehkuiincompatibilityfix-1.0.2.jar` | `1.0.2` | `Addons + Bug Fixes` | 24/09/2026 - 16:58 |
| 265 | Ender's Spells and Stuff: Requiem | `ess_requiem-0.1.7.jar` | `0.1.7` | `Addons` | 24/09/2026 - 16:58 |
| 266 | Euphoria Patches | `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar` | `1.10.5-r5.9.3-neoforge` | `Addons + Cosmetic + Miscellaneous + Utility & QoL` | 24/09/2026 - 16:58 |
| 267 | ExpandAbility | `expandability-12.0.0.jar` | `12.0.0` | `API and Library` | 24/09/2026 - 16:58 |
| 268 | Expanded Delight | `expandeddelight-0.1.4.jar` | `0.1.4` | `Addons + Farming + Food + Ores and Resources` | 24/09/2026 - 16:58 |
| 269 | Explorer's Compass | `ExplorersCompass-1.21.1-3.4.0-neoforge.jar` | `1.21.1-3.4.0-neoforge` | `Adventure and RPG + Armor, Tools, and Weapons + Map and Information + Structures` | 24/09/2026 - 16:58 |
| 270 | Explosive Enhancement: Reforged | `explosiveenhancement-neoforge-1.21.1-1.1.2.jar` | `1.1.2` | `Cosmetic + Miscellaneous` | 24/09/2026 - 16:58 |
| 271 | Create: Extra Copycats | `extra_copycats-1.0.2.jar` | `1.0.2` | `Addons + Create` | 24/09/2026 - 16:58 |
| 272 | Factory Construction Registry Probe | `factory_construction_registry_probe-0.1.0.jar` | `0.1.0` | `— (dossier ausente; categoria não atribuída)` | — |
| 273 | FamiliarsLib | `familiarslib-1.21.1-1.7.1.jar` | `1.7.1 do JAR/publicação; metadata runtime 1.21.1-1.7` | `API and Library` | 22/10/2026 - 23:20 |
| 274 | Fantasy Armor (Medieval Series) | `fantasy_armor-neoforge-1.2.4-1.21.1.jar` | `1.2.4-1.21.1` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Magic` | 24/09/2026 - 17:35 |
| 275 | Farmer's Spell 'n Spell Book | `farmers-spell-n-spellbook-1.0.5.1-1.21.1.jar` | `1.0.5.1-1.21.1` | `Farmer's Delight + Food + Magic` | 24/09/2026 - 17:35 |
| 276 | Farmer's Delight | `FarmersDelight-1.21.1-1.3.4.jar` | `1.3.4` | `Cosmetic + Farming + Food` | 24/09/2026 - 17:35 |
| 277 | Farmer's Delight: Extended | `farmersdelight_extended-1.21.1-0.2.2.jar` | `1.21.1-0.2.2` | `Addons + Create + Food` | 24/09/2026 - 17:36 |
| 278 | Farmers Structures | `FarmersStructures-1.0.6-1.21.1_neoforge.jar` | `1.0.6` | `Addons + Farming + MCreator + Structures + World Gen` | 24/09/2026 - 17:36 |
| 279 | FastSuite | `FastSuite-1.21.1-6.0.7.jar` | `6.0.7` | `Performance` | 24/09/2026 - 17:36 |
| 280 | FerriteCore ((Neo)Forge) | `ferritecore-7.0.3-neoforge.jar` | `7.0.3` | `Performance` | 24/09/2026 - 17:37 |
| 281 | Fire's Ender Expansion | `firesenderexpansion-2.4.1.jar` | `2.4.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 17:37 |
| 282 | First-person Model | `firstperson-neoforge-2.7.2-mc1.21.1.jar` | `2.7.2` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic` | 24/09/2026 - 18:26 |
| 283 | Flowing Fluids | `flowing_fluids-1.0.6-1.21-neoforge.jar` | `1.0.6` | `Miscellaneous` | 24/09/2026 - 18:27 |
| 284 | Create: Fluid | `fluid-2.1.6.jar` | `2.1.6` | `Addons + Create` | 24/09/2026 - 18:23 |
| 285 | Create: FluidLogistics | `fluidlogistics-1.3.0-mc1.21.1.jar` | `1.3.0` | `Create + Utility & QoL` | 24/09/2026 - 18:24 |
| 286 | Forgified Fabric API | `forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar` | `0.116.15+2.3.5+1.21.1` | `Technology` | 22/10/2026 - 19:46 |
| 287 | Fragmentum [NeoForge Edition] | `fragmentum-neoforge-1.21.1-2.4.4.jar` | `2.4.4` | `API and Library` | 22/10/2026 - 23:19 |
| 288 | FTB Chunks (NeoForge) | `ftb-chunks-neoforge-2101.1.22.jar` | `2101.1.22` | `Map and Information + Server Utility` | 24/09/2026 - 18:24 |
| 289 | FTB Library (NeoForge) | `ftb-library-neoforge-2101.1.36.jar` | `2101.1.36` | `API and Library` | 24/09/2026 - 18:24 |
| 290 | FTB Quests (NeoForge) | `ftb-quests-neoforge-2101.1.36.jar` | `2101.1.36` | `Adventure and RPG + API and Library + Map and Information + Server Utility` | 24/09/2026 - 18:24 |
| 291 | FTB Teams (NeoForge) | `ftb-teams-neoforge-2101.1.11.jar` | `2101.1.11` | `API and Library` | 24/09/2026 - 18:24 |
| 292 | FTB Ultimine (NeoForge) | `ftb-ultimine-neoforge-2101.1.15.jar` | `2101.1.15` | `Armor, Tools, and Weapons + Server Utility` | 24/09/2026 - 19:05 |
| 293 | FTB XMod Compat | `ftb-xmod-compat-neoforge-21.1.11.jar` | `21.1.11` | `API and Library` | 22/10/2026 - 22:00 |
| 294 | Fusion (Connected Textures) | `fusion-1.3.15a-neoforge-mc1.21.1.jar` | `1.3.15+a` (metadata runtime); filename/release `1.3.15a` | `API and Library + Cosmetic` | 24/09/2026 - 19:05 |
| 295 | Fancy World Animations | `fwa+1.21.1-neoforge-1.2.31.jar` | `1.2.31` | `Cosmetic` | 24/09/2026 - 19:05 |
| 296 | Fzzy Config | `fzzy_config-0.7.7+1.21+neoforge.jar` | `0.7.7+1.21+neoforge` | `API and Library + Utility & QoL` | 24/09/2026 - 19:05 |
| 297 | Gabou's Libs | `gaboulibs-neoforge-1.9.jar` | `1.9` | `API and Library` | 24/09/2026 - 19:05 |
| 298 | Create Aeronautics: Gadgets & Gizmos | `gadgets-and-gizmos-bundled-V1.2.2.jar` | `1.2.2` | `Addons + Create` | 22/10/2026 - 22:26 |
| 299 | Create: Garnished | `garnished-2.1.9.2+1.21.1-neoforged.jar` | `2.1.9.2` | `Addons + Automation + Create + Food` | 24/09/2026 - 19:05 |
| 300 | Gaze - A Malum Addon | `gaze-1.1.7.1.jar` | `1.1.7.1` | `Addons` | 24/09/2026 - 19:05 |
| 301 | GeckoLib | `geckolib-neoforge-1.21.1-4.9.2.jar` | `4.9.2` | `API and Library` | 24/09/2026 - 19:05 |
| 302 | Global Packs | `globalpacks-neoforge-1.21.1-21.0.6.jar` | `21.0.6` | `Map and Information + Utility & QoL` | 24/09/2026 - 21:04 |
| 303 | Glodium | `Glodium-1.21-2.2-neoforge.jar` | `1.21-2.2-neoforge` | `API and Library` | 24/09/2026 - 21:04 |
| 304 | GroovyModLoader (GML) | `gml-6.0.2.jar` | `6.0.2` | `API and Library` | 24/09/2026 - 21:04 |
| 305 | Goety - The Dark Arts | `goety-3.1.4.jar` | `3.1.4` | `Adventure and RPG + Magic + Mobs + Structures` | 24/10/2026 - 01:38 |
| 306 | Goety Cataclysm | `goety_cataclysm-1.21.1-1.8.2.jar` | `1.21.1-1.8.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 21:04 |
| 307 | Goety Iron | `GoetyIron-1.21.1-NeoForge-3.1.jar` | `3.1` | `Magic + Mobs` | 24/09/2026 - 21:04 |
| 308 | Grappling Hook Mod: Skybound | `grapplemod-1.1+1.21.1.neoforge.jar` | `1.1+1.21.1.neoforge` | `Player Transport + Skyblock + Utility & QoL` | 24/09/2026 - 21:04 |
| 309 | GTBC's Geomancy Plus - Iron's Spells Addon | `gtbcs_geomancy_plus-1.1.0-1.21.1.jar` | `1.1.0-1.21.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 21:04 |
| 310 | GTBC's SpellLib/API | `gtbcs_spell_lib-2.2.0-1.21.1.jar` | `2.2.0-1.21.1` | `Addons + API and Library` | 24/09/2026 - 21:04 |
| 311 | Guide-API Village and Pillage | `Guide-API-VP-1.21.1-2.3.0.jar` | `2.3.0` | `API and Library + Map and Information` | 24/09/2026 - 21:04 |
| 312 | Hazen 'N Stuff | `hazennstuff-1.4.0.14.jar` | `1.4.0.14` | `Addons + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 21:57 |
| 313 | HazentouveLib | `hazentouvelib-1.0.9.jar` | `1.0.9` | `API and Library` | 24/09/2026 - 21:57 |
| 314 | Hexalia | `hexalia-neoforge-1.3.7.jar` | `1.3.7` | `Adventure and RPG + Magic + Mobs + World Gen` | 27/09/2026 - 03:14 |
| 315 | Ice And Fire: Dragon Care | `Ice and Fire - Dragon Care-1.3.1 - 1.21.1v.jar` | `1.3.1 - 1.21.1v` | `Addons` | 27/09/2026 - 03:14 |
| 316 | IceAndFire Community Edition | `iceandfire-2.1.2.jar` | `2.1.2` | `Adventure and RPG + Magic + Ores and Resources + World Gen` | 27/09/2026 - 03:14 |
| 317 | Ice and Fire X Epic Fight | `iceandfire-ce-epicfight-armor-compat-1.0.0.jar` | `1.0.0` | `Addons + Armor, Tools, and Weapons + Bug Fixes + Cosmetic` | 24/09/2026 - 22:20 |
| 318 | Ice And Fire: Dread Land | `iceandfire_dreadland-0.1.2.jar` | `0.1.2` | `Addons + Adventure and RPG + Biomes + Dimensions` | 24/09/2026 - 22:20 |
| 319 | Iceberg [Neo/Forge] | `Iceberg-1.21.1-neoforge-1.3.2.jar` | `1.3.2` | `API and Library` | 27/09/2026 - 03:14 |
| 320 | Integrated Dungeons Arise | `IDA v2.1.1-1.21.1.jar` | `2.1.1` | `Adventure and RPG + Structures + World Gen` | 24/09/2026 - 22:20 |
| 321 | Integrated Dungeons and Structures | `idas-1.13.7+1.21.1-neoforge.jar` | `1.13.7+1.21.1-neoforge` | `Adventure and RPG + Armor, Tools, and Weapons + Create + Structures + World Gen` | 24/09/2026 - 22:20 |
| 322 | Iglee's Library | `igleelib-1.21.1-1.2.7.jar` | `1.21.1-1.2.7` | `API and Library` | 24/09/2026 - 22:53 |
| 323 | Cataclysm: Ignis Soulfires | `ignissoulfires-1.8.0.jar` | `1.8.0` | `Addons + Armor, Tools, and Weapons + Cosmetic + Ores and Resources` | 24/09/2026 - 22:53 |
| 324 | Ignis Soulfires: Spellbooks | `ignissoulfires_spellbooks-1.1.0.jar` | `1.1.0` | `Addons + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 22:53 |
| 325 | Integrated Mowzie's Mobs | `IMM v1.3.0-1.21.1.jar` | `1.3.0` | `Adventure and RPG + Structures + World Gen` | 24/09/2026 - 22:53 |
| 326 | ImmediatelyFast | `ImmediatelyFast-NeoForge-1.6.14+1.21.1.jar` | `1.6.14+1.21.1` | `Performance + Utility & QoL` | 22/10/2026 - 22:24 |
| 327 | Immersive Aeronautics - Immersive Portals + Create: Aeronautics | `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar` | `1.1.4` | `Create` | 24/09/2026 - 22:53 |
| 328 | Immersive Furniture | `immersive_furniture-neoforge-0.3.3+1.21.1.jar` | `0.3.3+1.21.1` | `Cosmetic + Storage` | 24/09/2026 - 22:53 |
| 329 | Immersive Portal - Iron's Spells 'n Spellbooks Addon | `immersive_portal_irons_spells_n_spellbooks_addon-1.0.1.jar` | `1.0.1` | `Magic + Player Transport` | 24/09/2026 - 22:53 |
| 330 | Immersive Portals: True Immersion | `immersive_portals_true_immersion-2.0.4.jar` | `2.0.4` | `Addons` | 24/09/2026 - 22:53 |
| 331 | InsaneLib | `insanelib-2.4.33.0.jar` | `2.4.33.0` | `API and Library` | 22/10/2026 - 19:46 |
| 332 | Integrated API | `integrated_api-neoforge-1.21.1-1.8.2.jar` | `1.8.2` | `Adventure and RPG + API and Library + Structures + World Gen` | 24/09/2026 - 23:17 |
| 333 | Integrated Cataclysm | `integrated_cataclysm-1.0.6+1.21.1-neoforge.jar` | `1.0.6+1.21.1-neoforge` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Create + World Gen` | 24/09/2026 - 23:17 |
| 334 | Integrated Simply Swords | `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar` | `1.4.0+1.21.1-neoforge` | `Addons + Adventure and RPG + Armor, Tools, and Weapons` | 24/09/2026 - 23:18 |
| 335 | Integrated Stronghold | `integrated_stronghold-1.1.4+1.21.1-neoforge.jar` | `1.1.4+1.21.1-neoforge` | `Adventure and RPG + Armor, Tools, and Weapons + Create + Structures + World Gen` | 25/09/2026 - 00:26 |
| 336 | Integrated Villages | `integrated_villages-1.3.3+1.21.1-neoforge.jar` | `1.3.3+1.21.1-neoforge` | `Adventure and RPG + Create + Magic + Structures + World Gen` | 24/09/2026 - 23:21 |
| 337 | Inventory Sorter | `inventorysorter-1.21.1-24.0.24.jar` | `24.0.24` | `Miscellaneous` | 24/09/2026 - 23:21 |
| 338 | Iris Shaders | `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar` | `1.8.14-beta.1+mc1.21.1` | `Cosmetic + Miscellaneous` | 25/09/2026 - 00:26 |
| 339 | Apotheosis x Iron's Spellbooks Compat | `irons_apothic-2.2.2.jar` | `2.2.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 22/10/2026 - 23:21 |
| 340 | Iron's Gems 'n Jewelry | `irons_jewelry-1.21.1-2.0.2.jar` | `1.21.1-2.0.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 23:22 |
| 341 | Iron's Lib | `irons_lib-1.21.1-2.1.0.jar` | `1.21.1-2.1.0` | `API and Library + Cosmetic` | 24/09/2026 - 23:22 |
| 342 | Iron's Spells 'n Spellbooks | `irons_spellbooks-1.21.1-3.16.3.jar` | `1.21.1-3.16.3` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs + Structures` | 25/09/2026 - 01:24 |
| 343 | KubeJS Iron's Spells | `irons_spells_js-4.0.3.jar` | `4.0.3` | `Addons + Adventure and RPG + KubeJS + Magic + Utility & QoL` | 25/09/2026 - 01:24 |
| 344 | IronSable | `ironsable-1.2.0.jar` | `1.2.0` | `Addons + Magic` | 25/09/2026 - 01:24 |
| 345 | IronSable X Wind's Spellbooks | `ironsable-wind-1.0.0.jar` | `1.0.0` | `Magic` | 25/09/2026 - 01:24 |
| 346 | Magic From The East | `iss_magicfromtheeast-1.1.5.jar` | `1.1.5` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic + World Gen` | 25/09/2026 - 01:24 |
| 347 | Jade 🔍 | `Jade-1.21.1-NeoForge-15.10.6.jar` | `15.10.6+neoforge` | `Map and Information + Utility & QoL` | 25/09/2026 - 01:24 |
| 348 | Just Enough Effect Descriptions (JEED) | `jeed-1.21-2.3.2.jar` | `1.21-2.3.2` | `Addons + Map and Information` | 25/09/2026 - 01:24 |
| 349 | Just Enough Items (JEI) | `jei-1.21.1-neoforge-19.56.0.440.jar` | `19.56.0.440` | `API and Library + Map and Information` | 24/10/2026 - 01:37 |
| 350 | JinxedLib | `jinxedlib-neoforge-1.21.1-1.0.4.jar` | `1.0.4` | `API and Library + Server Utility + Utility & QoL` | 25/09/2026 - 01:25 |
| 351 | JourneyMap Integration | `jmi-neoforge-1.21.1-1.9.jar` | `1.21.1-1.9` | `Addons + Map and Information` | 25/09/2026 - 01:25 |
| 352 | JourneyMap | `journeymap-neoforge-1.21.1-6.0.8.jar` | `1.21.1-6.0.8` | `Map and Information` | 25/09/2026 - 02:10 |
| 353 | Jupiter | `jupiter-2.3.7-1.21.1-neoforge.jar` | `2.3.7` | `API and Library + Server Utility + Utility & QoL` | 25/09/2026 - 02:10 |
| 354 | Just Enough Breeding (JEBr) | `justenoughbreeding-neoforge-1.21.1-3.2.1.jar` | `3.2.1` | `Addons + Map and Information + Mobs + Utility & QoL` | 25/09/2026 - 02:10 |
| 355 | Just Enough Professions (JEP) | `JustEnoughProfessions-neoforge-1.21.1-4.0.5.jar` | `4.0.5` | `Addons + Map and Information + Utility & QoL` | 25/09/2026 - 02:10 |
| 356 | Just Enough Resources (JER) | `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar` | `1.6.0.17` | `Addons + Biomes + Map and Information + Mobs + Ores and Resources` | 25/09/2026 - 02:10 |
| 357 | KilaGraph | `kilagraph-neoforge-1.21.1-21.1.0.14.jar` | `21.1.0.14` | `API and Library + Utility & QoL` | 25/09/2026 - 02:10 |
| 358 | Kiwi 🥝 (Neo/Forge) | `Kiwi-1.21.1-NeoForge-15.8.7.jar` | `15.8.7+neoforge` | `API and Library` | 25/09/2026 - 02:11 |
| 359 | Knight Lib | `knightlib-neoforge-1.21.1-2.0.1.jar` | `2.0.1` | `API and Library` | 23/10/2026 - 14:20 |
| 360 | Konkrete | `konkrete_neoforge_1.9.9_MC_1.21.jar` | `1.9.9` | `API and Library` | 25/09/2026 - 02:11 |
| 361 | Kotlin for Forge | `kotlinforforge-5.12.0-all.jar` | `5.12.0` | `API and Library` | 25/09/2026 - 02:11 |
| 362 | KubeJS Create | `kubejs-create-neoforge-2101.3.1-build.18.jar` | `2101.3.1-build.18` | `KubeJS` | 25/09/2026 - 10:33 |
| 363 | KubeJS | `kubejs-neoforge-2101.7.2-build.377.jar` | `2101.7.2-build.377` | `API and Library + KubeJS + Map and Information + Server Utility + World Gen` | 25/09/2026 - 10:33 |
| 364 | KubeJS Curios | `kubejs_curios_neoforge_1.21.1-1.0.4.jar` | `1.0.4` | `KubeJS` | 25/09/2026 - 10:33 |
| 365 | KubeJS Additions | `kubejsadditions-neoforge-1.21.1-6.0.0.jar` | `1.21.1-6.0.0` | `API and Library + Energy, Fluid, and Item Transport + KubeJS + Utility & QoL` | 25/09/2026 - 10:33 |
| 366 | KubeJS Ars Nouveau | `kubejsarsnouveau-1.3.2.jar` | `1.3.2` | `KubeJS + Magic` | 25/09/2026 - 10:33 |
| 367 | KubeJS Delight | `kubejsdelight-1.1.6.jar` | `1.1.6` | `KubeJS` | 25/09/2026 - 10:34 |
| 368 | L_Ender's Cataclysm | `L_Ender's Cataclysm 1.21.1-3.33.jar` | `3.33` | `Adventure and RPG + Armor, Tools, and Weapons + Mobs + Structures` | 25/09/2026 - 10:34 |
| 369 | LambDynamicLights | `lambdynamiclights-4.8.11+1.21.1.jar` | `4.8.11+1.21.1` | `Adventure and RPG + Cosmetic` | 25/09/2026 - 10:34 |
| 370 | LDLib | `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar` | `2.2.39.a` | `API and Library + Utility & QoL` | 25/09/2026 - 10:34 |
| 371 | Legendary Monsters | `legendary_monsters-2.2.2 MC 1.21.1.jar` | `2.2.2 (distribuição); metadata interna 1.21.1` | `Armor, Tools, and Weapons + Mobs + Structures` | 25/09/2026 - 10:34 |
| 372 | Legendary Spellbooks | `legendary_spellbooks-1.21.1+neo-0.3.2.jar` | `0.3.2` | `Addons + Magic` | 25/09/2026 - 11:24 |
| 373 | Leylines: Iron's Spells 'n Spellbooks Addon | `leylines-1.0.3.jar` | `1.0.3` | `Addons + Magic + Structures + World Gen` | 25/09/2026 - 11:24 |
| 374 | Lionfish API | `lionfishapi-3.1.jar` | `3.1` | `API and Library` | 25/09/2026 - 11:24 |
| 375 | Lithostitched | `lithostitched-1.8.0+beta6-neoforge-21.1.jar` | `1.8.0+beta6` | `API and Library + Miscellaneous + World Gen` | 23/10/2026 - 02:00 |
| 376 | Load My F***ing Tags | `lmft-1.1.1+1.21.9-neoforge.jar` | `1.1.1+1.21.9` | `Miscellaneous + Server Utility` | 25/09/2026 - 11:24 |
| 377 | Epic Fight x Better Lock On: Movement & Camera Fixes | `lockonmovementfix-neoforge-1.0.2.jar` | `1.0.2` | `Addons + Bug Fixes + Utility & QoL` | 25/09/2026 - 11:24 |
| 378 | Lodestone | `lodestone-1.21.1-1.8.2.jar` | `1.8.2` | `API and Library` | 25/09/2026 - 11:24 |
| 379 | Loot Journal: Pickup Notifier [Forge Edition] | `loot_journal-neoforge-1.21.1-6.2.1.jar` | `6.2.1` | `Adventure and RPG + Cosmetic + Utility & QoL` | 25/09/2026 - 11:24 |
| 380 | Loot Integrations | `lootintegrations-1.21.1-4.7.jar` | `4.7` | `Adventure and RPG + Server Utility` | 25/09/2026 - 11:24 |
| 381 | Loot Integrations: L_Ender 's Cataclysm | `lootintegrations_cataclysm-1.2.jar` | `1.2 (distribuição); metadata interna 1` | `Addons + Structures` | 25/09/2026 - 11:24 |
| 382 | Loot Integrations: Ice and Fire | `lootintegrations_iceandfire-1.2.jar` | `1.2` | `Addons + Structures` | 25/09/2026 - 12:00 |
| 383 | Loot Integrations: Integrated Dungeons, Villages & Strongholds & Cataclysm | `lootintegrations_integrated-1.5.jar` | `1.5 (distribuição); metadata interna 1` | `Addons + Structures` | 25/09/2026 - 12:00 |
| 384 | Loot Integrations: Randomized Loot Compatibility | `lootintegrations_vanilla-1.7.jar` | `1.7 (distribuição); metadata interna 1` | `Addons + Structures` | 25/09/2026 - 12:00 |
| 385 | Yung Structures Addon for Loot Integrations | `lootintegrations_yungs-1.6.jar` | `1.6 (distribuição); metadata interna 1` | `Addons + Adventure and RPG + Structures` | 25/09/2026 - 12:00 |
| 386 | LootJS: KubeJS Addon | `lootjs-neoforge-1.21.1-3.7.0.jar` | `1.21.1-3.7.0` | `KubeJS + Map and Information + Server Utility` | 25/09/2026 - 12:00 |
| 387 | Lootr (Forge & NeoForge) | `lootr-neoforge-1.21.1-1.11.38.125.jar` | `1.21.1-1.11.38.125` | `Server Utility + Structures` | 25/09/2026 - 12:00 |
| 388 | Lychee (Neo/Forge) | `Lychee-1.21.1-NeoForge-6.7.0.jar` | `6.7.0+neoforge` | `Utility & QoL` | 22/10/2026 - 22:24 |
| 389 | Malum | `malum-1.21.1-1.8.2.jar` | `1.8.2` | `Armor, Tools, and Weapons + Cosmetic + Magic + Ores and Resources + World Gen` | 25/09/2026 - 12:00 |
| 390 | Max Health Fix | `maxhealthfix-neoforge-1.21.1-21.1.4.jar` | `21.1.4` | `Adventure and RPG + Armor, Tools, and Weapons + Bug Fixes + Server Utility + Utility & QoL` | 25/09/2026 - 12:00 |
| 391 | MineColonies: Jade crops addon | `mcjadecrops-1.1.1300.jar` | `1.1.1300` | `Addons + Farming + Utility & QoL` | 25/09/2026 - 12:00 |
| 392 | Mechanicals Lib | `mechanicals-1.21.1-1.1.6.jar` | `1.1.6` | `API and Library` | 25/09/2026 - 12:31 |
| 393 | Melody | `melody_neoforge_1.0.10_MC_1.21.jar` | `1.0.10` | `API and Library` | 25/09/2026 - 12:31 |
| 394 | MezzConfig | `mezz_config-1.21.1-neoforge-0.5.9.jar` | `0.5.9` | `API and Library` | 20/09/2026 - 01:38 |
| 395 | MineColonies | `minecolonies-1.1.1387-1.21.1-snapshot.jar` | `1.1.1387-1.21.1-snapshot` | `Adventure and RPG + Armor, Tools, and Weapons + Automation + Storage + Structures` | 22/10/2026 - 19:45 |
| 396 | Compatibility addon for MineColonies | `MineColonies_Compatibility-1.21.1-3.57.jar` | `3.57` | `Addons + Utility & QoL` | 23/10/2026 - 00:43 |
| 397 | Tweaks addon for MineColonies | `MineColonies_Tweaks-1.21.1-3.33.jar` | `3.33` | `Addons + Utility & QoL` | 25/09/2026 - 12:31 |
| 398 | Miner's Delight + | `minersdelight-1.21.1-1.4.5.jar` | `1.4.5` | `Cosmetic + Farming + Food` | 25/09/2026 - 12:31 |
| 399 | Ragdoll mob corpses | `mob_ragdoll_corpse-1.1.5.jar` | `1.1.5` | `Addons + Mobs` | 25/09/2026 - 12:31 |
| 400 | Mobstein : Revive animals and necromancy! | `mobstein-5.4.4-neoforge-1.21.1.jar` | `5.4.4` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Mobs` | 25/09/2026 - 12:31 |
| 401 | Model Gap Fix | `modelfix-1.21-1.10.jar` | `1.21-1.10` | `API and Library + Cosmetic + Utility & QoL` | 25/09/2026 - 12:31 |
| 402 | ModernFix | `modernfix-neoforge-5.27.24+mc1.21.1.jar` | `5.27.24+mc1.21.1` | `Performance + Server Utility + Utility & QoL` | 25/09/2026 - 13:25 |
| 403 | Modern UI | `ModernUI-NeoForge-1.21.1-3.13.0.1-universal.jar` | `3.13.0.1` | `API and Library + Cosmetic + Miscellaneous + Utility & QoL` | 25/09/2026 - 13:25 |
| 404 | Modonomicon | `modonomicon-1.21.1-neoforge-1.120.4.jar` | `1.120.4` | `Adventure and RPG + API and Library + Map and Information` | 25/09/2026 - 13:25 |
| 405 | MonoLib | `monolib-neoforge-1.21.1-4.1.0.jar` | `4.1.0` | `API and Library + Education + Utility & QoL` | 25/09/2026 - 13:25 |
| 406 | Monsters & Spellbooks: Iron's Spells 'n Spellbooks Addon | `monsterspellbooks-0.0.16.3.jar` | `0.0.16.3` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 25/09/2026 - 13:25 |
| 407 | Moonlight Lib | `moonlight-1.21.1-3.6.4-neoforge.jar` | `1.21.1-3.6.4` | `API and Library` | 17/09/2026 - 00:06 |
| 408 | More Relics | `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` | `1.7.7-forRelics-0.12.8-1.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 25/09/2026 - 13:25 |
| 409 | Mouse Tweaks | `MouseTweaks-neoforge-mc1.21-2.26.1.jar` | `2.26.1` | `Miscellaneous + Storage + Utility & QoL` | 25/09/2026 - 13:25 |
| 410 | Mowzie's Cataclysm | `mowzies_cataclysm-1.2.2.jar` | `1.2.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 25/09/2026 - 13:25 |
| 411 | Mowzie's Mobs | `mowziesmobs-1.21.1-1.8.2.jar` | `1.8.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs + Structures` | 25/09/2026 - 13:25 |
| 412 | M.R.U | `mru-1.0.40+1.21.1-neoforge.jar` | `1.0.40+1.21.1` | `API and Library` | 16/09/2026 - 18:42 |
| 413 | Multi-Piston | `multipiston-1.2.58-1.21.1.jar` | `1.2.58-1.21.1` | `Adventure and RPG + Cosmetic + Player Transport + Redstone` | 25/09/2026 - 14:00 |
| 414 | My Nether's Delight | `MyNethersDelight-1.21.1-1.10.4.1.jar` | `1.10.4.1` | `Cosmetic + Farming + Food` | 25/09/2026 - 14:00 |
| 415 | Nature's Compass | `NaturesCompass-1.21.1-3.4.0-neoforge.jar` | `1.21.1-3.4.0-neoforge` | `Armor, Tools, and Weapons + Biomes + Map and Information + Technology` | 25/09/2026 - 14:00 |
| 416 | Dungeons Delight | `neoforge-dungeonsdelight-1.21.1-1.5.1.jar` | `1.5.1 (distribuição); metadata interna 1.5.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Food + Magic` | 17/09/2026 - 21:40 |
| 417 | RunicLib | `neoforge-runiclib-1.21.1-5.0.7.jar` | `5.0.7` | `API and Library + Magic + Utility & QoL` | 25/09/2026 - 14:00 |
| 418 | Create: Northstar - Redux | `Northstar-0.6.5+1.21.1.jar` | `0.6.5+1.21.1` | `Addons + Create + Dimensions + KubeJS + Technology` | 16/09/2026 - 18:39 |
| 419 | Not Enough Glyphs | `not_enough_glyphs-1.21.1-4.6.2.jar` | `4.6.2` | `Addons + Magic + Utility & QoL` | 18/09/2026 - 08:51 |
| 420 | Not Enough Animations | `notenoughanimations-neoforge-1.12.4-mc1.21.1.jar` | `1.12.4` | `Cosmetic + Map and Information` | 25/09/2026 - 14:00 |
| 421 | [NTGL] NukaTeam's Gun Lib | `ntgl-1.21.1-3.2.0.jar` | `3.2.0` | `API and Library + Armor, Tools, and Weapons` | 25/09/2026 - 14:00 |
| 422 | Nutritional Balance | `nutritionalbalance-1.21.1-7.0.3.jar` | `1.21.1-7.0.3` | `Adventure and RPG + Food` | 29/09/2026 - 23:37 |
| 423 | Nyf's Spiders | `nyfsspiders-neoforge-1.21.1-3.0.1.jar` | `3.0.1` | `Mobs` | 25/09/2026 - 14:28 |
| 424 | Obscure Tooltips | `obscure_tooltips-neoforge-1.21.1-4.2.4.jar` | `4.2.4` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Map and Information` | 25/09/2026 - 14:28 |
| 425 | ShatterLib \| OctoLib | `OctoLib-NEOFORGE-0.6.2+1.21.jar` | `0.6.2` | `API and Library` | 25/09/2026 - 14:28 |
| 426 | Oh The Biomes We've Gone | `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar` | `2.6.0` | `Biomes + Farming + Mobs + Structures + World Gen` | 25/09/2026 - 14:28 |
| 427 | Oh The Trees You'll Grow | `Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar` | `5.3.2` | `API and Library + Biomes + Structures + World Gen` | 25/09/2026 - 14:28 |
| 428 | Oracle Index | `oracle_index-neoforge-1.4.0.jar` | `1.4.0` | `API and Library + Education + Map and Information + Utility & QoL` | 17/09/2026 - 22:51 |
| 429 | Overflowing Bars | `OverflowingBars-v21.1.1-1.21.1-NeoForge.jar` | `21.1.1` | `Cosmetic + Utility & QoL` | 25/09/2026 - 14:28 |
| 430 | oωo (owo-lib) | `owo-lib-neoforge-0.12.15.5-beta.1+1.21.jar` | `0.12.15.5-beta.1+1.21` | `API and Library` | 22/10/2026 - 22:49 |
| 431 | Ozymandias' Sundries | `ozymandias_sundries-0.0.5.jar` | `0.0.5 (distribuição/publicação); metadata interna 0.0.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 25/09/2026 - 14:28 |
| 432 | Paladin Spells - Iron's Spells Addon | `paladin_spells-1.21.1-1.1.1.jar` | `1.21.1-1.1.1` | `Addons + Magic` | 25/09/2026 - 15:17 |
| 433 | ParCool! ~ Minecraft Parkour ~ | `ParCool-1.21.1-4.0.0.3.jar` | `4.0.0.3` | `Adventure and RPG + Miscellaneous + Utility & QoL` | 23/10/2026 - 01:43 |
| 434 | Particle Effects | `ParticleEffects-1.5.0+1.21.1+neoforge.jar` | `1.5.0+1.21.1+neoforge` | `Cosmetic + Utility & QoL` | 25/09/2026 - 15:17 |
| 435 | Particle Rain | `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar` | `4.0.0-beta.11` | `Cosmetic` | 25/09/2026 - 15:17 |
| 436 | Particular ✨ Reforged | `particular-1.21.1-NeoForge-1.5.7.jar` | `1.5.7` | `Cosmetic` | 25/09/2026 - 15:17 |
| 437 | PartiCull: Particle Fix | `PartiCull-neoforge-1.21.1-v2.0.jar` | `2.0` | `Performance + Utility & QoL` | 25/09/2026 - 15:17 |
| 438 | Patchouli | `Patchouli-1.21.1-93-NEOFORGE.jar` | `1.21.1-93-NEOFORGE` | `API and Library` | 25/09/2026 - 15:17 |
| 439 | Pehkui | `Pehkui-3.8.3+1.21-neoforge.jar` | `3.8.3+1.21-neoforge` | `API and Library` | 25/09/2026 - 15:17 |
| 440 | Petrolpark's Library | `petrolpark-1.21.1-1.5.11.jar` | `1.5.11` | `Addons + API and Library + Create` | 22/10/2026 - 19:46 |
| 441 | Presence Footsteps x Sable (Aeronautics Compat) | `pfsable-1.0.jar` | `1.0` | `Cosmetic + Utility & QoL` | 25/09/2026 - 15:17 |
| 442 | Photon | `photon-neoforge-1.21.1-2.2.6.a-all.jar` | `2.2.6.a` | `API and Library + Magic + Technology + Utility & QoL` | 22/10/2026 - 19:45 |
| 443 | Pickable Health Orbs | `pickable_orbs-1.21.1-1.0.0.jar` | `1.21.1-1.0.0` | `Adventure and RPG + Magic + Utility & QoL` | 26/09/2026 - 02:32 |
| 444 | Placebo | `Placebo-1.21.1-9.9.2.jar` | `9.9.2` | `API and Library` | 26/09/2026 - 02:32 |
| 445 | Platform | `Platform-neoforge-1.21.1-1.3.3.jar` | `1.3.3` | `API and Library` | 22/10/2026 - 21:59 |
| 446 | playerAnimator | `player-animation-lib-forge-2.0.4+1.21.1.jar` | `2.0.4+1.21.1` | `API and Library + Cosmetic` | 26/09/2026 - 02:33 |
| 447 | Player Animation Library | `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar` | `1.1.6+mc.1.21.1` | `API and Library` | 26/09/2026 - 02:33 |
| 448 | Polymorph+ | `polymorph_plus-neoforge-1.3.1+1.21.1.jar` | `1.3.1+1.21.1` | `Miscellaneous + Utility & QoL` | 22/10/2026 - 22:55 |
| 449 | Polytone | `polytone-1.21-4.4.0-neoforge.jar` | `1.21-4.4.0` | `Cosmetic + Utility & QoL` | 22/10/2026 - 23:21 |
| 450 | Pondus Inventory [Sable] | `pondus_inventory_fi-0.15-Beta.jar` | `0.15-Beta` | `Addons + API and Library + Utility & QoL` | 26/09/2026 - 02:33 |
| 451 | Create Aeronautics: Portable Engine Liquid Fuel | `portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar` | `2.0.0` | `Addons + Create + MCreator + Utility & QoL` | 26/09/2026 - 02:34 |
| 452 | Portable Hole | `PortableHole-v21.1.0-1.21.1-NeoForge.jar` | `21.1.0` | `Armor, Tools, and Weapons + Player Transport` | 26/09/2026 - 03:23 |
| 453 | Potentials | `potentials-neoforge-1.21-0.7.1.jar` | `0.7.1` | `API and Library + Energy, Fluid, and Item Transport + Utility & QoL` | 26/09/2026 - 03:23 |
| 454 | Create: Power Grid | `powergrid-mc1.21.1-0.6.1.jar` | `0.6.1` | `Addons + Create + Energy + Technology` | 26/09/2026 - 03:23 |
| 455 | Presence Footsteps (NeoForge) | `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar` | `1.21.1-1.12.0-beta.1` | `Cosmetic + Utility & QoL` | 26/09/2026 - 03:23 |
| 456 | Prickle | `prickle-neoforge-1.21.1-21.1.11.jar` | `21.1.11` | `API and Library + Server Utility + Utility & QoL` | 26/09/2026 - 03:24 |
| 457 | KubeJS Metalworks (KubeJS Addon for Productive Metalworks) | `productive-metalworks-kubejs-addon-1.0.0.jar` | `1.0.0` | `Addons + API and Library + KubeJS` | 26/09/2026 - 03:24 |
| 458 | Productive Metalworks | `productivemetalworks-1.21.1-1.15.1.jar` | `1.21.1-1.15.1` | `Ores and Resources + Processing` | 26/09/2026 - 03:24 |
| 459 | Prometheus | `prometheus-neoforge-1.21-1.2.5.jar` | `1.2.5` | `Adventure and RPG + API and Library + Cosmetic + Utility & QoL` | 26/09/2026 - 03:24 |
| 460 | Create: Protection Pixel | `protection_pixel-2.2.1-neoforge-1.21.1.jar` | `2.2.1` | `Adventure and RPG + Armor, Tools, and Weapons + Create + MCreator` | 26/09/2026 - 03:24 |
| 461 | Puddles & Floods | `puddleflood-1.1.5+1.21.1-neoforge.jar` | `1.1.5` | `Cosmetic` | 26/09/2026 - 03:24 |
| 462 | Pufferfish's Unofficial Additions | `pufferfish_unofficial_additions-1.21.1-2.2.8.jar` | `2.2.8` | `Addons` | 29/09/2026 - 23:37 |
| 463 | Pufferfish's Attributes [Fabric & Forge & NeoForge] | `puffish_attributes-0.8.3-1.21-neoforge.jar` | `0.8.3` | `Adventure and RPG + API and Library + Utility & QoL` | 27/09/2026 - 16:30 |
| 464 | Pufferfish's Skills [Fabric & Forge & NeoForge] | `puffish_skills-0.19.0-1.21-neoforge.jar` | `0.19.0` | `Adventure and RPG + API and Library + Utility & QoL` | 27/09/2026 - 16:30 |
| 465 | Punchy! - First person animations | `punchy-2.7e-neoforge-1.21.1.jar` | `2.7e` | `API and Library + Cosmetic + Map and Information` | 24/10/2026 - 02:10 |
| 466 | Epic Fight X Punchy! Neo | `punchy_epicfight_neoforge.jar` | `1.0.0` | `Addons` | 27/09/2026 - 16:30 |
| 467 | Puzzles Lib | `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar` | `21.1.60` | `API and Library` | 22/10/2026 - 21:57 |
| 468 | Quark | `Quark-4.1-484.jar` | `4.1-484` | `Cosmetic + Ores and Resources` | 27/09/2026 - 16:30 |
| 469 | Ragdoll Reactions | `ragdoll_reactions-1.21.1-0.7.0.jar` | `0.7.0` | `Addons + Cosmetic` | 27/09/2026 - 16:30 |
| 470 | Steam 'n' Rails Neoforge | `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar` | `0.3.0-beta.2+neoforge-mc1.21.1` | `Addons + Cosmetic + Create + Player Transport + Technology` | 27/09/2026 - 16:30 |
| 471 | Rechiseled | `rechiseled-1.2.6-neoforge-mc1.21.jar` | `1.2.6` | `Cosmetic` | 27/09/2026 - 16:30 |
| 472 | Iron's Spells 'n Spellbooks: Recolor | `recolor_tablet-1.3.3+1.21.1.jar` | `1.3.3+1.21.1` | `Cosmetic + Magic` | 27/09/2026 - 16:30 |
| 473 | Reese's Sodium Options | `reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar` | `2.2.3+mc1.21.1` | `Miscellaneous` | 27/09/2026 - 17:03 |
| 474 | Relics | `relics-1.21.1-0.12.8.jar` | `0.12.8` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Magic + Ores and Resources` | 22/10/2026 - 22:24 |
| 475 | Reliquified L_Ender 's Cataclysm new relics fix | `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar` | `1.0.2` | `Addons + Bug Fixes + Server Utility` | 27/09/2026 - 17:03 |
| 476 | Reliquified Ars Nouveau | `reliquified_ars_nouveau-1.21.1-0.8.1.jar` | `0.8.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 22/10/2026 - 22:49 |
| 477 | Reliquified Artifacts | `reliquified_artifacts-1.21.1-1.0.8.jar` | `1.0.8` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 17:03 |
| 478 | Reliquified Iron's Spells 'n Spellbooks | `reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar` | `0.2.7` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 23/10/2026 - 01:47 |
| 479 | Reliquified L_Ender 's Cataclysm | `reliquified_lenders_cataclysm-1.21.1-0.1.1.jar` | `0.1.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 17:03 |
| 480 | Resourceful Config | `resourcefulconfig-neoforge-1.21-3.0.11.jar` | `3.0.11` | `API and Library` | 27/09/2026 - 17:03 |
| 481 | Resourceful Lib | `resourcefullib-neoforge-1.21-3.0.12.jar` | `3.0.12` | `API and Library` | 27/09/2026 - 17:03 |
| 482 | Rhino | `rhino-2101.2.8-build.91.jar` | `2101.2.8-build.91` | `API and Library` | 27/09/2026 - 17:33 |
| 483 | Ritchie's Projectile Library | `ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar` | `2.1.2` | `API and Library` | 27/09/2026 - 17:33 |
| 484 | Create: Rubberworks | `rubberworks-neoforge-1.21.1-1.1.4.jar` | `1.1.4` | `Addons + Create` | 27/09/2026 - 17:33 |
| 485 | Sable Dynamic Lights (Create: Dynamic Lights fork) | `sable-dynamic-lights-1.21.1-2.0.1.jar` | `2.0.1` | `Cosmetic + Create` | 27/09/2026 - 17:33 |
| 486 | Sable | `sable-neoforge-1.21.1-2.0.5.jar` | `2.0.5` | `API and Library` | 27/09/2026 - 17:33 |
| 487 | sable-x-cpm | `sable-x-cpm-0.3.2+1.21.1.jar` | `0.3.2+1.21.1` | `Bug Fixes + Cosmetic + Create + Utility & QoL` | 27/09/2026 - 17:33 |
| 488 | Create Additions - Sable Compat | `sable_createaddition_compat-0.1.13.jar` | `0.1.13` | `Addons + Create` | 27/09/2026 - 17:33 |
| 489 | Sable & Flowing Fluids Compat | `sable_flowing_fluids_compat-1.0.2.jar` | `1.0.2` | `Addons + Bug Fixes` | 27/09/2026 - 17:33 |
| 490 | Sable: Ragdolls | `sable_player_ragdoll-1.21.1-0.7.5.jar` | `0.7.5` | `Addons + API and Library + Cosmetic + Create` | 27/09/2026 - 17:33 |
| 491 | Sable: Ragdoll - Patch | `sable_player_ragdoll_patch-1.21.1-1.9.jar` | `1.9` | `Bug Fixes` | 22/10/2026 - 22:49 |
| 492 | Sable Assembly Fix | `SableAssemblyFix-1.0.0.jar` | `1.0.0` | `Bug Fixes + Create + Server Utility` | 27/09/2026 - 18:15 |
| 493 | Sable Beyond | `sablebeyond-neoforge-1.21.1-v0.5.0.jar` | `0.5.0` | `Addons + API and Library + Create + KubeJS + Utility & QoL` | 27/09/2026 - 18:16 |
| 494 | Jade Sable Compat | `sablejade-1.3.0.jar` | `1.3.0` | `Utility & QoL` | 27/09/2026 - 18:35 |
| 495 | Sable: Mass view (Create Aeronautics) | `sablemassview-1.0.0.jar` | `1.0.0` | `Addons` | 27/09/2026 - 18:16 |
| 496 | Sable: Physics Compat | `sablephysicscompat-1.3.0.jar` | `1.3.0` | `Addons + Bug Fixes + Create` | 27/09/2026 - 18:16 |
| 497 | Create Stuff 'N Additions x Sable & Aeronautics Compat | `SableStuffAdditionsCompat v1.0.3-1.21.1.jar` | `1.0.3` | `Addons + Automation + Bug Fixes + Create` | 27/09/2026 - 18:17 |
| 498 | Searchables | `Searchables-neoforge-1.21.1-1.0.2.jar` | `1.0.2` | `API and Library + Cosmetic + Map and Information + Utility & QoL` | 27/09/2026 - 18:17 |
| 499 | ServerCore | `servercore-neoforge-1.5.19+1.21.1.jar` | `1.5.19+1.21.1` | `Performance + Server Utility` | 27/09/2026 - 18:17 |
| 500 | ShadowsZ | `shadowsz-1.1.9.jar` | `1.1.9` | `Addons + Magic + Mobs` | 27/09/2026 - 18:17 |
| 501 | Simply Swords: Cataclysm | `simplycataclysm-1.0.2+1.21.1+neoforge.jar` | `1.0.2+1.21.1+neoforge` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 18:17 |
| 502 | Simply More | `simplymore-forge-1.3.0_alpha.jar` | `1.3.0_alpha` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 19:00 |
| 503 | Simply Swords [Fabric & Forge] | `simplyswords-neoforge-1.70.2-1.21.1.jar` | `1.70.2-1.21.1` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 30/09/2026 - 10:20 |
| 504 | Simply Tooltips | `SimplyTooltips-neoforge-0.1.5.jar` | `0.1.5` | `API and Library + Miscellaneous` | 27/09/2026 - 19:01 |
| 505 | Create: Coasters Simulated | `simulatedcoasters-0.1.5.jar` | `0.1.5` | `Addons + Create` | 27/09/2026 - 19:01 |
| 506 | Skin Layers 3D | `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar` | `1.11.2` | `Adventure and RPG + Cosmetic` | 23/10/2026 - 02:00 |
| 507 | Sky Aesthetics | `sky_aesthetics-neoforge-2.0.13-beta.jar` | `2.0.13-beta` | `API and Library + Cosmetic + Dimensions + Utility & QoL` | 27/09/2026 - 19:01 |
| 508 | Create Slice & Dice | `sliceanddice-4.3.3-neoforge.jar` | `4.3.3` | `Addons + Farming + Technology` | 27/09/2026 - 19:01 |
| 509 | Create: Smart Bounds | `smart_bounds-1.0.0.jar` | `1.0.0` | `Bug Fixes + Create + Performance + Technology` | 27/09/2026 - 19:02 |
| 510 | SmartBrainLib (Forge/Fabric/Quilt) | `SmartBrainLib-neoforge-1.21.1-1.16.11.jar` | `1.16.11` | `API and Library` | 27/09/2026 - 19:02 |
| 511 | Snow! Real Magic! ⛄ (Neo/Forge) | `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar` | `12.2.2+neoforge` | `Cosmetic + Utility & QoL + World Gen` | 27/09/2026 - 19:02 |
| 512 | Sodium | `sodium-neoforge-0.8.13+mc1.21.1.jar` | `0.8.13+mc1.21.1` | `Performance` | 27/09/2026 - 20:56 |
| 513 | Somake Spells - Iron's Spells Addon | `somakespells-1.0.9-1.21.1.jar` | `1.0.9` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 20:56 |
| 514 | Thirst Upgrade for Sophisticated Backpacks (Thirst Was Taken) | `sophisticated-thirst-upgrade.jar` | `0.1.8` | `Miscellaneous` | 27/09/2026 - 21:19 |
| 515 | Sophisticated JEI Index | `sophisticated_jei_index-1.2.3+1.21.1.jar` | `1.2.3` | `Addons + Applied Energistics 2 + Refined Storage + Storage` | 27/09/2026 - 20:56 |
| 516 | Sophisticated Backpacks | `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar` | `3.26.3` | `Armor, Tools, and Weapons + Storage` | 23/10/2026 - 01:13 |
| 517 | Sophisticated Backpacks Create Integration | `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar` | `0.2.0` | `Addons + Storage` | 27/09/2026 - 21:19 |
| 518 | Sophisticated Core | `sophisticatedcore-1.21.1-1.5.1.2341.jar` | `1.5.1` | `API and Library` | 23/10/2026 - 15:08 |
| 519 | Sophisticated Storage | `sophisticatedstorage-1.21.1-1.5.91.2127.jar` | `1.5.91` | `Storage` | 23/10/2026 - 15:06 |
| 520 | Sophisticated Storage Create Integration | `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar` | `0.1.21` | `Addons + Storage` | 27/09/2026 - 21:19 |
| 521 | Soul fire'd | `soul-fire-d-neoforge-1.21-6.1.0.jar` | `6.1.0` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Utility & QoL` | 27/09/2026 - 20:57 |
| 522 | Sound Physics: Aeronautics | `sound-physics-remastered-neoforge-1.4.0.1.jar` | `1.4.0.1` | `Create + Utility & QoL` | 27/09/2026 - 22:12 |
| 523 | Sounds | `sounds-2.4.22+lts+1.21.1-neoforge.jar` | `2.4.22+lts` | `Adventure and RPG + Cosmetic + Utility & QoL` | 27/09/2026 - 22:12 |
| 524 | spark | `spark-1.10.124-neoforge.jar` | `1.10.124` | `Server Utility` | 27/09/2026 - 22:12 |
| 525 | Spell Codex: Iron's Spells 'n Spellbooks Addon | `specs_irons_spellbooks-1.6.5.jar` | `1.6.5` | `Addons + Adventure and RPG + Magic + Utility & QoL` | 23/10/2026 - 15:23 |
| 526 | SpellActionbar | `spell_actionbar-1.1.4.jar` | `1.1.4` | `Addons + Adventure and RPG + Magic + Utility & QoL` | 27/09/2026 - 22:12 |
| 527 | StarbuncleMania | `starbunclemania-1.21.1-1.5.8.jar` | `1.5.8` | `Addons + Automation + Energy, Fluid, and Item Transport + Magic` | 27/09/2026 - 22:12 |
| 528 | Streams Reflowing | `StreamsReflowing-1.21.1-neoforge-2.13.5.jar` | `2.13.5` | `Biomes + Cosmetic + World Gen` | 23/10/2026 - 01:43 |
| 529 | Structurize | `structurize-1.0.833-1.21.1-snapshot.jar` | `1.0.833-1.21.1-snapshot` | `Cosmetic + Server Utility + Structures` | 27/09/2026 - 22:12 |
| 530 | Strut Your Stuff (Struts) | `struts-1.3.1.jar` | `1.3.1` | `API and Library` | 27/09/2026 - 22:12 |
| 531 | Stylish Effects | `StylishEffects-v21.1.3-1.21.1-NeoForge.jar` | `21.1.3` | `Cosmetic + Map and Information + Utility & QoL` | 27/09/2026 - 22:12 |
| 532 | Create: Deep Seas - Lava Fix | `submarinefix-1.0.1.jar` | `1.0.1` | `Bug Fixes + Create` | 27/09/2026 - 22:58 |
| 533 | Subtle Effects | `SubtleEffects-neoforge-1.21.1-1.14.3.jar` | `1.14.3` | `Cosmetic + Miscellaneous` | 27/09/2026 - 22:58 |
| 534 | SuperMartijn642's Config Lib | `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar` | `1.1.8` | `API and Library` | 27/09/2026 - 22:58 |
| 535 | SuperMartijn642's Core Lib | `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar` | `1.1.24` | `API and Library` | 27/09/2026 - 22:58 |
| 536 | Supplementaries | `supplementaries-1.21.1-3.9.9-neoforge.jar` | `1.21.1-3.9.9` | `Cosmetic + Miscellaneous + Redstone + Storage` | 22/10/2026 - 22:24 |
| 537 | Tectonic | `tectonic-3.0.28-neoforge-21.1.jar` | `3.0.28` | `World Gen` | 27/09/2026 - 23:18 |
| 538 | TenshiLib (Forge/NeoForge) | `tenshilib-1.21.1-2.3.0.b-neoforge.jar` | `1.21.1-2.3.0.b-neoforge` | `API and Library` | 27/09/2026 - 23:18 |
| 539 | TerraBlender (NeoForge) | `TerraBlender-neoforge-1.21.1-4.1.0.8.jar` | `4.1.0.8` | `API and Library + Biomes + World Gen` | 27/09/2026 - 22:58 |
| 540 | Terralith | `Terralith_1.21.x_v2.6.2.jar` | `2.6.2` | `Biomes + Structures + World Gen` | 27/09/2026 - 23:18 |
| 541 | Create: TFMG Community Edition | `tfmg-1.21.1-1.3.1-community.jar` | `1.3.1-community` | `Addons + Armor, Tools, and Weapons + Create + Ores and Resources + Technology` | 27/09/2026 - 22:58 |
| 542 | ThirstWasFixed | `thirstwasfixed-2.1.6.jar` | `2.1.6` | `Addons + Bug Fixes` | 22/10/2026 - 23:19 |
| 543 | Thirst Was Reclaimed | `ThirstWasReclaimed-1.21.1-3.0.5.jar` | `1.21.1-3.0.5` | `Food` | 28/09/2026 - 00:04 |
| 544 | Corail Tombstone | `tombstone-neoforge-1.21.1-9.5.6.jar` | `9.5.6` | `Adventure and RPG + Magic + Mobs + Utility & QoL` | 28/09/2026 - 00:04 |
| 545 | Tom's Simple Storage Mod | `toms_storage-1.21-2.4.2.jar` | `2.4.2` | `Storage + Technology` | 28/09/2026 - 00:04 |
| 546 | TownTalk | `towntalk-1.2.0.jar` | `1.2.0` | `Addons + Adventure and RPG + Cosmetic + Utility & QoL` | 28/09/2026 - 00:27 |
| 547 | Toxony | `toxony-0.10.7.jar` | `0.10.7` | `Adventure and RPG + Armor, Tools, and Weapons + Genetics + Magic` | 28/09/2026 - 00:27 |
| 548 | Create:Tracks+ | `tracks_plus-1.0.6b6.jar` | `1.0.6b6` | `Create` | 22/10/2026 - 22:24 |
| 549 | Transmog | `transmog-neoforge-1.6.0+1.21.1.jar` | `1.6.0` | `Armor, Tools, and Weapons + Cosmetic` | 28/09/2026 - 00:04 |
| 550 | T.O Magic 'n Extras - Iron's Spells Addon | `traveloptics-4.4.0.1-1.21.1.jar` | `4.4.0.1-1.21.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 28/09/2026 - 00:04 |
| 551 | Sable: True Impact (For Create: Aeronautics) | `true_impact-0.5.7-delta.jar` | `0.5.7-delta` | `Create + Technology` | 28/09/2026 - 00:04 |
| 552 | Tunes n' Tomes: a bards journey | `tunes_n_tomes-1.1.0-HOTFIX.jar` | `1.1.0-HOTFIX` | `Addons + Magic` | 28/09/2026 - 01:09 |
| 553 | UnChipped | `unchipped-1.21-1.2.jar` | `1.21-1.2` | `Addons + Automation + Create + Processing` | 28/09/2026 - 01:09 |
| 554 | Underground Villages, Stoneholm (Fabric/Forge/NeoForge) | `underground_village-neoforge-1.21.1-2.0.jar` | `2.0` | `Biomes + Structures + World Gen` | 28/09/2026 - 01:09 |
| 555 | Uranus | `uranus-3.0-beta.1.jar` | `3.0-beta.1` | `API and Library + Utility & QoL` | 28/09/2026 - 01:09 |
| 556 | Vampirism Iron's Spells Compatibility | `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar` | `1.21.1-0.0.9` | `Addons + Adventure and RPG + Food + Magic` | 23/10/2026 - 14:57 |
| 557 | Vampiric Ageing - A Vampirism Addon | `vampiricageing-1.21-1.4.21.jar` | `1.21-1.4.21` | `Magic` | 28/09/2026 - 01:09 |
| 558 | Vampirism - Become a vampire! | `Vampirism-1.21-1.10.13.jar` | `1.10.13` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 28/09/2026 - 01:09 |
| 559 | Vampirism Integrations | `vampirism_integrations-1.21.1-1.10.2.jar` | `1.10.2` | `Magic + Miscellaneous` | 28/09/2026 - 01:09 |
| 560 | Vanilla Backport | `VanillaBackport-neoforge-1.21.1-1.1.7.10.jar` | `1.1.7.10` | `Armor, Tools, and Weapons + Biomes + Mobs + Ores and Resources + World Gen` | 22/10/2026 - 21:57 |
| 561 | Village Spawn Point | `villagespawnpoint-1.21.1-4.6.jar` | `4.6` | `Adventure and RPG + World Gen` | 28/09/2026 - 01:09 |
| 562 | Vintage Improvenents - SSW Edition | `vintageimprovements-1.21.1-0.0.0.7.jar` | `1.21.1-0.0.0.7` | `Addons + Create` | 28/09/2026 - 02:10 |
| 563 | VS / Sable Hose Connectors | `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar` | `0.1.8` | `Addons + Create + Industrial Craft + Storage + Technology` | 28/09/2026 - 02:10 |
| 564 | Waystones | `waystones-neoforge-1.21.1-21.1.45.jar` | `21.1.45` | `Adventure and RPG + Magic + Player Transport + Server Utility` | 28/09/2026 - 02:28 |
| 565 | Waystones: Sable (Create Aeronautics Addon) | `waystonessable-1.0.7.jar` | `1.0.7` | `Addons + Create` | 28/09/2026 - 02:10 |
| 566 | Wayward Attributes | `wayward_attributes-1.21.1-1.1.1.jar` | `1.1.1` | `API and Library + Cosmetic` | 28/09/2026 - 02:10 |
| 567 | YDM's Weapon Master | `weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar` | `4.2.7` | `Cosmetic` | 28/09/2026 - 02:10 |
| 568 | Weapons of Miracles - epic fight | `WeaponsOfMiracles-2.0.178.jar` | `2.0.178` | `Addons + Adventure and RPG + Armor, Tools, and Weapons` | 28/09/2026 - 02:10 |
| 569 | Create Aeronautics: Weight | `weight-1.2.0.jar` | `1.2.0` | `Addons + Create` | 28/09/2026 - 02:10 |
| 570 | Werewolves - Become a Beast! | `Werewolves-1.21-2.0.3.3.jar` | `2.0.3.3` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 28/09/2026 - 02:10 |
| 571 | Wind's Spellbooks : Iron's Spells 'n Spellbooks Addon | `wind_spellbooks-1.0.5.jar` | `1.0.5` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs + Structures` | 28/09/2026 - 02:10 |
| 572 | WorldWeaver: New Dawn | `worldweaver-21.0.25.jar` | `21.0.25` | `API and Library + Biomes + Utility & QoL + World Gen` | 22/10/2026 - 22:26 |
| 573 | WunderLib: New Dawn | `wunderlib-21.0.10.jar` | `21.0.10` | `API and Library + Utility & QoL` | 23/10/2026 - 15:15 |
| 574 | YetAnotherConfigLib | `yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar` | `3.8.2+1.21.1-neoforge` | `API and Library` | 28/09/2026 - 08:36 |
| 575 | Fundamental Principles - Iron's Spells Addon | `ypfundamentals-1.1.7.1.jar` | `1.1.7.1` | `Addons + Adventure and RPG + Magic + Mobs` | 30/09/2026 - 10:20 |
| 576 | YUNG's API (NeoForge) [1.20.4-1.21.1 ONLY] | `YungsApi-1.21.1-NeoForge-5.1.8.jar` | `1.21.1-NeoForge-5.1.8` | `API and Library + World Gen` | 28/09/2026 - 08:36 |
| 577 | YUNG's Better Caves (Forge/NeoForge) | `YungsBetterCaves-1.21.1-NeoForge-3.1.6.jar` | `1.21.1-NeoForge-3.1.6` | `Adventure and RPG + Biomes + Structures + World Gen` | 28/09/2026 - 08:36 |
| 578 | YUNG's Better Desert Temples (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar` | `1.21.1-NeoForge-4.1.5` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 08:36 |
| 579 | YUNG's Better Dungeons (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterDungeons-1.21.1-NeoForge-5.1.4.jar` | `1.21.1-NeoForge-5.1.4` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 08:36 |
| 580 | YUNG's Better End Island (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar` | `1.21.1-NeoForge-3.1.2` | `Adventure and RPG + Dimensions + Structures + World Gen` | 28/09/2026 - 08:36 |
| 581 | YUNG's Better Jungle Temples (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar` | `1.21.1-NeoForge-3.1.2` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 08:36 |
| 582 | YUNG's Better Mineshafts (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar` | `1.21.1-NeoForge-5.1.1` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 09:00 |
| 583 | YUNG's Better Nether Fortresses (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar` | `1.21.1-NeoForge-3.1.5` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 09:00 |
| 584 | YUNG's Better Ocean Monuments (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterOceanMonuments-1.21.1-NeoForge-4.1.2.jar` | `1.21.1-NeoForge-4.1.2` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 09:00 |
| 585 | YUNG's Better Witch Huts (NeoForge) [1.20.4 - 1.21.1 ONLY] | `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar` | `1.21.1-NeoForge-4.1.1` | `Adventure and RPG + Structures + World Gen` | 28/09/2026 - 09:00 |
| 586 | YUNG's Cave Biomes (Forge/NeoForge) | `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar` | `1.21.1-NeoForge-3.1.1` | `Adventure and RPG + Biomes + Mobs + World Gen` | 28/09/2026 - 09:00 |
| 587 | Zeta | `Zeta-1.1-40.jar` | `1.1-40` | `API and Library` | 28/09/2026 - 09:21 |

## Dossiers históricos/removidos organizados

Estes dossiers existem no catálogo documental, mas **não correspondem a entradas top-level da modlist física atual**. Eles não recebem número físico.

| Nome | Último JAR catalogado | Última versão catalogada | Estado físico atual | Pasta / categorias | Dossier | Última edição |
|---|---|---|---|---|---|---|
| Create Enchantment Industry Plus | `create_enchantment_industry_plus-1.1.1-1.21.1.jar` | `1.1.1` | removido da modlist física atual | `Create + Technology` | `Create + Technology/✅-create-enchantment-industry-plus.md` | 29/09/2026 - 02:33 |

## Divergências de metadata preservadas

- **Copycats+ aeronautics weight (#013):** o artefato/publicação e o filename físico são `1.1.1`, enquanto a metadata interna auditada reporta `1.1.0`. As duas informações permanecem explícitas.
- **Easy NPC: Core (#239):** o JAR físico identifica a distribuição `7.12.1`, mas a metadata runtime da modlist não declara versão. O índice preserva a ausência em vez de inventar metadata.
- **Dungeons Delight (#416):** a distribuição/JAR instalado é `1.5.1`, enquanto a metadata interna auditada reporta `1.5.0`. As duas informações permanecem explícitas.


## Gate de promoção coordenada — Sophisticated (10/10/2026)

**Classificação: PENDENTE / BLOQUEADO PARA PROMOÇÃO FÍSICA.** Esta seção é um **plano técnico de manutenção**, não uma alteração no inventário físico nem aprovação de compatibilidade. A tabela ordinal acima continua refletindo o snapshot `modlist(1).txt` de 27/09/2026. Os seis dossiês abaixo foram atualizados separadamente com pesquisa upstream em 10/10/2026; suas versões/documentos físicos históricos são preservados até conferência de novos JARs.

### Matriz de artefatos: instalado no snapshot × candidato oficial NeoForge 1.21.1

| Nº físico | Componente | JAR no snapshot | JAR público candidato (não instalado / não validado em runtime) |
|---:|---|---|---|
| #515 | Sophisticated JEI Index | `sophisticated_jei_index-1.2.3+1.21.1.jar` | `sophisticated_jei_index-1.2.4+1.21.1.jar` |
| #516 | Sophisticated Backpacks | `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar` | `sophisticatedbackpacks-1.21.1-3.26.9.2195.jar` |
| #517 | Sophisticated Backpacks Create Integration | `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar` | `sophisticatedbackpackscreateintegration-1.21.1-0.2.2.188.jar` |
| #518 | Sophisticated Core | `sophisticatedcore-1.21.1-1.5.1.2341.jar` | `sophisticatedcore-1.21.1-1.5.7.2381.jar` |
| #519 | Sophisticated Storage | `sophisticatedstorage-1.21.1-1.5.91.2127.jar` | `sophisticatedstorage-1.21.1-1.6.2.2159.jar` |
| #520 | Sophisticated Storage Create Integration | `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar` | `sophisticatedstoragecreateintegration-1.21.1-0.1.22.238.jar` |

**Identificação das fontes externas (arquivos específicos):**
- Core: https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/9083874
- Backpacks: https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks/files/9083940
- Backpacks Create Integration: https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-create-integration/files/9074505
- Storage: https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9083992
- Storage Create Integration: https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-create-integration/files/9074616
- Sophisticated JEI Index: https://www.curseforge.com/minecraft/mc-mods/sophisticated-jei-index/files/9089418

O `1.2.4` do JEI Index **não tem changelog público**; não atribuir fix próprio por analogia com Backpacks/JEI. Não confundir builds lançadas para Minecraft `26.x` com releases do perfil `1.21.1`.

### Superfícies de compatibilidade que ultrapassam os seis JARs

O mesmo snapshot registra consumidores/bridges que **não podem ser ignorados** numa substituição coordenada:

- **#054** — Sophisticated Backpacks: Ars Compat / `arssophisticatedcompat-0.3.0.jar`: **correção oficial de identificação 13/10**; Source, Sourcelink, Potion Jar e reparo pertencem à **mochila Sophisticated Backpacks**, não ao armazenamento em blocos. O projeto Storage: Ars Compat usa outro JAR e não está comprovado como instalado.
- **#160** — Create: Sophisticated Backpacks Compat / `create_sophback_compat-1.0.jar`: coexistência com a bridge oficial Backpacks Create; avaliar double-handling de inventário e hooks de Create sem presumir redundância ou removê-lo automaticamente.
- **#514** — Thirst Upgrade for Sophisticated Backpacks / `sophisticated-thirst-upgrade.jar`: persistência e ativação de upgrade após alteração de APIs/Core.
- **Create** (snapshot 6.0.10), **JEI** (snapshot 19.56.0.440; revisão upstream distinta), **Ars Nouveau**, **Tom's Storage** e demais integrações de armazenamento presentes: validar APIs, recipe transfer e read/write de inventory em contraptions.

### Ordem operacional obrigatória antes da promoção

1. **Recaptura física:** obter novo dump de `mods/` e, se possível, os seis JARs atuais para registrar nome, SHA-256, `META-INF/neoforge.mods.toml`/`mods.toml`, mod IDs, versões de runtime, lados cliente/servidor e intervalos de dependências. Confirmar ausência de versões duplicadas. Sem isso não alterar linhas #515–#520 do índice.
2. **Validação de candidatos:** obter todos os seis binários oficiais exatos; verificar seus SHA-256/metadata. Mapear, por arquivo, as restrições de Core/Backpacks/Storage/Create/JEI e comparar com os consumidores #054, #160 e #514. O dossiê de Storage indica exigência de Core **≥1.5.2** para a linha 1.6.x, mas o limite exato de cada candidato deve ser confirmado no próprio artefato e não inferido desta nota.
3. **Staging em cópia isolada:** backup verificável de `world/`, `config/`, `serverconfig/`, `defaultconfigs/` e scripts. Substituir **apenas na cópia** o conjunto compatível de Core, Storage, Backpacks e bridges. O JEI Index pode requerer validação separada porque é de outro mantenedor, mas deve permanecer incluído no teste integrado.
4. **Gate funcional:** iniciar dedicated server e cliente NeoForge 1.21.1 / Java 21; validar login/relogin; migrar cópia de mundo com itens contendo NBT/data components; verificar linked barrel/chest/backpack/controller, Ender Linker, rebind, multi-jogador concorrente, storage com tank/memory/upgrades, save/restart e chunk unload/reload. Com Create testar contraptions montadas, em movimento e desmontadas, incluindo links entre endpoints, transferências e upgrade tick. Com JEI Index testar recipe transfer, Tom's Storage, equip/unequip e ausência de contagem dupla. Comparar quantidade/identidade de stacks antes e depois e procurar *ghost slots*, duplicação e perda de dados.
5. **Decisão fail-closed:** se faltar metadata, dependência ou aprovação de runtime, **não promover** nem retirar JARs originais; registrar o bloqueio. Somente após teste real e backup aprovado substituir atomicamente os seis JARs necessários, remover versões antigas sem duplicatas, regenerar modlist e hashes, atualizar o nome dos dossiês versionados **de acordo com a nova instalação**, corrigir referências e refazer QC.
6. **Rollback:** qualquer desync, erro de load, alteração silenciosa de item data, dupe/loss ou incompatibilidade de bridge exige retorno **conjunto** dos JARs e da cópia correspondente do mundo — não reabrir dados migrados em runtime antigo sem restauração coerente.

**Estado desta nota em 10/10/2026:** verificação de fontes públicas e consistência do snapshot documental; **nenhum JAR baixado/instalado, nenhum novo dump físico obtido, nenhuma migração de mundo ou teste de runtime executado**. A promoção de versões permanece pendente.

## Matriz de atualizações externas — 13/10/2026 (solicitação de 30 mods)

> **Escopo documental, NÃO inventário físico novo.** Estas 30 entradas foram reexaminadas contra publicações oficiais NeoForge/Minecraft **1.21.1**. Versões na coluna 'Candidato upstream' são publicações de referência, não instalação, teste, aprovação nem substituição do JAR. A tabela física ordenada de 587 entradas e o registro de duas adições pós-snapshot permanecem imutáveis até recebimento de novo dump. Arquivos `.md` mantêm `✅-` e versão **histórica física** no nome; o sinal `✅` existente não certifica novos upgrades. A seção abaixo **supersede os candidatos datados do gate Sophisticated de 10/10/2026**, preservado acima como histórico.

| Ordem da solicitação | Projeto | Dossiê técnico | Última evidência física (snapshot histórico) | Candidato upstream NeoForge 1.21.1 / conclusão | Decisão e gate |
|---:|---|---|---|---|---|
| 01 | Easy NPC (bundle) | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library/%E2%9C%85-easy-npc-bundle%20v7.12.1.md) | `7.12.1` | `7.15.0` | 7.15.0 BLOQUEADO: EME 2.4.0 presente não atende `[2.5.0,3.0.0)`; 7.14.0 CANDIDATO CONDICIONAL: Core/UI declaram EME opcional `[2.4.0,3.0.0)`; JARs/QA pendentes |
| 02 | Create: Teleporters | [Abrir `.md`](./Create%20%2B%20Energy%2C%20Fluid%2C%20and%20Item%20Transport%20%2B%20Technology/%E2%9C%85-create-teleporters-remastered%20v2.0.2b.md) | `2.0.2b (filename); metadata 2.0.2` | `2.0.2b` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 03 | Sophisticated Backpacks Create Integration | [Abrir `.md`](./Addons%20%2B%20Storage/%E2%9C%85-sophisticated-backpacks-create-integration%20v0.2.0.md) | `0.2.0` | `0.2.3.194` | PENDENTE: Backpacks ≥3.26.10 + Create ≥6.0.7 no source, linked storage/QA |
| 04 | Sophisticated Core | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-sophisticated-core%20v1.5.1.md) | `1.5.1` | `1.5.11.2394` | PENDENTE: Core 1.5.11 candidato satisfaz piso 1.5.9 de Backpacks/Storage; ABI/QA |
| 05 | VS / Sable Hose Connectors | [Abrir `.md`](./Addons%20%2B%20Create%20%2B%20Industrial%20Craft%20%2B%20Storage%20%2B%20Technology/%E2%9C%85-vs-sable-hose-connectors%20v0.1.8.md) | `0.1.8` | `0.2.2` | PENDENTE: novas interações sem wrench, config e transferência atômica |
| 06 | Placebo | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-placebo%20v9.9.2.md) | `9.9.2` | `9.9.4` | PENDENTE: consumer linkage com Apotheosis/Apothic |
| 07 | Ace's Spell Utils | [Abrir `.md`](./Addons%20%2B%20API%20and%20Library/%E2%9C%85-aces-spell-utils%20v1.2.7.2-1.21.1.md) | `1.2.7.2-1.21.1` | `1.2.7.2-1.21.1` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 08 | Supplementaries | [Abrir `.md`](./Cosmetic%20%2B%20Miscellaneous%20%2B%20Redstone%20%2B%20Storage/%E2%9C%85-supplementaries%20v1.21.1-3.9.9.md) | `1.21.1-3.9.9` | `4.0.1` | PENDENTE: migração entre linhas 3.x/4.x; Moonlight/Amendments |
| 09 | AzureLib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-azurelib%20v3.1.11.md) | `3.1.11` | `3.1.19 Release; 3.1.20-alpha.1 pré-release` | PENDENTE: alpha não equivale a estabilidade |
| 10 | Easy NPC: Core | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library/%E2%9C%85-easy-npc%20v7.12.1.md) | `7.12.1 (JAR); metadata sem versão` | `7.15.0` | 7.15.0 BLOQUEADO: EME 2.4.0 abaixo do mínimo opcional `[2.5.0,3.0.0)`; 7.14.0 CANDIDATO CONDICIONAL: EME opcional `[2.4.0,3.0.0)`, sem validação binária/runtime |
| 11 | Create Aeronautics: Gadgets & Gizmos | [Abrir `.md`](./Addons%20%2B%20Create/%E2%9C%85-create-aeronautics-gadgets-and-gizmos%20v1.2.2.md) | `1.2.2` | `1.2.10` | PENDENTE: v1.2.9 desaconselhada pelo upstream; revalidar bundle e lógica |
| 12 | Reliquified Artifacts | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Magic/%E2%9C%85-reliquified-artifacts%20v1.0.8.md) | `1.0.8` | `1.0.9 Beta` | PENDENTE: Relics/Artifacts/Curios e efeitos sem duplicação |
| 13 | Minecolonies: Epiched (Epic Colonies) | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Cosmetic/%E2%9C%85-epic-colonies%20v21.0.8.md) | `21.0.8` | `21.0.9` | PENDENTE: novos movesets EpicFight Nightfall + Extra; animações cidadãos |
| 14 | Easy Model Entities | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library%20%2B%20Automation%20%2B%20Mobs%20%2B%20Utility%20%26%20QoL/%E2%9C%85-easy-model-entities%20v2.4.0.md) | `2.4.0` | `2.4.0 para NeoForge 1.21.1` | REVALIDADO (source 2.5.0 1.21.1, binário não localizado): EME 2.4.0 NeoForge 1.21.1; incompatível com faixa de Core/UI 7.15.0 quando presente |
| 15 | Polymorph+ | [Abrir `.md`](./Miscellaneous%20%2B%20Utility%20%26%20QoL/%E2%9C%85-polymorph-plus%20v1.3.1%2B1.21.1.md) | `1.3.1+1.21.1` | `1.3.2+1.21.1` | PENDENTE: receitas/GUI/JEI/KubeJS |
| 16 | Eidolon: Repraised | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Magic/%E2%9C%85-eidolon-repraised%20v0.5.0.2.md) | `0.5.0.2` | `0.5.0.2` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 17 | Fragmentum [NeoForge] | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-fragmentum%20v2.4.4.md) | `2.4.4` | `5.3.0` | BLOQUEADO: mudança incompatível de API/Kotlin/registries desde 5.0 |
| 18 | ThirstWasFixed | [Abrir `.md`](./Addons%20%2B%20Bug%20Fixes/%E2%9C%85-thirst-was-fixed%20v2.1.6.md) | `2.1.6` | `2.1.6` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 19 | Polytone | [Abrir `.md`](./Cosmetic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-polytone%20v1.21-4.4.0.md) | `1.21-4.4.0` | `5.1.1` | PENDENTE: voxel volume/Veil, shaders, packs; compat de Amendments |
| 20 | Just Enough Items (JEI) | [Abrir `.md`](./API%20and%20Library%20%2B%20Map%20and%20Information/%E2%9C%85-just-enough-items%20v19.56.0.440.md) | `19.56.0.440` | `19.64.0.453 Beta` | PENDENTE: addon APIs, recipe/indexing e integração |
| 21 | Subtle Effects | [Abrir `.md`](./Cosmetic%20%2B%20Miscellaneous/%E2%9C%85-subtle-effects%20v1.14.3.md) | `1.14.3` | `1.15.1` | PENDENTE: partículas e correções de servidor específicas de 1.21.1 |
| 22 | Collective | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-collective%20v8.39.md) | `8.39` | `8.47` | PENDENTE: changes específicas do target não isoladas; Serilum consumers |
| 23 | Aeronautics Player Tilt | [Abrir `.md`](./Addons%20%2B%20Cosmetic%20%2B%20Create/%E2%9C%85-aeronautics-player-tilt%20v0.2.0.md) | `0.2.0` | `0.2.0` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 24 | Sophisticated Backpacks | [Abrir `.md`](./Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Storage/%E2%9C%85-sophisticated-backpacks%20v3.26.3.md) | `3.26.3` | `3.26.12.2208` | PENDENTE: Core ≥1.5.9, Mob Catcher upgrade/linked groups, anti-dupe |
| 25 | Easy NPC: Config UI | [Abrir `.md`](./Utility%20%26%20QoL/%E2%9C%85-easy-npc-config-ui%20v7.12.1.md) | `7.12.1` | `7.15.0` | 7.15.0 BLOQUEADO: EME 2.4.0 instalada historicamente não satisfaz `[2.5.0,3.0.0)`; 7.14.0 CANDIDATO CONDICIONAL: EME opcional `[2.4.0,3.0.0)`, dependências/UI e testes pendentes |
| 26 | Crystal Chronicles — Iron's Spells | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Dimensions%20%2B%20Magic/%E2%9C%85-crystal-chronicles%20v0.1.3-alpha.md) | `0.1.3-alpha` | `0.1.4-alpha` | PENDENTE: versão alpha, magia e worldgen |
| 27 | BlockUI | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-blockui%20v1.0.212-1.21.1-snapshot.md) | `1.0.212-1.21.1-snapshot` | `1.0.212-1.21.1-snapshot` | REVALIDADO: possível republicação; conferir file ID/SHA, sem bump |
| 28 | Sophisticated Storage Create Integration | [Abrir `.md`](./Addons%20%2B%20Storage/%E2%9C%85-sophisticated-storage-create-integration%20v0.1.21.md) | `0.1.21` | `0.1.23.246` | PENDENTE: Storage ≥1.6.3 + Create ≥6.0.7 no source, linked groups/QA |
| 29 | Integrated API | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library%20%2B%20Structures%20%2B%20World%20Gen/%E2%9C%85-integrated-api%20v1.8.2.md) | `1.8.2` | `1.9.0` | PENDENTE: consumers Integrated Structures e registries worldgen |
| 30 | Sophisticated Storage | [Abrir `.md`](./Storage/%E2%9C%85-sophisticated-storage%20v1.5.91.md) | `1.5.91` | `1.6.5.2176` | PENDENTE: 1.6.5 corrige crash sem Backpacks; Core ≥1.5.9, groups/QA |

> **Auditoria de rastreabilidade — 13/10/2026:** os 30 links da coluna **Dossiê técnico** foram reconciliados com caminhos existentes na `main`; cada filename `✅-... vX.md` retém a **última evidência física histórica**, não a release candidata. O endereço CurseForge `easy-npc/files/9151672` citado nos dossiês Easy NPC corresponde a um JAR **Fabric**: ele é fonte do changelog comum, mas **NÃO** é o artefato NeoForge. Verificar o JAR NeoForge 1.21.1 e manter o bloqueio EME 2.5.0+ até publicação compatível comprovada.

### Prioridade: dependências e conteúdo novo que exigem validação

- **Easy NPC #01/#10/#25 ↔ Easy Model Entities #14:** NeoForge 1.21.1 publica Bundle/Core/Config UI **7.15.0**, porém o changelog do Core declara **Easy Model Entities 2.5.0+**, enquanto a última EME **NeoForge 1.21.1** identificada é **2.4.0**. A 2.5.0 localizada destina-se a **Minecraft 26.1.2**, não podendo substituir 2.4.0 no perfil. **PROMOÇÃO BLOQUEADA** até manifesto/fallback ou build compatível demonstrada. **Novidades reais 7.15.0:** ações `Jump`, `Assist Owner` e `Follow Nearest Player`; inventário de NPC com item pickup e death drops; `Sit on Shoulder`, boss bar, animação `Hold`, preset Bank Teller, `/easy_npc debug core loglevel` e novos métodos de API. **Fixes 7.15.0:** lock por diálogo, NPCs deletados/duplicados ressurgindo, memória/owner/UI, sons via resource pack, patch Epic Fight do Wither Skeleton NPC, default spawner limit e retenção de backup (30 dias). **Não são novidades 7.15.0:** `Set Home Here`, `Reset Home` e export/import com identidade e posição (**7.12.0**, já anterior ao JAR físico 7.12.1); macros de diálogo e protocolo novo são 7.13.0, late-viewer pose/trading/sound sync é 7.14.0. Fonte primária: https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md ; EME: https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all.
- **Gadgets & Gizmos #11:** a versão 1.2.9 é marcada pelo autor '**DO NOT USE! Causes crashes**'. Avaliar **1.2.10** diretamente: Counter, reset Do N, numeric compare int/float, Watch/Send Chat, stop sound, null-level guard com Supplementaries. Smoke de graphs, permissions/chat em sublevels, bundle e estado persistente. Fonte: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/9141062.
- **Fragmentum #17:** release **5.0.0** anuncia *breaking changes*, migração Kotlin/Modern Stonecutter; 5.3.0 acrescenta `AssetRegistry` com resource reload, `ConfigScreenFactory.stringFieldOption` e `Deferred`/`Lazy`. Revalidar consumidores/APIs e JarJar antes do salto **2.4.4 → 5.3.0**. Fonte: https://www.curseforge.com/minecraft/mc-mods/fragmentum-neoforge/files/9150745.
- **VS/Sable Hose Connectors #05:** 0.2.2 altera conexão sem wrench, wrench rotate/sneak remove e oferece config para recuperar interação antiga; conferir transfers exactly-once, connector state e input conflicts. Fonte: https://www.curseforge.com/minecraft/mc-mods/vs-hose-connectors/files/9147235.
- **Sophisticated #03/#04/#24/#28/#30:** não atualizar Core, Backpacks, Storage ou bridges Create isoladamente; conferir consumidor **Sophisticated JEI Index**, addon extra `create_sophback_compat-1.0.jar` e Ars Compat. Exigir backup de mundo, suite anti-duplicação de inventário, reopen/save/restart, chunk unload e teste com contraptions. O gate histórico de 10/10 acima foi substituído pelas versões deste lote.
- **Supplementaries #08 / Polytone #19 / AzureLib #09:** mudanças em generated resources, shaders, voxel volume e render/animation; diferenciar release 3.1.19 de alpha 3.1.20. O novo Polytone 5.1.1 modifica voxel volume/compat Veil. Testar resource reload e coexistência de Amendments/Moonlight.
- **Minecolonies Epiched #13:** 21.0.9 amplia movesets quando **EpicFight Nightfall + EpicFight Extra** estão juntos; corrige meshes de Settlers. Atribuição restrita aos addons realmente presentes.
- **Subtle Effects #21:** 1.15.1 melhora partículas (potted flowers/withering, copper scraping/unwaxing, mud), e corrige crash de splashes em servidor. Fixes identificados pelo autor como 26.x não são automaticamente aplicáveis a NeoForge 1.21.1.
- **Revalidação sem mudança de release:** #02 Create Teleporters, #07 Ace's Spell Utils, #14 Easy Model Entities, #16 Eidolon Repraised, #18 ThirstWasFixed, #23 Aeronautics Player Tilt e #27 BlockUI. **BlockUI republicado com mesmo nome/versão** exige SHA/file ID para diagnosticar se bytes mudaram; não inventar bump.

### Gate especializado — novas superfícies de gameplay Easy NPC 7.15.0 (13/10/2026)

1. **Inventário/pickup/death drops:** inventário serializado, conservação de itens (nem dupe nem loss), interação com loot, terceiros, unload/restart e jogadores simultâneos; não presumir configuração não publicada.
2. **IA/objetivos:** `Jump`, `Assist Owner` e `Follow Nearest Player` contra pathfinding, prioridade de tarefas, distância, claim/fake player, logout do owner e situações sem target.
3. **Apresentação/UI:** `Sit on Shoulder`, boss bar e `Hold` animation contra EME, EMF/ETF, Epic Fight, shaders e recursos; Config UI é editor, não provider do estado autoritativo.
4. **API/servidor:** revisar novos métodos de addons no source exato antes de afirmar assinaturas; `/easy_npc debug core loglevel` exige auditoria de permissões/log volume; `storage.cfg` e retenção de backups 30 dias exigem backup próprio do pack.
5. **Dependência:** validar `neoforge.mods.toml` no Core alvo; requisito declarado EME 2.5.0+ só foi encontrado em artefato para Minecraft 26.1.2. Não desativar o bloqueio com base em versões para outro Minecraft ou apenas por a EME ser opcional em versões mais antigas.
6. **Estado verificado:** nenhuma atualização física da família foi executada. Os `.md` continuam em `✅-... v7.12.1.md` e EME em `✅-easy-model-entities v2.4.0.md`, conforme última evidência física histórica. O CI do repositório não certifica gameplay in-game.

### Auditoria do manifesto NeoForge — EME opcional com faixa obrigatória (13/10/2026)

**Nova evidência oficial lida no source**, complementando e prevalecendo sobre as hipóteses de manifesto ainda não inspecionado das seções históricas acima:

- `easy_npc` Core 7.15.0 declara `modId = "easy_model_entities"`, `type = "optional"`, `versionRange = "[2.5.0,3.0.0)"`, `ordering = "AFTER"`, `side = "BOTH"`.
- `easy_npc_config_ui` 7.15.0 declara a **mesma** entrada opcional/faixa para EME; exige Core com declaração `mandatory = true` e range baseado na versão empacotada. O Bundle declara Core + Config UI como dependências necessárias.
- A documentação **NeoForge 1.21.1** esclarece que `optional` **não exige presença**, mas **valida compatibilidade de versão se o mod estiver presente**. Consequência: EME 2.4.0 da última modlist física **não satisfaz** o contrato de Core/UI 7.15.0; **BLOQUEADO** instalar a tríade 7.15.0 por atualização isolada. A ausência de EME em ambiente descartável é um cenário de teste, **não uma autorização de remover a biblioteca do modpack**, pois recursos/mundos já podem depender dela.
- Versão EME 2.5.0 publicada para **Minecraft 26.1.2** não é candidata válida para 1.21.1. A última NeoForge 1.21.1 encontrada em CurseForge/Modrinth continua 2.4.0. Como o campo de versão **runtime** dessa entrada física é histórico e aparece vazio, o reconhecimento de versão pelo NeoForge também deve ser checado em JAR e logs.
- **Divergência upstream no Bundle:** `neoforge.mods.toml` ainda descreve inclusão por *Jar-in-Jar*, porém `bundle/NeoForge/build.gradle` declara Core e Config UI via `runtimeOnly` para dev e como projetos obrigatórios na publicação, sem tarefa JarJar visível. A composição real do JAR publicado **não foi inspecionada**. Não presumir Bundle = JAR único completo ou Bundle = declaração somente com certeza; validar `META-INF/jarjar/`, `META-INF/jars/`, mod IDs top-level, metadata e ausência de duplicatas antes de qualquer instalação.
- **Estado documental:** 30 posições de atualização upstream ficam preservadas, bem como os filenames com `✅-` e versões **físicas históricas**. Nenhum upgrade, remoção, backup ou teste in-game ocorreu nesta auditoria. O único avanço é o gate de dependência, agora sustentado por manifests e documentação de loader oficiais.

**Fontes do source:** [Core 1.21.1 `neoforge.mods.toml`](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [Config UI 1.21.1 `neoforge.mods.toml`](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [Bundle 1.21.1 `neoforge.mods.toml`](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [Bundle build.gradle](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/build.gradle), [NeoForge 1.21.1 Mod Files](https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/), [Easy Model Entities CurseForge](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all?version=1.21.1).

### Condições para alterar a modlist física e os filenames

1. Recolher dump atual da pasta `mods/` com caminho, filename, metadados internos, hashes e ordem, incluindo verificação de dependências JarJar e os módulos Easy NPC.
2. Separar versão **publicada**, **compatível** e **instalada**; não copiar automaticamente a coluna de publicação para a coluna de instalação. Bloqueios e prereleases/alphas/betas exigem decisão e smoke test explícitos.
3. Somente depois de comprovar substituição física, atualizar as 587 posições ou reordenar segundo novo dump e então renomear os `.md` para `✅-<mod> v<versão física>.md`, incluindo links e contadores. Até lá os 30 arquivos preservam seus nomes canônicos já versionados.
4. CI do repositório valida a documentação/código de integração do repositório, **não substitui** os smoke tests do modpack real para novas versões físicas.

**Fontes detalhadas, changelogs e matrizes individuais:** seções `13/10/2026` dos 30 dossiês respectivos; estes registros preservam explicitamente ausência de changelog nas releases em que o delta não pôde ser identificado com segurança.


### Follow-up de versões disponíveis no source — rota intermediária 7.14.0 (13/10/2026)

**Evidência oficial adicional:** Easy NPC no [commit upstream `2c7fb934c6626224220227e6db5150edd891cdd3`](https://github.com/MarkusBordihn/BOs-Easy-NPC/commit/2c7fb934c6626224220227e6db5150edd891cdd3) declara `version=7.14.0` para Minecraft `1.21.1`; os manifestos do [Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml) e da [Config UI](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml) exigem EME **apenas se presente**, na faixa **`[2.4.0,3.0.0)`**. O [`gradle.properties` 7.14.0](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/core/gradle.properties) indica explicitamente `neoforge_easy_model_entities_version=KIk2E6sP` — [EME 2.4.0 1.21.1 NeoForge oficial](https://modrinth.com/mod/easy-model-entities/version/KIk2E6sP). A 7.14.0 trouxe correções de custom poses, atributos, trades e sons sincronizados com observadores tardios (changelog oficial).

**Novidade EME:** o [source upstream `7305984c57845b97d0190bfc6d4b3e0363ca5aaf`](https://github.com/MarkusBordihn/BOs-Easy-Model-Entities/commit/7305984c57845b97d0190bfc6d4b3e0363ca5aaf) da branch **Minecraft 1.21.1** já marca `version=2.5.0` em `gradle.properties`, e seu `CHANGELOG.md` já traz a 2.5.0. **Nenhuma release/binário NeoForge 1.21.1 2.5.0 foi comprovada nas listagens oficiais até esta consulta.** A 2.5.0 publicada para 26.1.2 não é um substituto para o pack 1.21.1.

**Gate não alterado:** **7.15.0 + EME 2.4.0 = BLOQUEADO** por `[2.5.0,3.0.0)`. **7.14.0 + EME 2.4.0 = CANDIDATO CONDICIONAL**, somente compatibilidade declarativa no source, sem validação dos JARs nem de runtime. **7.15.0 + EME 2.5.0 1.21.1 = PENDENTE** de distribuição e QA. Nenhuma promoção física, alteração dos 30 registros do snapshot, renomeação de dossiê ou instalação foi efetuada. Antes de qualquer troca: manifests reais, JarJar, hashes, identidade de mod IDs, backup de mundos e NPCs, paridade cliente/servidor, boot e regressões de persistência e renderização.


## Auditoria coordenada Sophisticated — 13/10/2026, após PR #892

> **Autoridade instalada preservada:** as posições físicas #516–#520 continuam sendo o último snapshot `modlist(1).txt`; não foi obtido dump novo. Os JARs e filenames `✅-...` históricos permanecem inalterados. Valores seguintes são publicações verificadas para **Minecraft 1.21.1 / NeoForge**, NÃO instalações nem certificação de compatibilidade in-game.

| Item | Versão/runtime do último snapshot | Candidato publicado | Dependências do source oficial, após expansão da metadata | Decisão |
|---|---|---|---|---|
| Core (#518) | `1.5.1` / `1.5.1.2341` | `1.5.11.2394` | NeoForge ≥21.1.229, Minecraft 1.21.1 | PENDENTE: consumidores e migração |
| Backpacks (#516) | `3.26.3` / `3.26.3.2158` | `3.26.12.2208` | **Core required ≥1.5.9** | PENDENTE: restrições de upgrades/dupe |
| Storage (#519) | `1.5.91` / `1.5.91.2127` | **`1.6.5.2176`** | **Core required ≥1.5.9**; Backpacks ≥3.26.11 é dependência de *build*, não hard dependency no manifesto-template | PENDENTE: linked groups/migração |
| Backpacks Create (#517) | `0.2.0` / `0.2.0.168` | `0.2.3.194` | **Backpacks required ≥3.26.10; Create required ≥6.0.7** | PENDENTE: contraptions, state e sync |
| Storage Create (#520) | `0.1.21` / `0.1.21.209` | `0.1.23.246` | **Storage required ≥1.6.3; Create required ≥6.0.7** | PENDENTE: contraptions, state e sync |

**Método/limite:** ranges extraídos da combinação `gradle.properties`, `src/main/templates/META-INF/neoforge.mods.toml` e `build.gradle` dos sources 1.21.x **pinados**: [Core](https://github.com/P3pp3rF1y/SophisticatedCore/tree/400660f2d4e6165076977b09e66cae26a7f2dfd3), [Backpacks](https://github.com/P3pp3rF1y/SophisticatedBackpacks/tree/974d5e1d74fdac91b20b026407b8ea5ba0e7ebc4), [Storage](https://github.com/P3pp3rF1y/SophisticatedStorage/tree/bba2c29dfa47cacb29c0520d6ccd3736878c24aa), [Backpacks Create](https://github.com/P3pp3rF1y/SophisticatedBackpacksCreateIntegration/tree/8a02ea01adfcea2c136e1cd4a713681a116a608b), [Storage Create](https://github.com/P3pp3rF1y/SophisticatedStorageCreateIntegration/tree/909b0dd1d9a8585d57a71c279f83ec8c378d2091). O build transforma `[1.21.1-1.5.9,1.21.2)` em `[1.5.9,)`; analogamente os ranges de bases/bridges. Todos miram Minecraft `[1.21.1,1.21.2)` e NeoForge `[21.1.229,)`. Loader histórico `21.1.250` e Create histórico `6.0.10` atendem esses pisos **apenas numericamente**. Ainda não foram extraídos os manifests reais dos cinco JARs publicados.

**Correções publicadas verificadas:** [Storage 1.6.4](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9151544) permite grupo de storage linked compartilhado entre Backpacks e Storage e corrige Controller deposit perdendo item após chunk unload; a [Storage 1.6.5](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9154831) corrige crash **na ausência de Backpacks**, nova release alvo de 13/10, substituindo a referência da matriz anterior a 1.6.4; [Backpacks 3.26.12](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks/files/9154769) corrige bypass das restrições de remoção do Mob Catcher upgrade via linked storage; ambas bridges registram suporte a grupos compartilhados ([Backpacks Create 0.2.3](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-create-integration/files/9151543), [Storage Create 0.1.23](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-create-integration/files/9151604)). [Core 1.5.11](https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/all?version=1.21.1) foi publicado para NeoForge 1.21.1; não foi isolado seu changelog granular.

**Cross-check obrigatório antes de promoção:** #515 Sophisticated JEI Index `1.2.3`; #160 Create Sophisticated Backpacks Compat `1.0` (risco de double-handling com bridge oficial); #054 Ars Sophisticated Compat `0.3.0`; #514 Sophisticated Thirst Upgrade `0.1.8`. Confirmar metadados e ABI desses consumidores. Não inferir ausência de conflito apenas por satisfazer ranges e não remover addons arbitrariamente.

**Gate físico e funcional PENDENTE:** baixar os cinco JARs de fonte oficial, verificar SHA-256, mod IDs, versão expandida, JarJar e manifests NeoForge efetivos; cópia e backup consistentes do mundo; migração de itens com NBT/data components, contas de saldo em linked groups Backpacks↔Storage, Controller após chunk unload, Ender Linker e memória de slots; upgrade Mob Catcher com tentativa de bypass por link, tank/crafting/Pickup/Magnet; Create assembly/disassembly, transfers exactly once, unload/reload, save/restart, GUI sync com dois jogadores, conexões após reiniciar e rollback. O teste "Storage sem Backpacks" cabe **somente em perfil descartável**, não em remoção física do pack. **Nenhum download de JAR, instalação física ou teste in-game foi realizado nesta auditoria.**

**Decisão técnica:** os cinco candidatos são **coerentes quanto a mínimos declarativos do source**; a promoção coordenada permanece **PENDENTE** até validação binária, consumidores adicionais, mundo e runtime. CI do repositório não certifica comportamento dos JARs publicados.


## Auditoria de consumidores Sophisticated — 13/10/2026 (lote pós-PR #893)

**Autoridade física preservada:** os quatro JARs abaixo continuam na modlist histórica, sem novo dump físico, instalação ou validação de boot. As versões externas são **candidatas/documentação**, não upgrades efetuados.

| # | Componente | Última evidência física | Publicação oficial NeoForge 1.21.1 | Decisão para a migração Sophisticated |
|---|---|---|---|---|
| #054 | Sophisticated Backpacks: Ars Compat | `arssophisticatedcompat-0.3.0.jar` / runtime 0.3.0 | [Backpacks Ars Compat 0.3.0 — arquivo coincidente, CurseForge 8653384](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384) | **IDENTIDADE DOCUMENTAL CORRIGIDA POR FILENAME; HASH/ABI PENDENTES.** Storage Ars Compat é outro mod/File ID 8655579 |
| #160 | Create: Sophisticated Backpacks Compat | `create_sophback_compat-1.0.jar` / runtime version VAZIA, display name defeituoso | [1.0, 03/08/2025](https://www.curseforge.com/minecraft/mc-mods/create-sophisticated-backpacks-compat/files/all) | **NÃO VERIFICADO** com Backpacks novos; recipes Create e KubeJS precisam de comparação |
| #514 | Sophisticated Thirst Upgrade | `sophisticated-thirst-upgrade.jar` / runtime 0.1.8 | [0.1.8, file 8102344](https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst/files/8102344) | **PENDENTE:** addon feito para Thirst Was Taken; pack usa Reclaimed 3.0.5, autor não promete port futuro |
| #515 | Sophisticated JEI Index | `sophisticated_jei_index-1.2.3+1.21.1.jar` / runtime 1.2.3 | [1.2.4+1.21.1, file 9089418, sem changelog](https://www.curseforge.com/minecraft/mc-mods/sophisticated-jei-index/files/9089418) | **CANDIDATO DECLARATIVO**, mas sem verificação dos manifests do JAR, mixins, recipe transfer ou tests |

### Evidências oficiais e classificação de compatibilidade

**JEI Index:** o [source oficial `fa06464034db2c86ce483008630c4e9690bbc91c`](https://github.com/AmicBeam/Sophisticated-JEI-Index/tree/fa06464034db2c86ce483008630c4e9690bbc91c) contém projeto específico `versions/1.21.1`; o [Gradle](https://github.com/AmicBeam/Sophisticated-JEI-Index/blob/fa06464034db2c86ce483008630c4e9690bbc91c/versions/1.21.1/gradle.properties) prevê NeoForge `[21.1.238,)`, Core `[1.4.89,)`, Backpacks `[3.25.78,)` e JEI `[19.44.0.403,)` requeridos no [template efetivo `generateModMetadata`](https://github.com/AmicBeam/Sophisticated-JEI-Index/blob/fa06464034db2c86ce483008630c4e9690bbc91c/versions/1.21.1/src/main/templates/META-INF/neoforge.mods.toml). Isso é numericamente coerente com Core 1.5.11, Backpacks 3.26.12, JEI 19.56.0.440 e NeoForge 21.1.250 (este último inventário), **sem comprovar compatibilidade runtime**. O arquivo `versions/1.21.1/META-INF/neoforge.mods.toml` separado tem versão 1.0.2 obsoleta e não deve ser confundido com o manifesto gerado/empacotado. O [config source](https://github.com/AmicBeam/Sophisticated-JEI-Index/blob/fa06464034db2c86ce483008630c4e9690bbc91c/versions/1.21.1/src/main/java/com/sbjeiindex/config/SBJEIIndexConfig.java) tem `enableTransferWithoutUpgrade=false` (se true dispensa upgrade para backpack equipado) e `maxEnabledBackpacksScanned=0` (ilimitado). Conferir existência desses recursos no JAR físico 1.2.3. A [README](https://github.com/AmicBeam/Sophisticated-JEI-Index/blob/fa06464034db2c86ce483008630c4e9690bbc91c/README.md) exclui **nested backpacks** como fontes de JEI na versão 1.21.1; Inception só é listado na 26.1.2. Tom's Storage tem integração via **JEI, não EMI**.

**Create Sophback Compat versus bridge oficial:** a [descrição do projeto de Blizzor](https://www.curseforge.com/minecraft/mc-mods/create-sophisticated-backpacks-compat) trata **recipes de máquinas Create** (Upgrade Base, Auto Smelting Upgrade, Netherite Backpack na [galeria](https://www.curseforge.com/minecraft/mc-mods/create-sophisticated-backpacks-compat/gallery)); `sophisticatedbackpackscreateintegration` fornece capacidades de mochilas em **contraptions/linked inventories**. São **complementares pelos escopos publicados**, não duplicatas globais. Revisar receitas conflitantes, mas **não remover automaticamente** #160. Metadata do #160 com display name e runtime vazios permanece registrada como defeito, sem correção artificial.

**Ars:** [0.3.0 upstream](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-ars-compat-sophisticated/files/8655579) declara Source Storage, Source Link, Potion Jar e Enchanter's Upgrade; requisitos Ars Nouveau, Sophisticated Storage e Core. O autor informa que build/metadata foram checados, mas não todos os smoke tests in-game. O arquivo físico com nome diferente da publicação exige prova binária antes de equipará-los. Source pertence a Ars; storage e slots a Sophisticated; a bridge não deve gerar Source/reparar duas vezes em linked group.

**Hidratação:** [arquivo oficial do addon](https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst/files/8102344) mantém `sophisticated-thirst-upgrade.jar` e informa ausência de intenção de port para outras versões. [Reclaimed source 1.21.1](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/3a4326893b726010bbd7580c557de3d8447fc8a9) identifica `mod_id=thirst`, mas está em trabalho 3.0.6, não é o binário físico 3.0.5; nome/ID coincidentes não garantem contratos API nem item-use do addon concebido para Taken. Testar auto-consumo, filtros, container remainder, cooldown, dois usuários, restart e coexistência com Thirst Was Fixed.

**Gates ainda PENDENTES:** inspecionar os quatro JARs e metadados reais (IDs/ranges/hash/JarJar); comparar recipe JSON e KubeJS para #160; validar ABI Source/linked lifecycle para #054; JEI protocol, packet, Mixin e config client/server para #515; hidratação Reclaimed/Fixed para #514. Em cópia com backup de mundo: dedicated server + cliente, dois jogadores, Tom's recipe transfer, Source upgrade/linked groups, Create contraptions, save/restart, chunk unload/reload, anti-dupe e rollback. **CI do repositório não constitui essa certificação.** Core/Backpacks/Storage e bridges da PR #893 permanecem candidatos, não instalação.

**Dossiês correspondentes:** [#054](./Magic%20%2B%20Storage%20%2B%20Utility%20%26%20QoL/%E2%9C%85-ars-sophisticated-compatibility%20v0.3.0.md), [#160](./Addons%20%2B%20Create%20%2B%20Processing/%E2%9C%85-create-sophisticated-backpacks-compat%20v1.0.md), [#514](./Miscellaneous/%E2%9C%85-sophisticated-thirst-upgrade%20v0.1.8.md), [#515](./Addons%20%2B%20Applied%20Energistics%202%20%2B%20Refined%20Storage%20%2B%20Storage/%E2%9C%85-sophisticated-jei-index%20v1.2.3.md).


## Errata prioritária — projeto do Ars Compat #054 (13/10/2026, após PR #894)

A rechecagem dos **dois projetos oficiais distintos** mantidos por LuminaCherry corrigiu a atribuição anterior:

1. **[Sophisticated Backpacks: Ars Compat — CurseForge Project 1653477, File ID 8653384](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384):** arquivo **`arssophisticatedcompat-0.3.0.jar`**, NeoForge 1.21.1, 15/08/2026. Coincide exatamente com o **nome físico da entrada #054** do snapshot. Upgrades de Source/Sourcelink/Potion Jar/reparo são voltados para **backpacks**; dependências declaradas no release: **Ars Nouveau, Sophisticated Backpacks e Sophisticated Core**.
2. **[Sophisticated Storage: Ars Compat — CurseForge Project 1653878, File ID 8655579](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-ars-compat-sophisticated/files/8655579):** arquivo diferente, **`arssophisticatedstoragecompat-0.3.0.jar`** para NeoForge 1.21.1. Esse projeto aplica upgrades ao **Sophisticated Storage em blocos**. **Não foi confirmado como instalado na modlist histórica**; sua existência publicada não cria presença física.

**Decisão:** o nome apresentado na linha #054 e a documentação do dossiê foram corrigidos para **Backpacks Ars Compat**. Conservar JAR, runtime `0.3.0`, mod ID histórico `arssophisticatedcompat`, SHA-1 histórico `7cc6c1e1d92d109230f68b6a63dc4bfb13c245a6`, categoria, posição, quantidade de mods e slug do dossiê existente (evita links órfãos). A menção antiga do índice de 10/10/2026 a Storage Ars Compat foi corrigida como erro documental; a tabela da PR #894 foi ajustada. As demais partes históricas que descrevem Storage como consumidor de Ars passam a ser **substituídas por esta errata**.

**Limite de confiança:** coincidência exata de filename de publicação + target e versão é evidência forte para **identificação catalográfica**, porém nenhum JAR instalado foi disponibilizado para re-hash, leitura de `META-INF/neoforge.mods.toml` ou comparação byte-a-byte com o arquivo 8653384. **Não há prova binária, teste de jogo ou atualização física.** A migração Sophisticated para Core 1.5.11/Backpacks 3.26.12/Storage 1.6.5 permanece PENDENTE, com testes de Ars voltados ao **inventory do backpack**. O arquivo distinto `arssophisticatedstoragecompat-0.3.0.jar` não deve ser instalado automaticamente.


## Auditoria de proveniência SHA-1 e API Reclaimed exata — 13/10/2026

**Escopo restrito:** Sophisticated Backpacks: Ars Compat (#054), Sophisticated Thirst Upgrade (#514), Thirst Was Fixed (#542) e Thirst Was Reclaimed (#543). **Não foi encontrado JAR físico neste ambiente:** o GitHub contém apenas `gradle/wrapper/gradle-wrapper.jar` (não os mods), o material montado no Projeto só inclui a instrução canônica e a busca Dropbox não localizou os dois JARs pelo filename. Tentativa de buscar o arquivo oficial por CDN não obteve bytes. Assim, **não foram inspecionados manifests, bytecode, SHA-256, configurações ou worlds**. Nenhuma versão física/entrada/filename foi alterada.

| Posição | Última autoridade física — versão e filename preservados | Nova evidência verificável | Decisão |
|---|---|---|---|
| #054 | `arssophisticatedcompat-0.3.0.jar` / `0.3.0`, SHA-1 histórico `7cc6c1e1d92d109230f68b6a63dc4bfb13c245a6` | Manifestos públicos [Ars Eclectica](https://modpacks.ch/modpacks/1073937/ars-eclectica-an-ars-nouveau-experience) e [Tribe of Frost](https://modpacks.ch/modpacks/1727342/tribe-of-frost) repetem **exatamente SHA-1 + filename + CurseForge File ID `8653384`** de [Backpacks Ars Compat](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384). A variante distinta Storage Ars Compat aparece com SHA-1 `1a5b5e262b577a0ffa015060bdfe610c0bc7ba01` e File ID `8655579`. | **CORROBORAÇÃO SECUNDÁRIA DE PROVENIÊNCIA**, sem inspeção binária; #054 segue identificado como Backpacks |
| #514 | `sophisticated-thirst-upgrade.jar` / runtime `0.1.8`, SHA-1 histórico `788e5f75df0def9824b1a939e92aecffd0227d66` | Manifests independentes [Create - Marooned](https://modpacks.ch/modpacks/1560021/create-marooned) e [XerologicCraft](https://modpacks.ch/modpacks/1661579/xerologiccraft) associam mesmo SHA-1/filename ao [CurseForge File ID `8102344`](https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst/files/8102344). | **CORROBORAÇÃO SECUNDÁRIA DE PROVENIÊNCIA**, funcionamento com Reclaimed ainda **NÃO VERIFICADO** |
| #542 | `thirstwasfixed-2.1.6.jar` / `2.1.6` | Integração deve ser revisada junto dos novos mixins de purity 3.0.6 do Reclaimed; nenhuma nova versão ou teste físico demonstrado. | **MANTER**, regressão coordenada pendente |
| #543 | `ThirstWasReclaimed-1.21.1-3.0.5.jar` / runtime `1.21.1-3.0.5` | Source exato 3.0.5 [commit `bf07c26...`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5) **já inclui** bridges legadas `dev.ghen.thirst.api.ThirstHelper` e `dev.ghen.thirst.foundation.common.capability.ModAttachment` criadas em junho; o `ModCapabilities` do source alternativo Water Upgrade 1.20.1 não foi encontrado ali. [3.0.6](https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed/files/8893710) é **candidata publicada**, com source dedicado a purity em tank Sophisticated. | **3.0.5 FÍSICA; 3.0.6 PENDENTE** de verificação de mixins/ABI com Sophisticated |

**Descoberta de regressão material:** a [comparação oficial 3.0.5→3.0.6](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/compare/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5...3a4326893b726010bbd7580c557de3d8447fc8a9) adiciona mixins em `TankUpgradeWrapper` e `SwapEmptyFluidContainerHandler` de Sophisticated Core; ambas fazem injeções com `require=1`. O [upstream testou](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/README.md) um ambiente antigo, Core `1.3.89` e Backpacks `3.25.14`, não o histórico `1.5.1` nem os candidatos `1.5.11`/`3.26.12`. **A correção upstream não certifica compatibilidade com o pack.** Submeter tank mixins, pureza, fluid handlers, save/restart, Create contraptions e auto-consumo ao gate antes de qualquer troca coordenada.

**Alternativa externa não aprovada:** [waterupgrade 1.0.0 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/waterupgrade/files/8837011) oferece Drinking Upgrade, mas lista suporte a outros providers sem garantir nominalmente Thirst Was Reclaimed. O [source público `xlxyvergil/Water-Upgrade` pinado](https://github.com/xlxyvergil/Water-Upgrade/tree/ca8b1ec88de58445e4a6200dac2dd343ce6d4823) corresponde a **Forge 1.20.1**, não ao binário NeoForge 1.21.1; o handler legacy importa `ModCapabilities`, não localizado no source Reclaimed 3.0.5. **NÃO adicionar ou substituir Sophisticated Thirst Upgrade com essa alternativa sem inspecionar o JAR 1.21.1 e demonstrar API/QA.**

**Limite de evidência:** Modpacks.ch é terceiro agregador de manifests, **não fonte oficial de SHA autenticado**; coincidência de SHA-1 não equivale a ter re-hashado o JAR do usuário ou executado Minecraft. Nenhuma instalação, remoção, nova aprovação ou teste local realizada. O source Reclaimed correspondente à versão 3.0.5 oferece **evidência estática de compatibilidade antiga parcial**, não demonstra funcionalidade do addon `sophisticatedthirst` nem ABI segura do mixin de purity em Core 1.5.11. O gate físico permanece **BLOQUEADO POR AUSÊNCIA DE ARTEFATOS**.

**Dossiês revalidados:** [Ars Backpacks Compat #054](./Magic%20%2B%20Storage%20%2B%20Utility%20%26%20QoL/%E2%9C%85-ars-sophisticated-compatibility%20v0.3.0.md), [Thirst Upgrade #514](./Miscellaneous/%E2%9C%85-sophisticated-thirst-upgrade%20v0.1.8.md), [Thirst Was Fixed #542](./Addons%20%2B%20Bug%20Fixes/%E2%9C%85-thirst-was-fixed%20v2.1.6.md), [Thirst Was Reclaimed #543](./Food/%E2%9C%85-thirst-was-reclaimed%20v3.0.5.md).


## Candidato externo bloqueado — Soph Upgrade Addons NeoForge 1.21.1 (13/10/2026)

**Não é entrada física**, não é atualização concluída, não incrementa o inventário, não substitui `sophisticatedthirst` e não altera a tabela de 587 itens do snapshot ou o total conhecido de duas inclusões posteriores.

| Domínio | Histórico do pack | Candidato externo verificável | Status / motivo |
|---|---|---|---|
| Loader | `NeoForge 21.1.250` na posição #001 do último snapshot | [Soph Upgrade Addons 0.9.2 / MC 1.21.1 NeoForge — CurseForge ID 9071404](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/9071404); source `gradle.properties` `neo_version=21.1.252`; template exige `[21.1.252,)` | **BLOQUEADO DECLARATIVAMENTE** contra loader físico histórico 21.1.250; manifesto JAR não inspecionado |
| Hidratação | `ThirstWasReclaimed-1.21.1-3.0.5.jar` (#543) + `thirstwasfixed-2.1.6.jar` (#542) | [SUA 0.9.2 source `eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c`](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c): `MlusThirstCompat` e `DrinkCompatProvider` escolhem source `cn.mlus.thirst` se `modId=thirst` / classe existir. Os membros referenciados existem no [Reclaimed source 3.0.5](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5) | **SUPORTE NOMINAL EM SOURCE, NÃO TESTADO EM JAR/RUNTIME**, e ainda bloqueado pelo NeoForge |
| Upgrade de bebida | `sophisticated-thirst-upgrade.jar` (#514), runtime `0.1.8`, provider Taken originalmente | SUA Drink/Advanced Drink com filtros 9/16 slots por padrão, purity threshold, `drinkForHurt`, cooldowns e raio 3 blocos de backpack colocado; outros upgrades Ender/void/network/potion/salvaging | **SOBREPOSIÇÃO PARCIAL**: dois consumers auto-drink, não há confirmação de mutual exclusion com `sophisticatedthirst`; não coinstalar como se fossem independentes |
| Core/Backpacks | Core `1.5.1` (#518), Backpacks `3.26.3` (#516) no último snapshot; candidatos Core `1.5.11` e Backpacks `3.26.12` na PR #893 | Source SUA 0.9.2 exige Core `[1.2,)`, Backpacks `[3.21,)` (faixas NeoForge geradas); ambas satisfeitas numericamente pelos históricos | **Ranges permissivos ≠ compatibilidade ABI**; conferir binary, ligação de classes, packs atual/novo e regressões |

**Cronologia e precisão da fonte:** a [listagem oficial filtrada do CurseForge](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/all?page=1&pageSize=20&showAlphaFiles=hide&version=1.21.1) traz **0.9.1 (03/10/2026)** e **0.9.2 (05/10/2026)** como releases NeoForge 1.21.1; a 0.9.2 anota correção de receitas. O source `1.21.1` com `mod_version=0.9.2` foi lido no commit [`eb7d4f35`](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c). O commit posterior [`b1d2bb48`](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/b1d2bb48a30d488b0f01fa99e8c80e60e00680a2) declara **0.9.2.1 apenas no source** e troca `neo_version_range` para `[21.1.197,)` em `gradle.properties`, **sem** alterar `neo_version=21.1.252` nem o template que lê `neo_version`; por isso não prova compatibilidade mínima 21.1.197 nem publicação de um binário 0.9.2.1. Deixar a divergência como questão upstream.

**Ownership/risco:** Reclaimed = autoridade de sede/pureza; Sophisticated = inventário/upgrades; #514 = consumer atual; SUA = **alternativa futura não instalada**, com outros subsistemas integrados de redes e salvaging. A [classe `DrinkUpgrade`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/upgrades/drink/DrinkUpgrade.java) retorna lista vazia de conflitos do próprio upgrade, não demonstrando detecção nativa de addon concorrente. O [wrapper](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/upgrades/drink/DrinkUpgradeWrapper.java) manipula uso do item, débito de inventário, retorno e `onDrink`; esses contratos precisam de testes de **consumo único** e client/server, não de aprovação por leitura estática.

**Próximo gate:** confirmar um loader atualizado apenas com novo dump físico ou comprovação equivalente; obter manifesto do `9071404` e comparar o mínimo resolvido; avaliar source/JAR exato do `sophisticatedthirst` e SUA; cenários de backpack em movimento/colocado, radius 3, filtros, purity, save/restart, dois jogadores, recursos adicionais e plano de migração/reversão. **Nenhum mod foi adicionado/removido, nenhum runtime foi executado e nenhuma atualização física foi certificada nesta pesquisa.**

**Dossiês cruzados:** [#514 Thirst Upgrade](./Miscellaneous/%E2%9C%85-sophisticated-thirst-upgrade%20v0.1.8.md), [#543 Reclaimed](./Food/%E2%9C%85-thirst-was-reclaimed%20v3.0.5.md), [#516 Backpacks](./Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Storage/%E2%9C%85-sophisticated-backpacks%20v3.26.3.md).


## Contrato source de tanques — Reclaimed 3.0.6 × Sophisticated Core 1.5.11 (13/10/2026)

**Contexto físico inalterado:** o inventário físico histórico de 587 linhas segue NeoForge `21.1.250` (#001), Sophisticated Backpacks `3.26.3` (#516), Sophisticated Core `1.5.1` (#518), Sophisticated Thirst Upgrade `0.1.8` (#514), Thirst Was Fixed `2.1.6` (#542) e Thirst Was Reclaimed `3.0.5` (#543); duas adições posteriores confirmadas anteriormente **não foram recontadas como linhas desse snapshot**. Nenhum artefato foi alterado.

**Resultado positivo e delimitado:** o [source oficial Sophisticated Core `1.21.x`, commit `400660f2d4e6165076977b09e66cae26a7f2dfd3`](https://github.com/P3pp3rF1y/SophisticatedCore/tree/400660f2d4e6165076977b09e66cae26a7f2dfd3) corresponde a `mod_version=1.5.11`, e a classe [`TankUpgradeWrapper.java`](https://github.com/P3pp3rF1y/SophisticatedCore/blob/400660f2d4e6165076977b09e66cae26a7f2dfd3/src/main/java/net/p3pp3rf1y/sophisticatedcore/upgrades/tank/TankUpgradeWrapper.java) possui `getCustomFluidHandler`, `CUSTOM_FLUIDHANDLER_FACTORIES`, `fill` com callsite `FluidStack.setAmount` e a classe interna `SwapEmptyFluidContainerHandler` com construtor, `fill`, `drain(FluidStack, FluidAction)` e `ItemStack.isSameItemSameComponents`. Esses são os **alvos estruturais** descritos em [`MixinTankUpgradeWrapper`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/sophisticated/MixinTankUpgradeWrapper.java) e [`MixinSwapEmptyFluidContainerHandler`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/sophisticated/MixinSwapEmptyFluidContainerHandler.java), adicionados ao source Reclaimed `3.0.6`. **Alvos observados no source candidato ≠ injeção Mixin validada em JAR publicado.**

**Interação transmod importante:** o [`MixinFluidStack` do Reclaimed](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/MixinFluidStack.java) compara água ignorando exclusivamente `ThirstComponent.PURITY` (com proteção anti-recursão `ThreadLocal`); isso permite ao `TankUpgradeWrapper.fill` aceitar água com purezas diferentes apesar de sua comparação inicial de componentes. O Reclaimed então tenta misturar usando a **pureza mínima/mais suja** no `EXECUTE`. A mudança de comparador é **global em `FluidStack`**, exigindo regressão em providers de fluidos do pack (tanques Sophisticated, Create, tubulações, recipientes), incluindo `SIMULATE` não mutável.

**Limite de QA upstream:** [dois GameTests publicados em source](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/java/cn/mlus/thirst/compat/sophisticated/SophisticatedPurityGameTests.java) verificam bottle, pureza 0–3, fill/drain, mix e serialização; [README](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/README.md) declara ambiente NeoForge `21.1.215`, Core `1.3.89` e Backpacks `3.25.14` — **não** o histórico Core `1.5.1` nem o candidato Core `1.5.11`/Backpacks `3.26.12`. O dossiê [Core #518](./API%20and%20Library/%E2%9C%85-sophisticated-core%20v1.5.1.md) contém matriz dos alvos, e o [Reclaimed #543](./Food/%E2%9C%85-thirst-was-reclaimed%20v3.0.5.md) detalha a semântica da mistura.

**Decisão final desta rechecagem: COMPARAÇÃO ESTRUTURAL SOURCE POSITIVA; MIGRAÇÃO FÍSICA PENDENTE.** Não foi baixado/inspecionado nenhum dos JARs de distribuição nem executado boot, Mixins, GameTests ou teste de mundo. Gate seguinte: confirmar os targets exatos no bytecode das versões distribuídas, cada `require=1`, nenhuma duplicação de consumo no `sophisticatedthirst`, serialização de purity e compatibilidade com Create/Thirst Was Fixed sob dedicated server. Sem essas provas, **não promover Core, Backpacks nem Reclaimed**.



## NeoForge 21.1.259 publicado — gate transversal do loader e SUA (13/10/2026)

**Estado físico inalterado:** #001 continua neoforge-21.1.250 (modloader), Minecraft 1.21.1 / Java 21, conforme snapshot de 587 entradas top-level, incluindo o loader. Duas adições confirmadas posteriormente continuam fora da numeração do snapshot. Nenhum novo dump de JAR, backup, world ou bootstrap foi obtido. As linhas físicas do índice não devem ser editadas por descoberta de versões upstream.

**Correção de descoberta externa motivada pelo review Codex:** o [índice principal do Maven NeoForged](https://maven.neoforged.net/releases/net/neoforged/neoforge/) inicialmente retornou página incompleta até 21.1.253. A [árvore Maven espelhada](https://mirrors.qlu.edu.cn/bmclapi/net/neoforged/neoforge/) registra os artefatos da série 21.1.254–21.1.259, com [diretório 21.1.259](https://mirrors.qlu.edu.cn/bmclapi/net/neoforged/neoforge/21.1.259/) datado de 11/10/2026. O [changelog cumulativo 21.1.259](https://mirrors.qlu.edu.cn/bmclapi/net/neoforged/neoforge/21.1.259/neoforge-21.1.259-changelog.txt) identifica deltas desde 250: **251** deprecia getSeaLevel; **252** muda StackCopySlot / ItemHandlerCopySlot para transmitir slot index; **253** atualiza FancyModLoader para 4.0.45; **254** atualiza **Mixin 0.16.4**; **255** atualiza **MixinExtras 0.5.5**; **256** remove operação de shader color no loading overlay; **257** traz traduções; **258** atualiza Gradle/GradleUtils; **259** corrige o ItemStack enviado a Item.onLeftClickEntity em spin attacks. A 21.1.259 é a **candidata externa mais recente localizada nesta revisão**; não há JAR local autenticado.

| Superfície | Situação histórica do pack | Gate / decisão |
|---|---|---|
| **Loader #001** | NeoForge **21.1.250** | **21.1.259 publicada upstream, NÃO INSTALADA**. Staging baseline 250 vs candidata 259, com backup e todos os outros JARs iguais; não parar automaticamente na 253. |
| **SUA externo 0.9.2** | Ausente do snapshot; nunca adicionar como instalado por pesquisa | [Soph Upgrade Addons / CurseForge File ID 9071404](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/9071404), source pinado com NeoForge **[21.1.252,)**. 259 atende somente o piso **numérico**, não a ABI ou gameplay. Bloqueado no pack físico 250. |
| **Thirst #514/#542/#543** | Sophisticated Thirst Upgrade 0.1.8; Thirst Was Fixed 2.1.6; Thirst Was Reclaimed 3.0.5 | Não remover nem duplicar consumer de sede, sem prova em dedicated server de pureza, consumo, filtro, remainder, persistência e ownership. |
| **Compatibilidade Fabric #105/#106/#286** | Sinytra Connector Beta 17, Connector Extras e Forgified Fabric API | FancyModLoader **4.0.45**, Mixin **0.16.4** e MixinExtras **0.5.5** atravessam mod discovery / bytecode transformations; QA de client/server, mixin load, Fabric consumers, registry/network, jarjar e reload obrigatório. |
| **Sophisticated #516–#520** | Backpacks, Core, Storage e bridges nas versões do snapshot | Staging do loader deve ser separado da atualização do stack; evitar confundir evento de regressão do loader com atualização de Core/Thirst. |
| **Combate/overlay** | Mods de combate, Epic Fight, UI e shaders físicos existentes | Cobrir spin attacks/Item.onLeftClickEntity da 259 e overlay/shaders da 256 sem presumir que ocorrerá regressão. |

**Fluxo obrigatório antes de instalação:** (1) reconfirmar o inventário físico/JARs, dependências min/max e jarjar; (2) salvar o mundo e perfil completos; (3) instalar somente NeoForge 21.1.259 em cópia/staging com rollback 21.1.250; (4) executar cliente + dedicated server, todos os mods, transformação Connector + FFAPI, mixins e extras, registries, networking, worldgen, Create, fluids, inventários, magia, ataque giratório, shaders, save/restart e dois clientes; (5) avaliar separadamente promoções de Sophisticated/Thirst e SUA 0.9.2 somente após loader estável. **Esses testes não foram executados aqui e sucesso do CI do repositório não os substitui.**

**Dossiês:** [NeoForge #001](./Sem%20projeto%20CurseForge%20confirmado/%E2%9C%85-neoforge.md); [Sinytra #105](./Technology/%E2%9C%85-sinytra-connector%20v2.0.0-beta.17%2B1.21.1.md); [Thirst Upgrade #514](./Miscellaneous/%E2%9C%85-sophisticated-thirst-upgrade%20v0.1.8.md).


## Source Thirst Was Fixed 2.2.0 — risco ParCool/Moonlight (13/10/2026)

**Nenhuma mudança no snapshot de 587 linhas:** #001 NeoForge 21.1.250; #542 Thirst Was Fixed 2.1.6; #543 Thirst Was Reclaimed 3.0.5; ParCool 4.0.0.3. A [release oficial CurseForge 2.1.6, ID 8794908](https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/8794908) continua a mais recente localizada nessa página; [source do autor 47fb0e7 de 13/10/2026](https://github.com/Partonetrain/thirstwasfixed/tree/47fb0e7063f9e906aa05da492a938ad45f39cb8c) declara mod_version=2.2.0, **sem JAR de distribuição 2.2.0 verificado**.

| Superfície | Evidência source pinada | Gate técnico |
|---|---|---|
| NeoForge | Source 2.1.6 declara neo_version=21.1.247; source 2.2.0 declara neo_version=21.1.256 para desenvolvimento. O template de ThirstWasFixed usa neo_version_range=[21.1.247,) no manifest, em ambas. | Loader físico 21.1.250 satisfaz o range declarado, não a compatibilidade binária; não promover por range. |
| ParCool | [Commit intermediário 3c2fd04](https://github.com/Partonetrain/thirstwasfixed/commit/3c2fd04b61f6563513e36111aa8bd5f8b73ebe8c): STAMINA_RECOVERY muda de com.alrex.parcool.api.Attributes para com.alrex.parcool.api.ParCoolAttributes; dependência ParCool de desenvolvimento muda de CurseForge ID 6797814 para 9106851. | Verificar ParCool físico 4.0.0.3, assinatura de classe/atributo e mod compat; não atualizar ThirstWasFixed isoladamente. |
| Moonlight | [Mixin 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/mixin/moonlight/SoftFluidTankMixin.java): alvos SoftFluidTank.isFluidCompatible e addFluidOntoExisting, pureza mínima em água de purezas diferentes, quando opção habilitada; source 2.2.0 igual. | Alvos diferentes do FluidStack global no candidato Reclaimed 3.0.6, mas testar travessia entre Moonlight/Supplementaries, Create e Sophisticated, componentes, purity e volume. |
| Mixin bootstrap | [JSON 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/resources/thirstwasfixed.mixins.json) com required=true, defaultRequire=1 e plugin de compatibilidade condicional. | Verificar bytecode real e dedicated server; source não é prova de aplicação de injector. |

**Decisão:** source 2.2.0 identificado, **NÃO INSTALADO/NÃO APROVADO**; dossiê #542 atualizado apenas com evidências e testes pendentes. [Dossiê completo](./Addons%20%2B%20Bug%20Fixes/%E2%9C%85-thirst-was-fixed%20v2.1.6.md).


**Precisão adicional dos builds ParCool:** [CurseForge 6797814](https://www.curseforge.com/minecraft/mc-mods/parcool/files/6797814) = ParCool **3.4.1.1-NF** (alvo de desenvolvimento do ThirstWasFixed 2.1.6); [CurseForge 9106851](https://www.curseforge.com/minecraft/mc-mods/parcool/files/9106851) = ParCool **4.0.1.2** (alvo de desenvolvimento do source ThirstWasFixed 2.2.0). **Físico do pack: ParCool #433 4.0.0.3**. A versão física está entre os dois targets de desenvolvimento; a presença/ausência de ParCoolAttributes nesse JAR não foi determinada e permanece gate binário obrigatório. Não inferir que a troca de dependência upstream obriga atualização de ParCool na produção.


## Gate source ParCool 4.0.0.3 × Thirst Was Fixed 2.1.6 — 13/10/2026

**Ordem física histórica intacta:** ParCool #433 `4.0.0.3`, Thirst Was Fixed #542 `2.1.6`, Reclaimed #543 `3.0.5` e NeoForge #001 `21.1.250`. Uma **comparação de source exato** reduz a pendência da PR #900 sobre o owner da stamina: a [branch oficial `1.21.1-v4`, commit `5116ae71574428ba63e162f7e39a0f2c5e1d641c`](https://github.com/alRex-U/ParCool/tree/5116ae71574428ba63e162f7e39a0f2c5e1d641c) declara MC 1.21.1/ParCool 4.0.0.3 e fornece `com.alrex.parcool.api.ParCoolAttributes.STAMINA_RECOVERY` (`DeferredHolder<Attribute, Attribute>`), mas a árvore de 881 paths não contém `api/Attributes.java`. Em contraste, o [source TWF 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/ParCoolHelper.java) usa `com.alrex.parcool.api.Attributes.STAMINA_RECOVERY`, sugerindo **desalinhamento estático do owner** ao ativar as bridges de stamina.

**Condição de ativação:** [Config.java TWF 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/Config.java) declara os defaults de Stamina Bonus/Penalty Requirement iguais a **0**, e `ParCoolHelper.applyModifiers` condiciona acessos ao owner a valores não nulos; a config efetiva **NÃO foi lida**. O [source TWF 2.2.0](https://github.com/Partonetrain/thirstwasfixed/tree/47fb0e7063f9e906aa05da492a938ad45f39cb8c) usa `ParCoolAttributes`, presente já em ParCool 4.0.0.3 source, mas isso **não certifica binary/runtime** nem autoriza update. Range NeoForge ParCool 4.0.0.3 no source `[21.1.248,)` é numericamente atendido por 21.1.250, sem prova de boot do conjunto.

**Decisão operacional:** inspecionar JARs e thresholds reais, aplicar teste de linkage no dedicated server com valores 0/não nulos e verificar modifiers, save/relog; manter versões instaladas no catálogo até demonstração física. O dossiê de [ParCool #433](./Adventure%20and%20RPG%20%2B%20Miscellaneous%20%2B%20Utility%20%26%20QoL/%E2%9C%85-parcool%20v4.0.0.3.md) e o de [TWF #542](./Addons%20%2B%20Bug%20Fixes/%E2%9C%85-thirst-was-fixed%20v2.1.6.md) detalham a matriz. Sem JAR/boot ou atualização física neste lote.


## ParCool #433 — Curios/Epic Fight #521 versus boot dedicado: problemas distintos (13/10/2026)

**Estado histórico inalterado:** ParCool 4.0.0.3, Curios 9.5.1+1.21.1, Epic Fight 21.17.3.1, NeoForge 21.1.250 e Thirst Was Fixed 2.1.6. A [CurseForge ParCool](https://www.curseforge.com/minecraft/mc-mods/parcool/files/all?gameVersionTypeId=6&page=1) confirma **4.0.1.2 como release externa recente para NeoForge 1.21.1**, não instalada. A [CurseForge do Thirst Was Fixed](https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/all) continua listando **2.1.6 como último JAR**, portanto 2.2.0 é apenas um source candidato.

A [issue oficial ParCool #521](https://github.com/alRex-U/ParCool/issues/521) descreve **StackOverflowError por reentrada de capacidades Curios/Epic Fight no ambiente Forge 1.20.1**, não no pack NeoForge 1.21.1. O [source ParCool 4.0.0.3 na branch 1.21.1](https://github.com/alRex-U/ParCool/blob/5116ae71574428ba63e162f7e39a0f2c5e1d641c/src/main/java/com/alrex/parcool/extern/curios/capability/EquipAbleCuriosWrapper.java) ainda usa `itemStack.getAttributeModifiers()` no construtor de `EquipAbleCuriosWrapper`; no [source 4.0.1.1/1.21.1](https://github.com/alRex-U/ParCool/blob/efc8f3c6887dd8f817a88f4031a7858b37834548/src/main/java/com/alrex/parcool/extern/curios/capability/EquipAbleCuriosWrapper.java) a consulta passa para `itemStack.getItem().getDefaultAttributeModifiers(itemStack)`. **Risco estático cross-loader, não crash local comprovado:** testar Traceur Gloves/Curios, Epic Fight, recursion e modifiers externos em staging.

**Não confundir com o segundo fix da 4.0.1.1:** [changelog oficial](https://github.com/alRex-U/ParCool/blob/efc8f3c6887dd8f817a88f4031a7858b37834548/changelog/v_4.0.1.1.md) lista separadamente `Crash on boot of server` e `#521`. O método `AbstractLocalStamina.get(LocalPlayer)` foi introduzido no [source NeoForge 4.0.1.0](https://github.com/alRex-U/ParCool/blob/b7b0529d0e85820d1b4847c19623f046f7a299da/src/main/java/com/alrex/parcool/api/stamina/AbstractLocalStamina.java), sem `@OnlyIn(Dist.CLIENT)`, e recebeu a anotação na [4.0.1.1](https://github.com/alRex-U/ParCool/blob/efc8f3c6887dd8f817a88f4031a7858b37834548/src/main/java/com/alrex/parcool/api/stamina/AbstractLocalStamina.java). **O método não existe no source 4.0.0.3**; não atribuir retrospectivamente esse crash ao JAR instalado.

**Decisão:** não promover ParCool 4.0.1.2 sem QA de JARs, Curios/Traceur Gloves, dedicated server, Epic Fight, Thirst Was Fixed, saves e rollback. [Dossiê #433 detalhado](./Adventure%20and%20RPG%20%2B%20Miscellaneous%20%2B%20Utility%20%26%20QoL/%E2%9C%85-parcool%20v4.0.0.3.md). Nenhum JAR, configuração, mundo ou teste físico foi alterado/executado.


## Inconsistência corrigida — Epic ParCool não instalado; bridge oficial 21.0.0 incompatível com ParCool 4.0.0.x (13/10/2026)

**Fonte física histórica e ausência:** a lista de 587 entradas numeradas preserva **Epic Fight #260 `21.17.3.1`**, **ParCool #433 `4.0.0.3`** e **Curios #211 `9.5.1+1.21.1`**, mas **não contém o addon Epic ParCool**. O dossiê Epic Fight antigo o citava em duas passagens como bridge fisicamente presente; essas passagens foram reconciliadas. Não confundir menção de compatibilidade upstream com instalação de um mod adicional.

**Gate do addon opcional ausente:** [Epic ParCool oficial 21.0.0 / NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/official-epic-fight-x-parcool/files/7490217) tem source [`1.21.1`, commit `b1f703e7ed911bd2704cd566b8aa511723b8b78a`](https://github.com/Antikythera-Studios/epicparcool/tree/b1f703e7ed911bd2704cd566b8aa511723b8b78a) com `ParCool-3.4.3.0.jar`, dependency range `[3.4.2.0,)` e mixins referenciando classes/descriptors pré-4.0. [Issue #18](https://github.com/Antikythera-Studios/epicparcool/issues/18) relata falha com ParCool 4.0.0.1; [#19](https://github.com/Antikythera-Studios/epicparcool/issues/19) documenta sete targets ausentes e dois descriptors alterados em 4.0.0.3, além de um problema distinto de mixin client-only em dedicated server. A edição oficial 21.0.0 fica **BLOQUEADA para inclusão direta**, sem inferir falha atual do pack que não tem essa bridge.

**Outro projeto (não avaliado):** [\[NEOFORGE\] Epic Fight X Parcool 1.0.0, de Ivotday](https://www.curseforge.com/minecraft/mc-mods/epic-fight-x-parcool/files/all) é release distinta para 1.21.1 (10/08/2026), **não instalada, não aprovada e com compatibilidade ainda não verificada**. Antes de selecionar qualquer addon, inspecionar binário e testar API de movement, battle mode, animações, inputs, stamina, dedicated server, Curios e persistência. [Dossiê Epic Fight #260](./Adventure%20and%20RPG%20%2B%20API%20and%20Library%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons/%E2%9C%85-epic-fight%20v21.17.3.1.md); [dossiê ParCool #433](./Adventure%20and%20RPG%20%2B%20Miscellaneous%20%2B%20Utility%20%26%20QoL/%E2%9C%85-parcool%20v4.0.0.3.md). Nenhum JAR, mundo ou config mudou.


## Epic Fight Compat #263 — oitavo alvo físico: Bosses'Rise #079 (13/10/2026)

**Reconciliação de cobertura, sem atualização física:** o snapshot ordenado registra **Epic Fight Compat #263 `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` (1.1.0)**, **Bosses'Rise #079 `block_factorys_bosses-2.1.2-neo-1.21.1.jar` (2.1.2)** e **Epic Fight #260 (21.17.3.1)**. A ficha Epic Fight Compat citava **sete** módulos com targets presentes, mas omitia o **oitavo**, Bosses'Rise. O [source da release/tag oficial `v1.1.0`](https://github.com/payangar-dev/epicfight-mod-compat/blob/v1.1.0/src/main/java/com/payangar/epicfightcompat/compat/CompatRegistry.java) contém **17 módulos** e o [módulo `block_factorys_bosses`](https://github.com/payangar-dev/epicfight-mod-compat/blob/v1.1.0/src/main/java/com/payangar/epicfightcompat/compat/blockfactorysbosses/BlockFactorysBossesModule.java) exige exatamente o mod ID instalado. O [changelog CurseForge oficial 1.1.0 NeoForge 1.21.1 (File ID 8097273)](https://www.curseforge.com/minecraft/mc-mods/epic-fight-mod-compat/files/8097273) confirma essa integração.

**Fronteira funcional obrigatória:** o módulo Bosses'Rise fornece **oito JSONs de capabilities de armas/escudos**, **não** patches de entidades/bosses. Não atribuir automaticamente bosses GeckoLib, AI, fases, hits ou cinematics ao Epic Fight por causa desse módulo. Das 17 integrações source, **oito** possuem target top-level na snapshot (#342 Iron's Spells, #503 Simply Swords, #502 Simply More, #411 Mowzie's Mobs, #468 Quark, #079 Bosses'Rise, #536 Supplementaries, #276 Farmer's Delight); as outras nove não foram identificadas como targets instalados. A tag upstream inclui `mod_version=0.1.0` no `gradle.properties`; a publicação 1.1.0 e a metadata do snapshot têm autoridade sobre a versão física registrada. Não extrapolar presença de source para ABI/runtimes certificados.

**Decisão:** documentação corrigida, **sem instalar/atualizar/remover** qualquer mod; preservar **587/587 posições e ordem física histórica**; QA pendente: conferir JARs reais, oito weapon capabilities, bypass de gauntlets especiais, ausência de entity patches de bosses, regressão Epic Fight 21.17.3.1 e dedicated server. [Dossiê compat #263](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Mobs/%E2%9C%85-epic-fight-compat%20v1.1.0.md); [Bosses'Rise #079](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Mobs%20%2B%20Structures%20%2B%20World%20Gen/%E2%9C%85-bosses-rise%20v2.1.2.md).

## Atualização solicitada — 22/10/2026 — 83 mods

> **Escopo:** auditoria de catálogo upstream para Minecraft 1.21.1 / NeoForge na ordem exata solicitada. **Não é uma nova modlist física.** As 587 linhas físicas acima permanecem autoridade de presença/JAR/versão instalada. Releases Beta/Alpha/Pre-Release/Snapshot permanecem fail-closed até staging e QA.

| Pedido | Projeto | Dossiê | Versão documental/física preservada | Upstream 22/10/2026 | Estado |
|---:|---|---|---|---|---|
| 01 | MineColonies | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Automation%20%2B%20Storage%20%2B%20Structures/%E2%9C%85-minecolonies%20v1.1.1387-1.21.1-snapshot.md) | `1.1.1387-1.21.1-snapshot` | 1.1.1403 Release; 1.1.1405-snapshot Beta | **PENDENTE; snapshot BLOQUEADO** |
| 02 | Easy NPC | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library/%E2%9C%85-easy-npc-bundle%20v7.12.1.md) | `7.12.1` | 7.17.0 | **COORDENADO com Core/UI/EME** |
| 03 | Alex's Mobs Continued | [Abrir `.md`](./Mobs/%E2%9C%85-alexs-mobs-continued%20v2.1.13.md) | `2.1.13` | 2.2.4 (Release NeoForge 1.21.1, 19/10) | **PENDENTE QA — nova Release 2.2.4; cadeia 2.1.14→2.2.4 auditada** |
| 04 | Ars 'n Spells | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Magic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-ars-n-spells%20v3.3.4.md) | `3.3.4` | 3.3.7 | **PENDENTE** |
| 05 | Photon | [Abrir `.md`](./API%20and%20Library%20%2B%20Magic%20%2B%20Technology%20%2B%20Utility%20%26%20QoL/%E2%9C%85-photon%20v2.2.6.a.md) | `2.2.6.a` | 2.2.8 | **PENDENTE** |
| 06 | Forgified Fabric API | [Abrir `.md`](./Technology/%E2%9C%85-forgified-fabric-api%20v0.116.15%2B2.3.5%2B1.21.1.md) | `0.116.15+2.3.5+1.21.1` | 0.116.15+2.3.6+1.21.1 | **PENDENTE** |
| 07 | BCLib: New Dawn | [Abrir `.md`](./API%20and%20Library%20%2B%20Biomes%20%2B%20Utility%20%26%20QoL%20%2B%20World%20Gen/%E2%9C%85-bclib-new-dawn%20v21.0.26.md) | `21.0.26` | 21.0.28 | **COORDENADO NEW DAWN** |
| 08 | InsaneLib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-insanelib%20v2.4.33.0.md) | `2.4.33.0` | 2.4.33.1 | **PENDENTE** |
| 09 | Create: Teleporters | [Abrir `.md`](./Create%20%2B%20Energy%2C%20Fluid%2C%20and%20Item%20Transport%20%2B%20Technology/%E2%9C%85-create-teleporters-remastered%20v2.0.2b.md) | `2.0.2b` | 3.0.1b | **PENDENTE** |
| 10 | Petrolpark Library | [Abrir `.md`](./Addons%20%2B%20API%20and%20Library%20%2B%20Create/%E2%9C%85-petrolparks-library%20v1.5.11.md) | `1.5.11` | 1.5.11 Release estável; 1.5.12 Beta (19/10) | **1.5.11 Release mantida; 1.5.12 Beta bloqueada para promoção** |
| 11 | Punchy! - First person animations | [Abrir `.md`](./API%20and%20Library%20%2B%20Cosmetic%20%2B%20Map%20and%20Information/%E2%9C%85-punchy%20v2.7e.md) | `2.7e` | 2.8g (18/10) | **2.8 NEOFORGE 1.21.1 DOCUMENTADA COM CHANGELOG DIRETO EM 24/10; 2.8d/2.8e CONFIRMADAS NO MODRINTH; 2.8a E PROVA EXATA DOS DELTAS 2.8f/2.8g AINDA PENDENTES** |
| 12 | Sophisticated Core | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-sophisticated-core%20v1.5.1.md) | `1.5.1` | 1.5.17.2426 Release (21/10) | **PESQUISA DOCUMENTAL CONCLUÍDA EM 23/10; 1.5.15 E 1.5.16 VINCULADAS A COMMITS COM mod_version; QA/INSTALAÇÃO PENDENTES** |
| 13 | Alex's Caves Continued | [Abrir `.md`](./World%20Gen/%E2%9C%85-alexs-caves-continued%20v1.0.10.md) | `1.0.10` | 1.0.10 | **REVALIDADO** |
| 14 | Puzzles Lib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-puzzles-lib%20v21.1.60.md) | `21.1.60` | 21.1.65 | **PENDENTE** |
| 15 | CBC: Advanced technologies crash fix | [Abrir `.md`](./Addons%20%2B%20Create%20%2B%20Technology/%E2%9C%85-cbcat-fix%20v1.0.1.md) | `1.0.1` | 1.1.4 (15/10) | **AUDITORIA DOCUMENTAL 1.0.1→1.1.4 COMPLETA; CATEGORIA CORRIGIDA; QA FÍSICO SEPARADO** |
| 16 | Vanilla Backport | [Abrir `.md`](./Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Biomes%20%2B%20Mobs%20%2B%20Ores%20and%20Resources%20%2B%20World%20Gen/%E2%9C%85-vanillabackport%20v1.1.7.10.md) | `1.1.7.10` | 1.2 (Release NeoForge 1.21.1, 18/10) | **AUDITORIA DOCUMENTAL; MIGRAÇÃO MAJOR E BRIDGES EXIGEM QA** |
| 17 | Platform | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-platform%20v1.3.3.md) | `1.3.3` | 1.4 | **PENDENTE** |
| 18 | FTB XMod Compat | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-ftb-xmod-compat%20v21.1.11.md) | `21.1.11` | 21.1.13 (Release NeoForge 1.21.1, 20/10) | **AUDITORIA DOCUMENTAL; SG Economy API 1.1+** |
| 19 | Amendments | [Abrir `.md`](./Cosmetic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-amendments%20v1.21-2.1.10.md) | `1.21-2.1.10` | 2.2.0 | **PENDENTE** |
| 20 | Alshanex's Familiars | [Abrir `.md`](./Addons%20%2B%20Magic%20%2B%20Mobs/%E2%9C%85-alshanexs-familiars%20v1.21.1_v4.0.3.md) | `1.21.1_v4.0.3` | 5.0 (Release NeoForge 1.21.1, 21/10) | **AUDITORIA DOCUMENTAL; BACKUP E FAMILIARSLIB EXIGIDOS** |
| 21 | Create:Tracks+ | [Abrir `.md`](./Create/%E2%9C%85-create-tracks-plus%20v1.0.6b6.md) | `1.0.6b6` | Anunciado beta6; JAR publicado `tracks_plus-1.0.6b7.jar` (15/10) | **BLOQUEADO: ausência de changelog/diff b6→b7** |
| 22 | Relics | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Cosmetic%20%2B%20Magic%20%2B%20Ores%20and%20Resources/%E2%9C%85-relics%20v0.12.8.md) | `0.12.8` | 0.12.9 Beta (18/10) | **AUDITORIA DOCUMENTAL CONCLUÍDA; Beta não promovida** |
| 23 | Lychee [Neo/Forge] | [Abrir `.md`](./Utility%20%26%20QoL/%E2%9C%85-lychee-tweaker%20v6.7.0%2Bneoforge.md) | `6.7.0+neoforge` | 6.7.1 Beta (17/10) | **AUDITORIA DOCUMENTAL CONCLUÍDA; Beta não promovida** |
| 24 | EMF Compat: Core | [Abrir `.md`](./API%20and%20Library%20%2B%20Cosmetic/%E2%9C%85-emf-compat-core%20v2.0.0.md) | `2.0.0` | 2.3.2 (NeoForge 1.21.1, 16/10) | **BLOQUEADO: delta individual 2.3.1 não isolado** |
| 25 | Supplementaries | [Abrir `.md`](./Cosmetic%20%2B%20Miscellaneous%20%2B%20Redstone%20%2B%20Storage/%E2%9C%85-supplementaries%20v1.21.1-3.9.9.md) | `1.21.1-3.9.9` | 4.0.2 (16/10) | **AUDITORIA DOCUMENTAL CONCLUÍDA; QA físico pendente** |
| 26 | AzureLib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-azurelib%20v3.1.11.md) | `3.1.11` | 3.1.22; 3.1.23-alpha.2 (nome Alpha, CF Type R) | **BLOQUEADO: builds 3.1.19–3.1.23 sem changelog individual** |
| 27 | Easy NPC: Core | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library/%E2%9C%85-easy-npc%20v7.12.1.md) | `7.12.1` | 7.17.0 | **CONCLUÍDA DOCUMENTALMENTE NA PR #925; QA coordenado pendente** |
| 28 | Create Aeronautics: Gadgets & Gizmos | [Abrir `.md`](./Addons%20%2B%20Create/%E2%9C%85-create-aeronautics-gadgets-and-gizmos%20v1.2.2.md) | `1.2.2` | 1.2.10; 2.0.0 Pre-Release 3 | **BLOQUEADO: 1.2.6 sem changelog isolado; 1.2.9 insegura** |
| 29 | WorldWeaver: New Dawn | [Abrir `.md`](./API%20and%20Library%20%2B%20Biomes%20%2B%20Utility%20%26%20QoL%20%2B%20World%20Gen/%E2%9C%85-worldweaver-new-dawn%20v21.0.25.md) | `21.0.25` | 21.0.29 (20/10) | **BLOQUEADO: changelogs 21.0.26–21.0.29 não individualizados** |
| 30 | ImmediatelyFast | [Abrir `.md`](./Performance%20%2B%20Utility%20%26%20QoL/%E2%9C%85-immediatelyfast%20v1.6.14%2B1.21.1.md) | `1.6.14+1.21.1` | 1.6.15 (13/10) | **AUDITORIA DOCUMENTAL CONCLUÍDA; QA visual pendente** |
| 31 | Advanced Loot Info (ALI) | [Abrir `.md`](./Addons%20%2B%20API%20and%20Library%20%2B%20Map%20and%20Information/%E2%9C%85-advanced-loot-info%20v2.1.0.md) | `2.1.0` | 2.5.0 Beta (16/10; cadeia 2.2.0→2.5.0) | **AUDITORIA DOCUMENTAL FECHADA; Beta não instalada** |
| 32 | owo (owo-lib) | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-owo-lib%20v0.12.15.5-beta.1%2B1.21.md) | `0.12.15.5-beta.1+1.21` | 0.12.15.6-beta.1+1.21 (Beta, NeoForge) | **AUDITORIA DOCUMENTAL FECHADA; Beta não instalada** |
| 33 | Minecolonies: Epiched (Epic Colonies) | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Cosmetic/%E2%9C%85-epic-colonies%20v21.0.8.md) | `21.0.8` | 21.1.2 (17/10; 21.0.9→21.1.2) | **AUDITORIA DOCUMENTAL FECHADA; validar EFM/MineColonies** |
| 34 | Easy Model Entities | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library%20%2B%20Automation%20%2B%20Mobs%20%2B%20Utility%20%26%20QoL/%E2%9C%85-easy-model-entities%20v2.4.0.md) | `2.4.0` | 2.6.0 (NeoForge 1.21.1; 2.5.0 intermediária) | **AUDITORIA DOCUMENTAL FECHADA; Easy NPC coordenado** |
| 35 | Polymorph+ | [Abrir `.md`](./Miscellaneous%20%2B%20Utility%20%26%20QoL/%E2%9C%85-polymorph-plus%20v1.3.1%2B1.21.1.md) | `1.3.1+1.21.1` | 1.4.1 (NeoForge 1.21.1) | **AUDITORIA DOCUMENTAL FECHADA; source 1.21.1 pinado, QA JEI pendente** |
| 36 | Sable: Ragdoll - Patch | [Abrir `.md`](./Bug%20Fixes/%E2%9C%85-sable-ragdolls-patch%20v1.9.md) | `1.9` | 1.9.1 (Release NeoForge 1.21.1, 19/10) | **CORRIGIDO E AUDITADO; alpha cape transparency** |
| 37 | Eidolon : Repraised | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Magic/%E2%9C%85-eidolon-repraised%20v0.5.0.2.md) | `0.5.0.2` | 0.5.0.7 (17/10; 0.5.0.4/.5/.6/.7) | **AUDITORIA DOCUMENTAL FECHADA; locator ritual QA** |
| 38 | [ETF] Entity Texture Features - [Fabric & Forge] | [Abrir `.md`](./Cosmetic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-entity-texture-features%20v7.2.1.md) | `7.2.1` | 7.2.6 (15/10; 7.2.2→7.2.6) | **AUDITORIA DOCUMENTAL FECHADA; EMF/ETF render QA** |
| 39 | Reliquified Ars Nouveau | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Magic/%E2%9C%85-reliquified-ars-nouveau%20v0.8.1.md) | `0.8.1` | 0.8.1 Beta (sem publicação posterior NeoForge 1.21.1) | **REVALIDADO; zero versões intermediárias** |
| 40 | Create:LazyTick | [Abrir `.md`](./Addons%20%2B%20Bug%20Fixes%20%2B%20Create/%E2%9C%85-create-lazy-tick%20v2.6.25-6.0.10.md) | `2.6.25-6.0.10` | 2.7.30 / Create 6.0.10 (28/09) | **AUDITORIA DOCUMENTAL FECHADA; Modrinth histórico 2.6.25→2.7.30** |
| 41 | Ecliptic Seasons | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Cosmetic%20%2B%20Farming%20%2B%20Utility%20%26%20QoL/%E2%9C%85-ecliptic-seasons%20v0.15.0-rc-3-1.md) | `0.15.0-rc-3-1` | 0.15.4.1 Beta (NeoForge 1.21.1, 16/10) | **AUDITORIA DOCUMENTAL FECHADA; Beta não promovida** |
| 42 | Fragmentum ✿ NeoForge | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-fragmentum%20v2.4.4.md) | `2.4.4` | 5.4.0 (NeoForge 1.21.1, 16/10) | **AUDITORIA DOCUMENTAL FECHADA; migração 2.x→5.x exige QA** |
| 43 | ThirstWasFixed | [Abrir `.md`](./Addons%20%2B%20Bug%20Fixes/%E2%9C%85-thirst-was-fixed%20v2.1.6.md) | `2.1.6` | 2.2.1 (NeoForge 1.21.1, 15/10) | **AUDITORIA DOCUMENTAL FECHADA; ParCool/ABI não homologados** |
| 44 | FamiliarsLib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-familiarslib%20v1.21.1-1.7.md) | `1.21.1-1.7` | 1.8 (NeoForge 1.21.1, 24/09) | **AUDITORIA DOCUMENTAL FECHADA; metadata divergente preservada** |
| 45 | Cyclops Core | [Abrir `.md`](./API%20and%20Library%20%2B%20Server%20Utility/%E2%9C%85-cyclops-core%20v1.30.0.md) | `1.30.0` | 1.30.0-1205 Beta (18/10) | **AUDITORIA DE DIFF BETA 1205 CONCLUÍDA; QA FÍSICO SEPARADO** |
| 46 | Apotheosis x Iron's Spellbooks Compat | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Magic/%E2%9C%85-irons-apothic%20v2.2.2.md) | `2.2.2` | 2.2.8+1.21.1 (17/10) | **AUDITORIA DOCUMENTAL FECHADA; QA spell/affix pendente** |
| 47 | Dynamic Trees - VanillaBackport | [Abrir `.md`](./Addons%20%2B%20World%20Gen/%E2%9C%85-dynamic-trees-vanillabackport%20v1.7.0.md) | `1.7.0` | 1.7.0 (sem release posterior NeoForge 1.21.1) | **REVALIDADO: intervalo vazio; provider 1.2 não homologado** |
| 48 | Polytone | [Abrir `.md`](./Cosmetic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-polytone%20v1.21-4.4.0.md) | `1.21-4.4.0` | 5.2.0 NeoForge 1.21.1 (20/10) | **AUDITORIA DOCUMENTAL FECHADA; 5.1.3 changelog genérico** |
| 49 | Just Enough Items (JEI) | [Abrir `.md`](./API%20and%20Library%20%2B%20Map%20and%20Information/%E2%9C%85-just-enough-items%20v19.56.0.440.md) | `19.56.0.440` | 19.64.0.455 Release (16/10); 19.69.0.457 Beta (20/10) | **19.69.0.456 DETALHADA COM CHANGELOG NEOFORGE EXATO EM 24/10 (LUCENE, REFRESH API, IDs SINTÉTICOS, ATALHOS); .455/.457 DELTAS EXCLUSIVOS AINDA NÃO VINCULADOS AOS JARS; PENDENTE** |
| 50 | Create Aeronautics: Automated Logistics | [Abrir `.md`](./Addons%20%2B%20Automation%20%2B%20Create/create-aeronautics-automated-logistics%20v0.6.2.md) | `0.6.2` | 0.6.2 | **BLOQUEADO: dossier sem ✅; não editado** |
| 51 | Compatibility addon for MineColonies | [Abrir `.md`](./Addons%20%2B%20Utility%20%26%20QoL/%E2%9C%85-minecolonies-compatibility%20v3.57.md) | `3.57` | 3.60 Beta (19/10; 3.59 corrigiu teach recipes) | **AUDITORIA DOCUMENTAL 3.57→3.60 COMPLETA; QA FÍSICO SEPARADO** |
| 52 | Collective | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-collective%20v8.39.md) | `8.39` | 8.65 (22/10) | **AUDITORIA DOCUMENTAL 8.39→8.65 COMPLETA (8.48/8.55 NÃO PUBLICADAS 1.21.1); QA FÍSICO SEPARADO** |
| 53 | Create: Stats & Power | [Abrir `.md`](./Addons%20%2B%20Create/%E2%9C%85-create-stats-power%20v1.13.1.md) | `1.13.1` | 1.15.0 | **AUDITORIA DOCUMENTAL 1.13.1→1.14.5→1.15.0 CONCLUÍDA; QA FÍSICO SEPARADO** |
| 54 | Aeronautics Player Tilt | [Abrir `.md`](./Addons%20%2B%20Cosmetic%20%2B%20Create/%E2%9C%85-aeronautics-player-tilt%20v0.2.0.md) | `0.2.0` | 0.2.3 (11/10) | **DELTAS DOCUMENTADOS 0.2.1→0.2.3; QA FÍSICO SEPARADO** |
| 55 | Sophisticated Backpacks | [Abrir `.md`](./Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Storage/%E2%9C%85-sophisticated-backpacks%20v3.26.3.md) | `3.26.3` | 3.26.14.2222 | **RELEASES 3.26.4–3.26.14 DOCUMENTADAS POR SOURCE; PROMOÇÃO FÍSICA COORDENADA PENDENTE** |
| 56 | Climbable Ropes for Create Aeronautics | [Abrir `.md`](./Addons%20%2B%20Create/%E2%9C%85-climbable-ropes-for-create-aeronautics%20v2.1.3.md) | `2.1.3` | 2.2.0 (19/10, Modrinth oficial/source) | **2.1.4 E 2.2.0 REVISADAS; ANIMAÇÃO IK PROCEDURAL; QA FÍSICO SEPARADO** |
| 57 | Epic Fight: Curios Compat 4.0 | [Abrir `.md`](./Addons%20%2B%20Bug%20Fixes%20%2B%20Cosmetic/%E2%9C%85-epic-fight-curios-compat%20v1.4.md) | `1.4` | 4.0 Release NeoForge 1.21.1 (23/10; file ID 9296370) | **PESQUISA DOCUMENTAL 2.2 → 3.0 → 3.1 → 4.0 CONCLUÍDA EM 24/10; JAR 2.2/METADATA 1.4 INSTALADOS, MANIFEST E QA PENDENTES** |
| 58 | Easy NPC: Config UI | [Abrir `.md`](./Utility%20%26%20QoL/%E2%9C%85-easy-npc-config-ui%20v7.12.1.md) | `7.12.1` | 7.17.0 | **RELEASES 7.13–7.17 DOCUMENTADAS; PROTOCOLO CLIENT/SERVER COORDENADO; QA FÍSICO SEPARADO** |
| 59 | Goety - The Dark Arts | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Magic%20%2B%20Mobs%20%2B%20Structures/%E2%9C%85-goety%20v3.1.4.md) | `3.1.4` | 3.2.1 | **BACKPORT DE 1.20.1 2.5.59.0–.2 MAPEADO POR FONTES UPSTREAM EM 24/10; FALTA PROVA DA PRESENÇA DAS MUDANÇAS NO JAR NEOFORGE 3.2.1; PENDENTE** |
| 60 | BetterNether: New Dawn | [Abrir `.md`](./Biomes%20%2B%20Cosmetic%20%2B%20Mobs%20%2B%20Structures%20%2B%20World%20Gen/%E2%9C%85-betternether-new-dawn%20v21.0.26.md) | `21.0.26` | 21.0.29 (19/10) | **21.0.27→21.0.29 REVISADAS COM DIFF SOURCE; QA NEW DAWN SEPARADO** |
| 61 | Streams Reflowing | [Abrir `.md`](./Biomes%20%2B%20Cosmetic%20%2B%20World%20Gen/%E2%9C%85-streamsreflowing%20v2.13.5.md) | `2.13.5` | 2.15.2 | **RELEASES 2.13.8/2.14.1/2.14.4/2.15.0/2.15.2 REVISADAS; CONFIG .toml→.properties E ENVIO OPCIONAL DA SEED DOCUMENTADOS; QA FÍSICO SEPARADO** |
| 62 | Reliquified Iron's Spells 'n Spellbooks | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Magic/%E2%9C%85-reliquified-irons-spells-n-spellbooks%20v0.2.7.md) | `0.2.7` | 0.2.8 Beta | **0.2.8 BETA REVISADA INTEGRALMENTE; DIMENSION SEAL/ANIMATED TEXTURES/ABILITY ICONS DOCUMENTADOS; QA FÍSICO SEPARADO** |
| 63 | Enhanced AI | [Abrir `.md`](./Mobs/%E2%9C%85-enhanced-ai%20v4.2.3.0.md) | `4.2.3.0` | 4.2.5.0 (20/10) | **4.2.4.0 E 4.2.5.0 REVISADAS COM CHANGELOGS EXATOS; QA FÍSICO SEPARADO** |
| 64 | ParCool! ~ Minecraft Parkour ~ | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Miscellaneous%20%2B%20Utility%20%26%20QoL/%E2%9C%85-parcool%20v4.0.0.3.md) | `4.0.0.3` | 4.0.1.6 | **4.0.0.6/4.0.1.0/.1/.2/.5/.6 REVISADAS; CURIOS OBRIGATÓRIO NO UPSTREAM; QA FÍSICO SEPARADO** |
| 65 | Skin Layers 3D | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Cosmetic/%E2%9C%85-3d-skin-layers%20v1.11.2.md) | `1.11.2` | 1.11.4 (18/10; NeoForge 1.21.1 verificado) | **AUDITORIA 1.11.2→1.11.3→1.11.4 COMPLETA; FALSO BLOQUEIO DE CATÁLOGO CORRIGIDO; QA FÍSICO SEPARADO** |
| 66 | Lithostitched | [Abrir `.md`](./API%20and%20Library%20%2B%20Miscellaneous%20%2B%20World%20Gen/%E2%9C%85-lithostitched%20v1.8.0%2Bbeta6.md) | `1.8.0+beta6` | 1.8.1 | **BETA6→1.8.0→1.8.1 DOCUMENTADAS; NOVOS TARGETS API/TAGS/STRUCTURE PROCESSORS; QA DE WORLDGEN SEPARADO** |
| 67 | Better Modlist | [Abrir `.md`](./Cosmetic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-better-modlist%20v21.1.1.md) | `21.1.1` | 21.1.5 (20/10) | **21.1.2/21.1.3/21.1.4/21.1.5 AUDITADAS EM RELEASES OFICIAIS; QA DE GUI SEPARADO** |
| 68 | Enhanced Boss Bars (Fabric/Forge/NeoForge) | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Cosmetic%20%2B%20Miscellaneous%20%2B%20Mobs/%E2%9C%85-enhanced-boss-bars%20v1.0.0.md) | `1.0.0` | 1.1.3 Release NeoForge 1.21.1 (22/10; file ID 9292243) | **1.1.3 DOCUMENTADA EM 24/10; DELTA EXCLUSIVO DO JAR 1.1.1 NEOFORGE 1.21.1 AINDA PENDENTE; FAIL-CLOSED** |
| 69 | BetterEnd: New Dawn | [Abrir `.md`](./Biomes%20%2B%20Cosmetic%20%2B%20Mobs%20%2B%20Structures%20%2B%20World%20Gen/%E2%9C%85-betterend-new-dawn%20v21.0.34.md) | `21.0.34` | 21.0.38 | **HISTÓRICO 21.0.35–.38 DETALHADO; COMPARAÇÃO BINÁRIA .37/.38 E DEPENDÊNCIAS NEW DAWN PENDENTES** |
| 70 | Create: Curios Backtank | [Abrir `.md`](./Addons%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Automation%20%2B%20Create/%E2%9C%85-create-curios-backtank%20v1.0.1.md) | `1.0.1` | 1.0.2 | **PESQUISA DOCUMENTAL CONCLUÍDA EM 23/10: RENDERIZAÇÃO E BÔNUS DE LAVA; QA IN-GAME PENDENTE** |
| 71 | Advanced Core Info (ACI) | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-advanced-core-info%20v1.1.0.md) | `1.1.0` | 1.5.0 Beta | **PESQUISA DOCUMENTAL 1.2–1.5 CONCLUÍDA EM 23/10; BETA NÃO PROMOVIDA, ABI/QA PENDENTES** |
| 72 | Create Deep Seas [BETA] | [Abrir `.md`](./Addons%20%2B%20Create/%E2%9C%85-create-deep-seas%20v2.2.4.md) | `2.2.4` | 3.3.0 Release; 3.4.5 Beta (22/10) | **PESQUISA 3.0.0–3.4.5 CONCLUÍDA EM 23/10; MIGRAÇÃO/QA DE RELEASE E BETA NÃO APROVADAS** |
| 73 | Cold Sweat | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Utility%20%26%20QoL/%E2%9C%85-cold-sweat%20v2.4.3.1.md) | `2.4.3.1` | 2.4.4 Release (16/10) | **PESQUISA DE CHANGELOG CONCLUÍDA EM 23/10; MIGRAÇÃO TÉRMICA/JEI E QA PENDENTES** |
| 74 | Knight Lib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-knight-lib%20v2.0.1.md) | `2.0.1` | 2.0.5 Release (15/10) | **PESQUISA DOCUMENTAL 2.0.2–2.0.5 CONCLUÍDA EM 23/10; API/QA COM COMPANIONS PENDENTES** |
| 75 | Create: Creating Space | [Abrir `.md`](./Create%20%2B%20Dimensions%20%2B%20Player%20Transport%20%2B%20Technology%20%2B%20World%20Gen/%E2%9C%85-creating-space%20v1.7.22.md) | `1.7.22` | 1.7.23 Release (21/10) | **PESQUISA DOCUMENTAL CONCLUÍDA EM 24/10: RELEASE TAG v1.21.1-1.7.23, JAR SHA-256 E PRS #130–132 IDENTIFICADOS; BACKLOG RENÚMERADO #17; HASH CROSS-PLATFORM E QA/PROMOÇÃO PENDENTES** |
| 76 | [EMF] Entity Model Features [Fabric & Forge] | [Abrir `.md`](./Cosmetic%20%2B%20Mobs%20%2B%20Utility%20%26%20QoL/%E2%9C%85-entity-model-features%20v3.3.5.md) | `3.3.5` | 3.3.12 Release (15/10) | **PESQUISA DOCUMENTAL 3.3.6–3.3.12 CONCLUÍDA EM 23/10; ETF 7.2.6 E QA GRÁFICO PENDENTES** |
| 77 | Vampirism Iron's Spells Compatibility | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Food%20%2B%20Magic/%E2%9C%85-vampire-spells-addon%20v1.21.1-0.0.9.md) | `1.21.1-0.0.9` | 0.0.10 Release (17/10) | **PESQUISA DOCUMENTAL CONCLUÍDA EM 23/10; CONFIGS HOLY/BLOOD SCHOOL DETALHADAS; QA E PROMOÇÃO PENDENTES** |
| 78 | Sophisticated Storage | [Abrir `.md`](./Storage/%E2%9C%85-sophisticated-storage%20v1.5.91.md) | `1.5.91` | 1.6.9.2199 Release (23/10) | **PESQUISA DOCUMENTAL 1.6.0–1.6.9 CONCLUÍDA EM 23/10; CORE ≥1.5.12, BACKPACKS/CREATE E QA ANTI-DUPE PENDENTES** |
| 79 | WunderLib: New Dawn | [Abrir `.md`](./API%20and%20Library%20%2B%20Utility%20%26%20QoL/%E2%9C%85-wunderlib-neoforge%20v21.0.10.md) | `21.0.10` | 21.0.11 Release (14/10) | **PESQUISA DOCUMENTAL CONCLUÍDA EM 23/10: BUMP/BUILD E METADATA DO SOURCE IDENTIFICADOS; BYTECODE E QA NEW DAWN PENDENTES** |
| 80 | Create: Ornithopter Glider | [Abrir `.md`](./Addons%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Create%20%2B%20Player%20Transport%20%2B%20Utility%20%26%20QoL/%E2%9C%85-create-ornithopter-glider%20v1.2.0-1.21.1.md) | `1.2.0-1.21.1` | 1.2.0+1.21.1 Release (03/06) | **REVALIDADO EM 23/10: SEM UPGRADE NEOFORGE 1.21.1; CHANGELOG DA VERSÃO FÍSICA CERTIFICADO; QA IN-GAME PENDENTE** |
| 81 | AmbientSounds 6 | [Abrir `.md`](./Miscellaneous/%E2%9C%85-ambientsounds-6%20v6.3.8.md) | `6.3.8` | 6.3.11 Release (19/10) | **PESQUISA DOCUMENTAL 6.3.9–6.3.11 CONCLUÍDA EM 23/10; CREATIVECORE/QA DE ÁUDIO PENDENTES** |
| 82 | Spell Codex: Iron's Spells 'n Spellbooks Addon | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Magic%20%2B%20Utility%20%26%20QoL/%E2%9C%85-spell-codex-irons-spells-n-spellbooks-addon%20v1.6.5.md) | `1.6.5` | 1.6.13 Release (19/10) | **PESQUISA DOCUMENTAL 1.6.6–1.6.13 CONCLUÍDA EM 23/10; ACTIONBAR 1.1.4 FÍSICA NÃO ATENDE MIN 1.1.7; QA/PROMOÇÃO PENDENTES** |
| 83 | Apothic Compats | [Abrir `.md`](./Addons%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons/%E2%9C%85-apothic-compats%20v0.2.4.2.md) | `0.2.4.2` | 0.2.6 Release NeoForge 1.21.1 (18/10) | **PESQUISA DOCUMENTAL 0.2.4.3–0.2.6 CONCLUÍDA EM 23/10; 0.2.4.3 ALINHADA A APOTHEOSIS 8.8.0; 0.2.5.2+ EXIGE RECONCILIAR STACK 8.9.x; QA/PROMOÇÃO PENDENTES** |

### Gates transversais — 22/10/2026

- **Easy NPC:** Bundle/Core/Config UI 7.17.0 e EME 2.6.0 passam a formar um candidato coordenado 1.21.1. O bloqueio de 13/10 por ausência de EME pública ≥2.5 deixou de existir, mas manifests/JarJar, persistência de NPC, networking, render, backups e multiplayer continuam obrigatórios. Conferir o histórico completo 7.12.2→7.17.0 nos três dossiers: 7.16 adiciona revive/shoulder/priority e exige EME 2.6.0 quando presente; 7.17 corrige persistência/saves, mudanças de dimensão e modifica Light Level padrão de 7 para 0, inclusive em NPCs preexistentes com valor default. A branch fonte consultada ainda indica 7.16.0; manifesto do JAR 7.17.0 não inspecionado.
- **Sophisticated:** Core 1.5.17.2426, Backpacks 3.26.14.2222 e Storage 1.6.8.2196 devem ser avaliados como stack; bridges/JEI/Ars/Thirst, linked storage, anti-dupe, chunk unload e save/restart impedem promoção isolada.
- **New Dawn:** BCLib 21.0.28, WorldWeaver 21.0.29, BetterNether 21.0.28, BetterEnd 21.0.38 e WunderLib 21.0.11 exigem backup e QA coordenado de registries/worldgen em mundos existentes e chunks novos.
- **ParCool / Thirst:** ParCool 4.0.1.6 e ThirstWasFixed 2.2.1 exigem revalidação conjunta de stamina API/attributes, configs, Epic Fight/Curios e dedicated server.
- **JEI / Polymorph+:** usar JEI 19.64.0.455 como candidato estável; 19.69.0.457 permanece Beta. Polymorph+ 1.4.1 deve ser testado junto por alterações explícitas de compatibilidade JEI 19.52+.
- **Pré-releases:** MineColonies snapshot, Relics/Lychee/Ecliptic/ALI/owo/Reliquified Iron/Cyclops/ACI betas, AzureLib alpha, Gadgets & Gizmos 2.0 pre-release e Create Deep Seas alpha não são baseline de produção.
- **Bloqueios de identidade:** #50 Automated Logistics não foi editado porque o dossier não possui `✅-`; #57 Epic Fight Curios 3.0 não tem arquivo 1.21.1 exato comprovado pela fonte consultada; #65 Skin Layers registra físico 1.11.2, enquanto a última publicação NeoForge 1.21.1 explicitamente comprovada é 1.10.1. Nenhum desses casos autoriza inferência ou rename.



## Auditoria cumulativa de versões — lote solicitado #01–#10 (22/10/2026)

> **Escopo: catálogos publicados até 22/10/2026 para Minecraft 1.21.1 / NeoForge, sem instalação ou smoke test físico.** Auditoria do histórico entre a versão do JAR físico documental e as releases públicas subsequentes; links/séries, mudanças funcionais, exceções específicas de Minecraft/loader e QA documentados dentro de cada dossier. Não alterar a autoridade das 587 linhas físicas, a versão no filename ou a posição ordinal. Os números #01–#10 abaixo são a **ordem do pedido**, não a posição física. O mod Easy NPC Bundle #02 já recebeu a revisão intermediária 7.12.2→7.17.0 pela PR #925; não foi reeditado no presente lote.

| Pedido | Mod | Instalada (snapshot) | Cadência auditada / referência upstream | Decisão documental |
|---:|---|---|---|---|
| 01 | MineColonies | 1.1.1387-snapshot | 1.1.1392, .1393, .1394, .1395, .1396, .1397, .1399, .1402, .1403-snapshot, **1.1.1403 Release** e **1.1.1405-snapshot** | Histórico fechado; Release 1403 candidata; 1405 snapshot Beta não aprovada |
| 02 | Easy NPC (Bundle) | 7.12.1 | 7.12.2, 7.13.0, 7.14.0, 7.15.0, 7.16.0 e **7.17.0** | Histórico já complementado pela PR #925; bundle/Core/UI/EME coordenados |
| 03 | Alex's Mobs Continued | 2.1.13 | 2.1.14, 2.2.0, 2.2.1, 2.2.2, 2.2.3 e **2.2.4** (Release) | Retificação: versão mais recente não é 2.1.13; fixes 26.3/Fabric segregados |
| 04 | Ars 'n Spells | 3.3.4 | 3.3.5, 3.3.6, **3.3.7** | Deltas já auditados; compatibilidade mana/Irons/Ars e versão client-server pendentes |
| 05 | Photon | 2.2.6.a | 2.2.7 e **2.2.8** | FX/dynamic lighting/volumetric fog e KilaMaterial rastreados; GPU/render QA pendente |
| 06 | Forgified Fabric API | 0.116.15+2.3.5+1.21.1 | **0.116.15+2.3.6+1.21.1** | Fix codec unwrapper mixin; ABI Connector/NeoForge não testada |
| 07 | BCLib: New Dawn | 21.0.26 | **21.0.27**, **21.0.28** | Compostagem, anvil recipes, voxel shapes e worldgen source pinados; stack New Dawn coordenado |
| 08 | InsaneLib | 2.4.33.0 | **2.4.33.1** | Fix de carregamento indevido de módulos cliente em servidor; dedicated QA pendente |
| 09 | Create: Teleporters | 2.0.2b (metadata 2.0.2) | 3.0, 3.0.1, **3.0.1b** | Teleporte de trains/Sable, portal configs e fixes; migração 2.x→3.x e saves bloqueados até QA |
| 10 | Petrolpark's Library | 1.5.11 | **1.5.12 Beta** | 1.5.11 permanece última Release estável; Beta JEI não autorizada para promoção |

**Concluído neste lote:** histórico de versões/candidatos e documentação das alterações relevantes anunciadas, sempre com indicação de evidência ausente. **Não concluído/fora de escopo:** baixar e comparar binários upstream, novos dumps de mods/, SHA-256 atual, smoke tests client/dedicated/multiplayer, migração de mundo, atualização física. CI documental não substitui esses gates.


## Auditoria incremental detalhada — lote solicitado 11–20 (22/10/2026)
Documentados na sequência da lista de pedido, não pela posição física da tabela superior. Fontes e changelogs relevantes estão em cada dossier. Todas as instalações continuam na versão histórica. QA, metadata do JAR upstream e migração de save não foram executados.
| Pedido | Projeto | Versão física | Publicação upstream NeoForge 1.21.1 | Estado da auditoria documental |
|---:|---|---|---|---|
| 11 | Punchy! - First person animations | 2.7e | 2.8g | Pasta CurseForge correta; 2.8 NeoForge 1.21.1 detalhada por release específica, 2.8d/e documentadas por Modrinth oficial; 2.8a HOTFIX sem delta exclusivo e 2.8f/g sem correspondência granular ao JAR NeoForge |
| 12 | Sophisticated Core | 1.5.1 | 1.5.17.2426 | PESQUISA DE CHANGELOG CONCLUÍDA: 1.5.15 cache de wrappers, 1.5.16 leftovers de crafting; commits modificam mod_version; sem análise de bytecode ou QA |
| 13 | Alex's Caves Continued | 1.0.10 | 1.0.10 | REVALIDADO; intervalo vazio |
| 14 | Puzzles Lib | 21.1.60 | 21.1.65 | Cadeia 21.1.61→21.1.65 revisada, callback encantado NeoForge |
| 15 | CBC: Advanced technologies crash fix | 1.0.1 (runtime 1.0.0) | 1.1.4 | Pasta corrigida em 23/10: Addons + Create + Technology; releases 1.1.0/1.1.1/1.1.2/1.1.4 documentadas integralmente; certificação documental concluída |
| 16 | Vanilla Backport | 1.1.7.10 | 1.2 | Rework amplo; migration/compatibilidades precisam de QA físico |
| 17 | Platform | 1.3.3 | 1.4 | APIs/Events overhaul documentados conforme granularidade real do release |
| 18 | FTB XMod Compat | 21.1.11 | 21.1.13 | Correção de JEI 21.1.12 e ruptura de SG Economy API 1.1+ em 21.1.13 |
| 19 | Amendments | 1.21-2.1.10 | 2.2.0 | 2.1.11 (Polytone) e 2.2.0 (wall/fence/pane, EBE, Iris) |
| 20 | Alshanex's Familiars | 1.21.1_v4.0.3 | 5.0 | 4.0.4 + Spectrum Update 5.0 e migração para FamiliarsLib, backup de save requerido |
**Regra de exclusão do Bloco de Notas:** itens 11, 12 e 15 permanecem pendentes por lacunas objetivas; os outros sete só podem ser retirados após merge, CI e re-fetch de `main`. Não confundir auditoria documental com instalação ou QA de runtime.


## Auditoria cumulativa por release — lote solicitado #21–#30 (22/10/2026)
Ordem abaixo é a do pedido, não a posição física. Este lote documenta somente releases **NeoForge Minecraft 1.21.1** publicadas e o changelog verificável até 22/10; não modifica JAR, versão instalada ou título dos dossiers. Itens com lacuna significativa no release-by-release são registrados como **BLOQUEADOS**, não certificados por inferência.
| Pedido | Mod | Instalada | Upstream / cadeia | Resultado da revisão documental |
|---:|---|---|---|---|
| 21 | Create:Tracks+ | 1.0.6b6 | Arquivo 9185485: exibe beta6 mas chama JAR 1.0.6b7 (Beta, 15/10) | **BLOQUEADO** — sem changelog ou diff do JAR |
| 22 | Relics | 0.12.8 | 0.12.9 Beta, 18/10 | Histórico documental fechado; candidatar Beta só com QA |
| 23 | Lychee [Neo/Forge] | 6.7.0 | 6.7.1 Beta, 17/10 | Histórico documental fechado; EMI remainders e ClientSideStrategy |
| 24 | EMF Compat: Core | 2.0.0 | 2.3.0, 2.3.1, 2.3.2 | **BLOQUEADO** — não foi isolado changelog/diff da 2.3.1 |
| 25 | Supplementaries | 3.9.9 | 4.0.0, 4.0.1, 4.0.2 | Histórico revisado por file NeoForge; risco pulley dupe, novos blocks e config |
| 26 | AzureLib | 3.1.11 | 3.1.12–3.1.22; 3.1.20-alpha.1 e 3.1.23-alpha.2 segregadas | **BLOQUEADO** — releases recentes ainda sem changelog individual |
| 27 | Easy NPC: Core | 7.12.1 (metadata runtime sem versão) | 7.12.2–7.17.0 | Histórico já auditado na PR #925; não refeito nem movido |
| 28 | Create Aeronautics: Gadgets & Gizmos | 1.2.2 | 1.2.3–1.2.10; 1.2.9 unsafe; linha 2.0.0 pre-release | **BLOQUEADO** — delta 1.2.6 individual ausente |
| 29 | WorldWeaver: New Dawn | 21.0.25 | 21.0.26–21.0.29 | **BLOQUEADO** — publicação confirmada sem changelogs individuados |
| 30 | ImmediatelyFast | 1.6.14+1.21.1 | 1.6.15 | Histórico documental fechado: Iron's spell cooldown render + tela Cosmetica |
**Regra de exclusão do bloco de notas deste lote:** somente **22, 23, 25, 27 e 30** podem sair **depois** de PR validada, mesclada e re-fetch da main. Manter **21, 24, 26, 28, 29** com bloqueios explícitos. Esta classificação acompanha documentação, **não significa que o mod foi fisicamente atualizado**.


## Auditoria cumulativa por release — lote solicitado #31–#40 (22/10/2026)
Esta matriz complementar preserva a numeração do pedido, distinta da ordem física dos 587 mods acima. Todas as alterações deste lote são documentais: nenhuma instalação, reordenação física, metadata local, JAR ou pasta foi alterada. Versões de outros Minecraft/loaders não são atribuídas à cadeia NeoForge 1.21.1.
| Pedido | Mod oficial | Físico documentado | Upstream verificado | Estado |
|---:|---|---|---|---|
| 31 | Advanced Loot Info (ALI) | 2.1.0 | 2.2.0, 2.3.0, 2.4.0, 2.5.0 Beta | FECHADO documental; Beta não instalada |
| 32 | owo (owo-lib) | 0.12.15.5-beta.1+1.21 | 0.12.15.6-beta.1+1.21 Beta | FECHADO documental; Beta não instalada |
| 33 | Minecolonies: Epicfied (Epic Colonies) | 21.0.8 | 21.0.9, 21.0.91, 21.1.0, 21.1.1, 21.1.2 | FECHADO documental; QA Epic Fight/MineColonies pendente |
| 34 | Easy Model Entities | 2.4.0 (metadata runtime ausente) | 2.5.0, 2.6.0 | FECHADO documental; homologar conjunto Easy NPC/EME |
| 35 | Polymorph+ | 1.3.1+1.21.1 | 1.3.2, 1.4.0 (source 1.21.1), 1.4.1 JAR NeoForge 1.21.1 | FECHADO documental por commits upstream 1.21.1; JAR 1.4.0 não individualizado, QA pendente |
| 36 | Sable: Ragdoll - Patch | 1.9 | 1.9.1 Release (19/10), cape alpha | FECHADO documental; retificado anterior sem bump |
| 37 | Eidolon : Repraised | 0.5.0.2 | 0.5.0.4, 0.5.0.5, 0.5.0.6, 0.5.0.7 | FECHADO documental; checar rituais locator/mana |
| 38 | [ETF] Entity Texture Features | 7.2.1 | 7.2.2, 7.2.3, 7.2.4, 7.2.5, 7.2.6 | FECHADO documental; render/shaders/EMF QA |
| 39 | Reliquified Ars Nouveau | 0.8.1 Beta | 0.8.1 Beta, sem release posterior para 1.21.1 | REVALIDADO; intervalo vazio |
| 40 | Create:LazyTick | 2.6.25-6.0.10 | 2.7.29, 2.7.30 / Create 6.0.10 | FECHADO documental: Modrinth changelog cumulativo lista 2.6.25→2.7.29→2.7.30; QA pendente |
**Regra para o Bloco de Notas, após CI+merge+re-fetch:** os **dez itens 31–40** podem sair da lista de pendências de auditoria **documental**, porque as publicações intermediárias são agora cobertas por changelog oficial ou commits upstream versionados da linha NeoForge 1.21.1. O JAR 1.4.0 Polymorph+ não foi individualizado como download: source 1.21.1 versionado é a evidência do delta. Esta revisão **não comprova atualização física, hashes de novos JARs, segurança de upgrade ou testes in-game**.


## Auditoria cumulativa por release — lote solicitado #41–#50 (22/10/2026)
Esta matriz segue a numeração do pedido, não a posição física da tabela de 587 mods. **Somente dossiers com ✅ e versão física explícita foram editados (#41–#49).** O dossier #50 SEM ✅ permanece intocado, sem promoção de elegibilidade. Toda versão nova é publicação upstream documental, não JAR instalado/homologado.
| Pedido | Mod | Versão física | Upstream NeoForge 1.21.1 | Auditoria documental |
|---:|---|---|---|---|
| 41 | Ecliptic Seasons | 0.15.0-rc-3-1 | 0.15.1→0.15.4.1 Beta | Fechada; 0.15.4 corrige plantio no inverno; 0.15.4.1 rebalanceia chuva |
| 42 | Fragmentum NeoForge | 2.4.4 | 5.0.0→5.1.1→5.2.0→5.3.0→5.4.0 | Fechada documental; breaking Kotlin/registries e opção Lite, migração física sem QA |
| 43 | ThirstWasFixed | 2.1.6 | 2.2.0→2.2.1 | Fechada documental; atributo ParCool e config Linux |
| 44 | FamiliarsLib | filename 1.7.1 / metadata 1.21.1-1.7 | 1.8 | Fechada documental; storage 10→50 e backup |
| 45 | Cyclops Core | 1.30.0 | 1.30.0-1205 Beta | SOURCE DIFF 1:1 (11/09→18/10): rótulos de tecla corrigidos nos infobooks, Beta e QA físico separado |
| 46 | Apotheosis x Iron's Spellbooks Compat | 2.2.2 | 2.2.3→2.2.8 | Fechada documental; affix triggers, datagen, casts e interrupts |
| 47 | Dynamic Trees - VanillaBackport | 1.7.0 | 1.7.0 | Revalidado sem release posterior; validar integração com VanillaBackport 1.2 |
| 48 | Polytone | 1.21-4.4.0 | 4.5.x→5.0.x→5.1.1→5.1.2→5.1.3→5.2.0 | Fechada segundo changelogs disponíveis; 5.1.3 descrita genericamente pelo autor |
| 49 | Just Enough Items (JEI) | 19.56.0.440 | 19.64.0.455 Release; 19.69.0.457 Beta | **BLOQUEADO**: .456 ampliada com changelog oficial NeoForge 1.21.1; .455 e .457 ainda sem vínculo exclusivo ao JAR exato |
| 50 | Create Aeronautics: Automated Logistics | 0.6.2 | 0.6.2 | **INELEGÍVEL** (dossier sem ✅); nenhum edit, move ou inclusão como certificado |
**Regra do Bloco de Notas após CI, merge e re-fetch:** liberar **41, 42, 43, 44, 46, 47 e 48** apenas como revisão do histórico documental. **Manter 45 e 49** até suprir fontes; **manter 50** até auditoria certificadora separada. QA físico/backup/migração não realizados.
