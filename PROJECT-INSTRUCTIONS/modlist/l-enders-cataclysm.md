# L_Ender's Cataclysm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815d848eee2537023b72
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Cataclysm `3.33`, Lionfish API `3.1` e Curios `9.5.1+1.21.1` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** L_Ender's Cataclysm
- **Arquivo JAR:** `L_Ender's Cataclysm 1.21.1-3.33.jar`
- **Versão 1.21.1:** 3.33
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração, Mobs, RPG
- **Função:** Provider grande de aventura/endgame: estruturas e dungeons, mobs/bosses, AI/combate, projéteis/áreas, loot, materiais, equipamentos, locators e blocos funcionais.
- **Dependências:** Required: Lionfish API + Curios API. No runtime físico: Lionfish API 3.1 e Curios 9.5.1+1.21.1; source 1.21 do Cataclysm 3.33 usa a linha NeoForge 1.21.1.
- **Sobreposição:** Base authority para seus bosses/estruturas/loot. Addons físicos `cataclysm_spellbooks`, `cataclysmfortresses`, `goety_cataclysm`, `ignissoulfires` e `integrated_cataclysm` expandem/integram; não substituem o mod-base.
- **Compatibilidade/Riscos:** Alto impacto em worldgen, boss lifecycle, loot e atributos. Riscos: worldgen overlap, double-damage/effects, loot inflation, attribute/Curios stacking, chunk unload duplication e version drift de addons. Addons devem usar Cataclysm como authority.
- **Observações:** Source oficial usa repositório histórico `lender544/new1.20.1`, mas a branch correta da build instalada é `1.21`; `gradle.properties` dessa branch declara MC 1.21.1 e mod 3.33. Não usar `master`/1.20.1 como equivalente.
- **Procedência:** modlist.txt física atual + CurseForge/Modrinth oficiais Cataclysm 3.33 + source oficial branch `1.21` (`gradle.properties`, ModEntities, ModItems, ModBlocks, worldgen/structure).
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lendercataclysm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source branch 1.21/3.33 pinado; estruturas, entidades, combat entities, loot/equipment, configs/components, lifecycle, integrações e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `L_Ender's Cataclysm 1.21.1-3.33.jar`, mod id `cataclysm`, versão `3.33`, Minecraft 1.21.1/NeoForge. É um provider grande de **worldgen, mobs/bosses, combate, loot e equipamento endgame**.

## 1. Identidade e source pin
A modlist física confirma JAR, mod id `cataclysm`, versão 3.33 e `cataclysm.mixins.json`. CurseForge/Modrinth confirmam a release 3.33 para NeoForge 1.21.1 em 22/08/2026. O repositório oficial mantém o nome histórico `new1.20.1`, porém a branch **`1.21`** é a linha correta desta build; nela `gradle.properties` declara Minecraft 1.21.1, Cataclysm 3.33 e NeoForge 21.1.x. Não usar `master`/1.20.1 como source equivalente ao binário atual.

## 2. Papel no modpack e authority
Cataclysm é authority de suas estruturas, spawn rules, entidades, boss AI/phases, projéteis/áreas de ataque, materiais, equipamentos, dungeon-locator items, loot e configs próprias. Addons instalados devem integrar esses contratos; não criar uma segunda authority de boss health, loot, cooldown ou spawn sem bridge explícita.

## 3. Dependências obrigatórias
As relações oficiais da release 3.33 marcam **Lionfish API** e **Curios API** como required dependencies. No pack físico estão presentes `lionfishapi-3.1.jar` e `curios-neoforge-9.5.1+1.21.1.jar`. O source 1.21 compila contra Lionfish 3.1 e uma linha Curios 1.21.1; o runtime físico mais novo de Curios deve ser validado por boot/regressão, não tratado como divergência por si.

## 4. Estruturas/worldgen confirmados na branch 1.21
O datapack/source registra estruturas com os IDs:
- `abandoned_spire`, `abandoned_temple`, `abandoned_village`;
- `acropolis`;
- `amethyst_nest`;
- `ancient_factory`;
- `burning_arena`;
- `cursed_pyramid`;
- `desert_occupied_village`, `desert_site`, `desert_temple`;
- `frosted_prison`;
- `ruined_citadel`;
- `soul_black_smith`;
- `sunken_city`.
A distribuição final depende dos JSONs/tags/worldgen da build. Mods próprios não devem recolocar essas estruturas por lógica paralela; integração deve observar seus IDs/tags/datapack.

## 5. Entidades vivas registradas
O `ModEntities` 1.21 confirma, entre outras, as seguintes entidades com atributos próprios: `ender_golem`, `ender_guardian`, `netherite_monstrosity`, `netherite_ministrosity`, `ignis`, `endermaptera`, `ignited_revenant`, `ignited_berserker`, `the_harbinger`, `the_watcher`, `the_prowler`, `the_leviathan`, `the_baby_leviathan`, `deepling`, `deepling_brute`, `deepling_angler`, `deepling_priest`, `deepling_warlock`, `lionfish`, `coral_golem`, `coralssus`, `amethyst_crab`, `koboleton`, `kobolediator`, `ancient_remnant`, `modern_remnant`, `wadjet`, `maledictus`, `draugr`, `royal_draugr`, `elite_draugr`, `aptrgangr`, `hippocamtus`, `cindaria`, `clawdian`, `scylla`, `urchinkin`, `drowned_host` e `symbiocto`.
Os boss-class systems incluem pelo source entidades dedicadas como Ender Guardian, Netherite Monstrosity, Ignis, Harbinger, Leviathan, Ancient Remnant, Maledictus e Scylla. A classificação exata de “boss/miniboss” para cada mob deve seguir a classe/config da build, não o tamanho visual.

## 6. Entidades transitórias de combate e sincronização
O mod registra numerosos `MobCategory.MISC` para ataques e efeitos causais, incluindo famílias de `lava_bomb`, `flare_bomb`, `flame_jet`, `lightning_storm`, `void_rune`, `abyss_mine/mark/orb`, flechas/shards/spikes, `phantom_halberd`, `laser_beam`, wither missiles/howitzers, `void_howitzer`, `void_vortex`, tentáculos/hook do Leviathan, `scylla_ceraunus`/`player_ceraunus`/`brontes`, portals/abyss blasts, coral spear/bardiche arremessados, `dimensional_rift`, `earthquake`, sandstorms, desert stele, `axe_blade`, `octo_ink`, `water_spear` e `lightning_spear`.
Essas entidades têm tracking/update intervals próprios; portanto dano, hit, projectile ownership e remoção devem permanecer server-authoritative e sincronizados. Um addon não deve aplicar o mesmo dano novamente ao observar apenas o efeito visual.

## 7. Spawn e AI
A branch 1.21 registra spawn placements específicos para mobs como Endermaptera, Koboleton, Deeplings aquáticos, Coral Golem, Amethyst Crab e Ignited Berserker, e cria attribute sets para todo o elenco vivo. Isso significa que spawn natural, estrutura e boss encounter são contratos do provider. Alterações de biome/spawn via datapack/addon precisam evitar double-spawn e preservar regras de placement.

## 8. Equipamentos, materiais e progressão de loot
A build possui materiais/armaduras e equipamentos endgame, incluindo conjuntos **Ignitium** e **Cursium**, Bone Reptile pieces, Monstrous Helm e Bloom Stone Pauldrons. Entre armas/itens de combate confirmados no source estão Bulwark of the Flame, Gauntlet of Guard/Bulwark/Maelstrom, The Incinerator, The Annihilator, Soul Render, Void Forge, Cursed Bow, Laser Gatling, Wither/Void Assault Shoulder Weapons, Coral Spear/Bardiche, Astrape, Ceraunus, Brontes, The Immolator e Meat Shredder.
Há também curios/equipment especiais e drops de bosses/materiais. Esses itens não devem ser balanceados por nome apenas: atributos, cooldowns e efeitos são configuráveis e alguns são materialmente alterados durante o lifecycle de components.

## 9. Dungeon Eyes / localização
`ModItems` registra Dungeon Eye items associados a tags de localização, incluindo `mech_eye`, `flame_eye`, `void_eye`, `monstrous_eye`, `abyss_eye`, `desert_eye` e `cursed_eye` (e outros da mesma família na build). Eles funcionam como interface de localização/progressão do próprio Cataclysm; quests próprias devem consumir evidência causal adequada sem substituir o locator provider.

## 10. Blocos e block systems
`ModBlocks` usa `DeferredRegister.Blocks` e registra grandes famílias estruturais/decorativas (Witherite, Enderrite, Ignitium, Ancient Metal, Cursium, End Stone/Purpur/Void/Obsidian variants etc.) além de blocos funcionais: traps de void/teleport/explosion/poison/ignite/falling, `altar_of_fire`, `altar_of_void`, `altar_of_amethyst`, `altar_of_abyss`, `boss_respawner`, `cursed_tombstone`, `dungeon_block`, `emp`, `mechanical_fusion_anvil`, `door_of_seal`, skull/head blocks e `abyssal_egg`.
Alguns dungeon blocks são deliberadamente resistentes/sem loot; automação/mining mods não devem presumir quebrabilidade comum.

## 11. Data components, atributos e config
A branch 1.21 usa `ModifyDefaultComponentsEvent` para modificar components/attributes de equipamentos após carregar config. Ignitium/Cursium e outros equipamentos recebem atributos derivados de valores configuráveis; vários são marcados unbreakable. Armas também recebem attack damage/speed a partir do config.
Consequência: o valor “real” no runtime é função da versão + config atual, não apenas do valor hardcoded do item constructor. Integrações RPG não devem duplicar modifiers já aplicados pelo Cataclysm.

## 12. Client / server e multiplayer
Boss AI, spawn, dano, loot, block state e progressão de encounter pertencem ao servidor. O cliente renderiza modelos/animações/partículas/screen shake/sky effects e recebe entidades rastreadas. Em multiplayer, causalidade de kill/hit/drop deve vir do servidor; efeitos visuais e bossbar não são source of truth.

## 13. Lifecycle crítico
Validar especialmente:
- geração de estruturas em chunks novos e convivência com worldgen existente;
- save/reload de bosses e entidades complexas;
- chunk unload/reload durante encounter;
- morte do boss e geração de loot exatamente uma vez;
- respawn/re-summon por altars/boss respawner quando aplicável;
- projéteis/áreas removidos corretamente após owner death/unload;
- datapack/resource reload sem corromper tags/loot/worldgen;
- config reload/restart mantendo components e atributos coerentes.

## 14. Integrações concretas presentes no pack
A modlist contém addons diretamente relacionados: `Cataclysm: Spellbooks`, `Cataclysm x YUNG's Better Nether Fortresses Compat`, `Goety Cataclysm`, `Cataclysm: Ignis Soulfires` e `Integrated Cataclysm`. Eles são expansões/bridges independentes; não fazem parte do JAR-base e devem manter páginas próprias. Também há Curios como required dependency e integrações indiretas com o stack de combate/loot que precisam ser testadas, não presumidas.

## 15. Riscos técnicos no pack
1. **Worldgen overlap:** estruturas grandes competindo por densidade/território com IDAS, Integrated Dungeons e outros worldgen mods.
2. **Double damage/effects:** Epic Fight/spell addons/bridges reagindo ao mesmo hit ou projectile.
3. **Loot inflation:** múltiplos addons injetando recompensas nas mesmas estruturas/bosses.
4. **Attribute stacking:** equipamentos Cataclysm + Apothic/RPG modifiers excedendo balance/ranges.
5. **Curios overlap:** acessórios/slots/modifiers duplicados por compat layers.
6. **Chunk lifecycle:** boss/projétil persistindo ou duplicando após unload/reload.
7. **Boss ownership:** quests/projetos próprios detectando bossbar/render em vez de death/advancement/state causal.
8. **Version drift:** addons compilados contra 3.32/1.20.1 ou source `master` usados indevidamente com runtime 3.33.
9. **Destructive attacks:** ataques/estruturas podem interagir com claims, MineColonies e contraptions; validar em mundo de teste.

## 16. Matriz de testes
- [ ] Dedicated server cold boot com Cataclysm 3.33 + Lionfish API 3.1 + Curios físico atual.
- [ ] Entrar/relogar perto de cada dungeon não duplica mobs/bosses.
- [ ] Chunk unload/reload durante boss fight preserva encounter sem clone/stale entity.
- [ ] Boss death concede loot/progressão exatamente uma vez em SP e MP.
- [ ] Dois jogadores causando dano simultâneo não duplicam kill credit/reward.
- [ ] Dungeon Eyes localizam apenas estruturas/tags esperadas.
- [ ] Equip/unequip de armor/Curios não acumula modifiers após relog/death.
- [ ] Projectiles/areas são removidos corretamente após hit, timeout, owner death e unload.
- [ ] Configs alteram stats uma vez e não duplicam default components.
- [ ] Estruturas coexistem com worldgen do pack sem densidade/aninhamento anormal.
- [ ] Addons Cataclysm presentes carregam contra 3.33 sem missing class/method.
- [ ] Epic Fight/spell bridges não produzem double damage/stagger.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- modlist física atual: JAR 3.33, mod id e mixin;
- CurseForge/Modrinth oficiais: release 3.33, NeoForge 1.21.1, Client & Server, dependencies Lionfish API + Curios; changelog 3.33 informa otimização de projéteis e atualização `uk_ua`;
- source oficial branch `1.21`: `gradle.properties`, `ModEntities`, `ModItems`, `ModBlocks` e datapack `worldgen/structure` auditados.
A auditoria é por subsistemas, conforme permitido para mods grandes; não foi feita uma linha para cada bloco/item/classe. Valores numéricos de boss/weapon config não são reproduzidos aqui porque dependem do config/runtime e devem ser consultados no source/config específico quando necessários.
