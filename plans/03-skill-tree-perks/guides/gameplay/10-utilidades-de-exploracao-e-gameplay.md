<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 10. Utilidades de exploração e gameplay

## Village Spawn Point — 4.6

`villagespawnpoint-1.21.1-4.6.jar`
**Village Spawn Point** altera somente a seleção do **spawn inicial de mundos novos**: em vez de usar o ponto vanilla comum, procura uma village válida e posiciona o spawn no centro desse assentamento.
Ele não cria villages, profissões ou estruturas próprias; usa o worldgen já existente. Por isso Integrated Villages, Dynamic Village e outros mods de settlements continuam responsáveis pelo conteúdo das vilas encontradas. A build instalada é `4.6` para Minecraft 1.21.1 e funciona no lado do servidor.

## Corail Tombstone — 9.5.5

`tombstone-neoforge-1.21.1-9.5.5.jar`
**Corail Tombstone** começa como um sistema de proteção de morte: quando o jogador morre, o inventário é armazenado em um **túmulo recuperável**, evitando que os itens fiquem espalhados/despawnem no mundo. O mod possui ampla configuração para recuperação, teleporte e comportamento do grave, além de compatibilidades com outros sistemas de inventário/equipamento.
Acima desse núcleo existe uma progressão própria chamada **Knowledge of Death**. Pontos podem ser obtidos ao interagir com souls, libertá-las de receptáculos, realizar prayers em decorative graves e completar advancements; esses pontos desbloqueiam perks ligados às mecânicas do Tombstone. Souls também participam de um pequeno sistema de itens encantáveis/magia funerária.
Versões modernas incluem ainda **Forgotten Knowledge**, com scrolls, enigmas e conhecimentos desbloqueáveis durante exploração, além de compendium e preferências em GUI própria. A build `9.5.5` é a release NeoForge 1.21.1 instalada, publicada em 16/08/2026; essa linha inclui também compatibilidade com Create Aeronautics para situações de morte/respawn associadas a veículos.

## Portable Hole — 21.1.0

`PortableHole-v21.1.0-1.21.1-NeoForge.jar`
**Portable Hole** é uma ferramenta de travessia que abre **túneis temporários e direcionais** através de blocos sólidos. Ao usar o item em uma face, uma sequência de blocos é substituída temporariamente por espaço atravessável, permitindo cruzar paredes, pisos ou tetos sem minerar nem alterar permanentemente a construção.
Depois da duração configurada, os blocos retornam automaticamente. Profundidade, duração, cooldown, hardness máxima, blocos afetáveis e efeitos visuais são configuráveis; partículas e bordas de portal tornam o espaço temporário legível. A build instalada é `21.1.0` para NeoForge 1.21.1 e utiliza Puzzles Lib.

## Polymorph+ — 1.3.1+1.21.1

`polymorph_plus-neoforge-1.3.1+1.21.1.jar`
**Polymorph+** resolve conflitos quando duas ou mais receitas válidas utilizam a mesma combinação de ingredientes. Em vez de o registry escolher silenciosamente um único resultado, a interface apresenta as opções compatíveis e permite selecionar qual receita deve ser executada naquele crafting/menu.
O projeto é uma implementação substituta compatível com o ecossistema do Polymorph original e mantém suporte a addons feitos para ele, incluindo `Polymorphic Energistics` no AE2. Ele não deve coexistir com o Polymorph original como segundo resolver de receitas. A build instalada é `1.3.1+1.21.1` para NeoForge 1.21.1.

## Waystones — 21.1.41

`waystones-neoforge-1.21.1-21.1.41.jar`
**Waystones** cria uma rede persistente de **destinos de teleporte descobertos ou construídos**. Uma waystone ativada passa a integrar a lista de destinos do jogador; variantes e itens do próprio ecossistema acessam essa rede conforme as regras configuradas pelo servidor/modpack.
Custos, cooldowns, geração natural, restrições dimensionais e condições de uso podem ser configurados. A build instalada é `21.1.41` para NeoForge 1.21.1, e bridges presentes no pack expõem esses destinos em outros sistemas como JourneyMap e Sable sem criar uma segunda rede de viagem. O resultado é uma infraestrutura de transporte persistente e configurável, não apenas um comando de teleporte.

## Nature's Compass — runtime 1.21.1-3.4.0-neoforge

`NaturesCompass-1.21.1-3.4.0-neoforge.jar`
**Nature's Compass** fornece uma interface de busca para **biomas registrados**, incluindo biomas adicionados por mods quando compatíveis com o registry. O jogador escolhe um alvo e a bússola procura uma ocorrência segundo raio/amostragem configurados, retornando direção, distância e estado da busca.
A build `3.4.0` instalada também inclui a opção de procurar a **próxima ocorrência** de um bioma já localizado, configuração para consumir **níveis de XP** durante a busca, opção de **durabilidade/reparo** da bússola e melhorias na seleção de biomas, inclusive respeito à tag `c:hidden_from_locator_selection`.
O mod não altera worldgen, não cria o bioma procurado e não teleporta o jogador; apenas consulta o mundo existente/gerável. O metadata runtime instalado é literalmente `1.21.1-3.4.0-neoforge`, preservado sem abreviar para a publicação 3.4.0.

## Explorer's Compass — runtime 1.21.1-3.4.0-neoforge

`ExplorersCompass-1.21.1-3.4.0-neoforge.jar`
**Explorer's Compass** aplica a busca a **estruturas registradas**. A interface permite escolher uma estrutura vanilla ou modded disponível, procura uma ocorrência segundo limites configuráveis e transforma a bússola em indicador de direção/distância até o alvo localizado.
A build `3.4.0` instalada permite buscar a **próxima instância** de uma estrutura já localizada, pode cobrar **níveis de XP**, pode usar **durabilidade com receita de reparo** e melhora a filtragem/seleção de estruturas, inclusive ocultando entradas marcadas com `c:hidden_from_locator_selection`. Essa linha também corrige casos de busca que podiam ignorar estruturas a oeste do ponto inicial.
Ele não gera estruturas nem modifica suas chances de spawn; funciona como ferramenta de descoberta sobre o worldgen existente. A release pública é 3.4.0, enquanto o metadata runtime da instalação é `1.21.1-3.4.0-neoforge`.

## JourneyMap — runtime 1.21.1-6.0.5

`journeymap-neoforge-1.21.1-6.0.5.jar`
**JourneyMap** registra o terreno explorado e o apresenta por **minimapa, mapa em tela cheia e waypoints persistentes**. O mapa acompanha dimensões, posições e landmarks descobertos pelo jogador sem alterar o worldgen.
Waypoints podem ter nomes, ícones/cores e contexto dimensional, e a API permite que addons adicionem overlays e marcadores de sistemas externos. Claims, teleporte e outros dados continuam pertencendo aos mods que os fornecem. A build NeoForge instalada é 6.0.5, com metadata runtime canônico `1.21.1-6.0.5`.

## JourneyMap Integration — runtime 1.21.1-1.9

`jmi-neoforge-1.21.1-1.9.jar`
**JourneyMap Integration (JMI)** injeta informações de outros sistemas na interface do JourneyMap. No stack atual, integrações com **FTB Chunks** e **Waystones** permitem representar claims, destinos e marcadores relevantes no mapa.
O addon não cria claims, waystones, proteção territorial ou um mapa próprio: ele traduz dados desses sistemas para as camadas e markers da API JourneyMap. A build instalada possui runtime `1.21.1-1.9`.

## Loot Journal — 6.2.1

`loot_journal-neoforge-1.21.1-6.2.1.jar`
**Loot Journal** é uma camada client-side de acompanhamento visual de itens coletados. Quando o jogador obtém loot, o mod exibe **notificações configuráveis**, com animações, layout, temas e filtros que permitem controlar quais pickups merecem destaque e como aparecem na tela.
Ele não muda loot tables, baús ou chances de drop: apenas registra/apresenta o que foi efetivamente coletado. Por isso sua função é distinta de Lootr e Loot Integrations. A build instalada é `6.2.1` para NeoForge 1.21.1.

## Lootr — runtime 1.21.1-1.11.38.124

`lootr-neoforge-1.21.1-1.11.38.124.jar`
**Lootr** transforma containers de loot de estruturas em **instâncias individuais por jogador**. Um mesmo baú pode ser aberto por várias pessoas e cada uma recebe sua própria geração de loot, evitando que o primeiro explorador esvazie permanentemente a recompensa para todos os demais.
O sistema preserva a lógica das loot tables do container; ele muda quem já consumiu aquela instância, não quais itens a tabela pode gerar. Os containers também possuem apresentação visual própria conforme o estado. Isso o distingue de Loot Integrations, que altera a composição/distribuição das tabelas. O runtime instalado é `1.21.1-1.11.38.124`.

## Loot Integrations — 4.7

`lootintegrations-1.21.1-4.7.jar`
**Loot Integrations** é o framework responsável por **injetar conteúdo modded em loot tables de outros mods e estruturas**. Em vez de cada estrutura distribuir apenas itens do próprio projeto, regras de integração podem fazer equipamentos, materiais e recursos externos aparecerem em baús adequados ao contexto. A linha 4.7 também expõe configuração para seleção e chance relativa do conteúdo integrado.
O pack contém módulos top-level específicos para **Cataclysm** (`lootintegrations_cataclysm-1.2.jar`, runtime `1`), **Ice and Fire** (`lootintegrations_iceandfire-1.2.jar`, runtime `1.2`), **Integrated Structures** (`lootintegrations_integrated-1.5.jar`, runtime `1`), tabelas **vanilla/reutilizadas** (`lootintegrations_vanilla-1.7.jar`, runtime `1`) e estruturas **YUNG** (`lootintegrations_yungs-1.6.jar`, runtime `1`). O framework controla *o que* pode aparecer; Lootr controla *quem recebe* cada instância do container. Divergências filename/runtime dos módulos permanecem preservadas.

### Loot Integrations: Randomized Loot Compatibility — runtime 1

`lootintegrations_vanilla-1.7.jar`
Este módulo top-level do **Loot Integrations** amplia tabelas vanilla e tabelas amplamente reutilizadas por estruturas para que itens modded comparáveis possam entrar na distribuição de loot. O objetivo é aumentar a variedade sem substituir o framework principal: o core continua executando a lógica de integração, enquanto este addon fornece os alvos/regras voltados às tabelas vanilla.
Como essas tabelas são reutilizadas em muitos contextos, o módulo pode afetar uma faixa ampla de estruturas. O filename/publicação é `1.7`, mas o metadata runtime canônico do JAR declara `1`; o guia preserva as duas identidades.

## FTB Chunks — 2101.1.21

`ftb-chunks-neoforge-2101.1.21.jar`
**FTB Chunks** fornece **mapa territorial, claims, proteção e force-loading de chunks** integrados ao FTB Teams. Jogadores e equipes podem reivindicar regiões, controlar permissões de interação e manter chunks selecionados carregados conforme limites e regras configurados.
O mod possui visualização própria e API para integrações. No pack, JourneyMap Integration pode projetar claims no JourneyMap, enquanto `Create Aeronautics: FTB Chunks` adapta a lógica territorial ao stack físico; nenhum deles substitui o sistema-base. A build instalada é `2101.1.21`.

## FTB Ultimine — 2101.1.15

`ftb-ultimine-neoforge-2101.1.15.jar`
**FTB Ultimine** permite aplicar uma única ação de mineração ou colheita a **grupos de blocos conectados ou formas selecionadas**. Ao manter a tecla configurada, o jogador visualiza previamente o conjunto afetado; o sistema verifica ferramenta, alvo, quantidade máxima, durabilidade e regras definidas antes de executar a operação e concentra os drops próximos ao jogador.
Os modos cobrem veios, árvores, áreas de escavação, hoe e crops conforme configuração. A build instalada é `2101.1.15`; `Create Ultimine` é a integração separada que estende essa lógica a ações Create compatíveis.

## Quark — 4.1-482

`Quark-4.1-482.jar`
**Quark** é uma coleção modular de mecânicas **Vanilla+** distribuídas por construção, automação/redstone, inventário, mobs, encantamento e mundo. Não existe uma única árvore de progressão: cada módulo adiciona ou altera uma parte do jogo e pode ser habilitado ou desabilitado separadamente.
Entre os recursos funcionais estão **Feeding Trough** para manejo/reprodução animal, mudanças em pistons e redstone, Matrix Enchanting, conteúdo subterrâneo, melhorias de inventário e diversos blocos/interações contextuais. A build instalada é `4.1-482` para NeoForge 1.21.1.

## Supplementaries — 3.9.3

`supplementaries-1.21.1-3.9.3-neoforge.jar`
**Supplementaries** adiciona uma grande coleção de blocos e itens com estética vanilla, mas cuja identidade principal é **interação funcional**. Jars armazenam conteúdos, signposts orientam navegação, faucets transferem fluidos ou itens em contextos suportados, weather vanes respondem ao clima, spring launchers impulsionam entidades, e vários blocos participam de redstone, armazenamento, iluminação e pequenas automações.
O mod funciona como uma camada transversal de utilidades físicas e ambientação utilizável, não apenas como pacote decorativo. A build atual é `3.9.3` para NeoForge 1.21.1 e depende de Moonlight Lib.

## Amendments — 1.21-2.1.9

`amendments-1.21-2.1.9-neoforge.jar`
**Amendments** modifica e amplia **blocos vanilla já existentes**, adicionando novos comportamentos, estados e pequenas interações físicas em vez de criar uma coleção paralela de sistemas. Entre os exemplos estão mistura de poções em caldeirões, lanternas animadas, carpeted stairs, candle skulls, double cakes, lilypads melhorados e outras respostas contextuais de blocos conhecidos.
O resultado é uma camada Vanilla+ centrada em comportamento, construção e ambientação funcional, sem progressão própria. A build instalada é `1.21-2.1.9` e utiliza Moonlight Lib/Selene conforme a release.

## VanillaBackport — 1.1.7.10

`VanillaBackport-neoforge-1.21.1-1.1.7.10.jar`
**VanillaBackport** porta para Minecraft 1.21.1 **blocos, itens, mobs e mecânicas de versões vanilla posteriores** sem exigir migrar toda a instância para outra versão. O projeto procura conservar comportamento, receitas, tags e apresentação próximos ao jogo oficial, permitindo que o conteúdo backportado participe normalmente de integrações externas.
No pack, isso inclui bridges como Delightful Backport para ingredientes adicionais e Dynamic Trees–VanillaBackport para Pale Oak/Creaking. A build instalada é `1.1.7.10` para NeoForge 1.21.1.
