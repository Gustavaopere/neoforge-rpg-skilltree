# KubeJSDelight

## Propriedades do registro

- **Mod:** KubeJSDelight
- **Arquivo JAR:** kubejsdelight-1.1.6.jar
- **Versão 1.21.1:** 1.1.6
- **Categoria:** Compat, Automação, Comida
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-delight
- **Função:** Integra Farmer's Delight ao KubeJS para registrar knives/pies/feasts custom e criar recipes de Cutting Board/Cooking Pot por scripts.
- **Dependências:** O snapshot de 25/09/2026 registra Farmer's Delight 1.3.4 e KubeJS 2101.7.2-build.377; presença atual dos JARs não revalidada nesta etapa. NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Riscos: registry migration de items custom, servings/food duplication, recipe conflicts/reload e API drift KubeJS/Farmer's Delight. A apresentação do arquivo no CurseForge contém prefixo 1.21.1, mas o campo oficial `File name` é exatamente igual ao filename do snapshot (`kubejsdelight-1.1.6.jar`).
- **Sobreposição:** Complementa Farmer's Delight/KubeJS; recipes/scripts podem tocar as mesmas rotas que outros addons culinários, mas isso é composição configurável.
- **Observações:** Runtime físico KubeJSDelight 1.1.6 permanece atual para NeoForge 1.21.1. O core KubeJS físico está em build.377.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge/Modrinth oficiais KubeJS Delight 1.1.6 + KubeJS Wiki Farmer's Delight.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — KubeJSDelight 1.1.6/JAR físico reconfirmado; matriz física reconciliada com KubeJS 2101.7.2-build.377 e Farmer's Delight 1.3.4. Custom content/recipes, lifecycle e riscos preservados.
- **Data da última decisão:** 2026-09-06

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #367: JAR `kubejsdelight-1.1.6.jar`, mod id `kubejsdelight`, runtime `1.1.6`, SHA-1 `66a5de83677d38828242ab3dc96bf1b6f2771198`.

> **ESCOPO CANÔNICO.** No snapshot físico de 25/09/2026 consta `kubejsdelight-1.1.6.jar`, mod ID `kubejsdelight` e versão `1.1.6`. O addon conecta builders/recipes do Farmer's Delight ao KubeJS; não institui sistema alimentar paralelo. O JAR atual em disco não foi reaberto nesta auditoria.
## 1. Conteúdo custom documentado
A integração permite registrar por startup scripts:
- custom knives;
- custom pies;
- custom feasts.
A documentação 1.21.1 usa builders específicos do Farmer's Delight e permite definir propriedades como servings do feast.
## 2. Recipes Farmer's Delight
Server scripts podem criar recipes de **Cutting Board** e **Cooking Pot**. Esses recipes continuam executados pelo provider Farmer's Delight; KubeJSDelight apenas expõe builders/integração ao script.
## 3. Script lifecycle
Items/blocks custom pertencem a `startup_scripts`; recipes pertencem a `server_scripts`. Essa separação evita registrar conteúdo depois do registry freeze e permite `/reload` dos recipes sem recriar item/block IDs.
## 4. Runtime atual
O snapshot físico de 25/09/2026 registra Farmer's Delight 1.3.4 e KubeJS build.377, sem nova inspeção local nesta etapa. A release pública 1.1.6 é NeoForge 1.21.1. O título da release no CurseForge inclui `1.21.1`, porém o campo **File name** do arquivo oficial #8192573 é `kubejsdelight-1.1.6.jar`, igual ao snapshot histórico; não há divergência real de filename documentada pela publicação.
## 5. Food/serving authority
Farmer's Delight continua authority de Cutting Board/Cooking Pot e comportamento de food/feast. Scripts podem definir conteúdo custom, mas effects/nutrition/servings precisam ser tratados como dados do item/recipe efetivamente registrado.
## 6. Riscos
1. Registry ID de knife/pie/feast muda em mundo existente.
2. Feast servings configuradas incorretamente causam consumo/dupe.
3. Cutting/Cooking recipes duplicam após reload.
4. Recipe custom conflita com outro datapack/addon culinário.
5. KubeJS/FD update quebra builder API.
6. Quest assume alimento custom que não existe nos scripts atuais.
## 7. Boundary para quests/perks
Comer/servir/cortar só deve contar a partir do evento/state real do provider, não da simples existência da recipe no JEI. Conteúdo custom só entra em quests depois que seu registry ID estiver comprovado nos scripts/runtime.
## 8. Matriz de testes
- [ ] Dedicated server inicia com KubeJSDelight 1.1.6 + FD 1.3.4 + KubeJS build.377.
- [ ] Custom knife registra uma vez e preserva tier/comportamento.
- [ ] Pie/feast servings não duplicam itens.
- [ ] Cutting Board recipe consome/produz exatamente o esperado.
- [ ] Cooking Pot recipe mantém inputs/containers corretos.
- [ ] `/reload` não duplica recipes.
- [ ] Conteúdo custom persiste por registry ID após restart.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências e limite
CurseForge/Modrinth e KubeJS Wiki confirmam Release 1.1.6, custom knives/pies/feasts e recipes Cutting Board/Cooking Pot. Revalidação externa em 13/09/2026 mantém `1.1.6` como a release NeoForge 1.21.1 mais recente localizada. Scripts reais do pack não foram auditados; nenhum item/recipe custom é assumido ativo sem o script correspondente.

## 10. Auditoria NeoForge 1.21.1 / 1.1.6 — 14/10/2026

### 10.1. Identidade, lançamento, dependências e proveniência

**Snapshot físico histórico:** posição #367, `kubejsdelight-1.1.6.jar`, versão `1.1.6`, mod ID `kubejsdelight`, SHA-1 `66a5de83677d38828242ab3dc96bf1b6f2771198`, de 25/09/2026. O snapshot ordenado contém 587 top-level incluindo o modloader; modificações pós-snapshot estão anotadas à parte no índice. **O JAR de disco atual, sua metadata e seus scripts não foram inspecionados nesta etapa.**

**Release do autor:** [CurseForge oficial #8192573](https://www.curseforge.com/minecraft/mc-mods/kubejs-delight/files/8192573), publicada em **03/06/2026**, NeoForge Minecraft `1.21.1`, `Client & Server`, release `1.1.6`; o [índice de versões oficial](https://www.curseforge.com/minecraft/mc-mods/kubejs-delight/files/all) não apresenta release NeoForge 1.21.1 posterior. A página usa como título “kubejsdelight-1.21.1-1.1.6.jar”, mas seu campo **File name** registra **`kubejsdelight-1.1.6.jar`**. Portanto, **o filename real da distribuição coincide com o registrado no snapshot**. Changelog oficial 1.1.6: “upgraded to Farmer's Delight 1.3.2”; **não** indica que o pack foi migrado para 1.3.2.

**Source de autor fixado:** [QinomeD/kubejsdelight, branch `1.21.1`, commit `464ea8356bfcf7ac976688e9c81f1d355564dddf`](https://github.com/QinomeD/kubejsdelight/tree/464ea8356bfcf7ac976688e9c81f1d355564dddf). O [`build.gradle`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/build.gradle) fixa `version = "1.1.6"` e Java 21. O [`gradle.properties`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/gradle.properties) utiliza para compilação **KubeJS `2101.7.2-build.277`, NeoForge `21.1.219`, Farmer's Delight `1.3.2`** (Curse Maven `8083481`). O [`neoforge.mods.toml`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/resources/META-INF/neoforge.mods.toml) declara **KubeJS e Farmer's Delight obrigatórios, `BOTH`**, com ranges mínimos interpolados a partir dessas versões. O arquivo declara licença **GNU LGPL 3.0**, coerente com a página CurseForge.

**Drift do pack catalogado:** KubeJS #363 **2101.7.2-build.377**, Farmer's Delight #276 **1.3.4**, NeoForge #001 **21.1.250**. Todos excedem as versões de compilação registradas no source, porém **compatibilidade efetiva não foi testada**. A equivalência bytecode entre source público e artefato físico não foi comprovada. O [Modrinth oficial](https://modrinth.com/mod/kubejs-delight) apresenta o projeto como integração Farmer's Delight + KubeJS.

### 10.2. Entrypoints e recipes reais do source 1.1.6

O [`KubeJSDelightPlugin.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/KubeJSDelightPlugin.java) registra **dois schemas** (`farmersdelight:cutting` e `farmersdelight:cooking`), **três builders** (block `farmersdelight:pie`, block `farmersdelight:feast` e item `farmersdelight:knife`) e os componentes `chance_result`, `sound` e `cooking_pot_recipe_book_tab`; expõe binding/wrapper global `ChanceResult`. Trata-se de **superfícies para scripts**, não de receitas ou itens novos automaticamente presentes no perfil.

| Schema / API | Campos e contrato encontrados no código |
| --- | --- |
| [`CuttingRecipeJS.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/recipe/CuttingRecipeJS.java) | `ingredients` (lista de ingredientes), `tool` (ingrediente), `result` (lista de `ChanceResult`, role OUTPUT), `sound` opcional. |
| [`CookingRecipeJS.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/recipe/CookingRecipeJS.java) | `recipe_book_tab` (enum `CookingPotRecipeBookTab`), `ingredients` lista, `result` ItemStack, `experience` float opcional padrão `0`, `cookingtime` int opcional padrão `200`, `container` ItemStack opcional. |
| [`ChanceResultWrapper.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/recipe/wrapper/ChanceResultWrapper.java) | `ChanceResult.of(stack, chance)` registra chance explícita; conversão automática de item simples em `ChanceResult` aplica `chance=1` (100%). Entrada vazia/nula vira stack vazia de chance zero. |

A [Wiki KubeJS Farmer's Delight](https://kubejs.com/wiki/addons/farmers-delight) ilustra `StartupEvents.registry(...)` para builders e `ServerEvents.recipes(...)` para receitas. Para cooking, lista as tabs `meals`, `drinks` e `misc`. **Essas chamadas são exemplos externos**, não evidência de scripts ativos no modpack. Validar a sintaxe contra o script, KubeJS e JAR efetivos antes da adoção.

### 10.3. Knife, pie e feast: implementação, invariantes e riscos

- [`KnifeItemBuilder.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/item/custom/KnifeItemBuilder.java): estende `HandheldItemBuilder`, constrói `KnifeItem` e registra atributos via `KnifeItem.createAttributes(toolTier, attackDamageBaseline, speedBaseline)`, além de tags Farmer's Delight KNIVES e `c:tools/knife`. Tier, atributos e comportamento de corte exigem teste com scripts reais; não presumir atributo bonus de perks por mera definição da categoria.
- [`PieBlockBuilder.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/block/custom/PieBlockBuilder.java) expõe `sliceItem(ResourceLocation)` e `bites(int)`; padrões source `sliceItem=minecraft:air` e `bites=4`. [`BasicPieBlockJS`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/block/custom/BasicPieBlockJS.java) resolve o item de fatia no registry e retorna `getMaxBites`. Verificar ID de slice registrado e contagem de bites válida; um item ausente não deve contar como conteúdo consumível ativo.
- [`FeastBlockBuilder.java`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/block/custom/FeastBlockBuilder.java) expõe `servingsAmount(int)`, `servingItems(ResourceLocation[])` e `hasLeftovers(Boolean)`. O construtor inicia `servings=4`, mas **a propriedade `SERVINGS` só é inicializada ao chamar `servingsAmount(...)`**. [`BasicFeastBlockJS`](https://github.com/QinomeD/kubejsdelight/blob/464ea8356bfcf7ac976688e9c81f1d355564dddf/src/main/java/qinomed/kubejsdelight/block/custom/BasicFeastBlockJS.java) usa essa propriedade ao registrar o state padrão e lê `servingsList.get(0)` para o primeiro serving. **Condição de segurança antes de criar feasts:** invocar `servingsAmount` com quantidade válida e `servingItems` com array não vazio de IDs existentes; testar listas menores que o número de porções, estados de leftovers e save/reload. Os riscos de `null`/índice inválido são **inferências do código-fonte**; não foram reproduzidos no pack.
- `BasicFeastBlockJS.getServingItem` escolhe o índice conforme porções consumidas, limitando o índice superior ao último item configurado. Testar se diferentes servings e leftovers persistem sem conceder itens em duplicidade.
- **Autoridade funcional:** Farmer's Delight #276 executa Cutting Board/Cooking Pot e comportamento dos blocos/alimentos; KubeJS #363 gerencia eventos de script e registries; KubeJS Delight #367 fornece builders, wrappers e serializers. Outros datapacks culinários podem competir pelos mesmos IDs de receitas, mas coexistência não é por si só duplicação ou incompatibilidade.

### 10.4. Issue externa e matriz de regressão (nenhum teste executado)

A [issue oficial #16 — “Cutting recipes are created twice”](https://github.com/QinomeD/kubejsdelight/issues/16), aberta em 03/07/2025 e ainda marcada **open** na consulta, relata um script `farmersdelight.cutting` duplicado com segundo ID sufixado `_2`. É um **relato externo**, sem reprodução confirmada na combinação KubeJS Delight 1.1.6 / Farmer's Delight 1.3.4 / KubeJS build.377 do pack. Investigar geração de IDs, carregamento duplo de scripts e colisões de datapacks antes de atribuir a causa ao addon.

Testes operacionais **pendentes**:
- [ ] Confirmar JAR top-level em disco, metadata, mod ID `kubejsdelight`, hash SHA-1, ranges de dependências e ausência de duplicata; comparar com source pinado.
- [ ] Cold boot client + dedicated server NeoForge/Java 21; observar recipe schema que importa `CookingPotRecipeBookTab` de pacote `client.recipebook` no source; não inferir falha de classloading sem boot.
- [ ] Localizar scripts reais que instanciam `farmersdelight:knife`, `farmersdelight:pie`, `farmersdelight:feast`, `farmersdelight:cutting` e `farmersdelight:cooking`; confirmar IDs e versão das dependências.
- [ ] Verificar atributos de facas e tags, ataques, uso de Cutting Board, tier e quebra/durabilidade após restart.
- [ ] Criar pie com slice válido e contagem de bites; feast com `servingsAmount` explícito, lista não vazia de porções, arrays curtos, leftovers e persistência/multiplayer.
- [ ] Testing de chance 0, 0.75 e 1.0 versus `ChanceResult.of`; não duplicar recompensas após `/reload`, reinício ou duas receitas iguais.
- [ ] Validar cooking `recipe_book_tab`/container/experience/cookingtime`, retirada do recipiente e igualdade servidor/JEI; dois `/reload` seguidos devem preservar uma receita por ID.
- [ ] Testar eventuais interações com mods culinários adicionais e economia de alimentos, sem dupe/duplo processamento.
- [ ] Para quests/perks, atribuir progresso apenas a ações reais do servidor (craft/corte/consumo/serving com causalidade), nunca ao mero registro do recipe ou exibição JEI.

**Conclusão documental:** manter versão histórica **1.1.6**; nenhuma instalação/upgrade/remoção e nenhuma alteração de mecânica. API do source consultado **não substitui** inspeção do JAR nem demonstra consumers KubeJS ativos. Inventário de 587 posições históricas mantido intocado.
