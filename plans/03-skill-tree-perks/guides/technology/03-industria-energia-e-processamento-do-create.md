<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 3. Indústria, energia e processamento do Create

## Create: Fluid — 2.1.5

`fluid-2.1.5.jar`
**Create: Fluid** amplia a logística de fluidos do Create com dispositivos de medição, distribuição, envase e controle. A **Mechanical Pipette** manipula quantidades pequenas de líquido; componentes como **Centrifugal Pump**, Copper Tap, valves e Communicating Vessel acrescentam novas formas de mover, interromper ou equilibrar fluxos.
O addon também possui **Can Filler/Cans** para transportar fluidos em recipientes, Fluid Manifest para identificação, **Fluid Atomizer** para transformar líquidos em efeitos/processos próprios e componentes de drenagem como Gutter Outlet. Há controles de redstone e válvulas múltiplas para tornar redes hidráulicas mais programáveis.
A integração com Mechanical Arms e fluid handlers permite incluir esses componentes em linhas automatizadas. Ele continua usando a lógica de fluidos do Create como base; não cria uma rede universal separada de pipes.

## Create Crafts & Additions — 1.7.0

`createaddition-1.7.0.jar`
**Create Crafts & Additions** conecta rotação Create ao ecossistema **FE**. O **Alternator** converte potência cinética em energia; Electric/Servo Motors realizam o caminho inverso; fios e connectors distribuem FE; e o **Accumulator** oferece armazenamento elétrico integrado à estética do Create.
O addon inclui ainda **Rolling Mill** para processamento, Portable Energy Interface para transferência durante contraptions, Redstone Relay, Tesla Coil e Digital Adapter. Algumas linhas produtivas próprias trabalham com biomassa e líquidos, incluindo straw usado na alimentação de Blaze Burners.
Na build `1.7.0`, o Servo Motor e ajustes de compatibilidade com Sable fazem parte da linha NeoForge 1.21.1 instalada. O resultado é uma infraestrutura onde energia elétrica e stress rotacional podem ser convertidos conforme a máquina ou sistema conectado exige.

## Create Diesel Generators — 1.21.1-1.3.15

`createdieselgenerators-1.21.1-1.3.15.jar`
**Create Diesel Generators** adiciona uma cadeia de **petróleo bruto, refino e motores a combustível** integrada ao Create. Pumpjacks e infraestrutura de extração obtêm crude oil; etapas de processamento produzem combustíveis utilizáveis por engines e burners, transformando líquidos industriais em potência cinética.
O conteúdo também inclui diferentes motores/upgrades, Bulk Fermenters, armazenamento e transporte em oil barrels/canisters, além de materiais/processos como compression molding, casting, asphalt e cement. A linha 1.3.x também possui dispositivos industriais adicionais, como sprayers/turrets que trabalham com fluidos compatíveis.
A build instalada é exatamente `createdieselgenerators-1.21.1-1.3.15.jar`, release NeoForge publicada para 1.21.1. A string completa `1.21.1-1.3.15` continua sendo o runtime registrado no catálogo.

## Destroy — 0.4.1

`destroy-1.21.1-0.4.1.jar`
**Destroy** leva Create para **química industrial e engenharia química**. O sistema trabalha com misturas e reações, vats, destilação e separação de substâncias, fazendo composição química e condições de processo participarem diretamente da automação em vez de cada fluido ser apenas uma receita estática.
O port 1.21.1 também inclui infraestrutura como pumpjack/seismograph para depósitos de óleo, ageing barrel, centrifuge, cooler, distillation tower, dynamo/arc furnace, extrusion, glassblowing, sieve, coleta de latex e etapas de circuit lithography. A linha "Chemistry and Carnage" acrescenta explosivos, fogos, álcool e outros produtos derivados dessas cadeias.
Poluição e riscos químicos fazem parte do domínio do mod. Portanto combustíveis e petróleo são apenas uma parte do sistema; o núcleo é a transformação de substâncias por operações químicas automatizáveis.

### Petrolpark's Library — 1.5.6

`petrolpark-1.21.1-1.5.6.jar`
**Petrolpark's Library** é o framework compartilhado usado por Destroy e projetos relacionados. Centraliza infraestrutura técnica para receitas, registries, componentes e sistemas reutilizados pelo ecossistema, sem representar uma progressão tecnológica independente.
A build `1.5.6` é particularmente relevante ao ambiente atual porque inclui correções de **multipart blocks em sublevels Sable**, permitindo que estruturas dos mods consumidores se comportem corretamente quando usadas em espaços físicos móveis.

## Create: Vintage Improvements — SSW Edition — 0.0.0.7

`vintageimprovements-1.21.1-0.0.0.7.jar`
**Vintage Improvements — SSW Edition** é o port NeoForge 1.21.1/Create 6 do Vintage Improvements. Ele adiciona máquinas cinéticas e recipe types voltados à fabricação industrial de **wires, rods, sheets, springs** e outras formas intermediárias de materiais.
Entre os equipamentos estão **Compressor**, com tanque interno e diferentes modos de processo; **Vibrating Table**, inclusive com recipes de unpacking; **Centrifuge**, que trabalha com Basins; **Curving Press** com heads específicos; além de outros processos mecânicos herdados do projeto original. A edição SSW também inclui correções de overflow, resultados incorretos em servidor, laser e curving press na migração para Create 6.

## Create: Blaze Burner Fuels — 1.0.2

`create_blaze_burner_fuels-1.0.2-neoforge-1.21.1.jar`
**Create: Blaze Burner Fuels** amplia a economia térmica do Create com novas fontes para **Blaze Burners**, inclusive alternativas renováveis ao uso contínuo de Blaze Cakes. O addon adiciona itens e receitas próprias de combustível, como pellets/briquetes e outras rotas de materiais processados.
Esses combustíveis servem para estados de heating/superheating usados por mixers, basins e Steam Boiler setups. O mod, portanto, altera a forma de abastecer calor industrial sem adicionar um motor cinético ou uma rede energética paralela.

## Create Metallurgy — 1.0.3

`createmetallurgy-1.0.3-1.21.1.jar`
**Create Metallurgy** acrescenta fundição e ligas ao fluxo do Create. O **Industrial Crucible** derrete materiais em fluidos metálicos; faucets e componentes associados transferem o metal líquido para etapas de casting, permitindo produzir formas metálicas através de uma linha física de fusão e vazamento.
O sistema também aceita combinações de metais para ligas e integra crushed ores e materiais especiais às receitas de fusão. Na linha 1.0.3, correções do Industrial Crucible/Faucet e receita de crushed tungsten fazem parte da build NeoForge 1.21.1 instalada.

## Productive Metalworks — runtime 1.21.1-1.15.1

`productivemetalworks-1.21.1-1.15.1.jar`
**Productive Metalworks** é um sistema de **foundry multiblock** voltado a smelting e casting. Minérios e itens metálicos podem ser derretidos em metais líquidos, armazenados/processados dentro da fundição e depois vazados em moldes para gerar formas e produtos metálicos.
A estrutura multiblock concentra capacidade térmica, armazenamento de fluidos e operações de casting em uma instalação industrial própria. Por isso sua lógica é distinta de receitas de basin do Create: o objeto central é a foundry e o ciclo metal líquido → molde → item.

## Create: Metalwork — 2.0.0

`createmetalwork-2.0.0.jar`
**Create: Metalwork** adiciona novos **crushed ores e fluidos metálicos** para aumentar o rendimento obtido de minérios usando processamento Create. A proposta é inserir etapas adicionais entre minério bruto e ingot, aproveitando moagem, fluidos e receitas mecânicas para extrair mais metal de cada unidade coletada.
É uma expansão de ore processing, não um multiblock de foundry. A build `2.0.0` é a release NeoForge 1.21.1 instalada e inclui correções específicas de compatibilidade da linha atual.

## Create: More Automation — 0.5.2

`create_more_automation-0.5.2-neoforge-1.21.1.jar`
**Create: More Automation** amplia o número de recursos vanilla e Create que podem ser produzidos por linhas mecânicas, acrescentando receitas pensadas para deployers, basins, crushing, mixing e outros processos já existentes no Create.
A build NeoForge 1.21.1 é uma reestruturação significativa em relação à antiga linha 1.20.1: o próprio projeto informa que várias receitas foram alteradas e continuam em desenvolvimento. A versão `0.5.2`, por exemplo, reajustou receitas de Ice e Moss. Seu conteúdo é majoritariamente **recipe-driven**: a expansão vem de novas rotas de fabricação, não de uma segunda família de máquinas.

## Create: Ultimate Factory — 2.2.4

`create_ultimate_factory-2.2.4-neoforge-1.21.1.jar`
**Create: Ultimate Factory** adiciona um conjunto curado de aproximadamente **30 receitas de automação** para recursos que normalmente exigiriam coleta, exploração ou etapas manuais. As receitas utilizam máquinas e sequências do Create para transformar esses recursos em produtos de fábrica de forma repetível.
A proposta é manter essas rotas relativamente balanceadas em custo e complexidade, fazendo a expansão ocorrer pelo desenho de linhas produtivas. Assim como More Automation, seu foco não é uma nova infraestrutura energética ou logística, mas aumentar o catálogo de coisas que uma fábrica Create consegue produzir autonomamente.

## Create: Dreams n' Desires — 2.3a-BETA

`DnDesires-1.21.1-2.3a-BETA.jar`
**Create: Dreams n' Desires** é uma expansão ampla e deliberadamente heterogênea do Create. O projeto adiciona **blocos, mecanismos, ferramentas/equipamentos, worldgen, decoração, utilidades e novas possibilidades de automação**, em vez de se concentrar em uma única cadeia como petróleo ou metalurgia.
Seu catálogo funciona como uma coleção de extensões que procuram manter a linguagem visual e funcional do Create: novos componentes podem participar de contraptions e fábricas, enquanto itens e blocos adicionais ampliam construção e exploração. Por essa natureza, o escopo é melhor entendido como uma expansão geral do ecossistema.
A publicação NeoForge 1.21.1 é classificada como Release no CurseForge, mas o próprio identificador do arquivo continua **`2.3a-BETA`**; o guia preserva essa string sem interpretar a etiqueta externa como uma mudança de versão.

## Create: Factory — 0.7b-1.21.1

`create_factory-0.7b-1.21.1.jar`
**Create: Factory** é uma expansão de **alimentos e produção culinária industrializada**. Ela adiciona ingredientes, comidas e receitas desenhadas para serem processadas com máquinas Create, transformando preparo de doces e outros alimentos em sequências de mixing, filling e demais operações mecânicas.
O foco é conteúdo consumível e suas cadeias de fabricação, não novas fontes de energia. A versão atual `0.7b-1.21.1` é release NeoForge 1.21.1 e inclui correções específicas da linha atual, como o comportamento de potions armazenadas em jars.

## Create: Integrated Farming — 1.3.3b

`create-integrated-farming-1.3.3b.jar`
**Create: Integrated Farming** acrescenta máquinas e blocos voltados à **produção agropecuária automatizada** dentro do Create. A proposta é tratar criação animal, pesca e manejo rural como processos industriais integráveis a belts, spouts e logística mecânica, não apenas automatizar receitas finais de comida.
Entre os sistemas estão **Chicken Roosts**, componentes de alimentação/processamento e mecanismos que aceitam fluidos agrícolas por tags, permitindo consumir óleos vegetais produzidos por outras cadeias industriais compatíveis. A linha atual também possui Fishing Nets e interação com mecanismos montados em sublevels Sable.
A build `1.3.3b` corrige especificamente a coleta de saída de Fishing Nets por **Simulated Auger Shafts** quando a entrada está em painéis coplanares conectados dentro de sublevels Sable. Quando Nether Depths Upgrade estiver presente, esta linha exige versão 3.2 ou posterior desse mod.

## Ratatouille — 1.4.0

`create_ratatouille-1.21.1-1.4.0.jar`
**Ratatouille** expande Create com máquinas próprias de **agricultura e processamento de alimentos**. O **Oven** é um multiblock voltado a cozinhar grandes lotes com aquecimento uniforme; o **Thresher** processa grãos como wheat e pode trabalhar com culturas compatíveis como rice e corn.
No campo, a **Irrigation Tower** mantém áreas amplas de farmland hidratadas, enquanto o **Spreader** acelera o amadurecimento de culturas em área e também pode induzir breeding em animais próximos. O projeto ainda apresenta infraestrutura como Compost Tower associada ao ciclo agrícola.
A ficha individual no Notion chama o mod simplesmente de **Ratatouille**; o guia passa a usar esse mesmo nome, preservando o JAR `create_ratatouille-1.21.1-1.4.0.jar`.

## Create: Food — 2.7.1

`createfood-neoforge-1.21.1-2.7.1.jar`
**Create: Food** é uma expansão culinária de grande porte baseada em Create, com **mais de mil itens relacionados a comida e mais de cem fluidos** no catálogo atual. Ingredientes, alimentos, bebidas e etapas intermediárias são estruturados para participar de processos mecânicos e cadeias automatizadas.
A versão 2.7.1 tornou o conteúdo de compatibilidade cross-mod amplamente **config-driven**: blocos, display blocks, fluidos, itens, efeitos e tooltips podem ser controlados por listas de configuração. Farmer's Delight e outros addons culinários ampliam o conteúdo disponível, enquanto Create permanece a base tecnológica obrigatória.

## Create: Garnished — 2.1.9.2

`garnished-2.1.9.2+1.21.1-neoforged.jar`
**Create: Garnished** adiciona uma cadeia culinária própria centrada principalmente em **nuts/nozes**, novos ingredientes e alimentos processáveis com o maquinário Create. As matérias-primas entram em receitas mecânicas e podem compor linhas automatizadas de produção alimentar.
O addon também detecta integrações com outros mods quando presentes. A build `2.1.9.2` é a release NeoForge 1.21.1 instalada.

## Create: Central Kitchen — 2.6.0

`create-central-kitchen-2.6.0.jar`
**Create: Central Kitchen** é uma camada de **automação culinária e compatibilidade** para Farmer's Delight e seus addons. Ele adapta ingredientes, recipientes e processos de cozinha para que possam ser executados ou movimentados por máquinas do Create, conectando cooking recipes ao mesmo fluxo de mixing, filling e logística usado por outras linhas de fábrica.
O addon é extensível e inclui integrações condicionais com outros mods culinários detectados. Por isso atua como uma central de compatibilidade: cada integração traduz conteúdo externo para operações Create, em vez de adicionar uma culinária independente.
A versão `2.6.0` foi publicada especificamente para **Create 1.21.1-6.0.10**, que é exatamente a versão do Create presente no pack.

## Create: Arm-made Cuisine — 1.0.0

`create_cuisine-1.0.0-mc1.21.1-neoforge.jar`
**Create: Arm-made Cuisine** integra o Mechanical Arm à **Cuisine Skillet** do Cuisine Delight. Um braço pode colocar alimentos ou pacotes contendo ingredientes na skillet aquecida, fazendo a etapa de abastecimento da receita funcionar como parte de uma linha Create.
Quando segura uma **spatula**, o Mechanical Arm executa continuamente o stir-fry enquanto houver alimento na skillet. Com um **plate**, ele consegue retirar a preparação pronta e entregá-la ao próximo ponto configurado, automatizando também o empratamento/saída.
O addon inclui um Ponder específico para explicar a interação com a Cuisine Skillet. Assim, sua função é bastante delimitada: automatiza ações que normalmente exigiriam interação manual do jogador dentro do sistema do Cuisine Delight.

## Create: Fishery Industry — 5.1.1

`createfisheryindustry-5.1.1.jar`
**Create: Fishery Industry** transforma atividades aquáticas em um conjunto de **pesca automática, mergulho, captura de criaturas, processamento de alimentos e logística** no estilo Create. Equipamentos próprios permitem obter recursos da água sem depender exclusivamente da fishing rod manual e encaminhá-los para processamento mecânico.
O escopo inclui exploração subaquática e captura, além da transformação e transporte dos produtos obtidos. Dessa forma, a cadeia começa na aquisição automatizada de recursos aquáticos e termina em armazenamento/processamento dentro da fábrica Create.
A build `5.1.1` é a release NeoForge 1.21.1 atual da linha 5.x instalada.

## Create Aquatic Ambitions — 2.0.4

`create_aquatic_ambitions-1.21.1-2.0.4.jar`
**Create Aquatic Ambitions** adiciona **bulk processing** voltado a recursos aquáticos e costeiros. A proposta é fazer prismarine, coral, cobre e materiais relacionados entrarem em cadeias mecânicas reproduzíveis, reduzindo dependência de coleta manual ou farms específicas para cada recurso.
As receitas utilizam a infraestrutura de processamento do Create em escala, convertendo materiais e condições aquáticas em rotas de produção contínua. O addon também inclui itens/equipamentos associados ao tema oceânico, mas sua função central é ampliar o conjunto de recursos renováveis ou processáveis industrialmente.

## Create: Deep Dark — 3.0.2

`create_deep_dark-3.0.2-neoforge-1.21.1.jar`
**Create: Deep Dark** adiciona uma camada de **endgame baseada no Deep Dark**. A exploração de Ancient Cities e território do Warden fornece recursos raros que entram em receitas e equipamentos processados pelas mecânicas do Create.
O addon acrescenta materiais, itens e gear de alto nível cuja fabricação utiliza máquinas/etapas Create, ligando exploração perigosa a uma progressão tecnológica posterior. Assim, o Deep Dark deixa de ser apenas uma fonte de loot vanilla e passa a alimentar novas cadeias de crafting industrial.

## Create: Recycle Everything Continued — runtime 1.1

`create_recycle_everything-2.1.0.jar`
**Create Recycle Everything (Continued)** cria uma extensa tabela de **receitas de desmontagem/reciclagem**, principalmente usando Crushing Wheels, para devolver matérias-primas a partir de itens já fabricados. A versão 2.1.0 acrescenta reciclagem de várias peças de armadura e amplia cobertura de itens vanilla, Create e integrações condicionais.
O fluxo é deliberadamente parcial: um item processado retorna componentes ou recursos aproveitáveis, permitindo reinserir gear e blocos obsoletos na fábrica em vez de simplesmente descartá-los. Algumas receitas são habilitadas apenas quando mods integrados estão presentes.
A identidade continua dupla: o filename/publicação é `create_recycle_everything-2.1.0.jar`, mas o metadata runtime local declara `1.1`; o guia preserva ambos.

## Create: Rock & Stone — 1.3.1-1.21.1-6

`create_rns-1.3.1-1.21.1-6.jar`
**Create: Rock & Stone** adiciona **depósitos subterrâneos próprios** que representam reservas minerais exploráveis por maquinário Create. O **Deposit Scanner** localiza essas reservas; **Miner Bearing** e **Mine Head** formam a infraestrutura usada para extrair recursos continuamente do depósito.
Os depósitos podem ser finitos ou configurados para comportamento diferente conforme o pack, e sua composição pode incluir materiais vanilla ou de outros mods. A definição é data-driven, com suporte a datapacks/KubeJS, permitindo criar novos tipos de depósito sem adicionar outro sistema de mineração.
Integrações com Jade e JourneyMap ajudam a identificar e acompanhar as reservas detectadas, tornando prospecção e extração partes distintas da mesma cadeia.

## Create Cobblestone — 1.4.12+neoforge-1.21.1-144

`createcobblestone-1.4.12+neoforge-1.21.1-144.jar`
**Create Cobblestone** substitui geradores vanilla de água/lava por um **bloco gerador movido a Stress Units**. A produção de cobblestone passa a depender da rede cinética, podendo ser ligada, escalada e posicionada como outra máquina Create sem manter fluidos atualizando blocos continuamente.
A proposta também é reduzir problemas de desempenho e construção causados por geradores convencionais dentro de contraptions ou fábricas densas. O recurso gerado pode alimentar automaticamente crushing, washing e cadeias posteriores de materiais de construção.
O runtime é preservado integralmente como `1.4.12+neoforge-1.21.1-144`.

## Create: Mechanical Spawner — runtime 1.3.1-6.0.10

`create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`
**Create: Mechanical Spawner** adiciona um spawner acionado por **potência rotacional**. Em vez de depender de um cage vanilla encontrado no mundo, o bloco utiliza stress/speed e recursos configurados para produzir mobs dentro de uma linha Create.
O sistema suporta **Random Spawn Fluid**, cuja seleção pode depender do bioma, e fluidos específicos associados a criaturas. Configurações controlam consumo de stress, velocidade, alcance e condições de spawn, permitindo que custo e produtividade façam parte da engenharia da fábrica.
A publicação/filename da build é `1.3.2-6.0.10`, enquanto o metadata local declara `1.3.1-6.0.10`; essa divergência permanece registrada sem alterar o JAR.

## Create Confectionery — runtime 1.1.3.

`create-confectionery1.21.1_v1.1.3b.jar`
**Create Confectionery** transforma confeitaria em uma cadeia produtiva ligada ao Create. A matéria-prima de cacau passa por etapas próprias para gerar **Crushed Cocoa, Cocoa Butter e Cocoa Powder**, que alimentam variedades como chocolate branco, preto e ruby, além de caramel e produtos derivados.
O catálogo inclui gingerbread, marshmallows, chocolate candies, honey candy, hot chocolate e Full Chocolate Bars, além de versões glaceadas de alimentos. Alguns produtos possuem efeitos próprios: a documentação cita **Stimulation**, Rest, Saturation e Speed, enquanto Hot Chocolate concede regeneração. A linha 1.21.1 também inclui Candy Cane Tools e receitas reversíveis para converter determinados blocos/barras de chocolate novamente em formas utilizáveis.
A build instalada é `create-confectionery1.21.1_v1.1.3b.jar`; o metadata runtime local declara literalmente `1.1.3.`. A release `v1.1.3b` é a atual para NeoForge 1.21.1 e inclui hotfix da receita de chocolate-glazed marshmallow.

## Create Slice & Dice — 4.3.3

`sliceanddice-4.3.3-neoforge.jar`
**Create Slice & Dice** integra principalmente Farmer's Delight ao Create por automação de preparação e cozinha. O **Slicer** registra automaticamente receitas de Cutting Board e executa o corte usando a ferramenta instalada na própria máquina; knives e axes são permitidos por padrão, e a tag `sliceanddice:allowed_tools` permite ampliar esse conjunto.
Receitas do **Cooking Pot** do Farmer's Delight são traduzidas para **heated mixing**, permitindo executar preparação culinária em linhas mecânicas. O addon também possui **Sprinkler**, alimentado por fluidos via pipes: água simula chuva na área, lava causa dano de fogo, poções aplicam seus efeitos e Liquid Fertilizer produz efeito de bonemeal sobre blocos/culturas.
A build `4.3.3` é a release NeoForge 1.21.1 instalada; ela corrige o comportamento de potion sprinkler e expõe `SprinklerProvider` para integrações.
