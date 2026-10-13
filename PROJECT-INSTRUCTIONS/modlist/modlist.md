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
| 006 | Advanced Core Info (ACI) | `AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar` | `1.1.0` | `API and Library` | 22/09/2026 - 13:40 |
| 007 | Advanced Loot Info (ALI) | `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar` | `2.1.0` | `Addons + API and Library + Map and Information` | 22/09/2026 - 13:40 |
| 008 | Advancement Plaques [Neo/Forge] | `AdvancementPlaques-1.21.1-neoforge-1.6.8.jar` | `1.6.8` | `Cosmetic + Map and Information + Miscellaneous + Utility & QoL` | 22/09/2026 - 13:40 |
| 009 | SnackPirate's Aeromancy Additions | `aero_additions-1.2.8.jar` | `1.2.8` | `Addons + Magic` | 22/09/2026 - 13:40 |
| 010 | Aeronautics Camera Sync | `aero_cam_sync-1.4.0.jar` | `1.4.0` | `Addons + Cosmetic + Create` | 22/09/2026 - 13:40 |
| 011 | Aeronautics Player Tilt | `aero_player_tilt-0.2.0.jar` | `0.2.0` | `Addons + Cosmetic + Create` | 18/09/2026 - 08:51 |
| 012 | Create Aeronautics and Connected Fluid Vessel Compatability | `aeroconnectedfluidvessel-1.0.0.jar` | `1.0.0` | `Create + Utility & QoL` | 18/09/2026 - 15:31 |
| 013 | Copycats+ aeronautics weight | `aerocopycats-1.1.1.jar` | `1.1.1` (distribuição/filename); metadata interna `1.1.0` | `Addons + Create + Utility & QoL` | 18/09/2026 - 15:31 |
| 014 | Create Aeronautics: Compatibility | `aeronauticscompat-1.1.3.jar` | `1.1.3` | `Addons + Create + Utility & QoL` | 18/09/2026 - 15:31 |
| 015 | Create: AeroStar Northstar Comp | `AeroStar-1.0.1.jar` | `1.0.1` | `Addons + Create` | 29/09/2026 - 02:57 |
| 016 | Create: Aeroworks | `aeroworks-1.5.0.jar` | `1.5.0` | `Addons + Create + Technology + Utility & QoL` | 18/09/2026 - 15:31 |
| 017 | AI Improvements: Performance Tuning | `AI-Improvements-1.21-0.5.3.jar` | `0.5.3` | `Mobs + Server Utility` | 18/09/2026 - 15:31 |
| 018 | Create Aeronautics: Alcubierre | `alcubierre-1.2.6.jar` | `1.2.6` | `Addons + Create` | 18/09/2026 - 15:31 |
| 019 | Alex's Caves Continued | `alexscaves-1.0.10-neoforge+1.21.1.jar` | `1.0.10` | `World Gen` | 16/09/2026 - 19:09 |
| 020 | Alex's Delight | `alexsdelight-1.6.jar` | `1.6` | `Addons + Food` | 18/09/2026 - 15:31 |
| 021 | Alex's Mobs Continued | `alexsmobs-2.1.13-neoforge+1.21.1.jar` | `2.1.13` | `Mobs` | 16/09/2026 - 19:09 |
| 022 | Almost Unified | `almostunified-neoforge-1.21.1-1.4.2.jar` | `1.21.1-1.4.2` | `API and Library + KubeJS + Ores and Resources + Processing + Server Utility` | 18/09/2026 - 15:31 |
| 023 | Alshanex's Familiars | `alshanex_familiars-1.21.1_v4.0.3.jar` | `1.21.1_v4.0.3` | `Addons + Magic + Mobs` | 18/09/2026 - 15:31 |
| 024 | Alternate Current | `alternate_current-mc1.21-1.9.0.jar` | `1.9.0` | `Redstone + Server Utility + Technology + Utility & QoL` | 19/09/2026 - 12:06 |
| 025 | AmbientSounds 6 | `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar` | `6.3.8` | `Miscellaneous` | 19/09/2026 - 12:06 |
| 026 | Amendments | `amendments-1.21-2.1.10-neoforge.jar` | `1.21-2.1.10` | `Cosmetic + Utility & QoL` | 19/09/2026 - 12:06 |
| 027 | Amplified Nether | `Amplified_Nether_26.2_v1.2.16.jar` | `1.2.16` | `Biomes + Dimensions + World Gen` | 19/09/2026 - 12:06 |
| 028 | Animal Husbandry | `AnimalHusbandry-neoforge-0.4.1.jar` | `0.4.1` | `Farming + Genetics + Mobs` | 19/09/2026 - 12:06 |
| 029 | Create: Apokinetics | `apokinetics-1.0.6.jar` | `1.0.6` | `Addons + Automation + Create + Magic + Technology` | 19/09/2026 - 12:06 |
| 030 | Apotheosis | `Apotheosis-1.21.1-8.8.0.jar` | `8.8.0` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 19/09/2026 - 12:06 |
| 031 | Apotheotic Creation | `apotheoticcreation-2.0.0.jar` | `2.0.0` | `Automation + Create + Storage + Utility & QoL` | 19/09/2026 - 12:06 |
| 032 | Apothic Category Compat | `apothic_compat-2.0.2.jar` | `2.0.2` | `Addons + Armor, Tools, and Weapons + Bug Fixes` | 19/09/2026 - 12:06 |
| 033 | Apothic Compats | `apothic_compats-0.2.4.2.jar` | `0.2.4.2` | `Addons + Armor, Tools, and Weapons` | 19/09/2026 - 12:06 |
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
| 046 | Ars 'n' Spells | `ars_n_spells-3.3.4.jar` | `3.3.4` | `Addons + Adventure and RPG + Magic + Utility & QoL` | 18/09/2026 - 08:51 |
| 047 | Ars Nouveau | `ars_nouveau-1.21.1-5.13.1.jar` | `5.13.1` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Magic + Mobs` | 19/09/2026 - 17:09 |
| 048 | Ars Polymorphia | `ars_polymorphia-1.0.3.jar` | `1.0.3` | `Addons + Magic + Storage + Utility & QoL` | 19/09/2026 - 17:10 |
| 049 | Ars Sable | `ars_sable-1.21.1-1.1.2.jar` | `1.1.2` | `Addons + API and Library + Magic + Utility & QoL` | 19/09/2026 - 17:11 |
| 050 | Ars Technica | `ars_technica-1.21.1-2.7.6.jar` | `2.7.6` | `Armor, Tools, and Weapons + Magic + Technology + Utility & QoL` | 23/09/2026 - 00:58 |
| 051 | Ars Nouveau: Two-Way Portals (with immersive portal support) | `ars_two_way_portals-2.0.0.jar` | `2.0.0` | `Addons` | 19/09/2026 - 17:10 |
| 052 | Ars Zero | `ars_zero-1.21.1-2.0.2.jar` | `2.0.2` | `Addons + Cosmetic + Magic + Technology` | 19/09/2026 - 17:10 |
| 053 | Ars Nouveau's Flavors & Delight | `arsdelight-2.2.2.jar` | `2.2.2` | `Addons + Farming + Food + Magic` | 19/09/2026 - 17:10 |
| 054 | Sophisticated Storage: Ars Compat \| Sophisticated Storage Ars-Nouveau Compatibility | `arssophisticatedcompat-0.3.0.jar` | `0.3.0` | `Magic + Storage + Utility & QoL` | 19/09/2026 - 18:06 |
| 055 | Artifacts | `artifacts-neoforge-13.2.5.jar` | `13.2.5` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Structures` | 19/09/2026 - 18:06 |
| 056 | Asterism Arcanum \| Addon for Iron's Spells and Spellbooks | `asterismarcanum-1.21.1-0.1.0.jar` | `1.21.1-0.1.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 18:06 |
| 057 | Athena | `athena-neoforge-1.21.1-4.0.6.jar` | `4.0.6` | `API and Library + Utility & QoL` | 19/09/2026 - 18:06 |
| 058 | Atlas API | `atlas_api-1.21.1-1.2.0.jar` | `1.21.1-1.2.0` | `API and Library` | 19/09/2026 - 18:06 |
| 059 | AttributeFix | `attributefix-neoforge-1.21.1-21.1.3.jar` | `21.1.3` | `Bug Fixes + Utility & QoL` | 19/09/2026 - 18:06 |
| 060 | Azimuth API | `azimuth-1.4.8.jar` | `1.4.8` | `Addons + API and Library + Create` | 18/09/2026 - 10:38 |
| 061 | AzureLib | `azurelib-neo-1.21.1-3.1.11.jar` | `3.1.11` | `API and Library` | 19/09/2026 - 18:06 |
| 062 | Create: Backpack Pixel | `backpack_pixel-1.2.0-neoforge-1.21.1.jar` | `1.2.0` | `Addons + Armor, Tools, and Weapons + Create + Storage + Technology` | 19/09/2026 - 18:06 |
| 063 | Backported Spells: Iron's Spells x Vanilla Backport | `backportedspellbooks-0.1.2.jar` | `0.1.2 (distribuição/filename); metadata runtime 0.1.0` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 19/09/2026 - 18:23 |
| 064 | Balm | `balm-neoforge-1.21.1-21.0.65.jar` | `21.0.65` | `API and Library` | 19/09/2026 - 21:35 |
| 065 | Battle Arts - API | `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar` | `21.17.7` | `Addons + API and Library` | 19/09/2026 - 21:35 |
| 066 | BCLib: New Dawn | `bclib-21.0.26.jar` | `21.0.26` | `API and Library + Biomes + Utility & QoL + World Gen` | 19/09/2026 - 21:35 |
| 067 | Create: Bells & Whistles | `bellsandwhistles-0.4.7-1.21.1.jar` | `0.4.7-1.21.1` | `Cosmetic + Create + Player Transport + Technology + Utility & QoL` | 19/09/2026 - 21:35 |
| 068 | Better Advanced Tooltips | `better-advanced-tooltips-2101.1.0-build.5.jar` | `2101.1.0-build.5` | `API and Library + Map and Information + Utility & QoL` | 19/09/2026 - 21:35 |
| 069 | Better Library | `better_lib-neoforge-1.21.1-1.0.111.jar` | `1.0.111` | `API and Library + Server Utility + Utility & QoL` | 19/09/2026 - 21:35 |
| 070 | Better Modlist | `better_modlist-21.1.1.jar` | `21.1.1` | `Cosmetic + Utility & QoL` | 19/09/2026 - 21:35 |
| 071 | BetterEnd: New Dawn | `BetterEnd-21.0.34.jar` | `21.0.34` | `Biomes + Cosmetic + Mobs + Structures + World Gen` | 19/09/2026 - 21:35 |
| 072 | Better Fps - Render Distance | `betterfpsdist-1.21.1-6.1.jar` | `6.1` | `Cosmetic + Miscellaneous` | 19/09/2026 - 21:46 |
| 073 | Epic Fight - Better lock on | `betterlockon-2.0.8-neoforge.jar` | `2.0.8-neoforge` | `Addons + Adventure and RPG` | 19/09/2026 - 21:35 |
| 074 | BetterNether: New Dawn | `BetterNether-21.0.26.jar` | `21.0.26` | `Biomes + Cosmetic + Mobs + Structures + World Gen` | 19/09/2026 - 23:37 |
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
| 090 | CBC: Advanced technologies crash fix | `cbcatfix-1.21.1-neoforge-1.0.1.jar` | `1.0.1 (distribuição/filename); metadata runtime 1.0.0` | `Addons + Create` | 23/09/2026 - 00:58 |
| 091 | CERBON's API [Forge \| Fabric \| NeoForge] | `CerbonsAPI-NeoForge-1.21-1.3.0.jar` | `1.3.0` | `API and Library` | 23/09/2026 - 00:58 |
| 092 | Chipped | `chipped-neoforge-1.21.1-4.0.2.jar` | `4.0.2` | `Cosmetic` | 23/09/2026 - 01:02 |
| 093 | Chunky (Forge/NeoForge) | `Chunky-NeoForge-1.4.23.jar` | `1.4.23` | `Dimensions + Server Utility + World Gen` | 23/09/2026 - 01:02 |
| 094 | Citadel | `citadel-2.7.1-1.21.1.jar` | `2.7.1` | `API and Library + Miscellaneous` | 23/09/2026 - 01:13 |
| 095 | Climbable Ropes for Create Aeronautics | `climbable_ropes-2.1.3.jar` | `2.1.3` | `Addons + Create` | 23/09/2026 - 01:02 |
| 096 | Clockwork | `clockwork-neoforge-1.21.1-1.1.4.jar` | `1.1.4` | `Cosmetic + Mobs + Technology` | 23/09/2026 - 01:02 |
| 097 | Cloth Config API (Fabric/Forge/NeoForge) | `cloth-config-15.0.140-neoforge.jar` | `15.0.140` | `API and Library` | 23/09/2026 - 01:02 |
| 098 | Clumps | `Clumps-neoforge-1.21.1-19.0.0.1.jar` | `19.0.0.1` | `Server Utility + Storage + Utility & QoL` | 23/09/2026 - 01:02 |
| 099 | Cobweb | `cobweb-neoforge-1.21-1.4.0.jar` | `1.4.0` | `API and Library` | 23/09/2026 - 01:02 |
| 100 | CodeChicken Lib 1.8.+ | `CodeChickenLib-1.21.1-4.6.1.529.jar` | `4.6.1.529` | `API and Library` | 23/09/2026 - 01:02 |
| 101 | Codxlib | `codxlib-1.6.0-neoforge+1.21.1.jar` | `1.6.0` | `Utility & QoL` | 23/09/2026 - 01:02 |
| 102 | Cold Sweat | `ColdSweat-2.4.3.1.jar` | `2.4.3.1` | `Adventure and RPG + Armor, Tools, and Weapons + Utility & QoL` | 23/09/2026 - 01:02 |
| 103 | Collective | `collective-1.21.1-8.39.jar` | `8.39` | `API and Library` | 23/09/2026 - 01:02 |
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
| 140 | Create: Curios Backtank | `create_curios_backtank-neoforge-1.21.1-1.0.1.jar` | `1.0.1` | `Addons + Armor, Tools, and Weapons + Automation + Create` | 23/09/2026 - 08:35 |
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
| 161 | Create: Stats & Power | `create_stats-1.13.1.jar` | `1.13.1` | `Addons + Create` | 23/09/2026 - 11:23 |
| 162 | Create Deep Seas | `create_submarine-2.2.4.jar` | `2.2.4` | `Addons + Create` | 23/09/2026 - 12:37 |
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
| 191 | Create:LazyTick | `CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar` | `2.6.25-6.0.10` | `Addons + Bug Fixes + Create` | 23/09/2026 - 23:51 |
| 192 | Create: Liquid Fuel | `createliquidfuel-3.0.0-1.21.1.jar` | `3.0.0-1.21.1` | `Addons + API and Library` | 24/09/2026 - 00:20 |
| 193 | Create: Mechanical Companion | `createmechanicalcompanion-1.9-neoforge-1.21.1.jar` | `1.9 (artefato/publicação); metadata runtime vazia` | `Addons + Adventure and RPG + Create + Mobs + Technology` | 24/09/2026 - 00:20 |
| 194 | Create: Metallurgy | `createmetallurgy-1.0.3-1.21.1.jar` | `1.0.3` | `Addons + Automation + Create + Processing + Technology` | 24/09/2026 - 00:20 |
| 195 | Create: Metalwork | `createmetalwork-2.0.0.jar` | `2.0.0` | `Automation + Create + Energy, Fluid, and Item Transport + Processing` | 24/09/2026 - 00:20 |
| 196 | Create Missiles | `createmissiles-1.0.3+neoforge-1.21.1.jar` | `1.0.3` | `Addons + Create + Map and Information + Structures + Technology` | 24/09/2026 - 00:20 |
| 197 | Create Nuclear | `createnuclear-1.3.2-beta.3-neoforge.jar` | `1.3.2-beta.3` | `Addons + Create + Energy + Ores and Resources + Technology` | 24/09/2026 - 00:20 |
| 198 | Create: Ornithopter Glider | `createornithopterglider-1.2.0-1.21.1.jar` | `1.2.0-1.21.1 (runtime/filename); publicação 1.2.0+1.21.1` | `Addons + Armor, Tools, and Weapons + Create + Player Transport + Utility & QoL` | 24/09/2026 - 00:20 |
| 199 | Create: Prismatic Shine | `createprism-1.2.2.jar` | `1.2.2` | `Addons + Create` | 24/09/2026 - 00:20 |
| 200 | Create Propulsion: Simulated | `createpropulsion-1.1.5.jar` | `1.1.5` | `Addons + Create + Player Transport + Technology` | 24/09/2026 - 00:20 |
| 201 | Create : radiologistics | `CreateRadiologistics-1.1.1.jar` | `1.1.1` | `Create` | 24/09/2026 - 00:20 |
| 202 | Create: Schematic Checker | `createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar` | `2.27.45-6.0` | `Addons + Bug Fixes + Create` | 24/09/2026 - 00:44 |
| 203 | Create : Teleporters | `createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar` | `2.0.2b (filename/publicação); metadata runtime 2.0.2` | `Create + Energy, Fluid, and Item Transport + Technology` | 24/09/2026 - 00:44 |
| 204 | Create: Transmission! | `createtransmission-1.2.2+neoforge-create6-1.21.1.jar` | `1.2.2+neoforge-create6-1.21.1` | `Addons + Cosmetic + Create + Technology + Utility & QoL` | 24/09/2026 - 00:44 |
| 205 | Create Ultimine | `createultimine-1.21.1-neoforge-1.3.2.jar` | `1.3.2` | `Addons + Create + Utility & QoL` | 24/09/2026 - 00:44 |
| 206 | Create : Creating Space | `creatingspace-1.21.1-1.7.22.jar` | `1.7.22` | `Create + Dimensions + Player Transport + Technology + World Gen` | 24/09/2026 - 00:44 |
| 207 | CreativeCore | `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar` | `2.13.46` | `API and Library` | 24/09/2026 - 00:44 |
| 208 | Crystal Chronicles - Iron's Spells 'n Spellbooks | `crystal_chronicles-0.1.3-alpha.jar` | `0.1.3-alpha` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Dimensions + Magic` | 24/09/2026 - 00:44 |
| 209 | Cuisine Delight | `cuisinedelight-1.2.10.jar` | `1.2.10` | `Food + Utility & QoL` | 24/09/2026 - 00:44 |
| 210 | Cupboard | `cupboard-1.21.1-4.1.jar` | `4.1` | `Utility & QoL` | 24/09/2026 - 00:44 |
| 211 | Curios API | `curios-neoforge-9.5.1+1.21.1.jar` | `9.5.1+1.21.1` | `Adventure and RPG + API and Library + Armor, Tools, and Weapons` | 24/09/2026 - 00:44 |
| 212 | Customizable Player Models | `CustomPlayerModels-1.21-0.6.27a.jar` | `0.6.27a` | `Cosmetic` | 24/09/2026 - 01:22 |
| 213 | Cyclops Core | `cyclopscore-1.21.1-neoforge-1.30.0.jar` | `1.30.0` | `API and Library + Server Utility` | 24/09/2026 - 01:22 |
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
| 231 | Dynamic Trees - VanillaBackport | `dtvanillabackport-1.21.1-1.7.0.jar` | `1.7.0` | `Addons + World Gen` | 24/09/2026 - 10:44 |
| 232 | Dynamic RPG Resource Bars | `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar` | `0.7.1` | `Adventure and RPG + API and Library + Cosmetic + Magic + Utility & QoL` | 24/09/2026 - 11:14 |
| 233 | Dynamic Brightness | `DynamicBrightness-neoforge-1.3.1.jar` | `1.3.1` | `Utility & QoL` | 24/09/2026 - 11:14 |
| 234 | Dynamic Trees Addon Lib | `DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar` | `0.2.0-BETA03` | `Addons + API and Library + Cosmetic` | 24/09/2026 - 11:14 |
| 235 | Dynamic Trees | `dynamictrees-neoforge-1.21.1-1.7.2.jar` | `1.7.2` | `Biomes + Cosmetic + Farming + World Gen` | 24/09/2026 - 11:14 |
| 236 | Dynamic Trees Plus | `DynamicTreesPlus-neoforge-1.21.1-1.3.2.jar` | `1.3.2` | `Addons + Biomes + Cosmetic + Farming + World Gen` | 24/09/2026 - 11:14 |
| 237 | Create: Dynamic Village | `dynamicvillage-0.9-1.21.1.jar` | `0.9` | `Cosmetic + Create + Structures + Technology + World Gen` | 24/09/2026 - 11:14 |
| 238 | Easy Model Entities | `easy_model_entities-neoforge-1.21.1-2.4.0.jar` | `2.4.0 do JAR; metadata runtime sem versão` | `Adventure and RPG + API and Library + Automation + Mobs + Utility & QoL` | 24/09/2026 - 11:14 |
| 239 | Easy NPC: Core | `easy_npc-neoforge-1.21.1-7.12.1.jar` | `7.12.1 do JAR; metadata runtime sem versão` | `Adventure and RPG + API and Library` | 24/09/2026 - 11:14 |
| 240 | Easy NPC | `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` | `7.12.1` | `Adventure and RPG + API and Library` | 24/09/2026 - 11:14 |
| 241 | Easy NPC: Config UI | `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` | `7.12.1` | `Utility & QoL` | 24/09/2026 - 11:14 |
| 242 | Ecliptic Seasons: MultiMod Patch | `Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.1.jar` | `0.32.1` | `Addons + Utility & QoL` | 24/09/2026 - 14:02 |
| 243 | Ecliptic Seasons | `EclipticSeasons-1.21.1-neoforge-0.15.0-rc-3-1.jar` | `0.15.0-rc-3-1` | `Adventure and RPG + Cosmetic + Farming + Utility & QoL` | 24/09/2026 - 14:02 |
| 244 | Ecliptic Seasons : Bundles | `EclipticSeasons-Bundles-0.18.0.2.jar` | `0.18.0.2` | `Addons + Utility & QoL` | 24/09/2026 - 14:02 |
| 245 | Epic Fight x Iron's Spells: Enhanced Animations | `efiscompat-3.1.0.jar` | `3.1.0` | `Cosmetic + Miscellaneous` | 24/09/2026 - 14:02 |
| 246 | Eidolon : Repraised | `eidolon_repraised-1.21.1-0.5.0.2.jar` | `0.5.0.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 14:02 |
| 247 | EMF Compat: Core | `emf_compat_core_1.21.1_2.0.0.jar` | `2.0.0` | `API and Library + Cosmetic` | 24/09/2026 - 14:02 |
| 248 | EMF Compat: Create | `emf_compat_create_1.21.1_2.0.0.jar` | `2.0.0` | `Bug Fixes + Cosmetic` | 24/09/2026 - 14:02 |
| 249 | EMF Compat: Iron's Spells 'n Spellbooks | `emf_compat_iron_spells_1.21.1_2.0.0.jar` | `2.0.0` | `Addons + Bug Fixes` | 24/09/2026 - 14:02 |
| 250 | Enchantment Descriptions | `enchdesc-neoforge-1.21.1-21.1.11.jar` | `21.1.11` | `Cosmetic + Magic + Map and Information + Utility & QoL` | 24/09/2026 - 14:02 |
| 251 | Ender's Delight | `endersdelight-1.3.1.jar` | `1.3.1` | `Addons + Farming + Food` | 24/09/2026 - 14:02 |
| 252 | Enhanced AI | `enhancedai-4.2.3.0.jar` | `4.2.3.0` | `Mobs` | 24/09/2026 - 16:02 |
| 253 | Enhanced Boss Bars | `enhancedbossbars-1.0.0.jar` | `1.0.0` | `Adventure and RPG + Cosmetic + Miscellaneous + Mobs` | 24/09/2026 - 16:02 |
| 254 | [EMF] Entity Model Features [Fabric & Forge] | `entity_model_features-3.3.5-1.21-neoforge.jar` | `3.3.5` | `Cosmetic + Mobs + Utility & QoL` | 24/09/2026 - 16:02 |
| 255 | [ESF] Entity Sound Features | `entity_sound_features-0.8.2-1.21-neoforge.jar` | `0.8.2` | `Cosmetic + Mobs + Utility & QoL` | 24/09/2026 - 16:02 |
| 256 | [ETF] Entity Texture Features - [Fabric & Forge] | `entity_texture_features-7.2.1-1.21-neoforge.jar` | `7.2.1` | `Cosmetic + Utility & QoL` | 24/09/2026 - 16:02 |
| 257 | Entity Culling Fabric/Forge | `entityculling-neoforge-1.10.5-mc1.21.1.jar` | `1.10.5` | `Miscellaneous + Utility & QoL` | 29/09/2026 - 02:57 |
| 258 | EpheroLib | `EpheroLib-1.21.1-NEO-FORGE-1.2.0.jar` | `1.2.0` | `API and Library` | 24/09/2026 - 16:02 |
| 259 | Epic Fight: Curios Compat 2.0 | `Epic Fight x Curios Compat 2.2.jar` | `2.2 (distribuição/filename); metadata runtime 1.4` | `Addons + Bug Fixes + Cosmetic` | 24/09/2026 - 16:02 |
| 260 | Epic Fight | `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar` | `21.17.3.1` | `Adventure and RPG + API and Library + Armor, Tools, and Weapons` | 24/09/2026 - 16:02 |
| 261 | Epic Fight - Epic-API | `epic_api-21.3.1.jar` | `21.3.1` | `API and Library` | 24/09/2026 - 16:02 |
| 262 | Minecolonies: Epicfied (Epic Colonies) | `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar` | `21.0.8` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Cosmetic` | 24/09/2026 - 16:58 |
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
| 273 | FamiliarsLib | `familiarslib-1.21.1-1.7.1.jar` | `1.7.1 do JAR/publicação; metadata runtime 1.21.1-1.7` | `API and Library` | 24/09/2026 - 17:35 |
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
| 286 | Forgified Fabric API | `forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar` | `0.116.15+2.3.5+1.21.1` | `Technology` | 24/09/2026 - 18:24 |
| 287 | Fragmentum [NeoForge Edition] | `fragmentum-neoforge-1.21.1-2.4.4.jar` | `2.4.4` | `API and Library` | 24/09/2026 - 18:24 |
| 288 | FTB Chunks (NeoForge) | `ftb-chunks-neoforge-2101.1.22.jar` | `2101.1.22` | `Map and Information + Server Utility` | 24/09/2026 - 18:24 |
| 289 | FTB Library (NeoForge) | `ftb-library-neoforge-2101.1.36.jar` | `2101.1.36` | `API and Library` | 24/09/2026 - 18:24 |
| 290 | FTB Quests (NeoForge) | `ftb-quests-neoforge-2101.1.36.jar` | `2101.1.36` | `Adventure and RPG + API and Library + Map and Information + Server Utility` | 24/09/2026 - 18:24 |
| 291 | FTB Teams (NeoForge) | `ftb-teams-neoforge-2101.1.11.jar` | `2101.1.11` | `API and Library` | 24/09/2026 - 18:24 |
| 292 | FTB Ultimine (NeoForge) | `ftb-ultimine-neoforge-2101.1.15.jar` | `2101.1.15` | `Armor, Tools, and Weapons + Server Utility` | 24/09/2026 - 19:05 |
| 293 | FTB XMod Compat | `ftb-xmod-compat-neoforge-21.1.11.jar` | `21.1.11` | `API and Library` | 24/09/2026 - 19:05 |
| 294 | Fusion (Connected Textures) | `fusion-1.3.15a-neoforge-mc1.21.1.jar` | `1.3.15+a` (metadata runtime); filename/release `1.3.15a` | `API and Library + Cosmetic` | 24/09/2026 - 19:05 |
| 295 | Fancy World Animations | `fwa+1.21.1-neoforge-1.2.31.jar` | `1.2.31` | `Cosmetic` | 24/09/2026 - 19:05 |
| 296 | Fzzy Config | `fzzy_config-0.7.7+1.21+neoforge.jar` | `0.7.7+1.21+neoforge` | `API and Library + Utility & QoL` | 24/09/2026 - 19:05 |
| 297 | Gabou's Libs | `gaboulibs-neoforge-1.9.jar` | `1.9` | `API and Library` | 24/09/2026 - 19:05 |
| 298 | Create Aeronautics: Gadgets & Gizmos | `gadgets-and-gizmos-bundled-V1.2.2.jar` | `1.2.2` | `Addons + Create` | 24/09/2026 - 19:05 |
| 299 | Create: Garnished | `garnished-2.1.9.2+1.21.1-neoforged.jar` | `2.1.9.2` | `Addons + Automation + Create + Food` | 24/09/2026 - 19:05 |
| 300 | Gaze - A Malum Addon | `gaze-1.1.7.1.jar` | `1.1.7.1` | `Addons` | 24/09/2026 - 19:05 |
| 301 | GeckoLib | `geckolib-neoforge-1.21.1-4.9.2.jar` | `4.9.2` | `API and Library` | 24/09/2026 - 19:05 |
| 302 | Global Packs | `globalpacks-neoforge-1.21.1-21.0.6.jar` | `21.0.6` | `Map and Information + Utility & QoL` | 24/09/2026 - 21:04 |
| 303 | Glodium | `Glodium-1.21-2.2-neoforge.jar` | `1.21-2.2-neoforge` | `API and Library` | 24/09/2026 - 21:04 |
| 304 | GroovyModLoader (GML) | `gml-6.0.2.jar` | `6.0.2` | `API and Library` | 24/09/2026 - 21:04 |
| 305 | Goety - The Dark Arts | `goety-3.1.4.jar` | `3.1.4` | `Adventure and RPG + Magic + Mobs + Structures` | 24/09/2026 - 21:04 |
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
| 326 | ImmediatelyFast | `ImmediatelyFast-NeoForge-1.6.14+1.21.1.jar` | `1.6.14+1.21.1` | `Performance + Utility & QoL` | 24/09/2026 - 22:53 |
| 327 | Immersive Aeronautics - Immersive Portals + Create: Aeronautics | `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar` | `1.1.4` | `Create` | 24/09/2026 - 22:53 |
| 328 | Immersive Furniture | `immersive_furniture-neoforge-0.3.3+1.21.1.jar` | `0.3.3+1.21.1` | `Cosmetic + Storage` | 24/09/2026 - 22:53 |
| 329 | Immersive Portal - Iron's Spells 'n Spellbooks Addon | `immersive_portal_irons_spells_n_spellbooks_addon-1.0.1.jar` | `1.0.1` | `Magic + Player Transport` | 24/09/2026 - 22:53 |
| 330 | Immersive Portals: True Immersion | `immersive_portals_true_immersion-2.0.4.jar` | `2.0.4` | `Addons` | 24/09/2026 - 22:53 |
| 331 | InsaneLib | `insanelib-2.4.33.0.jar` | `2.4.33.0` | `API and Library` | 24/09/2026 - 22:27 |
| 332 | Integrated API | `integrated_api-neoforge-1.21.1-1.8.2.jar` | `1.8.2` | `Adventure and RPG + API and Library + Structures + World Gen` | 24/09/2026 - 23:17 |
| 333 | Integrated Cataclysm | `integrated_cataclysm-1.0.6+1.21.1-neoforge.jar` | `1.0.6+1.21.1-neoforge` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Create + World Gen` | 24/09/2026 - 23:17 |
| 334 | Integrated Simply Swords | `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar` | `1.4.0+1.21.1-neoforge` | `Addons + Adventure and RPG + Armor, Tools, and Weapons` | 24/09/2026 - 23:18 |
| 335 | Integrated Stronghold | `integrated_stronghold-1.1.4+1.21.1-neoforge.jar` | `1.1.4+1.21.1-neoforge` | `Adventure and RPG + Armor, Tools, and Weapons + Create + Structures + World Gen` | 25/09/2026 - 00:26 |
| 336 | Integrated Villages | `integrated_villages-1.3.3+1.21.1-neoforge.jar` | `1.3.3+1.21.1-neoforge` | `Adventure and RPG + Create + Magic + Structures + World Gen` | 24/09/2026 - 23:21 |
| 337 | Inventory Sorter | `inventorysorter-1.21.1-24.0.24.jar` | `24.0.24` | `Miscellaneous` | 24/09/2026 - 23:21 |
| 338 | Iris Shaders | `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar` | `1.8.14-beta.1+mc1.21.1` | `Cosmetic + Miscellaneous` | 25/09/2026 - 00:26 |
| 339 | Apotheosis x Iron's Spellbooks Compat | `irons_apothic-2.2.2.jar` | `2.2.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 23:21 |
| 340 | Iron's Gems 'n Jewelry | `irons_jewelry-1.21.1-2.0.2.jar` | `1.21.1-2.0.2` | `Adventure and RPG + Armor, Tools, and Weapons + Magic` | 24/09/2026 - 23:22 |
| 341 | Iron's Lib | `irons_lib-1.21.1-2.1.0.jar` | `1.21.1-2.1.0` | `API and Library + Cosmetic` | 24/09/2026 - 23:22 |
| 342 | Iron's Spells 'n Spellbooks | `irons_spellbooks-1.21.1-3.16.3.jar` | `1.21.1-3.16.3` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs + Structures` | 25/09/2026 - 01:24 |
| 343 | KubeJS Iron's Spells | `irons_spells_js-4.0.3.jar` | `4.0.3` | `Addons + Adventure and RPG + KubeJS + Magic + Utility & QoL` | 25/09/2026 - 01:24 |
| 344 | IronSable | `ironsable-1.2.0.jar` | `1.2.0` | `Addons + Magic` | 25/09/2026 - 01:24 |
| 345 | IronSable X Wind's Spellbooks | `ironsable-wind-1.0.0.jar` | `1.0.0` | `Magic` | 25/09/2026 - 01:24 |
| 346 | Magic From The East | `iss_magicfromtheeast-1.1.5.jar` | `1.1.5` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic + World Gen` | 25/09/2026 - 01:24 |
| 347 | Jade 🔍 | `Jade-1.21.1-NeoForge-15.10.6.jar` | `15.10.6+neoforge` | `Map and Information + Utility & QoL` | 25/09/2026 - 01:24 |
| 348 | Just Enough Effect Descriptions (JEED) | `jeed-1.21-2.3.2.jar` | `1.21-2.3.2` | `Addons + Map and Information` | 25/09/2026 - 01:24 |
| 349 | Just Enough Items (JEI) | `jei-1.21.1-neoforge-19.56.0.440.jar` | `19.56.0.440` | `API and Library + Map and Information` | 25/09/2026 - 01:24 |
| 350 | JinxedLib | `jinxedlib-neoforge-1.21.1-1.0.4.jar` | `1.0.4` | `API and Library + Server Utility + Utility & QoL` | 25/09/2026 - 01:25 |
| 351 | JourneyMap Integration | `jmi-neoforge-1.21.1-1.9.jar` | `1.21.1-1.9` | `Addons + Map and Information` | 25/09/2026 - 01:25 |
| 352 | JourneyMap | `journeymap-neoforge-1.21.1-6.0.8.jar` | `1.21.1-6.0.8` | `Map and Information` | 25/09/2026 - 02:10 |
| 353 | Jupiter | `jupiter-2.3.7-1.21.1-neoforge.jar` | `2.3.7` | `API and Library + Server Utility + Utility & QoL` | 25/09/2026 - 02:10 |
| 354 | Just Enough Breeding (JEBr) | `justenoughbreeding-neoforge-1.21.1-3.2.1.jar` | `3.2.1` | `Addons + Map and Information + Mobs + Utility & QoL` | 25/09/2026 - 02:10 |
| 355 | Just Enough Professions (JEP) | `JustEnoughProfessions-neoforge-1.21.1-4.0.5.jar` | `4.0.5` | `Addons + Map and Information + Utility & QoL` | 25/09/2026 - 02:10 |
| 356 | Just Enough Resources (JER) | `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar` | `1.6.0.17` | `Addons + Biomes + Map and Information + Mobs + Ores and Resources` | 25/09/2026 - 02:10 |
| 357 | KilaGraph | `kilagraph-neoforge-1.21.1-21.1.0.14.jar` | `21.1.0.14` | `API and Library + Utility & QoL` | 25/09/2026 - 02:10 |
| 358 | Kiwi 🥝 (Neo/Forge) | `Kiwi-1.21.1-NeoForge-15.8.7.jar` | `15.8.7+neoforge` | `API and Library` | 25/09/2026 - 02:11 |
| 359 | Knight Lib | `knightlib-neoforge-1.21.1-2.0.1.jar` | `2.0.1` | `API and Library` | 25/09/2026 - 02:11 |
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
| 375 | Lithostitched | `lithostitched-1.8.0+beta6-neoforge-21.1.jar` | `1.8.0+beta6` | `API and Library + Miscellaneous + World Gen` | 25/09/2026 - 11:24 |
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
| 388 | Lychee (Neo/Forge) | `Lychee-1.21.1-NeoForge-6.7.0.jar` | `6.7.0+neoforge` | `Utility & QoL` | 25/09/2026 - 12:00 |
| 389 | Malum | `malum-1.21.1-1.8.2.jar` | `1.8.2` | `Armor, Tools, and Weapons + Cosmetic + Magic + Ores and Resources + World Gen` | 25/09/2026 - 12:00 |
| 390 | Max Health Fix | `maxhealthfix-neoforge-1.21.1-21.1.4.jar` | `21.1.4` | `Adventure and RPG + Armor, Tools, and Weapons + Bug Fixes + Server Utility + Utility & QoL` | 25/09/2026 - 12:00 |
| 391 | MineColonies: Jade crops addon | `mcjadecrops-1.1.1300.jar` | `1.1.1300` | `Addons + Farming + Utility & QoL` | 25/09/2026 - 12:00 |
| 392 | Mechanicals Lib | `mechanicals-1.21.1-1.1.6.jar` | `1.1.6` | `API and Library` | 25/09/2026 - 12:31 |
| 393 | Melody | `melody_neoforge_1.0.10_MC_1.21.jar` | `1.0.10` | `API and Library` | 25/09/2026 - 12:31 |
| 394 | MezzConfig | `mezz_config-1.21.1-neoforge-0.5.9.jar` | `0.5.9` | `API and Library` | 20/09/2026 - 01:38 |
| 395 | MineColonies | `minecolonies-1.1.1387-1.21.1-snapshot.jar` | `1.1.1387-1.21.1-snapshot` | `Adventure and RPG + Armor, Tools, and Weapons + Automation + Storage + Structures` | 16/09/2026 - 19:09 |
| 396 | Compatibility addon for MineColonies | `MineColonies_Compatibility-1.21.1-3.57.jar` | `3.57` | `Addons + Utility & QoL` | 17/09/2026 - 21:40 |
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
| 430 | oωo (owo-lib) | `owo-lib-neoforge-0.12.15.5-beta.1+1.21.jar` | `0.12.15.5-beta.1+1.21` | `API and Library` | 25/09/2026 - 14:28 |
| 431 | Ozymandias' Sundries | `ozymandias_sundries-0.0.5.jar` | `0.0.5 (distribuição/publicação); metadata interna 0.0.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 25/09/2026 - 14:28 |
| 432 | Paladin Spells - Iron's Spells Addon | `paladin_spells-1.21.1-1.1.1.jar` | `1.21.1-1.1.1` | `Addons + Magic` | 25/09/2026 - 15:17 |
| 433 | ParCool! ~ Minecraft Parkour ~ | `ParCool-1.21.1-4.0.0.3.jar` | `4.0.0.3` | `Adventure and RPG + Miscellaneous + Utility & QoL` | 25/09/2026 - 15:17 |
| 434 | Particle Effects | `ParticleEffects-1.5.0+1.21.1+neoforge.jar` | `1.5.0+1.21.1+neoforge` | `Cosmetic + Utility & QoL` | 25/09/2026 - 15:17 |
| 435 | Particle Rain | `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar` | `4.0.0-beta.11` | `Cosmetic` | 25/09/2026 - 15:17 |
| 436 | Particular ✨ Reforged | `particular-1.21.1-NeoForge-1.5.7.jar` | `1.5.7` | `Cosmetic` | 25/09/2026 - 15:17 |
| 437 | PartiCull: Particle Fix | `PartiCull-neoforge-1.21.1-v2.0.jar` | `2.0` | `Performance + Utility & QoL` | 25/09/2026 - 15:17 |
| 438 | Patchouli | `Patchouli-1.21.1-93-NEOFORGE.jar` | `1.21.1-93-NEOFORGE` | `API and Library` | 25/09/2026 - 15:17 |
| 439 | Pehkui | `Pehkui-3.8.3+1.21-neoforge.jar` | `3.8.3+1.21-neoforge` | `API and Library` | 25/09/2026 - 15:17 |
| 440 | Petrolpark's Library | `petrolpark-1.21.1-1.5.11.jar` | `1.5.11` | `Addons + API and Library + Create` | 17/09/2026 - 23:58 |
| 441 | Presence Footsteps x Sable (Aeronautics Compat) | `pfsable-1.0.jar` | `1.0` | `Cosmetic + Utility & QoL` | 25/09/2026 - 15:17 |
| 442 | Photon | `photon-neoforge-1.21.1-2.2.6.a-all.jar` | `2.2.6.a` | `API and Library + Magic + Technology + Utility & QoL` | 26/09/2026 - 02:32 |
| 443 | Pickable Health Orbs | `pickable_orbs-1.21.1-1.0.0.jar` | `1.21.1-1.0.0` | `Adventure and RPG + Magic + Utility & QoL` | 26/09/2026 - 02:32 |
| 444 | Placebo | `Placebo-1.21.1-9.9.2.jar` | `9.9.2` | `API and Library` | 26/09/2026 - 02:32 |
| 445 | Platform | `Platform-neoforge-1.21.1-1.3.3.jar` | `1.3.3` | `API and Library` | 26/09/2026 - 02:33 |
| 446 | playerAnimator | `player-animation-lib-forge-2.0.4+1.21.1.jar` | `2.0.4+1.21.1` | `API and Library + Cosmetic` | 26/09/2026 - 02:33 |
| 447 | Player Animation Library | `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar` | `1.1.6+mc.1.21.1` | `API and Library` | 26/09/2026 - 02:33 |
| 448 | Polymorph+ | `polymorph_plus-neoforge-1.3.1+1.21.1.jar` | `1.3.1+1.21.1` | `Miscellaneous + Utility & QoL` | 26/09/2026 - 02:33 |
| 449 | Polytone | `polytone-1.21-4.4.0-neoforge.jar` | `1.21-4.4.0` | `Cosmetic + Utility & QoL` | 26/09/2026 - 02:34 |
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
| 465 | Punchy! - First person animations | `punchy-2.7e-neoforge-1.21.1.jar` | `2.7e` | `Cosmetic + Map and Information` | 27/09/2026 - 16:30 |
| 466 | Epic Fight X Punchy! Neo | `punchy_epicfight_neoforge.jar` | `1.0.0` | `Addons` | 27/09/2026 - 16:30 |
| 467 | Puzzles Lib | `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar` | `21.1.60` | `API and Library` | 27/09/2026 - 16:30 |
| 468 | Quark | `Quark-4.1-484.jar` | `4.1-484` | `Cosmetic + Ores and Resources` | 27/09/2026 - 16:30 |
| 469 | Ragdoll Reactions | `ragdoll_reactions-1.21.1-0.7.0.jar` | `0.7.0` | `Addons + Cosmetic` | 27/09/2026 - 16:30 |
| 470 | Steam 'n' Rails Neoforge | `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar` | `0.3.0-beta.2+neoforge-mc1.21.1` | `Addons + Cosmetic + Create + Player Transport + Technology` | 27/09/2026 - 16:30 |
| 471 | Rechiseled | `rechiseled-1.2.6-neoforge-mc1.21.jar` | `1.2.6` | `Cosmetic` | 27/09/2026 - 16:30 |
| 472 | Iron's Spells 'n Spellbooks: Recolor | `recolor_tablet-1.3.3+1.21.1.jar` | `1.3.3+1.21.1` | `Cosmetic + Magic` | 27/09/2026 - 16:30 |
| 473 | Reese's Sodium Options | `reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar` | `2.2.3+mc1.21.1` | `Miscellaneous` | 27/09/2026 - 17:03 |
| 474 | Relics | `relics-1.21.1-0.12.8.jar` | `0.12.8` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Magic + Ores and Resources` | 27/09/2026 - 17:03 |
| 475 | Reliquified L_Ender 's Cataclysm new relics fix | `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar` | `1.0.2` | `Addons + Bug Fixes + Server Utility` | 27/09/2026 - 17:03 |
| 476 | Reliquified Ars Nouveau | `reliquified_ars_nouveau-1.21.1-0.8.1.jar` | `0.8.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 17:03 |
| 477 | Reliquified Artifacts | `reliquified_artifacts-1.21.1-1.0.8.jar` | `1.0.8` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 17:03 |
| 478 | Reliquified Iron's Spells 'n Spellbooks | `reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar` | `0.2.7` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 17:03 |
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
| 491 | Sable: Ragdoll - Patch | `sable_player_ragdoll_patch-1.21.1-1.9.jar` | `1.9` | `Bug Fixes` | 27/09/2026 - 17:33 |
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
| 506 | Skin Layers 3D | `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar` | `1.11.2` | `Adventure and RPG + Cosmetic` | 27/09/2026 - 19:01 |
| 507 | Sky Aesthetics | `sky_aesthetics-neoforge-2.0.13-beta.jar` | `2.0.13-beta` | `API and Library + Cosmetic + Dimensions + Utility & QoL` | 27/09/2026 - 19:01 |
| 508 | Create Slice & Dice | `sliceanddice-4.3.3-neoforge.jar` | `4.3.3` | `Addons + Farming + Technology` | 27/09/2026 - 19:01 |
| 509 | Create: Smart Bounds | `smart_bounds-1.0.0.jar` | `1.0.0` | `Bug Fixes + Create + Performance + Technology` | 27/09/2026 - 19:02 |
| 510 | SmartBrainLib (Forge/Fabric/Quilt) | `SmartBrainLib-neoforge-1.21.1-1.16.11.jar` | `1.16.11` | `API and Library` | 27/09/2026 - 19:02 |
| 511 | Snow! Real Magic! ⛄ (Neo/Forge) | `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar` | `12.2.2+neoforge` | `Cosmetic + Utility & QoL + World Gen` | 27/09/2026 - 19:02 |
| 512 | Sodium | `sodium-neoforge-0.8.13+mc1.21.1.jar` | `0.8.13+mc1.21.1` | `Performance` | 27/09/2026 - 20:56 |
| 513 | Somake Spells - Iron's Spells Addon | `somakespells-1.0.9-1.21.1.jar` | `1.0.9` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` | 27/09/2026 - 20:56 |
| 514 | Thirst Upgrade for Sophisticated Backpacks (Thirst Was Taken) | `sophisticated-thirst-upgrade.jar` | `0.1.8` | `Miscellaneous` | 27/09/2026 - 21:19 |
| 515 | Sophisticated JEI Index | `sophisticated_jei_index-1.2.3+1.21.1.jar` | `1.2.3` | `Addons + Applied Energistics 2 + Refined Storage + Storage` | 27/09/2026 - 20:56 |
| 516 | Sophisticated Backpacks | `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar` | `3.26.3` | `Armor, Tools, and Weapons + Storage` | 27/09/2026 - 20:56 |
| 517 | Sophisticated Backpacks Create Integration | `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar` | `0.2.0` | `Addons + Storage` | 27/09/2026 - 21:19 |
| 518 | Sophisticated Core | `sophisticatedcore-1.21.1-1.5.1.2341.jar` | `1.5.1` | `API and Library` | 27/09/2026 - 21:19 |
| 519 | Sophisticated Storage | `sophisticatedstorage-1.21.1-1.5.91.2127.jar` | `1.5.91` | `Storage` | 27/09/2026 - 21:26 |
| 520 | Sophisticated Storage Create Integration | `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar` | `0.1.21` | `Addons + Storage` | 27/09/2026 - 21:19 |
| 521 | Soul fire'd | `soul-fire-d-neoforge-1.21-6.1.0.jar` | `6.1.0` | `Adventure and RPG + Armor, Tools, and Weapons + Cosmetic + Utility & QoL` | 27/09/2026 - 20:57 |
| 522 | Sound Physics: Aeronautics | `sound-physics-remastered-neoforge-1.4.0.1.jar` | `1.4.0.1` | `Create + Utility & QoL` | 27/09/2026 - 22:12 |
| 523 | Sounds | `sounds-2.4.22+lts+1.21.1-neoforge.jar` | `2.4.22+lts` | `Adventure and RPG + Cosmetic + Utility & QoL` | 27/09/2026 - 22:12 |
| 524 | spark | `spark-1.10.124-neoforge.jar` | `1.10.124` | `Server Utility` | 27/09/2026 - 22:12 |
| 525 | Spell Codex: Iron's Spells 'n Spellbooks Addon | `specs_irons_spellbooks-1.6.5.jar` | `1.6.5` | `Addons + Adventure and RPG + Magic + Utility & QoL` | 27/09/2026 - 22:12 |
| 526 | SpellActionbar | `spell_actionbar-1.1.4.jar` | `1.1.4` | `Addons + Adventure and RPG + Magic + Utility & QoL` | 27/09/2026 - 22:12 |
| 527 | StarbuncleMania | `starbunclemania-1.21.1-1.5.8.jar` | `1.5.8` | `Addons + Automation + Energy, Fluid, and Item Transport + Magic` | 27/09/2026 - 22:12 |
| 528 | Streams Reflowing | `StreamsReflowing-1.21.1-neoforge-2.13.5.jar` | `2.13.5` | `Biomes + Cosmetic + World Gen` | 27/09/2026 - 22:12 |
| 529 | Structurize | `structurize-1.0.833-1.21.1-snapshot.jar` | `1.0.833-1.21.1-snapshot` | `Cosmetic + Server Utility + Structures` | 27/09/2026 - 22:12 |
| 530 | Strut Your Stuff (Struts) | `struts-1.3.1.jar` | `1.3.1` | `API and Library` | 27/09/2026 - 22:12 |
| 531 | Stylish Effects | `StylishEffects-v21.1.3-1.21.1-NeoForge.jar` | `21.1.3` | `Cosmetic + Map and Information + Utility & QoL` | 27/09/2026 - 22:12 |
| 532 | Create: Deep Seas - Lava Fix | `submarinefix-1.0.1.jar` | `1.0.1` | `Bug Fixes + Create` | 27/09/2026 - 22:58 |
| 533 | Subtle Effects | `SubtleEffects-neoforge-1.21.1-1.14.3.jar` | `1.14.3` | `Cosmetic + Miscellaneous` | 27/09/2026 - 22:58 |
| 534 | SuperMartijn642's Config Lib | `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar` | `1.1.8` | `API and Library` | 27/09/2026 - 22:58 |
| 535 | SuperMartijn642's Core Lib | `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar` | `1.1.24` | `API and Library` | 27/09/2026 - 22:58 |
| 536 | Supplementaries | `supplementaries-1.21.1-3.9.9-neoforge.jar` | `1.21.1-3.9.9` | `Cosmetic + Miscellaneous + Redstone + Storage` | 27/09/2026 - 23:18 |
| 537 | Tectonic | `tectonic-3.0.28-neoforge-21.1.jar` | `3.0.28` | `World Gen` | 27/09/2026 - 23:18 |
| 538 | TenshiLib (Forge/NeoForge) | `tenshilib-1.21.1-2.3.0.b-neoforge.jar` | `1.21.1-2.3.0.b-neoforge` | `API and Library` | 27/09/2026 - 23:18 |
| 539 | TerraBlender (NeoForge) | `TerraBlender-neoforge-1.21.1-4.1.0.8.jar` | `4.1.0.8` | `API and Library + Biomes + World Gen` | 27/09/2026 - 22:58 |
| 540 | Terralith | `Terralith_1.21.x_v2.6.2.jar` | `2.6.2` | `Biomes + Structures + World Gen` | 27/09/2026 - 23:18 |
| 541 | Create: TFMG Community Edition | `tfmg-1.21.1-1.3.1-community.jar` | `1.3.1-community` | `Addons + Armor, Tools, and Weapons + Create + Ores and Resources + Technology` | 27/09/2026 - 22:58 |
| 542 | ThirstWasFixed | `thirstwasfixed-2.1.6.jar` | `2.1.6` | `Addons + Bug Fixes` | 28/09/2026 - 00:04 |
| 543 | Thirst Was Reclaimed | `ThirstWasReclaimed-1.21.1-3.0.5.jar` | `1.21.1-3.0.5` | `Food` | 28/09/2026 - 00:04 |
| 544 | Corail Tombstone | `tombstone-neoforge-1.21.1-9.5.6.jar` | `9.5.6` | `Adventure and RPG + Magic + Mobs + Utility & QoL` | 28/09/2026 - 00:04 |
| 545 | Tom's Simple Storage Mod | `toms_storage-1.21-2.4.2.jar` | `2.4.2` | `Storage + Technology` | 28/09/2026 - 00:04 |
| 546 | TownTalk | `towntalk-1.2.0.jar` | `1.2.0` | `Addons + Adventure and RPG + Cosmetic + Utility & QoL` | 28/09/2026 - 00:27 |
| 547 | Toxony | `toxony-0.10.7.jar` | `0.10.7` | `Adventure and RPG + Armor, Tools, and Weapons + Genetics + Magic` | 28/09/2026 - 00:27 |
| 548 | Create:Tracks+ | `tracks_plus-1.0.6b6.jar` | `1.0.6b6` | `Create` | 28/09/2026 - 00:04 |
| 549 | Transmog | `transmog-neoforge-1.6.0+1.21.1.jar` | `1.6.0` | `Armor, Tools, and Weapons + Cosmetic` | 28/09/2026 - 00:04 |
| 550 | T.O Magic 'n Extras - Iron's Spells Addon | `traveloptics-4.4.0.1-1.21.1.jar` | `4.4.0.1-1.21.1` | `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 28/09/2026 - 00:04 |
| 551 | Sable: True Impact (For Create: Aeronautics) | `true_impact-0.5.7-delta.jar` | `0.5.7-delta` | `Create + Technology` | 28/09/2026 - 00:04 |
| 552 | Tunes n' Tomes: a bards journey | `tunes_n_tomes-1.1.0-HOTFIX.jar` | `1.1.0-HOTFIX` | `Addons + Magic` | 28/09/2026 - 01:09 |
| 553 | UnChipped | `unchipped-1.21-1.2.jar` | `1.21-1.2` | `Addons + Automation + Create + Processing` | 28/09/2026 - 01:09 |
| 554 | Underground Villages, Stoneholm (Fabric/Forge/NeoForge) | `underground_village-neoforge-1.21.1-2.0.jar` | `2.0` | `Biomes + Structures + World Gen` | 28/09/2026 - 01:09 |
| 555 | Uranus | `uranus-3.0-beta.1.jar` | `3.0-beta.1` | `API and Library + Utility & QoL` | 28/09/2026 - 01:09 |
| 556 | Vampirism Iron's Spells Compatibility | `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar` | `1.21.1-0.0.9` | `Addons + Adventure and RPG + Food + Magic` | 28/09/2026 - 01:28 |
| 557 | Vampiric Ageing - A Vampirism Addon | `vampiricageing-1.21-1.4.21.jar` | `1.21-1.4.21` | `Magic` | 28/09/2026 - 01:09 |
| 558 | Vampirism - Become a vampire! | `Vampirism-1.21-1.10.13.jar` | `1.10.13` | `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs` | 28/09/2026 - 01:09 |
| 559 | Vampirism Integrations | `vampirism_integrations-1.21.1-1.10.2.jar` | `1.10.2` | `Magic + Miscellaneous` | 28/09/2026 - 01:09 |
| 560 | Vanilla Backport | `VanillaBackport-neoforge-1.21.1-1.1.7.10.jar` | `1.1.7.10` | `Armor, Tools, and Weapons + Biomes + Mobs + Ores and Resources + World Gen` | 28/09/2026 - 01:09 |
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
| 572 | WorldWeaver: New Dawn | `worldweaver-21.0.25.jar` | `21.0.25` | `API and Library + Biomes + Utility & QoL + World Gen` | 28/09/2026 - 08:36 |
| 573 | WunderLib: New Dawn | `wunderlib-21.0.10.jar` | `21.0.10` | `API and Library + Utility & QoL` | 28/09/2026 - 08:36 |
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

- **#054** — Sophisticated Storage: Ars Compat / `arssophisticatedcompat-0.3.0.jar`: abrir/fechar containers, capability, recipes e interação com Ars Nouveau e Storage 1.6.x.
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
| 01 | Easy NPC (bundle) | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library/%E2%9C%85-easy-npc-bundle%20v7.12.1.md) | `7.12.1` | `7.15.0` | BLOQUEADO 7.15.0; 7.14.0 condicional: manifestos Core/UI declaram EME opcional somente em `[2.5.0,3.0.0)`; versão física 2.4.0 incompatível |
| 02 | Create: Teleporters | [Abrir `.md`](./Create%20%2B%20Energy%2C%20Fluid%2C%20and%20Item%20Transport%20%2B%20Technology/%E2%9C%85-create-teleporters-remastered%20v2.0.2b.md) | `2.0.2b (filename); metadata 2.0.2` | `2.0.2b` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 03 | Sophisticated Backpacks Create Integration | [Abrir `.md`](./Addons%20%2B%20Storage/%E2%9C%85-sophisticated-backpacks-create-integration%20v0.2.0.md) | `0.2.0` | `0.2.3.194` | PENDENTE: promoção coordenada do ecossistema Sophisticated |
| 04 | Sophisticated Core | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-sophisticated-core%20v1.5.1.md) | `1.5.1` | `1.5.11.2394` | PENDENTE: ABI e dependências Sophisticated |
| 05 | VS / Sable Hose Connectors | [Abrir `.md`](./Addons%20%2B%20Create%20%2B%20Industrial%20Craft%20%2B%20Storage%20%2B%20Technology/%E2%9C%85-vs-sable-hose-connectors%20v0.1.8.md) | `0.1.8` | `0.2.2` | PENDENTE: novas interações sem wrench, config e transferência atômica |
| 06 | Placebo | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-placebo%20v9.9.2.md) | `9.9.2` | `9.9.4` | PENDENTE: consumer linkage com Apotheosis/Apothic |
| 07 | Ace's Spell Utils | [Abrir `.md`](./Addons%20%2B%20API%20and%20Library/%E2%9C%85-aces-spell-utils%20v1.2.7.2-1.21.1.md) | `1.2.7.2-1.21.1` | `1.2.7.2-1.21.1` | REVALIDADO: sem nova release 1.21.1 demonstrada |
| 08 | Supplementaries | [Abrir `.md`](./Cosmetic%20%2B%20Miscellaneous%20%2B%20Redstone%20%2B%20Storage/%E2%9C%85-supplementaries%20v1.21.1-3.9.9.md) | `1.21.1-3.9.9` | `4.0.1` | PENDENTE: migração entre linhas 3.x/4.x; Moonlight/Amendments |
| 09 | AzureLib | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-azurelib%20v3.1.11.md) | `3.1.11` | `3.1.19 Release; 3.1.20-alpha.1 pré-release` | PENDENTE: alpha não equivale a estabilidade |
| 10 | Easy NPC: Core | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library/%E2%9C%85-easy-npc%20v7.12.1.md) | `7.12.1 (JAR); metadata sem versão` | `7.15.0` | BLOQUEADO 7.15.0; 7.14.0 condicional: EME presente 2.4.0 não satisfaz intervalo opcional `[2.5.0,3.0.0)` |
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
| 24 | Sophisticated Backpacks | [Abrir `.md`](./Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Storage/%E2%9C%85-sophisticated-backpacks%20v3.26.3.md) | `3.26.3` | `3.26.12.2208` | PENDENTE: atualizar com Core, Storage e bridges; anti-dupe |
| 25 | Easy NPC: Config UI | [Abrir `.md`](./Utility%20%26%20QoL/%E2%9C%85-easy-npc-config-ui%20v7.12.1.md) | `7.12.1` | `7.15.0` | BLOQUEADO 7.15.0; 7.14.0 condicional: dependência EME opcional com validação de versão `[2.5.0,3.0.0)` |
| 26 | Crystal Chronicles — Iron's Spells | [Abrir `.md`](./Addons%20%2B%20Adventure%20and%20RPG%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons%20%2B%20Dimensions%20%2B%20Magic/%E2%9C%85-crystal-chronicles%20v0.1.3-alpha.md) | `0.1.3-alpha` | `0.1.4-alpha` | PENDENTE: versão alpha, magia e worldgen |
| 27 | BlockUI | [Abrir `.md`](./API%20and%20Library/%E2%9C%85-blockui%20v1.0.212-1.21.1-snapshot.md) | `1.0.212-1.21.1-snapshot` | `1.0.212-1.21.1-snapshot` | REVALIDADO: possível republicação; conferir file ID/SHA, sem bump |
| 28 | Sophisticated Storage Create Integration | [Abrir `.md`](./Addons%20%2B%20Storage/%E2%9C%85-sophisticated-storage-create-integration%20v0.1.21.md) | `0.1.21` | `0.1.23.246` | PENDENTE: bridge coordenada, dupe/loss |
| 29 | Integrated API | [Abrir `.md`](./Adventure%20and%20RPG%20%2B%20API%20and%20Library%20%2B%20Structures%20%2B%20World%20Gen/%E2%9C%85-integrated-api%20v1.8.2.md) | `1.8.2` | `1.9.0` | PENDENTE: consumers Integrated Structures e registries worldgen |
| 30 | Sophisticated Storage | [Abrir `.md`](./Storage/%E2%9C%85-sophisticated-storage%20v1.5.91.md) | `1.5.91` | `1.6.4.2169` | PENDENTE: API/storage persistence; upgrade coordenado |

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
