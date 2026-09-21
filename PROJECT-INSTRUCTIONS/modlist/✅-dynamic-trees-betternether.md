# Dynamic Trees - BetterNether

## Propriedades do registro

- **Mod:** Dynamic Trees - BetterNether
- **Arquivo JAR:** `dtbetternether-1.21.1-2.2.0.jar`
- **Versão 1.21.1:** `2.2.0`
- **Categoria:** Compat, Worldgen
- **Função:** Bridge Dynamic Trees ↔ Better Nether com species/families/seeds, growth logic, mushroom/cap infrastructure, rooty soils, gen features e feature cancellation para worldgen dinâmico.
- **Dependências:** Source 2.2.0: Dynamic Trees 1.7.1, Dynamic Trees Plus 1.5.0, Better Nether 21.0.23 e BCLib 21.0.25. Runtime físico atual: Dynamic Trees 1.7.2, Dynamic Trees Plus 1.3.2, Better Nether 21.0.26 e BCLib 21.0.26. Drift DTP abaixo do baseline source continua regression gate explícito.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Version drift misto: Dynamic Trees Plus 1.3.2 no runtime está abaixo do baseline 1.5.0 do source, enquanto DT/Better Nether/BCLib estão acima. Soma-se divergência de distribuição: build física/source 2.2.0 está à frente do latest público CurseForge 1.21.1 (2.1.0). Não rebaixar nem assumir equivalência de artefatos sem validar origem/binário.
- **Fonte:** https://github.com/dannykim2011/DynamicTrees-BetterNether/tree/1.21.1
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `dtbetternether-1.21.1-2.2.0.jar` / runtime 2.2.0 e o stack atual. Source oficial branch 1.21.1 matching 2.2.0 revalidado em 21/09/2026. CurseForge público para 1.21.1 permanece em 2.1.0; physical/source e distribuição pública são mantidos separados fail-closed.
- **Observações:** Runtime/source matching 2.2.0 preservados. Em 12/09/2026, CurseForge público para Minecraft 1.21.1 ainda expõe 2.1.0 como release mais recente, enquanto a modlist contém `dtbetternether-1.21.1-2.2.0.jar` e o branch source 1.21.1 declara 2.2.0. Trata-se de divergência de distribuição/origem, não motivo para rebaixar o pack.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 21/09/2026 — lote físico #226: dtbetternether-1.21.1-2.2.0.jar / runtime 2.2.0 e source branch 1.21.1 matching reconfirmados. Divergência atual: distribuição pública CurseForge para 1.21.1 ainda mostra 2.1.0 como latest; a modlist física/source 2.2.0 permanecem authority local.
- **Decisão:** Manter
- **Histórico da decisão:** Decisão `Manter` preservada. Em 08/09/2026, a ficha foi reconciliada para o runtime físico 2.2.0 e reconstruída com source 1.21.1 da mesma versão.
- **Sobreposição:** Adapta/substitui a geração arbórea/fúngica do Better Nether para Dynamic Trees. Não substitui Better Nether, BCLib ou Dynamic Trees; seu papel é integração e worldgen replacement.
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs
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
Há uma divergência de distribuição relevante: em 12/09/2026, a página pública do CurseForge para Minecraft 1.21.1 ainda apresenta **2.1.0** como release mais recente, enquanto a modlist física contém **2.2.0** e o branch source 1.21.1 também declara **2.2.0**. Para este catálogo, physical/source matching 2.2.0 permanece authority local; o artefato não é rebaixado para 2.1.0 por ausência da mesma build na distribuição pública.
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
- modlist física atual de 21/09/2026: `dtbetternether-1.21.1-2.2.0.jar` / runtime 2.2.0 e dependências atuais;
- CurseForge público revalidado em 12/09/2026: latest 1.21.1 exposto = 2.1.0, divergente do physical/source matching 2.2.0;
- source oficial branch `1.21.1`, `gradle.properties` 2.2.0;
- species/family/seed/growth/rooty-soil/worldgen/mushroom files do branch exato.
> **Boundary canônico:** Better Nether fornece biomas e conteúdo; Dynamic Trees fornece a engine; DT Better Nether adapta species/fungi e controla a substituição de worldgen.
