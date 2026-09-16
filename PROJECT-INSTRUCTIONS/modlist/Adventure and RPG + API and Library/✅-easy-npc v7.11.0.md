# Easy NPC

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack:** Integrado ao Github
- **Autoridade física:** `easy_npc-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc`, campo de versão runtime vazio; `7.12.1` vem do filename/build/publicação, não da metadata runtime
- **Auditoria de migração Notion → GitHub:** 2026-09-16

## Propriedades do registro

- **Mod:** Easy NPC
- **Arquivo JAR:** `easy_npc-neoforge-1.21.1-7.12.1.jar`
- **Versão 1.21.1:** metadata runtime não declarada/preenchida; filename/build/publicação identificam `7.12.1`
- **Categoria:** RPG; QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all
- **Função:** Core server-authoritative do Easy NPC para criar, persistir e controlar NPCs customizados, diálogos, ações, trading, aparência/skins, comportamento e integrações do ecossistema.
- **Dependências:** NeoForge 1.21.1. Easy NPC Config UI 7.12.1 e Easy NPC Bundle 7.12.1 estão fisicamente presentes. O filename/publicação identifica Core 7.12.1, mas a coluna `mod version` da modlist física permanece vazia. Epic Fight 21.17.3.1 está presente e é uma integração suportada pela linha Easy NPC.
- **Compatibilidade/Riscos:** NPC persistente é state crítico: riscos de duplicate restore, stale index/file state, backup stutter, owner-login restore, follow/look target após logout, action/trade double execution, custom model/pose interference e UI/client divergindo do servidor. A linha 7.11.0 corrigiu várias dessas superfícies; 7.12.1 acrescenta correções importantes de presets/restore/spawn e browser de presets.
- **Sobreposição:** Pode sobrepor funções narrativas/trading de outros sistemas de NPC/quest, mas Easy NPC é o provider direto dos NPCs criados nele. Epic Fight integra combate/animação; Config UI edita o state; nenhum deles deve duplicar ownership do NPC.
- **Observações:** Fail-closed preservado: `7.12.1` vem do filename/build/publicação do artefato instalado, **não** da metadata runtime, que continua vazia. A 7.12.1 corrige restore/import/export e browser de presets, leaks/rate-limit/IDs instáveis em spawn, sorting/filter/layout e fluxos administrativos; a integração opcional com Easy Model Entities nessa linha exige EME 2.4.0+. Core/Bundle/Config UI estão fisicamente alinhados em 7.12.1.
- **Procedência:** modlist.txt física atual reconferida em 16/09/2026 confirma `easy_npc-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc`, campo de versão runtime vazio e SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc`; também confirma Bundle e Config UI 7.12.1. CurseForge oficial confirma a linha 7.12.1 para NeoForge 1.21.1.
- **Histórico da decisão:** sem histórico adicional registrado.
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — build física promovida de 7.11.0 para `easy_npc-neoforge-1.21.1-7.12.1.jar`; metadata runtime permanece ausente e não foi inferida. Deltas 7.12.1 incorporados sem promover testes runtime não executados.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> **Autoridade física:** `easy_npc-neoforge-1.21.1-7.12.1.jar` · mod id `easy_npc` · NeoForge 1.21.1. **A coluna `mod version` da modlist física está vazia.** O identificador `7.12.1` vem do filename/build e da publicação oficial correspondente; não é tratado como metadata runtime.

## 1. Papel no modpack
Easy NPC Core é o provider central de NPCs customizados do projeto. Ele mantém a entidade/NPC, persistência, diálogos, ações, trading, aparência, comportamento e APIs correspondentes. O Config UI é apenas a superfície de edição; o Bundle é distribuição/dependência.

## 2. Authority / ownership
- **Easy NPC Core:** identidade e state do NPC, persistência, comportamento, dialogs/actions/trades e integrações próprias.
- **Servidor:** autoridade final sobre mutações, posição, inventário/trade, ações e save.
- **Config UI:** edição/apresentação/configuração; não deve manter cópia autoritativa independente.
- **Epic Fight:** combate/animação quando a integração correspondente atua.

NPCs criados no Easy NPC não devem ser recriados por scripts externos como uma segunda entidade paralela para representar o mesmo personagem.

## 3. Versionamento fail-closed
A modlist física confirma o JAR `easy_npc-neoforge-1.21.1-7.12.1.jar` e o mod id `easy_npc`, mas o campo de versão runtime continua vazio.

A publicação oficial e o filename identificam o artefato instalado como 7.12.1 para NeoForge 1.21.1. Portanto a ficha preserva a distinção: **filename/build/release = 7.12.1; metadata runtime = não declarada**.

A 7.12.1 está agora fisicamente instalada. Seu changelog corrige restore/import/export e o browser de presets; evita reload a cada tecla e retenção indevida de NPCs/seleção; corrige sorting/filter/layout, import de preset sem posição, leaks de spawn rate limit, IDs de preset instáveis e stacking de NPCs em Spawn New; Restore passa a recusar a operação quando não permitida em vez de gerar duplicata. Também adiciona melhorias de sorting, match count, reload/auto-close, exibição de NPC ID e tooltips. Na linha 7.12.1, a integração opcional com Easy Model Entities exige **EME 2.4.0+**.

## 4. Criação e edição de NPCs
O core suporta criação e edição de NPCs customizados com propriedades de aparência, comportamento e interação. O fluxo normal usa comandos e/ou Config UI/config wand quando o módulo visual está instalado.

Toda alteração persistente precisa terminar em um único state server-side; fechar/reabrir a tela não pode duplicar NPCs ou reexecutar ações.

## 5. Diálogos e interações
O projeto suporta diálogos e interações configuráveis. Diálogo é uma camada de apresentação/flow; qualquer action disparada pelo diálogo precisa ser validada no servidor e ser idempotente quando o usuário clica duas vezes, reloga ou sofre latency.

Quests externas podem consumir o resultado, mas não devem presumir que mostrar uma linha de diálogo significa que uma action server-side foi concluída.

## 6. Actions
Easy NPC permite associar ações a interações/eventos do NPC. Como essas ações podem alterar gameplay, a regra é execução exatamente uma vez no servidor com validação de contexto/permissão.

Riscos típicos: ação disparada em client + server, action repetida ao reabrir diálogo, ou state parcialmente salvo após unload.

## 7. Trading
Trading é um subsystem próprio relevante do core. Offers, conditions e settlement precisam permanecer server-authoritative; item/currency não pode ser consumido/entregue duas vezes em concorrência multiplayer.

Ao integrar economia/quests, consumir o resultado do trade em vez de implementar outra transação sobre a mesma GUI.

## 8. Skins e aparência
A linha Easy NPC suporta skins por nome de jogador/URL e outras opções de aparência. Recurso visual remoto precisa ter fallback controlado; falha de textura não deve apagar o NPC nem alterar seu state gameplay.

Model/skin providers externos podem tocar a mesma render surface e precisam de precedence clara.

## 9. Humanoid NPCs e custom models
A release 7.11.0, baseline imediatamente anterior, contém ajuste para humanoid NPCs ignorarem custom player models/animations/poses em rotas onde esses transforms causavam interferência. Isso é especialmente relevante porque o pack contém Customizable Player Models, Entity Model Features/Entity Texture Features e Easy Model Entities.

Não interpretar esse ajuste como incompatibilidade geral; testar o tipo de NPC/model concreto. A promoção para 7.12.1 não elimina esse regression gate.

## 10. Persistência e índice de NPCs
NPCs são conteúdo persistente do mundo. A linha 7.11.0 registra correções para stale NPC data, sincronização entre índice e arquivos, writes redundantes, warnings de unload e cenários em que NPCs podiam ser restaurados/duplicados incorretamente. A 7.12.1 reforça essa superfície ao corrigir leaks/IDs instáveis e fluxos de spawn/restore/presets.

A integridade do índice/state é parte da autoridade do core. Não editar arquivos manualmente em produção sem backup e formato documentado.

## 11. Backup / restore
A linha recente mantém sistema de backup de NPCs e a 7.11.0 corrigiu stutter de backups periódicos em mundos grandes, além de preservar timestamps de NPCs removidos e corrigir caminhos de restore que podiam ressuscitar/duplicar NPCs.

Na 7.12.1, Restore passa a falhar de forma explícita quando a restauração não é permitida, em vez de seguir para um caminho que poderia gerar uma entidade duplicada. Upgrade deve sempre ser validado em cópia do mundo antes de promover o save principal.

## 12. Follow/look target e logout
A 7.11.0 corrigiu um caso de server freeze quando NPC estava seguindo/olhando para um player que desconectava. Isso continua tornando logout/reconnect durante follow/look um regression gate obrigatório.

Target references precisam ser invalidadas quando a entidade/player deixa o mundo.

## 13. Presets
A linha 7.11.0 melhorou o browser de presets para incluir presets custom/world em SNBT. Preset é template de configuração, não um NPC runtime até ser instanciado.

A 7.12.1 amplia esse subsystem: corrige import/export, evita reload do browser a cada tecla e retenção de NPCs/seleção, corrige sorting/filter/layout e import sem posição, além de expor sorting, match count, reload/auto-close, NPC ID e tooltips. Aplicar preset a NPC existente deve ter semantics claras de merge/replace e nunca gerar uma segunda entidade por acidente.

## 14. Chat bubbles
A release 7.11.0 inclui correções de chat bubbles com Iris/shaders, formatting/macros/traduções e opção `minLightLevel` para legibilidade. Esses são aspectos de apresentação; a mensagem/dialog state continua associado ao NPC/core.

Em servidor dedicado, renderer de chat bubble não deve ser requerido para processar interação.

## 15. Epic Fight
O Easy NPC possui suporte integrado ao Epic Fight na linha moderna. O pack instala **Epic Fight 21.17.3.1** e diversos addons de compatibilidade.

A autoridade precisa ser dividida: Easy NPC mantém NPC/AI/action state; Epic Fight controla seu framework de combate/animação quando aplicado. Não executar dano/animation event em duplicidade nas duas camadas.

## 16. Cobblemon mixin — limite de presença
O JAR físico declara `easy_npc.cobblemon.mixins.json`, demonstrando uma superfície opcional de integração. **Cobblemon não foi confirmado na modlist física atual**, portanto essa compatibilidade não é tratada como ativa no pack.

Essa é uma aplicação direta da regra fail-closed: mixin opcional no JAR não prova provider instalado.

## 17. Config UI e Bundle
O pack contém fisicamente:
- `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` — metadata `7.12.1`;
- `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` — metadata `7.12.1`.

O Bundle organiza dependências/distribuição; o Config UI fornece ferramentas gráficas/networking de configuração. Nenhum dos dois deve ser modelado como um segundo provider de NPC state. O fato de esses dois módulos declararem 7.12.1 não autoriza preencher retroativamente a metadata runtime vazia do Core.

## 18. Client / Server
- NPC identity/state/position/actions/trades: server-authoritative;
- dialogs e screens: client-facing com commit validado no servidor;
- skins/models/chat bubbles: apresentação client-side;
- networking: deve carregar intenção/state de forma side-correct;
- dedicated server não pode depender de renderer/GUI para salvar ou tickar NPC.

## 19. Lifecycle
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
- preset apply/import/export/browser;
- backup/restore;
- Config UI open/save/cancel;
- update de versão.

## 20. Multiplayer / idempotência
Dois jogadores podem interagir com o mesmo NPC. Actions e trades precisam liquidar exatamente uma vez. O NPC não pode existir duplicado por restore/login/spawn race. State visual deve convergir após reconnect sem criar clone server-side.

## 21. Riscos
1. duplicate NPC restore;
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
14. tratar `7.12.1` como metadata runtime do Core quando a coluna continua vazia;
15. regressão em preset browser/restore/spawn após a promoção 7.11.0 → 7.12.1.

## 22. Matriz de testes
1. Dedicated server boot com Core + Config UI + Bundle 7.12.1.
2. Criar NPC, salvar, reiniciar e confirmar identidade única.
3. Chunk unload/reload repetido.
4. Follow/look player → player logout → reconnect.
5. Remover NPC → backup/restore controlado sem ressurreição indevida.
6. Dois players abrindo diálogo simultaneamente.
7. Action exactly-once sob double click/latency.
8. Trading concorrente sem dupe/loss.
9. Skin por player/URL com recurso válido e inválido.
10. Humanoid NPC com CPM/EMF/ETF/EME presentes.
11. Epic Fight combat/animation smoke-test.
12. Chat bubbles com shader do perfil atual.
13. Preset custom/world apply, import/export, filtro e reload.
14. Config UI save/cancel/reopen.
15. Backup periódico em mundo com muitos NPCs.
16. Spawn New/Restore sob permissões válidas e inválidas, confirmando ausência de stacking/duplicação.

**Esta catalogação não afirma que esses testes foram executados.**

## 23. Evidências
- modlist física atual: JAR Core 7.12.1, mod id, campo de versão runtime vazio, SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc`, Config UI/Bundle 7.12.1 e Epic Fight presentes;
- publicação oficial Easy NPC Core: escopo de NPC/dialog/trading/actions/skins/API e release 7.12.1 para NeoForge 1.21.1;
- release/changelog 7.11.0: estabilidade, backup/restore, stale state, follow/look logout, humanoid models, presets e chat bubbles;
- changelog 7.12.1: correções de restore/import/export/preset browser/spawn state e requisito EME 2.4.0+ para a integração opcional.

> **Boundary canônico:** Easy NPC Core é authority do **NPC persistente e de suas operações**. UI, renderers e combat frameworks são consumidores/adapters, não uma segunda fonte de verdade.