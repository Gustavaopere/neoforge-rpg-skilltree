# Sophisticated JEI Index

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#515**: JAR `sophisticated_jei_index-1.2.3+1.21.1.jar`, mod id `sophisticated_jei_index`, runtime `1.2.3`, SHA-1 `0b32bc2fef551923b37356e747d6eca835e521d5`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated JEI Index
- **Arquivo JAR:** `sophisticated_jei_index-1.2.3+1.21.1.jar`
- **Versão 1.21.1:** 1.2.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, QoL, Armazenamento
- **Função:** Adiciona JEI Index Upgrade ao Sophisticated Backpacks e permite recipe transfer usando ingredientes dos backpacks equipados, inclusive em terminais de crafting suportados.
- **Dependências:** JEI 19.56.0.440 e Sophisticated Backpacks 3.26.3 estão presentes. Das integrações de terminal publicadas, Tom's Storage 2.4.2 está presente; AE2, Refined Storage e Beyond Dimensions não foram encontrados top-level no snapshot físico atual. EMI só é path quando presente/configurado.
- **Sobreposição:** Não é outro recipe viewer; estende JEI/EMI recipe fill para inventories Sophisticated e terminais compatíveis.
- **Compatibilidade/Riscos:** Bridge de recipe transfer/index. Riscos: ingredient duplication/loss, source-priority mismatch, stale backpack index, terminal API drift, client/server recipe mismatch e cross-player inventory leakage. A build física 1.2.3 é Release; a página oficial do arquivo não publica changelog, portanto nenhum delta interno é atribuído por inferência.
- **Observações:** JAR físico `sophisticated_jei_index-1.2.3+1.21.1.jar`. O projeto publica suporte a terminais AE2/Refined Storage/Tom's Storage/Beyond Dimensions, mas no snapshot físico atual somente Tom's Storage 2.4.2 está presente entre esses providers; recipe transfer precisa ser server-authoritative para evitar dupe/loss.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + SHA-1 físico + CurseForge File ID 8848988 (Sophisticated JEI Index 1.2.3 para NeoForge 1.21.1) + confirmação física de JEI 19.56.0.440, Sophisticated Backpacks 3.26.3 e Tom's Storage 2.4.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-jei-index/files/8848988
- **Atualização/Status:** RECONCILIADO EM 27/09/2026 — autoridade física atual é Sophisticated JEI Index 1.2.3 (`sophisticated_jei_index-1.2.3+1.21.1.jar`). CurseForge File ID 8848988 confirma a release NeoForge 1.21.1; a página do arquivo não publica changelog, portanto nenhum delta interno é inferido.
- **Histórico da decisão:** Mantido para indexação/recipe transfer do ecossistema Sophisticated. A 1.2.3, publicada em 10/09/2026 para NeoForge 1.21.1, passou de update disponível a runtime físico efetivamente instalado no snapshot de 27/09/2026.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticated_jei_index-1.2.3+1.21.1.jar`, mod id `sophisticated_jei_index`, versão `1.2.3`, NeoForge 1.21.1. Ele estende recipe transfer/indexação do JEI para **backpacks equipados** e terminais suportados; não substitui o JEI.

## 1. Identidade e papel
- **Mod:** Sophisticated JEI Index.
- **JAR:** `sophisticated_jei_index-1.2.3+1.21.1.jar`.
- **Mod id:** `sophisticated_jei_index`.
- **Versão:** `1.2.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Decisão vigente:** **Manter**.
## 2. Providers centrais
O stack físico contém:
- JEI `19.56.0.440`;
- Sophisticated Backpacks `3.26.3`.
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
AE2 não foi encontrado top-level no snapshot físico atual. O suporte upstream permanece documentado, mas este path não é tratado como integração runtime ativa enquanto o provider estiver ausente.
Se a network estiver sem quantidade suficiente, o backpack pode completar conforme a integração; nenhuma fonte deve ser debitada duas vezes.
## 9. Refined Storage
Refined Storage também não foi encontrado top-level no snapshot físico atual. O suporte upstream permanece documentado, mas não é tratado como integração runtime ativa neste pack.
A mesma regra se aplica: network extraction é authority do provider; Sophisticated JEI Index apenas coordena o preenchimento com fontes adicionais.
## 10. Tom's Storage
Tom's Storage `2.4.2` está fisicamente presente e é a integração de terminal publicada relevante ativa neste snapshot do pack.
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
## 18. Runtime atual 1.2.3
Em **10/09/2026** foi publicada a 1.2.3 para Minecraft 1.21.1; no snapshot físico atual de 27/09/2026 essa é a versão efetivamente instalada.
Pela regra do projeto, a autoridade física fixa a versão canônica atual em **1.2.3**. A página oficial do arquivo não publica changelog; portanto nenhum comportamento, fix ou alteração interna é atribuído à 1.2.3 por inferência.
## 19. Riscos técnicos
1. **Dupe/loss em transfer:** mesma stack debitada/inserida incorretamente.
2. **Double counting:** capability expõe o mesmo inventory duas vezes.
3. **Priority mismatch:** source errada é consumida primeiro.
4. **Stale index:** backpack removido continua contado.
5. **Terminal API drift:** AE2/RS/Tom's atualizam interface/capability.
6. **Cross-player leak:** inventory de outro jogador é indexado.
7. **Recipe mismatch:** client recipe/data diverge do servidor.
8. **Tag ambiguity:** alternativa inválida é escolhida.
9. **Version drift:** documentação histórica 1.2.2 ou futura de outra linha é aplicada indevidamente ao runtime físico 1.2.3.
## 20. Matriz de testes
- [ ] Cliente/servidor iniciam com JEI Index 1.2.3 + JEI 19.56.0.440 + Sophisticated Backpacks 3.26.3.
- [ ] Backpack sem Index Upgrade não é usado como source.
- [ ] Backpack com upgrade fornece ingredient ao recipe transfer.
- [ ] Inventory + backpack completam recipe sem double counting.
- [ ] Dois backpacks com mesmo item seguem prioridade consistente.
- [ ] Shift/max transfer respeita quantidade real disponível.
- [ ] Ingredient por tag escolhe combinação válida.
- [ ] Se AE2 for reintroduzido, terminal combina network/player/backpack sem dupe.
- [ ] Se Refined Storage for reintroduzido, terminal combina network/player/backpack sem dupe.
- [ ] Tom's Storage 2.4.2 combina network/player/backpack sem dupe.
- [ ] Remover/equipar backpack invalida index imediatamente.
- [ ] Dois players não compartilham backpacks/index.
- [ ] Recipe reload/reconnect não deixa cache stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 21. Evidências e limites
- Modlist física atual de 27/09/2026: JEI Index 1.2.3, JEI 19.56.0.440, Sophisticated Backpacks 3.26.3 e Tom's Storage 2.4.2; AE2 e Refined Storage não estão top-level.
- CurseForge File ID 8848988: build física 1.2.3 Release NeoForge 1.21.1.
- Página oficial atual: backpack recipe transfer, terminal integrations e source/recipe-fill behavior.
- Página oficial 1.2.3 de 10/09/2026: runtime atual confirmado; arquivo sem changelog, sem inferência de delta interno.
- **Limite:** config local, EMI e detalhes internos de prioridade não foram inferidos além do publicado; testes runtime permanecem pendentes.

## 22. Revisão upstream 1.2.4 — 10/10/2026

**Instalado conforme snapshot físico:** `sophisticated_jei_index-1.2.3+1.21.1.jar` / 1.2.3. A atualização pública para **NeoForge Minecraft 1.21.1** é **`sophisticated_jei_index-1.2.4+1.21.1.jar`**, de **07/10/2026**, CurseForge file **9089418**. É a próxima versão pública após 1.2.3, sem etapa intermediária localizada.

### Evidência/limite
A página oficial de 1.2.4 traz explicitamente **“File has no changelog”**. Portanto, **não é possível atribuir uma correção de recipe transfer, performance ou compatibilidade específica a 1.2.4**. Mudanças em Sophisticated Backpacks, JEI ou outro consumer não são automaticamente mudanças do JEI Index. Se for necessário saber o delta interno para liberação, comparar source/tag ou JARs 1.2.3 vs 1.2.4.

### Gate de integração
A stack física conhece JEI 19.56.0.440, Sophisticated Backpacks 3.26.3 e Tom's Storage 2.4.2; AE2/Refined Storage/Beyond Dimensions não foram confirmados top-level. Testar transfer de receitas com ingredientes distribuídos entre mochila e inventário; prioridade entre duas mochilas; backpack equip/unequip; max transfer; recipes por tags; Tom's crafting terminal; dois usuários e reload/reconnect. A revisão upstream de Backpacks para 3.26.9 ainda não foi promovida fisicamente; não inferir compatibilidade futura pela mera co-publicação.

**Fonte oficial:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-jei-index/files/9089418

**Estado:** 1.2.4 publicada, não instalada no snapshot; nenhum smoke test realizado.

