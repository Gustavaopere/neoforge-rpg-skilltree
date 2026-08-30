<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 4. Alimentação, culinária e agricultura

## Farmer's Delight — 1.3.3

`FarmersDelight-1.21.1-1.3.3.jar`
**Farmer's Delight** é a base culinária de boa parte do stack alimentar. Ele amplia agricultura e cozinha com culturas e ingredientes próprios, utensílios, **Cutting Board**, **Cooking Pot**, Stove, containers e uma grande quantidade de refeições compostas.
O preparo é dividido em operações: ingredientes podem ser cortados com ferramentas específicas, cozidos em potes, assados ou combinados em pratos que restauram fome/saturação e podem fornecer efeitos. Isso cria uma cadeia culinária em que a forma de processar o ingrediente importa tanto quanto a receita final.
A versão instalada `1.3.3` é a release NeoForge 1.21.1 de 17/08/2026. O mod também funciona como plataforma para vários addons do pack, incluindo Alex's Delight, Ender's Delight, My Nether's Delight e Miner's Delight.

## Farmer's Delight: Extended — runtime 1.21.1-0.2.2

`farmersdelight_extended-1.21.1-0.2.2.jar`
**Farmer's Delight: Extended** adiciona receitas e alimentos pensados para preencher a área de interseção entre **Farmer's Delight e Create** sem introduzir novas crops ou novos mobs. O conteúdo parte de ingredientes já existentes e amplia as formas de transformá-los em pratos e produtos compatíveis com as cadeias culinárias do pack.
Seu papel é principalmente recipe/content expansion: ele aproveita a infraestrutura de cozinha já existente em vez de criar um segundo sistema culinário. O arquivo instalado é `farmersdelight_extended-1.21.1-0.2.2.jar`; a versão pública é `0.2.2`, enquanto o runtime preservado na modlist é `1.21.1-0.2.2`.

## Create: Garnished — 2.1.9.2

`garnished-2.1.9.2+1.21.1-neoforged.jar`
**Create: Garnished** é um addon culinário do Create centrado principalmente em **nuts/nozes e ingredientes derivados**. Ele acrescenta matérias-primas, alimentos e receitas próprias que podem ser produzidos por processos mecânicos, transformando esse conjunto de ingredientes em uma cadeia industrializada de cozinha.
O conteúdo possui integrações condicionais quando outros mods alimentares estão presentes e permanece distinto de Create: Food, Confectionery e Ratatouille porque traz seu próprio catálogo de materiais/receitas. A build atual do pack é `2.1.9.2` para NeoForge 1.21.1.

## Delightful Backport — 1.0

`Delightful-Backport-1.0-1.21.1-neoforge.jar`
**Delightful Backport** é uma bridge pequena e específica entre VanillaBackport e Farmer's Delight. Ela faz os **ovos azuis e marrons** trazidos pelo backport serem tratados como ingredientes equivalentes nas rotas culinárias do Farmer's Delight, em vez de permanecerem itens visualmente diferentes sem integração de cozinha.
Esses ovos podem participar de preparos como fried eggs usando smoker, furnace, campfire, Stove e Skillet. Portanto o mod não adiciona uma segunda árvore culinária: ele corrige a interoperabilidade de um ingrediente backportado. A build `1.0` para NeoForge 1.21.1 é publicada como alpha.

## Create Confectionery — runtime 1.1.3.

`create-confectionery1.21.1_v1.1.3b.jar`
**Create Confectionery** transforma confeitaria em uma cadeia produtiva ligada ao Create. Cacau pode ser processado em **Crushed Cocoa, Cocoa Butter e Cocoa Powder**, que servem de base para chocolate preto, branco e ruby, caramel e outros derivados.
O catálogo inclui gingerbread, marshmallows, chocolate candies, honey candy, hot chocolate e Full Chocolate Bars, além de alimentos glaceados. Alguns produtos possuem efeitos próprios; Hot Chocolate, por exemplo, concede regeneração, enquanto diferentes chocolates trabalham com efeitos como Stimulation, Rest, Saturation ou Speed.
A build instalada é `v1.1.3b`; o metadata runtime declara literalmente `1.1.3.`. Essa diferença entre filename/publicação e runtime permanece registrada.

## Ratatouille — 1.4.0

`create_ratatouille-1.21.1-1.4.0.jar`
**Ratatouille** adiciona máquinas próprias para agricultura e processamento de alimentos dentro da linguagem do Create. O **Oven** é um multiblock para cozinhar grandes lotes; o **Thresher** processa grãos como wheat e culturas compatíveis; a **Irrigation Tower** hidrata áreas amplas de farmland; e o **Spreader** acelera crops em área e pode estimular breeding de animais próximos.
O addon também trabalha com infraestrutura agrícola como Compost Tower e integra produção de campo ao restante da fábrica. Assim, não é apenas uma coleção de receitas: ele adiciona máquinas que mudam como plantio, processamento e criação animal podem ser automatizados. A ficha do Notion usa o nome canônico curto **Ratatouille**, mantido aqui.

## Create Slice & Dice — 4.3.3

`sliceanddice-4.3.3-neoforge.jar`
**Create Slice & Dice** traduz ações do Farmer's Delight para automação Create. O **Slicer** registra receitas de Cutting Board e executa o corte usando a ferramenta instalada na máquina; knives e axes são aceitos por padrão e a tag `sliceanddice:allowed_tools` permite ampliar os utensílios válidos.
Receitas do **Cooking Pot** podem ser executadas como heated mixing, e o **Sprinkler** usa fluidos bombeados para produzir efeitos na área: água simula chuva, lava causa fogo, poções aplicam seus efeitos e Liquid Fertilizer age como bonemeal sobre cultivos. A build `4.3.3` corrige o comportamento de potion sprinklers e expõe API para providers adicionais.

## Create: Food — 2.7.1

`createfood-neoforge-1.21.1-2.7.1.jar`
**Create: Food** é uma expansão culinária ampla construída para processamento mecânico. Ela acrescenta muitos ingredientes, alimentos e produtos intermediários cujas receitas podem atravessar mixing, pressing, filling e outras operações do Create, criando linhas alimentares completas dentro da fábrica.
O addon possui integrações condicionais com outros mods de comida. Na linha `2.7.1`, esse conteúdo cross-mod passou a ser controlado por **listas de configuração**, permitindo habilitar ou desabilitar famílias de compatibilidade sem tratar todo o catálogo externo como obrigatório. A build instalada é a release NeoForge 1.21.1 `2.7.1`.

## Create: Fishery Industry — 5.1.1

`createfisheryindustry-5.1.1.jar`
**Create: Fishery Industry** transforma recursos aquáticos em uma cadeia completa de obtenção e processamento. O addon inclui **pesca automatizada, mergulho e captura de criaturas**, permitindo que a produção comece no próprio ambiente aquático em vez de depender apenas da fishing rod manual.
Os produtos obtidos podem seguir para processamento de alimentos e outros recursos e então entrar na logística do Create. Assim, sua função abrange aquisição, captura, exploração subaquática e tratamento industrial dos resultados, e não apenas uma receita automática de peixe. A build `5.1.1` é a release NeoForge 1.21.1 instalada.

## Cuisine Delight — 1.2.10

`cuisinedelight-1.2.10.jar`
**Cuisine Delight** troca a lógica de uma lista fechada de receitas por um sistema de **composição livre de pratos**. A proposta oficial é permitir que o jogador cozinhe seu próprio prato sem depender de uma receita predefinida, escolhendo e combinando ingredientes no processo culinário.
Isso faz quantidade, composição e escolha dos ingredientes participarem diretamente da comida produzida, tornando o preparo mais próximo de um sistema de cooking do que de crafting convencional. No pack, Create: Arm-made Cuisine adiciona automação específica para essa cozinha. A build `1.2.10` é a release NeoForge 1.21.1 atual.

## Create: Arm-made Cuisine — 1.0.0

`create_cuisine-1.0.0-mc1.21.1-neoforge.jar`
**Create: Arm-made Cuisine** é a bridge específica entre Create e Cuisine Delight. Ela permite que **Mechanical Arms** operem a Cuisine Skillet: inserir ingredientes, executar a etapa de mexer/preparar e retirar ou empratar o resultado por automação.
O addon não cria uma segunda cozinha nem substitui Cuisine Delight; ele expõe suas operações manuais à lógica cinética do Create. A build instalada é `1.0.0` para NeoForge 1.21.1 e depende de Create + Cuisine Delight 1.2.10.

## Create: Central Kitchen — 2.6.0

`create-central-kitchen-2.6.0.jar`
**Create: Central Kitchen** é uma camada ampla de integração entre Create, Farmer's Delight e outros mods culinários suportados. Ele converte operações de cozinha em processos compatíveis com **mixing, heating, filling e automação cinética**, permitindo montar linhas industriais para receitas que originalmente dependiam de estações culinárias manuais.
As integrações são condicionais aos mods detectados, portanto o addon funciona como uma infraestrutura de interoperabilidade culinária e não como um novo catálogo independente de alimentos. O runtime instalado é `2.6.0`.

## Butchercraft — 2.6.5

`butchercraft-2.6.5.jar`
**Butchercraft** adiciona um sistema de açougue semi-realista que acompanha o animal do abate ao preparo. Em vez de a criatura desaparecer deixando poucos drops, o mod permite aproveitar muito mais do corpo por uma sequência de **slaughter, suspensão e desmonte**.
O **Butcher Knife** é usado para abater e cortar; o **Meathook** permite pendurar carcaças, especialmente animais maiores; o **Butcher Block** processa animais menores e cortes; e o **Meat Grinder** transforma carne em mince e também funciona como sausage stuffer. O resultado é uma variedade maior de tipos de carne e subprodutos que podem seguir para cozinha.
As tabelas de butcher knife, meathook e butcher block são extensíveis por datapack/loot tables. A build `2.6.5` é a release NeoForge 1.21.1 instalada.

## Alex's Delight — 1.6

`alexsdelight-1.6.jar`
**Alex's Delight** é a integração alimentar entre **Alex's Mobs e Farmer's Delight**. Ela transforma drops e recursos das criaturas de Alex's Mobs em ingredientes e pratos utilizáveis pelo sistema culinário do Farmer's Delight, fazendo a fauna ter também valor gastronômico.
A função é conectar dois catálogos já existentes: as criaturas continuam pertencendo ao Alex's Mobs e os utensílios/mecânicas de cozinha continuam vindo do Farmer's Delight, enquanto o addon fornece os alimentos e receitas cruzadas. A versão `1.6` é a release NeoForge 1.21.1 atual.

## Ender's Delight — 1.3.1

`endersdelight-1.3.1.jar`
**Ender's Delight** leva a cozinha do Farmer's Delight para o **End**, transformando recursos da dimensão e drops de suas criaturas em ingredientes próprios. Ender Pearls e Chorus Fruits entram em novas receitas, enquanto Endermen, Shulkers e Endermites passam a fornecer materiais culinários adicionais.
A documentação cita ingredientes como **Ender Sight**, proveniente de Endermen, **Shulker Mollusk**, obtido do interior de Shulkers, e **Mite Crust**, derivado de Endermites. Esses recursos são usados em pratos temáticos, dando à exploração do End uma cadeia alimentar própria em vez de apenas mais refeições com ingredientes do Overworld.
A build `1.3.1` é a release NeoForge 1.21.1 publicada em 03/08/2026.

## My Nether's Delight — 1.10.4

`MyNethersDelight-1.21.1-1.10.4.jar`
**My Nether's Delight** é uma expansão extensa do Farmer's Delight voltada ao **Nether**. Hoglins fornecem cortes que viram sausages, chops, burgers, stews e Stuffed Hoglin; Striders entram em soups, tarts, stroganoff e Striderloaf; Ghasts participam de preparos próprios; e **Bullet Peppers** alimentam uma linha de comidas extremamente picantes.
O mod adiciona o **Blazier**, uma estação de cozinha com quatro níveis de calor que pode atuar em processos equivalentes a campfire cooking, smoking, baking e smelting. O sistema de **Pungency** faz comidas picantes reagirem à proximidade de fontes de calor: sem proteção elas podem causar dano, enquanto jogadores protegidos contra fogo podem receber regeneração.
Há também agricultura própria do Nether, com Powdery Cannon/Cane, Bullet Pepper, **Resurgent Soil/Farmland**, Fungus Colonies e materiais de construção/cozinha. A build `1.10.4` é a release NeoForge 1.21.1 instalada.

## Miner's Delight — 1.4.5

`minersdelight-1.21.1-1.4.5.jar`
**Miner's Delight** é o addon do Farmer's Delight voltado à **mineração e ao ambiente subterrâneo**. Ele adiciona ingredientes, alimentos e preparos temáticos que aproveitam recursos encontrados durante exploração de cavernas, fazendo a rotina do minerador alimentar uma linha culinária própria.
O mod segue as ferramentas e filosofia do Farmer's Delight em vez de criar um sistema de cozinha separado. A build `1.4.5` é a release NeoForge 1.21.1 atual, publicada em 29/04/2026.

## Expanded Delight — 0.1.4

`expandeddelight-0.1.4.jar`
**Expanded Delight** amplia o Farmer's Delight com **ingredientes, crops, alimentos e receitas adicionais** que utilizam a mesma infraestrutura culinária do mod-base. A função é aumentar o catálogo e as combinações disponíveis sem introduzir um sistema de cozinha concorrente.
A build instalada é `0.1.4`. O conteúdo compartilha o domínio culinário de outros addons, mas seus ingredientes e receitas permanecem próprios.

## Dungeon's Delight — 1.5.0

`neoforge-dungeonsdelight-1.21.1-1.5.0.jar`
**Dungeon's Delight** conecta culinária a exploração e combate ao permitir transformar **monstros e recursos de dungeons em pratos, equipamentos e efeitos próprios**. Em vez de apenas adicionar receitas domésticas, ele dá valor culinário a drops hostis e cria uma linha temática de "monstrous treats".
A release `1.5.0` exige Farmer's Delight 1.3+ e NeoForge 21.1.219+; o stack atual atende esses mínimos. A versão também ajusta efeitos e equipamentos ligados ao conteúdo de dungeon.
