# Sophisticated JEI Index

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d28537c47b84722bb4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sophisticated_jei_index-1.2.2+1.21.1.jar`, mod id `sophisticated_jei_index`, runtime `1.2.2`; Sophisticated Backpacks 3.26.2 e redes/JEI relevantes presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sophisticated JEI Index 1.2.2 e seus providers físicos citados estão presentes. A página também registra upstream 1.2.3 de 10/09/2026 como update disponível; isso não altera a versão instalada. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sophisticated JEI Index
- **Arquivo JAR:** `sophisticated_jei_index-1.2.2+1.21.1.jar`
- **Versão 1.21.1:** 1.2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, QoL, Armazenamento
- **Função:** Adiciona JEI Index Upgrade ao Sophisticated Backpacks e permite recipe transfer usando ingredientes dos backpacks equipados, inclusive em terminais de crafting suportados.
- **Dependências:** JEI 19.53.0.426 e Sophisticated Backpacks 3.26.2 estão presentes. Integrações físicas relevantes incluem AE2, Refined Storage e Tom's Storage; Beyond Dimensions não foi encontrado top-level. EMI só é path quando presente/configurado.
- **Sobreposição:** Não é outro recipe viewer; estende JEI/EMI recipe fill para inventories Sophisticated e terminais compatíveis.
- **Compatibilidade/Riscos:** Bridge de recipe transfer/index. Riscos: ingredient duplication/loss, source-priority mismatch, stale backpack index, terminal API drift, client/server recipe mismatch e cross-player inventory leakage. 1.2.2 física é Release; 1.2.3 upstream mais nova existe e deve ser tratada como update disponível, não como versão instalada.
- **Observações:** JAR físico `sophisticated_jei_index-1.2.2+1.21.1.jar`. O projeto prioriza fontes conforme contexto e integra terminais AE2/Refined Storage/Tom's Storage; recipe transfer precisa ser server-authoritative para evitar dupe/loss.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sophisticated JEI Index 1.2.2 e página atual do projeto + confirmação física de JEI/Sophisticated Backpacks e terminais suportados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-jei-index/files/8726946
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sophisticated JEI Index 1.2.2 reconstruído: JEI Index Upgrade, recipe transfer from equipped backpacks, source priority, crafting-terminal integrations, multiplayer authority, lifecycle, riscos e testes. Upstream 1.2.3 existe desde 10/09/2026, mas não está instalado.
- **Histórico da decisão:** Mantido para indexação/recipe transfer do ecossistema Sophisticated. Em 11/09/2026 o runtime físico continua 1.2.2; upstream 1.2.3, publicada em 10/09/2026 para 1.21.1, foi registrada apenas como atualização disponível.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticated_jei_index-1.2.2+1.21.1.jar`, mod id `sophisticated_jei_index`, versão `1.2.2`, NeoForge 1.21.1. Ele estende recipe transfer/indexação do JEI para **backpacks equipados** e terminais suportados; não substitui o JEI.

## 1. Identidade e papel
- **Mod:** Sophisticated JEI Index.
- **JAR:** `sophisticated_jei_index-1.2.2+1.21.1.jar`.
- **Mod id:** `sophisticated_jei_index`.
- **Versão:** `1.2.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Decisão vigente:** **Manter**.

## 2. Providers centrais
O stack físico contém:
- JEI `19.53.0.426`;
- Sophisticated Backpacks `3.26.2`.

JEI continua authority da recipe UI/transfer contract; Sophisticated Backpacks continua authority do inventory. O addon conecta os dois.

## 3. JEI Index Upgrade
O projeto adiciona um **JEI Index Upgrade** ao backpack. Com ele habilitado, recipe transfer pode considerar ingredientes disponíveis no backpack equipado além do inventory normal do player.

O upgrade deve controlar participação da mochila; um backpack sem upgrade/disabled não deve ser indexado apenas por estar equipado.

## 4. Recipe transfer
Ao transferir uma receita, o sistema precisa encontrar os ingredients requeridos nas fontes autorizadas e mover/consumir exatamente as quantidades necessárias.

A operação deve ser atômica o suficiente para não deixar metade da receita transferida com stacks duplicadas quando uma fonte muda durante o processo.

## 5. Ordem de seleção dos backpacks
A documentação atual informa que backpacks elegíveis são consultados respeitando sua ordem/seleção prevista pelo addon. Essa prioridade importa quando duas mochilas contêm o mesmo ingredient.

O dossiê não inventa uma ordem absoluta não publicada além do contrato de seleção do projeto; runtime deve confirmar qual slot/backpack é preferido no stack atual.

## 6. Player inventory + backpacks
Recipe transfer usa o inventory do player em conjunto com backpacks habilitados, não como substituição total.

O mesmo stack não pode ser contado duas vezes caso seja exposto por capability/view duplicada. Quantidade disponível precisa ser calculada por source real.

## 7. Crafting terminals
O projeto publica integração com crafting terminals de storage networks. A prioridade descrita é, em linhas atuais, usar recursos do terminal/network e complementar com inventory/backpacks conforme integration contract.

O resultado precisa respeitar ownership do terminal: o addon não pode extrair item que a network não autorizaria normalmente.

## 8. AE2
AE2 está fisicamente presente. Em terminal AE2 suportado, recipe fill precisa separar itens provenientes da network, player inventory e backpacks.

Se a network estiver sem quantidade suficiente, o backpack pode completar conforme a integração; nenhuma fonte deve ser debitada duas vezes.

## 9. Refined Storage
Refined Storage também está presente e é listado pelo projeto como terminal suportado.

A mesma regra se aplica: network extraction é authority do provider; Sophisticated JEI Index apenas coordena o preenchimento com fontes adicionais.

## 10. Tom's Storage
Tom's Storage está fisicamente presente e é uma integração publicada relevante para este pack.

Como o pack usa Tom's como sistema importante de storage, testar crafting terminal com ingredientes parcialmente na rede e parcialmente em backpacks é regression gate prioritário.

## 11. Beyond Dimensions
O projeto também menciona Beyond Dimensions em integrações de terminal. Nenhum JAR top-level desse provider foi encontrado no snapshot físico atual.

Portanto essa capacidade é upstream, **não integração runtime ativa confirmada** neste pack.

## 12. Shift-click / quantidade máxima
O projeto suporta transferências maiores/rápidas de recipe quando a UI do viewer solicita quantidade máxima compatível. A quantidade calculada precisa considerar todas as fontes sem oversubscription.

Um ingrediente usado em múltiplos slots do recipe não pode ser prometido duas vezes pela mesma stack.

## 13. Complete-set semantics
Recipe viewers trabalham com conjuntos completos de ingredientes. O addon precisa respeitar alternativas/tag ingredients e não escolher combinação que deixa o recipe incompleto apesar de haver outra combinação válida.

Isso é especialmente relevante em packs com muitos tags equivalentes de materiais.

## 14. EMI support
A documentação atual também descreve suporte a EMI, incluindo recipe fill/recipe tree. Esse path só é runtime quando EMI existe e, em multiplayer, o projeto exige o componente server-side correspondente.

Não foi atribuído como integração ativa apenas pela feature upstream; JEI é o viewer físico confirmado deste dossiê.

## 15. Client / server boundary
Cliente decide UI/intent de recipe transfer, mas servidor deve validar:
- inventories realmente acessíveis;
- quantidades;
- extraction/insertion;
- recipe/container state.

Um packet de transfer não pode confiar em snapshot client-side antigo para criar itens.

## 16. Multiplayer e ownership
Cada player deve indexar apenas backpacks que lhe pertencem/estão no contexto permitido. Inventories de outro player não podem aparecer como fonte por cache global ou equipment lookup incorreto.

Terminais com permissões próprias continuam subordinados ao provider da network.

## 17. Lifecycle
Validar:
- equip/unequip backpack;
- enable/disable/remover JEI Index Upgrade;
- mover ingredients durante recipe screen;
- abrir/fechar terminal;
- recipe reload;
- reconnect;
- dimension change;
- backpack replacement/upgrades;
- network online/offline.

Index/cache de uma mochila removida não pode continuar fornecendo ingredients.

## 18. Atualização upstream 1.2.3
Em **10/09/2026** foi publicada uma 1.2.3 para Minecraft 1.21.1. A modlist física atual continua em **1.2.2**.

Pela regra do projeto, presença/versão canônica permanece 1.2.2. A 1.2.3 é somente **update disponível**, a ser avaliado em outro fluxo antes de alteração da modlist.

## 19. Riscos técnicos
1. **Dupe/loss em transfer:** mesma stack debitada/inserida incorretamente.
2. **Double counting:** capability expõe o mesmo inventory duas vezes.
3. **Priority mismatch:** source errada é consumida primeiro.
4. **Stale index:** backpack removido continua contado.
5. **Terminal API drift:** AE2/RS/Tom's atualizam interface/capability.
6. **Cross-player leak:** inventory de outro jogador é indexado.
7. **Recipe mismatch:** client recipe/data diverge do servidor.
8. **Tag ambiguity:** alternativa inválida é escolhida.
9. **Update drift:** documentação 1.2.3 é aplicada indevidamente ao runtime 1.2.2.

## 20. Matriz de testes
- [ ] Cliente/servidor iniciam com JEI Index 1.2.2 + JEI + Sophisticated Backpacks.
- [ ] Backpack sem Index Upgrade não é usado como source.
- [ ] Backpack com upgrade fornece ingredient ao recipe transfer.
- [ ] Inventory + backpack completam recipe sem double counting.
- [ ] Dois backpacks com mesmo item seguem prioridade consistente.
- [ ] Shift/max transfer respeita quantidade real disponível.
- [ ] Ingredient por tag escolhe combinação válida.
- [ ] AE2 terminal combina network/player/backpack sem dupe.
- [ ] Refined Storage terminal idem.
- [ ] Tom's Storage terminal idem.
- [ ] Remover/equipar backpack invalida index imediatamente.
- [ ] Dois players não compartilham backpacks/index.
- [ ] Recipe reload/reconnect não deixa cache stale.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
- Modlist física atual: JEI Index 1.2.2, JEI 19.53.0.426, Sophisticated Backpacks 3.26.2 e redes relevantes.
- CurseForge oficial 1.2.2: build física Release NeoForge 1.21.1.
- Página oficial atual: backpack recipe transfer, terminal integrations e source/recipe-fill behavior.
- Upstream 1.2.3 de 10/09/2026: registrado apenas como atualização disponível.
- **Limite:** config local, EMI e detalhes internos de prioridade não foram inferidos além do publicado; testes runtime permanecem pendentes.
