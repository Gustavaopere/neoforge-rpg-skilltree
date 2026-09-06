# 10.10 — Registro de lotes do corpus editorial pt-BR

Este arquivo acompanha a produção incremental do corpus editorial real previsto em `10-ptbr-corpus-editorial.md`. Ele não substitui o plano canônico; registra somente lotes efetivamente escritos, revisados e validados.

## Regras de fechamento de lote

Um lote só pode ser tratado como concluído quando:

- todas as entradas usam `language: pt_br`;
- `entry_id`, `namespace`, `kind` e `availability` correspondem ao conteúdo real em runtime;
- todo resumo e toda seção textual possuem fonte explícita;
- textos não congelam em prosa valores mecânicos que podem mudar por configuração/runtime;
- não existem `TODO`, `TBD`, `FIXME` ou placeholders;
- as entradas estão marcadas como `REVIEWED` somente após revisão factual e linguística;
- o CI editorial e o CI agregado do mod passam no HEAD final.

## Lote 1 — Vanilla / criação e companhia

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:bee` — Abelha
2. `ENTITY:minecraft:cat` — Gato
3. `ENTITY:minecraft:chicken` — Galinha
4. `ENTITY:minecraft:cow` — Vaca
5. `ENTITY:minecraft:goat` — Cabra
6. `ENTITY:minecraft:horse` — Cavalo
7. `ENTITY:minecraft:pig` — Porco
8. `ENTITY:minecraft:rabbit` — Coelho
9. `ENTITY:minecraft:sheep` — Ovelha
10. `ENTITY:minecraft:wolf` — Lobo

## Lote 2 — Vanilla / fauna e montarias complementares

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:axolotl` — Axolote
2. `ENTITY:minecraft:camel` — Camelo
3. `ENTITY:minecraft:donkey` — Burro
4. `ENTITY:minecraft:fox` — Raposa
5. `ENTITY:minecraft:frog` — Sapo
6. `ENTITY:minecraft:llama` — Lhama
7. `ENTITY:minecraft:mooshroom` — Coguvaca
8. `ENTITY:minecraft:ocelot` — Jaguatirica
9. `ENTITY:minecraft:parrot` — Papagaio
10. `ENTITY:minecraft:turtle` — Tartaruga

### Critérios editoriais aplicados ao lote 2

- fontes primárias `RUNTIME` e `OFFICIAL_CODE` por entrada;
- sem probabilidades, dano, vida, velocidade, cooldown ou capacidades numéricas hardcoded em prosa;
- distinção explícita entre domesticação, confiança e simples uso de sela quando as mecânicas diferem;
- variantes descritas sem transformar regras de worldgen/configuração em texto universal;
- descrições redigidas em pt-BR e separadas dos fatos técnicos/providers do Compêndio.

## Lote 3 — Vanilla / fauna ambiente e aquática

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch3.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:armadillo` — Tatu
2. `ENTITY:minecraft:bat` — Morcego
3. `ENTITY:minecraft:cod` — Bacalhau
4. `ENTITY:minecraft:dolphin` — Golfinho
5. `ENTITY:minecraft:glow_squid` — Lula-brilhante
6. `ENTITY:minecraft:panda` — Panda
7. `ENTITY:minecraft:polar_bear` — Urso-polar
8. `ENTITY:minecraft:pufferfish` — Baiacu
9. `ENTITY:minecraft:salmon` — Salmão
10. `ENTITY:minecraft:squid` — Lula

### Critérios editoriais aplicados ao lote 3

- identidade de todas as dez entidades confirmada no registry de Minecraft 1.21.1;
- comportamento descrito a partir do runtime e das classes oficiais correspondentes;
- sem stats, probabilidades, durações, alcances, cooldowns ou limites de grupo hardcoded em prosa;
- heranças relevantes preservadas quando informativas, como `AbstractSchoolingFish`, `Bucketable` e `Squid` → `GlowSquid`;
- relações explícitas Lula ↔ Lula-brilhante permanecem referências editoriais, sem criar fatos técnicos paralelos;
- texto em pt-BR revisado para não confundir comportamento neutro, passivo, defesa, domesticação ou interação.

## Lote 4 — Vanilla / criaturas especiais e utilitárias

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch4.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:allay` — Allay
2. `ENTITY:minecraft:iron_golem` — Golem de Ferro
3. `ENTITY:minecraft:mule` — Mula
4. `ENTITY:minecraft:skeleton_horse` — Cavalo Esqueleto
5. `ENTITY:minecraft:sniffer` — Farejador
6. `ENTITY:minecraft:snow_golem` — Golem de Neve
7. `ENTITY:minecraft:strider` — Andarilho
8. `ENTITY:minecraft:tadpole` — Girino
9. `ENTITY:minecraft:trader_llama` — Lhama do Comerciante
10. `ENTITY:minecraft:tropical_fish` — Peixe Tropical

### Critérios editoriais aplicados ao lote 4

- identidade das dez entidades confirmada para Minecraft 1.21.1 e cada ficha ancorada em `RUNTIME` e `OFFICIAL_CODE`;
- comportamentos especiais descritos pela implementação real, incluindo coleta do Allay, escavação do Farejador, estados dos golems, armadilha do Cavalo Esqueleto e vínculo da Lhama do Comerciante;
- heranças e contratos relevantes preservados sem duplicar fatos técnicos, incluindo `AbstractHorse`, `AbstractChestedHorse`, `AbstractSchoolingFish`, `Bucketable`, `Saddleable`, `ItemSteerable`, `Shearable` e `RangedAttackMob`;
- nenhuma duração, chance, dano, cura, velocidade, capacidade de inventário ou outro parâmetro mutável foi congelado em prosa;
- diferenças entre criatura amigável, animal, construto, montaria e comportamento defensivo foram mantidas sem inferir classificação pelo nome do ID;
- conteúdo validado pelo CI editorial e pelo CI agregado, incluindo carregamento do pacote, JUnit, GameTests, build, JAR e dedicated-server smoke.

## Lote 5 — Vanilla / hostis comuns e variantes

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch5.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:bogged` — Pantanoso
2. `ENTITY:minecraft:cave_spider` — Aranha das Cavernas
3. `ENTITY:minecraft:creeper` — Creeper
4. `ENTITY:minecraft:drowned` — Afogado
5. `ENTITY:minecraft:husk` — Zumbi-Múmia
6. `ENTITY:minecraft:skeleton` — Esqueleto
7. `ENTITY:minecraft:spider` — Aranha
8. `ENTITY:minecraft:stray` — Errante
9. `ENTITY:minecraft:witch` — Bruxa
10. `ENTITY:minecraft:zombie` — Zumbi

### Critérios editoriais aplicados ao lote 5

- identidade das dez entidades confirmada no registry de Minecraft 1.21.1 e ancorada em `RUNTIME` e `OFFICIAL_CODE`;
- relações de herança relevantes preservadas, incluindo `Bogged`/`Skeleton`/`Stray` via `AbstractSkeleton`, `CaveSpider` via `Spider` e `Drowned`/`Husk` via `Zombie`;
- comportamentos próprios descritos a partir das classes vanilla, incluindo explosão/carga do Creeper, mobilidade aquática do Afogado, poções da Bruxa, veneno da Aranha das Cavernas e corte do Pantanoso;
- nenhuma chance, dano, duração de efeito, alcance, cadência, raio de explosão ou outro parâmetro mecânico mutável foi congelado em prosa;
- variantes com registry ID próprio permanecem entradas independentes, sem duplicar a ficha da classe-base;
- o pacote passou pelo CI editorial e pelo CI agregado antes deste registro, incluindo JUnit, GameTests, validadores do Compêndio, build, JAR e dedicated-server smoke.

## Lote 6 — Vanilla / hostis de progressão e estruturas

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch6.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:blaze` — Blaze
2. `ENTITY:minecraft:breeze` — Vórtice
3. `ENTITY:minecraft:elder_guardian` — Guardião-Mestre
4. `ENTITY:minecraft:enderman` — Enderman
5. `ENTITY:minecraft:evoker` — Invocador
6. `ENTITY:minecraft:ghast` — Ghast
7. `ENTITY:minecraft:guardian` — Guardião
8. `ENTITY:minecraft:phantom` — Phantom
9. `ENTITY:minecraft:pillager` — Saqueador
10. `ENTITY:minecraft:wither_skeleton` — Esqueleto Wither

### Critérios editoriais aplicados ao lote 6

- identidade e classes das dez entidades confirmadas para Minecraft 1.21.1, com fichas ancoradas em `RUNTIME` e `OFFICIAL_CODE`;
- nomenclatura pt-BR revisada contra os assets da linha 1.21.x, preservando `Phantom` para a versão 1.21.1 em vez de adotar retroativamente a tradução posterior `Espectro`;
- comportamentos especializados registrados sem congelar números, incluindo ataque do Blaze, IA de salto/deslizamento do Vórtice, rotina adicional do Guardião-Mestre, provocação/teleporte do Enderman, feitiços do Invocador, projéteis do Ghast, feixe do Guardião, fases de ataque do Phantom, besta do Saqueador e ataque próprio do Esqueleto Wither;
- heranças e contratos relevantes preservados, incluindo `Guardian` → `ElderGuardian`, `AbstractSkeleton` → `WitherSkeleton`, `SpellcasterIllager`, `NeutralMob`, `CrossbowAttackMob`, `InventoryCarrier`, `FlyingMob` e os behaviors do pacote `breeze`;
- a referência de raid do Saqueador usa o pacote correto de 1.21.1, `net.minecraft.world.entity.raid.Raider`, sem transportar caminho de pacote incorreto;
- nenhuma duração, chance, dano, alcance, cadência, força de explosão, tamanho de inventário ou outro parâmetro mecânico mutável foi congelado em prosa;
- o corpus corrigido passou pelo Compendium Editorial CI #210 e pelo CI agregado #2378, incluindo JUnit, GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 7 — Vanilla / chefes e ameaças de estruturas

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch7.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:ender_dragon` — Dragão Ender
2. `ENTITY:minecraft:endermite` — Endermite
3. `ENTITY:minecraft:magma_cube` — Cubo de magma
4. `ENTITY:minecraft:ravager` — Devastador
5. `ENTITY:minecraft:shulker` — Shulker
6. `ENTITY:minecraft:silverfish` — Traça
7. `ENTITY:minecraft:vex` — Vex
8. `ENTITY:minecraft:vindicator` — Vingador
9. `ENTITY:minecraft:warden` — Defensor
10. `ENTITY:minecraft:wither` — Wither

### Critérios editoriais aplicados ao lote 7

- identidade das dez entidades confirmada no registry de Minecraft 1.21.1 e nomenclatura revisada contra o asset `pt_br` da mesma linha, incluindo `Dragão Ender`, `Devastador`, `Traça`, `Vingador` e `Defensor`;
- chefes tratados por seus sistemas reais em vez de descrições genéricas: o Dragão Ender usa a infraestrutura de fases do pacote `enderdragon.phases`, enquanto o Wither mantém alvos múltiplos, projéteis e estado próprio de chefe;
- o Defensor foi documentado a partir de `Warden`, `WardenAi`, `AngerManagement`, `VibrationSystem` e do comportamento sônico no caminho correto `net.minecraft.world.entity.ai.behavior.warden.SonicBoom`;
- relações e comportamentos estruturais relevantes foram preservados: `Ravager` → `Raider`, `MagmaCube` → `Slime`, carapaça/fixação do Shulker, infestação e chamada de grupo da Traça, vínculo/investida do Vex, estado Johnny do Vingador e ciclo de vida próprio da Endermite;
- nenhuma duração, chance, dano, alcance, recarga, velocidade, força de projétil, resistência, limiar de raiva ou outro parâmetro mecânico mutável foi congelado em prosa;
- todas as fichas usam `RUNTIME` e `OFFICIAL_CODE`, permanecem `REVIEWED`/`RUNTIME` e não transformam detalhes de configuração ou composição de encontros em regras universais;
- o corpus corrigido passou pelo Compendium Editorial CI #218 e pelo RPG Skill Tree CI #2390, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 8 — Vanilla / habitantes, Nether e criaturas regulares remanescentes

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch8.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:hoglin` — Hoglin
2. `ENTITY:minecraft:piglin` — Piglin
3. `ENTITY:minecraft:piglin_brute` — Piglin bárbaro
4. `ENTITY:minecraft:slime` — Slime
5. `ENTITY:minecraft:villager` — Aldeão
6. `ENTITY:minecraft:wandering_trader` — Vendedor ambulante
7. `ENTITY:minecraft:zoglin` — Zoglin
8. `ENTITY:minecraft:zombie_horse` — Cavalo-zumbi
9. `ENTITY:minecraft:zombie_villager` — Aldeão zumbi
10. `ENTITY:minecraft:zombified_piglin` — Piglin-zumbi

### Critérios editoriais aplicados ao lote 8

- identidade das dez entidades confirmada para Minecraft 1.21.1 e nomenclatura pt-BR conferida contra os assets da mesma linha;
- fichas ancoradas em `RUNTIME` e `OFFICIAL_CODE`, preservando as diferenças entre Hoglin/Zoglin, Piglin/Piglin bárbaro/Piglin-zumbi, Aldeão/Aldeão zumbi e Vendedor ambulante;
- comportamento do Slime, comércio e reputação dos habitantes, estados dos piglins e relações de conversão foram descritos sem congelar preços, chances, tempos, distâncias, dano ou outros parâmetros mutáveis;
- o Cavalo-zumbi foi documentado sem inventar spawn natural ou método de obtenção que o runtime 1.21.1 não garante;
- durante a validação do lote foi identificado e corrigido um falso positivo do filtro de placeholders: a fronteira ASCII de `TODO` tratava a sequência final de palavras pt-BR como `método` como placeholder; o loader agora usa `Pattern.UNICODE_CHARACTER_CLASS` e possui teste de regressão que aceita `método` e continua rejeitando `TODO` isolado;
- a causa foi isolada por teste por entrada antes da correção; a instrumentação diagnóstica temporária foi removida do HEAD final;
- o corpus e a correção Unicode passaram pelo Compendium Editorial CI #229 e pelo RPG Skill Tree CI #2406, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke antes deste registro.

## Lote 9 — Vanilla / flores pequenas frequentes

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/flora-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:minecraft:dandelion` — Dente-de-leão
2. `FLORA:minecraft:poppy` — Papoula
3. `FLORA:minecraft:blue_orchid` — Orquídea-azul
4. `FLORA:minecraft:allium` — Alho-silvestre
5. `FLORA:minecraft:azure_bluet` — Flor-silvestre-azul
6. `FLORA:minecraft:red_tulip` — Tulipa vermelha
7. `FLORA:minecraft:orange_tulip` — Tulipa laranja
8. `FLORA:minecraft:white_tulip` — Tulipa branca
9. `FLORA:minecraft:pink_tulip` — Tulipa rosa
10. `FLORA:minecraft:oxeye_daisy` — Margarida

### Critérios editoriais aplicados ao lote 9

- o lote inicia a cobertura de flora vanilla depois de 80 entidades revisadas, seguindo a prioridade canônica que coloca flora frequente antes de conteúdo raro ou administrativo;
- identidade e nomes pt-BR das dez flores foram conferidos na linha Minecraft 1.21.1, sem transportar traduções de versões posteriores;
- a classificação `FLORA` foi sustentada por evidência estrutural do provider, pelo runtime de `FlowerBlock` e pelo tag datapack `minecraft:small_flowers`, não apenas pelo nome dos registry IDs;
- distribuição concreta não foi apresentada como universal: biomas, worldgen e datapacks ativos continuam sendo a fonte factual para ocorrência;
- conversões de corante só foram registradas quando o arquivo de receita exato de 1.21.1 foi confirmado para Dente-de-leão, Papoula, Orquídea-azul, Alho-silvestre e Flor-silvestre-azul; não foram inventados paths de receita para as demais flores;
- nenhuma frequência de geração, chance, condição numérica de sobrevivência, quantidade de resultado ou outro parâmetro mutável foi congelado em prosa;
- o corpus passou pelo Compendium Editorial CI #232 e pelo RPG Skill Tree CI #2413, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke antes deste registro.

## Lote 10 — Vanilla / flores remanescentes e flora decorativa

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/flora-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:minecraft:cornflower` — Centáurea
2. `FLORA:minecraft:lily_of_the_valley` — Lírio-do-vale
3. `FLORA:minecraft:wither_rose` — Rosa do Wither
4. `FLORA:minecraft:torchflower` — Plantocha
5. `FLORA:minecraft:sunflower` — Girassol
6. `FLORA:minecraft:lilac` — Lilás
7. `FLORA:minecraft:peony` — Peônia
8. `FLORA:minecraft:rose_bush` — Roseira
9. `FLORA:minecraft:pitcher_plant` — Planta ancestral
10. `FLORA:minecraft:pink_petals` — Pétalas rosas

### Critérios editoriais aplicados ao lote 10

- o lote completa as entradas ainda não cobertas de `minecraft:small_flowers` e `minecraft:tall_flowers` na linha 1.21.1 e acrescenta `minecraft:pink_petals`, integrante explícito de `minecraft:flowers`;
- nomes pt-BR foram conferidos contra os assets da linha Minecraft 1.21.1, incluindo `Centáurea`, `Lírio-do-vale`, `Rosa do Wither`, `Plantocha` e `Planta ancestral`;
- classificação `FLORA` usa presença explícita nos tags vanilla e identidade de bloco em runtime, sem inferência pelo texto do ID;
- Plantocha e Planta ancestral registram a existência dos blocos técnicos de plantio `minecraft:torchflower_crop` e `minecraft:pitcher_crop`, mas não congelam duração, estágio nem condição de crescimento;
- conversões de corante foram registradas somente após confirmação dos recipes exatos de 1.21.1 para as dez entradas; quantidades de saída permanecem fora da prosa editorial;
- distribuição concreta continua condicionada por worldgen, biomas e datapacks ativos, sem frequência ou local universal inventado;
- o corpus passou pelo Compendium Editorial CI #237 e pelo RPG Skill Tree CI #2425, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke antes deste registro.

## Lote 11 — Alex's Caves / biomas e estruturas de worldgen

Arquivos:

- `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/alexscaves/biomes-batch1.json`
- `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/alexscaves/structures-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:alexscaves:abyssal_chasm` — Fendas Abissais
2. `BIOME:alexscaves:candy_cavity` — Candy Cavity
3. `BIOME:alexscaves:forlorn_hollows` — Cavernas Desoladas
4. `BIOME:alexscaves:magnetic_caves` — Cavernas Magnéticas
5. `BIOME:alexscaves:primordial_caves` — Cavernas Primordiais
6. `BIOME:alexscaves:toxic_caves` — Cavernas Tóxicas
7. `STRUCTURE:alexscaves:abyssal_ruins` — Abyssal Ruins
8. `STRUCTURE:alexscaves:acid_pit` — Acid Pit
9. `STRUCTURE:alexscaves:cake_cave` — Cake Cave
10. `STRUCTURE:alexscaves:dino_bowl` — Dino Bowl

### Critérios editoriais aplicados ao lote 11

- provider reconciliado com a modlist canônica e a Auditoria Mestre antes da autoria: Alex's Caves Neo 2.0.2, namespace `alexscaves`;
- os seis biomas correspondem aos IDs registrados pelo provider e permanecem `RUNTIME`, sem congelar frequência, posição ou parâmetros visuais numéricos em prosa;
- as quatro estruturas são vinculadas pelos datapacks aos biomas correspondentes: Abyssal Ruins → Fendas Abissais, Acid Pit → Cavernas Tóxicas, Cake Cave → Candy Cavity e Dino Bowl → Cavernas Primordiais;
- nomes pt-BR só foram usados quando sustentados pelos assets do provider; `Candy Cavity` e os quatro títulos de estrutura foram preservados sem inventar tradução;
- todo resumo e seção possui fonte explícita `RUNTIME`, `DATAPACK`, `OFFICIAL_CODE` ou `VERIFIED_COMMUNITY` conforme a natureza do fato;
- não há probabilidades, alturas, pesos de spawn, contagens, chances ou outros parâmetros mutáveis congelados na prosa editorial;
- o lote foi desenvolvido em TDD: o teste Batch11 falhou primeiro exclusivamente pela ausência dos recursos; os pacotes foram adicionados somente depois desse RED correto.

## Lote 12 — Oh The Biomes We've Gone / biomas Overworld (batch 1)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/biomeswevegone/biomes-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:biomeswevegone:allium_shrubland` — Matagal de Alho-silvestre
2. `BIOME:biomeswevegone:amaranth_grassland` — Campo de Amarantos
3. `BIOME:biomeswevegone:araucaria_savanna` — Savana de Araucária
4. `BIOME:biomeswevegone:aspen_boreal` — Álamo Boreal
5. `BIOME:biomeswevegone:atacama_outback` — Deserto do Atacama
6. `BIOME:biomeswevegone:baobab_savanna` — Savana de Baobá
7. `BIOME:biomeswevegone:basalt_barrera` — Barreira de Basalto
8. `BIOME:biomeswevegone:bayou` — Ribeiro
9. `BIOME:biomeswevegone:black_forest` — Floresta Negra
10. `BIOME:biomeswevegone:canadian_shield` — Escudo Canadense

### Critérios editoriais aplicados ao lote 12

- provider reconciliado com a modlist canônica e a Auditoria Mestre antes da autoria: Oh The Biomes We've Gone 2.6.0, namespace `biomeswevegone`;
- o recorte corresponde aos dez primeiros biomas Overworld registrados em `BWGBiomes.java` na linha oficial 1.21.1 da release 2.6.0, ancorada no commit `3040862ddd02c2487c946cac2803502e59508062`;
- os títulos pt-BR são exatamente os fornecidos pelo `assets/biomeswevegone/lang/pt_br.json` do mesmo commit do provider, sem tradução editorial inventada;
- a prosa registra somente identidade de runtime, categorias/tags de biome e elegibilidade estrutural explicitamente declaradas pelo código oficial; tags de estrutura não são tratadas como garantia de geração;
- nenhum peso, chance, frequência, altura, posição, densidade ou parâmetro configurável foi congelado no texto;
- o lote foi desenvolvido em TDD: o commit `ee1a866280364cf472743d6423c61fc11af608d7` executou 909 testes e falhou exatamente uma vez, no Batch12, porque o recurso ainda não existia; o corpus foi adicionado somente depois desse RED correto.

## Lote 13 — Oh The Biomes We've Gone / biomas Overworld (batch 2)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/biomeswevegone/biomes-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:biomeswevegone:cika_woods` — Bosque de Cika
2. `BIOME:biomeswevegone:coconino_meadow` — Pradaria Coconino
3. `BIOME:biomeswevegone:coniferous_forest` — Floresta de Coníferas
4. `BIOME:biomeswevegone:crag_gardens` — Crag Gardens
5. `BIOME:biomeswevegone:crimson_tundra` — Tundra Carmesim
6. `BIOME:biomeswevegone:cypress_swamplands` — Pântano de Ciprestes
7. `BIOME:biomeswevegone:cypress_wetlands` — Terras Úmidas de Cipreste
8. `BIOME:biomeswevegone:dacite_ridges` — Cumes de Dacito
9. `BIOME:biomeswevegone:dacite_shore` — Costa de Dacito
10. `BIOME:biomeswevegone:dead_sea` — Mar Morto

### Critérios editoriais aplicados ao lote 13

- o recorte continua imediatamente após `canadian_shield` na ordem do registry `BWGBiomes.java` da release 2.6.0;
- IDs, categorias, tags de bioma e elegibilidade estrutural são sustentados pelo commit oficial `3040862ddd02c2487c946cac2803502e59508062`;
- os dez títulos pt-BR são exatamente os valores do `assets/biomeswevegone/lang/pt_br.json` no mesmo commit, incluindo `Crag Gardens`, que permanece sem tradução inventada;
- tags de vila, posto de saqueadores e tesouro enterrado são descritas somente como elegibilidade de worldgen; categorias internas como lago frio grande e arco de espeleotema não são convertidas em geometria, frequência ou valores numéricos;
- nenhum peso, chance, altura, posição, densidade ou outro parâmetro configurável foi congelado na prosa;
- TDD confirmado no commit `0aa9db81c0e71c033d0260b64f51e584b6745dbe`: 910 testes, exatamente 1 falha no Batch13 pela ausência do recurso; o corpus foi adicionado somente depois desse RED correto.

## Lote 14 — Oh The Biomes We've Gone / biomas Overworld (batch 3)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/biomeswevegone/biomes-batch3.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:biomeswevegone:ebony_woods` — Bosque de Ébano
2. `BIOME:biomeswevegone:enchanted_tangle` — Emaranhado Encantado
3. `BIOME:biomeswevegone:eroded_borealis` — Erosão Boreal
4. `BIOME:biomeswevegone:firecracker_chaparral` — Chaparral de Fogos de Artifício
5. `BIOME:biomeswevegone:forgotten_forest` — Floresta Esquecida
6. `BIOME:biomeswevegone:fragment_jungle` — Selva Fragmentada
7. `BIOME:biomeswevegone:frosted_coniferous_forest` — Floresta de Coníferas Congeladas
8. `BIOME:biomeswevegone:frosted_taiga` — Taiga Congelada
9. `BIOME:biomeswevegone:howling_peaks` — Picos Uivantes
10. `BIOME:biomeswevegone:ironwood_gour` — Vale de Pau-Ferro

### Critérios editoriais aplicados ao lote 14

- o recorte continua imediatamente após `dead_sea` na ordem do registry `BWGBiomes.java` da release 2.6.0;
- IDs, categorias, tags de bioma e elegibilidade estrutural são sustentados pelo commit oficial `3040862ddd02c2487c946cac2803502e59508062`;
- os dez títulos pt-BR são exatamente os valores do `assets/biomeswevegone/lang/pt_br.json` no mesmo commit;
- tags de iglu, vila nevada, posto de saqueadores, mansão da floresta e vila BWG são tratadas somente como elegibilidade de worldgen, não garantia de geração;
- categorias internas como `IRONWOOD_GOUR_PLATEAU` não são convertidas em geometria, altura, frequência ou valores numéricos;
- nenhum peso, chance, altura, posição, densidade ou outro parâmetro configurável foi congelado na prosa;
- TDD confirmado no commit `4bf0ef99ce38dc84b5a4ce621dc71024a408266a`: 911 testes, exatamente 1 falha no Batch14 pela ausência do recurso; o corpus foi adicionado somente depois desse RED correto.

## Lote 15 — Oh The Biomes We've Gone / biomas Overworld (batch 4)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/biomeswevegone/biomes-batch4.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:biomeswevegone:jacaranda_jungle` — Selva de Jacarandá
2. `BIOME:biomeswevegone:lush_stacks` — Pilhas Exuberantes
3. `BIOME:biomeswevegone:maple_taiga` — Taiga de Bordo
4. `BIOME:biomeswevegone:mojave_desert` — Deserto de Mojave
5. `BIOME:biomeswevegone:orchard` — Pomar
6. `BIOME:biomeswevegone:overgrowth_woodlands` — Bosques Cobertos de Vegetação
7. `BIOME:biomeswevegone:pale_bog` — Pântano Pálido
8. `BIOME:biomeswevegone:prairie` — Pradaria
9. `BIOME:biomeswevegone:pumpkin_valley` — Vale da Abóbora
10. `BIOME:biomeswevegone:rainbow_beach` — Praia do Arco-Íris

### Critérios editoriais aplicados ao lote 15

- o recorte continua imediatamente após `ironwood_gour` na ordem do registry `BWGBiomes.java` da release 2.6.0;
- IDs, categorias, tags de bioma e elegibilidade estrutural são sustentados pelo commit oficial `3040862ddd02c2487c946cac2803502e59508062`;
- os dez títulos pt-BR são exatamente os valores do `assets/biomeswevegone/lang/pt_br.json` no mesmo commit;
- tags vanilla de vilas, posto de saqueadores, pirâmide do deserto e tesouro enterrado, além das tags estruturais próprias do BWG, são tratadas somente como elegibilidade de worldgen, não garantia de geração;
- categorias internas como `LUSH_ARCH` e `LARGE_COLD_LAKE` não são convertidas em geometria, frequência, altura ou valores numéricos;
- nenhum peso, chance, altura, posição, densidade ou outro parâmetro configurável foi congelado na prosa;
- TDD confirmado no commit `3f88813aacf64eb1bbc8d498599126018d271cd6`: 912 testes, exatamente 1 falha no Batch15 pela ausência do recurso; o corpus foi adicionado somente depois desse RED correto.

## Lote 16 — Oh The Biomes We've Gone / biomas Overworld (batch 5)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/biomeswevegone/biomes-batch5.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:biomeswevegone:red_rock_valley` — Vale da Rocha Vermelha
2. `BIOME:biomeswevegone:red_rock_peaks` — Picos de Rocha Vermelha
3. `BIOME:biomeswevegone:redwood_thicket` — Matagal de Sequoias
4. `BIOME:biomeswevegone:rose_fields` — Campo de Rosas
5. `BIOME:biomeswevegone:rugged_badlands` — Ermo Acidentado
6. `BIOME:biomeswevegone:sakura_grove` — Bosque de Sakura
7. `BIOME:biomeswevegone:shattered_glacier` — Geleira Quebrada
8. `BIOME:biomeswevegone:sierra_badlands` — Serra de Ermo
9. `BIOME:biomeswevegone:skyris_vale` — Vale de Skyris
10. `BIOME:biomeswevegone:tropical_rainforest` — Floresta Tropical

### Critérios editoriais aplicados ao lote 16

- o recorte continua imediatamente após `rainbow_beach` na ordem do registry `BWGBiomes.java` da release 2.6.0;
- IDs, categorias, tags de bioma e elegibilidade estrutural são sustentados pelo commit oficial `3040862ddd02c2487c946cac2803502e59508062`;
- os dez títulos pt-BR são exatamente os valores do `assets/biomeswevegone/lang/pt_br.json` no mesmo commit;
- tags de vilas, posto de saqueadores, pirâmide do deserto, tesouro enterrado e estruturas próprias do BWG são tratadas somente como elegibilidade de worldgen, não garantia de geração;
- categorias internas como `RED_ROCK_ARCH`, `SHARPENED_ROCKS`, `DRY`, `PEAK`, `SLOPE` e `ICY` não são convertidas em geometria, altura, frequência, temperatura ou outros valores numéricos;
- nenhum peso, chance, altura, posição, densidade ou outro parâmetro configurável foi congelado na prosa;
- TDD confirmado no commit `cdb79fba9a04b813a4e91d5f33fd0cb9eb595a21`: 961 testes, exatamente 1 falha no Batch16 pela ausência do recurso; o corpus foi adicionado somente depois desse RED correto.

## Lote 17 — Oh The Biomes We've Gone / biomas Overworld (batch 6 final)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/biomeswevegone/biomes-batch6.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:biomeswevegone:temperate_grove` — Bosque Temperado
2. `BIOME:biomeswevegone:weeping_witch_forest` — Floresta das Bruxas Choronas
3. `BIOME:biomeswevegone:white_mangrove_marshes` — Mangue Branco
4. `BIOME:biomeswevegone:windswept_desert` — Deserto Ventoso
5. `BIOME:biomeswevegone:zelkova_forest` — Floresta de Zelkova

### Critérios editoriais aplicados ao lote 17

- o recorte continua imediatamente após `tropical_rainforest` e encerra a seção Overworld de `BWGBiomes.java` da release 2.6.0;
- IDs, ordem, categorias, tags de bioma e elegibilidade estrutural são sustentados pelo commit oficial `3040862ddd02c2487c946cac2803502e59508062`;
- os cinco títulos pt-BR são exatamente os valores do `assets/biomeswevegone/lang/pt_br.json` no mesmo commit;
- tags de vila de planície, posto de saqueadores, mansão da floresta e Vila de Salem são tratadas somente como elegibilidade de worldgen, não garantia de geração;
- categorias internas como `SPARSE`, `MAGICAL`, `DENSE` e `WINDSWEPT` não são convertidas em densidade, intensidade, altura, frequência ou outros valores numéricos;
- nenhum peso, chance, altura, posição, densidade ou outro parâmetro configurável foi congelado na prosa;
- TDD confirmado no commit `cd6e091deb96bf770c7c15763fe65f82456d8674`: 975 testes, exatamente 1 falha no Batch17 pela ausência do recurso; o corpus foi adicionado somente depois desse RED correto.

## Lote 18 — BetterEnd: New Dawn / biomas do End (batch 1)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/betterend/biomes-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:betterend:amber_land` — Terras de Âmbar
2. `BIOME:betterend:blossoming_spires` — Pináculos em Flor
3. `BIOME:betterend:chorus_forest` — Floresta de Choros
4. `BIOME:betterend:crystal_mountains` — Montanhas de Cristal
5. `BIOME:betterend:dragon_graveyards` — Cemitérios de Dragão
6. `BIOME:betterend:dry_shrubland` — Arbusto Seco
7. `BIOME:betterend:dust_wastelands` — Terras Devastadas
8. `BIOME:betterend:foggy_mushroomland` — Terras de Cogumelos Neovoada
9. `BIOME:betterend:glowing_grasslands` — Prados Brilhantes
10. `BIOME:betterend:ice_starfield` — Campo de Estrelas de Gelo

### Critérios editoriais aplicados ao lote 18

- provider reconciliado antes da autoria com a modlist canônica e a Auditoria Mestre do Notion: `BetterEnd-21.0.34.jar`, namespace `betterend`, estado `Instalado`, decisão `Manter`;
- o recorte corresponde aos dez primeiros biomas na ordem do registry oficial `EndBiomes.java` do commit `360b6fea407befdcf9aab7f771cad5586cbfb826`, cujo `gradle.properties` declara Minecraft 1.21.1 e BetterEnd 21.0.34;
- os dez títulos pt-BR são exatamente os valores do `assets/betterend/lang/pt_br.json` no mesmo commit, inclusive a grafia oficial `Terras de Cogumelos Neovoada`, sem correção editorial silenciosa;
- os datapacks Wover sustentam somente as classificações de placement usadas na prosa: sete entradas em `is_end/land`, `chorus_forest` em `is_end/highland`, `dust_wastelands` em `is_end/midland` e `ice_starfield` em `is_end/small_island` e `is_end/barrens`;
- as categorias de placement não são convertidas em altura, tamanho, densidade, geometria, peso ou frequência fixos;
- `EndBiomes.registerBiomeToggles()` comprova que estes dez IDs participam do conjunto configurável do provider, portanto disponibilidade e ocorrência concretas permanecem condicionadas à configuração e ao worldgen ativos;
- TDD confirmado no commit `0dbb401d21a90fd310ea07f58e940a79184edb6f`: 976 testes, exatamente 1 falha no Batch18 pela ausência de `betterend/biomes-batch1.json`; o corpus foi adicionado somente depois desse RED correto.

## Lote 19 — BetterEnd: New Dawn / biomas do End (batch 2)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/betterend/biomes-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:betterend:flower_islets` — Flower Islets
2. `BIOME:betterend:waterfall_ponds` — Waterfall Ponds
3. `BIOME:betterend:lantern_woods` — Madeiras Lanterna
4. `BIOME:betterend:megalake` — Megalake
5. `BIOME:betterend:shadow_forest` — Floresta da Sombra
6. `BIOME:betterend:sulphur_springs` — Fontes Sulfúricas
7. `BIOME:betterend:umbrella_jungle` — Selva de Guarda-chuvas
8. `BIOME:betterend:umbra_valley` — Vale Da Sombra
9. `BIOME:betterend:megalake_grove` — Arvoredos de Megalake
10. `BIOME:betterend:neon_oasis` — Oasis Neon

### Critérios editoriais aplicados ao lote 19

- o recorte continua imediatamente após `ice_starfield` na ordem do registry oficial `EndBiomes.java` do commit congelado `360b6fea407befdcf9aab7f771cad5586cbfb826`;
- oito títulos são preservados exatamente do `assets/betterend/lang/pt_br.json`: `Madeiras Lanterna`, `Megalake`, `Floresta da Sombra`, `Fontes Sulfúricas`, `Selva de Guarda-chuvas`, `Vale Da Sombra`, `Arvoredos de Megalake` e `Oasis Neon`;
- `flower_islets` e `waterfall_ponds` não possuem chave `biome.betterend.*` nem no `pt_br.json` nem no `en_us.json` congelados; `Flower Islets` e `Waterfall Ponds` são fallbacks editoriais explicitamente derivados das classes `FlowerIsletsBiome`/`WaterfallPondsBiome` e dos registry paths, não traduções atribuídas ao provider;
- o datapack Wover inclui `flower_islets` e `waterfall_ponds` em `is_end/small_island`; os outros oito IDs do lote estão em `is_end/land`;
- `flower_islets` e `waterfall_ponds` não aparecem em `registerBiomeToggles()`; os outros oito aparecem no conjunto configurável do provider;
- `megalake_grove` declara `MEGALAKE` como bioma-base associado e `neon_oasis` declara `DUST_WASTELANDS`; essas relações são registradas sem inferir chance, proximidade, geometria ou frequência;
- nenhuma categoria de placement, nome de bioma ou vínculo de base foi convertido em altura, tamanho, densidade, posição, peso, chance, frequência ou outro parâmetro mutável;
- TDD confirmado no commit `2b9ab97cd9dcf415446e5cc99da1197e6e8088e6`, RPG Skill Tree CI run `33994047460`, job `101381231279`: 977 testes, exatamente 1 falha no Batch19 pela ausência de `betterend/biomes-batch2.json`; o corpus foi adicionado somente depois desse RED correto.

## Lote 20 — BetterEnd: New Dawn / biomas do End (batch 3 final)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/betterend/biomes-batch3.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:betterend:painted_mountains` — Montanhas Pintadas
2. `BIOME:betterend:empty_end_cave` — Caverna do End Vazia
3. `BIOME:betterend:empty_smaragdant_cave` — Caverna Exuberante de Esmaragdante Vazia
4. `BIOME:betterend:lush_smaragdant_cave` — Caverna Exuberante de Esmaragdante
5. `BIOME:betterend:empty_aurora_cave` — Caverna Aurora Vazia
6. `BIOME:betterend:lush_aurora_cave` — Caverna Exuberante de Aurora
7. `BIOME:betterend:jade_cave` — Caverna de Jade

### Critérios editoriais aplicados ao lote 20

- o recorte continua imediatamente após `neon_oasis` e encerra todos os `EndBiomeKey` declarados em `EndBiomes.java` no commit congelado `360b6fea407befdcf9aab7f771cad5586cbfb826`;
- os sete títulos pt-BR são preservados exatamente do `assets/betterend/lang/pt_br.json`;
- `painted_mountains` é registrado pelo provider como `IS_END_LAND`, aparece em Wover `is_end/land` e declara `DUST_WASTELANDS` como bioma-base associado; o vínculo fica fonteado em prosa sem criar referência técnica cross-batch;
- os seis biomas de caverna são registrados em `EndBiomesProvider` com `EndTags.IS_END_CAVE` e aparecem explicitamente no datapack `betterend:is_end_cave`;
- todos os sete IDs aparecem em `registerBiomeToggles()`, portanto ocorrência concreta permanece condicionada à configuração e ao worldgen ativos;
- nomes como `Vazia`, `Exuberante`, `Aurora`, `Jade` e a categoria cave não são convertidos em densidade, iluminação, composição mineral, altitude, profundidade, tamanho, frequência ou outros parâmetros sem fonte específica;
- TDD confirmado no commit `e54e2607177de769dcbce0d625226c1f960cef72`, RPG Skill Tree CI run `33996432070`, job `101387676028`: 978 testes, exatamente 1 falha no Batch20 pela ausência de `betterend/biomes-batch3.json`; o corpus foi adicionado somente depois desse RED correto.

## Lote 21 — BetterNether: New Dawn / biomas do Nether (batch 1)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/betternether/biomes-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:betternether:bone_reef` — Bone Reef
2. `BIOME:betternether:crimson_glowing_woods` — Crimson Glowing Woods
3. `BIOME:betternether:crimson_pinewood` — Crimson Pinewood
4. `BIOME:betternether:flooded_deltas` — Flooded Deltas
5. `BIOME:betternether:gloomwood` — Gloomwood
6. `BIOME:betternether:gravel_desert` — Gravel Desert
7. `BIOME:betternether:magma_land` — Magma Land
8. `BIOME:betternether:nether_grasslands` — Nether Grasslands
9. `BIOME:betternether:nether_jungle` — Nether Jungle
10. `BIOME:betternether:nether_mushroom_forest` — Nether Mushroom Forest

### Critérios editoriais aplicados ao lote 21

- provider reconciliado com a modlist/runtime e a Auditoria Mestre do Notion: `BetterNether-21.0.26.jar`, namespace `betternether`, estado `Instalado`, decisão `Manter`;
- o recorte corresponde aos dez primeiros biomas na ordem de `NetherBiomes.java` e `NetherBiomesProvider.bootstrap()` do commit congelado `543127cab48b1ecb0017fee4222af25e0583e185`, cujo `gradle.properties` declara Minecraft 1.21.1 e BetterNether 21.0.26;
- `NetherBiomeBuilder.createKey()` confirma os registry paths ao normalizar espaços para `_` e minúsculas;
- nove títulos do lote são preservados exatamente do `assets/betternether/lang/pt_br.json`, que os mantém em inglês; `gloomwood` não possui chave pt-BR e usa explicitamente o valor oficial `Gloomwood` do `en_us.json`, sem fingir tradução pt-BR;
- `gloomwood` é declarado e bootstrapped pelo provider, mas não aparece em `registerBiomeToggles()`; os outros nove aparecem. A diferença é registrada sem inferir bug ou obrigatoriedade;
- `nether_grasslands` possui numeric provider próprio registrado pelo provider; sua existência é documentada sem congelar números internos em prosa;
- nenhuma temperatura, umidade, chance, densidade, peso, extensão, geometria, altura ou frequência numérica do worldgen foi congelada no corpus;
- TDD confirmado no commit `9968ddc6db31a4e3e14ef9056f8a4e7fccbfb2b5`, RPG Skill Tree CI run `33998199993`, job `101392297879`: 979 testes, exatamente 1 falha no Batch21 pela ausência de `betternether/biomes-batch1.json`; o corpus foi adicionado somente depois desse RED correto.

## Lote 22 — BetterNether: New Dawn / biomas do Nether (batch 2)

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/betternether/biomes-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `BIOME:betternether:nether_mushroom_forest_edge` — Nether Mushroom Forest Edge
2. `BIOME:betternether:nether_swampland` — Nether Swampland
3. `BIOME:betternether:nether_swampland_terraces` — Nether Swampland Terraces
4. `BIOME:betternether:old_fungiwoods` — Old Fungiwoods
5. `BIOME:betternether:old_swampland` — Old Swampland
6. `BIOME:betternether:old_warped_woods` — Old Warped Woods
7. `BIOME:betternether:poor_nether_grasslands` — Poor Nether Grasslands
8. `BIOME:betternether:soul_plain` — Soul Plain
9. `BIOME:betternether:sulfuric_bone_reef` — Sulfuric Bone Reef
10. `BIOME:betternether:upside_down_forest` — Upside Down Forest

### Critérios editoriais aplicados ao lote 22

- o recorte continua imediatamente após `nether_mushroom_forest` e cobre os biomas 11–20 na ordem de `NetherBiomes.java` e `NetherBiomesProvider.bootstrap()` do commit congelado `543127cab48b1ecb0017fee4222af25e0583e185`;
- os dez títulos são preservados exatamente do `assets/betternether/lang/pt_br.json`, que os mantém em inglês;
- todos os dez IDs aparecem em `registerBiomeToggles()`;
- `NetherMushroomForest.withEdgeBiome()` aponta para `NETHER_MUSHROOM_FORREST_EDGE.key`, cujo registry path gerado é `nether_mushroom_forest_edge`;
- o numeric provider e o selector de surface rules desse edge usam a grafia oficial `nether_mushroom_forrest_edge`; essa divergência textual é registrada sem inferir bug, peso, escolha ou valor numérico;
- palavras dos nomes como `Old`, `Poor`, `Soul`, `Sulfuric`, `Terraces` e `Upside Down` não são convertidas em idade/depreciação, escassez, composição, risco, geometria ou outros parâmetros não fonteados;
- nenhuma temperatura, umidade, chance, densidade, peso, extensão, geometria, altura ou frequência numérica foi congelada no corpus;
- TDD confirmado no commit `87570747a4e9c1bb37a99523c553f75ca41c7d2a`, RPG Skill Tree CI run `33999540955`, job `101395818638`: 980 testes, exatamente 1 falha no Batch22 pela ausência de `betternether/biomes-batch2.json`; o corpus foi adicionado somente depois desse RED correto.

## Estado acumulado

- entidades vanilla reais no corpus: **80**;
- entradas de flora vanilla reais no corpus: **20**;
- entradas editoriais vanilla totais: **100**;
- entradas de worldgen Alex's Caves reais no corpus: **10**;
- entradas de worldgen Oh The Biomes We've Gone reais no corpus: **55**;
- entradas de worldgen BetterEnd: New Dawn reais no corpus: **27**;
- entradas de worldgen BetterNether: New Dawn reais no corpus: **20**;
- entradas editoriais totais: **212**;
- lotes documentados: **22**;
- namespaces atuais em produção: `minecraft`, `alexscaves`, `biomeswevegone`, `betterend`, `betternether`;
- o Stage 10.10 permanece aberto até a cobertura editorial exigida pela modlist e os demais gates do plano canônico serem concluídos.