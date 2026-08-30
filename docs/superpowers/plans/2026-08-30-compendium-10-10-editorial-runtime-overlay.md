# Compêndio Natural 10.10 — Runtime Editorial Overlay Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use `superpowers:subagent-driven-development` (recommended) or `superpowers:executing-plans` to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Integrar o corpus editorial pt-BR ao runtime Java do Compêndio como overlay separado dos fatos técnicos, com decoder fail-closed, snapshot imutável, publicação server-authoritative no startup e projeção segura até `CompendiumPageModel`/`CompendiumClientSnapshot`, sem implementar o transporte/reload completo reservado ao Stage 10.13.

**Architecture:** Manter `CompendiumEntry`/`CompendiumFact` e `ProviderMerger` como camada técnica. Criar domínio editorial puro em `compendium/editorial`; manter Gson, `ResourceManager` e lifecycle NeoForge em `runtime/compendium`, preservando o runner headless `scripts/compendium/test_model_catalog.sh`. O loader converte explicitamente IDs editoriais `KIND:namespace:path` para `CompendiumEntryId(KIND, namespace:path)`, valida o candidato completo contra os catálogos técnicos e só então publica um snapshot editorial atômico. A página recebe editorial separado e filtra referências contra o conjunto autorizado de IDs do snapshot cliente.

**Tech Stack:** Java 21, Minecraft 1.21.1, NeoForge 21.1, Gson/Minecraft resource APIs já presentes no runtime, GitHub Actions.

**Spec:** `docs/superpowers/specs/2026-08-30-compendium-10-10-editorial-runtime-overlay-design.md`

## Global constraints

- O schema runtime deve permanecer semanticamente equivalente a `scripts/compendium/editorial_corpus.py`: `schema=1`, `language=pt_br`, mesmos kinds suportados, fontes, status de revisão, disponibilidade e regras de referência.
- O corpus editorial 10.10 suporta somente `ENTITY`, `FLORA`, `TREE`, `CROP`, `BIOME`, `STRUCTURE` e `DIMENSION`; `BLOCK_FEATURE` continua fora desse contrato mesmo existindo em `CompendiumEntryKind`.
- IDs do corpus usam `KIND:namespace:path`; `CompendiumEntryId.parse()` usa `KIND|namespace:path`. A conversão editorial deve ser explícita e testada, nunca feita por substituição silenciosa ou parsing acidental.
- Não colocar Gson/Minecraft imports no domínio puro `compendium/editorial`: `scripts/compendium/test_model_catalog.sh` compila toda essa árvore com `javac --release 21` sem classpath NeoForge/Gson.
- Não transformar prosa editorial em `CompendiumFact<String>` e não inserir referências editoriais em `CompendiumEntry.relations()`.
- Não alterar precedência/semântica de `ProviderMerger`.
- Ausência de corpus é estado válido e publica snapshot editorial vazio.
- Um candidato editorial inválido nunca substitui o último snapshot válido e nunca invalida um catálogo técnico já publicado.
- Esta fatia não registra listener de `/reload`, não cria protocolo/rede/hash/cache e não instala snapshots de produção em `ClientCompendiumState`; essas responsabilidades permanecem no 10.13.
- A UI só consome dados já presentes em `CompendiumPageModel`; nunca acessa `ResourceManager` ou arquivos editoriais diretamente.
- Conteúdo editorial oculto por descoberta/visibilidade não pode vazar por título, resumo, seção, fonte ou referência.
- Sem nova arquitetura de paginação/scroll longo nesta fatia. Renderização física deve ser mínima e determinística sobre o modelo composto existente.
- Todo comportamento novo começa em RED e só recebe produção depois da falha esperada estar confirmada.

---

### Task 1: Domínio editorial puro e contrato de página

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/EditorialAvailability.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/EditorialReviewStatus.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/EditorialSourceType.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialSource.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialBlock.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialSection.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialContent.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialSnapshot.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialValidationException.java`
- Create: `src/test/java/dev/gustavopere/rpgskilltree/compendium/editorial/CompendiumEditorialModelTest.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumPageModel.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumPageModelFactory.java`
- Modify: `src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumPageModelFactoryTest.java`
- Modify: `scripts/compendium/test_model_catalog.sh`

- [ ] Escrever primeiro `CompendiumEditorialModelTest` cobrindo normalização/imutabilidade, source type, section IDs, `availability_reason`, duplicate snapshot IDs e lookup O(1) por `CompendiumEntryId`.
- [ ] Estender `CompendiumPageModelFactoryTest` antes da produção para exigir: overlay com ID correspondente; ausência de overlay preserva fallback; overlay com ID divergente falha; `HIDE_DETAILS_UNTIL_DISCOVERED` remove todo editorial; fatos técnicos permanecem `equals`-iguais; referências editoriais só permanecem quando o target pertence ao conjunto autorizado.
- [ ] Adicionar os novos testes ao `test_model_catalog.sh` e executar `bash scripts/compendium/test_model_catalog.sh`; capturar RED exclusivamente por classes/campos/overloads ainda inexistentes.
- [ ] Abrir PR draft neste RED para preservar a evidência do ciclo, caso ainda não exista PR para o branch.
- [ ] Implementar o mínimo domínio puro. Contrato de conteúdo:

```java
public record CompendiumEditorialContent(
    CompendiumEntryId entryId,
    String title,
    CompendiumEditorialBlock summary,
    List<CompendiumEditorialSection> sections,
    List<CompendiumEntryId> references,
    EditorialReviewStatus reviewStatus,
    EditorialAvailability availability,
    String availabilityReason
) { }
```

- [ ] `CompendiumEditorialContent` deve normalizar coleções com `List.copyOf`, exigir texto não vazio, exigir `availabilityReason` somente em `OPTIONAL`/`LEGACY` e oferecer `withReferences(List<CompendiumEntryId>)` para projeção fail-closed sem mutar o original.
- [ ] Implementar snapshot com API `empty()`, `fromEntries(Collection<CompendiumEditorialContent>)`, `entries()` e `find(CompendiumEntryId)`; ordenar por `id.serializedId()` e rejeitar duplicatas.
- [ ] Estender `CompendiumPageModel` com `Optional<CompendiumEditorialContent> editorialContent`; o construtor canônico exige `Optional` não nulo e construtores de compatibilidade delegam com `Optional.empty()`.
- [ ] Preservar `CompendiumPageModelFactory.create(entry, clientEntry, admin)` e fazê-lo delegar para novo overload editorial.
- [ ] Implementar overload com identidade e autorização explícitas:

```java
public static Optional<CompendiumPageModel> create(
    CompendiumEntry entry,
    CompendiumClientEntry clientEntry,
    boolean admin,
    Optional<CompendiumEditorialContent> editorial,
    Set<CompendiumEntryId> authorizedEntryIds
)
```

- [ ] No overload: rejeitar editorial com ID divergente; se `detailsVisible == false`, projetar `Optional.empty()`; quando visível, filtrar `references()` por `authorizedEntryIds`; nunca alterar `entry.sections()` nem relações técnicas.
- [ ] Rodar `bash scripts/compendium/test_model_catalog.sh` e exigir GREEN focado antes de seguir.
- [ ] Commit GREEN separado, mantendo evidência RED anterior.

---

### Task 2: Decoder/validator JSON de resources no runtime

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumEditorialResourceLoader.java`
- Create: `src/test/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumEditorialResourceLoaderJUnitTest.java`

**Placement refinement:** o design conceitual chama a unidade de `CompendiumEditorialResourceLoader`, mas a implementação fica em `runtime/compendium` porque ela depende de Gson, `ResourceLocation`, `ResourceManager` e `Resource`. O domínio editorial permanece puro e reutilizável; isso evita quebrar o compilador headless do Compêndio.

- [ ] Escrever JUnit RED para `prepare(...)` cobrindo: corpus vazio; pacote válido; `schema != 1`; idioma diferente de `pt_br`; namespace físico/declarado divergente; kind inválido/`BLOCK_FEATURE`; kind divergente do entry ID; namespace divergente; ID editorial malformado; duplicate ID entre resources; título/resumo/seção vazios; placeholders; source type inválido; source ref vazio/reticências; `availability` ausente; `RUNTIME` presente/ausente; `OPTIONAL`/`LEGACY` ausente com motivo; `OPTIONAL`/`LEGACY` mascarando ID presente; referência resolvida/não resolvida.
- [ ] Testar explicitamente a conversão `ENTITY:minecraft:zombie` -> `CompendiumEntryId.of(ENTITY, "minecraft:zombie")`, sem usar `CompendiumEntryId.parse()` diretamente.
- [ ] Executar somente o JUnit focado e capturar RED antes da produção:

```bash
./gradlew --no-daemon test --tests '*CompendiumEditorialResourceLoaderJUnitTest'
```

- [ ] Implementar primeiro função determinística de candidato:

```java
public static CompendiumEditorialSnapshot prepare(
    Map<ResourceLocation, JsonElement> resources,
    Collection<CompendiumEntry> technicalEntries
)
```

- [ ] `prepare` deve ordenar resources por `ResourceLocation`, validar todos os documentos e todas as referências antes de construir o snapshot final; nenhuma publicação/global state ocorre aqui.
- [ ] Implementar adapter de resource pack/datapack:

```java
public static CompendiumEditorialSnapshot load(
    ResourceManager resourceManager,
    Collection<CompendiumEntry> technicalEntries
)
```

- [ ] `load` deve usar `listResources("compendium/editorial/pt_br", path -> path.getPath().endsWith(".json"))`, aceitar somente resources do namespace `rpgskilltree`, parsear via `JsonParser.parseReader`, preservar o `ResourceLocation` no diagnóstico e delegar toda validação semântica a `prepare`.
- [ ] Extrair o primeiro segmento após `compendium/editorial/pt_br/` como namespace físico do pacote e exigir igualdade com `payload.namespace`.
- [ ] Replicar exatamente os source types offline: `RUNTIME`, `DATAPACK`, `OFFICIAL_DOCS`, `OFFICIAL_CODE`, `OFFICIAL_CHANGELOG`, `VERIFIED_COMMUNITY`.
- [ ] Rejeitar `TODO`, `TBD`, `FIXME`, `PLACEHOLDER` em texto/note/reason e rejeitar também source ref literal de reticências.
- [ ] Rodar o JUnit focado até GREEN e depois `./gradlew --no-daemon test` para detectar incompatibilidades com o restante do runtime.
- [ ] Commit GREEN separado.

---

### Task 3: Snapshot server-authoritative e publicação atômica no startup

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumEditorialCatalog.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumEditorialCatalogEvents.java`
- Create: `src/test/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumEditorialCatalogJUnitTest.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`

- [ ] Escrever JUnit RED para snapshot inicial vazio, publicação válida, segunda publicação válida, candidato inválido preservando snapshot anterior e primeira falha mantendo snapshot vazio.
- [ ] Testar que somente `CompendiumEditorialValidationException` é tratada como falha editorial recuperável; `NullPointerException`/bug de programação não deve ser engolido.
- [ ] Criar primitive testável de publicação, sem Minecraft server obrigatório:

```java
static PublicationResult tryPublish(Supplier<CompendiumEditorialSnapshot> candidateFactory)
```

```java
public record PublicationResult(
    boolean published,
    CompendiumEditorialSnapshot snapshot,
    String diagnostic
) { }
```

- [ ] Implementar entry point runtime que recebe `ResourceManager` + technical entries, chama o loader e reutiliza `tryPublish`.
- [ ] Manter `CURRENT` como referência `volatile` para snapshot imutável; construir candidato inteiro antes de trocar a referência.
- [ ] Criar helper no evento para consolidar os IDs técnicos atuais a partir de `RuntimeCompendiumEntityCatalog.snapshot().entries()`, `RuntimeCompendiumFloraCatalog.snapshot().entries()` e `RuntimeCompendiumWorldCatalog.snapshot().entries()`.
- [ ] Registrar `CompendiumEditorialCatalogEvents` em `RpgSkillTreeMod` após os eventos de entidade/flora/world.
- [ ] Usar `@SubscribeEvent(priority = EventPriority.LOWEST)` em `ServerStartedEvent` para que os catálogos técnicos de prioridade padrão já estejam publicados.
- [ ] Em sucesso, emitir `RuntimeDiagnostics.info(..., Category.COMPENDIUM, "editorial_catalog_published", "Compendium editorial catalog published with {} entries", count)`.
- [ ] Em falha editorial validada, emitir `RuntimeDiagnostics.warn(..., Category.COMPENDIUM, "editorial_catalog_rejected", ...)` e preservar snapshot anterior; não lançar a validação ao servidor.
- [ ] Rodar JUnit focado e `./gradlew --no-daemon test`; exigir GREEN antes do próximo task.
- [ ] Commit GREEN separado.

---

### Task 4: Projeção editorial determinística e wiring mínimo da UI

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumEditorialDisplayModel.java`
- Create: `src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumEditorialDisplayModelTest.java`
- Modify: `scripts/compendium/test_model_catalog.sh`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/CompendiumScreen.java`
- Modify: `src/main/resources/assets/rpgskilltree/lang/pt_br.json`
- Modify: `src/main/resources/assets/rpgskilltree/lang/en_us.json`

- [ ] Escrever RED puro para display model: sem editorial usa `page.displayName()` e zero blocos; com editorial usa título editorial; resumo vem primeiro; seções mantêm ordem; cada bloco expõe source refs; nenhuma source/reference é convertida em `CompendiumFact`.
- [ ] Adicionar `CompendiumEditorialDisplayModelTest` ao runner headless e capturar RED.
- [ ] Implementar modelo puro:

```java
public record CompendiumEditorialDisplayModel(
    String title,
    List<DisplayBlock> blocks
) {
    public record DisplayBlock(String sectionId, String text, List<String> sourceRefs) { }
}
```

- [ ] `CompendiumEditorialDisplayModel.from(CompendiumPageModel)` deve produzir título fallback e blocos imutáveis; resumo usa sectionId estável `summary` e vem antes das seções editoriais.
- [ ] Fazer `CompendiumScreen` usar `CompendiumEditorialDisplayModel.from(page)` dentro do detalhe; não acessar resources diretamente.
- [ ] Renderizar título editorial quando disponível; renderizar resumo e seções como prosa separada dos fatos técnicos; manter preview, debug, Notes e Relations funcionando.
- [ ] Não criar novo painel/scroll subsystem. Se o body físico não comportar todo o texto, renderizar apenas linhas que cabem no viewport atual, sem desenhar fora do retângulo e sem truncar/mutar o modelo armazenado.
- [ ] Adicionar chave pt-BR `screen.rpgskilltree.compendium.editorial.sources`: `Fontes: %s`.
- [ ] Adicionar paridade en-US `screen.rpgskilltree.compendium.editorial.sources`: `Sources: %s`.
- [ ] Se títulos de seção precisarem de label próprio nesta fatia, usar o `sectionId` somente como fallback técnico e não inventar traduções sem corpus/locale correspondente.
- [ ] Executar `bash scripts/compendium/test_model_catalog.sh` para validar display model + locale parity; executar `./gradlew --no-daemon test` para compile/wiring da tela.
- [ ] Commit GREEN separado.

---

### Task 5: CI editorial/runtime, smoke, reconciliação, review e merge

**Files:**
- Modify: `.github/workflows/compendium-editorial-ci.yml`
- Modify: `.github/workflows/alpha2-build.yml`
- Modify: `plans/10-compendio-natural/10-ptbr-corpus-editorial.md`

- [ ] Expandir os `paths` do `Compendium Editorial CI` para cobrir o domínio/editorial Java, page-model/display, runtime editorial, testes editoriais, tela e locales relevantes.
- [ ] Adicionar Java 21 e Gradle cache ao workflow editorial, preservando o teste Python atual.
- [ ] O workflow editorial deve executar exatamente estes gates focados:

```bash
python3 scripts/compendium/test_editorial_corpus.py
bash scripts/compendium/test_model_catalog.sh
./gradlew --no-daemon test --tests '*Editorial*'
```

- [ ] Ajustar `timeout-minutes` do workflow editorial para acomodar o Gradle focado sem transformar o workflow em cópia do aggregate.
- [ ] Atualizar o dedicated-server smoke do aggregate para também exigir a linha `Compendium editorial catalog published with ` antes de declarar startup completo. Não exigir contagem > 0; corpus vazio é válido.
- [ ] Atualizar o plano 10.10 marcando apenas infraestrutura realmente entregue: domínio Java editorial, decoder runtime, snapshot/publicação de startup, page-model projection e smoke. Manter abertos corpus real em escala, validação de stats mutáveis, QA linguística e transporte/reload 10.13.
- [ ] Rodar verificação completa disponível antes do HEAD candidato:

```bash
bash scripts/compendium/test_model_catalog.sh
python3 scripts/compendium/test_editorial_corpus.py
./gradlew --no-daemon test
./gradlew --no-daemon runGameTestServer
./gradlew --no-daemon build
```

- [ ] Fazer auto-review do diff contra a spec: nenhum `CompendiumFact<String>` editorial, nenhuma alteração de precedência de provider, nenhum listener `/reload`, nenhum pacote de rede/cache, nenhuma referência editorial vazando para target ausente do conjunto autorizado.
- [ ] Listar review threads e corrigir qualquer P1/P2 com ciclo RED→GREEN separado antes de mergear.
- [ ] Conferir `main` imediatamente antes da validação final. Como `main` já avançou durante o planejamento, auditar qualquer PR interveniente por sobreposição de arquivos e usar o merge-ref corrente como árvore canônica de CI.
- [ ] Exigir no HEAD final: `Compendium Editorial CI`, todos os workflows especializados aplicáveis e `RPG Skill Tree CI` GREEN, incluindo JUnit, GameTests, todos os validadores, build, JAR e dedicated-server smoke.
- [ ] Se o PR estiver draft e a transição ready-for-review continuar afetada pelo bug do conector, fechar o draft e recriar o mesmo head/base como PR não-draft; revalidar integralmente o novo merge-ref.
- [ ] Fazer merge com `expected_head_sha` do HEAD exatamente validado.
- [ ] Confirmar `main` no SHA de merge e PR em estado `merged=true`.

## Acceptance checklist desta implementação

- [ ] Modelo editorial Java é separado de `CompendiumFact`/`ProviderMerger`.
- [ ] Decoder runtime aceita o mesmo contrato editorial offline e rejeita divergências fail-closed.
- [ ] Conversão `KIND:namespace:path` -> `CompendiumEntryId` é explícita/testada.
- [ ] `BLOCK_FEATURE` editorial é rejeitado nesta versão do schema.
- [ ] Snapshot editorial é imutável, determinístico e indexado por ID.
- [ ] Publicação inválida preserva snapshot anterior; primeira falha mantém vazio.
- [ ] Catálogos técnicos permanecem válidos mesmo quando editorial é rejeitado.
- [ ] Startup server publica/rejeita editorial com diagnóstico estável e é observado pelo smoke.
- [ ] `CompendiumPageModel` carrega editorial separado e não altera fatos técnicos.
- [ ] Descoberta/visibilidade remove todo editorial quando detalhes não são autorizados.
- [ ] Referências editoriais são filtradas por IDs autorizados e não viram relações técnicas.
- [ ] `CompendiumClientSnapshot` aceita a página composta sem criar transporte live.
- [ ] Tela usa apenas o modelo composto e oferece pt-BR para texto próprio novo.
- [ ] `Compendium Editorial CI` cobre contratos Python, domínio/page-model headless e JUnit editorial runtime.
- [ ] Nenhuma responsabilidade do Stage 10.13 foi implementada por antecipação.
- [ ] CI completo do merge-ref final está GREEN.
- [ ] PR é mergeado na `main` com SHA esperado e confirmado após o merge.
