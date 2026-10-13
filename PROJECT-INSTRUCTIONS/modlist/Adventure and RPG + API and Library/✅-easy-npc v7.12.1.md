# Easy NPC

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#239**: JAR `easy_npc-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc`, runtime metadata **vazia**, SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc`.

## Propriedades do registro

- **Mod:** Easy NPC
- **Arquivo JAR:** `easy_npc-neoforge-1.21.1-7.12.1.jar`
- **Categoria:** RPG, QoL
- **Função:** Core server-authoritative do Easy NPC para criar, persistir e controlar NPCs customizados, diálogos, ações, trading, aparência/skins, comportamento e integrações do ecossistema.
- **Dependências:** NeoForge 1.21.1. Easy NPC Config UI 7.12.1 e Easy NPC Bundle 7.12.1 estão fisicamente presentes. O filename/publicação identifica Core 7.12.1, mas a coluna `mod version` da modlist física está vazia. Epic Fight 21.17.3.1 está presente e é uma integração suportada pela linha Easy NPC.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** NPC persistente é state crítico: riscos de duplicate restore, stale index/file state, backup stutter, owner-login restore, follow/look target após logout, action/trade double execution, custom model/pose interference e UI/client divergindo do servidor. A linha 7.11.0 contém correções explícitas para várias dessas superfícies.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all
- **Procedência:** modlist física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `easy_npc-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc`, SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc` e campo runtime vazio; Bundle/Config UI `7.12.1` e EME `2.4.0` também estão presentes. Changelogs oficiais 7.12.0/7.12.1 documentam a baseline instalada, e 7.12.2/7.13.0/7.14.0 foram revisados como updates upstream não instalados.
- **Observações:** Fail-closed preservado: `7.12.1` vem do filename/build/publicação do artefato instalado, não da metadata runtime. Core, Bundle e Config UI estão fisicamente alinhados em 7.12.1; Easy Model Entities está fisicamente em 2.4.0.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — artefato físico permanece `easy_npc-neoforge-1.21.1-7.12.1.jar`. A travessia completa posterior é **7.12.2 → 7.13.0 → 7.14.0**. 7.12.2/7.13.0 alteram protocolo/network boundaries; 7.14.0 corrige sincronização tardia de pose/settings/trading/sound. Promoção exige atualizar a família Core/Bundle/Config UI em conjunto.
- **Decisão:** Manter
- **Sobreposição:** Pode sobrepor funções narrativas/trading de outros sistemas de NPC/quest, mas Easy NPC é o provider direto dos NPCs criados nele. Epic Fight integra combate/animação; Config UI edita o state; nenhum deles deve duplicar ownership do NPC.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Autoridade física:** `easy_npc-neoforge-1.21.1-7.12.1.jar` · mod id `easy_npc` · NeoForge 1.21.1 · SHA-1 `ed56854e39545cefdde1fe6695d9f52a31393bdc`. **A coluna ****`mod version`**** da modlist física está vazia.** O identificador `7.12.1` vem do filename/build e da publicação oficial correspondente.
## 1. Papel no modpack
Easy NPC Core é o provider central de NPCs customizados do projeto. Ele mantém a entidade/NPC, persistência, diálogos, ações, trading, aparência, comportamento e APIs correspondentes. O Config UI é a superfície de edição; o Bundle é distribuição/dependência.
## 2. Authority / ownership
- **Easy NPC Core:** identidade e state do NPC, persistência, comportamento, dialogs/actions/trades, presets e integrações próprias.
- **Servidor:** authority final sobre mutações, posição, inventário/trade, ações e save.
- **Config UI:** edição/apresentação/configuração; não deve manter cópia autoritativa independente.
- **Epic Fight:** combate/animação quando a integração correspondente atua.
- **Easy Model Entities:** model/render integration quando configurada; não assume ownership do NPC persistente.
NPCs criados no Easy NPC não devem ser recriados por scripts externos como uma segunda entidade paralela para representar o mesmo personagem.
## 3. Versionamento fail-closed
A modlist física confirma o JAR `easy_npc-neoforge-1.21.1-7.12.1.jar` e o mod id `easy_npc`, mas o campo de versão runtime continua vazio.
A publicação oficial identifica o artefato como **7.12.1** para NeoForge 1.21.1. Portanto a ficha preserva a distinção obrigatória:
- **JAR/build/publicação:** 7.12.1;
- **metadata runtime na modlist:** não declarada;
- **Minecraft:** 1.21.1;
- **loader:** NeoForge.
Nenhuma ausência de metadata foi preenchida por inferência.
## 4. Criação e edição de NPCs
O core suporta criação e edição de NPCs customizados com propriedades de aparência, comportamento e interação. O fluxo normal usa comandos e/ou Config UI/config wand quando o módulo visual está instalado.
Toda alteração persistente precisa terminar em um único state server-side; fechar/reabrir a tela não pode duplicar NPCs ou reexecutar ações.
A 7.12.0 adicionou/ajustou superfícies importantes:
- botões **Set Home Here** e **Reset Home**;
- export de preset preservando **NPC ID e posição**, para que o import possa restaurar o mesmo NPC;
- opção **Block Vehicles** para impedir NPCs em boats, minecarts e chairs modded;
- profession selection em todas as skin configurations e uma skin built-in adicional por tipo;
- client settings para ocultar nametag atrás de paredes e `highlightEnabled` para outlines do wand;
- API access a movement, actions e presets e condição `Exists`.
## 5. Diálogos e interações
O projeto suporta diálogos e interações configuráveis. Diálogo é uma camada de apresentação/flow; qualquer action disparada pelo diálogo precisa ser validada no servidor e ser idempotente quando o usuário clica duas vezes, reloga ou sofre latency.
Quests externas podem consumir o resultado, mas não devem presumir que mostrar uma linha de diálogo significa que uma action server-side foi concluída.
A 7.12.0 alterou o API dialog call para reportar **unknown dialog label** em vez de sucesso incondicional, reduzindo falso positivo de integração.
## 6. Actions
Easy NPC permite associar ações a interações/eventos do NPC. Como essas ações podem alterar gameplay, a regra é execução exatamente uma vez no servidor com validação de contexto/permissão.
Riscos típicos: ação disparada em client + server, action repetida ao reabrir diálogo ou state parcialmente salvo após unload.
## 7. Trading
Trading é um subsystem próprio relevante do core. Offers, conditions e settlement precisam permanecer server-authoritative; item/currency não pode ser consumido/entregue duas vezes em concorrência multiplayer.
Ao integrar economia/quests, consumir o resultado do trade em vez de implementar outra transação sobre a mesma GUI.
A 7.12.0 passou a tocar o som **Trade completed** para trades concluídos; isso é feedback de apresentação, não uma segunda authority de settlement.
## 8. Skins e aparência
A linha Easy NPC suporta skins por nome de jogador/URL e outras opções de aparência. Recurso visual remoto precisa ter fallback controlado; falha de textura não deve apagar o NPC nem alterar seu state gameplay.
Model/skin providers externos podem tocar a mesma render surface e precisam de precedence clara.
A 7.12.1 adiciona sorting, match count, reload, auto-close, NPC ID e tooltips nas superfícies de presets/skins.
## 9. Humanoid NPCs e custom models
A 7.11.0 já continha ajuste para humanoid NPCs ignorarem custom player models/animations/poses em rotas onde esses transforms causavam interferência. Isso continua herdado pela 7.12.1.
A 7.12.0 alterou a dependência opcional de Easy Model Entities para **EME 2.4.0+**. O pack atual possui `easy_model_entities-neoforge-1.21.1-2.4.0.jar`; o campo runtime desse mod também aparece vazio na modlist, mas o filename/build satisfaz exatamente o piso publicado. Não interpretar isso como garantia de compatibilidade visual completa sem teste.
## 10. Persistência, índice e identidade de NPCs
NPCs são conteúdo persistente do mundo. A linha 7.11.0 já registrava correções para stale NPC data, sincronização entre índice e arquivos, writes redundantes, warnings de unload e cenários de restore/duplicação.
A 7.12.0 preserva NPC ID/position em presets exportados e corrige owner list/objectives perdidos em imports/spawn eggs; também corrige automatic-backup restore que podia devolver NPC sem owner.
A 7.12.1 corrige **unstable preset IDs**, leaks de rate limit no spawn e o fluxo **Spawn New** que podia empilhar NPCs no mesmo ponto.
A integridade do índice/state é parte da authority do core. Não editar arquivos manualmente em produção sem backup e formato documentado.
## 11. Backup / restore
A 7.11.0 já continha correções de stutter e caminhos de restore que podiam ressuscitar/duplicar NPCs.
Na linha instalada:
- 7.12.0 faz import de preset atualizar o NPC original na posição armazenada; criar cópia passa a exigir **Import as new NPC** ou preset item;
- 7.12.1 sempre mostra **Restore**, inclusive para datapack presets, com tooltip explicando a indisponibilidade quando aplicável;
- quando restore não é permitido, 7.12.1 **recusa** a operação em vez de spawnar uma cópia;
- mensagens de import/export foram corrigidas para não omitir/wrongly report replacements.
Upgrade continua devendo ser validado em cópia do mundo antes de promover o save principal.
## 12. Follow/look target e logout
A 7.11.0 corrigiu server freeze quando NPC seguia/olhava player que desconectava. Esse comportamento continua herdado pela 7.12.1 e torna logout/reconnect durante follow/look um regression gate obrigatório.
Target references precisam ser invalidadas quando a entidade/player deixa o mundo.
## 13. Presets
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
## 14. Chat bubbles e sons
A linha 7.11.0 inclui correções de chat bubbles com Iris/shaders, formatting/macros/traduções e `minLightLevel`.
A 7.12.0 adiciona **Sound configuration** para dialogs, speech, swimming, eating e fall damage, com seleção pesquisável, volume e pitch. Esses elementos são apresentação/feedback; a mensagem/dialog/action state continua associado ao core.
Em servidor dedicado, renderer de chat bubble e GUI sonora não devem ser necessários para processar interação.
## 15. Epic Fight
O Easy NPC possui suporte integrado ao Epic Fight na linha moderna. O pack instala **Epic Fight 21.17.3.1** e addons relacionados.
A authority precisa ser dividida: Easy NPC mantém NPC/AI/action state; Epic Fight controla seu framework de combate/animação quando aplicado. Não executar dano/animation event em duplicidade nas duas camadas.
## 16. Cobblemon mixin — limite de presença
O JAR físico declara `easy_npc.cobblemon.mixins.json`, demonstrando uma superfície opcional de integração. **Cobblemon não foi confirmado na modlist física atual**, portanto essa compatibilidade não é tratada como ativa no pack.
Mixin opcional no JAR não prova provider instalado.
## 17. Config UI e Bundle
O pack contém fisicamente:
- `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` / runtime `7.12.1`;
- `easy_npc_config_ui-neoforge-1.21.1-7.12.1.jar` / runtime `7.12.1`.
Core/Bundle/Config UI estão, portanto, alinhados pela versão de build. O Bundle organiza dependências/distribuição; o Config UI fornece ferramentas gráficas/networking de configuração. Nenhum dos dois é um segundo provider de NPC state.
## 18. Client / Server
- NPC identity/state/position/actions/trades: server-authoritative;
- dialogs e screens: client-facing com commit validado no servidor;
- skins/models/chat bubbles/sounds: apresentação client-side;
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
- preset apply/import/export/batch import;
- backup/restore;
- Config UI open/save/cancel;
- spawn rate limit;
- atualização de versão.
## 20. Multiplayer / idempotência
Dois jogadores podem interagir com o mesmo NPC. Actions e trades precisam liquidar exatamente uma vez. O NPC não pode existir duplicado por restore/login/import race. State visual deve convergir após reconnect sem criar clone server-side.
Wand outlines da linha 7.12.0 são restritos a holder/owner/admin; outros jogadores não devem ver outlines, e NPCs invisíveis devem permanecer ocultos para eles.
## 21. Riscos
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
## 22. Matriz de testes
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
## 23. Evidências
- modlist física atual de 21/09/2026: Core 7.12.1 por filename/build, mod id, **campo de versão runtime vazio**, SHA-1; Config UI/Bundle 7.12.1; Easy Model Entities 2.4.0 por filename/build; Epic Fight presente;
- publicação oficial Easy NPC Core: escopo de NPC/dialog/trading/actions/skins/API e Release 7.12.1 para NeoForge 1.21.1;
- changelog 7.11.0: estabilidade, backup/restore, stale state, follow/look logout, humanoid models, presets e chat bubbles;
- changelog 7.12.0: ID/posição de preset, owner restore, import sem cópia, sound config, vehicle blocking, skin/client settings, API/conditions e piso EME 2.4.0+;
- changelog 7.12.1: restore/import/export/preset browser/spawn state, IDs/rate-limit, batch import e UX de presets/skins.
> **Boundary canônico:** Easy NPC Core é authority do **NPC persistente e de suas operações**. UI, renderers, model providers e combat frameworks são consumidores/adapters, não uma segunda fonte de verdade.

## 24. Atualizações upstream 7.12.2 → 7.14.0 — não instaladas
A versão física do pack continua em **7.12.1**. A sequência pública posterior para NeoForge 1.21.1 auditada nesta rodada é **7.12.2 → 7.13.0 → 7.14.0**.

### 7.12.2
- corrige o hitbox da **Fairy**, que era pequeno demais e fazia name tag/speech bubble ficarem dentro do corpo;
- corrige NPCs escalados mantendo hitbox e eye level antigos, o que afetava hits, cliques e look targeting;
- adiciona limites na leitura de **nomes, listas e NPC data em network packets**, evitando payloads superdimensionados sem bound;
- impede aplicação de posições, escalas, atributos, nomes e durações inválidos;
- faz Fairy ocupar o espaço do próprio modelo para colisão e interação;
- alinha Skeleton, Stray e Bogged à altura de hitbox vanilla;
- altera a **network protocol version**, exigindo cliente e servidor na mesma versão.

### 7.13.0
- corrige tags de cor/formatação aparecendo como texto literal em nomes de botões de diálogo;
- corrige preview do editor exibindo nomes minúsculos como translation key crua;
- corrige o item **Move EasyNPC** para owners fora do creative;
- corrige `/easy_npc owner set` reportando sucesso quando a troca de owner falhava;
- corrige crash na tela de URL/player skin quando ocorre erro de download da skin;
- corrige pesquisa do preset browser que podia ficar sem resultados depois de scroll;
- adiciona `@initiator`, `@npc` e `@score()` em nomes de botões de diálogo;
- altera novamente a **network protocol version**, exigindo cliente e servidor na mesma versão;
- adiciona testes automáticos de cliente com Client Runtime Interface Toolkit;
- inclui refatoração ampla do core e cleanup interno.

### 7.14.0
- corrige o issue #846, preservando **custom poses para jogadores que só passam a observar o NPC depois**;
- corrige **attribute e trading settings** ausentes para late viewers;
- alterações de **max uses, XP e trading type** passam a aparecer nas trading screens imediatamente, sem exigir reload;
- alterações via `/easy_npc sound set` passam a alcançar jogadores sem reload do NPC;
- adiciona GameTests cobrindo poses/settings após respawn e para jogadores novos.

Esses deltas endurecem a regra de state replication: um NPC já existente precisa apresentar o mesmo pose/attribute/trading/sound state para observadores antigos, novos e recém-respawnados. Isso é particularmente relevante para quests/NPCs persistentes do pack.

**Mudança operacional crítica:** tanto 7.12.2 quanto 7.13.0 alteram o protocolo de rede. O pack físico contém Core, Bundle e Config UI em 7.12.1; uma promoção para 7.14.0 deve atualizar o conjunto Easy NPC de forma coordenada, sem misturar versões.

Impactos adicionais relevantes para este pack: o ajuste de hitbox/eye level deve ser regressado junto de Epic Fight e modelos/escala customizados; os novos bounds de network data e validações de posição/escala/atributos endurecem a fronteira cliente-servidor e merecem teste em presets/imports existentes.

Gate de regressão: dedicated server + client alinhados em 7.14.0; Fairy e NPCs escalados com hitbox/eye level corretos; Skeleton/Stray/Bogged; packets com dados excessivos/inválidos; diálogo/macros; owner commands; preset search; skin error handling; import/restore; integração com Easy Model Entities; late viewers/respawn preservando pose, attributes, trading settings e sound state.

Fontes upstream: changelogs oficiais Easy NPC: Core; releases 1.21.1 7.12.2, 7.13.0 e 7.14.0 publicadas no CurseForge. Nenhum teste acima foi executado nesta atualização documental.

## Reauditoria de publicação e compatibilidade — 13/10/2026

**Projeto:** Easy NPC: Core. **Última publicação oficial NeoForge 1.21.1 identificada:** `7.15.0`; artefato de referência: `easy_npc-neoforge-1.21.1-7.15.0.jar`. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all.

Release NeoForge 1.21.1 de 12/10/2026; a modlist histórica identifica Core `easy_npc-neoforge-1.21.1-7.12.1.jar` (#239) mas **metadata runtime sem versão**. Preservar explicitamente essa divergência histórica; não preencher a metadata ausente por inferência. Upgrade 7.12.1→7.15.0 junto do Easy NPC bundle e Config UI, sem misturar artefato core/bundle e sem contagem duplicada JarJar. Validar entidades/NPC dialogs, actions/server permissions, saved NPC configs, scripts/reload, client GUI e world migration. As adições confirmadas pelo changelog oficial 7.15.0 estão discriminadas na reavaliação técnica abaixo; não atribuir API signatures sem inspecionar source.

**Autoridade física:** estado histórico do JAR documentado neste dossiê e `modlist(1).txt` permanece intacto; novo dump do pack, hashes, metadata e testes runtime não foram fornecidos. **Decisão:** candidato upstream (ou revalidação sem release nova), não uma atualização instalada. **Filename:** preservar prefixo ✅ e versão física histórica no nome até evidência da instalação.

## Complemento crítico — changelog Easy NPC 7.15.0 e bloqueio da linha 1.21.1

A release 7.15.0 do **Core** (e do bundle vinculado) declara fixes que alteram comportamento persistente, não só interface:

- Preset import via `/execute` ou command blocks fica silencioso; limites de NPC também se aplicam ao spawner default.
- Sons de resource packs podem ser escolhidos na configuração; NPCs deixam de nascer canhotos aleatoriamente.
- Botões de diálogo com mesmo label em diálogos distintos deixam de compartilhar indevidamente o lock.
- Corrige NPCs deletados/duplicados que voltavam, vazamentos de memória, problemas de proprietário/GUI e falta do mob patch Epic Fight para NPC Wither Skeleton.
- **Correção histórica:** `Set Home Here`, `Reset Home`, preservação de ID/posição e atualização do NPC original por importação são novidades da **7.12.0**, anteriores ao JAR físico 7.12.1; não devem ser atribuídas à 7.15.0.
- Muda política de backup: dados de mundos e NPCs mortos expiram em **30 dias** conforme `storage.cfg`; revisar política local de retenção/backup antes do upgrade.
- **Novo requisito declarado para 7.15.0:** mesma versão do mod em cliente/servidor **e Easy Model Entities 2.5.0+**. O catálogo oficial consultado mostra Easy Model Entities 2.5.0 **para Minecraft 26.1.2**, mas a última publicação NeoForge **1.21.1** confirmada é **2.4.0**. **BLOQUEADO** migrar a tríade Easy NPC 7.15.0 no Minecraft 1.21.1 até sair build da dependência no alvo ou existir confirmação oficial inequívoca de compatibilidade alternativa. Não usar o JAR 26.1.2 em 1.21.1.

**Fontes e segregação por loader:** [changelog oficial da série 7.15.0](https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/9151672) (**esta página específica corresponde ao JAR Fabric 1.21.1; NÃO é o artefato NeoForge**); [bundle Easy NPC: releases filtradas por Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all?version=1.21.1) (confirmar `easy_npc_bundle-neoforge-1.21.1-7.15.0.jar`); [Core: releases filtradas por Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all?version=1.21.1) (confirmar `easy_npc-neoforge-1.21.1-7.15.0.jar`); [Easy Model Entities: arquivos por loader/MC](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all) (última build NeoForge 1.21.1 identificada: 2.4.0). **Matriz obrigatória:** backup de NPCs, teste de import preservando UUID/owner/home, prevenção de respawn duplicado, scripts/commands, Resource-pack sounds, lock local de diálogo, retenção de backup, Epic Fight NPC poses, client+dedicated server na mesma versão.

## Reavaliação de conteúdo 7.15.0 — fontes primárias e gates — 13/10/2026

### O que realmente foi ADICIONADO em 7.15.0 (não instalado)

- **Novas ações/objetivos:** `Jump`, `Assist Owner` e `Follow Nearest Player`. O changelog confirma a introdução, mas não fixa cooldown, distância, seleção de alvos ou precedence com objetivos anteriores; esses parâmetros devem ser derivados do JAR/config antes de criar contratos de datapack ou quests.
- **Inventário de NPC:** introduz item pickup e drops na morte. Este é um novo domínio de persistência/loot, sujeito a armazenamento duplicado, perda de itens e concorrência com looting, fake players, hoppers, claims ou mods que alterem death drops. Não afirmar regras de pickup/stack, permissões ou drop chances sem código/metadata.
- **Interação/apresentação:** `Sit on Shoulder`, boss bar, modo de animação `Hold` e preset **Bank Teller**. O `Sit on Shoulder` do NPC não deve ser automaticamente confundido com a capacidade de shoulder sitting da versão EME 2.5.0 em Minecraft 26.1.2; atribuir cada comportamento ao respectivo provider depois de verificar integração.
- **Operação/extensibilidade:** `/easy_npc debug core loglevel` e novos métodos de API para addons. As assinaturas e efeitos dos métodos novos não foram inspecionados, portanto **ABI e addon builds ficam em gate de compatibilidade**.

### Fixes 7.15.0 e mudanças desde o baseline 7.12.1

- Importação silenciosa de presets via `/execute` ou command blocks; limite de NPC aplicado também ao spawner padrão; áudios de resource packs selecionáveis.
- Locks de botões de diálogo agora isolados por diálogo, mesmo se labels coincidirem; corrige também NPCs deletados/duplicados reaparecendo, memory leaks e problemas de owner/UI.
- Correção de NPCs nascendo canhotos aleatoriamente e patch Epic Fight ausente para Wither Skeleton NPC.
- **7.14.0 anterior:** late-viewers recebem pose, atributos, trading settings e som atualizados, sem exigir reload; **7.13.0 anterior:** macros `@initiator`, `@npc`, `@score()` e nova versão do protocolo de rede.
- **Correção de atribuição histórica:** `Set Home Here`, `Reset Home`, export de ID/posição e import de presets atualizando o NPC existente são da **7.12.0**, portanto **já antecedem o JAR histórico 7.12.1**. Não computar como novidades 7.15.0.

### Dependência e decisão de promoção

**Changelog 7.15.0** declara alinhamento exato da versão Easy NPC no cliente/servidor e **Easy Model Entities 2.5.0+**. Até esta reauditoria, o artefato EME publicado para **NeoForge Minecraft 1.21.1** permanece **2.4.0**, ao passo que EME 2.5.0 NeoForge encontrada pertence a **Minecraft 26.1.2**. O histórico 7.12.0 citava EME 2.4.0+, mas isso **não** autoriza ignorar o novo requisito. A declaração na release é suficiente para suspender a promoção por risco, embora a natureza hard/optional exata de EME 7.15.0 dependa de examinar o `neoforge.mods.toml` e o comportamento de fallback do JAR alvo. **Decisão: BLOQUEADO em configuração atual até validação oficial/runtime; não substituir 2.4.0 por build 26.x.**

### Gate de aceitação em cópia de teste

1. Core + Bundle + Config UI sincronizados em versão e client/server handshake (network protocol 7.13+).
2. Criar NPC com inventário, coletar itens no chão, matar e inspecionar drops; repetir em unload/reload, morte/revivificação, dois clientes, player claim, respawn e rollback, verificando ausência de dupe/loss.
3. Testar Jump, Assist Owner e Follow Nearest Player com hostiles, alvos inválidos, vários players, pathfinding, shutdown/relog e prioridade de outros objetivos.
4. `Sit on Shoulder` e `Hold` contra EME, GeckoLib/EMF/ETF/Epic Fight; observar âncoras, escala, colisão e transforms, sem assumir alteração de hitbox.
5. Boss bar criar/remover/reconectar com rastreio correto e sem vazamento após unload/death; testar Bank Teller/trading/loot junto a Economy e MineColonies se aplicável.
6. Diálogos com labels duplicadas; import via command blocks e `/execute`; default spawner sob limite de NPCs; presets antigos, owners e posicionamento.
7. Testar sound resources/F3+T, Wither Skeleton Epic Fight mob patch, logs e permissão de `/easy_npc debug core loglevel`.
8. Verificar arquivo `storage.cfg` e políticas de expiração de backup de 30 dias **antes** de atualizar; backup externo independente do mod obrigatório.
9. Confirmar EME target 1.21.1, requisito exato no manifesto do Core 7.15.0 e smoke de dedicated server; não registrar nenhum teste como executado nesta documentação.

**Autoridades:** [changelog oficial Easy NPC, branch Minecraft 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md) (seções 7.15.0, 7.14.0, 7.13.0 e 7.12.0); [Easy NPC Core — arquivos NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all?version=1.21.1); [Easy Model Entities — arquivos oficiais](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all); [EME 2.5.0 NeoForge para Minecraft 26.1.2 — não compatível como JAR de 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/9152252).

**Estado físico:** `modlist(1).txt` continua sendo a última autoridade disponível. Sem novo dump/JAR/SHA, nenhuma destas funções de 7.15.0 pode ser declarada instalada ou testada. Não renomear o dossiê versionado `✅-` até confirmar nova instalação.

## Auditoria declarativa NeoForge 7.15.0 — 13/10/2026

### Dependência EME: tipo e faixa publicados no source

O `core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml` declara `[[dependencies.easy_npc]] modId = "easy_model_entities"`, **`type = "optional"`**, **`versionRange = "[2.5.0,3.0.0)"`**, `ordering = "AFTER"`, `side = "BOTH"`. Logo, **EME não é estritamente obrigatória quando ausente**, mas **se estiver instalada deve cumprir a faixa de compatibilidade**: a própria documentação NeoForge 1.21.1 explicita que `optional` não exige presença e ainda valida a versão de dependência presente.

O último snapshot físico contém **`easy_model_entities-neoforge-1.21.1-2.4.0.jar`**. **2.4.0 fica fora de `[2.5.0,3.0.0)`**. Portanto, Easy NPC Core **7.15.0 + EME física 2.4.0 é combinação não aprovada pelo contrato declarativo**; não assumir que a label opcional permita ignorar incompatibilidade. O campo de versão runtime da EME aparece vazio no dump histórico, agravando a necessidade de verificar o valor que o loader efetivamente atribui ao JAR.

### Caminhos tecnicamente distintos

1. **Manter o pack atual** (Core/Bundle/UI 7.12.1 + EME 2.4.0) até uma build EME 2.5.0+ para **NeoForge Minecraft 1.21.1** ser publicada/verificada.
2. **Avaliar ausência de EME somente em cópia descartável**, pois o manifesto declara a dependência como opcional. **Não recomendar removê-la do pack real**: há conteúdo, model entities e mundos potencialmente dependentes, e remover o provider pode provocar perdas, degradação visual ou dados órfãos. Um teste de boot isolado não certifica ausência de perda funcional.
3. **Alternativa upstream explícita:** uma release do Core para 1.21.1 com faixa compatível ou confirmação oficial sobre fallback. Não fabricar versão, editar manifestos do JAR ou aplicar mod de outro Minecraft como workaround.

**Estado:** `BLOQUEADO — incompatibilidade de faixa de versão com EME presente`. As descrições anteriores que tratavam a natureza `optional` do manifesto como **não verificada** foram superadas por esta leitura do source; isso **não** comprova o conteúdo binário nem substitui smoke client/dedicated-server.

**Código-fonte lido (branch oficial `1.21.1`, 13/10/2026):** [manifesto NeoForge Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [manifesto NeoForge Config UI](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [manifesto NeoForge Bundle](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [build.gradle do Bundle NeoForge](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/build.gradle), [gradle.properties do Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/gradle.properties), [documentação oficial NeoForge 1.21.1 — dependências opcionais](https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/), [CurseForge EME 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all?version=1.21.1), [Modrinth EME 2.4.0 NeoForge 1.21.1](https://modrinth.com/mod/easy-model-entities/version/KIk2E6sP). **Limite:** estes arquivos são source da branch 1.21.1; o manifesto do **binário publicado 7.15.0** ainda não foi extraído e comparado byte a byte, nem houve boot do perfil.
