<!-- Snapshot canônico do Notion: GUIA COMPLETO — Mods de Tecnologia | NeoForge 1.21.1
Fonte: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff
Parte 3/3. Continuação de part-02.md. -->

`createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar`
**Create: Enchantable Machinery** permite aplicar **encantamentos vanilla diretamente a máquinas/blocos Create compatíveis**. Enchanted Spout, Mechanical Mixer, Plough, Mechanical Roller e outros componentes suportados recebem efeitos derivados dos enchantments aplicados, como interações específicas de Silk Touch e outras propriedades.
Isso é mecanicamente diferente do Enchantment Industry: aqui a própria máquina recebe enchantments e modifica seu comportamento; o outro mod industrializa XP e o processo de encantar itens.
## Ars Creo — 5.4.0
`ars_creo-1.21.1-5.4.0.jar`
**Ars Creo** é a integração estrutural entre **Ars Nouveau e Create**. Ela permite que componentes mágicos participem de contraptions e fluxos Create: Source, jars, turrets, Starbuncles e outros sistemas passam a reconhecer movimento e automação mecânica, enquanto displays/fluidos e interações específicas conectam as duas infraestruturas.
O resultado é compatibilidade mecânica real entre source automation e contraptions, não apenas receitas cruzadas. A build instalada é `5.4.0` para NeoForge 1.21.1.
## Ars Technica — 2.7.6
`ars_technica-1.21.1-2.7.6.jar`
**Ars Technica** cria uma camada de technomancy sobre Ars Nouveau/Create. Seus **glyphs** reproduzem operações industriais inspiradas em processos como crushing e pressing, permitindo que spellcraft execute transformações normalmente associadas às máquinas Create.
O addon também possui equipamentos próprios e o **Source Motor**, que converte Source em potência cinética, fazendo energia mágica alimentar diretamente uma rede mecânica. O escopo é maior que compatibilidade de receitas: magia passa a gerar movimento e a executar processos industriais.
## IronSable — 1.2.0
`ironsable-1.2.0.jar`
**IronSable** conecta **Iron's Spells 'n Spellbooks** à física Sable/Aeronautics. Spells que aplicam força deixam de ignorar physics objects: empurrões, puxões e efeitos de vento podem agir sobre contraptions e estruturas físicas móveis.
Na 1.2.0, Tempest's Grasp, Downburst e Maelstrom podem usar a escola **Wind** quando Wind's Spellbooks está instalado, e o addon expõe uma API pública de física para companion mods. Ele não adiciona um segundo sistema de spells ou de física; traduz efeitos mágicos para forças compreendidas pelo Sable.
## Create: Wizardry — 1.21.1-0.5.1-pre1
`create_wizardry-1.21.1-0.5.1-pre1.jar`
**Create: Wizardry** integra **Create e Iron's Spells 'n Spellbooks** por processamento, fluidos e spellcasting automatizado. Materiais/componentes mágicos entram em receitas mecânicas, enquanto mana e recursos arcanos passam a participar de máquinas e linhas produtivas.
Um dos elementos centrais é o **Blaze Caster**, que permite incorporar lançamento de spells à automação em vez de exigir exclusivamente um jogador conjurando manualmente. A build instalada é `1.21.1-0.5.1-pre1`; o sufixo pre-release é mantido como parte da identidade da versão.
## Apokinetics — 1.0.5
`apokinetics-1.0.5.jar`
**Create: Apokinetics** aplica a lógica de gems/affixes de Apotheosis/Apothic às máquinas do Create. Máquinas compatíveis podem receber **sockets** e **Machine Gems** que modificam propriedades industriais, transformando equipamento de loot em componentes de otimização da fábrica.
O addon inclui **Apokinetic Table**, **Kinetic Pylon** e ferramentas de diagnóstico/gerenciamento ligadas ao sistema, criando uma progressão própria de melhoria de máquinas em vez de apenas reconhecer itens Apotheosis em filtros.
## Apotheotic Creation — 2.0.0
`apotheoticcreation-2.0.0.jar`
**Apotheotic Creation** é uma bridge focada em **filtragem e identificação de propriedades Apotheosis/Apothic dentro do Create**. Ela permite que Attribute Filters e componentes relacionados reconheçam informações como raridade e affixes dos itens.
Seu papel é diferente de Apokinetics: Apotheotic Creation expõe metadados de loot ao sistema de filtros/logística; Apokinetics adiciona sockets e Machine Gems às próprias máquinas.
# 12. Addons Create de equipamentos, utilidades e construção
## Create Stuff 'N Additions — runtime 2.1.4.
`create-stuff-additions1.21.1_v2.1.4b.jar`
**Create Stuff 'N Additions** amplia o Create com **equipamentos, ferramentas e gadgets pessoais**. As peças podem aumentar atributos físicos como força/mining speed e fornecer formas de movimento como flutuar, saltar e voar.
Os equipamentos não compartilham um único combustível: conforme a família/material — brass, andesite ou copper — podem operar com **steam**, heat energy ou hydraulic energy, usando água e/ou combustível conforme o sistema. O addon também adiciona rotas Create para produzir recursos vanilla como chainmail armor, coral, netherrack, crying obsidian e magma cream por processos como haunting/filling.
O JAR instalado é `v2.1.4b`; o metadata runtime declara literalmente `2.1.4.` com ponto final, e essa diferença permanece preservada.
## Create: Stuff & Netherite Additions — 1.2
`create_sna-1.2-neoforge-1.21.1.jar`
**Create Stuff & Netherite Additions** estende Stuff 'N Additions para o estágio Netherite. Seu conteúdo central são versões **mais fortes de jetpack e exoskeleton**, mantendo o modelo de equipamento tecnológico do mod-base, mas elevando proteção/desempenho para uma faixa endgame.
Ele depende diretamente de Create Stuff 'N Additions e não substitui sua progressão: adiciona o tier superior de equipamentos sobre a infraestrutura já existente. A build atual do pack é `1.2` para NeoForge 1.21.1.
## Create: Dragons Plus — 1.11.7b
`CreateDragonsPlus-1.11.7b.jar`
**Create: Dragons Plus** reúne utilidades e conteúdo complementar para Create, além de **integrações condicionais** com outros addons. Seu escopo é distribuído: pequenas conveniências, ajustes e recursos são ativados conforme os mods relacionados existem no ambiente, em vez de formar uma única cadeia industrial.
O JAR usa conditional mixins para carregar somente as integrações aplicáveis. A build atual do pack é `1.11.7b`; o componente `conditional-mixin 0.6.4` está embarcado internamente e não é um top-level separado.
## Create: Things and Misc — 4.1.1
`create_things_and_misc-4.1.1-neoforge-1.21.1.jar`
**Create: Misc & Things** é uma coleção ampla de conteúdo utilitário para Create, com **ferramentas, armas, equipamentos pessoais, gadgets, blocos tecnológicos e elementos decorativos**. Em vez de desenvolver uma única cadeia industrial, o addon preenche vários nichos pequenos ao redor da fábrica e do jogador.
A build instalada `4.1.1` é a release NeoForge 1.21.1 atual. O projeto é implementado com MCreator, mas isso descreve sua ferramenta de desenvolvimento e não altera seu papel funcional no catálogo.
## Create: More Features — 0.1.3
`create_mf-0.1.3-neoforge-1.21.1.jar`
**Create: More Features** é uma expansão geral que acrescenta **novos mecanismos, itens utilitários, profissões de villagers, automações/farms e dispositivos decorativos ou funcionais** ao Create. O projeto também recupera ideias ou itens removidos de versões anteriores do ecossistema e os adapta à linha atual.
Seu conteúdo é deliberadamente variado: parte serve à fábrica, parte à interação com villagers e parte à construção. Portanto ele funciona como um pacote de extensões menores, não como uma nova fonte de energia ou sistema logístico independente.
## Create: Connected — 1.3.2-mc1.21.1
`create_connected-1.3.2-mc1.21.1.jar`
**Create: Connected** amplia os componentes de controle, redstone e construção do Create. Entre as adições estão **novos clutches e gearboxes, redstone diodes/transmitters, versões rotacionadas de vaults e tanks** e blocos voltados a layouts mecânicos mais flexíveis.
O addon também melhora sistemas existentes: Sequenced Gearshift e Attribute Filter recebem funções adicionais, vários blocos passam a funcionar de forma mais completa em contraptions e schematics podem ser organizados em subpastas. Grande parte dos recursos pode ser habilitada ou desabilitada individualmente por configuração.
O runtime instalado declara literalmente `1.3.2-mc1.21.1` e exige Create 6.0.7+, requisito atendido pelo Create 6.0.10 do pack.
## Create: Copycats+ — 3.0.8
`copycats-3.0.8+mc.1.21.1-neoforge.jar`
**Create: Copycats+** expande maciçamente o conceito de **Copycat Blocks**: formas estruturais diferentes podem receber a aparência de outros blocos, permitindo reproduzir material, textura e acabamento sem perder a geometria especializada da peça.
O catálogo inclui numerosas variantes além das formas básicas do Create, úteis para revestir mecanismos, criar painéis, molduras, superfícies inclinadas e detalhes arquitetônicos. A build `3.0.8+mc.1.21.1-neoforge` instalada é a release NeoForge 1.21.1 atual publicada em agosto de 2026.
## Create: Extra Copycats — 1.0.2
`extra_copycats-1.0.2.jar`
**Create: Extra Copycats** adiciona formas adicionais de blocos Copycat ao ecossistema Create/Copycats, incluindo peças como **Copycat Collapsible Grid**. Como os demais copycats, as formas podem assumir materiais aplicados pelo jogador para integrar mecanismos e arquitetura sem limitar a paleta visual.
Ele é um addon separado de Copycats+: o JAR `extra_copycats-1.0.2.jar` permanece top-level na modlist atual e adiciona seu próprio subconjunto de formas.
## Create: Bells & Whistles — 0.4.7-1.21.1
`bellsandwhistles-0.4.7-1.21.1.jar`
**Create: Bells & Whistles** amplia principalmente a **estética ferroviária, industrial e de estações** do Create. O addon adiciona adornos, peças utilitárias e componentes de construção pensados para locomotivas, vagões, plataformas, estações e outras estruturas ligadas a trains.
Seu foco é tornar material rodante e infraestrutura ferroviária mais detalhados e variados visualmente, complementando as mecânicas de trem já fornecidas pelo Create e por outros addons ferroviários.
## Create: Bits 'n' Bobs — 2.2.7
`bits_n_bobs-2.2.7.jar`
**Create: Bits 'n' Bobs** combina customização visual com novos componentes mecânicos. Cogwheels podem trocar o tipo de madeira; pipes e tanks aceitam tingimento; e o addon acrescenta peças como **Cogwheel Chain Drives, Flanged Cogwheels e Chain Carriage** para novos layouts de transmissão e movimentação.
O **Flywheel Bearing** dá ao flywheel uma função mecânica própria de armazenamento/uso de energia cinética, fazendo o addon ultrapassar o escopo puramente decorativo. Assim, estética e pequenas extensões de engenharia aparecem no mesmo pacote.
## Create Deco — 2.1.3
`createdeco-2.1.3.jar`
**Create Deco** é um grande pacote de **arquitetura industrial** alinhado à linguagem visual do Create. Ele fornece estruturas metálicas, catwalks/passarelas, grades, placas, suportes e múltiplas variantes de materiais para detalhar fábricas, ferrovias, depósitos e instalações técnicas.
A função é predominantemente construtiva: o addon amplia a paleta de blocos industriais sem criar uma linha de produção ou sistema energético separado. A build instalada é `2.1.3`.
## Create: Design n' Decor — 2.2b
`Design-n-Decor-1.21.1-2.2b.jar`
**Create: Design n' Decor** adiciona **QoL de construção e variantes funcionais/decorativas** ao Create. A linha atual inclui múltiplos tipos de Crushing Wheels, containers retrabalhados, dyed depots, text plates, large fans, boilers, sheet metals, lamps, catwalks e breaker switches, além de diversas variantes tingidas e texturas reestruturadas.
Parte do conteúdo é visual, mas containers, fans, switches e outras peças continuam participando de interação ou infraestrutura Create. A build instalada `2.2b` é a release NeoForge 1.21.1 atual.
## Create: Furnitures — 1.1.2
`create_furnitures-1.1.2-neoforge-1.21.1.jar`
**Create: Furnitures** adiciona mobiliário construído para combinar com a estética industrial do Create. Mesas, cadeiras, bancos e outras peças recebem variantes de materiais e cores para equipar oficinas, fábricas, escritórios, estações e áreas residenciais sem abandonar a linguagem visual do restante da instalação.
É um addon predominantemente decorativo; sua contribuição está na ambientação e composição interna das construções, não em uma nova mecânica de automação.
## Create: Prismatic Shine — 1.2.2
`createprism-1.2.2.jar`
**Create: Prismatic Shine** é uma releitura do antigo Create: Crystal Clear focada em **casings transparentes e iluminados**. Ele adiciona glass casings e illumination casings que mantêm a estética mecânica do Create enquanto permitem estruturas visualmente abertas, luminosas ou translúcidas.
O objetivo é ampliar a construção de máquinas e fábricas com casings que exibem o interior ou funcionam como elementos de iluminação. A versão `1.2.2` é a release NeoForge 1.21.1 instalada.
## Create: Chromatic Return — runtime 1.0.0
`createchromaticreturn-1.0.4-neoforge-1.21.1.jar`
**Create: Chromatic Return** reintroduz o conceito de **Chromatic Compound** como uma progressão de ligas endgame com propriedades especiais. A linha atual possui materiais como **Multiplite, Anti-Plite, Industrium, Durasteel, Fortunite e Silkstrum**, cada um ligado a efeitos ou usos próprios — produção de componentes, durabilidade extrema, aumento de rendimento de minérios ou comportamento semelhante a Silk Touch.
Essas ligas alimentam ferramentas/armas de altíssimo nível, charms com efeitos permanentes enquanto equipados, livros infundidos e até itens equivalentes a recursos Creative. O sistema também possui enriquecimento de quartz que pode aumentar rendimento de crushed ores/gem ores em processos aquecidos ou superheated.
O arquivo/publicação instalado é `1.0.4`, enquanto o metadata runtime declara `1.0.0`; as duas identidades são preservadas sem normalização.
# 13. Ferramentas de controle e interface Create
## Create Quality of Life — 1.6.3 fix1
`Create Quality of Life-1.21.1-1.6.3-fix1.jar`
**Create: Quality Of Life** reúne pequenas extensões voltadas a reduzir atrito no uso cotidiano do Create. Em vez de introduzir uma grande cadeia tecnológica, acrescenta ferramentas e comportamentos auxiliares para tornar construção, configuração e operação das máquinas mais rápidas ou menos repetitivas.
A build instalada é `1.6.3-fix1`, release NeoForge 1.21.1 atual. O mod também é dependência funcional de Create: Chromatic Return na configuração presente.
## Create Tweaked Controllers — 1.21.1-1.2.7
`create_tweaked_controllers-1.21.1-1.2.7.jar`
**Create: Tweaked Controllers** adiciona um **Advanced Controller** para comandar contraptions Create com teclado, mouse ou gamepad. Isso permite mapear entradas do jogador para controles de veículos e mecanismos de forma mais direta que a interface vanilla de redstone links/contraption controls.
A versão `1.2.7` suporta Create 6 e declara compatibilidade com Create Simulated e com ambientes Valkyrien Skies/Clockwork, refletindo seu foco em contraptions dirigíveis e sistemas móveis.
## Create Ultimine — 1.3.2
`createultimine-1.21.1-neoforge-1.3.2.jar`
**Create Ultimine** integra ações compatíveis do Create ao **FTB Ultimine**. Operações que normalmente seriam executadas bloco a bloco podem participar da seleção em área do Ultimine quando suportadas pelo addon, incluindo interações com ferramentas e wrench do ecossistema Create.
Ele não implementa um segundo sistema de vein mining: reutiliza o mecanismo de seleção/execução do FTB Ultimine e apenas ensina ações Create a participarem desse fluxo.
## Create Utilities J — 0.3.4+1.21.1
`Create-Utilities-J-1.21.1-0.3.4+1.21.1.jar`
**Create Utilities J** é a continuação mantida do antigo Create Utilities. A linha atual porta e preserva os componentes utilitários do projeto, corrige bugs e mantém o addon compatível com as APIs modernas de Create/NeoForge.
Na build `0.3.4+1.21.1`, o projeto migrou networking para a payload API do NeoForge, atualizou saved data/capability registration, restaurou a creative tab e corrigiu problemas como serialização de frequência com ItemStack vazio. Também existem correções específicas de sistemas legados, como o Void Motor. O foco é manutenção funcional e infraestrutura das utilidades já fornecidas pelo addon.
## Create Stats & Numbers — 1.2.81
`create_stats-1.2.81.jar`
**Create Stats & Numbers** amplia Create com **telemetria, eletricidade FE, controle e instrumentação de fábrica**. Stats displays, recorders, alarms, data dials, production detectors, hologramas e mapas de topologia permitem medir produção, eficiência, estado de máquinas e danos em seções de veículos ou instalações.
A parte elétrica forma uma rede própria com **cabos, wire terminals, dynamos, motors e capacitor banks**. O **Power Distribution Unit (PDU)** centraliza a distribuição e permite atribuir prioridades aos dispositivos; em modo automático, cargas de prioridade inferior podem ser desligadas primeiro quando a geração não atende a demanda.
O addon também inclui Factory Logic Controller, Dispatch Board, Load Balancer, Calculator, iluminação industrial, Induction Heater/Range e componentes veiculares como **Flight Control Computer, RPM Governor, cockpit console, Path Scanner e Pathfinder Computer**. A build `1.2.81` é um hotfix para inconsistência entre PDU e Flow Meter e inclui melhorias na interface web local.
## Create Optical — 0.4.2
`create_optical-0.4.2.jar`
**Create Optical** introduz uma camada de **sinais por feixes ópticos** dentro do ecossistema Create. Fontes ópticas dependem de potência/rotação e emitem feixes que podem ser direcionados, detectados ou usados como entrada lógica por componentes compatíveis.
A mecânica oferece uma linguagem de transmissão e controle diferente de eixos, redstone dust ou wireless redstone links, permitindo construir circuitos visuais baseados em direção e presença de luz/feixe.
## Create Cyber Goggles — 8.3.14
`CreateCyberGoggles-1.21.1-8.3.14-NeoForge.jar`
**Create: Cyber Goggles** é uma ferramenta **client-side de inspeção e assistência modular** para o Create. Os goggles ampliam as informações apresentadas ao jogador ao observar máquinas, redes e componentes, tornando mais fácil diagnosticar estados e parâmetros durante construção ou manutenção.
Seu escopo é interface/assistência visual; ele não substitui o equipamento físico do mod Create Goggles (Create Plus), que adiciona Goggle Helmets e Armored Backtanks. A build `8.3.14`, direcionada a Create 6.0.10, corrige um crash client-side quando o centro de uma contraption coincide exatamente com o centro da OBB do dispositivo e também atualiza localização.
## Create Goggles — 6.1.1
`creategoggles-1.21.1-6.1.1-[NEOFORGE].jar`
**Create Goggles (Create Plus)** adiciona **Goggle Helmets e Armored Backtanks**, combinando as Engineer's Goggles com peças de armadura/equipamento. O objetivo é preservar a leitura de informações do Create enquanto o jogador utiliza capacetes ou backtanks mais protegidos/funcionais.
É uma expansão de equipamento físico, não um overlay independente. A build `6.1.1` instalada é a beta NeoForge 1.21.1 atual do projeto.
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
# 15. Bibliotecas tecnológicas
## Azimuth API — 1.4.7
`azimuth-1.4.7.jar`
**Azimuth API** é uma biblioteca de infraestrutura para addons do ecossistema Create. Ela concentra recursos estruturais, de renderização e de integração cinética usados por consumidores em vez de fazer cada addon implementar essas camadas de forma independente.
Um consumidor atual registrado no pack é **Bits 'n' Tracks**. Azimuth não adiciona uma linha de progressão ou máquinas próprias; sua presença representa uma dependência top-level compartilhada.
## KilaGraph — 21.1.0.11
`kilagraph-neoforge-1.21.1-21.1.0.11.jar`
**KilaGraph** é um toolkit de **node graphs programáveis e shader graphs** construído sobre LDLib2. Ele fornece Blueprint Graphs para lógica/data flow, RenderType Graphs para pipelines visuais, Shader Function Graphs reutilizáveis e um editor in-game baseado nas ferramentas do LDLib2.
A biblioteca inclui nodes voltados ao próprio Minecraft — items, blocks, fluids, entities, NBT, queries de mundo, math, listas, maps, strings e operações de shader. É infraestrutura para mods consumidores, não um shaderpack ou sistema visual autônomo. A build 1.21.1 está no canal beta.
## Multi-Piston — 1.2.58-1.21.1
`multipiston-1.2.58-1.21.1.jar`
**Multi-Piston** implementa um sistema de **pistão multidirecional** capaz de movimentar estruturas de formas que o piston vanilla não representa. Além da mecânica própria, ele funciona como componente técnico do ecossistema MineColonies.
Na modlist atual, MineColonies é consumidor confirmado dessa biblioteca/mod, e a versão `1.2.58-1.21.1` supera o mínimo exigido pelo snapshot instalado. Não é equivalente às contraptions Create: sua implementação e seu papel de dependência são separados.
## NukaTeam's Gun Lib — 3.2.0
`ntgl-1.21.1-3.2.0.jar`
**NukaTeam's Gun Lib (NTGL)** é um framework para mods e gun packs implementarem **armas animadas, armas melee, granadas e sistemas de tiro**. A API fornece modos de disparo, munição, dual wielding, tipos de projétil e infraestrutura visual/funcional para conteúdos consumidores.
A biblioteca não representa uma arma específica nem uma progressão própria completa: seu valor está em fornecer o runtime comum para packs e mods de armamento que a utilizam. A build 3.2.0 é a release NeoForge 1.21.1 instalada.
## Potentials — 0.7.1
`potentials-neoforge-1.21-0.7.1.jar`
**Potentials** é uma biblioteca de **capabilities cross-platform** para transferência de energia, fluidos e itens. Ela abstrai diferenças entre loaders/APIs para que mods consumidores possam consultar e movimentar esses recursos através de uma interface comum.
A linha `0.7.1` é a compatível com Minecraft 1.21/1.21.1 presente no pack e permanece beta. Não adiciona máquinas ou redes próprias; fornece a camada de interoperabilidade usada por consumidores específicos.
## Ritchie's Projectile Library — 2.1.2
`ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`
**Ritchie's Projectile Library** fornece infraestrutura para projéteis rápidos, long-range e de grande volume. Entre os recursos estão sincronização de movimento mais precisa, **chunkloading configurável para projéteis**, screen shake e **projectile bursts** capazes de representar shotgun pellets, fragmentação e shrapnel sem criar uma quantidade excessiva de entidades independentes.
É uma biblioteca para mods consumidores, especialmente conteúdos de firearms/artillery, e não uma arma isolada. A build 2.1.2 também inclui correções NeoForge ligadas ao registry de rede e efeitos de cannon shake.
## Sophisticated Core — runtime 1.4.90
`sophisticatedcore-1.21.1-1.4.90.2299.jar`
**Sophisticated Core** é a biblioteca compartilhada do ecossistema Sophisticated. Ela centraliza infraestrutura de **upgrades, inventários, storage, recipes, filtros e comportamento comum** utilizada por Sophisticated Backpacks, Sophisticated Storage e integrações correspondentes.
Não adiciona uma progressão jogável independente: seu conteúdo é consumido pelos mods-base. A build instalada `1.4.90.2299` é a release NeoForge 1.21.1 de 22/08/2026; o metadata runtime declara `1.4.90`. Entre as correções dessa build está o restocking de receitas com múltiplas alternativas de ingrediente.
## Mechanicals Lib — 1.1.6
`mechanicals-1.21.1-1.1.6.jar`
**Mechanicals Lib** é a biblioteca comum utilizada por mods do ecossistema Mechanicals. Ela concentra registries, helpers e código compartilhado para que addons consumidores não precisem embarcar implementações duplicadas.
Não adiciona uma progressão tecnológica própria. Um consumidor confirmado na modlist atual é **Create: Mechanical Spawner**, que depende dessa biblioteca para sua infraestrutura.
## Cupboard — 4.1
`cupboard-1.21.1-4.1.jar`
**Cupboard** é uma biblioteca/framework compartilhado para mods consumidores. Além de helpers e infraestrutura comum, a linha atual fornece **framework de configuração JSON, stacktraces completos em crashes, logging de erros de comandos e de carregamento síncrono de chunks**, além de proteções para falhas comuns durante carregamento de entidades, como rotações inválidas.
Ela não adiciona máquinas, itens de progressão ou gameplay próprio: os recursos visíveis pertencem aos mods que utilizam sua API. A build instalada foi atualizada para `4.1` na modlist de 28/08/2026; o guia anterior ainda registrava `4.0` e foi corrigido para o JAR canônico `cupboard-1.21.1-4.1.jar`.
## Curios API — 9.5.1+1.21.1
`curios-neoforge-9.5.1+1.21.1.jar`
**Curios API** fornece a infraestrutura de **slots de acessórios adicionais** usada por grande parte do pack. Mods podem registrar categorias como back, charm, ring, necklace e outros slots, definir regras de equipagem e consultar os itens equipados sem disputar os slots vanilla de armadura/offhand.
Isso permite que jetpacks, backtanks, goggles, charms e diversos acessórios permaneçam funcionais através de uma API comum. Curios não possui uma progressão própria significativa; é a camada de slots/equipamento consumida por vários mods e bridges.
## Cyclops Core — 1.29.3
`cyclopscore-1.21.1-neoforge-1.29.3.jar`
**Cyclops Core** é a biblioteca central do ecossistema CyclopsMC. Ela fornece APIs, configuração, network helpers, registries e outras estruturas compartilhadas por mods da família, como Integrated Dynamics/EvilCraft quando presentes.
A versão atual da modlist é `1.29.3`. O fato de outras bibliotecas gerais existirem no pack não substitui sua API específica; sua função é exclusivamente infraestrutura para consumidores Cyclops.
## Forgified Fabric API — 0.116.15+2.3.5+1.21.1
`forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
**Forgified Fabric API** porta módulos da **Fabric API para NeoForge**, fornecendo as interfaces que mods originalmente escritos contra Fabric API esperam encontrar. No pack ele trabalha especialmente em conjunto com **Sinytra Connector**, permitindo que mods Fabric e suas dependências utilizem APIs equivalentes dentro do ambiente NeoForge.
Ele não substitui o Connector: Forgified Fabric API fornece APIs; Connector realiza a camada de compatibilidade/carregamento necessária aos mods Fabric. A build atual é `0.116.15+2.3.5+1.21.1`.
<callout icon="ℹ️" color="gray_bg">
	**Componentes internos não contados como mods top-level:** Advanced AE pode embarcar/usar AE2AddonLib internamente, e o ecossistema AE2WTLib expõe APIs compartilhadas para seus consumidores. Como esses nomes não correspondem a JARs top-level independentes da modlist atual, ficam registrados apenas como contexto técnico e não como entradas de mod do catálogo.
</callout>
# 16. Mapa rápido dos ecossistemas
<table fit-page-width="true" header-row="true">
<tr>
<td>Objetivo</td>
<td>Ecossistema principal</td>
<td>Exemplos instalados</td>
</tr>
<tr>
<td>Automação mecânica visível</td>
<td>Create</td>
<td>Create, More Automation, Ultimate Factory, Metallurgy</td>
</tr>
<tr>
<td>Armazenamento digital e autocrafting</td>
<td>Applied Energistics 2</td>
<td>AE2, Advanced AE, Applied Create</td>
</tr>
<tr>
<td>Máquinas energizadas especializadas</td>
<td>Oritech</td>
<td>Oritech, Oritech Things</td>
</tr>
<tr>
<td>Eletricidade e conversão cinética</td>
<td>Create energético</td>
<td>New Age, Crafts & Additions, Power Grid</td>
</tr>
<tr>
<td>Indústria pesada</td>
<td>Create industrial</td>
<td>TFMG, Diesel Generators, Metalwork</td>
</tr>
<tr>
<td>Energia nuclear</td>
<td>Create nuclear</td>
<td>Create Nuclear, New Age</td>
</tr>
<tr>
<td>Veículos construídos por blocos</td>
<td>Sable + Aeronautics</td>
<td>Create Aeronautics, Aeroworks, Propulsion</td>
</tr>
<tr>
<td>Exploração espacial</td>
<td>Create espacial</td>
<td>Northstar Redux, Creating Space, Alcubierre</td>
</tr>
<tr>
<td>Ferrovias</td>
<td>Create trains</td>
<td>Steam 'n' Rails, Blocks & Bogies</td>
</tr>
<tr>
<td>Artilharia e defesa</td>
<td>Create militar</td>
<td>Big Cannons, CBC Advanced Technology, Missiles</td>
</tr>
<tr>
<td>Integração magia-tecnologia</td>
<td>Technomancy</td>
<td>Ars Creo, Ars Technica, Create Wizardry, Apokinetics</td>
</tr>
<tr>
<td>Armazenamento físico conectado</td>
<td>Storage</td>
<td>Sophisticated Storage, Tom's Storage</td>
</tr>
</table>
# 17. Fontes principais de referência
- [Create Aeronautics — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics)
- [Applied Energistics 2 — CurseForge](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
- [Oritech — CurseForge](https://www.curseforge.com/minecraft/mc-mods/oritech)
- [Create: New Age — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-new-age)
- [Create Nuclear — CurseForge](https://www.curseforge.com/minecraft/mc-mods/createnuclear)
- [Create: The Factory Must Grow — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-industry)
- [Create: Northstar Redux — CurseForge](https://www.curseforge.com/minecraft/mc-mods/northstar-redux)
- [Create: Creating Space — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-creating-space)
- [Applied Create — CurseForge](https://www.curseforge.com/minecraft/mc-mods/applied-create)
- [Create: Aeroworks — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeroworks)
## Gameplay e sistemas
<mention-page url="https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6"/>