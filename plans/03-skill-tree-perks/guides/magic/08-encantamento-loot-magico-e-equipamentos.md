<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

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
