# 10.03 — Modelo de dados, identidade e providers

## Objetivo

Definir e implementar a representação canônica para qualquer entrada do Compêndio antes dos estágios de descoberta, UI e adapters específicos.

## Escopo entregue

### A — Identidade canônica e fact model

- [x] implementar `CompendiumEntryKind` como enum canônico de identidade;
- [x] incluir `BLOCK_FEATURE` desde a primeira revisão estável do modelo;
- [x] implementar `CompendiumEntryId(kind, resourceLocation)` sem depender de nome traduzido;
- [x] validar namespace/path de `ResourceLocation` em representação pura Java;
- [x] fornecer `serializedId()` estável e `parse(...)` com round-trip testado;
- [x] permitir que dois kinds compartilhem o mesmo `ResourceLocation` sem colisão;
- [x] implementar `CompendiumFact` com valor tipado, unidade, fonte, confiança, visibilidade e snapshot/timestamp opcional;
- [x] impedir que `FactConfidence.UNAVAILABLE` seja tratado como fato confirmado.

Tipos principais:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryKind.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryId.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumFact.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactSource.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactConfidence.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactVisibility.java
```

`ResourceLocation` continua sendo a autoridade técnica. Nome traduzido e metadata editorial podem mudar sem alterar a identidade persistível.

### B — Entrada, seções, relações e políticas

- [x] implementar `CompendiumEntry` imutável;
- [x] preservar `id`, `sourceModId`, `translationKey`, categorias, seções, relações, discovery/visibility policy, proveniência e `contentVersion`;
- [x] copiar defensivamente coleções recebidas;
- [x] rejeitar `contentVersion <= 0`;
- [x] normalizar IDs de categoria e rejeitar categoria vazia;
- [x] rejeitar seções duplicadas por ID;
- [x] implementar relações tipadas com fonte e confiança explícitas;
- [x] impedir relação marcada como evidência indisponível de se apresentar como fato válido.

Relações canônicas suportadas:

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

### C — Catálogo canônico e publicação atômica

Implementação:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalog.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogSnapshot.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogBuilder.java
```

- [x] catálogo publicado como snapshot imutável;
- [x] lookup por `CompendiumEntryId`;
- [x] lookup por namespace/mod;
- [x] lookup por categoria;
- [x] aliases permanecem resolução auxiliar e nunca substituem o ID canônico;
- [x] IDs canônicos duplicados são rejeitados;
- [x] dois kinds diferentes podem usar o mesmo `ResourceLocation`;
- [x] publicação só troca o snapshot depois de `build()`/validação integral;
- [x] falha de validação mantém o snapshot anterior intacto.

`CompendiumCatalog.publish(builder)` constrói primeiro o snapshot validado e somente então substitui a referência `volatile`, evitando estado parcialmente publicado.

### D — Providers independentes e merge determinístico

Implementação:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/CompendiumProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderContext.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderContribution.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderDiagnostic.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderResult.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderMerger.java
```

- [x] provider enriquece uma entrada sem possuir o catálogo inteiro;
- [x] ausência de provider preserva a entrada base;
- [x] prioridade maior vence conflito explicitamente;
- [x] conflito de mesma prioridade possui desempate determinístico por `providerId`;
- [x] conflito de fato gera diagnóstico `FACT_CONFLICT`;
- [x] ordem de entrada dos providers não altera o resultado;
- [x] `providerId` duplicado é rejeitado para impedir ambiguidade dependente da ordem;
- [x] contribuição pode acrescentar fatos, categorias e relações;
- [x] `last write wins` silencioso não é usado.

A ordem editorial planejada para providers continua sendo implementada pelos estágios consumidores; este subplano entrega o mecanismo de composição e seus invariantes, não adapters concretos.

### E — Schema data-driven versionado

Implementação:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumDataKind.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaException.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaValidator.java
```

Recursos representativos:

```text
src/main/resources/data/rpgskilltree/compendium/entries/pig.json
src/main/resources/data/rpgskilltree/compendium/categories/fauna.json
src/main/resources/data/rpgskilltree/compendium/relations/pig_overworld.json
src/main/resources/data/rpgskilltree/compendium/discovery/pig.json
```

- [x] todo documento exige `schema_version: 1`;
- [x] versão ausente/unsupported falha fechada;
- [x] campos obrigatórios são validados por tipo de documento;
- [x] erros carregam arquivo lógico + caminho do campo;
- [x] fixtures versionadas são validadas no gate do CI;
- [x] documento inválido não publica estado parcial no catálogo.

### F — Extensibilidade: fronteira deste subplano

- [x] estabelecer `CompendiumProvider` como seam público mínimo para fatos/categorias/relações;
- [x] manter internals de catálogo/snapshot independentes de qualquer mod opcional;
- [x] não introduzir referência client-only no modelo base;
- [x] reservar registro público global, eventos, discovery triggers e renderer extensions para os estágios que possuem esses runtimes.

A especificação original previa também registro de discovery trigger e renderer/section. Eles **não foram antecipados artificialmente** neste subplano porque descoberta e UI ainda não existem. A API pública de registro/eventos é propriedade explícita de `10.11 — Integrações, adapters e extensibilidade`, enquanto triggers são concretizados a partir de 10.04 e renderers em 10.09. Isso evita criar uma segunda API temporária que depois precisaria ser quebrada.

## Testes e TDD

Cobertura adicionada:

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryIdTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumFactTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogBuilderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderMergeTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaTest.java
scripts/compendium/test_model_catalog.sh
```

Casos aceitos:

- [x] dois kinds compartilham a mesma `ResourceLocation` sem colisão;
- [x] identidade serializada faz round-trip;
- [x] nome traduzido muda sem mudar a identidade;
- [x] coleção recebida é copiada defensivamente;
- [x] provider ausente não remove entrada base;
- [x] conflito entre providers é determinístico e diagnosticado;
- [x] provider duplicado é rejeitado;
- [x] reload/publicação inválida mantém snapshot anterior;
- [x] dado `UNAVAILABLE` não é fato confirmado;
- [x] schemas inválidos incluem arquivo + campo;
- [x] recursos data-driven de exemplo passam validação;
- [x] gates anteriores 10.01/10.02 continuam passando;
- [x] build NeoForge e dedicated-server smoke continuam passando.

### Evidência RED → GREEN

| Ciclo | Evidência |
| --- | --- |
| RED inicial do modelo | `33193330246` / run #813 — testes novos chegaram antes das classes de produção e falharam no gate do catálogo |
| GREEN funcional intermediário | `33194086672` / run #850 — modelo/providers + regressões + build + smoke passaram |
| RED de hardening | `33194396355` / run #854 — `CompendiumEntryId.parse(...)` inexistente provocou falha de compilação esperada |
| GREEN final do branch | `33194589524` / run #857 — Core, todos os gates do Compêndio, validators, NeoForge build, JAR e dedicated-server smoke passaram |

## Evidência integrada

```text
Implementation head: 52094d8252b92ba0a15db2d57de9d8c1b0a0ae0f
Merged PR: #66
Merge commit: 112d9266de9ece584f2f58adff03ffb6c8776ca6
PR CI: 33194589524 / run #857
Post-merge main CI: 33195224667 / run #858
Compendium model/provider tests: success
Existing Compendium gates: success
NeoForge build: success
Built JAR verification: success
Dedicated-server smoke: success
Artifact upload: success
Acceptance: satisfied
```

## Acceptance

**Satisfied.** Catálogo, identidade persistível, fact model, relações, merge determinístico de providers e schemas versionados estão implementados e testados sem depender da UI final. A implementação foi integrada na `main` e revalidada no CI pós-merge.