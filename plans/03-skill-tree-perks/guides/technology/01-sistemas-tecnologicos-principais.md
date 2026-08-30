<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

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
