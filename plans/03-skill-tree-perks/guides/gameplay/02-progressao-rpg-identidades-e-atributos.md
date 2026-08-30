<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 2. Progressão RPG, identidades e atributos

## Identity2 — 2.2.1

`1.21.1-identity2-neoforge-2.2.1.jar`
**Identity2** é um sistema de morph: ao desbloquear identidades de criaturas, o jogador pode assumir suas formas e receber características e habilidades associadas a elas. O sistema mantém variantes, ajusta hitbox/altura dos olhos e inclui habilidades específicas de várias entidades, além de interações de IA dependentes da forma.
No pack, ele pertence ao eixo RPG/mobs porque altera diretamente o corpo e as capacidades jogáveis do personagem; não é apenas uma modificação visual. O stack atual também inclui **Pehkui 3.8.3+1.21-neoforge**, que fornece escalas de entidade, e **Epic Fight - Pehkui Incompatibility FIX 1.0.2**, que mantém essa camada de escala compatível com o combate Epic Fight.

## Walkers / Woodwalkers — 5.8.13

`walkers-5.8.13.jar`
**Woodwalkers** é um sistema de morph em que o jogador desbloqueia uma **segunda forma de criatura** e pode alternar para ela por keybind. O desbloqueio é feito ao observar uma criatura por um período configurado; depois de escolhida, a forma passa a fazer parte da identidade jogável do personagem conforme as regras do mod.
A transformação é funcional: **tamanho, vida e capacidades** mudam para acompanhar o mob, e quase todas as criaturas possuem alguma habilidade característica — como mobilidade de Strider sobre lava, voo de Bat ou capacidades ofensivas de Ghast. O nome do projeto é Woodwalkers, enquanto o mod id/JAR instalado permanece `walkers` 5.8.13. A dependência principal é CraftedCore.

## Pehkui — runtime 3.8.3+1.21-neoforge

`Pehkui-3.8.3+1.21-neoforge.jar`
**Pehkui** é um framework de **escala de entidades**. Ele permite que jogadores e mobs tenham tamanho alterado de forma persistente e expõe escalas relacionadas a dimensões, hitbox, alcance, velocidade e outras propriedades derivadas para mods consumidores.
Isso o diferencia de Identity2 e Walkers: Pehkui não fornece formas de criatura nem um sistema de morph por conta própria; ele fornece a infraestrutura de escala que outros sistemas podem utilizar quando precisam representar entidades maiores ou menores. No pack, Identity2 é um consumidor relevante e existe também `Epic Fight - Pehkui Incompatibility FIX` para corrigir a apresentação do jogador quando escala e animação Epic Fight interagem.
A build instalada é `3.8.3+1.21-neoforge`, publicada para a linha Minecraft 1.21/1.21.1. O canal público é beta, o que descreve maturidade da release e não incerteza sobre a identidade do JAR atual.

## Pufferfish's Skills — 0.18.3

`puffish_skills-0.18.3-1.21-neoforge.jar`
É o framework jogável de **árvores de habilidades** do pack. Ele permite que datapacks e addons definam categorias de skill, nós, requisitos, conexões, custos, recompensas e fontes de experiência.
Para o jogador, isso aparece como árvores nas quais pontos são investidos para desbloquear bônus ou habilidades. O mod também consegue conceder experiência a partir de ações como dano, mortes e outras fontes configuradas, permitindo que progressão seja vinculada ao que o personagem realmente faz.

## Pufferfish's Attributes — 0.8.3

`puffish_attributes-0.8.3-1.21-neoforge.jar`
**Pufferfish's Attributes** é um provider amplo de atributos dinâmicos para sistemas RPG. Ele registra estatísticas que outros mods, equipamentos, efeitos, datapacks e árvores de skills podem modificar sem precisar implementar a lógica de cada grandeza separadamente.
O catálogo inclui atributos ligados a **stamina**, tipos de dano e resistência, knockback, armor/toughness/protection/resistance shred, regeneração natural, stealth, **life steal**, redução de queda, velocidades de projéteis de bow/crossbow e ganho de experiência, entre outros. Dessa forma, um sistema de progressão pode trabalhar com estatísticas que vão muito além de vida, ataque e velocidade vanilla.
Esses atributos originalmente faziam parte de versões antigas de Pufferfish's Skills, mas foram separados em um mod independente; a linha moderna de Skills é compatível com ele. A build `0.8.3` instalada é a release mantida para NeoForge 1.21/1.21.1.

## Pufferfish's Unofficial Additions — 2.2.8

`pufferfish_unofficial_additions-1.21.1-2.2.8.jar`
**Pufferfish's Unofficial Additions** é um addon para Pufferfish's Skills que aumenta o vocabulário usado por árvores de progressão. Ele adiciona **novas fontes de experiência**, novas rewards e operações de cálculo/configuração que podem ser referenciadas pelos arquivos das skill trees.
Entre as fontes documentadas estão **harvest de crops** — com acesso à quantidade real de seeds/crops dropados — e **fishing**, permitindo calcular XP conforme o item pescado. As rewards incluem aplicar efeitos permanentes, conceder imunidade até determinado amplifier, alterar duração/amplifier de efeitos recebidos e permitir caminhar sobre powder snow.
Há ainda integração direta com **Iron's Spells 'n Spellbooks**: lançar spells pode gerar experiência com fórmulas que recebem escola, spell, nível, raridade, mana cost, duração, cooldown e outros dados do cast. Os antigos atributos próprios deste addon foram movidos para Additional Attributes, portanto ele não substitui Pufferfish's Attributes.

## Additional Attributes — 1.2.2

`additional_attributes-1.21.1-1.2.2.jar`
**Additional Attributes** registra atributos adicionais voltados a mecânicas que normalmente não são expostas como estatísticas modificáveis. Na linha 1.21.1 isso inclui atributos derivados de **Looting, Fishing Lure/Fishing Luck e harvest**, permitindo, por exemplo, aumentar diretamente a quantidade de crops colhidas por modificadores de atributo.
O mod também possui integrações específicas com outros sistemas presentes no pack. Para **Apotheosis/Apothic**, há um atributo de *Apothic Crafting* que controla chance/faixa de raridade de affixes em itens craftados, com clamps configuráveis por datapack. Para **Iron's Spells**, atributos podem aumentar/reduzir nível geral, por escola ou por spell específico, conceder spells/escolas inatas independentes do spellbook e controlar a chance de preservar scrolls.
Assim, ele funciona como uma camada de estatísticas integradoras e não é equivalente ao conjunto generalista do Pufferfish's Attributes. A build NeoForge 1.21.1 instalada é `1.2.2`.

## Apothic Attributes — 2.10.1

`ApothicAttributes-1.21.1-2.10.1.jar`
**Apothic Attributes** é a camada de atributos compartilhada pelo ecossistema Apothic/Apotheosis. Além de infraestrutura para outros módulos, registra estatísticas jogáveis que podem ser modificadas por equipamentos, affixes, gems, efeitos e sistemas externos: **critical strike chance/damage, dodge chance, life steal, healing received, overheal, armor/protection pierce e armor/protection shred**, entre outras grandezas do ecossistema.
A distinção entre *pierce* e *shred* é relevante: pierce ignora parte da defesa durante a resolução do ataque, enquanto shred representa redução aplicada à defesa do alvo conforme as regras do atributo. Esses valores são expostos pelo sistema de atributos do Minecraft e podem ser consumidos por outros conteúdos que reconheçam os atributos registrados.
O mod também fornece infraestrutura/interface utilizada por Apotheosis e pelos módulos Apothic. Portanto ele não é apenas uma biblioteca invisível: define contratos de estatísticas que afetam diretamente cálculo de combate e progressão de equipamento. A build instalada é `2.10.1` para NeoForge 1.21.1 e depende do ecossistema Placebo/Apothic correspondente.

## Wayward Attributes — 1.1.1

`wayward_attributes-1.21.1-1.1.1.jar`
**Wayward Attributes** é principalmente um **overhaul visual de como atributos aparecem nas tooltips**, não um segundo grande provider de estatísticas RPG. Ele associa ícones aos atributos por um sistema data-driven e reorganiza sua apresentação para tornar equipamentos e modificadores mais legíveis.
Além da camada visual, o mod expõe como atributos algumas propriedades que normalmente não aparecem dessa forma: swords passam a mostrar propriedades de **sweeping damage**, enquanto bows e crossbows têm suas características representadas no mesmo modelo de atributos. Portanto sua função principal é apresentação/normalização visual de dados, com pequena exposição adicional de propriedades de armas.
A build `1.1.1` é a release NeoForge 1.21.1 atual. Sua sobreposição relevante é com outros formatadores de tooltip, não com Pufferfish's Attributes ou Apothic Attributes como sistema de progressão.

## AttributeFix — 21.1.3

`attributefix-neoforge-1.21.1-21.1.3.jar`
**AttributeFix** remove limites arbitrários do sistema de atributos do Minecraft que impedem mods e datapacks de trabalhar corretamente com valores muito acima ou abaixo das faixas vanilla. Ele atua na infraestrutura de atributos — vida, dano, velocidade e outras estatísticas registradas — para que sistemas RPG possam aplicar modificadores maiores sem serem truncados pelos clamps padrão.
Não cria novos atributos, perks ou progressão por conta própria; sua função é permitir que providers e equipamentos existentes usem o sistema de atributos em escalas mais amplas. A build instalada é `21.1.3` para NeoForge 1.21.1.

## Max Health Fix — 21.1.4

`maxhealthfix-neoforge-1.21.1-21.1.4.jar`
**Max Health Fix** corrige um bug antigo do Minecraft em que a vida atual do jogador pode ser reduzida incorretamente ao valor vanilla de 20 ao entrar no mundo, mesmo quando equipamentos ou outros sistemas concedem um `max_health` superior.
O patch preserva corretamente a relação entre vida atual e vida máxima durante login/reentrada, evitando que builds com bônus persistentes de saúde percam vida apenas por reconectar. A build instalada é `21.1.4` para NeoForge 1.21.1.

## Apotheosis — 8.7.0

`Apotheosis-1.21.1-8.7.0.jar`
**Apotheosis** é um sistema de progressão RPG centrado em loot especial, affixes, gems, sockets e world tiers. Equipamentos encontrados no mundo podem receber propriedades e raridades próprias, e a progressão passa a envolver obtenção, avaliação e melhoria desses itens em vez de depender apenas do equipamento vanilla.
Na arquitetura atual, partes históricas do mod são módulos top-level separados. **Apothic Attributes** fornece a infraestrutura de atributos; **Apothic Enchanting** implementa o overhaul de encantamentos; **Apothic Spawners** expande e torna configuráveis os spawners. Esses JARs são componentes complementares presentes no pack, não cópias do `Apotheosis-1.21.1-8.7.0.jar`.

## Artifacts — 13.2.3

`artifacts-neoforge-13.2.3.jar`
**Artifacts** transforma exploração em fonte de acessórios poderosos que **não são craftáveis normalmente**. Cada artifact aparece em conjuntos limitados de loot — baús de estruturas, arqueologia ou raramente equipado em entidades — e concede uma habilidade própria quando usado no slot adequado.
O mod também adiciona **underground campsites**, onde um baú pode ser substituído por um Mimic hostil; derrotar um Mimic sempre fornece um artifact aleatório. Cada item possui modelo equipado próprio e incentiva procurar tipos diferentes de estrutura para completar o catálogo. A build `13.2.3` é a release NeoForge 1.21.1 instalada e inclui correções específicas de compatibilidade com Lootr e slots de acessórios.

## Reliquified Artifacts — 1.0.8

`reliquified_artifacts-1.21.1-1.0.8.jar`
**Reliquified Artifacts** conecta Artifacts ao modelo de progressão do **Relics**. O addon adapta e amplia acessórios do Artifacts para que participem das mecânicas do ecossistema Relics e adiciona conteúdo cruzado relacionado, mantendo os dois mods-base como providers separados.
Seu papel é de bridge/conteúdo RPG entre sistemas de acessórios; não duplica o catálogo de Artifacts nem o core de Relics. A build instalada é `1.0.8`, beta oficial para NeoForge 1.21.1, e depende de Relics + Artifacts.

## Iron's Gems 'n Jewelry — runtime 1.21.1-2.0.2

`irons_jewelry-1.21.1-2.0.2.jar`
**Iron's Gems 'n Jewelry** adiciona um sistema modular de **jewelcrafting**. Joias são fabricadas na Jewelcrafting Station a partir de patterns e materiais; alguns patterns são conhecidos de início, enquanto outros precisam ser descobertos em loot ou obtidos por **Artisan Scrolls/trades**.
O projeto possui mais de dez mil combinações possíveis entre padrões e materiais, sete gemstone items obtíveis e uma profissão de villager própria. O pattern define o tipo de bônus/atributo e os materiais alteram as propriedades da peça, permitindo produzir acessórios Curios com estatísticas variadas. A build instalada é `1.21.1-2.0.2` e depende de Iron's Lib, Atlas API e Curios API.

## Apothic Category Compat — 2.0.2

`apothic_compat-2.0.2.jar`
**Apothic Category Compat** corrige itens modded que o sistema automático de categorias do Apotheosis 8.x não classifica adequadamente para affixes. Por meio de um data map, armas externas podem ser roteadas para categorias como **bow, trident, sword, heavy weapon ou categorias adicionais registradas por outros addons**, fazendo o loot/affix system aplicar regras apropriadas.
Na linha 1.21.1 há overrides para conteúdos de Cataclysm, Alex's Caves/Mobs, Born in Chaos, Deeper and Darker, Ice and Fire, Iron's Spellbooks e outros mods. O addon também possui **affix blacklist** para impedir determinados affixes em novos rolls. A build instalada é `2.0.2` para NeoForge 1.21.1/Apotheosis 8.x.

## Apothic Compats — 0.2.4.2

`apothic_compats-0.2.4.2.jar`
**Apothic Compats** distribui datapacks de integração para que conteúdo de outros mods participe do ecossistema Apotheosis de maneira mais profunda. Conforme o mod detectado, pode acrescentar **affixed loot entries, gear sets, affixes, gems, invaders e categorias adicionais** em vez de apenas classificar uma arma existente.
A lista 1.21.1 cobre vários mods presentes no pack, incluindo Applied Energistics 2, Alex's Caves, Alex's Mobs, Ars Nouveau e Amendments, entre muitos outros. Ele também possui suporte opcional a Ancient Reforging. A build `0.2.4.2` é a release NeoForge 1.21.1 instalada, publicada em 22/07/2026.

## Apothic Spawners — 1.4.0

`ApothicSpawners-1.21.1-1.4.0.jar`
**Apothic Spawners** transforma mob spawners em blocos coletáveis e configuráveis. Com Silk Touch, spawners podem ser movidos; **Spawner Modifiers** aplicados com itens vanilla alteram minimum/maximum delay, spawn count, max nearby entities, player range e spawn range, além de propriedades avançadas como ignore players/conditions/light, redstone control, No AI, Silent, Youthful, Burning e Echoing.
O módulo também adiciona o encantamento **Capturing**, que dá chance de mobs derrubarem spawn eggs; esses ovos podem trocar a entidade produzida por um spawner. Assim, o mod cria uma progressão de automação de mobs baseada em localizar, mover, modificar e especializar spawners. A build `1.4.0` é a release NeoForge 1.21.1 instalada.

## Pickable Health Orbs — runtime 1.21.1-1.0.0

`pickable_orbs-1.21.1-1.0.0.jar`
**Pickable Orbs** adiciona pickups no estilo RPG que aparecem como entidades no mundo. Mobs ou blocos configurados podem soltar diferentes tipos de orbe e, ao contato, o jogador recebe imediatamente benefícios como cura ou efeitos de poção; por padrão não se trata de um item guardado para uso posterior.
A configuração permite criar/remover tipos, controlar chance e origem dos drops e definir duração/amplifier dos efeitos. Dessa forma o sistema funciona como uma camada de recompensa momentânea durante combate e exploração.
