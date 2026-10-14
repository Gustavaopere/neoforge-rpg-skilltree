# KubeJSCurios

## Propriedades do registro

- **Mod:** KubeJSCurios
- **Arquivo JAR:** kubejs_curios_neoforge_1.21.1-1.0.4.jar
- **Versão 1.21.1:** 1.0.4
- **Categoria:** Compat, Automação, RPG
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-curios/files/6825089
- **Função:** Integra Curios ao KubeJS, expondo comportamento de curios items, atributos/loot/context checks e registro de renderers customizados por scripts.
- **Dependências:** KubeJS 2101.7.2-build.377 + Curios 9.5.1+1.21.1 estão fisicamente presentes. NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Riscos: duplicate attribute modifiers, looting/fortune stacking, renderer/pose conflicts, client authority leakage e API drift KubeJS/Curios. Scripts reais precisam ser auditados antes de atribuir comportamento concreto.
- **Sobreposição:** Complementa Curios/KubeJS; não cria slots independentes. Pode interagir com outros addons de acessórios/modifiers e animation/render stack.
- **Observações:** JAR físico `kubejs_curios_neoforge_1.21.1-1.0.4.jar`, mod id `kubejs_curios`, runtime 1.0.4. Release NeoForge 1.21.1 de 29/07/2025.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge oficial KubeJS Curios 1.0.4 e documentação pública do projeto.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — KubeJSCurios 1.0.4/JAR físico reconfirmado; matriz física reconciliada com KubeJS 2101.7.2-build.377 e Curios 9.5.1+1.21.1. Corpo técnico preservado.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #364: JAR `kubejs_curios_neoforge_1.21.1-1.0.4.jar`, mod id `kubejs_curios`, runtime `1.0.4`, SHA-1 `d76c4181e1436bc85f108c7191f1ed7ee374d318`.

> **ESCOPO CANÔNICO.** Runtime documentado: `kubejs_curios_neoforge_1.21.1-1.0.4.jar`, mod id `kubejs_curios`, versão `1.0.4`. É uma integração Curios ↔ KubeJS; não cria inventário independente de acessórios.
## 1. Superfície de item Curios
A documentação oficial mostra builders/hooks KubeJS para comportamento de curios items: modificadores de atributos, regras de drop, fortune/looting adjustments e checks contextuais como neutralidade de piglins, powdered snow e Enderman mask.
Essas funções operam sobre o Curios provider e o item/script; não transferem ownership dos slots para KubeJSCurios.
## 2. Renderização client-side
`CuriosJSEvents.registerRenderer` permite registrar/remover renderers customizados para items. O callback recebe contexto de render, pose stack, entity/slot context e buffers. Isso é estritamente apresentação; nunca deve conceder stats ou gameplay state.
## 3. Curios authority
Curios 9.5.1+1.21.1 está fisicamente presente. Curios continua authority de slots, equip/unequip e inventory state. KubeJSCurios expõe esse contrato a scripts e adiciona callbacks/renderer glue.
## 4. Script lifecycle
Render registration pertence ao client lifecycle e pode ser reconstruído em reload. Comportamentos autoritativos de item precisam executar no side adequado. Handlers devem ser limpos/recarregados sem duplicação.
## 5. Atributos e loot
Scripts podem modificar atributos e influenciar looting/fortune por item equipado. Esses hooks são economicamente sensíveis: duas integrações aplicando o mesmo bônus podem produzir stacking inesperado. UUID/identity de modifiers deve permanecer estável quando a API assim exigir.
## 6. Riscos
1. Renderer custom quebra pose/animation stack.
2. Client callback usado como gameplay authority.
3. Attribute modifier duplica após equip/reload.
4. Looting/fortune stacking altera economia.
5. Script assume slot inexistente ou Curios API diferente.
6. Update KubeJS/Curios quebra wrappers.
## 7. Boundary para quests/perks
Item equipado deve ser lido pelo state Curios server-side; render visível não prova equip. Perks não devem replicar modifiers já aplicados pelo script/Curios item.
## 8. Matriz de testes
- [ ] Dedicated server inicia com KubeJSCurios 1.0.4 + KubeJS build.377 + Curios 9.5.1.
- [ ] Equip/unequip atualiza state uma vez.
- [ ] Modificadores não acumulam após relog/reload.
- [ ] Fortune/looting respeitam contexto e não double-stack.
- [ ] Renderer custom aparece apenas client-side e não quebra dedicated server.
- [ ] Pose permanece correta com animation mods do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências e limite
CurseForge oficial confirma Release 1.0.4 NeoForge 1.21.1 e documenta hooks de item/atributos/loot e `CuriosJSEvents.registerRenderer`. Scripts reais do pack não foram lidos; nenhum trinket/renderer custom é atribuído ao runtime sem localizar o script.


## 10. Revalidação de API e eventos por source 1.0.4 — 13/10/2026

**Estado físico histórico do pack:** o último inventário `modlist(1).txt` com 587 entradas registra **KubeJS #363 `2101.7.2-build.377`**, **KubeJSCurios #364 `1.0.4`** (`kubejs_curios_neoforge_1.21.1-1.0.4.jar`, mod ID `kubejs_curios`), **Curios #211 `9.5.1+1.21.1`** e NeoForge #001 `21.1.250`. O SHA-1 histórico do KubeJSCurios é `d76c4181e1436bc85f108c7191f1ed7ee374d318`. Não se leu o JAR físico ou scripts ativos nesta etapa; os campos de instalação são históricos, não certificação de novo estado em disco.

**Release mais recente compatível identificada:** [CurseForge oficial KubeJS Curios — listagem Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/kubejs-curios/files/all?page=1&version=1.21.1) confirma **`kubejs_curios_neoforge_1.21.1-1.0.4.jar`**, release de **29/07/2025**, [File ID `6825089`](https://www.curseforge.com/minecraft/mc-mods/kubejs-curios/files/6825089). Não há promoção de versão para este loader. Página do autor: [CurseForge, projeto `1255211`](https://www.curseforge.com/minecraft/mc-mods/kubejs-curios).

**Fonte técnica auditada:** [`zhaijineet/KubeJS-Curios`, branch `1.21.1`, commit `a0139c3819ee49083375c6da9197ea6fc14b8bc9`](https://github.com/zhaijineet/KubeJS-Curios/tree/a0139c3819ee49083375c6da9197ea6fc14b8bc9). O `gradle.properties` fixa `mod_version=1.0.4`, Minecraft `1.21.1`, `mod_id=kubejs_curios`, NeoForge de compilação `21.1.168`, KubeJS de compilação **`2101.7.1-build.181`** e Curios de compilação **`9.4.2+1.21.1`**. O último inventário físico contém versões posteriores de KubeJS, Curios e NeoForge. **Essas diferenças são drift de ambiente de compilação, não prova de incompatibilidade nem garantia de retro/forward-compat.** Não foi feita comparação binária exata do JAR histórico com o commit do source.

### 10.1. Helper injetado em LivingEntity e diferença entre consultas e alterações

No source [`LivingEntityMixin.java`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/mixin/LivingEntityMixin.java), `LivingEntity` implementa [`KubeJSCuriosHelper`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/curios/KubeJSCuriosHelper.java), que delega ao Curios:
- **Consultas:** `getCuriosRegistrySlot(String)`, `getCuriosRegistrySlots()`, `getEntityCuriosRegistrySlots()`, `getCurioCapability(ItemStack)`, `getEquippedCurios()`, `isCuriosEquipped(Item)`/`isCuriosEquipped(Predicate<ItemStack>)`, `findFirstCurio(...)`, `findCurios(...)`, `findCurio(String, int)`.
- **Acesso por nome de slot:** `getCuriosStacksHandler(String)`; não presumir existência de `ring`, `back`, `head` nem outro slot sem consultar runtime e provider.
- **Mutação:** `setEquippedCurio(String, int, ItemStack)`, `addCuriosSlotModifier(String, ResourceLocation, double, Operation)`, `removeCuriosSlotModifier(String, ResourceLocation)`, `clearCuriosSlotModifiers()`.

**Precaução efetiva do código:** `getCuriosInventory()` chama `CuriosApi.getCuriosInventory(...).orElseThrow(IllegalStateException)`; **não retorna Optional vazio** quando a capability falta. Scripts e perks devem usar guardas de pré-condição antes da chamada e executar alteração de inventário/modificadores somente no lado autorizado. `addCuriosSlotModifier` delega a **`addPermanentSlotModifier`**, não a um efeito efêmero por hit: registrar identidade de modifier estável, reset explícito e compatibilidade com save/relog. O source não prova que todo script existente do pack usa esses métodos.

### 10.2. EventGroup real, nomes de hooks e side

Em [`KubeJSCuriosEvents.java`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/kubejs/KubeJSCuriosEvents.java), o grupo é **`CuriosJSEvents`**, com os seguintes hooks:
- **CLIENT:** `registerRenderer`.
- **COMMON:** `attributeModifier`, `canEquip`, `canUnequip`, `slotModifiersUpdated`.
- **SERVER:** `change`, `drops`, `dropRules`.

O [`EventHandler.java`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/event/EventHandler.java) faz o bridge para eventos nativos Curios e define o ScriptType CLIENT/SERVER nas chamadas COMMON. `drops` oferece resultado capaz de cancelar o evento nativo quando o handler retorna a condição correspondente; logo, **não tratar hooks de drops como apenas cosméticos**. [`KubeJSCuriosEventJS.java`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/kubejs/KubeJSCuriosEventJS.java) mostra que `attributeModifier` disponibiliza `addModifier`, `removeModifier`, `removeAttribute`, `clearModifiers`, usando uma cópia `LinkedHashMultimap` apenas na primeira tentativa de edição. Os nomes e a estrutura dos eventos são referentes à **versão de source 1.0.4**, não aliases genéricos de KubeJS para qualquer versão.

### 10.3. Builder de capacidades e registros de renderer

O [`KubeJSCuriosPlugin.java`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/kubejs/KubeJSCuriosPlugin.java) registra os bindings globais `CuriosJSCapabilityBuilder` e `CuriosApi`. A classe [`CapabilityCurios.java`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/curios/CapabilityCurios.java) fornece builder com `curioTick`, `onEquip`, `onUnequip`, `canEquip`, `canUnequip`, `addAttribute`, `modifyAttribute`, `modifyFortuneLevel`, `modifyLootingLevel`, `canDrop`, `makesPiglinsNeutral`, `canWalkOnPowderedSnow` e `isEnderMask`. O exemplo oficial combina `StartupEvents.registry('item', ...)` ou `ItemEvents.modification(...)` com `attachCuriosCapability(CuriosJSCapabilityBuilder.create()...)`. **O catálogo não executou nem instalou tal script.**

O [`loadRegister()`](https://github.com/zhaijineet/KubeJS-Curios/blob/a0139c3819ee49083375c6da9197ea6fc14b8bc9/src/main/java/net/zhaiji/kubejscurios/kubejs/KubeJSCuriosEventJS.java) limpa e repopula a registry de renderers a partir de um snapshot e depois dispara `CuriosJSEvents.registerRenderer`. Isso é caminho **client-side** e depende da ordem de registro/reload; dois bridges visuais sobre o mesmo item podem gerar sobreposição, mas nenhum conflito real foi reproduzido. Testar especialmente com Epic Fight x Curios Compat **#259**, Supplementaries **#536**, Sophisticated Backpacks **#516** e outras bridges de equipagem.

### 10.4. Implicações para perks e matriz de regressão — PENDENTE

Para perks que concedam bônus enquanto acessórios estão equipados, a leitura do slot pelo Curios no servidor é o **predicado**; o cálculo e a persistência do bônus devem pertencer ao sistema de perks/consumer, respeitando a aplicação nativa do item e evitando modificadores duplicados. `slotModifiersUpdated` e `change` podem servir de pontos de invalidação de cache ou reconciliação, **após validar semântica exata e ordem de execução no runtime**. Não usar `registerRenderer` como gatilho para conceder atributo, alterar item ou salvar progresso.

Testes recomendados **não executados**: (1) boot dedicated server KubeJS build.377 + Curios 9.5.1; (2) **entidade sem inventário Curios**: verificar o `IllegalStateException` de `getCuriosInventory()`; **separadamente**, item sem `ICurio` em `getCurioCapability(ItemStack)` deve retornar `Optional.empty()` sem lançar essa exceção; (3) equip/unequip, slot inexistente e slot inativo; (4) adição/remoção de modifier permanente com IDs estáveis e equip múltiplo; (5) reload KubeJS, datapack e resource pack sem double-registration; (6) death, relog, dimension change e restart; (7) `drops` e `dropRules` sem dupe; (8) dois jogadores com acessórios diferentes, com client render versus server-authoritative effect; (9) visualização junto à bridge Epic Fight e efeitos/looting de consumers reais; (10) avaliar scripts efetivamente presentes antes de alterar gameplay.

**Decisão:** permanece **`Manter`**, conforme propriedade preexistente. Esta auditoria é atualização documental e identificação de contratos/riscos; não corresponde a nova decisão formal, upgrade, teste runtime ou instalação física.
