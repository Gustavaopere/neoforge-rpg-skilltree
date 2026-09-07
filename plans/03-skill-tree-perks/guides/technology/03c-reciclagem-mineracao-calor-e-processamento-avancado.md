<!-- Reconciliado com modlist.txt atual em 2026-09-07. -->

[← Capítulo principal](03-industria-energia-e-processamento-do-create.md) · [← Índice do guia](README.md)

# 3C. Reciclagem, mineração, calor e processamento avançado

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

## Create Cobblestone — 1.5.0+neoforge-1.21.1-153

`createcobblestone-1.5.0+neoforge-1.21.1-153.jar`
**Create Cobblestone** substitui geradores vanilla de água/lava por um **bloco gerador movido a Stress Units**. A produção de cobblestone passa a depender da rede cinética, podendo ser ligada, escalada e posicionada como outra máquina Create sem manter fluidos atualizando blocos continuamente.
A proposta também é reduzir problemas de desempenho e construção causados por geradores convencionais dentro de contraptions ou fábricas densas. O recurso gerado pode alimentar automaticamente crushing, washing e cadeias posteriores de materiais de construção.
O runtime é preservado integralmente como `1.5.0+neoforge-1.21.1-153`.

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

## Create Liquid Fuel — runtime 3.0.0-1.21.1

`createliquidfuel-3.0.0-1.21.1.jar`
**Create Liquid Fuel** acrescenta uma extensão estreita e concreta ao Blaze Burner: combustíveis fluidos podem ser bombeados para o burner e participar do aquecimento Create. Na linha 3.0.0, a compatibilidade de combustíveis foi movida para um **Fluid data map sincronizado**, inclusive com suporte a condições NeoForge.

Para perks, o combustível e o estado térmico continuam pertencendo ao pipeline do Create/addon. Não criar uma segunda reserva de combustível ou contabilizar novamente o mesmo consumo.

## Create: Sulfuric Resonance — 0.4.1

`sulfuricresonance-0.4.1.jar`
**Create: Sulfuric Resonance** amplia Create com engenharia termquímica: geração/transmissão de calor, química de enxofre e ácido sulfúrico, materiais avançados, processamento por combustão, Precision Processing e Resonance Processing. O sistema combina heat, rotação, reagentes e tempos de processo em layouts físicos compatíveis com os componentes normais do Create.

A build `0.4.1` é a release pública atual instalada. O mod declara requisito de Create 6.0.7+ e NeoForge 21.1.238+; o pack usa Create 6.0.10. Há incompatibilidade visual documentada entre seus Thermochemical Cogwheels e **Create: Bits 'n' Bobs**. A decisão de 06/09 era retirar Bits 'n' Bobs, mas foi **explicitamente revisada em 07/09/2026**: o pack mantém `bits_n_bobs-2.3.1.jar` e aceita conscientemente o conflito visual. Isso não é evidência de correção técnica. Monitorar e render-testar especificamente os Thermochemical Cogwheels após updates de qualquer um dos mods. A decisão vigente está em [`../gameplay/CURATION-DECISIONS-2026-09-07.md`](../gameplay/CURATION-DECISIONS-2026-09-07.md).

## Create: Fantasizing Again — runtime 1.2.0-b3

`create_fantasizing-1.21.1-1.2.0-b3.jar`
**Create: Fantasizing Again** é um addon Create de utilidades e conteúdo de alto poder. A modlist carrega a build beta `1.2.0-b3`; o projeto público identifica essa linha como beta para NeoForge 1.21.1.

Como a descrição pública dessa build não define um único recurso canônico compartilhado comparável a stress, FE ou heat, integrações de perk devem apontar para o item/máquina/evento real que pretendem alterar. O nome e a categoria do addon não são evidência suficiente para inventar eficiência genérica.

---
