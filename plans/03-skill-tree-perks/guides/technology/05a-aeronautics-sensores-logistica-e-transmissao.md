<!-- Reconciliado com modlist.txt atual em 2026-09-06. -->

[← Capítulo principal](05-fisica-veiculos-e-create-aeronautics.md) · [← Índice do guia](README.md)

# 5A. Aeronautics: sensores, logística e transmissão

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

## Create Aeronautics: Transmission & Linkage — 0.2.8

`create_aeronautics_transmission_linkage-0.2.8.jar`
**Create Aeronautics: Transmission & Linkage** adiciona componentes para transmitir **movimento cinético e deslocamento mecânico entre partes físicas articuladas**. Universal joints permitem conectar eixos que não permanecem perfeitamente alinhados; kinetic converters adaptam transferência entre contextos diferentes; e hydraulic rods introduzem extensão/retração controlada.
Essas peças tornam possível construir mecanismos articulados dentro ou entre physics contraptions, como juntas, braços, superfícies móveis e acoplamentos cuja geometria muda durante o funcionamento. A versão instalada é `0.2.8` para NeoForge 1.21.1.

## Create Aeronautics: Toolgun — 0.3.6

`create_aeronautics_toolgun-0.3.6.jar`
**Create Aeronautics: Toolgun** introduz um workflow de engenharia inspirado em toolguns para manipular contraptions físicas. O sistema inclui **blueprints** para salvar e imprimir crafts físicos e ferramentas para apagar, selecionar ou modificar estruturas sem desmontá-las manualmente bloco a bloco.
A linha atual também possui **magnetic field guns** voltadas a mover/manipular objetos físicos. O objetivo é fornecer ferramentas de edição adequadas à escala de ships e veículos Aeronautics, onde operações de construção convencionais se tornam pouco práticas.

## Create Aeronautics: Throwable Rope Connector — 0.4.3

`create_aeronautics_throwable_rope_connector-0.4.3.jar`
**Throwable Rope Connector** transforma rope connectors do Aeronautics em uma ferramenta prática de **docking e conexão à distância**. O jogador pode lançar a conexão em vez de precisar posicionar os dois pontos manualmente lado a lado, permitindo prender ship↔dock ou ship↔ship durante manobras.
A versão `0.4.3` inclui correção específica para a mira do **Mounted Rope Launcher em contraptions Sable**, especialmente em disparos de um veículo físico contra outro. O addon atua sobre vínculos/docking; não é o mesmo sistema de escalada de Climbable Ropes.

## Create Aeronautics: Copycat Wing — 1.0.3

`CreateAeronauticsCopycatWing-1.21.1-1.0.3.jar`
**Create Aeronautics: Copycat Wing** registra blocos do **Copycats+** como partes válidas de asa para o modelo aerodinâmico do Aeronautics. Isso permite construir superfícies de lift com formatos e materiais visuais de Copycat sem perder a função física de wing.
A função é especificamente aerodinâmica. `aerocopycats`, presente separadamente no stack, trata propriedades físicas/massa de blocos Copycat em Sable; Copycat Wing trata reconhecimento desses blocos como superfícies que geram sustentação.

## Create Aeronautics x Curios API Compat — runtime 2.0

`createaeronauticscurios-neoforge-1.21.1-2.2.jar`
**Create Aeronautics x Curios API Compat** faz equipamentos utilitários do Aeronautics funcionarem quando equipados em slots **Curios**, em vez de exigir ocupação dos slots vanilla correspondentes. Os principais casos documentados são **Aviator Goggles** e **Linked Typewriter**.
A integração preserva o comportamento dos goggles enquanto equipados e permite ativação/uso remoto do Linked Typewriter pelo slot Curios. O filename/publicação é `2.2`, mas o metadata runtime carregado declara `2.0`; o catálogo mantém as duas strings.
