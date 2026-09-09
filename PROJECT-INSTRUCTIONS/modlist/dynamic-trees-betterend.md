# Dynamic Trees - BetterEnd

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81d4891de31e0aaca56e  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees - BetterEnd
- **Arquivo JAR:** `dtbetterend-1.21.1-2.2.0.jar`
- **Versão 1.21.1:** `2.2.0`
- **Categoria:** Compat; Worldgen
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/dannykim2011/DynamicTrees-BetterEnd/tree/1.21.1
- **Função:** Bridge Dynamic Trees ↔ Better End que registra species/families/seeds, growth logic, rooty soils, feature cancellation e worldgen dinâmico para a vegetação arbórea do Better End.
- **Dependências:** Source 2.2.0: Dynamic Trees 1.7.1, Dynamic Trees Plus 1.5.0, Better End 21.0.32, BCLib 21.0.25. Runtime físico atual: Dynamic Trees 1.7.2, Dynamic Trees Plus 1.3.2, Better End 21.0.34, BCLib 21.0.26. O runtime está acima do baseline em DT/Better End/BCLib, mas abaixo em Dynamic Trees Plus; essa divergência é regression gate explícito.
- **Compatibilidade/Riscos:** Version drift misto entre source e runtime: Dynamic Trees Plus 1.3.2 está abaixo do baseline 1.5.0 do source 2.2.0, enquanto DT/Better End/BCLib estão acima. Somam-se riscos de tree static+dynamic duplication, feature-canceller failure, species/tag/rooty-soil drift, mushroom/cap felling/rendering e worldgen seams.
- **Sobreposição:** Substitui/adapta a geração arbórea do Better End para Dynamic Trees. Não substitui Better End nem Dynamic Trees; seu papel é a bridge e prevenção de duplicação entre árvores estáticas/dinâmicas.
- **Observações:** Referência histórica 2.0.0h2 foi superada pelo runtime físico 2.2.0. Source 2.2.0 usa DT 1.7.1 / DTP 1.5.0 / Better End 21.0.32 / BCLib 21.0.25; o runtime atual usa DT 1.7.2 / DTP 1.3.2 / Better End 21.0.34 / BCLib 21.0.26. O drift de DTP é regressão prioritária, não incompatibilidade comprovada.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dtbetterend-1.21.1-2.2.0.jar` e stack runtime. Source oficial branch 1.21.1 declara 2.2.0 e foi auditado para 12 species, seeds, families, growth logic, rooty soils e worldgen configs.
- **Histórico da decisão:** Decisão `Manter` preservada. Em 08/09/2026, a ficha foi reconciliada para o runtime físico 2.2.0 e reconstruída com source 1.21.1 da mesma versão.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — source 2.2.0 pinado; 12 species, families/seeds, growth logic, rooty soils, worldgen cancellers, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-30.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dtbetterend-1.21.1-2.2.0.jar` · mod id `dtbetterend` · versão `2.2.0` · NeoForge 1.21.1.

## 1. Papel no modpack
Dynamic Trees for Better End é uma **bridge de worldgen/content** entre Dynamic Trees e Better End. Ela converte as árvores/plantas arbóreas relevantes de Better End para o modelo dinâmico de Dynamic Trees, com families/species, seeds, growth logic, rooty soils, feature cancellation e geração própria.

## 2. Source pin
O source oficial `dannykim2011/DynamicTrees-BetterEnd`, branch `1.21.1`, declara exatamente:
- `mod_version=2.2.0`;
- Dynamic Trees `1.7.1`;
- Dynamic Trees Plus `1.5.0`;
- Better End `21.0.32`;
- BCLib `21.0.25`;
- NeoForge `21.1.172`.

O runtime físico atual do pack usa Dynamic Trees 1.7.2, Dynamic Trees Plus 1.3.2, Better End 21.0.34 e BCLib 21.0.26. O drift é misto: DT/Better End/BCLib estão acima do baseline do source, enquanto Dynamic Trees Plus 1.3.2 está abaixo do baseline 1.5.0. Isso é **regression gate explícito**, não prova automática de incompatibilidade.

## 3. Authority / ownership
- **Better End:** biomas, materiais, blocos e árvores/vegetação originais.
- **Dynamic Trees:** engine de growth, branches, leaves, rooty soil, seeds e worldgen tree replacement.
- **DT Better End:** adapters/species/growth/worldgen que ligam os dois.
- **BCLib:** infraestrutura exigida pelo stack Better End.

A bridge não deve criar uma segunda versão estática e dinâmica da mesma árvore no mesmo placement pass.

## 4. Species confirmadas
O branch exato contém **12 species JSON**:
1. `amaranita`;
2. `dragon_helix_tree`;
3. `dragon_tree`;
4. `helix_tree`;
5. `jellyshroom`;
6. `lacugrove`;
7. `lucernia`;
8. `mossy_glowshroom`;
9. `neon_cactus`;
10. `pythadendron`;
11. `tenanea`;
12. `umbrella_tree`.

Há seeds correspondentes para as 12.

## 5. Families
O source registra families para:
- amaranita;
- dragon_tree;
- helix_tree;
- jellyshroom;
- lacugrove;
- lucernia;
- mossy_glowshroom;
- neon_cactus;
- pythadendron;
- tenanea;
- umbrella_tree.

`dragon_helix_tree` aparece como species própria e compartilha a infraestrutura familiar apropriada em vez de exigir uma family homônima separada.

## 6. Growth logic customizada
O branch possui lógica específica para:
- Dragon Helix;
- Dragon Tree;
- Helix Tree;
- Lacugrove;
- Lucernia;
- Mushroom Stem;
- Neon Cactus;
- Pythadendron;
- Tenanea;
- Umbrella Tree.

Isso demonstra que a bridge não é somente um datapack de substituição: várias espécies têm algoritmos/handlers próprios de crescimento.

## 7. Mushrooms e Umbrella Tree
Há infraestrutura especial para mushrooms e para Umbrella Tree, incluindo family/species próprios, renderer/felling handler e geração de canopy/clusters.

Mods externos de tree-felling devem operar via Dynamic Trees contracts; quebrar blocos de stem/cap manualmente pode ignorar a lógica de queda/loot do provider.

## 8. Rooty soils
O source declara rooty soils Better End para:
- amber moss;
- chorus nylium;
- end moss;
- end mycelium;
- endstone dust;
- jungle moss;
- pink moss;
- rutiscus;
- sangnum;
- shadow grass.

O solo é parte do growth state de Dynamic Trees. Ferramentas de terraform/copy devem preservar soil/species relation quando aplicável.

## 9. Worldgen e feature cancellation
O addon possui `world_gen/default.json` e `feature_cancellers.json`, além de custom gen features como:
- Base Trunk Clearance;
- Better End Decorations;
- Flower Vines;
- Remove Broken Chorus;
- Umbrella Tree Canopy;
- Umbrella Tree Clusters;
- Umbrella Tree Growth Canopy.

Feature cancellers são críticos para evitar árvore original + árvore dinâmica ocupando o mesmo espaço.

## 10. Data/resources
O branch contém/generated:
- family/species/seeds;
- leaves/caps/branches;
- loot tables;
- tags;
- rooty soils;
- worldgen configs.

Datapack reload ou alteração de Tree Packs pode mudar placement/growth sem alterar o mod JAR; customização deve respeitar os namespaces/registries de Dynamic Trees.

## 11. Client / Server
Growth, worldgen, felling, drops e soil state são server-authoritative. Models/leaves/caps/rendering são client-side presentation. Dedicated server não deve depender de renderer classes.

## 12. Lifecycle
Validar:
- geração de chunks novos no End/Better End;
- sapling/seed planting;
- crescimento completo;
- felling;
- chunk unload/reload;
- server restart;
- datapack reload;
- worldgen em chunks pré-existentes vs novos;
- bonemeal/growth acceleration quando suportados.

## 13. Multiplayer
Tree growth/felling/loot deve ocorrer uma vez no servidor. Dois jogadores derrubando a mesma árvore não podem produzir double loot nem deixar branch network órfã.

## 14. Version drift atual
O branch 2.2.0 foi compilado contra DT 1.7.1 / DTP 1.5.0 / Better End 21.0.32 / BCLib 21.0.25, enquanto o pack usa 1.7.2 / 1.3.2 / 21.0.34 / 21.0.26.

O ponto mais sensível é **Dynamic Trees Plus 1.3.2 no runtime contra 1.5.0 no baseline do source**. A divergência atinge superfícies de species/worldgen e precisa de smoke-test com a combinação física atual. Não rebaixar ou atualizar dependências apenas para igualar o buildscript sem evidência runtime.

## 15. Riscos
1. árvore estática e dinâmica gerarem juntas;
2. species não resolver após ID/tag drift;
3. rooty soil incompatível com bloco atualizado de Better End;
4. feature canceller falhar;
5. mushroom/cap rendering ou felling quebrar;
6. version drift DT/DTP/BCLib/Better End;
7. datapack reload deixar cache stale;
8. tree-felling externo duplicar drops;
9. worldgen seams em chunks antigos/novos;
10. custom growth logic entrar em loop/estado inválido.

## 16. Matriz de testes
1. Dedicated server boot com stack física atual.
2. Gerar chunks Better End novos e procurar as 12 species.
3. Confirmar ausência da contraparte estática duplicada onde a bridge deve substituir.
4. Plantar/crescer/fellar cada species em amostra controlada.
5. Validar mushrooms e Umbrella Tree separadamente.
6. Testar os 10 rooty soils Better End.
7. Chunk unload/reload durante crescimento.
8. Restart com árvores parcialmente crescidas.
9. Datapack reload/Tree Pack customization em mundo de teste.
10. Dois jogadores derrubando mesma árvore.
11. Smoke-test após update de Better End/BCLib/DT/DTP.

**Testes listados são requisitos futuros; não foram executados nesta catalogação.**

## 17. Evidências
- modlist física canônica de 08/09/2026: runtime 2.2.0 e dependências atuais;
- source oficial branch `1.21.1`, `gradle.properties` 2.2.0;
- family/species/seed/rooty-soil/worldgen/growth files do branch exato.

> **Boundary canônico:** Better End fornece o conteúdo/biomas; Dynamic Trees fornece a engine arbórea; DT Better End adapta as espécies e impede duplicação de worldgen.
