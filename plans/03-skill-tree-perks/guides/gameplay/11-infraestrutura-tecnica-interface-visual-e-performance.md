<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 11. Infraestrutura técnica, interface, visual e performance

> Este apêndice cataloga **JARs top-level reais da modlist** que não pertencem principalmente aos eixos de Magia ou Tecnologia e que também não constituem, por si só, um sistema jogável como os capítulos anteriores. Entram aqui modloader, APIs/libraries, interface, áudio, apresentação e otimizações. Eles são mantidos no guia para que a cobertura conjunta dos três catálogos corresponda à modlist atual, sem promover dependências internas jar-in-jar a mods independentes.

## NeoForge — runtime neoforge-21.1.248

`neoforge-21.1.248 (modloader)`
**NeoForge** é o modloader e a plataforma de execução sobre a qual esta instância 1.21.1 funciona. Ele fornece o ciclo de carregamento de mods e a infraestrutura comum usada pelos JARs NeoForge: descoberta de mods, registries, event buses, networking, configuração e hooks que permitem que código externo interaja com o Minecraft sem alterar cada sistema diretamente.
A entrada aparece explicitamente na modlist canônica como `neoforge-21.1.248 (modloader)`, com runtime `neoforge-21.1.248`. Ela representa a plataforma do pack, não um mod de conteúdo e não deve ser confundida com uma biblioteca jar-in-jar de algum addon.

## AdvancedCoreInfo — 1.0.0

`AdvancedCoreInfo-neoforge-1.21.1-1.0.0.jar`
**Advanced Core Info (ACI)** é a biblioteca compartilhada da família Advanced Info. Seu papel é centralizar mecanismos reutilizados por Advanced Loot Info e Advanced Worldgen Info, incluindo **descoberta de plugins, árvores estruturadas de tooltips, transferência de dados servidor→cliente e infraestrutura genérica de apresentação/análise**.
ACI não acrescenta uma tela ou sistema jogável autônomo quando instalado sozinho; ele fornece os contratos e serviços utilizados pelos módulos informativos que dependem dele. A modlist carrega a build `1.0.0` como JAR top-level.

## Advanced Loot Info — 2.0.1

`AdvancedLootInfo-neoforge-1.21.1-2.0.1.jar`
**Advanced Loot Info (ALI)** é um plugin de informação para recipe viewers que expõe **loot tables e trades de villagers** de forma consultável. Ele trabalha com JEI, EMI ou REI e organiza entradas, condições, funções e number providers usados pelas tabelas de loot, permitindo investigar de onde um item pode vir e sob quais regras.
A API de plugins permite que outros mods adicionem tipos próprios de loot entries, condições, funções, number providers e categorias. O projeto também possui suporte direto a LootJS. ALI informa e inspeciona o sistema de loot; ele não altera tabelas, não instancia baús por jogador e não substitui Lootr ou Loot Integrations. A build instalada é `2.0.1` e usa AdvancedCoreInfo como core compartilhado.

## Advancement Plaques — 1.6.8

`AdvancementPlaques-1.21.1-neoforge-1.6.8.jar`
**Advancement Plaques** substitui a apresentação padrão dos advancement toasts por placas/pop-ups estilizados, mantendo a conquista e seus critérios pertencentes ao sistema vanilla ou ao mod que registrou o advancement. O efeito é de **apresentação**, com layouts e animações próprios para tornar a notificação mais legível e destacada.
A build `1.6.8` é a release NeoForge 1.21.1 instalada. Essa linha também respeita overrides de som fornecidos por conteúdo compatível, como Aether, quando aplicável. O mod não cria uma progressão paralela nem altera os requisitos dos advancements.

## AI-Improvements — 0.5.3

`AI-Improvements-1.21-0.5.3.jar`
**AI-Improvements** modifica partes de baixo nível da IA vanilla com foco em **reduzir custo de processamento** e permitir desativar determinados comportamentos de IA quando desejado. Sua atuação é principalmente server-side/singleplayer e procura diminuir trabalho desnecessário feito por entidades, embora o próprio projeto ressalte que o ganho depende do cenário e deve ser medido no pack real.
Ele é funcionalmente diferente de **Enhanced AI**: AI-Improvements otimiza ou simplifica processamento; Enhanced AI acrescenta capacidades e comportamentos mais agressivos aos mobs. A build instalada é `0.5.3`.

## AmbientSounds — 6.3.8

`AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`

**AmbientSounds 6** é uma camada client-side de soundscape ambiental. O sistema detecta contexto de mundo — bioma, região e condições relevantes — e mistura **loops suaves e conjuntos variáveis de sons** para que cavernas, florestas, água e outros ambientes possuam paisagens sonoras próprias em vez de dependerem apenas dos eventos sonoros vanilla.
A geração de ambience é configurável e pode ser depurada para identificar quais sons estão ativos. O mod requer **CreativeCore** e não altera spawn, clima ou worldgen; ele reage ao estado existente para produzir áudio. A modlist carrega `6.3.8` para NeoForge 1.21.1.

## AppleSkin — runtime 3.0.9+mc1.21

`appleskin-neoforge-mc1.21-3.0.9.jar`
**AppleSkin** torna visíveis informações do sistema vanilla de fome que normalmente ficam ocultas. Tooltips mostram o valor nutricional de alimentos; o HUD pode indicar **saturação, exhaustion, fome/saturação que seriam restauradas pelo item segurado e potencial de recuperação de vida**, além de dados adicionais no overlay F3.
O mod é essencialmente informativo e não muda as regras de hunger/saturation. Para mostrar valores exatos de saturação/exhaustion, pode sincronizar esses dados do servidor para o cliente. O filename é `3.0.9`, enquanto o metadata runtime preservado pela modlist é `3.0.9+mc1.21`.

## Platform — 1.3.3

`Platform-neoforge-1.21.1-1.3.3.jar`
**Platform** é uma biblioteca cross-platform baseada em Architectury que abstrai APIs e contratos comuns entre Fabric, Forge, NeoForge e Quilt. Ela fornece infraestrutura técnica compartilhada para mods consumidores, incluindo acesso uniforme a funcionalidades de loader e registries, sem adicionar uma progressão ou conteúdo jogável próprio.
A build instalada é `1.3.3` para NeoForge 1.21.1. Como biblioteca específica, não é substituível automaticamente por Architectury, Balm ou outras APIs presentes no pack: consumidores compilados contra Platform continuam exigindo seu contrato.

## Architectury — 13.0.11

`architectury-13.0.11-neoforge.jar`
**Architectury API** é uma camada de abstração para mods multiplataforma. Ela oferece interfaces comuns sobre diferenças entre Fabric e Forge/NeoForge, incluindo **event hooks, networking, chamadas ao loader, registries e abstrações de plataforma**, reduzindo a quantidade de código específico de loader que cada projeto precisa manter.
No pack ela existe como dependência compartilhada de mods que foram construídos sobre essa API. Não adiciona mecânicas ao jogador por conta própria. A build top-level instalada é `13.0.11`.

## Athena — 4.0.6

`athena-neoforge-1.21.1-4.0.6.jar`
**Athena** é uma solução/API client-side para **connected block textures e modelos conectáveis**. Mods podem definir como faces e texturas de blocos se conectam aos vizinhos e deixar que Athena faça a composição/renderização dessas variações sem cada projeto implementar seu próprio pipeline de connected textures.
Seu efeito visível aparece através dos mods que utilizam a biblioteca; Athena em si funciona principalmente como backend de renderização e recursos. A build `4.0.6` é a release NeoForge 1.21.1 presente na modlist.

## Atlas API — runtime 1.21.1-1.2.0

`atlas_api-1.21.1-1.2.0.jar`
**Atlas API** fornece geração de **texture atlases em runtime**, incluindo texturas data-driven, e helpers para carregar modelos de itens que utilizam sprites gerados dinamicamente. Isso permite que um mod componha muitas combinações visuais sem precisar embarcar antecipadamente uma textura estática para cada resultado possível.
O projeto usa Iron's Gems 'n Jewelry como exemplo dessa abordagem: materiais e padrões podem ser combinados e renderizados por atlases criados durante a execução. A versão runtime preservada no pack é `1.21.1-1.2.0`.

## AzureLib — 3.1.11

`azurelib-neo-1.21.1-3.1.11.jar`
**AzureLib** é uma biblioteca de modelos e animações que permite a mods Java utilizar **modelos no formato Bedrock e animações customizadas** para entidades, itens e blocos. Ela fornece o runtime que carrega assets, controla estados de animação e conecta modelos animados ao rendering do jogo.
Consequentemente, criaturas ou equipamentos que dependem dela podem ter rigs e animações complexas sem cada mod desenvolver um engine de animação separado. A build instalada é `3.1.11` para NeoForge 1.21.1.

## Balm — 21.0.65

`balm-neoforge-1.21.1-21.0.65.jar`
**Balm** é uma abstraction layer para desenvolvimento multi-loader. Ela fornece **interfaces, eventos e serviços comuns** para que mods do mesmo ecossistema compartilhem código entre Fabric, Forge e NeoForge, escondendo muitas diferenças de implementação do loader.
Para o jogador, Balm funciona como dependência: recursos concretos vêm dos mods que a utilizam, como Waystones e outros projetos do autor. A modlist contém a release NeoForge 1.21.1 `21.0.65`.

## Battle Arts API — 21.17.7

`battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`
**Battle Arts API** é a parte de API separada do ecossistema Epic Fight Battle Arts. Ela define infraestrutura para **Battle Styles, Combat Arts e Proficiencies** e permite que outros mods registrem seus próprios estilos/arts sem depender do conteúdo específico fornecido pelo projeto original.
Isso torna a API um contrato de combate extensível sobre a linha Epic Fight compatível: ela não deve ser tratada como um segundo overhaul de combate independente. A build top-level instalada é `21.17.7` para Minecraft 1.21.1/NeoForge.

## Better lib — 1.0.111

`better_lib-neoforge-1.21.1-1.0.111.jar`
**Better Library** é uma biblioteca compartilhada usada pelos mods do seu autor para centralizar sistemas comuns. A documentação do projeto cita infraestrutura de **configuração, mensagens de primeiro ingresso/live messages e outros serviços reutilizáveis**, além de expor pontos que podem ser usados por projetos externos.
Sua presença evita que cada mod dependente replique a mesma implementação de configuração e comunicação. A build instalada é `1.0.111` para NeoForge 1.21.1.

## Better Fps - Render Distance — 6.1

`betterfpsdist-1.21.1-6.1.jar`
**Better Fps - Render Distance** é uma otimização client-side do volume renderizado. Em vez de tratar toda a distância de renderização apenas como o volume padrão do Minecraft, permite usar uma região **3D circular/ajustável**, com escalas horizontal e vertical configuráveis para deixar de renderizar chunk sections que dificilmente contribuem para a cena — especialmente cavernas muito abaixo do jogador.
O projeto estima redução relevante de chunk sections renderizadas dependendo das configurações. A distância de entidades também pode acompanhar o ajuste, e há modo de debug para visualizar quantas seções estão sendo descartadas. A build NeoForge 1.21.1 instalada é `6.1`; o mod atua somente no cliente.

## Biolith — 3.0.14

`biolith-neoforge-3.0.14.jar`
**Biolith** é uma API de worldgen voltada à **colocação, substituição e organização compatível de biomas**. Ela fornece um mecanismo comum para mods inserirem biomas e coordenarem alterações no terreno sem cada provider precisar implementar toda a lógica de biome placement e compatibilidade sozinho.
O JAR não é, por si só, um pacote de novos biomas; ele serve como infraestrutura para providers que o utilizam. A build instalada é `3.0.14` para NeoForge.

## BCLib: New Dawn — 21.0.26

`bclib-21.0.26.jar`
**BCLib: New Dawn** é a continuação mantida da biblioteca compartilhada do ecossistema **BetterX/New Dawn**. Ela fornece core utilities, registros e funcionalidades comuns utilizadas por projetos como BetterEnd e BetterNether, evitando que cada expansão replique a mesma infraestrutura de worldgen e conteúdo.
A library funciona em cliente e servidor e não adiciona, sozinha, uma terceira dimensão ou conjunto independente de biomas: o conteúdo visível pertence aos consumidores. A build `21.0.26` é a release NeoForge 1.21.1 instalada, publicada em 09/08/2026. Essa versão adiciona suporte reutilizável a **Chiseled Bookshelves para custom wood material sets**; a linha 21.0 também inclui trabalho de compatibilidade com renderizadores de terreno distante e block entities otimizadas.

## BjornLib — 1.0.88

`bjornlib-neoforge-1.0.88-1.21.1.jar`
**BjornLib** é uma biblioteca de código genérico criada para os mods de FuriusMaximus. Entre os serviços documentados estão **Ability Registry e eventos relacionados, Particle Builder com rendering luminoso, Lightning Builder e Leveling Builder para progressão/leveling de mobs**.
Isso significa que, embora seja uma library, ela expõe primitives que podem sustentar habilidades, efeitos e escalonamento de entidades em mods dependentes. A build top-level instalada é `1.0.88` para NeoForge 1.21.1.

## BlockUI — runtime 1.0.211-1.21.1-snapshot

`blockui-1.0.211-1.21.1-snapshot.jar`
**BlockUI** é o framework de interface do ecossistema MineColonies. Ele usa **XML para declarar a estrutura das telas** e classes `Window` como backend para callbacks e fornecimento de dados. A biblioteca oferece componentes como imagens, botões/handlers, entrada de texto, listas/telas com scroll e drag e outros elementos de UI.
MineColonies, Structurize, MultiPiston e outros projetos usam essa infraestrutura em vez de implementar widgets e layouts separadamente. O runtime instalado é `1.0.211-1.21.1-snapshot`.

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

## CPM OSC Compat — 1.7.2

`cpm-osc-compat-1.7.2.jar`
**Customizable Player Models OSC Compat** conecta o ecossistema CPM a **OSC/VMC (Open Sound Control/Virtual Motion Capture)**. A bridge expõe parâmetros do estado do jogador e do modelo para aplicações externas compatíveis e permite que fluxos baseados nesses protocolos acompanhem dados do personagem durante a execução.
A linha do projeto inclui parâmetros como iluminação, horário do mundo, itens segurados/equipados, blocos ao redor e slot de hotbar, além dos dados próprios do modelo CPM. O JAR top-level instalado é `1.7.2`.

## CraftedCore — 5.8.2

`craftedcore-5.8.2.jar`
**CraftedCore** é uma API/library multiplataforma que reúne código comum para os mods do autor ToCraft. A documentação é explícita que o core **não adiciona recursos jogáveis por si só**; ele fornece os serviços compartilhados de que outros projetos dependem.
Parte da arquitetura do projeto se baseia em padrões do ecossistema Architectury, permitindo manter código comum entre diferentes loaders. A build presente na modlist é `5.8.2`.

## Crash Assistant — 1.11.12

`CrashAssistant-neoforge-1.20.6-1.21.4-1.11.12.jar`
**Crash Assistant** intercepta a experiência pós-crash para abrir uma **GUI dedicada de diagnóstico**, reunindo e analisando logs afetados imediatamente depois da falha. Isso reduz a necessidade de procurar manualmente arquivos em pastas diferentes antes de identificar exceções, mods citados e contexto do crash.
O filename instalado declara compatibilidade de faixa `1.20.6-1.21.4` e o runtime é `1.11.12`; portanto a build inclui Minecraft 1.21.1 dentro da faixa suportada. O mod é uma ferramenta de suporte/diagnóstico, não corrige automaticamente a causa de cada crash.

## CreativeCore — 2.13.44

`CreativeCore_NEOFORGE_v2.13.44_mc1.21.1.jar`
**CreativeCore** é a biblioteca central dos mods de CreativeMD. Ela reúne infraestrutura reutilizável como **GUI API, sistema de configuração, networking/packets com suporte a pacotes fragmentados, renderização dinâmica e abstrações independentes de loader**, além de utilidades comuns usadas pelos projetos consumidores.
Isso explica por que mods como AmbientSounds dependem dela sem que CreativeCore acrescente conteúdo próprio ao mundo. A build top-level instalada é `2.13.44` para NeoForge 1.21.1.

## Cupboard — 4.1

`cupboard-1.21.1-4.1.jar`
**Cupboard** é uma biblioteca e conjunto de utilidades técnicas compartilhadas por mods do ecossistema Someaddon. A linha moderna concentra helpers de desenvolvimento, configuração e diagnósticos reutilizáveis, evitando que cada mod dependente replique a mesma infraestrutura.
Ela não constitui gameplay próprio; a função observável vem dos mods que consomem suas APIs. A build instalada é `4.1` para NeoForge 1.21.1; esta release melhora a legibilidade das configurações com quebras de linha mais claras sem invalidar configs antigas.

## Curios API — 9.5.1+1.21.1

`curios-neoforge-9.5.1+1.21.1.jar`
**Curios API** fornece um sistema extensível de **slots extras de equipamento/acessórios**. Mods podem registrar identificadores de slot e equipar itens fora da armadura/inventário vanilla — anéis, amuletos, backpacks, spellbooks, jetpacks e outros tipos — usando um contrato central compatível entre diferentes addons.
Por padrão, Curios praticamente não adiciona conteúdo: sua função é expor a GUI/inventário e a API para slots definidos pelos consumidores. Identificadores iguais podem ser mesclados para aumentar interoperabilidade entre mods. A build instalada é `9.5.1+1.21.1`.

## Customizable Player Models — 0.6.27a

`CustomPlayerModels-1.21-0.6.27a.jar`
**Customizable Player Models (CPM)** permite criar e utilizar **modelos de jogador personalizados**, alterando geometria, texturas, partes do corpo, poses e animações do avatar. O ecossistema inclui editor próprio e mecanismos para sincronizar/apresentar esses modelos em multiplayer quando a instalação correspondente está disponível.
No pack, o addon CPM OSC Compat conecta essa representação a protocolos externos, mas CPM continua sendo o provider do modelo/avatar. A build instalada é `0.6.27a` para a linha 1.21.

## Cyclops Core — 1.29.3

`cyclopscore-1.21.1-neoforge-1.29.3.jar`
**Cyclops Core** é a biblioteca comum do ecossistema CyclopsMC, usada por projetos como EvilCraft e Integrated Dynamics. Além de serviços compartilhados de configuração/networking/registro, a documentação pública expõe recursos como **declaração de custom recipes via XML**, permitindo que os mods dependentes reutilizem infraestrutura em vez de duplicá-la.
A library não cria uma linha de gameplay autônoma. A build instalada é `1.29.3` para NeoForge 1.21.1.

## Distant Horizons — 3.2.0-b

`DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar`
**Distant Horizons** estende a distância visual do mundo usando **Level of Detail (LOD)**. Chunks distantes deixam de ser renderizados com toda a geometria normal e são substituídos por representações simplificadas, permitindo enxergar terreno muito além do render distance vanilla sem manter todos esses chunks no pipeline completo de renderização.
O sistema mantém dados de LOD próprios e pode atingir distâncias muito grandes, com custo crescente de RAM/GPU conforme a configuração. Ele não aumenta a simulation distance nem transforma terreno distante em chunks plenamente ativos. A build instalada é `3.2.0-b` para 1.21.1, classificada beta.

## Dynamic Brightness — 1.3.1

`DynamicBrightness-neoforge-1.3.1.jar`
**Dynamic Brightness** adiciona um efeito de **adaptação ocular**: a luminosidade percebida pelo cliente muda gradualmente conforme o jogador passa entre ambientes claros e escuros, evitando transições instantâneas de exposição.
É um efeito visual client-side e não altera light levels reais, mob spawning ou regras de iluminação do servidor. A modlist carrega a build NeoForge `1.3.1`.

## Enchantment Descriptions — 21.1.11

`enchdesc-neoforge-1.21.1-21.1.11.jar`
**Enchantment Descriptions** acrescenta explicações dos efeitos dos encantamentos diretamente aos **tooltips de itens encantados**, incluindo suporte a muitos encantamentos modded. Isso transforma informações que normalmente exigiriam wiki/JEI em documentação contextual dentro do inventário.
Descrições podem ser customizadas e várias opções de apresentação são configuráveis. O mod depende de **Bookshelf**, também top-level no pack. A build instalada é `21.1.11` para NeoForge 1.21.1.

## Enhanced Boss Bars — 1.0.0

`enhancedbossbars-1.0.0.jar`
**Enhanced Boss Bars** substitui visualmente boss bars por apresentações específicas e mais elaboradas, inclusive para bosses de mods suportados. A versão em mod foi criada justamente para lidar melhor com casos de compatibilidade que o resource pack original não conseguia tratar adequadamente, como bosses de **L_Ender's Cataclysm** e Aether.
O efeito é client-side e não altera vida, fases ou IA do boss. A build `1.0.0` é a release NeoForge 1.21.1 instalada.

## Entity Model Features — 3.2.4

`entity_model_features-3.2.4-1.21-neoforge.jar`
**Entity Model Features (EMF)** implementa suporte ao formato **OptiFine Custom Entity Models (CEM)** fora do OptiFine. Resource packs podem substituir ou animar modelos de entidades usando arquivos e regras do ecossistema CEM, inclusive com variações condicionais.
EMF utiliza **Entity Texture Features (ETF)** para recursos como modelos randômicos, configuração e variação das texturas declaradas pelos modelos. É uma camada client-side de renderização/modelos e não altera a lógica das entidades. A build instalada é `3.2.4`.

## Entity Sound Features — 0.8.1

`entity_sound_features-0.8.1-1.21-neoforge.jar`
**Entity Sound Features (ESF)** aplica aos sons de entidades um sistema de variações baseado nas regras `.properties` usadas por ETF/OptiFine. Resource packs podem selecionar **variações sonoras condicionais por entidade/contexto**, em vez de limitar cada mob a um único conjunto estático definido apenas pelo jogo.
O mod também expõe utilidades sonoras adicionais para integração com ETF/EMF. É client-side e a build instalada é `0.8.1`.

## Entity Texture Features — 7.1

`entity_texture_features_1.21-neoforge-7.1.jar`
**Entity Texture Features (ETF)** implementa recursos de texturas de entidade associados ao ecossistema OptiFine: **texturas customizadas, emissivas e variantes condicionais/randômicas**, além de funcionalidades relacionadas a skins de jogador. Resource packs podem usar regras para selecionar aparências conforme propriedades da entidade.
ETF é também dependência funcional de EMF para parte de suas variações e configuração. A build instalada é `7.1` para NeoForge 1.21.1.

## EntityCulling — 1.10.5

`entityculling-neoforge-1.10.5-mc1.21.1.jar`
**EntityCulling** reduz trabalho de renderização usando **path-tracing assíncrono** para determinar entidades e block entities que estão totalmente ocultas por geometria e, portanto, não precisam ser desenhadas naquele momento. O objetivo é diminuir custo de GPU/CPU de cenas com muitos objetos invisíveis atrás de paredes ou outras estruturas.
O culling afeta apenas renderização; entidades continuam existindo e sendo processadas pelo jogo. A build instalada é `1.10.5` para NeoForge 1.21.1.

## EpheroLib — 1.2.0

`EpheroLib-1.21.1-NEO-FORGE-1.2.0.jar`
**EpheroLib** é uma biblioteca multiplataforma criada para facilitar o compartilhamento de infraestrutura entre mods Fabric e Forge/NeoForge. Ela oferece uma base comum para consumidores que precisam abstrair diferenças de plataforma sem implementar caminhos totalmente separados para cada loader.
Não acrescenta uma linha de gameplay isolada. O top-level instalado é `1.2.0` para NeoForge 1.21.1.

## Epic-API — 21.3.1

`epic_api-21.3.1.jar`
**Epic-API** é uma biblioteca específica para addons do **Epic Fight**. A ficha atual registra infraestrutura reutilizável para **animações, weapon capabilities, entity patches, heavy attacks e integrações**, oferecendo contratos para que extensões trabalhem sobre o combate Epic Fight sem copiar a implementação-base.
Ela não é um compat pack geral nem um segundo sistema de combate; sua função é API. A build instalada é `21.3.1`.

## Euphoria Patcher — runtime 1.9.3-r5.8.1-neoforge

`EuphoriaPatcher-1.9.3-r5.8.1-neoforge.jar`
**Euphoria Patches** é um addon para **Complementary Shaders Reimagined/Unbound** que acrescenta uma grande coleção de features e settings opcionais ao shader. O Patcher integra essas extensões ao ambiente do jogo para que o pacote Complementary compatível possa carregar e configurar os patches.
É uma camada estritamente visual/client-side; sem o shader compatível, não se transforma em um sistema de iluminação física do servidor. O runtime instalado preserva `1.9.3-r5.8.1-neoforge`.

## ExpandAbility — 12.0.0

`expandability-12.0.0.jar`
**ExpandAbility** é uma library que expõe eventos para controlar determinadas **habilidades vanilla do jogador**. A versão instalada permite, por exemplo, habilitar natação fora de fluidos, impedir natação mesmo dentro de fluidos e controlar a capacidade de caminhar sobre fluidos.
Isso fornece um contrato reutilizável para mods que querem modificar essas capacidades sem substituir toda a implementação do jogador. A build `12.0.0` suporta NeoForge 1.21/1.21.1.

## Explosive Enhancement: Reforged — 1.1.2

`explosiveenhancement-neoforge-1.21.1-1.1.2.jar`
**Explosive Enhancement: Reforged** substitui a apresentação visual das explosões por efeitos/partículas mais elaborados sem trocar o cálculo vanilla/modded de dano ou a física central da explosão. Assim, TNT, creepers e explosões produzidas por outros sistemas continuam determinando sua força pelas regras originais, enquanto o feedback visual é retrabalhado.
A build instalada é especificamente o fork **Reforged** `1.1.2`, não o projeto homônimo 1.4.x. A ficha do pack também registra um tempfix dessa release para um crash envolvendo o Creeper Head Projectile de Iron's Spells.

## FerriteCore — 7.0.3

`ferritecore-7.0.3-neoforge.jar`
**FerriteCore** é um conjunto de **otimizações de uso de memória** para Minecraft modded. Em vez de reduzir view distance ou remover conteúdo, ele altera estruturas internas e estratégias de armazenamento para evitar manter representações redundantes em memória; a linha `7.0.3` também reduz memória usada por **data component patches**.
O projeto funciona em cliente e servidor. A build `7.0.3-neoforge` é a release 1.21.1 instalada.

## First-person Model — 2.7.2

`firstperson-neoforge-2.7.2-mc1.21.1.jar`
**First-person Model** substitui a apresentação vanilla de primeira pessoa pelo **modelo de terceira pessoa do próprio jogador**, fazendo torso, pernas e equipamento permanecerem visíveis a partir dos olhos do personagem em vez de mostrar apenas braços separados.
O mod não altera animações nem regras de combate: é puramente visual e client-side, podendo conectar-se a qualquer servidor sem instalação obrigatória no servidor. A build instalada é `2.7.2` para NeoForge 1.21.1.

## Forgified Fabric API — runtime 0.116.15+2.3.5+1.21.1

`forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
**Forgified Fabric API (FFAPI)** porta para NeoForge os **hooks, events e APIs essenciais do Fabric API** exigidos por mods Fabric executados através do ecossistema Sinytra Connector. Isso inclui mecanismos de interoperabilidade para registries, partículas, biomas/dimensões, rendering e outras superfícies que muitos mods Fabric assumem como disponíveis.
Ela não é o loader Fabric nem executa mods Fabric sozinha: Connector cuida da camada de compatibilidade de loader e FFAPI fornece as APIs Fabric esperadas pelos consumidores. A build instalada é `0.116.15+2.3.5+1.21.1`.

## Fragmentum — 2.4.4

`fragmentum-neoforge-1.21.1-2.4.4.jar`
**Fragmentum** é o framework leve da **Obscuria Collection**. O projeto concentra ferramentas compartilhadas e uma arquitetura multi-loader para que os mods da coleção mantenham o código de conteúdo separado das diferenças entre plataformas.
O core não adiciona conteúdo quando instalado sozinho; existe porque consumidores dependem de seus serviços comuns. A build NeoForge 1.21.1 instalada é `2.4.4`.

## FTB Library — 2101.1.35

`ftb-library-neoforge-2101.1.35.jar`
**FTB Library** centraliza infraestrutura compartilhada pelos mods FTB, com destaque para **GUI/widgets, utilidades comuns e APIs usadas por FTB Teams, FTB Chunks e outros projetos do ecossistema**. Isso permite que os módulos funcionais compartilhem a mesma linguagem de interface e serviços sem copiar código.
Ela não cria claims ou vein mining sozinha; essas funções pertencem a FTB Chunks e FTB Ultimine. A release top-level instalada é `2101.1.35` para NeoForge 1.21.1.

## FTB Teams — 2101.1.11

`ftb-teams-neoforge-2101.1.11.jar`
**FTB Teams** fornece identidade e persistência de **equipes** para sistemas que compartilham progressão, permissões ou território. Jogadores podem criar teams e configurar propriedades; mods consumidores podem então associar claims, quests, acesso e outros estados à equipe em vez de tratá-los apenas por jogador individual.
No stack atual, FTB Chunks utiliza essa camada para interações e proteção territorial. Os dados das equipes são persistidos no mundo. A build instalada é `2101.1.11` para NeoForge 1.21.1.

## Fusion — runtime 1.3.14+a

`fusion-1.3.14a-neoforge-mc1.21.1.jar`
**Fusion** amplia o formato de resource packs com **connected textures, continuous textures, scrolling textures, overlays e custom entity models**, além de permitir que mods registrem tipos adicionais de textura/modelo. Superfícies podem, por exemplo, conectar bordas entre blocos ou formar uma textura contínua atravessando vários blocos.
Apesar de possuir API para mods, o efeito principal é client-side/renderização de recursos. A build instalada preserva o runtime `1.3.14+a`.

## Fzzy Config — runtime 0.7.6+1.21+neoforge

`fzzy_config-0.7.6+1.21+neoforge.jar`
**Fzzy Config** é um engine de configuração multiplataforma com **serialização automática, geração de GUI, validação/correção de valores, sincronização servidor↔cliente e atualização versionada de configs**. Mod authors podem definir estruturas de configuração e deixar que a biblioteca construa boa parte da interface e do fluxo de sync automaticamente.
As telas são navegáveis por teclado e possuem suporte a narration; a API também integra ambientes como Catalogue. O runtime instalado é `0.7.6+1.21+neoforge`.

## Gabou's Libs — 1.8.7

`gaboulibs-neoforge-1.8.7.jar`
**Gabou's Libs** é a library compartilhada pelos mods de Gaboouu. Ela reúne **sistemas, utilidades e infraestrutura comum**, inclusive serviços usados por projetos ligados a clima, estações e simulação ambiental, para manter comportamento e manipulação de dados consistentes entre consumidores e loaders.
Não adiciona uma mecânica própria quando instalada isoladamente. A build top-level no pack é `1.8.7` para NeoForge; a linha atual reforça networking/autenticação, corrige registros duplicados de payload e amplia tolerância a respostas atrasadas do desafio de verificação.

## GeckoLib 4 — 4.9.2

`geckolib-neoforge-1.21.1-4.9.2.jar`
**GeckoLib** é uma biblioteca de **modelos e animações 3D** usada por entidades, blocos, itens, armaduras e outros objetos. Ela fornece runtime de animação, controllers, keyframes e integração de modelos para que mods criem movimentos complexos e state-driven sem construir um engine próprio.
Por isso muitos mods de criaturas e bosses dependem dela, mas GeckoLib não adiciona essas criaturas por conta própria. A versão instalada é `4.9.2`, release NeoForge 1.21.1.

## Global Packs — 21.0.6

`globalpacks-neoforge-1.21.1-21.0.6.jar`
**Global Packs** carrega **datapacks e resource packs globais** automaticamente em todos os mundos da instância. Em vez de copiar manualmente um datapack para cada save novo, o pack pode ser colocado nas pastas globais geradas pelo mod e passa a participar do carregamento de cada mundo compatível.
Isso é infraestrutura de distribuição/configuração do modpack; o conteúdo real continua pertencendo aos datapacks/resource packs carregados. A build instalada é `21.0.6` para NeoForge 1.21.1.

## Glodium — runtime 1.21-2.2-neoforge

`Glodium-1.21-2.2-neoforge.jar`
**Glodium** é uma code library do ecossistema GlodBlock voltada a **renderização, networking e registries**. Ela fornece implementações comuns para consumidores que precisam registrar conteúdo, trocar dados e renderizar componentes sem repetir a mesma infraestrutura.
A própria página do projeto ressalta que a library não faz nada útil quando instalada sozinha. O runtime preservado no pack é `1.21-2.2-neoforge`.

## GroovyModLoader — 6.0.2

`gml-6.0.2.jar`
**GroovyModLoader (GML)** é um **language provider** que permite carregar mods escritos em Groovy no ambiente NeoForge. Ele inclui módulos da linguagem Groovy e fornece o `GMLLangProvider`, que reconhece declarações `@GMod` e integra esses projetos ao ciclo de carregamento do modloader.
Não é um mod de scripting de gameplay para o jogador: sua função é permitir que outros mods sejam implementados/carregados em Groovy. A build instalada é `6.0.2`, compatível com 1.21/1.21.1.

## Patchouli — runtime 1.21.1-93-NEOFORGE

`Patchouli-1.21.1-93-NEOFORGE.jar`
**Patchouli** é um framework data-driven para livros e guias in-game. Mods consumidores podem definir capítulos, páginas, categorias e progressão documental sem implementar uma interface de livro própria, tornando a biblioteca parte da infraestrutura de documentação do pack.
A build instalada é a release estável `93` para NeoForge 1.21.1. Alguns livros de consumidores antigos ainda podem usar formatos removidos e serem ignorados, mas isso é um problema do conteúdo desses consumidores, não da identidade ou validade do Patchouli. Sua coexistência com GuideME ou Modonomicon não caracteriza redundância automática, porque cada mod consumidor depende da API para a qual foi construído.

## GuideME — 21.1.17

`guideme-21.1.17.jar`
**GuideME** é um toolkit para criação de **guidebooks in-game**. O conteúdo dos guias pode ser escrito em Markdown e enriquecido com elementos interativos, incluindo **cenas 3D ao vivo**, permitindo que mods e modpacks mantenham documentação integrada à própria interface do jogo.
Ele é particularmente usado no ecossistema AE2, mas a infraestrutura pode ser consumida por outros projetos. A build instalada é `21.1.17` para NeoForge 1.21.1.

## Iceberg — 1.3.2

`Iceberg-1.21.1-neoforge-1.3.2.jar`
**Iceberg** é uma modding library que adiciona **events, helpers e utilities** reutilizáveis para projetos NeoForge/Forge. O objetivo é expor hooks e operações comuns que seriam repetidos em cada mod dependente.
Ela não adiciona conteúdo autônomo ao jogador; recursos visíveis pertencem aos mods consumidores. A build instalada é `1.3.2` para NeoForge 1.21.1.

## Iglee's Library — runtime 1.21.1-1.2.7

`igleelib-1.21.1-1.2.7.jar`
**Iglee's Library** é a dependência compartilhada pelos mods de iglee42, centralizando código reutilizado entre seus projetos. A documentação pública a apresenta explicitamente como a library de toda essa família de mods.
Seu papel é de infraestrutura, sem gameplay próprio quando isolada. O runtime instalado preserva `1.21.1-1.2.7`.

## ImmediatelyFast — runtime 1.6.13+1.21.1

`ImmediatelyFast-NeoForge-1.6.13+1.21.1.jar`
**ImmediatelyFast** otimiza o **immediate mode rendering** do cliente através de buffers e batching mais eficientes. A otimização alcança entidades, block entities, partículas, texto, GUI/HUD e rendering imediato feito por outros mods, reduzindo draw calls/uploads redundantes e principalmente custo de CPU em cenas pesadas.
A versão instalada é `1.6.13+1.21.1`; o projeto é client-side e não altera regras de gameplay. Esta release restaura corretamente o depth-test após flush do DrawContext, corrige exceção ao fechar buffers sem uso e elimina vazamento de file handle da configuração.

## InsaneLib — 2.4.29.0

`insanelib-2.4.29.0.jar`
**InsaneLib** é a biblioteca comum dos mods de Insane96, reunindo **utilitários, configuração e infraestrutura compartilhada** para os consumidores do ecossistema. Ela evita que cada projeto replique serviços técnicos iguais.
Não possui alteração relevante de gameplay instalada sozinha e não deve ser tratada como uma alternativa genérica intercambiável a outras APIs. O runtime atual é `2.4.29.0`.

## Integrated API — 1.8.0

`integrated_api-neoforge-1.21.1-1.8.0.jar`
**Integrated API** é a library da família **Integrated Structures**. Ela coloca em um único mod **structure types, abstrações de dados e utilities compartilhadas** que são reutilizadas por Integrated Cataclysm, Integrated Stronghold, Integrated Villages, IDAS e outros projetos relacionados.
A função é estrutural: as dungeons/structures concretas pertencem aos addons consumidores. A build instalada é `1.8.0` para NeoForge 1.21.1.

## Jade — runtime 15.10.6+neoforge

`Jade-1.21.1-NeoForge-15.10.6.jar`
**Jade** é um HUD contextual do tipo Waila/Hwyla: ao olhar para um bloco ou entidade, exibe **nome, mod de origem e dados úteis** fornecidos pelo próprio jogo ou por plugins de integração. Dependendo do alvo, pode mostrar conteúdo de armazenamento, combustível, fluidos, estado de colmeias e outras propriedades sincronizadas pelo servidor.
Também se integra a recipe viewers para abrir receitas/usos do alvo. O runtime instalado é `15.10.6+neoforge`; a build correspondente é a linha 15.10.6 para Minecraft 1.21.1.

## JinxedLib — 1.0.4

`jinxedlib-neoforge-1.21.1-1.0.4.jar`
**JinxedLib** é uma library para desenvolvimento multi-loader e modpacks. Entre as ferramentas públicas estão **helpers de registry, compostabilidade data-driven, furnace fuels data-driven e classes comuns de blocos** que contornam diferenças/visibilidade entre plataformas.
Isso permite que mods consumidores movam mais comportamento para dados e compartilhem código entre loaders. A build NeoForge 1.21.1 instalada é `1.0.4`.

## Jupiter — 2.3.7

`jupiter-2.3.7-1.21.1-neoforge.jar`
**Jupiter** é uma **biblioteca de configuração com sincronização automática**, usada por mods que precisam manter opções e estado configurável coerentes entre lados do jogo. Ela fornece a infraestrutura de config/sync e não adiciona uma mecânica de gameplay própria.
Não deve ser confundida com libraries de UI de configuração genéricas como Cloth/Fzzy: o contrato e os consumidores são específicos. A build instalada é `2.3.7` para NeoForge 1.21.1.

## Melody — 1.0.10

`melody_neoforge_1.0.10_MC_1.21.jar`
**Melody** é uma biblioteca client-side de áudio baseada em **OpenAL**, usada por outros mods para reprodução e gerenciamento de música e sons de fundo. Ela não adiciona conteúdo jogável próprio: fornece serviços de áudio reutilizáveis para consumidores que precisam controlar streams, playback e recursos sonoros fora das rotinas vanilla mais simples.
No pack, **FancyMenu** é consumidor confirmado e declara Melody como dependência obrigatória. A build top-level instalada é `1.0.10`, publicada para a linha Minecraft 1.21 e compatível com 1.21.1; versões posteriores para outras linhas do jogo não substituem esta identidade instalada.

## MRU — 1.0.33+1.21.1

`mru-1.0.33+1.21.1-neoforge.jar`
**MRU** é uma biblioteca compartilhada usada pelos mods de Cassian e IMB11. A linha moderna concentra **registration hooks**, APIs para consultar inventário do jogador e abstrações para conteúdos como **bundles, backpacks e slots/acessórios**, além de helpers multi-version usados para manter os mesmos projetos em várias linhas do Minecraft.
Ela funciona em cliente e servidor e não cria gameplay próprio quando carregada isoladamente. A build instalada é `1.0.33+1.21.1` para NeoForge 1.21.1. O catálogo ainda não comprova qual consumidor atual exige MRU, então o guia descreve o contrato da library sem atribuir dependências específicas não verificadas.

## YUNG's API — runtime 1.21.1-NeoForge-5.1.8

`YungsApi-1.21.1-NeoForge-5.1.8.jar`
**YUNG's API** é a biblioteca compartilhada da família de mods de estruturas e worldgen de YUNG. Ela centraliza contratos, helpers e infraestrutura reutilizada por projetos como Better Mineshafts, Better Dungeons, Better Caves e outros módulos YUNG, evitando que cada addon replique o mesmo backend.
A API não gera estruturas ou cavernas sozinha; o conteúdo visível pertence aos consumidores. A build instalada é `5.1.8` para NeoForge 1.21/1.21.1. Essa versão reverte mudanças problemáticas da 5.1.7 e inclui correções de interoperabilidade com bibliotecas de estruturas; vários consumidores YUNG estão presentes no pack.

## Lionfish API — 3.1

`lionfishapi-3.1.jar`
**Lionfish API** é uma biblioteca leve de animação e utilidades usada pelo ecossistema de **L_Ender's Cataclysm** e outros consumidores. Ela fornece contratos e helpers específicos que esses mods utilizam para comportamento e apresentação, sem acrescentar mobs, bosses ou conteúdo jogável próprio quando instalada isoladamente.
No pack, L_Ender's Cataclysm é um consumidor direto relevante. A build top-level instalada é `3.1`; ela não é intercambiável automaticamente com GeckoLib, AzureLib ou outras libraries de animação, porque cada consumidor depende da API para a qual foi implementado.

## Resourceful Config — 3.0.11

`resourcefulconfig-neoforge-1.21-3.0.11.jar`
**Resourceful Config** é a biblioteca de configuração da Team Resourceful. Ela fornece contratos para mods consumidores **definirem, carregarem, sincronizarem e exporem configurações** sem precisar implementar separadamente toda a infraestrutura de serialização e comunicação entre cliente/servidor.
Ela não adiciona opções de gameplay próprias; as configurações concretas pertencem aos mods que usam a API. A build instalada é `3.0.11` para NeoForge 1.21/1.21.1; essa linha inclui correções de ciclo de vida de configs em dedicated server.

## Uranus — 3.0-beta.1

`uranus-3.0-beta.1.jar`
**Uranus** é uma biblioteca/runtime de animação e utilidades para entidades, derivada do ecossistema técnico de LLibrary/Citadel e usada por consumidores modernos que precisam de helpers específicos de entidades, animação e comportamento.
A build instalada é `3.0-beta.1`, um port NeoForge legítimo para Minecraft 1.21.1 publicado como beta. A linha atual declara requisito de Ice & Fire CE moderno; o pack utiliza Ice And Fire Community Edition 2.1.1. Uranus não adiciona criaturas ou progressão própria quando carregado isoladamente.

## Stylish Effects — 21.1.3

`StylishEffects-v21.1.3-1.21.1-NeoForge.jar`
**Stylish Effects** redesenha a apresentação dos **status effects** no HUD e no inventário. Em vez da lista vanilla ocupar a interface da forma padrão, o mod usa widgets mais compactos e configuráveis, mostrando ícone, duração, amplifier e outras informações de maneira reorganizada.
Posição, layout e comportamento visual podem ser ajustados pelo cliente. O mod não altera duração, potência ou regras dos efeitos; modifica apenas como esses dados são apresentados. A build instalada é `21.1.3` para NeoForge 1.21.1 e utiliza Puzzles Lib.

## Sounds — 2.4.22+lts

`sounds-2.4.22+lts+1.21.1-neoforge.jar`
**Sounds** amplia a resposta sonora do cliente com novos efeitos para **interações, blocos e elementos de interface**, além de permitir que parte desse comportamento seja personalizada por resources/data. A intenção é fazer ações cotidianas possuírem feedback acústico mais específico do que o conjunto vanilla.
Ele fornece sons; não é um sistema de propagação acústica. Por isso é funcionalmente distinto de mods Sound Physics, que trabalham com oclusão, distância e propagação do áudio já existente. A build instalada é `2.4.22+lts` para NeoForge 1.21.1.

## Sodium — 0.8.13-beta.2+mc1.21.1

`sodium-neoforge-0.8.13-beta.2+mc1.21.1.jar`
**Sodium** substitui e otimiza partes centrais do **pipeline de renderização** do Minecraft com foco em FPS, eficiência de CPU/GPU, construção de chunks e redução de trabalho gráfico desnecessário. Ele funciona como base de renderização sobre a qual outras extensões gráficas e bridges do pack precisam permanecer compatíveis.
A instalação atual é a build oficial NeoForge `0.8.13-beta.2+mc1.21.1`. O sufixo beta representa maturidade da linha, não dúvida sobre a identidade do JAR. No stack atual ele participa do núcleo gráfico junto a Flywheel, Sable, shaders e dynamic lights.

## Reese's Sodium Options — 2.2.3+mc1.21.1

`reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar`
**Reese's Sodium Options** substitui/reorganiza a tela de opções gráficas do Sodium para tornar um grande conjunto de configurações mais navegável. As opções são distribuídas em categorias e uma interface mais compacta, facilitando localizar e comparar ajustes sem alterar a lógica do renderer.
O addon é client-side e **não aumenta FPS por si próprio**: a otimização continua pertencendo ao Sodium. A build instalada é `2.2.3+mc1.21.1` para NeoForge 1.21.1.

## Vanity — arquivo 5.0.3; metadata 5.0.2

`vanity-neoforge-1.21.1-5.0.3.jar`
**Vanity** é um sistema data-driven de **customização cosmética de equipamentos e itens**. Vanity Packs definem Designs que podem ser aplicados por meio da Styling Table, permitindo trocar a aparência apresentada por um item sem substituir sua identidade funcional, atributos ou lógica original.
O sistema funciona também como infraestrutura para addons cosméticos; `Malum: Vestis`, por exemplo, depende de Vanity para disponibilizar seus designs de foices. O arquivo instalado é `5.0.3`, enquanto o metadata interno detectado informa `5.0.2`; essa divergência de empacotamento é preservada no catálogo em vez de ser normalizada.

## Modern UI — 3.13.0.1

`ModernUI-NeoForge-1.21.1-3.13.0.1-universal.jar`
**Modern UI** é um framework client-side de interface e renderização de texto. Ele acrescenta **font rendering avançado, animações, emoji, blur e componentes de UI**, além de infraestrutura reutilizável por telas e interfaces que precisam de apresentação mais sofisticada que os widgets vanilla.
Seu papel é simultaneamente visual e de framework; não cria progressão ou conteúdo de mundo. A build `3.13.0.1` inclui correções de compatibilidade relevantes para rendering de tooltips/texto, inclusive casos envolvendo ImmediatelyFast e Create, e é a versão instalada para NeoForge 1.21.1.

## Photon — 2.2.4

`photon-neoforge-1.21.1-2.2.4-all.jar`
**Photon** é um framework e **editor avançado de VFX** para mods consumidores. Ele oferece sistemas de partículas, trails, shaders e ferramentas de composição visual para criar efeitos complexos sem que cada projeto implemente do zero seu próprio editor e runtime de efeitos.
A linha `2.2.4` inclui correções para recompilação repetitiva de shaders e outros problemas do pipeline de VFX. A build instalada é beta oficial para NeoForge 1.21.1; Photon atua como infraestrutura visual e não como um particle pack decorativo autônomo.

## Lodestone — 1.8.2

`lodestone-1.21.1-1.8.2.jar`
**Lodestone** é o framework compartilhado da Lodestar Team e concentra principalmente infraestrutura de **renderização e efeitos** para mods consumidores. O pacote inclui shaders, utilities, screenshake, atributos comuns, fogo customizado aplicado a entidades e um sistema de partículas com grande quantidade de parâmetros por partícula.
A biblioteca também fornece partículas de GUI, helpers simples para multiblocks e inventários de block entities, um sistema de **world events persistentes por dimensão** e carregamento/modificação de texturas em runtime. Esses recursos permitem que mods dependentes implementem efeitos e sistemas visuais complexos sem manter backends separados. A build `1.8.2` é a release NeoForge 1.21.1 instalada e inclui melhorias de screen-particle rendering e mudanças internas de backend.

## Create: Big Contraptions — 1.0

`bigcontraptions-neoforge-1.0.jar`
Apesar do nome, **Create: Big Contraptions não é um addon funcional do Create**. É um patch client-side para o limite de playerdata associado ao bug MC-185901, elevando a quantidade de dados de jogador que o cliente consegue receber/manusear para permitir casos com estruturas ou estados muito volumosos, inclusive contraptions com grande NBT. Não adiciona máquinas, cinética ou contraptions próprias.

## ModernFix — 5.27.20+mc1.21.1

`modernfix-neoforge-5.27.20+mc1.21.1.jar`
**ModernFix** reúne otimizações e correções para instalações modded, atuando em memória, carregamento, caches e outros caminhos internos do jogo. A função é reduzir custo e corrigir problemas estruturais sem adicionar conteúdo jogável; ele pode coexistir com otimizações especializadas como FerriteCore porque os projetos atuam em mecanismos diferentes.

## YDM's Weapon Master — 4.2.7

`weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`
**YDM's Weapon Master** exibe itens e armas do hotbar diretamente no corpo do jogador. Posições podem ser configuradas e o sistema possui tratamento para categorias como shields e banners, funcionando como uma camada de representação de equipamento visível e não como alteração dos atributos ou movesets dessas armas.

## Sky Aesthetics — 2.0.13-beta

`sky_aesthetics-neoforge-2.0.13-beta.jar`
**Sky Aesthetics** amplia a apresentação visual do céu e da atmosfera com elementos e variações adicionais, incluindo recursos ligados ao skybox, estrelas e constelações. É um componente de renderização/ambientação; a build instalada é beta e pode se compor visualmente com shaders e outras camadas de céu, mas não altera clima ou regras ambientais do servidor.

## Tooltip Overhaul — 1.5.1

`tooltipoverhaul-neoforge-1.21.1-1.5.1.jar`
**Tooltip Overhaul** reformula a leitura de tooltips extensos com wrapping, rolagem, comparação de equipamentos e ajustes de apresentação. A linha instalada também trabalha com integrações como JEI/EMI, ModernFix e conteúdo complexo de equipamentos, mantendo a função estritamente informativa/visual e sem modificar os atributos exibidos.

## Model Gap Fix — runtime 1.21-1.10

`modelfix-1.21-1.10.jar`
**Model Gap Fix** corrige pequenas frestas e linhas visuais que podem surgir em modelos de itens e blocos devido à forma como a geometria/textura é processada pelo renderer. É um patch client-side de apresentação e não altera hitbox, modelo lógico ou comportamento dos objetos.

## Just Enough Professions (JEP) — 4.0.5

`JustEnoughProfessions-neoforge-1.21.1-4.0.5.jar`
**JEP** é um addon informativo do JEI que mostra qual **workstation/job-site block** corresponde a cada profissão de villager. Ele consulta profissões registradas, inclusive modded quando suportadas, mas não cria profissões, não muda trades e não altera a IA dos villagers.

## Fancy World Animations — 1.2.31

`fwa+1.21.1-neoforge-1.2.31.jar`
**Fancy World Animations** adiciona transições e movimentos suaves a elementos do mundo como portas, trapdoors, botões, alavancas, lanternas, jukeboxes e outras interações. Atua no pipeline visual do cliente; a linha 1.2.31 inclui compatibilidade com o occlusion culling do EntityCulling.

## NotEnoughAnimations — 1.12.4

`notenoughanimations-neoforge-1.12.4-mc1.21.1.jar`
**NotEnoughAnimations** acrescenta e melhora animações de terceira pessoa para ações que o vanilla representa de forma limitada ou apenas em primeira pessoa. É conteúdo visual pronto para o jogador e não uma API: Player Animator e Player Animation Library fornecem infraestrutura, enquanto NotEnoughAnimations fornece animações concretas.

## Player Animator — 2.0.4+1.21.1

`player-animation-lib-forge-2.0.4+1.21.1.jar`
**Player Animator** é a biblioteca/API do ecossistema playeranimator/KosmX usada por outros mods para aplicar animações customizadas ao modelo do jogador. Ela permite controlar poses e partes do corpo e fornece a infraestrutura de composição/reprodução utilizada por consumidores que dependem especificamente dessa API.
O JAR top-level é uma entrada canônica da modlist atual. Alguns mods podem embarcar uma cópia interna via JarJar, mas isso não transforma o arquivo top-level em duplicata nem prova que todos os seus consumidores estejam atendidos pela cópia interna. A build instalada é `2.0.4+1.21.1`.

## Player Animation Library — 1.1.6+mc.1.21.1

`PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`
**Player Animation Library** é uma segunda biblioteca de animação do jogador, com implementação e API próprias. Ela fornece mecanismos para mods aplicarem e comporem animações sobre o player mantendo interoperabilidade entre movimentos de diferentes consumidores.
Apesar do objetivo geral semelhante ao Player Animator, as duas APIs não são intercambiáveis automaticamente: dependentes compilados para uma delas não passam a usar a outra sem integração específica. A versão instalada é `1.1.6+mc.1.21.1` para NeoForge 1.21.1.

## Just Enough Items (JEI) — 19.44.0.406

`jei-1.21.1-neoforge-19.44.0.406.jar`
**Just Enough Items (JEI)** é o índice central de itens, ingredientes, receitas e usos do pack. A lista lateral permite pesquisar conteúdo registrado por nome/mod e inspecionar **como um item é produzido** e **em quais processos ele é utilizado**, enquanto a interface de categorias representa crafting, cooking, máquinas e outros tipos de recipe expostos pelo jogo ou por plugins.
O sistema também oferece bookmarks/favoritos e mecanismos de navegação entre ingredientes e receitas, tornando-se a camada de consulta comum para um pack com muitos sistemas de crafting. Sua API permite que outros mods registrem categorias próprias, catalysts/máquinas, transfer handlers, ingredient types e informações adicionais sem precisar criar um recipe viewer independente.
Addons como **Just Enough Professions**, **Advanced Loot Info**, JEED e integrações específicas ampliam os dados apresentados, mas não substituem o core JEI. A build instalada é `19.44.0.406` para NeoForge 1.21.1.

## Mouse Tweaks — 2.26.1

`MouseTweaks-neoforge-mc1.21-2.26.1.jar`
**Mouse Tweaks** melhora gestos de inventário, principalmente drag e scroll para distribuir, mover ou transferir stacks com menos cliques. O efeito é restrito à interação das GUIs: ele não cria inventários, armazenamento ou logística próprios.

## Searchables — 1.0.2

`Searchables-neoforge-1.21.1-1.0.2.jar`
**Searchables** é uma biblioteca para campos de busca, filtros e autocomplete. No pack há consumidor causal confirmado: **Controlling 19.0.5** usa essa infraestrutura para sua interface de pesquisa de keybinds; Searchables não funciona como um recipe viewer ou mecanismo de busca de itens independente.

## SuperMartijn642's Core Lib — 1.1.24

`supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`
**SuperMartijn642's Core Lib** reúne utilities e infraestrutura reutilizadas pelos mods do ecossistema SuperMartijn642. É uma dependência técnica geral, sem uma linha de gameplay própria; sua necessidade decorre dos consumidores implementados contra essa API.

## SuperMartijn642's Config Library — 1.1.8

`supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`
**SuperMartijn642's Config Library** fornece especificamente a camada de configuração usada por mods do mesmo ecossistema. Ela complementa a Core Lib em outro papel: core/utilities gerais ficam na primeira, enquanto definição e manejo de configurações ficam nesta biblioteca.

## TenshiLib — runtime 1.21.1-2.3.0.b-neoforge

`tenshilib-1.21.1-2.3.0.b-neoforge.jar`
**TenshiLib** é a biblioteca/core compartilhada dos projetos de flemmli97. O próprio projeto a define como o core e library usado pelos outros mods do autor: ela centraliza código comum, utilidades e infraestrutura reutilizável para que os consumidores não precisem embarcar implementações duplicadas.
Ela funciona em **cliente e servidor** e não representa, isoladamente, um sistema de gameplay. O arquivo instalado `tenshilib-1.21.1-2.3.0.b-neoforge.jar` é a release NeoForge 1.21.1 publicada em 22/08/2026; o changelog de `2.3.0.b` registra especificamente uma correção do arquivo TOML do NeoForge. O runtime preservado pela modlist é `1.21.1-2.3.0.b-neoforge`.

## PrickleMC — 21.1.11

`prickle-neoforge-1.21.1-21.1.11.jar`
**PrickleMC** é uma biblioteca de configuração e utilidades consumida por outros mods. Seu contrato é específico e não deve ser tratado como substituto genérico de Cloth Config, Fzzy Config ou outras config libraries apenas porque todas atuam no domínio de configuração.

## Subtle Effects — 1.14.3

`SubtleEffects-neoforge-1.21.1-1.14.3.jar`
**Subtle Effects** acrescenta pequenas partículas e alguns sons ambientais para tornar ações e ambientes mais expressivos sem um grande overhaul visual. Distância, culling e diversos efeitos são configuráveis; alguns recursos são estritamente client-side e o mod utiliza Fzzy Config.

## Resourceful Lib — 3.0.12

`resourcefullib-neoforge-1.21-3.0.12.jar`
**Resourceful Lib** é a library geral da Team Resourceful, reunindo utilidades de registries, networking, dados e recursos para mods consumidores. Ela é distinta de Resourceful Config: a primeira fornece infraestrutura ampla; a segunda é especializada no sistema de configurações.

## Knight Lib — 1.6.1

`knightlib-neoforge-1.21.1-1.6.1.jar`
**Knight Lib** é uma biblioteca de suporte usada por mods consumidores que foram implementados contra sua API. Ela centraliza infraestrutura e serviços compartilhados para que esses projetos não precisem duplicar a mesma base técnica em cada JAR.
Não adiciona gameplay relevante isoladamente e não é intercambiável automaticamente com Architectury, Cloth Config ou outras libraries genéricas: cada consumidor exige o contrato específico que utiliza. A build instalada é `1.6.1` para NeoForge 1.21.1.

## ShatterLib | OctoLib — runtime 0.6.2

`OctoLib-NEOFORGE-0.6.2+1.21.jar`
**OctoLib** é uma biblioteca compartilhada do ecossistema OctoStudios/Shatterbyte. Ela fornece configuração YAML com validação/serialização profunda e infraestrutura visual/técnica reutilizável, incluindo tween/keyframes, UI particles, child widgets, cores/easings, trails e networking.
No pack há consumidor causal confirmado: **Relics** declara OctoLib/ShatterLib como dependência. O filename instalado é `0.6.2+1.21`, enquanto o metadata runtime canônico é `0.6.2`; o guia preserva as duas identidades. A biblioteca não adiciona gameplay independente e não é substituível por GeckoLib, Lodestone ou outras APIs apenas por semelhança de categoria.

## SmartBrainLib — 1.16.11

`SmartBrainLib-neoforge-1.21.1-1.16.11.jar`
**SmartBrainLib** é uma biblioteca de IA voltada a behavior trees, sensores, memories, tasks e utilidades de cérebro reutilizadas por mods de entidades. Ela fornece infraestrutura para implementar comportamentos mais complexos sem cada projeto recriar toda a camada de decisão de mobs.
A build instalada é `1.16.11` para NeoForge 1.21.1. O catálogo ainda não confirmou um consumidor causal específico no pack, portanto a classificação permanece **Sem decisão**: a biblioteca deve ser mantida documentada como top-level instalado, mas não promovida automaticamente a dependência nem tratada como gameplay próprio.

## Puzzles Lib — 21.1.52

`PuzzlesLib-v21.1.52-1.21.1-NeoForge.jar`
**Puzzles Lib** é a biblioteca compartilhada do ecossistema Fuzs. Ela fornece infraestrutura de **registro, configuração, networking e utilidades comuns** usada pelos mods consumidores, permitindo que funcionalidades recorrentes sejam implementadas uma vez na library.
No pack há consumidor causal confirmado: **Overflowing Bars 21.1.1** carrega e utiliza diretamente classes/eventos da Puzzles Lib. Por isso esta entrada representa uma dependência top-level real, não uma library órfã inferida apenas pelo nome. A build instalada é `21.1.52`, release estável NeoForge 1.21.1.

## Particle Rain — 4.0.0-beta.11

`particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`
**Particle Rain** é um overhaul client-side da apresentação do clima. Ele substitui chuva e neve vanilla por partículas com movimento e colisão mais naturais e adiciona efeitos atmosféricos configuráveis como **wind-blown rain, mist, haze e sandstorms**, com regras por bioma, bloco e condição climática.
O mod não calcula estações, temperatura corporal ou um novo sistema meteorológico: ele lê as condições existentes e muda sua apresentação visual. Quantidade de partículas, obstrução e efeitos individuais podem ser configurados. A build instalada é `4.0.0-beta.11` para NeoForge 1.21.1.

## Particular Reforged — 1.5.7

`particular-1.21.1-NeoForge-1.5.7.jar`
**Particular Reforged** é uma camada client-side de **partículas ambientais e efeitos de interação**, enriquecendo especialmente água, chuva, superfícies e movimentos no ambiente. O objetivo é tornar ações e fenômenos já existentes mais visíveis por partículas adicionais, sem alterar as regras físicas ou climáticas que os originam.
Ele não substitui shader nem um sistema de clima: apenas acrescenta apresentação visual aos eventos. No stack atual, a release `1.5.7` possui inclusive correção específica para **boats/ships Sable**, evitando spam de splashes durante navegação. A build instalada é a release estável NeoForge 1.21.1 de 16/08/2026.

## LowDragLib2 — 2.2.37

`ldlib2-neoforge-1.21.1-2.2.37-all.jar`
**LowDragLib2 (LDLib2)** é um framework técnico avançado para mods que precisam de **UI, rendering, sincronização, persistência de dados e editores in-game** mais complexos do que os componentes vanilla fornecem diretamente. Ele oferece widgets, ferramentas de edição e infraestrutura reutilizável para consumidores que constroem interfaces e sistemas visuais próprios.
No pack, **KilaGraph** é um consumidor relevante dessa infraestrutura. LDLib2 não adiciona conteúdo visual autônomo nem substitui libraries genéricas; fornece APIs específicas que os consumidores foram escritos para usar. O runtime instalado é `2.2.37` para NeoForge 1.21.1.

## Kiwi — runtime 15.8.7+neoforge

`Kiwi-1.21.1-NeoForge-15.8.7.jar`
**Kiwi** é o toolkit/library da Snownee usado por mods de conteúdo e QoL que dependem de sua infraestrutura. Ele fornece contratos e utilidades compartilhadas para que os consumidores implementem recursos sem replicar a mesma base técnica.
Não acrescenta uma linha de gameplay própria quando instalado isoladamente e não é intercambiável automaticamente com Architectury, Balm ou outras libraries: mods que dependem de Kiwi esperam sua API específica. O arquivo instalado é a build NeoForge 1.21.1 `15.8.7`, com runtime `15.8.7+neoforge`.

## WunderLib NeoForge — 21.0.10

`wunderlib-21.0.10.jar`
**WunderLib** é uma biblioteca compartilhada do ecossistema **BetterX/New Dawn**, usada por BetterEnd: New Dawn, BetterNether: New Dawn e componentes relacionados. Ela centraliza infraestrutura reutilizável necessária por esses projetos, evitando que cada mod replique a mesma base técnica.
No pack, seus consumidores atuais incluem BetterEnd e BetterNether. WunderLib não é equivalente a BCLib ou WorldWeaver: essas bibliotecas ocupam contratos diferentes dentro do mesmo stack e podem coexistir como dependências complementares. A build top-level instalada é `21.0.10` para NeoForge 1.21.1.

## Structurize — runtime 1.0.832-1.21.1

`structurize-1.0.832-1.21.1.jar`
**Structurize** é a infraestrutura de **blueprints, seleção de área, preview e colocação de estruturas** usada principalmente pelo MineColonies. Ela fornece ferramentas e contratos para representar construções complexas e reconstruí-las no mundo preservando estados/orientações necessários.
MineColonies `1.1.1374-1.21.1-snapshot` instalado exige Structurize `>=1.0.832`, e a build presente corresponde exatamente a esse piso. Embora trabalhe com schematics/estruturas, sua função é distinta do Schematicannon e das ferramentas de blueprint do Create: ela é o backend estrutural esperado pelo ecossistema MineColonies.

## MonoLib — 4.1.0

`monolib-neoforge-1.21.1-4.1.0.jar`
**MonoLib** é uma biblioteca compartilhada que fornece **eventos, comandos, registro e utilidades comuns** para mods consumidores. Ela não adiciona uma progressão ou sistema jogável relevante quando instalada isoladamente; seu comportamento aparece por meio dos projetos que utilizam sua API.
No pack, **Dis-Enchanting Table** é consumidor confirmado. Por isso MonoLib deve ser lida como dependência técnica top-level, não como alternativa intercambiável a outras libraries genéricas. A build instalada é `4.1.0` para NeoForge 1.21.1.

## Konkrete — 1.9.9

`konkrete_neoforge_1.9.9_MC_1.21.jar`
**Konkrete** é uma biblioteca técnica usada por mods do ecossistema Keksuccino e projetos relacionados. Ela centraliza utilidades e infraestrutura compartilhada que esses consumidores esperam encontrar, sem adicionar por si só uma progressão ou sistema de gameplay relevante.
A versão instalada é `1.9.9`, correspondente à linha aplicável a Minecraft 1.21/1.21.1; releases posteriores do projeto miram versões mais novas do jogo e não substituem automaticamente este JAR. Como outras libraries específicas, Konkrete não é intercambiável apenas por existir outra API genérica no pack.

## Zeta — 1.1-40

`Zeta-1.1-40.jar`
**Zeta** é a biblioteca estrutural criada para mods modulares do ecossistema Vazkii. Ela é uma reestruturação do sistema modular originalmente desenvolvido dentro do Quark e funciona como sucessora do AutoRegLib, concentrando a infraestrutura comum necessária para registro, organização e funcionamento de módulos sem obrigar cada consumidor a reimplementar esse backend.
No pack, **Quark 4.1-482** é o consumidor principal registrado, portanto Zeta não é uma library órfã nem um substituto genérico de Architectury, Balm ou outras APIs. O JAR `Zeta-1.1-40.jar` é exatamente a release NeoForge 1.21.1 publicada em 24/04/2026 e presente na modlist canônica.

## A Good Place — runtime 1.21-1.2.5

`a_good_place-1.21-1.2.5-neoforge.jar`
**A Good Place** é uma melhoria visual client-side para colocação de blocos. Em vez de o bloco simplesmente aparecer no destino, partículas/modelos produzem uma animação de colocação. Resource packs podem definir quais blocos participam e controlar a própria trajetória por pontos de controle.

## sable-x-cpm — 0.3.2+1.21.1

`sable-x-cpm-0.3.2+1.21.1.jar`
**sable-x-cpm** faz outfits/modelos de **Customizable Player Models** serem aplicados também às peças físicas dos ragdolls e cadáveres Sable. Sem a bridge, cada peça usa um PlayerModel vanilla que CPM não intercepta; o addon associa o modelo CPM correto antes da renderização. Capas/elytra permanecem no caminho vanilla e peças customizadas muito afastadas do osso pai podem apresentar separação nas juntas.

## Dynamic RPG Resource Bars — 0.7.1

`dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar`
**Dynamic RPG Resource Bars** fornece barras animadas e reposicionáveis para vida, stamina e mana. A aparência pode ser substituída por sprites de resource pack e um editor integrado controla layout/apresentação. Na linha NeoForge 1.21.1, o mod consegue ler mana de **Ars Nouveau** e **Iron's Spells 'n Spellbooks**; ele apenas apresenta recursos existentes e não cria um novo sistema de mana ou stamina.

## Overflowing Bars — 21.1.1

`OverflowingBars-v21.1.1-1.21.1-NeoForge.jar`
**Overflowing Bars** reformula a apresentação de **vida, armadura e armor toughness quando esses valores ultrapassam os limites visuais vanilla**. Em vez de criar várias linhas de corações/ícones ou depender de códigos de cor difíceis de acompanhar, ele empilha camadas compactas e mostra um **contador de linhas/camadas**, tornando valores RPG muito altos legíveis sem ocupar grande parte da tela.
O mod é exclusivamente client-side e não altera os atributos, seus limites nem o cálculo de dano: ele só representa no HUD valores produzidos por sistemas como atributos, equipamentos e efeitos externos. A build instalada `21.1.1` é a release NeoForge 1.21.1; essa versão também evita renderizar os elementos quando o HUD é ocultado com `F1`.

## Just Enough Effect Descriptions — runtime 1.21-2.3.2

`jeed-1.21-2.3.2.jar`
**JEED** adiciona ao recipe viewer informações e descrições para efeitos de status e poções registrados. Funciona como plugin para interfaces compatíveis como JEI/REI/EMI e serve como camada de consulta; não cria nem altera os efeitos que documenta.

## Moonlight Lib — runtime 1.21.1-3.5.2

`moonlight-1.21.1-3.5.2-neoforge.jar`
**Moonlight Lib (Selene)** é uma biblioteca client/server usada por vários mods para evitar que cada projeto reimplemente infraestrutura comum. Ela fornece utilidades para **registro dinâmico, geração dinâmica de assets, villagers e IA customizada, trades data-driven, global datapacks, map markers e animações de itens em primeira/terceira pessoa**, além de outros helpers de conteúdo e sincronização.
No pack, consumidores confirmados incluem **Supplementaries** e outros projetos do ecossistema MehVahdJukaar. A build `3.5.2` é a release NeoForge 1.21.1 de 27/08/2026; essa atualização adiciona `ILoomItem`, permitindo que itens não-banner participem do Loom e desenhem seu próprio preview, adiciona `TabAdderHelper` e aplica um hotfix sobre a atualização anterior. Como biblioteca, Moonlight não cria uma progressão jogável isolada: suas funções aparecem por meio dos mods consumidores.

## Polytone — runtime 1.21-4.0.2

`polytone-1.21-4.0.2-neoforge.jar`
**Polytone** é um framework orientado a **resource packs** para modificar profundamente apresentação visual e ambiental sem exigir que cada pack crie um mod Java próprio. Ele suporta **colormaps e lightmaps, block sounds, partículas customizadas e emitters em modelos de entidades, post shaders, custom item models, texturas variantes por bioma, cores de mapas, raridade/tooltips, creative tabs e modificações de GUI**, incluindo mover slots e adicionar elementos.
Também consegue alterar partículas existentes, controlar cores e offsets de blocos e trabalhar com texturas animadas dependentes do horário do mundo; parte do formato mantém compatibilidade com convenções do OptiFine. Na linha `4.0.2`, mudanças de GPU particles foram portadas para 1.21.1: initializers podem usar um campo de colormap amostrado no spawn e particle colormaps passam a ser amostrados na própria partícula, evitando que todas compartilhem indevidamente uma única cor. Links da tela de informações do pack também voltaram a ser clicáveis.
