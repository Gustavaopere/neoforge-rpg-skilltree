<!-- Guia temático canônico versionado no GitHub | reconciliado contra modlist.txt 2026-09-07 -->

[← Índice do guia](README.md)

# 7. Exploração, dimensões e worldgen

## Oh The Biomes We've Gone — 2.6.0

`Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`
**Oh The Biomes We've Gone (BWG)** é um grande provider de worldgen que adiciona dezenas de **biomas, árvores, madeiras, plantas, flores, recursos e blocos ambientais** ao Overworld e a outros espaços suportados pelo projeto. A proposta não é apenas trocar paletas: muitos biomas possuem vegetação e materiais próprios que entram em construção, exploração e coleta.
A versão `2.6.0` é a release NeoForge 1.21.1 instalada. Essa linha migrou configurações de worldgen para JSON e permite controle mais fino de features vanilla. O mod depende de **Oh The Trees You'll Grow** para sua infraestrutura de árvores e possui integrações atuais no pack, incluindo Dynamic Trees BWG e conteúdo sazonal. Ele coexiste com outros providers de bioma, mas permanece um sistema de worldgen próprio.

## Tectonic — 3.0.26

`tectonic-3.0.26-neoforge-21.1.jar`
**Tectonic** remodela a **geometria do terreno** do Overworld: amplia montanhas e vales, cria canyons/buttes mais marcados em badlands, wetlands, rolling hills e transições de relevo mais longas. Seu foco é a forma do mundo, não um novo catálogo de blocos ou uma coleção fechada de biomas.
Isso o diferencia de providers como Terralith e BWG, que também definem identidade de biomas/conteúdo. A build instalada é `3.0.26` para NeoForge 1.21.1.

## Terralith — 2.6.2

`Terralith_1.21.x_v2.6.2.jar`
**Terralith** é um grande overhaul data-driven do Overworld que adiciona dezenas de **biomas e paisagens** usando majoritariamente blocos vanilla. Montanhas, vales, canyons, regiões costeiras e formações incomuns são combinados em uma composição de worldgen própria, sem exigir que cada bioma introduza uma família nova de blocos.
A versão instalada é `2.6.2`. O pack também possui `Dynamic Trees - Terralith`, que reconhece seus biomas/placements e converte árvores compatíveis para o sistema de crescimento dinâmico.

## YUNG's Cave Biomes — 3.1.1

`YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`
**YUNG's Cave Biomes** adiciona **ambientes e decoração subterrânea** às cavernas, criando regiões temáticas com blocos, vegetação e composição visual próprias. Ele atua sobre a identidade interna das cavernas sem assumir o mesmo papel do `YUNG's Better Caves`, que remodela principalmente o carving, escala e geometria subterrânea.
A build instalada `3.1.1` é para NeoForge 1.21.1 e depende de YUNG's API.

## Alex's Caves Continued — 1.0.9

`alexscaves-1.0.9-neoforge+1.21.1.jar`
**Alex's Caves Continued** é a implementação canônica escolhida para o `modid alexscaves` no pack. Ela preserva os seis destinos subterrâneos do conteúdo original — **Magnetic Caves, Primordial Caves, Toxic Caves, Abyssal Chasm, Forlorn Hollows e Candy Cavity** — com seus blocos, criaturas, itens, Cave Tablets/Cave Compendium e progressão de descoberta.
A modlist física de 07/09/2026 confirma que `alexscaves-2.0.2.jar` foi removido e que somente `alexscaves-1.0.9-neoforge+1.21.1.jar` permanece. Portanto o antigo bloqueio de discovery por `modid alexscaves` duplicado está **fisicamente resolvido**. A decisão curatorial continua **Manter** Alex's Caves Continued.

## Deeper and Darker — 1.4.1

`deeperdarker-neoforge-1.21.1-1.4.1.jar`
**Deeper and Darker** expande o Deep Dark de um único bioma ameaçador para uma linha completa de exploração. Além de novos blocos, itens, recursos e criaturas ligados à estética sculk, o mod adiciona a dimensão **Otherside**, acessada pela progressão do Deep Dark.
A Otherside possui biomas próprios, fauna, materiais e estruturas, incluindo o **Ancient Temple**, fazendo a exploração continuar para além das Ancient Cities vanilla. Assim, o mod combina extensão do subsolo existente com uma dimensão nova, em vez de ser apenas um pacote de decoração sculk. A build `1.4.1` é a release NeoForge 1.21.1 instalada.

## Better End — 21.0.34

`BetterEnd-21.0.34.jar`
**BetterEnd: New Dawn** reconstrói o End como uma dimensão ecológica e explorável, com numerosos **biomas, flora, árvores/fungos, materiais, blocos, estruturas e recursos próprios**. Em vez de ilhas quase vazias separadas por chorus plants, diferentes regiões passam a apresentar identidade ambiental e materiais específicos.
O conteúdo também amplia construção e coleta pós-Ender Dragon, fazendo o End ter uma progressão ambiental própria em vez de servir apenas como caminho para Elytra e End Cities. A build instalada é `21.0.34`, com runtime `betterend`, e depende da infraestrutura BCLib/New Dawn correspondente.

## Better Nether — 21.0.26

`BetterNether-21.0.26.jar`
**BetterNether: New Dawn** amplia o Nether com uma grande variedade de **biomas, vegetação, fungos, madeiras, minérios, materiais, estruturas e blocos de construção**. A dimensão deixa de depender somente dos poucos biomas vanilla e passa a ter regiões com recursos e ambientações próprias.
O mod atua principalmente no conteúdo ecológico/material do Nether; alterações de geometria global, como altura e relevo amplificado, pertencem ao Amplified Nether. A build `21.0.26` é o port New Dawn instalado para NeoForge 1.21.1 e utiliza a infraestrutura BCLib correspondente.

## Amplified Nether — 1.2.16

`Amplified_Nether_26.2_v1.2.16.jar`
**Amplified Nether** modifica a forma física da dimensão em vez de acrescentar um catálogo próprio de mobs ou materiais. O Nether passa a utilizar **256 blocos de altura**, relevo amplificado com grandes montanhas/vales e distribuição tridimensional de biomas, aumentando muito a escala vertical da exploração.
Por atuar na geração do terreno, ele pode coexistir com expansões de conteúdo como BetterNether: uma define a geometria e o espaço disponível, enquanto a outra fornece biomas e recursos. A build instalada usa a linha `v1.2.16`; o filename conserva o rótulo `26.2`, mas o JAR está presente e carregado na modlist NeoForge 1.21.1.

## Dynamic Trees — 1.7.2

`dynamictrees-neoforge-1.21.1-1.7.2.jar`
**Dynamic Trees** substitui o modelo vanilla de árvore como estrutura estática por organismos que **crescem continuamente**. Árvores começam pequenas, desenvolvem troncos e ramificações ao longo do tempo, competem por espaço e podem responder a condições ambientais como luz, solo, chuva e temperatura conforme a espécie/configuração.
Sementes podem se espalhar e permitir regeneração natural de florestas. O sistema de corte trata a árvore como uma estrutura conectada: derrubar sua base remove o conjunto e calcula madeira conforme tamanho/forma, em vez de exigir quebrar cada log individualmente.
A linha `1.7.2` também usa expressões data-driven para chance/densidade de worldgen. O pack possui vários treepacks/bridges que convertem espécies de outros mods para esse modelo dinâmico.

### Dynamic Trees Plus — 1.3.2

`DynamicTreesPlus-neoforge-1.21.1-1.3.2.jar`
**Dynamic Trees Plus** amplia a infraestrutura do core para vegetação que não cabe no modelo simples de árvore convencional. Ele fornece tipos e comportamentos adicionais usados por **fungos gigantes, cactos e outras plantas/estruturas arbóreas especiais**, além de recursos comuns consumidos por treepacks externos.
No pack ele funciona principalmente como extensão técnica do Dynamic Trees e como dependência de integrações que precisam representar vegetação especial — por exemplo, conteúdos de BetterEnd/BWG. A build instalada é `1.3.2` para NeoForge 1.21.1.

### Dynamic Trees - BetterEnd — 2.2.0

`dtbetterend-1.21.1-2.2.0.jar`
Bridge **BetterEnd ↔ Dynamic Trees**. Converte espécies e vegetação arbórea/fúngica relevantes do BetterEnd para o modelo de crescimento dinâmico, de modo que a flora da dimensão continue aparecendo nos biomas corretos sem voltar a árvores estáticas vanilla-style.
O addon também utiliza recursos do Dynamic Trees Plus quando precisa representar vegetação especial. A modlist física de 07/09/2026 confirma JAR e runtime `2.2.0`.

### Dynamic Trees - BetterNether — 2.2.0

`dtbetternether-1.21.1-2.2.0.jar`
Bridge **BetterNether ↔ Dynamic Trees**. Adapta árvores e vegetação compatível dos biomas BetterNether ao sistema de espécies dinâmicas, incluindo crescimento, sementes e substituição durante worldgen.
Seu papel é preservar a identidade vegetal do BetterNether enquanto o core Dynamic Trees controla como as espécies crescem e se renovam. A modlist física de 07/09/2026 confirma JAR e runtime `2.2.0`.

### Dynamic Trees - Oh The Biomes We've Gone — 1.1.0-BETA02

`dtbwg-1.1.0-BETA02.jar`
Bridge **Oh The Biomes We've Gone ↔ Dynamic Trees**. Ela registra e converte espécies de árvores do BWG para crescimento dinâmico e integra essas variantes ao worldgen dos biomas correspondentes, evitando que o overhaul de árvores descaracterize a vegetação própria do BWG.
A build `1.1.0-BETA02` depende do Dynamic Trees e utiliza Dynamic Trees Plus para tipos de vegetação que exigem recursos adicionais. O sufixo beta descreve maturidade da bridge, não incerteza de identidade.

### Dynamic Trees - Quark — runtime não declarado

`dtquark-2.6.1.jar`
Bridge **Quark ↔ Dynamic Trees**. Integra as espécies e recursos arbóreos relevantes do Quark ao sistema dinâmico para que árvores adicionadas/alteradas pelo Quark possam usar crescimento, sementes e worldgen compatíveis com o core Dynamic Trees.
O filename/publicação instalada é `2.6.1`, mas a modlist não declara uma versão runtime interna para esse JAR. O guia preserva essa ausência em vez de inferir que a string do arquivo seja necessariamente o metadata runtime.

### Dynamic Trees - Terralith — 1.3.0

`dtterralith-1.3.0.jar`
Bridge **Terralith ↔ Dynamic Trees**. Ela reconhece biomas e placements do Terralith e substitui árvores compatíveis por espécies dinâmicas, preservando a composição vegetal esperada do datapack/mod de terrain enquanto Dynamic Trees controla crescimento e regeneração.
A build `1.3.0` é a release NeoForge 1.21.1 instalada e depende dos dois sistemas-base.

### Dynamic Trees–VanillaBackport — 1.5.0h

`dtvanillabackport-1.21.1-1.6.0.jar`
Bridge **VanillaBackport ↔ Dynamic Trees** focada principalmente na **Pale Oak** trazida das versões vanilla posteriores. A árvore recebe crescimento/ramificação dinâmica, propagação compatível e integração com worldgen sem perder a relação especial com o **Creaking Heart**.
A build atual é `1.5.0h`, substituindo a linha anterior 1.5.0 e correspondendo ao JAR carregado na modlist.

### Dynamic Trees Addon Lib — 0.2.0-BETA03

`DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar`
**Dynamic Trees Addon Lib** é a biblioteca comum usada por treepacks e bridges do ecossistema Dynamic Trees. Ela centraliza **modelos, registries, helpers e utilidades de integração** que seriam duplicados em cada addon individual, permitindo que compat packs registrem espécies e recursos com uma base compartilhada.
Não adiciona uma nova progressão ou provider de árvores por si só. A build `0.2.0-BETA03` é infraestrutura do stack; o sufixo beta descreve a maturidade da API.

### Dynamic Trees: Universal Compat — 2.3 (opcional)

`NeoForge-dynamictreescompat-2.3.jar`
A auditoria de 06/09/2026 classificou **Dynamic Trees: Universal Compat** como **Opcional**. O projeto detecta árvores/registries/features de outros mods e tenta gerar compatibilidade automática, mas o pack já possui treepacks dedicados para vários providers. Não há evidência suficiente para assumir que a build 2.3 deduplica todos os casos cobertos por bridges canônicas.
Se for mantido, validar em mundo descartável: species registradas, geração em chunks novos, ausência de árvores estáticas+dinâmicas duplicadas e precedência dos treepacks dedicados. Não usar sua presença como motivo para remover bridges específicas sem evidência provider por provider.

## Streams Reflowing — 2.13.1

`StreamsReflowing-1.21.1-neoforge-2.13.1.jar`
**Streams Reflowing** altera a hidrologia do worldgen para produzir **cursos d'água contínuos e visualmente mais naturais**, em vez de depender apenas de lagos e rios definidos pelo modelo vanilla. Streams acompanham o relevo, conectam regiões e criam trajetórias que tornam água corrente uma parte mais explícita da paisagem.
O mod atua na geração física de cursos d'água e portanto é diferente de expansões que apenas adicionam biomas aquáticos ou blocos de rio. A build instalada é `2.13.1` para NeoForge 1.21.1.

## Nota operacional — `alexscaves`

A decisão curatorial e o estado físico agora estão alinhados: `alexscaves-1.0.9-neoforge+1.21.1.jar` permanece como implementação canônica e `alexscaves-2.0.2.jar` não aparece mais na modlist física de 07/09/2026. Não existe bloqueio de startup por duplicidade `alexscaves` no snapshot atual.
