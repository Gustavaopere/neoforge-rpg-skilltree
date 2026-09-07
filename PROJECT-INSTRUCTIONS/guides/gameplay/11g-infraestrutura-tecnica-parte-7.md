# 11G. Infraestrutura técnica — parte 7

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **SmartBrainLib — 1.16.11** até **Polytone — 1.21-4.1.0**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

## SmartBrainLib — 1.16.11

`SmartBrainLib-neoforge-1.21.1-1.16.11.jar`
**SmartBrainLib** é uma biblioteca de IA voltada a behavior trees, sensores, memories, tasks e utilidades de cérebro reutilizadas por mods de entidades. Ela fornece infraestrutura para implementar comportamentos mais complexos sem cada projeto recriar toda a camada de decisão de mobs.
A build instalada é `1.16.11` para NeoForge 1.21.1. O catálogo ainda não confirmou um consumidor causal específico no pack, portanto a classificação permanece **Sem decisão**: a biblioteca deve ser mantida documentada como top-level instalado, mas não promovida automaticamente a dependência nem tratada como gameplay próprio.

## Puzzles Lib — 21.1.56

`PuzzlesLib-v21.1.56-mc1.21.1-NeoForge.jar`
**Puzzles Lib** é a biblioteca compartilhada do ecossistema Fuzs. Ela fornece infraestrutura de **registro, configuração, networking e utilidades comuns** usada pelos mods consumidores, permitindo que funcionalidades recorrentes sejam implementadas uma vez na library.
No pack há consumidor causal confirmado: **Overflowing Bars 21.1.1** carrega e utiliza diretamente classes/eventos da Puzzles Lib. Por isso esta entrada representa uma dependência top-level real, não uma library órfã inferida apenas pelo nome. A build instalada é `21.1.56`, release estável NeoForge 1.21.1.

## Particle Rain — 4.0.0-beta.11

`particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`
**Particle Rain** é um overhaul client-side da apresentação do clima. Ele substitui chuva e neve vanilla por partículas com movimento e colisão mais naturais e adiciona efeitos atmosféricos configuráveis como **wind-blown rain, mist, haze e sandstorms**, com regras por bioma, bloco e condição climática.
O mod não calcula estações, temperatura corporal ou um novo sistema meteorológico: ele lê as condições existentes e muda sua apresentação visual. Quantidade de partículas, obstrução e efeitos individuais podem ser configurados. A build instalada é `4.0.0-beta.11` para NeoForge 1.21.1.

## Particular Reforged — 1.5.7

`particular-1.21.1-NeoForge-1.5.7.jar`
**Particular Reforged** é uma camada client-side de **partículas ambientais e efeitos de interação**, enriquecendo especialmente água, chuva, superfícies e movimentos no ambiente. O objetivo é tornar ações e fenômenos já existentes mais visíveis por partículas adicionais, sem alterar as regras físicas ou climáticas que os originam.
Ele não substitui shader nem um sistema de clima: apenas acrescenta apresentação visual aos eventos. No stack atual, a release `1.5.7` possui inclusive correção específica para **boats/ships Sable**, evitando spam de splashes durante navegação. A build instalada é a release estável NeoForge 1.21.1 de 16/08/2026.

## LowDragLib2 — 2.2.39.a

`ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`
**LowDragLib2 (LDLib2)** é um framework técnico avançado para mods que precisam de **UI, rendering, sincronização, persistência de dados e editores in-game** mais complexos do que os componentes vanilla fornecem diretamente. Ele oferece widgets, ferramentas de edição e infraestrutura reutilizável para consumidores que constroem interfaces e sistemas visuais próprios.
No pack, **KilaGraph** é um consumidor relevante dessa infraestrutura. LDLib2 não adiciona conteúdo visual autônomo nem substitui libraries genéricas; fornece APIs específicas que os consumidores foram escritos para usar. O runtime instalado é `2.2.39.a` para NeoForge 1.21.1.

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
MineColonies `1.1.1376-1.21.1-snapshot` instalado usa o stack Structurize atual `1.0.832`, e a build presente corresponde exatamente a esse piso. Embora trabalhe com schematics/estruturas, sua função é distinta do Schematicannon e das ferramentas de blueprint do Create: ela é o backend estrutural esperado pelo ecossistema MineColonies.

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

## Moonlight Lib — 1.21.1-3.6.1

`moonlight-1.21.1-3.6.1-neoforge.jar`
**Moonlight Lib (Selene)** é uma biblioteca client/server usada por vários mods para evitar que cada projeto reimplemente infraestrutura comum. Ela fornece utilidades para **registro dinâmico, geração dinâmica de assets, villagers e IA customizada, trades data-driven, global datapacks, map markers e animações de itens em primeira/terceira pessoa**, além de outros helpers de conteúdo e sincronização.
No pack, consumidores confirmados incluem **Supplementaries** e outros projetos do ecossistema MehVahdJukaar. A build instalada é `3.6.1` para NeoForge 1.21.1. O changelog citado anteriormente correspondia à linha `3.5.2` e não é usado como evidência da build corrente; qualquer integração sensível à versão deve auditar especificamente a API/implementação de `3.6.1`. Como biblioteca, Moonlight não cria uma progressão jogável isolada: suas funções aparecem por meio dos mods consumidores.

## Polytone — 1.21-4.1.0

`polytone-1.21-4.1.0-neoforge.jar`
**Polytone** é um framework orientado a **resource packs** para modificar profundamente apresentação visual e ambiental sem exigir que cada pack crie um mod Java próprio. Ele suporta **colormaps e lightmaps, block sounds, partículas customizadas e emitters em modelos de entidades, post shaders, custom item models, texturas variantes por bioma, cores de mapas, raridade/tooltips, creative tabs e modificações de GUI**, incluindo mover slots e adicionar elementos.
Também consegue alterar partículas existentes, controlar cores e offsets de blocos e trabalhar com texturas animadas dependentes do horário do mundo; parte do formato mantém compatibilidade com convenções do OptiFine. Na linha `4.0.2`, mudanças de GPU particles foram portadas para 1.21.1: initializers podem usar um campo de colormap amostrado no spawn e particle colormaps passam a ser amostrados na própria partícula, evitando que todas compartilhem indevidamente uma única cor. Links da tela de informações do pack também voltaram a ser clicáveis.
