# 11C. Infraestrutura técnica — parte 3

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **CPM OSC Compat — 1.7.2** até **ExpandAbility — 12.0.0**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

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

## Cyclops Core — 1.29.4

`cyclopscore-1.21.1-neoforge-1.29.4.jar`
**Cyclops Core** é a biblioteca comum do ecossistema CyclopsMC, usada por projetos como EvilCraft e Integrated Dynamics. Além de serviços compartilhados de configuração/networking/registro, a documentação pública expõe recursos como **declaração de custom recipes via XML**, permitindo que os mods dependentes reutilizem infraestrutura em vez de duplicá-la.
A library não cria uma linha de gameplay autônoma. A build instalada é `1.29.4` para NeoForge 1.21.1.

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

## Entity Model Features — 3.3.5

`entity_model_features-3.3.5-1.21-neoforge.jar`
**Entity Model Features (EMF)** implementa suporte ao formato **OptiFine Custom Entity Models (CEM)** fora do OptiFine. Resource packs podem substituir ou animar modelos de entidades usando arquivos e regras do ecossistema CEM, inclusive com variações condicionais.
EMF utiliza **Entity Texture Features (ETF)** para recursos como modelos randômicos, configuração e variação das texturas declaradas pelos modelos. É uma camada client-side de renderização/modelos e não altera a lógica das entidades. A build instalada é `3.3.5`.

## Entity Sound Features — 0.8.2

`entity_sound_features-0.8.2-1.21-neoforge.jar`
**Entity Sound Features (ESF)** aplica aos sons de entidades um sistema de variações baseado nas regras `.properties` usadas por ETF/OptiFine. Resource packs podem selecionar **variações sonoras condicionais por entidade/contexto**, em vez de limitar cada mob a um único conjunto estático definido apenas pelo jogo.
O mod também expõe utilidades sonoras adicionais para integração com ETF/EMF. É client-side e a build instalada é `0.8.2`.

## Entity Texture Features — 7.2.1

`entity_texture_features-7.2.1-1.21-neoforge.jar`
**Entity Texture Features (ETF)** implementa recursos de texturas de entidade associados ao ecossistema OptiFine: **texturas customizadas, emissivas e variantes condicionais/randômicas**, além de funcionalidades relacionadas a skins de jogador. Resource packs podem usar regras para selecionar aparências conforme propriedades da entidade.
ETF é também dependência funcional de EMF para parte de suas variações e configuração. A build instalada é `7.2.1` para NeoForge 1.21.1.

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

## Euphoria Patcher — 1.10.0-r5.9-neoforge

`EuphoriaPatcher-1.10.0-r5.9-neoforge.jar`
**Euphoria Patches** é um addon para **Complementary Shaders Reimagined/Unbound** que acrescenta uma grande coleção de features e settings opcionais ao shader. O Patcher integra essas extensões ao ambiente do jogo para que o pacote Complementary compatível possa carregar e configurar os patches.
É uma camada estritamente visual/client-side; sem o shader compatível, não se transforma em um sistema de iluminação física do servidor. O runtime instalado preserva `1.10.0-r5.9-neoforge`.

## ExpandAbility — 12.0.0

`expandability-12.0.0.jar`
**ExpandAbility** é uma library que expõe eventos para controlar determinadas **habilidades vanilla do jogador**. A versão instalada permite, por exemplo, habilitar natação fora de fluidos, impedir natação mesmo dentro de fluidos e controlar a capacidade de caminhar sobre fluidos.
Isso fornece um contrato reutilizável para mods que querem modificar essas capacidades sem substituir toda a implementação do jogador. A build `12.0.0` suporta NeoForge 1.21/1.21.1.
