<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

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
