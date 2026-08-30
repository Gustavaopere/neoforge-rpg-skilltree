<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 14. Compatibilidades e infraestrutura técnica

## Lychee Tweaker — runtime 6.5.4+neoforge

`Lychee-1.21.1-NeoForge-6.5.4.jar`
**Lychee Tweaker** é um framework **data-driven de interações e receitas in-world**. Datapacks/JSON podem definir processos como item-on-block, interação com fogo ou fluidos, lightning, explosions, crushing, dripstone e outros eventos do mundo sem exigir criar um mod Java específico para cada receita.
Ele funciona como infraestrutura de scripting/configuração para conteúdos que precisam representar transformação física no mundo, especialmente útil quando uma receita não cabe no crafting table ou em uma recipe type convencional. A build instalada é `6.5.4`, com metadata runtime `6.5.4+neoforge`, e permanece classificada como beta oficial para NeoForge 1.21.1.

## Pondus Inventory — 0.15-Beta

`pondus_inventory_fi-0.15-Beta.jar`
**Pondus Inventory** estende a física de **Sable** para o conteúdo armazenado dentro dos ships/sublevels. Itens e blocos em inventários e **fluidos expostos por fluid handlers** passam a contribuir para a massa física total da estrutura, de modo que carga transportada deixe de ser invisível para o modelo de peso.
O sistema permite multiplicadores globais e ajustes por namespace/mod para massas de itens, blocos e fluidos. Assim, sua função é complementar a física/massa do Sable; ele não cria inventários, veículos ou armazenamento próprios. A build instalada é `0.15-Beta`.

## Create: Cold Sweat — 1.1.2

`create_cold_sweat-1.1.2.jar`
**Create: Cold Sweat** é a bridge térmica entre Create e Cold Sweat. Ela adiciona **temperaturas de fluidos**, fórmulas térmicas específicas para objetos Create e interações que não podem ser reproduzidas completamente apenas por datapack ou KubeJS.
Boilers, pipes, tanks e outras estruturas podem atuar como fontes ou sumidouros de temperatura conforme configuração; isso permite desde instalações industriais perigosamente quentes até pisos aquecidos por tubulações. A build 1.1.2 para NeoForge 1.21.1 também inclui correção específica de inicialização em dedicated server.

## Almost Unified — 1.21.1-1.4.2

`almostunified-neoforge-1.21.1-1.4.2.jar`
**Almost Unified** é uma infraestrutura de **unificação de materiais e receitas**. Quando vários mods registram equivalentes do mesmo recurso — por exemplo, diferentes ingots, nuggets, plates ou dusts associados às mesmas tags — o sistema escolhe uma variante preferida e reescreve receitas compatíveis para reduzir a fragmentação de itens equivalentes.
A unificação é orientada por tags e prioridades configuráveis, portanto o mod não simplesmente apaga materiais: ele ajusta quais variantes aparecem como saída/ingrediente canônico nos fluxos de crafting e processamento. Isso é especialmente relevante em um pack com muitas cadeias metalúrgicas e industriais.

## Create: Addon Compatibility — 1.0.0

`createaddoncompatibility-neoforge-1.21.1-1.0.0.jar`
**Create: Addon Compatibility** é uma camada específica de **unificação entre addons do Create**. Ela utiliza regras de compatibilidade para reduzir materiais, fluidos e receitas duplicadas produzidas por extensões industriais que representam os mesmos recursos de formas diferentes.
O mod trabalha em conjunto com **Almost Unified**, mas com conhecimento específico do ecossistema Create. Assim, Almost Unified fornece o mecanismo genérico de unificação e este addon fornece regras/ajustes voltados às extensões Create presentes no pack.

## Create: Dynamic Village — 0.9

`dynamicvillage-0.9-1.21.1.jar`
**Create: Dynamic Village** leva Create para **vilas vanilla** por meio de estruturas, profissões, job sites, trades e loot próprios. Entre as profissões documentadas estão **Mechanical Engineer** e **Hydraulic Engineer**, com economia ligada a componentes e materiais do Create.
A versão `0.9` revisa o interior das 20 construções do addon, corrige salas sem iluminação e chests ausentes e remove o antigo anel de ar ao redor dos prédios. A geração passou a ser ajustada por bioma e recebeu opções de configuração para **Biome Density** e **Village Size**, controlando quantidade de prédios customizados, alcance e densidade dos settlements.

## Create: Pillagers Arise — 132.36

`create_pillagers_arise NeoForge 1.21.1-132.36.jar`
**Create: Pillagers Arise** leva a estética e a engenharia do Create para estruturas hostis geradas no mundo. O projeto adiciona **10 novas estruturas** defendidas por pillagers, incluindo fortalezas e outposts mecanizados com contraptions, defesas automatizadas, armadilhas e loot próprios.
A proposta é transformar encontros com pillagers em exploração de instalações industriais hostis: Create aparece integrado ao próprio layout e às defesas dessas bases, não apenas como decoração aplicada a uma estrutura vanilla. A versão `132.36` é a release NeoForge 1.21.1 atual do projeto.

## CreateColonies — 2.0.6

`createcolonies-2.0.6.jar`
**CreateColonies** integra **Create, Structurize e MineColonies** para que blueprints da colônia consigam construir estruturas que utilizam componentes Create. A bridge adapta regras de placement e configuração de blocos que não podem ser tratados como cubos estáticos comuns.
Entre os elementos atendidos estão rails, belts, bogeys, train stations, deployers e outros componentes cuja orientação, montagem ou estado precisa ser reconstruído corretamente pelo builder da colônia. O objetivo é permitir que infraestrutura Create faça parte de schematics/blueprints MineColonies de maneira funcional.

## Create Aero + Connected Fluid Vessel Compat — 1.0.0

`aeroconnectedfluidvessel-1.0.0.jar`
**Create Aero + Connected FluidVessel Compat** corrige a interação entre **Create Aeronautics/Sable e os FluidVessels do Create: Connected**. Sem a bridge, steam vents do Aeronautics não reconhecem o FluidVessel como boiler válido quando ele está integrado a uma estrutura física.
A integração permite usar FluidVessels como boilers para produzir steam em airships, sincroniza corretamente sinais de redstone entre vents ligados ao mesmo vessel e corrige um caso em que shafts dos steam vents podiam se separar em physics entities independentes. A build instalada é `1.0.0`.

## Aero Copycats — runtime 1.1.0 / arquivo 1.1.1

`aerocopycats-1.1.1.jar`
**Aero Copycats** atribui **massa física (kpg)** apropriada aos blocos do Copycats+ quando usados em Sable/Create Aeronautics. Em vez de todas as formas Copycat receberem um peso genérico, o addon define valores para o catálogo completo e escala a massa em geometrias como Copycat Layers conforme o número de camadas aumenta.
Essa função é física, não aerodinâmica: `Create Aeronautics: Copycat Wing` registra formas Copycat como superfícies que geram lift; Aero Copycats define quanto essas peças pesam no solver Sable. O filename atual é `1.1.1`, enquanto a ficha/runtime local declara `1.1.0`; a divergência permanece explícita.

## Aeronautics Compat — 1.1.3

`aeronauticscompat-1.1.3.jar`
**AeronauticsCompat** é um pacote geral de **patches de compatibilidade para Sable/Create Aeronautics**. Ele adapta mods que originalmente assumem um mundo estático para reconhecer corretamente posição, câmera, interação ou estado de blocos/entidades dentro de physics ships.
A linha atual acumulou correções para diferentes integrações — incluindo suporte de câmera, Storage Drawers e, em versões anteriores, Immersive Paintings, furniture, Sleep Tight e casos de Cobblemon. A build instalada `1.1.3` é a release NeoForge 1.21.1 atual e requer Sable.

## Create: Smart Bounds — 1.0.0

`smart_bounds-1.0.0.jar`
**Create: Smart Bounds** é uma otimização **client-side de render bounds** para block entities do Create. Vários componentes usam bounding boxes de renderização muito maiores que o conteúdo realmente visível; isso pode manter block entities sendo renderizadas mesmo quando estão fora da tela. O addon substitui esses bounds por limites mais adequados em componentes suportados.
A cobertura documentada inclui **Mechanical Arms, Belts, Chain Conveyors, Factory Panels, PSI/Deployers, Rollers e Frog Ports**. O mod também refaz parte do cache dessas bounding boxes: belts, por exemplo, deixam de recalcular o render bound a cada tick e passam a atualizá-lo apenas quando o estado relevante realmente muda.
Ele não altera recipes, stress ou ticking das máquinas. Seu objetivo é reduzir trabalho de renderização, especialmente em fábricas grandes e contraptions densas. A build `1.0.0` é a única release NeoForge 1.21.1 publicada.

## CreateBetterFps — 1.1.4

`createbetterfps-1.21.1-1.1.4.jar`
**CreateBetterFps** é uma otimização **client-side** direcionada ao custo de renderização do Create. O alvo principal são cenas com grande quantidade de componentes, contraptions e efeitos visuais, especialmente quando shaders tornam a renderização do ecossistema mais pesada.
Ele não modifica receitas ou progressão e não substitui Create: Lazy Tick: BetterFps atua principalmente no lado visual/render; Lazy Tick reduz trabalho de atualização lógica de blocos e máquinas.

## Create Lazy Tick — 2.6.25-6.0.10

`CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar`
**Create: Lazy Tick** reduz o custo lógico de fábricas Create diminuindo ou reorganizando atualizações de componentes que não precisam executar trabalho completo a cada tick. Belts, funnels, chutes, depots e outros elementos podem reaproveitar cache ou atualizar em frequência menor quando o estado permite.
A build `2.6.25-6.0.10` é especificamente alinhada ao Create 6.0.10 e inclui correções recentes para **Mechanical Arms, Basins e ammo containers do Create Big Cannons**, além de ajustes de regras/cache. O foco é CPU/tick time de fábricas grandes, distinto da otimização de renderização do CreateBetterFps.

## Create JEI Compat — 1.0.3

`createjeicompat-1.0.3.jar`
**Create JEI Compat** melhora a representação de processos Create no **JEI**, especialmente **Sequenced Assembly** longas. A integração acrescenta paginação e controles de navegação quando uma sequência possui muitas etapas, mantendo ingredientes e fases legíveis e pesquisáveis sem comprimir todo o processo em uma única tela.
A versão `1.0.3` também inclui controles por teclado, atualização in-place do layout e suporte opcional ao stack EMI através de JEmi. Seu papel é de visualização/consulta de receitas, não alteração dos processos executados pelas máquinas.
