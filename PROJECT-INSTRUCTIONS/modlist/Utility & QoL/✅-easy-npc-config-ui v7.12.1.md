# Easy NPC: Config UI

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar`
- **Versão 1.21.1:** `7.12.1`
- **Categoria:** QoL; Visual
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc
- **Função:** Módulo gráfico de configuração do Easy NPC: screens/controles para editar NPCs e networking do fluxo de configuração, mantendo Core/server como authority do NPC state.
- **Dependências:** Easy NPC Core + NeoForge 1.21.1. Fisicamente, Config UI e Bundle estão em `7.12.1`; o Core usa `easy_npc-neoforge-1.21.1-7.12.1.jar`, mas sua coluna de metadata runtime permanece vazia e não é inventada.
- **Compatibilidade/Riscos:** UI/API/network drift, state stale, double-submit, permission check apenas client-side, screen client-only em dedicated server, preset identity/import/export/restore e edição concorrente. Core/Bundle/Config UI devem permanecer alinhados.
- **Sobreposição:** Comandos/config wand/UI são superfícies administrativas do mesmo Core; não há segunda cópia autoritativa de configuração.
- **Observações:** Config UI 7.12.1 é o runtime físico. O salto 7.11.0→7.12.1 inclui mudanças relevantes no preset browser, import/export/restore, administração e diversas superfícies da família Easy NPC.
- **Procedência:** modlist física de 16/09/2026 + distribuição/changelog oficial Easy NPC 7.12.1. Nenhum teste de UI/networking/persistência foi executado.
- **Histórico da decisão:**
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — Config UI/Bundle físicos atualizados de 7.11.0 para 7.12.1; Core filename também 7.12.1, sem metadata runtime publicada na modlist.
- **Data da última decisão:** 2026-09-06

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
A modlist confirma Config UI/Bundle 7.12.1 e Core filename 7.12.1 com metadata runtime vazia. A documentação oficial mantém Config UI como módulo de configuração/networking; Bundle continua composição modular. O dossiê migrado do Notion foi preservado em escopo, authority, networking, concorrência, lifecycle e riscos, agora reconciliado aos deltas 7.12.x.

> **Boundary canônico:** Config UI captura e transporta **edições**. Core/server continua authority do NPC e do state persistente.

## 11. Reauditoria física — 16/09/2026
Runtime Config UI atualizado para 7.12.1. Nenhum teste de Save/Cancel, preset, import/export/restore, concorrência ou dedicated server foi executado nesta passagem.