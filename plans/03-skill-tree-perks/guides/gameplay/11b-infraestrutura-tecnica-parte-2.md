# 11B. Infraestrutura técnica — parte 2

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **Bookshelf — 21.1.81** até **Cosmetic Armor Reworked — runtime 1.21.1-v1-neoforge**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

## Bookshelf — 21.1.81

`bookshelf-neoforge-1.21.1-21.1.81.jar`
**Bookshelf** é uma biblioteca open source que reúne **frameworks, helpers e utilidades de código** para mods do ecossistema Darkhax e projetos externos. Entre os consumidores conhecidos está Enchantment Descriptions; a linha 1.21.1 também oferece utilidades para dados, networking/serialização e representação/descrição de sistemas como loot tables.
Como library, Bookshelf não constitui conteúdo jogável autônomo: seus efeitos aparecem por meio dos mods que chamam suas APIs. A build instalada é `21.1.81` para NeoForge 1.21.1.

## Caelus API — 7.0.1+1.21.1

`caelus-neoforge-7.0.1+1.21.1.jar`
**Caelus API** abstrai a lógica hardcoded de voo de Elytra em um **atributo de entidade de fall-flying**. Mods podem aplicar modificadores a esse atributo para habilitar ou desabilitar voo tipo Elytra em equipamentos ou condições próprias, sem substituir diretamente toda a lógica vanilla.
O atributo tem valor padrão zero; valores suficientes habilitam fall-flying, e a própria Elytra vanilla é adaptada para utilizar o mesmo contrato mantendo seu comportamento normal. A build instalada é `7.0.1+1.21.1`.

## 3D Skin Layers — 1.11.2

`skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`
**3D Skin Layers** é um mod client-side de renderização de jogador. Ele transforma a segunda camada normalmente plana da skin — chapéu, mangas, jaqueta e detalhes equivalentes — em uma **malha tridimensional com volume real**, preservando transparências usadas em óculos, visores e outros detalhes cosméticos.
Para controlar custo de renderização, o projeto pode retornar jogadores mais distantes para a apresentação 2D tradicional; a documentação atual usa uma distância próxima de 12 blocos como limiar padrão desse comportamento. A build instalada é `1.11.2` para NeoForge 1.21.1.

## Catalogue — 1.11.2

`catalogue-neoforge-1.21.1-1.11.2.jar`
**Catalogue** substitui a lista de mods do loader por uma interface unificada e pesquisável. Ela permite **buscar, filtrar e favoritar mods, abrir configurações, acessar homepage/issue tracker e visualizar dependências/dependentes**, além de esconder bibliotecas da listagem quando desejado.
O sistema utiliza logos/ícones declarados pelos mods e serve apenas como interface de administração/consulta da instalação. Não altera o carregamento ou a lógica dos mods exibidos. A modlist contém a release NeoForge 1.21.1 `1.11.2`.

## Chipped — 4.0.2

`chipped-neoforge-1.21.1-4.0.2.jar`
**Chipped** é uma grande expansão de blocos de construção voltada a **variantes decorativas de materiais existentes**. Por meio de estações/workbenches temáticas, blocos-base podem ser transformados em diversas versões com padrões, cortes e estilos diferentes, aumentando o vocabulário arquitetônico sem introduzir uma progressão tecnológica própria.
A biblioteca Athena sustenta parte da renderização/conectividade usada pelo ecossistema visual. O JAR instalado é `4.0.2` para NeoForge 1.21.1.

## Chunky — 1.4.23

`Chunky-NeoForge-1.4.23.jar`
**Chunky** é uma ferramenta de **pré-geração de chunks**. Administradores podem definir uma região/forma e gerar antecipadamente os chunks que seriam criados durante exploração normal, reduzindo picos futuros de chunkgen quando jogadores percorrem essas áreas.
As tarefas podem ser iniciadas, acompanhadas, pausadas e retomadas, com métricas como quantidade processada, percentual, taxa e ETA. O mod não substitui o worldgen de Tectonic/Terralith/BWG/YUNG etc.; ele executa antecipadamente o pipeline de geração definido por esses providers. A build instalada é `1.4.23`.

## Citadel — 2.7.1

`citadel-2.7.1-1.21.1.jar`
**Citadel** é uma biblioteca compartilhada para mods do ecossistema de Alex. Ela concentra código e infraestrutura reutilizável de entidades/renderização e serviços comuns, evitando que cada projeto dependente carregue implementações duplicadas.
Sua presença é técnica: conteúdo concreto vem dos mods que usam a API. A build `2.7.1` é a release NeoForge 1.21.1 instalada.

## Cloth Config v15 API — 15.0.140

`cloth-config-15.0.140-neoforge.jar`
**Cloth Config API** é um framework para criação de **telas de configuração**. Ele fornece widgets, categorias, entradas, validação e componentes usados por mods para expor opções editáveis ao jogador de forma consistente, em vez de cada projeto construir seu menu de configuração do zero.
Não define configurações de gameplay por conta própria; somente fornece a infraestrutura de UI consumida pelos dependentes. A build instalada é `15.0.140` para NeoForge/Minecraft 1.21.1.

## YetAnotherConfigLib (YACL) — 3.8.2+1.21.1-neoforge

`yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`
**YetAnotherConfigLib (YACL)** é uma biblioteca para criação de telas e sistemas de configuração. Ela fornece componentes de UI, categorias, opções e infraestrutura reutilizável para que mods consumidores exponham configurações sem implementar toda a interface do zero.
A build instalada é `3.8.2+1.21.1-neoforge`. YACL não adiciona gameplay próprio e não é substituível automaticamente por Cloth Config ou outras config libs, porque consumidores são compilados contra APIs específicas. A ficha do catálogo foi reconciliada nesta auditoria para `Estado no pack = Instalado` e fonte canônica 28/08.

## Clumps — 19.0.0.1

`Clumps-neoforge-1.21.1-19.0.0.1.jar`
**Clumps** reduz a quantidade de entidades de experiência ao **agrupar XP orbs próximos em orbs maiores**, diminuindo o custo de tick/renderização quando uma farm, máquina ou combate gera muita experiência em uma área pequena.
Também faz a experiência ser coletada imediatamente quando a entidade entra em contato com o jogador, evitando grandes quantidades de orbs persistindo ao redor dele. Nas versões modernas seu efeito principal é server-side. A build instalada é `19.0.0.1`.

## Cobweb — 1.4.0

`cobweb-neoforge-1.21-1.4.0.jar`
**Cobweb** é a library/API do ecossistema Crystal Nest. Ela centraliza contratos e utilidades compartilhados pelos projetos que dependem dessa base, permitindo reutilização de lógica comum entre loaders e mods.
O projeto é distribuído como **Crystal Nest API** e não adiciona um sistema jogável independente. O runtime top-level presente no pack é `1.4.0`.

## CodeChickenLib — 4.6.1.529

`CodeChickenLib-1.21.1-4.6.1.529.jar`
**CodeChickenLib** é uma biblioteca histórica do ecossistema Chicken-Bones/CodeChicken. Ela reúne infraestrutura para **matemática e transformações 3D, rendering, networking, configuração e outras rotinas de modding** utilizadas por projetos dependentes.
O JAR não representa conteúdo autônomo; seu papel é fornecer serviços comuns em runtime. A build instalada é `4.6.1.529` para NeoForge 1.21.1.

## CodxLib — 1.5.1

`codxlib-1.5.1-neoforge+1.21.1.jar`
**CodxLib** é a biblioteca de suporte compartilhada pelos mods do ecossistema Codx. O projeto centraliza **serviços comuns e contratos reutilizáveis** para que os mods dependentes mantenham comportamento consistente entre Fabric, Forge e NeoForge.
Ela não adiciona conteúdo jogável sozinha; a função observável vem dos consumidores da library. A modlist carrega `1.5.1` para NeoForge 1.21.1.

## Collective — 8.39

`collective-1.21.1-8.39.jar`
**Collective** é a biblioteca comum dos mods de Serilum. Ela concentra código, eventos, helpers e implementações reaproveitadas por muitos projetos do autor, reduzindo duplicação entre dezenas de pequenos mods.
Como dependência, não constitui uma mecânica independente. A build instalada é `8.39` para a linha 1.21.1.

## Connector Extras — runtime 1.12.1+1.21.1

`ConnectorExtras-1.12.1+1.21.1.jar`
**Connector Extras** amplia o Sinytra Connector com **bridges para APIs e plugins de terceiros**. Entre os módulos documentados está a conversão bidirecional entre Team Reborn Energy e Forge Energy, com razão configurável, além de bridges para que recipe viewers consigam descobrir plugins provenientes tanto do lado Fabric quanto do lado Forge/NeoForge.
Ele não é o componente que executa mods Fabric; trabalha sobre Connector para adaptar APIs adicionais que esses mods podem esperar encontrar. O runtime instalado é `1.12.1+1.21.1`.

## Sinytra Connector — publicação 2.0.0-beta.17+1.21.1

`connector-2.0.0-beta.17+1.21.1-full.jar`
**Sinytra Connector** é uma camada de compatibilidade que permite executar uma parcela de **mods Fabric em um ambiente NeoForge**, traduzindo expectativas de loader/API para o runtime NeoForge e trabalhando com a infraestrutura de compatibilidade necessária ao ecossistema Fabric.
A linha `2.0.0-beta.17` é a build 1.21.1 instalada. O sufixo `full` pertence ao filename; a inspeção da modlist não forneceu uma string de versão runtime separada, portanto o guia preserva a identidade pela publicação/arquivo em vez de inventar um metadata ausente.

## Continuity — runtime 3.0.0+1.21.neoforge

`continuity-3.0.0+1.21.neoforge.jar`
**Continuity** implementa **connected textures** e recursos de emissive/overlay texture de forma eficiente no cliente. Resource packs e mods podem definir regras para que texturas se conectem entre blocos vizinhos, evitando bordas repetitivas e permitindo superfícies contínuas.
A build instalada `3.0.0+1.21.neoforge` inclui suporte NeoForge 1.21.1 e trata também casos como camadas customizadas em blocos móveis. Ele modifica renderização de recursos; não altera propriedades físicas dos blocos.

## Controlling — 19.0.5

`Controlling-neoforge-1.21.1-19.0.5.jar`
**Controlling** reorganiza a tela de keybindings para instalações com muitos mods. Ele adiciona **busca por nome**, filtro de binds em conflito e visualização de teclas ainda disponíveis, tornando possível localizar e resolver colisões de controles sem percorrer manualmente uma lista extensa.
É um mod client-side de interface e não altera o input registrado pelos outros mods além da forma de administrá-lo. A build instalada é `19.0.5`.

## CorgiLib — 5.0.0.9

`Corgilib-NeoForge-1.21.1-5.0.0.9.jar`
**CorgiLib** é a biblioteca compartilhada dos mods de Corgi Taco. A documentação expõe infraestrutura como **serialização de configs ****`.json5`**** via Mojang codecs, codecs comentados, geração de árvores a partir de NBT, registries de funções de easing/blending, filtros de entidades e codecs para villager trades**.
Esses recursos são consumidos por mods de worldgen e conteúdo do ecossistema; a library em si não adiciona uma progressão própria. A build instalada é `5.0.0.9` para NeoForge 1.21.1.

## Cosmetic Armor Reworked — runtime 1.21.1-v1-neoforge

`cosmeticarmorreworked-1.21.1-v1-neoforge.jar`
**Cosmetic Armor Reworked** separa **aparência e função da armadura** em dois conjuntos: o equipamento funcional continua fornecendo proteção/atributos, enquanto slots cosméticos independentes determinam o que é renderizado no personagem.
Isso permite utilizar estatísticas de uma armadura e exibir outra sem alterar os valores defensivos. Em multiplayer o mod precisa participar do ambiente de cliente/servidor para manter os slots e sincronização correspondentes. A build instalada preserva o runtime `1.21.1-v1-neoforge`.
