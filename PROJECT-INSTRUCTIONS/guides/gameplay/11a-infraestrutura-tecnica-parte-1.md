# 11A. Infraestrutura técnica — parte 1

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **NeoForge — runtime neoforge-21.1.248** até **BlockUI — runtime 1.0.211-1.21.1-snapshot**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

## NeoForge — runtime neoforge-21.1.248

`neoforge-21.1.248 (modloader)`
**NeoForge** é o modloader e a plataforma de execução sobre a qual esta instância 1.21.1 funciona. Ele fornece o ciclo de carregamento de mods e a infraestrutura comum usada pelos JARs NeoForge: descoberta de mods, registries, event buses, networking, configuração e hooks que permitem que código externo interaja com o Minecraft sem alterar cada sistema diretamente.
A entrada aparece explicitamente na modlist canônica como `neoforge-21.1.248 (modloader)`, com runtime `neoforge-21.1.248`. Ela representa a plataforma do pack, não um mod de conteúdo e não deve ser confundida com uma biblioteca jar-in-jar de algum addon.

## AdvancedCoreInfo — 1.1.0

`AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar`
**Advanced Core Info (ACI)** é a biblioteca compartilhada da família Advanced Info. Seu papel é centralizar mecanismos reutilizados por Advanced Loot Info e Advanced Worldgen Info, incluindo **descoberta de plugins, árvores estruturadas de tooltips, transferência de dados servidor→cliente e infraestrutura genérica de apresentação/análise**.
ACI não acrescenta uma tela ou sistema jogável autônomo quando instalado sozinho; ele fornece os contratos e serviços utilizados pelos módulos informativos que dependem dele. A modlist carrega a build `1.1.0` como JAR top-level.

## Advanced Loot Info — 2.1.0

`AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`
**Advanced Loot Info (ALI)** é um plugin de informação para recipe viewers que expõe **loot tables e trades de villagers** de forma consultável. Ele trabalha com JEI, EMI ou REI e organiza entradas, condições, funções e number providers usados pelas tabelas de loot, permitindo investigar de onde um item pode vir e sob quais regras.
A API de plugins permite que outros mods adicionem tipos próprios de loot entries, condições, funções, number providers e categorias. O projeto também possui suporte direto a LootJS. ALI informa e inspeciona o sistema de loot; ele não altera tabelas, não instancia baús por jogador e não substitui Lootr ou Loot Integrations. A build instalada é `2.1.0` e usa AdvancedCoreInfo como core compartilhado.

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
