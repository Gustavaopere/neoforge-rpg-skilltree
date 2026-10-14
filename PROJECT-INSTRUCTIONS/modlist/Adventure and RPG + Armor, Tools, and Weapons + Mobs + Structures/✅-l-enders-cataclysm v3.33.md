# L_Ender's Cataclysm

## Propriedades do registro

- **Mod:** L_Ender's Cataclysm
- **Arquivo JAR:** L_Ender's Cataclysm 1.21.1-3.33.jar
- **Versão 1.21.1:** 3.33
- **Categoria:** Worldgen, Exploração, Mobs, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lendercataclysm
- **Função:** Provider grande de aventura/endgame: estruturas e dungeons, mobs/bosses, AI/combate, projéteis/áreas, loot, materiais, equipamentos, locators e blocos funcionais.
- **Dependências:** Required: Lionfish API + Curios API. No runtime físico: Lionfish API 3.1 e Curios 9.5.1+1.21.1; source 1.21 do Cataclysm 3.33 usa a linha NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Alto impacto em worldgen, boss lifecycle, loot e atributos. Riscos: worldgen overlap, double-damage/effects, loot inflation, attribute/Curios stacking, chunk unload duplication e version drift de addons. Addons devem usar Cataclysm como authority.
- **Sobreposição:** Base authority para seus bosses/estruturas/loot. Addons físicos `cataclysm_spellbooks`, `cataclysmfortresses`, `goety_cataclysm`, `ignissoulfires` e `integrated_cataclysm` expandem/integram; não substituem o mod-base.
- **Observações:** Source oficial usa repositório histórico `lender544/new1.20.1`, mas a branch correta da build instalada é `1.21`; `gradle.properties` dessa branch declara MC 1.21.1 e mod 3.33. Não usar `master`/1.20.1 como equivalente.
- **Procedência:** modlist.txt física atual + CurseForge/Modrinth oficiais Cataclysm 3.33 + source oficial branch `1.21` (`gradle.properties`, ModEntities, ModItems, ModBlocks, worldgen/structure).
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — L_Ender's Cataclysm 3.33/JAR físico reconfirmado; 3.33 permanece a versão nominal NeoForge 1.21.1 mais recente localizada; existe republicação homônima posterior em 20/09/2026 que exige reconciliação do artefato. Corpo técnico preservado.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #368: JAR `L_Ender's Cataclysm 1.21.1-3.33.jar`, mod id `cataclysm`, runtime `3.33`, SHA-1 `5ff39c0eddfa08ea0e921bdcb17da7f32f0b00ce`.

> **ESCOPO CANÔNICO.** O snapshot de 25/09/2026 registra `L_Ender's Cataclysm 1.21.1-3.33.jar` (mod ID `cataclysm`, versão 3.33) como provider de worldgen, bosses, combate, loot e equipamentos. A linha 3.33 tem **publicações distintas com o mesmo filename**; a distribuição específica do JAR local não foi associada a um File ID nesta auditoria.
## 1. Identidade e source pin
A modlist física confirma JAR, mod id `cataclysm`, versão 3.33 e `cataclysm.mixins.json`. CurseForge confirma arquivos da release 3.33 para NeoForge 1.21.1 desde 22/08/2026, e outra publicação com o **mesmo nome e número 3.33 em 20/09/2026**; a identidade binária do snapshot precisa ser reconciliada por hash/File ID. O repositório oficial mantém o nome histórico `new1.20.1`, porém a branch **`1.21`** é a linha correta desta build; nela `gradle.properties` declara Minecraft 1.21.1, Cataclysm 3.33 e NeoForge 21.1.x. Não usar `master`/1.20.1 como source equivalente ao binário atual.
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

## 18. Revalidação de publicação 3.33 e proveniência binária — 14/10/2026

### 18.1. Múltiplas publicações com o mesmo número e filename

A [lista de arquivos CurseForge do projeto (filtro 1.21.1)](https://www.curseforge.com/minecraft/mc-mods/lendercataclysm/files/all?page=1&pageSize=20&version=1.21.1) registra **três entradas NeoForge** exibidas sob o título `L_Ender's Cataclysm 1.21.1-3.33.jar`: **uma de 20/09/2026 e duas de 22/08/2026**. A publicação mais recente identificada é o [File ID `8931182`](https://www.curseforge.com/minecraft/mc-mods/lendercataclysm/files/8931182), de **20/09/2026**, ainda com **versão 3.33**. Seu changelog oficial declara correção de divergência de licença entre CurseForge, Modrinth e 1.20.1, além de atualização de tradução `uk_ua`. **Não há mudança de gameplay confirmada por esse changelog.** Não classificar essa publicação como atualização 3.34, nem tratar todos os arquivos 3.33 como bytes idênticos.

**Última autoridade física verificável (snapshot de 25/09/2026):** #368, `L_Ender's Cataclysm 1.21.1-3.33.jar`, SHA-1 `5ff39c0eddfa08ea0e921bdcb17da7f32f0b00ce`, mod ID `cataclysm`, versão `3.33`. **Nenhum File ID CurseForge foi certificado para esse SHA-1 histórico.** Embora a snapshot tenha data posterior a 20/09/2026, a cronologia **não prova** qual das três distribuições foi instalada. A alteração física e qualquer promoção de release ficam **PENDENTES** até abrir o JAR atual, confirmar SHA/metadata e comparar com hashes oficiais das distribuições, quando disponíveis.

### 18.2. Source e dependências: authority distinta

**Source oficial reaberto e pinado:** [`lender544/new1.20.1`, branch `1.21`, commit `10e21131b8eb2ce92be416409f91925e8c5decf0`](https://github.com/lender544/new1.20.1/tree/10e21131b8eb2ce92be416409f91925e8c5decf0). No [`gradle.properties` dessa revisão](https://github.com/lender544/new1.20.1/blob/10e21131b8eb2ce92be416409f91925e8c5decf0/gradle.properties), os campos declarados são `minecraft_version=1.21.1`, `mod_id=cataclysm`, `mod_version=3.33`, `neo_version=21.1.219`, `lf_version_range=3.1` e `curios_version=9.2.2+1.21.1`. **São declarações do source, não confirmação de identidade do binário instalado.** A branch `master` de linha 1.20.1 não deve ser substituída no lugar da branch `1.21` para APIs, mobs ou estruturas da build documentada.

A [lista de dependências oficial](https://www.curseforge.com/minecraft/mc-mods/lendercataclysm/relations/dependencies) declara **Curios API e Lionfish API como obrigatórias**. O snapshot do pack mantém Curios #211 `9.5.1+1.21.1` e Lionfish API #374 `3.1`; divergência entre Curios usado no source e runtime histórico não demonstra incompatibilidade sem teste. O `mod_license` do source distingue restrições sobre assets e licenciamento do código; a republicação 8931182 relata harmonização de licença. **Não extrapolar permissões de redistribuição** sem verificar a licença efetiva do arquivo distribuído e seus assets.

### 18.3. Decisão de manutenção e verificações operacionais

**Status: MANTER ESTADO HISTÓRICO / PENDENTE DE RECONCILIAÇÃO BINÁRIA.** Não foi tomada decisão nova de update ou remoção (a propriedade histórica `Decisão: Sem decisão` permanece). Toda a pesquisa anterior sobre worldgen, Boss AI, entidades, atributos, armaduras, dungeon eyes, addons e matriz de testes deve permanecer no dossiê. Os riscos de worldgen, saves e compatibilidade dos addons são prioridade superior à data de publicação da release.

- [ ] Reabrir o JAR **atualmente usado pelo perfil**, registrar tamanho exato, SHA-1/SHA-256, `META-INF/neoforge.mods.toml`, dependências, mod ID e mixins; comparar com SHA-1 do snapshot e com o File ID correspondente à distribuição oficial.
- [ ] Obter hashes binários confiáveis das distribuições 3.33 de 22/08 e 20/09 antes de atribuir ao pack o File ID `8931182`; evitar alteração de arquivo só porque o número nominal e o filename coincidem.
- [ ] Se os binários divergirem materialmente, comparar manifest, recursos/lang, classes, registries, mixins e alterações de gameplay/metadata **antes** de propor qualquer troca; preservar backups dos mundos.
- [ ] Validar boot client/dedicated server, compatibilidade com Lionfish/Curios, bosses e respectivos addons, carregamento de estruturas em chunks novos, equipamentos, loot, multiplayer, mortes e boss respawner.
- [ ] Para RPG Skill Tree/perks, preferir eventos server-authoritative; checar IDs, fases/AI, dano, cooldown de item e modificadores de attributes contra a **versão binária efetivamente instalada**, sem deduzir contratos do source nominal.

**Nesta atualização:** somente proveniência documental e saneamento de formatação histórica, sem alteração no `modlist.md`, mod JAR, mundo, config, datapack, resource pack, shader ou código próprio. Nenhum teste runtime foi executado nesta etapa.
