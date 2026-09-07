[← Índice do guia](README.md)

# 17. Lacunas históricas de documentação fechadas

> Estes **21 JARs já faziam parte do recorte Gameplay de 321** em 30/08/2026, mas apareciam apenas no inventário/reconciliação, sem ficha descritiva própria. Eles não são “mods novos”; esta página corrige o déficit documental.

## Oracle Index — 1.3.1

`oracle_index-neoforge-1.3.1.jar`

**Oracle Index** é um visualizador de documentação/wiki in-game voltado a consultar documentação de mods sem sair do jogo. Ele funciona principalmente como camada client-side de descoberta e navegação por conteúdo documental, inclusive em combinação com wikis externas. É útil para jogadores e para validação manual de conteúdo, mas não altera registries, progressão ou regras do mod documentado.

**Authority/integração:** documentação/UI. O conteúdo exibido deve ser tratado como referência, não como estado mecânico.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/oracle-index) · [Modrinth — busca](https://modrinth.com/mods?q=Oracle+Index) · [GitHub — busca](https://github.com/search?q=Oracle+Index+minecraft&type=repositories)


## Just Enough Resources — 1.6.0.17

`JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`

**Just Enough Resources (JER)** amplia JEI com informação sobre **mob drops, loot de dungeons, enchantments, plant/seed drops, villager trades e worldgen**, incluindo gráficos de distribuição de minério. Ele é uma ferramenta de inspeção do conteúdo gerado por outros sistemas; não altera por si só os drops ou a geração.

**Authority/integração:** consulta/diagnóstico. Nunca usar a categoria exibida no JEI como hook causal de loot/worldgen.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer) · [Modrinth](https://modrinth.com/mod/just-enough-resources-jer) · [GitHub — busca](https://github.com/search?q=Just+Enough+Resources+minecraft&type=repositories)


## Oh The Trees You'll Grow — 5.3.2

`Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar`

**Oh The Trees You'll Grow** é uma biblioteca para criação/configuração de árvores usando templates `.nbt` de estruturas. Ela é consumida por mods de bioma e worldgen para gerar árvores customizadas sem cada projeto implementar um pipeline próprio. O próprio projeto a descreve como tree library usada por ecossistemas como Oh The Biomes We've Gone.

**Authority/integração:** biblioteca de worldgen. A espécie/bioma continua pertencendo ao provider consumidor.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/oh-the-trees-youll-grow) · [Modrinth — busca](https://modrinth.com/mods?q=Oh+The+Trees+You%27ll+Grow) · [GitHub — busca](https://github.com/search?q=Oh+The+Trees+You%27ll+Grow+minecraft&type=repositories)


## Placebo — 9.9.2

`Placebo-1.21.1-9.9.2.jar`

**Placebo** é a biblioteca compartilhada de Shadows_of_Fire. A documentação oficial é explícita: sozinho, o mod praticamente apenas se registra e existe para fornecer código comum a outros projetos. No pack, sua importância é de dependência técnica para consumidores do ecossistema Apothic/afins, não de gameplay independente.

**Authority/integração:** library only. Nunca promover a presence de Placebo a evidência de uma mecânica do consumidor.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/placebo) · [Modrinth — busca](https://modrinth.com/mods?q=Placebo) · [Código](https://github.com/Shadows-of-Fire/Placebo)


## Presence Footsteps (NeoForge) — 1.21.1-1.12.0-beta.1

`PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`

**Presence Footsteps (NeoForge)** é o port NeoForge de Presence Footsteps e substitui o feedback sonoro simples de passos por conjuntos de sons mais dinâmicos conforme o bloco/superfície percorrida. O projeto é client-side e permite customização de sons. Ele melhora leitura espacial e imersão, mas não altera atrito, velocidade ou propriedades físicas do bloco.

**Authority/integração:** áudio client-side. Nunca inferir material/estado mecânico apenas do som tocado.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=Presence+Footsteps+%28NeoForge%29) · [Modrinth](https://modrinth.com/mod/pf-neoforge) · [GitHub — busca](https://github.com/search?q=Presence+Footsteps+%28NeoForge%29+minecraft&type=repositories)


## TerraBlender — 4.1.0.8

`TerraBlender-neoforge-1.21.1-4.1.0.8.jar`

**TerraBlender** é uma API de worldgen/biomas usada para inserir e organizar regiões de biomas de forma compatível com o pipeline moderno do Minecraft. Mods consumidores usam a biblioteca para participar da composição do Overworld/Nether sem substituir toda a geração. Ela deve ser analisada quando um mod de bioma depende de seus regions, mas não é um provider de biomas por si só.

**Authority/integração:** worldgen API. Provider real é o mod que registra regiões/biomas via TerraBlender.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/terrablender) · [Modrinth — busca](https://modrinth.com/mods?q=TerraBlender) · [Código](https://github.com/Glitchfiend/TerraBlender)


## Domum Ornamentum — 1.0.236-snapshot

`domum-ornamentum-1.0.236-snapshot-main.jar`

**Domum Ornamentum** fornece blocos **skinnable/combináveis** voltados a construção e é parte importante do ecossistema LDTTeam/MineColonies. O código do projeto o descreve como base para blocos que podem assumir materiais/skins usados em construções. Isso o torna relevante para estilos e prédios de colônia, mas não para lógica de cidadãos ou jobs.

**Authority/integração:** blocos/decoração do ecossistema MineColonies. Não é provider de claim, job ou progresso de colônia.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/domum-ornamentum) · [Modrinth — busca](https://modrinth.com/mods?q=Domum+Ornamentum) · [Código](https://github.com/ldtteam/Domum-Ornamentum)


## Immersive Portals: True Immersion — 2.0.4

`immersive_portals_true_immersion-2.0.4.jar`

**Immersive Portals: True Immersion** estende Immersive Portals com **mais interações através do plano do portal**, aprofundando a ideia de portal como abertura contínua em vez de tela de teleporte. O addon depende do comportamento do portal-base; ele não substitui o engine de portal nem deve ser usado sozinho para concluir que uma travessia ocorreu.

**Authority/integração:** addon de interação. Para perks de portal/travessia, usar o evento/estado do provider de portal e distinguir interação através do portal de teleport efetivo.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/immersive-portals-true-immersion) · [Modrinth — busca](https://modrinth.com/mods?q=Immersive+Portals%3A+True+Immersion) · [GitHub — busca](https://github.com/search?q=Immersive+Portals%3A+True+Immersion+minecraft&type=repositories)


## Just Enough Breeding — 3.2.1

`justenoughbreeding-neoforge-1.21.1-3.2.1.jar`

**Just Enough Breeding (JEBr)** é um plugin para JEI/REI/EMI que mostra **informações de reprodução de entidades**. Ele ajuda a descobrir ingredientes, condições e parceiros usados por animais/mobs compatíveis, mas não executa breeding e não altera a IA reprodutiva.

**Authority/integração:** consulta/UI. O breeding real deve ser validado no evento/estado da entidade provider.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/justenoughbreeding) · [Modrinth — busca](https://modrinth.com/mods?q=Just+Enough+Breeding) · [GitHub — busca](https://github.com/search?q=Just+Enough+Breeding+minecraft&type=repositories)


## Kotlin for Forge — filename 5.12.0; metadata top-level em branco

`kotlinforforge-5.12.0-all.jar`

**Kotlin for Forge** adiciona um language loader Kotlin e utilidades opcionais para mods escritos em Kotlin. A build `5.12.0` suporta NeoForge/Forge e 1.21.1, mas a linha da modlist não expõe `modid`, runtime name ou runtime version top-level; o guia preserva essa ausência em vez de preencher por inferência.

**Authority/integração:** language loader/library. Não é provider de gameplay; a presença só viabiliza consumidores Kotlin.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/kotlin-for-forge) · [Modrinth — busca](https://modrinth.com/mods?q=Kotlin+for+Forge) · [GitHub — busca](https://github.com/search?q=Kotlin+for+Forge+minecraft&type=repositories)


## LambDynamicLights — 4.8.11+1.21.1

`lambdynamiclights-4.8.11+1.21.1.jar`

**LambDynamicLights** adiciona iluminação dinâmica client-side: itens, entidades e outras fontes podem iluminar visualmente o ambiente sem transformar cada posição em uma fonte de luz vanilla persistente. Isso melhora exploração e leitura visual, mas não deve ser confundido com light level lógico usado por spawn, crops ou outras regras de servidor.

**Authority/integração:** renderização client-side. Não usar brilho dinâmico como prova de light level de gameplay.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=LambDynamicLights) · [Modrinth — busca](https://modrinth.com/mods?q=LambDynamicLights) · [GitHub — busca](https://github.com/search?q=LambDynamicLights+minecraft&type=repositories)


## Lithostitched — 1.8.0+beta4

`lithostitched-1.8.0+beta4-neoforge-21.1.jar`

**Lithostitched** é uma library de worldgen dedicada a aumentar **configurabilidade e compatibilidade** entre providers de terreno/bioma/estrutura. Ela expõe abstrações e helpers para modificar worldgen sem cada mod duplicar mixins e datapacks complexos. A build instalada continua `1.8.0+beta4`; releases mais novas existem para outros targets, mas isso não altera a versão do pack.

**Authority/integração:** worldgen infrastructure. Não é biome/structure provider por si só.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/lithostitched) · [Modrinth — busca](https://modrinth.com/mods?q=Lithostitched) · [GitHub — busca](https://github.com/search?q=Lithostitched+minecraft&type=repositories)


## Load My F***ing Tags — 1.1.1+1.21.9

`lmft-1.1.1+1.21.9-neoforge.jar`

**Load My F***ing Tags (LMFT)** muda a tolerância de carregamento de tags para que uma entrada inválida não descarte **a tag inteira**. Isso evita que um erro de mod/datapack quebre sistemas amplos como `minecraft:mineable/pickaxe`. O JAR multi-versão `1.1.1+1.21.9` declara compatibilidade abrangente com a linha 1.21 e é usado aqui em 1.21.1.

**Authority/integração:** resiliência de datapack/tags. Ele não cria tags novas nem valida a intenção semântica de uma entrada.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/lmft) · [Modrinth](https://modrinth.com/mod/lmft) · [GitHub — busca](https://github.com/search?q=Load+My+F%2A%2A%2Aing+Tags+minecraft&type=repositories)


## Modonomicon — 1.120.4

`modonomicon-1.21.1-neoforge-1.120.4.jar`

**Modonomicon** é uma plataforma data-driven de documentação in-game inspirada em Thaumonomicon/Patchouli. Books são definidos por datapack, podem usar visualização 2D de progresso, condições de desbloqueio, multiblocks e page types extensíveis. Para o pack, isso é uma superfície importante de documentação e onboarding, mas o unlock visual do book só é authority quando o próprio sistema consumidor o define como estado de progressão.

**Authority/integração:** documentação/progress visualization. Não equiparar automaticamente entry unlock a advancement/quest/perk sem contrato explícito.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/modonomicon) · [Modrinth](https://modrinth.com/mod/modonomicon) · [Wiki/Docs](https://klikli-dev.github.io/modonomicon/) · [Código](https://github.com/klikli-dev/modonomicon)


## Prometheus — 1.2.5

`prometheus-neoforge-1.21-1.2.5.jar`

**Prometheus** é uma API/backbone para **fogo modded**. Ele fornece infraestrutura reutilizável para projetos que precisam registrar ou manipular variantes de fogo e comportamento relacionado, evitando que cada mod implemente seu próprio conjunto incompatível de hooks. O próprio projeto o classifica como library/game-mechanics backbone.

**Authority/integração:** API de fogo. O dano/estado causal deve vir do fogo/provider concreto que usa Prometheus.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/prometheus) · [Modrinth](https://modrinth.com/mod/prometheus-api/version/1.2.5) · [GitHub — busca](https://github.com/search?q=Prometheus+minecraft&type=repositories)


## Rechiseled — 1.2.5

`rechiseled-1.2.5-neoforge-mc1.21.jar`

**Rechiseled** permite transformar blocos em numerosas variantes decorativas, incluindo connected textures. O chisel é a operação principal e o resultado continua sendo conteúdo construtivo/estético; integrações como Fusion podem participar da apresentação das conexões. Para projetos próprios, é uma fonte de paleta visual e building blocks, não um sistema de progressão.

**Authority/integração:** decoração/construção. Só usar uma variante como material mecânico se tags/atributos reais do bloco sustentarem isso.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/rechiseled) · [Modrinth](https://modrinth.com/mod/rechiseled) · [GitHub — busca](https://github.com/search?q=Rechiseled+minecraft&type=repositories)


## Rhino — 2101.2.8-build.91

`rhino-2101.2.8-build.91.jar`

**Rhino** é o runtime JavaScript usado pelo ecossistema KubeJS, baseado em uma fork da engine Mozilla Rhino com suporte moderno de linguagem e sem depender do Nashorn removido do Java. Ele não executa conteúdo de pack sozinho: fornece a VM/bridge Java↔JavaScript sobre a qual KubeJS carrega scripts.

**Authority/integração:** runtime de scripting. O provider de regra continua sendo KubeJS/script e, em última instância, o sistema de gameplay consumido.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/rhino) · [Modrinth — busca](https://modrinth.com/mods?q=Rhino) · [GitHub — busca](https://github.com/search?q=Rhino+minecraft&type=repositories)


## Shine — 2.0.2+1.21.1-neoforge

`shine-2.0.2+1.21.1-neoforge.jar`

**Shine: VFX** adiciona efeitos visuais e bloom client-side para tornar partículas/entidades/cenas mais expressivas. A build `2.0.2` NeoForge 1.21.1 é beta e possui limitações específicas com determinadas versões de Sodium; a compatibilidade visual não altera os eventos de gameplay que originam o efeito.

**Authority/integração:** VFX client-side. Não usar bloom/partícula como condição causal de perk.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/shine-vfx/files/8625805) · [Modrinth — busca](https://modrinth.com/mods?q=Shine) · [GitHub — busca](https://github.com/search?q=Shine+minecraft&type=repositories)


## Strut Your Stuff — 1.3.1

`struts-1.3.1.jar`

**Strut Your Stuff** é uma library para blocos que **ocupam o espaço entre duas posições** como estruturas contínuas e interagíveis. Ela fornece colisão, rendering com Flywheel, clipping de superfície e estilos de cable/strut, incluindo opções sem colisão e droop. Consumidores podem construir cabos, suportes e elementos físicos sobre essa base.

**Authority/integração:** library geométrica/física. O significado mecânico do strut/cabo pertence ao mod consumidor.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/strut-your-stuff) · [Modrinth](https://modrinth.com/mod/strut-your-stuff) · [Wiki/Docs](https://struts.azmod.net) · [GitHub — busca](https://github.com/search?q=Strut+Your+Stuff+minecraft&type=repositories)


## UnChipped — 1.21-1.2

`unchipped-1.21-1.2.jar`

**UnChipped** fornece conteúdo decorativo/variantes de blocos com foco em ampliar a paleta de construção. No pack ele funciona como camada estética adicional; não deve ser confundido com Chipped/Rechiseled ou com um provider de materiais estruturais simplesmente por possuir variantes semelhantes.

**Authority/integração:** decoração. Para efeitos baseados em material, usar tags/propriedades reais do bloco.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=UnChipped) · [Modrinth — busca](https://modrinth.com/mods?q=UnChipped) · [GitHub — busca](https://github.com/search?q=UnChipped+minecraft&type=repositories)


## WorldWeaver — 21.0.25

`worldweaver-21.0.25.jar`

**WorldWeaver: New Dawn** é a continuação/port NeoForge da utility library do ecossistema BetterX. Seu foco é infraestrutura comum de **world generation**, e a release 21.0.25 contém correções específicas de composição de biomas/integração TerraBlender quando BetterNether está ausente. Ele sustenta providers como BetterEnd/BetterNether, mas não adiciona sozinho uma dimensão relevante de gameplay.

**Authority/integração:** worldgen library. Provider de bioma/dimensão continua sendo o consumidor BetterX correspondente.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge) · [Modrinth — busca](https://modrinth.com/mods?q=WorldWeaver) · [Código](https://github.com/Reijin2312/WorldWeaver-New-Dawn)
