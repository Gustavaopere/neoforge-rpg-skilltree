# BlockUI

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81538c20e460f49741ac  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** BlockUI
- **Arquivo JAR:** `blockui-1.0.211-1.21.1-snapshot.jar`
- **Versão 1.21.1:** `1.0.211-1.21.1-snapshot`
- **Categoria:** Biblioteca; Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/blockui
- **Função:** Framework XML de interface: layouts declarativos em XML com backing Window para callbacks/data supply, além de imagens, buttons/handlers, text input, scroll e drag screens usados por MineColonies/Structurize e afins.
- **Dependências:** Biblioteca estrutural. Pack confirma MineColonies 1.1.1377, Structurize 1.0.832 e Multi-Piston 1.2.58 como consumidores/ecossistema relacionados.
- **Compatibilidade/Riscos:** Snapshot/beta 1.0.211: riscos em API/layout changes, resource/XML reload, callback duplication, client/server data authority e consumers compilados para snapshot diferente. UI não deve virar authority de gameplay.
- **Sobreposição:** Framework específico de UI. Não é substituível por outra GUI library sem migração explícita dos consumidores MineColonies/Structurize/etc.
- **Observações:** Runtime name UI Library Mod. 1.0.211 é snapshot NeoForge 1.21.1. BlockUI é XML-based: XML define estrutura; backing Window trata callbacks e dados. Ferramentas públicas incluem images, buttons/handlers, text input, scroll/drag screens.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge oficial BlockUI + documentação oficial MineColonies/BlockUI + arquivo 1.0.211 snapshot.
- **Histórico da decisão:** sem histórico adicional registrado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — XML/window/widget/data-binding lifecycle, consumers físicos, side e riscos de snapshot catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `blockui-1.0.211-1.21.1-snapshot.jar`, mod id `blockui`, runtime name `UI Library Mod`, versão `1.0.211-1.21.1-snapshot`. É uma build **snapshot/beta** para NeoForge 1.21.1 e uma dependência estrutural do ecossistema MineColonies.

## 1. Papel e autoridade
BlockUI é um **framework de interface XML-based**. O XML define a estrutura da tela e uma classe backing `Window` fornece callbacks e dados para os widgets. A library não é authority de colony state, inventário, crafting ou qualquer gameplay do consumer; ela apresenta e encaminha interação para a lógica real.

## 2. Modelo XML
A estrutura visual é declarada em XML. Isso permite que consumidores componham layouts sem hardcodar toda a hierarquia de widgets no código.

Riscos operacionais:
- XML inválido ou resource ausente impedir abertura da tela;
- id de widget divergente do esperado pelo backing code;
- alteração de layout em resource pack quebrar callback/data binding;
- cache de resource antigo sobreviver a reload.

## 3. Backing Window
A documentação oficial define uma classe backing **`Window`** para tratar callbacks e fornecer dados à interface.

Contrato:
- Window observa/representa state do consumer;
- ação de botão/input pode solicitar mudança;
- gameplay state deve ser validado no servidor/provider;
- fechar/destruir a janela deve liberar listeners/referências do objeto exibido.

Não usar valor de label/text field client-side como authority do sistema.

## 4. Imagens
BlockUI oferece widgets de imagem. Imagens são apresentação; falha de asset deve degradar UI sem alterar state de servidor. Resource reload precisa invalidar textura/resource handles conforme o lifecycle do Minecraft.

## 5. Buttons e Button Handlers
Buttons e handlers são uma superfície central do framework. Riscos:
- handler registrado duas vezes;
- double click/packet duplicar operação;
- botão continuar ativo depois de o alvo/container deixar de ser válido;
- client-side handler executar mutation sem server validation.

Operações como contratar cidadão, iniciar build ou alterar configuração do consumer precisam continuar authority do mod consumidor.

## 6. Text Input
Text input permite entrada do usuário em telas. Dados precisam ser tratados como input não confiável:
- validar tamanho/formato;
- sanitizar quando usado em nomes/comandos/paths;
- servidor confirmar permissões;
- não aceitar client text como referência direta a objeto inexistente.

## 7. Scroll e Drag Screens
O framework suporta scroll e drag screens. Essas funções são layout/input local e precisam manter foco, clipping, hit testing e posição coerentes em diferentes resoluções/UI scales.

Não confundir drag visual de elemento com transferência real de item/entidade sem callback explícito do consumer.

## 8. Ecossistema físico no pack
A modlist atual confirma componentes fortemente relacionados:
- MineColonies `1.1.1377-1.21.1-snapshot`;
- Structurize `1.0.832-1.21.1`;
- Multi-Piston `1.2.58-1.21.1`;
- BlockUI `1.0.211-1.21.1-snapshot`.

A documentação oficial também cita projetos como StorageRacks. A necessidade de BlockUI é determinada pelos consumidores; não remover por coexistência com outras GUI libraries.

## 9. Snapshot versioning
A build instalada é explicitamente `snapshot`. Existe release estável 1.0.209 na linha 1.21.1, enquanto 1.0.211 snapshot é posterior. Portanto:
- não assumir API stability sem verificar consumers;
- atualizar BlockUI isoladamente pode quebrar XML/widget/backing contracts;
- downgrade também pode quebrar consumers compilados para métodos/correções posteriores.

Versionar em conjunto com MineColonies/Structurize é a abordagem segura.

## 10. Changelog 1.0.211
Material oficial da build 1.0.211 inclui ajuste relacionado a sections/cache (`getSections()` retornando sections não cacheadas). Isso reforça que caching e atualização de estruturas UI são parte relevante do lifecycle.

Não hardcodar comportamento antigo de cache em compat externa.

## 11. Client/server e packets
A renderização/UI é cliente. Porém ações iniciadas na UI frequentemente afetam gameplay de MineColonies/consumers.

Regras:
- cliente envia intenção;
- server/consumer valida player, permission, distance, ownership e state;
- servidor executa mutation;
- UI atualiza a partir do state sincronizado.

Nunca conceder authority a um widget só porque a tela está aberta.

## 12. Lifecycle de tela
Validar:
- abrir/fechar/reabrir;
- trocar dimensão com tela aberta;
- container/colony/building alvo ser removido;
- logout/reconnect;
- resize da janela/UI scale;
- resource reload;
- screen replacement/nesting;
- callbacks assíncronos do consumer.

Referências a block entity/entity/menu inválidos devem falhar fechado.

## 13. Resource reload
Como layouts e assets vêm de resources, F3+T/resource pack changes podem reconstruir elementos. Risks:
- widget id removido mas callback ainda buscado;
- imagem/model missing;
- cache de sections antigo;
- layout atual manter referência a resource já descartado.

Consumers devem reconstruir a UI pelo framework em vez de manter ponte stale.

## 14. Multiplayer
Em multiplayer, uma tela pode exibir state que muda por outro jogador ou pelo servidor. O client snapshot visual pode ficar temporariamente desatualizado; qualquer ação deve ser revalidada no momento do server handling.

Duas telas alterando o mesmo building/storage não podem confiar em optimistic local state para evitar race conditions.

## 15. Acessibilidade e escala
Como library visual, validar diferentes GUI scales/resoluções, texto longo/localizações e scroll. Layout XML não deve cortar controles obrigatórios ou tornar ações inacessíveis quando string traduzida é maior.

Isso é especialmente relevante em MineColonies, que possui telas densas e grande volume de dados.

## 16. Riscos
1. API snapshot incompatível com consumer.
2. XML/widget IDs divergentes do backing code.
3. Button callback duplicar packet/operação.
4. Client widget virar authority de gameplay.
5. Cache/resource stale após reload.
6. Target object ser removido enquanto Window permanece aberta.
7. Text input não validado no servidor.
8. UI scale/localização quebrar layout/interação.

## 17. Matriz de testes
1. Dedicated server boot com BlockUI + MineColonies stack.
2. Abrir telas principais MineColonies/Structurize e navegar widgets.
3. Buttons: uma ação → um packet/settlement.
4. Text input com valores válidos, vazios, longos e caracteres especiais.
5. Scroll/drag em diferentes GUI scales.
6. Resource reload com tela fechada e aberta quando suportado.
7. Alvo de Window removido/descarregado durante uso.
8. Dois jogadores alterando o mesmo objeto por UI.
9. Logout/dimension change sem callback/listener órfão.
10. Atualização futura: validar BlockUI e consumers como conjunto, não isoladamente.

## 18. Evidência
- modlist física atual: BlockUI 1.0.211 snapshot, MineColonies 1.1.1377, Structurize 1.0.832 e Multi-Piston 1.2.58;
- CurseForge oficial: BlockUI XML-based UI system, backing Window, images, buttons/handlers, text input, scroll/drag screens;
- documentação oficial MineColonies/BlockUI sobre o modelo XML + Window;
- arquivo oficial `blockui-1.0.211-1.21.1-snapshot.jar` e material de changelog da build.

> 🖥️ Authority canônica: BlockUI = estrutura/apresentação/callback framework; MineColonies e outros consumers = gameplay/data reais. Um widget nunca é fonte de verdade por si só.
