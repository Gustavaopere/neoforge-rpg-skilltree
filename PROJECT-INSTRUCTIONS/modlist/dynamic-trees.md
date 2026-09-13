# Dynamic Trees

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db819685b7ecd3ff89713d  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees
- **Arquivo JAR:** `dynamictrees-neoforge-1.21.1-1.7.2.jar`
- **Versão 1.21.1:** `1.7.2`
- **Categoria:** Worldgen; QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamictrees
- **Função:** Engine principal de árvores dinâmicas: substitui árvores estáticas suportadas por redes de branches/leaves que crescem, usam rooty soil, propagam seeds, podem ser derrubadas como estrutura e integram worldgen por species/families/treepacks.
- **Dependências:** NeoForge 1.21.1. É base do stack Dynamic Trees instalado: Dynamic Trees Plus 1.3.2, Dynamic Trees Addon Lib 0.2.0-BETA03 e bridges para BetterEnd, BetterNether, BWG, Quark, Terralith e VanillaBackport.
- **Compatibilidade/Riscos:** Core de worldgen/growth altamente acoplado a treepacks. Riscos: árvore estática+dinâmica duplicada por canceller falho, species/family/data drift, rooty soil inválido, felling/loot duplicado, treepacks em baseline diferente e seams entre chunks antigos/novos. DT Plus 1.3.2 e alguns bridges possuem baselines distintos do runtime 1.7.2.
- **Sobreposição:** É o provider-base do sistema arbóreo dinâmico. Addons/treepacks não o substituem; adaptam outros providers. Sobreposição real ocorre com geração estática/worldgen/tree-felling concorrente, que precisa de cancellation/precedence explícita.
- **Observações:** Runtime 1.7.2. A release adiciona/expande expressões de chance/density no worldgen e reduz erros redundantes de treepacks ausentes. O stack atual inclui Plus, Addon Lib e múltiplos bridges; histórico TFC/ArborFirmaCraft permanece apenas histórico e não define o runtime atual.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dynamictrees-neoforge-1.21.1-1.7.2.jar`, mod id `dynamictrees` e versão 1.7.2. Publicação/wiki oficial da linha 1.7.2 confirma expression-based worldgen chance/density, feature cancellers e contratos do ecossistema.
- **Histórico da decisão:** Uma revisão anterior sugeriu substituir Dynamic Trees pelo stack ArborFirmaCraft/TFC. Essa leitura foi corrigida depois: ArborFirmaCraft e Dynamic Trees não cumprem a mesma função. O usuário manteve Dynamic Trees + addons porque o objetivo do Dynamic Trees é alterar crescimento/forma/comportamento das árvores, enquanto ArborFirmaCraft adiciona/expande conteúdo de árvores/madeiras no ecossistema TFC. Portanto não tratar os dois como substitutos diretos.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — core 1.7.2, species/families, growth/rooty soil, seeds/felling, worldgen expressions/cancellers, treepacks, lifecycle/MP, integrations, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dynamictrees-neoforge-1.21.1-1.7.2.jar` · mod id `dynamictrees` · versão `1.7.2` · NeoForge 1.21.1.

## 1. Papel no modpack
Dynamic Trees é o **engine arbóreo central** do pack. Ele substitui o modelo de árvore estática por uma estrutura viva baseada em species/families, branches, leaves, rooty soil, seeds, growth logic, worldgen e felling.

Os muitos addons instalados não são engines paralelos: eles adaptam conteúdo de outros mods ao core.

## 2. Authority / ownership
- **Dynamic Trees:** growth network, branches/leaves, species/families, rooty soils, seeds, tree felling e tree worldgen replacement.
- **Biome/worldgen mods:** continuam donos dos biomas, blocos e conteúdo original que bridges adaptam.
- **Treepacks/bridges:** definem adapters/species/dados específicos para o provider-alvo.
- **Mods externos de felling/worldgen:** precisam respeitar hooks/cancellers para não processar a mesma árvore duas vezes.

## 3. Species e families
O modelo do Dynamic Trees separa a definição biológica/funcional em species e family. Isso permite que growth, leaves, branch properties, seed behavior e worldgen sejam data-driven e extensíveis.

Integrações devem usar ResourceLocations do registry/data layer em vez de inferir uma species pelo bloco visual final.

## 4. Branch network e crescimento
Árvores dinâmicas crescem por uma rede de branches que aumenta e ramifica ao longo do tempo. Growth logic decide direção e comportamento, podendo ser estendida por addons.

Alterações externas de blocos da árvore precisam preservar a coerência da branch network; quebrar segmentos arbitrariamente pode deixar estrutura inválida ou disparar felling incorreto.

## 5. Leaves / canopy
Leaves participam de um sistema de suporte/células em torno dos branches, em vez de serem apenas folhas vanilla colocadas por uma feature estática. Cell kits e leaves properties podem ser especializados por addons.

Resource/data reload e update de treepacks precisam ser tratados como possíveis invalidadores de canopy behavior.

## 6. Rooty soil
O solo raiz é parte do estado de crescimento. Bridges podem registrar rooty soils correspondentes aos blocos de seus biome mods.

Ferramentas de construção, copy/paste ou world-editing que troquem somente o bloco superficial podem quebrar a relação entre species e solo. Para integração crítica, validar o provider real e seus tags/types.

## 7. Seeds e propagação
Seeds são a unidade de plantio/propagação do sistema. Treepacks podem substituir drops, seeds ou regras de placement. O servidor deve ser authority de plantio, growth e drops.

Loot scripts externos não devem adicionar uma segunda seed para o mesmo evento sem intenção explícita.

## 8. Felling
O core suporta derrubada da árvore como estrutura dinâmica. Tree-felling externo é uma superfície de sobreposição relevante: se ambos processarem branches/loot, pode ocorrer double loot, entidades de queda duplicadas ou network órfã.

Regression gate: felling manual, ferramenta externa compatível, dois jogadores concorrentes e restart durante estado parcial.

## 9. Worldgen replacement
Dynamic Trees substitui geração estática por árvores dinâmicas onde existe integração. A infraestrutura usa worldgen configs e **feature cancellers** para impedir que a feature original seja executada junto da dinâmica.

A wiki oficial documenta cancellers para categorias como tree, rooted tree, mushroom e fungus; addons podem fornecer cancellers adicionais. Falha de canceller normalmente aparece como árvore estática + dinâmica coexistindo no mesmo bioma.

## 10. Worldgen 1.7.2 — chance/density expressions
A linha 1.7.2 amplia o sistema de worldgen com expressões para chance/density, permitindo composição mais flexível do placement. Isso aumenta a capacidade de tuning, mas também torna datapacks antigos sensíveis a mudanças de expressão/schema.

A mesma release remove/reduz erros redundantes sobre treepacks ausentes, o que não deve ser confundido com validação automática de todos os consumers.

## 11. Treepacks e data-driven content
O ecossistema é fortemente data-driven. Species, families, leaves, soils, gen features, worldgen configs e cancellers podem vir de resources/datapacks dos addons.

Consequência operacional: um JAR carregar sem crash não prova que todas as species resolveram. Missing IDs/tags podem degradar worldgen sem impedir boot.

## 12. Stack físico atual
O pack contém, entre outros:
- Dynamic Trees Plus `1.3.2`;
- Dynamic Trees Addon Lib `0.2.0-BETA03`;
- BetterEnd bridge `2.2.0`;
- BetterNether bridge `2.2.0`;
- BWG bridge `1.1.0-BETA02`;
- Quark bridge `2.6.1`;
- Terralith bridge `1.3.0`;
- VanillaBackport bridge `1.6.0`.

Cada bridge possui seu próprio baseline de source; a combinação física atual deve ser validada como conjunto.

## 13. Dynamic Trees Plus
Plus estende o core com tipos vegetais especiais, como cacti e huge mushrooms. O runtime instalado é 1.3.2 e a release inclui correção para a linha Dynamic Trees 1.7.x.

Entretanto, os sources dos bridges BetterEnd/BetterNether 2.2.0 usam DTP 1.5.0 como baseline; isso foi registrado como regression gate, não como incompatibilidade já comprovada.

## 14. Addon Lib
Addon Lib centraliza gen features, growth logic kits, cell kits e custom types consumidos por treepacks. O core continua authority do engine; a library apenas disponibiliza contratos reutilizáveis.

## 15. Client / Server
Worldgen, growth, felling, soil, seeds e drops são server-authoritative. Models/branches/leaves presentation são client-facing.

Dedicated server não deve depender de render classes, e cliente não deve decidir growth ou loot.

## 16. Lifecycle
Validar:
- bootstrap/registries;
- criação de mundo;
- geração de chunks novos;
- plantio de seed;
- crescimento parcial/completo;
- chunk unload/reload;
- felling;
- save/restart;
- datapack reload;
- dimension travel;
- atualização de treepack/core;
- geração após update em chunks novos vs antigos.

## 17. Multiplayer / idempotência
Growth e felling precisam liquidar uma vez no servidor. Dois jogadores derrubando a mesma árvore não podem duplicar drops. Seeds e tree state devem convergir após reconnect.

Um cliente sem state visual atualizado pode renderizar incorretamente, mas isso não deve alterar a tree network server-side.

## 18. Performance
Árvores dinâmicas possuem state e updates mais ricos que árvores estáticas. Densidade excessiva, muitos addons e growth simultâneo podem aumentar custo de tick/worldgen.

Performance deve ser medida no pack real; não usar número genérico de árvores/tick como verdade sem profiler.

## 19. Riscos
1. static+dynamic duplication;
2. feature canceller não atingir alvo atualizado;
3. species/family ResourceLocation drift;
4. rooty soil inválido;
5. leaves/cell state inconsistente;
6. tree-felling double loot;
7. seed/drop duplication;
8. treepack compilado contra API diferente;
9. worldgen expression/data schema drift;
10. seams entre chunks antigos e novos;
11. performance regressions por densidade/growth;
12. reload deixar cache/data stale.

## 20. Matriz de testes
1. Dedicated server boot com stack Dynamic Trees completo.
2. Mundo novo: geração vanilla suportada sem árvores estáticas duplicadas.
3. Plantar e crescer species base em amostra controlada.
4. Felling manual e com qualquer sistema externo habilitado.
5. Dois jogadores felling a mesma árvore.
6. Chunk unload/reload durante growth.
7. Restart com árvores parcialmente crescidas.
8. Datapack reload.
9. Testar cada bridge instalada em bioma/dimensão correspondente.
10. Verificar seeds/drops sem duplicação.
11. Comparar chunk antigo e novo após update.
12. Profile de tick/worldgen em área de alta densidade.

**Esta catalogação não afirma que esses testes foram executados.**

## 21. Evidências
- modlist física canônica de 08/09/2026: runtime 1.7.2 e stack de addons/bridges;
- documentação/wiki oficial Dynamic Trees: species/families, worldgen e feature cancellers;
- release oficial 1.7.2: expression-based chance/density e ajustes de treepack diagnostics.

> **Boundary canônico:** Dynamic Trees é authority da **engine de árvores dinâmicas**. Bridges adaptam conteúdo; worldgen providers continuam donos dos biomas/blocos de origem.
