# 11E. Infraestrutura técnica — parte 5

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **Integrated API — 1.8.0** até **ModernFix — 5.27.24+mc1.21.1**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

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
A build instalada é `3.0-beta.1`, um port NeoForge legítimo para Minecraft 1.21.1 publicado como beta. A linha atual declara requisito de Ice & Fire CE moderno; o pack utiliza Ice And Fire Community Edition `2.1.2`. Uranus não adiciona criaturas ou progressão própria quando carregado isoladamente.

## Stylish Effects — 21.1.3

`StylishEffects-v21.1.3-1.21.1-NeoForge.jar`
**Stylish Effects** redesenha a apresentação dos **status effects** no HUD e no inventário. Em vez da lista vanilla ocupar a interface da forma padrão, o mod usa widgets mais compactos e configuráveis, mostrando ícone, duração, amplifier e outras informações de maneira reorganizada.
Posição, layout e comportamento visual podem ser ajustados pelo cliente. O mod não altera duração, potência ou regras dos efeitos; modifica apenas como esses dados são apresentados. A build instalada é `21.1.3` para NeoForge 1.21.1 e utiliza Puzzles Lib.

## Sounds — 2.4.22+lts

`sounds-2.4.22+lts+1.21.1-neoforge.jar`
**Sounds** amplia a resposta sonora do cliente com novos efeitos para **interações, blocos e elementos de interface**, além de permitir que parte desse comportamento seja personalizada por resources/data. A intenção é fazer ações cotidianas possuírem feedback acústico mais específico do que o conjunto vanilla.
Ele fornece sons; não é um sistema de propagação acústica. Por isso é funcionalmente distinto de mods Sound Physics, que trabalham com oclusão, distância e propagação do áudio já existente. A build instalada é `2.4.22+lts` para NeoForge 1.21.1.

## Sodium — 0.8.13+mc1.21.1

`sodium-neoforge-0.8.13+mc1.21.1.jar`
**Sodium** substitui e otimiza partes centrais do **pipeline de renderização** do Minecraft com foco em FPS, eficiência de CPU/GPU, construção de chunks e redução de trabalho gráfico desnecessário. Ele funciona como base de renderização sobre a qual outras extensões gráficas e bridges do pack precisam permanecer compatíveis.
A instalação atual é a build oficial NeoForge `0.8.13+mc1.21.1`. O sufixo beta representa maturidade da linha, não dúvida sobre a identidade do JAR. No stack atual ele participa do núcleo gráfico junto a Flywheel, Sable, shaders e dynamic lights.

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

## Photon — 2.2.5

`photon-neoforge-1.21.1-2.2.5-all.jar`
**Photon** é um framework e **editor avançado de VFX** para mods consumidores. Ele oferece sistemas de partículas, trails, shaders e ferramentas de composição visual para criar efeitos complexos sem que cada projeto implemente do zero seu próprio editor e runtime de efeitos.
A linha `2.2.5` inclui correções para recompilação repetitiva de shaders e outros problemas do pipeline de VFX. A build instalada é beta oficial para NeoForge 1.21.1; Photon atua como infraestrutura visual e não como um particle pack decorativo autônomo.

## Lodestone — 1.8.2

`lodestone-1.21.1-1.8.2.jar`
**Lodestone** é o framework compartilhado da Lodestar Team e concentra principalmente infraestrutura de **renderização e efeitos** para mods consumidores. O pacote inclui shaders, utilities, screenshake, atributos comuns, fogo customizado aplicado a entidades e um sistema de partículas com grande quantidade de parâmetros por partícula.
A biblioteca também fornece partículas de GUI, helpers simples para multiblocks e inventários de block entities, um sistema de **world events persistentes por dimensão** e carregamento/modificação de texturas em runtime. Esses recursos permitem que mods dependentes implementem efeitos e sistemas visuais complexos sem manter backends separados. A build `1.8.2` é a release NeoForge 1.21.1 instalada e inclui melhorias de screen-particle rendering e mudanças internas de backend.

## Create: Big Contraptions — 1.0

`bigcontraptions-neoforge-1.0.jar`
Apesar do nome, **Create: Big Contraptions não é um addon funcional do Create**. É um patch client-side para o limite de playerdata associado ao bug MC-185901, elevando a quantidade de dados de jogador que o cliente consegue receber/manusear para permitir casos com estruturas ou estados muito volumosos, inclusive contraptions com grande NBT. Não adiciona máquinas, cinética ou contraptions próprias.

## ModernFix — 5.27.24+mc1.21.1

`modernfix-neoforge-5.27.24+mc1.21.1.jar`
**ModernFix** reúne otimizações e correções para instalações modded, atuando em memória, carregamento, caches e outros caminhos internos do jogo. A função é reduzir custo e corrigir problemas estruturais sem adicionar conteúdo jogável; ele pode coexistir com otimizações especializadas como FerriteCore porque os projetos atuam em mecanismos diferentes.
