<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 6. Fauna, inimigos e bosses

## Born in Chaos — 1.7.6

`born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar`
**Born in Chaos** é uma expansão de hostilidade e aventura construída em torno de **inimigos com regras próprias**, não apenas variantes com mais vida ou dano. O roster inclui undead, spirits, arthropods e outras criaturas com comportamentos que exigem respostas diferentes: alguns inimigos explodem ou se transformam quando enfraquecidos, outros bloqueiam ataques e possuem counters específicos, alguns aplicam debuffs ou fortalecem criaturas próximas, enquanto summoners conseguem produzir minions durante o combate.
A dificuldade também pode evoluir conforme o mundo progride. O **Nightmare Stalker**, por exemplo, possui comportamento ligado à idade do mundo e ganha capacidades adicionais em estágios posteriores; o sistema de **Naughtiness** associado ao Krampus observa determinadas ações do jogador e participa de seus encontros e summons. O mod ainda adiciona minibosses/bosses, eventos sazonais, estruturas, armas, armaduras, blocos e achievements, fazendo exploração e equipamento participarem da mesma expansão de ameaça.
Os spawns utilizam tipos de bioma em vez de depender apenas de uma lista rígida de biomas vanilla, permitindo que as criaturas apareçam também em ambientes modded compatíveis. A build instalada é `1.7.6` para NeoForge 1.21.1 e utiliza GeckoLib para a camada de entidades/animações.

## Fungal Infection: Spore — 2.2.0j

`spore_1.21.1_2.2.0j_neo.jar`
**Fungal Infection: Spore** é um sistema de infestação fúngica hostil que trata a ameaça como um **ecossistema em expansão**. A Mycelium Infection pode contaminar hospedeiros adequados; criaturas abatidas ou tomadas pela infecção retornam como formas infectadas, que passam a buscar alimento, biomassa e novas oportunidades de propagação em vez de funcionarem como mobs isolados.
Os infectados possuem uma progressão própria: acumulam biomassa, **evoluem ou sofrem mutações para formas mais fortes** e podem atuar em hordas, chamar reforços e obedecer a uma hierarquia de criaturas. Estruturas biológicas como **Mounds** espalham a infecção por blocos e ajudam a transformar o ambiente, enquanto estágios mais avançados trabalham com biomassa, armadilhas e a reconstrução de uma **Hivemind**, tornando a expansão territorial parte central da mecânica.
A build instalada é `2.2.0j` para NeoForge 1.21.1. O addon **Spore: Infnexus** abaixo acrescenta um Nexus persistente sobre esse sistema-base; ele não substitui a lógica de infecção, evolução e hivemind do Spore.

## Spore: Infnexus — 2.0.4

`Infnexus-2.0.4-1.21.1.jar`
**Spore: Infnexus** acrescenta ao Fungal Infection: Spore um **Nexus of Infection**, núcleo central inicialmente invulnerável que transforma a infestação em uma ameaça territorial persistente. A partir dele, a corrupção se expande para fora em um raio crescente; na linha 2.0.4, o avanço não se limita mais a pintar biomassa sobre terreno existente: o Nexus **reconstrói o solo conquistado**, formando uma zona infectada com biomas distintos e mais conteúdo próprio dentro da área.
O avanço pode ser enfraquecido por ações contra o ecossistema Spore. A documentação atual cita **matar mobs infectados** e congelar a infecção usando **CDU units** como formas de reduzir sua força, ligando combate e contenção ambiental ao estado do Nexus. Isso cria um objetivo regional de longo prazo: enquanto o core persiste, o território continua sendo pressionado pela expansão.
A build instalada é `2.0.4` para NeoForge 1.21.1 e exige Spore 2.2 ou superior. Sua função é adicionar o núcleo territorial e a lógica de zona infectada; a infecção básica, evolução de criaturas, biomassa e hivemind continuam pertencendo ao mod-base.

## Legendary Monsters — runtime 1.21.1

`legendary_monsters-2.2.2 MC 1.21.1.jar`
**Legendary Monsters** adiciona uma camada de encontros de **mid e late game** formada por bosses, minibosses, estruturas próprias e equipamentos obtidos dessas lutas. A proposta é inserir desafios especiais no mundo sem substituir os mobs vanilla: estruturas e arenas levam a inimigos com padrões próprios e recompensas associadas.
O projeto também inclui armas e armaduras ligadas ao conteúdo dos bosses e continua recebendo ajustes de combate, spawn e estruturas. O arquivo instalado é a release pública **2.2.2 para NeoForge 1.21.1**, publicada em 23/08/2026; o metadata interno do JAR declara apenas `1.21.1` como runtime version, e por isso o guia mantém filename/publicação e runtime separados. A expansão mágica `Legendary Spellbooks 0.3.2` permanece documentada no Guia de Magia.

## Ice And Fire Community Edition — 2.1.1

`iceandfire-2.1.1.jar`
**IceAndFire Community Edition** é a continuação comunitária do ecossistema Ice and Fire para versões modernas. O mod adiciona **dragões elementais, criaturas míticas, ninhos/covis, estruturas, materiais, armas, armaduras e o Dragon Forge**, formando uma linha extensa de exploração, combate e criação de criaturas.
Dragões possuem crescimento, domesticação, montaria e recursos próprios; outras criaturas míticas também têm drops e equipamentos associados. O fork comunitário inclui rewrites, otimizações e conteúdo novo sobre a base clássica. A build `2.1.1` é a release NeoForge 1.21.1 instalada, publicada em 19/08/2026, e serve de mod-base para Dragon Care, Dread Land e bridges específicas presentes no pack.

## Ice And Fire: Dragon Care — runtime 1.3.0 - 1.21.1v

`Ice and Fire - Dragon Care-1.3.0 - 1.21.1v.jar`
**Dragon Care** transforma dragões domesticados do Ice And Fire CE em criaturas de manejo prolongado. O addon acrescenta **bonding**, alimentação e rotinas de cuidado, ferramentas veterinárias, limpeza e formas de obter determinados recursos sem matar o animal.
A proposta é deslocar o dragão domesticado de uma simples montaria/arma para um companheiro que exige manutenção e relação contínua com o jogador. A build `1.3.0 - 1.21.1v` é a release NeoForge 1.21.1 instalada, publicada em 03/08/2026, e depende diretamente do Ice And Fire Community Edition.

## Ice And Fire: Dread Land — 0.1.2 beta

`iceandfire_dreadland-0.1.2.jar`
**Ice And Fire: Dread Land** adiciona uma dimensão de aventura própria ligada ao Ice And Fire CE. A Dread Land é dividida em **quatro regiões/reinos temáticos — Iceland, Fireland, Lightning e Dreadland —**, com estruturas, dungeons, chaves e uma progressão baseada em acesso a áreas e portal.
O addon amplia o conteúdo de exploração do mod-base em vez de apenas adicionar mais um mob. A build `0.1.2` é beta NeoForge 1.21.1 publicada em 22/08/2026; essa classificação representa maturidade/WIP do conteúdo, não dúvida sobre a presença ou identidade do JAR instalado.

## Alex's Mobs Continued — 2.1.8

`alexsmobs-2.1.8-neoforge+1.21.1.jar`
**Alex's Mobs Continued** mantém o conteúdo de Alex's Mobs disponível nas versões modernas sem redesenhar ou rebalancear o roster original. O projeto declara cerca de **116 animais e monstros**, cada um com modelo, animação, IA e comportamento próprios, distribuídos por diferentes ambientes.
As criaturas não funcionam apenas como decoração: há relações ecológicas, métodos específicos de tame/breeding, montarias, capturas, drops, equipamentos e itens utilitários associados. O **Animal Dictionary** continua servindo como referência interna para descobrir comportamento e interações. A build `2.1.8` é a versão atualmente instalada para NeoForge 1.21.1.

## Mowzie's Mobs — 1.8.2

`mowziesmobs-1.21.1-1.8.2.jar`
**Mowzie's Mobs** adiciona criaturas e bosses com forte foco em **IA própria, animações, padrões de ataque e encontros roteirizados**. Em vez de simplesmente aumentar atributos de mobs vanilla, cada criatura importante possui identidade mecânica própria, incluindo ataques telegráficos, fases, arenas/estruturas e recompensas associadas.
A linha 1.8 introduziu criaturas como Bilokosa/Bilokosa Howler e continuou refinando bosses existentes, armas e summons. A build `1.8.2` é a release NeoForge 1.21.1 instalada. O conteúdo cruza o domínio de outros mods de bosses, mas seus encontros, habilidades e equipamentos são próprios.

## L_Ender's Cataclysm — 3.33

`L_Ender's Cataclysm 1.21.1-3.33.jar`
**L_Ender's Cataclysm** é um grande mod de aventura/endgame centrado em **estruturas míticas, dungeons, inimigos de elite, bosses multiataque e equipamentos poderosos**. O jogador encontra conteúdo distribuído pelo mundo e por dimensões já existentes, enfrenta estruturas hostis e progride até lutas de boss com padrões e fases próprias.
Os drops alimentam armas, armaduras e materiais de alto nível, fazendo o mod funcionar como uma camada de progressão de combate para personagens já desenvolvidos. A versão `3.33` é a release NeoForge 1.21.1 instalada, publicada em 22/08/2026. Addons como Mowzie's Cataclysm e bridges Reliquified permanecem expansões separadas do mod-base.

## Mowzie's Cataclysm — 1.2.2

`mowzies_cataclysm-1.2.2.jar`
**Mowzie's Cataclysm** é uma integração de conteúdo entre Mowzie's Mobs e L_Ender's Cataclysm. A implementação atual adiciona **quatro Eyes** usados para localizar bosses de Mowzie's Mobs por uma lógica semelhante aos itens de localização ligados aos bosses de Cataclysm, criando continuidade de exploração entre os dois ecossistemas.
O projeto também prevê conteúdo adicional como boss music discs, mas o guia registra apenas o que a linha atual documenta como implementado. A build `1.2.2` é a release NeoForge 1.21.1 instalada.

## Companions! — 1.3.2

`companions-neoforge-1.21.1-1.3.2.jar`
**Companions!** adiciona criaturas domesticáveis voltadas a exploração e combate, cada uma com **habilidades próprias**, além de mobs hostis, armas e um boss. Companheiros possuem estados de comportamento — wandering, sitting e following — e podem ser curados com Small/Great Essence obtidas de inimigos hostis; summons gerados por outros companions seguem regras separadas.
O sistema é configurável e transforma aliados em uma camada jogável própria, não apenas pets cosméticos. A build `1.3.2` é a release NeoForge 1.21.1 instalada, publicada em 08/08/2026.

## Enhanced AI — 4.2.2.1

`enhancedai-4.2.2.1.jar`
**Enhanced AI** altera capacidades e decisões de mobs hostis para tornar perseguição e combate menos passivos. O projeto é conhecido por comportamentos como **creepers abrindo caminho/breaching, zombies minerando e skeletons atacando de forma mais eficiente**, além de módulos configuráveis de perseguição, interação com o ambiente e patrulha.
A intensidade dessas mudanças pode ser controlada por configuração. O mod altera comportamento de gameplay; não deve ser confundido com `AI-Improvements`, cujo papel principal é reduzir custo de processamento de IA. A build `4.2.2.1` é a release NeoForge 1.21.1 instalada.

## Nyf's Spiders — 3.0.1

`nyfsspiders-neoforge-1.21.1-3.0.1.jar`
**Nyf's Spiders** substitui a navegação limitada das aranhas por uma locomoção de escalada muito mais contínua. Aranhas passam a reconhecer **paredes, superfícies verticais e transições de orientação** como caminhos utilizáveis, podendo perseguir jogadores por geometrias que a IA vanilla não atravessaria adequadamente.
A linha 3.0 migrou a implementação para **Advanced Wall Climber API**, mantendo o objetivo de fazer spiders se moverem como criaturas realmente capazes de aderir a superfícies. O JAR `3.0.1` é a release NeoForge 1.21.1 instalada.

## Ragdoll mob corpses — 1.1.5

`mob_ragdoll_corpse-1.1.5.jar`
**Ragdoll mob corpses** faz criaturas mortas deixarem corpos físicos ragdoll em vez de simplesmente desaparecerem após a animação de morte. Os cadáveres entram no mesmo tipo de apresentação física do stack Sable e podem permanecer como objetos pós-morte interativos.
O projeto também descreve usos como carregar presas/companheiros e enterrar companheiros, fazendo o corpo persistente participar da ambientação e da interação após a morte. A build instalada é `1.1.5` para NeoForge 1.21.1.
