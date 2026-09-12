# Dynamic Trees - BetterNether

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81e0bb84f88b58228d68  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees - BetterNether
- **Arquivo JAR:** `dtbetternether-1.21.1-2.2.0.jar`
- **Versão 1.21.1:** `2.2.0`
- **Categoria:** Compat; Worldgen
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/dannykim2011/DynamicTrees-BetterNether/tree/1.21.1
- **Função:** Bridge Dynamic Trees ↔ Better Nether com species/families/seeds, growth logic, mushroom/cap infrastructure, rooty soils, gen features e feature cancellation para worldgen dinâmico.
- **Dependências:** Source 2.2.0: Dynamic Trees 1.7.1, Dynamic Trees Plus 1.5.0, Better Nether 21.0.23, BCLib 21.0.25. Runtime físico atual: Dynamic Trees 1.7.2, Dynamic Trees Plus 1.3.2, Better Nether 21.0.26, BCLib 21.0.26. O runtime está acima do baseline em DT/Better Nether/BCLib, mas abaixo em Dynamic Trees Plus; tratar como regression gate explícito.
- **Compatibilidade/Riscos:** Version drift misto: Dynamic Trees Plus 1.3.2 no runtime está abaixo do baseline 1.5.0 do source, enquanto DT/Better Nether/BCLib estão acima. Riscos adicionais: static+dynamic duplication, feature-canceller drift, mushroom/cap branch corruption, rooty-soil/tag drift, decoration double-processing e tree-felling double loot.
- **Sobreposição:** Adapta/substitui a geração arbórea/fúngica do Better Nether para Dynamic Trees. Não substitui Better Nether, BCLib ou Dynamic Trees; seu papel é integração e worldgen replacement.
- **Observações:** Referência histórica 2.0.0h2 foi superada pelo runtime físico 2.2.0. Source 2.2.0 usa DT 1.7.1 / DTP 1.5.0 / Better Nether 21.0.23 / BCLib 21.0.25; runtime atual usa DT 1.7.2 / DTP 1.3.2 / Better Nether 21.0.26 / BCLib 21.0.26. O drift de DTP é regressão prioritária, não incompatibilidade comprovada.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dtbetternether-1.21.1-2.2.0.jar` e stack atual. Source oficial branch 1.21.1 declara 2.2.0 e foi auditado para 12 species, families/seeds, growth logic, mushrooms, rooty soils, gen features e worldgen cancellers.
- **Histórico da decisão:** Decisão `Manter` preservada. Em 08/09/2026, a ficha foi reconciliada para o runtime físico 2.2.0 e reconstruída com source 1.21.1 da mesma versão.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — source 2.2.0 pinado; 12 species, mushrooms/caps, growth, soils, worldgen cancellation, lifecycle/MP, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-30.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dtbetternether-1.21.1-2.2.0.jar` · mod id `dtbetternether` · versão `2.2.0` · NeoForge 1.21.1.

## 1. Papel no modpack
Dynamic Trees for Better Nether é a bridge entre **Dynamic Trees** e **Better Nether**, convertendo árvores, fungos e cactos arbóreos do Better Nether para growth/felling/worldgen dinâmicos. O addon contém species/families, custom growth logic, mushroom/cap infrastructure, rooty soils e feature cancellation.

## 2. Source pin
O source oficial `dannykim2011/DynamicTrees-BetterNether`, branch `1.21.1`, declara:
- `mod_version=2.2.0`;
- Dynamic Trees `1.7.1`;
- Dynamic Trees Plus `1.5.0`;
- Better Nether `21.0.23`;
- BCLib `21.0.25`;
- NeoForge `21.1.172`.

O runtime físico atual usa DT 1.7.2, DTP 1.3.2, Better Nether 21.0.26 e BCLib 21.0.26. O drift é misto: DT/Better Nether/BCLib estão acima do baseline do source, enquanto Dynamic Trees Plus 1.3.2 está abaixo do baseline 1.5.0. Isso exige regressão explícita com a combinação instalada.

## 3. Authority / ownership
- **Better Nether:** conteúdo/biomas/blocos originais.
- **Dynamic Trees:** engine de growth, branches, leaves/caps, soil, seeds e felling.
- **DT Better Nether:** adapters/species e regras de substituição/worldgen.
- **BCLib:** infraestrutura do ecossistema BetterX.

A bridge deve cancelar/reconciliar a geração estática quando substitui uma planta por forma dinâmica.

## 4. Species confirmadas
O branch exato registra **12 species**:
1. `anchor_tree`;
2. `big_warped`;
3. `crimson_glowing`;
4. `crimson_pine`;
5. `nether_brown_mushroom`;
6. `nether_cactus`;
7. `nether_red_mushroom`;
8. `nether_sakura`;
9. `old_willow`;
10. `rubeus`;
11. `wart`;
12. `willow`.

Há seeds correspondentes às 12.

## 5. Families
O source contém families para:
- anchor_tree;
- big_warped;
- crimson_glowing;
- crimson_pine;
- nether_brown_mushroom;
- nether_cactus;
- nether_red_mushroom;
- nether_sakura;
- rubeus;
- wart;
- willow.

`old_willow` é species própria dentro da infraestrutura familiar apropriada.

## 6. Growth logic customizada
Há lógica específica para Anchor Tree, Big Warped, Crimson Glowing, Crimson Pine, Mushroom Stem, Nether Cactus, Nether Sakura, Old Willow, Rubeus, Wart e Willow.

Como crescimento no Nether pode envolver teto, paredes e substratos não convencionais, integração externa não deve pressupor comportamento de árvore Overworld vanilla.

## 7. Mushrooms / cap system
O addon possui infraestrutura especializada para mushrooms:
- shapes de Nether Brown/Red Mushroom;
- Profiled Mushroom Shape;
- Protected Mushroom Branch Block/Family/Species;
- Stable Cap Properties;
- Stable Dynamic Cap Block;
- registros DT Plus associados.

Esse sistema é parte do provider; tree-felling ou block-conversion externo deve respeitar branch/cap semantics para não duplicar drops ou deixar caps órfãos.

## 8. Gen features
O branch registra gen features como:
- Base Trunk Clearance;
- Crimson Glowing Wall Decorations;
- Exposed Shroomlight;
- Willow Vines.

Worldgen precisa coordenar decoração, vines e shroomlights com a árvore dinâmica final.

## 9. Rooty soils
Better Nether-specific rooty soils confirmados:
- ceiling_mushrooms;
- jungle_grass;
- mushroom_grass;
- nether_mycelium;
- nether_wart_block;
- netherrack_moss;
- sepia_mushroom_grass;
- soul_sandstone;
- swampland_grass.

Também são usados soils base de Dynamic Trees para crimson_nylium, netherrack, soul_sand, soul_soil e warped_nylium.

## 10. Worldgen / feature cancellers
O source contém `world_gen/default.json`, `feature_cancellers.json`, além de cancellers por block e feature type. O objetivo operacional é impedir a geração original incompatível e inserir a versão dinâmica de forma determinística.

Mudanças de Better Nether em IDs/feature types podem quebrar a seleção/cancelamento sem impedir o jogo de iniciar; por isso worldgen visual precisa de QA real.

## 11. Tags e data
O branch gera/usa tags para branches que queimam, fungus branches/caps, leaves, rooty soil, saplings, stripped variants, wart blocks e seeds. Scripts que alteram flammability, felling ou composting devem preferir tags/provider contracts em vez de listas duplicadas.

## 12. Client / Server
Worldgen, growth, felling, soil e drops são server-authoritative. Cap/leaf/branch render é client-facing. Dedicated server não deve carregar classes de render.

## 13. Lifecycle
Validar:
- geração de chunks novos;
- growth em solo/teto quando aplicável;
- bonemeal/growth acceleration;
- felling de árvores e mushrooms;
- chunk unload/reload;
- save/restart;
- datapack reload;
- transição Nether↔Overworld;
- atualização de Better Nether/BCLib.

## 14. Multiplayer
Growth e felling liquidam uma vez no servidor. Dois jogadores não devem gerar double drops nem quebrar o network de branches/caps por concorrência.

## 15. Version drift atual
O source 2.2.0 foi construído contra DT 1.7.1 / DTP 1.5.0 / Better Nether 21.0.23 / BCLib 21.0.25; o pack usa 1.7.2 / 1.3.2 / 21.0.26 / 21.0.26.

O ponto mais sensível é **Dynamic Trees Plus 1.3.2 no runtime contra 1.5.0 no baseline do source**. Não há evidência aqui de incompatibilidade comprovada, mas a divergência alcança species/worldgen e é regression gate obrigatório.

## 16. Riscos
1. geração estática + dinâmica duplicada;
2. feature canceller não reconhecer feature atualizada;
3. mushroom/cap branch state corrompido;
4. rooty soil ID/tag drift;
5. vines/shroomlights decorados duas vezes;
6. tree-felling externo duplicar drops;
7. version drift DT/DTP/Better Nether/BCLib;
8. datapack cache stale;
9. worldgen seams em chunks antigos/novos;
10. growth customizado em teto/parede entrar em estado inválido.

## 17. Matriz de testes
1. Dedicated server boot com stack atual.
2. Gerar chunks Better Nether e localizar as 12 species.
3. Confirmar ausência de duplicação static+dynamic.
4. Plantar/crescer/fellar amostra de cada species.
5. Testar mushrooms Red/Brown e cap network.
6. Testar Nether Cactus e Wart.
7. Verificar vines/shroomlights/decorações.
8. Testar rooty soils Better Nether + soils base.
9. Chunk unload/reload e restart durante growth.
10. Dois jogadores felling a mesma estrutura.
11. Smoke-test após update de Better Nether/BCLib/DT/DTP.

**Esses testes não foram executados nesta catalogação.**

## 18. Evidências
- modlist física canônica de 08/09/2026: runtime 2.2.0 e dependências atuais;
- source oficial branch `1.21.1`, `gradle.properties` 2.2.0;
- species/family/seed/growth/rooty-soil/worldgen/mushroom files do branch exato.

> **Boundary canônico:** Better Nether fornece biomas e conteúdo; Dynamic Trees fornece a engine; DT Better Nether adapta species/fungi e controla a substituição de worldgen.
