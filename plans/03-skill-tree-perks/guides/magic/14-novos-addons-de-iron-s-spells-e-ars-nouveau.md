<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Novos addons de Iron's Spells e Ars Nouveau

## Apprentice's Codex — 0.9.7

`apprentice_codex-0.9.7+mc1.21.1.jar`
**Apprentice's Codex** é uma expansão de side-grades para Iron's Spells, com cerca de **60 spells** distribuídos entre combate, defesa/suporte, exploração, utilidade, coleta e crafting. Há summons de armas, rifles e shotguns mágicos, barreiras, guard stances, traps, healing/mana recovery, luz e visão remota, armazenamento, localização de estruturas e tesouros, além de spells para cortar árvores, modelar terreno, colher plantações, processar itens e coletar recursos.
O equipamento é igualmente central: **Spellcaster Guns**, Swingcast Staves, Offhand Spell Amplifiers e armas especializadas como Smashcast Scepter, Focus Staffbow, Multipurpose Staffrifle, Reflectcast Shield e Mana Force Blade usam casting como parte do próprio moveset. O mod também inclui spellbooks, curios, robes de estilo glass-cannon e gear de caster sem escola fixa.
Na infraestrutura existem Spellcaster Workbench, Atelier Station e **Spell Dispenser**. O Dispenser pode lançar scroll spells suportados por redstone, usa mana potions como combustível e possui suporte opcional a contraptions Create. Muitos comportamentos são configuráveis por datapacks. A build `0.9.7` instalada é beta NeoForge 1.21.1.

## Ars Morph — 2.0.0

`ars_morph-1.21.1-2.0.0.jar`
**Ars Morph** é uma camada de compatibilidade dedicada entre **Identity 2/Morph** e **Ars Nouveau**. Seu papel é fazer o estado de identidade/transformação do jogador continuar reconhecido dentro das mecânicas do Ars, evitando que morph e spellcraft funcionem como sistemas completamente desconectados.
Ele não fornece o sistema de transformação — isso continua pertencendo ao Identity 2 — nem substitui Ars Nouveau. O addon existe especificamente para adaptar a interação entre as duas APIs e permitir que o personagem transformado continue participando corretamente do ecossistema mágico Ars.

## Crystal Chronicles — 0.1.3-alpha

`crystal_chronicles-0.1.3-alpha.jar`
**Crystal Chronicles** adiciona uma camada de pós-jogo para Iron's Spells, com tiers superiores de **armaduras 3D, armas e staffs** ligados às escolas mágicas e um conjunto **Prismatic** que representa uma etapa avançada do equipamento. Na build atual esses itens ainda usam receitas placeholder em parte da progressão, porque o sistema definitivo permanece em desenvolvimento.
A exploração começa no End com o biome **Bismuth Formations**, que fornece materiais para montar um portal. O frame é construído com blocos de Bismuth, finalizado com o **Bismuth Portal Chisel** e ativado lançando o spell **Prismatic Portal**. O destino é uma grande dimensão cavernosa em alpha, dividida em biomas inspirados nas escolas do Iron's, incluindo áreas Flesh, Heavenly e Volcanic.
Cada biome também possui blocos de construção temáticos, variantes trabalhadas e alguns connected textures. Mobs, perigos ambientais e a progressão definitiva de cristais/equipamentos ainda são partes WIP. A versão instalada é `0.1.3-alpha` para NeoForge 1.21.1.

## Legendary Spellbooks — 0.3.2

`legendary_spellbooks-1.21.1+neo-0.3.2.jar`
**Legendary Spellbooks** converte habilidades usadas pelos inimigos de **Legendary Monsters** em spells propriamente integrados ao framework de Iron's Spells. Em vez de apenas adicionar loot inspirado nos bosses, o addon registra esses ataques nas escolas, atributos e regras de casting do Iron's.
O catálogo inclui uma linha **Annihilation**, que escala também com Ender e Fire, e spells distribuídos por escolas como Evocation, Blood, Lightning, Ice, Fire e Nature. Entre eles estão Annihilation Beam/Bomb/Shockwave, Summon Flameborn Knights, Cloud Rail, Energy Beam, Tornado, Glacier Eruption, Flame Eater e Ambush Thorns.
A progressão é ligada ao conteúdo do Legendary Monsters: vários spells entram nas loot tables das criaturas correspondentes e podem ser deliberadamente não craftáveis para preservar essa origem. A build `0.3.2` é a beta NeoForge 1.21.1 instalada.

## Somake Spells — 1.0.8

`somakespells-1.0.8-1.21.1-fix.jar`
**Somake Spells** é uma expansão grande de Iron's Spells inspirada inicialmente em T.O Magic 'n Extras, mas desenvolvida com catálogo próprio. A build atual possui **mais de 50 spells**, concentrados principalmente em **Lightning, Fire, Aqua e Symmetry**, com conteúdo adicional Blood e Ender. O addon introduz a própria **Aqua School**, com spell power/resistance e habilidades aquáticas dedicadas.
Há uma mecânica de **charges** elementais: além dos elementos internos, o mod reconhece escolas introduzidas por outros addons, incluindo Sound, Spirit e Geo. O catálogo de equipamento inclui armas como Glacium Greataxe, Core Splitter, Witherite Glaive, Ruined Blade, Clef Sword, Hallow Sword, Rock Sword e Boltcutter, além de conjuntos Dark Metal Battlemage, Ceranium, Aquamancer e Abyssium.
Os **Grimoires** funcionam como spellbooks evolutivos associados a elementos, inclusive escolas externas compatíveis. O addon possui integrações com Magic From The East e Cataclysm, suporte a Born in Chaos e conexões opcionais com outros addons do ecossistema Iron's. A instalação usa a build corrigida `1.0.8-1.21.1-fix`.

## ShadowsZ — 1.1.9

`shadowsz-1.1.9.jar`
**ShadowsZ** transforma Iron's Spells em base para uma progressão de **Shadow Monarch / necromante-summoner** inspirada em Solo Leveling. Depois de derrotar criaturas, o jogador pode revelar suas sombras e tentar realizar **Shadow Arising** para convertê-las em soldados permanentes. A chance leva em conta a vida máxima da criatura e a Mana disponível.
O exército possui gerenciamento próprio: cada shadow pode receber nome, comportamento e postura de combate; pode ser invocado individualmente ou organizado em grupos, e o jogador consegue ordenar ataques coletivos. Existe também **Position Swap**, que troca instantaneamente a posição do personagem com uma de suas sombras.
As shadows ganham XP e níveis próprios, com pontos investidos em **Health, Speed, Damage e Armor**. Sistemas opcionais permitem fundir shadows ou equipá-las com armas e armaduras reais. Há ainda um modo de progressão do próprio jogador, com títulos **Necromancer → Shadow Overlord → Shadow Monarch** e aumento gradual da capacidade do exército.
O mod também acrescenta a escola **Umbral** ao Iron's, com atributos de Umbral Spell Power/Resistance e spells como **Miasma, Umbral Bond e Aura of the Monarch**. Possui suporte dedicado para vários dos mobs do pack, incluindo Legendary Monsters, Mowzie's Mobs e L_Ender's Cataclysm.
