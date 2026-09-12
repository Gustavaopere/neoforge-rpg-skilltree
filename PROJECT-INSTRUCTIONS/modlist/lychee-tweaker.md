# Lychee Tweaker

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81838ffaecebbe43d784
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Lychee Tweaker
- **Arquivo JAR:** `Lychee-1.21.1-NeoForge-6.7.0.jar`
- **Versão 1.21.1:** 6.7.0+neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Automação, Compat
- **Função:** Framework data-driven de crafting/interações in-world com recipe types, serializers, contextos, condições, post-actions, registries sincronizados e hooks de runtime para datapacks/addons.
- **Dependências:** Kiwi requerido. Cloth Config opcional. O próprio JAR embarca via JarJar fabric-particles-v1 4.0.2+824f924c19; essa biblioteca não é entrada top-level da modlist.
- **Sobreposição:** Não é substituto direto de KubeJS nem de mods de conteúdo: Lychee fornece engine/serialização para recipes contextuais; datapacks/addons definem as recipes concretas.
- **Compatibilidade/Riscos:** Mudanças de schema/IDs podem quebrar datapacks; alguns recipe types requerem client; há mixins em RecipeManager/reload/dispenser/GUI. Testar block_clicking 6.7.0, reload/cache, multiplayer e automação por dispenser.
- **Observações:** A linha fabric-particles-v1-4.0.2+824f924c19 observada na modlist é JarJar interno do Lychee, confirmado pelo build.gradle; não criar página top-level separada. A 6.7.0 alterou block_clicking e corrigiu crafting.
- **Procedência:** Modlist física + CurseForge oficial + source Snownee/Lychee branch 1.21-neoforge, atualmente exatamente em 6.7.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lychee
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 6.7.0 exato; recipe types/serializers, registries, configs, JarJar, mixins, riscos e testes documentados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🧪 **Escopo canônico desta ficha:** `Lychee-1.21.1-NeoForge-6.7.0.jar`, exatamente o JAR top-level da modlist física. O JAR contém `META-INF/jarjar/fabric-particles-v1-4.0.2+824f924c19.jar`; essa biblioteca é **embarcada pelo Lychee via JarJar** e não deve ser contada como mod top-level independente. O source oficial `Snownee/Lychee`, branch `1.21-neoforge`, declara atualmente `mod_version=6.7.0`, Minecraft 1.21.1 e NeoForge 21.1.235, coincidindo com a build instalada.

## 1. Identidade e autoridade
- **Nome:** Lychee.
- **JAR:** `Lychee-1.21.1-NeoForge-6.7.0.jar`.
- **Versão:** `6.7.0+neoforge` no metadata físico; projeto/source `6.7.0`.
- **Mod ID:** `lychee`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Upstream:** `Snownee/Lychee`, branch `1.21-neoforge`.
- **Release type no source:** beta.

## 2. Papel técnico no pack
O próprio README define Lychee como um **mod de crafting in-world data-driven**. Seu papel é fornecer uma engine de receitas/eventos contextuais que datapacks e outros mods podem declarar sem implementar cada interação diretamente em Java.
Ele deve ser tratado como **framework de receitas e automação contextual**, não como um mod de conteúdo temático autônomo. Removê-lo pode quebrar datapacks/addons que dependam dos serializers, recipe types, post-actions, condições ou contextos registrados por Lychee.

## 3. Tipos de receita registrados
`RecipeTypes.java` da branch 6.7.0 registra os seguintes tipos sob o namespace Lychee:
- `blank`;
- `item_burning`;
- `item_inside`;
- `block_interacting`;
- `block_clicking`;
- `anvil_crafting`;
- `block_crushing`;
- `lightning_channeling`;
- `item_exploding`;
- `block_exploding`;
- `random_block_ticking`;
- `dripstone_dripping`;
- `entity_ticking`.
Também existem os tipos auxiliares `category_metadata` e `category_modifier` para apresentação/categorização e um serializer `crafting` específico (`ShapedCraftingRecipe`).

## 4. Serialização e data-driven API
`RecipeSerializers.java` registra serializers correspondentes para todos os tipos acima, incluindo `crafting`. Isso confirma que a superfície operacional do mod é declarativa: recipes são carregadas pelo sistema de recursos/receitas do Minecraft e interpretadas por serializers próprios.
O runtime mantém cache dos `LycheeRecipeType` e, em `buildCache()`, chama `refreshCache` e `updateEmptyState` para cada tipo. Também detecta se há recipes crafting usando o serializer próprio de Lychee. Mudanças de datapack/reload, portanto, podem alterar comportamento sem trocar o JAR.

## 5. Registries próprios sincronizados
`LycheeRegistries.java` cria e registra cinco `MappedRegistry` próprios, todos com `sync(true)`:
- `contextual`: tipos de condições contextuais;
- `post_action`: tipos de ações executadas após processamento;
- `context`: chaves de contexto Lychee;
- `context_serializer`: codecs/serializers de contexto;
- `ui_element`: tipos de elementos de UI.
A sincronização explícita desses registries mostra que parte da definição precisa estar coerente entre servidor e cliente. Datapacks/extensões que adicionem tipos ou dependam de IDs específicos devem ser tratados como contrato técnico.

## 6. Contextos e requisitos client-side
Nem todo recipe type possui o mesmo perfil:
- `block_interacting` e `block_clicking` usam o param set de interação com bloco, marcam `requiresClient=true` e podem impedir consumo de inputs;
- `dripstone_dripping` usa contexto de bloco, extrai chance e também requer client;
- `random_block_ticking` usa contexto de bloco e extrai chance;
- `lightning_channeling` e `item_exploding` podem impedir consumo de inputs;
- `item_inside` também permite impedir consumo de inputs.
Isso é importante para servidores: uma receita existir como JSON não implica que toda a execução seja puramente server-only; alguns tipos têm sincronização/representação client relevante.

## 7. Configuração confirmada na 6.7.0
`LycheeConfig.java` possui quatro controles diretos:
- `debug.enable` → `debug`;
- `fragment.enable` → `enableFragment=true`;
- `yamlRecipes.enable` → `enableYamlRecipes=true`;
- `dispenserFallableBlockPlacement=true`.
O código confirma usos concretos:
- debug altera logging/diagnóstico e comportamento de debug visual;
- `enableFragment` participa do processamento de recipe fragments no `RecipeManager`;
- `dispenserFallableBlockPlacement` participa do mixin de dispenser para colocação de blocos, incluindo fallable blocks sob as condições definidas pelo mod.

## 8. Mixins e pontos de intervenção
A branch 6.7.0 contém mixins que alcançam pontos do runtime vanilla/NeoForge, entre eles:
- `RecipeManagerMixin`: integração de fragments no carregamento de receitas;
- `SimpleJsonResourceReloadListenerMixin`: integração no reload de recursos JSON;
- `DefaultDispenseItemBehaviorMixin`: comportamento de dispenser/placement;
- `DataResultMixin`: diagnóstico ampliado quando debug está ativo;
- `GuiRenderStateMixin` client-side: suporte ao modo visual de debug.
Consequência operacional: conflitos com outros mods que mixem os mesmos métodos são possíveis, mas não devem ser presumidos sem log/mixin audit específico.

## 9. Dependências e bibliotecas embarcadas
O `build.gradle` da branch exata confirma:
- **Kiwi** como dependência requerida na publicação;
- **Cloth Config** como dependência opcional;
- integrações/dev dependencies para viewers e KubeJS, sem transformar todos em dependências runtime obrigatórias;
- JarJar de `org.sinytra.forgified-fabric-api:fabric-particles-v1:4.0.2+824f924c19`.

### Regra de inventário aplicada
`fabric-particles-v1-4.0.2+824f924c19.jar` aparece fisicamente dentro de `META-INF/jarjar/` do Lychee. Ele pertence ao host Lychee e **não recebe linha/página top-level separada no catálogo**.

## 10. Mudanças específicas da release 6.7.0
A publicação oficial da 6.7.0 para MC 1.21.1/NeoForge registra:
- adição do atributo `action` a block clicking recipe;
- correções de crafting (“round 3”);
- correção para block clicking recipes só produzirem efeito quando o jogador realmente inicia o clique no bloco.
Esses pontos devem ser considerados ao depurar datapacks escritos para 6.6.x: comportamento de `block_clicking` mudou na própria build instalada.

## 11. Fronteiras de autoridade
- **Lychee:** define engine, recipe types, serializers, registries, contextos, conditions/post-actions e hooks necessários à execução.
- **Datapacks/addons:** definem recipes concretas e, portanto, o conteúdo efetivo que ocorre no mundo.
- **KubeJS:** pode coexistir como camada de scripting; a presença de KubeJS em ambiente de desenvolvimento/source não significa automaticamente que Lychee dependa dele em runtime.
- **Viewers de receita:** integração de visualização não é a autoridade da execução da recipe.

## 12. Riscos de integração
1. **Schema/ID:** datapack antigo ou escrito para outra versão pode usar campos/IDs incompatíveis.
2. **Reload/cache:** alterações em JSON/fragments são materialmente relevantes e devem ser testadas após reload/restart adequado.
3. **Client/server:** recipe types marcados `requiresClient` exigem validar sincronização e apresentação em multiplayer.
4. **Mixins:** conflitos só podem ser confirmados por logs/auditoria; não assumir compatibilidade perfeita apenas porque o jogo inicia.
5. **Dispenser:** Lychee modifica colocação via dispenser; packs com automação pesada devem testar comportamento com blocos afetados.
6. **JarJar:** não remover/duplicar manualmente `fabric-particles-v1` com base apenas na linha interna da modlist.

## 13. Matriz mínima de testes
- Carregar uma recipe de cada família realmente usada pelo pack e confirmar parsing sem erro.
- Executar `/reload` ou ciclo equivalente e confirmar reconstrução de caches sem recipes órfãs.
- Testar `block_clicking` especificamente na 6.7.0: não deve disparar fora do início válido de clique.
- Testar `block_interacting`/`block_clicking` em servidor dedicado com cliente modded.
- Testar uma recipe com chance (`random_block_ticking` ou `dripstone_dripping`) somente se houver datapack real usando-a.
- Testar dispenser em automações que coloquem blocos/fallable blocks.
- Verificar logs com debug apenas em sessão diagnóstica; não manter debug como requisito normal.
- Testar datapacks/addons consumidores após qualquer update do Lychee, mesmo em patch release.

## 14. Evidências consultadas
- Modlist física: `Lychee-1.21.1-NeoForge-6.7.0.jar` e JarJar interno `fabric-particles-v1-4.0.2+824f924c19.jar`.
- CurseForge oficial: https://www.curseforge.com/minecraft/mc-mods/lychee
- Source oficial: https://github.com/Snownee/Lychee/tree/1.21-neoforge
- `gradle.properties`: 6.7.0, MC 1.21.1, NeoForge 21.1.235.
- `build.gradle`: Kiwi requerido, Cloth Config opcional e JarJar de fabric-particles-v1.
- `RecipeTypes.java`, `RecipeSerializers.java`, `LycheeRegistries.java`, `LycheeConfig.java` da branch 1.21-neoforge.

> ✅ **Authority forte:** diferentemente de um source apenas aproximado, a branch consultada declara exatamente a 6.7.0 instalada. A ficha pode, portanto, usar os tipos, serializers, registries e configs acima como evidência direta desta build.
