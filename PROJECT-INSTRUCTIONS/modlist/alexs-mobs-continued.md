# Alex's Mobs Continued

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c5a6f4d3f24f528793
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alex's Mobs Continued
- **Arquivo JAR:** `alexsmobs-2.1.11-neoforge+1.21.1.jar`
- **Versão 1.21.1:** 2.1.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Mobs, Exploração
- **Função:** Grande expansão de fauna/ecossistema com 89 mobs jogáveis documentados no roster oficial original, mais o Sea Bear secreto quando Super Secret Settings está ativo. O conteúdo cobre fauna terrestre/aquática/aérea, Nether e End, predador-presa, grupos/herds, domesticação e breeding seletivos, montarias, captura em balde, ovos/colônias, criaturas segmentadas, um chief (Warped Mosco), um boss (Void Worm), materiais/drops, armas, ferramentas, armaduras, blocos utilitários, efeitos/potions, Animal Dictionary, advancements e configuração granular de spawn. A ficha interna contém catálogo individual dos 89 mobs + Sea Bear, sistemas, itens, blocos, efeitos, riscos e matriz de validação.
- **Dependências:** OBRIGATÓRIA: CodxLib 1.6.0+ para a release 2.1.11; o pack instala codxlib-1.6.0-neoforge+1.21.1.jar. Citadel 2.7.1 permanece presente por outros consumidores, mas não é hard dependency externa deste port.
- **Sobreposição:** Sobrepõe parcialmente o domínio de fauna/mobs, exploração e ecossistema de outros grandes mob packs, mas não é redundância simples: cada criatura tem IA, cadeia de interação e recursos próprios. Interseções relevantes no pack: Enhanced AI (comportamento), AI-Improvements (custo/infra de IA), mods de worldgen/biomas (spawn), Alex's Delight/Farmer's Delight (uso culinário de drops), além de outros mods de fauna/bosses que aumentam densidade ecológica e carga de entidades.
- **Compatibilidade/Riscos:** Roster/registries continuam provider-native. Enhanced AI 4.2.3.0 pode alterar goals/pathfinding; worldgen/biome providers alteram distribuição de spawn; Alex's Delight 1.6 consome drops. 2.1.11 adiciona edição in-game de Spawn Group Sizes e depende do fix numérico do CodxLib 1.6.0. Patches feitos para outros ports de Alex não são presumidos compatíveis.
- **Observações:** Runtime físico 2.1.11. `/amc menu` → Spawning expõe Spawn Group Sizes e grava a mesma opção `spawnGroupSizes`. Catálogo 89 mobs normais + Sea Bear secreto foi preservado; não converter a publicidade '~116' em 116 criaturas comuns.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Alex's Mobs Continued 2.1.11 + CodxLib 1.6.0 + dossiê operacional existente e fontes do roster original.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/alexs-mobs-continued
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime 2.1.11, UI Spawn Group Sizes, CodxLib 1.6.0+, Enhanced AI 4.2.3.0 e catálogo operacional preservado no QC global #21.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita integralmente após feedback de que a descrição anterior era genérica e insuficiente para outro chat operar. A nova ficha passa a ser um dossiê operacional do conteúdo do mod e não apenas um resumo de propósito.
- **Data da última decisão:** 2026-09-07

> 🔎 **ESCOPO CANÔNICO DESTA FICHA.** O runtime físico do pack é `alexsmobs-2.1.11-neoforge+1.21.1.jar`, mod id `alexsmobs`, em NeoForge 1.21.1. A página pública de **Alex's Mobs Continued** usa a frase “around 116 animals and monsters”, porém a página oficial do Alex's Mobs original enumera **89 mobs de gameplay**. O registro de entidades também contém vários EntityTypes técnicos — partes de criaturas segmentadas, projéteis, veículos e entidades auxiliares. Portanto, para documentação de gameplay, esta ficha usa **89 mobs normais + Sea Bear secreto**, e não interpreta “~116” como 116 criaturas normais distintas.

## 1. Identidade, versão e authority
- **Mod:** Alex's Mobs Continued.
- **Runtime instalado:** `2.1.11`.
- **JAR físico:** `alexsmobs-2.1.11-neoforge+1.21.1.jar`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Mod id:** `alexsmobs`.
- **Papel:** grande provider de fauna, ecossistema, exploração, combate de criaturas, tame/breeding, mobilidade, drops funcionais, equipamentos e interações ambientais.
- **Origem:** continuação não oficial que preserva o conteúdo do Alex's Mobs original em versões modernas.
- **Decisão do pack:** **Manter**.
- **Authority de versão:** a modlist física prevalece. O snapshot físico de 08/09/2026 confirma que a instância já foi atualizada para **2.1.11**.

## 2. O que o mod realmente adiciona
O mod não é “um pacote de bichos decorativos”. O roster foi projetado para que as criaturas participem do mundo por **comportamento, cadeia ecológica e utilidade**. Há predador-presa, rebanhos e tropas, domesticação por condições específicas, breeding, imprinting, montarias, animais de transporte, pesca/forrageamento automatizável, mobs que transformam blocos/biomas, criaturas que produzem recursos sem serem mortas, entidades aquáticas capturáveis em balde, ovos e colônias, monstros multipartes, criaturas do Nether e End, um chief e um boss.

### Números que esta ficha consegue sustentar
- **89 mobs normais de gameplay** no roster oficial publicado do Alex's Mobs original.
- **+ 1 mob secreto/joke:** Sea Bear, condicionado a Super Secret Settings.
- **33 passivos**, **26 neutros**, **8 defensivos**, **12 placáveis/pacificáveis**, **8 hostis**, **1 chief mob** e **1 boss** entre os 89 normais.
- A wiki comunitária indexa **25 blocos próprios**, **19 efeitos de status**, **7 famílias de poções** e **135 páginas/entradas de itens**. O número 135 deve ser entendido como índice de conteúdo da wiki, não como contagem de registry ids comprovada nesta auditoria.
- O catálogo de equipamento documentado inclui pelo menos **37 peças/itens de equipamento**, com subconjuntos de **8 armas**, **9 ferramentas/utilitários** e **17 armaduras/vestíveis**, além de saddles/tack, veículos e itens de captura/spawn.

## 3. Catálogo completo de criaturas — 89 mobs + Sea Bear
> **Como ler:** “temperamento” segue a classificação da wiki comunitária: passivo, neutro, defensivo, placável, hostil, chief ou boss. A wiki é fonte secundária; detalhes críticos para automação/perks devem ser runtime-tested. O roster em si é cruzado com a listagem oficial.

### 3.1 Passivos — 33
1. **Banana Slug** — `banana_slug`. Old Growth Pine/Spruce Taiga. Lesma extremamente lenta, escala laterais de blocos e deixa slime ao se mover. Produz **Banana Slug Slime** em vida e ao morrer; o slime vira bloco absorvente que transforma água próxima em **Crystallized Mucus**, útil para drenagem temporária de grandes volumes. Variações amarela/branca, com/sem manchas. Breed com Brown Mushrooms.
2. **Blobfish** — `blobfish`. Oceano profundo, abaixo da superfície. Possui estado comprimido em profundidade e aparência depressurizada rosada quando trazido para pouca água; pode ser capturado em balde. Slimeball permite sobreviver temporariamente fora d'água. Alimenta a cadeia do **Fish Oil**, recurso associado a mobilidade/efeitos aquáticos.
3. **Blue Jay** — `blue_jay`. Florestas, taigas, groves/cherry e variantes. Ave que come itens de comida dropados. Ao receber sementes, canta e pode **revelar monstros próximos**, útil para localizar perigos/cavidades. Glow Berries criam vínculo temporário com o jogador; se um Raccoon também receber Glow Berries, Blue Jay e Raccoon formam uma dupla que luta junta, com o pássaro pousando no guaxinim fora do combate. Breed com Maggots/larvas.
4. **Catfish** — `catfish`. Rivers/Frozen Rivers/Swamps/Mangrove Swamps. Três tamanhos: small, medium e large. Pequenos/médios sugam itens; small armazena até 3 stacks e medium até 9 stacks. Large pode engolir um mob menor que 1 bloco. Ao sofrer dano ou encontrar Sea Pickle, regurgita o conteúdo. Sea Lantern atrai. Todos podem ser bucketados preservando conteúdo, permitindo transporte de itens ou de um mob. Drop de Raw Catfish.
5. **Cockroach** — `cockroach`. Cavernas/áreas escuras; evita luz forte. Come comida dropada, é presa de Cave Centipedes, pode sobreviver decapitada em estado crítico, dança perto de Jukebox e interage com **Maraca/Sombrero**. Breed com Sugar e pode gerar **Cockroach Ootheca**.
6. **Comb Jelly** — `comb_jelly`. Frozen/Deep Frozen Ocean à noite. Ctenóforo aquático de variações azul/verde/vermelho; bucketável. Dropa **Rainbow Jelly**, que aplica visual arco-íris a mobs/jogador e pode ser usada em **Rainbow Glass**, vidro que muda de cor e emite luz.
7. **Cosmic Cod** — `cosmic_cod`. The End, em cardumes. É hidrofóbico e sofre em água; cardumes podem teleportar juntos quando ameaçados. Bucketável. É alimento-chave para **Cosmaw** e também participa de transformações via Capsid.
8. **Crow** — `crow`. Plains/forests/taigas/meadow/grove/cherry. Come comida dropada e pode danificar/roubar culturas de farmland; Carved Pumpkin atua como espantalho em pequena área. É tameável e breedable; no ecossistema do mod funciona como ave utilitária e praga agrícola, não simples decoração.
9. **Devil's Hole Pupfish** — `devils_hole_pupfish`. Peixe extremamente raro, inspirado no animal real; o design do mod restringe sua ocorrência fortemente. Bucketável. Representa conteúdo de coleção/conservação e deve ser tratado como spawn raro ao validar worldgen.
10. **Endergrade** — `endergrade`. Ilhas externas do End. Criatura flutuante lenta; come Chorus Flowers, é presa de Enderiophages. Pode ser **selada/tornada montaria com Saddle** e controlada com **Chorus Fruit on a Stick**. Breed com Chorus Fruit.
11. **Flutter** — `flutter`. Lush Caves. Pequena criatura floral; pode ser domesticada alimentando diferentes tipos de flores. Tamed: follow/stay/wander e dispara **Pollen Balls** homing contra ameaças. Flower Pot funciona como proteção/equipamento; pode ser carregada. Heal com flores, breed com Bone Meal e pode estimular vegetação/azaleas. Dropa Spore Blossom.
12. **Fly** — `fly`. Overworld com céu exposto. Inseto de 2 HP; interage com undead, é presa de spiders/frogs/potoos e se transforma em **Crimson Mosquito** ao ir para o Nether. Pode gerar **Maggots**, recurso recorrente de breeding/alimentação de outros mobs.
13. **Flying Fish** — `flying_fish`. Oceans/Lukewarm Ocean. Salta para fora d'água e plana por curtas distâncias; bucketável. Drop/item Flying Fish participa de **Flying Fish Boots**, que melhoram saltos saindo da água e combinam bem com Elytra.
14. **Gazelle** — `gazelle`. Savanna/Savanna Plateau/Windswept Savanna. Animal de rebanho que foge coletivamente; dropa Mutton e **Gazelle Horn**. Breed com Acacia Blossom/Wheat. O horn pode elevar Speed II a Speed III.
15. **Hummingbird** — `hummingbird`. Jungles e biomas florais. Três variações; poliniza flores/culturas e pode ser ancorado por **Hummingbird Feeder** abastecido com água+açúcar, aumentando a utilidade agrícola. Breed com flores. Sem drop de combate importante.
16. **Jerboa** — `jerboa`. Desert à noite; dorme durante o dia. Pequeno e caçado por Rattlesnakes, foxes/cats. Pede sementes e recompensa o jogador com **Fleet-Footed**, efeito de velocidade quando corre/pula. Breed com Maggots, Crimson Mosquito Larvae ou Leafcutter Ant Pupae.
17. **Laviathan** — `laviathan`. Lava Sea do Nether. Grande criatura passiva, nada em lava e se move lentamente em terra. Lura **Crimson Mosquitoes** com fumaça e os captura. Exposição adequada à água pode solidificá-la em variante obsidiana, que também consegue nadar em água. Tameável e breedable; Crimson Mosquito Larvae participam do manejo, Magma Cream cura. Usa **Straddlite Saddle/Tack** para transporte sobre lava.
18. **Maned Wolf** — `maned_wolf`. Savanna e variantes. Ao receber **Apple**, produz stench por alguns segundos: atrai mobs próximos que não sejam monstros e acelera temporariamente crescimento de Gongylidia em Leafcutter Ant Chambers. É importante para a cadeia Leafcutter Ant. Breedable. Em 2.1.10 houve correção específica para freeze ao alimentar maçã quando era a última do stack.
19. **Mimic Octopus** — `mimic_octopus`. Warm Ocean. Camufla-se copiando a cor do bloco e regenera durante camuflagem; imita Creeper, Guardian e Pufferfish para afugentar predadores. Tame com vários Lobster Tails quando não camuflado; tamed pode follow/stay/wander e ser bucketado. Slimeball estende sobrevivência terrestre. Com 5–8 Mimicream, aprende ataques das formas imitadas: pequena explosão, laser de Guardian e espinhos venenosos. Breed com Tropical Fish.
20. **Mudskipper** — `mudskipper`. Mangrove Swamp. Semi-aquático; dois indivíduos fazem disputa ritual de dominância. Bucketável. Tame com Lobster Tails; tamed usa **Mud Ball** contra ameaças, causando dano/slowness, e pode strafe em combate. Breed com larvas ou Lobster Tails.
21. **Mungus** — `mungus`. Mushroom Fields. Cresce mushrooms no corpo, dispara feixe em mushrooms para gerar/spread de cogumelos e solta **Mungal Spores** em vida. Com cinco mushrooms nas costas, morte pode causar conversão ambiental/biome dependendo do mushroom, portanto é um mob com efeito de mundo e não apenas fauna. Também participa da transformação Crimson Mosquito → **Warped Mosco** quando coberto com Warped Fungus.
22. **Potoo** — `potoo`. Dark Forest. Ave quase imóvel de dia, pousa em logs/leaves; à noite caça Flies. Pupilas reagem ao nível de luz. Pode ser manejado pelo **Falconry Glove** e breed com Maggots.
23. **Rain Frog** — `rain_frog`. Desert durante chuva/tempestade. Costuma ficar enterrado na areia e emerge com chuva, dano ou escavação por shovel. Dança perto de Jukebox; dança prolongada pode **iniciar chuva**, tornando-o uma criatura de interação climática. Breedable.
24. **Roadrunner** — `roadrunner`. Desert/Badlands. Caça Rattlesnakes; dropa **Roadrunner Feather**, usada em Roadrunner Boots que aumentam mobilidade em areia. Breed com Maggots. Name-tag “Meep/Meep Meep” ativa easter egg de velocidade/som.
25. **Seagull** — `seagull`. Beach/Stony Shore. Voa, come comida dropada e **rouba comida do hotbar** do jogador; após roubo há cooldown antes de repetir. Breedable. Deve ser considerado em sistemas que assumem segurança de alimentos no hotbar.
26. **Seal** — `seal`. Beaches/Stony Shores/cold/frozen oceans. Semi-aquático, forma pods, bask/sleep e foge coletivamente. É presa de Orcas/Polar Bears. Se alimentado com peixe enquanto basking, pode mergulhar e retornar com itens do seabed, incluindo junk ou recursos raros como Shark Teeth/Scutes/Nautilus Shells. Breed com Lobster Tails.
27. **Shoebill** — `shoebill`. Swamp/Mangrove Swamp. Tímido, pesca usando loot de fishing; consome peixe e descarta bycatch, permitindo automação de pesca. Crocodile Eggs podem melhorar lure e Terrapin Eggs podem melhorar luck. Também preda filhotes de algumas tartarugas/crocodilianos. Não é breedable.
28. **Spectre** — `spectre`. Ilhas externas do End. Criatura espectral passiva, resistente a dano comum; **Soul Heart** a atrai. O jogador pode se prender com lead para ser carregado entre ilhas do End e soltar crouch/right-click. Atua como transporte aéreo orgânico.
29. **Stradpole** — `stradpole`. Nether/lava e também água; pequena criatura associada ao Straddler. Pode ser lançada como projétil por Straddlers e também manipulada/capturada. Alimentação com Crimson Mosquito Larvae pode transformá-la em Straddler.
30. **Sugar Glider** — `sugar_glider`. Birch/Old Growth Birch Forest. Escala paredes e plana entre árvores. Tame com Sweet Berries; follow/stay/wander. Pode ser colocado na cabeça do dono e concede **slow falling**. Tamed forageia leaves, obtendo junk ou ocasionalmente recursos como arrows/Hair of Bear/Moose Antlers. Breed com Honeycomb.
31. **Terrapin** — `terrapin`. Rivers. Tartaruga semi-aquática bucketável com seis tipos de shell e seis de skin, gerando centenas de combinações/mutações. Jogador pode pisar nela para recolher ao casco e pisar novamente para **lançá-la como shell**, causando dano em entidades. Breed com Seagrass e põe Terrapin Eggs.
32. **Toucan** — `toucan`. Jungle/Bamboo/Sparse Jungle. Cinco variantes. Ao receber Apple/Banana, pode **plantar saplings** correspondentes; Golden Apple cria Golden Toucan mais eficiente, Enchanted Golden Apple cria variante que planta sem exigir alimentação constante. Breedable.
33. **Triops** — `triops`. Água em Desert/Badlands e variantes. Bucketável e breedable. A presença de Triops **intimida Crimson Mosquitoes**, fazendo-os tremer, desistir de atacar e se afastar; também possui ciclo com Triops Eggs.

### 3.2 Neutros — 26
1. **Anteater** — `anteater`. Jungle/Bamboo/Sparse Jungle. Ataca Leafcutter Ants com língua e invade Anthills, cavando/extraindo recursos. Filhotes podem montar nas costas de adultos. Defende-se com garras e breedable. Forma contraponto ecológico direto ao sistema de colônias de Leafcutter Ant.
2. **Bald Eagle** — `bald_eagle`. Biomas elevados/montanhosos. Predador aéreo que caça, mergulha sobre presas e pode ser domesticado com Fish Oil. **Falconry Glove** permite carregar/lançar a ave contra alvos e recuperar; **Falconry Hood** complementa falcoaria. É uma ferramenta de exploração/combate, não apenas pet.
3. **Bison** — `bison`. Plains/Snowy Plains/Meadow e afins. Grande herbívoro de rebanho com charge. Pode ser sheared para **Bison Fur**, que volta a crescer; fur produz blocos/carpet e equipamento para neve/powder snow. Limpa snow layers em certas interações e breed com Wheat. 2.1.10 corrigiu pathfinding/agro que fazia o bison ficar bravo sem perseguir.
4. **Bunfungus** — `bunfungus`. Mushroom Fields ou transformação de Rabbit com Mungal Spores. Gigante fúngico, muito resistente; descansa de dia e combate monstros à noite com saltos/área. Carrot pode pacificar/fortalecer temporariamente. Representa defesa ambiental ligada ao ecossistema mushroom.
5. **Cachalot Whale** — `cachalot_whale`. Ocean/deep ocean adequados; pods. Usa **echolocation** para caçar Squid/Glow Squid/Mimic Octopus/Giant Squid; luta com Giant Squid, quebra obstáculos frágeis/boats e dorme verticalmente. Há variante albina rara. Pode encalhar em circunstâncias específicas; resgate é uma interação própria. Recursos como tooth/ambergris entram em equipamentos/utilidades.
6. **Caiman** — `caiman`. Mangrove Swamp. Semi-aquático; morde quando provocado e faz comportamento de breach/vibração na superfície. Tame ocorre por **imprinting**: ficar próximo de Caiman Eggs quando eclodem faz o filhote vincular-se ao indivíduo mais próximo. Tamed possui follow/stay/wander e pode ser criado.
7. **Capuchin Monkey** — `capuchin_monkey`. Jungle e variantes. Vive em tropas, arremessa cobblestone. Tame com Banana; follow/stay/wander e pode ficar no ombro. Do ombro, auxilia em combate; **Ancient Dart** melhora o ataque ranged/piercing. Heal com Eggs, breed com Maggots. Bananas também podem gerar Banana Peel/Sopa De Macaco na cadeia de itens.
8. **Cosmaw** — `cosmaw`. End Highlands/Midlands/Barrens; raro, voa e não pousa. Tame com Cosmic Cod; tamed não é pet de DPS: seu papel principal é **resgatar o dono que cai no void**, pegando-o e levando-o de volta a terra. Heal com Chorus Fruit; breed com Cosmic Cod.
9. **Elephant** — `elephant`. Savannas. Rebanhos com leader mais forte; ataques de gore/trunk toss/stomp e charge do líder. Reage a bees, come foliage/hay, filhotes órfãos podem acompanhar adultos. Tame com Acacia Blossoms; líderes precisam ser domesticados como calf. Rideable; chest/carpet adicionam armazenamento/visual. Leader tamed pode executar charge enquanto montado. 2.1.10 corrigiu pathfinding/agro.
10. **Emu** — `emu`. Fauna terrestre de regiões secas/quentes; neutral, breedable, produz **Emu Eggs/Emu Feather**. Integra cadeia de comida/equipamento como Outback Leggings. Deve ser validado no runtime para spawn weights e ciclo exato de ovos na build Continued.
11. **Frilled Shark** — `frilled_shark`. Ocean, abaixo de y≈25; quase todos os tipos oceânicos. Morde e aplica **Exsanguination**. Usa dentes refletivos para atrair Squid/Glow Squid e pode soltar **Serrated Shark Teeth** durante caça; bucketável. Superfície deixa o animal mais pálido/lento. Teeth alimentam o **Shield of the Deep**.
12. **Gelada Monkey** — `gelada_monkey`. Meadow. Grupos de quatro com leader; grupo inteiro defende membro atacado. Leaders fazem desafios ritualizados e disputas não necessariamente letais. Forageia grass e possui comportamento social próprio; breedable.
13. **Giant Squid** — `giant_squid`. Deep ocean abaixo de y≈25 e alguns oceans; também pode surgir quando Squid vanilla é atingido por lightning. Neutral, usa tinta e é presa/rival de Cachalot Whale. Pode perder **Lost Tentacle** em luta com whale; Lost Tentacle alimenta **Grappling Squok**. Possui estado depressurizado ao subir e variante azul rara/condicional.
14. **Gorilla** — `gorilla`. Jungles. Grupos liderados por Silverback; babies montam adultos, forrageiam folhas. Tame com Bananas; tamed follow/stay/wander, defendem o owner; breed com Bananas. 2.1.10 corrigiu pathfinding/agro de gorillas.
15. **Kangaroo** — `kangaroo`. Badlands e Savannas. Locomoção por saltos; luta com punch/kick. Tame com cerca de 10–15 Carrots. Tamed tem **pouch inventory**: pode usar melee weapon armazenada, equipar helmet/chestplate e consumir comida não-carne para se curar. Breed com Dead Bush/Grass. Dropa meat e hide.
16. **Leafcutter Ant** — `leafcutter_ant`. Colônias em Jungles, via **Leafcutter Anthill**. Workers coletam leaves e retornam; Queen permanece na colônia. Há limite de população e crescimento condicionado a folhas/queen. Leaf harvest pode expandir **Leafcutter Ant Chambers** e produzir **Gongylidia**. Shroomlight permite colher Gongylidia sem provocar workers; Maned Wolf stench acelera crescimento. Gongylidia cura/pacifica e alimenta queen; **Leafcutter Ant Pupa** inicia nova colônia. Anteaters predam ants. 2.1.10 corrigiu pathfinding/agro de workers.
17. **Lobster** — `lobster`. Underwater Beach. Seis variantes de cor com raridades muito diferentes; reage com pinça ao ataque. Bucketável. Lobster Tail é alimento/recurso importante para breeding/taming de vários mobs aquáticos.
18. **Mantis Shrimp** — `mantis_shrimp`. Warm Ocean/Mangrove Swamp. Socos extremamente fortes, knockback, fogo e **quebra de blocos**. Caça fish/squid/guardians/shulkers e Mimic Octopus. Tame com 10–30 Tropical Fish. Tamed: follow/stay/wander/**breaking blocks**; sneak-use entrega um block como alvo e o shrimp procura/quebra blocos iguais. Water Bucket permite sobrevivência terrestre permanente do tamed. Breed com Lobster Tails.
19. **Moose** — `moose`. Snowy/cold biomes. Grande herbívoro de rebanho; machos com antlers fazem disputas. Pode shed antlers naturalmente, que voltam a crescer; **Moose Antler** alimenta Antler Headdress. Recebe cobertura de neve em condições frias e interage com wolves. Breed com Dandelions.
20. **Orca** — `orca`. Cold/Deep Cold/Frozen Oceans. 60 HP na referência secundária; caça em grupo. Preda Salmon, Seals, Turtles, Drowned, Guardians, baby Cachalot Whales, Polar Bears e até Moose em situações aquáticas. Ataques incluem bite/tail slam. Não tame/breed. Integra cadeia ecológica marinha de alto nível.
21. **Raccoon** — `raccoon`. Plains/forests/taigas/meadows/cherry. Procura comida, lava alimento em água, pode roubar containers/villagers. Tame deixando Egg para ele lavar na água; tamed follow/stay/wander e usa carpet como bandana. Breed com Bread. Glow Berries ativam dupla de combate com Blue Jay. **Raccoon Tail** pode virar Frontiersman's Cap.
22. **Snow Leopard** — `snow_leopard`. Snowy Plains/Taiga/Frozen River/Ocean/slopes/peaks/grove. Predador que stalk e pounce, caça Sheep/livestock e reage ao ataque. Não tame, mas breedable. Sem drop importante; sua função é ecológica/combat behavior.
23. **Tarantula Hawk** — `tarantula_hawk`. Desert. Vespa parasitoide. Ataca Spiders aplicando **Debilitating Sting**, paralisa, carrega e enterra em Sand. Tame com Spider Eyes; tamed follow/stay/wander e protege owner. Breed com Fermented Spider Eyes; reprodução usa Spider como hospedeiro: egg é implantado e grub emerge depois. Grub criado no Nether cresce em variante Nether. Tattered/Tarantula Hawk Wings entram em Elytra própria.
24. **Tasmanian Devil** — `tasmanian_devil`. Forest/Birch/Dark Forest. Caça Rabbit/Chicken e reage em grupo. Pode mastigar Bones e transformá-los em Bone Meal. Rotten Flesh desencadeia howl que afeta/afugenta monstros próximos, criando utilidade defensiva. Breedable.
25. **Underminer** — `underminer`. Abandoned Mineshaft, aparecendo depois de permanência prolongada. Fantasma incorpóreo, atravessa blocos e desaparece temporariamente quando jogador se aproxima. Neutral; minera visualmente blocos e pode indicar **ore escondido** atrás do alvo. Drop raro **Ghostly Pickaxe**. Config possui `underminerDisappearDistance`.
26. **Warped Toad** — `warped_toad`. Warped Forest. Vive em terra/água/lava e caça pequenos/médios insects, engolindo Fly e Crimson Mosquito. Tame/breed com Crimson Mosquito Larva; tamed follow/stay/wander e defende owner; heal com Maggots. Dropa Nether Wart/Shroomlight.

### 3.3 Defensivos — 8
1. **Alligator Snapping Turtle** — `alligator_snapping_turtle`. Swamp/Mangrove Swamp. Ataca quando chega muito perto; semi-aquática. Acumula moss depois de passar tempo em água; pode ser sheared para Seagrass e raramente **Spiked Scute**, recurso de equipamento. Breedable.
2. **Enderiophage** — `enderiophage`. End Midlands. Voa e caça Endergrades. Ataque pode aplicar **Ender Flu**, efeito que escala e pode culminar em surgimento de novos phages; Chorus Fruit/Milk são mecanismos de cura na referência. Ao infectar perde o olho e pode obtê-lo de Endermen. Dropa **Capsid**, bloco de processamento/armazenamento que participa de Enderiophage Rocket e conversões como Cosmic Cod/Mysterious Worm.
3. **Grizzly Bear** — `grizzly_bear`. Forests. Adultos protegem cubs, interagem com beehives/honey e Salmon. Honey altera comportamento e pode abrir janela segura de aproximação; alimentação apropriada constrói confiança/tame na mecânica do mod. **Hair of Bear** é recurso associado. Predador/forager de grande porte.
4. **Platypus** — `platypus`. River/Frozen River; raro. Semi-aquático, retaliando com Poison via esporões. Bucketável. Redstone “carrega” a capacidade sensorial e faz o animal procurar em Clay Blocks itens como Clay Balls/Maggots; Redstone Block aumenta a colheita. Breed com Lobster Tails. Fedora é easter egg/vestível associado.
5. **Rattlesnake** — `rattlesnake`. Desert/Badlands. Dá aviso sonoro antes de ataque e usa veneno; Roadrunner é predador. **Rattlesnake Rattle** participa de Poisonous Essence/potion chain. Placação/evitação depende de respeitar distância; breedable.
6. **Rhinoceros** — `rhinoceros`. Savannas. Spawna em grupos, fica agressivo se aproximado/atacado e usa **charge/impale**. Wheat pacifica. Ataca Illagers e Ravagers, inserindo-se ativamente em raids/combate ambiental. Breedable. 2.1.10 corrigiu pathfinding/agro de rhinos.
7. **Skunk** — `skunk`. Forests. Defesa principal é spray que causa efeitos de náusea/stink e afugenta criaturas. **Stink in a Bottle** e **Stink Ray** transformam a mecânica em ferramenta: mobs hostis/neutros podem redirecionar agressão ao alvo pulverizado e passivos fogem; pode gerar lingering cloud se o alvo estiver sob potion effect. Breed com Sweet Berries.
8. **Sunbird** — `sunbird`. Peaks/mountains e Cherry Grove na linha atual. Criatura voadora fire/lava immune. Concede **Sunbird's Blessing** ao jogador não agressor, melhorando mobilidade aérea/Elytra; agressor recebe **Sunbird's Curse**, que favorece queda. Queima undead e orbita Beacons ativos. Não precisa ser morto para sua principal utilidade.

### 3.4 Placáveis/pacificáveis — 12
1. **Anaconda** — `anaconda` + `anaconda_part`. Swamp/Mangrove Swamp. Multipart; embosca, enrola/constringe e engole presa. Presa engolida não gera drops normais. Depois de três grandes refeições pode shed **Snake Skin**, usada em banner/Vine Lasso. Raw Chicken pacifica e também participa do breeding.
2. **Bone Serpent** — `bone_serpent` + partes. Lava Sea do Nether. Serpente segmentada que salta da lava e ataca; inimiga de Soul Vultures/Wither Skeletons. **Unsettling Kimono** ou condições de Straddleboard podem evitar agressão. Drop de Bone Serpent Tooth; tooth + Lava Bottle entra em **Lava Vision**.
3. **Cave Centipede** — `centipede_head/body/tail`. Cavernas profundas, multipart; sobe paredes, caça Cockroaches e aplica Poison. **Bug Pheromones** podem pacificar. Dropa Cave Centipede Legs, que entram em **Cave Centipede Leggings** para wall climbing e em potion chain de Poison Resistance.
4. **Crimson Mosquito** — `crimson_mosquito`. Crimson Forest ou transformação de Fly no Nether. Voa, prende-se ao alvo e suga sangue. Depois de se alimentar pode gerar Blood Sac/Proboscis e alimentar armas de sangue. **Bug Pheromones/Mosquito Repellent** reduzem agressão. Se sugar um Mungus adequadamente convertido com Warped Fungus, pode virar **Warped Mosco**.
5. **Crocodile** — `crocodile`. Rivers/Swamps/Mangrove. Semi-aquático; morde, agarra, arrasta para água e usa death roll. Shield pode interromper/stun em condição de investida; sneak é importante para escapar de grapple. Tame não é por feed direto: **Crocodile Egg** hatch/imprint produz filhote vinculado. Adultos produzem/dropam **Crocodile Scute**, usada na Crocodile Chestplate de mobilidade aquática.
6. **Froststalker** — `froststalker`. Ice Spikes/Frozen Peaks. Caçador de pack, cerca alvo e salta; prefere Tusklins. **Froststalker Helmet** pacifica. Tem formas spiked/spikeless; dropa Froststalker Horn e gelo quando spiked. Breedable.
7. **Hammerhead Shark** — `hammerhead_shark`. Warm Ocean. Não caça jogador saudável automaticamente da mesma forma que um hostil; prioriza entidades feridas e fauna aquática. Circula e faz dash. Durante ataques pode soltar **Shark Teeth** em vez de exigir kill, coerente com filosofia de recursos renováveis; teeth fazem Shark Tooth Arrows, eficazes underwater.
8. **Komodo Dragon** — `komodo_dragon`. Sparse Jungle. Predador com bite venenoso, caça fauna e pode canibalizar Komodos enfraquecidos; indivíduos fazem wrestling. Pode ser tame com grande quantidade de Rotten Flesh e depois usar follow/stay/wander/saddle; breed com Rotten Flesh. Produz **Komodo Dragon Spit**, recurso de potion/crafting. Qualquer divergência de tag/tame entre original e Continued deve ser validada no runtime 2.1.10.
9. **Skelewag** — `skelewag`. Próximo a Shipwrecks submersos. Peixe-esqueleto agressivo, mas **Unsettling Kimono** o pacifica. Faz charges/slashes; pode ter Drowned jockey e banner. Dropa Bones/Fish Bones e raramente **Skelewag Skull**, que funciona como arma rápida e também block/parry.
10. **Soul Vulture** — `soul_vulture`. Bone Fossils em Soul Sand Valley. Só pousa em Bone Blocks. Ataca jogador sem **Unsettling Kimono**; circula e mergulha, podendo roubar vida/heal via soulsteal. Depois de acumular soulsteal suficiente pode fornecer **Soul Heart**, importante para Soulsteal/Spectre.
11. **Tiger** — `tiger`. Bamboo Jungle/Cherry Grove; grupos 1–3. Antes de atacar fica quase invisível, deixando olhos visíveis; pounce aplica **Scared Still**. Alimentar Chicken/Porkchop/fresh meat concede **Tiger's Blessing**, tornando Tigers aliados temporários e fazendo-os defender o jogador; atacar um Tiger remove o benefício. Breed com Acacia Blossoms e pode gerar White Tiger raro.
12. **Tusklin** — `tusklin`. Snowy Plains/Ice Spikes. Grande ancestral do Hoglin; gore/throw e perseguição curta. Brown Mushroom pacifica; Red Mushroom é usada para breeding. Pode receber Saddle, mas tenta buck off; **Ancient Hogshoes** melhoram a montaria e aceitam encantamentos similares a boots. Froststalkers o caçam.

### 3.5 Hostis — 8
1. **Dropbear** — `dropbear`. Nether Wastes. Marsupial demoníaco que escala teto, espera prey abaixo e cai para causar dano antes de usar bite/claws. Dropa **Dropbear Claw**, usada em Flint and Steel alternativo, Tendon Whip e Potion of Clinging, que permite andar no teto.
2. **Farseer** — `farseer`. Próximo/fora da **World Border**, em qualquer dimensão. Hostil endgame raro; usa múltiplos braços, beam ocular, projéteis estáticos e portais próprios para reposicionamento. Referência secundária reporta ataques que ignoram armor/enchantments e beam extremamente letal. Dropa **Farseer Arms**; acesso normal costuma envolver Dimensional Carver/Shattered Dimensional Carver. Precisa de smoke test antes de qualquer balanceamento custom.
3. **Guster** — `guster`. Desert durante rain/thunder; variantes standard/red/soul conforme sedimento/bioma. Atira sand, puxa mobs/items para um tornado e os lança. Vulnerável a água e mais resistente a projéteis. Dropa **Guster Eye**: faz **Gustmaker** redstone que empurra entidades/itens e **Pocket of Sand** como arma.
4. **Mimicube** — `mimicube`. End Cities. Slime hostil que **copia equipamento e ações do alvo**: weapon, helmet, shield, bow/crossbow/trident e comida. Dropa **Mimicream**; combinar Mimicream com item damageable cria cópia de durabilidade zerada que preserva propriedades/enchantments e deve ser reparada para uso.
5. **Murmur** — `murmur` + `murmur_head` + tendon segments. Cavernas profundas e condições específicas de Cherry Grove; multipart, com pescoço/tendon extensível que alcança grandes distâncias e cabeça que morde. Dropa **Elastic Tendon** e pode dropar Unsettling Kimono. Danificar cabeça/pescoço tem regras próprias; kimono não torna o próprio Murmur passivo.
6. **Rocky Roller** — `rocky_roller`. Cavernas. Monstro rochoso que enrola o corpo e acelera em carga; impacto/landing pode aplicar **Earthquake** visual. Dropa **Rocky Shell**, usada em Rock Shell Chestplate: permite ao jogador rolar em sprint e colidir com entidades.
7. **Skreecher** — `skreecher`. Deep Dark coberto, não spawnando com céu aberto. Possui apenas 2 HP na referência, mas sua ameaça é indireta: ao detectar jogador faz ruído e **pode invocar Warden** depois de alguns segundos. Projectile pode derrubá-lo/interromper temporariamente. Dropa **Skreecher Soul**, usada em **Sculk Boomer**, trap ativada por vibração/Sculk Sensor.
8. **Straddler** — `straddler`. Basalt Delta. Gigante hostil capaz de caminhar em lava, água e terra. Ataque ranged **lança Stradpoles** no jogador; os temporários desaparecem depois. Dropa Basalt e raramente **Straddlite**, material de Straddleboard/saddlery.

### 3.6 Chief e boss
1. **Warped Mosco** — `warped_mosco`. Chief mob; normalmente nasce da transformação de Crimson Mosquito depois de alimentar-se de Mungus com Warped Fungi quando a config permite. Cerca de 100 HP na referência secundária. Usa body slam, punches/smashes, voo e blood-sucking para curar. Dropa **Warped Muscle/Hemolymph Sacs**, usados no **Hemolymph Blaster**.
2. **Void Worm** — `void_worm` + múltiplas partes/portal/projéteis. Boss do End, 160 HP default na config de referência. Invocado lançando **Mysterious Worm** no void do End. Multipart, atravessa/abre portais, dispara projectiles/crystals e quebra Chorus Plant/Flower. Dropa **Void Worm Mandible/Eye** e gera loot protegido por Ender Residue. Mysterious Worm está ligado à Capsid/Crimson Mosquito Larva. Config controla health modifier, dimensões e summonability.

### 3.7 Secreto/joke — não contar como mob normal do roster
1. **Sea Bear** — `sea_bear`. Só deve aparecer como easter egg com **Super Secret Settings**. Com a opção ativa, usar Sombrero em Ocean pode invocá-lo; possui 200 HP na referência e é agressivo. **Anti-Sea Bear Circle** feito em Sand com Stick impede ataque dentro do círculo. Se spawnado por comando sem secret setting, pode ser passivo. Não possui Animal Dictionary entry. Serve para explicar por que uma contagem estrita do conteúdo pode chegar a 90 nomes mesmo quando a lista oficial padrão é 89.

## 4. EntityTypes técnicos: por que “~116” não equivale a 116 mobs normais
O source de 1.21 registra, além dos mobs acima, entidades auxiliares. Exemplos auditados:
- `bone_serpent_part`, `centipede_body`, `centipede_tail`, `anaconda_part`, `void_worm_part`, `murmur_head`, `tendon_segment`: partes de entidades multipartes.
- `mosquito_spit`, `shark_tooth_arrow`, `tossed_item`, `sand_shot`, `gust`, `hemolymph`, `ice_shard`, `pollen_ball`, `mud_ball`, `void_worm_shot`: projéteis/efeitos de combate.
- `cockroach_egg`, `emu_egg`, `enderiophage_rocket`: entidades de item/projétil/spawn.
- `straddleboard`: veículo.
- `cachalot_echo`: representação da echolocation.
- `void_portal`: portal do Void Worm.
- `vine_lasso`, `squid_grapple`: entidades de utilitários de captura/grapple.

Isso demonstra tecnicamente por que **contagem de EntityTypes não deve ser usada como contagem de criaturas de gameplay**.

## 5. Redes ecológicas e sistemas emergentes
### 5.1 Predador ↔ presa
Há relações explícitas entre criaturas, por exemplo: Roadrunner ↔ Rattlesnake; Cave Centipede ↔ Cockroach; Anteater ↔ Leafcutter Ant; Froststalker ↔ Tusklin; Tarantula Hawk ↔ Spider; Mantis Shrimp ↔ aquatic prey/Shulker; Hammerhead/Frilled Shark ↔ squid/fish; Orca ↔ seals/fish/guardians/baby whales; Cachalot Whale ↔ Giant Squid; Potoo ↔ Fly; Warped Toad/Laviathan ↔ Crimson Mosquito; Snow Leopard ↔ livestock. Isso cria dinâmica real de população/combate e pode impactar farms e densidade de entidades.

### 5.2 Rebanho, tropa e liderança
Elephants, Gorillas, Geladas, Moose, Orcas, Gazelles e outros trabalham em grupos com fuga/defesa/leader logic. Isso importa para performance, agro e `spawnGroupSizes`. Não assumir IA independente por indivíduo.

### 5.3 Domesticação, confiança e imprinting
O mod usa modelos diferentes: feed repetido (Gorilla, Kangaroo, Mantis Shrimp, Mudskipper, Mimic Octopus, Sugar Glider, Tarantula Hawk, Warped Toad, Cosmaw), confiança contextual (Grizzly), hatch/imprinting (Crocodile/Caiman), mounts condicionais (Endergrade, Tusklin, Laviathan) e buffs temporários em vez de tame (Tiger's Blessing). Um sistema externo de “pet” não pode tratar tudo como `TamableAnimal` equivalente sem auditar a classe real.

### 5.4 Recursos sem kill
Vários materiais são obtidos por comportamento: Shark Teeth durante ataques, Moose Antler por shedding, Bison Fur por shearing/regrowth, Mungal Spores em vida, Komodo Spit, Alligator Snapping Turtle scute por shearing, Raccoon/Seal foraging, Platypus digging, Leafcutter Gongylidia, etc. Isso é relevante para quests e economia: **não projetar todas as receitas como loot de abate**.

### 5.5 Captura, ovos e transporte
Muitos aquáticos têm bucket variants: Blobfish, Comb Jelly, Cosmic Cod, Frilled Shark, Mimic Octopus, Mudskipper, Platypus, Stradpole, Terrapin, Triops e Catfish por tamanho, além de Lobster e outros. Há Crocodile Egg, Caiman Egg, Terrapin Egg, Platypus Egg, Triops Eggs, Emu Egg, Cockroach Ootheca e Leafcutter Pupa. Essa variedade afeta inventário, despawn, reprodução e automação.

## 6. Itens e recursos
### 6.1 Materiais principais documentados
Acacia Blossom; Ambergris; Banana Slug Slime; Bison Fur; Blood Sac; Bone Serpent Tooth; Cachalot Whale Tooth; Cave Centipede Leg; Cockroach Wing/Fragment; Crimson Mosquito Larva; Crimson Mosquito Proboscis; Crocodile Scute; Dropbear Claw; Elastic Tendon; Emu Feather; Farseer Arm; Fish Bones; Froststalker Horn; Gazelle Horn; Guster Eye; Hair of Bear; Hemolymph Sac; Kangaroo Hide; Komodo Dragon Spit/Bottle; Lava Bottle; Lost Tentacle; Mimicream; Moose Antler; Mungal Spores; Poisonous Essence; Raccoon Tail; Rattlesnake Rattle; Roadrunner Feather; Rocky Shell; Shark Tooth/Serrated Shark Tooth; Shark Tooth Arrow; Shed Snake Skin; Skreecher Soul; Soul Heart; Spiked Scute; Straddlite; Tarantula Hawk Wing/Tattered Wing; Void Worm Eye; Void Worm Mandible; Warped Muscle.

### 6.2 Consumíveis/alimentos conhecidos
Banana; Blobfish item; Boiled Emu Egg; Raw/Cooked Catfish; Raw/Cooked Kangaroo Meat; Kangaroo Burger; Raw/Cooked Lobster Tail; Raw/Cooked Moose Ribs; Cosmic Cod item; Fish Oil; Flying Fish item; Gongylidia; Maggot; Mosquito Repellent Stew; Rainbow Jelly; Shrimp-Fried Rice; Sopa De Macaco e outros alimentos derivados.

### 6.3 Equipamentos, armas e ferramentas
**Armas (índice secundário):** Ancient Dart, Blood Sprayer, Hemolymph Blaster, Pocket of Sand, Shield of the Deep, Skelewag Skull, Stink Ray, Tendon Whip.

**Ferramentas/utilitários:** Dimensional Carver, Shattered Dimensional Carver, Echolocator, Endolocator, Falconry Glove, Ghostly Pickaxe, Grappling Squok, Strange Fish Finder, Vine Lasso.

**Armaduras/vestíveis:** Ancient Hogshoes, Antler Headdress, Cave Centipede Leggings, Crocodile Chestplate, Falconry Hood, Fedora, Flying Fish Boots, Frontiersman's Cap, Froststalker Helmet, Novelty Hat, Outback Leggings, Roadrunner Boots, Rock Shell Chestplate, Sombrero, Spiked Turtle Shell, Tarantula Hawk Elytra, Unsettling Kimono.

**Outros equipamentos/veículos:** Chorus Fruit on a Stick, Straddleboard, Straddlite Saddle, Straddlite Tack, Enderiophage Rocket.

### 6.4 Efeitos de alguns equipamentos que devem ser preservados em integrações
- **Cave Centipede Leggings:** wall climbing.
- **Rock Shell Chestplate:** sprint pode colocar jogador em estado de rolagem, danificando entidades; também muda hitbox/movimento e drena hunger.
- **Antler Headdress:** adiciona impacto/knockback a ataques.
- **Shield of the Deep:** shield funcional; retaliates com dano/Exsanguination. 2.1.10 corrige posição visual ao bloquear/offhand.
- **Flying Fish Boots:** mobilidade ao sair da água; sinergia com Elytra.
- **Roadrunner Boots:** mobilidade em areia.
- **Froststalker Helmet/Unsettling Kimono:** também funcionam como **gates de pacificação** de mobs específicos; não são só armor stats.
- **Falconry Glove/Hood:** sistema de falcoaria com Bald Eagle/Potoo.
- **Vine Lasso:** captura/transporte de mobs hostis sem equivaler exatamente a Lead/fence tie.
- **Grappling Squok:** grapple derivado de Lost Tentacle.
- **Dimensional Carver:** mobilidade dimensional/endgame; Shattered variant conecta-se à busca por Farseer/world border.

## 7. Blocos próprios — 25 indexados
1. Anti-Sea Bear Circle — Red Sand.
2. Anti-Sea Bear Circle — Sand.
3. Banana Peel — hazard/interação ligada a Banana/Capuchin.
4. Banana Slug Slime Block — absorção de água para Crystallized Mucus.
5. Bison Fur Carpet.
6. Block of Bison Fur.
7. Block of Straddlite.
8. Caiman Egg.
9. Capsid — armazenamento/processamento do ecossistema Enderiophage.
10. Crocodile Egg.
11. Crystallized Banana Slug Mucus.
12. Ender Residue — associado ao Void Worm/loot.
13. Gustmaker — redstone gust que empurra entidades/itens.
14. Hummingbird Feeder — água+açúcar, ancora hummingbirds/polinação.
15. Leafcutter Ant Chamber — fungus/Gongylidia.
16. Leafcutter Anthill — colônia/queen/workers.
17. Platypus Egg.
18. Rainbow Glass — mudança de cor/emissão luminosa.
19. Sculk Boomer — trap sonora/dano acionada por vibração/Sculk Sensor.
20. Stink in a Bottle — componente do sistema Skunk/Stink Ray.
21. Terrapin Egg.
22. Transmutation Table — sistema próprio de transmutação; config `limitTransmutingToLootTables` e pesos devem ser respeitados.
23. Triops Eggs.
24. Void Worm Beak.
25. Void Worm Effigy.

## 8. Efeitos de status e poções
### Positivos
- **Bug Pheromones** (`bug_pheromones`) — reduz/neutraliza agressão de insetos relevantes.
- **Clinging** (`clinging`) — permite aderência/caminhada em teto, inspirado no Dropbear.
- **Fleet-Footed** (`fleet_footed`) — mobilidade de corrida/salto, concedida via Jerboa.
- **Knockback Resistance** (`knockback_resistance`) — resistência a knockback.
- **Lava Vision** (`lava_vision`) — permite enxergar através de lava.
- **Mosquito Repellent** (`mosquito_repellent`) — afasta/neutraliza Crimson Mosquito.
- **Oiled** (`oiled`) — efeito ligado a Fish Oil/aquatic movement.
- **Orca's Might** (`orcas_might`) — buff ligado a interação com Orca.
- **Poison Resistance** (`poison_resistance`) — previne/remove Poison.
- **Soulsteal** (`soulsteal`) — roubo de vida ligado ao Soul Vulture/Soul Heart.
- **Sunbird's Blessing** (`sunbird_blessing`) — melhora mobilidade aérea/Elytra.
- **Tiger's Blessing** (`tigers_blessing`) — pacifica Tigers e transforma-os temporariamente em aliados.

### Neutro
- **Earthquake** (`earthquake`) — screen shake/impacto associado a Rocky Roller.

### Negativos
- **Debilitating Sting** (`debilitating_sting`) — paralisia/debilitação da Tarantula Hawk.
- **Ender Flu** (`ender_flu`) — infecção do Enderiophage com progressão própria.
- **Exsanguination** (`exsanguination`) — damage over time associado a Frilled Shark/Shield of the Deep.
- **Scared Still** (`fear`) — medo/imobilização ligado ao Tiger pounce.
- **Sunbird's Curse** (`sunbird_curse`) — contraponto à Blessing, favorecendo queda.

### Joke
- **Power Outage** (`power_down`) — efeito associado ao conteúdo secreto/easter egg.

### Famílias de poções documentadas
Bug Pheromones; Clinging; Knockback Resistance I/II; Lava Vision; Poison Resistance; Soulsteal I/II; Swiftness III. Receitas exatas devem ser consultadas/validada via JEI/runtime ao implementar quests, pois o pack pode alterar recipes via outros mods/datapacks.

## 9. Animal Dictionary e advancements
O **Animal Dictionary** é o manual in-game do mod. Possui entry por mob normal e pode ser usado diretamente em uma criatura para abrir sua entry. Também pode interagir com Lectern/Chiseled Bookshelf. O Sea Bear, por ser segredo, não possui entry. A linha 2.1.10 corrigiu iluminação/render dos modelos mostrados no Dictionary.

O mod possui advancements ligados a encontros e mecânicas específicas, por exemplo tame de Tarantula Hawk/Mantis Shrimp, transformar Stradpole, obter Rainbow Jelly, usar Underminer para revelar ore, etc. Para quests do modpack, **preferir usar esses gatilhos reais quando expostos** em vez de criar achievements genéricos que ignoram a mecânica própria.

## 10. Configuração e datapackability
A configuração é ampla e não se resume a enable/disable global. A referência documenta parâmetros como:
- `<mobName>SpawnWeight` e `<mobName>SpawnRolls` por criatura.
- `spawnGroupSizes` — novidade da 2.1.10; permite capar min/max por mob, ex.: `alexsmobs:orca|1|1` para spawn solo.
- `leafcutterAntFungusGrowChance` e `leafcutterAntRepopulateFeedings`.
- `limitGusterSpawnsToWeather`.
- `limitTransmutingToLootTables`, `transmutingWeightRemoveStep`.
- `mimicreamBlacklist`, `mimicreamRepair`.
- `mimicubeSpawnInEndCity`.
- `mungusBiomeTransformationType`.
- `neutralBoneSerpents`.
- `polarBearsAttackSeals`.
- `tusklinShoesBarteringChance`.
- `underminerDisappearDistance`.
- `voidWormDamageModifier`, `voidWormMaxHealth` (default de referência: 160), `voidWormSpawnDimensions`, `voidWormSummonable`.
- `wanderingTraderOffers`.
- `warpedMoscoMobTriggers`, `warpedMoscoTransformation`.
- `wolvesAttackMoose`.
- opções para spawn de Cachalot Whale encalhado e outros eventos raros.

A página oficial original também descreve configuração por biome registry/dictionary/tags/dimensions, criada justamente para coexistir com biome mods. Em um pack com worldgen extenso, **spawn precisa ser validado no mundo real do pack**, não apenas pelo biome vanilla listado na wiki.

## 11. Dependências e runtime do pack
### Obrigatória
- **CodxLib:** a release 2.1.11 exige **CodxLib 1.6.0 ou superior**. O pack instala `codxlib-1.6.0-neoforge+1.21.1.jar` / runtime 1.6.0, portanto satisfaz exatamente o novo piso.

### Citadel
Continued declara que **não precisa do Citadel externo**: as partes necessárias foram incorporadas. O pack possui `citadel-2.7.1-1.21.1.jar` porque outros mods dependem dele. Isso é coexistência, não dependência direta do Alex's Mobs Continued.

### Integrações locais confirmadas
- **Alex's Delight 1.6** (`alexsdelight-1.6.jar`): usa recursos/drops de Alex's Mobs dentro da culinária Farmer's Delight.
- **Enhanced AI 4.2.3.0** (`enhancedai-4.2.3.0.jar`): pode alterar goals/targeting/pathfinding e é a principal área de teste comportamental cruzado.
- **AI-Improvements 0.5.3:** atua mais em custo/infra de IA; mesmo assim, teste de performance deve cobrir densidade alta de entidades.
- **Citadel 2.7.1:** presente, mas não contar como requisito deste port.
- **Biome/worldgen stack do pack:** altera disponibilidade/distribuição dos ambientes usados por spawn; os próprios filtros/configs de Alex's Mobs precisam ser observados.
- **AeronauticsCompat 1.1.3:** como o pack usa sublevels/contraptions físicos, qualquer mob transportado para esses espaços deve ser smoke-tested para pathfinding, ownership e serialização; não presumir compatibilidade só pela presença do bridge.

## 12. Mudanças específicas da versão 2.1.10
O changelog upstream de 2.1.10 registra:
1. **Maned Wolf:** corrigido freeze/save+close ao alimentar Apple quando a maçã era a última do stack; a correção também cobre outros mobs que liam a mesma condição ao comer.
2. **Pathfinding/agro:** Bison, Rhinoceros, Elephant, Gorilla e Leafcutter Ants podiam ficar agressivos mas não perseguir; corrigido.
3. **Animal Dictionary:** iluminação dos modelos corrigida.
4. **Shield of the Deep:** posição visual ao bloquear corrigida, voltando ao comportamento esperado de 2.0.7.
5. **`spawnGroupSizes`:** nova opção de configuração para limitar tamanho de grupo por entidade.
6. **Dependência:** CodxLib >=1.4.0 no NeoForge/Forge.

## 13. Mudança específica da versão 2.1.11
A release **2.1.11**, agora instalada no pack, expõe a configuração de **Spawn Group Sizes** diretamente na interface `/amc menu` → Spawning. O menu grava a mesma opção `spawnGroupSizes` introduzida em 2.1.10, mantendo arquivo e GUI alinhados.

A release também passa a exigir **CodxLib 1.6.0+**. O changelog informa ainda correção no CodxLib para os botões `+`/`-` numéricos da `/amc menu`, que antes podiam somar o novo valor ao antigo e levar rapidamente a configuração ao máximo. Como o pack usa CodxLib 1.6.0, essa correção faz parte do baseline atual.

Os fixes de 2.1.10 para pathfinding/agro, Animal Dictionary, Shield of the Deep e `spawnGroupSizes` continuam herdados pela 2.1.11 e permanecem válidos como regressões a testar.

## 14. Riscos upstream a acompanhar — não são falhas locais confirmadas
Reports públicos recentes do repositório Continued incluem temas como:
- Leafcutter Ant não retornar ao nest.
- Tarantula Hawk Elytra com display incorreto em NeoForge 1.21.1.
- Terrapin com problema de movimento underwater/drowning.
- Animal Dictionary aparecendo mesmo quando desativado por server config.
- Gustmaker não funcionando em linha 26.2.
- Anaconda body caindo no chão em linha 26.2.
- worldgen error em snowy biome reportado para NeoForge 1.21.1.

Esses itens são **fila de validação**, não justificativa para afirmar que o pack sofre do bug sem reprodução.

## 15. Matriz de validação recomendada para esta instância
1. **Discovery/startup:** confirmar um único mod id `alexsmobs`, sem implementação legada duplicada, e CodxLib resolvido.
2. **Spawn Overworld:** amostrar forest, savanna, desert/badlands, swamp/mangrove, snowy/peaks, river/ocean, caves/lush/deep dark e mushroom fields.
3. **Spawn Nether:** Bone Serpent, Crimson Mosquito, Warped Toad, Warped Mosco transformation, Straddler/Stradpole, Dropbear, Laviathan, Soul Vulture.
4. **Spawn End:** Endergrade, Enderiophage, Spectre, Mimicube, Cosmaw/Cosmic Cod e Void Worm summon.
5. **Group AI regression herdada de 2.1.10:** provocar Bison, Rhino, Elephant, Gorilla e Leafcutter workers no runtime 2.1.11 e verificar pursuit/pathfinding real.
6. **Enhanced AI compatibility:** repetir hostis e grupos com Enhanced AI ativo; observar goals duplicados, freeze, impossible path, friendly-fire ou targeting anormal.
7. **Tame/breed:** um exemplo de cada modelo: Gorilla/Kangaroo; Mimic Octopus/Mantis Shrimp; Crocodile/Caiman imprint; Tiger Blessing; Endergrade/Tusklin/Laviathan mount.
8. **Bucket/persistence:** capturar/recolocar Catfish com conteúdo, Terrapin, Platypus, Mimic Octopus, Mudskipper e Frilled Shark; conferir NBT/persistence.
9. **Colony:** Leafcutter worker coleta leaf, retorna ao anthill, chamber cresce, Gongylidia aparece, queen repopula e pupa inicia nova colônia.
10. **Non-kill resources:** Moose antler shedding, Bison shearing/regrowth, Mungal Spores, Shark Teeth, Komodo Spit, Snapper scute.
11. **Special blocks:** Hummingbird Feeder, Gustmaker, Sculk Boomer, Capsid, Banana Slug Slime/Crystallized Mucus, Transmutation Table.
12. **Equipment:** Shield of the Deep main/offhand, Centipede Leggings wall climb, Rock Shell rolling, Falconry, Vine Lasso, Straddleboard/Saddle/Tack, Tarantula Hawk Elytra.
13. **Dictionary:** abrir menu, abrir entry por interação em mob, conferir model lighting e ausência de Sea Bear entry.
14. **Worldgen/biome compatibility:** verificar que mobs não ficaram impossíveis por alteração de biome tags/registries; conferir `spawnGroupSizes` e weights se densidade estiver alta.
15. **Boss/chief:** Warped Mosco transformation + loot; Void Worm summon, multipart hitboxes, portals/projectiles, boss death/loot.
16. **Dedicated server/sync:** tame ownership, riding, bucket NBT, multipart entities, projectiles e effects sem client-only assumptions.
17. **Performance:** observar entity count, pathfinding de herds/colonies e tick cost em áreas com grande fauna.

## 16. Regras para outros chats usarem esta ficha
- Não resumir Alex's Mobs Continued como “adiciona ~116 animais”. Isso perde a estrutura de gameplay e ainda mistura mobs com EntityTypes técnicos.
- Para **quests**, usar comportamento próprio: tame, imprinting, shearing, foraging, rescue, ecology, boss summon, colony, non-kill resource loops.
- Para **perks**, não assumir que todos os mobs compartilham base class, tame API, damage pattern ou spawn pipeline; multipartes e custom goals precisam de hooks específicos.
- Para **economia**, separar recurso renovável/non-kill de drop de morte.
- Para **worldgen**, usar tags/config real do runtime; bioma vanilla listado é referência, não garantia em um pack modded.
- Para **integração de IA**, fail-closed se Enhanced AI ou outro mod mudar comportamento sem hook comprovado.
- Para **atualização**, modlist física é authority de versão; upstream mais novo só vira realidade depois que o JAR do pack mudar.

## 17. Fontes e grau de confiança
**Authority física do pack:** `modlist.txt` física atual de 08/09/2026 — `alexsmobs-2.1.11-neoforge+1.21.1.jar`, CodxLib 1.6.0, Citadel 2.7.1, Alex's Delight 1.6 e Enhanced AI 4.2.3.0.

**Fonte primária do port:** [Alex's Mobs Continued — CurseForge](https://www.curseforge.com/minecraft/mc-mods/alexs-mobs-continued). Usada para propósito do port, loaders, dependências e changelog 2.1.10.

**Fonte primária do roster original:** [Alex's Mobs — CurseForge oficial](https://www.curseforge.com/minecraft/mc-mods/alexs-mobs). A página oficial enumera o roster publicado de 89 mobs e descreve a filosofia de comportamento/recursos/configuração.

**Fonte técnica:** source 1.21 do Alex's Mobs, especialmente `AMEntityRegistry.java`, usado para separar mobs de EntityTypes auxiliares e verificar spawn placement/registro técnico.

**Fonte secundária detalhada:** [Alex's Mobs Unofficial Wiki](https://alexs-mobs-unofficial.fandom.com/wiki/Alex%27s_Mobs_Unofficial_Wiki). Ela própria avisa que é editável pela comunidade e não totalmente confiável. Foi usada para mecânicas finas, habitats, tame/breed, items/blocks/effects e deve ser cruzada com runtime quando a informação virar contrato técnico.

**Regra de confiança:** presença/JAR/versão = modlist física; releases/dependências/changelog = publisher; roster = página oficial original + registro; mecânicas minuciosas = source/Animal Dictionary quando possível e wiki como referência secundária; bugs = somente “confirmados no pack” após reprodução local.
