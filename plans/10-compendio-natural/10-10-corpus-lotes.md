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

## Lote 11 — TerraFirmaCraft / cultivos básicos

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/crops-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:tfc:crop/barley` — Cevada
2. `FLORA:tfc:crop/oat` — Aveia
3. `FLORA:tfc:crop/rye` — Centeio
4. `FLORA:tfc:crop/maize` — Milho
5. `FLORA:tfc:crop/wheat` — Trigo
6. `FLORA:tfc:crop/rice` — Arroz
7. `FLORA:tfc:crop/beet` — Beterraba
8. `FLORA:tfc:crop/cabbage` — Alface
9. `FLORA:tfc:crop/carrot` — Cenoura
10. `FLORA:tfc:crop/garlic` — Alho

### Critérios editoriais aplicados ao lote 11

- o lote inicia a cobertura editorial não-vanilla pelo eixo TFC/ambiente/agro, após os 100 verbetes vanilla usados como referência de qualidade;
- o provider foi fixado na versão instalada TerraFirmaCraft `1.21.1-4.2.8` e as evidências editoriais apontam para o tag upstream `v4.2.8`;
- registry IDs e nomes pt-BR foram mantidos conforme o provider, inclusive `tfc:crop/cabbage` com o rótulo localizado `Alface`, sem reescrever a identidade técnica;
- o texto de cultivo é sustentado pelo Field Guide do TFC e evita congelar em prosa limiares numéricos mutáveis de clima, hidratação ou nutrientes;
- planta cultivada, forma silvestre, estado morto e sementes são tratadas como identidades técnicas distintas, sem fusão editorial de registry IDs;
- as dez fichas usam `availability: OPTIONAL`, pois TerraFirmaCraft é um provider opcional: a ficha permanece carregável quando o provider está ausente e pode coexistir com a entrada técnica quando ele está presente;
- o loader ganhou teste de regressão para essa semântica: `OPTIONAL` é aceito com provider ausente ou presente, `RUNTIME` continua exigindo presença e `LEGACY` continua fail-closed quando a entrada técnica existe;
- antes deste registro, o HEAD funcional passou pelo Compendium Editorial CI #251 e pelo RPG Skill Tree CI #2463, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 12 — TerraFirmaCraft / cultivos alimentares complementares

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/crops-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:tfc:crop/cassava` — Mandioca
2. `FLORA:tfc:crop/green_bean` — Vagem
3. `FLORA:tfc:crop/lentil` — Lentilha
4. `FLORA:tfc:crop/peanut` — Amendoim
5. `FLORA:tfc:crop/soybean` — Soja
6. `FLORA:tfc:crop/onion` — Cebola
7. `FLORA:tfc:crop/potato` — Batata
8. `FLORA:tfc:crop/tomato` — Tomate
9. `FLORA:tfc:crop/red_bell_pepper` — Pimentão vermelho
10. `FLORA:tfc:crop/yellow_bell_pepper` — Pimentão amarelo

### Critérios editoriais aplicados ao lote 12

- o provider permanece fixado no TerraFirmaCraft `1.21.1-4.2.8`, com registry IDs, localização e Field Guide auditados no tag upstream `v4.2.8`;
- `Vagem`, `Soja`, `Cebola`, `Batata` e `Tomate` preservam a localização pt-BR do provider, normalizando apenas capitalização quando necessário;
- onde o arquivo `pt_br.json` ainda mantém o rótulo em inglês (`Cassava`, `Lentil`, `Peanut`, `Red Bell Pepper` e `Yellow Bell Pepper`), o Compêndio fornece os aliases editoriais `Mandioca`, `Lentilha`, `Amendoim`, `Pimentão vermelho` e `Pimentão amarelo`, sem alterar nenhum registry ID;
- Vagem e Tomate registram a mecânica comprovada de cultivo trepador em dois blocos com graveto de suporte, mas nenhum limite numérico de clima, hidratação, nutrientes ou estágio foi congelado em prosa;
- planta cultivada, forma silvestre, estado morto e sementes continuam tratadas como identidades técnicas distintas;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL`, válidas tanto com o provider ausente quanto presente conforme o contrato corrigido no lote anterior;
- o TDD do lote reproduziu RED exclusivamente pela ausência de `crops-batch2.json`; após o corpus, o HEAD funcional passou pelo Compendium Editorial CI #272 e pelo RPG Skill Tree CI #2493, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 13 — TerraFirmaCraft / fechamento dos cultivos e início dos arbustos frutíferos

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/agro-batch3.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:tfc:crop/squash` — Moringa
2. `FLORA:tfc:crop/pumpkin` — Abóbora
3. `FLORA:tfc:crop/melon` — Melancia
4. `FLORA:tfc:crop/canola` — Canola
5. `FLORA:tfc:crop/radish` — Rabanete
6. `FLORA:tfc:crop/alfalfa` — Alfafa
7. `FLORA:tfc:crop/jute` — Juta
8. `FLORA:tfc:crop/papyrus` — Papiro
9. `FLORA:tfc:crop/sugarcane` — Cana de açúcar
10. `FLORA:tfc:plant/blackberry_bush` — Arbusto de amora

### Critérios editoriais aplicados ao lote 13

- os nove cultivos do lote fecham todas as entradas restantes da coleção `CROPS` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8` depois dos lotes 11 e 12; o décimo verbete inicia a próxima fatia agro/flora com o primeiro arbusto frutífero auditado, `blackberry_bush`;
- o rótulo `Moringa` para `tfc:crop/squash` é preservado exatamente como fornecido pelo `pt_br.json` do provider, sem corrigir silenciosamente a localização nem alterar o registry ID técnico;
- onde o provider ainda deixa nomes em inglês, o Compêndio fornece aliases editoriais pt-BR próprios: `Pumpkin` → `Abóbora`, `Melon` → `Melancia`, `Radish` → `Rabanete`, `Alfalfa` → `Alfafa` e `Papyrus` → `Papiro`;
- Abóbora e Melancia registram somente a mecânica comprovada de cultivo de propagação e produção de blocos de fruto adjacentes; quantidades, clima, hidratação e nutrientes não foram congelados em prosa;
- Canola, Rabanete e Alfafa preservam o papel de culturas de cobertura que enriquecem o solo, enquanto Juta, Papiro e Cana de açúcar registram crescimento em dois blocos conforme o Field Guide, sem transportar limites numéricos mutáveis para o texto editorial;
- o Arbusto de amora usa a localização pt-BR do próprio provider e registra apenas o ciclo sazonal geral e a associação oficial com áreas de poucas árvores, deixando calendário, clima e worldgen concretos sob autoridade do provider/datapacks;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL` e carregam tanto com o TerraFirmaCraft ausente quanto presente conforme o contrato do loader;
- o TDD reproduziu RED exclusivamente pela ausência intencional de `agro-batch3.json` no RPG Skill Tree CI #2516 (`151 tests completed, 1 failed`); após o corpus, o HEAD funcional passou pelo Compendium Editorial CI #289 e pelo RPG Skill Tree CI #2517, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 14 — TerraFirmaCraft / arbustos frutíferos remanescentes

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/berries-batch4.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:tfc:plant/raspberry_bush` — Arbusto de framboesa
2. `FLORA:tfc:plant/blueberry_bush` — Arbusto de mirtilo
3. `FLORA:tfc:plant/elderberry_bush` — Sabugueiro
4. `FLORA:tfc:plant/snowberry_bush` — Arbusto de snowberry
5. `FLORA:tfc:plant/bunchberry_bush` — Arbusto de bunchberry
6. `FLORA:tfc:plant/gooseberry_bush` — Arbusto de groselha
7. `FLORA:tfc:plant/cloudberry_bush` — Arbusto de cloudberry
8. `FLORA:tfc:plant/strawberry_bush` — Morangueiro
9. `FLORA:tfc:plant/wintergreen_berry_bush` — Arbusto de wintergreen berry
10. `FLORA:tfc:plant/cranberry_bush` — Arbusto de cranberry

### Critérios editoriais aplicados ao lote 14

- o lote fecha as dez entradas restantes da coleção `BERRIES` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8` depois de `blackberry_bush` ter iniciado essa cobertura no lote 13;
- registry IDs, classificação de crescimento e localização foram confrontados com `resources/constants.py`, o Field Guide e `pt_br.json` do provider; no caso da framboesa, o Compêndio normaliza apenas o espaço final e a capitalização da localização oficial;
- `Snowberry Bush`, `Bunchberry Bush`, `Cloudberry Bush` e `Wintergreen Berry Bush` ainda permanecem em inglês no locale pt-BR do provider, portanto recebem aliases editoriais conservadores sem inventar espécie botânica que o mod não declare;
- Framboesa, Mirtilo e Sabugueiro preservam o comportamento oficial de arbustos expansivos e a associação documentada a áreas com poucas árvores; os demais arbustos baixos usam somente as regras gerais confirmadas pelo Field Guide;
- Cranberry mantém sua exceção real de berry aquática/submersa, sem transformar parâmetros de água, clima ou geração do mundo em constantes editoriais;
- nenhum limite numérico de temperatura, hidratação, chuva, mês, estágio ou frequência de geração foi congelado em prosa;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL` e foram exercitadas com o TerraFirmaCraft ausente e presente pelo teste de regressão do lote;
- o TDD reproduziu RED na PR draft #299 exclusivamente pela ausência intencional de `berries-batch4.json`; após o corpus, o head funcional `83fb191db9fe7d269a53ed3174c1e9cb1a87f62e` passou o Compendium Editorial CI #292 e o RPG Skill Tree CI #2524 completos, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, NeoForge build, verificação do JAR e dedicated-server smoke.

## Lote 15 — TerraFirmaCraft / pomar e início das árvores de madeira

Arquivos:

- `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/orchards-batch5.json`
- `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/trees-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `FLORA:tfc:plant/banana_sapling` — Muda de bananeira
2. `FLORA:tfc:plant/cherry_sapling` — Muda de cerejeira
3. `FLORA:tfc:plant/green_apple_sapling` — Pé de maçã verde
4. `FLORA:tfc:plant/red_apple_sapling` — Pé de maça vermelha
5. `FLORA:tfc:plant/lemon_sapling` — Muda de limoeiro
6. `FLORA:tfc:plant/olive_sapling` — Muda de oliveira
7. `FLORA:tfc:plant/orange_sapling` — Muda de laranjeira
8. `FLORA:tfc:plant/peach_sapling` — Muda de pessegueiro
9. `FLORA:tfc:plant/plum_sapling` — Muda de ameixoeira
10. `TREE:tfc:wood/sapling/acacia` — Muda de acácia

### Critérios editoriais aplicados ao lote 15

- as nove primeiras entradas fecham a coleção `FRUITS` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8`; como essa coleção possui exatamente nove frutíferas arbóreas, a décima entrada inicia a coleção `WOODS` pela primeira espécie declarada no provider, `ACACIA`;
- a classificação foi determinada pelo runtime, não pelo nome: as mudas das frutíferas comuns usam `FruitTreeSaplingBlock`, derivado de `BushBlock`, e a bananeira possui implementação especializada, por isso entram como `FLORA`; a muda de acácia usa `TFCSaplingBlock`, derivado de `SaplingBlock`, sendo descoberta como `TREE` pelo coletor do Compêndio;
- os rótulos pt-BR foram confrontados com `pt_br.json`; espaços finais do provider são apenas normalizados, enquanto `Pé de maça vermelha` preserva deliberadamente a grafia da localização oficial em vez de corrigi-la silenciosamente;
- a bananeira mantém sua exceção estrutural real: crescimento vertical sem a copa de folhas das frutíferas comuns, frutificação no topo e necessidade de novo plantio após a colheita conforme o Field Guide;
- as demais frutíferas registram somente o ciclo sazonal, formação de galhos/folhas e crescimento controlado pelo sistema do provider, sem congelar meses, temperatura, hidratação ou tempo de crescimento em prosa;
- a acácia registra a identidade `Wood.ACACIA`, o uso de `TFCSaplingBlock` e sua família de blocos de madeira, mantendo timings e modificadores de crescimento sob autoridade da configuração/runtime;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL` e o teste do lote comprova carregamento com TerraFirmaCraft ausente e presente;
- o TDD produziu RED válido na PR draft #309 pelo RPG Skill Tree CI #2600: após compilação e checks prévios verdes, `157 tests completed, 1 failed`, exclusivamente em `CompendiumCheckedInEditorialBatch15JUnitTest` pela ausência intencional dos pacotes; após o corpus, o HEAD funcional passou pelo Compendium Editorial CI #349 e pelo RPG Skill Tree CI #2610 completos, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 16 — TerraFirmaCraft / árvores de madeira, segunda fatia

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/trees-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `TREE:tfc:wood/sapling/ash` — Muda de ash
2. `TREE:tfc:wood/sapling/aspen` — Muda de aspen
3. `TREE:tfc:wood/sapling/birch` — Muda de eucalipto
4. `TREE:tfc:wood/sapling/blackwood` — Muda de acácia-negra
5. `TREE:tfc:wood/sapling/chestnut` — Muda de castanheira
6. `TREE:tfc:wood/sapling/douglas_fir` — Muda de douglas fir
7. `TREE:tfc:wood/sapling/hickory` — Muda de nogueira
8. `TREE:tfc:wood/sapling/kapok` — Muda de sumaúma
9. `TREE:tfc:wood/sapling/mangrove` — Propágulo de mangrove
10. `TREE:tfc:wood/sapling/maple` — Muda de bordo

### Critérios editoriais aplicados ao lote 16

- o lote continua imediatamente a coleção `WOODS` depois de `ACACIA`, seguindo a ordem declarada por `Wood.java` no TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8`: `ASH`, `ASPEN`, `BIRCH`, `BLACKWOOD`, `CHESTNUT`, `DOUGLAS_FIR`, `HICKORY`, `KAPOK`, `MANGROVE` e `MAPLE`;
- as dez entradas são mudas registradas por `Wood.BlockType.SAPLING` com `TFCSaplingBlock`, sustentando a classificação `TREE` pelo runtime em vez de inferência textual do registry ID;
- os rótulos foram confrontados com `pt_br.json` e os espaços finais do provider são apenas normalizados; a associação oficial `birch` → `Muda de eucalipto` é preservada explicitamente sem alterar `Wood.BIRCH` nem o registry ID;
- `Mangrove Propagule` ainda permanece em inglês no locale pt-BR do provider, portanto recebe o alias editorial conservador `Propágulo de mangrove`, traduzindo somente o termo genérico sem inventar uma espécie botânica diferente;
- o crescimento permanece sob autoridade de `TFCSaplingBlock`, das configurações por muda e do modificador global do provider; duração, chance e outros valores numéricos mutáveis não são congelados na prosa editorial;
- troncos, folhas, tábuas e demais componentes de cada família continuam identidades técnicas próprias e são citados apenas como família de madeira, sem fundir seus registry IDs com a ficha da muda;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL` e o teste do lote comprova carregamento com TerraFirmaCraft ausente e presente;
- o TDD produziu RED válido na draft #313 pelo RPG Skill Tree CI #2645: no merge-ref contra `main@9958caaabebff95bfbbd0a226ca571e5bfe5316c`, compilação e checks prévios passaram e `163 tests completed, 1 failed`, exclusivamente em `CompendiumCheckedInEditorialBatch16JUnitTest` pela ausência intencional de `trees-batch2.json`; após o corpus, o primeiro GREEN funcional passou pelo Compendium Editorial CI #381 e pelo RPG Skill Tree CI #2648, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 17 — TerraFirmaCraft / fechamento das árvores de madeira e início da fauna aquática

Arquivos:

- `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/trees-batch3.json`
- `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch1.json`

Estado: `REVIEWED`

Entradas:

1. `TREE:tfc:wood/sapling/oak` — Muda de carvalho
2. `TREE:tfc:wood/sapling/palm` — Muda de palmeira
3. `TREE:tfc:wood/sapling/pine` — Muda de pinheiro
4. `TREE:tfc:wood/sapling/rosewood` — Muda de jacarandá
5. `TREE:tfc:wood/sapling/sequoia` — Muda de sequoia
6. `TREE:tfc:wood/sapling/spruce` — Muda de espruce
7. `TREE:tfc:wood/sapling/sycamore` — Muda de sicômoro
8. `TREE:tfc:wood/sapling/white_cedar` — Muda de cedro branco
9. `TREE:tfc:wood/sapling/willow` — Muda de salgueiro
10. `ENTITY:tfc:bluegill` — Peixe bluegill

### Critérios editoriais aplicados ao lote 17

- as nove árvores seguem imediatamente `MAPLE` na ordem declarada por `Wood.java` e fecham a coleção `WOODS` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8` com `OAK`, `PALM`, `PINE`, `ROSEWOOD`, `SEQUOIA`, `SPRUCE`, `SYCAMORE`, `WHITE_CEDAR` e `WILLOW`;
- as nove mudas são registradas por `Wood.BlockType.SAPLING` com `TFCSaplingBlock`; a palmeira preserva a exceção real do provider que também permite areia como substrato, sem generalizar essa regra às demais madeiras;
- os rótulos pt-BR das mudas foram confrontados com `pt_br.json`, normalizando somente espaços finais quando presentes e preservando associações oficiais como `rosewood` → `Muda de jacarandá`;
- a décima entrada inicia a fauna aquática pela primeira constante do enum `Fish`, `BLUEGILL`, registrada pelo provider como `FreshwaterFish` na categoria `WATER_AMBIENT`;
- `entity.tfc.bluegill` ainda possui o rótulo `Bluegill` em inglês no locale pt-BR; o Compêndio usa o alias conservador `Peixe bluegill`, traduzindo apenas a classificação genérica sem inventar um nome comum brasileiro que o mod não declara;
- `FreshwaterFish` deriva de `Salmon`, usa controle de movimento do TFC, participa da lógica de pesca, aceita água no contrato de spawn e suporta coleta no recipiente configurado; biome, frequência, dimensões e demais números mutáveis permanecem fora da prosa editorial;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL` e o teste do lote comprova carregamento com TerraFirmaCraft ausente e presente;
- o TDD produziu RED válido na draft #316 pelo RPG Skill Tree CI #2683: compilação e checks prévios passaram e `171 tests completed, 1 failed`, exclusivamente em `CompendiumCheckedInEditorialBatch17JUnitTest` pela ausência intencional de `trees-batch3.json` e `fauna-batch1.json`; após o corpus, o primeiro GREEN funcional passou pelo Compendium Editorial CI #424 e pelo RPG Skill Tree CI #2697, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 18 — TerraFirmaCraft / peixes de água doce, segunda fatia

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:tfc:crappie` — Peixe crappie
2. `ENTITY:tfc:lake_trout` — Peixe lake trout
3. `ENTITY:tfc:largemouth_bass` — Peixe largemouth bass
4. `ENTITY:tfc:rainbow_trout` — Peixe rainbow trout
5. `ENTITY:tfc:salmon` — Peixe salmon
6. `ENTITY:tfc:smallmouth_bass` — Peixe smallmouth bass
7. `ENTITY:tfc:northern_pike` — Peixe northern pike
8. `ENTITY:tfc:burbot` — Peixe burbot
9. `ENTITY:tfc:arctic_char` — Peixe arctic char
10. `ENTITY:tfc:muksun` — Peixe muksun

### Critérios editoriais aplicados ao lote 18

- o lote continua imediatamente `BLUEGILL` na ordem declarada por `Fish.java` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8`, cobrindo exatamente `CRAPPIE`, `LAKE_TROUT`, `LARGEMOUTH_BASS`, `RAINBOW_TROUT`, `SALMON`, `SMALLMOUTH_BASS`, `NORTHERN_PIKE`, `BURBOT`, `ARCTIC_CHAR` e `MUKSUN`;
- as dez constantes são registradas pelo mapa `FRESHWATER_FISH` como `FreshwaterFish` na categoria `WATER_AMBIENT`; a classificação deriva do runtime real e não do texto dos registry IDs;
- o locale `pt_br` do provider ainda mantém os dez nomes de espécie em inglês, portanto o Compêndio usa aliases conservadores `Peixe ...`, traduzindo somente a categoria genérica e evitando importar nomes taxonômicos externos que o mod não adotou;
- `FreshwaterFish` deriva de `Salmon`, usa `TFCFishMoveControl`, participa da lógica de pesca por `GetHookedGoal`, aceita água no contrato aquático e suporta coleta no recipiente configurado para a espécie;
- `Fish.SALMON` preserva a exceção real do provider de reutilizar os eventos sonoros vanilla de salmão, enquanto as demais constantes registram seu conjunto de sons pelo TFC;
- bioma, clima, frequência, dimensão, tamanho de grupo e demais parâmetros de spawn/worldgen permanecem sob autoridade do provider, da configuração e dos datapacks ativos; nenhum desses valores foi congelado na prosa editorial;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL` e o teste do lote comprova carregamento tanto com TerraFirmaCraft ausente quanto presente;
- o TDD produziu RED válido na draft #322 pelo RPG Skill Tree CI #2748: Core/wiki/coverage e compilação passaram e `182 tests completed, 1 failed`, exclusivamente em `CompendiumCheckedInEditorialBatch18JUnitTest` pela ausência intencional de `fauna-batch2.json`; após o corpus, o primeiro GREEN funcional passou pelo Compendium Editorial CI #471 e pelo RPG Skill Tree CI #2752, incluindo JUnit, NeoForge GameTests, validadores do Compêndio, build, verificação do JAR e dedicated-server smoke.

## Lote 19 — TerraFirmaCraft / fechamento dos peixes de água doce e continuação da fauna aquática

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch3.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:tfc:tilapia` — Peixe tilapia
2. `ENTITY:tfc:spotted_gudgeon` — Peixe spotted gudgeon
3. `ENTITY:tfc:peacock_bass` — Peixe peacock bass
4. `ENTITY:tfc:pacu` — Peixe pacu
5. `ENTITY:tfc:red_piranha` — Peixe red piranha
6. `ENTITY:tfc:cod` — Peixe cod
7. `ENTITY:tfc:tropical_fish` — Peixe tropical fish
8. `ENTITY:tfc:pufferfish` — Peixe pufferfish
9. `ENTITY:tfc:jellyfish` — Água-viva jellyfish
10. `ENTITY:tfc:isopod` — Isópode isopod

### Critérios editoriais aplicados ao lote 19

- as cinco primeiras entradas continuam imediatamente `MUKSUN` e fecham, na ordem de `Fish.java` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8`, as constantes restantes de `FRESHWATER_FISH`: `TILAPIA`, `SPOTTED_GUDGEON`, `PEACOCK_BASS`, `PACU` e `RED_PIRANHA`;
- as cinco seguintes continuam a ordem de registros aquáticos `WATER_AMBIENT` de `TFCEntities.java` sem saltos, cobrindo `COD`, `TROPICAL_FISH`, `PUFFERFISH`, `JELLYFISH` e `ISOPOD`;
- o locale `pt_br` do provider mantém os nomes específicos dessas entidades em inglês; os aliases editoriais traduzem somente a classificação genérica e não inventam nomenclatura taxonômica externa;
- `COD`, `TROPICAL_FISH` e `PUFFERFISH` preservam as classes correspondentes usadas pelo TFC, o contrato de água salgada, o movimento do provider, a interação com pesca e a coleta em balde sem congelar parâmetros numéricos mutáveis;
- `JELLYFISH` preserva variantes persistidas, coleta em balde, água salgada e dano por contato comprovado pelo runtime, sem fixar em prosa o valor numérico desse dano;
- `ISOPOD` usa o contrato `AquaticCritter::salty`, mantém a evasão e o deslocamento pelo fundo comprovados pelo provider e não recebe comportamento de coleta, reprodução ou alimentação que o código auditado não declare;
- clima, bioma, frequência, grupo e demais parâmetros de spawn/worldgen permanecem sob autoridade do TerraFirmaCraft, da configuração e dos datapacks ativos;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL`, e `CompendiumCheckedInEditorialBatch19JUnitTest` valida exatamente essas dez entradas tanto com o provider ausente quanto presente;
- o TDD produziu RED válido na draft #334 contra `main@f613dac5a15b26c7a92e07a9d9cb537c2412ddf2`: o RPG Skill Tree CI #2834 chegou a `871 tests completed, 1 failed`, exclusivamente pela ausência intencional de `fauna-batch3.json`;
- durante a validação funcional foi identificado um desalinhamento pré-existente de CI: `Volcanoes Consolidated Release Readiness` exigia `Volcanoes Consolidation Contract` em toda PR, enquanto este último tinha filtros `paths` e não disparava em mudanças editoriais. A correção mínima removeu somente esses filtros; no head `6bcbd5f6ae39226417ef9f59844df5e76c983b3f`, `Volcanoes Consolidation Contract` #80, Compendium Editorial CI #564, RPG Skill Tree CI #2900, SonarQube #135, Worldgen Compatibility Matrix #102 e `Volcanoes Consolidated Release Readiness` #84 concluíram com `success`, incluindo o `aggregate-exact-head` 10/10.

## Lote 20 — TerraFirmaCraft / continuação da fauna aquática e início da fauna anfíbia

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch4.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:tfc:lobster` — Lagosta lobster
2. `ENTITY:tfc:crayfish` — Lagostim crayfish
3. `ENTITY:tfc:horseshoe_crab` — Caranguejo-ferradura horseshoe crab
4. `ENTITY:tfc:dolphin` — Golfinho dolphin
5. `ENTITY:tfc:orca` — Orca
6. `ENTITY:tfc:manatee` — Peixe-boi manatee
7. `ENTITY:tfc:squid` — Lula squid
8. `ENTITY:tfc:octopoteuthis` — Lula octopoteuthis
9. `ENTITY:tfc:turtle` — Tartaruga turtle
10. `ENTITY:tfc:penguin` — Pinguim penguin

### Critérios editoriais aplicados ao lote 20

- o lote continua imediatamente `ISOPOD` na ordem real de registros de `TFCEntities.java` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8`, atravessando `WATER_AMBIENT`, `WATER_CREATURE`, `UNDERGROUND_WATER_CREATURE` e `CREATURE` sem saltos;
- `LOBSTER` e `HORSESHOE_CRAB` usam `AquaticCritter::salty`, enquanto `CRAYFISH` usa `AquaticCritter::fresh`; a ficha preserva evasão, fuga após agressão e deslocamento pelo fundo comprovados pela classe compartilhada sem inventar alimentação, reprodução ou captura;
- `DOLPHIN` e `ORCA` reutilizam `TFCDolphin` e seu contrato de água salgada, mantendo respiração, procura por água, pesca, ataque, nado, salto e acompanhamento de barcos apenas no nível demonstrado pelo código;
- `MANATEE` usa a implementação própria `Manatee`, aceita água comum e registra somente os comportamentos de nado, pânico, evasão e debater-se comprovados pelo runtime;
- `SQUID` usa `TFCSquid`, enquanto `OCTOPOTEUTHIS` estende essa implementação e acrescenta o contrato de brilho `IGlow`; tamanho dinâmico, tinta, persistência de estado e escurecimento permanecem descritos sem congelar probabilidades, durações ou intensidades numéricas;
- `TURTLE` e `PENGUIN` usam a base `AmphibiousAnimal`/`AmphibianAi`; a tartaruga mantém a atividade de `PLAY_DEAD`, e o pinguim sobrescreve `isPlayingDeadEffective()` para `false`, de modo que a atividade ainda pode operar sobre memórias/alvos, mas os efeitos adicionais de regeneração e resistência de `AmphibianPlayDeadBehavior` não são concedidos ao pinguim;
- o locale `pt_br` do provider mantém os nomes específicos do lote em inglês, salvo `Orca`; por isso os aliases editoriais traduzem apenas a categoria comum quando útil e preservam o token oficial do mod para rastreabilidade, sem importar taxonomia externa;
- bioma, clima, frequência, tamanho de grupo e demais parâmetros de spawn/worldgen permanecem sob autoridade do provider, da configuração e dos datapacks ativos; nenhum desses valores foi congelado na prosa editorial;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL`, e `CompendiumCheckedInEditorialBatch20JUnitTest` valida exatamente essas dez entradas com TerraFirmaCraft ausente e presente;
- o TDD produziu RED válido na draft #350 contra `main@a20b9efe0b1b5e6c7dc2a1e9fa386440e8469482`: o teste compilou normalmente e o RPG Skill Tree CI #2963 executou `872 tests completed, 1 failed`, exclusivamente em `CompendiumCheckedInEditorialBatch20JUnitTest` no `assertNotNull` do recurso intencionalmente ausente `fauna-batch4.json`; após o corpus, o primeiro GREEN funcional passou pelo Compendium Editorial CI #604 e pelo RPG Skill Tree CI #2968, incluindo JUnit, NeoForge GameTests, todos os validadores do Compêndio/runtime, NeoForge build, verificação do JAR e dedicated-server smoke.

## Lote 21 — TerraFirmaCraft / fechamento da fauna costeira e início dos predadores

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch5.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:tfc:leopard_seal` — Leopard Seal
2. `ENTITY:tfc:frog` — Frog
3. `ENTITY:tfc:polar_bear` — Urso polar
4. `ENTITY:tfc:grizzly_bear` — Urso pardo
5. `ENTITY:tfc:black_bear` — Urso preto
6. `ENTITY:tfc:cougar` — Puma
7. `ENTITY:tfc:panther` — Pantera
8. `ENTITY:tfc:lion` — Leoa
9. `ENTITY:tfc:sabertooth` — Sabertooth
10. `ENTITY:tfc:tiger` — Tiger

### Critérios editoriais aplicados ao lote 21

- o lote continua imediatamente `PENGUIN` na ordem real de registros de `TFCEntities.java` do TerraFirmaCraft `1.21.1-4.2.8`/upstream `v4.2.8`, cobrindo exatamente `LEOPARD_SEAL`, `FROG`, `POLAR_BEAR`, `GRIZZLY_BEAR`, `BLACK_BEAR`, `COUGAR`, `PANTHER`, `LION`, `SABERTOOTH` e `TIGER` e encerrando antes de `CROCODILE`;
- `LEOPARD_SEAL` usa `LeopardSeal`, derivada de `AmphibiousAnimal`, com `PinnipedAI`, alimento em `tfc:seal_food` e `isPlayingDeadEffective()` retornando `false`; a ficha não converte comentários pendentes do upstream em comportamento implementado;
- `TFCFrog` persiste sexo, familiaridade, alimentação e estado temporal de acasalamento, exige alimento não apodrecido pertencente a `tfc:frog_food` e escolhe sua variante de spawn a partir da temperatura média consultada pelo runtime, sem congelar limiares numéricos na prosa;
- `POLAR_BEAR`, `GRIZZLY_BEAR` e `BLACK_BEAR` usam `Predator::createBear`, compartilhando perfil diurno, caça/combate, sono persistido e memória de lar; os três registros preservam também a imunidade à neve fofa declarada pelo provider;
- `COUGAR` e `PANTHER` compartilham `FelinePredator::createCougar`, enquanto `LION`, `SABERTOOTH` e `TIGER` usam suas fábricas específicas; os cinco felinos são criados com perfil noturno e herdam escalada, navegação especializada e estados de movimento de `FelinePredator` sem diferenças inventadas além das demonstradas pelo código;
- os títulos preservam a localização efetivamente fornecida pelo provider: `Urso polar`, `Urso pardo`, `Urso preto`, `Puma`, `Pantera` e inclusive `Leoa` para `tfc:lion`; `Leopard Seal`, `Frog`, `Sabertooth` e `Tiger` permanecem em inglês quando os materiais pt-BR auditados não fornecem uma tradução consolidada, sem importar nomenclatura taxonômica externa;
- clima, bioma, frequência, tamanho de grupo e demais parâmetros de spawn/worldgen permanecem sob autoridade do TerraFirmaCraft, da configuração e dos datapacks ativos; nenhum desses valores foi congelado na prosa editorial;
- todas as dez fichas permanecem `REVIEWED`/`OPTIONAL`, e `CompendiumCheckedInEditorialBatch21JUnitTest` valida exatamente essas dez entradas com TerraFirmaCraft ausente e presente;
- o TDD produziu RED válido na draft #362 no head `d3f8fa6adb3a0bff6dab728386da04dffe37d63c`: o RPG Skill Tree CI #3050 chegou à etapa JUnit com os checks anteriores verdes, e o build executado pelo audit de provenance registrou `884 tests completed, 1 failed`, exclusivamente em `CompendiumCheckedInEditorialBatch21JUnitTest` pela ausência intencional de `fauna-batch5.json`;
- após o corpus, a primeira validação expôs um único conflito contratual: a prosa citava literalmente o token reservado `TODO`, rejeitado pelo `CompendiumEditorialResourceLoader`; a correção mínima substituiu somente esse token por `pendente`, sem alterar identidade, fonte ou semântica factual. No head `174e5b7966d8633304c405dfbd02c5c7f660ce0c`, o Compendium Editorial CI #672 e o Volcanoes Third-Party Provenance Audit #252 concluíram com `success`, comprovando o GREEN funcional antes do fechamento documental.

## Estado acumulado

- entidades vanilla reais no corpus: **80**;
- entradas de flora vanilla reais no corpus: **20**;
- entradas editoriais vanilla totais: **100**;
- entradas editoriais TFC reais no corpus: **110**;
- entradas editoriais totais: **210**;
- lotes documentados: **21**;
- namespaces em produção: `minecraft`, `tfc`;
- o Stage 10.10 permanece aberto até a cobertura editorial exigida pela modlist e os demais gates do plano canônico serem concluídos.
