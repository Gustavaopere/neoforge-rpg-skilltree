<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

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
