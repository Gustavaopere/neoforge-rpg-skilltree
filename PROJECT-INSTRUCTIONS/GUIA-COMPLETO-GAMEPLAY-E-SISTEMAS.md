# GUIA COMPLETO — Gameplay e Sistemas | NeoForge 1.21.1

Combate, movimento, progressão, sobrevivência, fauna, exploração, interface e infraestrutura geral do pack.

**Fonte canônica:** diretório versionado no GitHub.

**Snapshot consolidado deste anexo:** **2026-09-07**, modlist física com **612 entradas top-level incluindo NeoForge**. Para presença/JAR/runtime, `CURRENT-MODLIST.md` + delta 07/09 prevalecem sobre qualquer capítulo histórico.

> Este anexo preserva os capítulos históricos já consolidados e acrescenta uma camada de freshness 07/09. Quando uma versão antiga aparecer dentro de um capítulo descritivo, use a reconciliação atual como authority de identidade instalada.

## Índice operacional

- Reconciliação atual da modlist (`CURRENT-MODLIST.md`)
- 00. Visão geral e escopo
- 01. Combate, movimento e ação
- 02. Progressão RPG, identidades e atributos
- 03. Sobrevivência e condições ambientais
- 04. Alimentação, culinária e agricultura
- 05. Ecossistema [Let's Do]
- 06. Fauna, inimigos e bosses
- 07. Exploração, dimensões e worldgen
- 08. Estruturas e dungeons
- 09. MineColonies e civilização
- 10. Utilidades de exploração e gameplay
- 11. Infraestrutura técnica, interface, visual e performance
- 12. Navegação entre os quatro guias
- 13. Projetos próprios do modpack
- 14. Mobstein
- 15. Simply Swords e ecossistema
- 16. Novos módulos incorporados no snapshot 607
- 17. Lacunas históricas de documentação fechadas
- 18. Fontes e referências técnicas
- 19. Atualização da modlist — 2026-09-07

## Regras de manutenção

- modlist física atual = authority de presença/JAR/runtime;
- bibliotecas/UI/visual/compat não viram provider automaticamente;
- updates de versão não revalidam hooks por si;
- projetos próprios exigem delta provider→árvore e perk→provider;
- sem hook seguro, fail-closed;
- GitHub continua fonte canônica editorial dos guias.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/CURRENT-MODLIST.md -->

# Reconciliação atual da modlist — Gameplay e Sistemas

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-07.** O estado físico atual é definido pelo snapshot auditável de 06/09/2026 mais o delta físico de 07/09. O delta prevalece para todo artefato alterado. Dependências `jarjar` embutidas não contam como mods top-level.

A `modlist.txt` atual contém **612 entradas top-level**, incluindo o NeoForge modloader: NeoForge + **611 JARs**.

## Fechamento da reconciliação

- baseline 06/09: **607** entradas top-level;
- **21** updates do mesmo mod;
- **9** adições reais;
- **4** remoções físicas reais;
- saldo: **+5**;
- estado atual: **612** entradas top-level;
- Notion reconciliado: **612 `Instalado`**, **612 JARs físicos distintos** e **613/613 registros `Verificado`**;
- auditoria de versão runtime fechada: **605/605 JARs que declaram `modVersion` batem exatamente com `Versão 1.21.1` no Notion**;
- **7/7 JARs sem `modVersion` físico** permanecem com `Versão 1.21.1` vazia no Notion.

## Mudanças de Gameplay/Sistemas em 07/09

Entraram no recorte: Bosses of Mass Destruction 1.3.3, Bosses'Rise 2.1.2, CERBON's API 1.3.0, Integrated Mowzie's Mobs 1.1.0, Simple Inventory Sorter 24.0.24, [Let's Do] Furniture 1.1.4 e MineColonies: Jade crops 1.1.1300.

## Remoções e bloqueios resolvidos

Foram removidos fisicamente `alexscaves-2.0.2.jar`, `alexsmobs-1.22.9.jar`, `spore_1.21.1_2.2.0j_neo.jar` e `Infnexus-2.0.4-1.21.1.jar`.

Os dois bloqueios por IDs duplicados `alexscaves` e `alexsmobs` estão **fisicamente resolvidos**. Alex's Caves Continued `1.0.9` permanece como única implementação `alexscaves`; Alex's Mobs Continued foi atualizado para `2.1.10` e permanece como única implementação `alexsmobs`.

## Authority de curadoria

- **Create: Bits 'n' Bobs 2.3.1 — `Manter`.** O conflito visual com Create: Sulfuric Resonance 0.4.1 é aceito conscientemente, não corrigido.
- **More Relics 1.7.7 — `Manter`.** Integrações provider-specific continuam fail-closed até compatibilidade real com Relics 0.12.8 ser comprovada.
- **Integrated Mowzie's Mobs 1.1.0 — `Manter`.** Runtime local 1.1.0 é authority física; publicação pública exata da build ainda não foi demonstrada.

## Regra operacional

- Este fechamento + o delta físico de 07/09 formam a authority atual de presença/JAR/versão.
- Os capítulos permanecem authority descritiva de função, integração, riscos e classificação.
- Biblioteca, UI, visual, compat ou scripting não deve ser promovido automaticamente a provider mecânico.
- Um update de versão não revalida automaticamente hooks/APIs usados por perks.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/00-visao-geral.md -->

<!-- Guia temático canônico versionado no GitHub | referência atual de presença/JAR/versão: modlist.txt reconciliada em 2026-08-30 -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **RECONCILIADO COM A MODLIST ATUAL — 2026-08-30:** a referência corrente possui **573 entradas top-level incluindo NeoForge**. Presença, filename JAR e versão são fixados por [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md), que prevalece sobre versões históricas ainda embutidas em capítulos descritivos. A reconciliação incorpora o conjunto **Simply Swords / Simply More / Integrated Simply Swords / Simply Swords: Cataclysm**, **Simply Tooltips**, **Oracle Index**, novas bibliotecas/infraestruturas e os drifts de versão detectados desde 28/08.

## Stack atual de sobrevivência

- **Cold Sweat 2.4.2:** temperatura corporal.
- **Create: Cold Sweat 1.1.2:** bridge Create↔Cold Sweat, não segundo sistema térmico.
- **Ecliptic Seasons 0.14.99-beta-2:** clima/estações; não substitui temperatura corporal.
- **Thirst Was Reclaimed 3.0.4:** sede/hidratação.
- **Thirst Was Fixed 2.1.5:** compatibilidade/fixes do sistema de sede.
- **Nutritional Balance 1.21.1-7.0.3:** nutrição quando a métrica requerida puder ser lida com segurança.
- **AnimalHusbandry 0.4.1:** criação e manejo de animais com genética, saúde, reprodução, linhagem e gestação.

> Este guia reúne os **mods de gameplay e sistemas gerais** do pack que não pertencem principalmente aos eixos de Magia ou Tecnologia. Entram aqui combate, movimento, progressão, sobrevivência, alimentação, fauna, bosses, exploração, estruturas, colônias e utilidades que alteram diretamente a forma de jogar. Para que o conjunto temático **Gameplay/Sistemas + Magia + Tecnologia** cubra integralmente a modlist top-level, ele também mantém cobertura técnica para bibliotecas/APIs, interface, visual, áudio e performance que não pertençam principalmente aos outros dois eixos temáticos. Esses componentes são descritos pelo papel técnico real e não tratados como sistemas de gameplay equivalentes aos capítulos principais.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/01-combate-movimento-e-acao.md -->

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


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/02-progressao-rpg-identidades-e-atributos.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 2. Progressão RPG, identidades e atributos

## Identity2 — 2.2.4

`1.21.1-identity2-neoforge-2.2.4.jar`
**Identity2** é um sistema de morph: ao desbloquear identidades de criaturas, o jogador pode assumir suas formas e receber características e habilidades associadas a elas. O sistema mantém variantes, ajusta hitbox/altura dos olhos e inclui habilidades específicas de várias entidades, além de interações de IA dependentes da forma.
No pack, ele pertence ao eixo RPG/mobs porque altera diretamente o corpo e as capacidades jogáveis do personagem; não é apenas uma modificação visual. O stack atual também inclui **Pehkui 3.8.3+1.21-neoforge**, que fornece escalas de entidade, e **Epic Fight - Pehkui Incompatibility FIX 1.0.2**, que mantém essa camada de escala compatível com o combate Epic Fight.

## Walkers / Woodwalkers — 5.8.13

`walkers-5.8.13.jar`
**Woodwalkers** é um sistema de morph em que o jogador desbloqueia uma **segunda forma de criatura** e pode alternar para ela por keybind. O desbloqueio é feito ao observar uma criatura por um período configurado; depois de escolhida, a forma passa a fazer parte da identidade jogável do personagem conforme as regras do mod.
A transformação é funcional: **tamanho, vida e capacidades** mudam para acompanhar o mob, e quase todas as criaturas possuem alguma habilidade característica — como mobilidade de Strider sobre lava, voo de Bat ou capacidades ofensivas de Ghast. O nome do projeto é Woodwalkers, enquanto o mod id/JAR instalado permanece `walkers` 5.8.13. A dependência principal é CraftedCore.

## Pehkui — runtime 3.8.3+1.21-neoforge

`Pehkui-3.8.3+1.21-neoforge.jar`
**Pehkui** é um framework de **escala de entidades**. Ele permite que jogadores e mobs tenham tamanho alterado de forma persistente e expõe escalas relacionadas a dimensões, hitbox, alcance, velocidade e outras propriedades derivadas para mods consumidores.
Isso o diferencia de Identity2 e Walkers: Pehkui não fornece formas de criatura nem um sistema de morph por conta própria; ele fornece a infraestrutura de escala que outros sistemas podem utilizar quando precisam representar entidades maiores ou menores. No pack, Identity2 é um consumidor relevante e existe também `Epic Fight - Pehkui Incompatibility FIX` para corrigir a apresentação do jogador quando escala e animação Epic Fight interagem.
A build instalada é `3.8.3+1.21-neoforge`, publicada para a linha Minecraft 1.21/1.21.1. O canal público é beta, o que descreve maturidade da release e não incerteza sobre a identidade do JAR atual.

## Pufferfish's Skills — 0.18.3

`puffish_skills-0.18.3-1.21-neoforge.jar`
É o framework jogável de **árvores de habilidades** do pack. Ele permite que datapacks e addons definam categorias de skill, nós, requisitos, conexões, custos, recompensas e fontes de experiência.
Para o jogador, isso aparece como árvores nas quais pontos são investidos para desbloquear bônus ou habilidades. O mod também consegue conceder experiência a partir de ações como dano, mortes e outras fontes configuradas, permitindo que progressão seja vinculada ao que o personagem realmente faz.

## Pufferfish's Attributes — 0.8.3

`puffish_attributes-0.8.3-1.21-neoforge.jar`
**Pufferfish's Attributes** é um provider amplo de atributos dinâmicos para sistemas RPG. Ele registra estatísticas que outros mods, equipamentos, efeitos, datapacks e árvores de skills podem modificar sem precisar implementar a lógica de cada grandeza separadamente.
O catálogo inclui atributos ligados a **stamina**, tipos de dano e resistência, knockback, armor/toughness/protection/resistance shred, regeneração natural, stealth, **life steal**, redução de queda, velocidades de projéteis de bow/crossbow e ganho de experiência, entre outros. Dessa forma, um sistema de progressão pode trabalhar com estatísticas que vão muito além de vida, ataque e velocidade vanilla.
Esses atributos originalmente faziam parte de versões antigas de Pufferfish's Skills, mas foram separados em um mod independente; a linha moderna de Skills é compatível com ele. A build `0.8.3` instalada é a release mantida para NeoForge 1.21/1.21.1.

## Pufferfish's Unofficial Additions — 2.2.8

`pufferfish_unofficial_additions-1.21.1-2.2.8.jar`
**Pufferfish's Unofficial Additions** é um addon para Pufferfish's Skills que aumenta o vocabulário usado por árvores de progressão. Ele adiciona **novas fontes de experiência**, novas rewards e operações de cálculo/configuração que podem ser referenciadas pelos arquivos das skill trees.
Entre as fontes documentadas estão **harvest de crops** — com acesso à quantidade real de seeds/crops dropados — e **fishing**, permitindo calcular XP conforme o item pescado. As rewards incluem aplicar efeitos permanentes, conceder imunidade até determinado amplifier, alterar duração/amplifier de efeitos recebidos e permitir caminhar sobre powder snow.
Há ainda integração direta com **Iron's Spells 'n Spellbooks**: lançar spells pode gerar experiência com fórmulas que recebem escola, spell, nível, raridade, mana cost, duração, cooldown e outros dados do cast. Os antigos atributos próprios deste addon foram movidos para Additional Attributes, portanto ele não substitui Pufferfish's Attributes.

## Additional Attributes — 1.2.2

`additional_attributes-1.21.1-1.2.2.jar`
**Additional Attributes** registra atributos adicionais voltados a mecânicas que normalmente não são expostas como estatísticas modificáveis. Na linha 1.21.1 isso inclui atributos derivados de **Looting, Fishing Lure/Fishing Luck e harvest**, permitindo, por exemplo, aumentar diretamente a quantidade de crops colhidas por modificadores de atributo.
O mod também possui integrações específicas com outros sistemas presentes no pack. Para **Apotheosis/Apothic**, há um atributo de *Apothic Crafting* que controla chance/faixa de raridade de affixes em itens craftados, com clamps configuráveis por datapack. Para **Iron's Spells**, atributos podem aumentar/reduzir nível geral, por escola ou por spell específico, conceder spells/escolas inatas independentes do spellbook e controlar a chance de preservar scrolls.
Assim, ele funciona como uma camada de estatísticas integradoras e não é equivalente ao conjunto generalista do Pufferfish's Attributes. A build NeoForge 1.21.1 instalada é `1.2.2`.

## Apothic Attributes — 2.10.1

`ApothicAttributes-1.21.1-2.10.1.jar`
**Apothic Attributes** é a camada de atributos compartilhada pelo ecossistema Apothic/Apotheosis. Além de infraestrutura para outros módulos, registra estatísticas jogáveis que podem ser modificadas por equipamentos, affixes, gems, efeitos e sistemas externos: **critical strike chance/damage, dodge chance, life steal, healing received, overheal, armor/protection pierce e armor/protection shred**, entre outras grandezas do ecossistema.
A distinção entre *pierce* e *shred* é relevante: pierce ignora parte da defesa durante a resolução do ataque, enquanto shred representa redução aplicada à defesa do alvo conforme as regras do atributo. Esses valores são expostos pelo sistema de atributos do Minecraft e podem ser consumidos por outros conteúdos que reconheçam os atributos registrados.
O mod também fornece infraestrutura/interface utilizada por Apotheosis e pelos módulos Apothic. Portanto ele não é apenas uma biblioteca invisível: define contratos de estatísticas que afetam diretamente cálculo de combate e progressão de equipamento. A build instalada é `2.10.1` para NeoForge 1.21.1 e depende do ecossistema Placebo/Apothic correspondente.

## Wayward Attributes — 1.1.1

`wayward_attributes-1.21.1-1.1.1.jar`
**Wayward Attributes** é principalmente um **overhaul visual de como atributos aparecem nas tooltips**, não um segundo grande provider de estatísticas RPG. Ele associa ícones aos atributos por um sistema data-driven e reorganiza sua apresentação para tornar equipamentos e modificadores mais legíveis.
Além da camada visual, o mod expõe como atributos algumas propriedades que normalmente não aparecem dessa forma: swords passam a mostrar propriedades de **sweeping damage**, enquanto bows e crossbows têm suas características representadas no mesmo modelo de atributos. Portanto sua função principal é apresentação/normalização visual de dados, com pequena exposição adicional de propriedades de armas.
A build `1.1.1` é a release NeoForge 1.21.1 atual. Sua sobreposição relevante é com outros formatadores de tooltip, não com Pufferfish's Attributes ou Apothic Attributes como sistema de progressão.

## AttributeFix — 21.1.3

`attributefix-neoforge-1.21.1-21.1.3.jar`
**AttributeFix** remove limites arbitrários do sistema de atributos do Minecraft que impedem mods e datapacks de trabalhar corretamente com valores muito acima ou abaixo das faixas vanilla. Ele atua na infraestrutura de atributos — vida, dano, velocidade e outras estatísticas registradas — para que sistemas RPG possam aplicar modificadores maiores sem serem truncados pelos clamps padrão.
Não cria novos atributos, perks ou progressão por conta própria; sua função é permitir que providers e equipamentos existentes usem o sistema de atributos em escalas mais amplas. A build instalada é `21.1.3` para NeoForge 1.21.1.

## Max Health Fix — 21.1.4

`maxhealthfix-neoforge-1.21.1-21.1.4.jar`
**Max Health Fix** corrige um bug antigo do Minecraft em que a vida atual do jogador pode ser reduzida incorretamente ao valor vanilla de 20 ao entrar no mundo, mesmo quando equipamentos ou outros sistemas concedem um `max_health` superior.
O patch preserva corretamente a relação entre vida atual e vida máxima durante login/reentrada, evitando que builds com bônus persistentes de saúde percam vida apenas por reconectar. A build instalada é `21.1.4` para NeoForge 1.21.1.

## Apotheosis — 8.7.0

`Apotheosis-1.21.1-8.7.0.jar`
**Apotheosis** é um sistema de progressão RPG centrado em loot especial, affixes, gems, sockets e world tiers. Equipamentos encontrados no mundo podem receber propriedades e raridades próprias, e a progressão passa a envolver obtenção, avaliação e melhoria desses itens em vez de depender apenas do equipamento vanilla.
Na arquitetura atual, partes históricas do mod são módulos top-level separados. **Apothic Attributes** fornece a infraestrutura de atributos; **Apothic Enchanting** implementa o overhaul de encantamentos; **Apothic Spawners** expande e torna configuráveis os spawners. Esses JARs são componentes complementares presentes no pack, não cópias do `Apotheosis-1.21.1-8.7.0.jar`.

## Artifacts — 13.2.3

`artifacts-neoforge-13.2.3.jar`
**Artifacts** transforma exploração em fonte de acessórios poderosos que **não são craftáveis normalmente**. Cada artifact aparece em conjuntos limitados de loot — baús de estruturas, arqueologia ou raramente equipado em entidades — e concede uma habilidade própria quando usado no slot adequado.
O mod também adiciona **underground campsites**, onde um baú pode ser substituído por um Mimic hostil; derrotar um Mimic sempre fornece um artifact aleatório. Cada item possui modelo equipado próprio e incentiva procurar tipos diferentes de estrutura para completar o catálogo. A build `13.2.3` é a release NeoForge 1.21.1 instalada e inclui correções específicas de compatibilidade com Lootr e slots de acessórios.

## Reliquified Artifacts — 1.0.8

`reliquified_artifacts-1.21.1-1.0.8.jar`
**Reliquified Artifacts** conecta Artifacts ao modelo de progressão do **Relics**. O addon adapta e amplia acessórios do Artifacts para que participem das mecânicas do ecossistema Relics e adiciona conteúdo cruzado relacionado, mantendo os dois mods-base como providers separados.
Seu papel é de bridge/conteúdo RPG entre sistemas de acessórios; não duplica o catálogo de Artifacts nem o core de Relics. A build instalada é `1.0.8`, beta oficial para NeoForge 1.21.1, e depende de Relics + Artifacts.

## Iron's Gems 'n Jewelry — runtime 1.21.1-2.0.2

`irons_jewelry-1.21.1-2.0.2.jar`
**Iron's Gems 'n Jewelry** adiciona um sistema modular de **jewelcrafting**. Joias são fabricadas na Jewelcrafting Station a partir de patterns e materiais; alguns patterns são conhecidos de início, enquanto outros precisam ser descobertos em loot ou obtidos por **Artisan Scrolls/trades**.
O projeto possui mais de dez mil combinações possíveis entre padrões e materiais, sete gemstone items obtíveis e uma profissão de villager própria. O pattern define o tipo de bônus/atributo e os materiais alteram as propriedades da peça, permitindo produzir acessórios Curios com estatísticas variadas. A build instalada é `1.21.1-2.0.2` e depende de Iron's Lib, Atlas API e Curios API.

## Apothic Category Compat — 2.0.2

`apothic_compat-2.0.2.jar`
**Apothic Category Compat** corrige itens modded que o sistema automático de categorias do Apotheosis 8.x não classifica adequadamente para affixes. Por meio de um data map, armas externas podem ser roteadas para categorias como **bow, trident, sword, heavy weapon ou categorias adicionais registradas por outros addons**, fazendo o loot/affix system aplicar regras apropriadas.
Na linha 1.21.1 há overrides para conteúdos de Cataclysm, Alex's Caves/Mobs, Born in Chaos, Deeper and Darker, Ice and Fire, Iron's Spellbooks e outros mods. O addon também possui **affix blacklist** para impedir determinados affixes em novos rolls. A build instalada é `2.0.2` para NeoForge 1.21.1/Apotheosis 8.x.

## Apothic Compats — 0.2.4.2

`apothic_compats-0.2.4.2.jar`
**Apothic Compats** distribui datapacks de integração para que conteúdo de outros mods participe do ecossistema Apotheosis de maneira mais profunda. Conforme o mod detectado, pode acrescentar **affixed loot entries, gear sets, affixes, gems, invaders e categorias adicionais** em vez de apenas classificar uma arma existente.
A lista 1.21.1 cobre vários mods presentes no pack, incluindo Applied Energistics 2, Alex's Caves, Alex's Mobs, Ars Nouveau e Amendments, entre muitos outros. Ele também possui suporte opcional a Ancient Reforging. A build `0.2.4.2` é a release NeoForge 1.21.1 instalada, publicada em 22/07/2026.

## Apothic Spawners — 1.4.0

`ApothicSpawners-1.21.1-1.4.0.jar`
**Apothic Spawners** transforma mob spawners em blocos coletáveis e configuráveis. Com Silk Touch, spawners podem ser movidos; **Spawner Modifiers** aplicados com itens vanilla alteram minimum/maximum delay, spawn count, max nearby entities, player range e spawn range, além de propriedades avançadas como ignore players/conditions/light, redstone control, No AI, Silent, Youthful, Burning e Echoing.
O módulo também adiciona o encantamento **Capturing**, que dá chance de mobs derrubarem spawn eggs; esses ovos podem trocar a entidade produzida por um spawner. Assim, o mod cria uma progressão de automação de mobs baseada em localizar, mover, modificar e especializar spawners. A build `1.4.0` é a release NeoForge 1.21.1 instalada.

## Pickable Health Orbs — runtime 1.21.1-1.0.0

`pickable_orbs-1.21.1-1.0.0.jar`
**Pickable Orbs** adiciona pickups no estilo RPG que aparecem como entidades no mundo. Mobs ou blocos configurados podem soltar diferentes tipos de orbe e, ao contato, o jogador recebe imediatamente benefícios como cura ou efeitos de poção; por padrão não se trata de um item guardado para uso posterior.
A configuração permite criar/remover tipos, controlar chance e origem dos drops e definir duração/amplifier dos efeitos. Dessa forma o sistema funciona como uma camada de recompensa momentânea durante combate e exploração.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/03-sobrevivencia-e-condicoes-ambientais.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 3. Sobrevivência e condições ambientais

## Cold Sweat — 2.4.2

`ColdSweat-2.4.2.jar`
**Cold Sweat** adiciona um sistema dinâmico de **temperatura corporal** influenciado tanto pelo mundo quanto pelo estado do jogador. Bioma e temperatura ambiental, clima, altitude, blocos próximos, itens carregados ou usados e outras condições entram no cálculo térmico; por isso atravessar uma montanha, uma região gelada, o Nether ou uma área artificialmente aquecida pode exigir preparação diferente.
A sobrevivência térmica possui várias ferramentas próprias. **Armaduras podem receber insulation**, blocos quentes ou frios irradiam temperatura ao redor, o **Thermometer** permite acompanhar valores com precisão e **Waterskins** servem como alívio imediato ou como packs aquecidos/resfriados. A infraestrutura de base inclui dispositivos como **Hearth, Boiler e Icebox**, que permitem condicionar espaços e formar instalações permanentes de aquecimento ou refrigeração.
O sistema é altamente data-driven e configurável por configs, JSON/datapacks e KubeJS. A build instalada `2.4.2` é a release NeoForge 1.21.1 de 22/06/2026; nessa versão, fontes térmicas em objetos **Sable/Create Aeronautics** passaram a emitir temperatura corretamente e a integração de sede foi atualizada para **Thirst Was Reclaimed**, ambos presentes no stack atual.

## Ecliptic Seasons — 0.14.99-beta-2

`EclipticSeasons-1.21.1-neoforge-0.14.99-beta-2.jar`
**Ecliptic Seasons** transforma o calendário do mundo em um sistema sazonal baseado em **24 termos solares**. O avanço do ano altera clima e precipitação, cores de foliage/ambiente, comportamento de ecossistemas e condições de crescimento das culturas, fazendo agricultura e exploração dependerem do período sazonal em vez de permanecerem idênticas o ano inteiro.
A linha 0.14 também trabalha com renderização sazonal e integrações de mundo distante, incluindo ajustes para **Distant Horizons** e Embeddium. O stack instalado possui ainda **Ecliptic Seasons: MultiMod Patch 0.32.0-beta**, que adiciona compatibilidades em código, e **Ecliptic Seasons: Bundles 0.18.0**, que fornece datapacks/resource packs para conteúdo externo.
Ele não mede a temperatura corporal do jogador: essa função continua pertencendo ao Cold Sweat. A build `0.14.99-beta-2` é a versão NeoForge 1.21.1 instalada; o sufixo beta descreve maturidade da release, não dúvida sobre identidade ou presença.

## Snow! Real Magic! — runtime 12.2.2+neoforge

`SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`
**Snow! Real Magic!** expande o comportamento físico das snow layers. A neve pode cobrir **slabs, stairs, fences, vegetação e outras superfícies não cúbicas**, acumular com maior naturalidade durante nevascas e formar camadas que respeitam melhor a geometria do bloco subjacente.
O mod também adiciona regras configuráveis para acumulação, queda/movimento das camadas e interação com blocos cobertos. Assim, Ecliptic Seasons determina o contexto sazonal/climático em que a neve aparece, enquanto Snow! Real Magic! determina **como a neve depositada ocupa e reage ao mundo**. A build `12.2.2` é a release NeoForge 1.21.1 instalada, cujo runtime declara `12.2.2+neoforge`.

## Nutritional Balance — runtime 1.21.1-7.0.3

`nutritionalbalance-1.21.1-7.0.3.jar`
**Nutritional Balance** adiciona um sistema persistente de dieta no qual alimentos fornecem **Nutritional Units (NUs)** distribuídas entre nutrientes. Tooltips mostram os nutrientes de cada comida e uma GUI própria, aberta por padrão com `N`, exibe o nível atual de cada grupo, faixas-alvo e limites de malnutrition/engorgement.
Manter todos os nutrientes em suas faixas ideais concede buffs configuráveis — como benefícios de vida, velocidade ou eficiência — enquanto permanecer abaixo ou acima dos limites pode aplicar debuffs. **Sugar** funciona como nutriente não essencial, sem penalidade por deficiência, enquanto vegetables possuem comportamento especial que não pune excesso.
O sistema é data-driven: nutrientes usam tags e o mod percorre recipes para inferir o valor de comidas compostas a partir de seus ingredientes, permitindo incorporar alimentos vanilla e modded sem exigir uma tabela manual para cada prato. Buffs, debuffs, thresholds e integrações podem ser ajustados por datapack/config.
A versão `7.0.3` para NeoForge 1.21.1 também reestruturou a construção/traversal de nutrientes para reduzir travamentos em modpacks muito grandes.

## Thirst Was Reclaimed — runtime 1.21.1-3.0.4

`ThirstWasReclaimed-1.21.1-3.0.4.jar`
**Thirst Was Reclaimed** é o provider de **sede/hidratação** do pack. Ele adiciona uma necessidade separada da fome, com formas próprias de consumir água e regras de qualidade/pureza que fazem fontes de hidratação terem valores diferentes em vez de toda água ser equivalente.
O sistema é client+server e a release pública atual do ramo 1.21.1 é `3.0.4`. O metadata local preserva a string `1.21.1-3.0.4` como runtime. As correções e integrações adicionais ficam no addon Thirst Was Fixed, não no core.

## Thirst Was Fixed — 2.1.5

`thirstwasfixed-2.1.5.jar`
**Thirst Was Fixed** é a camada de correções e integrações do Thirst Was Reclaimed. Ele trata estados de pureza em cauldrons, permite configurar pureza de água de chuva/dripstone, beber de cauldrons e aplicar benefícios quando a barra de sede está cheia.
Também conecta sede a sistemas instalados: **Ars Nouveau Potion Flasks** podem restaurar hidratação, a Urn of Endless Waters do Ars Elemental pode interagir com água/pureza, FTB Ultimine pode exigir sede mínima, ParCool pode alterar regeneração de stamina conforme hidratação e Amendments pode purificar água em cauldrons aquecidos. A build instalada `2.1.5` foi publicada em 24/08/2026.

## Sophisticated Thirst Upgrade — 0.1.8

`sophisticated-thirst-upgrade.jar`
**Sophisticated Thirst Upgrade** leva a hidratação para o sistema de upgrades do Sophisticated Backpacks. O upgrade monitora itens armazenados na mochila capazes de restaurar sede e pode **consumi-los automaticamente**, de maneira análoga aos upgrades automáticos de alimentação do ecossistema Sophisticated.
Ele depende da mochila e do sistema de sede existente; não cria uma barra própria. O runtime instalado é `0.1.8`.

## Ecliptic Seasons: MultiMod Patch — 0.32.0-beta

`Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.0-beta.jar`
**MultiMod Patch** adiciona integrações em código para que outros sistemas reconheçam corretamente o calendário e as condições sazonais do Ecliptic Seasons. No stack atual, a cobertura pode atingir sistemas como **Cold Sweat, Dynamic Trees, MineColonies, JourneyMap** e outros mods suportados pela versão instalada.
Ele não cria estações novas: traduz o estado sazonal do Ecliptic Seasons para APIs e mecânicas de outros mods. A release instalada é `0.32.0-beta` para NeoForge 1.21.1.

## Ecliptic Seasons: Bundles — 0.18.0

`EclipticSeasons-Bundles-0.18.0.jar`
**Ecliptic Seasons: Bundles** complementa o patch em uma camada de **datapacks e resource packs**. Ele fornece dados sazonais para crops, vegetação, biomas e conteúdo de mods suportados, permitindo que recursos externos recebam regras e representação coerentes com as estações.
Portanto Bundles e MultiMod Patch não são duplicatas: um concentra dados/recursos e o outro integra lógica em código. O runtime atual é `0.18.0`.

## Puddles & Floods — 1.1.5

`puddleflood-1.1.5+1.21.1-neoforge.jar`
**Puddles & Floods** adiciona puddles que surgem e acumulam durante chuva, podem se conectar em formas irregulares e fazem cursos d'água parecerem **transbordar visualmente suas margens**. As poças evaporam conforme as condições e podem usar a água do shader quando configurado.
Quantidade de cobertura, raio de geração ao redor do jogador, velocidade de coleta/evaporação e comportamento de conexão com água são configuráveis. A maior parte da camada visual pode funcionar no cliente; a build `1.1.5` é a release NeoForge 1.21.1 de 08/07/2026.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/04-alimentacao-culinaria-e-agricultura.md -->

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


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/05-ecossistema-let-s-do.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 5. Ecossistema [Let's Do]

## Farm & Charm — 1.1.23

`letsdo-farm_and_charm-neoforge-1.1.23.jar`
**[Let's Do] Farm & Charm** é o núcleo agrícola/rural de parte do ecossistema Let's Do. Ele adiciona **novos grãos e frutas**, blocos funcionais de fazenda e uma cozinha de estilo farmhouse, fazendo cultivo e transformação dos produtos ocorrerem em várias etapas.
Entre os equipamentos estão **Mincer**, Cooking Pot/Bowls e Stove. A linha recente permite, por exemplo, alimentar e acender o Stove manualmente, trabalhar ingredientes em bowls que precisam ser mexidos e processar carnes pelo Mincer. Assim, a fazenda fornece matérias-primas que seguem para equipamentos culinários próprios, e não apenas novas crops decorativas.
A build `1.1.23` é a release NeoForge 1.21.1 publicada em 30/07/2026 e funciona como base compatível para Bakery e Brewery instalados.

## Bakery — 2.1.6

`letsdo-bakery-neoforge-2.1.6.jar`
**[Let's Do] Bakery** expande a produção de pães e confeitaria com **variações de bread, cupcakes e cakes**, muitos deles consumíveis e também colocáveis no mundo. O conteúdo trabalha com ingredientes e etapas de padaria próprios, além de containers e blocos temáticos usados no preparo/apresentação.
A edição atual é a linha com **compatibilidade aprimorada com Farm & Charm**, compartilhando ingredientes e infraestrutura do ecossistema Let's Do. A build `2.1.6` é a release NeoForge 1.21.1 instalada e inclui correções para recipientes/remainders de receitas e blocos como trays.

## Brewery — 2.1.9

`letsdo-brewery-neoforge-2.1.9.jar`
**[Let's Do] Brewery** adiciona o ramo de **fermentação e produção de bebidas** do ecossistema Let's Do. Ingredientes agrícolas podem seguir para infraestrutura própria de brewery e resultar em diferentes bebidas, fazendo cultivo e processamento alimentar também alimentarem uma cadeia de fermentação.
A edição instalada é especificamente a linha **Brewery - Farm & Charm Compat**, criada para melhorar a interoperabilidade com Farm & Charm em 1.21.1. O JAR `letsdo-brewery-neoforge-2.1.9.jar` é a release NeoForge publicada em 28/03/2026.

## Vinery — 1.5.3

`letsdo-vinery-neoforge-1.5.3.jar`
**[Let's Do] Vinery** cria uma cadeia própria de **viticultura e produção de vinho**. O jogador cultiva uvas, processa a colheita e utiliza equipamentos de vinícola para transformar os frutos em bebidas, fazendo agricultura e fermentação formarem uma progressão temática separada das cervejas do Brewery e dos chás do HerbalBrews.
Além da fabricação das bebidas, o mod inclui blocos e decoração voltados a construir uma **vinícola completa**, fazendo a infraestrutura de produção também participar da ambientação. A build instalada `1.5.3` é a linha NeoForge 1.21.1 publicada em 28/01/2026 e está classificada como beta; isso descreve maturidade da versão, não incerteza sobre o JAR.

## HerbalBrews — 1.1.3

`letsdo-herbalbrews-neoforge-1.1.3.jar`
**[Let's Do] HerbalBrews** adiciona uma cadeia de **chá e café** baseada em coleta de plantas e processamento dos ingredientes. Folhas e ervas precisam ser obtidas e **secas** antes de entrarem nos sistemas de brewing, separando preparo da matéria-prima e infusão final em etapas próprias.
O mod também possui decoração temática para espaços de chá/café, mas a função principal é jogável: gathering → drying → brewing. Ele não é equivalente a Brewery ou Vinery, que trabalham com outras matérias-primas e processos. A build `1.1.3` é a release NeoForge 1.21.1 atual, publicada em 21/02/2026.

## Meadow — 1.4.8

`letsdo-meadow-neoforge-1.21.1-1.4.8.jar`
**[Let's Do] Meadow** combina worldgen rural/alpino com **manejo de gado e produção de queijo**. O jogador encontra paisagens de pastagem, flores e estruturas temáticas, cuida do rebanho, ordenha vacas e utiliza o leite em etapas de fabricação e envelhecimento de queijo.
A fauna bovina também é ampliada: a documentação atual apresenta **nove raças de vaca**, incluindo Highland Cattle. O mod acrescenta culturas como oat, materiais naturais, mobiliário e blocos próprios, portanto não é apenas um pacote culinário nem somente worldgen. A build `1.4.8` é a release NeoForge 1.21.1 instalada.

## Candlelight — 2.1.12

`letsdo-candlelight-neoforge-2.1.12.jar`
**[Let's Do] Candlelight** amplia o lado de **refeições servidas e jantar** do ecossistema Let's Do. Mesas, pratos, velas e mobiliário formam espaços de restaurante/jantar enquanto alimentos próprios utilizam essa infraestrutura para apresentação e consumo, fazendo decoração e culinária funcionarem juntas.
A edição instalada é especificamente a variante **Candlelight - Farm & Charm Compat**, criada para compartilhar melhor ingredientes e regras com Farm & Charm em Minecraft 1.21.1. Assim, Candlelight funciona como extensão gastronômica/ambiental do stack rural, não como substituto do sistema-base de agricultura. O JAR `2.1.12` é a release NeoForge de 13/03/2026.

## Wilder Nature — 1.1.5

`letsdo-wildernature-neoforge-1.1.5.jar`
**[Let's Do] Wilder Nature** é a linha de fauna do ecossistema Let's Do. A versão atual reintroduz os animais conhecidos das versões anteriores com **modelos e conteúdo retrabalhados**, distribuindo novas criaturas pelo mundo e associando a elas recursos de exploração, alimentação e ambientação natural.
O projeto é classificado simultaneamente como Mobs, World Gen, Food e Adventure/RPG, refletindo que os animais não funcionam apenas como decoração. A build `1.1.5` é a release NeoForge 1.21.1 publicada em 30/07/2026.

## AnimalHusbandry — 0.4.1

`AnimalHusbandry-neoforge-0.4.1.jar`
**AnimalHusbandry** transforma reprodução vanilla em um sistema persistente de **manejo, saúde e genética**. Animais possuem traços que podem ser transmitidos aos descendentes, o sistema acompanha linhagem e aplica consequências de **inbreeding**, e a reprodução utiliza períodos de gestação específicos por espécie em vez de gerar filhotes imediatamente.
A camada de cuidado inclui saúde/doenças e interação com recursos de curral. Na versão `0.4.0`, galinhas ganharam **ninhos e incubação de ovos**, e a IA/pathfinding foi ampliada para que animais consigam localizar e utilizar recursos de manejo acessíveis no recinto. Isso permite que layout do curral e disponibilidade de infraestrutura façam parte do sistema.
O mod é, portanto, uma simulação própria de husbandry/genética e não apenas uma expansão de modelos de animais. A build NeoForge 1.21.1 instalada é `0.4.0` e depende de Architectury API.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/06-fauna-inimigos-e-bosses.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 6. Fauna, inimigos e bosses

## Born in Chaos — 1.7.6

`born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar`
**Born in Chaos** é uma expansão de hostilidade e aventura construída em torno de **inimigos com regras próprias**, não apenas variantes com mais vida ou dano. O roster inclui undead, spirits, arthropods e outras criaturas com comportamentos que exigem respostas diferentes: alguns inimigos explodem ou se transformam quando enfraquecidos, outros bloqueiam ataques e possuem counters específicos, alguns aplicam debuffs ou fortalecem criaturas próximas, enquanto summoners conseguem produzir minions durante o combate.
A dificuldade também pode evoluir conforme o mundo progride. O **Nightmare Stalker**, por exemplo, possui comportamento ligado à idade do mundo e ganha capacidades adicionais em estágios posteriores; o sistema de **Naughtiness** associado ao Krampus observa determinadas ações do jogador e participa de seus encontros e summons. O mod ainda adiciona minibosses/bosses, eventos sazonais, estruturas, armas, armaduras, blocos e achievements, fazendo exploração e equipamento participarem da mesma expansão de ameaça.
Os spawns utilizam tipos de bioma em vez de depender apenas de uma lista rígida de biomas vanilla, permitindo que as criaturas apareçam também em ambientes modded compatíveis. A build instalada é `1.7.6` para NeoForge 1.21.1 e utiliza GeckoLib para a camada de entidades/animações.

## Fungal Infection: Spore — 2.2.0j

`spore_1.21.1_2.2.0j_neo.jar`
**Fungal Infection: Spore** é um sistema de infestação fúngica hostil que trata a ameaça como um **ecossistema em expansão**. A Mycelium Infection pode contaminar hospedeiros adequados; criaturas abatidas ou tomadas pela infecção retornam como formas infectadas, que passam a buscar alimento, biomassa e novas oportunidades de propagação em vez de funcionarem como mobs isolados.
Os infectados possuem uma progressão própria: acumulam biomassa, **evoluem ou sofrem mutações para formas mais fortes** e podem atuar em hordas, chamar reforços e obedecer a uma hierarquia de criaturas. Estruturas biológicas como **Mounds** espalham a infecção por blocos e ajudam a transformar o ambiente, enquanto estágios mais avançados trabalham com biomassa, armadilhas e a reconstrução de uma **Hivemind**, tornando a expansão territorial parte central da mecânica.
A build instalada é `2.2.0j` para NeoForge 1.21.1. O addon **Spore: Infnexus** abaixo acrescenta um Nexus persistente sobre esse sistema-base; ele não substitui a lógica de infecção, evolução e hivemind do Spore.

## Spore: Infnexus — 2.0.4

`Infnexus-2.0.4-1.21.1.jar`
**Spore: Infnexus** acrescenta ao Fungal Infection: Spore um **Nexus of Infection**, núcleo central inicialmente invulnerável que transforma a infestação em uma ameaça territorial persistente. A partir dele, a corrupção se expande para fora em um raio crescente; na linha 2.0.4, o avanço não se limita mais a pintar biomassa sobre terreno existente: o Nexus **reconstrói o solo conquistado**, formando uma zona infectada com biomas distintos e mais conteúdo próprio dentro da área.
O avanço pode ser enfraquecido por ações contra o ecossistema Spore. A documentação atual cita **matar mobs infectados** e congelar a infecção usando **CDU units** como formas de reduzir sua força, ligando combate e contenção ambiental ao estado do Nexus. Isso cria um objetivo regional de longo prazo: enquanto o core persiste, o território continua sendo pressionado pela expansão.
A build instalada é `2.0.4` para NeoForge 1.21.1 e exige Spore 2.2 ou superior. Sua função é adicionar o núcleo territorial e a lógica de zona infectada; a infecção básica, evolução de criaturas, biomassa e hivemind continuam pertencendo ao mod-base.

## Legendary Monsters — runtime 1.21.1

`legendary_monsters-2.2.2 MC 1.21.1.jar`
**Legendary Monsters** adiciona uma camada de encontros de **mid e late game** formada por bosses, minibosses, estruturas próprias e equipamentos obtidos dessas lutas. A proposta é inserir desafios especiais no mundo sem substituir os mobs vanilla: estruturas e arenas levam a inimigos com padrões próprios e recompensas associadas.
O projeto também inclui armas e armaduras ligadas ao conteúdo dos bosses e continua recebendo ajustes de combate, spawn e estruturas. O arquivo instalado é a release pública **2.2.2 para NeoForge 1.21.1**, publicada em 23/08/2026; o metadata interno do JAR declara apenas `1.21.1` como runtime version, e por isso o guia mantém filename/publicação e runtime separados. A expansão mágica `Legendary Spellbooks 0.3.2` permanece documentada no Guia de Magia.

## Ice And Fire Community Edition — 2.1.1

`iceandfire-2.1.1.jar`
**IceAndFire Community Edition** é a continuação comunitária do ecossistema Ice and Fire para versões modernas. O mod adiciona **dragões elementais, criaturas míticas, ninhos/covis, estruturas, materiais, armas, armaduras e o Dragon Forge**, formando uma linha extensa de exploração, combate e criação de criaturas.
Dragões possuem crescimento, domesticação, montaria e recursos próprios; outras criaturas míticas também têm drops e equipamentos associados. O fork comunitário inclui rewrites, otimizações e conteúdo novo sobre a base clássica. A build `2.1.1` é a release NeoForge 1.21.1 instalada, publicada em 19/08/2026, e serve de mod-base para Dragon Care, Dread Land e bridges específicas presentes no pack.

## Ice And Fire: Dragon Care — runtime 1.3.0 - 1.21.1v

`Ice and Fire - Dragon Care-1.3.0 - 1.21.1v.jar`
**Dragon Care** transforma dragões domesticados do Ice And Fire CE em criaturas de manejo prolongado. O addon acrescenta **bonding**, alimentação e rotinas de cuidado, ferramentas veterinárias, limpeza e formas de obter determinados recursos sem matar o animal.
A proposta é deslocar o dragão domesticado de uma simples montaria/arma para um companheiro que exige manutenção e relação contínua com o jogador. A build `1.3.0 - 1.21.1v` é a release NeoForge 1.21.1 instalada, publicada em 03/08/2026, e depende diretamente do Ice And Fire Community Edition.

## Ice And Fire: Dread Land — 0.1.2 beta

`iceandfire_dreadland-0.1.2.jar`
**Ice And Fire: Dread Land** adiciona uma dimensão de aventura própria ligada ao Ice And Fire CE. A Dread Land é dividida em **quatro regiões/reinos temáticos — Iceland, Fireland, Lightning e Dreadland —**, com estruturas, dungeons, chaves e uma progressão baseada em acesso a áreas e portal.
O addon amplia o conteúdo de exploração do mod-base em vez de apenas adicionar mais um mob. A build `0.1.2` é beta NeoForge 1.21.1 publicada em 22/08/2026; essa classificação representa maturidade/WIP do conteúdo, não dúvida sobre a presença ou identidade do JAR instalado.

## Alex's Mobs Continued — 2.1.8

`alexsmobs-2.1.8-neoforge+1.21.1.jar`
**Alex's Mobs Continued** mantém o conteúdo de Alex's Mobs disponível nas versões modernas sem redesenhar ou rebalancear o roster original. O projeto declara cerca de **116 animais e monstros**, cada um com modelo, animação, IA e comportamento próprios, distribuídos por diferentes ambientes.
As criaturas não funcionam apenas como decoração: há relações ecológicas, métodos específicos de tame/breeding, montarias, capturas, drops, equipamentos e itens utilitários associados. O **Animal Dictionary** continua servindo como referência interna para descobrir comportamento e interações. A build `2.1.8` é a versão atualmente instalada para NeoForge 1.21.1.

## Mowzie's Mobs — 1.8.2

`mowziesmobs-1.21.1-1.8.2.jar`
**Mowzie's Mobs** adiciona criaturas e bosses com forte foco em **IA própria, animações, padrões de ataque e encontros roteirizados**. Em vez de simplesmente aumentar atributos de mobs vanilla, cada criatura importante possui identidade mecânica própria, incluindo ataques telegráficos, fases, arenas/estruturas e recompensas associadas.
A linha 1.8 introduziu criaturas como Bilokosa/Bilokosa Howler e continuou refinando bosses existentes, armas e summons. A build `1.8.2` é a release NeoForge 1.21.1 instalada. O conteúdo cruza o domínio de outros mods de bosses, mas seus encontros, habilidades e equipamentos são próprios.

## L_Ender's Cataclysm — 3.33

`L_Ender's Cataclysm 1.21.1-3.33.jar`
**L_Ender's Cataclysm** é um grande mod de aventura/endgame centrado em **estruturas míticas, dungeons, inimigos de elite, bosses multiataque e equipamentos poderosos**. O jogador encontra conteúdo distribuído pelo mundo e por dimensões já existentes, enfrenta estruturas hostis e progride até lutas de boss com padrões e fases próprias.
Os drops alimentam armas, armaduras e materiais de alto nível, fazendo o mod funcionar como uma camada de progressão de combate para personagens já desenvolvidos. A versão `3.33` é a release NeoForge 1.21.1 instalada, publicada em 22/08/2026. Addons como Mowzie's Cataclysm e bridges Reliquified permanecem expansões separadas do mod-base.

## Mowzie's Cataclysm — 1.2.2

`mowzies_cataclysm-1.2.2.jar`
**Mowzie's Cataclysm** é uma integração de conteúdo entre Mowzie's Mobs e L_Ender's Cataclysm. A implementação atual adiciona **quatro Eyes** usados para localizar bosses de Mowzie's Mobs por uma lógica semelhante aos itens de localização ligados aos bosses de Cataclysm, criando continuidade de exploração entre os dois ecossistemas.
O projeto também prevê conteúdo adicional como boss music discs, mas o guia registra apenas o que a linha atual documenta como implementado. A build `1.2.2` é a release NeoForge 1.21.1 instalada.

## Companions! — 1.3.2

`companions-neoforge-1.21.1-1.3.2.jar`
**Companions!** adiciona criaturas domesticáveis voltadas a exploração e combate, cada uma com **habilidades próprias**, além de mobs hostis, armas e um boss. Companheiros possuem estados de comportamento — wandering, sitting e following — e podem ser curados com Small/Great Essence obtidas de inimigos hostis; summons gerados por outros companions seguem regras separadas.
O sistema é configurável e transforma aliados em uma camada jogável própria, não apenas pets cosméticos. A build `1.3.2` é a release NeoForge 1.21.1 instalada, publicada em 08/08/2026.

## Enhanced AI — 4.2.2.1

`enhancedai-4.2.2.1.jar`
**Enhanced AI** altera capacidades e decisões de mobs hostis para tornar perseguição e combate menos passivos. O projeto é conhecido por comportamentos como **creepers abrindo caminho/breaching, zombies minerando e skeletons atacando de forma mais eficiente**, além de módulos configuráveis de perseguição, interação com o ambiente e patrulha.
A intensidade dessas mudanças pode ser controlada por configuração. O mod altera comportamento de gameplay; não deve ser confundido com `AI-Improvements`, cujo papel principal é reduzir custo de processamento de IA. A build `4.2.2.1` é a release NeoForge 1.21.1 instalada.

## Nyf's Spiders — 3.0.1

`nyfsspiders-neoforge-1.21.1-3.0.1.jar`
**Nyf's Spiders** substitui a navegação limitada das aranhas por uma locomoção de escalada muito mais contínua. Aranhas passam a reconhecer **paredes, superfícies verticais e transições de orientação** como caminhos utilizáveis, podendo perseguir jogadores por geometrias que a IA vanilla não atravessaria adequadamente.
A linha 3.0 migrou a implementação para **Advanced Wall Climber API**, mantendo o objetivo de fazer spiders se moverem como criaturas realmente capazes de aderir a superfícies. O JAR `3.0.1` é a release NeoForge 1.21.1 instalada.

## Ragdoll mob corpses — 1.1.5

`mob_ragdoll_corpse-1.1.5.jar`
**Ragdoll mob corpses** faz criaturas mortas deixarem corpos físicos ragdoll em vez de simplesmente desaparecerem após a animação de morte. Os cadáveres entram no mesmo tipo de apresentação física do stack Sable e podem permanecer como objetos pós-morte interativos.
O projeto também descreve usos como carregar presas/companheiros e enterrar companheiros, fazendo o corpo persistente participar da ambientação e da interação após a morte. A build instalada é `1.1.5` para NeoForge 1.21.1.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/07-FRESHNESS-2026-09-07.md -->

# Overlay de freshness do capítulo 7 — 2026-09-07

- `alexscaves-2.0.2.jar` foi removido; **Alex's Caves Continued 1.0.9** é a única implementação `alexscaves`.
- `alexsmobs-1.22.9.jar` foi removido; **Alex's Mobs Continued 2.1.10** é a única implementação `alexsmobs`.
- Dynamic Trees BetterEnd e BetterNether estão em **2.2.0**.
- Os blockers antigos de discovery por IDs duplicados foram resolvidos fisicamente.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/07-exploracao-dimensoes-e-worldgen.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

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

## Alex's Caves — 2.0.2

`alexscaves-2.0.2.jar`
Adiciona seis destinos subterrâneos raros: **Magnetic Caves, Primordial Caves, Toxic Caves, Abyssal Chasm, Forlorn Hollows e Candy Cavity**. Cada região possui blocos, criaturas, itens e mecânicas próprias.
A exploração começa através de Underground Cabins, Cave Tablets e Cave Compendium, fazendo os biomas funcionarem como locais que precisam ser descobertos e procurados, não apenas como decoração aleatória de cavernas.

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

### Dynamic Trees - BetterEnd — 2.0.0h

`dtbetterend-1.21.1-2.0.0h.jar`
Bridge **BetterEnd ↔ Dynamic Trees**. Converte espécies e vegetação arbórea/fúngica relevantes do BetterEnd para o modelo de crescimento dinâmico, de modo que a flora da dimensão continue aparecendo nos biomas corretos sem voltar a árvores estáticas vanilla-style.
O addon também utiliza recursos do Dynamic Trees Plus quando precisa representar vegetação especial. A build `2.0.0h` corresponde exatamente ao JAR NeoForge 1.21.1 instalado.

### Dynamic Trees - BetterNether — 2.0.0h

`dtbetternether-1.21.1-2.0.0h.jar`
Bridge **BetterNether ↔ Dynamic Trees**. Adapta árvores e vegetação compatível dos biomas BetterNether ao sistema de espécies dinâmicas, incluindo crescimento, sementes e substituição durante worldgen.
Seu papel é preservar a identidade vegetal do BetterNether enquanto o core Dynamic Trees controla como as espécies crescem e se renovam. A build instalada é `2.0.0h`.

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

`dtvanillabackport-1.21.1-1.5.0h.jar`
Bridge **VanillaBackport ↔ Dynamic Trees** focada principalmente na **Pale Oak** trazida das versões vanilla posteriores. A árvore recebe crescimento/ramificação dinâmica, propagação compatível e integração com worldgen sem perder a relação especial com o **Creaking Heart**.
A build atual é `1.5.0h`, substituindo a linha anterior 1.5.0 e correspondendo ao JAR carregado na modlist.

### Dynamic Trees Addon Lib — 0.2.0-BETA03

`DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar`
**Dynamic Trees Addon Lib** é a biblioteca comum usada por treepacks e bridges do ecossistema Dynamic Trees. Ela centraliza **modelos, registries, helpers e utilidades de integração** que seriam duplicados em cada addon individual, permitindo que compat packs registrem espécies e recursos com uma base compartilhada.
Não adiciona uma nova progressão ou provider de árvores por si só. A build `0.2.0-BETA03` é infraestrutura do stack; o sufixo beta descreve a maturidade da API.

## Streams Reflowing — 2.13.1

`StreamsReflowing-1.21.1-neoforge-2.13.1.jar`
**Streams Reflowing** altera a hidrologia do worldgen para produzir **cursos d'água contínuos e visualmente mais naturais**, em vez de depender apenas de lagos e rios definidos pelo modelo vanilla. Streams acompanham o relevo, conectam regiões e criam trajetórias que tornam água corrente uma parte mais explícita da paisagem.
O mod atua na geração física de cursos d'água e portanto é diferente de expansões que apenas adicionam biomas aquáticos ou blocos de rio. A build instalada é `2.13.1` para NeoForge 1.21.1.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/08-estruturas-e-dungeons.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 8. Estruturas e dungeons

## Integrated Cataclysm — runtime 1.0.6+1.21.1-neoforge

`integrated_cataclysm-1.0.6+1.21.1-neoforge.jar`
**Integrated Cataclysm** retrabalha e integra estruturas de **L_Ender's Cataclysm** usando a filosofia da família Integrated: estruturas passam a incorporar elementos e blocos de outros mods suportados, incluindo integrações declaradas com Create e Supplementaries.
O addon altera a apresentação e integração das estruturas ligadas ao conteúdo Cataclysm; bosses e progressão principal continuam pertencendo ao mod-base. A build instalada é `1.0.6+1.21.1-neoforge`.

## Integrated Dungeons Arise — 2.1.1

`IDA v2.1.1-1.21.1.jar`
**Integrated Dungeons Arise (IDA)** retrabalha estruturas de **When Dungeons Arise** para que elas se encaixem melhor em um mundo fortemente modded. Em vez de apenas copiar estruturas do mod-base, o projeto revisa geração, interiores, loot e integrações para usar conteúdo de outros mods quando disponível.
A versão `2.1.1` inclui, entre outras mudanças, uma nova versão do Lighthouse alinhada visualmente a IDAS, melhorias de geração/loot no Aviary, um overhaul do Ceryneian Hind como dig site com compat opcional de Better Archeology e integração de Guard Villagers em estruturas específicas. Assim, o mod funciona como overhaul/integration layer de WDA, distinto de IDAS embora os dois compartilhem linguagem de integração.

## Integrated Dungeons and Structures — runtime 1.13.7+1.21.1-neoforge

`idas-1.13.7+1.21.1-neoforge.jar`
**Integrated Dungeons and Structures (IDAS)** adiciona estruturas próprias deliberadamente construídas com conteúdo de vários mods, evitando que o worldgen pareça dividido em ilhas de conteúdo independentes. A base 1.21.1 exige **Integrated API, Create, Quark e Supplementaries** e pode incorporar conteúdo opcional de mods como Alex's Mobs, Farmer's Delight, Ice and Fire, Ars Nouveau, Terralith e Waystones quando detectados.
Isso permite dungeons e pontos de interesse com blocos, mobs, loot e mecânicas cruzadas, funcionando como uma camada de exploração integrada ao modpack. A build instalada é `1.13.7+1.21.1-neoforge`, release de 11/06/2026.

## FarmersStructures — 1.0.6

`FarmersStructures-1.0.6-1.21.1_neoforge.jar`
**Farmers Structures** leva Farmer's Delight para o worldgen por meio de estruturas que funcionam ao mesmo tempo como ambientação, loot e **demonstração das mecânicas culinárias/rurais**. O projeto possui dezenas de construções temáticas; a atualização 1.0.6 acrescenta cerca de 40 estruturas novas, incluindo cat house, flower beds, frog pond e blacksmith workshop com mina.
Em vez de criar uma segunda agricultura, ele distribui conteúdo e referências de Farmer's Delight pelo mundo para que o jogador encontre suas mecânicas durante exploração. A build instalada `1.0.6` é a release NeoForge 1.21.1 de 22/05/2026.

## Create: Pillagers Arise — 132.36

`create_pillagers_arise NeoForge 1.21.1-132.36.jar`
**Create: Pillagers Arise** transforma tecnologia Create em conteúdo hostil de worldgen. O mod adiciona **10 estruturas** controladas por pillagers — fortalezas, postos e instalações — que utilizam estética e componentes Create em fortificações, contraptions, mecanismos, armadilhas e defesas automatizadas.
Esses locais funcionam como desafios de exploração/combate e fontes de loot, fazendo o ecossistema Create aparecer fora das fábricas construídas pelo jogador. A build `132.36` é a release NeoForge 1.21.1 instalada.

## Integrated Stronghold — runtime 1.1.4+1.21.1-neoforge

`integrated_stronghold-1.1.4+1.21.1-neoforge.jar`
**Integrated Stronghold** substitui o Stronghold vanilla por um complexo muito maior e mais elaborado, com **salas, corredores, encontros, decoração e conteúdo integrado de mods externos**. O objetivo é fazer a jornada até o End Portal funcionar como uma dungeon propriamente dita, e não apenas como uma rede repetitiva de stone bricks.
Assim como outros projetos Integrated, ele utiliza materiais e elementos do ecossistema modded para que a estrutura pareça parte do mundo do pack. A build `1.1.4+1.21.1-neoforge` é a release 1.21.1 instalada.

## Integrated Villages — runtime 1.3.3+1.21.1-neoforge

`integrated_villages-1.3.3+1.21.1-neoforge.jar`
**Integrated Villages** reconstrói vilas para que settlements usem **edifícios maiores, layouts mais elaborados e materiais/conteúdo provenientes do stack modded**, em vez de parecerem estruturas vanilla isoladas no meio de um mundo altamente modificado.
As construções podem incorporar blocos e elementos de mods detectados por meio da infraestrutura Integrated API, aproximando moradia, profissões, decoração e loot do restante do pack. A build instalada é `1.3.3+1.21.1-neoforge`; o mod atua sobre estrutura/worldgen de vilas, enquanto addons como Create: Dynamic Village adicionam profissões e conteúdo temático próprios.

## YUNG's Better Caves — 3.1.5

`YungsBetterCaves-1.21.1-NeoForge-3.1.5.jar`
**YUNG's Better Caves** altera o próprio **cave carving** do mundo, substituindo grande parte da repetição vanilla por sistemas subterrâneos maiores e mais variados. Cavernas podem assumir escalas, larguras e formatos diferentes, formar redes extensas e incluir regiões inundadas, rios e lagos subterrâneos.
Seu papel é geométrico: ele define a forma das cavidades onde outros conteúdos subterrâneos podem aparecer. Por isso é distinto de Alex's Caves, que adiciona biomas/cavernas temáticas com mobs e materiais próprios. A build `3.1.5` é a release NeoForge 1.21.1 instalada e depende de YUNG's API.

## YUNG's Better Desert Temples — 4.1.5

`YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar`
**YUNG's Better Desert Temples** substitui o pequeno templo vanilla por uma dungeon com **salas, parkour, armadilhas, puzzles e uma progressão interna**. Por padrão, jogadores recebem Mining Fatigue dentro do templo até concluir o desafio e derrotar o **Pharaoh** no final, impedindo simplesmente atravessar as paredes para alcançar o loot.
A estrutura oferece recompensas melhores e puzzles com circuitos/redstone ocultos na própria construção. A linha 1.21.1 também refinou peças de puzzle, entrance hall e storage rooms com loot adicional. A build instalada é `4.1.5` NeoForge 1.21.1.

## YUNG's Better Dungeons — 5.1.4

`YungsBetterDungeons-1.21.1-NeoForge-5.1.4.jar`
**YUNG's Better Dungeons** redesenha as dungeons vanilla e acrescenta três famílias grandes de dungeon: **Catacombs, Fortresses of the Undead e Spider Caves**, além de versões menores espalhadas pelo subterrâneo. Cada família possui arquitetura, distribuição de mobs e exploração próprias, em vez de repetir a sala cúbica vanilla com um spawner central.
Os tipos individuais podem ser ajustados por configuração/datapack, e existe ainda geração opcional de pequenas dungeons no Nether. A build `5.1.4` é a versão NeoForge 1.21.1 instalada.

## YUNG's Better End Island — 3.1.2

`YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar`
**YUNG's Better End Island** redesenha especificamente a **ilha central e o encontro com o Ender Dragon**. Obsidian pillars, End Gateways, plataforma de chegada e portal central recebem novas estruturas, incluindo uma torre central que organiza o cenário da batalha.
O dragão não aparece imediatamente ao entrar no End: ele é invocado quando o jogador se aproxima da bell tower central. Para ressummon, quatro End Crystals podem ser posicionados em blocos de bedrock dentro das alas da torre, embora as posições vanilla continuem aceitas por compatibilidade. BetterEnd: New Dawn atua sobre a dimensão ampla; este mod atua sobre a ilha/boss encounter. A build instalada é `3.1.2`.

## YUNG's Better Jungle Temples — 3.1.2

`YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`
**YUNG's Better Jungle Temples** reconstrói o templo da jungle como uma dungeon com **nova arquitetura, armadilhas, puzzles e loot**. A exploração exige percorrer a estrutura e resolver seus mecanismos em vez de localizar rapidamente os dois baús do templo vanilla.
O projeto possui peças opcionais de integração com **Create, Supplementaries e Alex's Mobs** quando esses mods estão presentes, permitindo que parte da estrutura utilize conteúdo do próprio pack. A build `3.1.2` é a release NeoForge 1.21.1 instalada.

## YUNG's Better Mineshafts — 5.1.1

`YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar`
**YUNG's Better Mineshafts** substitui as linhas retas/repetitivas vanilla por redes de túneis muito mais orgânicas. O projeto possui **13 variantes ligadas a biomas**, incluindo uma rara Mushroom Mineshaft, além de abandoned workstations, cellars, depósitos de minério no fim de alguns túneis, miner outposts e raras aberturas para a superfície.
Assim, mineshafts passam a funcionar como estruturas exploráveis com pontos de interesse e loot distribuído. Better Caves continua responsável pelo formato das cavernas; Better Mineshafts insere uma estrutura construída dentro desse subterrâneo. A build instalada é `5.1.1`.

## YUNG's Better Nether Fortresses — 3.1.5

`YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar`
**YUNG's Better Nether Fortresses** reconstrói as fortresses do zero como complexos muito maiores, divididos em **bridge networks, Keep e Lava Halls** que descem para regiões inferiores da estrutura. O resultado oferece mais rotas, salas, combate e recompensas do que o corredor repetitivo da fortress vanilla.
Existe compatibilidade nativa opcional com **Create**, permitindo que determinadas partes da fortress usem peças Create quando o mod está instalado. A build `3.1.5` é a release NeoForge 1.21.1 atual desse ramo e mantém a lógica de spawn de mobs da fortress alinhada ao comportamento vanilla.

## Cataclysm x YUNG's Better Nether Fortresses Compat — runtime 1.21.1

`cataclysmfortresses-1.21.1-NeoForge.jar`
**Cataclysm x YUNG's Better Nether Fortresses Compat** é uma bridge de worldgen/spawn entre L_Ender's Cataclysm e YUNG's Better Nether Fortresses. Ela ajusta a fortress redesenhada por YUNG para participar corretamente da lógica/tag utilizada pelo conteúdo Cataclysm associado à progressão após **Ignis**, evitando que o overhaul estrutural faça essa integração perder o contexto de Nether Fortress.
O addon não gera um terceiro tipo de fortress e não adiciona um novo boss; adapta o structure set existente para que os dois mods-base reconheçam o mesmo local. O JAR instalado declara runtime `1.21.1`.

## YUNG's Better Ocean Monuments — 4.1.2

`YungsBetterOceanMonuments-1.21.1-NeoForge-4.1.2.jar`
**YUNG's Better Ocean Monuments** transforma o monumento em uma estrutura **maior e com layout interno randomizado**, tornando cada exploração menos previsível. Corredores, salas e rotas internas são reorganizados e a recompensa é ampliada em relação ao vanilla.
Entre os possíveis loots especiais estão **Tridents e Heart of the Sea**, além dos recursos normais do monumento. O redesign continua trabalhando com guardians/elder guardians e com a identidade submarina da estrutura, mas amplia substancialmente sua escala. A build instalada é `4.1.2` para NeoForge 1.21.1.

## YUNG's Better Witch Huts — 4.1.1

`YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`
**YUNG's Better Witch Huts** substitui a cabana única vanilla por **múltiplas variantes de hut** e adiciona também um **witch's circle** como ponto de interesse de swamp. As construções possuem design mais elaborado e loot mais significativo, mantendo witches e alquimia como tema central.
Brewing Stands podem conter itens/potions que funcionam como pistas para receitas de poção vanilla; a linha 1.21.1 também permite output adicional em determinados stands. A build `4.1.1` é a release NeoForge 1.21.1 instalada.

## Underground Village, Stoneholm — 2.0

`underground_village-neoforge-1.21.1-2.0.jar`
**Stoneholm** adiciona **vilas subterrâneas de grande porte**, organizadas como assentamentos habitáveis abaixo da superfície em vez de simples salas isoladas. As estruturas combinam corredores, casas, villagers e loot, criando pontos de exploração que também funcionam como settlements reais.
A presença de villagers faz essas estruturas participarem de mecânicas normais de comércio e população, enquanto a localização subterrânea muda completamente como são encontradas e acessadas. O JAR atual é `underground_village-neoforge-1.21.1-2.0.jar`; a ficha preserva `2.0` como versão do arquivo.

## Medieval Buildings: The Nether Edition — 1.0.2

`medieval_buildings_nether_edition-1.21.1-1.0.2-neoforge.jar`
**Medieval Buildings: The Nether Edition** distribui novas estruturas de estética medieval pelo Nether, transformando áreas abertas da dimensão em pontos de exploração construídos. As edificações funcionam como landmarks, locais de loot e encontros ambientais, sem criar uma dimensão ou sistema de progressão separado.
Seu papel é de structure/worldgen: BetterNether e Amplified Nether alteram ambiente e terreno, enquanto este mod acrescenta construções temáticas dentro desse espaço. A build `1.0.2` é a release NeoForge 1.21.1 instalada.

## Create: Dynamic Village — 0.9

`dynamicvillage-0.9-1.21.1.jar`
**Create: Dynamic Village** leva o ecossistema Create para settlements vanilla através de **estruturas, profissões, job sites, trades e loot** próprios. Villagers podem assumir profissões como **Mechanical Engineer** e **Hydraulic Engineer**, transformando componentes e materiais Create em parte da economia da vila.
As construções associadas fazem essas profissões aparecerem organicamente no worldgen, permitindo encontrar tecnologia Create antes mesmo de o jogador montar sua própria fábrica. O mod altera conteúdo e economia de villages; Integrated Villages atua principalmente sobre arquitetura/layout. A build instalada é `0.9` para NeoForge 1.21.1.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/09-minecolonies-e-civilizacao.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 9. MineColonies e civilização

## MineColonies — runtime 1.1.1374-1.21.1-snapshot

`minecolonies-1.1.1374-1.21.1-snapshot.jar`
**MineColonies** é um sistema completo de **construção e gerenciamento de civilização**. O jogador funda uma colônia, escolhe estilos de construção, posiciona edifícios e recebe cidadãos com necessidades, atributos e profissões que passam a executar tarefas reais no mundo.
Builders constroem e evoluem estruturas; miners, foresters, farmers, fishermen e outros produtores obtêm recursos; crafters transformam materiais; **Warehouse + Couriers** formam a espinha dorsal logística; guards e barracks defendem a população. A colônia possui também **Research**, hospitalidade/saúde, moradia, educação e cadeias de trabalho dependentes umas das outras.
A progressão exige equilibrar população, comida, materiais, rotas de entrega, capacidade produtiva e defesa. Raids e ameaças externas tornam segurança parte do sistema, enquanto edifícios superiores desbloqueiam trabalhadores e eficiência adicionais.
A build instalada é `1.1.1374-1.21.1-snapshot` e depende do stack Structurize, Multi-Piston, BlockUI e Domum Ornamentum. Os addons Compatibility 3.56 e Tweaks 3.33 publicam alvo MineColonies 1.1.1368; essa diferença permanece registrada como **acoplamento de versões**, sem alterar a identidade dos JARs atuais.

## Epic Fight: Epicfied (Epic Colonies) — 21.0.8

`EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar`
**Epic Colonies** é a bridge MineColonies ↔ Epic Fight. Ela adapta cidadãos, guards, raiders e outras entidades da colônia ao **modelo animado e à linguagem visual de combate do Epic Fight**, aplicando modelos, expressões e animações compatíveis ao invés de deixar os NPCs presos ao renderer vanilla durante o Battle Mode.
Isso faz os confrontos da colônia coexistirem visualmente com o overhaul de combate usado pelo jogador. O filename da build `21.0.8` registra explicitamente alvo `EFM-21.16.4`, enquanto o core atualmente instalado é Epic Fight `21.17.3.1`; o guia preserva essa diferença de alvo como informação de coupling.

## MineColonies Compatibility — 3.56

`MineColonies_Compatibility-1.21.1-3.56.jar`
**Compatibility addon for MineColonies** amplia o conjunto de itens e sistemas externos que os cidadãos conseguem usar de forma funcional. O addon adiciona adapters para **ferramentas, alimentos, profissões e redes/logística de outros mods**, permitindo que workers reconheçam recursos que o MineColonies base não conheceria sozinho.
A linha do projeto inclui integrações condicionais conforme os mods presentes, portanto não cria cópias desses sistemas: traduz itens e mecânicas externas para as necessidades de colonists. A build instalada `3.56` é beta NeoForge 1.21.1 publicada em 13/08/2026 e foi construída tendo MineColonies `1.1.1368` como alvo, enquanto o pack executa `1.1.1374-snapshot`; a divergência fica documentada como acoplamento de versão.

## MineColonies Tweaks — 3.33

`MineColonies_Tweaks-1.21.1-3.33.jar`
**Tweaks addon for MineColonies** expande configuração e regras internas da colônia. Entre os recursos documentados estão **probabilidade de colônias abandonadas no worldgen, delays de construção/destruição, configuração de crops, controle de mourning, imunidade/thorns de raiders e chance de resurrection**, além de comandos administrativos próprios.
O addon também amplia ferramentas/itens reconhecidos por cidadãos e ajusta diferentes comportamentos de workers. A build `3.33` é beta NeoForge 1.21.1 de 08/08/2026 e publica alvo MineColonies `1.1.1368`; o core instalado é `1.1.1374-snapshot`, por isso as duas versões permanecem explicitamente separadas.

## Let's Do addon for MineColonies — 2.1

`MineColonies_LetsDo-1.21.1-2.1.jar`
**Let's Do addon for MineColonies** conecta a colônia aos módulos rurais/culinários da família **[Let's Do]**. O objetivo é fazer recursos, alimentos e interações desses mods serem reconhecidos pelos sistemas de workers e compatibilidade do MineColonies, em vez de existirem como itens externos que os cidadãos ignoram.
A bridge depende do MineColonies e dos addons Compatibility/Tweaks correspondentes, além dos módulos Let's Do suportados. A release `2.1` é estável para NeoForge 1.21.1; como o ecossistema de addons foi publicado contra uma linha anterior do core, o guia mantém a diferença em relação ao snapshot `1.1.1374` instalado.

## Towntalk — 1.2.0

`towntalk-1.2.0.jar`
**TownTalk** adiciona **chatter contextual aos colonists do MineColonies**, fazendo cidadãos emitirem falas relacionadas à vida da cidade e às situações em que se encontram. A função é dar identidade social à população e tornar a colônia menos silenciosa, sem alterar a cadeia produtiva ou substituir o sistema de jobs.
Como addon de ambientação funcional ligado aos NPCs, ele depende do MineColonies para existir. A build instalada é `1.2.0` para a linha 1.21.1.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/10-utilidades-de-exploracao-e-gameplay.md -->

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


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/11-infraestrutura-tecnica-interface-visual-e-performance.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 11. Infraestrutura técnica, interface, visual e performance

> Este apêndice cataloga **JARs top-level reais da modlist** que não pertencem principalmente aos eixos de Magia ou Tecnologia e que também não constituem, por si só, um sistema jogável como os capítulos anteriores. Entram aqui modloader, APIs/libraries, interface, áudio, apresentação e otimizações. Eles são mantidos no guia para que a cobertura conjunta dos três catálogos corresponda à modlist atual, sem promover dependências internas jar-in-jar a mods independentes.

## NeoForge — runtime neoforge-21.1.248

`neoforge-21.1.248 (modloader)`
**NeoForge** é o modloader e a plataforma de execução sobre a qual esta instância 1.21.1 funciona. Ele fornece o ciclo de carregamento de mods e a infraestrutura comum usada pelos JARs NeoForge: descoberta de mods, registries, event buses, networking, configuração e hooks que permitem que código externo interaja com o Minecraft sem alterar cada sistema diretamente.
A entrada aparece explicitamente na modlist canônica como `neoforge-21.1.248 (modloader)`, com runtime `neoforge-21.1.248`. Ela representa a plataforma do pack, não um mod de conteúdo e não deve ser confundida com uma biblioteca jar-in-jar de algum addon.

## AdvancedCoreInfo — 1.0.0

`AdvancedCoreInfo-neoforge-1.21.1-1.0.0.jar`
**Advanced Core Info (ACI)** é a biblioteca compartilhada da família Advanced Info. Seu papel é centralizar mecanismos reutilizados por Advanced Loot Info e Advanced Worldgen Info, incluindo **descoberta de plugins, árvores estruturadas de tooltips, transferência de dados servidor→cliente e infraestrutura genérica de apresentação/análise**.
ACI não acrescenta uma tela ou sistema jogável autônomo quando instalado sozinho; ele fornece os contratos e serviços utilizados pelos módulos informativos que dependem dele. A modlist carrega a build `1.0.0` como JAR top-level.

## Advanced Loot Info — 2.0.1

`AdvancedLootInfo-neoforge-1.21.1-2.0.1.jar`
**Advanced Loot Info (ALI)** é um plugin de informação para recipe viewers que expõe **loot tables e trades de villagers** de forma consultável. Ele trabalha com JEI, EMI ou REI e organiza entradas, condições, funções e number providers usados pelas tabelas de loot, permitindo investigar de onde um item pode vir e sob quais regras.
A API de plugins permite que outros mods adicionem tipos próprios de loot entries, condições, funções, number providers e categorias. O projeto também possui suporte direto a LootJS. ALI informa e inspeciona o sistema de loot; ele não altera tabelas, não instancia baús por jogador e não substitui Lootr ou Loot Integrations. A build instalada é `2.0.1` e usa AdvancedCoreInfo como core compartilhado.

## Advancement Plaques — 1.6.8

`AdvancementPlaques-1.21.1-neoforge-1.6.8.jar`
**Advancement Plaques** substitui a apresentação padrão dos advancement toasts por placas/pop-ups estilizados, mantendo a conquista e seus critérios pertencentes ao sistema vanilla ou ao mod que registrou o advancement. O efeito é de **apresentação**, com layouts e animações próprios para tornar a notificação mais legível e destacada.
A build `1.6.8` é a release NeoForge 1.21.1 instalada. Essa linha também respeita overrides de som fornecidos por conteúdo compatível, como Aether, quando aplicável. O mod não cria uma progressão paralela nem altera os requisitos dos advancements.

## AI-Improvements — 0.5.3

`AI-Improvements-1.21-0.5.3.jar`
**AI-Improvements** modifica partes de baixo nível da IA vanilla com foco em **reduzir custo de processamento** e permitir desativar determinados comportamentos de IA quando desejado. Sua atuação é principalmente server-side/singleplayer e procura diminuir trabalho desnecessário feito por entidades, embora o próprio projeto ressalte que o ganho depende do cenário e deve ser medido no pack real.
Ele é funcionalmente diferente de **Enhanced AI**: AI-Improvements otimiza ou simplifica processamento; Enhanced AI acrescenta capacidades e comportamentos mais agressivos aos mobs. A build instalada é `0.5.3`.

## AmbientSounds — 6.3.8

`AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`

**AmbientSounds 6** é uma camada client-side de soundscape ambiental. O sistema detecta contexto de mundo — bioma, região e condições relevantes — e mistura **loops suaves e conjuntos variáveis de sons** para que cavernas, florestas, água e outros ambientes possuam paisagens sonoras próprias em vez de dependerem apenas dos eventos sonoros vanilla.
A geração de ambience é configurável e pode ser depurada para identificar quais sons estão ativos. O mod requer **CreativeCore** e não altera spawn, clima ou worldgen; ele reage ao estado existente para produzir áudio. A modlist carrega `6.3.8` para NeoForge 1.21.1.

## AppleSkin — runtime 3.0.9+mc1.21

`appleskin-neoforge-mc1.21-3.0.9.jar`
**AppleSkin** torna visíveis informações do sistema vanilla de fome que normalmente ficam ocultas. Tooltips mostram o valor nutricional de alimentos; o HUD pode indicar **saturação, exhaustion, fome/saturação que seriam restauradas pelo item segurado e potencial de recuperação de vida**, além de dados adicionais no overlay F3.
O mod é essencialmente informativo e não muda as regras de hunger/saturation. Para mostrar valores exatos de saturação/exhaustion, pode sincronizar esses dados do servidor para o cliente. O filename é `3.0.9`, enquanto o metadata runtime preservado pela modlist é `3.0.9+mc1.21`.

## Platform — 1.3.3

`Platform-neoforge-1.21.1-1.3.3.jar`
**Platform** é uma biblioteca cross-platform baseada em Architectury que abstrai APIs e contratos comuns entre Fabric, Forge, NeoForge e Quilt. Ela fornece infraestrutura técnica compartilhada para mods consumidores, incluindo acesso uniforme a funcionalidades de loader e registries, sem adicionar uma progressão ou conteúdo jogável próprio.
A build instalada é `1.3.3` para NeoForge 1.21.1. Como biblioteca específica, não é substituível automaticamente por Architectury, Balm ou outras APIs presentes no pack: consumidores compilados contra Platform continuam exigindo seu contrato.

## Architectury — 13.0.11

`architectury-13.0.11-neoforge.jar`
**Architectury API** é uma camada de abstração para mods multiplataforma. Ela oferece interfaces comuns sobre diferenças entre Fabric e Forge/NeoForge, incluindo **event hooks, networking, chamadas ao loader, registries e abstrações de plataforma**, reduzindo a quantidade de código específico de loader que cada projeto precisa manter.
No pack ela existe como dependência compartilhada de mods que foram construídos sobre essa API. Não adiciona mecânicas ao jogador por conta própria. A build top-level instalada é `13.0.11`.

## Athena — 4.0.6

`athena-neoforge-1.21.1-4.0.6.jar`
**Athena** é uma solução/API client-side para **connected block textures e modelos conectáveis**. Mods podem definir como faces e texturas de blocos se conectam aos vizinhos e deixar que Athena faça a composição/renderização dessas variações sem cada projeto implementar seu próprio pipeline de connected textures.
Seu efeito visível aparece através dos mods que utilizam a biblioteca; Athena em si funciona principalmente como backend de renderização e recursos. A build `4.0.6` é a release NeoForge 1.21.1 presente na modlist.

## Atlas API — runtime 1.21.1-1.2.0

`atlas_api-1.21.1-1.2.0.jar`
**Atlas API** fornece geração de **texture atlases em runtime**, incluindo texturas data-driven, e helpers para carregar modelos de itens que utilizam sprites gerados dinamicamente. Isso permite que um mod componha muitas combinações visuais sem precisar embarcar antecipadamente uma textura estática para cada resultado possível.
O projeto usa Iron's Gems 'n Jewelry como exemplo dessa abordagem: materiais e padrões podem ser combinados e renderizados por atlases criados durante a execução. A versão runtime preservada no pack é `1.21.1-1.2.0`.

## AzureLib — 3.1.11

`azurelib-neo-1.21.1-3.1.11.jar`
**AzureLib** é uma biblioteca de modelos e animações que permite a mods Java utilizar **modelos no formato Bedrock e animações customizadas** para entidades, itens e blocos. Ela fornece o runtime que carrega assets, controla estados de animação e conecta modelos animados ao rendering do jogo.
Consequentemente, criaturas ou equipamentos que dependem dela podem ter rigs e animações complexas sem cada mod desenvolver um engine de animação separado. A build instalada é `3.1.11` para NeoForge 1.21.1.

## Balm — 21.0.65

`balm-neoforge-1.21.1-21.0.65.jar`
**Balm** é uma abstraction layer para desenvolvimento multi-loader. Ela fornece **interfaces, eventos e serviços comuns** para que mods do mesmo ecossistema compartilhem código entre Fabric, Forge e NeoForge, escondendo muitas diferenças de implementação do loader.
Para o jogador, Balm funciona como dependência: recursos concretos vêm dos mods que a utilizam, como Waystones e outros projetos do autor. A modlist contém a release NeoForge 1.21.1 `21.0.65`.

## Battle Arts API — 21.17.7

`battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`
**Battle Arts API** é a parte de API separada do ecossistema Epic Fight Battle Arts. Ela define infraestrutura para **Battle Styles, Combat Arts e Proficiencies** e permite que outros mods registrem seus próprios estilos/arts sem depender do conteúdo específico fornecido pelo projeto original.
Isso torna a API um contrato de combate extensível sobre a linha Epic Fight compatível: ela não deve ser tratada como um segundo overhaul de combate independente. A build top-level instalada é `21.17.7` para Minecraft 1.21.1/NeoForge.

## Better lib — 1.0.111

`better_lib-neoforge-1.21.1-1.0.111.jar`
**Better Library** é uma biblioteca compartilhada usada pelos mods do seu autor para centralizar sistemas comuns. A documentação do projeto cita infraestrutura de **configuração, mensagens de primeiro ingresso/live messages e outros serviços reutilizáveis**, além de expor pontos que podem ser usados por projetos externos.
Sua presença evita que cada mod dependente replique a mesma implementação de configuração e comunicação. A build instalada é `1.0.111` para NeoForge 1.21.1.

## Better Fps - Render Distance — 6.1

`betterfpsdist-1.21.1-6.1.jar`
**Better Fps - Render Distance** é uma otimização client-side do volume renderizado. Em vez de tratar toda a distância de renderização apenas como o volume padrão do Minecraft, permite usar uma região **3D circular/ajustável**, com escalas horizontal e vertical configuráveis para deixar de renderizar chunk sections que dificilmente contribuem para a cena — especialmente cavernas muito abaixo do jogador.
O projeto estima redução relevante de chunk sections renderizadas dependendo das configurações. A distância de entidades também pode acompanhar o ajuste, e há modo de debug para visualizar quantas seções estão sendo descartadas. A build NeoForge 1.21.1 instalada é `6.1`; o mod atua somente no cliente.

## Biolith — 3.0.14

`biolith-neoforge-3.0.14.jar`
**Biolith** é uma API de worldgen voltada à **colocação, substituição e organização compatível de biomas**. Ela fornece um mecanismo comum para mods inserirem biomas e coordenarem alterações no terreno sem cada provider precisar implementar toda a lógica de biome placement e compatibilidade sozinho.
O JAR não é, por si só, um pacote de novos biomas; ele serve como infraestrutura para providers que o utilizam. A build instalada é `3.0.14` para NeoForge.

## BCLib: New Dawn — 21.0.26

`bclib-21.0.26.jar`
**BCLib: New Dawn** é a continuação mantida da biblioteca compartilhada do ecossistema **BetterX/New Dawn**. Ela fornece core utilities, registros e funcionalidades comuns utilizadas por projetos como BetterEnd e BetterNether, evitando que cada expansão replique a mesma infraestrutura de worldgen e conteúdo.
A library funciona em cliente e servidor e não adiciona, sozinha, uma terceira dimensão ou conjunto independente de biomas: o conteúdo visível pertence aos consumidores. A build `21.0.26` é a release NeoForge 1.21.1 instalada, publicada em 09/08/2026. Essa versão adiciona suporte reutilizável a **Chiseled Bookshelves para custom wood material sets**; a linha 21.0 também inclui trabalho de compatibilidade com renderizadores de terreno distante e block entities otimizadas.

## BjornLib — 1.0.88

`bjornlib-neoforge-1.0.88-1.21.1.jar`
**BjornLib** é uma biblioteca de código genérico criada para os mods de FuriusMaximus. Entre os serviços documentados estão **Ability Registry e eventos relacionados, Particle Builder com rendering luminoso, Lightning Builder e Leveling Builder para progressão/leveling de mobs**.
Isso significa que, embora seja uma library, ela expõe primitives que podem sustentar habilidades, efeitos e escalonamento de entidades em mods dependentes. A build top-level instalada é `1.0.88` para NeoForge 1.21.1.

## BlockUI — runtime 1.0.211-1.21.1-snapshot

`blockui-1.0.211-1.21.1-snapshot.jar`
**BlockUI** é o framework de interface do ecossistema MineColonies. Ele usa **XML para declarar a estrutura das telas** e classes `Window` como backend para callbacks e fornecimento de dados. A biblioteca oferece componentes como imagens, botões/handlers, entrada de texto, listas/telas com scroll e drag e outros elementos de UI.
MineColonies, Structurize, MultiPiston e outros projetos usam essa infraestrutura em vez de implementar widgets e layouts separadamente. O runtime instalado é `1.0.211-1.21.1-snapshot`.

## Bookshelf — 21.1.81

`bookshelf-neoforge-1.21.1-21.1.81.jar`
**Bookshelf** é uma biblioteca open source que reúne **frameworks, helpers e utilidades de código** para mods do ecossistema Darkhax e projetos externos. Entre os consumidores conhecidos está Enchantment Descriptions; a linha 1.21.1 também oferece utilidades para dados, networking/serialização e representação/descrição de sistemas como loot tables.
Como library, Bookshelf não constitui conteúdo jogável autônomo: seus efeitos aparecem por meio dos mods que chamam suas APIs. A build instalada é `21.1.81` para NeoForge 1.21.1.

## Caelus API — 7.0.1+1.21.1

`caelus-neoforge-7.0.1+1.21.1.jar`
**Caelus API** abstrai a lógica hardcoded de voo de Elytra em um **atributo de entidade de fall-flying**. Mods podem aplicar modificadores a esse atributo para habilitar ou desabilitar voo tipo Elytra em equipamentos ou condições próprias, sem substituir diretamente toda a lógica vanilla.
O atributo tem valor padrão zero; valores suficientes habilitam fall-flying, e a própria Elytra vanilla é adaptada para utilizar o mesmo contrato mantendo seu comportamento normal. A build instalada é `7.0.1+1.21.1`.

## 3D Skin Layers — 1.11.2

`skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`
**3D Skin Layers** é um mod client-side de renderização de jogador. Ele transforma a segunda camada normalmente plana da skin — chapéu, mangas, jaqueta e detalhes equivalentes — em uma **malha tridimensional com volume real**, preservando transparências usadas em óculos, visores e outros detalhes cosméticos.
Para controlar custo de renderização, o projeto pode retornar jogadores mais distantes para a apresentação 2D tradicional; a documentação atual usa uma distância próxima de 12 blocos como limiar padrão desse comportamento. A build instalada é `1.11.2` para NeoForge 1.21.1.

## Catalogue — 1.11.2

`catalogue-neoforge-1.21.1-1.11.2.jar`
**Catalogue** substitui a lista de mods do loader por uma interface unificada e pesquisável. Ela permite **buscar, filtrar e favoritar mods, abrir configurações, acessar homepage/issue tracker e visualizar dependências/dependentes**, além de esconder bibliotecas da listagem quando desejado.
O sistema utiliza logos/ícones declarados pelos mods e serve apenas como interface de administração/consulta da instalação. Não altera o carregamento ou a lógica dos mods exibidos. A modlist contém a release NeoForge 1.21.1 `1.11.2`.

## Chipped — 4.0.2

`chipped-neoforge-1.21.1-4.0.2.jar`
**Chipped** é uma grande expansão de blocos de construção voltada a **variantes decorativas de materiais existentes**. Por meio de estações/workbenches temáticas, blocos-base podem ser transformados em diversas versões com padrões, cortes e estilos diferentes, aumentando o vocabulário arquitetônico sem introduzir uma progressão tecnológica própria.
A biblioteca Athena sustenta parte da renderização/conectividade usada pelo ecossistema visual. O JAR instalado é `4.0.2` para NeoForge 1.21.1.

## Chunky — 1.4.23

`Chunky-NeoForge-1.4.23.jar`
**Chunky** é uma ferramenta de **pré-geração de chunks**. Administradores podem definir uma região/forma e gerar antecipadamente os chunks que seriam criados durante exploração normal, reduzindo picos futuros de chunkgen quando jogadores percorrem essas áreas.
As tarefas podem ser iniciadas, acompanhadas, pausadas e retomadas, com métricas como quantidade processada, percentual, taxa e ETA. O mod não substitui o worldgen de Tectonic/Terralith/BWG/YUNG etc.; ele executa antecipadamente o pipeline de geração definido por esses providers. A build instalada é `1.4.23`.

## Citadel — 2.7.1

`citadel-2.7.1-1.21.1.jar`
**Citadel** é uma biblioteca compartilhada para mods do ecossistema de Alex. Ela concentra código e infraestrutura reutilizável de entidades/renderização e serviços comuns, evitando que cada projeto dependente carregue implementações duplicadas.
Sua presença é técnica: conteúdo concreto vem dos mods que usam a API. A build `2.7.1` é a release NeoForge 1.21.1 instalada.

## Cloth Config v15 API — 15.0.140

`cloth-config-15.0.140-neoforge.jar`
**Cloth Config API** é um framework para criação de **telas de configuração**. Ele fornece widgets, categorias, entradas, validação e componentes usados por mods para expor opções editáveis ao jogador de forma consistente, em vez de cada projeto construir seu menu de configuração do zero.
Não define configurações de gameplay por conta própria; somente fornece a infraestrutura de UI consumida pelos dependentes. A build instalada é `15.0.140` para NeoForge/Minecraft 1.21.1.

## YetAnotherConfigLib (YACL) — 3.8.2+1.21.1-neoforge

`yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`
**YetAnotherConfigLib (YACL)** é uma biblioteca para criação de telas e sistemas de configuração. Ela fornece componentes de UI, categorias, opções e infraestrutura reutilizável para que mods consumidores exponham configurações sem implementar toda a interface do zero.
A build instalada é `3.8.2+1.21.1-neoforge`. YACL não adiciona gameplay próprio e não é substituível automaticamente por Cloth Config ou outras config libs, porque consumidores são compilados contra APIs específicas. A ficha do catálogo foi reconciliada nesta auditoria para `Estado no pack = Instalado` e fonte canônica 28/08.

## Clumps — 19.0.0.1

`Clumps-neoforge-1.21.1-19.0.0.1.jar`
**Clumps** reduz a quantidade de entidades de experiência ao **agrupar XP orbs próximos em orbs maiores**, diminuindo o custo de tick/renderização quando uma farm, máquina ou combate gera muita experiência em uma área pequena.
Também faz a experiência ser coletada imediatamente quando a entidade entra em contato com o jogador, evitando grandes quantidades de orbs persistindo ao redor dele. Nas versões modernas seu efeito principal é server-side. A build instalada é `19.0.0.1`.

## Cobweb — 1.4.0

`cobweb-neoforge-1.21-1.4.0.jar`
**Cobweb** é a library/API do ecossistema Crystal Nest. Ela centraliza contratos e utilidades compartilhados pelos projetos que dependem dessa base, permitindo reutilização de lógica comum entre loaders e mods.
O projeto é distribuído como **Crystal Nest API** e não adiciona um sistema jogável independente. O runtime top-level presente no pack é `1.4.0`.

## CodeChickenLib — 4.6.1.529

`CodeChickenLib-1.21.1-4.6.1.529.jar`
**CodeChickenLib** é uma biblioteca histórica do ecossistema Chicken-Bones/CodeChicken. Ela reúne infraestrutura para **matemática e transformações 3D, rendering, networking, configuração e outras rotinas de modding** utilizadas por projetos dependentes.
O JAR não representa conteúdo autônomo; seu papel é fornecer serviços comuns em runtime. A build instalada é `4.6.1.529` para NeoForge 1.21.1.

## CodxLib — 1.5.1

`codxlib-1.5.1-neoforge+1.21.1.jar`
**CodxLib** é a biblioteca de suporte compartilhada pelos mods do ecossistema Codx. O projeto centraliza **serviços comuns e contratos reutilizáveis** para que os mods dependentes mantenham comportamento consistente entre Fabric, Forge e NeoForge.
Ela não adiciona conteúdo jogável sozinha; a função observável vem dos consumidores da library. A modlist carrega `1.5.1` para NeoForge 1.21.1.

## Collective — 8.39

`collective-1.21.1-8.39.jar`
**Collective** é a biblioteca comum dos mods de Serilum. Ela concentra código, eventos, helpers e implementações reaproveitadas por muitos projetos do autor, reduzindo duplicação entre dezenas de pequenos mods.
Como dependência, não constitui uma mecânica independente. A build instalada é `8.39` para a linha 1.21.1.

## Connector Extras — runtime 1.12.1+1.21.1

`ConnectorExtras-1.12.1+1.21.1.jar`
**Connector Extras** amplia o Sinytra Connector com **bridges para APIs e plugins de terceiros**. Entre os módulos documentados está a conversão bidirecional entre Team Reborn Energy e Forge Energy, com razão configurável, além de bridges para que recipe viewers consigam descobrir plugins provenientes tanto do lado Fabric quanto do lado Forge/NeoForge.
Ele não é o componente que executa mods Fabric; trabalha sobre Connector para adaptar APIs adicionais que esses mods podem esperar encontrar. O runtime instalado é `1.12.1+1.21.1`.

## Sinytra Connector — publicação 2.0.0-beta.17+1.21.1

`connector-2.0.0-beta.17+1.21.1-full.jar`
**Sinytra Connector** é uma camada de compatibilidade que permite executar uma parcela de **mods Fabric em um ambiente NeoForge**, traduzindo expectativas de loader/API para o runtime NeoForge e trabalhando com a infraestrutura de compatibilidade necessária ao ecossistema Fabric.
A linha `2.0.0-beta.17` é a build 1.21.1 instalada. O sufixo `full` pertence ao filename; a inspeção da modlist não forneceu uma string de versão runtime separada, portanto o guia preserva a identidade pela publicação/arquivo em vez de inventar um metadata ausente.

## Continuity — runtime 3.0.0+1.21.neoforge

`continuity-3.0.0+1.21.neoforge.jar`
**Continuity** implementa **connected textures** e recursos de emissive/overlay texture de forma eficiente no cliente. Resource packs e mods podem definir regras para que texturas se conectem entre blocos vizinhos, evitando bordas repetitivas e permitindo superfícies contínuas.
A build instalada `3.0.0+1.21.neoforge` inclui suporte NeoForge 1.21.1 e trata também casos como camadas customizadas em blocos móveis. Ele modifica renderização de recursos; não altera propriedades físicas dos blocos.

## Controlling — 19.0.5

`Controlling-neoforge-1.21.1-19.0.5.jar`
**Controlling** reorganiza a tela de keybindings para instalações com muitos mods. Ele adiciona **busca por nome**, filtro de binds em conflito e visualização de teclas ainda disponíveis, tornando possível localizar e resolver colisões de controles sem percorrer manualmente uma lista extensa.
É um mod client-side de interface e não altera o input registrado pelos outros mods além da forma de administrá-lo. A build instalada é `19.0.5`.

## CorgiLib — 5.0.0.9

`Corgilib-NeoForge-1.21.1-5.0.0.9.jar`
**CorgiLib** é a biblioteca compartilhada dos mods de Corgi Taco. A documentação expõe infraestrutura como **serialização de configs ****`.json5`**** via Mojang codecs, codecs comentados, geração de árvores a partir de NBT, registries de funções de easing/blending, filtros de entidades e codecs para villager trades**.
Esses recursos são consumidos por mods de worldgen e conteúdo do ecossistema; a library em si não adiciona uma progressão própria. A build instalada é `5.0.0.9` para NeoForge 1.21.1.

## Cosmetic Armor Reworked — runtime 1.21.1-v1-neoforge

`cosmeticarmorreworked-1.21.1-v1-neoforge.jar`
**Cosmetic Armor Reworked** separa **aparência e função da armadura** em dois conjuntos: o equipamento funcional continua fornecendo proteção/atributos, enquanto slots cosméticos independentes determinam o que é renderizado no personagem.
Isso permite utilizar estatísticas de uma armadura e exibir outra sem alterar os valores defensivos. Em multiplayer o mod precisa participar do ambiente de cliente/servidor para manter os slots e sincronização correspondentes. A build instalada preserva o runtime `1.21.1-v1-neoforge`.

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

## Cyclops Core — 1.29.3

`cyclopscore-1.21.1-neoforge-1.29.3.jar`
**Cyclops Core** é a biblioteca comum do ecossistema CyclopsMC, usada por projetos como EvilCraft e Integrated Dynamics. Além de serviços compartilhados de configuração/networking/registro, a documentação pública expõe recursos como **declaração de custom recipes via XML**, permitindo que os mods dependentes reutilizem infraestrutura em vez de duplicá-la.
A library não cria uma linha de gameplay autônoma. A build instalada é `1.29.3` para NeoForge 1.21.1.

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

## Entity Model Features — 3.2.4

`entity_model_features-3.2.4-1.21-neoforge.jar`
**Entity Model Features (EMF)** implementa suporte ao formato **OptiFine Custom Entity Models (CEM)** fora do OptiFine. Resource packs podem substituir ou animar modelos de entidades usando arquivos e regras do ecossistema CEM, inclusive com variações condicionais.
EMF utiliza **Entity Texture Features (ETF)** para recursos como modelos randômicos, configuração e variação das texturas declaradas pelos modelos. É uma camada client-side de renderização/modelos e não altera a lógica das entidades. A build instalada é `3.2.4`.

## Entity Sound Features — 0.8.1

`entity_sound_features-0.8.1-1.21-neoforge.jar`
**Entity Sound Features (ESF)** aplica aos sons de entidades um sistema de variações baseado nas regras `.properties` usadas por ETF/OptiFine. Resource packs podem selecionar **variações sonoras condicionais por entidade/contexto**, em vez de limitar cada mob a um único conjunto estático definido apenas pelo jogo.
O mod também expõe utilidades sonoras adicionais para integração com ETF/EMF. É client-side e a build instalada é `0.8.1`.

## Entity Texture Features — 7.1

`entity_texture_features_1.21-neoforge-7.1.jar`
**Entity Texture Features (ETF)** implementa recursos de texturas de entidade associados ao ecossistema OptiFine: **texturas customizadas, emissivas e variantes condicionais/randômicas**, além de funcionalidades relacionadas a skins de jogador. Resource packs podem usar regras para selecionar aparências conforme propriedades da entidade.
ETF é também dependência funcional de EMF para parte de suas variações e configuração. A build instalada é `7.1` para NeoForge 1.21.1.

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

## Euphoria Patcher — runtime 1.9.3-r5.8.1-neoforge

`EuphoriaPatcher-1.9.3-r5.8.1-neoforge.jar`
**Euphoria Patches** é um addon para **Complementary Shaders Reimagined/Unbound** que acrescenta uma grande coleção de features e settings opcionais ao shader. O Patcher integra essas extensões ao ambiente do jogo para que o pacote Complementary compatível possa carregar e configurar os patches.
É uma camada estritamente visual/client-side; sem o shader compatível, não se transforma em um sistema de iluminação física do servidor. O runtime instalado preserva `1.9.3-r5.8.1-neoforge`.

## ExpandAbility — 12.0.0

`expandability-12.0.0.jar`
**ExpandAbility** é uma library que expõe eventos para controlar determinadas **habilidades vanilla do jogador**. A versão instalada permite, por exemplo, habilitar natação fora de fluidos, impedir natação mesmo dentro de fluidos e controlar a capacidade de caminhar sobre fluidos.
Isso fornece um contrato reutilizável para mods que querem modificar essas capacidades sem substituir toda a implementação do jogador. A build `12.0.0` suporta NeoForge 1.21/1.21.1.

## Explosive Enhancement: Reforged — 1.1.2

`explosiveenhancement-neoforge-1.21.1-1.1.2.jar`
**Explosive Enhancement: Reforged** substitui a apresentação visual das explosões por efeitos/partículas mais elaborados sem trocar o cálculo vanilla/modded de dano ou a física central da explosão. Assim, TNT, creepers e explosões produzidas por outros sistemas continuam determinando sua força pelas regras originais, enquanto o feedback visual é retrabalhado.
A build instalada é especificamente o fork **Reforged** `1.1.2`, não o projeto homônimo 1.4.x. A ficha do pack também registra um tempfix dessa release para um crash envolvendo o Creeper Head Projectile de Iron's Spells.

## FerriteCore — 7.0.3

`ferritecore-7.0.3-neoforge.jar`
**FerriteCore** é um conjunto de **otimizações de uso de memória** para Minecraft modded. Em vez de reduzir view distance ou remover conteúdo, ele altera estruturas internas e estratégias de armazenamento para evitar manter representações redundantes em memória; a linha `7.0.3` também reduz memória usada por **data component patches**.
O projeto funciona em cliente e servidor. A build `7.0.3-neoforge` é a release 1.21.1 instalada.

## First-person Model — 2.7.2

`firstperson-neoforge-2.7.2-mc1.21.1.jar`
**First-person Model** substitui a apresentação vanilla de primeira pessoa pelo **modelo de terceira pessoa do próprio jogador**, fazendo torso, pernas e equipamento permanecerem visíveis a partir dos olhos do personagem em vez de mostrar apenas braços separados.
O mod não altera animações nem regras de combate: é puramente visual e client-side, podendo conectar-se a qualquer servidor sem instalação obrigatória no servidor. A build instalada é `2.7.2` para NeoForge 1.21.1.

## Forgified Fabric API — runtime 0.116.15+2.3.5+1.21.1

`forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
**Forgified Fabric API (FFAPI)** porta para NeoForge os **hooks, events e APIs essenciais do Fabric API** exigidos por mods Fabric executados através do ecossistema Sinytra Connector. Isso inclui mecanismos de interoperabilidade para registries, partículas, biomas/dimensões, rendering e outras superfícies que muitos mods Fabric assumem como disponíveis.
Ela não é o loader Fabric nem executa mods Fabric sozinha: Connector cuida da camada de compatibilidade de loader e FFAPI fornece as APIs Fabric esperadas pelos consumidores. A build instalada é `0.116.15+2.3.5+1.21.1`.

## Fragmentum — 2.4.4

`fragmentum-neoforge-1.21.1-2.4.4.jar`
**Fragmentum** é o framework leve da **Obscuria Collection**. O projeto concentra ferramentas compartilhadas e uma arquitetura multi-loader para que os mods da coleção mantenham o código de conteúdo separado das diferenças entre plataformas.
O core não adiciona conteúdo quando instalado sozinho; existe porque consumidores dependem de seus serviços comuns. A build NeoForge 1.21.1 instalada é `2.4.4`.

## FTB Library — 2101.1.35

`ftb-library-neoforge-2101.1.35.jar`
**FTB Library** centraliza infraestrutura compartilhada pelos mods FTB, com destaque para **GUI/widgets, utilidades comuns e APIs usadas por FTB Teams, FTB Chunks e outros projetos do ecossistema**. Isso permite que os módulos funcionais compartilhem a mesma linguagem de interface e serviços sem copiar código.
Ela não cria claims ou vein mining sozinha; essas funções pertencem a FTB Chunks e FTB Ultimine. A release top-level instalada é `2101.1.35` para NeoForge 1.21.1.

## FTB Teams — 2101.1.11

`ftb-teams-neoforge-2101.1.11.jar`
**FTB Teams** fornece identidade e persistência de **equipes** para sistemas que compartilham progressão, permissões ou território. Jogadores podem criar teams e configurar propriedades; mods consumidores podem então associar claims, quests, acesso e outros estados à equipe em vez de tratá-los apenas por jogador individual.
No stack atual, FTB Chunks utiliza essa camada para interações e proteção territorial. Os dados das equipes são persistidos no mundo. A build instalada é `2101.1.11` para NeoForge 1.21.1.

## Fusion — runtime 1.3.14+a

`fusion-1.3.14a-neoforge-mc1.21.1.jar`
**Fusion** amplia o formato de resource packs com **connected textures, continuous textures, scrolling textures, overlays e custom entity models**, além de permitir que mods registrem tipos adicionais de textura/modelo. Superfícies podem, por exemplo, conectar bordas entre blocos ou formar uma textura contínua atravessando vários blocos.
Apesar de possuir API para mods, o efeito principal é client-side/renderização de recursos. A build instalada preserva o runtime `1.3.14+a`.

## Fzzy Config — runtime 0.7.6+1.21+neoforge

`fzzy_config-0.7.6+1.21+neoforge.jar`
**Fzzy Config** é um engine de configuração multiplataforma com **serialização automática, geração de GUI, validação/correção de valores, sincronização servidor↔cliente e atualização versionada de configs**. Mod authors podem definir estruturas de configuração e deixar que a biblioteca construa boa parte da interface e do fluxo de sync automaticamente.
As telas são navegáveis por teclado e possuem suporte a narration; a API também integra ambientes como Catalogue. O runtime instalado é `0.7.6+1.21+neoforge`.

## Gabou's Libs — 1.8.7

`gaboulibs-neoforge-1.8.7.jar`
**Gabou's Libs** é a library compartilhada pelos mods de Gaboouu. Ela reúne **sistemas, utilidades e infraestrutura comum**, inclusive serviços usados por projetos ligados a clima, estações e simulação ambiental, para manter comportamento e manipulação de dados consistentes entre consumidores e loaders.
Não adiciona uma mecânica própria quando instalada isoladamente. A build top-level no pack é `1.8.7` para NeoForge; a linha atual reforça networking/autenticação, corrige registros duplicados de payload e amplia tolerância a respostas atrasadas do desafio de verificação.

## GeckoLib 4 — 4.9.2

`geckolib-neoforge-1.21.1-4.9.2.jar`
**GeckoLib** é uma biblioteca de **modelos e animações 3D** usada por entidades, blocos, itens, armaduras e outros objetos. Ela fornece runtime de animação, controllers, keyframes e integração de modelos para que mods criem movimentos complexos e state-driven sem construir um engine próprio.
Por isso muitos mods de criaturas e bosses dependem dela, mas GeckoLib não adiciona essas criaturas por conta própria. A versão instalada é `4.9.2`, release NeoForge 1.21.1.

## Global Packs — 21.0.6

`globalpacks-neoforge-1.21.1-21.0.6.jar`
**Global Packs** carrega **datapacks e resource packs globais** automaticamente em todos os mundos da instância. Em vez de copiar manualmente um datapack para cada save novo, o pack pode ser colocado nas pastas globais geradas pelo mod e passa a participar do carregamento de cada mundo compatível.
Isso é infraestrutura de distribuição/configuração do modpack; o conteúdo real continua pertencendo aos datapacks/resource packs carregados. A build instalada é `21.0.6` para NeoForge 1.21.1.

## Glodium — runtime 1.21-2.2-neoforge

`Glodium-1.21-2.2-neoforge.jar`
**Glodium** é uma code library do ecossistema GlodBlock voltada a **renderização, networking e registries**. Ela fornece implementações comuns para consumidores que precisam registrar conteúdo, trocar dados e renderizar componentes sem repetir a mesma infraestrutura.
A própria página do projeto ressalta que a library não faz nada útil quando instalada sozinha. O runtime preservado no pack é `1.21-2.2-neoforge`.

## GroovyModLoader — 6.0.2

`gml-6.0.2.jar`
**GroovyModLoader (GML)** é um **language provider** que permite carregar mods escritos em Groovy no ambiente NeoForge. Ele inclui módulos da linguagem Groovy e fornece o `GMLLangProvider`, que reconhece declarações `@GMod` e integra esses projetos ao ciclo de carregamento do modloader.
Não é um mod de scripting de gameplay para o jogador: sua função é permitir que outros mods sejam implementados/carregados em Groovy. A build instalada é `6.0.2`, compatível com 1.21/1.21.1.

## Patchouli — runtime 1.21.1-93-NEOFORGE

`Patchouli-1.21.1-93-NEOFORGE.jar`
**Patchouli** é um framework data-driven para livros e guias in-game. Mods consumidores podem definir capítulos, páginas, categorias e progressão documental sem implementar uma interface de livro própria, tornando a biblioteca parte da infraestrutura de documentação do pack.
A build instalada é a release estável `93` para NeoForge 1.21.1. Alguns livros de consumidores antigos ainda podem usar formatos removidos e serem ignorados, mas isso é um problema do conteúdo desses consumidores, não da identidade ou validade do Patchouli. Sua coexistência com GuideME ou Modonomicon não caracteriza redundância automática, porque cada mod consumidor depende da API para a qual foi construído.

## GuideME — 21.1.17

`guideme-21.1.17.jar`
**GuideME** é um toolkit para criação de **guidebooks in-game**. O conteúdo dos guias pode ser escrito em Markdown e enriquecido com elementos interativos, incluindo **cenas 3D ao vivo**, permitindo que mods e modpacks mantenham documentação integrada à própria interface do jogo.
Ele é particularmente usado no ecossistema AE2, mas a infraestrutura pode ser consumida por outros projetos. A build instalada é `21.1.17` para NeoForge 1.21.1.

## Iceberg — 1.3.2

`Iceberg-1.21.1-neoforge-1.3.2.jar`
**Iceberg** é uma modding library que adiciona **events, helpers e utilities** reutilizáveis para projetos NeoForge/Forge. O objetivo é expor hooks e operações comuns que seriam repetidos em cada mod dependente.
Ela não adiciona conteúdo autônomo ao jogador; recursos visíveis pertencem aos mods consumidores. A build instalada é `1.3.2` para NeoForge 1.21.1.

## Iglee's Library — runtime 1.21.1-1.2.7

`igleelib-1.21.1-1.2.7.jar`
**Iglee's Library** é a dependência compartilhada pelos mods de iglee42, centralizando código reutilizado entre seus projetos. A documentação pública a apresenta explicitamente como a library de toda essa família de mods.
Seu papel é de infraestrutura, sem gameplay próprio quando isolada. O runtime instalado preserva `1.21.1-1.2.7`.

## ImmediatelyFast — runtime 1.6.13+1.21.1

`ImmediatelyFast-NeoForge-1.6.13+1.21.1.jar`
**ImmediatelyFast** otimiza o **immediate mode rendering** do cliente através de buffers e batching mais eficientes. A otimização alcança entidades, block entities, partículas, texto, GUI/HUD e rendering imediato feito por outros mods, reduzindo draw calls/uploads redundantes e principalmente custo de CPU em cenas pesadas.
A versão instalada é `1.6.13+1.21.1`; o projeto é client-side e não altera regras de gameplay. Esta release restaura corretamente o depth-test após flush do DrawContext, corrige exceção ao fechar buffers sem uso e elimina vazamento de file handle da configuração.

## InsaneLib — 2.4.29.0

`insanelib-2.4.29.0.jar`
**InsaneLib** é a biblioteca comum dos mods de Insane96, reunindo **utilitários, configuração e infraestrutura compartilhada** para os consumidores do ecossistema. Ela evita que cada projeto replique serviços técnicos iguais.
Não possui alteração relevante de gameplay instalada sozinha e não deve ser tratada como uma alternativa genérica intercambiável a outras APIs. O runtime atual é `2.4.29.0`.

## Integrated API — 1.8.0

`integrated_api-neoforge-1.21.1-1.8.0.jar`
**Integrated API** é a library da família **Integrated Structures**. Ela coloca em um único mod **structure types, abstrações de dados e utilities compartilhadas** que são reutilizadas por Integrated Cataclysm, Integrated Stronghold, Integrated Villages, IDAS e outros projetos relacionados.
A função é estrutural: as dungeons/structures concretas pertencem aos addons consumidores. A build instalada é `1.8.0` para NeoForge 1.21.1.

## Jade — runtime 15.10.6+neoforge

`Jade-1.21.1-NeoForge-15.10.6.jar`
**Jade** é um HUD contextual do tipo Waila/Hwyla: ao olhar para um bloco ou entidade, exibe **nome, mod de origem e dados úteis** fornecidos pelo próprio jogo ou por plugins de integração. Dependendo do alvo, pode mostrar conteúdo de armazenamento, combustível, fluidos, estado de colmeias e outras propriedades sincronizadas pelo servidor.
Também se integra a recipe viewers para abrir receitas/usos do alvo. O runtime instalado é `15.10.6+neoforge`; a build correspondente é a linha 15.10.6 para Minecraft 1.21.1.

## JinxedLib — 1.0.4

`jinxedlib-neoforge-1.21.1-1.0.4.jar`
**JinxedLib** é uma library para desenvolvimento multi-loader e modpacks. Entre as ferramentas públicas estão **helpers de registry, compostabilidade data-driven, furnace fuels data-driven e classes comuns de blocos** que contornam diferenças/visibilidade entre plataformas.
Isso permite que mods consumidores movam mais comportamento para dados e compartilhem código entre loaders. A build NeoForge 1.21.1 instalada é `1.0.4`.

## Jupiter — 2.3.7

`jupiter-2.3.7-1.21.1-neoforge.jar`
**Jupiter** é uma **biblioteca de configuração com sincronização automática**, usada por mods que precisam manter opções e estado configurável coerentes entre lados do jogo. Ela fornece a infraestrutura de config/sync e não adiciona uma mecânica de gameplay própria.
Não deve ser confundida com libraries de UI de configuração genéricas como Cloth/Fzzy: o contrato e os consumidores são específicos. A build instalada é `2.3.7` para NeoForge 1.21.1.

## Melody — 1.0.10

`melody_neoforge_1.0.10_MC_1.21.jar`
**Melody** é uma biblioteca client-side de áudio baseada em **OpenAL**, usada por outros mods para reprodução e gerenciamento de música e sons de fundo. Ela não adiciona conteúdo jogável próprio: fornece serviços de áudio reutilizáveis para consumidores que precisam controlar streams, playback e recursos sonoros fora das rotinas vanilla mais simples.
No pack, **FancyMenu** é consumidor confirmado e declara Melody como dependência obrigatória. A build top-level instalada é `1.0.10`, publicada para a linha Minecraft 1.21 e compatível com 1.21.1; versões posteriores para outras linhas do jogo não substituem esta identidade instalada.

## MRU — 1.0.33+1.21.1

`mru-1.0.33+1.21.1-neoforge.jar`
**MRU** é uma biblioteca compartilhada usada pelos mods de Cassian e IMB11. A linha moderna concentra **registration hooks**, APIs para consultar inventário do jogador e abstrações para conteúdos como **bundles, backpacks e slots/acessórios**, além de helpers multi-version usados para manter os mesmos projetos em várias linhas do Minecraft.
Ela funciona em cliente e servidor e não cria gameplay próprio quando carregada isoladamente. A build instalada é `1.0.33+1.21.1` para NeoForge 1.21.1. O catálogo ainda não comprova qual consumidor atual exige MRU, então o guia descreve o contrato da library sem atribuir dependências específicas não verificadas.

## YUNG's API — runtime 1.21.1-NeoForge-5.1.8

`YungsApi-1.21.1-NeoForge-5.1.8.jar`
**YUNG's API** é a biblioteca compartilhada da família de mods de estruturas e worldgen de YUNG. Ela centraliza contratos, helpers e infraestrutura reutilizada por projetos como Better Mineshafts, Better Dungeons, Better Caves e outros módulos YUNG, evitando que cada addon replique o mesmo backend.
A API não gera estruturas ou cavernas sozinha; o conteúdo visível pertence aos consumidores. A build instalada é `5.1.8` para NeoForge 1.21/1.21.1. Essa versão reverte mudanças problemáticas da 5.1.7 e inclui correções de interoperabilidade com bibliotecas de estruturas; vários consumidores YUNG estão presentes no pack.

## Lionfish API — 3.1

`lionfishapi-3.1.jar`
**Lionfish API** é uma biblioteca leve de animação e utilidades usada pelo ecossistema de **L_Ender's Cataclysm** e outros consumidores. Ela fornece contratos e helpers específicos que esses mods utilizam para comportamento e apresentação, sem acrescentar mobs, bosses ou conteúdo jogável próprio quando instalada isoladamente.
No pack, L_Ender's Cataclysm é um consumidor direto relevante. A build top-level instalada é `3.1`; ela não é intercambiável automaticamente com GeckoLib, AzureLib ou outras libraries de animação, porque cada consumidor depende da API para a qual foi implementado.

## Resourceful Config — 3.0.11

`resourcefulconfig-neoforge-1.21-3.0.11.jar`
**Resourceful Config** é a biblioteca de configuração da Team Resourceful. Ela fornece contratos para mods consumidores **definirem, carregarem, sincronizarem e exporem configurações** sem precisar implementar separadamente toda a infraestrutura de serialização e comunicação entre cliente/servidor.
Ela não adiciona opções de gameplay próprias; as configurações concretas pertencem aos mods que usam a API. A build instalada é `3.0.11` para NeoForge 1.21/1.21.1; essa linha inclui correções de ciclo de vida de configs em dedicated server.

## Uranus — 3.0-beta.1

`uranus-3.0-beta.1.jar`
**Uranus** é uma biblioteca/runtime de animação e utilidades para entidades, derivada do ecossistema técnico de LLibrary/Citadel e usada por consumidores modernos que precisam de helpers específicos de entidades, animação e comportamento.
A build instalada é `3.0-beta.1`, um port NeoForge legítimo para Minecraft 1.21.1 publicado como beta. A linha atual declara requisito de Ice & Fire CE moderno; o pack utiliza Ice And Fire Community Edition 2.1.1. Uranus não adiciona criaturas ou progressão própria quando carregado isoladamente.

## Stylish Effects — 21.1.3

`StylishEffects-v21.1.3-1.21.1-NeoForge.jar`
**Stylish Effects** redesenha a apresentação dos **status effects** no HUD e no inventário. Em vez da lista vanilla ocupar a interface da forma padrão, o mod usa widgets mais compactos e configuráveis, mostrando ícone, duração, amplifier e outras informações de maneira reorganizada.
Posição, layout e comportamento visual podem ser ajustados pelo cliente. O mod não altera duração, potência ou regras dos efeitos; modifica apenas como esses dados são apresentados. A build instalada é `21.1.3` para NeoForge 1.21.1 e utiliza Puzzles Lib.

## Sounds — 2.4.22+lts

`sounds-2.4.22+lts+1.21.1-neoforge.jar`
**Sounds** amplia a resposta sonora do cliente com novos efeitos para **interações, blocos e elementos de interface**, além de permitir que parte desse comportamento seja personalizada por resources/data. A intenção é fazer ações cotidianas possuírem feedback acústico mais específico do que o conjunto vanilla.
Ele fornece sons; não é um sistema de propagação acústica. Por isso é funcionalmente distinto de mods Sound Physics, que trabalham com oclusão, distância e propagação do áudio já existente. A build instalada é `2.4.22+lts` para NeoForge 1.21.1.

## Sodium — 0.8.13-beta.2+mc1.21.1

`sodium-neoforge-0.8.13-beta.2+mc1.21.1.jar`
**Sodium** substitui e otimiza partes centrais do **pipeline de renderização** do Minecraft com foco em FPS, eficiência de CPU/GPU, construção de chunks e redução de trabalho gráfico desnecessário. Ele funciona como base de renderização sobre a qual outras extensões gráficas e bridges do pack precisam permanecer compatíveis.
A instalação atual é a build oficial NeoForge `0.8.13-beta.2+mc1.21.1`. O sufixo beta representa maturidade da linha, não dúvida sobre a identidade do JAR. No stack atual ele participa do núcleo gráfico junto a Flywheel, Sable, shaders e dynamic lights.

## Reese's Sodium Options — 2.2.3+mc1.21.1

`reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar`
**Reese's Sodium Options** substitui/reorganiza a tela de opções gráficas do Sodium para tornar um grande conjunto de configurações mais navegável. As opções são distribuídas em categorias e uma interface mais compacta, facilitando localizar e comparar ajustes sem alterar a lógica do renderer.
O addon é client-side e **não aumenta FPS por si próprio**: a otimização continua pertencendo ao Sodium. A build instalada é `2.2.3+mc1.21.1` para NeoForge 1.21.1.

## Vanity — arquivo 5.0.3; metadata 5.0.2

`vanity-neoforge-1.21.1-5.0.3.jar`
**Vanity** é um sistema data-driven de **customização cosmética de equipamentos e itens**. Vanity Packs definem Designs que podem ser aplicados por meio da Styling Table, permitindo trocar a aparência apresentada por um item sem substituir sua identidade funcional, atributos ou lógica original.
O sistema funciona também como infraestrutura para addons cosméticos; `Malum: Vestis`, por exemplo, depende de Vanity para disponibilizar seus designs de foices. O arquivo instalado é `5.0.3`, enquanto o metadata interno detectado informa `5.0.2`; essa divergência de empacotamento é preservada no catálogo em vez de ser normalizada.

## Modern UI — 3.13.0.1

`ModernUI-NeoForge-1.21.1-3.13.0.1-universal.jar`
**Modern UI** é um framework client-side de interface e renderização de texto. Ele acrescenta **font rendering avançado, animações, emoji, blur e componentes de UI**, além de infraestrutura reutilizável por telas e interfaces que precisam de apresentação mais sofisticada que os widgets vanilla.
Seu papel é simultaneamente visual e de framework; não cria progressão ou conteúdo de mundo. A build `3.13.0.1` inclui correções de compatibilidade relevantes para rendering de tooltips/texto, inclusive casos envolvendo ImmediatelyFast e Create, e é a versão instalada para NeoForge 1.21.1.

## Photon — 2.2.4

`photon-neoforge-1.21.1-2.2.4-all.jar`
**Photon** é um framework e **editor avançado de VFX** para mods consumidores. Ele oferece sistemas de partículas, trails, shaders e ferramentas de composição visual para criar efeitos complexos sem que cada projeto implemente do zero seu próprio editor e runtime de efeitos.
A linha `2.2.4` inclui correções para recompilação repetitiva de shaders e outros problemas do pipeline de VFX. A build instalada é beta oficial para NeoForge 1.21.1; Photon atua como infraestrutura visual e não como um particle pack decorativo autônomo.

## Lodestone — 1.8.2

`lodestone-1.21.1-1.8.2.jar`
**Lodestone** é o framework compartilhado da Lodestar Team e concentra principalmente infraestrutura de **renderização e efeitos** para mods consumidores. O pacote inclui shaders, utilities, screenshake, atributos comuns, fogo customizado aplicado a entidades e um sistema de partículas com grande quantidade de parâmetros por partícula.
A biblioteca também fornece partículas de GUI, helpers simples para multiblocks e inventários de block entities, um sistema de **world events persistentes por dimensão** e carregamento/modificação de texturas em runtime. Esses recursos permitem que mods dependentes implementem efeitos e sistemas visuais complexos sem manter backends separados. A build `1.8.2` é a release NeoForge 1.21.1 instalada e inclui melhorias de screen-particle rendering e mudanças internas de backend.

## Create: Big Contraptions — 1.0

`bigcontraptions-neoforge-1.0.jar`
Apesar do nome, **Create: Big Contraptions não é um addon funcional do Create**. É um patch client-side para o limite de playerdata associado ao bug MC-185901, elevando a quantidade de dados de jogador que o cliente consegue receber/manusear para permitir casos com estruturas ou estados muito volumosos, inclusive contraptions com grande NBT. Não adiciona máquinas, cinética ou contraptions próprias.

## ModernFix — 5.27.20+mc1.21.1

`modernfix-neoforge-5.27.20+mc1.21.1.jar`
**ModernFix** reúne otimizações e correções para instalações modded, atuando em memória, carregamento, caches e outros caminhos internos do jogo. A função é reduzir custo e corrigir problemas estruturais sem adicionar conteúdo jogável; ele pode coexistir com otimizações especializadas como FerriteCore porque os projetos atuam em mecanismos diferentes.

## YDM's Weapon Master — 4.2.7

`weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`
**YDM's Weapon Master** exibe itens e armas do hotbar diretamente no corpo do jogador. Posições podem ser configuradas e o sistema possui tratamento para categorias como shields e banners, funcionando como uma camada de representação de equipamento visível e não como alteração dos atributos ou movesets dessas armas.

## Sky Aesthetics — 2.0.13-beta

`sky_aesthetics-neoforge-2.0.13-beta.jar`
**Sky Aesthetics** amplia a apresentação visual do céu e da atmosfera com elementos e variações adicionais, incluindo recursos ligados ao skybox, estrelas e constelações. É um componente de renderização/ambientação; a build instalada é beta e pode se compor visualmente com shaders e outras camadas de céu, mas não altera clima ou regras ambientais do servidor.

## Tooltip Overhaul — 1.5.1

`tooltipoverhaul-neoforge-1.21.1-1.5.1.jar`
**Tooltip Overhaul** reformula a leitura de tooltips extensos com wrapping, rolagem, comparação de equipamentos e ajustes de apresentação. A linha instalada também trabalha com integrações como JEI/EMI, ModernFix e conteúdo complexo de equipamentos, mantendo a função estritamente informativa/visual e sem modificar os atributos exibidos.

## Model Gap Fix — runtime 1.21-1.10

`modelfix-1.21-1.10.jar`
**Model Gap Fix** corrige pequenas frestas e linhas visuais que podem surgir em modelos de itens e blocos devido à forma como a geometria/textura é processada pelo renderer. É um patch client-side de apresentação e não altera hitbox, modelo lógico ou comportamento dos objetos.

## Just Enough Professions (JEP) — 4.0.5

`JustEnoughProfessions-neoforge-1.21.1-4.0.5.jar`
**JEP** é um addon informativo do JEI que mostra qual **workstation/job-site block** corresponde a cada profissão de villager. Ele consulta profissões registradas, inclusive modded quando suportadas, mas não cria profissões, não muda trades e não altera a IA dos villagers.

## Fancy World Animations — 1.2.31

`fwa+1.21.1-neoforge-1.2.31.jar`
**Fancy World Animations** adiciona transições e movimentos suaves a elementos do mundo como portas, trapdoors, botões, alavancas, lanternas, jukeboxes e outras interações. Atua no pipeline visual do cliente; a linha 1.2.31 inclui compatibilidade com o occlusion culling do EntityCulling.

## NotEnoughAnimations — 1.12.4

`notenoughanimations-neoforge-1.12.4-mc1.21.1.jar`
**NotEnoughAnimations** acrescenta e melhora animações de terceira pessoa para ações que o vanilla representa de forma limitada ou apenas em primeira pessoa. É conteúdo visual pronto para o jogador e não uma API: Player Animator e Player Animation Library fornecem infraestrutura, enquanto NotEnoughAnimations fornece animações concretas.

## Player Animator — 2.0.4+1.21.1

`player-animation-lib-forge-2.0.4+1.21.1.jar`
**Player Animator** é a biblioteca/API do ecossistema playeranimator/KosmX usada por outros mods para aplicar animações customizadas ao modelo do jogador. Ela permite controlar poses e partes do corpo e fornece a infraestrutura de composição/reprodução utilizada por consumidores que dependem especificamente dessa API.
O JAR top-level é uma entrada canônica da modlist atual. Alguns mods podem embarcar uma cópia interna via JarJar, mas isso não transforma o arquivo top-level em duplicata nem prova que todos os seus consumidores estejam atendidos pela cópia interna. A build instalada é `2.0.4+1.21.1`.

## Player Animation Library — 1.1.6+mc.1.21.1

`PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`
**Player Animation Library** é uma segunda biblioteca de animação do jogador, com implementação e API próprias. Ela fornece mecanismos para mods aplicarem e comporem animações sobre o player mantendo interoperabilidade entre movimentos de diferentes consumidores.
Apesar do objetivo geral semelhante ao Player Animator, as duas APIs não são intercambiáveis automaticamente: dependentes compilados para uma delas não passam a usar a outra sem integração específica. A versão instalada é `1.1.6+mc.1.21.1` para NeoForge 1.21.1.

## Just Enough Items (JEI) — 19.44.0.406

`jei-1.21.1-neoforge-19.44.0.406.jar`
**Just Enough Items (JEI)** é o índice central de itens, ingredientes, receitas e usos do pack. A lista lateral permite pesquisar conteúdo registrado por nome/mod e inspecionar **como um item é produzido** e **em quais processos ele é utilizado**, enquanto a interface de categorias representa crafting, cooking, máquinas e outros tipos de recipe expostos pelo jogo ou por plugins.
O sistema também oferece bookmarks/favoritos e mecanismos de navegação entre ingredientes e receitas, tornando-se a camada de consulta comum para um pack com muitos sistemas de crafting. Sua API permite que outros mods registrem categorias próprias, catalysts/máquinas, transfer handlers, ingredient types e informações adicionais sem precisar criar um recipe viewer independente.
Addons como **Just Enough Professions**, **Advanced Loot Info**, JEED e integrações específicas ampliam os dados apresentados, mas não substituem o core JEI. A build instalada é `19.44.0.406` para NeoForge 1.21.1.

## Mouse Tweaks — 2.26.1

`MouseTweaks-neoforge-mc1.21-2.26.1.jar`
**Mouse Tweaks** melhora gestos de inventário, principalmente drag e scroll para distribuir, mover ou transferir stacks com menos cliques. O efeito é restrito à interação das GUIs: ele não cria inventários, armazenamento ou logística próprios.

## Searchables — 1.0.2

`Searchables-neoforge-1.21.1-1.0.2.jar`
**Searchables** é uma biblioteca para campos de busca, filtros e autocomplete. No pack há consumidor causal confirmado: **Controlling 19.0.5** usa essa infraestrutura para sua interface de pesquisa de keybinds; Searchables não funciona como um recipe viewer ou mecanismo de busca de itens independente.

## SuperMartijn642's Core Lib — 1.1.24

`supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`
**SuperMartijn642's Core Lib** reúne utilities e infraestrutura reutilizadas pelos mods do ecossistema SuperMartijn642. É uma dependência técnica geral, sem uma linha de gameplay própria; sua necessidade decorre dos consumidores implementados contra essa API.

## SuperMartijn642's Config Library — 1.1.8

`supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`
**SuperMartijn642's Config Library** fornece especificamente a camada de configuração usada por mods do mesmo ecossistema. Ela complementa a Core Lib em outro papel: core/utilities gerais ficam na primeira, enquanto definição e manejo de configurações ficam nesta biblioteca.

## TenshiLib — runtime 1.21.1-2.3.0.b-neoforge

`tenshilib-1.21.1-2.3.0.b-neoforge.jar`
**TenshiLib** é a biblioteca/core compartilhada dos projetos de flemmli97. O próprio projeto a define como o core e library usado pelos outros mods do autor: ela centraliza código comum, utilidades e infraestrutura reutilizável para que os consumidores não precisem embarcar implementações duplicadas.
Ela funciona em **cliente e servidor** e não representa, isoladamente, um sistema de gameplay. O arquivo instalado `tenshilib-1.21.1-2.3.0.b-neoforge.jar` é a release NeoForge 1.21.1 publicada em 22/08/2026; o changelog de `2.3.0.b` registra especificamente uma correção do arquivo TOML do NeoForge. O runtime preservado pela modlist é `1.21.1-2.3.0.b-neoforge`.

## PrickleMC — 21.1.11

`prickle-neoforge-1.21.1-21.1.11.jar`
**PrickleMC** é uma biblioteca de configuração e utilidades consumida por outros mods. Seu contrato é específico e não deve ser tratado como substituto genérico de Cloth Config, Fzzy Config ou outras config libraries apenas porque todas atuam no domínio de configuração.

## Subtle Effects — 1.14.3

`SubtleEffects-neoforge-1.21.1-1.14.3.jar`
**Subtle Effects** acrescenta pequenas partículas e alguns sons ambientais para tornar ações e ambientes mais expressivos sem um grande overhaul visual. Distância, culling e diversos efeitos são configuráveis; alguns recursos são estritamente client-side e o mod utiliza Fzzy Config.

## Resourceful Lib — 3.0.12

`resourcefullib-neoforge-1.21-3.0.12.jar`
**Resourceful Lib** é a library geral da Team Resourceful, reunindo utilidades de registries, networking, dados e recursos para mods consumidores. Ela é distinta de Resourceful Config: a primeira fornece infraestrutura ampla; a segunda é especializada no sistema de configurações.

## Knight Lib — 1.6.1

`knightlib-neoforge-1.21.1-1.6.1.jar`
**Knight Lib** é uma biblioteca de suporte usada por mods consumidores que foram implementados contra sua API. Ela centraliza infraestrutura e serviços compartilhados para que esses projetos não precisem duplicar a mesma base técnica em cada JAR.
Não adiciona gameplay relevante isoladamente e não é intercambiável automaticamente com Architectury, Cloth Config ou outras libraries genéricas: cada consumidor exige o contrato específico que utiliza. A build instalada é `1.6.1` para NeoForge 1.21.1.

## ShatterLib | OctoLib — runtime 0.6.2

`OctoLib-NEOFORGE-0.6.2+1.21.jar`
**OctoLib** é uma biblioteca compartilhada do ecossistema OctoStudios/Shatterbyte. Ela fornece configuração YAML com validação/serialização profunda e infraestrutura visual/técnica reutilizável, incluindo tween/keyframes, UI particles, child widgets, cores/easings, trails e networking.
No pack há consumidor causal confirmado: **Relics** declara OctoLib/ShatterLib como dependência. O filename instalado é `0.6.2+1.21`, enquanto o metadata runtime canônico é `0.6.2`; o guia preserva as duas identidades. A biblioteca não adiciona gameplay independente e não é substituível por GeckoLib, Lodestone ou outras APIs apenas por semelhança de categoria.

## SmartBrainLib — 1.16.11

`SmartBrainLib-neoforge-1.21.1-1.16.11.jar`
**SmartBrainLib** é uma biblioteca de IA voltada a behavior trees, sensores, memories, tasks e utilidades de cérebro reutilizadas por mods de entidades. Ela fornece infraestrutura para implementar comportamentos mais complexos sem cada projeto recriar toda a camada de decisão de mobs.
A build instalada é `1.16.11` para NeoForge 1.21.1. O catálogo ainda não confirmou um consumidor causal específico no pack, portanto a classificação permanece **Sem decisão**: a biblioteca deve ser mantida documentada como top-level instalado, mas não promovida automaticamente a dependência nem tratada como gameplay próprio.

## Puzzles Lib — 21.1.52

`PuzzlesLib-v21.1.52-1.21.1-NeoForge.jar`
**Puzzles Lib** é a biblioteca compartilhada do ecossistema Fuzs. Ela fornece infraestrutura de **registro, configuração, networking e utilidades comuns** usada pelos mods consumidores, permitindo que funcionalidades recorrentes sejam implementadas uma vez na library.
No pack há consumidor causal confirmado: **Overflowing Bars 21.1.1** carrega e utiliza diretamente classes/eventos da Puzzles Lib. Por isso esta entrada representa uma dependência top-level real, não uma library órfã inferida apenas pelo nome. A build instalada é `21.1.52`, release estável NeoForge 1.21.1.

## Particle Rain — 4.0.0-beta.11

`particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`
**Particle Rain** é um overhaul client-side da apresentação do clima. Ele substitui chuva e neve vanilla por partículas com movimento e colisão mais naturais e adiciona efeitos atmosféricos configuráveis como **wind-blown rain, mist, haze e sandstorms**, com regras por bioma, bloco e condição climática.
O mod não calcula estações, temperatura corporal ou um novo sistema meteorológico: ele lê as condições existentes e muda sua apresentação visual. Quantidade de partículas, obstrução e efeitos individuais podem ser configurados. A build instalada é `4.0.0-beta.11` para NeoForge 1.21.1.

## Particular Reforged — 1.5.7

`particular-1.21.1-NeoForge-1.5.7.jar`
**Particular Reforged** é uma camada client-side de **partículas ambientais e efeitos de interação**, enriquecendo especialmente água, chuva, superfícies e movimentos no ambiente. O objetivo é tornar ações e fenômenos já existentes mais visíveis por partículas adicionais, sem alterar as regras físicas ou climáticas que os originam.
Ele não substitui shader nem um sistema de clima: apenas acrescenta apresentação visual aos eventos. No stack atual, a release `1.5.7` possui inclusive correção específica para **boats/ships Sable**, evitando spam de splashes durante navegação. A build instalada é a release estável NeoForge 1.21.1 de 16/08/2026.

## LowDragLib2 — 2.2.37

`ldlib2-neoforge-1.21.1-2.2.37-all.jar`
**LowDragLib2 (LDLib2)** é um framework técnico avançado para mods que precisam de **UI, rendering, sincronização, persistência de dados e editores in-game** mais complexos do que os componentes vanilla fornecem diretamente. Ele oferece widgets, ferramentas de edição e infraestrutura reutilizável para consumidores que constroem interfaces e sistemas visuais próprios.
No pack, **KilaGraph** é um consumidor relevante dessa infraestrutura. LDLib2 não adiciona conteúdo visual autônomo nem substitui libraries genéricas; fornece APIs específicas que os consumidores foram escritos para usar. O runtime instalado é `2.2.37` para NeoForge 1.21.1.

## Kiwi — runtime 15.8.7+neoforge

`Kiwi-1.21.1-NeoForge-15.8.7.jar`
**Kiwi** é o toolkit/library da Snownee usado por mods de conteúdo e QoL que dependem de sua infraestrutura. Ele fornece contratos e utilidades compartilhadas para que os consumidores implementem recursos sem replicar a mesma base técnica.
Não acrescenta uma linha de gameplay própria quando instalado isoladamente e não é intercambiável automaticamente com Architectury, Balm ou outras libraries: mods que dependem de Kiwi esperam sua API específica. O arquivo instalado é a build NeoForge 1.21.1 `15.8.7`, com runtime `15.8.7+neoforge`.

## WunderLib NeoForge — 21.0.10

`wunderlib-21.0.10.jar`
**WunderLib** é uma biblioteca compartilhada do ecossistema **BetterX/New Dawn**, usada por BetterEnd: New Dawn, BetterNether: New Dawn e componentes relacionados. Ela centraliza infraestrutura reutilizável necessária por esses projetos, evitando que cada mod replique a mesma base técnica.
No pack, seus consumidores atuais incluem BetterEnd e BetterNether. WunderLib não é equivalente a BCLib ou WorldWeaver: essas bibliotecas ocupam contratos diferentes dentro do mesmo stack e podem coexistir como dependências complementares. A build top-level instalada é `21.0.10` para NeoForge 1.21.1.

## Structurize — runtime 1.0.832-1.21.1

`structurize-1.0.832-1.21.1.jar`
**Structurize** é a infraestrutura de **blueprints, seleção de área, preview e colocação de estruturas** usada principalmente pelo MineColonies. Ela fornece ferramentas e contratos para representar construções complexas e reconstruí-las no mundo preservando estados/orientações necessários.
MineColonies `1.1.1374-1.21.1-snapshot` instalado exige Structurize `>=1.0.832`, e a build presente corresponde exatamente a esse piso. Embora trabalhe com schematics/estruturas, sua função é distinta do Schematicannon e das ferramentas de blueprint do Create: ela é o backend estrutural esperado pelo ecossistema MineColonies.

## MonoLib — 4.1.0

`monolib-neoforge-1.21.1-4.1.0.jar`
**MonoLib** é uma biblioteca compartilhada que fornece **eventos, comandos, registro e utilidades comuns** para mods consumidores. Ela não adiciona uma progressão ou sistema jogável relevante quando instalada isoladamente; seu comportamento aparece por meio dos projetos que utilizam sua API.
No pack, **Dis-Enchanting Table** é consumidor confirmado. Por isso MonoLib deve ser lida como dependência técnica top-level, não como alternativa intercambiável a outras libraries genéricas. A build instalada é `4.1.0` para NeoForge 1.21.1.

## Konkrete — 1.9.9

`konkrete_neoforge_1.9.9_MC_1.21.jar`
**Konkrete** é uma biblioteca técnica usada por mods do ecossistema Keksuccino e projetos relacionados. Ela centraliza utilidades e infraestrutura compartilhada que esses consumidores esperam encontrar, sem adicionar por si só uma progressão ou sistema de gameplay relevante.
A versão instalada é `1.9.9`, correspondente à linha aplicável a Minecraft 1.21/1.21.1; releases posteriores do projeto miram versões mais novas do jogo e não substituem automaticamente este JAR. Como outras libraries específicas, Konkrete não é intercambiável apenas por existir outra API genérica no pack.

## Zeta — 1.1-40

`Zeta-1.1-40.jar`
**Zeta** é a biblioteca estrutural criada para mods modulares do ecossistema Vazkii. Ela é uma reestruturação do sistema modular originalmente desenvolvido dentro do Quark e funciona como sucessora do AutoRegLib, concentrando a infraestrutura comum necessária para registro, organização e funcionamento de módulos sem obrigar cada consumidor a reimplementar esse backend.
No pack, **Quark 4.1-482** é o consumidor principal registrado, portanto Zeta não é uma library órfã nem um substituto genérico de Architectury, Balm ou outras APIs. O JAR `Zeta-1.1-40.jar` é exatamente a release NeoForge 1.21.1 publicada em 24/04/2026 e presente na modlist canônica.

## A Good Place — runtime 1.21-1.2.5

`a_good_place-1.21-1.2.5-neoforge.jar`
**A Good Place** é uma melhoria visual client-side para colocação de blocos. Em vez de o bloco simplesmente aparecer no destino, partículas/modelos produzem uma animação de colocação. Resource packs podem definir quais blocos participam e controlar a própria trajetória por pontos de controle.

## sable-x-cpm — 0.3.2+1.21.1

`sable-x-cpm-0.3.2+1.21.1.jar`
**sable-x-cpm** faz outfits/modelos de **Customizable Player Models** serem aplicados também às peças físicas dos ragdolls e cadáveres Sable. Sem a bridge, cada peça usa um PlayerModel vanilla que CPM não intercepta; o addon associa o modelo CPM correto antes da renderização. Capas/elytra permanecem no caminho vanilla e peças customizadas muito afastadas do osso pai podem apresentar separação nas juntas.

## Dynamic RPG Resource Bars — 0.7.1

`dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar`
**Dynamic RPG Resource Bars** fornece barras animadas e reposicionáveis para vida, stamina e mana. A aparência pode ser substituída por sprites de resource pack e um editor integrado controla layout/apresentação. Na linha NeoForge 1.21.1, o mod consegue ler mana de **Ars Nouveau** e **Iron's Spells 'n Spellbooks**; ele apenas apresenta recursos existentes e não cria um novo sistema de mana ou stamina.

## Overflowing Bars — 21.1.1

`OverflowingBars-v21.1.1-1.21.1-NeoForge.jar`
**Overflowing Bars** reformula a apresentação de **vida, armadura e armor toughness quando esses valores ultrapassam os limites visuais vanilla**. Em vez de criar várias linhas de corações/ícones ou depender de códigos de cor difíceis de acompanhar, ele empilha camadas compactas e mostra um **contador de linhas/camadas**, tornando valores RPG muito altos legíveis sem ocupar grande parte da tela.
O mod é exclusivamente client-side e não altera os atributos, seus limites nem o cálculo de dano: ele só representa no HUD valores produzidos por sistemas como atributos, equipamentos e efeitos externos. A build instalada `21.1.1` é a release NeoForge 1.21.1; essa versão também evita renderizar os elementos quando o HUD é ocultado com `F1`.

## Just Enough Effect Descriptions — runtime 1.21-2.3.2

`jeed-1.21-2.3.2.jar`
**JEED** adiciona ao recipe viewer informações e descrições para efeitos de status e poções registrados. Funciona como plugin para interfaces compatíveis como JEI/REI/EMI e serve como camada de consulta; não cria nem altera os efeitos que documenta.

## Moonlight Lib — runtime 1.21.1-3.5.2

`moonlight-1.21.1-3.5.2-neoforge.jar`
**Moonlight Lib (Selene)** é uma biblioteca client/server usada por vários mods para evitar que cada projeto reimplemente infraestrutura comum. Ela fornece utilidades para **registro dinâmico, geração dinâmica de assets, villagers e IA customizada, trades data-driven, global datapacks, map markers e animações de itens em primeira/terceira pessoa**, além de outros helpers de conteúdo e sincronização.
No pack, consumidores confirmados incluem **Supplementaries** e outros projetos do ecossistema MehVahdJukaar. A build `3.5.2` é a release NeoForge 1.21.1 de 27/08/2026; essa atualização adiciona `ILoomItem`, permitindo que itens não-banner participem do Loom e desenhem seu próprio preview, adiciona `TabAdderHelper` e aplica um hotfix sobre a atualização anterior. Como biblioteca, Moonlight não cria uma progressão jogável isolada: suas funções aparecem por meio dos mods consumidores.

## Polytone — runtime 1.21-4.0.2

`polytone-1.21-4.0.2-neoforge.jar`
**Polytone** é um framework orientado a **resource packs** para modificar profundamente apresentação visual e ambiental sem exigir que cada pack crie um mod Java próprio. Ele suporta **colormaps e lightmaps, block sounds, partículas customizadas e emitters em modelos de entidades, post shaders, custom item models, texturas variantes por bioma, cores de mapas, raridade/tooltips, creative tabs e modificações de GUI**, incluindo mover slots e adicionar elementos.
Também consegue alterar partículas existentes, controlar cores e offsets de blocos e trabalhar com texturas animadas dependentes do horário do mundo; parte do formato mantém compatibilidade com convenções do OptiFine. Na linha `4.0.2`, mudanças de GPU particles foram portadas para 1.21.1: initializers podem usar um campo de colormap amostrado no spawn e particle colormaps passam a ser amostrados na própria partícula, evitando que todas compartilhem indevidamente uma única cor. Links da tela de informações do pack também voltaram a ser clicáveis.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/12-navegacao-entre-os-tres-guias.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 12. Navegação entre os quatro guias

Os quatro guias consolidados formam uma única base de consulta para a auditoria de perks: Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios. Use os índices locais para o snapshot versionado e os links canônicos quando aplicável.

- [Mods de Magia — snapshot local](../magic/README.md) · [Notion canônico](https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03)
- [Mods de Tecnologia — snapshot local](../technology/README.md) · [Notion canônico](https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff)
- [Projetos Próprios — snapshot local](../projects/README.md) · authority operacional consolidada neste anexo


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/13-projetos-proprios-do-modpack.md -->

<!-- Snapshot auditável reconciliado em 2026-08-30. -->

# 13. Projetos próprios do modpack — integração canônica para perks

A partir de 30/08/2026, os quatro projetos próprios fazem parte da auditoria obrigatória das perks.

**Fonte canônica completa:** [Projetos Próprios do Modpack](../projects/README.md)

Este capítulo é apenas o recorte de **Gameplay e Sistemas**. Para decidir `Provider/Mods`, hook, status e fail-closed, o Chat 1 deve ler os quatro dossiês completos e a matriz cruzada.

## RPG Skill Tree — autoridade central de progressão

[Dossiê completo](../projects/01-rpg-skill-tree.md)

- **Canônico:** estado persistente de jogador, Level/XP/CPP/atributos, serviços de progressão, núcleo da Skill Tree 03.01–03.05, runtime único de node effects, world scaling completo e API pública read-only.
- **Canônico específico:** subtrees Technomancer, Warlock, Druid e Metamorph.
- **Misto/planejado:** fechamento geral de classes/masteries, Stage 05 combat-magic hooks, várias integrações Stage 06, itemização Stage 11, corpos/clones Stage 12 e cartografia Stage 13.
- Perks usam boundaries canônicos e nunca storage/attachments internos.
- Uma ação = uma mutação canônica; Mastery exige autoria causal e deduplicação.

## Volcanoes — geologia, hazards ambientais e engenharia física

[Dossiê completo](../projects/02-volcanoes.md)

- **Canônico:** geologia/strata/depósitos persistentes, tectônica/terremotos, volcano sites/magma/lava/erupções/cinzas/piroclastos/geotermia, Atmosphere, respiração/gases/poluição, pressão e protection equipment.
- `GeologicalDepositSource` é uma SPI read-only apropriada para descoberta/prospecção bounded; o core não cria scanner concorrente.
- Cold Sweat permanece autoridade de temperatura corporal; Destroy conserva sua autoridade de poluição.
- Sable/Aeronautics integram pressão física sem inventar cabine selada quando a API não prova o estado.
- **RNS parcial/fail-closed:** Volcanoes já prova placement físico bounded/determinístico de Cu/Fe/Au; RNS continua authority de prospecção/native metal worldgen até o handoff seletivo de ownership ser fechado.
- Sem Mastery por tick de gás, pressão, calor, tremor ou mera permanência ambiental.

## Enshrouded — Shroud, Exposure, Corrupted Ecology e Flame

[Dossiê completo](../projects/03-enshrouded.md)

- **Canônico:** Shroud Field persistente/bounded, `ShroudQuery`, Terrain Corruption via `MutationAuthority`, Exposure/Madness/Deadly Shroud/Red Sludge e ecologia corrompida.
- **Flame Progression canônica:** Flame State, Flame Altar, Sanctuary/Flame Ward e Level 1 Ritual estão fechados.
- `FlamePassageQuery` é boundary real; lookup incerto falha fechado.
- **06.01 Story State é canônico** em `main@77552a3d...`; Boss Provider/manifestação/Lich Skull e o restante de Lich & Story, Client Experience, Integrations e Hardening não podem ser promovidos em bloco.
- Shroud/Exposure não são Black Arcana Corruption/Strain.
- Sem Mastery por tick de exposição ou edge-dancing.

## Black Arcana — risco arcano como gameplay

[Dossiê completo](../projects/04-black-arcana.md)

- **Canônico:** Arcana Core, Integration Layer e World Safety.
- **Parcial:** Casting & UX possui código mergeado com QA manual aberta; Arcane Danger tem componentes reais, mas o Stage 05A segue ativo.
- Danger Model, Arcane Resistance, Corruption Resistance, Arcane Strain e Arcane Backlash precisam ser distinguidos de hazards físicos ou Shroud.
- `ARCANE_BACKLASH` é terminal: não recursa, não crita, não lifesteala e não concede Mastery/proc ofensivo.
- Equipment set bonus infrastructure já está em `main`, mas não representa fechamento integral de Equipment/Containment.
- Rituals, Spell Domains e Progression & Balance permanecem preparatórios/planejados.

## Matriz cruzada obrigatória

[Matriz de integração cruzada](../projects/05-cross-project-integration-matrix.md)

Antes de fechar uma perk híbrida, registrar explicitamente:

- authority principal;
- provider/consumer secundário;
- direção da bridge;
- hook causal;
- identidade de deduplicação;
- fallback;
- fail-closed.

Integração temática não cria automaticamente um hook.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/14-mobstein.md -->

# 14. Mobstein — fauna ressuscitada, experimentos, bosses e estruturas

## Mobstein : Revive animals and necromancy! — 5.4.4

`mobstein-5.4.4-neoforge-1.21.1.jar`

**Mobstein** adiciona uma linha de gameplay centrada em ressurreição de mobs, animais utilitários, experimentos, necromancia corporal e encontros próprios. A release instalada é `5.4.4` para **NeoForge 1.21.1**, publicada em 04/05/2026. O projeto é client + server e requer **GeckoLib** na linha 1.21.1; o autor recomenda JEI para consultar receitas.

A progressão começa com o guidebook e a **Clinical Stretch**, usada para ressuscitar corpos durante a noite. Diferentes criaturas ressuscitadas têm utilidades próprias. Exemplos documentados pelo autor incluem Axolotl com regeneration/night vision, Dolphin montável com water breathing, Silverfish voltado à mineração rápida em cavernas, Rabbit com jump boost e Warden/Frankenstein como guarda-costas.

O mod também trabalha com **corpos, cabeças, órgãos, seringas, Surgery Stretch e Subject Assembly Machine**. Corpos podem fornecer partes e órgãos; experimentos e mannequins podem ser montados a partir de componentes. Há ainda criaturas especiais como Dr. Mobstenio e Igor.

O conteúdo de exploração inclui **Frankenstein Castle**, **Witherstein Ruins**, **Old Ruins** e o boss **Witherstein**, com três estágios documentados.

### “Perks” internas do Mobstein

O próprio Mobstein usa o termo `perk` para `Attack`, `Health`, `Speed` e `Template` na criação de criaturas. Essas **não são perks/nodes do RPG Skill Tree**.

### Regra para perks do RPG Skill Tree

Mobstein passa a ser provider obrigatório de cobertura quando a fantasia tocar ressurreição de criaturas, corpos/cabeças/órgãos, criação de experimentos, allies/bodyguards ressuscitados, milestones de estrutura/boss ou ownership de companion/summon.

Não reduzir o provider a `+dano de summon` por conveniência. Mastery/reward não deve ser concedida por tick, por um ressuscitado apenas permanecer vivo/seguindo o jogador, ou por repetir indefinidamente a mesma ressurreição sem identidade deduplicável.

A necromancia do Mobstein é domínio próprio. Não converter automaticamente seus corpos/ressuscitados em **Black Arcana Corruption**, **Goety Soul Energy**, **Malum spirits**, **Eidolon rituals** ou **Enshrouded Shroud**. Qualquer bridge futura precisa de contrato explícito e authority definida.

**Fonte:** CurseForge oficial, projeto 1193873; release `mobstein-5.4.4-neoforge-1.21.1.jar` de 04/05/2026.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/15-simply-swords-e-ecossistema.md -->

[← Índice do guia](README.md)

# 15. Simply Swords e ecossistema — contratos para integração em perks

> **Objetivo deste capítulo:** documentar mecânicas, authority, hooks e riscos do stack Simply Swords instalado para que o Chat 1 possa decidir cobertura de perks sem inventar comportamento do provider. Este capítulo **não cria perks** e não substitui a auditoria da versão/JAR atual em [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md).

## 15.1 Estado instalado e ownership

| Componente | JAR instalado | Papel / authority |
|---|---|---|
| Simply Swords | `simplyswords-neoforge-1.70.2-1.21.1.jar` | Provider principal de famílias de armas, implicits, Runic Powers, Unique Weapons, Awakening, Runic Forge e sockets/gem powers. |
| Simply More | `simplymore-forge-1.3.0_alpha.jar` | Addon de tipos de arma e Uniques adicionais; a linha `1.3.0 ALPHA` também migra seus weapon types para implicits e inicia compatibilidade com Iron's Spells. |
| Integrated Simply Swords | `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar` | Bridge de materiais: preenche famílias de armas Simply Swords para materiais fornecidos por outros mods. |
| Simply Swords: Cataclysm | `simplycataclysm-1.0.2+1.21.1+neoforge.jar` | Bridge L_Ender's Cataclysm ↔ Simply Swords, com famílias por material e traits próprios em materiais específicos. |
| Simply Tooltips | `SimplyTooltips-neoforge-0.1.5.jar` | Camada client-side de apresentação data-driven. **Não é provider mecânico de perk.** |
| Epic Fight - Mod Compat | `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` | Adapter de presets/capabilities de combate Epic Fight para armas compatíveis. **Não é authority dos implicits, Awakening, Runic Powers ou habilidades dos Uniques.** |

A release instalada de Simply Swords `1.70.2` corrige explicitamente um crash com Epic Fight. Isso prova compatibilidade corrigida da linha instalada, mas não significa que toda arma exótica possua moveset bespoke.

## 15.2 Simply Swords — famílias de armas e progressão

Simply Swords não é apenas um pacote de modelos. A documentação atual da linha 1.21 descreve **15 famílias padrão**, além de Hammer e Dagger usados por determinados Uniques. Cada família possui tradeoff de dano/alcance/velocidade e um **Implicit** próprio.

Fluxo nativo principal:

1. armas padrão usam progressão de materiais, incluindo Iron/Gold/Diamond e upgrade para Netherite;
2. uma arma Netherite pode ser convertida em **Runic weapon** usando Runic Tablet como template de smithing e Diamond como addition;
3. a Runic weapon mantém seu tipo/Implicit e recebe um **Runic Power**;
4. **Unique Weapons** possuem efeitos próprios e, na linha atual, participam de Awakening e sockets quando aplicável;
5. Runic Forge e Runic Tablets são a progressão autoritativa de Awakening; a skill tree não deve duplicar essa progressão.

### Famílias e Implicits nativos

Os valores abaixo são **defaults do provider**. Config do modpack/servidor pode alterá-los; portanto o Chat 1 deve tratar o valor efetivo do stack/config como autoridade quando desenhar escalonamento.

| Família | Implicit nativo | Faixa default | Observação para auditoria de perk |
|---|---|---:|---|
| Rapier, Spear | chance de ignorar armor | 15–35% | Alto risco de double-dip com armor negation/penetration do Epic Fight ou outras perks. |
| Cutlass | chance de loot extra on-hit | 1–3% | Capacidade econômica; não tratar como simples dano. Exige auditoria anti-duplicação de drops. |
| Glaive, Greataxe, Halberd | chance de aplicar Bleed | 10–60% | O proc/status pertence ao Simply Swords; não reimplementar Bleed externamente. |
| Sai, Dagger | aumento de dano por backstab | 20–40% | Depende de causalidade posicional real do provider. |
| Claymore, Longsword | chance de defletir dano recebido | 5–15% | É defesa/IncomingDamageHandler, não proc ofensivo comum. |
| Greathammer | armor sunder por hit | 2–10% | Alteração de armor por acúmulo/hit; precisa de anti-stack e ownership claros. |
| Hammer | armor sunder por hit | 2–6% | Mesma família mecânica, faixa menor. |
| Katana | chance de dano duplo | 5–15% | Multiplicativo de alto risco; não somar outro “double damage” sem regra explícita. |
| Chakram, Twinblade | chance de ganhar attack speed on-hit | 5–25% | Interage diretamente com velocidade/cadência e Epic Fight. |
| Scythe | chance de executar inimigo com vida baixa | 5–15% | Execute deve permanecer provider-native; nunca substituir por dano genérico. |
| Warglaive | chance de atacar duas vezes | 5–15% | Alto risco de duplicar on-hit, enchant, gem power e outros procs. |

Os tipos built-in expostos pela API são `rapier`, `cutlass`, `sai`, `dagger`, `claymore`, `longsword`, `greathammer`, `hammer`, `katana`, `spear`, `glaive`, `halberd`, `warglaive`, `chakram`, `scythe`, `greataxe` e `twinblade`.

### Implicit como contrato público

A linha atual fornece API pública, portanto o RPG Skill Tree **não precisa adivinhar tipo de arma pelo nome** quando houver integração de código adequada:

- `SimplySwordsAPI.registerWeaponType(item, type)`;
- `SimplySwordsAPI.registerWeaponType(tag, type)`;
- `SimplySwordsAPI.getOrCreateWeaponImplicit(stack)`;
- `SimplySwordsAPI.appendWeaponImplicitTooltip(...)`;
- `SimplySwordsAPI.applyWeaponImplicitDamage(...)`;
- `SimplySwordsAPI.applyWeaponImplicitOnHit(...)`;
- `SimplySwordsAPI.registerWeaponImplicit(definition)` para addons que realmente definam um novo tipo.

`WeaponImplicitDefinition` possui handlers distintos para:

- `DamageHandler` — modifica dano de saída;
- `HitHandler` — executa depois de hit confirmado;
- `IncomingDamageHandler` — pode interferir/cancelar dano recebido enquanto a arma está segurada;
- `TooltipFormatter` — apresentação do roll.

O roll do Implicit fica persistido no stack. Armas comuns usam a faixa inteira; subclasses de `UniqueWeaponItem` rolam no **top 10%** da faixa. Socket/Awakening/save-reload não devem rerrolar esse valor.

**Consequência para o Chat 1:** Implicit é uma capacidade observável e estável do provider. Uma perk pode ser avaliada em torno de um tipo/roll real somente se respeitar o roll já existente; não deve criar um segundo sistema paralelo de “implicit da skill tree”.

## 15.3 Runic Powers e Runic weapons

Runic Powers são habilidades de arma do Simply Swords e podem ser passivas, acionadas por trigger ou ativas. A progressão nativa parte de uma arma Netherite e de um Runic Tablet; o resultado preserva o tipo/Implicit e adiciona a power.

Pontos importantes para design:

- a power pertence ao **stack da arma**, não ao jogador abstratamente;
- alguns rolls podem ser **Greater**, isto é, uma forma superior definida pelo próprio provider;
- Runic weapons ficam abaixo dos Uniques na progressão geral, mas sua Runic Power funciona em força integral e não usa a curva de Awakening dos Unique Weapons;
- o provider possui loot/pity próprios para Runic Tablets e permite reroll conforme suas regras;
- uma perk não deve conceder “Greater”, rerrolar power, ignorar custo de tablet ou substituir pity sem um contrato deliberado e hook real.

Para integração, o princípio é **provider-native first**: observar power/estado já existente ou modificar apenas parâmetros cuja API real permita. Não simular a mesma habilidade em eventos genéricos do RPG Skill Tree.

## 15.4 Unique Weapons e Awakening

A linha atual possui progressão **Awakening de 8 níveis**. `UniqueWeaponItem`, incluindo `UniqueSwordItem`, recebe automaticamente `AwakeningProfile.DEFAULT`.

No perfil default documentado:

- nível 0 começa com multiplicador de attack damage `0.50`;
- nível 0 começa com multiplicador de attack speed `0.75`;
- a habilidade é desbloqueada no nível `4`;
- os atributos interpolam até a força integral no nível `8`.

A API pública relevante inclui:

- `AwakeningApi.isAwakeningSystemEnabled()`;
- `AwakeningApi.usesAwakeningProgression(stack)`;
- `AwakeningApi.getLevel(stack)`;
- `AwakeningApi.isAbilityUnlocked(stack)`;
- `AwakeningApi.getAbilityUnlockLevel(stack)`;
- `AwakeningApi.getEffectMultiplier(stack)`;
- `AwakeningApi.getAttributeMultiplier(stack)`;
- `AwakeningApi.getAttackSpeedMultiplier(stack)`;
- `AwakeningApi.scaleEffect(...)` / `scaleChance(...)`;
- `AwakeningApi.areGemPowersActive(stack)` e helpers próprios de gem scaling.

`SimplySwordsAPI.scaleAbilityDamage(...)` e `scaleAbilityValue(...)` já aplicam a lógica de scaling apropriada. **Não aplicar `scaleEffect` de novo no resultado**, pois isso duplica o escalonamento de Awakening.

Aquisição também possui semântica própria:

- `AwakeningApi.initializeNaturalDrop(stack)` cria reward natural em nível 0;
- `AwakeningApi.initializeFullyAwakened(stack)` é uma concessão intencional de nível 8;
- pity loot e transformações por Contained Remnant já inicializam drops naturais corretamente.

Quando o sistema global de Awakening está desativado, Uniques comuns se comportam como nível 8, preservando o nível armazenado; por isso o Chat 1 deve consultar `usesAwakeningProgression` quando precisar saber se o progresso persistido está realmente ativo.

**Boundary obrigatório:** Awakening é progressão natural autoritativa do Simply Swords. Uma perk pode ser desenhada para interagir com esse estado somente se houver razão sistêmica; ela não deve “dar níveis de Awakening grátis”, contornar Runic Forge/Tablet ou desbloquear habilidade antes da progressão nativa sem uma decisão explícita extraordinária.

## 15.5 Sockets, Runefused/Netherfused gems e powers

Unique Weapons nativos possuem dois tipos de socket quando o sistema está habilitado:

- **Runefused**;
- **Netherfused**.

Na documentação de desenvolvimento atual, `UniqueWeaponItem.inventoryTick` cria os dois sockets nativos e é responsável por:

- tick de gem powers equipadas em main/off hand;
- socketing e replacement por inventory click;
- devolução da gem deslocada;
- post-hit das gem powers;
- tooltip dos sockets.

A API também permite sockets em itens externos configurados por item ID ou `#tag` por meio de `AdditionalGemSocketApi`.

Hooks públicos para bases customizadas:

- `SimplySwordsAPI.inventoryTickGemSocketLogic(...)`;
- `SimplySwordsAPI.onClickedGemSocketLogic(...)`;
- `SimplySwordsAPI.postHitGemSocketLogic(...)`;
- `SimplySwordsAPI.appendTooltipGemSocketLogic(...)`;
- `SimplySwordsAPI.onWeaponSwing(...)` para caminhos customizados de swing.

As powers públicas derivam de classes como:

- `RunicGemPower`;
- `RunefusedGemPower`;
- `NetherGemPower`;
- `GemPower`.

`GemPowerRegistry` usa IDs namespaced sincronizados entre servidor/cliente. Desde `1.70.0`, `GemPowerComponent` persiste **IDs**, não registry-entry identity. Isso foi feito justamente para evitar incompatibilidades/fuzzy replacement em stacks reconstruídos por storage mods como AE2/Create/Refined Storage.

Para dano/valor de gem powers, helpers como `gemPowerScaledDamage`/`gemPowerScaledValue` já escolhem o resultado físico/fixo versus spell scaling e aplicam Awakening de gem **uma vez**. Não escalar novamente no RPG Skill Tree.

## 15.6 Habilidades de Unique — como tratar no design

Uniques podem possuir passivas e/ou habilidades ativas próprias. O sistema público inclui `UniqueWeaponActiveAbility`, contextos de habilidade e cooldown autoritativo. `SimplySwordsAPI.tryActivateWeaponAbility(...)` verifica, entre outras condições, unlock de Awakening e cooldown antes de ativar.

Também existem helpers de hit/ability que preservam regras importantes:

- `applyDelegatedWeaponHit(...)` / `applyEntityWeaponHit(...)` executam pipeline de hit de arma com contexto, Implicit e post-hit controlados;
- `applyAbilityBoltDamage(...)` é server-side, valida target, aplica enchantments pertinentes, bypassa iframes para o bolt e **suprime Implicits** nesse dano para evitar proc indevido;
- helpers de target aplicam regras de alvo/friendly-fire próprias.

Isso significa que “uma habilidade causa outro hit” **não autoriza** a perk a disparar todos os on-hit novamente. O provider já possui caminhos explícitos para deduplicar/suprimir efeitos.

Exemplo representativo: **Tempest** é Chakram, recebe Implicit de attack-speed-on-hit e possui a habilidade Vortex, com efeitos elementais que se acumulam no alvo e uma ativação que recolhe esses elementos para formar um vortex ofensivo. O importante para o guia não é transformar Tempest em uma perk nominal, mas demonstrar que um Unique pode combinar:

- família/Implicit;
- passiva/stack próprio;
- active ability;
- Awakening;
- sockets/gem powers;
- scaling físico ou mágico.

Logo, qualquer perk genérica de armas deve ser auditada contra **todas essas camadas** para não contar o mesmo evento duas ou três vezes.

## 15.7 Simply More — escopo instalado e cautela de alpha

> **ATUALIZAÇÃO DE PROVENIÊNCIA — 2026-09-07:** o artefato instalado foi fechado como **Simply More 1.3.0 ALPHA 5**. Filename local `simplymore-forge-1.3.0_alpha.jar`, runtime `1.3.0_alpha`, SHA-1 `51636477cd5c378f42d9700e1fe35cd952c8f4f1`, CurseForge File ID `8736778`, publicação `simplymore-neoforge-1.3.0_alpha5+1.21.1.jar`. A ambiguidade da revisão Alpha foi resolvida. Isso **não** promove comportamento mecânico não auditado: integração de perk continua conservadora/fail-closed quando o hook específico não estiver comprovado.

Simply More adiciona **10 weapon types** ao ecossistema:

1. Great Katanas;
2. Grandswords — podem desabilitar shields quando o oponente tenta bloquear;
3. Backhand Blades;
4. Lances — possuem forte bônus de dano/força quando usadas montado;
5. Khopeshs;
6. Daggers;
7. Pernachs;
8. Quarterstaffs;
9. Great Spears;
10. Deer Horns.

O projeto também declara **33 Unique Weapons**.

A linha instalada é `1.3.0 ALPHA`. O changelog da primeira build dessa linha registra mudanças especialmente relevantes para perks:

- weapon types do Simply More passam a ter **implicit abilities**, acompanhando o sistema introduzido/expandido pelo Simply Swords 1.70;
- o bônus da Lance passa a ser um **Implicit**, em vez de status effect;
- começa a implementação de compatibilidade com **Iron's Spells and Spellbooks**, inicialmente apenas para Uniques retrabalhados;
- vários efeitos on-hit passam a ocorrer **uma vez por swing**, não uma vez por entidade atingida;
- alguns efeitos deixam de ser status effects comuns e passam a estado próprio que não pode ser limpo com milk;
- Uniques marcados para rework tiveram funcionalidade removida e **podem atualmente não fazer nada**;
- o próprio autor alerta para crashes com Uniques antigos e quebra/reset de configs.

### Ambiguidade de artifact da linha alpha

O filename instalado `simplymore-forge-1.3.0_alpha.jar` não é suficiente, sozinho, para distinguir algumas revisões publicadas dessa série: páginas posteriores de Alpha 3/Alpha 5 também expõem o mesmo filename interno em certos uploads. Portanto:

- o guia considera como contrato seguro apenas o comportamento comum documentado para `1.3.0 ALPHA` e aquilo que puder ser confirmado no runtime/metadata do pack;
- recursos exclusivos de Alpha 2/3/4/5 **não devem ser presumidos** sem File ID/hash/runtime adicional;
- se uma perk depender de uma Unique específica do Simply More, o Chat 1 deve validar a classe/efeito no artifact instalado; se não puder provar, classificar **SEM HOOK SEGURO / FAIL-CLOSED**.

**Boundary:** Simply More é provider mecânico quando define seu próprio weapon type/Implicit/Unique. Não é apenas “conteúdo visual”. Entretanto, o estado alpha exige integração conservadora.

## 15.8 Integrated Simply Swords — bridge de materiais, não novo sistema de perks

Integrated Simply Swords `1.4.0+1.21.1-neoforge` é um addon de compatibilidade que **preenche lacunas de famílias de armas Simply Swords para materiais adicionados por outros mods**. A versão 1.4.0 é o port para 1.21.1 e incorporou temporariamente o escopo do antigo Knaves Needs.

Sua autoridade é principalmente:

- material/tier do mod de origem;
- família/tipo de arma e Implicit do Simply Swords;
- receita/integração fornecida pelo addon.

Portanto, “arma Integrated Simply Swords” não deve automaticamente criar uma perk própria. Em geral, ela deve cair sob cobertura universal por weapon type/material **a menos que** o addon prove uma mecânica própria adicional.

O Chat 1 deve evitar usar o namespace do addon como substituto da semântica real. Para uma arma integrada, perguntar:

1. qual mod é dono do material/tier?;
2. qual `weaponType` Simply Swords a arma recebe?;
3. existe efeito próprio além do Implicit padrão?;
4. Epic Fight está apenas atribuindo preset de animação ou há bridge mecânica adicional?

Sem efeito adicional provado, a classificação correta tende a **COBERTO POR SISTEMA UNIVERSAL**, não “perk de Integrated Simply Swords”.

## 15.9 Simply Swords: Cataclysm — material families e traits próprios

`simplycataclysm-1.0.2+1.21.1+neoforge.jar` conecta materiais de L_Ender's Cataclysm às famílias de armas Simply Swords.

O source atual expõe famílias para **Ancient Metal, Black Steel, Cursium, Ignitium e Witherite** e assets/recipes de quinze arquétipos Simply Swords: Chakram, Claymore, Cutlass, Glaive, Greataxe, Greathammer, Halberd, Katana, Longsword, Rapier, Sai, Scythe, Spear, Twinblade e Warglaive.

O addon também possui `weapon_attributes` data-driven por família/material. Portanto, dano/speed devem ser lidos do provider/config real e não hardcoded em perk.

### Cursium — Accursed Rage

`CursiumSwordItem` implementa callback de dano melee. A cada ciclo relevante, o sistema pode acumular **Accursed Rage** no atacante até um máximo configurável. Enquanto o efeito está presente, o dano recebe um adicional proporcional ao amplifier/stacks. Chance, duração, máximo e dano extra são configuráveis.

**Authority:** Simply Swords: Cataclysm / `CursiumSwordItem` + config do addon.

**Risco:** uma perk que acrescente stacks manualmente ou replique o dano adicional pode dobrar a curva nativa.

### Ignitium — Blazing Brand + lifesteal

`IgnitiumSwordItem` possui chance configurável de aplicar/acumular **Blazing Brand** no alvo. O stack pode alimentar lifesteal do atacante; o cálculo também normaliza o fator considerando attack speed do player dentro de limites internos.

**Authority:** Simply Swords: Cataclysm / `IgnitiumSwordItem`.

**Risco:** bônus externo de lifesteal ou attack speed pode alterar a economia pretendida. Não remover a normalização ou transformar cada hit derivado em novo heal.

### Witherite — Mecha Pulse e Mecha Smite

`WitheriteSwordItem` implementa duas famílias de efeito:

- **Mecha Pulse:** acumula charge no atacante quando não está em cooldown; ao atingir threshold, limpa a charge, aplica cooldown, stuna o alvo e acrescenta dano configurável;
- **Mecha Smite:** pode aplicar Wither/fire no alvo e pode conceder Regeneration ao atacante sob condição de vida, conforme chances/thresholds/config.

**Authority:** Simply Swords: Cataclysm / `WitheriteSwordItem` e seus effects/configs.

**Risco:** qualquer perk de “mais stun”, “menos cooldown”, “mais charge”, “mais regen” ou proc extra precisa usar estado real do addon; nunca criar contador paralelo.

### Ancient Metal e Black Steel

As famílias existem e possuem tier/weapon attributes próprios, mas este guia **não atribui trait especial** a elas sem evidência específica equivalente à encontrada para Cursium/Ignitium/Witherite. Se o Chat 1 quiser uma integração nominal baseada nesses materiais, deve reabrir source/artifact/config e provar a mecânica; do contrário, tratar como cobertura universal de material + weapon type.

### 1.0.2

A release `1.0.2` registra correção para que armas de **Ignitium, Cursium e Witherite** sejam corretamente unbreakable. Durabilidade/inquebrável, portanto, é propriedade do addon/material; uma perk não deve “restaurar” ou recriar essa regra.

## 15.10 Simply Tooltips — boundary estritamente de apresentação

Simply Tooltips é client-side e data-driven. Ele renderiza tooltips modernos para itens com temas válidos e permite expansão por config/resource pack.

Simply Swords oferece integração de addon por tag:

`data/simplytooltips/tags/item/simply_swords_compat.json`

Essa integração pode apresentar ability sections, action labels, linhas de Implicit, atributos e hints. Um addon também pode construir provider de tooltip que mostre progresso de Awakening.

**Classificação obrigatória:** `NÃO DEVE SER INTEGRADO` como provider de perk. Tooltip pode explicar estado, mas não é authority do estado. O RPG Skill Tree deve ler API/componente real de Simply Swords, não parsear texto do tooltip.

## 15.11 Epic Fight × Simply Swords / Simply More

O pack possui `Epic Fight - Mod Compat 1.1.0`, cuja lista de suporte inclui explicitamente **Simply Swords** e **Simply More** no escopo de armas. A bridge atribui presets Epic Fight quando uma arma modded encaixa de forma limpa em um arquétipo; quando não existe mapeamento limpo, não força um comportamento exótico inventado.

Separação de authority:

| Questão | Authority |
|---|---|
| Moveset/preset/animação/Battle Mode | Epic Fight + Epic Fight Compat |
| Família Simply Swords / Implicit | Simply Swords ou addon que registra o tipo |
| Runic Power | Simply Swords |
| Unique active/passive | Simply Swords / addon dono da Unique |
| Awakening e Runic Forge | Simply Swords |
| Socket/gem power | Simply Swords |
| Trait de material Cataclysm | Simply Swords: Cataclysm |
| Tooltips | Simply Tooltips / camada client-side, sem authority mecânica |

A release Simply Swords 1.70.2 instalada corrige um crash com Epic Fight, mas isso não altera a regra acima. Uma perk nunca deve inferir que “Epic Fight processou a arma” significa que Epic Fight é dono do Implicit ou da habilidade do item.

## 15.12 Matriz provider → árvore para o Chat 1

Esta matriz não decide a perk; ela classifica o que **precisa ser considerado** na auditoria provider → árvore.

| Capacidade detectada | Provider/authority | Hook/estado real | Disposição inicial para auditoria |
|---|---|---|---|
| Weapon type Simply Swords | Simply Swords | `registerWeaponType`, registry/tags de implicit | **COBERTO POR SISTEMA UNIVERSAL** ou especialização de armas; não precisa perk nominal por mod. |
| Rolled Implicit | Simply Swords / addon | `getOrCreateWeaponImplicit`, componente persistido | **CANDIDATO A INTEGRAÇÃO**, preservando roll nativo. |
| Armor ignore | Simply Swords | DamageHandler do Implicit | **CANDIDATO**, com auditoria anti-double-dip com armor negation. |
| Bleed | Simply Swords | HitHandler/estado provider | **CANDIDATO**, sem recriar status. |
| Backstab | Simply Swords | Implicit de tipo | **CANDIDATO**, mantendo causalidade posicional. |
| Deflect | Simply Swords | IncomingDamageHandler | **CANDIDATO**, domínio defensivo/arma; não converter em dodge genérico. |
| Armor sunder | Simply Swords | Implicit de Hammer/Greathammer | **CANDIDATO**, com limite/stack do provider. |
| Double damage / double strike / execute | Simply Swords | Implicit provider | **ALTO RISCO**; só integrar com deduplicação explícita. |
| Attack-speed proc | Simply Swords | Implicit provider | **CANDIDATO**, cruzar Epic Fight/stamina/cadência. |
| Extra loot | Simply Swords | Cutlass Implicit | **ALTO RISCO ECONÔMICO**; exigir anti-dup e evento autoritativo. |
| Runic Power | Simply Swords | GemPower/Runic stack state | **PROGRESSÃO NATIVA AUTORITATIVA**; integração mínima por padrão. |
| Greater Runic Power | Simply Swords | roll/provider state | **PROGRESSÃO NATIVA AUTORITATIVA**; não conceder/rerrolar gratuitamente. |
| Awakening level 0–8 | Simply Swords | `AwakeningApi` | **PROGRESSÃO NATIVA AUTORITATIVA**. |
| Unlock de ability | Simply Swords | `AwakeningApi.isAbilityUnlocked` | **PROGRESSÃO NATIVA AUTORITATIVA**. |
| Unique passive/active | arma Unique / addon | item class + ability API | **CANDIDATO ESPECÍFICO** somente quando uma perk tiver razão temática e hook provado. |
| Runefused/Netherfused sockets | Simply Swords | `GemPowerComponent`, socket API | **PROGRESSÃO NATIVA AUTORITATIVA** / integração mínima. |
| Gem power | Simply Swords / addon power | `GemPowerRegistry` + component IDs | **CANDIDATO**, jamais duplicar scaling de Awakening. |
| Simply More weapon types | Simply More | Implicits/addon state | **COBERTO POR SISTEMA UNIVERSAL** por padrão; perk nominal só com mecânica própria justificada. |
| Simply More Uniques | Simply More | item/effect específico | **SEM HOOK SEGURO** para Uniques não auditados do alpha; validar individualmente. |
| Material bridge do Integrated Simply Swords | material source + Simply Swords | item/tier/type | **COBERTO POR SISTEMA UNIVERSAL** por padrão. |
| Cursium Accursed Rage | Simply Swords: Cataclysm | Cursium item/effect/config | **CANDIDATO A BRIDGE/PERK**, se árvore pertinente justificar. |
| Ignitium Blazing Brand/lifesteal | Simply Swords: Cataclysm | Ignitium item/effect/config | **CANDIDATO A BRIDGE/PERK**, com anti-double-heal. |
| Witherite Pulse/Smite | Simply Swords: Cataclysm | Witherite item/effects/config | **CANDIDATO A BRIDGE/PERK**, preservando charge/cooldown/threshold. |
| Modern tooltip | Simply Tooltips | client render/data | **NÃO DEVE SER INTEGRADO** como perk. |
| Epic Fight preset | Epic Fight Compat | capability/preset mapping | **BRIDGE DE COMBATE**, não provider dos procs de Simply Swords. |

## 15.13 Contrato mínimo para qualquer perk que tocar este stack

Antes de o Chat 1 aprovar uma integração, registrar explicitamente:

1. **Provider/authority:** Simply Swords, Simply More, Simply Cataclysm, material source ou Epic Fight — sem fundir owners diferentes.
2. **Boundary/API/query/hook:** componente, API pública, item class, status/effect ou evento real da versão instalada.
3. **Causalidade:** hit normal, outgoing damage, incoming damage, post-hit, swing, active ability, tick de power, nível de Awakening etc.
4. **Deduplicação:** como impedir que extra hits, AoE, chain, double strike, Epic Fight ou ability damage reapliquem Implicit/gem/enchant indevidamente.
5. **Provider-native first:** manter Runic Forge, Runic Tablet, pity, cooldown, Awakening, sockets e rolls sob controle nativo.
6. **Fallback:** somente degradação segura. Se a API/classe esperada não existir, omitir a integração daquele provider.
7. **Fail-closed:** nunca substituir ausência de hook por `+dano`, `+velocidade`, `+loot`, `+lifesteal` ou outro bônus genérico.
8. **Config-awareness:** valores default deste capítulo não autorizam hardcode; ler config/runtime quando o efeito depender do número real.
9. **Anti-double-count:** Epic Fight animation hit, Simply Swords Implicit, Unique ability, gem power e addon trait podem coexistir no mesmo ataque.
10. **Teste dedicado:** validar servidor dedicado quando qualquer integração usar API/common code; não importar classes client-only de tooltip.

## 15.14 Riscos concretos para design/implementação

- **Dano multiplicativo:** Katana double damage, Warglaive double strike e execução da Scythe podem explodir escalonamento quando combinados com perks percentuais.
- **Proc fan-out:** AoE/chain/extra hit pode transformar “uma vez por swing” em “uma vez por entidade” se o RPG Skill Tree observar o evento errado. Simply More 1.3.0 corrigiu explicitamente vários casos nessa direção.
- **Iframe bypass:** helpers de ability podem bypassar iframes intencionalmente e suprimir Implicit. Reproduzir o dano fora desse pipeline muda gameplay.
- **Awakening duplicado:** `scaleAbilityDamage`, `scaleAbilityValue`, `gemPowerScaledDamage` e `gemPowerScaledValue` já incorporam curvas pertinentes; uma segunda multiplicação é bug.
- **Cooldown ownership:** active ability possui cooldown do item/provider; uma perk não deve resetar ou encurtar genericamente sem contrato explícito.
- **Sockets:** não conceder segunda camada de sockets da skill tree; utilizar o sistema nativo e seus IDs/componentes.
- **Storage compatibility:** não comparar identity de registry holder de gem power; a linha 1.70 migrou para IDs justamente para persistência estável.
- **Alpha do Simply More:** Uniques podem estar sem funcionalidade ou sujeitos a crash. Nenhum efeito individual deve ser assumido sem validação do artifact instalado.
- **Material ≠ perk:** Integrated Simply Swords e boa parte de Simply Cataclysm expandem cobertura de materiais. Material novo não implica automaticamente node novo.
- **Tooltip ≠ estado:** nunca parsear tooltip para descobrir Implicit/Awakening/gem; usar API/componente real.
- **Epic Fight ≠ owner da arma:** preset/moveset não transfere ownership de Implicit, Unique effect ou gem power.

## 15.15 Evidência técnica consultada

Fontes upstream usadas para esta especificação:

- Simply Swords — documentação principal: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/docs/main.mdx
- Weapon Types & Progression: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/docs/weapon-types/weapon_types.mdx
- Weapon Implicits — player docs: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/docs/weapon-types/weapon_implicits.mdx
- Weapon Implicits — developer API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/weapon-implicits.md
- Awakening API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/awakening.md
- Gem sockets/powers API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/gem-sockets-and-powers.md
- Combat/damage API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/combat-and-damage.md
- Unique weapon walkthrough: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/unique-weapon-walkthrough.md
- SimplySwordsAPI: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/common/src/main/java/net/sweenus/simplyswords/api/SimplySwordsAPI.java
- Client / Simply Tooltips integration: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/client-integration.md
- Simply Swords 1.70.2 NeoForge release: https://www.curseforge.com/minecraft/mc-mods/simply-swords/files/8746001
- Simply More project: https://www.curseforge.com/minecraft/mc-mods/simply-more
- Simply More 1.3.0 ALPHA installed line: https://www.curseforge.com/minecraft/mc-mods/simply-more/files/8721021
- Integrated Simply Swords 1.4.0: https://modrinth.com/mod/integrated-simply-swords/version/1.4.0%2B1.21.1-neoforge
- Epic Fight - Mod Compat: https://www.curseforge.com/minecraft/mc-mods/epic-fight-mod-compat
- Simply Swords: Cataclysm repository: https://github.com/Cephelo/SimplyCataclysmMod
- Cursium implementation: https://github.com/Cephelo/SimplyCataclysmMod/blob/master/src/main/java/dev/cephelo/simplycataclysm/item/CursiumSwordItem.java
- Ignitium implementation: https://github.com/Cephelo/SimplyCataclysmMod/blob/master/src/main/java/dev/cephelo/simplycataclysm/item/IgnitiumSwordItem.java
- Witherite implementation: https://github.com/Cephelo/SimplyCataclysmMod/blob/master/src/main/java/dev/cephelo/simplycataclysm/item/WitheriteSwordItem.java

## 15.16 Regra operacional para o Chat 1

Ao encontrar uma perk de combate/armas, o Chat 1 deve cruzá-la com este capítulo quando ela puder afetar **dano de arma, attack speed, armor penetration/sunder, backstab, bleed, execute, extra hit, loot on-hit, Runic Power, Unique ability, Awakening, sockets/gem powers ou materiais Cataclysm**.

A pergunta obrigatória não é apenas “a perk funciona com Simply Swords?”, mas:

> **qual camada do stack é a authority do efeito, qual evento real deve ser observado e como a integração evita duplicar Implicit, ability, gem power, addon trait ou pipeline do Epic Fight?**

Sem resposta comprovável para essas três partes, a integração deve permanecer **FAIL-CLOSED** e voltar para pesquisa em vez de receber um bônus genérico substituto.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/18-fontes-e-referencias-tecnicas.md -->

# 18. Fontes e referências técnicas — Gameplay

O GitHub mantém o índice transversal de fontes do recorte Gameplay dividido em blocos. Em auditoria, preferir source/docs da versão instalada, depois release/projeto, depois modlist runtime e só então descrição consolidada. Não inventar slugs/URLs quando a origem exata não estiver comprovada.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/gameplay/19-atualizacao-modlist-2026-09-07.md -->

# 19. Atualização da modlist — 2026-09-07

> Este capítulo incorpora ao guia Gameplay/Sistemas o delta físico reconciliado em 07/09/2026.

## Novas entradas do domínio

### Bosses of Mass Destruction — 1.3.3
`BOMD-NeoForge-1.21-1.3.3.jar`

Boss provider de grande porte com arenas/estruturas, padrões de ataque, fases e loot. Antes de qualquer perk baseada nele, validar hitboxes/combate real com Epic Fight e usar hook causal concreto.

### Bosses'Rise — 2.1.2
`block_factorys_bosses-2.1.2-neo-1.21.1.jar`

Boss/RPG provider com múltiplas fases, arenas, loot e mobs associados. Coexistência com outros boss providers deve ser julgada por densidade, dificuldade e progressão, não por semelhança temática.

### CERBON's API — 1.3.0
`CerbonsAPI-NeoForge-1.21-1.3.0.jar`

Dependência confirmada de Bosses of Mass Destruction. **Não é provider de perk.**

### Integrated Mowzie's Mobs — 1.1.0
`IMM v1.1.0-1.21.1.jar`

Retrabalha placement/integração de estruturas do Mowzie's Mobs para worldgen modded. O runtime local 1.1.0 é authority física; a publicação pública exata dessa build não deve ser inferida. Validar novos chunks e conflitos de placement.

### Simple Inventory Sorter — 24.0.24
`inventorysorter-1.21.1-24.0.24.jar`

QoL de inventário; não cria progressão/atributo de personagem.

### [Let's Do] Furniture — 1.1.4
`letsdo-furniture-neoforge-1.1.4.jar`

Conteúdo de móveis/interiores do ecossistema [Let's Do]. Não é provider mecânico de perk por si só.

### MineColonies: Jade crops — 1.1.1300
`mcjadecrops-1.1.1300.jar`

Bridge client-side de HUD Jade ↔ crops do MineColonies. Não altera crescimento e não é provider de agricultura/progressão.

## Remoções confirmadas

`alexscaves-2.0.2.jar`, `alexsmobs-1.22.9.jar`, `spore_1.21.1_2.2.0j_neo.jar` e `Infnexus-2.0.4-1.21.1.jar` foram removidos. Os blockers por `alexscaves`/`alexsmobs` duplicados estão resolvidos.

## Updates de interesse

Amendments `2.1.10`, Alex's Mobs Continued `2.1.10`, Dynamic Trees BetterEnd/BetterNether `2.2.0`, InsaneLib `2.4.32.0`, Lithostitched `1.8.0+beta6`, MineColonies `1.1.1377`, Moonlight `3.6.3`, Photon `2.2.6.a`, Polytone `4.2.0`, Puzzles Lib `21.1.59`, Supplementaries `3.9.8` e Vampirism `1.10.13`.

## Regra para perks

Nenhum mod novo vira provider automaticamente. Bibliotecas, HUD, bridges e worldgen compatibility continuam não-provider por padrão. Boss providers só entram em contratos após auditoria de hooks concretos.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/MODLIST-DELTA-2026-09-07.md -->

# Delta físico da modlist — 2026-09-07

> **AUTORIDADE OPERACIONAL DO SNAPSHOT:** a modlist física atual possui **612 entradas top-level**, incluindo NeoForge + **611 JARs**. Dependências `jarjar` embutidas não contam como mods top-level.

## Fechamento quantitativo

- baseline físico de 06/09: **607** entradas top-level;
- updates do mesmo mod: **21**;
- adições reais: **9**;
- remoções físicas reais: **4**;
- saldo líquido: **+5**;
- estado físico atual: **612** entradas top-level;
- Notion reconciliado: **612 `Instalado`**, **612 JARs físicos distintos** e **613/613 registros `Verificado`**;
- runtime version audit: **605/605** JARs que declaram `modVersion` batem exatamente com `Versão 1.21.1` no Notion;
- **7/7** JARs sem `modVersion` físico permanecem sem runtime version inferida no Notion.

## 21 updates

- Alex's Mobs Continued: `2.1.9 → 2.1.10`;
- Amendments: `2.1.9 → 2.1.10`;
- Apotheosis: `8.7.0 → 8.8.0`;
- Ars 'n' Spells: `3.2.4 → 3.3.0`;
- Create: Bits 'n' Bobs: `2.3.0 → 2.3.1`;
- Create: Copycats+: `3.0.8 → 3.0.9`;
- Create: Cyber Goggles: `8.5.1 → 8.5.2`;
- Dynamic Trees - BetterEnd: `2.1.0 → 2.2.0`;
- Dynamic Trees - BetterNether: `2.1.0 → 2.2.0`;
- GTBC's SpellLib: `2.1.0 → 2.2.0`;
- InsaneLib: `2.4.31 → 2.4.32`;
- Lithostitched: `1.8.0+beta4 → 1.8.0+beta6`;
- Lychee Tweaker: `6.6.1 → 6.7.0`;
- MineColonies: `1.1.1376 → 1.1.1377`;
- Moonlight Lib: `3.6.1 → 3.6.3`;
- Petrolpark's Library: `1.5.8 → 1.5.9`;
- Photon: `2.2.5 → 2.2.6.a`;
- Polytone: `4.1.0 → 4.2.0`;
- Puzzles Lib: `21.1.56 → 21.1.59`;
- Supplementaries: `3.9.7 → 3.9.8`;
- Vampirism: `1.10.12 → 1.10.13`.

## 9 adições

- `BOMD-NeoForge-1.21-1.3.3.jar` — Bosses of Mass Destruction;
- `block_factorys_bosses-2.1.2-neo-1.21.1.jar` — Bosses'Rise;
- `CerbonsAPI-NeoForge-1.21-1.3.0.jar` — CERBON's API;
- `IMM v1.1.0-1.21.1.jar` — Integrated Mowzie's Mobs;
- `inventorysorter-1.21.1-24.0.24.jar` — Simple Inventory Sorter;
- `ironsable-wind-1.0.0.jar` — Ironsable x Wind's Spellbooks;
- `letsdo-furniture-neoforge-1.1.4.jar` — [Let's Do] Furniture;
- `mcjadecrops-1.1.1300.jar` — MineColonies: Jade crops;
- `morerelics-1.7.7-1.21.1.jar` — More Relics.

## 4 remoções físicas

- `alexscaves-2.0.2.jar`;
- `alexsmobs-1.22.9.jar`;
- `spore_1.21.1_2.2.0j_neo.jar`;
- `Infnexus-2.0.4-1.21.1.jar`.

Os antigos bloqueios de discovery por IDs duplicados `alexscaves` e `alexsmobs` estão fisicamente resolvidos no snapshot atual.

## Curadoria vigente

- **Alex's Caves Continued 1.0.9 — Manter**; é a única implementação `alexscaves` presente.
- **Alex's Mobs Continued 2.1.10 — Manter**; é a única implementação `alexsmobs` presente.
- **Create: Bits 'n' Bobs 2.3.1 — Manter**; o conflito visual com Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 continua sendo risco aceito e precisa de render-test.
- **More Relics 1.7.7 — Manter**; o pack usa Relics 0.12.8 apesar do upstream não declarar esse suporte. Integrações/perks provider-specific permanecem **FAIL-CLOSED** até teste real.
- **Integrated Mowzie's Mobs 1.1.0 — Manter**; o runtime local é autoridade física, mas a publicação pública exata dessa build não deve ser inferida.

## Regra

Presença física, filename e runtime version vêm da modlist auditada. Decisão curatorial é uma camada separada. Update de versão não revalida automaticamente hook/API de perk.
