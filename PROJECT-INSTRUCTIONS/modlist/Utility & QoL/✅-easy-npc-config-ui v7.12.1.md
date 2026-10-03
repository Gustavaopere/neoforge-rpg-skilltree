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
