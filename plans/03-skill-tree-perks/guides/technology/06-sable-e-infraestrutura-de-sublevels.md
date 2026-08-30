<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 6. Sable e infraestrutura de sublevels

## Dimensional Sable — 1.0.5

`dimensional_sable-1.0.5.jar`
**Dimensional Sable** fornece a infraestrutura para **teleportar physics objects do Sable entre dimensões**. Em vez de mover apenas jogadores ou entidades vanilla, a operação transfere o objeto físico/sublevel correspondente, permitindo que contraptions compatíveis continuem existindo após a mudança dimensional.
Create Aeronautics é um dos principais consumidores desse comportamento. A versão `1.0.5` foi atualizada para a linha Sable 2.0.3+ e funciona como API/ferramenta de transporte interdimensional de objetos físicos, não como um teleporter geral de gameplay para qualquer bloco.

## Sable Beyond — 0.5.0

`sablebeyond-neoforge-1.21.1-v0.5.0.jar`
**Sable Beyond** amplia a quantidade de mecânicas vanilla/Create que continuam funcionando de forma física entre o mundo principal e sublevels. **Mechanical Arms** podem interagir entre espaços; Encased Fans podem empurrar/puxar sublevels; e Water Wheels podem gerar força de propulsão em situações compatíveis.
O addon também introduz **massa dinâmica**: containers, tanks, spouts, drains e basins podem alterar a massa do objeto físico conforme o conteúdo armazenado. Fórmulas e fatores de massa de entidades são configuráveis.
Fogo pode se propagar entre sublevel e mundo principal, lava pode causar ignição, fluidos correntes podem aplicar força à estrutura e basins podem interagir com líquidos externos. Várias dessas extensões são configuráveis e algumas ficam desabilitadas por padrão, permitindo modular quanto da simulação adicional é aplicada.

## Sable: Physics Compat — 1.3.0

`sablephysicscompat-1.3.0.jar`
**Sable: Physics Compat** fornece tags e parâmetros físicos para **blocos adicionados por outros mods**, fazendo-os participar corretamente de massa, flutuação, fricção, elasticidade, airtightness e outras propriedades usadas pelo Sable.
A versão `1.3.0` amplia cobertura para Architect's Palette e diversas famílias Macaw's, além de Storage Drawers. Também expande propriedades para blocos de mods como Supplementaries, Aether, Quark, Ice and Fire e Cataclysm, entre outros.
Sem essa camada, muitos blocos modded ainda poderiam ser montados, mas tenderiam a usar defaults genéricos ou propriedades incompletas. O addon funciona como uma base de dados física de compatibilidade, não como um novo solver.

## Sable: True Impact — 0.5.7-delta

`true_impact-0.5.7-delta.jar`
**Sable: True Impact** transforma colisões de objetos físicos e veículos Sable/Create Aeronautics em **eventos de impacto com dano e destruição**. A energia da colisão pode ser convertida em dano a entidades e estruturas, além de provocar cracking/fratura e destruição de terreno conforme a intensidade e as regras configuradas.
Isso acrescenta consequências físicas às contraptions: massa, velocidade e impacto deixam de ser apenas movimento visual e passam a interferir no mundo. A build instalada `0.5.7-delta` é uma beta NeoForge 1.21.1; o caráter experimental e destrutivo descreve a mecânica/maturidade, não incerteza sobre o JAR atual.

## Sable Create Addition Compat — 0.1.13

`sable_createaddition_compat-0.1.13.jar`
**Sable Create Addition Compat** adapta componentes de **Create Crafts & Additions** aos sublevels físicos. Fios podem conectar mundo principal↔sublevel ou sublevel↔sublevel, com renderização e transformação de endpoints ajustadas ao movimento; conexões também podem romper quando o deslocamento excede a condição suportada.
A compatibilidade inclui renderização e comportamento de block entities como **Accumulator, Electric/Servo Motor, Portable Energy Interface, Rolling Mill e Liquid Blaze Burner**, para que continuem aparecendo e operando corretamente quando a estrutura se move.
A versão `0.1.13` removeu um pin rígido de versão e atualizou suporte a Sable mais recente. A release é beta, mas o escopo funcional da bridge está definido.

## Sable: Stuff&Additions Compatibility — 1.0.3

`SableStuffAdditionsCompat v1.0.3-1.21.1.jar`
**Sable: Stuff&Additions Compatibility** adapta o conteúdo de **Create: Stuff & Additions** para funcionar dentro de Sable/Simulated/Aeronautics. A bridge corrige comportamentos de itens, componentes e block entities que originalmente assumem coordenadas e contexto do level principal.
Seu objetivo é permitir que o equipamento tecnológico de Stuff & Additions continue funcional em physics contraptions sem criar versões alternativas desses itens. A build `1.0.3` é a release NeoForge 1.21.1 instalada.

## VS / Sable Hose Connectors — 0.1.8

`VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`
**VS / Sable Hose Connectors** fornece conexões de transporte entre estruturas físicas e o mundo, com suporte tanto a Valkyrien Skies quanto a **Aeronautics/Sable**. No ramo Sable, hose connectors mantêm fluxos através da fronteira entre um sublevel móvel e a infraestrutura externa.
A versão 0.1.8 também permite rotacionar Hose Connectors e Electric Wire Connectors. A bridge resolve transferência entre veículos/ships e instalações fixas; não cria um sistema de armazenamento independente.

## Sable Dynamic Lights — 2.0.1

`sable-dynamic-lights-1.21.1-2.0.1.jar`
**Sable Dynamic Lights** é um fork/integração de iluminação dinâmica voltado a **Create contraptions e sublevels Sable**. Fontes de luz transportadas por estruturas móveis continuam iluminando o ambiente a partir de sua posição física real, em vez de ficarem visualmente presas às coordenadas originais do level principal.
Isso cobre contraptions, veículos e outros espaços móveis que normalmente não são compreendidos por sistemas de dynamic lighting convencionais. O mod atua na apresentação luminosa e não altera a iluminação permanente dos blocos nem a física da estrutura. A build `2.0.1` é a beta NeoForge 1.21.1 instalada.

## SableMassView — 1.0.0

`sablemassview-1.0.0.jar`
**SableMassView** é uma ferramenta client-side de inspeção. Com **tooltips avançados (****`F3+H`****)** ativados, ela mostra a **massa física atribuída pelo Sable a cada bloco** diretamente na tooltip do item/bloco.
O addon não recalcula nem modifica a massa; apenas expõe um valor já utilizado pelo physics engine. Isso torna visível uma propriedade que influencia peso total e comportamento de contraptions, permitindo consultar materiais sem precisar inferir seus parâmetros físicos.

## Jade Sable Compat — 1.3.0

`sablejade-1.3.0.jar`
**Jade Sable Compat** corrige o ray tracing/seleção de blocos do Jade quando o alvo pertence a um **sublevel ou construção móvel Sable**. Em vez de o overlay identificar por engano o bloco situado nas mesmas coordenadas do level principal, a integração refaz a seleção usando a transformação do sublevel.
Com isso, título, ícone e dados exibidos pelo Jade correspondem ao bloco realmente sob a mira dentro da contraption. A versão `1.3.0` é client-side e é a release NeoForge 1.21.1 atual.

## Waystones: Sable — 1.0.7

`waystonessable-1.0.7.jar`
**Waystones: Sable** faz Waystones reconhecerem corretamente **SubLevels móveis**. A bridge corrige validação de destino, cálculo de distância, sincronização cliente-servidor e transformação de coordenadas quando uma waystone está dentro de uma contraption ou quando o teleporte aponta para um sublevel.
O objetivo é preservar a semântica normal do Waystones mesmo quando o ponto de viagem não pertence ao grid estático do mundo. A build `1.0.7` é a release NeoForge 1.21.1 atual dessa integração.

## Sound Physics Aeronautics — runtime 1.3.0.2

`sound-physics-aeronautics-1.4.0.jar`
**Sound Physics Aeronautics** é um fork de Sound Physics Remastered adaptado ao espaço transformado de **Sable/Aeronautics**. Ele calcula acústica, oclusão e alcance sonoro levando em conta paredes e geometria dentro de sublevels móveis, em vez de avaliar apenas o level principal.
A integração também cobre efeitos de movimento associados a veículos, incluindo comportamento espacial/Doppler conforme suportado pelo fork, fazendo som de máquinas, passos e ambiente acompanhar a posição física da contraption.
O arquivo instalado/publicado é `1.4.0`, mas o provider carregado continua declarando runtime **`1.3.0.2`**. Não existe um segundo Sound Physics Remastered top-level na modlist atual.

## Presence Footsteps x Sable — 1.0

`pfsable-1.0.jar`
**Presence Footsteps x Sable** é um patch específico para que o Presence Footsteps consulte corretamente a superfície sob o jogador quando ele está sobre uma **contraption Sable/Aeronautics**. Dessa forma, o material real do bloco móvel produz o conjunto de passos correspondente em vez de o sistema consultar erroneamente o bloco do mundo principal.
A bridge reutiliza os sons e regras do Presence Footsteps; não adiciona uma segunda biblioteca de áudio nem novos materiais próprios. O JAR `pfsable-1.0.jar` é a release NeoForge 1.21.1 instalada.
