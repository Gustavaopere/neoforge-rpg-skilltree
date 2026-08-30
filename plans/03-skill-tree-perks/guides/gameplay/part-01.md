<!-- Snapshot canônico do Notion: GUIA COMPLETO — Gameplay e Sistemas | NeoForge 1.21.1
Fonte: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6
Parte 1/4. Continuar em part-02.md. -->

<callout icon="📌" color="blue_bg">
	**RECONCILIADO + QC DELTA 13/13 — 2026-08-28:** `modlist 28.08.26.txt` é a fonte canônica para presença, JAR e versão. No eixo Gameplay/Interface entram **Pickable Orbs, Dynamic RPG Resource Bars, A Good Place e JEED**; o stack Sable/Ragdoll também permanece referenciado como conteúdo cross-domain no corpo/apêndice técnico. As entradas foram revalidadas por JAR e descritas sem transformar HUD, visual ou compatibilidade em sistemas de gameplay equivalentes. A cobertura global dos 13 novos mods foi conferida entre os três guias.
</callout>
## Stack atual de sobrevivência — 28/08/2026
- **Cold Sweat 2.4.2:** temperatura corporal.
- **Create: Cold Sweat 1.1.2:** bridge Create↔Cold Sweat, não segundo sistema térmico.
- **Ecliptic Seasons 0.14.99-beta-2:** clima/estações; não substitui temperatura corporal.
- **Thirst Was Reclaimed 3.0.4:** sede/hidratação.
- **Thirst Was Fixed 2.1.5:** compatibilidade/fixes do sistema de sede.
- **Nutritional Balance 1.21.1-7.0.3:** nutrição quando a métrica requerida puder ser lida com segurança.
- **AnimalHusbandry 0.4.0:** criação e manejo de animais com genética, saúde, reprodução, linhagem e gestação.
<callout icon="⚔️" color="red_bg">
	Este documento reúne os **mods de gameplay e sistemas gerais** do pack que não pertencem principalmente aos eixos de Magia ou Tecnologia. Entram aqui combate, movimento, progressão, sobrevivência, alimentação, fauna, bosses, exploração, estruturas, colônias e utilidades que alteram diretamente a forma de jogar. Para que os três guias cubram integralmente a modlist top-level, o final deste documento também mantém um **apêndice técnico** para bibliotecas/APIs, interface, visual, áudio e performance que não pertençam principalmente aos guias de Magia ou Tecnologia. Esses componentes são descritos pelo papel técnico real e não tratados como sistemas de gameplay equivalentes aos capítulos principais.
</callout>
<table_of_contents/>
# 1. Combate, movimento e ação
## Epic Fight — 21.17.3.1
`epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`
**Epic Fight** transforma o combate do Minecraft em um sistema de ação inspirado em jogos Souls-like. O jogador alterna para um Battle Mode próprio, no qual ataques deixam de ser simples cliques instantâneos e passam a utilizar sequências de golpes, animações com fases de ataque, alcance real das armas, stagger, impacto e gerenciamento de stamina.
Armas diferentes podem possuir movesets próprios, combos e ataques especiais. O sistema também trabalha com skills ativas e passivas, esquiva, defesa, guardas e atributos de combate como impacto, peso, armor negation e stun armor. Isso transforma escolha de arma, timing e posicionamento em partes centrais da luta.
No pack, Epic Fight funciona como a base do combate físico e recebe conteúdo de outros mods, principalmente Weapons of Miracles e Epic ParCool.
## Epic Fight - Better Lock On — 2.0.8-neoforge
`betterlockon-2.0.8-neoforge.jar`
**Epic Fight - Better Lock On** é uma camada client-side de aprimoramento do lock-on do Epic Fight. O mod adiciona um **indicador visual explícito de alvo travado** e ajusta a experiência de seleção/acompanhamento de alvo para tornar o estado do lock-on mais legível durante combate rápido.
Ele não substitui o sistema de combate nem cria um segundo Battle Mode: trabalha sobre o targeting já fornecido pelo Epic Fight. A build instalada `2.0.8` é a release NeoForge 1.21.1 publicada em 12/05/2026.
## Lock-On Movement Fix — 1.0.2
`lockonmovementfix-neoforge-1.0.2.jar`
**Epic Fight x Better Lock On: Movement & Camera Fixes** corrige como movimento, esquiva e mira se comportam durante lock-on. Com um alvo travado, WASD passa a mover o personagem **relativamente à câmera** em primeira pessoa, terceira pessoa vanilla e câmeras compatíveis, evitando que o input seja artificialmente puxado em direção ao inimigo.
Dodge rolls também seguem a direção WASD relativa à câmera; attack lunges usam o alvo travado para evitar golpes avançando por um facing antigo; o corpo pode permanecer voltado ao alvo enquanto bloqueia ou carrega um spell; e a mira de bows, crossbows e itens é alinhada ao crosshair.
O addon ainda oferece **auto-lock ao atacar um alvo não travado** e troca de alvo por flick lateral do mouse, com suporte equivalente ao right stick do Controllable. Better Lock On é opcional para parte dessas correções; quando presente, os dois mods trabalham sobre o mesmo targeting. A build NeoForge 1.21.1 instalada é `1.0.2`.
## Weapons of Miracles — 2.0.176
`WeaponsOfMiracles-2.0.176.jar`
**Weapons of Miracles** é uma grande expansão de conteúdo construída diretamente para Epic Fight. Ela adiciona **armas próprias, animações, movesets, skills, guardas/parries e estilos de luta** cuja lógica depende do sistema de combate do mod-base, em vez de simplesmente atribuir presets vanilla a novos itens.
As armas funcionam como kits de combate distintos: ataques básicos, skills e animações podem alterar mobilidade, timing, defesa e sequências disponíveis. A própria linha 2.0 continua ajustando comportamentos específicos de armas e guardas; a versão `2.0.176`, publicada para NeoForge 1.21.1 em 20/08/2026, foi testada pelo projeto contra **Epic Fight 21.17.3.1**, exatamente a versão instalada no pack.
## ParCool! — 4.0.0.2
`ParCool-1.21.1-4.0.0.2.jar`
**ParCool** substitui a mobilidade básica do Minecraft por um sistema amplo de parkour. O jogador ganha ações como corrida rápida, vault sobre obstáculos, wall run, wall jump, agarrar e escalar paredes, cat leap, dodge, dive, crawl e outras técnicas de travessia.
O sistema utiliza stamina e pode compartilhar o sistema de stamina do Epic Fight. Com isso, movimentação fora de combate e movimentação durante lutas passam a usar a mesma linguagem de resistência física.
## Grappling Hook Mod: Skybound — runtime 1.1+1.21.1.neoforge
`grapplemod-1.1+1.21.1.neoforge.jar`
**Grappling Hook Mod: Skybound** é um fork modernizado do sistema clássico de grappling hook, mantendo a travessia baseada em **corda física**: o gancho se prende a superfícies e o movimento do jogador é determinado pela tensão/comprimento da conexão em vez de um teleporte ou dash simples.
A particularidade desta linha é tratar corretamente alvos móveis. O projeto documenta suporte direto a **contraptions do Create e airships/sublevels Sable**, além de paredes vanilla, permitindo que o ponto de ancoragem acompanhe estruturas em movimento. O JAR NeoForge `1.1+1.21.1.neoforge` é a build oficial de 31/05/2026; componentes de compatibilidade embarcados dentro dele continuam sendo internos, não mods top-level separados.
## Epic ParCool — 21.0.0
`epic x parcool-neoforge-21.0.0-1.21.1.jar`
**Epic ParCool** é a integração oficial entre Epic Fight e ParCool. Ela adapta os movimentos do jogador fornecidos pelo ParCool para coexistirem com o estado, animações e lógica do **Battle Mode** do Epic Fight, evitando que parkour e combate funcionem como dois controladores de movimento independentes.
Assim, ações de travessia podem ser encadeadas com combate mantendo o modelo de animação do Epic Fight. O projeto é mantido pelo próprio ecossistema Epic Fight e a build `21.0.0` é a release NeoForge 1.21.1 atual, publicada em 19/01/2026.
## Epic Fight Compat — 1.1.0
`epicfightcompat-1.1.0-mc1.21.1-neoforge.jar`
**Epic Fight Compat** é uma camada geral de adapters para **armas e entidades de mods externos** que podem ser traduzidos de forma limpa para presets/capabilities existentes do Epic Fight. Ele aplica patches somente quando o conteúdo correspondente está presente, evitando que cada mod precise fornecer seu próprio addon completo.
Na release `1.1.0` para NeoForge 1.21.1, a cobertura inclui itens ou entidades de **Farmer's Delight, ReArm, Supplementaries, Quark, Mowzie's Mobs, Illager Invasion, It Takes a Pillage, Darker Depths, Bosses'Rise, Hearth & Harvest e Mutant Monsters**, entre outros. Isso o torna um compat transversal; bridges especializadas continuam úteis quando precisam de renderização ou mecânicas que não cabem nesses presets genéricos.
## Ice and Fire CE x Epic Fight Armor Compat — 1.0.0
`iceandfire-ce-epicfight-armor-compat-1.0.0.jar`
**Ice and Fire CE x Epic Fight Armor Compat** é uma bridge específica de **armaduras** entre Ice And Fire Community Edition e Epic Fight. Ela adapta o equipamento do mod de dragões ao modelo animado/capabilities do Epic Fight para que as peças mantenham renderização e comportamento corretos durante poses e animações do Battle Mode.
Seu escopo é deliberadamente estreito: não adiciona novas armas, movesets ou criaturas e não substitui Epic Fight Compat, que faz adaptações gerais de entidades/armas de múltiplos mods. A build `1.0.0` é a release NeoForge 1.21.1 instalada.
## Epic Fight x Curios Compat — runtime 1.4
`Epic Fight x Curios Compat 2.2.jar`
**Epic Fight x Curios Compat** corrige e amplia a renderização de equipamentos guardados em slots **Curios** quando o modelo/animações do Epic Fight estão ativos. O projeto oferece um **Curios Slots Editor** e possui suporte declarado para vários tipos de backpack, spellbook e acessórios, incluindo Sophisticated Backpacks, Iron's Spells, Supplementaries e outros conteúdos compatíveis.
A release 2.2 também corrige entrada de texto/números no editor e um caso de **double rendering do quiver do Supplementaries**. O arquivo/publicação instalado é `2.2`, mas o metadata runtime local declara `1.4`; o guia preserva essa divergência em vez de substituir uma identidade pela outra.
## Epic Fight - Pehkui Incompatibility FIX — 1.0.2
`epicfightpehkuiincompatibilityfix-1.0.2.jar`
**Epic Fight - Pehkui FIX** é um patch client-side para um problema específico de escala entre Epic Fight e Pehkui. Quando o tamanho/dimensões do jogador mudam via Pehkui — inclusive após determinadas transições — o renderer do Epic Fight pode manter os braços na altura antiga, desalinhados dos olhos e do novo corpo.
O patch recalcula esse alinhamento para manter os braços na altura correta da visão após mudanças de scale/dimension. Ele não adiciona movesets nem novas escalas; existe apenas para fazer os dois sistemas existentes renderizarem corretamente juntos. A build `1.0.2` é a release NeoForge 1.21.1 instalada, publicada em 15/07/2026.
## Soul Fire'd — 6.1.0
`soul-fire-d-neoforge-1.21-6.1.0.jar`
**Soul Fire'd** transforma soul fire em uma família de mecânicas própria, em vez de tratá-lo apenas como uma recoloração do fogo comum. O projeto altera gameplay relacionado ao fogo de almas e adiciona conteúdo de aventura/equipamento associado, fazendo entidades e itens reconhecerem esse tipo de dano/combustão de forma específica.
Por alterar mecânicas de jogo, o mod é exigido em **cliente e servidor**. O JAR `soul-fire-d-neoforge-1.21-6.1.0.jar` é a release 6.1.0 compatível com Minecraft 1.21/1.21.1 publicada em 22/12/2025. Ele complementa combate e exploração, mas não substitui Epic Fight nem constitui um segundo overhaul geral de combate.
## Punchy — 2.7d
`punchy-2.7d-neoforge-1.21.1.jar`
**Punchy** adiciona feedback e animação em **primeira pessoa**, fazendo mãos e ações reagirem com movimento e física visual mais expressivos durante interação e combate. Seu foco é a apresentação da ação vista pelo próprio jogador, não um overhaul de regras de combate.
A build `2.7d` é a release NeoForge 1.21.1 publicada em 21/08/2026. Como Epic Fight possui sua própria linguagem de animações durante Battle Mode, o pack também contém uma bridge específica entre os dois.
## Punchy Epic Fight Compat — 1.0.0
`punchy_epicfight_neoforge.jar`
**Epic Fight X Punchy! Neo** é a bridge que impede as animações de primeira pessoa do Punchy de concorrerem com os estados de combate do Epic Fight. Quando o Battle Mode ou condições compatíveis estão ativos, a integração ajusta/desativa a renderização correspondente do Punchy.
O runtime local é `1.0.0`. É uma camada de compatibilidade entre os dois mods-base, não um sistema adicional de combate ou animação independente.
## Sable Ragdolls — 0.7.5
`sable_player_ragdoll-1.21.1-0.7.5.jar`
**Sable Ragdolls** adiciona um estado corporal físico para jogadores usando o motor Sable. O corpo é representado por peças simuladas, permitindo tombos/ragdolls por comando, por itens marcados em datapack ou por addons externos. O sistema também inclui dummies, suporte a skin/perfil, despawn configurável e API pública.
Ele funciona como base para outras camadas instaladas: Ragdoll Reactions decide quando eventos físicos provocam o estado; Sable Ragdolls Patch corrige interações; sable-x-cpm adapta modelos CPM.
## Ragdoll Reactions — 0.7.0
`ragdoll_reactions-1.21.1-0.7.0.jar`
**Ragdoll Reactions** conecta eventos físicos do mundo ao estado de ragdoll. Colisões fortes, atropelamentos, mudanças bruscas de direção e explosões podem derrubar o jogador, com sensibilidade, limite de velocidade de lançamento, cooldown de retrigger e ativação geral configuráveis no servidor.
O addon não implementa a física do corpo por conta própria: depende de Sable e Sable Player Ragdoll e apenas transforma determinados impactos/movimentos em gatilhos para o sistema.
## Sable Ragdolls Patch — 1.9
`sable_player_ragdoll_patch-1.21.1-1.9.jar`
**Sable Ragdolls Patch** corrige problemas de renderização, colisão e estado envolvendo Player Ragdoll/Ragdoll Corpse e outros componentes do pack. Entre os casos cobertos estão modelos/arms oversized com FA+Player, display de Curios, second skin/cape, swim pose, carrying/inventory e colisões de jogador/ragdoll.
A versão `1.9` também corrige a exibição dos braços de primeira pessoa do Punchy durante ragdoll, impede Ender Pearls e Wind Bombs nesse estado e corrige um caso em que `/sable remove @e` podia deixar o jogador preso fora do mundo.
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
# 3. Sobrevivência e condições ambientais
## Cold Sweat — 2.4.2
`ColdSweat-2.4.2.jar`
**Cold Sweat** adiciona um sistema dinâmico de **temperatura corporal** influenciado tanto pelo mundo quanto pelo estado do jogador. Bioma e temperatura ambiental, clima, altitude, blocos próximos, itens carregados ou usados e outras condições entram no cálculo térmico; por isso atravessar uma montanha, uma região gelada, o Nether ou uma área artificialmente aquecida pode exigir preparação diferente.
A sobrevivência térmica possui várias ferramentas próprias. **Armaduras podem receber insulation**, blocos quentes ou frios irradiam temperatura ao redor, o **Thermometer** permite acompanhar valores com precisão e **Waterskins** servem como alívio imediato ou como packs aquecidos/resfriados. A infraestrutura de base inclui dispositivos como **Hearth, Boiler e Icebox**, que permitem condicionar espaços e formar instalações permanentes de aquecimento ou refrigeração.
O sistema é altamente data-driven e configurável por configs, JSON/datapacks e KubeJS. A build instalada `2.4.2` é a release NeoForge 1.21.1 de 22/06/2026; nessa versão, fontes térmicas em objetos **Sable/Create Aeronautics** passaram a emitir temperatura corretamente e a integração de sede foi atualizada para **Thirst Was Reclaimed**, ambos presentes no stack atual.
## Ecliptic Seasons — 0.14.99-beta-2
`EclipticSeasons-1.21.1-neoforge-0.14.99-beta-2.jar`
**Ecliptic Seasons** transforma o calendário do mundo em um sistema sazonal baseado em **24 termos solares**. O avanço do ano altera clima e precipitação, cores de foliage/ambiente, comportamento de ecossistemas e condições de crescimento das culturas, fazendo agricultura e exploração dependerem do período sazonal em vez de permanecerem idênticas o ano inteiro.
A linha 0.14 também trabalha com renderização sazonal e integrações de mundo distante, incluindo ajustes para **Distant Horizons** e Embeddium. O stack instalado possui ainda **Ecliptic Seasons: MultiMod Patch 0.32.0-beta**, que adiciona compatibilidades em código, e **Ecliptic Seasons: Bundles 0.18.0**, que fornece datapacks/resource packs para conteúdo externo.
Ele não mede a temperatura corporal do jogador: essa função continua pertencendo ao Cold Sweat. A build `0.14.99-beta-2` é a versão NeoForge 1.21.1 instalada; o sufixo beta descreve maturidade da release, não dúvida sobre identidade ou presença.
## Snow! Real Magic! — runtime 12.2.2+neoforge
`SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`
**Snow! Real Magic!** expande o comportamento físico das snow layers. A neve pode cobrir **slabs, stairs, fences, vegetação e outras superfícies não cúbicas**, acumular com maior naturalidade durante nevascas e formar camadas que respeitam melhor a geometria do bloco subjacente.
O mod também adiciona regras configuráveis para acumulação, queda/movimento das camadas e interação com blocos cobertos. Assim, Ecliptic Seasons determina o contexto sazonal/climático em que a neve aparece, enquanto Snow! Real Magic! determina **como a neve depositada ocupa e reage ao mundo**. A build `12.2.2` é a release NeoForge 1.21.1 instalada, cujo runtime declara `12.2.2+neoforge`.
## Nutritional Balance — runtime 1.21.1-7.0.3
`nutritionalbalance-1.21.1-7.0.3.jar`
**Nutritional Balance** adiciona um sistema persistente de dieta no qual alimentos fornecem **Nutritional Units (NUs)** distribuídas entre nutrientes. Tooltips mostram os nutrientes de cada comida e uma GUI própria, aberta por padrão com `N`, exibe o nível atual de cada grupo, faixas-alvo e limites de malnutrition/engorgement.
Manter todos os nutrientes em suas faixas ideais concede buffs configuráveis — como benefícios de vida, velocidade ou eficiência — enquanto permanecer abaixo ou acima dos limites pode aplicar debuffs. **Sugar** funciona como nutriente não essencial, sem penalidade por deficiência, enquanto vegetables possuem comportamento especial que não pune excesso.
O sistema é data-driven: nutrientes usam tags e o mod percorre recipes para inferir o valor de comidas compostas a partir de seus ingredientes, permitindo incorporar alimentos vanilla e modded sem exigir uma tabela manual para cada prato. Buffs, debuffs, thresholds e integrações podem ser ajustados por datapack/config.
A versão `7.0.3` para NeoForge 1.21.1 também reestruturou a construção/traversal de nutrientes para reduzir travamentos em modpacks muito grandes.
## Thirst Was Reclaimed — runtime 1.21.1-3.0.4
`ThirstWasReclaimed-1.21.1-3.0.4.jar`
**Thirst Was Reclaimed** é o provider de **sede/hidratação** do pack. Ele adiciona uma necessidade separada da fome, com formas próprias de consumir água e regras de qualidade/pureza que fazem fontes de hidratação terem valores diferentes em vez de toda água ser equivalente.
O sistema é client+server e a release pública atual do ramo 1.21.1 é `3.0.4`. O metadata local preserva a string `1.21.1-3.0.4` como runtime. As correções e integrações adicionais ficam no addon Thirst Was Fixed, não no core.
## Thirst Was Fixed — 2.1.5
`thirstwasfixed-2.1.5.jar`
**Thirst Was Fixed** é a camada de correções e integrações do Thirst Was Reclaimed. Ele trata estados de pureza em cauldrons, permite configurar pureza de água de chuva/dripstone, beber de cauldrons e aplicar benefícios quando a barra de sede está cheia.
Também conecta sede a sistemas instalados: **Ars Nouveau Potion Flasks** podem restaurar hidratação, a Urn of Endless Waters do Ars Elemental pode interagir com água/pureza, FTB Ultimine pode exigir sede mínima, ParCool pode alterar regeneração de stamina conforme hidratação e Amendments pode purificar água em cauldrons aquecidos. A build instalada `2.1.5` foi publicada em 24/08/2026.
## Sophisticated Thirst Upgrade — 0.1.8
`sophisticated-thirst-upgrade.jar`
**Sophisticated Thirst Upgrade** leva a hidratação para o sistema de upgrades do Sophisticated Backpacks. O upgrade monitora itens armazenados na mochila capazes de restaurar sede e pode **consumi-los automaticamente**, de maneira análoga aos upgrades automáticos de alimentação do ecossistema Sophisticated.
Ele depende da mochila e do sistema de sede existente; não cria uma barra própria. O runtime instalado é `0.1.8`.
## Ecliptic Seasons: MultiMod Patch — 0.32.0-beta
`Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.0-beta.jar`
**MultiMod Patch** adiciona integrações em código para que outros sistemas reconheçam corretamente o calendário e as condições sazonais do Ecliptic Seasons. No stack atual, a cobertura pode atingir sistemas como **Cold Sweat, Dynamic Trees, MineColonies, JourneyMap** e outros mods suportados pela versão instalada.
Ele não cria estações novas: traduz o estado sazonal do Ecliptic Seasons para APIs e mecânicas de outros mods. A release instalada é `0.32.0-beta` para NeoForge 1.21.1.
## Ecliptic Seasons: Bundles — 0.18.0
`EclipticSeasons-Bundles-0.18.0.jar`
**Ecliptic Seasons: Bundles** complementa o patch em uma camada de **datapacks e resource packs**. Ele fornece dados sazonais para crops, vegetação, biomas e conteúdo de mods suportados, permitindo que recursos externos recebam regras e representação coerentes com as estações.
Portanto Bundles e MultiMod Patch não são duplicatas: um concentra dados/recursos e o outro integra lógica em código. O runtime atual é `0.18.0`.
## Puddles & Floods — 1.1.5
`puddleflood-1.1.5+1.21.1-neoforge.jar`
**Puddles & Floods** adiciona puddles que surgem e acumulam durante chuva, podem se conectar em formas irregulares e fazem cursos d'água parecerem **transbordar visualmente suas margens**. As poças evaporam conforme as condições e podem usar a água do shader quando configurado.
Quantidade de cobertura, raio de geração ao redor do jogador, velocidade de coleta/evaporação e comportamento de conexão com água são configuráveis. A maior parte da camada visual pode funcionar no cliente; a build `1.1.5` é a release NeoForge 1.21.1 de 08/07/2026.
# 4. Alimentação, culinária e agricultura
## Farmer's Delight — 1.3.3
`FarmersDelight-1.21.1-1.3.3.jar`
**Farmer's Delight** é a base culinária de boa parte do stack alimentar. Ele amplia agricultura e cozinha com culturas e ingredientes próprios, utensílios, **Cutting Board**, **Cooking Pot**, Stove, containers e uma grande quantidade de refeições compostas.
O preparo é dividido em operações: ingredientes podem ser cortados com ferramentas específicas, cozidos em potes, assados ou combinados em pratos que restauram fome/saturação e podem fornecer efeitos. Isso cria uma cadeia culinária em que a forma de processar o ingrediente importa tanto quanto a receita final.
A versão instalada `1.3.3` é a release NeoForge 1.21.1 de 17/08/2026. O mod também funciona como plataforma para vários addons do pack, incluindo Alex's Delight, Ender's Delight, My Nether's Delight e Miner's Delight.
## Farmer's Delight: Extended — runtime 1.21.1-0.2.2
`farmersdelight_extended-1.21.1-0.2.2.jar`
**Farmer's Delight: Extended** adiciona receitas e alimentos pensados para preencher a área de interseção entre **Farmer's Delight e Create** sem introduzir novas crops ou novos mobs. O conteúdo parte de ingredientes já existentes e amplia as formas de transformá-los em pratos e produtos compatíveis com as cadeias culinárias do pack.
Seu papel é principalmente recipe/content expansion: ele aproveita a infraestrutura de cozinha já existente em vez de criar um segundo sistema culinário. O arquivo instalado é `farmersdelight_extended-1.21.1-0.2.2.jar`; a versão pública é `0.2.2`, enquanto o runtime preservado na modlist é `1.21.1-0.2.2`.
## Create: Garnished — 2.1.9.2
`garnished-2.1.9.2+1.21.1-neoforged.jar`
**Create: Garnished** é um addon culinário do Create centrado principalmente em **nuts/nozes e ingredientes derivados**. Ele acrescenta matérias-primas, alimentos e receitas próprias que podem ser produzidos por processos mecânicos, transformando esse conjunto de ingredientes em uma cadeia industrializada de cozinha.
O conteúdo possui integrações condicionais quando outros mods alimentares estão presentes e permanece distinto de Create: Food, Confectionery e Ratatouille porque traz seu próprio catálogo de materiais/receitas. A build atual do pack é `2.1.9.2` para NeoForge 1.21.1.
## Delightful Backport — 1.0
`Delightful-Backport-1.0-1.21.1-neoforge.jar`
**Delightful Backport** é uma bridge pequena e específica entre VanillaBackport e Farmer's Delight. Ela faz os **ovos azuis e marrons** trazidos pelo backport serem tratados como ingredientes equivalentes nas rotas culinárias do Farmer's Delight, em vez de permanecerem itens visualmente diferentes sem integração de cozinha.
Esses ovos podem participar de preparos como fried eggs usando smoker, furnace, campfire, Stove e Skillet. Portanto o mod não adiciona uma segunda árvore culinária: ele corrige a interoperabilidade de um ingrediente backportado. A build `1.0` para NeoForge 1.21.1 é publicada como alpha.
## Create Confectionery — runtime 1.1.3.
`create-confectionery1.21.1_v1.1.3b.jar`
**Create Confectionery** transforma confeitaria em uma cadeia produtiva ligada ao Create. Cacau pode ser processado em **Crushed Cocoa, Cocoa Butter e Cocoa Powder**, que servem de base para chocolate preto, branco e ruby, caramel e outros derivados.
O catálogo inclui gingerbread, marshmallows, chocolate candies, honey candy, hot chocolate e Full Chocolate Bars, além de alimentos glaceados. Alguns produtos possuem efeitos próprios; Hot Chocolate, por exemplo, concede regeneração, enquanto diferentes chocolates trabalham com efeitos como Stimulation, Rest, Saturation ou Speed.
A build instalada é `v1.1.3b`; o metadata runtime declara literalmente `1.1.3.`. Essa diferença entre filename/publicação e runtime permanece registrada.
## Ratatouille — 1.4.0
`create_ratatouille-1.21.1-1.4.0.jar`
**Ratatouille** adiciona máquinas próprias para agricultura e processamento de alimentos dentro da linguagem do Create. O **Oven** é um multiblock para cozinhar grandes lotes; o **Thresher** processa grãos como wheat e culturas compatíveis; a **Irrigation Tower** hidrata áreas amplas de farmland; e o **Spreader** acelera crops em área e pode estimular breeding de animais próximos.
O addon também trabalha com infraestrutura agrícola como Compost Tower e integra produção de campo ao restante da fábrica. Assim, não é apenas uma coleção de receitas: ele adiciona máquinas que mudam como plantio, processamento e criação animal podem ser automatizados. A ficha do Notion usa o nome canônico curto **Ratatouille**, mantido aqui.
## Create Slice & Dice — 4.3.3
`sliceanddice-4.3.3-neoforge.jar`
**Create Slice & Dice** traduz ações do Farmer's Delight para automação Create. O **Slicer** registra receitas de Cutting Board e executa o corte usando a ferramenta instalada na máquina; knives e axes são aceitos por padrão e a tag `sliceanddice:allowed_tools` permite ampliar os utensílios válidos.
Receitas do **Cooking Pot** podem ser executadas como heated mixing, e o **Sprinkler** usa fluidos bombeados para produzir efeitos na área: água simula chuva, lava causa fogo, poções aplicam seus efeitos e Liquid Fertilizer age como bonemeal sobre cultivos. A build `4.3.3` corrige o comportamento de potion sprinklers e expõe API para providers adicionais.
## Create: Food — 2.7.1
`createfood-neoforge-1.21.1-2.7.1.jar`
**Create: Food** é uma expansão culinária ampla construída para processamento mecânico. Ela acrescenta muitos ingredientes, alimentos e produtos intermediários cujas receitas podem atravessar mixing, pressing, filling e outras operações do Create, criando linhas alimentares completas dentro da fábrica.
O addon possui integrações condicionais com outros mods de comida. Na linha `2.7.1`, esse conteúdo cross-mod passou a ser controlado por **listas de configuração**, permitindo habilitar ou desabilitar famílias de compatibilidade sem tratar todo o catálogo externo como obrigatório. A build instalada é a release NeoForge 1.21.1 `2.7.1`.
## Create: Fishery Industry — 5.1.1
`createfisheryindustry-5.1.1.jar`
**Create: Fishery Industry** transforma recursos aquáticos em uma cadeia completa de obtenção e processamento. O addon inclui **pesca automatizada, mergulho e captura de criaturas**, permitindo que a produção comece no próprio ambiente aquático em vez de depender apenas da fishing rod manual.
Os produtos obtidos podem seguir para processamento de alimentos e outros recursos e então entrar na logística do Create. Assim, sua função abrange aquisição, captura, exploração subaquática e tratamento industrial dos resultados, e não apenas uma receita automática de peixe. A build `5.1.1` é a release NeoForge 1.21.1 instalada.
## Cuisine Delight — 1.2.10
`cuisinedelight-1.2.10.jar`
**Cuisine Delight** troca a lógica de uma lista fechada de receitas por um sistema de **composição livre de pratos**. A proposta oficial é permitir que o jogador cozinhe seu próprio prato sem depender de uma receita predefinida, escolhendo e combinando ingredientes no processo culinário.
Isso faz quantidade, composição e escolha dos ingredientes participarem diretamente da comida produzida, tornando o preparo mais próximo de um sistema de cooking do que de crafting convencional. No pack, Create: Arm-made Cuisine adiciona automação específica para essa cozinha. A build `1.2.10` é a release NeoForge 1.21.1 atual.
## Create: Arm-made Cuisine — 1.0.0
`create_cuisine-1.0.0-mc1.21.1-neoforge.jar`
**Create: Arm-made Cuisine** é a bridge específica entre Create e Cuisine Delight. Ela permite que **Mechanical Arms** operem a Cuisine Skillet: inserir ingredientes, executar a etapa de mexer/preparar e retirar ou empratar o resultado por automação.
O addon não cria uma segunda cozinha nem substitui Cuisine Delight; ele expõe suas operações manuais à lógica cinética do Create. A build instalada é `1.0.0` para NeoForge 1.21.1 e depende de Create + Cuisine Delight 1.2.10.
## Create: Central Kitchen — 2.6.0
`create-central-kitchen-2.6.0.jar`
**Create: Central Kitchen** é uma camada ampla de integração entre Create, Farmer's Delight e outros mods culinários suportados. Ele converte operações de cozinha em processos compatíveis com **mixing, heating, filling e automação cinética**, permitindo montar linhas industriais para receitas que originalmente dependiam de estações culinárias manuais.
As integrações são condicionais aos mods detectados, portanto o addon funciona como uma infraestrutura de interoperabilidade culinária e não como um novo catálogo independente de alimentos. O runtime instalado é `2.6.0`.
## Butchercraft — 2.6.5
`butchercraft-2.6.5.jar`
**Butchercraft** adiciona um sistema de açougue semi-realista que acompanha o animal do abate ao preparo. Em vez de a criatura desaparecer deixando poucos drops, o mod permite aproveitar muito mais do corpo por uma sequência de **slaughter, suspensão e desmonte**.
O **Butcher Knife** é usado para abater e cortar; o **Meathook** permite pendurar carcaças, especialmente animais maiores; o **Butcher Block** processa animais menores e cortes; e o **Meat Grinder** transforma carne em mince e também funciona como sausage stuffer. O resultado é uma variedade maior de tipos de carne e subprodutos que podem seguir para cozinha.
As tabelas de butcher knife, meathook e butcher block são extensíveis por datapack/loot tables. A build `2.6.5` é a release NeoForge 1.21.1 instalada.
## Alex's Delight — 1.6
`alexsdelight-1.6.jar`
**Alex's Delight** é a integração alimentar entre **Alex's Mobs e Farmer's Delight**. Ela transforma drops e recursos das criaturas de Alex's Mobs em ingredientes e pratos utilizáveis pelo sistema culinário do Farmer's Delight, fazendo a fauna ter também valor gastronômico.
A função é conectar dois catálogos já existentes: as criaturas continuam pertencendo ao Alex's Mobs e os utensílios/mecânicas de cozinha continuam vindo do Farmer's Delight, enquanto o addon fornece os alimentos e receitas cruzadas. A versão `1.6` é a release NeoForge 1.21.1 atual.
## Ender's Delight — 1.3.1
`endersdelight-1.3.1.jar`
**Ender's Delight** leva a cozinha do Farmer's Delight para o **End**, transformando recursos da dimensão e drops de suas criaturas em ingredientes próprios. Ender Pearls e Chorus Fruits entram em novas receitas, enquanto Endermen, Shulkers e Endermites passam a fornecer materiais culinários adicionais.
A documentação cita ingredientes como **Ender Sight**, proveniente de Endermen, **Shulker Mollusk**, obtido do interior de Shulkers, e **Mite Crust**, derivado de Endermites. Esses recursos são usados em pratos temáticos, dando à exploração do End uma cadeia alimentar própria em vez de apenas mais refeições com ingredientes do Overworld.
A build `1.3.1` é a release NeoForge 1.21.1 publicada em 03/08/2026.
## My Nether's Delight — 1.10.4
`MyNethersDelight-1.21.1-1.10.4.jar`
**My Nether's Delight** é uma expansão extensa do Farmer's Delight voltada ao **Nether**. Hoglins fornecem cortes que viram sausages, chops, burgers, stews e Stuffed Hoglin; Striders entram em soups, tarts, stroganoff e Striderloaf; Ghasts participam de preparos próprios; e **Bullet Peppers** alimentam uma linha de comidas extremamente picantes.
O mod adiciona o **Blazier**, uma estação de cozinha com quatro níveis de calor que pode atuar em processos equivalentes a campfire cooking, smoking, baking e smelting. O sistema de **Pungency** faz comidas picantes reagirem à proximidade de fontes de calor: sem proteção elas podem causar dano, enquanto jogadores protegidos contra fogo podem receber regeneração.
Há também agricultura própria do Nether, com Powdery Cannon/Cane, Bullet Pepper, **Resurgent Soil/Farmland**, Fungus Colonies e materiais de construção/cozinha. A build `1.10.4` é a release NeoForge 1.21.1 instalada.
## Miner's Delight — 1.4.5
`minersdelight-1.21.1-1.4.5.jar`
**Miner's Delight** é o addon do Farmer's Delight voltado à **mineração e ao ambiente subterrâneo**. Ele adiciona ingredientes, alimentos e preparos temáticos que aproveitam recursos encontrados durante exploração de cavernas, fazendo a rotina do minerador alimentar uma linha culinária própria.
O mod segue as ferramentas e filosofia do Farmer's Delight em vez de criar um sistema de cozinha separado. A build `1.4.5` é a release NeoForge 1.21.1 atual, publicada em 29/04/2026.
## Expanded Delight — 0.1.4
`expandeddelight-0.1.4.jar`
**Expanded Delight** amplia o Farmer's Delight com **ingredientes, crops, alimentos e receitas adicionais** que utilizam a mesma infraestrutura culinária do mod-base. A função é aumentar o catálogo e as combinações disponíveis sem introduzir um sistema de cozinha concorrente.
A build instalada é `0.1.4`. O conteúdo compartilha o domínio culinário de outros addons, mas seus ingredientes e receitas permanecem próprios.
## Dungeon's Delight — 1.5.0
`neoforge-dungeonsdelight-1.21.1-1.5.0.jar`
**Dungeon's Delight** conecta culinária a exploração e combate ao permitir transformar **monstros e recursos de dungeons em pratos, equipamentos e efeitos próprios**. Em vez de apenas adicionar receitas domésticas, ele dá valor culinário a drops hostis e cria uma linha temática de "monstrous treats".
A release `1.5.0` exige Farmer's Delight 1.3+ e NeoForge 21.1.219+; o stack atual atende esses mínimos. A versão também ajusta efeitos e equipamentos ligados ao conteúdo de dungeon.
# 5. Ecossistema [Let's Do]
## Farm & Charm — 1.1.23
`letsdo-farm_and_charm-neoforge-1.1.23.jar`
**[Let's Do] Farm & Charm** é o núcleo agrícola/rural de parte do ecossistema Let's Do. Ele adiciona **novos grãos e frutas**, blocos funcionais de fazenda e uma cozinha de estilo farmhouse, fazendo cultivo e transformação dos produtos ocorrerem em várias etapas.
Entre os equipamentos estão **Mincer**, Cooking Pot/Bowls e Stove. A linha recente permite, por exemplo, alimentar e acender o Stove manualmente, trabalhar ingredientes em bowls que precisam ser mexidos e processar carnes pelo Mincer. Assim, a fazenda fornece matérias-primas que seguem para equipamentos culinários próprios, e não apenas novas crops decorativas.
A build `1.1.23` é a release NeoForge 1.21.1 publicada em 30/07/2026 e funciona como base compatível para Bakery e Brewery instalados.
## Bakery — 2.1.6
`letsdo-bakery-neoforge-2.1.6.jar`
**[Let's Do] Bakery** expande a produção de pães e confeitaria com **variações de bread, cupcakes e cakes**, muitos deles consumíveis e também colocáveis no mundo. O conteúdo trabalha com ingredientes e etapas de padaria próprios, além de containers e blocos temáticos usados no preparo/apresentação.
A edição atual é a linha com **compatibilidade aprimorada com Farm & Charm**, compartilhando ingredientes e infraestrutura do ecossistema Let's Do. A build `2.1.6` é a release NeoForge 1.21.1 instalada e inclui correções para recipientes/remainders de receitas e blocos como trays.
## Brewery — 2.1.9
`letsdo-brewery-neoforge-2.1.9.jar`
**[Let's Do] Brewery** adiciona o ramo de **fermentação e produção de bebidas** do ecossistema Let's Do. Ingredientes agrícolas podem seguir para infraestrutura própria de brewery e resultar em diferentes bebidas, fazendo cultivo e processamento alimentar também alimentarem uma cadeia de fermentação.
A edição instalada é especificamente a linha **Brewery - Farm & Charm Compat**, criada para melhorar a interoperabilidade com Farm & Charm em 1.21.1. O JAR `letsdo-brewery-neoforge-2.1.9.jar` é a release NeoForge publicada em 28/03/2026.
## Vinery — 1.5.3
`letsdo-vinery-neoforge-1.5.3.jar`
**[Let's Do] Vinery** cria uma cadeia própria de **viticultura e produção de vinho**. O jogador cultiva uvas, processa a colheita e utiliza equipamentos de vinícola para transformar os frutos em bebidas, fazendo agricultura e fermentação formarem uma progressão temática separada das cervejas do Brewery e dos chás do HerbalBrews.
Além da fabricação das bebidas, o mod inclui blocos e decoração voltados a construir uma **vinícola completa**, fazendo a infraestrutura de produção também participar da ambientação. A build instalada `1.5.3` é a linha NeoForge 1.21.1 publicada em 28/01/2026 e está classificada como beta; isso descreve maturidade da versão, não incerteza sobre o JAR.
## HerbalBrews — 1.1.3
`letsdo-herbalbrews-neoforge-1.1.3.jar`
**[Let's Do] HerbalBrews** adiciona uma cadeia de **chá e café** baseada em coleta de plantas e processamento dos ingredientes. Folhas e ervas precisam ser obtidas e **secas** antes de entrarem nos sistemas de brewing, separando preparo da matéria-prima e infusão final em etapas próprias.
O mod também possui decoração temática para espaços de chá/café, mas a função principal é jogável: gathering → drying → brewing. Ele não é equivalente a Brewery ou Vinery, que trabalham com outras matérias-primas e processos. A build `1.1.3` é a release NeoForge 1.21.1 atual, publicada em 21/02/2026.
## Meadow — 1.4.8
`letsdo-meadow-neoforge-1.21.1-1.4.8.jar`
**[Let's Do] Meadow** combina worldgen rural/alpino com **manejo de gado e produção de queijo**. O jogador encontra paisagens de pastagem, flores e estruturas temáticas, cuida do rebanho, ordenha vacas e utiliza o leite em etapas de fabricação e envelhecimento de queijo.
A fauna bovina também é ampliada: a documentação atual apresenta **nove raças de vaca**, incluindo Highland Cattle. O mod acrescenta culturas como oat, materiais naturais, mobiliário e blocos próprios, portanto não é apenas um pacote culinário nem somente worldgen. A build `1.4.8` é a release NeoForge 1.21.1 instalada.
## Candlelight — 2.1.12
`letsdo-candlelight-neoforge-2.1.12.jar`
**[Let's Do] Candlelight** amplia o lado de **refeições servidas e jantar** do ecossistema Let's Do. Mesas, pratos, velas e mobiliário formam espaços de restaurante/jantar enquanto alimentos próprios utilizam essa infraestrutura para apresentação e consumo, fazendo decoração e culinária funcionarem juntas.
A edição instalada é especificamente a variante **Candlelight - Farm & Charm Compat**, criada para compartilhar melhor ingredientes e regras com Farm & Charm em Minecraft 1.21.1. Assim, Candlelight funciona como extensão gastronômica/ambiental do stack rural, não como substituto do sistema-base de agricultura. O JAR `2.1.12` é a release NeoForge de 13/03/2026.
## Wilder Nature — 1.1.5
`letsdo-wildernature-neoforge-1.1.5.jar`
**[Let's Do] Wilder Nature** é a linha de fauna do ecossistema Let's Do. A versão atual reintroduz os animais conhecidos das versões anteriores com **modelos e conteúdo retrabalhados**, distribuindo novas criaturas pelo mundo e associando a elas recursos de exploração, alimentação e ambientação natural.
O projeto é classificado simultaneamente como Mobs, World Gen, Food e Adventure/RPG, refletindo que os animais não funcionam apenas como decoração. A build `1.1.5` é a release NeoForge 1.21.1 publicada em 30/07/2026.
## AnimalHusbandry — 0.4.0
`AnimalHusbandry-neoforge-0.4.0.jar`
**AnimalHusbandry** transforma reprodução vanilla em um sistema persistente de **manejo, saúde e genética**. Animais possuem traços que podem ser transmitidos aos descendentes, o sistema acompanha linhagem e aplica consequências de **inbreeding**, e a reprodução utiliza períodos de gestação específicos por espécie em vez de gerar filhotes imediatamente.
A camada de cuidado inclui saúde/doenças e interação com recursos de curral. Na versão `0.4.0`, galinhas ganharam **ninhos e incubação de ovos**, e a IA/pathfinding foi ampliada para que animais consigam localizar e utilizar recursos de manejo acessíveis no recinto. Isso permite que layout do curral e disponibilidade de infraestrutura façam parte do sistema.
O mod é, portanto, uma simulação própria de husbandry/genética e não apenas uma expansão de modelos de animais. A build NeoForge 1.21.1 instalada é `0.4.0` e depende de Architectury API.
# 6. Fauna, inimigos e bosses
## Born in Chaos — 1.7.6
`born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar`
**Born in Chaos** é uma expansão de hostilidade e aventura construída em torno de **inimigos com regras próprias**, não apenas variantes com mais vida ou dano. O roster inclui undead, spirits, arthropods e outras criaturas com comportamentos que exigem respostas diferentes: alguns inimigos explodem ou se transformam quando enfraquecidos, outros bloqueiam ataques e possuem counters específicos, alguns aplicam debuffs ou fortalecem criaturas próximas, enquanto summoners conseguem produzir minions durante o combate.
A dificuldade também pode evoluir conforme o mundo progride. O **Nightmare Stalker**, por exemplo, possui comportamento ligado à idade do mundo e ganha capacidades adicionais em estágios posteriores; o sistema de **Naughtiness** associado ao Krampus observa determinadas ações do jogador e participa de seus encontros e summons. O mod ainda adiciona minibosses/bosses, eventos sazonais, estruturas, armas, armaduras, blocos e achievements, fazendo exploração e equipamento participarem da mesma expansão de ameaça.
Os spawns utilizam tipos de bioma em vez de depender apenas de uma lista rígida de biomas vanilla, permitindo que as criaturas apareçam também em ambientes modded compatíveis. A build instalada é `1.7.6` para NeoForge 1.21.1 e utiliza GeckoLib para a camada de entidades/animações.