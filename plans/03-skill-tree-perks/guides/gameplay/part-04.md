<!-- Snapshot canônico do Notion: GUIA COMPLETO — Gameplay e Sistemas | NeoForge 1.21.1
Fonte: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6
Parte 4/4. Continuação de part-03.md. -->

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
# 12. Navegação entre os três guias
- <mention-page url="https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03"/>
- <mention-page url="https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff"/>