<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

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
