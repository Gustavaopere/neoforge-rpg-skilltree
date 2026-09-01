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
- o corpus corrigido passou pelo CI editorial #210 e pelo CI agregado #2378, incluindo JUnit, GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

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

## Estado acumulado

- entidades vanilla reais no corpus: **80**;
- entradas de flora vanilla reais no corpus: **20**;
- entradas editoriais vanilla totais: **100**;
- lotes documentados: **10**;
- namespace atual em produção: `minecraft`;
- o Stage 10.10 permanece aberto até a cobertura editorial exigida pela modlist e os demais gates do plano canônico serem concluídos.
