# 11F. Infraestrutura técnica — parte 6

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **YDM's Weapon Master — 4.2.7** até **ShatterLib | OctoLib — runtime 0.6.2**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

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

## Just Enough Items (JEI) — 19.53.0.425

`jei-1.21.1-neoforge-19.53.0.425.jar`
**Just Enough Items (JEI)** é o índice central de itens, ingredientes, receitas e usos do pack. A lista lateral permite pesquisar conteúdo registrado por nome/mod e inspecionar **como um item é produzido** e **em quais processos ele é utilizado**, enquanto a interface de categorias representa crafting, cooking, máquinas e outros tipos de recipe expostos pelo jogo ou por plugins.
O sistema também oferece bookmarks/favoritos e mecanismos de navegação entre ingredientes e receitas, tornando-se a camada de consulta comum para um pack com muitos sistemas de crafting. Sua API permite que outros mods registrem categorias próprias, catalysts/máquinas, transfer handlers, ingredient types e informações adicionais sem precisar criar um recipe viewer independente.
Addons como **Just Enough Professions**, **Advanced Loot Info**, JEED e integrações específicas ampliam os dados apresentados, mas não substituem o core JEI. A build instalada é `19.53.0.425` para NeoForge 1.21.1.

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
