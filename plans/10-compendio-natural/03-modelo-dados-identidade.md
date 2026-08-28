# 10.03 — Modelo de dados, identidade e providers

## Objetivo

Definir uma representação canônica para qualquer entrada do Compêndio antes de implementar descoberta, UI ou adapters específicos.

## Contrato de identidade

Toda entrada deve possuir identidade estável formada por:

```text
CompendiumEntryId(kind, resourceLocation)
```

Exemplos conceituais:

```text
ENTITY:minecraft:axolotl
TREE:terrafirmacraft:douglas_fir
STRUCTURE:minecraft:ancient_city
BIOME:minecraft:lush_caves
```

`ResourceLocation` é autoridade técnica. Nome traduzido nunca é chave de save.

## Tipos previstos

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryKind.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryId.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntry.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumSection.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumFact.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactSource.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactConfidence.java
```

### `CompendiumEntry`

Campos mínimos:

- `id`;
- `sourceModId`;
- `translationKey`;
- `categoryIds`;
- `sections`;
- `relations`;
- `discoveryPolicy`;
- `visibilityPolicy`;
- `provenance`;
- `contentVersion`.

### `CompendiumFact`

Um fato não deve ser somente uma string solta. Deve carregar:

- `factKey` estável;
- valor tipado quando possível;
- unidade;
- fonte;
- confiança/status;
- visibilidade;
- opcionalmente timestamp/snapshot quando o valor vier de runtime.

Fontes previstas:

- `REGISTRY`;
- `RUNTIME_ENTITY`;
- `LOOT_TABLE`;
- `DATAPACK`;
- `RESOURCE_PACK`;
- `ADAPTER`;
- `CURATED_EDITORIAL`;
- `MOD_LOCALIZATION`;
- `UNKNOWN`.

Confiança prevista:

- `EXACT`;
- `DERIVED`;
- `CONTEXTUAL`;
- `UNAVAILABLE`.

A UI deve ser capaz de diferenciar um valor exato de um valor dependente de contexto.

## Plano

### A — Registry canônico do Compêndio

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalog.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogSnapshot.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogBuilder.java
```

- [ ] catálogo publicado como snapshot imutável;
- [ ] lookup por `CompendiumEntryId`;
- [ ] lookup por namespace/mod;
- [ ] lookup por categoria;
- [ ] aliases nunca substituem o ID canônico;
- [ ] reload só publica snapshot novo depois de validação completa.

### B — Providers independentes

Criar API:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/CompendiumProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderContext.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderResult.java
```

Providers devem enriquecer uma entrada sem possuir o catálogo inteiro.

Ordem de composição planejada:

1. descoberta genérica por registry;
2. dados vanilla/runtime seguros;
3. datapack/resource pack;
4. adapters específicos de mods;
5. corpus editorial curado;
6. preferências/notas do jogador, apenas na apresentação.

Conflitos precisam de política determinística e diagnósticos; `last write wins` silencioso é proibido.

### C — Relações

Suportar arestas tipadas entre entradas:

- `SPAWNS_IN`;
- `FOUND_IN_STRUCTURE`;
- `BELONGS_TO_DIMENSION`;
- `EATS`;
- `ATTRACTED_BY`;
- `BREEDS_WITH_ITEM`;
- `DROPS`;
- `PREDATOR_OF`;
- `PREY_OF`;
- `GROWS_IN`;
- `TREE_VARIANT_OF`;
- `CROP_PRODUCT`;
- `RELATED_ENTRY`.

Relações sem evidência não devem ser inferidas editorialmente como fato.

### D — Schema data-driven

Recursos previstos:

```text
src/main/resources/data/rpgskilltree/compendium/entries/*.json
src/main/resources/data/rpgskilltree/compendium/categories/*.json
src/main/resources/data/rpgskilltree/compendium/relations/*.json
src/main/resources/data/rpgskilltree/compendium/discovery/*.json
```

O schema precisa de versão explícita e mensagens de erro com arquivo + campo.

### E — Extensão por terceiros

Definir API pública pequena e estável para:

- registrar provider;
- fornecer fatos adicionais;
- fornecer categorias/relações;
- acrescentar discovery trigger permitido;
- acrescentar renderer/section somente no cliente, sem quebrar dedicated server.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumEntryIdTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogBuilderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderMergeTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaTest.java
```

- [ ] dois kinds podem compartilhar a mesma `ResourceLocation` sem colisão;
- [ ] nome traduzido pode mudar sem quebrar save;
- [ ] provider ausente não remove entrada base;
- [ ] conflito entre providers gera resultado determinístico/diagnóstico;
- [ ] reload inválido mantém snapshot anterior;
- [ ] dado `UNAVAILABLE` não é exibido como fato confirmado.

## Acceptance

O subplano fecha quando catálogo, identidade, fact model, merge de providers e schemas estiverem implementados e testados, sem depender da UI final.
