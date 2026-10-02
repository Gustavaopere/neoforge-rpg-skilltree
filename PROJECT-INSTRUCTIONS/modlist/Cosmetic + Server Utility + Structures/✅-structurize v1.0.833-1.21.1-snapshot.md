# Structurize

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#529**: JAR `structurize-1.0.833-1.21.1-snapshot.jar`, mod id `structurize`, runtime `1.0.833-1.21.1-snapshot`, SHA-1 `e81934809824cb1435b8221313a143480379893a`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Structurize
- **Arquivo JAR:** `structurize-1.0.833-1.21.1-snapshot.jar`
- **Versão 1.21.1:** 1.0.833-1.21.1-snapshot
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Infraestrutura de blueprints/scans/preview/placement estrutural usada pelo MineColonies, com Scan Tool, Build Tool, blueprint packs, sincronização client-server e load balancing de operações.
- **Dependências:** Release 1.0.833 exige BlockUI 1.0.191-snapshot+ e Domum Ornamentum 1.0.203-snapshot+. Runtime físico: BlockUI 1.0.211, Domum 1.0.236, MineColonies 1.1.1381 e Structurize 1.0.833, todos 1.21.1 snapshots compatíveis com os mínimos publicados.
- **Sobreposição:** Tem ferramentas de construção/schematic, mas sua função de biblioteca para MineColonies é distinta do Schematicannon do Create.
- **Compatibilidade/Riscos:** Build Beta/snapshot. Riscos: drift entre MineColonies/Structurize/BlockUI/Domum, large scans, placement permissions, operações parciais em unload/restart, missing mod blocks e diferenças preview↔placement. Upstream 1.0.835 inclui fix de event e alinha handling com solid substitution block; atualizar o stack de forma coordenada.
- **Observações:** mod id `structurize`; runtime físico `1.0.833-1.21.1-snapshot`. Upstream publicou `1.0.835-1.21.1-snapshot` em 30/09/2026; não há artefato 1.21.1 1.0.834 visível na listagem atual. Changelog 1.0.835 contém `fix event (#855)` e `use same handle as solid substitution block (#847)`.
- **Procedência:** modlist física atual + CurseForge oficial Structurize 1.0.833 instalada e 1.0.835 snapshot publicada para NeoForge 1.21.1 + stack físico MineColonies/BlockUI/Domum. Revalidado em 01/10/2026; config de operações por tick e testes runtime continuam não executados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/structurize ; https://www.curseforge.com/minecraft/mc-mods/structurize/files/8829722
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Structurize físico permanece 1.0.833 snapshot. A release 1.0.835 foi comparada; os dois fixes publicados foram registrados fail-closed, sem ampliar seu significado além do changelog upstream.
- **Histórico da decisão:** 2026-08-27 — classificado como Dependência após confirmação do requisito Structurize \>=1.0.832 pelo MineColonies atualmente instalado.
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `structurize-1.0.833-1.21.1-snapshot.jar`, mod id `structurize`, versão `1.0.833-1.21.1-snapshot`. Esta build é **Beta/snapshot**, publicada em 07/09/2026, e é a infraestrutura de blueprints/placement manipulada pelo MineColonies atual.
## 1. Identidade, versão e decisão
- **Mod:** Structurize.
- **JAR físico:** `structurize-1.0.833-1.21.1-snapshot.jar`.
- **Mod id:** `structurize`.
- **Versão:** `1.0.833-1.21.1-snapshot`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta/snapshot; a release estável 1.0.832 permanece separada upstream.
- **Ambiente:** Client & Server.
- **Decisão vigente:** **Dependência**, preservada por consumidor físico confirmado: MineColonies.
## 2. Stack físico e dependências
A release 1.0.833 declara:
- BlockUI `1.0.191-1.21.1-snapshot` ou superior;
- Domum Ornamentum `1.0.203-1.21.1-snapshot` ou superior.
O pack contém:
- BlockUI `1.0.211-1.21.1-snapshot`;
- Domum Ornamentum `1.0.236-snapshot-main`;
- MineColonies `1.1.1381-1.21.1-snapshot`;
- Structurize `1.0.833-1.21.1-snapshot`.
Os mínimos publicados de Structurize estão satisfeitos. O trio Structurize/BlockUI/Domum Ornamentum deve ser tratado como stack versionado, não como bibliotecas independentes intercambiáveis.
## 3. Authority e ownership
- **Structurize:** authority do formato/fluxo de blueprint, scan, preview e placement estrutural de sua API/ferramentas.
- **MineColonies:** authority das colônias, buildings, workers, progressão e decisão de quando um blueprint vira construção da colônia.
- **BlockUI:** authority da infraestrutura de UI consumida pelo stack.
- **Domum Ornamentum:** authority dos blocos decorativos/adaptativos usados nas estruturas.
Structurize não deve ser tratado como provider da lógica de colony building apenas porque fornece a infraestrutura estrutural.
## 4. Blueprints e blueprint packs
A documentação do ecossistema define **Blueprint** como representação de estrutura contendo os blocos e entidades necessários para reconstrução/preview. **Blueprint Packs** agrupam blueprints em coleções.
Packs podem vir dentro de mods ou de diretórios de blueprints do usuário/servidor. A Build Tool fornece navegação/preview dessas coleções.
A semântica exata de cada blueprint é conteúdo do pack/consumidor, não hardcoded por Structurize.
## 5. Scan Tool
A Scan Tool permite:
- marcar uma região por dois cantos;
- capturar a estrutura nessa região;
- salvar scan em pack/diretório de blueprints;
- reutilizar o resultado com as ferramentas Structurize/MineColonies.
Scan de regiões grandes precisa ser tratado como operação potencialmente pesada em memória/IO e validado contra limites/config do servidor.
## 6. Build Tool
A Build Tool é a superfície central de placement/manipulação:
- seleciona blueprint/pack;
- mostra preview e posição/orientação;
- permite nomear/posicionar/deletar scans/blueprints conforme permissões e contexto;
- serve de base para workflows de construção usados por MineColonies.
O preview é client-side, mas o placement real precisa permanecer validado pelo servidor.
## 7. Outras ferramentas estruturais
O ecossistema atual do MineColonies documenta também:
- **Tag Tool**;
- **Shape Tool**;
- ferramentas de manipulação de blueprint/preview associadas ao Structurize.
Não atribuir ao Structurize conteúdo de gameplay específico do MineColonies; essas ferramentas são infraestrutura de construção/edição.
## 8. Shared previews / link sessions
Comandos atuais do ecossistema Structurize/MineColonies permitem sessões de preview compartilhado:
- `/structurize linksession create`;
- `/structurize linksession addplayer`;
- `/structurize linksession acceptinvite`.
Esse state colaborativo deve ser validado em multiplayer para evitar preview stale, permissões incorretas ou placement divergente.
## 9. Client-server blueprint sync
A documentação pública descreve sincronização de scans/blueprints entre cliente e servidor:
- blueprints disponibilizados pelo servidor podem ser baixados/localizados pelo cliente;
- blueprints locais podem ser enviados/colocados no servidor quando configuração/permissão permitem.
Isso cria uma fronteira de segurança e consistência: servidor deve controlar permissões e placement final, independentemente do preview local.
## 10. Load balancing e operações por tick
Structurize distribui operações grandes de construção/manipulação em buffers/ticks para evitar congelamento prolongado. A documentação expõe configuração de limite de operações por tick.
A configuração física do pack **não foi lida**; portanto nenhum valor atual de `maxOperationsPerTick` é afirmado. Valores exemplificados na documentação não devem ser confundidos com config real do usuário.
## 11. Release 1.0.833
A build física 1.0.833 é snapshot/Beta de 07/09/2026. O changelog público é pequeno e lista o commit **Cleanuppath (#852)**, além dos mínimos de BlockUI/Domum Ornamentum.
Como o canal é snapshot, incompatibilidades podem surgir mesmo sem mudança de versão major. Atualizações de MineColonies, BlockUI ou Domum Ornamentum devem ser testadas em conjunto.
## 12. Client / server
- Cliente: GUI, preview, seleção e manipulação visual.
- Servidor: placement real, permissões, world edits e sincronização de blueprints.
- O mesmo blueprint precisa produzir placement equivalente para todos os observadores, respeitando rotation/mirror/anchor definidos.
- Dedicated server deve iniciar com o stack sem exigir recursos puramente gráficos do cliente.
## 13. Lifecycle
Validar:
- client/server boot;
- carregar packs internos e externos;
- Scan Tool salvar/reabrir blueprint;
- Build Tool preview, rotate/mirror/move e placement;
- cancelar placement;
- chunk unload/reload durante operação grande;
- server restart com operação/blueprint state relevante;
- link session create/invite/accept/disconnect;
- update de blueprint pack já usado em colony/world;
- remoção/renomeação de blueprint referenciado por MineColonies.
## 14. Integrações concretas no pack
- **MineColonies 1.1.1381 snapshot:** consumidor principal e motivo da decisão Dependência.
- **BlockUI 1.0.211 snapshot:** dependency acima do mínimo.
- **Domum Ornamentum 1.0.236 snapshot-main:** dependency acima do mínimo e provider de blocos decorativos usados em blueprints.
- **Create/CreateColonies:** podem fornecer blocos exigidos por schematics; Structurize só registra/coloca o blueprint, não controla máquinas Create após placement.
- Mods de worldgen/claims/protection podem interceptar placement; testar permissões em multiplayer.
## 15. Riscos técnicos
1. **Snapshot drift:** stack MineColonies/Structurize/BlockUI/Domum pode quebrar por versões desalinhadas.
2. **Large scans:** blueprint grande pode gerar uso alto de memória/IO ou stutter de preview.
3. **Placement permissions:** cliente nunca deve conseguir contornar regras do servidor via blueprint local.
4. **Partial operations:** restart/chunk unload durante world edit não pode duplicar ou perder blocos silenciosamente.
5. **Missing mod blocks:** blueprint com blocos removidos/renomeados pode falhar ou substituir conteúdo de forma inesperada.
6. **Preview vs reality:** preview correto não prova placement server-side correto.
7. **Blueprint pack updates:** alteração de packs já usados pode quebrar upgrades/building references do MineColonies.
## 16. Matriz de testes
- [ ] Dedicated server boot com Structurize 1.0.833 + BlockUI 1.0.211 + Domum 1.0.236 + MineColonies 1.1.1381.
- [ ] Scan pequeno e grande salva/reabre corretamente.
- [ ] Build Tool preview, rotate, mirror e placement.
- [ ] Blueprint com blocos vanilla, Domum e Create.
- [ ] Placement respeita permissions/claims.
- [ ] Chunk unload/reload durante operação grande sem dupe/corrupção.
- [ ] Server restart após placement parcial sem reexecutar blocos indevidamente.
- [ ] Link session entre dois jogadores cria/compartilha preview corretamente.
- [ ] Cliente com blueprint local não consegue bypassar validação do servidor.
- [ ] MineColonies encontra e usa blueprint Structurize após relog/restart.
- [ ] Atualização de pack em cópia de teste preserva buildings existentes.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 17. Evidências
- Modlist física canônica 08/09/2026: JAR e versões do stack.
- CurseForge oficial Structurize 1.0.833: build Beta/snapshot, NeoForge 1.21.1 e mínimos BlockUI/Domum Ornamentum.
- Documentação oficial MineColonies/Structurize: blueprints, Scan Tool, Build Tool, link sessions e papel do Structurize na construção de schematics.
## 18. Revalidação física — 11/09/2026
O runtime físico continua exatamente `structurize-1.0.833-1.21.1-snapshot.jar`, com BlockUI `1.0.211`, Domum Ornamentum `1.0.236` e MineColonies `1.1.1381` no mesmo stack. A decisão **Dependência** permanece causalmente sustentada pelo MineColonies instalado.
A build continua snapshot/Beta; nenhuma operação de scan/placement/link session foi executada nesta recatalogação e a configuração real de operações por tick permanece não lida.
## 19. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `structurize-1.0.833-1.21.1-snapshot.jar`. A 1.0.833 continua sendo a snapshot/Beta 1.21.1 mais recente localizada; a release estável 1.0.832 permanece linha separada e não é um “downgrade recomendado”. O stack físico MineColonies `1.1.1381`, BlockUI `1.0.211` e Domum Ornamentum `1.0.236` continua acima dos mínimos publicados. A decisão **Dependência** permanece sustentada pelo MineColonies instalado. Nenhum scan, placement, link session ou teste de restart foi executado nesta revalidação.

## 20. Atualização upstream 1.0.835 — não instalada

A autoridade física continua em **Structurize 1.0.833-1.21.1-snapshot**. O CurseForge oficial publica **1.0.835-1.21.1-snapshot** como Beta para NeoForge 1.21.1 em 30/09/2026. A listagem pública atual não mostra um artefato 1.21.1 1.0.834 entre as duas versões.

### Changelog publicado
A release registra dois commits/fixes:
- **`fix event (#855)`**;
- **`use same handle as solid substitution block (#847)`**.

O texto público não especifica, por si só, qual evento foi corrigido nem toda a semântica do handle alterado. Por isso esta ficha não inventa classes, lifecycle ou bugs específicos adicionais. O segundo delta é tratado como mudança na superfície de substitution/placement handling, relevante para blueprint placement, sem afirmar comportamento não documentado.

### Impacto para o pack
Structurize é dependência direta do MineColonies e manipula blueprints/placement. Mesmo um delta curto pode ter fan-out alto quando toca eventos ou substitution handling. A promoção deve ser coordenada com MineColonies, BlockUI e Domum Ornamentum, preservando blueprints existentes.

### Gate de promoção 1.0.833 → 1.0.835
- [ ] Dedicated server boot com MineColonies/BlockUI/Domum físicos atuais.
- [ ] Scan Tool salva/reabre blueprint pequeno e grande.
- [ ] Build Tool preview e placement preservam rotate/mirror/substitutions.
- [ ] Solid substitution blocks e o handling relacionado não divergem entre preview e placement.
- [ ] Event-driven placement/cancel/reload não executa ação duas vezes.
- [ ] Chunk unload/reload durante operação grande não duplica nem perde blocos.
- [ ] Server restart após placement parcial não reexecuta operação concluída.
- [ ] Blueprint packs existentes do MineColonies continuam resolvendo buildings/upgrades.
- [ ] Link sessions multiplayer continuam sincronizando preview/ownership.

Fonte upstream: CurseForge Structurize 1.0.835-1.21.1-snapshot, file ID 9017852, changelog com PRs #855 e #847. Nenhum teste acima foi executado nesta atualização documental.
