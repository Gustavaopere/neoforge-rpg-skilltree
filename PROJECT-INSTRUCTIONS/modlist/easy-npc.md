# Easy NPC

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db812f8576f5016343b18c  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Easy NPC
- **Arquivo JAR:** `easy_npc-neoforge-1.21.1-7.11.0.jar`
- **Versão 1.21.1:** não declarada na metadata física; filename/publicação identificam `7.11.0`
- **Categoria:** RPG; QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all
- **Função:** Core server-authoritative do Easy NPC para criar, persistir e controlar NPCs customizados, diálogos, ações, trading, aparência/skins, comportamento e integrações do ecossistema.
- **Dependências:** NeoForge 1.21.1. Easy NPC Config UI 7.11.0 e Easy NPC Bundle 7.11.0 estão fisicamente presentes. O filename/publicação identifica Core 7.11.0, mas a coluna `mod version` da modlist física está vazia. Epic Fight 21.17.3.1 está presente e é uma integração suportada pela linha Easy NPC.
- **Compatibilidade/Riscos:** NPC persistente é state crítico: riscos de duplicate restore, stale index/file state, backup stutter, owner-login restore, follow/look target após logout, action/trade double execution, custom model/pose interference e UI/client divergindo do servidor. A linha 7.11.0 contém correções explícitas para várias dessas superfícies.
- **Sobreposição:** Pode sobrepor funções narrativas/trading de outros sistemas de NPC/quest, mas Easy NPC é o provider direto dos NPCs criados nele. Epic Fight integra combate/animação; Config UI edita o state; nenhum deles deve duplicar ownership do NPC.
- **Observações:** Fail-closed: `7.11.0` vem do filename/publicação oficial, não da coluna física de versão. Config UI/Bundle são módulos complementares, não providers paralelos. O JAR contém mixin Cobblemon, mas Cobblemon não foi confirmado na modlist física atual; não tratar essa integração como ativa.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `easy_npc-neoforge-1.21.1-7.11.0.jar`, mod id `easy_npc` e campo físico de versão vazio. Publicação oficial da linha 7.11.0 confirma a identificação da release, funcionalidades e changelog de estabilidade/persistência.
- **Histórico da decisão:** sem histórico adicional registrado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — filename/release 7.11.0 com metadata física de versão vazia; NPC state/persistência, dialogs/actions/trades, skins, Config UI, backups, Epic Fight, lifecycle/MP, riscos e testes catalogados.
- **Data da última decisão:** 2026-09-06.

## Dossiê operacional — padrão Alex's Mobs

> **Autoridade física:** `easy_npc-neoforge-1.21.1-7.11.0.jar` · mod id `easy_npc` · NeoForge 1.21.1. **A coluna `mod version` da modlist física está vazia.** O identificador `7.11.0` vem do filename e da publicação oficial correspondente.

## 1. Papel no modpack
Easy NPC Core é o provider central de NPCs customizados do projeto. Ele mantém a entidade/NPC, persistência, diálogos, ações, trading, aparência, comportamento e APIs correspondentes. O Config UI é apenas a superfície de edição; o Bundle é distribuição/dependência.

## 2. Authority / ownership
- **Easy NPC Core:** identidade e state do NPC, persistência, comportamento, dialogs/actions/trades e integrações próprias.
- **Servidor:** autoridade final sobre mutações, posição, inventário/trade, ações e save.
- **Config UI:** edição/apresentação/configuração; não deve manter cópia autoritativa independente.
- **Epic Fight:** combate/animação quando a integração correspondente atua.

NPCs criados no Easy NPC não devem ser recriados por scripts externos como uma segunda entidade paralela para representar o mesmo personagem.

## 3. Versionamento fail-closed
A modlist física confirma o JAR `easy_npc-neoforge-1.21.1-7.11.0.jar` e o mod id `easy_npc`, mas o campo de versão está vazio.

A publicação oficial identifica esse arquivo como 7.11.0 para NeoForge 1.21.1. Portanto a ficha preserva a distinção: filename/release = 7.11.0; metadata física de versão = não declarada.

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
A release 7.11.0 contém ajuste para humanoid NPCs ignorarem custom player models/animations/poses em rotas onde esses transforms causavam interferência. Isso é especialmente relevante porque o pack contém Customizable Player Models, Entity Model Features/Entity Texture Features e Easy Model Entities.

Não interpretar esse ajuste como incompatibilidade geral; testar o tipo de NPC/model concreto.

## 10. Persistência e índice de NPCs
NPCs são conteúdo persistente do mundo. A linha 7.11.0 registra correções para stale NPC data, sincronização entre índice e arquivos, writes redundantes, warnings de unload e cenários em que NPCs podiam ser restaurados/duplicados incorretamente.

A integridade do índice/state é parte da autoridade do core. Não editar arquivos manualmente em produção sem backup e formato documentado.

## 11. Backup / restore
A linha recente mantém sistema de backup de NPCs e a 7.11.0 corrige stutter de backups periódicos em mundos grandes, além de preservar timestamps de NPCs removidos e corrigir caminhos de restore que podiam ressuscitar/duplicar NPCs.

Upgrade deve sempre ser validado em cópia do mundo antes de promover o save principal.

## 12. Follow/look target e logout
A 7.11.0 corrige um caso de server freeze quando NPC estava seguindo/olhando para um player que desconectava. Isso torna logout/reconnect durante follow/look um regression gate obrigatório.

Target references precisam ser invalidadas quando a entidade/player deixa o mundo.

## 13. Presets
A linha 7.11.0 melhora o browser de presets para incluir presets custom/world em SNBT. Preset é template de configuração, não um NPC runtime até ser instanciado.

Aplicar preset a NPC existente deve ter semantics claras de merge/replace e nunca gerar uma segunda entidade por acidente.

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
- `easy_npc_bundle-neoforge-1.21.1-7.11.0.jar`;
- `easy_npc_config_ui-neoforge-1.21.1-7.11.0.jar`.

O Bundle organiza dependências/distribuição; o Config UI fornece ferramentas gráficas/networking de configuração. Nenhum dos dois deve ser modelado como um segundo provider de NPC state.

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
- preset apply;
- backup/restore;
- Config UI open/save/cancel;
- update de versão.

## 20. Multiplayer / idempotência
Dois jogadores podem interagir com o mesmo NPC. Actions e trades precisam liquidar exatamente uma vez. O NPC não pode existir duplicado por restore/login race. State visual deve convergir após reconnect sem criar clone server-side.

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
14. tratar 7.11.0 como metadata física quando a coluna está vazia.

## 22. Matriz de testes
1. Dedicated server boot com Core + Config UI + Bundle.
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
13. Preset custom/world apply.
14. Config UI save/cancel/reopen.
15. Backup periódico em mundo com muitos NPCs.

**Esta catalogação não afirma que esses testes foram executados.**

## 23. Evidências
- modlist física canônica de 08/09/2026: JAR, mod id, campo de versão vazio, Config UI/Bundle e Epic Fight presentes;
- publicação oficial Easy NPC Core: escopo de NPC/dialog/trading/actions/skins/API;
- release/changelog 7.11.0: estabilidade, backup/restore, stale state, follow/look logout, humanoid models, presets e chat bubbles.

> **Boundary canônico:** Easy NPC Core é authority do **NPC persistente e de suas operações**. UI, renderers e combat frameworks são consumidores/adapters, não uma segunda fonte de verdade.
