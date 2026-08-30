<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 4. Logística e fábricas conectadas

## Create Contraption Terminals — 1.4.0

`createcontraptionterminals-1.21-1.4.0.jar`
**Create Contraption Terminals** faz os terminais do **Tom's Simple Storage** continuarem funcionais depois que a estrutura é montada como contraption Create. Um terminal colocado na construção consegue acessar o **inventário inteiro da contraption assemblada**, preservando a experiência de storage centralizado durante movimento.
Isso permite usar trens e outras estruturas móveis como depósitos acessíveis por terminal em vez de abrir cada container individualmente. Contraptions montadas antes da instalação do addon precisam ser remontadas para que os terminais sejam registrados corretamente.

## Create: Ender Transmission — 2.1.1-1.21.1

`createendertransmission-2.1.1-1.21.1.jar`
**Create: Ender Transmission** adiciona links de longa distância para **itens, fluidos e energia**, incluindo conexões entre dimensões. Os endpoints formam pares/canais que transferem recursos sem exigir uma linha física contínua de belts, pipes ou cabos atravessando o trajeto.
O addon também possui **chunk loader alimentado por potência cinética**, permitindo manter pontos de uma infraestrutura remota ativos como parte da rede Create. Assim, seu escopo combina teletransporte de recursos e manutenção operacional de instalações distantes.
A publicação usa `v2.1.1`, enquanto o runtime local registra `2.1.1-1.21.1`; o guia mantém a string completa instalada.

## Create: Mobile Packages — 0.7.7

`create_mobile_packages-1.21.1-0.7.7.jar`
**Create: Mobile Packages** cria uma rede logística de entregas baseada em **Bee Ports e Robo Bees**. Ports são vinculados a uma Logistics Network e enviam packages para outros ports ou diretamente para jogadores pertencentes à mesma rede; os Robo Bees funcionam como couriers físicos que carregam as encomendas.
O **Portable Stock Ticker** permite consultar remotamente um Stock Network, pedir itens, pesquisar com JEI e enviar itens do próprio inventário para um endereço. O **Mobile Packager** cria ou edita packages de até nove stacks fora de uma instalação fixa.
Bee Ports também podem puxar packages de inventários adjacentes ou, com redstone, descarregar para eles. O resultado é uma camada de entrega móvel sobre o sistema de packages/stock do Create, com destinatários, redes e couriers persistentes.

## Create Stock Bridge — 0.2.0

`createstockbridge-1.21.1-0.2.0.jar`
**Create Stock Bridge** liga o **Create Stock Network** ao Applied Energistics 2 por um AE Stock Bridge. Itens presentes no estoque Create são expostos ao AE2 como itens solicitáveis/craftable, permitindo que a interface ME enxergue a disponibilidade logística do lado Create.
No sentido inverso, um **Packager conectado ao bridge** pode solicitar itens armazenados na ME Network para atender pedidos do sistema Create. A versão 0.2.0 também corrige cenários de envio parcial, Create Promises, Re-Packager e prioridade controlada por redstone.
Essa integração trabalha especificamente com stock requests/packages; ela não substitui Applied Create, que conecta AE2 a stress e processamento cinético.

## Create: Transmission! — runtime 1.2.2+neoforge-create6-1.21.1

`createtransmission-1.2.2+neoforge-create6-1.21.1.jar`
**Create: Transmission!** adiciona um único componente mecânico principal: a **Transmission Chain**. Ela transmite rotação ao longo de uma linha e pode alimentar belts de forma compacta, evitando montar Encased Chain Drives apenas para levar potência a trechos logísticos.
O bloco existe como alternativa de layout: mantém a lógica cinética do Create, mas oferece outra geometria para distribuição de rotação em fábricas onde espaço e leitura visual importam. A build instalada preserva o runtime completo `1.2.2+neoforge-create6-1.21.1`.

## Create: Filters Anywhere — 2.6.0

`createfiltersanywhere-1.21.1-2.6.0.jar`
**Create: Filters Anywhere** leva **List Filters e Attribute Filters** do Create para inventários, slots de filtro e máquinas de outros mods. Em vez de cada sistema externo depender apenas de sua própria sintaxe de whitelist/blacklist, interfaces compatíveis passam a aceitar filtros Create e os atributos que eles representam.
A build do pack possui integrações específicas com **AE2, Tom's Simple Storage, Sophisticated Core, Refined Storage, Oritech, Modular Routers e LaserIO**, entre outras. O addon também adiciona atributos e QoL aos próprios filtros, tornando-os uma linguagem de seleção compartilhada entre diferentes sistemas logísticos.

## Create: Pattern Schematics — 2.0.10

`create_pattern_schematics-2.0.10.jar`
**Create: Pattern Schematics** adiciona schematics pensados para **repetição automática de um padrão**. Em vez de copiar manualmente a mesma seção várias vezes, o padrão pode ser aplicado ao longo de contraptions, trains, gantries e outros eixos de movimento para construir sequências repetitivas.
Isso transforma módulos como trechos de ponte, parede, via ou estrutura industrial em unidades replicáveis. A lógica é diferente do Schematicannon comum: o foco não é apenas imprimir uma área salva, mas definir como a unidade se repete ao longo de um percurso.

## Create: Fast Schematic Cannon — runtime 1.4.1-neoforge

`CreateFastSchematicCannon-1.4.1-neoforge-1.21.1.jar`
**Create: Fast SchematicCannon** torna a velocidade de impressão do cannon configurável. O parâmetro de **prints per tick** permite executar múltiplas colocações em cada tick de trabalho, multiplicando a taxa de construção sobre o delay normal do Create.
O addon também permite configurar uma lista de **blocos que o Schematicannon não pode imprimir/quebrar**, protegendo componentes específicos durante construção automática e evitando casos como o chamado void-boiler issue. Portanto ele atua tanto em throughput quanto em regras de segurança de impressão.
O runtime local é `1.4.1-neoforge`; a publicação externa abrevia a release para `v1.4.1`.

## Create: Schematic Checker — runtime 2.27.45-6.0

`createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar`
**Create: Schematic Checker** é uma camada server-side de **proteção e sanitização de schematics**. Ao receber um schematic enviado pelo cliente, ele examina NBT e outros dados antes da impressão, bloqueando ou corrigindo padrões conhecidos que poderiam causar crashes, lag, duplicação ou inserir conteúdo malicioso no mundo.
O mod também possui reparos específicos para dados de blocos e addons usados em schematics. A release `2.27.45` para Create 6.0 inclui ajustes recentes envolvendo Copycats e Quark, além da manutenção contínua das regras de validação.
`TorqueAPI 1.2.2` vem incorporada dentro desse JAR como dependência interna e não corresponde a uma entrada top-level separada da modlist.
