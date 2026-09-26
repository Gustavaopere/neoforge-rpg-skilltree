# Domum Ornamentum

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#224**: JAR `domum-ornamentum-1.0.236-snapshot-main.jar`, mod id `domum_ornamentum`, runtime `1.0.236-snapshot`, SHA-1 `31b16b65eb0cc26473bcd607b1c7f386aa99680f`.

## Propriedades do registro

- **Mod:** Domum Ornamentum
- **Arquivo JAR:** `domum-ornamentum-1.0.236-snapshot-main.jar`
- **Versão 1.21.1:** `1.0.236-snapshot`
- **Categoria:** Visual, Biblioteca
- **Função:** Framework/content mod de blocos decorativos skinnable com material/texture state associado a block entities não-ticking, usado pelo ecossistema LDTTeam/Structurize/MineColonies.
- **Dependências:** Source 1.0.236-SNAPSHOT: Minecraft 1.21.1, NeoForge 21.1.219, BlockUI 1.0.211-1.21.1-snapshot e Structurize 1.0.833-1.21.1-snapshot. Runtime físico atual: BlockUI 1.0.212-1.21.1-snapshot, Structurize 1.0.833-1.21.1-snapshot e NeoForge 21.1.250. O pin do source e a versão física de BlockUI são mantidos distintos.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos de perda de material/skin data em schematic/copy, stale model cache, sync client/server, API drift entre snapshots, BE lifecycle e serialização por Structurize/MineColonies. Não tratar bloco skinnable como registry ID vanilla simples.
- **Fonte:** https://github.com/ldtteam/Domum-Ornamentum/tree/version/1.21.1
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `domum-ornamentum-1.0.236-snapshot-main.jar` / runtime `domum_ornamentum` 1.0.236-snapshot. Source oficial branch `version/1.21.1` matching e CurseForge oficial revalidados em 21/09/2026: 1.0.236-snapshot é latest Beta 1.21.1; stable mais recente é 1.0.231.
- **Observações:** JAR físico `domum-ornamentum-1.0.236-snapshot-main.jar`, mod id runtime `domum_ornamentum`, runtime 1.0.236-snapshot. A publicação 1.0.236 é Beta/snapshot e é mais recente que a stable Release 1.0.231; não promover snapshot a stable nem rebaixar o runtime físico. Source ainda usa `modId=domumornamentum`, divergência preservada.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 21/09/2026 — lote físico #223: domum-ornamentum-1.0.236-snapshot-main.jar / runtime 1.0.236-snapshot reconfirmados como latest Beta/snapshot para NeoForge 1.21.1. A stable Release 1.21.1 permanece 1.0.231; snapshot físico e stable foram mantidos separados.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, a ficha foi reconciliada de referências antigas 1.0.234 para o runtime físico 1.0.236-snapshot e reconstruída no padrão técnico.
- **Sobreposição:** Pode sobrepor esteticamente outros blocos decorativos, mas seu diferencial é o state material/skin parametrizado. Structurize/MineColonies consomem esse provider; não constituem substitutos da mesma authority.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `domum-ornamentum-1.0.236-snapshot-main.jar` · mod id físico `domum_ornamentum` · versão `1.0.236-snapshot` · NeoForge 1.21.1. O branch oficial `version/1.21.1` declara `modVersion = 1.0.236-SNAPSHOT`.
## 1. Papel no modpack
Domum Ornamentum é uma biblioteca/content mod de **blocos decorativos skinnable** usada pelo ecossistema LDTTeam/MineColonies. Seu ponto central é representar formas decorativas cujo material/textura é parametrizado, evitando registrar uma variante fixa para cada combinação possível de materiais.
## 2. Source pin e divergência de ID
O source oficial `ldtteam/Domum-Ornamentum`, branch `version/1.21.1`, corresponde à versão física `1.0.236-SNAPSHOT`. Na distribuição oficial, **1.0.236-snapshot é a Beta 1.21.1 mais recente**, enquanto a **stable Release 1.21.1 permanece 1.0.231**; o pack usa deliberadamente o snapshot físico e ele não é reclassificado como stable. O buildscript usa a propriedade `modId=domumornamentum`, enquanto a modlist física reporta o mod id runtime **`domum_ornamentum`**. Para presença/runtime, a metadata física prevalece; a propriedade do build é registrada apenas como contexto de source.
Baseline do branch:
- Minecraft `1.21.1`;
- NeoForge `21.1.219`;
- BlockUI source baseline `1.0.211-1.21.1-snapshot`; runtime físico atual `1.0.212-1.21.1-snapshot`;
- Structurize `1.0.833-1.21.1-snapshot`.
Essas duas versões também estão presentes fisicamente no pack.
## 3. Arquitetura de material / model
O README oficial descreve o sistema como **skinnable blocks**. O model loader obtém as informações de skin/material de um **block entity não-ticking**. Portanto o bloco visual possui state adicional além de um simples BlockState vanilla.
Regra operacional: qualquer ferramenta de copy/paste, contraption, schematic ou conversão de bloco precisa preservar o state/material associado. Copiar somente o registry ID pode produzir uma forma correta com textura/material incorreto ou default.
## 4. Authority / ownership
- **Domum Ornamentum:** forma decorativa, material/skin state, block entities e model data de seus blocos.
- **Minecraft/NeoForge:** block/item registry, save/chunk lifecycle e networking base.
- **Structurize/MineColonies:** estruturas/build requests/schematic workflows quando consumem os blocos.
- **BlockUI:** UI framework quando utilizado.
Integrações não devem criar uma segunda fonte de verdade para o material visual.
## 5. Registry de blocos confirmado
`ModBlocks` no branch exato usa `DeferredRegister.Blocks` e `DeferredRegister.Items` e confirma famílias/IDs como:
- `architectscutter`;
- timber frames por `TimberFrameType`;
- `dynamic_timberframe`;
- shingles: `shingle`, `shingle_flat`, `shingle_flat_lower`, `shingle_steep`, `shingle_steep_lower`;
- `shingle_slab`;
- `blockpaperwall` e `blocktiledpaperwall`;
- pillars: `blockpillar`, `blockypillar`, `squarepillar`;
- framed lights por `FramedLightType`;
- floating carpets para as 16 DyeColors;
- brick variants por `BrickType`;
- `blockbarreldeco_standing` e `blockbarreldeco_onside`;
- vanilla-compatible forms: fence, fence gate, slab, wall, stairs, trapdoor e door;
- `panel`;
- `light_brick`, `dark_brick`, `light_brick_stair`, `dark_brick_stair`;
- `post`;
- `fancy_door` e `fancy_trapdoors`;
- extra blocks por `ExtraBlockType`.
O registry possui geração por enums/cores; a lista de instâncias finais é maior que a lista de famílias acima.
## 6. Architects Cutter
`architectscutter` é uma peça funcional central do mod e participa da produção/seleção de formas decorativas. Recipe/UI specifics devem ser obtidos do runtime/source correspondente antes de automações externas; não assumir que se comporta como Stonecutter vanilla apenas pelo nome.
## 7. API pública
O README oficial orienta consumidores a compilar contra o **API jar** e usar o main jar em runtime. Também exemplifica acesso a blocks pela API e utilitários como `BlockUtils.getBlockStateFromStack`.
Código próprio deve preferir API pública a casts/reflection sobre classes internas. A API precisa ser pinada à linha 1.0.236 ao desenvolver integração crítica.
## 8. Model data e client rendering
A aparência depende de material data resolvido pelo model loader. Isso cria uma separação importante:
- material/skin state precisa persistir/sincronizar;
- model baking/render é client-side;
- gameplay/block presence é server/world state.
Resource reload, mudança de resource pack ou atualização de renderer pode exigir invalidation/rebuild de model data sem alterar o material salvo.
## 9. Block entities e persistência
O uso de block entities não-ticking reduz custo de tick, mas ainda exige save/load e sincronização corretos. Superfícies críticas:
- chunk unload/reload;
- server restart;
- schematic placement;
- clone/copy operations;
- piston/contraption quando permitido por outro provider;
- breaking/drop e recolocação preservando a combinação esperada.
## 10. Structurize / MineColonies
Domum Ornamentum é parte do ecossistema de construção LDTTeam. No pack, **Structurize 1.0.833-1.21.1-snapshot** está presente e é o parceiro técnico direto para schematics/structures. MineColonies, quando utiliza essas formas, deve tratar DO como provider dos blocos/material states.
Uma schematic que contenha DO precisa serializar os dados necessários; substituir por blocos vanilla por aparência perde a semântica paramétrica.
## 11. BlockUI
O branch pinado usa **BlockUI 1.0.211-1.21.1-snapshot**, enquanto a modlist física atual usa **BlockUI 1.0.212-1.21.1-snapshot**. Esse drift de patch deve permanecer explícito. UI state não deve ser confundido com material commitado no servidor. Screens podem ser recriadas/reabertas sem duplicar crafts ou material consumption.
## 12. Client / Server
- block/material persistence: server/world-authoritative;
- menus/crafting settlement: server-authoritative;
- model loader/textures: client-side;
- model/material sync: precisa convergir entre os lados;
- block entity não deve depender de renderer client para existir no dedicated server.
## 13. Lifecycle
Validar:
- placement com múltiplos materiais;
- break/drop/replacement;
- chunk unload/reload;
- server restart;
- schematic save/paste;
- resource reload;
- multiplayer join/rejoin;
- model cache invalidation;
- update de Structurize/BlockUI.
## 14. Multiplayer / idempotência
Material selection e crafting precisam ser confirmados pelo servidor. Dois clientes não podem consumir materiais ou alterar a skin do mesmo bloco simultaneamente de forma divergente. O visual local deve refletir o state salvo, não ser authority.
## 15. Riscos
1. perder material data em schematic/copy;
2. BE salvo com skin stale/inválida;
3. model cache não invalidado após reload;
4. client/server divergirem no material renderizado;
5. API drift entre snapshots;
6. Structurize/MineColonies serialização incompleta;
7. UI duplicar operação de craft;
8. tool/mod tratar skinnable block como bloco vanilla simples;
9. mod id do build ser confundido com mod id físico;
10. referência antiga a 1.0.234 substituir o runtime 1.0.236.
## 16. Matriz de testes
1. Dedicated server boot com DO + Structurize + BlockUI atuais.
2. Criar formas com pelo menos madeira, pedra e material modded suportado.
3. Break/drop/recolocação preservando resultado esperado.
4. Save/restart e chunk unload/reload.
5. Dois clientes vendo o mesmo material/skin.
6. Resource reload sem perda de state.
7. Schematic Structurize contendo múltiplos blocos DO → save/paste.
8. Copiar estrutura entre chunks e reiniciar.
9. Architects Cutter: interação e consumo exactly once.
10. Testar forms dinâmicas, doors/trapdoors/fences/stairs/panels.
11. Atualização futura de BlockUI/Structurize com API smoke-test.
**A catalogação não afirma que esses testes foram executados.**
## 17. Evidências
- modlist física atual de 21/09/2026: `domum-ornamentum-1.0.236-snapshot-main.jar`, mod id físico, runtime 1.0.236-snapshot e dependências presentes;
- source oficial `ldtteam/Domum-Ornamentum`, branch `version/1.21.1`, `modVersion=1.0.236-SNAPSHOT`;
- README oficial: skinnable blocks, non-ticking block entity/model loader e API jar;
- `ModBlocks.java` do branch exato para registries/famílias.
> **Boundary canônico:** Domum Ornamentum é authority da forma decorativa e de seu material/skin state. Schematic/build systems devem preservar esse state; renderização cliente não é a fonte de verdade.
