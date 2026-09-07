<!-- Reconciliado com modlist.txt atual em 2026-09-06. -->

[← Capítulo principal](05-fisica-veiculos-e-create-aeronautics.md) · [← Índice do guia](README.md)

# 5B. Aeronautics: propulsão, controle e física do jogador

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

## Climbable Ropes for Create Aeronautics — 2.1.3

`climbable_ropes-2.1.3.jar`
**Climbable Ropes for Create Aeronautics** torna cordas verticais do stack Aeronautics/Simulated realmente escaláveis pelo jogador, inclusive as cordas usadas pelo **Plunger Launcher**. Com as mãos vazias, o jogador pode agarrar a corda, usar W/S para subir ou descer, Space para saltar para fora e Sneak para soltá-la.
O movimento inclui animações próprias de escalada e comportamento de mantle/salto associado à corda. A linha atual `2.1.3` incorpora a manutenção posterior a correções em que a animação podia permanecer incorreta depois de respawn ou troca de dimensão e ajusta poses/renderização durante cancelamento do estado de climbing.
O JAR também possui dependências internas usadas pela animação, mas isso não altera a presença dos JARs top-level atuais do pack.

## Aeronautics Camera Sync — 1.4.0

`aero_cam_sync-1.4.0.jar`
**Aeronautics Camera Sync** sincroniza a orientação da câmera do jogador com a **rotação dinâmica da contraption física** onde ele está. Sem essa camada, o veículo pode inclinar, rolar ou girar enquanto a câmera continua orientada principalmente pelo frame do mundo, produzindo sensação visual desconectada do movimento.
O addon aplica a transformação do ship/sublevel à visão do jogador para que cockpit, interior e horizonte respondam de forma coerente durante pitch, yaw e roll. Sua função é estritamente de apresentação/câmera sobre a física existente; ele não altera forças ou controles do veículo.

## Create Aeronautics: Weight — 1.2.0

`weight-1.2.0.jar`
**Create Aeronautics: Weight** acrescenta massa dinâmica a elementos que normalmente não contribuem integralmente para a física de uma contraption. Jogadores, mobs, inventários, containers, fluid tanks, armaduras e carga podem aumentar o peso da estrutura Sable conforme configurações e mappings próprios.
Saltos e aterrissagens também aplicam força ao deck proporcionalmente à intensidade. As fontes de massa de inventário, conteúdo de containers, fluidos, armaduras e mob gear são configuráveis separadamente e os mappings podem ser recarregados por JSON/comandos. Não é um sistema de encumbrance: o peso é aplicado à **simulação física da contraption**.


## Aeronautics Player Tilt — 0.1.3

`aero_player_tilt-0.1.3.jar`
**Aeronautics Player Tilt** faz o corpo e a **hitbox** do jogador acompanharem a inclinação do convés de uma contraption Create Aeronautics. Isso permite que a orientação física do jogador corresponda ao deck em movimento; a lógica também corrige o comportamento de salto/gravity relativo ao convés e possui suporte experimental configurável para mobs e itens dropados.

O projeto depende de **Sable** e **Aeronautics Camera Sync 1.4.0+**. O toggle do jogador não cria uma nova física ou massa independente: a contraption/Sable continua sendo o contexto físico autoritativo. A versão da modlist é `0.1.3`.

---
