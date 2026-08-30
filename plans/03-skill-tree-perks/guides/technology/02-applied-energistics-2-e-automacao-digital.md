<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

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
