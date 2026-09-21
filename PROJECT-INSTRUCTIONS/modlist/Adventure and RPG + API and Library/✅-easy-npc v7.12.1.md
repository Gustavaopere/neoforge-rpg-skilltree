# Easy NPC

## Propriedades do registro

- **Mod:** Easy NPC
- **Arquivo JAR:** easy_npc-neoforge-1.21.1-7.12.1.jar
- **Versão 1.21.1:** —
- **Categoria:** RPG; QoL
- **Função:** Core server-authoritative do Easy NPC para criar, persistir e controlar NPCs customizados, diálogos, ações, trading, aparência/skins, comportamento e integrações do ecossistema.
- **Dependências:** NeoForge 1.21.1. Easy NPC Config UI 7.11.0 e Easy NPC Bundle 7.11.0 estão fisicamente presentes. O filename/publicação identifica Core 7.11.0, mas a coluna `mod version` da modlist física está vazia. Epic Fight 21.17.3.1 está presente e é uma integração suportada pela linha Easy NPC.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** NPC persistente é state crítico: riscos de duplicate restore, stale index/file state, backup stutter, owner-login restore, follow/look target após logout, action/trade double execution, custom model/pose interference e UI/client divergindo do servidor. A linha 7.11.0 contém correções explícitas para várias dessas superfícies.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all
- **Procedência:** modlist física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `easy_npc-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc`, SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc` e campo runtime vazio; Bundle/Config UI `7.12.1` e EME `2.4.0` também estão presentes. Changelogs oficiais 7.12.0/7.12.1 sustentam os deltas documentados.
- **Observações:** Fail-closed preservado: `7.12.1` vem do filename/build/publicação do artefato instalado, não da metadata runtime. Core, Bundle e Config UI estão fisicamente alinhados em 7.12.1; Easy Model Entities está fisicamente em 2.4.0.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #238: artefato físico atualizado para `easy_npc-neoforge-1.21.1-7.12.1.jar`. A coluna `mod version` da modlist continua vazia; `7.12.1` é identidade do filename/build/publicação, não metadata runtime inferida.
- **Decisão:** Manter
- **Histórico da decisão:** —
- **Sobreposição:** Pode sobrepor funções narrativas/trading de outros sistemas de NPC/quest, mas Easy NPC é o provider direto dos NPCs criados nele. Epic Fight integra combate/animação; Config UI edita o state; nenhum deles deve duplicar ownership do NPC.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs
> **Autoridade física:** `easy_npc-neoforge-1.21.1-7.12.1.jar` · mod id `easy_npc` · NeoForge 1.21.1 · SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc`. **A coluna ****`mod version`**** da modlist física está vazia.** O identificador `7.12.1` vem do filename/build e da publicação oficial correspondente.
### 1. Papel no modpack
Easy NPC Core é o provider central de NPCs customizados do projeto. Ele mantém a entidade/NPC, persistência, diálogos, ações, trading, aparência, comportamento e APIs correspondentes. O Config UI é a superfície de edição; o Bundle é distribuição/dependência.
### 2. Authority / ownership
- **Easy NPC Core:** identidade e state do NPC, persistência, comportamento, dialogs/actions/trades, presets e integrações próprias.
- **Servidor:** authority final sobre mutações, posição, inventário/trade, ações e save.
- **Config UI:** edição/apresentação/configuração; não deve manter cópia autoritativa independente.
- **Epic Fight:** combate/animação quando a integração correspondente atua.
- **Easy Model Entities:** model/render integration quando configurada; não assume ownership do NPC persistente.
NPCs criados no Easy NPC não devem ser recriados por scripts externos como uma segunda entidade paralela para representar o mesmo personagem.
### 3. Versionamento fail-closed
A modlist física confirma o JAR `easy_npc-neoforge-1.21.1-7.12.1.jar` e o mod id `easy_npc`, mas o campo de versão runtime continua vazio.
A publicação oficial identifica o artefato como **7.12.1** para NeoForge 1.21.1. Portanto a ficha preserva a distinção obrigatória:
- **JAR/build/publicação:** 7.12.1;
- **metadata runtime na modlist:** não declarada;
- **Minecraft:** 1.21.1;
- **loader:** NeoForge.
Nenhuma ausência de metadata foi preenchida por inferência.
### 4. Criação e edição de NPCs
O core suporta criação e edição de NPCs customizados com propriedades de aparência, comportamento e interação. O fluxo normal usa comandos e/ou Config UI/config wand quando o módulo visual está instalado.
Toda alteração persistente precisa terminar em um único state server-side; fechar/reabrir a tela não pode duplicar NPCs ou reexecutar ações.
A 7.12.0 adicionou/ajustou superfícies importantes:
- botões **Set Home Here** e **Reset Home**;
- export de preset preservando **NPC ID e posição**, para que o import possa restaurar o mesmo NPC;
- opção **Block Vehicles** para impedir NPCs em boats, minecarts e chairs modded;
- profession selection em todas as skin configurations e uma skin built-in adicional por tipo;
- client settings para ocultar nametag atrás de paredes e `highlightEnabled` para outlines do wand;
- API access a movement, actions e presets e condição `Exists`.
### 5. Diálogos e interações
O projeto suporta diálogos e interações configuráveis. Diálogo é uma camada de apresentação/flow; qualquer action disparada pelo diálogo precisa ser validada no servidor e ser idempotente quando o usuário clica duas vezes, reloga ou sofre latency.
Quests externas podem consumir o resultado, mas não devem presumir que mostrar uma linha de diálogo significa que uma action server-side foi concluída.
A 7.12.0 alterou o API dialog call para reportar **unknown dialog label** em vez de sucesso incondicional, reduzindo falso positivo de integração.
### 6. Actions
Easy NPC permite associar ações a interações/eventos do NPC. Como essas ações podem alterar gameplay, a regra é execução exatamente uma vez no servidor com validação de contexto/permissão.
Riscos típicos: ação disparada em client + server, action repetida ao reabrir diálogo ou state parcialmente salvo após unload.
### 7. Trading
Trading é um subsystem próprio relevante do core. Offers, conditions e settlement precisam permanecer server-authoritative; item/currency não pode ser consumido/entregue duas vezes em concorrência multiplayer.
Ao integrar economia/quests, consumir o resultado do trade em vez de implementar outra transação sobre a mesma GUI.
A 7.12.0 passou a tocar o som **Trade completed** para trades concluídos; isso é feedback de apresentação, não uma segunda authority de settlement.
### 8. Skins e aparência
A linha Easy NPC suporta skins por nome de jogador/URL e outras opções de aparência. Recurso visual remoto precisa ter fallback controlado; falha de textura não deve apagar o NPC nem alterar seu state gameplay.
Model/skin providers externos podem tocar a mesma render surface e precisam de precedence clara.
A 7.12.1 adiciona sorting, match count, reload, auto-close, NPC ID e tooltips nas superfícies de presets/skins.
### 9. Humanoid NPCs e custom models
A 7.11.0 já continha ajuste para humanoid NPCs ignorarem custom player models/animations/poses em rotas onde esses transforms causavam interferência. Isso continua herdado pela 7.12.1.
A 7.12.0 alterou a dependência opcional de Easy Model Entities para **EME 2.4.0+**. O pack atual possui `easy_model_entities-neoforge-1.21.1-2.4.0.jar`; o campo runtime desse mod também aparece vazio na modlist, mas o filename/build satisfaz exatamente o piso publicado. Não interpretar isso como garantia de compatibilidade visual completa sem teste.
### 10. Persistência, índice e identidade de NPCs
NPCs são conteúdo persistente do mundo. A linha 7.11.0 já registrava correções para stale NPC data, sincronização entre índice e arquivos, writes redundantes, warnings de unload e cenários de restore/duplicação.
A 7.12.0 preserva NPC ID/position em presets exportados e corrige owner list/objectives perdidos em imports/spawn eggs; também corrige automatic-backup restore que podia devolver NPC sem owner.
A 7.12.1 corrige **unstable preset IDs**, leaks de rate limit no spawn e o fluxo **Spawn New** que podia empilhar NPCs no mesmo ponto.
A integridade do índice/state é parte da authority do core. Não editar arquivos manualmente em produção sem backup e formato documentado.
### 11. Backup / restore
A 7.11.0 já continha correções de stutter e caminhos de restore que podiam ressuscitar/duplicar NPCs.
Na linha instalada:
- 7.12.0 faz import de preset atualizar o NPC original na posição armazenada; criar cópia passa a exigir **Import as new NPC** ou preset item;
- 7.12.1 sempre mostra **Restore**, inclusive para datapack presets, com tooltip explicando a indisponibilidade quando aplicável;
- quando restore não é permitido, 7.12.1 **recusa** a operação em vez de spawnar uma cópia;
- mensagens de import/export foram corrigidas para não omitir/wrongly report replacements.
Upgrade continua devendo ser validado em cópia do mundo antes de promover o save principal.
### 12. Follow/look target e logout
A 7.11.0 corrigiu server freeze quando NPC seguia/olhava player que desconectava. Esse comportamento continua herdado pela 7.12.1 e torna logout/reconnect durante follow/look um regression gate obrigatório.
Target references precisam ser invalidadas quando a entidade/player deixa o mundo.
### 13. Presets
Preset é template de configuração, não um NPC runtime até ser instanciado.
A 7.12.0/7.12.1 altera materialmente o fluxo:
- ID e posição são preservados em export;
- import padrão atualiza o NPC original em vez de gerar clone;
- spawn/import preservam owner list/objectives;
- `Restore` é visível com reason tooltip;
- preset browser deixa de recarregar a cada keystroke/leakar NPCs/limpar seleção;
- sorting, filter order e clipping em janelas pequenas foram corrigidos;
- `/easy_npc preset import data` sem posição deixa de ser interpretado incorretamente como default preset;
- `matching <pattern>` permite batch imports com limites, confirmação e posição clicável;
- sorting, match count, reload, auto-close, NPC ID e tooltips foram adicionados.
Aplicar preset a NPC existente deve ter semantics claras de merge/replace e nunca gerar segunda entidade por acidente.
### 14. Chat bubbles e sons
A linha 7.11.0 inclui correções de chat bubbles com Iris/shaders, formatting/macros/traduções e `minLightLevel`.
A 7.12.0 adiciona **Sound configuration** para dialogs, speech, swimming, eating e fall damage, com seleção pesquisável, volume e pitch. Esses elementos são apresentação/feedback; a mensagem/dialog/action state continua associado ao core.
Em servidor dedicado, renderer de chat bubble e GUI sonora não devem ser necessários para processar interação.
### 15. Epic Fight
O Easy NPC possui suporte integrado ao Epic Fight na linha moderna. O pack instala **Epic Fight 21.17.3.1** e addons relacionados.
A authority precisa ser dividida: Easy NPC mantém NPC/AI/action state; Epic Fight controla seu framework de combate/animação quando aplicado. Não executar dano/animation event em duplicidade nas duas camadas.
### 16. Cobblemon mixin — limite de presença
O JAR físico declara `easy_npc.cobblemon.mixins.json`, demonstrando uma superfície opcional de integração. **Cobblemon não foi confirmado na modlist física atual**, portanto essa compatibilidade não é tratada como ativa no pack.
Mixin opcional no JAR não prova provider instalado.
### 17. Config UI e Bundle
O pack contém fisicamente:
- `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` / runtime `7.12.1`;
- `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` / runtime `7.12.1`.
Core/Bundle/Config UI estão, portanto, alinhados pela versão de build. O Bundle organiza dependências/distribuição; o Config UI fornece ferramentas gráficas/networking de configuração. Nenhum dos dois é um segundo provider de NPC state.
### 18. Client / Server
- NPC identity/state/position/actions/trades: server-authoritative;
- dialogs e screens: client-facing com commit validado no servidor;
- skins/models/chat bubbles/sounds: apresentação client-side;
- networking: deve carregar intenção/state de forma side-correct;
- dedicated server não pode depender de renderer/GUI para salvar ou tickar NPC.
### 19. Lifecycle
Validar:
- criação e remoção;
- save/restart;
- chunk unload/reload;
- player login/logout;
- owner login/restore;
- follow/look target disconnect;
- dimension change;
- death/respawn quando aplicável ao tipo;
- dialog/action/trade;
- preset apply/import/export/batch import;
- backup/restore;
- Config UI open/save/cancel;
- spawn rate limit;
- atualização de versão.
### 20. Multiplayer / idempotência
Dois jogadores podem interagir com o mesmo NPC. Actions e trades precisam liquidar exatamente uma vez. O NPC não pode existir duplicado por restore/login/import race. State visual deve convergir após reconnect sem criar clone server-side.
Wand outlines da linha 7.12.0 são restritos a holder/owner/admin; outros jogadores não devem ver outlines, e NPCs invisíveis devem permanecer ocultos para eles.
### 21. Riscos
1. duplicate NPC restore/import;
2. stale index/file state;
3. backup stutter em mundo grande;
4. owner-login restore mover/ressuscitar NPC indevidamente;
5. follow/look target stale causar freeze;
6. dialog action double execution;
7. trade double settlement;
8. custom model/pose transform duplicado;
9. chat bubble/shader regressions;
10. skin URL/resource failure;
11. Epic Fight dano/animação duplicados;
12. Config UI editar state stale;
13. optional integration classloading sem provider;
14. tratar `7.12.1` como metadata runtime quando a coluna física está vazia;
15. preset browser/spawn leaks ou unstable IDs regressarem;
16. restore/import gerar cópia quando deveria atualizar/recusar;
17. batch import exceder limites/position context.
### 22. Matriz de testes
1. \[ \] Dedicated server boot com Core + Config UI + Bundle 7.12.1.
2. \[ \] Criar NPC, salvar, reiniciar e confirmar identidade única.
3. \[ \] Chunk unload/reload repetido.
4. \[ \] Follow/look player → logout → reconnect.
5. \[ \] Remover NPC → backup/restore controlado sem ressurreição indevida.
6. \[ \] Export/import preserva ID/posição/owner conforme semântica da 7.12.x.
7. \[ \] Restore indisponível recusa sem spawnar cópia e mostra reason tooltip.
8. \[ \] Preset browser search/sort/filter não leaka NPCs nem limpa seleção indevidamente.
9. \[ \] Spawn New não empilha clones por rate-limit/ID instável.
10. \[ \] `matching <pattern>` respeita limites/confirmação/posição.
11. \[ \] Dois players abrindo diálogo simultaneamente.
12. \[ \] Action exactly-once sob double click/latency.
13. \[ \] Trading concorrente sem dupe/loss.
14. \[ \] Skin por player/URL com recurso válido e inválido.
15. \[ \] Humanoid NPC com CPM/EMF/ETF/EME presentes; EME 2.4.0 smoke-test.
16. \[ \] Epic Fight combat/animation smoke-test.
17. \[ \] Chat bubbles com shader do perfil atual.
18. \[ \] Config UI save/cancel/reopen.
19. \[ \] Backup periódico em mundo com muitos NPCs.
**Esta catalogação não afirma que esses testes foram executados.**
### 23. Evidências
- modlist física atual de 21/09/2026: Core 7.12.1 por filename/build, mod id, **campo de versão runtime vazio**, SHA-1; Config UI/Bundle 7.12.1; Easy Model Entities 2.4.0 por filename/build; Epic Fight presente;
- publicação oficial Easy NPC Core: escopo de NPC/dialog/trading/actions/skins/API e Release 7.12.1 para NeoForge 1.21.1;
- changelog 7.11.0: estabilidade, backup/restore, stale state, follow/look logout, humanoid models, presets e chat bubbles;
- changelog 7.12.0: ID/posição de preset, owner restore, import sem cópia, sound config, vehicle blocking, skin/client settings, API/conditions e piso EME 2.4.0+;
- changelog 7.12.1: restore/import/export/preset browser/spawn state, IDs/rate-limit, batch import e UX de presets/skins.
> **Boundary canônico:** Easy NPC Core é authority do **NPC persistente e de suas operações**. UI, renderers, model providers e combat frameworks são consumidores/adapters, não uma segunda fonte de verdade.
