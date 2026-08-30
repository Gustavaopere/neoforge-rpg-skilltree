<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 15. Bibliotecas tecnológicas

## Azimuth API — 1.4.7

`azimuth-1.4.7.jar`
**Azimuth API** é uma biblioteca de infraestrutura para addons do ecossistema Create. Ela concentra recursos estruturais, de renderização e de integração cinética usados por consumidores em vez de fazer cada addon implementar essas camadas de forma independente.
Um consumidor atual registrado no pack é **Bits 'n' Tracks**. Azimuth não adiciona uma linha de progressão ou máquinas próprias; sua presença representa uma dependência top-level compartilhada.

## KilaGraph — 21.1.0.11

`kilagraph-neoforge-1.21.1-21.1.0.11.jar`
**KilaGraph** é um toolkit de **node graphs programáveis e shader graphs** construído sobre LDLib2. Ele fornece Blueprint Graphs para lógica/data flow, RenderType Graphs para pipelines visuais, Shader Function Graphs reutilizáveis e um editor in-game baseado nas ferramentas do LDLib2.
A biblioteca inclui nodes voltados ao próprio Minecraft — items, blocks, fluids, entities, NBT, queries de mundo, math, listas, maps, strings e operações de shader. É infraestrutura para mods consumidores, não um shaderpack ou sistema visual autônomo. A build 1.21.1 está no canal beta.

## Multi-Piston — 1.2.58-1.21.1

`multipiston-1.2.58-1.21.1.jar`
**Multi-Piston** implementa um sistema de **pistão multidirecional** capaz de movimentar estruturas de formas que o piston vanilla não representa. Além da mecânica própria, ele funciona como componente técnico do ecossistema MineColonies.
Na modlist atual, MineColonies é consumidor confirmado dessa biblioteca/mod, e a versão `1.2.58-1.21.1` supera o mínimo exigido pelo snapshot instalado. Não é equivalente às contraptions Create: sua implementação e seu papel de dependência são separados.

## NukaTeam's Gun Lib — 3.2.0

`ntgl-1.21.1-3.2.0.jar`
**NukaTeam's Gun Lib (NTGL)** é um framework para mods e gun packs implementarem **armas animadas, armas melee, granadas e sistemas de tiro**. A API fornece modos de disparo, munição, dual wielding, tipos de projétil e infraestrutura visual/funcional para conteúdos consumidores.
A biblioteca não representa uma arma específica nem uma progressão própria completa: seu valor está em fornecer o runtime comum para packs e mods de armamento que a utilizam. A build 3.2.0 é a release NeoForge 1.21.1 instalada.

## Potentials — 0.7.1

`potentials-neoforge-1.21-0.7.1.jar`
**Potentials** é uma biblioteca de **capabilities cross-platform** para transferência de energia, fluidos e itens. Ela abstrai diferenças entre loaders/APIs para que mods consumidores possam consultar e movimentar esses recursos através de uma interface comum.
A linha `0.7.1` é a compatível com Minecraft 1.21/1.21.1 presente no pack e permanece beta. Não adiciona máquinas ou redes próprias; fornece a camada de interoperabilidade usada por consumidores específicos.

## Ritchie's Projectile Library — 2.1.2

`ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`
**Ritchie's Projectile Library** fornece infraestrutura para projéteis rápidos, long-range e de grande volume. Entre os recursos estão sincronização de movimento mais precisa, **chunkloading configurável para projéteis**, screen shake e **projectile bursts** capazes de representar shotgun pellets, fragmentação e shrapnel sem criar uma quantidade excessiva de entidades independentes.
É uma biblioteca para mods consumidores, especialmente conteúdos de firearms/artillery, e não uma arma isolada. A build 2.1.2 também inclui correções NeoForge ligadas ao registry de rede e efeitos de cannon shake.

## Sophisticated Core — runtime 1.4.90

`sophisticatedcore-1.21.1-1.4.90.2299.jar`
**Sophisticated Core** é a biblioteca compartilhada do ecossistema Sophisticated. Ela centraliza infraestrutura de **upgrades, inventários, storage, recipes, filtros e comportamento comum** utilizada por Sophisticated Backpacks, Sophisticated Storage e integrações correspondentes.
Não adiciona uma progressão jogável independente: seu conteúdo é consumido pelos mods-base. A build instalada `1.4.90.2299` é a release NeoForge 1.21.1 de 22/08/2026; o metadata runtime declara `1.4.90`. Entre as correções dessa build está o restocking de receitas com múltiplas alternativas de ingrediente.

## Mechanicals Lib — 1.1.6

`mechanicals-1.21.1-1.1.6.jar`
**Mechanicals Lib** é a biblioteca comum utilizada por mods do ecossistema Mechanicals. Ela concentra registries, helpers e código compartilhado para que addons consumidores não precisem embarcar implementações duplicadas.
Não adiciona uma progressão tecnológica própria. Um consumidor confirmado na modlist atual é **Create: Mechanical Spawner**, que depende dessa biblioteca para sua infraestrutura.

## Cupboard — 4.1

`cupboard-1.21.1-4.1.jar`
**Cupboard** é uma biblioteca/framework compartilhado para mods consumidores. Além de helpers e infraestrutura comum, a linha atual fornece **framework de configuração JSON, stacktraces completos em crashes, logging de erros de comandos e de carregamento síncrono de chunks**, além de proteções para falhas comuns durante carregamento de entidades, como rotações inválidas.
Ela não adiciona máquinas, itens de progressão ou gameplay próprio: os recursos visíveis pertencem aos mods que utilizam sua API. A build instalada foi atualizada para `4.1` na modlist de 28/08/2026; o guia anterior ainda registrava `4.0` e foi corrigido para o JAR canônico `cupboard-1.21.1-4.1.jar`.

## Curios API — 9.5.1+1.21.1

`curios-neoforge-9.5.1+1.21.1.jar`
**Curios API** fornece a infraestrutura de **slots de acessórios adicionais** usada por grande parte do pack. Mods podem registrar categorias como back, charm, ring, necklace e outros slots, definir regras de equipagem e consultar os itens equipados sem disputar os slots vanilla de armadura/offhand.
Isso permite que jetpacks, backtanks, goggles, charms e diversos acessórios permaneçam funcionais através de uma API comum. Curios não possui uma progressão própria significativa; é a camada de slots/equipamento consumida por vários mods e bridges.

## Cyclops Core — 1.29.3

`cyclopscore-1.21.1-neoforge-1.29.3.jar`
**Cyclops Core** é a biblioteca central do ecossistema CyclopsMC. Ela fornece APIs, configuração, network helpers, registries e outras estruturas compartilhadas por mods da família, como Integrated Dynamics/EvilCraft quando presentes.
A versão atual da modlist é `1.29.3`. O fato de outras bibliotecas gerais existirem no pack não substitui sua API específica; sua função é exclusivamente infraestrutura para consumidores Cyclops.

## Forgified Fabric API — 0.116.15+2.3.5+1.21.1

`forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
**Forgified Fabric API** porta módulos da **Fabric API para NeoForge**, fornecendo as interfaces que mods originalmente escritos contra Fabric API esperam encontrar. No pack ele trabalha especialmente em conjunto com **Sinytra Connector**, permitindo que mods Fabric e suas dependências utilizem APIs equivalentes dentro do ambiente NeoForge.
Ele não substitui o Connector: Forgified Fabric API fornece APIs; Connector realiza a camada de compatibilidade/carregamento necessária aos mods Fabric. A build atual é `0.116.15+2.3.5+1.21.1`.

> **Componentes internos não contados como mods top-level:** Advanced AE pode embarcar/usar AE2AddonLib internamente, e o ecossistema AE2WTLib expõe APIs compartilhadas para seus consumidores. Como esses nomes não correspondem a JARs top-level independentes da modlist atual, ficam registrados apenas como contexto técnico e não como entradas de mod do catálogo.
