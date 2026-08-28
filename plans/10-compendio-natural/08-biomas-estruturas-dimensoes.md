# 10.08 — Biomas, estruturas e dimensões

## Objetivo

Criar a camada geográfica do Compêndio, permitindo navegar do conteúdo para seu habitat e do mundo para o conteúdo que ele contém.

A meta é responder perguntas como:

- onde esta criatura pode aparecer?;
- em quais biomas esta árvore/planta ocorre?;
- de qual dimensão esta estrutura faz parte?;
- quais espécies/estruturas já descobri neste bioma?;
- que conteúdo de determinado mod altera worldgen?

## Tipos cobertos

- `BIOME`;
- `STRUCTURE`;
- `DIMENSION`;
- relações de spawn/habitat entre worldgen e outras entradas.

## Plano

### A — Catálogo de biomas

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/world/BiomeProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/world/BiomeClimateFacts.java
```

Dados quando verificáveis:

- nome localizado;
- namespace/mod;
- temperatura/downfall e outros valores do registry quando semanticamente válidos;
- dimensão(ões) em que participa;
- tags relevantes;
- fauna/flora relacionada por spawn/worldgen resolvível;
- estruturas relacionadas;
- cave/surface/aquatic/etc. somente por tags/corpus confiável.

Valores de clima de mods como TFC devem ser rotulados conforme o sistema específico; não misturar temperatura vanilla com temperatura física/TFC como se fossem a mesma unidade.

### B — Catálogo de estruturas

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/world/StructureProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/world/StructurePlacementSummary.java
```

Dados quando verificáveis:

- nome pt-BR;
- namespace/mod/ID;
- dimensão;
- biomas/tags de bioma em que pode gerar;
- categoria editorial;
- frequência/placement somente quando puder ser descrita corretamente;
- mobs/bosses relacionados por adapter ou dados confiáveis;
- loot relevante por referência a loot tables, sem duplicar um recipe/loot browser inteiro;
- riscos/mecânicas especiais apenas por corpus curado com fonte;
- estruturas relacionadas/variants.

Não prometer coordenadas nem “chance exata” quando worldgen depende de spacing/separation/noise/datapacks complexos.

### C — YUNG e estruturas substituídas

O snapshot atual conhecido inclui YUNG's Cave Biomes e YUNG's Better Mineshafts. Na implementação:

- [ ] enumerar quais mods YUNG realmente estão carregados;
- [ ] distinguir estrutura vanilla substituída/alterada da estrutura totalmente nova;
- [ ] evitar páginas duplicadas se um mod apenas altera geração do ID vanilla;
- [ ] permitir descrição editorial específica de alterações quando comprovadas;
- [ ] adapters YUNG só existem onde dados genéricos não bastarem.

### D — Suites de estruturas do pack

Auditar pelo inventário runtime, quando presentes:

- Integrated Structures/IDAS;
- Structory;
- Stoneworks Structures;
- estruturas de Cataclysm/aventura/bosses;
- estruturas de dimensões;
- Stellarity e conteúdo do End;
- MineColonies/Structurize, apenas para elementos que façam sentido como descoberta do mundo;
- outros namespaces encontrados pelo 10.02.

A presença nominal só entra no código após confirmação no snapshot atual.

### E — Dimensões

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/world/DimensionProvider.java
```

Dados desejados:

- ID/nome;
- mod de origem;
- biomas conhecidos;
- estruturas;
- fauna/flora;
- regras ambientais relevantes somente quando verificáveis;
- relações de acesso/portal como texto curado/data-driven, sem tentar deduzir recipes/rituais arbitrariamente.

Cobrir vanilla e dimensões modded detectadas, incluindo famílias como Aether, Blue Skies, BetterEnd/BetterNether, Deeper and Darker e outras somente quando presentes.

### F — Descoberta geográfica

- [ ] biome descobre ao jogador realmente entrar nele;
- [ ] dimension descobre ao entrar nela;
- [ ] structure descobre quando o servidor confirma presença no bounding box/structure start aplicável;
- [ ] teleport transitório pode exigir tempo mínimo configurável;
- [ ] localização exata da primeira descoberta é opcional e com precisão limitada/configurável.

### G — Índices cruzados

Páginas devem oferecer relações navegáveis:

```text
Entidade -> Bioma -> Estruturas -> Dimensão
Árvore -> Bioma -> Dimensão
Estrutura -> Biomas -> Entidades/loot relacionado
Dimensão -> Biomas -> Estruturas -> Fauna/Flora
```

Não duplicar dados em vários JSON quando uma relação canônica pode ser referenciada.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/world/BiomeProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/world/StructureProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/world/DimensionProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/world/WorldDiscoveryTest.java
```

Casos obrigatórios:

- [ ] biome vanilla;
- [ ] cave biome;
- [ ] biome modded opcional;
- [ ] estrutura vanilla;
- [ ] estrutura modded;
- [ ] estrutura substituída sem duplicação indevida;
- [ ] dimensão modded presente/ausente;
- [ ] datapack muda worldgen e reload atualiza snapshot;
- [ ] discovery de structure é validada pelo servidor.

## Acceptance

O subplano fecha quando biomas, estruturas e dimensões registrados gerarem páginas navegáveis e relações geográficas verificáveis, sem hard dependency de suites opcionais de worldgen.
