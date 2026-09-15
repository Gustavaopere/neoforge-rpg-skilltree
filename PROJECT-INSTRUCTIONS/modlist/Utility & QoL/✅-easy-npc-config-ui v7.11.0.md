# Easy NPC: Config UI

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `easy_npc_config_ui-neoforge-1.21.1-7.11.0.jar`
- **Versão 1.21.1:** `7.11.0`
- **Categoria:** QoL; Visual
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc
- **Função:** Módulo gráfico de configuração do Easy NPC que fornece screens/controles para editar NPCs e a camada de networking necessária ao fluxo de configuração, mantendo o Core como authority do NPC state.
- **Dependências:** Easy NPC Core; NeoForge 1.21.1. Runtime físico: Config UI 7.11.0. Bundle 7.11.0 também está instalado e declara a composição Core + Config UI.
- **Compatibilidade/Riscos:** Riscos de UI/API/network drift se Config UI e Core estiverem desalinhados, edição de state stale, double-submit sob latency, permissões insuficientes, screen client-only carregada no servidor e assumir que fechar/salvar UI já equivale a commit sem confirmação server-side.
- **Sobreposição:** Sobreposição apenas de superfície administrativa com comandos/config wand: todos editam o mesmo Core. Não deve haver duas cópias de configuração autoritativa.
- **Observações:** Config UI 7.11.0 permanece alinhada ao Bundle/Core físicos. A linha 7.12.1 traz alterações importantes no browser/import/export/restore de presets e em superfícies administrativas; qualquer atualização deve manter Core, Bundle e Config UI compatíveis e ser validada contra state persistente.
- **Procedência:** modlist.txt física atual — 595 entradas totais incluindo o modloader — confirma `easy_npc_config_ui-neoforge-1.21.1-7.11.0.jar`, mod id `easy_npc_config_ui` e runtime 7.11.0. Distribuição oficial revalidada em 12/09/2026 confirma linha Easy NPC 7.12.1 para 1.21.1; o módulo instalado permanece 7.11.0.
- **Histórico da decisão:**
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — lote físico #243: Config UI 7.11.0 permanece instalado; upstream Easy NPC 7.12.1 foi publicado para NeoForge 1.21.1 em 10/09/2026. Core continua authority do NPC state e a UI permanece camada de edição/networking.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `easy_npc_config_ui-neoforge-1.21.1-7.11.0.jar` · mod id `easy_npc_config_ui` · versão `7.11.0` · NeoForge 1.21.1.

## 1. Papel no modpack
Easy NPC: Config UI é a superfície gráfica de edição do Easy NPC. Ela fornece screens/controles para configurar NPCs e a camada de networking necessária ao fluxo de configuração.

O módulo **não** possui um segundo NPC state independente: o Core continua sendo a authority das entidades, persistência, dialogs, actions e trades.

## 2. Authority / ownership
- **Easy NPC Core:** state persistente e operações do NPC.
- **Config UI:** formulário/screen, input do usuário e transporte da intenção de edição.
- **Servidor:** valida e aplica mudanças persistentes conforme os contratos do Core.

A tela pode mostrar um snapshot; esse snapshot não deve ser tratado como state final até o servidor aceitar o commit.

## 3. Relação com o Bundle
O Bundle oficial organiza a instalação de Core + Config UI como módulos separados. No pack atual, Bundle 7.11.0 e Config UI 7.11.0 estão fisicamente presentes; o Core usa filename 7.11.0, embora sua coluna `mod version` física esteja vazia.

Isso demonstra alinhamento de distribuição, mas não autoriza inventar metadata ausente do Core.

Em 10/09/2026 a família Easy NPC publicou **7.12.1 para NeoForge 1.21.1**. Para esta ficha isso significa update disponível do módulo de configuração em conjunto com Core/Bundle; o runtime físico continua 7.11.0. Os fixes upstream de preset browser/import/export/restore reforçam a necessidade de regression test de Save/Cancel/reopen e edição concorrente após qualquer atualização.

## 4. Superfícies de edição
A UI expõe controles para as capacidades fornecidas pelo Core, incluindo configuração de aparência, diálogos, ações, trading e comportamento conforme o tipo/feature disponível.

IDs, campos e constraints exatos devem ser lidos da build atual quando uma automação ou preset precisar escrever diretamente os dados; esta ficha não inventa schema de formulário não auditado.

## 5. Networking
A documentação oficial da família atribui ao Config UI também a infraestrutura de networking necessária ao fluxo de configuração. Isso torna side validation crítica:
- cliente envia intenção;
- servidor valida contexto/permissão;
- Core aplica state;
- clientes recebem state final sincronizado.

Nunca aplicar a mesma mutação no handler client e no server handler.

## 6. Save / Cancel / Reopen
Fluxos de UI devem distinguir claramente salvar, cancelar e fechar. Um `Cancel` não deve persistir alteração parcial; reabrir a UI deve refletir o state server-side atual, não cache local antigo.

Sob latency, double-click em Save não pode criar ação/trade/preset duplicado.

## 7. Permissões
Configuração de NPC é operação administrativa/gameplay sensível. O servidor precisa decidir quem pode editar; esconder ou desabilitar um botão no cliente não é controle de autorização suficiente.

Não presumir o modelo exato de permissão sem source/config da build; apenas preservar o boundary server-side.

## 8. Client / Server
Screens, widgets e layout são client-side. Networking/commit envolve ambos os lados, e o Core/server mantém a fonte de verdade.

Dedicated server deve carregar o módulo sem depender da inicialização de uma screen gráfica.

## 9. Lifecycle
Validar:
- abrir/fechar screen;
- save/cancel;
- editar NPC carregado;
- NPC descarregar enquanto a UI está aberta;
- player perder permissão/sair;
- reconnect;
- server restart;
- troca de dimensão;
- Core reload/update;
- edição simultânea por dois players.

## 10. Multiplayer / concorrência
Dois administradores podem abrir a mesma configuração. Sem controle de state/version, um save tardio pode sobrescrever mudanças recentes. O comportamento real precisa ser testado; integrações próprias devem evitar assumir optimistic locking inexistente.

A regra mínima é: mutation deve ser validada no servidor e resultar em state único.

## 11. Riscos
1. Core/Config UI em versões diferentes;
2. packet/API drift;
3. double-submit;
4. state stale sobrescrevendo edição recente;
5. Cancel persistir alteração parcial;
6. permission check apenas client-side;
7. NPC descarregar durante edição;
8. screen client-only carregar no dedicated server;
9. GUI mostrar valor não confirmado pelo Core;
10. tratar Config UI como provider de NPCs.

## 12. Matriz de testes
1. Dedicated server boot com Core + Config UI + Bundle.
2. Abrir UI por fluxo suportado e editar NPC simples.
3. Save → fechar → reabrir → conferir persistência.
4. Cancel → reabrir → confirmar ausência de alteração.
5. Double-click/latency no Save.
6. Dois players editando o mesmo NPC.
7. NPC unload/removal enquanto a UI está aberta.
8. Player logout/reconnect durante edição.
9. Testar permissões com usuário autorizado e não autorizado.
10. Restart do servidor após alteração.
11. Smoke-test após update de Core/Config UI.

**Esta catalogação não afirma que esses testes foram executados.**

## 13. Evidências
- modlist física atual: JAR/mod id/versão 7.11.0;
- documentação oficial Easy NPC: Config UI como módulo de configuração e networking;
- Bundle oficial: composição Core + Config UI como módulos separados.

> **Boundary canônico:** Config UI captura e transporta **edições**. O Core/server continua sendo a authority do NPC e do state persistente.
