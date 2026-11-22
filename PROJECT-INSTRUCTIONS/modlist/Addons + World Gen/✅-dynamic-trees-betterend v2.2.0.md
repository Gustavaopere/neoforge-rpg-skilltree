# Dynamic Trees - BetterEnd

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#226**: JAR `dtbetterend-1.21.1-2.2.0.jar`, mod id `dtbetterend`, runtime `2.2.0`, SHA-1 `665bbb5b7d39917def3f02d13ecf7e1fe5876084`.

## Propriedades do registro

- **Mod:** Dynamic Trees - BetterEnd
- **Arquivo JAR:** `dtbetterend-1.21.1-2.2.0.jar`
- **Versão 1.21.1:** `2.2.0`
- **Categoria:** Compat, Worldgen
- **Função:** Bridge Dynamic Trees ↔ Better End que registra species/families/seeds, growth logic, rooty soils, feature cancellation e worldgen dinâmico para a vegetação arbórea do Better End.
- **Dependências:** Source 2.2.0: Dynamic Trees 1.7.1, Dynamic Trees Plus 1.5.0, Better End 21.0.32 e BCLib 21.0.25. Runtime físico atual: Dynamic Trees 1.7.2, Dynamic Trees Plus 1.3.2, Better End 21.0.34 e BCLib 21.0.26. O drift de DTP abaixo do baseline source permanece explícito.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Version drift misto entre source e runtime: Dynamic Trees Plus 1.3.2 está abaixo do baseline 1.5.0 do source 2.2.0, enquanto DT/Better End/BCLib estão acima. Somam-se riscos de tree static+dynamic duplication, feature-canceller failure, species/tag/rooty-soil drift, mushroom/cap felling/rendering e worldgen seams.
- **Fonte:** https://github.com/dannykim2011/DynamicTrees-BetterEnd/tree/1.21.1
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `dtbetterend-1.21.1-2.2.0.jar` / runtime 2.2.0 e o stack atual. Source oficial branch 1.21.1 matching + CurseForge oficial File ID 8826408 revalidados em 21/09/2026; 2.2.0 continua a release NeoForge 1.21.1 mais recente.
- **Observações:** Referência histórica 2.0.0h2 foi superada pelo runtime físico 2.2.0. Source 2.2.0 usa DT 1.7.1 / DTP 1.5.0 / Better End 21.0.32 / BCLib 21.0.25; o runtime atual usa DT 1.7.2 / DTP 1.3.2 / Better End 21.0.34 / BCLib 21.0.26. O drift de DTP é regressão prioritária, não incompatibilidade comprovada.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 21/09/2026 — lote físico #225: dtbetterend-1.21.1-2.2.0.jar / runtime 2.2.0 reconfirmados como latest Release NeoForge 1.21.1. Drift físico DTP 1.3.2 abaixo do baseline source 1.5.0 permanece regression gate, não incompatibilidade comprovada.
- **Decisão:** Manter
- **Histórico da decisão:** Decisão `Manter` preservada. Em 08/09/2026, a ficha foi reconciliada para o runtime físico 2.2.0 e reconstruída com source 1.21.1 da mesma versão.
- **Sobreposição:** Substitui/adapta a geração arbórea do Better End para Dynamic Trees. Não substitui Better End nem Dynamic Trees; seu papel é a bridge e prevenção de duplicação entre árvores estáticas/dinâmicas.
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs
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

## 18. Auditoria incremental de 2.2.0 a 2.4.0 — 22/11/2026 (não instaladas)

**Identidade oficial e autoridade física:** projeto CurseForge **Dynamic Trees - BetterEnd** (ID **1606259**, autor *dannykim2011*, não confundir com **Dynamic Trees for BetterEnd** de outro projeto). O pack ainda possui **#226**, `dtbetterend-1.21.1-2.2.0.jar`, runtime **2.2.0**, NeoForge **21.1.250**; a última modlist física consultável não registra instalação posterior.

### 2.3.0 — publicada 18/11/2026, NeoForge 1.21.1 (File ID 9681813)

A [publicação oficial 2.3.0](https://www.curseforge.com/minecraft/mc-mods/dynamictrees-betterend/files/9681813) é um artefato **Release**, mas a página **não fornece changelog**. Para reconstruir seu delta sem atribuir conteúdo de outra versão, foi comparado o source oficial do branch `1.21.1`, [commit 2.2.0 anterior `911d7bb`](https://github.com/dannykim2011/DynamicTrees-BetterEnd/commit/911d7bbfe910b5cad91dff059fa8eea9f6baf7d6) → [commit 2.3.0 de 18/11 `47802ea`](https://github.com/dannykim2011/DynamicTrees-BetterEnd/commit/47802eac7e0bc606c7e91705fffff7907434ba8a). **São diferenças do repositório source, não um diff binário entre os JARs de distribuição.**

- **Proteção de árvores na worldgen:** novas features `dtbetterend:mound` e `dtbetterend:clear_volume` implementam versões protegidas de mounds/limpeza que evitam sobrescrever estruturas Dynamic Trees existentes (branches, trunk shells, surface roots e rooty soil). Várias species passam a referenciar explicitamente esses IDs em vez das features genéricas. A limpeza do tronco também ignora block entities, fluidos e surface roots.
- **Umbrella Tree:** altura máxima configurada passa de **50 para 25**; o cálculo de crescimento/topo e deslocamento dos ramos é alterado; as copas duplas recebem limite de raio conforme distância horizontal, para reduzir sobreposição. Os **JoCodes da umbrella tree foram substituídos** em parte substancial, com consequência para geração de **novos** exemplares; não assumir migração de árvores já geradas.
- **Atualização do stack de compilação:** source 2.3.0 usa NeoForge **21.1.267**, Dynamic Trees **1.7.2**, Dynamic Trees Plus **1.3.2** e Better End **21.0.39**; também troca os file IDs de BCLib, WorldWeaver e WunderLib. Esses números são baseline de **build/source**, não prova de que todos sejam mínimos formais de runtime.
- **Requisito formal relevante:** o template `META-INF/neoforge.mods.toml` declara `betterend` **obrigatório** com `versionRange=[21.0.39,)` após expansão das propriedades do build. Também declara Dynamic Trees `[1.7.2,)` e Dynamic Trees Plus **opcional** `[1.3.2,)`. **O pack físico tem Better End 21.0.34**, abaixo do range declarado pela 2.3.0. A DTP física **1.3.2** alcança o piso da source 2.3.0; o problema de upgrade documentado aqui é sobretudo o **Better End** e as suas dependências/transitividades, não presumir exigência DTP 1.5.0 para a versão 2.3.0.

### 2.4.0 — publicada 20/11/2026, NeoForge 1.21.1 (File ID 9716886)

A [publicação oficial 2.4.0](https://www.curseforge.com/minecraft/mc-mods/dynamictrees-betterend/files/9716886) confirma uma segunda release compatível com jogo/loader, **mais nova que a 2.3.0**. **A página declara “File has no changelog”**. No momento da consulta, o último commit publicado no branch source `1.21.1` ainda tem `mod_version=2.3.0` (commit `47802ea`, 18/11); **não há correspondência source→JAR da 2.4.0 comprovada**. Portanto **não atribuir** à 2.4.0 as mudanças de umbrella tree ou mounds listadas para o commit 2.3.0, nem inventar fix, dependency floor ou alteração de registries para a 2.4.0.

### Resultado, bloqueios e testes

**Atualização documental parcial, com bloqueio fail-closed da promoção física:** o intervalo publicado **2.2.0 → 2.3.0 → 2.4.0** está identificado, e mudanças *source-level* da **2.3.0** foram discriminadas. A **2.4.0** continua com delta funcional e dependências do binário **não verificáveis** sem changelog, source/tag correspondente ou comparação dos arquivos JAR. Além disso, **Better End 21.0.34 instalado não satisfaz o mínimo formal 21.0.39 da source 2.3.0**. Não tratar essas versões como plenamente auditadas/promovíveis; manter `✅-dynamic-trees-betterend v2.2.0.md`, versão física 2.2.0 e pasta oficial `Addons + World Gen`.

**Gates de QA futuros:** obter JARs exatos 2.3.0/2.4.0, inspecionar manifests/registries/resources e comparar com 2.2.0; atualizar Better End/BCLib/WorldWeaver de forma coordenada **somente após verificação**; testar startup cliente/servidor, chunks novos e antigos, saplings, árvores/fauna fúngica, mounds e rooty soil, árvores umbrella em geração/crescimento, blocos com entidade, liquids e coexistência de BetterNether; verificar ausência de geração duplicada, destruição de estruturas pré-existentes, queda de drops e inconsistências após save/reload. **Não houve edição de JAR, configuração ou teste em runtime.**

**Fontes:** [CurseForge 2.3.0](https://www.curseforge.com/minecraft/mc-mods/dynamictrees-betterend/files/9681813); [CurseForge 2.4.0](https://www.curseforge.com/minecraft/mc-mods/dynamictrees-betterend/files/9716886); [diff source 2.2.0→2.3.0](https://github.com/dannykim2011/DynamicTrees-BetterEnd/compare/911d7bbfe910b5cad91dff059fa8eea9f6baf7d6...47802eac7e0bc606c7e91705fffff7907434ba8a); [build 2.3.0](https://github.com/dannykim2011/DynamicTrees-BetterEnd/blob/47802eac7e0bc606c7e91705fffff7907434ba8a/gradle.properties); [manifest template](https://github.com/dannykim2011/DynamicTrees-BetterEnd/blob/47802eac7e0bc606c7e91705fffff7907434ba8a/src/main/resources/META-INF/neoforge.mods.toml).
