# FTB Chunks — 2101.1.22

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81d5b8bdc7fec78bc3f7  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FTB Chunks
- **Arquivo JAR:** `ftb-chunks-neoforge-2101.1.22.jar`
- **Versão 1.21.1:** `2101.1.22`
- **Categoria:** QoL; Exploração
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/FTBTeam/FTB-Chunks/tree/1.21.1/main
- **Função:** Mapa/minimapa client-facing + claims, proteção e force-loading server-authoritative. Persiste team claim data em SNBT, integra FTB Teams, aplica políticas de interação/fake players e mantém APIs/eventos para claim/map/waypoints.
- **Dependências:** FTB Library + FTB Teams. Source 2101.1.22 usa FTB Library 2101.1.34 e FTB Teams 2101.1.9; pack instala Library 2101.1.35 e Teams 2101.1.11. Architectury é dependência de build/runtime do ecossistema conforme metadata upstream.
- **Compatibilidade/Riscos:** Force-loading pode elevar ticking/CPU; fake-player protection pode bloquear automação; double-protection com outros claim systems; stale force-load cache; concorrência client map (CME corrigida em 2101.1.22); fluid/fire boundary protection opcional tem custo upstream documentado. Bridge Create Aeronautics: FTB Chunks está presente.
- **Sobreposição:** Sobreposição com outros sistemas de mapa/claim/chunkloading é funcional, não automaticamente redundante. FTB Chunks deve permanecer autoridade dos próprios claims/protection/force-load; evitar segunda camada autorizando ou negando a mesma ação sem política explícita.
- **Observações:** Runtime físico confirmado: FTB Chunks 2101.1.22, corrigindo metadata antiga que ainda citava 2101.1.21. Config server/world `ftbchunks-world.snbt`; team data persistido em `<world>/ftbchunks/<team UUID>.snbt`; client/local map data usa `local/ftbchunks`.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial FTBTeam/FTB-Chunks branch 1.21.1/main exatamente em 2101.1.22 + changelog oficial da mesma versão.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — FTB Chunks 2101.1.22 source-pinned; API, claims/protection, fake players, force-loading, persistência SNBT, mapa client, configs, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `ftb-chunks-neoforge-2101.1.22.jar`, mod id `ftbchunks`. O branch oficial `FTBTeam/FTB-Chunks:1.21.1/main` declara exatamente `mod_version=2101.1.22` e Minecraft 1.21.1. Esta ficha separa três domínios: **mapa/minimapa client-facing**, **claims/proteção server-authoritative** e **force-loading server-side**.

## 1. Identidade, versão e authority
- **Mod:** FTB Chunks.
- **JAR físico:** `ftb-chunks-neoforge-2101.1.22.jar`.
- **Mod id:** `ftbchunks`.
- **Versão instalada:** `2101.1.22`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source pin:** branch `1.21.1/main`, exatamente 2101.1.22.
- **Dependências upstream no source:** FTB Library `2101.1.34`, FTB Teams `2101.1.9`, Architectury `13.0.8` no ambiente de build dessa release. O pack instala FTB Library 2101.1.35 e FTB Teams 2101.1.11.
- **Authority:** FTB Teams é autoridade de identidade/equipe; FTB Chunks é autoridade de claim, proteção, limites, force-load e dados de mapa próprios; FTB Library fornece UI/config/utilidades compartilhadas.

## 2. Papel no modpack
FTB Chunks combina mapa/minimapa no cliente com sistema de claim e force-loading no servidor. Claims não são apenas marcações visuais: proteções de interação, block edit, entidades não-vivas, explosões, fake players, pistons e opcionalmente fluxo de fluidos/fogo dependem das políticas do mod. Force-loading mantém chunks ativos conforme limites e política de presença/offline.

## 3. API e objetos centrais confirmados
O source 2101.1.22 expõe API pública com:
- `FTBChunksAPI`;
- `ClaimedChunkManager`;
- `ClaimedChunk`;
- `ChunkTeamData`;
- `ClaimResult`;
- `Protection` e `ProtectionPolicy`;
- `FTBChunksProperties` e `FTBChunksTags`;
- `LevelMinYCalculator`;
- client API: `FTBChunksClientAPI`;
- eventos client: `ChunksUpdatedFromServerEvent`, `MapIconEvent`, `WaypointManagerEvent`;
- icon/minimap surfaces: `MapIcon`, `MapType`, `WaypointIcon`, `MinimapContext`, `MinimapInfoComponent`.
Para integrações próprias, preferir API/eventos publicados em vez de mixins quando eles cobrem o caso.

## 4. Persistência e ownership de dados
`ClaimedChunkManagerImpl` confirma que FTB Chunks mantém:
- `teamData` por UUID de Team;
- mapa de `claimedChunks` por `ChunkDimPos`;
- cache de chunks efetivamente force-loaded por dimensão;
- diretório server/world `ftbchunks` via `LevelResource("ftbchunks")`;
- arquivos por equipe em `<world>/ftbchunks/<team UUID>.snbt`, carregados via SNBT;
- diretório client/local `local/ftbchunks` criado no game folder.
O manager possui lifecycle explícito `init(TeamManager)` e `shutdown()`, e `initForceLoadedChunks(ServerLevel)` reaplica force-loading persistido à dimensão. Integrações não devem duplicar persistência de claims nem assumir que estado client-side do mapa é autoridade de proteção.

## 5. Configuração server/world — `ftbchunks-world.snbt`
O source pinado define `KEY = ftbchunks-world` e documenta dois níveis:
- defaults de modpack: `<instance>/config/ftbchunks-world.snbt`;
- override local do servidor: `<instance>/world/serverconfig/ftbchunks-world.snbt`.
Principais opções confirmadas e defaults:
- `disable_protection = false` — desliga toda proteção, mantendo claims úteis para force-load.
- `ally_mode` — política de aliados.
- `pvp_mode` — `ALWAYS`, `NEVER` ou `PER_TEAM` conforme descrição da config.
- `no_wilderness = false` + lista por dimensões — pode exigir claim para editar/interagir.
- `force_disable_minimap = false`.
- `long_range_tracker_interval = 20` ticks; `0` desliga tracking de longo alcance.
- `protect_unknown_explosions = true`.
- `require_game_stage = false`; quando ativo exige stage `ftbchunks_mapping` via provider de stages/KubeJS/Gamestages.
- `location_mode_override = false`.
- `piston_protection = true`.
- `flowing_fluid_protection = false` — opcional e explicitamente marcada upstream como potencial custo de performance por alta frequência de fluid ticks.
- `fire_spread_protection = false` — também opcional com alerta de performance.

### Fake players
- política global `fake_players` usa `ProtectionPolicy` com default `CHECK`;
- retenção de logs de acesso impedido: 7 dias por default;
- defaults de team incluem `def_fake_players=false` e `def_fake_player_ids=true`.

### Claiming
- `max_claimed_chunks = 500` por default; FTB Ranks pode sobrescrever.
- blacklist/whitelist de dimensões.
- `max_idle_days_before_unclaim = 0` — zero desliga expiração automática.
- `hard_team_claim_limit = 0` — zero sem hard limit adicional.
- party limit mode suporta `LARGEST`, `SUM`, `OWNER`, `AVERAGE`.

### Force loading
- `max_force_loaded_chunks = 25` por default; FTB Ranks pode sobrescrever.
- `force_load_mode` controla disponibilidade offline.
- `max_idle_days_before_unforce = 0`.
- `hard_team_force_limit = 0`.

### Waypoint sharing
Compartilhamento server/party/players é `true` por default para as três opções.

### Defaults de permissões do time
O source define defaults para entity interact, block interact/edit, nonliving entity attack, explosões, mob griefing, claim visibility, player visibility e PvP. Em NeoForge, block interact/edit têm propriedades específicas habilitadas em ambiente Forge-like.

## 6. Proteção e fake players
`ClaimedChunkManagerImpl.shouldPreventInteraction(...)` confirma o fluxo central:
- somente `ServerPlayer` entra nessa checagem;
- `disable_protection` e bypass de admin podem liberar a operação;
- fake players são detectados via `PlayerHooks.isFake`;
- política global de fake player pode forçar allow/deny;
- claim é resolvido pela posição/dimensão;
- `no_wilderness` pode aplicar restrição mesmo sem claim;
- a decisão final usa `ProtectionPolicy`;
- acesso impedido de fake player em claim pode ser registrado no team data.
A release 2101.1.16 mudou a ordem do admin bypass para ocorrer antes da checagem de fake players, permitindo fake player com UUID de admin em bypass operar como esperado.

## 7. Proteção ambiental e entidades
A 2101.1.15 adicionou/fortaleceu:
- proteção de entidades não-vivas e armor stands contra dano indireto como flechas/explosões;
- proteção opcional de fluidos atravessando fronteira de claims;
- proteção opcional de propagação de fogo entre claims.
Pistons também têm proteção dedicada na config. Mods de automação que usam fake players, deployers, miners ou entidades auxiliares precisam ser testados contra essas políticas em vez de receber bypass genérico.

## 8. Mapa, minimapa, waypoints e client
FTB Chunks mantém mapa/minimapa e waypoints no lado cliente, sincronizados com informações relevantes do servidor. A 2101.1.22:
- passou a ignorar foliage como grass/bushes/ferns por default no mapping para reduzir ruído;
- adicionou borda escurecida constante aos ícones de entidades/jogadores;
- corrigiu um problema de thread safety que podia causar `ConcurrentModificationException` client-side;
- corrigiu posição de player icon na claim manager screen em algumas resoluções.
A 2101.1.21 também otimizou minimap rendering, coalescendo textures para reduzir uploads à GPU, e moveu mais do salvamento de imagens do mapa para off-thread.

## 9. Eventos e integração de mapa
A 2101.1.14 adicionou `ChunksUpdatedFromServerEvent` para agregar múltiplas atualizações de chunk info em um evento client-side eficiente. Esse evento é a superfície indicada para mods integrarem claim info sem mixins quando suficiente. `MapIconEvent` e `WaypointManagerEvent` dão outras extensões client-facing.
Waypoints transient podem existir e não persistir após logout/dimension change, conforme changelog anterior da linha 2101.1.x; integrações devem escolher persistência conscientemente.

## 10. Force-loading
O manager reconstrói o mapa de force-loaded chunks a partir dos claims efetivamente force-loaded e, na inicialização de `ServerLevel`, chama a plataforma para adicionar os chunks novamente e força save do chunk source. O cache `forceLoadedChunkCache` é lazy e possui `clearForceLoadedCache()`. Qualquer mutação de state que ignore a invalidation do cache corre risco de estado obsoleto.

## 11. Client / server
- **Servidor:** claims, proteção, team data, limits, force-loading, políticas de fake players e validação de ações.
- **Cliente:** render do mapa/minimapa, ícones, telas de claim/waypoint/config e cache gráfico.
- **Compartilhado/network:** sync de claim info, player tracking e packets de ações/telas.
Nunca usar o mapa client como fonte de verdade para autorização. A proteção deve ser consultada no servidor/API do manager.

## 12. Lifecycle
Pontos críticos:
- server start → init do manager e carregamento lazy de team SNBT;
- level load → reaplicação de force-loaded chunks;
- team creation/change/delete → criação/atualização/remoção de team data e limites;
- player login/logout → visibilidade, tracking e state client;
- dimension change → map/waypoint/tracking e force-load por dimensão;
- server shutdown → manager `shutdown()`;
- config change → `DimensionFilter.clearMatcherCaches()` e recomputação de limites de todas as equipes;
- map save off-thread → concorrência client-side;
- LAN/open-to-LAN → já houve bugs específicos de unclaim/forceload em releases anteriores.

## 13. Multiplayer
Claims são team-owned, não simplesmente player-owned. Testar mudança de equipe, saída de membro, owner/party limit mode, dois jogadores editando fronteira simultaneamente, fake players e bypass. A 2101.1.15 corrigiu limites que não atualizavam imediatamente quando jogador saía do time; isso torna team membership lifecycle relevante.

## 14. Integrações concretas no pack
- **FTB Teams 2101.1.11:** autoridade de equipes; FTB Chunks consome `FTBTeamsAPI`.
- **FTB Library 2101.1.35:** UI/config/math/SNBT e infraestrutura compartilhada. 2101.1.21+ de FTB Chunks exige Library 2101.1.34+ por causa do panel scrolling; o pack atende.
- **FTB Quests:** integração indireta por ecossistema FTB e game stages/visibility pode existir, mas não presumir regras de quest sem configuração do pack.
- **KubeJS:** `require_game_stage` cita KubeJS/Gamestages como providers possíveis para `ftbchunks_mapping`; só atua se a opção for habilitada/configurada.
- **Create Aeronautics: FTB Chunks 1.1.1 está instalado:** bridge específica presente no pack; deve ser documentada na ficha própria e smoke-tested com claims/contraptions.
- **Mods com fake players/automação:** interação depende das políticas FTB Chunks; coexistência não implica permissão automática.

## 15. Riscos técnicos
- **Chunkloading excessivo:** muitos force-loads aumentam CPU/memória/ticking mesmo sem jogadores.
- **Stale force-load cache:** mutação sem invalidation correta.
- **Double protection:** outro mod de claims/protection pode bloquear a mesma ação por política diferente.
- **Fake-player denial:** automações podem parecer quebradas por protection policy.
- **Admin bypass leakage:** não reutilizar bypass fora do contexto previsto.
- **Fluid/fire boundary performance:** opções são off por default por custo potencial real.
- **Client concurrency:** 2101.1.22 corrigiu CME; regressão deve ser monitorada sob map saving/rendering.
- **Map data corruption/stale image:** client local data e save off-thread exigem teste de shutdown/reconnect.
- **Team membership race:** limites/ownership mudam quando membros entram/saem.
- **Version coupling:** FTB Chunks 2101.1.21+ depende do panel scrolling de FTB Library 2101.1.34+.

## 16. Matriz de testes obrigatória
- [ ] Dedicated server boot e shutdown limpos.
- [ ] Criar claim, reiniciar servidor e confirmar persistência SNBT.
- [ ] Unclaim e delete de team; arquivo/state não fica órfão indevidamente.
- [ ] Force-load, logout de todos, restart e verificar política `force_load_mode`.
- [ ] Alterar max claims/force-loads e confirmar recomputação de limites.
- [ ] Player entra/sai de team; limites e ownership atualizam imediatamente.
- [ ] Fake player permitido/negado por default, override global e política de team.
- [ ] Admin bypass e fake player com UUID correspondente sem bypass indevido.
- [ ] Block edit/interact/entity interact/nonliving attack em claim próprio, aliado, público e adversário.
- [ ] Explosion protection com source conhecido e desconhecido.
- [ ] Piston atravessando fronteira de claims.
- [ ] Flowing fluid/fire spread protection ativada em teste, medindo custo e regra de fronteira.
- [ ] Open-to-LAN unclaim/forceload.
- [ ] Mapa/minimapa após teleport/dimension change/login-relogin.
- [ ] Waypoints entre dimensões e transient waypoint lifecycle.
- [ ] Stress client com map saving/rendering para detectar CME/regressão de concorrência.
- [ ] Bridge Create Aeronautics: FTB Chunks com contraption entrando/saindo de claim.

## 17. Evidências e limites
**Source primário pinado:** `FTBTeam/FTB-Chunks`, branch `1.21.1/main`, `mod_version=2101.1.22`.
**Classes auditadas:** `FTBChunksWorldConfig`, `ClaimedChunkManagerImpl` e árvore de API/client API.
**Changelog oficial:** 2101.1.22 e releases 2101.1.x relevantes para segurança/lifecycle.
**Modlist física:** confirma JAR, mod id, versão e presença das integrações FTB/Aeronautics do pack.
**Não foram executados testes de runtime nesta catalogação.** A matriz acima é requisito futuro de validação.
