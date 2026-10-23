# Easy NPC: Config UI

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#241**: JAR `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc_config_ui`, runtime `7.12.1`, SHA-1 `84eb6b0500d9be672d6999df1ff57271a573c389`.

## Propriedades do registro

- **Mod:** Easy NPC: Config UI
- **Arquivo JAR:** `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar`
- **Versão 1.21.1:** `7.12.1`
- **Categoria:** QoL, Visual
- **Função:** Módulo gráfico de configuração do Easy NPC que fornece screens/controles para editar NPCs e a camada de networking necessária ao fluxo de configuração, mantendo o Core como authority do NPC state.
- **Dependências:** Easy NPC Core; NeoForge 1.21.1. Runtime físico: Config UI 7.12.1. Bundle 7.12.1 também está instalado e declara a composição Core + Config UI.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos de UI/API/network drift se Config UI e Core estiverem desalinhados, edição de state stale, double-submit sob latency, permissões insuficientes, screen client-only carregada no servidor e assumir que fechar/salvar UI já equivale a commit sem confirmação server-side. A upstream 7.13.0 altera o network protocol e exige cliente/servidor na mesma versão; 7.14.0 corrige sync tardio de pose/settings/trading/sound. Portanto a linha nova não deve ser promovida isoladamente sobre Core/Bundle 7.12.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-config-ui
- **Procedência:** modlist física atual confirma `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc_config_ui` e runtime `7.12.1`; Core filename e Bundle físicos também estão em 7.12.1. Changelogs oficiais 7.12.0/7.12.1 sustentam os deltas instalados; CurseForge oficial do Config UI foi revalidado em 03/10/2026 e publica 7.13.0 e 7.14.0 para NeoForge 1.21.1 como atualizações posteriores não instaladas.
- **Observações:** Config UI 7.12.1 permanece o runtime físico. O salto instalado 7.11.0→7.12.1 cobre preset browser/import/export/restore/administração. As releases upstream 7.13.0/7.14.0 acrescentam correções de dialog buttons, skin error handling, preset search, protocolo de rede e sincronização imediata/tardia de settings; não estão instaladas.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico permanece 7.12.1. A sequência posterior 1.21.1/NeoForge é 7.13.0 → 7.14.0; os deltas foram incorporados sem alterar versão instalada nem filename.
- **Decisão:** Dependência
- **Sobreposição:** Sobreposição apenas de superfície administrativa com comandos/config wand: todos editam o mesmo Core. Não deve haver duas cópias de configuração autoritativa.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` · mod id `easy_npc_config_ui` · versão `7.12.1` · NeoForge 1.21.1.
## 1. Papel e ownership
Easy NPC: Config UI é a superfície gráfica de edição. Não possui NPC state paralelo:
- **Core:** entidade, persistência, diálogos, actions, trades e comportamento;
- **Config UI:** formulário/screen e transporte da intenção de edição;
- **servidor:** valida contexto/permissão e aplica state final.
Um valor mostrado na tela pode ser snapshot; só é state final depois do commit server-side.
## 2. Relação Core / Bundle / Config UI
O Bundle organiza Core + Config UI como módulos separados. Na modlist de 16/09/2026:
- `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` → runtime 7.12.1;
- `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` → runtime 7.12.1;
- `easy_npc-neoforge-1.21.1-7.12.1.jar` → filename 7.12.1, coluna de mod version vazia.
O alinhamento de filenames/distribuição não autoriza preencher metadata ausente do Core.
## 3. Superfícies de edição
A UI expõe capacidades do Core, incluindo aparência, diálogos, actions, trading e comportamento conforme feature/tipo. IDs, constraints e schema exatos devem vir da build quando uma automação/preset escrever dados diretamente; não são inferidos.
## 4. Networking e Save/Cancel
Fluxo canônico: cliente envia intenção → servidor valida → Core aplica → state final sincroniza. Não duplicar mutação em handler client e server. Save, Cancel e Close precisam ser distintos; `Cancel` não deve persistir alteração parcial. Reopen deve refletir state do servidor. Double-click/latency não pode duplicar action/trade/preset.
## 5. Permissões, sides e concorrência
Autorização deve ser server-side. Ocultar botão no cliente não basta. Screens/widgets/layout são client-side; dedicated server não pode depender de inicialização gráfica. Dois administradores editando o mesmo NPC criam risco de stale write/last-write overwrite; comportamento real requer teste.
## 6. Atualização instalada — 7.12.1
O changelog oficial da família Easy NPC para 7.12.1 registra correções e melhorias diretamente relevantes à UI administrativa, entre elas:
- correções de visibilidade/reason no restore;
- mensagens de import/export;
- preset browser: reload por keystroke, leaks, seleção, sorting/filter/layout em janelas pequenas;
- comportamento de posição em import data;
- fixes de spawn limit, IDs de preset instáveis e stacking de Spawn New;
- correções de Restore refusal;
- sorting, match count, reload, auto-close, NPC ID e tooltips;
- batch imports por padrão com limites/confirmação/posição clicável.
O salto desde 7.11.0 também atravessa 7.12.0, cuja família incluiu mudanças amplas como administração/owner, Set Home, identidade/update de presets, Sound config/UI, Block Vehicles, client settings, NPC states, flying navigation, time/message actions e ajustes de integração. Como o changelog é da família Easy NPC, cada efeito deve ser atribuído ao Core/Config UI somente quando a superfície concreta exigir; a ficha não transforma todas essas mudanças em ownership exclusivo deste módulo.
## 7. Lifecycle
Validar abrir/fechar, save/cancel, NPC unload/removal durante edição, perda de permissão/logout, reconnect, restart, dimension change, Core update/reload e dois players editando simultaneamente.
## 8. Riscos
1. Core/Config UI/Bundle desalinhados;
2. packet/API drift;
3. double-submit;
4. state stale;
5. Cancel persistindo parcial;
6. permission client-only;
7. NPC unload durante edição;
8. client screen no dedicated server;
9. GUI exibindo valor não confirmado;
10. Config UI tratada como provider de NPC;
11. preset/import/export/restore regressions no salto 7.12.x.
## 9. Matriz de testes
- [ ] Dedicated server boot com Core + Config UI + Bundle físicos atuais.
- [ ] Abrir UI e editar NPC simples.
- [ ] Save → fechar → reabrir → persistência.
- [ ] Cancel → reabrir → nenhuma alteração.
- [ ] Double-click/latency no Save.
- [ ] Dois players editando o mesmo NPC.
- [ ] NPC unload/removal durante screen.
- [ ] Logout/reconnect e restart após alteração.
- [ ] Permissões autorizado/não autorizado.
- [ ] Preset browser sorting/filter/reload.
- [ ] Import/export/restore e batch import com 7.12.1.
**Esta reauditoria não afirma que esses testes foram executados.**
## 10. Evidências e limite
A modlist física atual de 21/09/2026 confirma Config UI/Bundle 7.12.1 e Core filename 7.12.1 com metadata runtime vazia. A documentação oficial mantém Config UI como módulo de configuração/networking; Bundle continua composição modular. O dossiê migrado do Notion foi preservado em escopo, authority, networking, concorrência, lifecycle e riscos, agora reconciliado aos deltas 7.12.x.
> **Boundary canônico:** Config UI captura e transporta **edições**. Core/server continua authority do NPC e do state persistente.
## 11. Reauditoria física — 16/09/2026
Runtime Config UI atualizado para 7.12.1. Nenhum teste de Save/Cancel, preset, import/export/restore, concorrência ou dedicated server foi executado nesta passagem.

## 12. Atualizações upstream 7.13.0 → 7.14.0 — não instaladas

A autoridade física continua em **Easy NPC: Config UI 7.12.1**. O CurseForge oficial publica **7.13.0** e depois **7.14.0** para NeoForge 1.21.1.

Deltas oficiais relevantes da família 7.13.0:
- corrige color/formatting tags em nomes de botões de diálogo que apareciam como texto literal;
- corrige preview do editor que exibia nomes em lowercase como raw translation key;
- corrige o item **Move EasyNPC** para owners fora do creative;
- corrige `/easy_npc owner set` reportando sucesso quando o owner não podia ser alterado;
- corrige crash nas telas de URL/player skin quando ocorre erro ao baixar skin;
- corrige busca do preset browser que podia retornar nenhum resultado depois de rolar a lista;
- adiciona `@initiator`, `@npc` e `@score()` em nomes de botões de diálogo;
- **altera a versão do protocolo de rede**, e o changelog exige cliente e servidor executando a mesma versão do mod.

### Impacto para o pack

A mudança de protocolo impede tratar 7.13.0/7.14.0 como update isolado do módulo gráfico. O pack físico mantém Core/Config UI/Bundle na linha **7.12.1**; qualquer promoção para 7.14.0 precisa reconciliar a família Easy NPC em conjunto e validar handshake/networking antes de substituir os JARs.

### 7.14.0 — sync de settings/UI

A família 7.14.0 corrige state que não chegava corretamente a observers/UI:
- custom poses e attribute/trading settings passam a aparecer para jogadores que começam a observar o NPC depois;
- **max uses, XP e trading type** alterados passam a refletir imediatamente nas trading screens, sem reload;
- mudanças por `/easy_npc sound set` passam a chegar aos players sem reload do NPC;
- novos GameTests cobrem poses/settings após respawn e para players novos.

Para Config UI, o ponto operacional é que a tela deve refletir state autoritativo atualizado e não depender de fechar/reabrir/reload para mostrar trading settings.

### Gate de promoção 7.12.1 → 7.14.0
- [ ] Core, Config UI e Bundle aplicáveis estão alinhados em 7.14.0; não misturar protocolo 7.12.1/7.13.x/7.14.0.
- [ ] Dedicated server + client realizam handshake e abrem a Config UI sem disconnect.
- [ ] Save/Cancel continua exactly-once sob latency.
- [ ] Dialog button formatting e tokens `@initiator`/`@npc`/`@score()` resolvem corretamente.
- [ ] URL/player skin download failure não derruba o cliente.
- [ ] Preset search continua correto após scroll/filter/reload.
- [ ] Move EasyNPC respeita ownership fora do creative.
- [ ] Owner command não reporta sucesso falso.
- [ ] Relog/restart preserva NPC state e não converte preview client-side em state autoritativo.

Fonte upstream: CurseForge oficial Easy NPC: Config UI, releases 7.13.0 e 7.14.0 para Minecraft 1.21.1/NeoForge. Nenhum teste acima foi executado nesta atualização documental.

## Atualização de publicação e gates — 13/10/2026

**Mod:** Easy NPC: Config UI. **Última release NeoForge 1.21.1 identificada:** `7.15.0`. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-config-ui/files/all.

Novo artefato NeoForge **`easy_npc_config_ui-neoforge-1.21.1-7.15.0.jar`** publicado em 12/10/2026. UI de configuração complementa Easy NPC bundle e Core, sem ownership de NPC AI/state. **BLOQUEIO de promoção coordenada:** Easy NPC Core 7.15.0 exige Easy Model Entities 2.5.0+ e versão igual no cliente/servidor; última build EME 1.21.1 verificada é 2.4.0. Não promover Config UI sozinha sem validar compat de todas as versões. Testar ID/dialog labels, lista de seleções, botões de NPC home, config de sons em resource packs, permissão/ownership server, reload, screen widgets e presets.

**Fonte física:** `modlist(1).txt` continua sendo o último snapshot acessível. Não se verificou instalação nova nem JAR/metadata/hash atual; manter versão histórica no filename `✅-... vX.md` e na linha física do índice até novo dump e smoke tests. **Estado:** atualização documental concluída na branch; promoção física **PENDENTE/BLOQUEADA** conforme os gates.

## Revisão de conteúdo oficial 7.15.0 e cronologia — 13/10/2026

**Separação de autoria:** a release do **Core 7.15.0** adiciona NPC inventory com item pickup/death drops, as ações `Jump`, `Assist Owner`, `Follow Nearest Player`, `Sit on Shoulder`, boss bar, `Hold` animation, preset Bank Teller e extensões de API. O **Config UI** é a superfície gráfica de edição/networking; as notas do Core **não provam que existam widgets novos específicos da Config UI**. Verificar como cada campo/ação é editado e sincronizado antes de documentar uma interface concreta.

**Versões e cronologia:** `Set Home Here` e `Reset Home` são funcionalidades da **7.12.0**, portanto já anteriores à instalação histórica 7.12.1; macros `@initiator`, `@npc`, `@score()` pertencem à **7.13.0**; synch de trading/pose/sound para late viewers à **7.14.0**.

**Gate:** Core/Bundle/Config UI alinhados 7.15.0 (cliente/servidor), EME 2.5.0+ ainda **não identificada para NeoForge Minecraft 1.21.1**; a EME 2.5.0 observada destina-se ao 26.1.2. Até validar requisito efetivo do Core, **não promover**. Testar Save/Cancel exactly-once, UI de inventário/ação se existir, options de owner/permissão, import/export, edição concorrente, invalid fields, desconexão e resource-pack sounds. Não reivindicar testes.

**Fontes oficiais:** [Easy NPC CHANGELOG.md — branch 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md) (seções 7.12.0 a 7.15.0); [Core 7.15.0 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all?version=1.21.1); [listagem Easy Model Entities](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all); [EME 2.5.0 NeoForge — Minecraft 26.1.2](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/9152252).

**Autoridade física:** sem dump novo de `mods/`, o JAR e a versão do último snapshot permanecem. Prefixo ✅ e versão histórica no filename são preservados.

## Auditoria declarativa NeoForge 7.15.0 — 13/10/2026

### Manifesto NeoForge Config UI 7.15.0

O `config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml` declara:
- `easy_npc` como dependência indicada por `mandatory = true` (sem `type` explícito nesse bloco); `versionRange = "[${file.jarVersion},${file.nextMajorVersion})"`; `ordering = "AFTER"`; `side = "BOTH"`. Para o build 7.15.0, a faixa substituída corresponde à linha major 7; isso **não é equivalência de versões idênticas**, mas o protocolo de rede do Core e o changelog ainda justificam **alinhamento exato dos três artefatos** para migração/QA.
- `easy_model_entities` com **`type = "optional"`**, `versionRange = "[2.5.0,3.0.0)"`, `ordering = "AFTER"`, `side = "BOTH"`.

Conforme documentação NeoForge 1.21.1, **opcional não significa ignorar a versão de um mod presente**. Com a EME 2.4.0 fisicamente registrada, a candidata Config UI 7.15.0 permanece **BLOQUEADA em conjunto com Core/Bundle**, sem recomendação de remover EME. Esta evidência especifica a natureza do gate, antes descrita como ambígua. Não confundir um UI de configuração com autoridade de inventário/AI de NPC.

**Código-fonte lido (branch oficial `1.21.1`, 13/10/2026):** [manifesto NeoForge Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [manifesto NeoForge Config UI](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [manifesto NeoForge Bundle](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [build.gradle do Bundle NeoForge](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/build.gradle), [gradle.properties do Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/gradle.properties), [documentação oficial NeoForge 1.21.1 — dependências opcionais](https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/), [CurseForge EME 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all?version=1.21.1), [Modrinth EME 2.4.0 NeoForge 1.21.1](https://modrinth.com/mod/easy-model-entities/version/KIk2E6sP). **Limite:** estes arquivos são source da branch 1.21.1; o manifesto do **binário publicado 7.15.0** ainda não foi extraído e comparado byte a byte, nem houve boot do perfil.


## Avaliação de fallback 7.14.0 — Config UI (13/10/2026)

O commit oficial [`2c7fb934c6626224220227e6db5150edd891cdd3`](https://github.com/MarkusBordihn/BOs-Easy-NPC/commit/2c7fb934c6626224220227e6db5150edd891cdd3) contém **Core 7.14.0** para Minecraft 1.21.1 e [manifesto NeoForge Config UI](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml) com `easy_model_entities` **opcional `[2.4.0,3.0.0)`**, `ordering = "AFTER"`, `side = "BOTH"`; o Core no mesmo commit possui a mesma faixa. Isso torna o candidato **7.14.0 declarativamente compatível com EME build 2.4.0** da última fotografia física. A UI também exige o Core e declara seu range vinculado à versão substituída pelo build; manter os três componentes alinhados em **7.14.0** para evitar quebra de protocolo. A 7.15.0 continua **BLOQUEADA** com a EME 2.4.0 presente.

**Limitações:** verificar os manifests extraídos dos JARs distribuídos, a versão de EME resolvida pelo NeoForge (o campo histórico no inventário está vazio), as dependências e a composição do Bundle. Revalidar telas de configuração, skin, custom pose, trading e novos espectadores após restart/relogin em ambiente descartável. **Não houve download/extração de JAR, teste funcional ou mudança física**. O [source EME 2.5.0 para 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-Model-Entities/blob/7305984c57845b97d0190bfc6d4b3e0363ca5aaf/gradle.properties) não equivale à publicação do binário NeoForge 1.21.1.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Easy NPC: Config UI
- **Último candidato 1.21.1 / NeoForge localizado:** **7.16.0 (Release, 17/10)**
- **Estado:** **CANDIDATO COORDENADO**
- **Gate de promoção:** Promover junto de Core/Bundle 7.16.0 e EME compatível; validar protocol/UI permissions, double-submit, late-viewer sync, models, save/restart e client/server parity.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-config-ui


## Revisão incremental por release — 22/10/2026 (Config UI 7.12.1 físico → 7.17.0 candidato)

**Nova publicação oficial:** `easy_npc_config_ui-neoforge-1.21.1-7.17.0.jar`, 21/10/2026. Config UI edita/apresenta o estado do **Core**; os itens abaixo são efeitos que precisam de regressão na interface e **não funcionalidades exclusivas deste JAR**.

| Release da família | Efeito no editor/UI que precisa de verificação |
|---|---|
| `7.12.2` | NPCs escalonados/Fairy: hitbox, eye level, seleção do editor e validação de valores inválidos; protocolo mudou. A presença como arquivo Config UI separado nessa versão não foi confirmada. |
| `7.13.0` | Edição de diálogo/macro `@initiator`, `@npc`, `@score()`, erro de skins, browser de presets e validação owner/permission. |
| `7.14.0` | Replicação de custom poses, attributes, trading e sons a quem observa depois; alteração deve aparecer sem reload. |
| `7.15.0` | Editor passa a interagir com novos atributos de inventory, `Sit on Shoulder`, boss bar, `Hold` e AI do Core; testar serialization/death drops; EME 2.5+ requisitada se instalada. |
| `7.16.0` | `Sit on Shoulder` muda da aba Abilities para Misc, ganha offset/distância/cooldown/combate; prioridade editável de objetivos, APIs de revive, regras de preset export 10/min e EME `[2.6.0,3.0.0)` no **source 7.16**. |
| `7.17.0` | Light Level padrão 7→0 afeta inclusive NPCs existentes com default; novos comandos de atributos e correções de gravação de equipment, nome, inventário, diálogos e objetivos. Testar valor persistente e UI pós-save/restart, comando `name clear`, mudança de modelo `root`, editor versus outro client/dimensão. |

**Bloqueio de promoção:** sem extração do `neoforge.mods.toml` do artefato UI 7.17.0, não afirmar ABI ou faixa de dependências final. Conferir Core/Bundle/UI 7.17.0 com EME 2.6.0 e loader; impedir dois mod IDs e testar permissions, input, late-join, edição simultânea, persistência, backups e client-server protocol. **Nenhuma nova versão física instalada/validada**; filename versionado 7.12.1 mantido.

**Fontes:** [CurseForge Config UI — 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc-config-ui/files/all?version=1.21.1); [changelog da família distribuído com Core 7.17.0, arquivo 9282416 (Fabric; não é o alvo binário NeoForge)](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/9282416/additional-files); [CHANGELOG upstream 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md).

## Fechamento de releases NeoForge Config UI 7.12.1 → 7.17.0 — 23/10/2026

**Baseline físico:** `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` / runtime `7.12.1`. **Último release** CurseForge NeoForge 1.21.1: `easy_npc_config_ui-neoforge-1.21.1-7.17.0.jar` de **21/10/2026**, projeto ID **1214728**. Fontes: https://www.curseforge.com/minecraft/mc-mods/easy-npc-config-ui/files/all ; https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md .

A sequência **distribuída para NeoForge 1.21.1** desde a baseline é `7.13.0 → 7.14.0 → 7.15.0 → 7.16.0 → 7.17.0`. O changelog **Core 7.12.2** está entre as datas de publicação, mas **não foi comprovado um `Config UI 7.12.2` autônomo**; não promover essa versão inexistente como release UI. A revisão detalhada dos efeitos na interface já está na seção "Revisão incremental por release — 22/10/2026"; a conciliação com o changelog upstream oficial do Core está resumida aqui.

| Família/versão | Alterações que repercutem na UI de configuração (nem todas exclusivas do artefato UI) |
|---|---|
| 7.13.0 | Novo protocolo de rede; clientes e servidor devem coincidir em versão. Corrigidos botões de diálogo (formatação), preview de editor com chave de tradução crua, owner Move EasyNPC, mensagens de ownership, skin/URL errors e busca de presets após scrolling. Macros `@initiator`, `@npc`, `@score()` em textos dos botões. |
| 7.14.0 | Replicação de custom poses, atributos, trade settings e alterações sonoras para observadores que começam a renderizar NPC posteriormente; ajustes sem exigir reload do NPC. |
| 7.15.0 | Novo inventário NPC, death drops/item pickup, Sit on Shoulder, boss bar, Hold animation, Bank Teller, condições Jump/Assist Owner/Follow Nearest Player; correções de NPCs duplicados/deletados, vazamento de memória e ownership. Requisito do conjunto: mesmo número de versão em client/server e Easy Model Entities 2.5.0+ opcional. |
| 7.16.0 | Sit on Shoulder movido para aba **Misc** e recebe offset, distância, cooldown e comportamento em combate. Prioridades de objetivos definidos por addons, ataque com Attack Speed, revive API/command, limites 10 exports por minuto; Easy Model Entities passa a exigir 2.6.0+. |
| 7.17.0 | Changelog publicado na família Core registra Light Level default 7→0 e correções de gravação de equipment/name/inventory/dialog/objectives; validar efeito no editor/atributos com save/restart. Não afirmar que lógica Core nova é implementada internamente pelo Config UI sem inspeção de classes. |

**Detalhes importantes de segurança e operação:** o protocolo 7.13.0 impede misturar clientes/servidor com famílias diferentes; a 7.15.0 altera persistência de inventário, death drops e backup (expiração 30 dias), demandando cópia de saves antes de atualizar. 7.16.0 exige auditoria de permissões/admin de presets e de shoulder AI, inclusive EME 2.6.0. A versão 7.17.0 deve migrar **Core + Config UI + Bundle** de maneira coordenada, nunca promover um único JAR e presumir compatibilidade de APIs. Controles de testes: edição simultânea por duas pessoas, permissões de dono/admin, validação/rollback de campos, reconexão tardia, presets, trading, nome/modelo, inventory/drop, resources, networking/handshake e save/restart.

**Precisão:** os changelogs acima são oficiais da **família Easy NPC / Core**, e sua relevância para Config UI foi documentada como integração/contrato; sem decompilar o JAR UI específico não se atribuem essas implementações ao módulo gráfico. Nenhum teste de runtime ou substituição de JAR foi executado. **Auditoria das versões publicadas do UI e pontos de integração registrada, com promoção física/QA separada.**

