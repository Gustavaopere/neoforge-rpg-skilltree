# Stage 10.03 Compendium Model Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement the canonical identity, fact model, immutable catalog snapshots, deterministic provider composition, relations and versioned data schemas required by `plans/10-compendio-natural/03-modelo-dados-identidade.md`.

**Architecture:** The Stage 10.03 API remains registry-first and independent from UI/discovery. Canonical identity is `CompendiumEntryKind + resource location`, where the resource location is stored as a validated canonical string so the pure-Java contract tests remain independent from a Minecraft runtime; NeoForge registry adapters convert actual `ResourceLocation` values at the boundary. Catalog publication is atomic: builders validate a complete staging snapshot, and `CompendiumCatalog` replaces the current snapshot only after successful construction. Provider conflicts use explicit priorities and diagnostics instead of silent last-write-wins.

**Tech Stack:** Java 21, NeoForge 1.21.1, Gson already supplied by the Minecraft/NeoForge runtime, shell-based pure-Java contract tests, GitHub Actions CI.

**Spec:** `plans/10-compendio-natural/03-modelo-dados-identidade.md`

## Global Constraints

- Minecraft NeoForge 1.21.1 and Java 21.
- `BLOCK_FEATURE` is part of the canonical `CompendiumEntryKind` from the first stable Stage 10.03 API revision.
- Runtime registry identity wins over translated/display names.
- Provider absence is fail-soft.
- Provider conflicts are deterministic and diagnostic; silent last-write-wins is forbidden.
- Invalid reload data must not replace the previous valid snapshot.
- No UI/client-only dependency is introduced in this stage.

---

### Task 1: Canonical identity and fact API

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryKind.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryId.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactSource.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactConfidence.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/FactVisibility.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumFact.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEntryIdTest.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumFactTest.java`

**Interfaces:**
- Produces `CompendiumEntryId.of(CompendiumEntryKind, String)`, stable `serializedId()`, and immutable typed facts.

- [ ] Write tests proving same resource location with different kinds does not collide, translated names are irrelevant to identity, malformed resource locations are rejected, and `UNAVAILABLE` facts cannot be treated as confirmed values.
- [ ] Run the new contract test and capture RED.
- [ ] Implement the minimal identity/fact API.
- [ ] Run tests and capture GREEN.

### Task 2: Entry, sections, relations and policies

**Files:**
- Create `CompendiumSection.java`, `CompendiumRelationType.java`, `CompendiumRelation.java`, `DiscoveryPolicy.java`, `VisibilityPolicy.java`, `CompendiumProvenance.java`, `CompendiumEntry.java` under `compendium/api/`.
- Test `CompendiumEntryTest.java`.

**Interfaces:**
- Produces immutable entry objects with canonical id, source mod id, translation key, categories, sections, relations, discovery/visibility policies, provenance, and content version.

- [ ] Test defensive copies, relation validation, content version validation and stable ID independent from display metadata.
- [ ] Implement immutable records/classes with validation.
- [ ] Run tests.

### Task 3: Immutable catalog and atomic publication

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogSnapshot.java`
- Create `CompendiumCatalogBuilder.java`
- Create `CompendiumCatalog.java`
- Test `CompendiumCatalogBuilderTest.java`

**Interfaces:**
- Produces lookup by `CompendiumEntryId`, namespace/mod and category.
- Produces `CompendiumCatalog.publish(builder)` semantics that retain the previous snapshot when validation fails.

- [ ] Test duplicate canonical IDs fail, two kinds with same resource location coexist, namespace/category lookups are immutable, and invalid publish retains previous snapshot.
- [ ] Implement validated immutable indexes and atomic snapshot replacement.
- [ ] Run tests.

### Task 4: Deterministic provider composition

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/CompendiumProvider.java`
- Create `ProviderContext.java`, `ProviderContribution.java`, `ProviderDiagnostic.java`, `ProviderResult.java`, `ProviderMerger.java`.
- Test `ProviderMergeTest.java`.

**Interfaces:**
- Providers have stable `providerId()` and integer `priority()`.
- Contributions may add facts/categories/relations without owning the catalog.
- Equal-priority conflicting facts produce an explicit conflict diagnostic and deterministic winner by provider id; higher priority wins explicitly.

- [ ] Test absent providers preserve base entry, deterministic ordering, explicit conflict diagnostics and no silent mutation.
- [ ] Implement provider merger.
- [ ] Run tests.

### Task 5: Versioned schemas and validation

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumDataKind.java`
- Create `CompendiumSchemaException.java`, `CompendiumSchemaValidator.java`.
- Create representative resources under `src/main/resources/data/rpgskilltree/compendium/{entries,categories,relations,discovery}/`.
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaTest.java`.

**Interfaces:**
- Every document requires `schema_version: 1`.
- Validation errors include logical file id and field path.

- [ ] Test missing/unsupported schema version, missing required fields, unknown root type and valid documents.
- [ ] Implement validation without publishing partial state.
- [ ] Run tests.

### Task 6: CI integration and regression

**Files:**
- Create `scripts/compendium/test_model_catalog.sh`.
- Modify `.github/workflows/alpha2-build.yml` to run Stage 10.03 model tests before NeoForge build.

- [ ] Run all new pure-Java contract tests through one shell script.
- [ ] Ensure existing Stage 10.01/10.02 tests remain unchanged and passing.
- [ ] Verify NeoForge build, JAR structure and dedicated-server smoke through PR CI.
- [ ] Review diff against the Stage 10.03 acceptance before merge.
