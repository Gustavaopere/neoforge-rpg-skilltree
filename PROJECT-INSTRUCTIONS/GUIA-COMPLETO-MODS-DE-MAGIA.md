# GUIA COMPLETO — Mods de Magia | NeoForge 1.21.1

Sistemas mágicos, recursos arcanos/ocultos, escolas, summons, rituais, equipamentos e integrações mágicas.

**Fonte canônica:** diretório versionado no GitHub.

**Snapshot consolidado deste anexo:** **2026-09-07**, modlist física com **612 entradas top-level incluindo NeoForge**. O recorte mágico possui **103 referências temáticas** após o delta atual.

> `CURRENT-MODLIST.md` + delta 07/09 prevalecem sobre versões históricas embutidas nos capítulos. Update de versão não prova que um hook antigo continua válido.

## Índice operacional

- Reconciliação atual da modlist
- 00–18. Corpo histórico consolidado
- 19A. Atualização da modlist mágica — 07/09
- 19B. Ecossistema Apothic e compats
- 20. Scripting/KubeJS/EMF
- 21. Fontes e referências técnicas

## Regras de manutenção

- provider-native first;
- bridge não vira escola;
- UI/VFX não vira authority de cast;
- More Relics permanece `Manter`, mas provider-specific fail-closed até validação com Relics 0.12.8;
- GitHub continua fonte canônica editorial.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/CURRENT-MODLIST.md -->

# Reconciliação atual da modlist — Mods de Magia

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-07.** O estado físico atual do pack é o snapshot de 06/09/2026 mais o delta de 07/09.

A modlist física atual contém **612 entradas top-level**, incluindo NeoForge. O recorte mágico do snapshot de 06/09 tinha **101 JARs**; em 07/09 entram dois módulos mágicos/cross-domain novos, totalizando **103 referências temáticas** antes de sobreposições com outros guias.

## Updates mágicos

- Apotheosis: `8.7.0 → 8.8.0`.
- Ars 'n' Spells: `3.2.4 → 3.3.0`.
- GTBC's SpellLib: `2.1.0 → 2.2.0` (`2.2.0-1.21.1` no runtime).
- Vampirism: `1.10.12 → 1.10.13`.

Updates de versão não revalidam automaticamente hooks/APIs de perks.

## Novos módulos mágicos/cross-domain

### Ironsable x Wind's Spellbooks — 1.0.0

`ironsable-wind-1.0.0.jar` — bridge Wind's Spellbooks ↔ IronSable/Sable. É compatibilidade de causalidade física, não nova escola nem provider autônomo.

### More Relics — 1.7.7 — MANTER / RISCO ACEITO

`morerelics-1.7.7-1.21.1.jar` está fisicamente instalado e a decisão curatorial vigente é **Manter**.

A documentação upstream para NeoForge 1.21.1 não declara suporte a Relics 0.11/0.12, enquanto o pack usa `relics-1.21.1-0.12.8.jar`. A decisão de manter não transforma esse desvio em compatibilidade comprovada. Contratos provider-specific permanecem **fail-closed até teste real** de carregamento, Curios, persistência, progressão nativa e hook pretendido.

## Regra operacional

- Este fechamento + o delta físico de 07/09 fixam a identidade física atual do domínio mágico.
- Bridge, biblioteca, UI ou compatibilidade não vira provider apenas por estar no recorte mágico.
- `Manter` More Relics não autoriza contrato provider-specific sem validação contra Relics 0.12.8.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/00-visao-geral.md -->

<!-- Guia temático canônico versionado no GitHub | referência atual de presença/JAR/versão: modlist.txt reconciliada em 2026-08-30 -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **RECONCILIADO COM A MODLIST ATUAL — 2026-08-30:** presença, JARs e versões do eixo mágico foram revalidados contra a `modlist.txt` atual. [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) registra **94 JARs mágicos/cross-domain** e prevalece quando um capítulo histórico ainda cita uma versão anterior. Entre os drifts incorporados estão Ars 'n' Spells `3.2.2`, Iron's Spells 'n Spellbooks `3.16.3`, Immersive Portal - Iron's Spells Addon `1.0.1`, Recolor `1.2.5+1.21.1`, GTBC's SpellLib `2.1.0-1.21.1`, Apprentice's Codex `0.9.7.1` e Create: Enchantment Industry `2.5.3b`.

> Este guia é um **catálogo descritivo** dos mods ligados à magia que aparecem na modlist atual. O foco é explicar o que cada um acrescenta ao jogo, como sua mecânica funciona e a qual ecossistema mágico ele pertence. Compatibilidade, biblioteca ou UI não são promovidas automaticamente a provider mecânico de perk.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/01-sistemas-magicos-principais.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 1. Sistemas mágicos principais

## Ars Nouveau — 5.13.1

`ars_nouveau-1.21.1-5.13.1.jar`
**Ars Nouveau** é um sistema completo de magia modular e automação mágica. O spellcraft é construído pelo próprio jogador a partir de **glyphs**: formas definem a maneira de aplicar um feitiço, efeitos definem a ação executada e augments alteram propriedades como potência, alcance, quantidade, duração ou comportamento. Isso permite criar desde ataques e mobilidade até manipulação de blocos, coleta, suporte e rotinas utilitárias sem depender apenas de uma lista fixa de habilidades.
A progressão gira em torno do **Spell Book**, aprendizado de glyphs e do recurso **Source**. Source pode ser produzido, armazenado e movimentado por uma infraestrutura própria e alimenta rituais, dispositivos e automações. O mod inclui Spell Turrets, Source Jars, relays, Enchanting Apparatus, rituais, equipamentos mágicos, artefatos, familiars e criaturas auxiliares como Starbuncles e Drygmys, fazendo a magia existir tanto no personagem quanto na base e nas linhas de produção.
Também funciona como plataforma para um grande ecossistema de addons. Esses addons podem acrescentar novas escolas e combinações elementais, métodos de casting, lógica e transporte remoto, automação com criaturas, integração com Create, portais, armazenamento e compatibilidade com outros sistemas mágicos e físicos.

## Iron's Spells 'n Spellbooks — runtime 1.21.1-3.16.3

`irons_spellbooks-1.21.1-3.16.3.jar`
**Iron's Spells 'n Spellbooks** é um sistema de magia de combate com feitiços prontos, progressão por equipamento e forte estrutura de RPG. Os spells são organizados em **escolas de magia**, possuem níveis e raridades e são equipados em spellbooks com quantidade limitada de slots. O casting utiliza mana, cooldowns, cast time e atributos próprios, incluindo poder e resistência por escola, de modo que arma, armadura, spellbook, acessórios e atributos alteram diretamente o desempenho mágico.
A progressão não fica restrita à interface de feitiços. Scrolls, materiais, spellbooks, armas, armaduras e curios entram por crafting, exploração, loot e comércio; o mod também adiciona spellcasters, inimigos, aliados, comerciantes, bosses e estruturas. Há sistemas de aprimoramento de equipamento e conteúdo pensado para fazer magia participar do combate, da exploração e do loot do mundo como uma camada RPG persistente.
O mod ainda atua como base de um ecossistema extenso de addons. Eles acrescentam escolas e arquétipos, familiars, necromancia, geomancia, magia astral e musical, conteúdo ligado ao End e a outros mods, novas estruturas e equipamentos, além de interfaces e bridges com sistemas como Ars Nouveau, Create, Goety, Epic Fight, Apotheosis e Sable.

## Goety — 3.1.4

`goety-3.1.4.jar`
**Goety** é um sistema amplo de magia sombria, necromancia e controle de servos. Sua economia mágica usa **Soul Energy**, obtida principalmente a partir de mortes e armazenada para alimentar feitiços, artefatos e dispositivos. O jogador utiliza wands e staffs em conjunto com focos para acessar diferentes poderes, enquanto a progressão libera conhecimento, rituais e ferramentas de magia cada vez mais avançados.
A característica central é a criação e o comando de **servants/minions**. O mod inclui mortos-vivos, criaturas mágicas e outras invocações que podem lutar e executar funções sob controle do jogador, além de mobs hostis, bosses, estruturas, equipamentos e tesouros próprios. A necromancia, portanto, não é apenas temática: ela forma um sistema de entidades persistentes e de gerenciamento de exército.
Goety também possui rituais, fabricação e infusão de artefatos, brewing próprio e máquinas ou construções mágicas alimentadas por Soul Energy. Addons instalados expandem especificamente sua relação com L_Ender's Cataclysm e Iron's Spells sem substituir o sistema-base.

## Malum — 1.8.2

`malum-1.21.1-1.8.2.jar`
**Malum** é um sistema de **spirit arcana** em que espíritos extraídos de criaturas e do mundo funcionam como matéria-prima mágica. Diferentes tipos de spirit representam propriedades distintas e entram em receitas, equipamentos e processos arcanos, fazendo a progressão depender da coleta, combinação e transformação dessas essências em vez de uma barra convencional de spellcasting.
O mod possui crafting arcano próprio, runas, totens, armas, ferramentas, armaduras e artefatos. Os **Spirit Rites** formam uma camada ritualística capaz de consumir e manipular spirits para produzir efeitos no mundo, enquanto outros sistemas do mod ligam o equipamento e a progressão do personagem às propriedades espirituais coletadas.
A identidade mecânica é de ocultismo e alquimia espiritual: combate e exploração alimentam uma cadeia de recursos que volta para ritos, gear e novas capacidades. No pack, **Gaze** e **Malum: Vestis** ampliam esse mesmo ecossistema em vez de formar sistemas mágicos independentes.

## Eidolon: Repraised — 0.5.0.2

`eidolon_repraised-1.21.1-0.5.0.2.jar`
**Eidolon: Repraised** é um sistema de ocultismo que combina **alquimia, teurgia, necromancia, rituais e artifice**. A progressão é guiada por conhecimento e por um livro próprio, em vez de apresentar todo o conteúdo de uma vez. Seus rituais usam símbolos, altares, braseiros, soul shards e outros componentes para conjurar entidades, aplicar wards e realizar processos mágicos persistentes.
A teurgia introduz uma relação ritual com poderes sombrios e luminosos: altares, orações, oferendas e reputação fazem parte do acesso a habilidades e conteúdo de vertentes diferentes. Isso cria caminhos sagrados e profanos dentro do mesmo sistema, com consequências e recursos próprios.
A parte de artifice inclui alquimia em crucibles, fabricação de itens mágicos, ferramentas, armaduras e curios. O resultado é uma magia de preparação e infraestrutura ritual, com mobs e equipamentos associados. A build `0.5.0.2` também possui correções específicas para partículas em sublevels Sable/Aeronautics.

## Hexalia — filename 1.3.6 / runtime 1.3.5

`hexalia-neoforge-1.3.6.jar`
**Hexalia** é um sistema de magia natural e bruxaria centrado em plantas, ervas e alquimia rústica. A progressão envolve encontrar e cultivar ingredientes, refiná-los com ferramentas próprias e utilizá-los em **rituais, brewing e transmutação**, fazendo a preparação dos materiais participar diretamente do uso da magia.
O mod adiciona flora encantada, brews capazes de combinar efeitos, idols e processos que manipulam elementos do mundo, além de equipamentos e ferramentas ligados à transformação de recursos. Também acrescenta estruturas e criaturas próprias, incluindo entidades naturais e **Silk Moths**, cuja criação produz Silk Fiber para materiais e vestimentas.
O arquivo instalado é `hexalia-neoforge-1.3.6.jar`; o metadata runtime registrado pela modlist declara `1.3.5`, portanto o catálogo preserva as duas identidades em vez de normalizá-las.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/02-ecossistema-ars-nouveau.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 2. Ecossistema Ars Nouveau

## Ars Additions — 1.21.1-21.3.0

`ars_additions-1.21.1-21.3.0.jar`
**Ars Additions** amplia Ars Nouveau principalmente em mobilidade, armazenamento remoto, Source e automação. O sistema de warp acrescenta estruturas e dispositivos próprios, incluindo Warp Nexus e recursos capazes de manter referências de destinos para teleporte e retorno. Glyphs como **Mark** e **Recall** permitem registrar posições e reutilizá-las como parte de spells e ferramentas.
O addon também estende a infraestrutura de Source e inventários. Há recursos para acesso remoto ao Storage Lectern, inclusive entre dimensões quando estabilizado, além de soluções para manipular Source a distância. Outros componentes ampliam exploração, localização e utilidades do Ars sem criar um segundo sistema mágico independente.
A camada de automação inclui ferramentas para trabalhar com o Enchanting Apparatus e outros processos do Ars, além de recursos configuráveis de manutenção de área/chunks e blocos decorativos associados ao ecossistema.

## Ars Controle — 1.21.1-1.6.15

`ars_controle-1.21.1-1.6.15.jar`
**Ars Controle** adiciona lógica, endereçamento remoto e automação avançada à infraestrutura de Ars Nouveau. O **Warping Spell Prism** pode redirecionar projéteis mágicos para coordenadas configuradas, inclusive em outros locais ou dimensões, permitindo separar fisicamente o ponto onde um spell é produzido do lugar onde ele atua.
A **Scryer's Linkage** representa remotamente outro bloco e permite que máquinas ou dispositivos interajam com recursos desse destino, incluindo itens, fluidos, energia e sinais de redstone. Sensores e filtros complementam essa camada ao observar estados do mundo e condicionar comportamentos.
O resultado é uma rede de controle mágico em que turrets, prisms, linkages e outros componentes podem consultar condições, encaminhar recursos e disparar efeitos automaticamente. O addon também possui integrações opcionais para configuração e controle programável quando os mods correspondentes estão presentes.

## Ars Creo — 5.4.0

`ars_creo-1.21.1-5.4.0.jar`
**Ars Creo** é uma integração estrutural entre Ars Nouveau e Create. Ela faz componentes mágicos continuarem funcionais dentro de contraptions e cria conversões entre a infraestrutura de Source e a linguagem cinética do Create. **Spell Turrets** podem ser montadas em estruturas móveis e executar spells enquanto a contraption está em operação, com diferentes comportamentos de ativação.
**Source Jars** podem viajar com contraptions e continuar servindo como reserva para turrets e outros componentes compatíveis. O addon também introduz geração cinética associada a Starbuncles e interações entre dispositivos do Ars e sistemas móveis do Create.
Assim, Source, criaturas auxiliares, turrets e automação mágica deixam de estar restritos a uma base estática e passam a participar de máquinas, veículos e mecanismos montados com Create.

## Ars Technica — 2.7.6

`ars_technica-1.21.1-2.7.6.jar`
**Ars Technica** adiciona uma vertente de technomancy baseada em Ars Nouveau e Create. Seus glyphs reproduzem ou reinterpretam processos mecânicos dentro de spells, incluindo operações equivalentes a **crushing, pressing, polishing, packing, fusing, superheating e outras transformações**. Um feitiço pode, portanto, executar etapas de processamento que normalmente seriam realizadas por máquinas Create.
O **Source Motor** faz a conversão inversa: consome Source para produzir potência cinética, com parâmetros de rotação e consumo configuráveis. O addon também inclui ferramentas como o Runic Spanner para manipular componentes mágicos, equipamentos e curios de Technomancer e focos que alteram ou favorecem processos de transmutação.
Essa camada acrescenta conteúdo próprio de spellcraft e equipamento, além da simples compatibilidade entre os dois mods: a automação mecânica passa a existir também como efeito mágico e Source pode assumir papel direto na geração cinética.

## Ars Elemental — 0.7.10.1

`ars_elemental-1.21.1-0.7.10.1.jar`
**Ars Elemental** reorganiza parte do spellcraft de Ars Nouveau em torno de **escolas elementais** e adiciona glyphs, focos, equipamentos e efeitos ligados a essas afinidades. Os foci funcionam como peças centrais da especialização: versões menores permitem entrar cedo em uma escola com vantagens e limitações próprias, enquanto versões maiores aprofundam o domínio elemental e modificam o comportamento de spells compatíveis.
O addon também adiciona equipamentos e curios, incluindo acessórios que reforçam dano ou efeitos de determinadas escolas e concedem passivos relacionados ao elemento. Há ainda especializações que extrapolam os quatro elementos básicos e trabalham com afinidades adicionais do ecossistema.
Além de acrescentar conteúdo próprio, Ars Elemental fornece a base funcional usada por **Ars Elemancy**, que combina essas afinidades em especializações híbridas.

## Ars Elemancy — 1.18.3

`ars_elemancy-1.21.1-1.18.3.jar`
**Ars Elemancy** expande Ars Elemental com especializações baseadas na **combinação de elementos**. Em vez de limitar o personagem a uma única afinidade, adiciona conjuntos de armadura e spell foci que representam pares elementais, formando linhas como Cindermancer, Lavamancer, Miremancer, Siltmancer, Tempestmancer e Vapormancer.
Cada combinação funciona como uma identidade híbrida derivada das escolas do Ars Elemental. Acima delas existe a progressão de **Elemancer**, que reúne múltiplas afinidades em equipamentos e focos de domínio mais amplo.
O mod depende diretamente de Ars Nouveau e Ars Elemental e estende o modelo de escolas e especializações desses sistemas; não cria uma camada de spellcasting separada.

## Not Enough Glyphs — 4.6.1

`not_enough_glyphs-1.21.1-4.6.1.jar`
**Not Enough Glyphs** amplia o vocabulário de Ars Nouveau com glyphs próprios e versões modernizadas de ideias de addons anteriores. O conjunto cobre utilidades, manipulação de entidades e blocos, alimentação, montaria, redirecionamento e outras ações que podem ser combinadas dentro da gramática normal de spells do Ars.
Um de seus sistemas próprios são as **Contingencies**, que armazenam um feitiço para ser disparado quando determinada condição ocorre. Outro é o **SpellBinder**, alternativa ao spellbook tradicional que organiza um conjunto maior de spells como tomes ou parchments e pode ser personalizado por capas e threads com efeitos específicos.
O mod procura evitar duplicação técnica quando detecta addons que fornecem originalmente certos glyphs, desativando ou ajustando conteúdo conforme a presença deles. Dessa forma, funciona ao mesmo tempo como expansão de spellcraft e como camada de organização para catálogos grandes de feitiços.

## Ars Zero — 2.0.2

`ars_zero-1.21.1-2.0.2.jar`
**Ars Zero** expande principalmente a forma de **conjurar e manter spells** no Ars Nouveau. O Spell Staff permite casting contínuo enquanto o uso é mantido, e o addon introduz contextos e glyphs projetados para feitiços que permanecem ativos ou são reaplicados de maneira controlada em vez de funcionar apenas como lançamentos discretos.
A linha 2.x acrescenta staffs especializados com modelos, afinidades e presets próprios, criando instrumentos de casting com comportamento definido além do spellbook convencional. Esses staffs podem representar estilos diferentes de conjuração mesmo quando continuam usando glyphs e mana do Ars Nouveau.
O addon também adiciona glyphs e conteúdo complementar e possui suporte a integrações atuais do ecossistema Ars, incluindo compatibilidades necessárias para ambientes físicos móveis quando aplicável.

## Starbunclemania — 1.5.7

`starbunclemania-1.21.1-1.5.7.jar`
**Starbunclemania** transforma Starbuncles em trabalhadores especializados para logística e automação. Além do transporte convencional de itens, o addon cria jobs e acessórios que permitem movimentar diferentes tipos de recurso por rotas configuradas, mantendo a lógica de criaturas trabalhadoras do Ars Nouveau.
Entre as especializações estão transporte de **fluidos**, energia e outros recursos suportados, usando variantes e recipientes próprios para cada função. O mod também trabalha com **Source liquefeito**, acrescentando uma forma adicional de representar, armazenar e processar o recurso mágico dentro de sistemas automatizados.
O resultado é uma infraestrutura de logística mágica em que Starbuncles assumem papéis comparáveis a componentes de uma rede de transporte, mas continuam sendo entidades com tarefas e vínculos próprios do ecossistema Ars.

## Ars 'n' Spells — 3.3.0

`ars_n_spells-3.3.0.jar`
**Ars 'n' Spells** é uma integração profunda entre Ars Nouveau e Iron's Spells 'n Spellbooks. Um dos eixos é a **unificação ou coordenação de mana**, com modos configuráveis que podem priorizar um dos sistemas, operar de forma híbrida, mantê-los separados ou desabilitar a integração. Regeneração, custos e apresentação das barras podem ser ajustados de acordo com esse modo.
O addon também conecta atributos e equipamento: bônus de mana, spell power, acessórios e outras propriedades podem atravessar a fronteira entre os dois ecossistemas conforme a configuração. Há mecânicas adicionais de sinergia, como efeitos relacionados a níveis altos de mana e interação com infraestrutura de Source.
No lado do spellcraft, o **Spell Loom** e mecanismos de exportação, binding e inscrição permitem converter ou vincular spells para uso através de interfaces e itens do outro sistema. Assim, spellbooks, scrolls, seleção de spells e feitiços construídos no Ars podem participar de um fluxo mágico compartilhado em vez de permanecerem completamente isolados.

## Ars Nouveau: Two-Way Portals — 2.0.0

`ars_two_way_portals-2.0.0.jar`
**Ars Nouveau: Two-Way Portals** transforma warp links do Ars em portais permanentes **bidirecionais**. Um par de estruturas válidas pode ser ligado para permitir ida e volta pelo mesmo vínculo, inclusive entre dimensões, sem exigir que o jogador mantenha dois sistemas independentes de destino.
Os portais podem ser construídos em orientações diferentes e mantêm informações do par associado. Há proteção contra loops imediatos de teleporte e ferramentas para manipular ou desfazer o vínculo quando a estrutura é alterada.
Quando uma implementação compatível de Immersive Portals está disponível, o portal pode usar apresentação contínua/see-through; sem ela, o sistema continua funcionando como teleporte tradicional. A função principal permanece o vínculo espacial bidirecional integrado ao Ars Nouveau.

## Ars Sable — 1.1.2

`ars_sable-1.21.1-1.1.2.jar`
**Ars Sable** é a camada de compatibilidade que faz sistemas do Ars Nouveau reconhecerem **sublevels e espaços físicos do Sable/Aeronautics**. Ela corrige o pathfinding de Starbuncles nesses espaços e adapta a relação entre Source, jars e relays quando origem e destino não pertencem ao mesmo nível convencional do mundo.
Também trata fluxos que dependem de coordenadas persistentes, como retorno de **Planarium**, warp portals e warp scrolls, evitando que posições de um sublevel sejam interpretadas como coordenadas vanilla comuns. A renderização de elementos do Ars em espaços físicos também recebe tratamento específico.
É uma bridge de infraestrutura: não adiciona uma segunda forma de Source nem um novo sistema de física, mas torna as mecânicas existentes do Ars conscientes do modelo espacial do Sable.

## Ars Sophisticated Compatibility — 0.3.0

`arssophisticatedcompat-0.3.0.jar`
**Ars Sophisticated Compatibility** é a ponte específica entre **Ars Nouveau e Sophisticated Storage** presente no pack. Ela adiciona upgrades que fazem inventários Sophisticated participarem diretamente da infraestrutura mágica, em vez de funcionarem apenas como recipientes externos ao sistema de Source.
Entre os recursos estão armazenamento e manipulação de **Source**, funcionalidade de **Source Link**, integração com **Potion Jar** e um mecanismo de reparo de itens encantados alimentado por Source. Esses upgrades transformam storages Sophisticated em componentes ativos de uma base Ars.
O JAR top-level da instância é `arssophisticatedcompat-0.3.0.jar`; a ficha do mod preserva essa identidade mesmo existindo diferenças de nomenclatura em artefatos publicados do projeto.

## Ars Polymorphia — 1.0.3

`ars_polymorphia-1.0.3.jar`
**Ars Polymorphia** integra Polymorph diretamente ao **Storage Lectern** do Ars Nouveau. Quando um mesmo conjunto de ingredientes corresponde a várias receitas válidas, a interface passa a oferecer seleção explícita do resultado em vez de deixar o sistema escolher uma receita conflitante de forma implícita.
A escolha é mantida enquanto os ingredientes relevantes não mudam, permitindo repetir crafts com o mesmo resultado sem selecionar novamente a cada operação. O addon atua somente na resolução e apresentação desses conflitos de receita dentro do crafting do Ars; não adiciona novos recipes ou um sistema de armazenamento próprio.

## Ars Nouveau's Flavors & Delight — 2.2.2

`arsdelight-2.2.2.jar`
**Ars Nouveau's Flavors & Delight** integra os ingredientes e criaturas de Ars Nouveau ao modelo culinário do Farmer's Delight. A versão atual adiciona dezenas de alimentos — mais de cinquenta itens culinários na documentação do projeto — além de ingredientes intermediários e receitas que usam recursos mágicos do Ars em preparação, cozinha e agricultura.
O conteúdo não é apenas uma coleção de comidas decorativas. Há mecânicas que interagem com spells e recursos do Ars, além de ferramentas e processos ligados a ingredientes mágicos e criaturas como Drygmys. A linha 2.2.x também possui integrações adicionais com componentes do ecossistema Ars, incluindo Ars Elemental e madeiras Archwood.
O resultado é uma extensão culinária real do sistema mágico: materiais que normalmente serviriam a spellcraft e automação também entram em cadeias alimentares, pratos e produção agrícola no formato do Farmer's Delight.

## Reliquified Ars Nouveau — 0.8.1

`reliquified_ars_nouveau-1.21.1-0.8.1.jar`
**Reliquified Ars Nouveau** é uma integração entre **Relics** e Ars Nouveau que adiciona acessórios construídos especificamente em torno das mecânicas do Ars. Em vez de apenas aceitar itens mágicos em slots Curios, o addon cria relics cujos efeitos consultam ou modificam recursos como mana, Source e spellcasting.
Esses acessórios entram no sistema de progressão e uso de Relics, mas seus efeitos dependem do contexto mágico do Ars Nouveau. Assim, o equipamento permanente do personagem pode responder ao uso de spells e à infraestrutura de Source sem criar um sistema de magia separado.

## Ars Hex Unity — 5.0.4b

`ars_hex-1.21.1-5.0.4b.jar`
**Ars Hex Unity** é o sucessor de Ars Scalaes e funciona como uma camada de compatibilidade entre Ars Nouveau e outros sistemas mágicos. A integração com **Hexerei** inclui recursos como Magebloom Brush para brooms e Enchanter's Broom capaz de receber spell inscription; com **Malum**, acrescenta glyph/equipamentos e interações com Soul Ward, Magic Proficiency e Spirit Spoils.
O addon também contém compatibilidades cruzadas envolvendo **Iron's Spells** e **Ars Elemental**, permitindo que escolas, resistências e efeitos elementais reconheçam conteúdo dos dois ecossistemas quando os mods correspondentes estão instalados. Não cria um novo sistema de mana ou spellcraft: amplia a interoperabilidade entre sistemas já existentes.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/03-ecossistema-iron-s-spells-n-spellbooks.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 3. Ecossistema Iron's Spells 'n Spellbooks

## SnackPirate's Aeromancy Additions — 1.2.8

`aero_additions-1.2.8.jar`
**SnackPirate's Aeromancy Additions** adiciona ao Iron's Spells uma escola de magia de **Wind/Aeromancy**. A identidade da escola é orientada principalmente a **defesa e agilidade**, usando vento para deslocamento, controle de posição, proteção e manipulação do espaço em combate.
Além dos spells, o addon possui conteúdo e equipamentos ligados à escola para que Aeromancy funcione como especialização própria dentro da progressão do Iron's, com atributos e gear coerentes com o arquétipo em vez de ser apenas um pequeno pacote de ataques elementais.

## Asterism Arcanum — 1.21.1-0.1.0

`asterismarcanum-1.21.1-0.1.0.jar`
**Asterism Arcanum** adiciona ao Iron's Spells uma escola **Astral**, estruturando spells e progressão em torno de estrelas, energia celeste e temática cósmica. A escola amplia o conjunto de afinidades do sistema-base com seus próprios efeitos e identidade de spellcasting.
O addon também acrescenta conteúdo associado à especialização Astral, de modo que a progressão não se resume a encontrar um único scroll: spells e elementos de equipamento formam um arquétipo próprio dentro das regras de mana, spell power e spellbooks do Iron's.

## Backported Spells: Iron's Spells x Vanilla Backport — 0.1.0

`backportedspellbooks-0.1.2.jar`
**Backported Spells: Iron's Spells x Vanilla Backport** leva temas de conteúdo vanilla futuro/backportado para o ecossistema do Iron's Spells. A build `0.1.2` acrescenta equipamentos e recursos associados a essas temáticas, incluindo **Miasmic Staff**, **Quicksilver Spellbook** e **Slime Boots**, além de materiais e minérios relacionados às Sulfur Caves.
Entre os spells adicionados estão **Slime Aspect, Sulfur Clouds, Sulfur Bomb e Sulfur Release**. O equipamento também possui propriedades próprias: o Quicksilver Spellbook é voltado a velocidade de casting/regeneração, enquanto as Slime Boots alteram queda e rebote.
O arquivo instalado é `backportedspellbooks-0.1.2.jar`, mas o metadata interno carregado declara runtime **0.1.0**. O guia preserva as duas informações separadamente: 0.1.2 identifica a build/arquivo e 0.1.0 é a string runtime observada.

## Cataclysm: Spellbooks — 1.1.12-1.21

`cataclysm_spellbooks-1.1.12-1.21.jar`
**Cataclysm: Spellbooks** conecta L_Ender's Cataclysm ao sistema de spellcasting do Iron's Spells. Ataques, poderes, criaturas e materiais associados aos bosses do Cataclysm são reinterpretados como **spells e equipamentos mágicos**, fazendo o conteúdo de boss participar também da progressão de mago.
A ponte mantém as regras do Iron's — mana, escolas, spellbooks, atributos e equipamentos — enquanto usa o Cataclysm como fonte temática e material. Assim, poderes que originalmente pertencem a encontros de boss ganham versões utilizáveis pelo jogador por meio do sistema de magia.
O runtime instalado declara `1.1.12-1.21`; a publicação correspondente da linha 1.21 é tratada separadamente quanto ao canal de maturidade.

## Deeper and Darker: Spellbooks — runtime 1.3.3-1.21.1

`darkermagic-1.3.3-1.21.1-ver.b.jar`
**Deeper and Darker: Spellbooks** integra Deeper and Darker ao Iron's Spells, convertendo materiais, criaturas e temas do Deep Dark em conteúdo de magia e equipamento. A integração se concentra especialmente na vertente **Eldritch**, fazendo a exploração do ecossistema Deeper and Darker alimentar também progressão de spellcasting.
A build instalada é a variante **VER B**, que possui diferenças de atributos em parte do equipamento em relação à variante alternativa. A linha atual também inclui correções relacionadas a summons em dedicated server.
O filename é `darkermagic-1.3.3-1.21.1-ver.b.jar`, enquanto o metadata runtime declara `1.3.3-1.21.1`; o guia mantém a variante do arquivo e a versão runtime como informações distintas.

## Discerning The Eldritch — runtime 1.4.3-1.21

`discerning_the_eldritch-1.4.3-1.21.jar`
**Discerning The Eldritch** aprofunda a escola **Eldritch** do Iron's Spells com um conjunto próprio de spells, armas, armaduras e conteúdo RPG. O foco é transformar a escola em uma especialização completa, com recursos ofensivos, utilitários e equipamento associado à temática de magia incompreensível, sombria e cósmica.
Como utiliza diretamente a infraestrutura do Iron's, seus feitiços seguem mana, spell power, cooldowns e spellbooks do sistema-base, enquanto o gear amplia a identidade do personagem fora do momento do casting. A string runtime instalada é preservada integralmente como **`1.4.3-1.21`**.

## Dreamless Spells — 1.1.9

`dreamless_spells-1.1.9.jar`
**Dreamless Spells** adiciona ao Iron's Spells uma proposta de **anti-magia** centrada na chamada **Empty school**. Seus spells trabalham com vazio, negação e neutralização de magia, formando um arquétipo diferente das escolas elementais e de dano convencional do sistema-base.
O addon também acrescenta armas, armaduras e Curios ligados a essa identidade, permitindo que a especialização exista tanto nos feitiços quanto no equipamento. A build atual depende de Iron's Spells e **Apothic Attributes**, usando a camada de atributos para sustentar parte dessa progressão.

## Ender's Spells and Stuff: Requiem — 0.1.6

`ess_requiem-0.1.6.jar`
**Ender's Spells and Stuff: Requiem** é uma expansão de conteúdo para Iron's Spells com coleção própria de spells, equipamentos e elementos RPG. Seu conteúdo entra diretamente no sistema de escolas, mana e spellbooks do Iron's, ampliando as possibilidades de conjuração e de equipamento sem funcionar como um mod-base separado.
A proposta é acrescentar novas combinações de habilidades e gear dentro do mesmo modelo de progressão do Iron's. A ficha atual registra a build `0.1.6` como conteúdo próprio e distinto dos demais addons, ainda que vários deles compartilhem o domínio geral de novos spells.

## Farmer's Spell 'n Spellbooks — runtime 1.0.5.0-1.21.1

`farmers-spell-n-spellbook-1.0.5.0-1.21.1.jar`
**Farmer's Spell 'n Spellbooks** é um crossover entre Farmer's Delight e Iron's Spells que transforma culinária e ingredientes em parte do spellcasting. Além de receitas cruzadas e alimentos mágicos, o addon introduz conteúdo associado à **School of Gluttony**, conectando efeitos de comida e recursos culinários às regras de escolas e spells do Iron's.
A integração faz a cozinha participar da progressão mágica em vez de existir apenas como fonte de hunger/saturation: ingredientes, pratos e itens temáticos passam a ter relação direta com equipamentos e habilidades da escola. O metadata runtime instalado é preservado integralmente como `1.0.5.0-1.21.1`.

## Fire's Ender Expansion — 2.4.1

`firesenderexpansion-2.4.1.jar`
**Fire's Ender Expansion** amplia o Iron's Spells com uma linha inteira de conteúdo centrada na **Ender class** e na exploração do End. O addon adiciona spells, armaduras, Curios, estruturas e outros elementos próprios ligados à dimensão, fazendo materiais e encontros do End participarem diretamente da progressão mágica.
A progressão combina exploração e equipamento: estruturas e conteúdo localizado no End fornecem contexto para adquirir ou desenvolver recursos da especialização, enquanto spellbooks e atributos do Iron's continuam controlando o casting. A build instalada `2.4.1` é a release NeoForge 1.21.1 correspondente.

## GTBC's Geomancy Plus — runtime 1.1.0-1.21.1

`gtbcs_geomancy_plus-1.1.0-1.21.1.jar`
**GTBC's Geomancy Plus** adiciona ao Iron's Spells uma especialização de **Geomancy**, voltada a terra, rocha e manipulação geológica. O addon inclui uma escola temática, spells e equipamentos próprios que usam o terreno e forças terrestres como linguagem de combate e controle.
A integração com **Mowzie's Mobs** faz parte do escopo declarado da build, conectando o conteúdo de geomancia a outro conjunto de criaturas e poderes presente no pack. O mod utiliza **GTBC's SpellLib** como biblioteca compartilhada; a SpellLib fornece infraestrutura, enquanto Geomancy Plus contém o conteúdo jogável de escola, feitiços e gear.

## Hazen N Stuff — 1.4.0.14

`hazennstuff-1.4.0.14.jar`
**Hazen N Stuff** é uma expansão ampla do Iron's Spells que adiciona spells, equipamentos e conteúdo RPG próprio em grande volume. Diferente de um bridge voltado a um único mod externo, seu escopo é ampliar diretamente o catálogo jogável do Iron's com novas opções de magia, armas, armaduras e outros elementos associados à progressão.
O addon utiliza **HazentouveLib** como biblioteca de suporte; a biblioteca centraliza infraestrutura técnica, enquanto Hazen N Stuff contém o conteúdo efetivamente utilizado pelo jogador. A build `1.4.0.14` é a versão NeoForge 1.21.1 instalada e publicada para essa linha.

## Cataclysm: Ignis Soulfires — 1.8.0

`ignissoulfires-1.8.0.jar`
**Cataclysm: Ignis Soulfires** é uma expansão de **L_Ender's Cataclysm** centrada em **Souled Ignitium**, uma evolução temática do equipamento de Ignis. O addon adiciona o novo material, soul essence/powder, caminhos de upgrade, ferramentas, armas, armaduras e horse armor com efeitos próprios inspirados em fogo, lava e soulflame.
As ferramentas Ignitium e Souled Ignitium podem assumir comportamentos de **throwable tools** e recebem habilidades especiais. O equipamento Souled amplia características do gear original com efeitos como imunidade total a fogo no conjunto completo, melhor visibilidade em lava, regeneração quando submerso em lava e utilidades de mobilidade relacionadas ao terreno vulcânico. Armas também recebem variantes Souled e mecânicas específicas.
O **Bulwark of the Soul Flame** funciona como uma evolução do escudo de Ignis e preserva mecânicas próprias de defesa/parry; o projeto também adiciona Souled Blazing Grips, trim material e outros componentes de upgrade. A build `1.8.0` é a release NeoForge 1.21.1 atual. **Ignis Soulfires: Spellbooks** é um addon separado que leva parte desse conteúdo para o ecossistema de spellcasting.

## Ignis Soulfires: Spellbooks — 1.1.0

`ignissoulfires_spellbooks-1.1.0.jar`
**Ignis Soulfires: Spellbooks** conecta o conteúdo de **Cataclysm: Ignis Soulfires** à camada de spellcasting de **Cataclysm: Spellbooks/Iron's Spells**. A bridge acrescenta uma variante mágica de equipamento **Souled Ignitium** e leva materiais e temas de Soulfire/Ignis para o sistema de spells e gear mágico.
A ficha atual registra runtime local `1.1.0`. Essa build está presente e carregada na modlist, embora os canais públicos consultados anteriormente tenham exposto uma numeração diferente; por isso o guia preserva a identidade local sem normalizar a versão para outro artefato.

## ISS: Magic From The East — 1.1.5

`iss_magicfromtheeast-1.1.5.jar`
**ISS: Magic From The East** é uma expansão temática do Iron's Spells inspirada em tradições e estéticas mágicas do leste asiático e do Oriente Médio. O conteúdo inclui **novas escolas**, spells, armas, armaduras, artefatos e estruturas, fazendo a identidade temática aparecer tanto no combate quanto na exploração e no equipamento.
As escolas e itens usam a infraestrutura normal de mana, spell power e spellbooks do Iron's, mas acrescentam arquétipos visuais e mecânicos distintos do repertório medieval-europeu do mod-base. Estruturas e artefatos próprios também fazem parte da distribuição desse conteúdo pelo mundo.

## Leyline Spellbooks — 1.0.3

`leylines-1.0.3.jar`
**Leyline Spellbooks** adiciona **leylines subterrâneas** como fenômenos físicos do mundo e uma escola mágica própria ligada a essas correntes de energia. A progressão combina exploração e spellcasting: o jogador encontra a infraestrutura das leylines no ambiente e utiliza seus recursos e fenômenos dentro do sistema do Iron's.
O addon inclui spells de manipulação temporal e espacial, portais e **rifts** que funcionam como eventos com ondas de inimigos. Dessa forma, a escola não existe apenas como uma lista de scrolls; ela introduz pontos de interesse, eventos e mecânicas de mundo associados ao uso da magia.

## Monsters & Spellbooks — filename 0.0.16.2 / runtime 0.0.14

`monsterspellbooks-0.0.16.2.jar`
**Monsters & Spellbooks** é uma expansão de grande porte para Iron's Spells. A documentação atual descreve **mais de 90 spells**, duas novas escolas de magia e um volume amplo de gear, além de inimigos, minérios, acessórios e estruturas. O addon expande simultaneamente combate, exploração, loot e construção de builds, em vez de atuar apenas como um pequeno pacote de feitiços.
Novos inimigos e estruturas colocam parte desse conteúdo diretamente no mundo, enquanto ores, equipamentos e acessórios sustentam rotas próprias de aquisição e progressão. As novas escolas ampliam também a camada de atributos e especialização do Iron's.
O arquivo correto na modlist é `monsterspellbooks-0.0.16.2.jar`. A release/filename é **0.0.16.2**, mas o metadata interno carregado declara runtime **0.0.14**; ambas as informações permanecem registradas separadamente.

## Paladin Spells — 1.1.1

`paladin_spells-1.21.1-1.1.1.jar`
**Paladin Spells** adiciona ao Iron's Spells um arquétipo de **paladino/tank mágico**. Seus spells são orientados a defesa, proteção de aliados, controle de ameaça/taunt, resistência e efeitos sagrados, fazendo a escola e o equipamento privilegiarem permanência em combate e proteção do grupo em vez de dano explosivo puro.
O addon também usa armadura e atributos como parte da identidade da especialização, aproximando spellcasting e papel defensivo. O resultado é uma classe funcional definida dentro das regras do Iron's, com habilidades de proteção e suporte associadas a presença na linha de frente.

## T.O Magic n' Extras — runtime 4.4.0.1-1.21.1

`traveloptics-4.4.0.1-1.21.1.jar`
**T.O Magic n' Extras** é uma expansão de grande porte para Iron's Spells que reúne spells, bosses, armas, armaduras, equipamentos especiais, estruturas e sistemas próprios de loot e progressão. O escopo é próximo ao de um pacote de conteúdo completo: acrescenta novos encontros e recompensas ao mundo ao mesmo tempo em que amplia o repertório de spellcasting.
O projeto possui integrações com outros mods, incluindo **L_Ender's Cataclysm** e **Alex's Caves**, fazendo criaturas, materiais e áreas desses ecossistemas participarem de parte do conteúdo mágico. Sistemas adicionais de itens e recuperação de recursos também aparecem ao longo de sua progressão.
A build instalada é `traveloptics-4.4.0.1-1.21.1.jar`, runtime `4.4.0.1-1.21.1`. A linha 1.21.1 é uma porta **alpha** e contém apenas parte da experiência total disponível em outras linhas do projeto; isso descreve maturidade e cobertura de conteúdo, não incerteza sobre qual JAR está instalado.

## Tunes 'n Tomes — 1.1.0 HOTFIX

`tunes_n_tomes-1.1.0-HOTFIX.jar`
**Tunes 'n Tomes** introduz uma especialização de **Bard** dentro do Iron's Spells por meio da escola **Melodic**. O spellcasting é estruturado em torno de som, música e performance, com uma mecânica de **resonance** que funciona como parte da identidade dos feitiços e diferencia a escola das afinidades elementais tradicionais.
O addon reúne spells e equipamentos associados à fantasia musical, fazendo o bardo existir como arquétipo de combate e suporte dentro das regras normais de mana, spellbooks e spell power do Iron's. Parte do conteúdo musical que historicamente esteve ligado ao ecossistema de familiars foi concentrada nessa linha, separando de forma mais clara a progressão musical da progressão de companions.

## Wind's Spellbooks — 1.0.5

`wind_spellbooks-1.0.5.jar`
**Wind's Spellbooks** adiciona uma escola completa de **Wind** ao Iron's Spells. A versão atual inclui **sete spells upgradáveis**, um conjunto próprio de armadura de mago, staff e spellbook, além de conteúdo de mundo associado à especialização.
A progressão também aparece na exploração: o addon adiciona a estrutura **Windmill** e um spellcaster próprio, o **Aeromancer**. Assim, a escola de vento não existe apenas como feitiços encontrados em scrolls; ela possui equipamento, encontro e ponto de interesse próprios dentro do mundo.

## Alshanex's Familiars — 1.21.1_v4.0.3

`alshanex_familiars-1.21.1_v4.0.3.jar`
**Alshanex's Familiars** adiciona familiars encontrados pelo mundo que podem ser **domesticados e levados como companheiros permanentes**. As criaturas são ligadas ao ecossistema de Iron's Spells e possuem funções próprias em aventura, incluindo participação em combate e habilidades associadas à magia.
O sistema transforma familiars em uma camada jogável de companion, não apenas em entidades cosméticas. O addon utiliza **FamiliarsLib** como infraestrutura compartilhada para lógica de familiars, armazenamento e recursos relacionados, enquanto esta página representa o conteúdo de criaturas e progressão efetivamente disponível ao jogador.

## Acolyte — 1.0.3

`acolyte-1.0.3.jar`
**Acolyte** amplia Iron's Spells com **spellcasters, facções de combate, estruturas e recrutamento de aliados**. O mod adiciona combatentes humanos e demoníacos — incluindo magos, guerreiros e arqueiros — e inimigos capazes de utilizar o próprio sistema de spells contra o jogador. Também inclui um miniboss Lieutenant e encontros estruturados em torno dessas forças.
A exploração é ampliada por tavernas, watchtowers, torres e castelos. Nas áreas amigáveis, o jogador pode contratar aliados temporários em troca de esmeraldas; no lado demoníaco, recursos como **Demon Horns** entram em trocas por scrolls e outros itens mágicos.
Assim, Iron's passa a existir também como linguagem de IA e de encontros de mundo: NPCs hostis e aliados usam spellcasting, e o jogador pode montar apoio temporário para enfrentar as ameaças introduzidas pelo addon.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/04-progressao-especializacao-e-interface-do-iron-s.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 4. Progressão, especialização e interface do Iron's

## Iron's Spells 'n Spellbooks: Recolor — 1.2.4+1.21.1

`recolor_tablet-1.2.4+1.21.1.jar`
**Recolor** adiciona o **Recolor Tablet**, usado para personalizar a tonalidade visual dos spells do Iron's por escola de magia. Depois de vinculado, o tablet abre uma lista das escolas; selecionar uma delas leva a uma color wheel que altera o hue dos efeitos correspondentes, e a opção de reset restaura a aparência original.
A modificação é visual e não altera dano, custo de mana, cooldown ou progressão das magias. A build instalada é `1.2.4+1.21.1`.

## Fundamental Principles - Iron's Spells Addon — 1.1.7.1

`ypfundamentals-1.1.7.1.jar`
**Fundamental Principles** adiciona uma camada de **progressão, categorização e balanceamento** sobre os spells do Iron's. Os feitiços são classificados automaticamente em **13 Principles** segundo seu comportamento — concentração de mana, projéteis, summons, percepção, targeting, repetição de casts, teleporte, status effects, AoE, movimento, cura, imbuement e estabilidade estrutural. Como a classificação é automática, spells de outros addons podem participar do mesmo sistema quando suas características são reconhecidas.
Cada Principle possui **níveis de 0 a 20** e ganha experiência pelo uso de spells pertencentes à categoria. Os níveis alteram spell power e liberam passivos próprios, como mana máxima, precisão de projéteis, cooldown de summons, targeting distance, chance de cargas extras, duração de efeitos, área, cast movement speed e bônus ligados a armas. A tela de Principles acompanha essa progressão separadamente.
O addon também adiciona **Spell Exhaustion**: casts acumulam fadiga e níveis altos de exhaustion reduzem a eficiência mágica até o jogador se recuperar. A **Remedium's Law** faz healing spells consumirem recursos naturais do corpo, como food/saturation, em vez de tratarem cura como simples conversão direta de mana. Spellbooks possuem progressão própria por tiers/covers, aumentando slots e poder conforme evoluem, e a linha atual inclui Mana Reinforcement, novos spells e mobs.
A página pública chama a release de `fundamental_principles_v_1.1.7.1.jar`, mas o próprio File Details oficial informa **`ypfundamentals-1.1.7.1.jar`** como filename real; portanto o JAR da modlist corresponde exatamente à release 1.1.7.1.

## Spell Codex / Specs — 1.6.5

`specs_irons_spellbooks-1.6.5.jar`
**Spell Codex** transforma a lista de spells do Iron's em um **sistema de descoberta e progressão por Codex**. Em vez de tratar todos os feitiços disponíveis apenas como scrolls soltos, organiza spells, tiers, requisitos e desbloqueios em uma interface dedicada, permitindo acompanhar o que já foi descoberto e o que ainda depende de progressão.
O addon é integrado à **Spell Actionbar**, de modo que progressão e execução de spells compartilham a mesma camada de interface. A versão `1.6.5` também permite casting de spells **imbuídos em armas**, com keybind dedicado e slot de HUD próprio; esses casts podem seguir regras específicas separadas da descoberta convencional do Codex.
O sistema é configurável e atua sobre o catálogo de spells do Iron's e de addons reconhecidos, servindo como camada de organização para uma instalação com muitas escolas e feitiços.

## Spell Actionbar — 1.1.4

`spell_actionbar-1.1.4.jar`
**Spell Actionbar** adiciona uma barra de ação dedicada ao Iron's Spells acima da hotbar. A base possui **três slots para scrolls** e pode ser expandida de acordo com spellbooks equipados, permitindo selecionar e lançar habilidades por hotkeys sem abrir o inventário ou a interface do spellbook durante o combate.
A HUD mostra ícones, cooldowns, mana e keybinds associados aos spells. Scrolls colocados na actionbar funcionam como referências de habilidade e **não são consumidos** ao lançar o feitiço.
Além do uso independente, a Actionbar serve como superfície de execução para o Spell Codex e para recursos adicionais como spells imbuídos em armas, concentrando seleção, estado e feedback de casting em uma única interface.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/05-integracoes-do-iron-s-com-outros-sistemas.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 5. Integrações do Iron's com outros sistemas

## Goety Iron — 3.1

`GoetyIron-1.21.1-NeoForge-3.1.jar`
**Goety Iron** integra Goety e Iron's Spells principalmente pela camada de **servants**. Criaturas e spellcasters do ecossistema Iron's podem ser incorporados à lógica de servos comandáveis do Goety, permitindo que entidades mágicas usem seus atributos e capacidades dentro do sistema de necromancia/controle de minions.
A versão `3.1` adiciona e configura atributos de spellcasting aplicáveis aos servants e inclui correções de compatibilidade. A bridge não cria uma terceira barra de magia: ela faz entidades de Iron's obedecerem às regras de summons e servants do Goety enquanto preservam capacidades relevantes de spellcaster.

## Create: Wizardry — 1.21.1-0.5.1-pre1

`create_wizardry-1.21.1-0.5.1-pre1.jar`
**Create: Wizardry** é uma integração de **magitech** entre Create e Iron's Spells. Ela acrescenta processamento, receitas, automação e conteúdo cruzado para que materiais e componentes mágicos possam participar de linhas mecânicas em vez de depender exclusivamente de crafting manual ou loot.
A bridge aproxima a progressão do Iron's da infraestrutura de fábrica: recursos mágicos entram em operações e cadeias Create, enquanto novos componentes conectam visual e funcionalmente os dois ecossistemas. Isso amplia o papel de máquinas e automação na produção de conteúdo de spellcasting.
O runtime instalado é `1.21.1-0.5.1-pre1`. A build é **pre-release**, característica de maturidade da versão, enquanto a identidade e o arquivo da instalação permanecem definidos.

## IronSable — 1.2.0

`ironsable-1.2.0.jar`
**IronSable** é a bridge entre Iron's Spells e a física de **Sable/Create Aeronautics**. Ela faz spells que aplicam força — como vento, empurrão, puxão e outros efeitos cinéticos — reconhecerem objetos físicos e contraptions em vez de atuarem apenas sobre entidades e coordenadas vanilla.
Com isso, o vetor produzido por um feitiço pode ser traduzido para o sistema físico usado por estruturas móveis. A bridge não adiciona novas escolas nem um segundo motor de física; sua função é permitir que os efeitos já existentes do Iron's produzam respostas coerentes em objetos simulados pelo Sable.

## Immersive Portal - Iron's Spells 'n Spellbooks Addon — 1.0.0

`immersive_portal_irons_spells_n_spellbooks_addon-1.0.0.jar`
Esta bridge adapta o **Portal Spell** do Iron's Spells para criar portais contínuos do **Immersive Portals**. O destino continua sendo definido pela lógica do spell, enquanto a passagem resultante utiliza a renderização/travessia espacial do mod de portais; o tamanho criado pode ser ajustado por configuração.
É uma integração de escopo estreito e depende diretamente de Iron's Spells 'n Spellbooks e Immersive Portals. A primeira release NeoForge 1.21.1 instalada é `1.0.0`, publicada em 27/08/2026.

## Epic Fight & Iron's Spellbook Animation Compat — 3.1.0

`efiscompat-3.1.0.jar`
**Epic Fight & Iron's Spellbook Animation Compat** adapta o casting e outras ações do Iron's Spells ao sistema de animações do **Epic Fight**. Em vez de spells interromperem ou ignorarem a postura do Battle Mode, o addon fornece animações e transições específicas para que conjuração e combate corporal usem a mesma linguagem visual.
A integração é voltada à apresentação e à coerência de ação: ela não altera escolas, mana ou dano dos spells, mas muda como o corpo do jogador executa esses casts quando Epic Fight está ativo. O projeto público também é identificado como **Epic Fight x Iron's Spells: Enhanced Animations**.
O arquivo instalado é `efiscompat-3.1.0.jar`; a publicação equivalente pode aparecer como `efiscompat-3.1.0-neoforge.jar`, enquanto o runtime é `3.1.0`. Essas strings são preservadas separadamente.

## Woodwalkers SpellBooks — runtime 0.3.1-BETA

`woodwalkers_spellbooks-0.3.1-BETA.jar`
**Woodwalkers SpellBooks** integra o sistema de transformação do **Woodwalkers** ao spellcasting de **Iron's Spells 'n Spellbooks**. A bridge adiciona o spell Evocation **Shapeshifting** e remove a necessidade de usar o desbloqueio convencional de formas: lançar o feitiço mirando uma entidade registra aquela criatura como uma forma disponível.
Desbloquear uma nova forma consome **níveis de XP** de acordo com o nível do spell. Depois de registrada, lançar Shapeshifting sem alvo transforma o personagem em sua forma secundária sem novo custo de XP. Durante a transformação continuam disponíveis os keybinds do Woodwalkers para retornar ao normal e usar a habilidade especial da forma.
A duração da transformação escala com o nível do spell e pode ser configurada, inclusive para duração infinita. Também é configurável o custo de XP e se o jogador pode utilizar outros spells enquanto está transformado. A build pública NeoForge 1.21.1 corresponde à versão 0.3.1; a modlist carrega o filename `woodwalkers_spellbooks-0.3.1-BETA.jar`, que é preservado como identificação local.

## Iron's Apothic — 2.2.1

`irons_apothic-2.2.1.jar`
**Iron's Apothic** conecta equipamentos e atributos do Iron's Spells à itemização de **Apotheosis/Apothic**. Staffs, armaduras e outros itens mágicos podem participar de sistemas de **affixes, gems, sockets, atributos e reforging**, fazendo o gear de spellcasting entrar na mesma camada de loot procedural e aprimoramento usada pelo Apotheosis.
A bridge também permite que propriedades relevantes de magia sejam reconhecidas por esse sistema de itemização, em vez de tratar equipamento do Iron's como itens externos sem suporte. Ela depende do Iron's Spells e dos módulos Apothic necessários para affixes e atributos; não introduz um sistema de spellcasting ou de loot independente.

## Reliquified Iron's Spells 'n Spellbooks — 0.2.7

`reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar`
**Reliquified Iron's Spells 'n Spellbooks** integra o sistema **Relics** ao Iron's Spells por meio de relics desenhados especificamente para spellcasting. Os acessórios podem responder a mana, escolas, casts e efeitos mágicos, usando a infraestrutura de Relics para criar progressão e passivos ligados a personagens conjuradores.
Como são relics reais, esses itens seguem o modelo de uso e evolução do mod Relics, mas seus gatilhos e benefícios são construídos ao redor de mecânicas do Iron's. O resultado é uma camada de acessórios mágicos persistentes, separada de spellbooks, armaduras e curios convencionais do mod-base.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/06-goety-e-necromancia.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 6. Goety e necromancia

## Goety Cataclysm — runtime 1.21.1-1.8.2

`goety_cataclysm-1.21.1-1.8.2.jar`
**Goety Cataclysm** é a compatibilidade direta entre **Goety** e **L_Ender's Cataclysm**. O addon transforma poderes e criaturas do Cataclysm em conteúdo acessível pela progressão sombria do Goety, incluindo spells e habilidades inspirados nos ataques de monstros e bosses do mod-base.
A integração leva esses poderes para a lógica de Soul Energy, focos, summons e servants do Goety, fazendo o conteúdo do Cataclysm participar de necromancia e invocação em vez de existir apenas como loot ou encontros separados. A versão instalada `1.21.1-1.8.2` é a release NeoForge 1.21.1 correspondente.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/07-malum-e-spirit-arcana.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 7. Malum e spirit arcana

## Gaze — 1.1.7.1

`gaze-1.1.7.1.jar`
**Gaze** é uma expansão de Malum voltada a aprofundar sua progressão de almas e spirit arcana. O addon adiciona uma **tela de progressão própria**, novos **Geas**, novos **Rites**, armas, runas, Curios e uma pouch, estendendo o ciclo de obter espíritos, desbloquear conhecimento e converter esses recursos em capacidades permanentes.
As versões atuais também trabalham com sistemas de bestiário e registros de espíritos obtidos de criaturas, além de alterar e ampliar ritos já existentes. É um addon ainda sujeito a mudanças estruturais, mas a build instalada `1.1.7.1` está alinhada à linha Malum 1.8.

## Malum: Vestis — 1.1.0

`vestis-1.1.0.jar`
**Malum: Vestis** é uma expansão **cosmética** de Malum construída sobre Vanity. Seu conteúdo atual acrescenta o design **Spirited Scythes**, obtido pelas rotas de aquisição de designs do ecossistema Vanity/Malum, com **oito estilos visuais de foice** inspirados nos diferentes spirits.

Ele não adiciona uma nova progressão de atributos ou um conjunto paralelo de armaduras: seu papel é ampliar a personalização visual de equipamento ligado a Malum. Requer Malum e Vanity: Core.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/08-encantamento-loot-magico-e-equipamentos.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 8. Encantamento, loot mágico e equipamentos

## Apothic Enchanting — 1.6.1

`ApothicEnchanting-1.21.1-1.6.1.jar`
**Apothic Enchanting** é um overhaul completo do sistema de encantamento. A mesa deixa de ser limitada ao modelo vanilla de nível 30 e passa a receber estatísticas fornecidas pelas estantes e blocos ao redor. **Eterna** representa a energia/nível disponível para encantamento; **Quanta** altera a variabilidade dos resultados; **Arcana** influencia a qualidade e o acesso a encantamentos mais raros; o sistema também trabalha com pistas adicionais, estabilidade e condições especiais para treasure enchantments.
Essas estatísticas fazem a montagem da área de enchanting virar uma progressão própria: diferentes estantes e configurações mudam concretamente a distribuição e a potência dos encantamentos disponíveis. O módulo também acrescenta encantamentos, tomes e utilidades relacionadas à extração/manipulação de enchantments e é parte requerida da instalação moderna do Apotheosis.

## Apotheosis — 8.7.0

`Apotheosis-1.21.1-8.7.0.jar`
**Apotheosis** concentra a camada de loot e progressão de equipamento do ecossistema Apothic. Armas, armaduras e outros itens compatíveis podem receber **affixes** com raridades e efeitos próprios; o sistema acrescenta **gems e sockets**, reforging, salvaging, gem cutting e uma **Augmenting Table** capaz de melhorar ou rerrolar affixes específicos.
A progressão também utiliza **World Tiers**. Os tiers são desbloqueados por critérios de avanço e, quando ativados, aumentam simultaneamente a força dos inimigos e a qualidade potencial do loot, chegando a raridades e affixes de endgame. Apothic Invaders/Elites e itens afixados fazem essa camada aparecer durante exploração e combate, não apenas em crafting.
A arquitetura é fortemente data-driven e aceita categorias de loot e atributos de outros mods. Por isso equipamentos mágicos de outros sistemas podem participar de affixes, gems e sockets quando uma integração os registra, como ocorre com o conteúdo de Iron's através de Iron's Apothic.

## Relics — 0.12.8

`relics-1.21.1-0.12.8.jar`
**Relics** adiciona dezenas de acessórios com mecânicas próprias, projetados como itens RPG que continuam evoluindo depois de serem encontrados. Cada relic combina habilidades específicas com um sistema de **leveling**, estatísticas aleatórias no estilo MMO e opções de melhoria/customização, de modo que duas cópias do mesmo tipo podem terminar com perfis diferentes.
Parte das relics possui **habilidades ativas progressivas**, enquanto outras atuam por passivos e gatilhos ligados ao combate, movimento ou outras ações. A versão 0.12.8 também registra experiência ganha pelas relics e adiciona o Shield of Retaliation. O mod é a base mecânica utilizada pelos addons Reliquified presentes no pack.

## Reliquary Reincarnations — runtime 2.0.80

`reliquary-1.21.1-2.0.80.1570.jar`
**Reliquary Reincarnations** é uma coleção de itens e blocos mágicos de obtenção relativamente trabalhosa que transformam drops e recursos em utilidades permanentes. O catálogo cobre múltiplos domínios — magia, armazenamento, alimentação e mobilidade — com artefatos e ferramentas que resolvem problemas específicos em vez de formar uma única árvore de spellcasting.
A progressão vem principalmente da aquisição dos materiais necessários para cada item e do uso de recursos consumíveis ou carregáveis associados a essas ferramentas. É um sistema independente de Relics e Artifacts. O JAR instalado/publicado é `2.0.80.1570`, enquanto o metadata runtime da instância declara `2.0.80`; as duas identificações permanecem preservadas.

## Reliquified L_Ender's Cataclysm — 0.1.1

`reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`
**Reliquified L_Ender's Cataclysm** integra o sistema **Relics** ao conteúdo de **L_Ender's Cataclysm**. O addon transforma conceitos e recompensas do Cataclysm em relics que seguem o modelo de progressão do Relics, com níveis, ranks, experiência, atributos, cooldowns e habilidades ativas/passivas em vez de serem apenas acessórios estáticos.
Entre as relics documentadas pela camada de compatibilidade atual estão **Void Cloak, Scouring Eye, Void Vortex in Bottle, Vacuum Glove e Void Bubble**. O projeto-base `0.1.1` é a release NeoForge 1.21.1 instalada e depende de Relics e L_Ender's Cataclysm.
Essa release foi criada contra a API antiga do Relics 0.10. Em 2026 surgiu um mod separado que adapta esses relics ao modelo `RelicTemplate` das versões Relics 0.12, restaurando Curios, atributos, ranks, cooldowns, XP e abilities; esse fix externo não está presente na modlist atual, portanto não é listado como parte deste addon.

## Reliquified Artifacts — 1.0.8

`reliquified_artifacts-1.21.1-1.0.8.jar`
**Reliquified Artifacts** redefine a integração entre **Artifacts** e **Relics** para que acessórios originados no Artifacts possam participar do modelo mais profundo de Relics. Em vez de funcionar apenas como compatibilidade de slots, a proposta é incorporar esses itens à lógica de progressão, estatísticas e mecânicas de relics.
O resultado é uma ponte entre dois catálogos de acessórios: Artifacts fornece identidades e efeitos de itens, enquanto Relics fornece a camada de evolução e customização usada pela integração. A build `1.0.8` instalada é a beta NeoForge 1.21.1 atual.

## Iron's Gems 'n Jewelry — runtime 1.21.1-2.0.2

`irons_jewelry-1.21.1-2.0.2.jar`
**Iron's Gems 'n Jewelry** adiciona um sistema de **jewelcrafting modular** baseado em padrões, materiais e gemas. As peças são fabricadas na Jewelcrafting Station: o padrão determina o tipo de joia e de benefício, os materiais escolhidos definem quais bônus serão aplicados e a qualidade desses materiais influencia a força final do item.
O mod possui mais de dez mil combinações modulares possíveis, sete gemas obtíveis e padrões que podem ser conhecidos desde o início ou desbloqueados por exploração e **Artisan Scrolls**. A profissão de villager **Jeweler** compra e vende materiais, peças prontas e padrões avançados. O sistema é data-driven e usa os acessórios como fontes persistentes de atributos e stats, não como uma lista adicional de spells.

## Create: Enchantment Industry — 2.5.3

`create-enchantment-industry-2.5.3.jar`
**Create: Enchantment Industry** transforma experiência e encantamentos em recursos processáveis pela infraestrutura Create. Sua proposta central é **automatic enchanting**: XP pode entrar em cadeias industriais e processos que normalmente dependem de interação manual passam a ser executáveis por máquinas, fluidos e sequências de processamento.
Com isso, armazenamento/manipulação de experiência, tratamento de itens encantados e operações relacionadas a enchantments podem ser incorporados a linhas de produção. A build `2.5.3` é a linha NeoForge 1.21.1 preparada para Create 6.0.10 e possui integrações condicionais com o ecossistema Apothic quando os respectivos módulos estão carregados.

## Create Enchantment Industry Plus — 1.1.1

`create_enchantment_industry_plus-1.1.1-1.21.1.jar`
**Create Enchantment Industry Plus** é uma extensão pequena e específica da cadeia de fluidos do Enchantment Industry. Ela adiciona **Empty Ink Sacs** obtidos por processamento de leather, permite encher esses recipientes com ink/black dye para produzir ink sacs e também drenar ink sacs novamente.
A integração com experiência acrescenta uma rota de spout para converter ink sacs em **glow ink sacs**, além de receitas adicionais de grinding/processamento. Portanto, o addon não amplia genericamente o sistema de enchanting: seu foco atual é industrializar o ciclo de ink e glow ink usando mecanismos do Create/Enchantment Industry.

## Create: Enchantable Machinery — 3.6.0

`createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar`
**Create: Enchantable Machinery** permite aplicar encantamentos vanilla a blocos e máquinas específicos do Create, criando variantes cujo comportamento muda conforme o enchantment aplicado. O efeito não é apenas visual: os encantamentos são interpretados pela lógica da máquina correspondente e influenciam seu processamento ou interação com o mundo.
O projeto inclui suporte a diversos componentes Create e continua tratando casos próprios de máquinas encantadas, como Spout, Mechanical Mixer, Fan, Plough e Roller. Assim, encantamento passa a ser uma propriedade funcional da infraestrutura mecânica, separada da automação de XP oferecida por Enchantment Industry.

## Apotheosis/Create Addon — 2.0.0

`apotheoticcreation-2.0.0.jar`
**Apotheotic Creation** é uma ponte técnica entre **Apotheosis/Apothic** e os sistemas de filtragem do Create. Ela fornece tags e interpretação para que **Attribute Filters**, Smart Observers, Brass Tunnels e outras rotas de triagem consigam reconhecer propriedades complexas de itens Apothic, como gear com affixes e gems.
Isso permite classificar e encaminhar automaticamente loot RPG com base em características que o Create puro não entende. O addon não cria um novo sistema de affixes nem de máquinas: ele torna metadados do Apotheosis legíveis para a automação logística do Create.

## Dis-Enchanting Table — 5.0.2

`disenchanting_table-merged-1.21.1-5.0.2.jar`
**Dis-Enchanting Table** adiciona uma bancada dedicada a recuperar encantamentos de itens e livros encantados, transferindo-os para livros em vez de perder o enchantment junto com o equipamento original. A mecânica permite, por exemplo, retirar encantamentos de uma ferramenta prestes a quebrar e reutilizá-los em outro fluxo de equipamento.
Na versão `5.0.2`, o inventário do bloco foi simplificado para trabalhar corretamente com **hoppers** e existe suporte a saída automática quando o modo de disenchanting automático está habilitado. Assim, a extração de encantamentos pode participar tanto de uso manual quanto de automação de inventário.

## Ozymandias Sundries — publicação 0.0.5 / runtime 0.0.1

`ozymandias_sundries-0.0.5.jar`
**Ozymandias Sundries** é uma expansão de conteúdo para **Iron's Spells 'n Spellbooks**. O addon adiciona armas mágicas e híbridas — como Sanctified Sword, Sacrificial Kris, Sculk Greatsword, Permafrost Axe, Cinderous Scimitar, Druidic Scythe, Ender Glaive, Levin Sword e Spectral Greatsword — além de equipamento adicional associado às escolas do sistema-base.
Também inclui spellbooks como **Fulminous Folio** e **Libram of Flesh** e spells próprios, entre eles **Levitate** e **Lightning Warp**. O projeto continua sendo conteúdo dependente de Iron's: mana, escolas, casting e infraestrutura de spellbook vêm do mod-base.
O JAR instalado é `0.0.5`, enquanto o metadata runtime local declara `0.0.1`; o guia mantém as duas identidades separadas.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/09-bibliotecas-e-infraestrutura-magica.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 9. Bibliotecas e infraestrutura mágica

## RunicLib — 5.0.7

`neoforge-runiclib-1.21.1-5.0.7.jar`
**RunicLib** é uma biblioteca multi-loader usada principalmente pelos projetos do ecossistema **Azurune**, mas exposta para outros desenvolvedores. Ela não adiciona uma progressão mágica autônoma; fornece infraestrutura comum para mods que precisam compartilhar código entre loaders e registrar sistemas reutilizáveis.
Entre as funções públicas estão um método comum de registry para projetos MultiLoader, utilidades como tratamento comum de flammability e **conditional recipes**, classes públicas compartilhadas e um conjunto de **effects/effect classes** pré-prontos. A biblioteca também concentra atributos e helpers consumidos por mods dependentes.
A instalação atual é `5.0.7` para NeoForge 1.21.1. Sua presença no guia representa infraestrutura técnica do stack mágico, não uma escola, spellbook ou árvore de progressão jogável.

## Ace's Spell Utils — 1.2.7.1

`aces_spell_utils-1.2.7.1-1.21.1.jar`
**Ace's Spell Utils** é uma API de suporte para addons de Iron's Spells, mas fornece uma quantidade considerável de infraestrutura reutilizável. Ela inclui classes-base para armas com habilidades ativas/passivas, magic swords, magic guns, loot bags, spellbooks passivos e itens com cooldowns próprios.
A biblioteca também registra atributos como **Mana Steal, Mana Rend, Spell Resistance Penetration, Magic Crit Chance/Damage, projectile crits, Life Recovery e Vigor Reap**. No lado de spellcraft oferece escolas utilitárias como Occult, Hydro e Technomancy, animações de casting e classe abstrata para summons; ainda possui helpers para boss music, shaders de pós-processamento e renderização emissiva de armaduras GeckoLib.

## GTBC's SpellLib — runtime 2.0.0-1.21.1

`gtbcs_spell_lib-2.0.0-1.21.1.jar`
**GTBC's SpellLib/API** é a biblioteca comum usada pelos addons de Iron's Spells do GameTechBC. Ela não adiciona uma progressão jogável independente: concentra classes, atributos, helpers e estruturas reutilizáveis para que os addons do autor compartilhem a mesma implementação de spells, itens e mecânicas.
A linha 2.0 amplia essa infraestrutura com recursos como atributo de **Elemental Permeability**, interfaces utilitárias para preservação localizada de inventário/XP/Curios em contextos específicos, framework de paintings customizadas e componentes reutilizáveis como Treasure Pouch. No pack, **GTBC's Geomancy Plus** é um consumidor direto dessa API.

## HazentouveLib — 1.0.9

`hazentouvelib-1.0.9.jar`
**HazentouveLib** é uma biblioteca para os mods e addons de Hazen e não adiciona conteúdo jogável por conta própria. Ela centraliza três escolas reutilizáveis — **Radiance, Shadow e Cosmic** —, classes-base para entidades spellcasters, o efeito **Hexed** e abstrações de itens mágicos, armaduras e maces/staffs.
Também fornece tiers e combinações de armor helpers, raridades ligadas a escolas, cinco keybinds reutilizáveis para habilidades e a abstração `AbstractTaggedSpell`. Essas APIs são consumidas por projetos como **Hazen N Stuff**. A build atual do pack, `1.0.9`, é a release NeoForge 1.21.1 publicada em 16/08/2026.

## FamiliarsLib — runtime 1.21.1-1.7

`familiarslib-1.21.1-1.7.1.jar`
**FamiliarsLib** contém o núcleo técnico extraído de Alshanex's Familiars. A API permite registrar **familiars próprios**, beds/casas de familiars, blocos de armazenamento ligados a essas criaturas e até spells associados à **Sound school**, de modo que addons possam criar companions compatíveis sem copiar a implementação do mod principal.
Também concentra comportamento compartilhado de familiars e infraestrutura usada pelo addon Alshanex's Familiars; atualizações da linha 1.7 incluíram correções de beds/healing e estabilidade de servidor. O filename/publicação é `1.7.1`, enquanto o metadata runtime da instância declara `1.21.1-1.7`; as duas strings permanecem separadas.

## Iron's Lib — runtime 1.21.1-2.1.0

`irons_lib-1.21.1-2.1.0.jar`
**Iron's Lib** reúne frameworks reutilizáveis para os mods do Iron431. A camada de **transmogs** permite aplicar modelos únicos a armaduras, construir interfaces customizáveis de armor e lidar automaticamente com cape physics; o framework de **statues** cobre geração dinâmica de estátuas de jogador, estruturas multibloco e renderização estática.
A biblioteca também implementa atributos RPG leves como **Armor Pierce, Mining Speed, Experience Gained, Arrow Damage, Crit Damage, Dodge Chance e Healing Received**. Um Attribute Remapper permite trocar atributos de itens dinamicamente e possui compatibilidade nativa com Apothic Attributes, com pontos de extensão tanto por código quanto por datapacks. Há ainda uma camada comum de integração Patreon usada pelos projetos do mesmo ecossistema.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/10-visao-geral-por-estilo-de-personagem.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 10. Visão geral por estilo de personagem

| Estilo | Mods que mais representam esse estilo |
| --- | --- |
| **Mago criador de spells** | Ars Nouveau, Not Enough Glyphs, Ars Zero, Ars Elemental, Ars Elemancy |
| **Mago RPG de escolas** | Iron's Spells, Asterism Arcanum, Aeromancy, Geomancy, Wind's Spellbooks, Magic From The East |
| **Necromante / summoner** | Goety, Goety Cataclysm, Goety Iron |
| **Ocultista ritualístico** | Malum, Eidolon: Repraised |
| **Technomancer** | Ars Creo, Ars Technica, Ars Controle, Create: Wizardry, Ars Sable, IronSable |
| **Paladino / suporte** | Paladin Spells |
| **Bardo** | Tunes 'n Tomes |
| **Mago elemental** | Ars Elemental, Ars Elemancy, Aeromancy, Geomancy Plus, Wind's Spellbooks |
| **Mago Eldritch / vazio** | Discerning The Eldritch, Deeper and Darker: Spellbooks, Dreamless Spells |
| **Mago astral** | Asterism Arcanum |
| **Mago do End** | Fire's Ender Expansion |


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/11-leitura-do-conjunto-magico-do-pack.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 11. Leitura do conjunto mágico do pack

O conjunto atual cobre várias das grandes fantasias de magia de um RPG. **Ars Nouveau** fornece spellcraft livre e automação; **Iron's Spells** fornece classes e escolas de combate; **Goety** cobre necromancia e summons; **Malum** cobre spirit arcana; **Eidolon** cobre ocultismo e rituais.
Os addons então aprofundam essas bases em direções específicas: elementos, música, paladino, astral, Eldritch, End, geomancia, tecnomagia, familiars, portais, logística e joalheria. Isso faz a magia do pack funcionar como vários caminhos de personagem diferentes, e não como um único mod com centenas de feitiços misturados.
---
**Base da listagem:** `modlist 28.08.26.txt`, NeoForge 1.21.1. Descrições consolidadas a partir da modlist atual e das páginas dos projetos usadas durante a auditoria.
---


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/12-guia-relacionado.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Guias relacionados

Os três catálogos devem ser lidos em conjunto durante auditorias de perks. Este arquivo contém apenas navegação; não duplica as descrições dos outros guias.

- [Mods de Tecnologia — fonte canônica no Notion](https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff)
- [Gameplay e Sistemas — fonte canônica no Notion](https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6)

Para o snapshot versionado no repositório, use também os índices locais:

- [Tecnologia](../technology/README.md)
- [Gameplay e Sistemas](../gameplay/README.md)


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/13-atualizacao-da-modlist-novos-mods-magicos-23-08-2026.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Atualização da modlist — novos mods mágicos (23/08/2026)

> Esta atualização usa `modlist 28.08.26.txt` e acrescenta os novos sistemas sobrenaturais, addons do Iron's e integrações mágicas detectados na modlist atual. O foco permanece exclusivamente em **descrever o conteúdo e a forma de jogar**.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/14-novos-addons-de-iron-s-spells-e-ars-nouveau.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Novos addons de Iron's Spells e Ars Nouveau

## Apprentice's Codex — 0.9.7

`apprentice_codex-0.9.7+mc1.21.1.jar`
**Apprentice's Codex** é uma expansão de side-grades para Iron's Spells, com cerca de **60 spells** distribuídos entre combate, defesa/suporte, exploração, utilidade, coleta e crafting. Há summons de armas, rifles e shotguns mágicos, barreiras, guard stances, traps, healing/mana recovery, luz e visão remota, armazenamento, localização de estruturas e tesouros, além de spells para cortar árvores, modelar terreno, colher plantações, processar itens e coletar recursos.
O equipamento é igualmente central: **Spellcaster Guns**, Swingcast Staves, Offhand Spell Amplifiers e armas especializadas como Smashcast Scepter, Focus Staffbow, Multipurpose Staffrifle, Reflectcast Shield e Mana Force Blade usam casting como parte do próprio moveset. O mod também inclui spellbooks, curios, robes de estilo glass-cannon e gear de caster sem escola fixa.
Na infraestrutura existem Spellcaster Workbench, Atelier Station e **Spell Dispenser**. O Dispenser pode lançar scroll spells suportados por redstone, usa mana potions como combustível e possui suporte opcional a contraptions Create. Muitos comportamentos são configuráveis por datapacks. A build `0.9.7` instalada é beta NeoForge 1.21.1.

## Ars Morph — 2.0.0

`ars_morph-1.21.1-2.0.0.jar`
**Ars Morph** é uma camada de compatibilidade dedicada entre **Identity 2/Morph** e **Ars Nouveau**. Seu papel é fazer o estado de identidade/transformação do jogador continuar reconhecido dentro das mecânicas do Ars, evitando que morph e spellcraft funcionem como sistemas completamente desconectados.
Ele não fornece o sistema de transformação — isso continua pertencendo ao Identity 2 — nem substitui Ars Nouveau. O addon existe especificamente para adaptar a interação entre as duas APIs e permitir que o personagem transformado continue participando corretamente do ecossistema mágico Ars.

## Crystal Chronicles — 0.1.3-alpha

`crystal_chronicles-0.1.3-alpha.jar`
**Crystal Chronicles** adiciona uma camada de pós-jogo para Iron's Spells, com tiers superiores de **armaduras 3D, armas e staffs** ligados às escolas mágicas e um conjunto **Prismatic** que representa uma etapa avançada do equipamento. Na build atual esses itens ainda usam receitas placeholder em parte da progressão, porque o sistema definitivo permanece em desenvolvimento.
A exploração começa no End com o biome **Bismuth Formations**, que fornece materiais para montar um portal. O frame é construído com blocos de Bismuth, finalizado com o **Bismuth Portal Chisel** e ativado lançando o spell **Prismatic Portal**. O destino é uma grande dimensão cavernosa em alpha, dividida em biomas inspirados nas escolas do Iron's, incluindo áreas Flesh, Heavenly e Volcanic.
Cada biome também possui blocos de construção temáticos, variantes trabalhadas e alguns connected textures. Mobs, perigos ambientais e a progressão definitiva de cristais/equipamentos ainda são partes WIP. A versão instalada é `0.1.3-alpha` para NeoForge 1.21.1.

## Legendary Spellbooks — 0.3.2

`legendary_spellbooks-1.21.1+neo-0.3.2.jar`
**Legendary Spellbooks** converte habilidades usadas pelos inimigos de **Legendary Monsters** em spells propriamente integrados ao framework de Iron's Spells. Em vez de apenas adicionar loot inspirado nos bosses, o addon registra esses ataques nas escolas, atributos e regras de casting do Iron's.
O catálogo inclui uma linha **Annihilation**, que escala também com Ender e Fire, e spells distribuídos por escolas como Evocation, Blood, Lightning, Ice, Fire e Nature. Entre eles estão Annihilation Beam/Bomb/Shockwave, Summon Flameborn Knights, Cloud Rail, Energy Beam, Tornado, Glacier Eruption, Flame Eater e Ambush Thorns.
A progressão é ligada ao conteúdo do Legendary Monsters: vários spells entram nas loot tables das criaturas correspondentes e podem ser deliberadamente não craftáveis para preservar essa origem. A build `0.3.2` é a beta NeoForge 1.21.1 instalada.

## Somake Spells — 1.0.8

`somakespells-1.0.8-1.21.1-fix.jar`
**Somake Spells** é uma expansão grande de Iron's Spells inspirada inicialmente em T.O Magic 'n Extras, mas desenvolvida com catálogo próprio. A build atual possui **mais de 50 spells**, concentrados principalmente em **Lightning, Fire, Aqua e Symmetry**, com conteúdo adicional Blood e Ender. O addon introduz a própria **Aqua School**, com spell power/resistance e habilidades aquáticas dedicadas.
Há uma mecânica de **charges** elementais: além dos elementos internos, o mod reconhece escolas introduzidas por outros addons, incluindo Sound, Spirit e Geo. O catálogo de equipamento inclui armas como Glacium Greataxe, Core Splitter, Witherite Glaive, Ruined Blade, Clef Sword, Hallow Sword, Rock Sword e Boltcutter, além de conjuntos Dark Metal Battlemage, Ceranium, Aquamancer e Abyssium.
Os **Grimoires** funcionam como spellbooks evolutivos associados a elementos, inclusive escolas externas compatíveis. O addon possui integrações com Magic From The East e Cataclysm, suporte a Born in Chaos e conexões opcionais com outros addons do ecossistema Iron's. A instalação usa a build corrigida `1.0.8-1.21.1-fix`.

## ShadowsZ — 1.1.9

`shadowsz-1.1.9.jar`
**ShadowsZ** transforma Iron's Spells em base para uma progressão de **Shadow Monarch / necromante-summoner** inspirada em Solo Leveling. Depois de derrotar criaturas, o jogador pode revelar suas sombras e tentar realizar **Shadow Arising** para convertê-las em soldados permanentes. A chance leva em conta a vida máxima da criatura e a Mana disponível.
O exército possui gerenciamento próprio: cada shadow pode receber nome, comportamento e postura de combate; pode ser invocado individualmente ou organizado em grupos, e o jogador consegue ordenar ataques coletivos. Existe também **Position Swap**, que troca instantaneamente a posição do personagem com uma de suas sombras.
As shadows ganham XP e níveis próprios, com pontos investidos em **Health, Speed, Damage e Armor**. Sistemas opcionais permitem fundir shadows ou equipá-las com armas e armaduras reais. Há ainda um modo de progressão do próprio jogador, com títulos **Necromancer → Shadow Overlord → Shadow Monarch** e aumento gradual da capacidade do exército.
O mod também acrescenta a escola **Umbral** ao Iron's, com atributos de Umbral Spell Power/Resistance e spells como **Miasma, Umbral Bond e Aura of the Monarch**. Possui suporte dedicado para vários dos mobs do pack, incluindo Legendary Monsters, Mowzie's Mobs e L_Ender's Cataclysm.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/15-alquimia-toxinas-e-mutacoes.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Alquimia, toxinas e mutações

## Toxony — 0.10.7

`toxony-0.10.7.jar`
**Toxony** é um sistema de alquimia ofensiva e progressão corporal inspirado em química antiga, ciência experimental e na lógica de poções de *The Witcher 3*. A entrada no mod é guiada pelo **Lost Journal**, que documenta ingredientes, processos laboratoriais e etapas da progressão.
O núcleo é o sistema de **Toxicity**. O personagem acumula toxinas conforme utiliza preparações e pode acompanhar sua composição por um Toxicity Gauge. Atingir determinados limiares interage com **mutagens**, que concedem buffs semipermanentes e alteram o personagem em troca de expô-lo a uma carga tóxica crescente; excesso de toxinas também produz consequências negativas.
A cadeia de crafting inclui plantas e materiais próprios, blocos de laboratório, misturas e **oils** que podem ser aplicados a armas ou utilizados ofensivamente. O mod acrescenta armas e ferramentas voltadas a monster hunting, armaduras, uma alternativa ao Netherite obtida no Overworld, estruturas, vegetação e decoração de laboratório/copper alchemy.
Há integrações específicas com outros sistemas: armas de prata causam dano aumentado a vampiros e lobisomens do **Vampirism**, certos mutagens concedem **School Spell Power** para Iron's Spells e o Toxicity Gauge pode ocupar slot Charm via Curios. A build `0.10.7` é beta NeoForge 1.21.1.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/16-ecossistema-vampirism.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Ecossistema Vampirism

## Vampirism — 1.10.12

`Vampirism-1.21-1.10.12.jar`
**Vampirism** adiciona duas progressões sobrenaturais completas: **vampiro** e **vampire hunter**. A infecção vampírica pode começar por mordida ou sangue injetado e evolui para uma transformação permanente. Vampiros substituem a fome comum por uma economia de **sangue**, obtido de animais e villagers; alimentar-se sem matar permite que a vítima regenere sangue, enquanto níveis mais altos também permitem converter humanos em vampiros.
A progressão vampírica usa **rituais e níveis**. Subir de nível aumenta poder físico e concede skill points para habilidades como night vision, transformação em morcego, teleporte e outras capacidades sobrenaturais. Em contrapartida, o sol causa dano e o avanço do vampiro faz surgirem hunters mais perigosos. Coffins, equipamentos e estruturas próprias participam dessa rotina noturna.
A rota de **hunter** possui arsenal, coleta, combate, exploração e árvore de habilidades própria, incluindo técnicas voltadas especificamente a enfrentar vampiros. O worldgen inclui Vampire Forest, vampire barons, estruturas e aldeias defendidas por hunters. Vilas podem entrar no conflito de facções e ser controladas por vampiros ou caçadores, enquanto a força de certos mobs escala em função do nível dos jogadores próximos.

## Bloodlines — 1.21-3.0.9

`bloodlines-1.21-3.0.9.jar`
**Bloodlines** adiciona uma camada de especialização sobre as facções de Vampirism. Vampiros e hunters podem entrar em **linhagens** específicas seguindo a quest de ingresso correspondente; uma vez dentro, a linhagem abre uma árvore própria de habilidades e regras que alteram a forma como aquela facção progride e luta.
Os ranks de bloodline podem conceder novas skills e também impor penalidades ou condições próprias, fazendo a escolha representar um arquétipo mecânico e não apenas um título. O addon possui rotas e sistemas temáticos diferentes por linhagem, incluindo conteúdo específico de hunters e vampiros. Também existe uma saída formal do sistema: a **Purity Injection** permite abandonar a bloodline e retornar à progressão sem linhagem.

## Vampiric Ageing — 1.4.21

`vampiricageing-1.21-1.4.21.jar`
**Vampiric Ageing** adiciona **Age Ranks** e evolução de longo prazo para vampiros, hunters e, quando Werewolves está presente, lobisomens. Por padrão, a progressão vampírica começa depois dos níveis altos do Vampirism e o jogador aumenta sua idade drenando sangue; o progresso pode ser consultado em Coffins. O método de envelhecimento é configurável e pode ser baseado em tempo, infecções, drenagem de sangue ou caça a outras facções.
Subir de Age Rank fortalece atributos existentes e libera capacidades adicionais. Hunters e werewolves possuem suas próprias variantes de ageing, com modificadores e poderes específicos; a linha atual inclui, por exemplo, percepção de entidades invisíveis por ações como **Wise Eye/Superior Senses**, além de parâmetros para regeneração, mineração, dano contra facções e força de leap dos lobisomens.
Quase toda a mecânica é configurável: requisitos de rank, método de evolução, perdas em morte e multiplicadores de poder podem ser ajustados ou desativados. Assim, Ageing funciona como uma camada posterior ao leveling normal das facções.

## Werewolves — 2.0.3.3

`Werewolves-1.21-2.0.3.3.jar`
**Werewolves** adiciona uma terceira facção sobrenatural ao ecossistema Vampirism. O jogador pode viver em forma humana durante o dia e assumir sua força bestial à noite; a condição altera alimentação, combate, resistências e vulnerabilidades. Werewolves se alimentam de **carne fresca**, recebem proteção natural da pelagem contra dano elevado e são particularmente vulneráveis a **armas de prata**.
A progressão permite escolher especializações diferentes, desde foco direto em combate até caminhos mais voltados a sobrevivência ou manutenção de características humanas. Transformação, habilidades e equipamentos próprios fazem o estado de lobisomem funcionar como uma progressão persistente, não como buff temporário.
O addon também modifica o mundo com conteúdo específico da facção, incluindo o biome **Werewolf Heaven**, onde lobisomens podem expressar sua força mesmo durante o dia. A build instalada `2.0.3.3` é a release NeoForge 1.21.1 atual.

## Vampirism Integrations — 1.10.2

`vampirism_integrations-1.21.1-1.10.2.jar`
**Vampirism Integrations** concentra compatibilidades externas que não ficam no core do Vampirism. Ferramentas de informação como **HWYLA/WAILA/WTHIT/Jade** podem exibir nível de sangue, informações de garlic e estado vampírico das criaturas por meio dessa camada.
A integração com **MCA/MCA Reborn** permite alimentar-se de villagers desse ecossistema, cria versões vampíricas/conversões e adapta comportamento de aldeias e villagers à lógica de mordidas e facções. Outras bridges tratam reconhecimento de vampiros como undead/holy targets, biomas onde a luz solar deve se comportar de modo especial e compatibilidades de crafting.
Para sistemas de sobrevivência, há integração com **Survive/Cold Sweat** que altera resistência térmica de vampiros e pode suprimir sede. Também existem pontos de integração para CraftTweaker editar receitas próprias do Vampirism e para mods de aldeias/guards respeitarem facções. A versão `1.10.2` é a release NeoForge 1.21.1 instalada.

## Vampirism Iron's Spells Compatibility — 0.0.9

`vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`
**Vampirism Iron's Spells Compatibility** conecta a economia de sangue do Vampirism às escolas **Blood** e **Holy** de Iron's Spells. Para vampiros, Ray of Siphoning e Devour podem restaurar sangue com base no dano de vida realmente entregue ao alvo, depois de absorção e demais etapas de processamento, sem contar overkill e respeitando a capacidade máxima de sangue.
Nos spells Blood que consomem mana, um vampiro normalmente paga mana primeiro; se não houver mana suficiente, o addon pode realizar um pagamento atômico com sangue em vez de consumir parcialmente recursos. Há também configuração para usar sangue como recurso principal nesses casts. Spells Blood lançados por vampiros recebem um multiplicador próprio de cooldown — por padrão `2/3`, equivalente a cooldown 1,5× menor — enquanto Ray mantém comportamento especial.
A escola Holy passa a reconhecer vampiros como alvos incompatíveis com healing comum: dano Holy contra NPCs vampiros é amplificado, healing Holy pode ferir vampiros em vez de curá-los e utility spells Holy podem causar dano e cancelar o cast quando o próprio caster é vampiro. As regras são configuradas no serverconfig por mundo.
---
**Guias relacionados:** [⚙️ Mods de Tecnologia](https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff) · [⚔️ Gameplay e Sistemas](https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6)


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/17-projetos-proprios-do-modpack.md -->

<!-- Snapshot auditável reconciliado em 2026-08-30. -->

# 17. Projetos próprios do modpack — integração mágica canônica

Os quatro projetos próprios integram a auditoria obrigatória das perks.

**Fonte canônica completa:** [Projetos Próprios do Modpack](../projects/README.md)

Este capítulo registra somente o recorte **mágico**. Para declarar provider/hook, o Chat 1 deve consultar o dossiê completo correspondente.

## Black Arcana — provider mágico próprio principal

[Dossiê completo](../projects/04-black-arcana.md)

Black Arcana é um sistema mágico próprio, não apenas um addon de Iron's/Ars.

- **Canônico:** Arcana Core server-authoritative, resource/cost provider, targeting/effects, cooldown/persistence, data-driven content, Integration Layer com Iron's/Ars/Eidolon/Malum/RPG e World Safety.
- **Casting & UX parcial:** código mergeado; QA manual visual/input ainda não fechada no status auditado.
- **Arcane Danger parcial com componentes reais:** Danger Model, Arcane Resistance, Corruption Resistance, Arcane Strain e Arcane Backlash.
- Arcane Resistance não é generic magic resistance; Corruption não é Shroud; Strain não é mana.
- Backlash usa dano realmente confirmado e provenance própria; não recursa, não crita, não lifesteala, não gera proc ofensivo nem Mastery.
- Equipment set bonus infrastructure está em `main`, mas não representa fechamento completo de 05A.06.
- RPG hazard provider 05A.10, Rituals, Spell Domains e Progression/Balance não devem ser promovidos além do estado realmente comprovado.

## RPG Skill Tree — progressão, perks e gates mágicos

[Dossiê completo](../projects/01-rpg-skill-tree.md)

- O RPG conserva autoridade de Level/XP/attributes/perk ranks e do runtime de efeitos da árvore.
- A API pública read-only pode expor level, Mastery, classes, especializações, perk ranks e attribute ranks para consumers externos.
- Stage 06 possui Iron's e Goety/Malum/Eidolon formalmente fechados; outras bridges gerais permanecem mistas/abertas.
- Os planos genéricos de `magic-pipeline`, summons e healing/support não são prova automática de hook disponível.
- Uma perk envolvendo Black Arcana, Iron's, Ars, Malum, Eidolon ou Goety deve indicar qual sistema possui a autoridade do cast/recurso e qual parte do RPG apenas fornece gate/effect.

## Enshrouded — Shroud/Flame sem duplicar Black Arcana

[Dossiê completo](../projects/03-enshrouded.md)

- `MagicResistanceService` de mobs corrompidos é canônico e permanece único reducer; adapters mágicos fornecem classificação/evidência, não segunda redução.
- Flame State, Flame Altar, **Sanctuary/Flame Ward** e Level 1 Ritual são canônicos.
- Stage 08 de integrações com Ars/Iron's/Goety/Malum/Eidolon é **PLANEJADO** no snapshot auditado.
- Shroud/Exposure/Madness não são Black Arcana Corruption, Arcane Resistance ou Strain.
- Nenhuma conversão automática é permitida.

## Volcanoes — provider ambiental, não sistema mágico

[Dossiê completo](../projects/02-volcanoes.md)

Volcanoes pode fornecer contexto físico para uma perk mágica híbrida — calor, Atmosphere, gases, pressão, geologia, vulcanismo — mas esses valores continuam ambientais.

Não transformar O₂, SO₂, pressão, temperatura ou tectonic stress em mana/Arcane Resistance por inferência. Uma interação exige bridge explícita e preserva a authority do Volcanoes para a grandeza física.

## Regra de pipeline para perk mágica híbrida

[Matriz de integração cruzada](../projects/05-cross-project-integration-matrix.md)

Uma perk híbrida deve escolher **um pipeline principal de cast/dano**. Os demais sistemas são providers/consumers auxiliares. Deduplicar:

- custo;
- cast identity;
- dano confirmado;
- Mastery;
- proc;
- sustain.

Sem bridge real, o componente dependente permanece pending/fail-closed em vez de receber comportamento genérico inventado.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/18-mobstein-necromancia.md -->

# 18. Mobstein — necromancia corporal e ressurreição

## Mobstein : Revive animals and necromancy! — 5.4.4

`mobstein-5.4.4-neoforge-1.21.1.jar`

**Mobstein** entra no eixo de necromancia como um sistema próprio de **ressurreição corporal**, criação de experimentos e mobs ressuscitados domesticáveis/guarda-costas. A release `5.4.4` é a versão estável NeoForge 1.21.1 publicada em 04/05/2026; GeckoLib é requerida nessa linha.

O mod trabalha com corpos completos, cabeças, partes anatômicas, órgãos, seringas, Clinical/Surgery Stretch, Subject Assembly Machine, criaturas ressuscitadas, Dr. Mobstenio, Igor e conteúdo de Frankenstein/Witherstein.

### Separação de autoridade

Mobstein **não é Goety, Black Arcana, Malum ou Eidolon**. A semelhança temática de necromancia não cria shared resource nem bridge automática. Ressuscitados Mobstein não consomem automaticamente Goety Soul Energy; corpos/órgãos não viram Malum spirits; ressurreição não é ritual Eidolon por inferência; corpos/ressuscitados não representam Black Arcana Corruption nem Enshrouded Shroud.

### Relevância para o Chat 1

O Chat 1 deve auditar Mobstein como provider próprio quando a árvore tocar ressurreição, cadáveres, órgãos, experimentos e aliados ressuscitados. A decisão pode terminar em perk própria, especialização necromântica compartilhada, bridge, cobertura universal de companions/summons, progressão nativa autoritativa, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`.

As quatro “perks” internas do Mobstein (`Attack`, `Health`, `Speed`, `Template`) pertencem ao próprio mod e **não são nodes do RPG Skill Tree**.

Milestones de ressurreição, montagem de experimento, estrutura ou boss só podem alimentar Mastery/progressão quando houver causalidade server-authoritative e identidade deduplicável. Manter um resurrected mob ativo/próximo/domesticado não gera Mastery por tick.

**Fonte:** CurseForge oficial, projeto 1193873; release `mobstein-5.4.4-neoforge-1.21.1.jar` de 04/05/2026.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/19-atualizacao-modlist-2026-09-07.md -->

# 19A. Atualização da modlist mágica — 2026-09-07

## Ironsable x Wind's Spellbooks — 1.0.0

`ironsable-wind-1.0.0.jar`

Bridge entre Wind's Spellbooks e IronSable/Sable. Faz spells de vento interagirem com blocos simulados/estruturas físicas. A authority do spell continua no mod mágico; a bridge materializa efeitos no domínio físico do Sable. Para perks, validar causalidade server-authoritative, dupla aplicação, desync e destruição excessiva.

## More Relics — 1.7.7 — MANTER / RISCO ACEITO

`morerelics-1.7.7-1.21.1.jar`

Está fisicamente instalado e a decisão curatorial vigente é **Manter**. O upstream não declara suporte a Relics 0.12 no target NeoForge 1.21.1, enquanto o pack usa Relics `0.12.8`. Portanto:

- presença física: confirmada;
- decisão: `Manter`;
- compatibilidade provider-specific: **não presumida**;
- perks/integrações específicas: **FAIL-CLOSED** até validação real.

## Updates mágicos

Apotheosis `8.8.0`; Ars 'n' Spells `3.3.0`; GTBC's SpellLib `2.2.0`; Vampirism `1.10.13`.

## Boundary

Ironsable x Wind é bridge, não escola. More Relics permanece conteúdo do ecossistema Relics; `Manter` não elimina a necessidade de validar o hook específico.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/19-ecossistema-apothic-e-compats.md -->

# 19B. Ecossistema Apothic — atualização incorporada

A linha NeoForge 1.21.1 separa responsabilidades antes agrupadas sob “Apotheosis”. Para perks, identificar o **módulo autoridade** antes de escolher hook.

- **Apothic Attributes** — provider de atributos e do pipeline de armor/protection pierce/shred. Perks devem preferir atributos registrados e evitar recalcular dano fora do pipeline.
- **Apothic Enchanting** — domínio de enchanting; não confundir com affixes/gems.
- **Apothic Spawners** — provider de spawners modificáveis; perks de farms/loot exigem causalidade e anti-abuso.
- **Apothic Category Compat** — corrige roteamento/categorias de itens; não cria affixes por si só.
- **Apothic Compats** — integrações/data packs de affixed loot, gear sets, invaders, gems, Curios etc.; antes de criar integração própria, verificar cobertura data-driven existente.
- **Iron's Apothic** permanece bridge Iron's ↔ ecossistema Apothic.

Regra: separar attribute, enchanting, spawner, loot category, affix/gem e datapack compat. Não duplicar categorização, loot ou aplicação de modifiers.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/20-scripting-e-compatibilidade-tecnica.md -->

# 20. Scripting e compatibilidade técnica — KubeJS e EMF

- **KubeJS Iron's Spells 4.0.3:** expõe criação/configuração de spells, schools, itens e eventos do Iron's para scripts. A authority mecânica continua no Iron's; não simular casting com evento genérico quando a bridge expõe pipeline nativo.
- **KubeJS Ars Nouveau 1.3.2:** bridge adequada para receitas Ars; não prova acesso a toda API de casting/glyphs.
- **EMF Compat: Iron's Spells 2.0.0:** compat client-side de animação/presentation. Nunca usar pose/render como authority de cast, mana, cooldown ou hit.

Quando Java e script puderem executar a mesma integração, escolher **um pipeline canônico** e deduplicar o outro.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/magic/21-fontes-e-referencias-tecnicas.md -->

# 21. Fontes e referências técnicas por mod

Em implementação/auditoria, usar esta ordem: **source/docs da versão instalada → página/release do projeto → modlist runtime → descrição deste guia**.

A revisão corrente mantém índices A–C, D–I, J–R e S–Z no GitHub. Links diretos só devem ser promovidos quando verificados; busca nominal segura é preferível a slug inventado.

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
