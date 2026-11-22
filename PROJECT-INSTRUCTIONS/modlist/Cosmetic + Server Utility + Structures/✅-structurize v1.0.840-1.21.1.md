# Structurize — 1.0.840-1.21.1 (instalação declarada em 22/11/2026)

> **Vigente em 22/11/2026:** `1.0.833-1.21.1-snapshot` → **`1.0.840-1.21.1`**; JAR oficial publicado `structurize-1.0.840-1.21.1.jar`, [CurseForge](https://www.curseforge.com/minecraft/mc-mods/structurize/files/9680037). Instalação confirmada pela declaração do usuário, não por leitura do JAR local ou metadados da nova build.

> **Auditoria das publicações intermediárias:** Histórico do dossier cobre 1.0.835, 1.0.837, 1.0.838, 1.0.839-snapshot/release e 1.0.840-snapshot/release, com source pinned e comparação de sete commits. Mudanças incluem iterador de placement, tolerância no rendering de blueprint, validações de permissão, paths e symlinks contra traversal, fix de NPE e validações de nomes/extensions .blueprint. 1.0.840 final é Release para 1.21.1 NeoForge.

> **Dependências aguardando sincronização documental:** MineColonies, BlockUI e Domum Ornamentum são consumers do stack; manifesto do file 9680037 cita BlockUI >=1.0.191-1.21.1-snapshot e Domum Ornamentum >=1.0.203-1.21.1-snapshot. Divergências de entradas futuras são pendência de sincronização documental, não veto.

> **Riscos/limites de evidência:** Blueprints e placement modificam mundo; reforços de validação não significam garantia de segurança. Testar ACL em multiplayer, scans/path traversal, preview e unload/restart.

> **Snapshot físico histórico (posição #529):** structurize-1.0.833-1.21.1-snapshot.jar, 1.0.833-1.21.1-snapshot; dados referentes à modlist física anterior à declaração de atualização.


> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#529**: JAR `structurize-1.0.833-1.21.1-snapshot.jar`, mod id `structurize`, runtime `1.0.833-1.21.1-snapshot`, SHA-1 `e81934809824cb1435b8221313a143480379893a`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Structurize
- **Arquivo JAR instalado (declarado pelo usuário):** `structurize-1.0.840-1.21.1.jar` — [distribuição](https://www.curseforge.com/minecraft/mc-mods/structurize/files/9680037); local não relido.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.0.840-1.21.1` (antes `1.0.833-1.21.1-snapshot`; metadata do novo JAR não lido).
- **Estado no pack:** `1.0.840-1.21.1` declarado instalado pelo usuário em 22/11/2026; inventário documental sincronizado; sem inspeção local.
- **Estado da pesquisa:** versão-alvo e distribuição verificadas; lançamentos intermediários documentados no dossier; lacunas e limites declarados no cabeçalho.
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Infraestrutura de blueprints/scans/preview/placement estrutural usada pelo MineColonies, com Scan Tool, Build Tool, blueprint packs, sincronização client-server e load balancing de operações.
- **Dependências:** MineColonies, BlockUI e Domum Ornamentum são consumers do stack; manifesto do file 9680037 cita BlockUI >=1.0.191-1.21.1-snapshot e Domum Ornamentum >=1.0.203-1.21.1-snapshot. Divergências de entradas futuras são pendência de sincronização documental, não veto.
- **Sobreposição:** Tem ferramentas de construção/schematic, mas sua função de biblioteca para MineColonies é distinta do Schematicannon do Create.
- **Compatibilidade/Riscos:** Blueprints e placement modificam mundo; reforços de validação não significam garantia de segurança. Testar ACL em multiplayer, scans/path traversal, preview e unload/restart.
- **Observações vigentes:** distribuição `structurize-1.0.840-1.21.1.jar`; versão `1.0.840-1.21.1` declarada instalada, sem metadata/SHA local. Dados anteriores na documentação detalhada são históricos.
- **Procedência:** posição física histórica #529; declaração de instalação em 22/11/2026 e publicação oficial [CurseForge](https://www.curseforge.com/minecraft/mc-mods/structurize/files/9680037); nenhum JAR local atual reinspecionado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/structurize ; https://www.curseforge.com/minecraft/mc-mods/structurize/files/8829722
- **Atualização/Status:** 22/11/2026 — `1.0.833-1.21.1-snapshot` → `1.0.840-1.21.1` documentado. Histórico do dossier cobre 1.0.835, 1.0.837, 1.0.838, 1.0.839-snapshot/release e 1.0.840-snapshot/release, com source pinned e comparação de sete commits. Mudanças incluem iterador de placement, tolerância no rendering de blueprint, validações de permissão, paths e symlinks contra traversal, fix de NPE e validações de nomes/extensions .blueprint. 1.0.840 final é Release para 1.21.1 NeoForge.
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
## Atualização incremental — 20/11/2026: 1.0.840 NeoForge 1.21.1

A [publicação 1.0.840](https://www.curseforge.com/minecraft/mc-mods/structurize/files/9680037) (file ID 9680037) é Release para NeoForge 1.21.1 de **18/11/2026**, posterior à 1.0.835-snapshot anteriormente documentada. O CurseForge informa dependências mínimas **BlockUI 1.0.191-1.21.1-snapshot** e **Domum Ornamentum 1.0.203-1.21.1-snapshot**; o projeto instalado possui esses mods, mas essa informação não comprova a versão física atual sem recapturar o inventário.

O changelog da publicação aponta para **Full Changelog** no upstream sem individualizar mudanças na página. Não foi reconstruído, nesta passagem, um inventário exaustivo de builds 1.0.836–1.0.839, nem demonstrado que todos os números tiveram JAR publicado. Exige reconciliação de tags/PRs/commits e avaliação de alterações em blueprint/placement, substitutions, MineColonies e lifecycle de mundo antes de promover a versão.

**Status PENDENTE:** 1.0.840 localizada, auditoria de publicações intermediárias incompleta. Baseline físico 1.0.833-1.21.1-snapshot e basename preservados; nenhuma alteração de arquivo JAR ou execução de testes.

## 21. Auditoria incremental concluída — 20/11/2026: 1.0.833 → 1.0.840, NeoForge 1.21.1

**Identidade física preservada:** `structurize-1.0.833-1.21.1-snapshot.jar`, posição #529, metadata/sha documentados acima. **Upstream publicado:** [1.0.840 Release, 18/11/2026](https://www.curseforge.com/minecraft/mc-mods/structurize/files/9680037) para NeoForge 1.21.1. Não houve atualização ou teste do JAR físico.

**Cadeia completa de versões publicadas posteriores à instalação, conforme [catálogo oficial](https://www.curseforge.com/minecraft/mc-mods/structurize/files/all):** 1.0.835-snapshot (30/09), 1.0.837-snapshot (26/10), 1.0.838-snapshot (07/11), 1.0.839-snapshot (08/11), 1.0.839 Release (11/11), 1.0.840-snapshot (18/11) e 1.0.840 Release (18/11). O catálogo consultado não contém publicações 1.0.834 ou 1.0.836 para NeoForge 1.21.1; não confundir números de build ausentes com releases ocultas. Releases e snapshots 1.0.839 compartilham o mesmo commit source; o mesmo ocorre com 1.0.840.

**Comparação oficial do código pinado:** [tag instalado 1.0.833-snapshot](https://github.com/ldtteam/Structurize/tree/v1.21.1-1.0.833-snapshot) → [tag release 1.0.840](https://github.com/ldtteam/Structurize/tree/v1.21.1-1.0.840). Os sete commits do branch alvo e seus efeitos foram conciliados por [compare source 1.0.833→1.0.840](https://github.com/ldtteam/Structurize/compare/v1.21.1-1.0.833-snapshot...v1.21.1-1.0.840):

| Tag / publicação | Data | Commits específicos / alterações comprovadas |
|---|---|---|
| 1.0.835-snapshot | 30/09/2026 | [#847](https://github.com/ldtteam/Structurize/pull/847) reutiliza o handler de solid substitution block; [#855](https://github.com/ldtteam/Structurize/pull/855) corrige evento, sem semântica adicional confirmada pelo título. Já documentados na seção 20. |
| 1.0.837-snapshot | 26/10/2026 | [#859](https://github.com/ldtteam/Structurize/pull/859) admite iterator/supplier externo em `StructurePlacer.executeStructureStep`, permitindo iteração sem preservar state indevidamente. [#858](https://github.com/ldtteam/Structurize/pull/858) amplia fault-tolerant blueprint rendering: exceções de entidades passam a ser capturadas além de `ClassCastException`, com tratamento de BlockEntities; altera também mensagens de erro para diagnóstico. |
| 1.0.838-snapshot | 07/11/2026 | [#863](https://github.com/ldtteam/Structurize/pull/863) adiciona **validação de mensagens/dados de blueprint**: verificação `player.isCreative()` antes da operação correspondente, normalização de paths, proibição de symlinks/path traversal e exigência de extensão `.blueprint`; grava `pack.json` sem seguir links simbólicos. Relevante à segurança do servidor e à autoridade de colocação. |
| 1.0.839-snapshot / 1.0.839 Release | 08 e 11/11/2026 | [#862](https://github.com/ldtteam/Structurize/pull/862) elimina NPE ao calcular suporte/gravity sobre bloco não sólido quando `placementContext.getBluePrint()` é nulo; preserva fallback ao iterar placement. Os dois tags upstream apontam ao mesmo commit `9ad76cfd`. |
| 1.0.840-snapshot / 1.0.840 Release | 18/11/2026 | [#864](https://github.com/ldtteam/Structurize/pull/864) reforça validação de nomes e caminhos em `ClientStructurePackLoader` e `StructurePacks`: rejeita packName vazio, path inválido, traversal fora do diretório de scans/packs e arquivos que não terminam em `.blueprint`, antes de ler/gravar blueprints. Ambos os tags apontam ao commit `4748f223`. |

**Dependências declaradas no file 9680037:** BlockUI `1.0.191-1.21.1-snapshot` ou superior e Domum Ornamentum `1.0.203-1.21.1-snapshot` ou superior. Conferir as versões reais da instância e a integração com MineColonies antes de trocar JAR, sem inferir compatibilidade pelo nome.

**Relevância para o dossier:** a linha recente corrige falhas de permissões e integridade do transporte de blueprints, traversal e symlink, renderização resiliente, iteradores de placement e NPE de suporte em blocos não sólidos. O servidor deve conservar autoridade sobre colocação e operações de mundo. O teste in-game continua pendente (blueprints locais e transferidos, players criativos/não criativos, rejeição de caminhos hostis, clientes em multiplayer, resource reload, migração de packs, alteração parcial em chunks). Source pinado verifica os deltas; nenhuma afirmação de verificação de binários, segurança completa ou QA foi feita.

**Status final desta auditoria: CONCLUÍDO — DOCUMENTAÇÃO VERIFICADA.** Todos os artefatos publicados após o snapshot físico até 1.0.840 foram inventariados e os deltas próprios de cada tag foram confrontados com o source oficial. A nota parcial anterior (seção 20 e índice histórico de 20/11) é substituída por esta reconciliação. Instalação e homologação de runtime permanecem pendentes. Nome certificado, versão, pasta e JAR físico permanecem intactos.
