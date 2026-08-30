<!-- Snapshot canônico do Notion: GUIA COMPLETO — Mods de Magia | NeoForge 1.21.1
Fonte: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03
Parte 2/2. Continuação de part-01.md. -->

Ele não adiciona uma nova progressão de atributos ou um conjunto paralelo de armaduras: seu papel é ampliar a personalização visual de equipamento ligado a Malum. Requer Malum e Vanity: Core.
# 8. Encantamento, loot mágico e equipamentos
## Apothic Enchanting — 1.6.1
`ApothicEnchanting-1.21.1-1.6.1.jar`
**Apothic Enchanting** é um overhaul completo do sistema de encantamento. A mesa deixa de ser limitada ao modelo vanilla de nível 30 e passa a receber estatísticas fornecidas pelas estantes e blocos ao redor. **Eterna** representa a energia/nível disponível para encantamento; **Quanta** altera a variabilidade dos resultados; **Arcana** influencia a qualidade e o acesso a encantamentos mais raros; o sistema também trabalha com pistas adicionais, estabilidade e condições especiais para treasure enchantments.
Essas estatísticas fazem a montagem da área de enchanting virar uma progressão própria: diferentes estantes e configurações mudam concretamente a distribuição e a potência dos encantamentos disponíveis. O módulo também acrescenta encantamentos, tomes e utilidades relacionadas à extração/manipulação de enchantments e é parte requerida da instalação moderna do Apotheosis.
## Apotheosis — 8.7.0
`Apotheosis-1.21.1-8.7.0.jar`
**Apotheosis** concentra a camada de loot e progressão de equipamento do ecossistema Apothic. Armas, armaduras e outros itens compatíveis podem receber **affixes** com raridades e efeitos próprios; o sistema acrescenta **gems e sockets**, reforging, salvaging, gem cutting e uma **Augmenting Table** capaz de melhorar ou rerrolar affixes específicos.
A progressão também utiliza **World Tiers**. Os tiers são desbloqueados por critérios de avanço e, quando ativados, aumentam simultaneamente a força dos inimigos e a qualidade potencial do loot, chegando a raridades e affixes de endgame. Apothic Invaders/Elites e itens afixados fazem essa camada aparecer durante exploração e combate, não apenas em crafting.
A arquitetura é fortemente data-driven e aceita categorias de loot e atributos de outros mods. Por isso equipamentos mágicos de outros sistemas podem participar de affixes, gems e sockets quando uma integração os registra, como ocorre com o conteúdo de Iron's através de Iron's Apothic.
## Relics — 0.12.8
`relics-1.21.1-0.12.8.jar`
**Relics** adiciona dezenas de acessórios com mecânicas próprias, projetados como itens RPG que continuam evoluindo depois de serem encontrados. Cada relic combina habilidades específicas com um sistema de **leveling**, estatísticas aleatórias no estilo MMO e opções de melhoria/customização, de modo que duas cópias do mesmo tipo podem terminar com perfis diferentes.
Parte das relics possui **habilidades ativas progressivas**, enquanto outras atuam por passivos e gatilhos ligados ao combate, movimento ou outras ações. A versão 0.12.8 também registra experiência ganha pelas relics e adiciona o Shield of Retaliation. O mod é a base mecânica utilizada pelos addons Reliquified presentes no pack.
## Reliquary Reincarnations — runtime 2.0.80
`reliquary-1.21.1-2.0.80.1570.jar`
**Reliquary Reincarnations** é uma coleção de itens e blocos mágicos de obtenção relativamente trabalhosa que transformam drops e recursos em utilidades permanentes. O catálogo cobre múltiplos domínios — magia, armazenamento, alimentação e mobilidade — com artefatos e ferramentas que resolvem problemas específicos em vez de formar uma única árvore de spellcasting.
A progressão vem principalmente da aquisição dos materiais necessários para cada item e do uso de recursos consumíveis ou carregáveis associados a essas ferramentas. É um sistema independente de Relics e Artifacts. O JAR instalado/publicado é `2.0.80.1570`, enquanto o metadata runtime da instância declara `2.0.80`; as duas identificações permanecem preservadas.
## Reliquified L_Ender's Cataclysm — 0.1.1
`reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`
**Reliquified L_Ender's Cataclysm** integra o sistema **Relics** ao conteúdo de **L_Ender's Cataclysm**. O addon transforma conceitos e recompensas do Cataclysm em relics que seguem o modelo de progressão do Relics, com níveis, ranks, experiência, atributos, cooldowns e habilidades ativas/passivas em vez de serem apenas acessórios estáticos.
Entre as relics documentadas pela camada de compatibilidade atual estão **Void Cloak, Scouring Eye, Void Vortex in Bottle, Vacuum Glove e Void Bubble**. O projeto-base `0.1.1` é a release NeoForge 1.21.1 instalada e depende de Relics e L_Ender's Cataclysm.
Essa release foi criada contra a API antiga do Relics 0.10. Em 2026 surgiu um mod separado que adapta esses relics ao modelo `RelicTemplate` das versões Relics 0.12, restaurando Curios, atributos, ranks, cooldowns, XP e abilities; esse fix externo não está presente na modlist atual, portanto não é listado como parte deste addon.
## Reliquified Artifacts — 1.0.8
`reliquified_artifacts-1.21.1-1.0.8.jar`
**Reliquified Artifacts** redefine a integração entre **Artifacts** e **Relics** para que acessórios originados no Artifacts possam participar do modelo mais profundo de Relics. Em vez de funcionar apenas como compatibilidade de slots, a proposta é incorporar esses itens à lógica de progressão, estatísticas e mecânicas de relics.
O resultado é uma ponte entre dois catálogos de acessórios: Artifacts fornece identidades e efeitos de itens, enquanto Relics fornece a camada de evolução e customização usada pela integração. A build `1.0.8` instalada é a beta NeoForge 1.21.1 atual.
## Iron's Gems 'n Jewelry — runtime 1.21.1-2.0.2
`irons_jewelry-1.21.1-2.0.2.jar`
**Iron's Gems 'n Jewelry** adiciona um sistema de **jewelcrafting modular** baseado em padrões, materiais e gemas. As peças são fabricadas na Jewelcrafting Station: o padrão determina o tipo de joia e de benefício, os materiais escolhidos definem quais bônus serão aplicados e a qualidade desses materiais influencia a força final do item.
O mod possui mais de dez mil combinações modulares possíveis, sete gemas obtíveis e padrões que podem ser conhecidos desde o início ou desbloqueados por exploração e **Artisan Scrolls**. A profissão de villager **Jeweler** compra e vende materiais, peças prontas e padrões avançados. O sistema é data-driven e usa os acessórios como fontes persistentes de atributos e stats, não como uma lista adicional de spells.
## Create: Enchantment Industry — 2.5.3
`create-enchantment-industry-2.5.3.jar`
**Create: Enchantment Industry** transforma experiência e encantamentos em recursos processáveis pela infraestrutura Create. Sua proposta central é **automatic enchanting**: XP pode entrar em cadeias industriais e processos que normalmente dependem de interação manual passam a ser executáveis por máquinas, fluidos e sequências de processamento.
Com isso, armazenamento/manipulação de experiência, tratamento de itens encantados e operações relacionadas a enchantments podem ser incorporados a linhas de produção. A build `2.5.3` é a linha NeoForge 1.21.1 preparada para Create 6.0.10 e possui integrações condicionais com o ecossistema Apothic quando os respectivos módulos estão carregados.
## Create Enchantment Industry Plus — 1.1.1
`create_enchantment_industry_plus-1.1.1-1.21.1.jar`
**Create Enchantment Industry Plus** é uma extensão pequena e específica da cadeia de fluidos do Enchantment Industry. Ela adiciona **Empty Ink Sacs** obtidos por processamento de leather, permite encher esses recipientes com ink/black dye para produzir ink sacs e também drenar ink sacs novamente.
A integração com experiência acrescenta uma rota de spout para converter ink sacs em **glow ink sacs**, além de receitas adicionais de grinding/processamento. Portanto, o addon não amplia genericamente o sistema de enchanting: seu foco atual é industrializar o ciclo de ink e glow ink usando mecanismos do Create/Enchantment Industry.
## Create: Enchantable Machinery — 3.6.0
`createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar`
**Create: Enchantable Machinery** permite aplicar encantamentos vanilla a blocos e máquinas específicos do Create, criando variantes cujo comportamento muda conforme o enchantment aplicado. O efeito não é apenas visual: os encantamentos são interpretados pela lógica da máquina correspondente e influenciam seu processamento ou interação com o mundo.
O projeto inclui suporte a diversos componentes Create e continua tratando casos próprios de máquinas encantadas, como Spout, Mechanical Mixer, Fan, Plough e Roller. Assim, encantamento passa a ser uma propriedade funcional da infraestrutura mecânica, separada da automação de XP oferecida por Enchantment Industry.
## Apotheosis/Create Addon — 2.0.0
`apotheoticcreation-2.0.0.jar`
**Apotheotic Creation** é uma ponte técnica entre **Apotheosis/Apothic** e os sistemas de filtragem do Create. Ela fornece tags e interpretação para que **Attribute Filters**, Smart Observers, Brass Tunnels e outras rotas de triagem consigam reconhecer propriedades complexas de itens Apothic, como gear com affixes e gems.
Isso permite classificar e encaminhar automaticamente loot RPG com base em características que o Create puro não entende. O addon não cria um novo sistema de affixes nem de máquinas: ele torna metadados do Apotheosis legíveis para a automação logística do Create.
## Dis-Enchanting Table — 5.0.2
`disenchanting_table-merged-1.21.1-5.0.2.jar`
**Dis-Enchanting Table** adiciona uma bancada dedicada a recuperar encantamentos de itens e livros encantados, transferindo-os para livros em vez de perder o enchantment junto com o equipamento original. A mecânica permite, por exemplo, retirar encantamentos de uma ferramenta prestes a quebrar e reutilizá-los em outro fluxo de equipamento.
Na versão `5.0.2`, o inventário do bloco foi simplificado para trabalhar corretamente com **hoppers** e existe suporte a saída automática quando o modo de disenchanting automático está habilitado. Assim, a extração de encantamentos pode participar tanto de uso manual quanto de automação de inventário.
## Ozymandias Sundries — publicação 0.0.5 / runtime 0.0.1
`ozymandias_sundries-0.0.5.jar`
**Ozymandias Sundries** é uma expansão de conteúdo para **Iron's Spells 'n Spellbooks**. O addon adiciona armas mágicas e híbridas — como Sanctified Sword, Sacrificial Kris, Sculk Greatsword, Permafrost Axe, Cinderous Scimitar, Druidic Scythe, Ender Glaive, Levin Sword e Spectral Greatsword — além de equipamento adicional associado às escolas do sistema-base.
Também inclui spellbooks como **Fulminous Folio** e **Libram of Flesh** e spells próprios, entre eles **Levitate** e **Lightning Warp**. O projeto continua sendo conteúdo dependente de Iron's: mana, escolas, casting e infraestrutura de spellbook vêm do mod-base.
O JAR instalado é `0.0.5`, enquanto o metadata runtime local declara `0.0.1`; o guia mantém as duas identidades separadas.
# 9. Bibliotecas e infraestrutura mágica
## RunicLib — 5.0.7
`neoforge-runiclib-1.21.1-5.0.7.jar`
**RunicLib** é uma biblioteca multi-loader usada principalmente pelos projetos do ecossistema **Azurune**, mas exposta para outros desenvolvedores. Ela não adiciona uma progressão mágica autônoma; fornece infraestrutura comum para mods que precisam compartilhar código entre loaders e registrar sistemas reutilizáveis.
Entre as funções públicas estão um método comum de registry para projetos MultiLoader, utilidades como tratamento comum de flammability e **conditional recipes**, classes públicas compartilhadas e um conjunto de **effects/effect classes** pré-prontos. A biblioteca também concentra atributos e helpers consumidos por mods dependentes.
A instalação atual é `5.0.7` para NeoForge 1.21.1. Sua presença no guia representa infraestrutura técnica do stack mágico, não uma escola, spellbook ou árvore de progressão jogável.
## Ace's Spell Utils — 1.2.7.1
`aces_spell_utils-1.2.7.1-1.21.1.jar`
**Ace's Spell Utils** é uma API de suporte para addons de Iron's Spells, mas fornece uma quantidade considerável de infraestrutura reutilizável. Ela inclui classes-base para armas com habilidades ativas/passivas, magic swords, magic guns, loot bags, spellbooks passivos e itens com cooldowns próprios.
A biblioteca também registra atributos como **Mana Steal, Mana Rend, Spell Resistance Penetration, Magic Crit Chance/Damage, projectile crits, Life Recovery e Vigor Reap**. No lado de spellcraft oferece escolas utilitárias como Occult, Hydro e Technomancy, animações de casting e classe abstrata para summons; ainda possui helpers para boss music, shaders de pós-processamento e renderização emissiva de armaduras GeckoLib.
## GTBC's SpellLib — runtime 2.0.0-1.21.1
`gtbcs_spell_lib-2.0.0-1.21.1.jar`
**GTBC's SpellLib/API** é a biblioteca comum usada pelos addons de Iron's Spells do GameTechBC. Ela não adiciona uma progressão jogável independente: concentra classes, atributos, helpers e estruturas reutilizáveis para que os addons do autor compartilhem a mesma implementação de spells, itens e mecânicas.
A linha 2.0 amplia essa infraestrutura com recursos como atributo de **Elemental Permeability**, interfaces utilitárias para preservação localizada de inventário/XP/Curios em contextos específicos, framework de paintings customizadas e componentes reutilizáveis como Treasure Pouch. No pack, **GTBC's Geomancy Plus** é um consumidor direto dessa API.
## HazentouveLib — 1.0.9
`hazentouvelib-1.0.9.jar`
**HazentouveLib** é uma biblioteca para os mods e addons de Hazen e não adiciona conteúdo jogável por conta própria. Ela centraliza três escolas reutilizáveis — **Radiance, Shadow e Cosmic** —, classes-base para entidades spellcasters, o efeito **Hexed** e abstrações de itens mágicos, armaduras e maces/staffs.
Também fornece tiers e combinações de armor helpers, raridades ligadas a escolas, cinco keybinds reutilizáveis para habilidades e a abstração `AbstractTaggedSpell`. Essas APIs são consumidas por projetos como **Hazen N Stuff**. A build atual do pack, `1.0.9`, é a release NeoForge 1.21.1 publicada em 16/08/2026.
## FamiliarsLib — runtime 1.21.1-1.7
`familiarslib-1.21.1-1.7.1.jar`
**FamiliarsLib** contém o núcleo técnico extraído de Alshanex's Familiars. A API permite registrar **familiars próprios**, beds/casas de familiars, blocos de armazenamento ligados a essas criaturas e até spells associados à **Sound school**, de modo que addons possam criar companions compatíveis sem copiar a implementação do mod principal.
Também concentra comportamento compartilhado de familiars e infraestrutura usada pelo addon Alshanex's Familiars; atualizações da linha 1.7 incluíram correções de beds/healing e estabilidade de servidor. O filename/publicação é `1.7.1`, enquanto o metadata runtime da instância declara `1.21.1-1.7`; as duas strings permanecem separadas.
## Iron's Lib — runtime 1.21.1-2.1.0
`irons_lib-1.21.1-2.1.0.jar`
**Iron's Lib** reúne frameworks reutilizáveis para os mods do Iron431. A camada de **transmogs** permite aplicar modelos únicos a armaduras, construir interfaces customizáveis de armor e lidar automaticamente com cape physics; o framework de **statues** cobre geração dinâmica de estátuas de jogador, estruturas multibloco e renderização estática.
A biblioteca também implementa atributos RPG leves como **Armor Pierce, Mining Speed, Experience Gained, Arrow Damage, Crit Damage, Dodge Chance e Healing Received**. Um Attribute Remapper permite trocar atributos de itens dinamicamente e possui compatibilidade nativa com Apothic Attributes, com pontos de extensão tanto por código quanto por datapacks. Há ainda uma camada comum de integração Patreon usada pelos projetos do mesmo ecossistema.
# 10. Visão geral por estilo de personagem
<table fit-page-width="true" header-row="true">
<tr>
<td>Estilo</td>
<td>Mods que mais representam esse estilo</td>
</tr>
<tr>
<td>**Mago criador de spells**</td>
<td>Ars Nouveau, Not Enough Glyphs, Ars Zero, Ars Elemental, Ars Elemancy</td>
</tr>
<tr>
<td>**Mago RPG de escolas**</td>
<td>Iron's Spells, Asterism Arcanum, Aeromancy, Geomancy, Wind's Spellbooks, Magic From The East</td>
</tr>
<tr>
<td>**Necromante / summoner**</td>
<td>Goety, Goety Cataclysm, Goety Iron</td>
</tr>
<tr>
<td>**Ocultista ritualístico**</td>
<td>Malum, Eidolon: Repraised</td>
</tr>
<tr>
<td>**Technomancer**</td>
<td>Ars Creo, Ars Technica, Ars Controle, Create: Wizardry, Ars Sable, IronSable</td>
</tr>
<tr>
<td>**Paladino / suporte**</td>
<td>Paladin Spells</td>
</tr>
<tr>
<td>**Bardo**</td>
<td>Tunes 'n Tomes</td>
</tr>
<tr>
<td>**Mago elemental**</td>
<td>Ars Elemental, Ars Elemancy, Aeromancy, Geomancy Plus, Wind's Spellbooks</td>
</tr>
<tr>
<td>**Mago Eldritch / vazio**</td>
<td>Discerning The Eldritch, Deeper and Darker: Spellbooks, Dreamless Spells</td>
</tr>
<tr>
<td>**Mago astral**</td>
<td>Asterism Arcanum</td>
</tr>
<tr>
<td>**Mago do End**</td>
<td>Fire's Ender Expansion</td>
</tr>
</table>
# 11. Leitura do conjunto mágico do pack
O conjunto atual cobre várias das grandes fantasias de magia de um RPG. **Ars Nouveau** fornece spellcraft livre e automação; **Iron's Spells** fornece classes e escolas de combate; **Goety** cobre necromancia e summons; **Malum** cobre spirit arcana; **Eidolon** cobre ocultismo e rituais.
Os addons então aprofundam essas bases em direções específicas: elementos, música, paladino, astral, Eldritch, End, geomancia, tecnomagia, familiars, portais, logística e joalheria. Isso faz a magia do pack funcionar como vários caminhos de personagem diferentes, e não como um único mod com centenas de feitiços misturados.
---
**Base da listagem:** `modlist 28.08.26.txt`, NeoForge 1.21.1. Descrições consolidadas a partir da modlist atual e das páginas dos projetos usadas durante a auditoria.
---
# Guia relacionado
<callout icon="⚙️" color="gray_bg">
	A parte tecnológica do pack foi organizada em uma página separada para funcionar como uma seção paralela deste catálogo.
	<mention-page url="https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff"/>
</callout>
## Gameplay e sistemas
<mention-page url="https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6"/>
# Atualização da modlist — novos mods mágicos (23/08/2026)
<callout icon="🆕" color="purple_bg">
	Esta atualização usa `modlist 28.08.26.txt` e acrescenta os novos sistemas sobrenaturais, addons do Iron's e integrações mágicas detectados na modlist atual. O foco permanece exclusivamente em **descrever o conteúdo e a forma de jogar**.
</callout>
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
# Alquimia, toxinas e mutações
## Toxony — 0.10.7
`toxony-0.10.7.jar`
**Toxony** é um sistema de alquimia ofensiva e progressão corporal inspirado em química antiga, ciência experimental e na lógica de poções de *The Witcher 3*. A entrada no mod é guiada pelo **Lost Journal**, que documenta ingredientes, processos laboratoriais e etapas da progressão.
O núcleo é o sistema de **Toxicity**. O personagem acumula toxinas conforme utiliza preparações e pode acompanhar sua composição por um Toxicity Gauge. Atingir determinados limiares interage com **mutagens**, que concedem buffs semipermanentes e alteram o personagem em troca de expô-lo a uma carga tóxica crescente; excesso de toxinas também produz consequências negativas.
A cadeia de crafting inclui plantas e materiais próprios, blocos de laboratório, misturas e **oils** que podem ser aplicados a armas ou utilizados ofensivamente. O mod acrescenta armas e ferramentas voltadas a monster hunting, armaduras, uma alternativa ao Netherite obtida no Overworld, estruturas, vegetação e decoração de laboratório/copper alchemy.
Há integrações específicas com outros sistemas: armas de prata causam dano aumentado a vampiros e lobisomens do **Vampirism**, certos mutagens concedem **School Spell Power** para Iron's Spells e o Toxicity Gauge pode ocupar slot Charm via Curios. A build `0.10.7` é beta NeoForge 1.21.1.
# Ecossistema Vampirism
## Vampirism — 1.10.12
`Vampirism-1.21-1.10.12.jar`
**Vampirism** adiciona duas progressões sobrenaturais completas: **vampiro** e **vampire hunter**. A infecção vampírica pode começar por mordida ou sangue injetado e evolui para uma transformação permanente. Vampiros substituem a fome comum por uma economia de **sangue**, obtido de animais e villagers; alimentar-se sem matar permite que a vítima regenere sangue, enquanto níveis mais altos também permitem converter humanos em vampiros.
A progressão vampírica usa **rituais e níveis**. Subir de nível aumenta poder físico e concede skill points para habilidades como night vision, transformação em morcego, teleporte e outras capacidades sobrenaturais. Em contrapartida, o sol causa dano e o avanço do vampiro faz surgirem hunters mais perigosos. Coffins, equipamentos e estruturas próprias participam dessa rotina noturna.
A rota de **hunter** possui arsenal, coleta, combate, exploração e árvore de habilidades própria, incluindo técnicas voltadas especificamente a enfrentar vampiros. O worldgen inclui Vampire Forest, vampire barons, estruturas e aldeias defendidas por hunters. Vilas podem entrar no conflito de facções e ser controladas por vampiros ou caçadores, enquanto a força de certos mobs escala em função do nível dos jogadores próximos.
## Bloodlines — 1.21-3.0.9
`bloodlines-1.21-3.0.9.jar`
**Bloodlines** adiciona uma camada de especialização sobre as facções de Vampirism. Vampiros e hunters podem entrar em **linhagens** específicas seguindo a quest de ingresso correspondente; uma vez dentro, a linhagem abre uma árvore própria de habilidades e regras que alteram a forma como aquela facção progride e luta.
Os ranks de bloodline podem conceder novas skills e também impor penalidades ou condições próprias, fazendo a escolha representar um arquétipo mecânico e não apenas um título. O addon possui rotas e sistemas temáticos diferentes por linhagem, incluindo conteúdo específico de hunters e vampiros. Também existe uma saída formal do sistema: a **Purity Injection** permite abandonar a bloodline e retornar à progressão sem linhagem.
## Vampiric Ageing — 1.4.21
`vampiricageing-1.21-1.4.21.jar`
**Vampiric Ageing** adiciona **Age Ranks** e evolução de longo prazo para vampiros, hunters e, quando Werewolves está presente, lobisomens. Por padrão, a progressão vampírica começa depois dos níveis altos do Vampirism e o jogador aumenta sua idade drenando sangue; o progresso pode ser consultado em Coffins. O método de envelhecimento é configurável e pode ser baseado em tempo, infecções, drenagem de sangue ou caça a outras facções.
Subir de Age Rank fortalece atributos existentes e libera capacidades adicionais. Hunters e werewolves possuem suas próprias variantes de ageing, com modificadores e poderes específicos; a linha atual inclui, por exemplo, percepção de entidades invisíveis por ações como **Wise Eye/Superior Senses**, além de parâmetros para regeneração, mineração, dano contra facções e força de leap dos lobisomens.
Quase toda a mecânica é configurável: requisitos de rank, método de evolução, perdas em morte e multiplicadores de poder podem ser ajustados ou desativados. Assim, Ageing funciona como uma camada posterior ao leveling normal das facções.
## Werewolves — 2.0.3.3
`Werewolves-1.21-2.0.3.3.jar`
**Werewolves** adiciona uma terceira facção sobrenatural ao ecossistema Vampirism. O jogador pode viver em forma humana durante o dia e assumir sua força bestial à noite; a condição altera alimentação, combate, resistências e vulnerabilidades. Werewolves se alimentam de **carne fresca**, recebem proteção natural da pelagem contra dano elevado e são particularmente vulneráveis a **armas de prata**.
A progressão permite escolher especializações diferentes, desde foco direto em combate até caminhos mais voltados a sobrevivência ou manutenção de características humanas. Transformação, habilidades e equipamentos próprios fazem o estado de lobisomem funcionar como uma progressão persistente, não como buff temporário.
O addon também modifica o mundo com conteúdo específico da facção, incluindo o biome **Werewolf Heaven**, onde lobisomens podem expressar sua força mesmo durante o dia. A build instalada `2.0.3.3` é a release NeoForge 1.21.1 atual.
## Vampirism Integrations — 1.10.2
`vampirism_integrations-1.21.1-1.10.2.jar`
**Vampirism Integrations** concentra compatibilidades externas que não ficam no core do Vampirism. Ferramentas de informação como **HWYLA/WAILA/WTHIT/Jade** podem exibir nível de sangue, informações de garlic e estado vampírico das criaturas por meio dessa camada.
A integração com **MCA/MCA Reborn** permite alimentar-se de villagers desse ecossistema, cria versões vampíricas/conversões e adapta comportamento de aldeias e villagers à lógica de mordidas e facções. Outras bridges tratam reconhecimento de vampiros como undead/holy targets, biomas onde a luz solar deve se comportar de modo especial e compatibilidades de crafting.
Para sistemas de sobrevivência, há integração com **Survive/Cold Sweat** que altera resistência térmica de vampiros e pode suprimir sede. Também existem pontos de integração para CraftTweaker editar receitas próprias do Vampirism e para mods de aldeias/guards respeitarem facções. A versão `1.10.2` é a release NeoForge 1.21.1 instalada.
## Vampirism Iron's Spells Compatibility — 0.0.9
`vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`
**Vampirism Iron's Spells Compatibility** conecta a economia de sangue do Vampirism às escolas **Blood** e **Holy** de Iron's Spells. Para vampiros, Ray of Siphoning e Devour podem restaurar sangue com base no dano de vida realmente entregue ao alvo, depois de absorção e demais etapas de processamento, sem contar overkill e respeitando a capacidade máxima de sangue.
Nos spells Blood que consomem mana, um vampiro normalmente paga mana primeiro; se não houver mana suficiente, o addon pode realizar um pagamento atômico com sangue em vez de consumir parcialmente recursos. Há também configuração para usar sangue como recurso principal nesses casts. Spells Blood lançados por vampiros recebem um multiplicador próprio de cooldown — por padrão `2/3`, equivalente a cooldown 1,5× menor — enquanto Ray mantém comportamento especial.
A escola Holy passa a reconhecer vampiros como alvos incompatíveis com healing comum: dano Holy contra NPCs vampiros é amplificado, healing Holy pode ferir vampiros em vez de curá-los e utility spells Holy podem causar dano e cancelar o cast quando o próprio caster é vampiro. As regras são configuradas no serverconfig por mundo.
---
**Guias relacionados:** [⚙️ Mods de Tecnologia](https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff) · [⚔️ Gameplay e Sistemas](https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6)