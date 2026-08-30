<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 1. Combate, movimento e ação

## Epic Fight — 21.17.3.1

`epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`
**Epic Fight** transforma o combate do Minecraft em um sistema de ação inspirado em jogos Souls-like. O jogador alterna para um Battle Mode próprio, no qual ataques deixam de ser simples cliques instantâneos e passam a utilizar sequências de golpes, animações com fases de ataque, alcance real das armas, stagger, impacto e gerenciamento de stamina.
Armas diferentes podem possuir movesets próprios, combos e ataques especiais. O sistema também trabalha com skills ativas e passivas, esquiva, defesa, guardas e atributos de combate como impacto, peso, armor negation e stun armor. Isso transforma escolha de arma, timing e posicionamento em partes centrais da luta.
No pack, Epic Fight funciona como a base do combate físico e recebe conteúdo de outros mods, principalmente Weapons of Miracles e Epic ParCool.

## Epic Fight - Better Lock On — 2.0.8-neoforge

`betterlockon-2.0.8-neoforge.jar`
**Epic Fight - Better Lock On** é uma camada client-side de aprimoramento do lock-on do Epic Fight. O mod adiciona um **indicador visual explícito de alvo travado** e ajusta a experiência de seleção/acompanhamento de alvo para tornar o estado do lock-on mais legível durante combate rápido.
Ele não substitui o sistema de combate nem cria um segundo Battle Mode: trabalha sobre o targeting já fornecido pelo Epic Fight. A build instalada `2.0.8` é a release NeoForge 1.21.1 publicada em 12/05/2026.

## Lock-On Movement Fix — 1.0.2

`lockonmovementfix-neoforge-1.0.2.jar`
**Epic Fight x Better Lock On: Movement & Camera Fixes** corrige como movimento, esquiva e mira se comportam durante lock-on. Com um alvo travado, WASD passa a mover o personagem **relativamente à câmera** em primeira pessoa, terceira pessoa vanilla e câmeras compatíveis, evitando que o input seja artificialmente puxado em direção ao inimigo.
Dodge rolls também seguem a direção WASD relativa à câmera; attack lunges usam o alvo travado para evitar golpes avançando por um facing antigo; o corpo pode permanecer voltado ao alvo enquanto bloqueia ou carrega um spell; e a mira de bows, crossbows e itens é alinhada ao crosshair.
O addon ainda oferece **auto-lock ao atacar um alvo não travado** e troca de alvo por flick lateral do mouse, com suporte equivalente ao right stick do Controllable. Better Lock On é opcional para parte dessas correções; quando presente, os dois mods trabalham sobre o mesmo targeting. A build NeoForge 1.21.1 instalada é `1.0.2`.

## Weapons of Miracles — 2.0.176

`WeaponsOfMiracles-2.0.176.jar`
**Weapons of Miracles** é uma grande expansão de conteúdo construída diretamente para Epic Fight. Ela adiciona **armas próprias, animações, movesets, skills, guardas/parries e estilos de luta** cuja lógica depende do sistema de combate do mod-base, em vez de simplesmente atribuir presets vanilla a novos itens.
As armas funcionam como kits de combate distintos: ataques básicos, skills e animações podem alterar mobilidade, timing, defesa e sequências disponíveis. A própria linha 2.0 continua ajustando comportamentos específicos de armas e guardas; a versão `2.0.176`, publicada para NeoForge 1.21.1 em 20/08/2026, foi testada pelo projeto contra **Epic Fight 21.17.3.1**, exatamente a versão instalada no pack.

## ParCool! — 4.0.0.2

`ParCool-1.21.1-4.0.0.2.jar`
**ParCool** substitui a mobilidade básica do Minecraft por um sistema amplo de parkour. O jogador ganha ações como corrida rápida, vault sobre obstáculos, wall run, wall jump, agarrar e escalar paredes, cat leap, dodge, dive, crawl e outras técnicas de travessia.
O sistema utiliza stamina e pode compartilhar o sistema de stamina do Epic Fight. Com isso, movimentação fora de combate e movimentação durante lutas passam a usar a mesma linguagem de resistência física.

## Grappling Hook Mod: Skybound — runtime 1.1+1.21.1.neoforge

`grapplemod-1.1+1.21.1.neoforge.jar`
**Grappling Hook Mod: Skybound** é um fork modernizado do sistema clássico de grappling hook, mantendo a travessia baseada em **corda física**: o gancho se prende a superfícies e o movimento do jogador é determinado pela tensão/comprimento da conexão em vez de um teleporte ou dash simples.
A particularidade desta linha é tratar corretamente alvos móveis. O projeto documenta suporte direto a **contraptions do Create e airships/sublevels Sable**, além de paredes vanilla, permitindo que o ponto de ancoragem acompanhe estruturas em movimento. O JAR NeoForge `1.1+1.21.1.neoforge` é a build oficial de 31/05/2026; componentes de compatibilidade embarcados dentro dele continuam sendo internos, não mods top-level separados.

## Epic ParCool — 21.0.0

`epic x parcool-neoforge-21.0.0-1.21.1.jar`
**Epic ParCool** é a integração oficial entre Epic Fight e ParCool. Ela adapta os movimentos do jogador fornecidos pelo ParCool para coexistirem com o estado, animações e lógica do **Battle Mode** do Epic Fight, evitando que parkour e combate funcionem como dois controladores de movimento independentes.
Assim, ações de travessia podem ser encadeadas com combate mantendo o modelo de animação do Epic Fight. O projeto é mantido pelo próprio ecossistema Epic Fight e a build `21.0.0` é a release NeoForge 1.21.1 atual, publicada em 19/01/2026.

## Epic Fight Compat — 1.1.0

`epicfightcompat-1.1.0-mc1.21.1-neoforge.jar`
**Epic Fight Compat** é uma camada geral de adapters para **armas e entidades de mods externos** que podem ser traduzidos de forma limpa para presets/capabilities existentes do Epic Fight. Ele aplica patches somente quando o conteúdo correspondente está presente, evitando que cada mod precise fornecer seu próprio addon completo.
Na release `1.1.0` para NeoForge 1.21.1, a cobertura inclui itens ou entidades de **Farmer's Delight, ReArm, Supplementaries, Quark, Mowzie's Mobs, Illager Invasion, It Takes a Pillage, Darker Depths, Bosses'Rise, Hearth & Harvest e Mutant Monsters**, entre outros. Isso o torna um compat transversal; bridges especializadas continuam úteis quando precisam de renderização ou mecânicas que não cabem nesses presets genéricos.

## Ice and Fire CE x Epic Fight Armor Compat — 1.0.0

`iceandfire-ce-epicfight-armor-compat-1.0.0.jar`
**Ice and Fire CE x Epic Fight Armor Compat** é uma bridge específica de **armaduras** entre Ice And Fire Community Edition e Epic Fight. Ela adapta o equipamento do mod de dragões ao modelo animado/capabilities do Epic Fight para que as peças mantenham renderização e comportamento corretos durante poses e animações do Battle Mode.
Seu escopo é deliberadamente estreito: não adiciona novas armas, movesets ou criaturas e não substitui Epic Fight Compat, que faz adaptações gerais de entidades/armas de múltiplos mods. A build `1.0.0` é a release NeoForge 1.21.1 instalada.

## Epic Fight x Curios Compat — runtime 1.4

`Epic Fight x Curios Compat 2.2.jar`
**Epic Fight x Curios Compat** corrige e amplia a renderização de equipamentos guardados em slots **Curios** quando o modelo/animações do Epic Fight estão ativos. O projeto oferece um **Curios Slots Editor** e possui suporte declarado para vários tipos de backpack, spellbook e acessórios, incluindo Sophisticated Backpacks, Iron's Spells, Supplementaries e outros conteúdos compatíveis.
A release 2.2 também corrige entrada de texto/números no editor e um caso de **double rendering do quiver do Supplementaries**. O arquivo/publicação instalado é `2.2`, mas o metadata runtime local declara `1.4`; o guia preserva essa divergência em vez de substituir uma identidade pela outra.

## Epic Fight - Pehkui Incompatibility FIX — 1.0.2

`epicfightpehkuiincompatibilityfix-1.0.2.jar`
**Epic Fight - Pehkui FIX** é um patch client-side para um problema específico de escala entre Epic Fight e Pehkui. Quando o tamanho/dimensões do jogador mudam via Pehkui — inclusive após determinadas transições — o renderer do Epic Fight pode manter os braços na altura antiga, desalinhados dos olhos e do novo corpo.
O patch recalcula esse alinhamento para manter os braços na altura correta da visão após mudanças de scale/dimension. Ele não adiciona movesets nem novas escalas; existe apenas para fazer os dois sistemas existentes renderizarem corretamente juntos. A build `1.0.2` é a release NeoForge 1.21.1 instalada, publicada em 15/07/2026.

## Soul Fire'd — 6.1.0

`soul-fire-d-neoforge-1.21-6.1.0.jar`
**Soul Fire'd** transforma soul fire em uma família de mecânicas própria, em vez de tratá-lo apenas como uma recoloração do fogo comum. O projeto altera gameplay relacionado ao fogo de almas e adiciona conteúdo de aventura/equipamento associado, fazendo entidades e itens reconhecerem esse tipo de dano/combustão de forma específica.
Por alterar mecânicas de jogo, o mod é exigido em **cliente e servidor**. O JAR `soul-fire-d-neoforge-1.21-6.1.0.jar` é a release 6.1.0 compatível com Minecraft 1.21/1.21.1 publicada em 22/12/2025. Ele complementa combate e exploração, mas não substitui Epic Fight nem constitui um segundo overhaul geral de combate.

## Punchy — 2.7d

`punchy-2.7d-neoforge-1.21.1.jar`
**Punchy** adiciona feedback e animação em **primeira pessoa**, fazendo mãos e ações reagirem com movimento e física visual mais expressivos durante interação e combate. Seu foco é a apresentação da ação vista pelo próprio jogador, não um overhaul de regras de combate.
A build `2.7d` é a release NeoForge 1.21.1 publicada em 21/08/2026. Como Epic Fight possui sua própria linguagem de animações durante Battle Mode, o pack também contém uma bridge específica entre os dois.

## Punchy Epic Fight Compat — 1.0.0

`punchy_epicfight_neoforge.jar`
**Epic Fight X Punchy! Neo** é a bridge que impede as animações de primeira pessoa do Punchy de concorrerem com os estados de combate do Epic Fight. Quando o Battle Mode ou condições compatíveis estão ativos, a integração ajusta/desativa a renderização correspondente do Punchy.
O runtime local é `1.0.0`. É uma camada de compatibilidade entre os dois mods-base, não um sistema adicional de combate ou animação independente.

## Sable Ragdolls — 0.7.5

`sable_player_ragdoll-1.21.1-0.7.5.jar`
**Sable Ragdolls** adiciona um estado corporal físico para jogadores usando o motor Sable. O corpo é representado por peças simuladas, permitindo tombos/ragdolls por comando, por itens marcados em datapack ou por addons externos. O sistema também inclui dummies, suporte a skin/perfil, despawn configurável e API pública.
Ele funciona como base para outras camadas instaladas: Ragdoll Reactions decide quando eventos físicos provocam o estado; Sable Ragdolls Patch corrige interações; sable-x-cpm adapta modelos CPM.

## Ragdoll Reactions — 0.7.0

`ragdoll_reactions-1.21.1-0.7.0.jar`
**Ragdoll Reactions** conecta eventos físicos do mundo ao estado de ragdoll. Colisões fortes, atropelamentos, mudanças bruscas de direção e explosões podem derrubar o jogador, com sensibilidade, limite de velocidade de lançamento, cooldown de retrigger e ativação geral configuráveis no servidor.
O addon não implementa a física do corpo por conta própria: depende de Sable e Sable Player Ragdoll e apenas transforma determinados impactos/movimentos em gatilhos para o sistema.

## Sable Ragdolls Patch — 1.9

`sable_player_ragdoll_patch-1.21.1-1.9.jar`
**Sable Ragdolls Patch** corrige problemas de renderização, colisão e estado envolvendo Player Ragdoll/Ragdoll Corpse e outros componentes do pack. Entre os casos cobertos estão modelos/arms oversized com FA+Player, display de Curios, second skin/cape, swim pose, carrying/inventory e colisões de jogador/ragdoll.
A versão `1.9` também corrige a exibição dos braços de primeira pessoa do Punchy durante ragdoll, impede Ender Pearls e Wind Bombs nesse estado e corrige um caso em que `/sable remove @e` podia deixar o jogador preso fora do mundo.
