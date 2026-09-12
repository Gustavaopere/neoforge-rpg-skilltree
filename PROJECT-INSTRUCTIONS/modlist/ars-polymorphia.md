# Ars Polymorphia

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cca4b2e748d8c0dfbb
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Polymorphia
- **Arquivo JAR:** `ars_polymorphia-1.0.3.jar`
- **Versão 1.21.1:** 1.0.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Compat, QoL
- **Função:** Adapter Polymorph-compatible para o Storage/Crafting Lectern do Ars Nouveau: enumera receitas conflitantes, exibe selector no terminal e aplica a escolha validada server-side sem criar recipes ou storage próprios.
- **Dependências:** Ars Nouveau 5.13.1 + provider Polymorph-compatible físico Polymorph+ 1.3.1+1.21.1. Não instalar segundo Polymorph original em paralelo.
- **Sobreposição:** Compartilha a authority Polymorph-compatible com adapters de outros providers quando esses providers estão presentes, mas cobre especificamente o Storage/Crafting Lectern Ars. No snapshot atual AE2 está ausente; não criar segundo resolver sobre o mesmo menu nem instalar Polymorph original em paralelo ao Polymorph+.
- **Compatibilidade/Riscos:** Riscos: mixins acoplados a internals do Crafting Lectern/Menu/Screen, drift de API Polymorph+, seleção stale após datapack reload, output ghost/client desync e contaminação entre jogadores se player-specific state falhar.
- **Observações:** mod id `ars_polymorphia`, runtime 1.0.3. Adapter específico do Storage/Crafting Lectern; AE2 está ausente do snapshot atual e nenhum resolver AE2 é contado como superfície ativa.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source oficial Vonr/Ars-Polymorphia 1.0.3 + Polymorph+ 1.3.1 físico e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ars_polymorphia-1.0.3.jar` / `1.0.3`; sem divergência física.
- **Fonte:** https://github.com/Vonr/Ars-Polymorphia
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #48: `ars_polymorphia-1.0.3.jar` / `1.0.3` conferidos contra a modlist atual; adapter Polymorph+ e estado técnico preservados.
- **Histórico da decisão:** Manter. Ficha reconstruída em 07/09/2026 contra o source 1.0.3 e o provider físico atual Polymorph+; confirmado que é adapter específico do Storage Lectern, não Ars Morph nem segundo recipe resolver.
- **Data da última decisão:** 2026-09-07

> 🧩 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_polymorphia-1.0.3.jar`, mod id `ars_polymorphia`, NeoForge 1.21.1. O próprio source 1.0.3 declara seu escopo: **adicionar suporte Polymorph ao Storage Lectern/Crafting Terminal do Ars Nouveau**. No pack atual, o provider de conflitos instalado é **Polymorph+ 1.3.1+1.21.1**, implementação substituta compatível com o ecossistema Polymorph. Ars Polymorphia não cria recipes nem storage novo; ele adapta seleção de recipes conflitantes ao terminal Ars.

## 1. Identidade e versão
- **Mod:** Ars Polymorphia.
- **JAR:** `ars_polymorphia-1.0.3.jar`.
- **Runtime:** `1.0.3`.
- **Mod id:** `ars_polymorphia`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Source oficial:** `Vonr/Ars-Polymorphia`, branch `master`, `mod_version=1.0.3`.
- **Decisão:** **Manter**.

## 2. Escopo exato
A função é estreita e operacional:
1. interceptar alterações na crafting matrix do **Crafting Lectern/Storage Lectern**;
2. enumerar todos os `CraftingRecipe` válidos para os ingredientes atuais;
3. expor esses resultados ao sistema Polymorph;
4. mostrar widget de seleção no terminal Ars;
5. preservar/aplicar a receita escolhida pelo jogador;
6. recalcular o resultado no servidor após seleção.

Não adiciona conteúdo mágico, recipe próprio, storage próprio ou segunda recipe manager.

## 3. Integração server-side no Crafting Lectern
O `CraftingLecternTileMixin` injeta em `onCraftingMatrixChanged(UUID)`.

Fluxo confirmado:
- obtém o servidor atual;
- resolve o player pelo UUID;
- exige `ServerLevel`;
- consulta `PolymorphApi.getPlayerRecipeData(player)`;
- lê a receita previamente selecionada;
- consulta `RecipeManager.getRecipesFor(RecipeType.CRAFTING, craftingInv, level)`;
- ignora resultados vazios;
- transforma as opções em `RecipePair(recipe id, result)`;
- se a seleção anterior ainda corresponde a uma opção válida, ajusta `currentRecipe`;
- envia lista/opção selecionada ao cliente pelos packets Polymorph.

A autoridade da receita continua no servidor.

## 4. Widget do Crafting Terminal
`CraftingTerminalWidget` estende `PlayerRecipesWidget` do Polymorph e é anexado à tela de storage/crafting Ars.

Superfícies confirmadas:
- botão selector;
- indicação de output atual;
- sprites próprios do addon;
- ao selecionar um recipe id válido, dispara `reset_crafting_result` para o servidor.

A UI é apenas escolha/apresentação; não deve escrever `currentRecipe` de forma autoritativa no cliente.

## 5. Packet serverbound — reset_crafting_result
`PacketResetCraftingResult` valida o contexto no servidor:
- o menu aberto precisa ser `CraftingTerminalMenu`;
- o tile associado precisa ser `CraftingLecternTile`;
- obtém a crafting matrix específica do UUID do jogador;
- pergunta ao recipe manager do Polymorph pela receita válida escolhida;
- só continua se houver recipe;
- atualiza `currentRecipe` via accessor;
- reexecuta `onCraftingMatrixChanged`;
- registra a seleção no `PlayerRecipeData` do Polymorph.

Isso evita confiar simplesmente em um output arbitrário enviado pelo cliente.

## 6. Persistência lógica da seleção
A escolha é mantida no **player recipe data do Polymorph** e reaproveitada quando ainda corresponde a uma receita válida para a matriz. Mudando ingredientes ou tornando a receita inválida, o conjunto é recalculado.

Não criar cache paralelo no Ars/Skill Tree; isso criaria duas authorities de seleção.

## 7. Mixin/accessor footprint
A árvore 1.0.3 contém mixins/accessors direcionados a:
- `CraftingLecternTile`;
- `CraftingTerminalMenu`;
- `CraftingTerminalScreen`;
- `StorageTerminalMenu`.

Logo a compatibilidade é explicitamente acoplada às superfícies de storage/crafting do Ars. Updates de Ars Nouveau que mudem esses internals exigem revalidação.

## 8. Provider Polymorph no pack atual
A dependência source original referencia API do **Polymorph**. O pack físico, porém, contém **Polymorph+ 1.3.1+1.21.1**, que é o resolver substituto compatível com o ecossistema Polymorph e deve ocupar essa única authority de conflitos de receita.

Regra:
- **Polymorph+/Polymorph API:** authority de recipe conflict/selection.
- **Ars Nouveau:** authority de Storage Lectern, crafting inventory e execução do craft.
- **Ars Polymorphia:** adapter entre ambos.

Não instalar/ativar um segundo Polymorph original em paralelo apenas para esta bridge.

## 9. Relação com outros resolvers do pack
Ars Polymorphia cobre especificamente o terminal Ars. Outros adapters Polymorph-compatible podem coexistir quando seus respectivos providers estão instalados, desde que adaptem menus distintos para a mesma authority de conflitos.

**Snapshot atual:** AE2 está ausente da modlist física; portanto nenhuma superfície ativa de resolução de conflitos AE2 é contabilizada neste pack. O risco aparece se dois resolvers independentes disputarem o **mesmo menu/output**.

## 10. O que o mod NÃO faz
- não altera recipes globais;
- não adiciona crafting outcomes;
- não converte Source;
- não cria mana;
- não integra Identity/morph de entidades;
- não é Ars Morph;
- não modifica spell casting;
- não substitui Storage Lectern.

## 11. Lifecycle/reload
Como a lista é obtida do `RecipeManager` do `ServerLevel` na mudança da matriz, datapack/reload pode alterar o conjunto de recipes disponível. Após reload, validar que:
- opções refletem o recipe manager atual;
- seleção antiga inválida não força recipe removido;
- clientes recebem a lista nova;
- nenhum output ghost permanece no terminal.

## 12. Multiplayer
A crafting inventory é obtida por `player UUID`; seleção é player-specific via Polymorph API. Testar dois jogadores usando o mesmo lectern simultaneamente para garantir que uma seleção não contamine a outra.

## 13. Riscos
1. mixin quebrar após alteração interna do Ars Nouveau;
2. API incompatível se Polymorph+ deixar de manter o contrato esperado;
3. receita selecionada stale após datapack reload;
4. output ghost/client desync;
5. seleção de um jogador afetar outro por cache incorreto;
6. dois recipe resolvers disputarem o mesmo menu;
7. client packet aceito sem recipe válida — mitigado pela validação server-side observada.

## 14. Matriz de validação
- boot client + dedicated server;
- Ars Nouveau 5.13.1 + Ars Polymorphia 1.0.3 + Polymorph+ 1.3.1;
- dois recipes com ingredientes idênticos no Storage Lectern;
- widget listar todas as opções;
- trocar seleção e craftar exatamente o escolhido;
- repetir craft mantendo ingredientes;
- mudar ingredientes e validar reset/recompute;
- remover recipe via datapack/reload;
- dois jogadores no mesmo lectern;
- fechar/reabrir terminal;
- reconnect/server restart;
- confirmar ausência de segundo resolver Polymorph original.

## 15. Fontes
- Modlist física do projeto, 07/09/2026.
- Guia consolidado de Magia do projeto.
- Source oficial `Vonr/Ars-Polymorphia` 1.0.3: `gradle.properties`, `CraftingLecternTileMixin`, `CraftingTerminalWidget`, `PacketResetCraftingResult`.
- Provider físico atual: Polymorph+ 1.3.1+1.21.1.
