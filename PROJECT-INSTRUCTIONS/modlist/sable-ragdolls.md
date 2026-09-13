# Sable Ragdolls

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db81ef88d9d1f05beee14a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable_player_ragdoll-1.21.1-0.7.5.jar`, mod id `sable_player_ragdoll`, runtime `0.7.5`, mixins `sable_player_ragdoll.neoforge.mixins.json` e `sable_player_ragdoll.mixins.json`; Sable 2.0.5, Sable x CPM 0.3.2+1.21.1, Sable Ragdolls Patch 1.9 e Ragdoll Reactions 0.7.0 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable Ragdolls 0.7.5 e as integrações físicas citadas estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable Ragdolls
- **Arquivo JAR:** `sable_player_ragdoll-1.21.1-0.7.5.jar`
- **Versão 1.21.1:** 0.7.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL, Compat
- **Função:** Sistema/API de ragdolls físicos baseado em Sable, com player sessions, dummies, despawn, item-tag triggers e API pública para addons.
- **Dependências:** Sable 2.0.5 é provider físico atual. Source 0.7.5 usa Sable Companion 1.6.0 e integrações opcionais como Curios. Stack físico também inclui Sable x CPM 0.3.2, Ragdolls Patch 1.9 e Ragdoll Reactions 0.7.0.
- **Sobreposição:** Core de player ragdoll/API. Não confundir com `Sable mob ragdoll corpses` 1.1.5 nem com `Sable: Ragdoll Corpse` 0.3.0; são providers/projetos distintos.
- **Compatibilidade/Riscos:** Riscos: double-launch por múltiplos triggers, root/part orphan, equipment duplication, pose/client-server desync, ghost state após disconnect/respawn, stale physics handles, addon API drift e distribuição pública CurseForge ainda presa em 0.7.2.
- **Observações:** Authority física e source oficial são 0.7.5. CurseForge ainda lista 0.7.2 como arquivo principal, mas `gradle.properties` oficial declara 0.7.5 e o Ragdolls Patch 1.9 exige explicitamente 0.7.5; não fazer downgrade automático.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + repositório oficial sable-player-ragdoll `main` com mod_version 0.7.5 + API source atual + CurseForge oficial + requisito publicado do Patch 1.9.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-ragdolls
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Sable Ragdolls 0.7.5 reconstruído contra source oficial 0.7.5: sessions/API, pose, dummies, dismember, equipment snapshots, lifecycle, integrations, distribuição CF divergente, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-28

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable_player_ragdoll-1.21.1-0.7.5.jar`, mod id `sable_player_ragdoll`, versão `0.7.5`, NeoForge 1.21.1. Há uma divergência de distribuição importante: a listagem pública principal do CurseForge ainda exibe 0.7.2, mas o repositório oficial `main` declara **mod_version=0.7.5**, o JAR físico é 0.7.5 e o Sable Ragdolls Patch 1.9 exige explicitamente 0.7.5. Portanto 0.7.5 é a authority do pack e também é corroborada upstream.

## 1. Identidade e papel
- **Mod:** Sable Ragdolls.
- **JAR:** `sable_player_ragdoll-1.21.1-0.7.5.jar`.
- **Mod id:** `sable_player_ragdoll`.
- **Versão instalada:** `0.7.5`.
- **Minecraft:** 1.21.1.
- **Java:** 21 no source/runtime do projeto.
- **Loader:** NeoForge.
- **Mixin configs físicos:** `sable_player_ragdoll.neoforge.mixins.json` e `sable_player_ragdoll.mixins.json`.
- **Papel:** sistema/API de ragdolls físicos baseado nos sublevels e physics do Sable.

## 2. Source pin 0.7.5 confirmado
O `gradle.properties` do repositório oficial atual declara:
- `mod_version=0.7.5`;
- `minecraft_version=1.21.1`;
- Java 21;
- Sable compile target 2.0.3;
- Sable Companion 1.6.0;
- Curios 9.5.1+1.21.1.

Isso fornece um source pin coerente com o JAR físico 0.7.5, apesar de a interface pública de arquivos do CurseForge estar defasada em 0.7.2.

## 3. Relação com Sable
Cada ragdoll é construído sobre a infraestrutura de sublevels/physics do Sable. Sable continua authority de body/transform/physics; Sable Ragdolls define como um jogador/dummy é convertido em conjunto de partes físicas, como essas partes são registradas e como o state de ragdoll é gerenciado.

A bridge não deve criar uma segunda física paralela fora do Sable.

## 4. Features de projeto confirmadas
A publicação oficial do projeto documenta:
- ragdoll manual via tecla H;
- player ragdolls físicos;
- spawning de dummy ragdolls;
- skin/profile para dummies;
- despawn configurável de dummies;
- datapack item tags para itens que causam ragdoll-on-hit;
- API pública para addons.

Essas features são lineage funcional do projeto e permanecem compatíveis com o source 0.7.5 atual, cuja API pública confirma expansão desses controles.

## 5. RagdollAPI — launch e sessão ativa
O source 0.7.5 expõe `RagdollAPI` com operações server-side para lançar ragdoll de `ServerPlayer` com velocidade inicial e opções, consultar sessão ativa e verificar se um player está ragdolled.

A API recusa iniciar outro ragdoll quando o player já está em state ragdolled, o que funciona como guard contra double-launch em integrations bem-comportadas.

## 6. Pose e lançamento assíncrono
O source atual permite launch com snapshot de pose inicial e, quando necessário, solicita pose antes de concluir o launch. Isso evidencia uma boundary client/server de pose/render que precisa convergir antes de materializar corretamente o corpo físico.

A pose visual não pode substituir server authority do state ragdoll; ela serve de snapshot inicial para montar a representação física.

## 7. Playerless dummies
A API 0.7.5 possui `spawnPlayerless` com:
- posição;
- heading;
- GameProfile;
- velocidade linear;
- regra de despawn;
- opções de limbs.

O projeto usa um profile default para dummy quando nenhum perfil custom é fornecido. Isso suporta dummies e integrations sem exigir que haja player ativo correspondente.

## 8. Despawn e sessions
A API trabalha com sessions e regras de despawn, permitindo que addons controlem duração/condições sem manter um registry paralelo próprio.

Um addon que inicia ragdoll deve reutilizar a session/API do provider e não criar timer/state duplicado fora do Sable Ragdolls.

## 9. Dismemberment e partes
A API pública 0.7.5 expõe operações de dismember por root/part sublevel e identifica partes de ragdoll pelo sublevel correspondente.

Isso amplia a superfície de integradores: dismember precisa preservar ownership da root, remover apenas a parte alvo e não deixar sublevels/handles órfãos.

## 10. Equipment snapshots
O source atual possui captura/aplicação de equipment snapshots e separa escopos de equipamento, incluindo integração opcional com mods.

Isso é especialmente relevante para:
- armor/cosmetics;
- Curios/extra equipment;
- corpse/dummy rendering;
- bridges como Sable x CPM.

Copiar equipamento para o ragdoll é representação/state do ragdoll; não deve duplicar itens funcionais no inventário do jogador.

## 11. Corpse flag e grabbing
A API 0.7.5 expõe controle para marcar ragdoll como corpse e para desabilitar grabbing em sublevels específicos. Isso fornece primitives para addons de corpse/dragging sem que eles precisem modificar internals diretamente.

A presença dessas APIs não confirma que o provider `ragdoll_corpse` esteja instalado no pack atual.

## 12. Mob-facing API no source atual
O `RagdollAPI` 0.7.5 atual também expõe operações para lançar/release ragdolls de `LivingEntity`, criar mobless ragdolls e remover/dismember por sublevel.

Isso é **superfície pública de API** da build atual; não deve ser confundido com o mod separado `Sable mob ragdoll corpses 1.1.5`, que está fisicamente presente e possui seu próprio ownership de conteúdo/lifecycle.

## 13. Velocity e physics handle safety
A session API consulta o physics handle do sublevel e verifica validade antes de ler velocidade. Esse padrão é importante: handles podem desaparecer quando body/sublevel é removido.

Addons que guardam handles sem revalidar podem provocar stale-handle crashes; o próprio provider oferece abstrações de session para reduzir isso.

## 14. Manual ragdoll e input
A feature pública “Press H to tumble” é um gatilho de usuário, mas a transição funcional deve ocorrer server-side. O cliente solicita a ação; o servidor/provider decide criar o ragdoll válido.

Spam de input não deve criar duas roots/sessions para o mesmo player graças ao guard de state ativo.

## 15. Datapack item tags
O projeto suporta tags de itens que provocam ragdoll-on-hit. Essas tags são data-driven e devem ser testadas após `/reload` quando aplicável.

Um hit deve produzir no máximo uma transição de ragdoll por evento lógico, mesmo se Ragdoll Reactions ou outro addon também observar o mesmo dano.

## 16. Integrações concretas no pack
- **Sable 2.0.5:** physics/sublevel provider.
- **Sable x CPM 0.3.2:** render de modelos CPM nos ragdolls.
- **Sable Ragdolls Patch 1.9:** fixes adicionais de render, collision e ações durante ragdoll.
- **Ragdoll Reactions 0.7.0:** addon presente que pode disparar ragdoll por eventos/reactions.
- **Sable mob ragdoll corpses 1.1.5:** mod separado presente; não deve ser confundido com a API player-ragdoll ou com `ragdoll_corpse` 0.3.0.
- **Punchy 2.7e:** integração relevante ao patch 1.9, não ao core por default.

## 17. Client / Server
Ragdoll state, session/root IDs, physics body, dismember e despawn precisam ser server-authoritative. Cliente lida com pose snapshot, render das partes, skin/model e input local.

Se um cliente perder render data, o ragdoll funcional não deve desaparecer do servidor; se o servidor remove a session, o cliente precisa descartar a representação.

## 18. Lifecycle crítico
Validar:
- manual launch;
- hit-triggered launch;
- death/respawn;
- dismount/release;
- dismember;
- dummy spawn/despawn;
- login/relogin;
- disconnect enquanto ragdolled;
- dimension change;
- chunk/sublevel unload;
- server restart;
- equipment snapshot refresh;
- remoção por API/command.

Nenhuma dessas transições pode deixar root/body/seat/owner state órfão.

## 19. Multiplayer e identidade
Cada ragdoll é ligado a UUID/profile/root específicos. Dois jogadores ragdolled ao mesmo tempo devem manter:
- root/sublevels separados;
- equipamento correto;
- skin/profile correto;
- velocity/session independentes;
- release/despawn independente.

Observers entrando depois precisam receber state suficiente para renderizar o ragdoll já existente.

## 20. Divergência CurseForge 0.7.2 × source/JAR 0.7.5
O CurseForge público ainda lista 0.7.2 como arquivo principal e único mais recente visível. Isso não autoriza downgrade:
- o JAR físico é 0.7.5;
- o source oficial atual declara 0.7.5;
- o Ragdolls Patch 1.9 publicado exige explicitamente 0.7.5.

Registrar essa divergência evita “corrigir” o pack para uma build pública mais antiga por engano.

## 21. Riscos técnicos
1. **Double ragdoll trigger:** dois addons/eventos iniciam a mesma session.
2. **Root/part orphan:** dismember/removal deixa sublevel ou handle ativo.
3. **Equipment duplication:** snapshot visual vira item funcional duplicado.
4. **Pose desync:** snapshot inicial/render diverge do body server-side.
5. **Disconnect/respawn ghost state:** player retorna sem liberar ragdoll antigo.
6. **Addon API drift:** patch/reactions/CPM bridge esperam outra API.
7. **Stale physics handle:** consumer usa body removido.
8. **Data reload duplication:** item tags/listeners disparam duas vezes.
9. **Distribution drift:** CurseForge 0.7.2 induz downgrade incorreto do JAR 0.7.5.
10. **Mob/player ownership confusion:** mod separado de mob corpse é tratado como o mesmo provider.

## 22. Matriz de testes
- [ ] Dedicated server inicia com Sable Ragdolls 0.7.5 + Sable 2.0.5.
- [ ] H inicia exatamente uma session/root por player.
- [ ] Spam de H não duplica ragdoll.
- [ ] Release/dismount retorna player sem root/seat ghost.
- [ ] Dummy com profile custom renderiza e despawna conforme regra.
- [ ] Item tag ragdoll-on-hit dispara uma vez após `/reload`.
- [ ] Dismember remove somente a parte alvo e não deixa sublevel órfão.
- [ ] Equipment snapshot reproduz equipamento sem duplicar item funcional.
- [ ] Disconnect/reconnect enquanto ragdolled não deixa ghost state.
- [ ] Death/respawn funciona com Ragdolls Patch 1.9.
- [ ] Sable x CPM mantém modelo correto para dois jogadores distintos.
- [ ] Ragdoll Reactions não causa double-launch com outros triggers.
- [ ] Stress de spawn/remove de dummies libera bodies/chunk tracking.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 23. Evidências e limites
- Modlist física canônica de 10/09/2026: JAR 0.7.5, mixins e integrations presentes.
- Repositório oficial `main`: `mod_version=0.7.5`, Minecraft 1.21.1/Java 21 e API pública correspondente.
- `RagdollAPI.java` atual: launch/session/playerless/dismember/equipment/corpse/grab/mob API.
- CurseForge oficial do projeto: features públicas do sistema; listagem de arquivos ainda parada em 0.7.2.
- Sable Ragdolls Patch 1.9: requisito explícito de Player Ragdoll 0.7.5, corroborando a versão física.
- **Limite:** não há changelog público do arquivo 0.7.5 no CurseForge; deltas exclusivos entre 0.7.2 e 0.7.5 não foram inventados.
