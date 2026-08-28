# 10.06 — Flora, árvores, fungos e cultivos

## Objetivo

Cobrir vegetação do modpack com a mesma qualidade estrutural da fauna, incluindo flora simples, fungos, árvores, cultivos, plantas aquáticas e variantes de árvores dinâmicas.

## Problema

Minecraft não possui um registry zoológico/botânico unificado. Muitos conteúdos vegetais aparecem apenas como blocos/itens/tags/features. Portanto a classificação deve combinar registries, tags, comportamento, recipes/drops e adapters, sem classificar qualquer bloco decorativo verde como planta.

## Tipos editoriais

- `FLORA` — planta não agrícola;
- `TREE` — espécie arbórea;
- `CROP` — cultivo agrícola;
- `FUNGUS` — subtipo editorial de flora quando útil;
- `AQUATIC_FLORA` — subtipo editorial;
- `BLOCK_FEATURE` — elemento natural que não cabe de forma segura nos tipos acima.

## Dados por planta

Quando verificáveis:

- nome pt-BR;
- mod/namespace/ID;
- tipo;
- bloco(s) e item(s) relacionados;
- habitat/biomas/dimensão;
- substrato/solo válido;
- requisitos de luz/água/clima quando expostos;
- estágio de crescimento;
- tempo/ticks somente quando determinístico ou descrito como variável;
- drops/colheita;
- alimento/produto resultante;
- usos relevantes por recipes/tags;
- sazonalidade/clima em TFC ou outros sistemas somente via adapter confiável;
- relações ecológicas verificáveis.

## Dados por árvore

Quando verificáveis:

- espécie;
- sapling/propágulo/semente;
- tronco/log/wood;
- leaves;
- frutos/resinas/outros produtos;
- biomas/habitat;
- substrato e condições de crescimento;
- forma vanilla/feature relacionada quando resolvível;
- contraparte Dynamic Trees quando existir;
- compatibilidade TFC/clima/chuva/temperatura quando o mod expuser contrato estável;
- usos do material sem enumerar receitas irrelevantes em excesso.

## Plano

### A — Classificador de blocos vegetais

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/FloraClassifier.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/FloraRegistryProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/CropProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/TreeProvider.java
```

Fontes de classificação:

1. tags conhecidas e datapack configurável;
2. tipos/classes vanilla estáveis;
3. relações bloco-item;
4. adapters de mods;
5. overrides do corpus.

- [ ] não depender de nome do arquivo/translation key como regra principal;
- [ ] manter blacklist/ignore apenas por ID explícito quando necessário;
- [ ] diagnosticar classificação ambígua.

### B — Agrupamento de espécie

Uma árvore pode envolver dezenas de IDs. O Compêndio deve agrupá-los em uma entrada de espécie e criar relações para seus componentes, em vez de páginas desconexas para log, stripped log, leaves e sapling.

Exemplo conceitual:

```text
TREE:<namespace>:<species>
  -> sapling
  -> log
  -> wood
  -> leaves
  -> fruit
  -> dynamic_tree_family
```

O ID de espécie customizado precisa de migração se não existir ID canônico upstream.

### C — Dynamic Trees

Quando Dynamic Trees estiver presente:

- [ ] detectar famílias/espécies pela API pública disponível;
- [ ] mapear árvore dinâmica para espécie normal correspondente;
- [ ] não duplicar a mesma espécie como duas entradas independentes por padrão;
- [ ] expor diferenças de crescimento apenas quando verificáveis;
- [ ] isolar adapter para ausência segura.

### D — TerraFirmaCraft

Quando TFC estiver presente:

- [ ] usar APIs/tags/dados oficiais para clima, chuva, temperatura e solo quando disponíveis;
- [ ] distinguir árvore, arbusto/fruta, cultivo e flora selvagem;
- [ ] mostrar requisitos sazonais/climáticos sem inventar intervalos;
- [ ] integrar relações com alimentos/colheita sem acoplar o core do Compêndio ao TFC.

### E — Mods de biomas e flora

O scanner do 10.02 deve cobrir automaticamente namespaces de mods como Biomes O' Plenty, Nature's Spirit, BYG, Wilder Wild, flora cavernícola/dimensional e outros presentes.

Para cada família:

- [ ] gerar páginas base;
- [ ] agrupar espécies de árvore;
- [ ] associar biomas/dimensões quando o worldgen permitir resolução confiável;
- [ ] marcar entradas que exigem adapter;
- [ ] produzir corpus pt-BR para as entradas prioritárias.

### F — Cultivos e Farmer's Delight/ecossistema

- [ ] detectar `CropBlock` e equivalentes;
- [ ] mapear seed/propágulo -> planta -> colheita;
- [ ] mostrar estágio máximo e condições somente quando estáveis;
- [ ] não transformar o Compêndio em recipe browser; usos culinários devem ser resumo/relações, deixando JEI/EMI para receitas completas;
- [ ] adapters agrícolas opcionais devem ser fail-soft.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraClassifierTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/TreeGroupingTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/CropProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/DynamicTreesAdapterTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/TfcFloraAdapterTest.java
```

Casos obrigatórios:

- [ ] flor vanilla;
- [ ] fungo;
- [ ] planta aquática;
- [ ] cultivo;
- [ ] árvore vanilla agrupada;
- [ ] árvore modded;
- [ ] Dynamic Trees presente e ausente;
- [ ] TFC presente e ausente;
- [ ] bloco decorativo semelhante a planta não é classificado sem base suficiente;
- [ ] entrada ambígua aparece no relatório de cobertura.

## Acceptance

O subplano fecha quando o catálogo consegue representar flora, árvores e cultivos do pack sem duplicação estrutural grosseira e com adapters opcionais para ecossistemas especializados.
