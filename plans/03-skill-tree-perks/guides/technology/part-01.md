<!-- Snapshot canônico do Notion: GUIA COMPLETO — Mods de Tecnologia | NeoForge 1.21.1
Fonte: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff
Parte 1/3. Continuar em part-02.md. -->

<callout icon="📌" color="blue_bg">
	**RECONCILIADO + QC DELTA 13/13 — 2026-08-28:** o guia foi reverificado contra `modlist 28.08.26.txt`. No eixo Tecnologia/Física, o delta novo é o stack **Sable Ragdolls, Ragdoll Reactions, Sable Ragdolls Patch, Sable x CPM e Sable mob ragdoll corpses**. Os cinco estão descritos pelo papel técnico real — física-base, gatilhos, patch, compatibilidade de modelo e corpos físicos de mobs — sem serem tratados como sistemas tecnológicos independentes. A cobertura global dos 13 novos mods foi conferida entre os três guias.
</callout>
<callout icon="⚙️" color="gray_bg">
	Este documento é um **catálogo descritivo dos mods de tecnologia** instalados no pack. O foco é explicar o que cada sistema acrescenta, como sua tecnologia funciona e como os addons se encaixam no ecossistema principal. Não é uma lista de problemas, conflitos ou recomendações de remoção.
</callout>
<table_of_contents/>
# 1. Sistemas tecnológicos principais
## Create — 6.0.10
`create-1.21.1-6.0.10.jar`
**Create** é a espinha dorsal mecânica do pack. Em vez de trabalhar principalmente com máquinas fechadas que recebem FE, ele constrói automação através de **rotação, velocidade e stress**. Eixos, engrenagens, correias, caixas de engrenagem, rodas d'água, motores, boilers e outras fontes produzem ou transmitem movimento; máquinas consomem capacidade de stress conforme trabalham.
O sistema de produção é deliberadamente físico. Crushing Wheels esmagam materiais, Mechanical Presses prensam, Mechanical Mixers misturam, Deployers simulam interação de jogador, Mechanical Arms distribuem itens, belts fazem transporte visível e contraptions transformam conjuntos inteiros de blocos em máquinas móveis. Isso faz com que uma fábrica Create seja também uma construção de engenharia, porque layout, transmissão mecânica, logística e sincronização importam.
Outra parte central são as **contraptions**. Blocos podem ser colados e montados em pistons, bearings, trains ou outras estruturas móveis. No seu pack esse conceito se expande drasticamente por Create Aeronautics e Sable, chegando a veículos físicos, aeronaves, submarinos e espaçonaves.
## Applied Energistics 2 — 19.2.17
`appliedenergistics2-19.2.17.jar`
**Applied Energistics 2 (AE2)** é o grande sistema de armazenamento digital e automação de crafting do pack. Ele substitui redes gigantes de baús por uma **ME Network**, na qual itens e fluidos são representados dentro de células de armazenamento e acessados por terminais.
A rede possui energia, cabos, dispositivos, interfaces e uma arquitetura baseada em canais. Storage Cells armazenam recursos; ME Drives concentram células; Terminals dão acesso ao conteúdo; Import/Export Buses movimentam itens; Storage Buses incorporam inventários externos; Pattern Providers e Molecular Assemblers executam **autocrafting sob demanda**.
O autocrafting é uma das funções mais importantes: receitas são gravadas em patterns e o sistema calcula ingredientes, subcomponentes e etapas necessárias. Com integrações do seu pack, AE2 também passa a interagir com processos do Create e até armazenar/transmitir grandezas relacionadas à rotação.
AE2 também possui **Spatial Storage**, capaz de capturar regiões do mundo em células espaciais, além de P2P tunnels, subnetworks e ferramentas de diagnóstico para redes complexas.
## Oritech — 1.2.10
`oritech-neoforge-1.21.1-1.2.10.jar`
**Oritech** é um sistema tecnológico mais tradicional, baseado em **máquinas animadas, energia, processamento avançado e equipamentos**. Ele complementa Create oferecendo máquinas especializadas e uma progressão industrial própria, com processamento de recursos, infraestrutura energética e tecnologia de alto nível.
Sua identidade visual é de maquinaria industrial compacta, mas com animações e componentes visíveis. A progressão leva o jogador de processamento básico para sistemas cada vez mais avançados, incluindo máquinas de fabricação, geração e armazenamento de energia, ferramentas e equipamentos tecnológicos.
No pack, ele funciona como uma segunda linguagem tecnológica: Create resolve problemas por cinética e montagem física; Oritech resolve muitos deles através de máquinas energizadas especializadas.
## Oritech Things — 0.0.46
`oritechthings-0.0.46.jar`
**Oritech Things** amplia Oritech com uma camada de upgrades, controle e utilidades voltada principalmente às máquinas avançadas do mod-base. Ele adiciona **addons tiered de Speed, Efficiency, Processing, Capacitor e Acceptor**, inclusive versões híbridas de Efficient Speed, permitindo escalar capacidade e desempenho de máquinas por vários níveis.
O addon aprofunda especialmente o **Particle Accelerator**: inclui Advanced Target Designator, sensor/controlador de velocidade com modo manual ou automático, Accelerator Linear Motor e Magnetic Field capaz de reduzir significativamente o tamanho do anel ao custo de energia. Também possui **Cross-Dimensional Drone Port**, permitindo definir destinos de drones em outra dimensão.
Outros recursos incluem **Exo JetPack** com voo criativo consumindo energia/turbo fuel, Frame Placer para montar automaticamente a estrutura do Destroyer e conteúdo próprio como Infested Amethyst/Amethyst Fish. Portanto ele não é apenas QoL: adiciona peças funcionais e automação avançada ao estágio alto da progressão Oritech.
## Sable — 2.0.5
`sable-neoforge-1.21.1-2.0.5.jar`
**Sable** é a infraestrutura que adiciona **sublevels** ao Minecraft: regiões móveis formadas por blocos, block entities e entidades que continuam interativas depois de montadas. O jogador pode permanecer dentro dessa região, andar, usar inventários e interagir com máquinas enquanto o conjunto inteiro muda de posição e orientação no mundo principal.
Isso fornece a base técnica para construções que se comportam como objetos físicos completos, em vez de simples animações de contraption. Sistemas como Create Aeronautics utilizam esses sublevels para veículos e estruturas móveis construídas livremente com blocos.
Como o conteúdo passa a existir em espaços coordenados próprios, outros mods precisam reconhecer corretamente transformação de posição, renderização, som, teleporte e interação entre level principal e sublevels; por isso o pack possui várias bridges específicas para Sable.
## Sable Assembly Fix — 1.0.0
`SableAssemblyFix-1.0.0.jar`
**Sable Assembly Fix** é um patch server-side para o ciclo de montagem e desmontagem de estruturas Sable/Create Aeronautics. Ele evita duplicação de itens quando block entities de outros mods possuem inventário, mas não implementam corretamente o contrato `Clearable`: sem o patch, o conteúdo pode ser preservado na estrutura montada e ao mesmo tempo dropado durante a desmontagem.
A correção é genérica e foi feita para cobrir inventários modded como kegs, cabinets, baskets, Item Drains e storages compatíveis, sem adicionar um novo sistema de armazenamento ou de física. Sua função é preservar a integridade de inventários durante `assembly/disassembly` no stack móvel. A build instalada é `1.0.0` para NeoForge 1.21.1 e depende do stack Sable + Create + Create Aeronautics.
## Create Aeronautics — 1.3.1
`create-aeronautics-bundled-1.21.1-1.3.1.jar`
**Create Aeronautics** usa Create e Sable para transformar construções de blocos em **veículos físicos montados pelo jogador**. Não há chassis pré-fabricado obrigatório: corpo, propulsão, controle, armazenamento e máquinas podem ser organizados como parte da própria construção antes da montagem.
O bundle reúne três linhas principais. **Simulated** fornece a base de montagem e interação com contraptions físicas; **Aeronautics** acrescenta voo e sustentação, incluindo propellers e mecanismos de hot air; **Offroad** acrescenta rodas, pneus, direção e componentes para veículos terrestres. A própria página oficial descreve o escopo indo de aviões, drones e balões a carros e caminhões.
As contraptions podem continuar contendo máquinas e logística do Create enquanto se movem. A versão 1.3.1 também registra correções para Docking Connector/fluidos, compatibilidade CC e parâmetros de atrito de pneus, reforçando que movimento, docking e sistemas internos do veículo fazem parte da simulação funcional.
## Create Tracks+ — 1.0.6b
`tracks_plus-1.0.6b.jar`
**Create Tracks+** é um fork/addon de lagartas físicas voltado ao stack **Create Aeronautics + Sable**. Ele adiciona conjuntos de tracks/esteiras para veículos, com geometria retrabalhada, track mounts, suspensão de maior curso e interação física com o terreno, permitindo que veículos transponham obstáculos usando o contato das lagartas em vez de tratar as tracks apenas como rodas visuais.
A build instalada é `1.0.6b`, linha beta para NeoForge 1.21.1. O projeto declara dependência de Create, Create Aeronautics e Sable e também declara **incompatibilidade com o Create:Tracks original**, pois este fork ocupa o mesmo papel funcional. Portanto esta entrada representa o provider de tracks presente no pack, não uma segunda instalação paralela do mod original.
## Immersive Aeronautics — runtime 6.0.7
`Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar`
**Immersive Aeronautics** é um rewrite de Immersive Portals voltado especificamente a **Create Aeronautics/Sable**. Seu objetivo é fazer navios e outras contraptions Sable atravessarem portais mantendo a experiência de portal contínuo e a integração visual do sistema, em vez de tratar o veículo como um conjunto incompatível de blocos durante a travessia.
A build 1.1.4 inclui correções para renderização quando há várias contraptions Sable simultâneas e para artefatos com Distant Horizons, além de trabalho específico para tornar o transporte de portais mais fluido. O arquivo publicado é `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar`, enquanto o metadata interno continua se identificando como **Immersive Portals 6.0.7**; o guia preserva as duas identidades separadamente.
A release 1.1.4 é beta para NeoForge 1.21.1; essa classificação descreve maturidade da implementação, não ausência de funcionalidade ou incerteza sobre o JAR instalado.
## Create: The Factory Must Grow — 1.2.4b Community
`tfmg-1.21.1-1.2.4b-community.jar`
**Create: The Factory Must Grow (TFMG)** expande Create para **indústria pesada, petróleo e eletricidade**. A progressão adiciona extração e refino de óleo, produção de combustíveis como diesel, gasolina e LPG, além de materiais industriais como aço, enxofre, alumínio e chumbo.
A energia mecânica passa a incluir **motores a combustão** de diferentes combustíveis e configuração de cilindros. O addon também possui um sistema elétrico próprio com **voltagem** e compatibilidade com FE, levando a fábrica de uma rede puramente cinética para linhas elétricas e equipamentos industriais de maior escala.
O conteúdo se estende a infraestrutura de refinaria, componentes químicos, pipes, blocos estruturais como concreto/cinderblocks/suportes e equipamentos associados à indústria pesada. Há ainda armas e produtos industriais, como napalm e flamethrowers, que derivam das mesmas cadeias de materiais e combustíveis.
O JAR do pack é a build comunitária `1.2.4b-community`; a documentação pública do projeto-base expõe atualmente a linha oficial 1.2.x para NeoForge 1.21.1, então o guia mantém a identidade do arquivo local sem substituí-la pela numeração pública.
## Create: Rubberworks — 1.1.4
`rubberworks-neoforge-1.21.1-1.1.4.jar`
**Create: Rubberworks** acrescenta uma cadeia de **resina → borracha** integrada às máquinas do Create. O objetivo declarado do projeto é substituir usos industriais artificiais de dried kelp por uma produção dedicada de borracha, dando a componentes do Create uma matéria-prima própria desse domínio.
O **Sapper** é usado para extrair resina, que depois entra em etapas de processamento mecânico até virar borracha e materiais derivados para receitas. A linha é pequena em comparação com TFMG, mas constitui uma cadeia produtiva real e separada, com coleta de matéria-prima e transformação industrial próprias.
## Create: New Age — 1.2.0+mc1.21.1
`create-new-age-1.2.0+neoforge-mc1.21.1.jar`
**Create: New Age** adiciona uma camada de **eletricidade, magnetismo, calor e nuclear** construída ao redor das mecânicas visuais do Create. Grandes bobinas de cobre girando com magnetos geram eletricidade; diferentes motores fazem a conversão inversa e transformam energia elétrica em rotação.
A rede elétrica usa fios e alimenta máquinas como o **Energiser**, que combina eletricidade e movimento para processos específicos. O addon também possui produção térmica e multiblocks nucleares cujo calor pode ferver água e alimentar steam engines do Create, mantendo ligação entre geração elétrica, calor e cinética.
Na versão 1.2.0 foram adicionados **efeito de radiação e Geiger counter**, além de comportamento de explosão para reatores superaquecidos. A mesma linha inclui Street Lights/Lamp Posts e suporte experimental a CC:Tweaked. Portanto o componente nuclear não é apenas decorativo: temperatura, radiação e falha de reator fazem parte do sistema.
## Create: Power Grid — 0.6.1
`powergrid-mc1.21.1-0.6.1.jar`
**Create: Power Grid** implementa uma rede elétrica inspirada em **engenharia elétrica real**, integrada ao Create. O sistema exige equilibrar geração e demanda para manter tensão estável e trabalha com geração, distribuição, circuitos, medição e conversão em vez de representar eletricidade apenas como uma reserva abstrata.
A linha 0.6 inclui Electric Pump, Energy Meter, FE Inverter, Large Induction Rotor, Integrated Circuits, Modular Displays, Solar Panels, Portable Drill/Saw e outros componentes de geração e controle. A build `0.6.1` corrige persistência de switches, renderização de fios, parsing de expressões e crashes com **TFMG Community Edition** e contraptions Sable em alta velocidade; também adiciona integração CC para battery e redstone converter, além de comportamento de corte de árvores ao Portable Saw.
## Create Nuclear — 1.3.2-beta.3
`createnuclear-1.3.2-beta.3-neoforge.jar`
**Create Nuclear** implementa uma cadeia de **fissão nuclear integrada ao Create**. O worldgen adiciona **Uranium e Thorium**, e o combustível depende de etapas de refino que podem ser automatizadas antes de chegar ao reator.
O reator é uma instalação industrial cujo desempenho depende da montagem e do ciclo de combustível; um projeto otimizado pode produzir milhões de **Stress Units**, convertendo a cadeia nuclear diretamente em capacidade cinética para a fábrica Create. O sistema também incorpora os perigos associados à radiação e ao funcionamento inadequado do reator, de modo que energia nuclear não é representada apenas por um bloco gerador isolado.
O JAR `1.3.2-beta.3` é a última build NeoForge publicada para **Minecraft 1.21.1** e permanece classificado como beta. O projeto já possui uma linha 2.x mais nova para Forge 1.20.1, mas ela é outra plataforma/versão de jogo e não representa a build instalada neste pack.
# 2. Applied Energistics 2 e automação digital
## Advanced AE — 1.6.12
`AdvancedAE-1.6.12-1.21.1.jar`
**AdvancedAE** é uma expansão de conveniência e endgame para redes AE2. Seus **Advanced Pattern Providers** permitem definir por qual face cada input de um processing pattern será enviado, reduzindo a necessidade de pipes e lados dedicados em máquinas com entradas específicas.
O **Quantum Computer** funciona como um sistema de crafting escalável: pode executar múltiplas tarefas simultâneas compartilhando co-processors e storage dentro de um multiblock configurável. O **Quantum Crafter** acessa o inventário ME diretamente, executa muitas operações por tick e suporta crafting recursivo, receitas com ingredientes não consumidos e limites de estoque.
O addon também inclui **Quantum Armor** conectada à rede ME, com utilidades como voo, magnetismo, alimentação automática e resistências; uma **Reaction Chamber** para processamento de alto consumo energético; buses especializados para manter estoque/importar/exportar; e **ME Throughput Monitor** para acompanhar variação de itens, fluidos ou energia na rede.
## ExtendedAE — runtime 1.21-2.2.35-neoforge
`ExtendedAE-1.21-2.2.35-neoforge.jar`
**ExtendedAE** amplia storage, I/O e autocrafting do AE2 com componentes de alta capacidade. Pattern Provider e Interface passam a ter **36 slots**; Import/Export Buses possuem versões até **8× mais rápidas**; e terminais de acesso a patterns recebem versões maiores, inclusive wireless.
O addon inclui **Infinity Cells** para água/cobblestone, Pattern Modifier para editar e clonar patterns em lote, buses filtrados por tag ou mod ID, Precise Export Bus, Active Formation Plane e versões paralelas de Inscriber/Charger/Molecular Assembler. O **Assembler Matrix** fornece um multiblock escalável de crafting de altíssimo throughput.
Há ainda ME Canner, Crystal Assembler/Restorer e **ME Wireless Connector** para ligar trechos de rede à distância. A release pública é 2.2.35; o metadata runtime instalado declara `1.21-2.2.35-neoforge`, e as duas identificações permanecem preservadas.
## AE2: Crafting Tree — 1.1.1
`ae2ct-1.21.1-1.1.1.jar`
**AE2: Crafting Tree** adiciona uma visualização explícita do plano de autocrafting do AE2. Um botão de **Show Crafting Plan** abre a árvore do craft, exibindo a relação entre produto final, componentes intermediários e dependências recursivas.
A função é diagnóstica: em receitas grandes, permite seguir visualmente quais subcrafts compõem a solicitação em vez de analisar apenas a lista plana de ingredientes do terminal.
## AE2 Import Export Card — 1.5.0
`ae2importexportcard-1.21.1-1.5.0.jar`
**AE2 Import Export Card** leva automação de inventário diretamente aos **wireless terminals**. A Import Card observa slots selecionados do inventário do jogador e envia automaticamente os itens correspondentes para a ME Network; a Export Card faz o caminho inverso e mantém quantidades configuradas no inventário a partir do armazenamento ME.
Ambas suportam filtros. A integração reconhece **Fuzzy Card** e **Inverter Card**; a Export Card pode usar **Acceleration Card** para aumentar transferência até stacks inteiros e **Crafting Card** para solicitar autocrafting quando o item configurado não existe em estoque. Assim, o inventário pessoal pode ser tratado como uma extensão automatizada da rede enquanto o terminal wireless está em uso.
## AE2 Network Analyzer — 2.1.5
`AE2NetworkAnalyzer-1.21-2.1.5-neoforge.jar`
**AE2 Network Analyzer** é uma ferramenta de inspeção visual para ME Networks. Ele representa a rede e seus devices de forma gráfica, permitindo examinar como componentes estão conectados e localizar segmentos ou dispositivos dentro de topologias grandes.
Seu papel é observabilidade e diagnóstico, não armazenamento ou crafting: transforma a estrutura lógica da rede em uma visualização navegável para entender conexões e organização.
## AE2 JEI Integration — 1.2.1
`ae2jeiintegration-1.2.1.jar`
**AE2 JEI Integration** restaura no Minecraft 1.21 a camada JEI que deixou de vir embutida no AE2. Ela registra no JEI as **receitas dos blocos-máquina** do ecossistema AE2 e permite preencher ingredientes diretamente a partir da visualização de receita.
A integração suporta **ingredient autofill**, click-and-drag de **Ghost Ingredients** para slots de configuração e opções para sincronizar a busca do JEI com a busca dos Terminals AE2. A versão 1.2.1 também registra correção de compatibilidade com ME Requester.
## AE2WTLib — 19.5.1
`ae2wtlib-19.5.1.jar`
**Applied Energistics 2 Wireless Terminals / AE2WTLib** adiciona versões wireless de vários terminais AE2 — incluindo Crafting, Pattern, Interface e Fluid Terminal — e fornece a biblioteca usada por integrações que operam sobre esses dispositivos.
O addon também possui um **Wireless Universal Terminal**, reunindo múltiplas interfaces em um único item. Com **Quantum Bridge Card**, os terminais podem permanecer ligados à ME Network a qualquer distância e inclusive entre dimensões, usando a infraestrutura de quantum linking em vez do alcance wireless convencional.
## Schematic Energistics — 1.5.4a
`schematicenergistics-1.21.1-1.5.4a.jar`
**Schematic Energistics** conecta diretamente o **Schematicannon do Create** à rede AE2 por meio do **Cannon Interface**. Colocado ao lado do cannon e conectado à ME Network, o bloco permite que o Schematicannon solicite automaticamente os materiais da construção ao armazenamento digital.
Se um item exigido pelo schematic não estiver armazenado, a integração pode acionar o **autocrafting do AE2** para produzi-lo. Também reabastece gunpowder automaticamente. Containers convencionais próximos continuam tendo prioridade, e a rede ME entra como fonte adicional quando esses inventários não fornecem o material necessário.
## Polymorphic Energistics — 0.4.1
`polyeng-0.4.1.jar`
**Polymorphic Energistics** leva a resolução de conflitos de receita do Polymorph para interfaces de crafting do AE2. Quando a mesma disposição de ingredientes pode gerar resultados diferentes, o jogador recebe o seletor correspondente dentro do fluxo de crafting do sistema ME em vez de ficar preso à receita escolhida automaticamente.
A versão 0.4.1 também possui integração com AE2WTLib para reposicionar o widget de seleção em wireless crafting terminals e evitar sobreposição com os controles de retorno de itens. No pack, a implementação Polymorph+ fornece a camada compatível usada por esse tipo de addon.
## Not Enough Patterns — 0.5.1
`nep-1.21.1-0.5.1.jar`
**Not Enough Patterns** estende o sistema de processing patterns do AE2 para máquinas e tipos de receita de outros mods. Em vez de obrigar toda integração externa a ser representada como um pattern genérico, o addon cria suporte específico para máquinas/receitas compatíveis e melhora a codificação desses processos dentro do ecossistema ME.
As versões públicas recentes também adicionaram compatibilidade com providers expandidos e corrigiram casos em que receitas transferidas por viewers externos podiam ser codificadas no tipo de pattern errado. O objetivo é fazer autocrafting intermod funcionar de maneira mais fiel à máquina que executará o processo.
A fonte de identidade instalada permanece a modlist: **`nep-1.21.1-0.5.1.jar`**. Índices públicos consultados exibem builds anteriores 0.3.x/0.5.0 em momentos diferentes, portanto o guia não rebaixa o JAR local para essas versões.
## Applied Create — 1.1.7
`appliedcreate-1.21.1-1.1.7.jar`
**Applied Create** é uma bridge estrutural entre AE2 e Create. Ela permite representar e transportar **capacidade cinética/stress** pela infraestrutura ME, conectando uma rede digital a máquinas que dependem de rotação.
O addon inclui **Kinetic Energy Acceptor**, armazenamento próprio de grandezas cinéticas e **Stress P2P Tunnel**, que leva capacidade mecânica por túneis P2P. O **ME Gearbox** faz conversão bidirecional entre a rede e componentes rotacionais, permitindo que uma extremidade digital participe de transmissão cinética sem substituir os conceitos de speed/stress do Create.
A integração também alcança autocrafting: Pattern Providers podem acionar **Mechanical Crafting** e outros processos Create registrados, fazendo uma solicitação ME atravessar etapas digitais e físicas dentro da mesma cadeia de produção.
# 3. Indústria, energia e processamento do Create
## Create: Fluid — 2.1.5
`fluid-2.1.5.jar`
**Create: Fluid** amplia a logística de fluidos do Create com dispositivos de medição, distribuição, envase e controle. A **Mechanical Pipette** manipula quantidades pequenas de líquido; componentes como **Centrifugal Pump**, Copper Tap, valves e Communicating Vessel acrescentam novas formas de mover, interromper ou equilibrar fluxos.
O addon também possui **Can Filler/Cans** para transportar fluidos em recipientes, Fluid Manifest para identificação, **Fluid Atomizer** para transformar líquidos em efeitos/processos próprios e componentes de drenagem como Gutter Outlet. Há controles de redstone e válvulas múltiplas para tornar redes hidráulicas mais programáveis.
A integração com Mechanical Arms e fluid handlers permite incluir esses componentes em linhas automatizadas. Ele continua usando a lógica de fluidos do Create como base; não cria uma rede universal separada de pipes.
## Create Crafts & Additions — 1.7.0
`createaddition-1.7.0.jar`
**Create Crafts & Additions** conecta rotação Create ao ecossistema **FE**. O **Alternator** converte potência cinética em energia; Electric/Servo Motors realizam o caminho inverso; fios e connectors distribuem FE; e o **Accumulator** oferece armazenamento elétrico integrado à estética do Create.
O addon inclui ainda **Rolling Mill** para processamento, Portable Energy Interface para transferência durante contraptions, Redstone Relay, Tesla Coil e Digital Adapter. Algumas linhas produtivas próprias trabalham com biomassa e líquidos, incluindo straw usado na alimentação de Blaze Burners.
Na build `1.7.0`, o Servo Motor e ajustes de compatibilidade com Sable fazem parte da linha NeoForge 1.21.1 instalada. O resultado é uma infraestrutura onde energia elétrica e stress rotacional podem ser convertidos conforme a máquina ou sistema conectado exige.
## Create Diesel Generators — 1.21.1-1.3.15
`createdieselgenerators-1.21.1-1.3.15.jar`
**Create Diesel Generators** adiciona uma cadeia de **petróleo bruto, refino e motores a combustível** integrada ao Create. Pumpjacks e infraestrutura de extração obtêm crude oil; etapas de processamento produzem combustíveis utilizáveis por engines e burners, transformando líquidos industriais em potência cinética.
O conteúdo também inclui diferentes motores/upgrades, Bulk Fermenters, armazenamento e transporte em oil barrels/canisters, além de materiais/processos como compression molding, casting, asphalt e cement. A linha 1.3.x também possui dispositivos industriais adicionais, como sprayers/turrets que trabalham com fluidos compatíveis.
A build instalada é exatamente `createdieselgenerators-1.21.1-1.3.15.jar`, release NeoForge publicada para 1.21.1. A string completa `1.21.1-1.3.15` continua sendo o runtime registrado no catálogo.
## Destroy — 0.4.1
`destroy-1.21.1-0.4.1.jar`
**Destroy** leva Create para **química industrial e engenharia química**. O sistema trabalha com misturas e reações, vats, destilação e separação de substâncias, fazendo composição química e condições de processo participarem diretamente da automação em vez de cada fluido ser apenas uma receita estática.
O port 1.21.1 também inclui infraestrutura como pumpjack/seismograph para depósitos de óleo, ageing barrel, centrifuge, cooler, distillation tower, dynamo/arc furnace, extrusion, glassblowing, sieve, coleta de latex e etapas de circuit lithography. A linha "Chemistry and Carnage" acrescenta explosivos, fogos, álcool e outros produtos derivados dessas cadeias.
Poluição e riscos químicos fazem parte do domínio do mod. Portanto combustíveis e petróleo são apenas uma parte do sistema; o núcleo é a transformação de substâncias por operações químicas automatizáveis.
### Petrolpark's Library — 1.5.6
`petrolpark-1.21.1-1.5.6.jar`
**Petrolpark's Library** é o framework compartilhado usado por Destroy e projetos relacionados. Centraliza infraestrutura técnica para receitas, registries, componentes e sistemas reutilizados pelo ecossistema, sem representar uma progressão tecnológica independente.
A build `1.5.6` é particularmente relevante ao ambiente atual porque inclui correções de **multipart blocks em sublevels Sable**, permitindo que estruturas dos mods consumidores se comportem corretamente quando usadas em espaços físicos móveis.
## Create: Vintage Improvements — SSW Edition — 0.0.0.7
`vintageimprovements-1.21.1-0.0.0.7.jar`
**Vintage Improvements — SSW Edition** é o port NeoForge 1.21.1/Create 6 do Vintage Improvements. Ele adiciona máquinas cinéticas e recipe types voltados à fabricação industrial de **wires, rods, sheets, springs** e outras formas intermediárias de materiais.
Entre os equipamentos estão **Compressor**, com tanque interno e diferentes modos de processo; **Vibrating Table**, inclusive com recipes de unpacking; **Centrifuge**, que trabalha com Basins; **Curving Press** com heads específicos; além de outros processos mecânicos herdados do projeto original. A edição SSW também inclui correções de overflow, resultados incorretos em servidor, laser e curving press na migração para Create 6.
## Create: Blaze Burner Fuels — 1.0.2
`create_blaze_burner_fuels-1.0.2-neoforge-1.21.1.jar`
**Create: Blaze Burner Fuels** amplia a economia térmica do Create com novas fontes para **Blaze Burners**, inclusive alternativas renováveis ao uso contínuo de Blaze Cakes. O addon adiciona itens e receitas próprias de combustível, como pellets/briquetes e outras rotas de materiais processados.
Esses combustíveis servem para estados de heating/superheating usados por mixers, basins e Steam Boiler setups. O mod, portanto, altera a forma de abastecer calor industrial sem adicionar um motor cinético ou uma rede energética paralela.
## Create Metallurgy — 1.0.3
`createmetallurgy-1.0.3-1.21.1.jar`
**Create Metallurgy** acrescenta fundição e ligas ao fluxo do Create. O **Industrial Crucible** derrete materiais em fluidos metálicos; faucets e componentes associados transferem o metal líquido para etapas de casting, permitindo produzir formas metálicas através de uma linha física de fusão e vazamento.
O sistema também aceita combinações de metais para ligas e integra crushed ores e materiais especiais às receitas de fusão. Na linha 1.0.3, correções do Industrial Crucible/Faucet e receita de crushed tungsten fazem parte da build NeoForge 1.21.1 instalada.
## Productive Metalworks — runtime 1.21.1-1.15.1
`productivemetalworks-1.21.1-1.15.1.jar`
**Productive Metalworks** é um sistema de **foundry multiblock** voltado a smelting e casting. Minérios e itens metálicos podem ser derretidos em metais líquidos, armazenados/processados dentro da fundição e depois vazados em moldes para gerar formas e produtos metálicos.
A estrutura multiblock concentra capacidade térmica, armazenamento de fluidos e operações de casting em uma instalação industrial própria. Por isso sua lógica é distinta de receitas de basin do Create: o objeto central é a foundry e o ciclo metal líquido → molde → item.
## Create: Metalwork — 2.0.0
`createmetalwork-2.0.0.jar`
**Create: Metalwork** adiciona novos **crushed ores e fluidos metálicos** para aumentar o rendimento obtido de minérios usando processamento Create. A proposta é inserir etapas adicionais entre minério bruto e ingot, aproveitando moagem, fluidos e receitas mecânicas para extrair mais metal de cada unidade coletada.
É uma expansão de ore processing, não um multiblock de foundry. A build `2.0.0` é a release NeoForge 1.21.1 instalada e inclui correções específicas de compatibilidade da linha atual.
## Create: More Automation — 0.5.2
`create_more_automation-0.5.2-neoforge-1.21.1.jar`
**Create: More Automation** amplia o número de recursos vanilla e Create que podem ser produzidos por linhas mecânicas, acrescentando receitas pensadas para deployers, basins, crushing, mixing e outros processos já existentes no Create.
A build NeoForge 1.21.1 é uma reestruturação significativa em relação à antiga linha 1.20.1: o próprio projeto informa que várias receitas foram alteradas e continuam em desenvolvimento. A versão `0.5.2`, por exemplo, reajustou receitas de Ice e Moss. Seu conteúdo é majoritariamente **recipe-driven**: a expansão vem de novas rotas de fabricação, não de uma segunda família de máquinas.
## Create: Ultimate Factory — 2.2.4
`create_ultimate_factory-2.2.4-neoforge-1.21.1.jar`
**Create: Ultimate Factory** adiciona um conjunto curado de aproximadamente **30 receitas de automação** para recursos que normalmente exigiriam coleta, exploração ou etapas manuais. As receitas utilizam máquinas e sequências do Create para transformar esses recursos em produtos de fábrica de forma repetível.
A proposta é manter essas rotas relativamente balanceadas em custo e complexidade, fazendo a expansão ocorrer pelo desenho de linhas produtivas. Assim como More Automation, seu foco não é uma nova infraestrutura energética ou logística, mas aumentar o catálogo de coisas que uma fábrica Create consegue produzir autonomamente.
## Create: Dreams n' Desires — 2.3a-BETA
`DnDesires-1.21.1-2.3a-BETA.jar`
**Create: Dreams n' Desires** é uma expansão ampla e deliberadamente heterogênea do Create. O projeto adiciona **blocos, mecanismos, ferramentas/equipamentos, worldgen, decoração, utilidades e novas possibilidades de automação**, em vez de se concentrar em uma única cadeia como petróleo ou metalurgia.
Seu catálogo funciona como uma coleção de extensões que procuram manter a linguagem visual e funcional do Create: novos componentes podem participar de contraptions e fábricas, enquanto itens e blocos adicionais ampliam construção e exploração. Por essa natureza, o escopo é melhor entendido como uma expansão geral do ecossistema.
A publicação NeoForge 1.21.1 é classificada como Release no CurseForge, mas o próprio identificador do arquivo continua **`2.3a-BETA`**; o guia preserva essa string sem interpretar a etiqueta externa como uma mudança de versão.
## Create: Factory — 0.7b-1.21.1
`create_factory-0.7b-1.21.1.jar`
**Create: Factory** é uma expansão de **alimentos e produção culinária industrializada**. Ela adiciona ingredientes, comidas e receitas desenhadas para serem processadas com máquinas Create, transformando preparo de doces e outros alimentos em sequências de mixing, filling e demais operações mecânicas.
O foco é conteúdo consumível e suas cadeias de fabricação, não novas fontes de energia. A versão atual `0.7b-1.21.1` é release NeoForge 1.21.1 e inclui correções específicas da linha atual, como o comportamento de potions armazenadas em jars.
## Create: Integrated Farming — 1.3.3b
`create-integrated-farming-1.3.3b.jar`
**Create: Integrated Farming** acrescenta máquinas e blocos voltados à **produção agropecuária automatizada** dentro do Create. A proposta é tratar criação animal, pesca e manejo rural como processos industriais integráveis a belts, spouts e logística mecânica, não apenas automatizar receitas finais de comida.
Entre os sistemas estão **Chicken Roosts**, componentes de alimentação/processamento e mecanismos que aceitam fluidos agrícolas por tags, permitindo consumir óleos vegetais produzidos por outras cadeias industriais compatíveis. A linha atual também possui Fishing Nets e interação com mecanismos montados em sublevels Sable.
A build `1.3.3b` corrige especificamente a coleta de saída de Fishing Nets por **Simulated Auger Shafts** quando a entrada está em painéis coplanares conectados dentro de sublevels Sable. Quando Nether Depths Upgrade estiver presente, esta linha exige versão 3.2 ou posterior desse mod.
## Ratatouille — 1.4.0
`create_ratatouille-1.21.1-1.4.0.jar`
**Ratatouille** expande Create com máquinas próprias de **agricultura e processamento de alimentos**. O **Oven** é um multiblock voltado a cozinhar grandes lotes com aquecimento uniforme; o **Thresher** processa grãos como wheat e pode trabalhar com culturas compatíveis como rice e corn.
No campo, a **Irrigation Tower** mantém áreas amplas de farmland hidratadas, enquanto o **Spreader** acelera o amadurecimento de culturas em área e também pode induzir breeding em animais próximos. O projeto ainda apresenta infraestrutura como Compost Tower associada ao ciclo agrícola.
A ficha individual no Notion chama o mod simplesmente de **Ratatouille**; o guia passa a usar esse mesmo nome, preservando o JAR `create_ratatouille-1.21.1-1.4.0.jar`.
## Create: Food — 2.7.1
`createfood-neoforge-1.21.1-2.7.1.jar`
**Create: Food** é uma expansão culinária de grande porte baseada em Create, com **mais de mil itens relacionados a comida e mais de cem fluidos** no catálogo atual. Ingredientes, alimentos, bebidas e etapas intermediárias são estruturados para participar de processos mecânicos e cadeias automatizadas.
A versão 2.7.1 tornou o conteúdo de compatibilidade cross-mod amplamente **config-driven**: blocos, display blocks, fluidos, itens, efeitos e tooltips podem ser controlados por listas de configuração. Farmer's Delight e outros addons culinários ampliam o conteúdo disponível, enquanto Create permanece a base tecnológica obrigatória.
## Create: Garnished — 2.1.9.2
`garnished-2.1.9.2+1.21.1-neoforged.jar`
**Create: Garnished** adiciona uma cadeia culinária própria centrada principalmente em **nuts/nozes**, novos ingredientes e alimentos processáveis com o maquinário Create. As matérias-primas entram em receitas mecânicas e podem compor linhas automatizadas de produção alimentar.
O addon também detecta integrações com outros mods quando presentes. A build `2.1.9.2` é a release NeoForge 1.21.1 instalada.
## Create: Central Kitchen — 2.6.0
`create-central-kitchen-2.6.0.jar`
**Create: Central Kitchen** é uma camada de **automação culinária e compatibilidade** para Farmer's Delight e seus addons. Ele adapta ingredientes, recipientes e processos de cozinha para que possam ser executados ou movimentados por máquinas do Create, conectando cooking recipes ao mesmo fluxo de mixing, filling e logística usado por outras linhas de fábrica.
O addon é extensível e inclui integrações condicionais com outros mods culinários detectados. Por isso atua como uma central de compatibilidade: cada integração traduz conteúdo externo para operações Create, em vez de adicionar uma culinária independente.
A versão `2.6.0` foi publicada especificamente para **Create 1.21.1-6.0.10**, que é exatamente a versão do Create presente no pack.
## Create: Arm-made Cuisine — 1.0.0
`create_cuisine-1.0.0-mc1.21.1-neoforge.jar`
**Create: Arm-made Cuisine** integra o Mechanical Arm à **Cuisine Skillet** do Cuisine Delight. Um braço pode colocar alimentos ou pacotes contendo ingredientes na skillet aquecida, fazendo a etapa de abastecimento da receita funcionar como parte de uma linha Create.
Quando segura uma **spatula**, o Mechanical Arm executa continuamente o stir-fry enquanto houver alimento na skillet. Com um **plate**, ele consegue retirar a preparação pronta e entregá-la ao próximo ponto configurado, automatizando também o empratamento/saída.
O addon inclui um Ponder específico para explicar a interação com a Cuisine Skillet. Assim, sua função é bastante delimitada: automatiza ações que normalmente exigiriam interação manual do jogador dentro do sistema do Cuisine Delight.
## Create: Fishery Industry — 5.1.1
`createfisheryindustry-5.1.1.jar`
**Create: Fishery Industry** transforma atividades aquáticas em um conjunto de **pesca automática, mergulho, captura de criaturas, processamento de alimentos e logística** no estilo Create. Equipamentos próprios permitem obter recursos da água sem depender exclusivamente da fishing rod manual e encaminhá-los para processamento mecânico.
O escopo inclui exploração subaquática e captura, além da transformação e transporte dos produtos obtidos. Dessa forma, a cadeia começa na aquisição automatizada de recursos aquáticos e termina em armazenamento/processamento dentro da fábrica Create.
A build `5.1.1` é a release NeoForge 1.21.1 atual da linha 5.x instalada.
## Create Aquatic Ambitions — 2.0.4
`create_aquatic_ambitions-1.21.1-2.0.4.jar`
**Create Aquatic Ambitions** adiciona **bulk processing** voltado a recursos aquáticos e costeiros. A proposta é fazer prismarine, coral, cobre e materiais relacionados entrarem em cadeias mecânicas reproduzíveis, reduzindo dependência de coleta manual ou farms específicas para cada recurso.
As receitas utilizam a infraestrutura de processamento do Create em escala, convertendo materiais e condições aquáticas em rotas de produção contínua. O addon também inclui itens/equipamentos associados ao tema oceânico, mas sua função central é ampliar o conjunto de recursos renováveis ou processáveis industrialmente.
## Create: Deep Dark — 3.0.2
`create_deep_dark-3.0.2-neoforge-1.21.1.jar`
**Create: Deep Dark** adiciona uma camada de **endgame baseada no Deep Dark**. A exploração de Ancient Cities e território do Warden fornece recursos raros que entram em receitas e equipamentos processados pelas mecânicas do Create.
O addon acrescenta materiais, itens e gear de alto nível cuja fabricação utiliza máquinas/etapas Create, ligando exploração perigosa a uma progressão tecnológica posterior. Assim, o Deep Dark deixa de ser apenas uma fonte de loot vanilla e passa a alimentar novas cadeias de crafting industrial.
## Create: Recycle Everything Continued — runtime 1.1
`create_recycle_everything-2.1.0.jar`
**Create Recycle Everything (Continued)** cria uma extensa tabela de **receitas de desmontagem/reciclagem**, principalmente usando Crushing Wheels, para devolver matérias-primas a partir de itens já fabricados. A versão 2.1.0 acrescenta reciclagem de várias peças de armadura e amplia cobertura de itens vanilla, Create e integrações condicionais.
O fluxo é deliberadamente parcial: um item processado retorna componentes ou recursos aproveitáveis, permitindo reinserir gear e blocos obsoletos na fábrica em vez de simplesmente descartá-los. Algumas receitas são habilitadas apenas quando mods integrados estão presentes.
A identidade continua dupla: o filename/publicação é `create_recycle_everything-2.1.0.jar`, mas o metadata runtime local declara `1.1`; o guia preserva ambos.
## Create: Rock & Stone — 1.3.1-1.21.1-6
`create_rns-1.3.1-1.21.1-6.jar`
**Create: Rock & Stone** adiciona **depósitos subterrâneos próprios** que representam reservas minerais exploráveis por maquinário Create. O **Deposit Scanner** localiza essas reservas; **Miner Bearing** e **Mine Head** formam a infraestrutura usada para extrair recursos continuamente do depósito.
Os depósitos podem ser finitos ou configurados para comportamento diferente conforme o pack, e sua composição pode incluir materiais vanilla ou de outros mods. A definição é data-driven, com suporte a datapacks/KubeJS, permitindo criar novos tipos de depósito sem adicionar outro sistema de mineração.
Integrações com Jade e JourneyMap ajudam a identificar e acompanhar as reservas detectadas, tornando prospecção e extração partes distintas da mesma cadeia.
## Create Cobblestone — 1.4.12+neoforge-1.21.1-144
`createcobblestone-1.4.12+neoforge-1.21.1-144.jar`
**Create Cobblestone** substitui geradores vanilla de água/lava por um **bloco gerador movido a Stress Units**. A produção de cobblestone passa a depender da rede cinética, podendo ser ligada, escalada e posicionada como outra máquina Create sem manter fluidos atualizando blocos continuamente.
A proposta também é reduzir problemas de desempenho e construção causados por geradores convencionais dentro de contraptions ou fábricas densas. O recurso gerado pode alimentar automaticamente crushing, washing e cadeias posteriores de materiais de construção.
O runtime é preservado integralmente como `1.4.12+neoforge-1.21.1-144`.
## Create: Mechanical Spawner — runtime 1.3.1-6.0.10
`create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`
**Create: Mechanical Spawner** adiciona um spawner acionado por **potência rotacional**. Em vez de depender de um cage vanilla encontrado no mundo, o bloco utiliza stress/speed e recursos configurados para produzir mobs dentro de uma linha Create.
O sistema suporta **Random Spawn Fluid**, cuja seleção pode depender do bioma, e fluidos específicos associados a criaturas. Configurações controlam consumo de stress, velocidade, alcance e condições de spawn, permitindo que custo e produtividade façam parte da engenharia da fábrica.
A publicação/filename da build é `1.3.2-6.0.10`, enquanto o metadata local declara `1.3.1-6.0.10`; essa divergência permanece registrada sem alterar o JAR.
## Create Confectionery — runtime 1.1.3.
`create-confectionery1.21.1_v1.1.3b.jar`
**Create Confectionery** transforma confeitaria em uma cadeia produtiva ligada ao Create. A matéria-prima de cacau passa por etapas próprias para gerar **Crushed Cocoa, Cocoa Butter e Cocoa Powder**, que alimentam variedades como chocolate branco, preto e ruby, além de caramel e produtos derivados.
O catálogo inclui gingerbread, marshmallows, chocolate candies, honey candy, hot chocolate e Full Chocolate Bars, além de versões glaceadas de alimentos. Alguns produtos possuem efeitos próprios: a documentação cita **Stimulation**, Rest, Saturation e Speed, enquanto Hot Chocolate concede regeneração. A linha 1.21.1 também inclui Candy Cane Tools e receitas reversíveis para converter determinados blocos/barras de chocolate novamente em formas utilizáveis.
A build instalada é `create-confectionery1.21.1_v1.1.3b.jar`; o metadata runtime local declara literalmente `1.1.3.`. A release `v1.1.3b` é a atual para NeoForge 1.21.1 e inclui hotfix da receita de chocolate-glazed marshmallow.
## Create Slice & Dice — 4.3.3
`sliceanddice-4.3.3-neoforge.jar`
**Create Slice & Dice** integra principalmente Farmer's Delight ao Create por automação de preparação e cozinha. O **Slicer** registra automaticamente receitas de Cutting Board e executa o corte usando a ferramenta instalada na própria máquina; knives e axes são permitidos por padrão, e a tag `sliceanddice:allowed_tools` permite ampliar esse conjunto.
Receitas do **Cooking Pot** do Farmer's Delight são traduzidas para **heated mixing**, permitindo executar preparação culinária em linhas mecânicas. O addon também possui **Sprinkler**, alimentado por fluidos via pipes: água simula chuva na área, lava causa dano de fogo, poções aplicam seus efeitos e Liquid Fertilizer produz efeito de bonemeal sobre blocos/culturas.
A build `4.3.3` é a release NeoForge 1.21.1 instalada; ela corrige o comportamento de potion sprinkler e expõe `SprinklerProvider` para integrações.
# 4. Logística e fábricas conectadas
## Create Contraption Terminals — 1.4.0
`createcontraptionterminals-1.21-1.4.0.jar`
**Create Contraption Terminals** faz os terminais do **Tom's Simple Storage** continuarem funcionais depois que a estrutura é montada como contraption Create. Um terminal colocado na construção consegue acessar o **inventário inteiro da contraption assemblada**, preservando a experiência de storage centralizado durante movimento.
Isso permite usar trens e outras estruturas móveis como depósitos acessíveis por terminal em vez de abrir cada container individualmente. Contraptions montadas antes da instalação do addon precisam ser remontadas para que os terminais sejam registrados corretamente.
## Create: Ender Transmission — 2.1.1-1.21.1
`createendertransmission-2.1.1-1.21.1.jar`
**Create: Ender Transmission** adiciona links de longa distância para **itens, fluidos e energia**, incluindo conexões entre dimensões. Os endpoints formam pares/canais que transferem recursos sem exigir uma linha física contínua de belts, pipes ou cabos atravessando o trajeto.
O addon também possui **chunk loader alimentado por potência cinética**, permitindo manter pontos de uma infraestrutura remota ativos como parte da rede Create. Assim, seu escopo combina teletransporte de recursos e manutenção operacional de instalações distantes.
A publicação usa `v2.1.1`, enquanto o runtime local registra `2.1.1-1.21.1`; o guia mantém a string completa instalada.
## Create: Mobile Packages — 0.7.7
`create_mobile_packages-1.21.1-0.7.7.jar`
**Create: Mobile Packages** cria uma rede logística de entregas baseada em **Bee Ports e Robo Bees**. Ports são vinculados a uma Logistics Network e enviam packages para outros ports ou diretamente para jogadores pertencentes à mesma rede; os Robo Bees funcionam como couriers físicos que carregam as encomendas.
O **Portable Stock Ticker** permite consultar remotamente um Stock Network, pedir itens, pesquisar com JEI e enviar itens do próprio inventário para um endereço. O **Mobile Packager** cria ou edita packages de até nove stacks fora de uma instalação fixa.
Bee Ports também podem puxar packages de inventários adjacentes ou, com redstone, descarregar para eles. O resultado é uma camada de entrega móvel sobre o sistema de packages/stock do Create, com destinatários, redes e couriers persistentes.
## Create Stock Bridge — 0.2.0
`createstockbridge-1.21.1-0.2.0.jar`
**Create Stock Bridge** liga o **Create Stock Network** ao Applied Energistics 2 por um AE Stock Bridge. Itens presentes no estoque Create são expostos ao AE2 como itens solicitáveis/craftable, permitindo que a interface ME enxergue a disponibilidade logística do lado Create.
No sentido inverso, um **Packager conectado ao bridge** pode solicitar itens armazenados na ME Network para atender pedidos do sistema Create. A versão 0.2.0 também corrige cenários de envio parcial, Create Promises, Re-Packager e prioridade controlada por redstone.
Essa integração trabalha especificamente com stock requests/packages; ela não substitui Applied Create, que conecta AE2 a stress e processamento cinético.
## Create: Transmission! — runtime 1.2.2+neoforge-create6-1.21.1
`createtransmission-1.2.2+neoforge-create6-1.21.1.jar`
**Create: Transmission!** adiciona um único componente mecânico principal: a **Transmission Chain**. Ela transmite rotação ao longo de uma linha e pode alimentar belts de forma compacta, evitando montar Encased Chain Drives apenas para levar potência a trechos logísticos.
O bloco existe como alternativa de layout: mantém a lógica cinética do Create, mas oferece outra geometria para distribuição de rotação em fábricas onde espaço e leitura visual importam. A build instalada preserva o runtime completo `1.2.2+neoforge-create6-1.21.1`.
## Create: Filters Anywhere — 2.6.0
`createfiltersanywhere-1.21.1-2.6.0.jar`
**Create: Filters Anywhere** leva **List Filters e Attribute Filters** do Create para inventários, slots de filtro e máquinas de outros mods. Em vez de cada sistema externo depender apenas de sua própria sintaxe de whitelist/blacklist, interfaces compatíveis passam a aceitar filtros Create e os atributos que eles representam.
A build do pack possui integrações específicas com **AE2, Tom's Simple Storage, Sophisticated Core, Refined Storage, Oritech, Modular Routers e LaserIO**, entre outras. O addon também adiciona atributos e QoL aos próprios filtros, tornando-os uma linguagem de seleção compartilhada entre diferentes sistemas logísticos.
## Create: Pattern Schematics — 2.0.10
`create_pattern_schematics-2.0.10.jar`
**Create: Pattern Schematics** adiciona schematics pensados para **repetição automática de um padrão**. Em vez de copiar manualmente a mesma seção várias vezes, o padrão pode ser aplicado ao longo de contraptions, trains, gantries e outros eixos de movimento para construir sequências repetitivas.
Isso transforma módulos como trechos de ponte, parede, via ou estrutura industrial em unidades replicáveis. A lógica é diferente do Schematicannon comum: o foco não é apenas imprimir uma área salva, mas definir como a unidade se repete ao longo de um percurso.
## Create: Fast Schematic Cannon — runtime 1.4.1-neoforge