<!-- Snapshot canônico do Notion: GUIA COMPLETO — Mods de Tecnologia | NeoForge 1.21.1
Fonte: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff
Parte 2/3. Continuação de part-01.md; continuar em part-03.md. -->

`CreateFastSchematicCannon-1.4.1-neoforge-1.21.1.jar`
**Create: Fast SchematicCannon** torna a velocidade de impressão do cannon configurável. O parâmetro de **prints per tick** permite executar múltiplas colocações em cada tick de trabalho, multiplicando a taxa de construção sobre o delay normal do Create.
O addon também permite configurar uma lista de **blocos que o Schematicannon não pode imprimir/quebrar**, protegendo componentes específicos durante construção automática e evitando casos como o chamado void-boiler issue. Portanto ele atua tanto em throughput quanto em regras de segurança de impressão.
O runtime local é `1.4.1-neoforge`; a publicação externa abrevia a release para `v1.4.1`.
## Create: Schematic Checker — runtime 2.27.45-6.0
`createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar`
**Create: Schematic Checker** é uma camada server-side de **proteção e sanitização de schematics**. Ao receber um schematic enviado pelo cliente, ele examina NBT e outros dados antes da impressão, bloqueando ou corrigindo padrões conhecidos que poderiam causar crashes, lag, duplicação ou inserir conteúdo malicioso no mundo.
O mod também possui reparos específicos para dados de blocos e addons usados em schematics. A release `2.27.45` para Create 6.0 inclui ajustes recentes envolvendo Copycats e Quark, além da manutenção contínua das regras de validação.
`TorqueAPI 1.2.2` vem incorporada dentro desse JAR como dependência interna e não corresponde a uma entrada top-level separada da modlist.
# 5. Física, veículos e Create Aeronautics
## Create: Aeroworks — 1.5.0
`aeroworks-1.5.0.jar`
**Create: Aeroworks** adiciona uma camada de **aviônica e controle** sobre contraptions físicas do Aeronautics. Joysticks, levers, pedals, yokes, throttle quadrants, steering wheels e desks fornecem input do piloto; **gyroscopes** ajudam a medir e estabilizar orientação; e servos convertem sinais de controle em saídas rotacionais capazes de acionar partes móveis do veículo.
O sistema modular permite construir cockpits por canais de entrada e saída, em vez de depender de um bloco único de direção. A versão `1.5.0` amplia essa linguagem com **Control Stand e Copycat Control Stand**, variantes de cobre dos módulos, steering wheels de cobre e nas 16 cores, um terceiro socket de pedal e compatibilidade **Drive-By-Sable**. Assim, pitch, yaw, roll, direção e superfícies móveis podem ser organizados como uma rede física de comandos dentro do veículo.
## Create: Radars — 0.4.9.4-1.21.1
`create_radar-0.4.9.4-1.21.1.jar`
**Create: Radars** fornece infraestrutura de **surveillance, detection, tracking e weapon control**. Blocos de radar detectam alvos no espaço ao redor, mantêm informações de posição/movimento e disponibilizam esses dados para displays e sistemas de controle compatíveis.
A informação de rastreamento pode alimentar mecanismos de mira/armamento em integrações externas. Assim, o mod-base trata de detectar e representar alvos; addons como Create Aero Radar usam esses dados dentro de veículos físicos e sistemas de armas.
## Create: Radiologistics — 1.1.1 beta
`CreateRadiologistics-1.1.1.jar`
**Create: Radiologistics** adiciona comunicação **wireless** e um sistema de **lógica programável por nós** para máquinas e contraptions Create. Sensores coletam estados, nós processam/encaminham informações e dispositivos de saída transformam esses sinais em ações mecânicas.
Entre os componentes da linha atual estão sensores, servo motor, ferramentas de configuração e nós usados para construir circuitos de controle distribuídos sem passar redstone física por toda a estrutura. Isso é especialmente útil em contraptions móveis, onde controle e telemetria podem viajar por rádio.
A build `1.1.1` permanece beta NeoForge 1.21.1; a classificação descreve maturidade do projeto, enquanto o JAR/runtime estão confirmados.
## Create Aero Radar — 0.1.1-1.21.1
`create_aero_radar-0.1.1-1.21.1.jar`
**Create Aero Radar** conecta **Create Aeronautics, Create: Radars e Create Big Cannons**. Ele traduz dados de detecção/rastreamento do radar para o espaço físico da contraption, permitindo que armamentos montados em aeronaves ou outros veículos utilizem informação coerente de alvo enquanto toda a estrutura se move e gira.
A integração cobre orientação, mira e convergência de armas sobre contraptions físicas. O radar continua sendo fornecido pelo mod Create: Radars e o armamento por Big Cannons; este addon é a ponte espacial/veicular entre esses sistemas.
## Create Aeronautics: Automated Logistics — 0.6.2
`create_aeronautics_automated_logistics-0.6.2.jar`
**Create Aeronautics: Automated Logistics** permite gravar e repetir **rotas de airships**, transformando veículos Aeronautics em transportadores autônomos. O jogador constrói uma rede de estações, registra o percurso e configura pontos de docking; depois o veículo pode repetir esse trajeto sem pilotagem manual contínua.
A automação é pensada para continuar funcionando mesmo quando ninguém está próximo, permitindo linhas logísticas persistentes entre bases. O projeto também mantém compatibilidade experimental com trens Simurail, mas o núcleo da build `0.6.2` são rotas, estações e docking automatizado de airships Aeronautics.
## Create Aeronautics: FTB Chunks — 1.1.1
`create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.1.1.jar`
**Create Aeronautics: FTB Chunks** faz ships/sublevels Sable participarem das regras territoriais do **FTB Chunks e FTB Teams**. Um **Contraption Claim Block** colocado no veículo abre uma interface própria para associar a contraption ao time do jogador.
Pela interface é possível **claim/unclaim os chunks ocupados pelo ship** e ativar force-loading para mantê-los carregados quando jogadores estiverem distantes. Assim, proteção, permissões de equipe e persistência de carregamento acompanham a estrutura física em vez de só existir no terreno estático do level principal.
## Create Aeronautics: Transmission & Linkage — 0.2.7
`create_aeronautics_transmission_linkage-0.2.7.jar`
**Create Aeronautics: Transmission & Linkage** adiciona componentes para transmitir **movimento cinético e deslocamento mecânico entre partes físicas articuladas**. Universal joints permitem conectar eixos que não permanecem perfeitamente alinhados; kinetic converters adaptam transferência entre contextos diferentes; e hydraulic rods introduzem extensão/retração controlada.
Essas peças tornam possível construir mecanismos articulados dentro ou entre physics contraptions, como juntas, braços, superfícies móveis e acoplamentos cuja geometria muda durante o funcionamento. A versão 0.2.7 é a release NeoForge 1.21.1 atual dessa linha.
## Create Aeronautics: Toolgun — 0.3.6
`create_aeronautics_toolgun-0.3.6.jar`
**Create Aeronautics: Toolgun** introduz um workflow de engenharia inspirado em toolguns para manipular contraptions físicas. O sistema inclui **blueprints** para salvar e imprimir crafts físicos e ferramentas para apagar, selecionar ou modificar estruturas sem desmontá-las manualmente bloco a bloco.
A linha atual também possui **magnetic field guns** voltadas a mover/manipular objetos físicos. O objetivo é fornecer ferramentas de edição adequadas à escala de ships e veículos Aeronautics, onde operações de construção convencionais se tornam pouco práticas.
## Create Aeronautics: Throwable Rope Connector — 0.4.3
`create_aeronautics_throwable_rope_connector-0.4.3.jar`
**Throwable Rope Connector** transforma rope connectors do Aeronautics em uma ferramenta prática de **docking e conexão à distância**. O jogador pode lançar a conexão em vez de precisar posicionar os dois pontos manualmente lado a lado, permitindo prender ship↔dock ou ship↔ship durante manobras.
A versão `0.4.3` inclui correção específica para a mira do **Mounted Rope Launcher em contraptions Sable**, especialmente em disparos de um veículo físico contra outro. O addon atua sobre vínculos/docking; não é o mesmo sistema de escalada de Climbable Ropes.
## Create Aeronautics: Copycat Wing — 1.0.2
`CreateAeronauticsCopycatWing-1.21.1-1.0.2.jar`
**Create Aeronautics: Copycat Wing** registra blocos do **Copycats+** como partes válidas de asa para o modelo aerodinâmico do Aeronautics. Isso permite construir superfícies de lift com formatos e materiais visuais de Copycat sem perder a função física de wing.
A função é especificamente aerodinâmica. `aerocopycats`, presente separadamente no stack, trata propriedades físicas/massa de blocos Copycat em Sable; Copycat Wing trata reconhecimento desses blocos como superfícies que geram sustentação.
## Create Aeronautics x Curios API Compat — runtime 2.0
`createaeronauticscurios-neoforge-1.21.1-2.2.jar`
**Create Aeronautics x Curios API Compat** faz equipamentos utilitários do Aeronautics funcionarem quando equipados em slots **Curios**, em vez de exigir ocupação dos slots vanilla correspondentes. Os principais casos documentados são **Aviator Goggles** e **Linked Typewriter**.
A integração preserva o comportamento dos goggles enquanto equipados e permite ativação/uso remoto do Linked Typewriter pelo slot Curios. O filename/publicação é `2.2`, mas o metadata runtime carregado declara `2.0`; o catálogo mantém as duas strings.
## Create Aeronautics: Harness — 1.0.1
`CreateAeronauticsHarness-1.21.1-1.0.1.jar`
**Create Aeronautics: Harness** permite anexar e **carregar objetos físicos/contraptions Aeronautics nas costas do jogador**. O objeto transportado permanece associado ao harness, criando uma forma portátil de movimentar componentes físicos pequenos sem pilotá-los ou desmontá-los.
Há integração com **Create Big Cannons**, permitindo configurações transportáveis envolvendo canhões/objetos compatíveis. A função é transporte de physics objects pelo jogador; não é um sistema de cinto de segurança ou fixação do jogador ao veículo.
## Create Deep Seas — 2.2.4
`create_submarine-2.2.4.jar`
**Create Deep Seas** estende o modelo de veículos construídos com blocos do Aeronautics/Sable para **barcos e submarinos**. A embarcação é tratada como uma contraption física na qual o jogador pode instalar mecanismos e navegar, em vez de uma entidade de barco com inventário fixo.
O domínio acrescentado é o movimento e a operação **sobre e sob a água**, permitindo projetos submersíveis construídos na mesma lógica modular dos airships. O JAR `2.2.4` é a release NeoForge 1.21.1 instalada e publicada para a linha atual.
## Create: Deep Seas - Lava Fix — 1.0.1
`submarinefix-1.0.1.jar`
**Create: Deep Seas - Lava Fix** corrige um caso específico do sistema de submarinos: mesmo após um compartimento selado remover corretamente a lava de seu interior, o jogador ainda podia receber dano de lava/fogo e manter o overlay de chamas porque as verificações vanilla não reconheciam o estado do interior do submarino.
O patch suprime esses danos e efeitos quando o jogador está realmente protegido pela estrutura selada. Ele cobre situações dentro do compartimento, em **hatches/doorways**, sentado em Create Seats, usando ladders e durante entrada/saída na superfície da lava; também corrige fire ticks persistentes depois de um rompimento do casco seguido de nova selagem.
O mod não adiciona submarinos nem altera a física principal de Deep Seas. Sem o mod-alvo ele simplesmente não executa sua correção. O JAR `submarinefix-1.0.1.jar` é a release NeoForge 1.21.1 instalada.
## Create Aeronautics: Portable Engine Liquid Fuel — 2.0.0
`portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar`
**Create Aeronautics: Portable Engine Liquid Fuel** adapta Portable Engines do ecossistema Aeronautics para consumir **combustíveis líquidos**. Em vez de limitar a propulsão portátil aos recursos originais do componente, engines podem ser abastecidos a partir de fluidos de combustível fornecidos por cadeias industriais compatíveis.
Isso conecta diretamente veículos às infraestruturas de tanks, pumps e refino do pack: o combustível pode ser produzido em fábrica, armazenado como fluido e então utilizado a bordo. O addon é uma bridge de fuel handling, não um motor físico separado.
## Create Propulsion: Simulated — 1.1.5
`createpropulsion-1.1.5.jar`
**Create Propulsion: Simulated** adiciona thrusters que aplicam **força e torque reais em pontos da contraption Sable/Aeronautics**. A linha inclui thrusters convencionais a combustível, **Ion Thrusters alimentados por FE**, Creative Thrusters e, na versão 1.1.5, **Solid Fuel Thruster** com combustíveis definidos por dados.
**Tilt Adapters** e a versão avançada permitem alterar direção/orientação do empuxo, tornando possível construir mecanismos de vectoring em vez de manter todos os motores fixos. A propulsão interage diretamente com massa e orientação do physics object, de modo que posição do thruster influencia o comportamento do veículo.
A 1.1.5 também inclui otimizações de sincronização/renderização e amplia a configuração data-driven de combustíveis.
## Create Aeronautics: Gadgets & Gizmos — 1.1.3
`createthrusters-bundled-V1.1.3.jar`
**Create Aeronautics: Gadgets & Gizmos** é uma expansão ampla de componentes para contraptions físicas. A linha inclui vários tipos de **thrusters**, Contraption Controller, Industrial Alternator/Motor, Smart Gearbox, Variable Transmission, servo/Vector/Aileron Bearings, analogue joystick, Bi-Directional Gearshift e componentes de movimentação como Claw, Physics Gantry e Powered Zipline.
O addon também acrescenta infraestrutura de bordo: Advanced Data Link, cabos, Fuel Oxidizer, Entity Launcher, Shipping Manifest e ferramentas como Physics Staff/Goggles, Network Linker e Configuration Clipboard. Há upgrades específicos para propulsion, smelting, smoking e haunting.
Vários dispositivos expõem integração com **CC:Tweaked** como peripherals, permitindo ler ou comandar thrusters, bearings, gearboxes, joysticks e controllers por computador. O metadata runtime usa `Create Gadgets & Gizmos`, enquanto o projeto público mantém o nome `Create Aeronautics: Gadgets & Gizmos`; o JAR atual é o bundle `1.1.3`.
## AeroStar — 1.0.1
`AeroStar-1.0.1.jar`
**AeroStar** é a compatibilidade entre **Create Aeronautics e Create: Northstar Redux** voltada a viagens espaciais com physics ships. O **Dimensional Drive** permite transferir uma contraption Aeronautics entre planetas/órbitas, preservando a estrutura física e o estado necessário para continuar operando depois da mudança de dimensão.
A integração procura manter componentes, assentos e dados do ship durante a transferência em vez de reconstruir a nave como uma entidade nova. Também acrescenta recursos voltados a **space stations**, fazendo uma nave construída com blocos participar do sistema planetário/orbital do Northstar.
O projeto é continuação corrigida e expandida de uma compatibilidade anterior entre Northstar e Aeronautics; a build `1.0.1` é a release instalada no pack.
## Climbable Ropes for Create Aeronautics — 2.1.1
`climbable_ropes-2.1.1.jar`
**Climbable Ropes for Create Aeronautics** torna cordas verticais do stack Aeronautics/Simulated realmente escaláveis pelo jogador, inclusive as cordas usadas pelo **Plunger Launcher**. Com as mãos vazias, o jogador pode agarrar a corda, usar W/S para subir ou descer, Space para saltar para fora e Sneak para soltá-la.
O movimento inclui animações próprias de escalada e comportamento de mantle/salto associado à corda. A versão `2.1.1` corrige casos em que a animação podia permanecer incorreta depois de respawn ou troca de dimensão e ajusta poses/renderização durante cancelamento do estado de climbing.
O JAR também possui dependências internas usadas pela animação, mas isso não altera a presença dos JARs top-level atuais do pack.
## Aeronautics Camera Sync — 1.3.6
`aero_cam_sync-1.3.6.jar`
**Aeronautics Camera Sync** sincroniza a orientação da câmera do jogador com a **rotação dinâmica da contraption física** onde ele está. Sem essa camada, o veículo pode inclinar, rolar ou girar enquanto a câmera continua orientada principalmente pelo frame do mundo, produzindo sensação visual desconectada do movimento.
O addon aplica a transformação do ship/sublevel à visão do jogador para que cockpit, interior e horizonte respondam de forma coerente durante pitch, yaw e roll. Sua função é estritamente de apresentação/câmera sobre a física existente; ele não altera forças ou controles do veículo.
## Create Aeronautics: Weight — 1.2.0
`weight-1.2.0.jar`
**Create Aeronautics: Weight** acrescenta massa dinâmica a elementos que normalmente não contribuem integralmente para a física de uma contraption. Jogadores, mobs, inventários, containers, fluid tanks, armaduras e carga podem aumentar o peso da estrutura Sable conforme configurações e mappings próprios.
Saltos e aterrissagens também aplicam força ao deck proporcionalmente à intensidade. As fontes de massa de inventário, conteúdo de containers, fluidos, armaduras e mob gear são configuráveis separadamente e os mappings podem ser recarregados por JSON/comandos. Não é um sistema de encumbrance: o peso é aplicado à **simulação física da contraption**.
# 6. Sable e infraestrutura de sublevels
## Dimensional Sable — 1.0.5
`dimensional_sable-1.0.5.jar`
**Dimensional Sable** fornece a infraestrutura para **teleportar physics objects do Sable entre dimensões**. Em vez de mover apenas jogadores ou entidades vanilla, a operação transfere o objeto físico/sublevel correspondente, permitindo que contraptions compatíveis continuem existindo após a mudança dimensional.
Create Aeronautics é um dos principais consumidores desse comportamento. A versão `1.0.5` foi atualizada para a linha Sable 2.0.3+ e funciona como API/ferramenta de transporte interdimensional de objetos físicos, não como um teleporter geral de gameplay para qualquer bloco.
## Sable Beyond — 0.5.0
`sablebeyond-neoforge-1.21.1-v0.5.0.jar`
**Sable Beyond** amplia a quantidade de mecânicas vanilla/Create que continuam funcionando de forma física entre o mundo principal e sublevels. **Mechanical Arms** podem interagir entre espaços; Encased Fans podem empurrar/puxar sublevels; e Water Wheels podem gerar força de propulsão em situações compatíveis.
O addon também introduz **massa dinâmica**: containers, tanks, spouts, drains e basins podem alterar a massa do objeto físico conforme o conteúdo armazenado. Fórmulas e fatores de massa de entidades são configuráveis.
Fogo pode se propagar entre sublevel e mundo principal, lava pode causar ignição, fluidos correntes podem aplicar força à estrutura e basins podem interagir com líquidos externos. Várias dessas extensões são configuráveis e algumas ficam desabilitadas por padrão, permitindo modular quanto da simulação adicional é aplicada.
## Sable: Physics Compat — 1.3.0
`sablephysicscompat-1.3.0.jar`
**Sable: Physics Compat** fornece tags e parâmetros físicos para **blocos adicionados por outros mods**, fazendo-os participar corretamente de massa, flutuação, fricção, elasticidade, airtightness e outras propriedades usadas pelo Sable.
A versão `1.3.0` amplia cobertura para Architect's Palette e diversas famílias Macaw's, além de Storage Drawers. Também expande propriedades para blocos de mods como Supplementaries, Aether, Quark, Ice and Fire e Cataclysm, entre outros.
Sem essa camada, muitos blocos modded ainda poderiam ser montados, mas tenderiam a usar defaults genéricos ou propriedades incompletas. O addon funciona como uma base de dados física de compatibilidade, não como um novo solver.
## Sable: True Impact — 0.5.7-delta
`true_impact-0.5.7-delta.jar`
**Sable: True Impact** transforma colisões de objetos físicos e veículos Sable/Create Aeronautics em **eventos de impacto com dano e destruição**. A energia da colisão pode ser convertida em dano a entidades e estruturas, além de provocar cracking/fratura e destruição de terreno conforme a intensidade e as regras configuradas.
Isso acrescenta consequências físicas às contraptions: massa, velocidade e impacto deixam de ser apenas movimento visual e passam a interferir no mundo. A build instalada `0.5.7-delta` é uma beta NeoForge 1.21.1; o caráter experimental e destrutivo descreve a mecânica/maturidade, não incerteza sobre o JAR atual.
## Sable Create Addition Compat — 0.1.13
`sable_createaddition_compat-0.1.13.jar`
**Sable Create Addition Compat** adapta componentes de **Create Crafts & Additions** aos sublevels físicos. Fios podem conectar mundo principal↔sublevel ou sublevel↔sublevel, com renderização e transformação de endpoints ajustadas ao movimento; conexões também podem romper quando o deslocamento excede a condição suportada.
A compatibilidade inclui renderização e comportamento de block entities como **Accumulator, Electric/Servo Motor, Portable Energy Interface, Rolling Mill e Liquid Blaze Burner**, para que continuem aparecendo e operando corretamente quando a estrutura se move.
A versão `0.1.13` removeu um pin rígido de versão e atualizou suporte a Sable mais recente. A release é beta, mas o escopo funcional da bridge está definido.
## Sable: Stuff&Additions Compatibility — 1.0.3
`SableStuffAdditionsCompat v1.0.3-1.21.1.jar`
**Sable: Stuff&Additions Compatibility** adapta o conteúdo de **Create: Stuff & Additions** para funcionar dentro de Sable/Simulated/Aeronautics. A bridge corrige comportamentos de itens, componentes e block entities que originalmente assumem coordenadas e contexto do level principal.
Seu objetivo é permitir que o equipamento tecnológico de Stuff & Additions continue funcional em physics contraptions sem criar versões alternativas desses itens. A build `1.0.3` é a release NeoForge 1.21.1 instalada.
## VS / Sable Hose Connectors — 0.1.8
`VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`
**VS / Sable Hose Connectors** fornece conexões de transporte entre estruturas físicas e o mundo, com suporte tanto a Valkyrien Skies quanto a **Aeronautics/Sable**. No ramo Sable, hose connectors mantêm fluxos através da fronteira entre um sublevel móvel e a infraestrutura externa.
A versão 0.1.8 também permite rotacionar Hose Connectors e Electric Wire Connectors. A bridge resolve transferência entre veículos/ships e instalações fixas; não cria um sistema de armazenamento independente.
## Sable Dynamic Lights — 2.0.1
`sable-dynamic-lights-1.21.1-2.0.1.jar`
**Sable Dynamic Lights** é um fork/integração de iluminação dinâmica voltado a **Create contraptions e sublevels Sable**. Fontes de luz transportadas por estruturas móveis continuam iluminando o ambiente a partir de sua posição física real, em vez de ficarem visualmente presas às coordenadas originais do level principal.
Isso cobre contraptions, veículos e outros espaços móveis que normalmente não são compreendidos por sistemas de dynamic lighting convencionais. O mod atua na apresentação luminosa e não altera a iluminação permanente dos blocos nem a física da estrutura. A build `2.0.1` é a beta NeoForge 1.21.1 instalada.
## SableMassView — 1.0.0
`sablemassview-1.0.0.jar`
**SableMassView** é uma ferramenta client-side de inspeção. Com **tooltips avançados (****`F3+H`****)** ativados, ela mostra a **massa física atribuída pelo Sable a cada bloco** diretamente na tooltip do item/bloco.
O addon não recalcula nem modifica a massa; apenas expõe um valor já utilizado pelo physics engine. Isso torna visível uma propriedade que influencia peso total e comportamento de contraptions, permitindo consultar materiais sem precisar inferir seus parâmetros físicos.
## Jade Sable Compat — 1.3.0
`sablejade-1.3.0.jar`
**Jade Sable Compat** corrige o ray tracing/seleção de blocos do Jade quando o alvo pertence a um **sublevel ou construção móvel Sable**. Em vez de o overlay identificar por engano o bloco situado nas mesmas coordenadas do level principal, a integração refaz a seleção usando a transformação do sublevel.
Com isso, título, ícone e dados exibidos pelo Jade correspondem ao bloco realmente sob a mira dentro da contraption. A versão `1.3.0` é client-side e é a release NeoForge 1.21.1 atual.
## Waystones: Sable — 1.0.7
`waystonessable-1.0.7.jar`
**Waystones: Sable** faz Waystones reconhecerem corretamente **SubLevels móveis**. A bridge corrige validação de destino, cálculo de distância, sincronização cliente-servidor e transformação de coordenadas quando uma waystone está dentro de uma contraption ou quando o teleporte aponta para um sublevel.
O objetivo é preservar a semântica normal do Waystones mesmo quando o ponto de viagem não pertence ao grid estático do mundo. A build `1.0.7` é a release NeoForge 1.21.1 atual dessa integração.
## Sound Physics Aeronautics — runtime 1.3.0.2
`sound-physics-aeronautics-1.4.0.jar`
**Sound Physics Aeronautics** é um fork de Sound Physics Remastered adaptado ao espaço transformado de **Sable/Aeronautics**. Ele calcula acústica, oclusão e alcance sonoro levando em conta paredes e geometria dentro de sublevels móveis, em vez de avaliar apenas o level principal.
A integração também cobre efeitos de movimento associados a veículos, incluindo comportamento espacial/Doppler conforme suportado pelo fork, fazendo som de máquinas, passos e ambiente acompanhar a posição física da contraption.
O arquivo instalado/publicado é `1.4.0`, mas o provider carregado continua declarando runtime **`1.3.0.2`**. Não existe um segundo Sound Physics Remastered top-level na modlist atual.
## Presence Footsteps x Sable — 1.0
`pfsable-1.0.jar`
**Presence Footsteps x Sable** é um patch específico para que o Presence Footsteps consulte corretamente a superfície sob o jogador quando ele está sobre uma **contraption Sable/Aeronautics**. Dessa forma, o material real do bloco móvel produz o conjunto de passos correspondente em vez de o sistema consultar erroneamente o bloco do mundo principal.
A bridge reutiliza os sons e regras do Presence Footsteps; não adiciona uma segunda biblioteca de áudio nem novos materiais próprios. O JAR `pfsable-1.0.jar` é a release NeoForge 1.21.1 instalada.
# 6.1. Extensões físicas de ragdoll do Sable — atualização 28/08
## Sable Ragdolls — 0.7.5
`sable_player_ragdoll-1.21.1-0.7.5.jar`
**Sable Ragdolls** usa a física do Sable para representar o corpo do jogador como um conjunto de peças físicas simuladas. O estado pode ser acionado manualmente, por itens definidos em datapack ou por addons externos; o mod também fornece dummies, suporte a skin/perfil, despawn configurável e uma API pública para outras integrações.
Ele é a base física do restante do stack de ragdoll: não define sozinho todos os gatilhos, patches ou modelos compatíveis, mas expõe o corpo articulado que essas extensões utilizam.
## Ragdoll Reactions — 0.7.0
`ragdoll_reactions-1.21.1-0.7.0.jar`
**Ragdoll Reactions** conecta eventos cinéticos do mundo ao estado físico do Player Ragdoll. Colisões fortes, atropelamentos, mudanças bruscas de direção, velocidades de lançamento e explosões podem provocar ragdoll, com sensibilidade, thresholds, cooldown de retrigger e ativação geral configuráveis no servidor.
O addon não implementa um segundo motor físico: depende de Sable + Sable Player Ragdoll e converte eventos de movimento/impacto em gatilhos para o sistema já existente.
## Sable Ragdolls Patch — 1.9
`sable_player_ragdoll_patch-1.21.1-1.9.jar`
**Sable Ragdolls Patch** é a camada de correção do stack. Ajusta renderização, colisão e estados envolvendo Player Ragdoll/Ragdoll Corpse, incluindo Curios, second skin/cape, swim pose, carrying/inventory e modelos ou braços com escala incorreta.
A release `1.9` também corrige os braços em primeira pessoa do Punchy durante ragdoll, bloqueia Ender Pearls e Wind Bombs nesse estado e trata um caso em que `/sable remove @e` podia deixar o jogador preso em estado inválido. É patch/compatibilidade, não outro sistema de ragdoll.
## Sable x CPM — 0.3.2+1.21.1
`sable-x-cpm-0.3.2+1.21.1.jar`
**Sable x CPM** integra **Customizable Player Models** aos ragdolls e cadáveres físicos do Sable. Cada peça simulada normalmente usa um PlayerModel vanilla; a bridge usa a plugin API do CPM para associar o modelo customizado correto antes da renderização.
Ela cobre Sable Player Ragdoll e Sable Ragdoll Corpse. Capas e elytra continuam usando o caminho vanilla, e partes CPM muito afastadas do osso pai podem apresentar separação visual nas juntas porque as peças são simuladas individualmente.
## Sable mob ragdoll corpses — 1.1.5
`mob_ragdoll_corpse-1.1.5.jar`
**Sable mob ragdoll corpses** estende a física pós-morte aos mobs. Em vez de desaparecerem imediatamente, criaturas mortas podem permanecer como corpos ragdoll físicos persistentes que participam da apresentação e da interação do mundo.
O projeto documenta usos como carregar presas ou companheiros e enterrar companheiros, transformando a morte de entidades em um estado físico persistente. Sua função é complementar o stack Sable/ragdoll; não altera IA viva nem cria um sistema separado de mobs.
# 7. Espaço e tecnologia de alto nível
## Create: Northstar Redux — 0.6.4+1.21.1
`Northstar-0.6.4+1.21.1.jar`
**Create: Northstar Redux** é um addon de **exploração espacial construído sobre a filosofia de engenharia do Create**. O jogador fabrica componentes, materiais e infraestrutura para montar foguetes/veículos espaciais como construções próprias, em vez de receber uma nave pré-fabricada por interface.
A progressão introduz materiais e processos de nível espacial, incluindo Titanium, sistemas ligados a oxigênio e ambientes planetários, além de diferentes corpos celestes e dimensões exploráveis. Recursos obtidos fora do Overworld retornam às cadeias de produção e permitem avançar o programa espacial.
A linha Redux moderniza Northstar para Create 6 e NeoForge 1.21.1. O JAR `0.6.4+1.21.1` é a release atual dessa linha e funciona como base para integrações como AeroStar.
## Create: Creating Space — 1.7.20
`creatingspace-1.21.1-1.7.20.jar`
**Create: Creating Space** permite projetar **foguetes como contraptions Create** e utilizá-los para viajar a outros planetas. O formato e a disposição da nave são construídos pelo jogador, e a proposta do projeto enfatiza uma fantasia de engenharia inspirada em ciência mais física do que em veículos espaciais prontos.
A progressão espacial depende da infraestrutura da base: peças, combustível e preparação do foguete entram em cadeias Create antes da viagem, enquanto os destinos formam a camada de exploração interplanetária. Desde a linha 1.7.10 o mod requer **Create 6+**.
O JAR `creatingspace-1.21.1-1.7.20.jar` é a release NeoForge 1.21.1 atual dessa linha; releases 1.7.21 posteriores pertencem à linha Forge 1.20.1, não à instância atual.
## Create Aeronautics: Alcubierre — 1.2.6
`alcubierre-1.2.6.jar`
**Create Aeronautics: Alcubierre** adiciona dois blocos de tecnologia de alto nível para physics contraptions. O **Antigravity Drive** conecta-se a uma rede cinética e, enquanto recebe potência, cancela a gravidade aplicada ao ship, permitindo mantê-lo flutuando sem sustentação aerodinâmica convencional.
O **Alcubierre Controller** funciona como cockpit de warp: recebe coordenadas e uma dimensão de destino e, após ativação pelo painel ou redstone, transfere o **ship inteiro** em poucos segundos. A partir da versão 1.2.6 o mod exige **Dimensional Sable**, que fornece a transferência interdimensional do physics object.
Assim, antigravidade e warp são aplicados à própria construção Sable/Aeronautics, preservando a ideia de veículo montado com blocos.
# 8. Armas, defesa e engenharia militar do Create
## Create Big Cannons — 5.11.7
`createbigcannons-5.11.7+mc.1.21.1.jar`
**Create Big Cannons** transforma artilharia em engenharia de blocos integrada ao Create. O jogador fabrica e monta grandes canhões e autocannons a partir de componentes físicos, escolhe materiais de construção e configura mecanismos de carregamento, breeches e mounts em vez de receber uma arma pronta como item.
O sistema inclui famílias próprias de munição, cargas propelentes, projectiles e fuzes, além de processos de fabricação e operação que fazem a artilharia participar da fábrica. A linha 5.11 também possui integração explícita com **Sable**, incluindo tratamento de impacto de projéteis em physics objects; a build 5.11.7 instalada é a release NeoForge 1.21.1 atual desse ramo.
## CBC Advanced Technology — 0.1.4c-1.21.1
`cbc_at_Neoforge_1.21.1_0.1.4c.jar`
**Create Big Cannons: Advanced Technologies** amplia CBC com armamento e mecanismos mais complexos. A ficha atual registra muzzle brakes, fume extractors, silencers, rifled barrels, autocannons avançados, rocket pods/rails, novas munições e foguetes, levando o sistema além dos canhões e autocannons básicos do mod-base.
Ele continua dependente da infraestrutura mecânica e balística de Create Big Cannons; não é uma coleção de armas independente. A release pública é `0.1.4c`, enquanto o metadata runtime da instância declara `0.1.4c-1.21.1`, distinção mantida no catálogo.
## CBCAT Fix — 1.0.0
`cbcatfix-1.21.1-neoforge-1.0.1.jar`
**CBCAT Fix** é um patch top-level dedicado à combinação atual de Create Big Cannons e Advanced Technologies. Sua função é corrigir crashes e comportamentos quebrados do addon em versões recentes do stack CBC, especialmente pontos ligados a armamentos/munições e compatibilidade funcional.
Ele não acrescenta uma segunda árvore de artilharia: existe para estabilizar e complementar CBC:AT no ambiente atual. O JAR instalado/publicado é `cbcatfix-1.21.1-neoforge-1.0.1.jar`, enquanto o metadata runtime declara `1.0.0`; ambas as identificações permanecem registradas.
## Create: Gunsmithing — 1.4.9
`create-gunsmithing-1.21.1-1.4.9.jar`
**Create: Gunsmithing** adiciona armas portáteis steampunk produzidas por mecânicas do Create. O catálogo oficial inclui Flintlock, Revolver, Shotgun, Nailgun, Gatling, Blazegun, Launcher, Pneumatic Hammer e Frag Grenade, com animações próprias e componentes associados.
A identidade do mod está tanto nas armas quanto na fabricação: o arsenal é construído dentro da linguagem industrial do Create, em vez de funcionar como um gun mod desconectado da fábrica. A linha também oferece suporte a gun packs NTGL para adicionar ou modificar armas.
## Create Missiles — 1.0.3
`createmissiles-1.0.3+neoforge-1.21.1.jar`
**Create Missiles** adiciona mísseis fisicamente simulados e modulares ao Create. Cada veículo pode ser montado com mais de **30 peças intercambiáveis**, combinando chassis, thrusters, guidance/navigation e warheads para formar projetos com comportamento próprio.
O lançamento usa uma estrutura multiblock de **Launch Pad** com Control Panel, Assembly Panel e Navigation Panel. O addon também possui drones capazes de voar até coordenadas específicas e mapear a área, integração com JEI/Ponder e receitas para clonar assemblies. A build 1.0.3 instalada é a release NeoForge 1.21.1 para Create 6.0.10 e inclui correção explícita para dedicated server.
## Create Guardian Beam Defense — 1.3.7.1b
`Create-Guardian-Beam-Defense-1.3.7.1b-1.21.1-neoforge.jar`
**Create Guardian Beam Defense** acrescenta unidades estacionárias de defesa inspiradas em Guardians e Ocean Monuments. As torres usam feixes contínuos/em rajadas, fazem **lock automático em mobs configurados** e causam dano progressivamente sem exigir que o jogador opere a arma manualmente.
O sistema possui níveis de defesa — incluindo variantes capazes de lidar com múltiplos alvos — e é integrado ao ecossistema Create como infraestrutura defensiva fixa. A build `1.3.7.1b` instalada continua classificada como beta para NeoForge 1.21.1.
# 9. Transporte ferroviário, marítimo e pessoal
## Create: Steam 'n' Rails — 0.3.0 beta 2
`railways-0.3.0-beta.2+neoforge-mc1.21.1.jar`
**Create: Steam 'n' Rails** é a grande expansão ferroviária do Create. Ela amplia trens com novos tipos e estilos de **tracks e bogeys**, componentes ferroviários, sinalização e recursos de composição/estação que tornam material rodante e infraestrutura muito mais variados que o conjunto base.
A edição instalada é o **port não oficial NeoForge 1.21.1**, cujo ramo 0.3.0 migrou o conjunto de recursos do upstream moderno. O arquivo atual `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar` é beta; essa classificação descreve a maturidade do port, não dúvida sobre sua presença no pack.
## Create Teleporters Remastered — 2.0.2
`createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar`
**Create Teleporters Remastered** reconstrói o antigo Create Teleporters com estética e interfaces alinhadas ao Create atual. O sistema inclui **Entity Teleporters** de tamanhos diferentes, **Item Teleporter**, links de teleporte com alcance configurável e um **Custom Portal multiblock** formado por Custom Portal Base e Quantum Casings.
A versão Remastered simplificou a arquitetura antiga, removeu receivers/gravity stabilizer e refez GUIs, modelos e mecânicas de portal. O JAR instalado usa a build `2.0.2b`, enquanto o metadata runtime declara `2.0.2`; as duas strings são mantidas separadamente.
## Create: Blocks & Bogies — 1.0.8
`create_bb-1.0.8-1.21.1.jar`
**Create: Blocks & Bogies** é uma expansão de customização do material rodante. Ela adiciona uma **interface interativa de configuração de bogies** e muitas famílias de wheelsets/drivers, permitindo ajustar visual, número de eixos e mecanismo aparente do conjunto.
Entre as opções estão Walschaerts, piston-only, pistonless, rodless e Scotch Yoke drivers em vários números de eixos, além de bogies menores/trailing. O mod exige Create 6.0+, mas não depende de Steam 'n' Rails para funcionar.
## Create: Ornithopter Glider — runtime 1.2.0-1.21.1
`createornithopterglider-1.2.0-1.21.1.jar`
**Create: Ornithopter Glider** adiciona um planador mecânico inspirado nas máquinas voadoras de Leonardo da Vinci, com modelo, animações e sons próprios. Durante o voo, a tecla configurada — Space por padrão — carrega um movimento de **flap** que produz um impulso adicional; o boost possui cooldown configurável e a configuração padrão documentada usa 2 segundos.
O glider pode usar o slot traseiro do Curios, mas Curios tornou-se opcional na 1.2.0. A fabricação também participa do ecossistema Create: o Ornithopter é montado através do **Mechanical Crafter**, em vez de uma receita manual comum. A publicação externa usa `1.2.0+1.21.1`, enquanto o arquivo/runtime local preserva `1.2.0-1.21.1`.
## Create: Mechanical Companion — runtime não declarado (publicação 1.9)
`createmechanicalcompanion-1.9-neoforge-1.21.1.jar`
**Create: Mechanical Companion** adiciona o **Mechanical Wolf**, um mob craftável que acompanha o jogador e é invocado ao equipar o Mechanical Wolf Link no slot de cabeça do Curios. Clicar no wolf abre sua interface de módulos; uma wrench pode ser usada para repará-lo.
Os módulos são organizados em quatro categorias. **Defensive** inclui Reinforced/Netherite Plates; **Offensive** inclui Smelting Fangs, Tesla Tail e Mounted Crossbow; **Movement** inclui Booster Rocket e Quantum Drive; **Utility** inclui Mob Radar, Mounted Light e Regenerative Casing. O mod também possui **Illager Workshops**, estruturas onde aparecem Illager Engineers/Supervisors e recursos ligados à criação do wolf.
A release pública instalada é `1.9` para NeoForge 1.21.1, mas a modlist não declara uma versão runtime interna; por isso o catálogo mantém `não declarada (publicação 1.9)` em vez de inferir o valor.
## Create Jetpack — 5.2.1
`create_jetpack-forge-5.2.1.jar`
**Create Jetpack** transforma o conceito do Copper Backtank em equipamento de voo. O jetpack utiliza **ar pressurizado** da infraestrutura Create para permitir mobilidade aérea, fazendo o voo consumir um recurso ligado ao backtank em vez de funcionar como voo criativo gratuito.
A build 5.2.1 é a release NeoForge 1.21.1 atual do projeto. O JAR também embarca Flight Lib como dependência interna, portanto essa biblioteca não aparece como mod top-level separado no catálogo.
## Create: Jetpack Curios — 1.2.0
`create_jetpack_curios-1.2.0-neoforge-1.21.1.jar`
**Create: Jetpack Curios** é a bridge entre Create Jetpack e Curios. Ela permite equipar **Create Jetpack e backtanks compatíveis no slot traseiro do Curios**, preservando seu funcionamento sem ocupar o slot convencional de peitoral.
Seu papel é exclusivamente de equipagem/compatibilidade: o sistema de voo continua pertencendo ao Create Jetpack. A build instalada é `1.2.0` para NeoForge 1.21.1.
## Create SA Curios Jetpacks — runtime 1.2.22
`create_sa_curios_jetpacks-neoforge-1.21.1-1.2.4.jar`
**Create SA Curios Jetpacks** integra os jetpacks e tanks de **Create Stuff 'N Additions** ao Curios. A bridge cobre equipagem em slot dedicado, comportamento funcional dos equipamentos quando usados fora do armor slot, visualização e rotas de reabastecimento/uso compatíveis com o sistema-base.
Ela é distinta de Create: Jetpack Curios porque atende outro mod-base: aqui o equipamento vem de Stuff 'N Additions. O arquivo/publicação instalado identifica `1.2.4`, enquanto o metadata runtime declara `1.2.22`; ambas as strings permanecem registradas.
## Create: Curios Backtank — 1.0.1
`create_curios_backtank-neoforge-1.21.1-1.0.1.jar`
**Create: Curios Backtank** permite usar os **backtanks do Create no slot traseiro do Curios**. O reservatório continua fornecendo sua função pneumática aos equipamentos Create compatíveis, mas deixa de competir diretamente com a armadura equipada no chest slot.
É uma bridge pequena e específica: não adiciona novo combustível, nova mochila ou novo jetpack. Sua função é mudar a forma de equipar o backtank mantendo a lógica original do Create.
# 10. Armazenamento e logística geral
## Sophisticated Backpacks — 3.25.78
`sophisticatedbackpacks-1.21.1-3.25.78.2107.jar`
**Sophisticated Backpacks** é o sistema portátil do ecossistema Sophisticated. As mochilas podem ser carregadas pelo jogador ou colocadas no mundo, possuem tiers de capacidade e aceitam **upgrades funcionais** que mudam seu comportamento em vez de apenas aumentar slots.
Entre os upgrades do sistema estão pickup/magnet para coleta automática, feeding, crafting, smelting, filtros, tanks e battery, além de módulos ligados à organização e automação. Isso permite configurar uma backpack como inventário pessoal simples, estação de crafting, coletor automático, suporte de fluidos/energia ou combinação desses papéis conforme os slots disponíveis.
A build instalada é `sophisticatedbackpacks-1.21.1-3.25.78.2107.jar`, release NeoForge 1.21.1 publicada em 19/08/2026. Ela depende de Sophisticated Core e é o mod-base das integrações Create descritas abaixo.
## Sophisticated Storage — 1.5.91.2127
`sophisticatedstorage-1.21.1-1.5.91.2127.jar`
**Sophisticated Storage** é um sistema de armazenamento físico modular baseado em **Barrels, Chests e Shulker Boxes com tiers e upgrades**. Chests/barrels podem subir por copper, iron, gold, diamond e netherite, aumentando capacidade e número de slots de upgrade sem exigir abandonar o inventário existente.
Os upgrades alteram comportamento do armazenamento — filtragem, automação, capacidade por slot, crafting e outras funções — enquanto configurações e conteúdo permanecem associados ao bloco. O mod também possui **Limited Barrels**, com 1–4 slots de grande capacidade, voltados a recursos em massa, além de personalização visual de madeira, cores e materiais de barrel.
## Sophisticated Storage Create Integration — 0.1.21.209
`sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar`
**Sophisticated Storage Create Integration** torna os blocos de Sophisticated Storage plenamente funcionais em **contraptions do Create**. Barrels, chests e shulker boxes mantêm conteúdo, configurações de inventário, slots memorizados e a maior parte dos upgrades enquanto a estrutura se move em trains, rotating platforms, flying machines e outras contraptions.
Upgrades dependentes de posição, como Pickup/Magnet, usam a posição dinâmica real do storage na contraption; stack/crafting upgrades continuam funcionais. Tier upgrades podem ser aplicados diretamente ao storage montado, e Storage Tool/Paintbrush mantêm controle de locks, indicadores, cores e materiais. O arquivo instalado é `0.1.21.209`, com runtime `0.1.21`.
## Create: Sophisticated Backpacks Compat — runtime sem versão declarada
`create_sophback_compat-1.0.jar`
**Create: Sophisticated Backpacks Compat** conecta a progressão de crafting do Sophisticated Backpacks às máquinas do Create. Ele adiciona **receitas de processamento Create** para componentes da mochila, permitindo que partes da fabricação/upgrades sejam produzidas em linhas mecânicas em vez de depender apenas do crafting convencional.
Isso é diferente de Sophisticated Backpacks Create Integration: este mod trata **receitas e fabricação**; a outra bridge trata o funcionamento de backpacks em contraptions. O arquivo/publicação é `1.0`, mas o metadata runtime não declara versão e expõe um nome interno incorreto; o catálogo preserva essa anomalia sem inventar uma versão runtime.
## Sophisticated Backpacks Create Integration — 0.1.8.134
`sophisticatedbackpackscreateintegration-1.21.1-0.1.8.134.jar`
**Sophisticated Backpacks Create Integration** faz mochilas Sophisticated funcionarem como armazenamento compatível com **contraptions Create**, mantendo seu papel de inventário móvel quando inseridas na estrutura física/contraption.
A integração é complementar ao mod de receitas `create_sophback_compat`: aqui o foco é **funcionamento da backpack em movimento**, enquanto o outro altera como seus componentes podem ser fabricados. A build instalada é `0.1.8.134`, com metadata runtime `0.1.8`.
## Create: Backpack Pixel — 1.2.0
`backpack_pixel-1.2.0-neoforge-1.21.1.jar`
**Create: Backpack Pixel** adiciona backpacks de estética Create com **componentes funcionais instaláveis**. As mochilas podem ser configuradas para finalidades diferentes combinando componentes, e também aceitam skins para mudar a aparência sem alterar sua função.
A interface pode ser aberta por clique direito ou pela tecla `B` enquanto a backpack está equipada. A linha 1.2.0 para NeoForge 1.21.1 não exige mais a antiga renderer API como dependência obrigatória, e o próprio projeto apresenta Backpack Pixel como complemento visual/funcional de Protection Pixel.
## Create: Protection Pixel — 2.2.1
`protection_pixel-2.2.1-neoforge-1.21.1.jar`
**Create: Protection Pixel** adiciona conjuntos de armadura steampunk em que **cada peça possui função e atributos próprios**, em vez de serem apenas variantes cosméticas. O equipamento é fabricado, processado e fortalecido usando a infraestrutura industrial do Create.
Exemplos documentados incluem **Plague Helmet**, que libera vapor para remover efeitos temporários como blindness, darkness, weakness e slowness; **Lancer Helmet**, que relaciona velocidade do usuário ao dano; e **Hunter Helmet**, voltado a detectar criaturas em movimento e auxiliar exploração/loot. Assim, os conjuntos formam uma camada de equipamentos tecnológicos com especializações diferentes.
## Tom's Simple Storage — 2.4.1
`toms_storage-1.21-2.4.1.jar`
**Tom's Simple Storage Mod** cria uma rede leve sobre **inventários físicos existentes**. Baús e outros containers continuam guardando os itens; cabos/conectores os unem a um terminal que apresenta o conteúdo como um inventário pesquisável e centralizado.
O sistema fornece acesso, organização e crafting sobre a rede sem converter recursos em células digitais como o AE2. Essa arquitetura também explica integrações do pack como Create Contraption Terminals, que levam terminais Tom's para contraptions móveis.
# 11. Pontes entre magia e tecnologia
## Create: Enchantment Industry — 2.5.3
`create-enchantment-industry-2.5.3.jar`
**Create: Enchantment Industry** transforma experiência e encantamento em processos industriais. **Liquid Experience** pode ser armazenada e transportada; o **Disenchanter** remove enchantments convertendo-os em experiência; o **Blaze Enchanter** automatiza encantamento; e o **Printer** replica conteúdos como livros escritos, enchanted books, name tags e train schedules.
O sistema também integra Mending em belts por Spout + Liquid Experience, permite experience nuggets em interações compatíveis de Deployer/Crushing Wheels e possui **hyper-enchanting** para níveis acima do cap convencional. Há compatibilidade específica com Apotheosis/Apothic quando detectado.
## Create Enchantment Industry Plus — 1.1.1
`create_enchantment_industry_plus-1.1.1-1.21.1.jar`
**Create Enchantment Industry Plus** é uma extensão pequena do sistema acima focada em **ink e glow ink**. Acrescenta sacs que podem ser preenchidos para produzir ink sacs, receitas de grinding e conversão de ink sac em glow ink sac; a linha atual também ajusta receitas para usar black dye onde aplicável.
## Create: Enchantable Machinery — 3.6.0