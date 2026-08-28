# 10.02 — Inventário do modpack e cobertura de conteúdo

## Objetivo

Transformar a modlist e os registries efetivamente carregados em um inventário reproduzível de conteúdo enciclopédico. O runtime continua sendo a autoridade; a modlist é snapshot auxiliar para proveniência e drift.

## Escopo entregue

### A — Modlist top-level

- [x] implementar `scripts/compendium/inventory_modlist.py`;
- [x] validar `Mods count: N` e falhar fechado em divergência;
- [x] preservar filename, mod id, nome, versão runtime e hashes disponíveis;
- [x] separar mods top-level de dependências `META-INF/jarjar/` e `META-INF/jars/`;
- [x] aceitar os paths JAR-in-JAR com ou sem `/` inicial;
- [x] associar dependência embarcada ao mod pai sem promovê-la a conteúdo editorial;
- [x] gerar SHA-256 dos bytes exatos do snapshot para detecção de drift;
- [x] gerar JSON e Markdown do snapshot.

`published_version` permanece nulo quando a fonte não distingue esse valor de forma confiável da versão runtime; o parser não fabrica versão a partir do filename.

### B — Registries runtime

- [x] coletar `ENTITY_TYPE`;
- [x] classificar blocos naturais relevantes como `FLORA`, `TREE` ou `CROP`;
- [x] coletar `BIOME`;
- [x] coletar `STRUCTURE`;
- [x] coletar dimensões via contrato de registry seguro para 1.21.1;
- [x] emitir `kind`, `resource_location`, `namespace`, `translation_key`, `mod_display_name`, `registry_source` e `present_at_runtime`;
- [x] registrar mods carregados, versões runtime e fingerprint SHA-256 determinístico;
- [x] manter a coleta opt-in e bounded, executada no startup do servidor quando `RPGSKILLTREE_COMPENDIUM_INVENTORY=1` estiver definido.

Implementação principal:

```text
src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeRegistryInventoryCollector.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeInventoryReportWriter.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/BlockCatalogClassifier.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumInventoryEvents.java
```

### C — Cobertura explícita

- [x] modelar `AUTO`, `CURATED`, `ADAPTER`, `IGNORED` e `ERROR`;
- [x] garantir exatamente um estado por entrada;
- [x] manter entrada inválida visível como `ERROR`, em vez de descartá-la;
- [x] exigir motivo para `IGNORED`/`ERROR` definidos por override;
- [x] rejeitar override para ID ausente do runtime;
- [x] preservar fallback `AUTO` para conteúdo desconhecido válido.

### D — Relatório e drift

- [x] implementar `scripts/compendium/inventory_runtime_report.py`;
- [x] gerar resumo por namespace/mod com entidades, flora, árvores, cultivos, biomas, estruturas e dimensões;
- [x] gerar listas individuais preservando todos os IDs;
- [x] detectar mods adicionados/removidos;
- [x] detectar registry IDs adicionados/removidos;
- [x] registrar removidos também como `orphaned_registry_entries`, sem autorizar deleção silenciosa de progresso;
- [x] implementar `scripts/compendium/generate_inventory.py` como pipeline único.

## Testes e TDD

Cobertura automatizada integrada:

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/ModpackInventoryTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CoverageClassifierTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/RegistryInventoryTest.java
scripts/compendium/test_inventory_modlist.py
scripts/compendium/test_inventory_runtime_report.py
scripts/compendium/test_inventory_catalog.sh
```

- [x] namespace vanilla é enumerado;
- [x] namespace/mod opcional presente aparece sem hard dependency;
- [x] mod opcional ausente não quebra startup;
- [x] JAR embarcado não é contado como top-level;
- [x] entrada desconhecida recebe cobertura explícita em vez de desaparecer;
- [x] `IGNORED` sem motivo falha;
- [x] drift entre snapshots é relatado;
- [x] dedicated-server smoke produz e valida inventário runtime;
- [x] build NeoForge e estrutura do JAR passam.

### Evidência TDD final

| Caso | RED | GREEN |
| --- | --- | --- |
| catálogo/cobertura inicial | run `33188383144` / #761 | implementação subsequente |
| parser da modlist | run `33188692575` / #777 | run `33188970925` / #785 |
| relatório runtime/cobertura | run `33189170451` / #787 | run `33189317745` / #789 |
| JAR-in-JAR sem `/` inicial | run `33190400921` / #802 | run `33190515169` / #803 |

## Evidência integrada

```text
Implementation head: 9016cbd23f742185d6182c0fae9cac0922ec852d
Merged PR: #59
Merge commit: 259eb2d4310f5c7c84dcf0db1a5c3c5f6522df1d
PR CI: 33190515169 / run #803
Post-merge main CI: 33190772894 / run #805
Compendium inventory tests: success
NeoForge build: success
Built JAR verification: success
Dedicated-server smoke: success
Acceptance: satisfied
```

## Snapshot real do pack

A fonte conhecida continua sendo `modlist agora atual.txt`, com 553 entradas top-level. O código não versiona uma falsa fotografia do pack nem trata esse número como presença eterna. O artefato integral deve ser regenerado na instância real sempre que o conjunto de mods mudar; essa execução operacional permanece rastreada em `PENDING.md` para o gate de conteúdo, sem reabrir o contrato de tooling deste subplano.

## Acceptance

**Satisfied.** Existe uma execução reproduzível que separa a modlist top-level, enumera os registries suportados do runtime carregado, atribui cobertura explícita a cada entrada válida, preserva erros/orphans e gera relatórios JSON/Markdown. O pipeline foi integrado e revalidado na `main` com dedicated-server smoke.