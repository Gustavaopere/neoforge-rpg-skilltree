# Alex's Caves Continued

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Autoridade física usada:** `modlist.txt` — 595 entradas totais incluindo o modloader
- **Auditoria de migração Notion → GitHub:** 2026-09-15

## Propriedades do banco

- **Mod:** Alex's Caves Continued
- **Arquivo JAR:** `alexscaves-1.0.9-neoforge+1.21.1.jar`
- **Versão 1.21.1:** 1.0.9
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Worldgen; Mobs; Exploração
- **Função:** Continuação/port do Alex's Caves para NeoForge moderno, preservando os seis cave biomes e o conteúdo original: 43 mobs próprios, centenas de blocos/itens, receitas, estruturas, progressão por Cave Tablet/Codex/Compendium/Map e sistemas específicos por bioma. O Continued acrescenta portabilidade, bug fixes e ferramentas administrativas `/acc`, sem redesign de gameplay.
- **Dependências:** OBRIGATÓRIA: CodxLib correspondente à versão/loader; pack atual possui `codxlib-1.6.0-neoforge+1.21.1.jar`. O Continued incorpora o código necessário do Citadel e não exige Citadel externo para funcionar; `citadel-2.7.1-1.21.1.jar` top-level permanece por outros consumidores.
- **Sobreposição:** É a implementação canônica `alexscaves` do pack. O antigo Alex's Caves 2.0.2 foi removido; não há segunda implementação concorrente no snapshot atual.
- **Compatibilidade/Riscos:** Única implementação `alexscaves` no snapshot. Riscos: worldgen/spawn/chunk borders, biome source/profundidade, estruturas externas dentro dos cave biomes, datapack tags/loot/recipes e mobs externos contaminando spawn pools. AeronauticsCompat cita outro port: cobertura sobre Continued é fail-closed até validar mixin targets/classes. Config efetiva `/acc` da instância não foi fornecida.
- **Observações:** Continued declara 43 mobs próprios e ~350 blocos; a wiki original tem 44 páginas de mobs porque Golden Frog usa `minecraft:frog` e é variante vanilla, reconciliando a contagem. `/acc menu` expõe 39 settings em sete páginas e a superfície herdada tem ~200 opções; valores efetivos desta instância permanecem não auditados/fail-closed.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge/Modrinth oficiais Alex's Caves Continued 1.0.9 + documentação/source do Alex's Caves original usado somente para conteúdo que o Continued declara preservar sem alterações. Artefato instalado `alexscaves-1.0.9-neoforge+1.21.1.jar`, runtime `1.0.9`, SHA-1 `64e4c99e9bac948731ffde5f6639401bdba47072`, mixin `alexscaves.mixins.json`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/alexs-caves-continued
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — dossiê operacional ampliado: 43 mobs próprios + Golden Frog vanilla variant reconciliados, seis biomas, progressão Tablet→Codex→Map/Compendium, `/acc` admin/config, client/server, lifecycle, integrações e fingerprint físico.
- **Histórico da decisão:** 2026-09-06 — escolhida como implementação canônica após conferir a modlist atual e a release 1.0.9 para NeoForge 1.21.1. O projeto Continued é mantido ativamente e declara preservar o conteúdo original sem redesign.
- **Data da última decisão:** 2026-09-06

## Escopo e papel
**Alex's Caves Continued** é a continuação/port do Alex's Caves para versões modernas, preservando deliberadamente o conteúdo do projeto original em vez de redesenhá-lo. O conjunto inclui os seis cave biomes — Magnetic Caves, Primordial Caves, Toxic Caves, Abyssal Chasm, Forlorn Hollows e Candy Cavity — além de mobs, blocos, itens, receitas, progressão e Cave Compendium.

## Runtime e autoridade
- JAR físico: `alexscaves-1.0.9-neoforge+1.21.1.jar`.
- Mod ID: `alexscaves`.
- Runtime: `1.0.9`.
- É a única implementação `alexscaves` presente no snapshot físico atual; o antigo `alexscaves-2.0.2.jar` foi removido.
- Presença/JAR/runtime vêm da modlist física de 595 top-level; builds upstream posteriores para outras versões não substituem essa autoridade.

## Dependências
O projeto Continued usa **CodxLib** correspondente ao loader/versão. O pack atual possui `codxlib-1.6.0-neoforge+1.21.1.jar`. A documentação do Continued informa que Citadel é empacotado/dispensado como dependência externa para o port; o `citadel-2.7.1-1.21.1.jar` top-level do pack pode permanecer por outros consumidores e não deve ser interpretado como requisito externo necessário deste mod.

## Integrações no pack
O mod participa diretamente de worldgen subterrâneo, exploração, fauna, loot e progressão de descoberta. Estruturas/mods que consomem conteúdo Alex's podem reconhecer o mesmo mod ID `alexscaves`, mas compatibilidade efetiva deve ser verificada por versão e assets/registry.

## Compatibilidade, sobreposição e riscos
O bloqueio antigo por mod ID duplicado está resolvido fisicamente. Permanecem riscos normais de worldgen em packs densos: distribuição de cave biomes, bordas entre chunks antigos/novos, interações de spawn e estruturas subterrâneas. O `AeronauticsCompat` upstream cita um port de Alex's Caves de Raguto; o pack usa o Continued de CodxIO, portanto **não assumir** que aqueles mixins de compatibilidade Sable cobrem esta implementação sem validar targets/classes reais.

## Limites
O projeto Continued declara preservar o conteúdo original; ele não é um redesign de mecânicas nem um segundo sistema de cavernas separado. Não substitui geradores gerais de terreno ou mods de estruturas.

## Ownership, linhagem e autoridade de conteúdo
- **Ownership de conteúdo:** o projeto Continued declara que não rebalanceia nem adiciona/remove conteúdo de gameplay: preserva os seis biomas, mobs, blocos, receitas, progressão e Cave Compendium do Alex's Caves original, acrescentando portabilidade, bug fixes e ferramentas administrativas server-side.
- **Ownership técnico do port:** compatibilidade multi-versão/loader, Citadel embutido e ferramentas `/acc`. A publicação do Continued informa que os **66 mixins** do projeto são verificados contra o bytecode de cada versão suportada; no snapshot físico desta instância o extrator registra o config `alexscaves.mixins.json`.
- **Mod ID físico:** `alexscaves`. A implementação canônica do pack é o Continued; não existe segundo top-level `alexscaves` concorrente no snapshot atual.
- **Dependência externa obrigatória do port:** CodxLib correspondente à versão/loader. **Citadel não é dependência externa deste Continued**, porque o código necessário é embutido; o Citadel top-level do pack pode existir por outros consumidores.

## Catálogo operacional por bioma
A publicação Continued sustenta **43 mobs próprios**. A wiki do conteúdo original indexa 44 páginas de criaturas porque inclui **Golden Frog**, que usa o EntityType vanilla `minecraft:frog`; portanto o roster operacional é coerente como **43 entidades/mobs próprios + 1 variante de frog vanilla**.

### Magnetic Caves — `alexscaves:magnetic_caves`
- Mobs próprios: **Boundroid, Ferrouslime, Magnetron, Notor, Teletor**.
- Sistemas centrais: Galena; Scarlet/Azure Neodymium com atração/repulsão; magnetismo afetando equipamento/objetos; Tesla Bulb; Magnetic Quarry e componentes magnéticos.
- Estrutura principal documentada: **Magnetic Ruins**; a geração também usa formas técnicas próprias de caverna.

### Primordial Caves — `alexscaves:primordial_caves`
- Mobs próprios: **Atlatitan, Grottoceratops, Luxtructosaurus, Relicheirus, Subterranodon, Tremorsaurus, Trilocaris, Vallumraptor**.
- Variante ambiental adicional: **Golden Frog** é uma variante do frog vanilla, não um EntityType próprio do mod.
- Sistemas centrais: Limestone, Ambersol/Amber, flora pré-histórica, criaturas/domesticação/ovos e conteúdo tectônico/volcânico.
- Estruturas/landmarks documentados: **Caveman House, Tribal Campsite e Volcano**.

### Toxic Caves — `alexscaves:toxic_caves`
- Mobs próprios: **Brainiac, Gammaroach, Nucleeper, Radgill, Raycat, Tremorzilla**.
- Sistemas centrais: radiação/efeito Irradiated, Uranium, Acid, Radon, Nuclear Bomb/Nuclear Furnace, Hazmat e resíduos.
- Estruturas/instalações documentadas incluem **Toxic Ruins** e conteúdo de Nuclear Furnace.

### Abyssal Chasm — `alexscaves:abyssal_chasm`
- Mobs próprios: **Deep One, Deep One Knight, Deep One Mage, Gossamer Worm, Hullbreaker, Lanternfish, Mine Guardian, Sea Pig, Tripodfish**.
- Sistemas centrais: exploração abissal subaquática, Deepsight, Abyssmarine/Muck, Deep Ones, recursos marinhos e **Submarine** como utilitário/veículo.
- Estruturas: **Abyssal Ruins, Deep One Ruins e Whalefall**.

### Forlorn Hollows — `alexscaves:forlorn_hollows`
- Mobs próprios: **Corrodent, Forsaken, Gloomoth, Underzealot, Vesper, Watcher**.
- Sistemas centrais: escuridão, Guano/Guanostone/Coprolith, Shadow Silk/Pure Darkness e equipamentos ligados a Darkness Incarnate.
- Estruturas documentadas: **Forlorn Bridge, Forlorn Hut/Idol/Post/Ruins e assentamentos Underzealot**; nomes/registro exatos de cada peça estrutural devem ser tratados conforme o runtime/datapack da build instalada.

### Candy Cavity — `alexscaves:candy_cavity`
- Mobs próprios: **Candicorn, Caniac, Caramel Cube, Gingerbread Man, Gum Worm, Gumbeeper, Gummy Bear, Licowitch, Sweetish Fish**.
- Sistemas centrais: Purple Soda, Rock Candy, chocolate/frosting, confeitaria, Sugar Rush, Conversion Crucible e loot/progressão de Licowitch.
- Estruturas principais: **Licowitch Tower** e **Gingerbread Town**; world features temáticas incluem arcos/elementos gigantes de candy terrain.

## Progressão de descoberta e documentação in-game
A cadeia de exploração é parte do ownership do mod, não apenas flavor text:
1. **Underground Cabins** fornecem Cave Tablets e podem conter Cave Compendium.
2. Cave Tablets são pesquisados na **Spelunkery Table** para gerar **Cave Codex**.
3. Codices podem ser usados para liberar capítulos do **Cave Compendium** e também craftar **Cave Biome Maps**.
4. Cada bioma possui progressão própria de informação; o conteúdo original também possui entradas secretas associadas a progressão avançada, como criaturas especiais de Primordial/Toxic.

Essa cadeia precisa ser validada em survival porque worldgen correto sem tablet/codex/map funcional ainda representa regressão de progressão.

## Administração e configuração — ferramentas exclusivas do Continued
O Continued adiciona uma camada operacional que o original não possuía em runtime moderno:
- O projeto informa cerca de **200 opções de configuração herdadas** do Alex's Caves.
- `/acc menu` expõe **39 ajustes** de gameplay/QoL em sete páginas: Status, World Generation, Mobs, Blocks, Items & Potions, Cave Tablet Loot e Vanilla Changes.
- `/acc config`, `/acc config all` e `/acc config <page>` permitem inspeção/alteração textual.
- `/acc biomes` mostra quais dos seis biomas estão habilitados e sua raridade.
- `/acc reload` recarrega configurações de geração de bioma sem reiniciar o servidor.
- `/acc version` expõe versão/loader e update check.
- Segundo a documentação do Continued, tudo exceto `version` e `help` é **operator-only**; o menu é servido pelo servidor.

A configuração efetiva da instância não foi fornecida neste lote, portanto a página registra a superfície existente sem afirmar quais toggles/raridades estão ativos.

## Client/server, lifecycle e multiplayer
- O Continued é um **content mod de client + server**; ambos precisam da build compatível.
- Worldgen, mobs, recipes e progressão são stateful e precisam concordar entre servidor e clientes. As ferramentas `/acc` adicionam administração server-side, mas não transformam o mod em server-only.
- Lifecycle crítico: bootstrap → datapack/registry load → geração de chunks → descoberta/compendium → spawn/AI → save/reload → `/acc reload` → dedicated server/reconnect.
- Mudanças de raridade/enablement de biomas devem ser verificadas apenas em **chunks novos** quando aplicável; chunks já gerados não devem ser tratados como prova de que nova configuração falhou.
- Para mundos existentes, testar bordas entre chunks antigos/novos, estruturas subterrâneas e spawn tables após qualquer mudança de worldgen/modlist.

## Integrações e riscos concretos no pack
- **AeronauticsCompat:** o upstream cita outro port de Alex's Caves; cobertura sobre Continued continua fail-closed até validar mixin target/classes no runtime.
- **Worldgen/biome stacks:** mods que alteram profundidade, biome source, estruturas ou spawn podem deslocar ou poluir os cave biomes; validar especialmente Abyssal Chasm sob oceanos e Primordial/Candy em terreno subterrâneo profundo.
- **AI/spawn:** outros mob packs podem usar as mesmas categorias/biome tags e alterar densidade. Primordial Caves historicamente usa política especial de monstros; o teste deve observar invasão/overspawn de mobs externos.
- **Datapacks:** recipes, tags, loot e estruturas são superfícies de conflito em `/reload`; qualquer erro deve ser atribuído por namespace/stack trace, não pelo simples fato de coexistirem mods de worldgen.

## Fingerprint físico do snapshot
- JAR: `alexscaves-1.0.9-neoforge+1.21.1.jar`
- Mod ID: `alexscaves`
- Runtime: `1.0.9`
- SHA-1: `64e4c99e9bac948731ffde5f6639401bdba47072`
- Mixin config exposto: `alexscaves.mixins.json`
- Dependência física confirmada no pack: `codxlib-1.6.0-neoforge+1.21.1.jar`.

## Limites de evidência
O catálogo de gameplay acima cruza a descrição oficial do Continued — que declara conteúdo idêntico ao original — com documentação/source do Alex's Caves original. Isso permite documentar o conteúdo preservado, mas **não autoriza assumir que nomes de classes Forge 1.20.1, assinaturas internas ou detalhes de implementação permanecem iguais no port NeoForge 1.21.1**. Para automação/mixins próprios, a autoridade deve ser a build Continued instalada.

## Testes recomendados
1. Gerar mundo novo e validar ocorrência dos seis cave biomes.
2. Conferir Cave Compendium, tablets/descoberta e progressão de acesso.
3. Validar spawns, pathfinding, loot, recipes e block entities principais.
4. Abrir mundo existente e gerar chunks novos para inspecionar bordas/worldgen.
5. Dedicated-server smoke com exploração prolongada.
6. Se mobs/blocos forem usados em physics ships, testar explicitamente Sable/Aeronautics em vez de presumir cobertura do AeronauticsCompat.

## Evidências
- [CurseForge oficial — Alex's Caves Continued](https://www.curseforge.com/minecraft/mc-mods/alexs-caves-continued) — autoridade para escopo do port, 43 mobs próprios, conteúdo preservado, CodxLib, Citadel embutido, `/acc` e client+server.
- [Modrinth oficial — Alex's Caves Continued](https://modrinth.com/mod/alexs-caves-continued) — confirmação independente do mesmo projeto/ambiente e descrição do Continued.
- [Source do Continued — Codx-org/AlexsCavesContinued](https://github.com/Codx-org/AlexsCavesContinued) — autoridade técnica do port quando for necessário validar classes/mixins da build moderna.
- [Source original — AlexModGuy/AlexsCaves](https://github.com/AlexModGuy/AlexsCaves) — usado somente para conteúdo/ownership que o Continued declara preservar; não para assumir assinaturas internas NeoForge 1.21.1.
- [Alex's Caves Wiki — Mobs](https://alexscaves.wiki.gg/wiki/Mobs) e páginas de bioma — fonte secundária para agrupamento de roster/estruturas e para a distinção Golden Frog=`minecraft:frog`.
- `modlist.txt` física do projeto — autoridade para presença, ordem, filename, runtime, mixin config e SHA-1 do artefato instalado.