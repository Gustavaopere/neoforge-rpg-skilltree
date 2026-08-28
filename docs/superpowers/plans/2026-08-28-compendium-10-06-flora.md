# Stage 10.06 Compendium Flora Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement automatic, fail-soft Compendium coverage for flora, crops and tree species without classifying decorative blocks as plants or duplicating one tree species across its component blocks.

**Architecture:** Keep botanical classification and species grouping as pure Java contracts under `compendium/flora`. Runtime NeoForge adapters under `runtime/compendium` may inspect block registries, stable vanilla block classes and tags, but must not depend on translation-key/name heuristics as the primary rule. Specialized ecosystems such as TFC and Dynamic Trees are represented by optional adapter contracts that consume stable IDs/tags and remain safe when those mods are absent. Runtime flora catalog publication stays isolated from the existing entity catalog so one domain cannot overwrite another; unified presentation/composition remains a later Compendium concern.

**Tech Stack:** Java 21, Minecraft 1.21.1, NeoForge 21.1.x, pure-Java contract tests, GitHub Actions focused Compendium CI, existing `CompendiumEntry`/`CompendiumCatalog` model.

**Spec:** `plans/10-compendio-natural/06-flora-arvores-cultivos.md`

## Global Constraints

- Minecraft NeoForge 1.21.1 and Java 21.
- `FLORA`, `TREE`, `CROP` and `BLOCK_FEATURE` are canonical entry kinds; fungus/aquatic are editorial categories, not new save-key kinds.
- Runtime registry/tag/class evidence outranks naming heuristics; translation key/file name never acts as the primary classifier.
- Decorative green blocks must remain unclassified when evidence is insufficient.
- Tree entries group species components rather than creating independent Compendium species pages for log/leaves/sapling variants.
- Optional TFC/Dynamic Trees behavior is fail-soft and must not introduce hard compile-time dependencies on those mods.
- No arbitrary world block placement, random tick execution or crop simulation is allowed during catalog collection.
- Dedicated-server code may not load client-only classes.

---

### Task 1: Pure flora classifier

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraKind.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraClassificationEvidence.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraClassification.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/FloraClassifier.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraClassifierTest.java`

**Interfaces:**
- Consumes: registry ID, stable class flags and tag IDs represented as strings/booleans.
- Produces: deterministic `FloraClassification` with canonical entry kind, editorial categories, evidence and ambiguity diagnostics.

- [ ] **Step 1: Write the failing classifier test** covering flower, fungus, aquatic flora, crop, explicit tree component, ambiguous decorative block and explicit ignore.
- [ ] **Step 2: Run `scripts/compendium/test_flora.sh` and verify RED** because classifier production types do not exist.
- [ ] **Step 3: Implement minimal classifier** with evidence precedence `override/tag > stable class > component relation`; never classify from translation key alone.
- [ ] **Step 4: Re-run focused flora tests and verify GREEN.**
- [ ] **Step 5: Commit classifier contract.**

### Task 2: Canonical flora and crop entries

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraFactKeys.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraSpeciesFacts.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/FloraRegistryProvider.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/CropProvider.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/CropProviderTest.java`

**Interfaces:**
- Consumes: classified registry descriptors and verified crop metadata.
- Produces: immutable `CompendiumEntry` pages with `FLORA` or `CROP` identity, source namespace, related block/item IDs and verified growth metadata only.

- [ ] **Step 1: Write failing tests** proving unknown growth time is omitted, max age is preserved when known, seed/produce relations remain typed facts, and fungus/aquatic categories stay editorial.
- [ ] **Step 2: Verify RED.**
- [ ] **Step 3: Implement minimal providers.**
- [ ] **Step 4: Verify GREEN plus existing model tests.**
- [ ] **Step 5: Commit flora/crop entry model.**

### Task 3: Tree species grouping

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/TreeComponentRole.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/TreeComponent.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/TreeSpeciesDescriptor.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/flora/TreeProvider.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/TreeGroupingTest.java`

**Interfaces:**
- Consumes: verified component associations for sapling/log/wood/leaves/fruit/propagule.
- Produces: one canonical `TREE:<namespace>:<species>` entry with deterministic component relations; duplicate component roles are diagnosed rather than last-write-wins.

- [ ] **Step 1: Write failing grouping tests** for oak-like tree, modded namespace, stripped log not becoming a second species, duplicate/conflicting roles and deterministic ordering.
- [ ] **Step 2: Verify RED.**
- [ ] **Step 3: Implement species descriptor/provider.**
- [ ] **Step 4: Verify GREEN.**
- [ ] **Step 5: Commit grouping layer.**

### Task 4: NeoForge runtime collector and coverage

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeFloraCatalogCollector.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraCatalogCoverage.java`
- Create: `scripts/compendium/verify_flora_runtime.py`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/FloraCatalogCoverageTest.java`

**Interfaces:**
- Consumes: `BuiltInRegistries.BLOCK`, stable vanilla block classes (`CropBlock`, `SaplingBlock`, flowers/plants/fungi where public), block tags and block-item registry relations.
- Produces: bounded descriptors and coverage diagnostics without placing blocks, executing random ticks or loading client code.

- [ ] **Step 1: Add failing coverage/runtime validator** requiring registry traversal, stable-class/tag evidence and forbidding translation-key/name-primary classification, `Block#randomTick`, world placement and client imports.
- [ ] **Step 2: Verify RED.**
- [ ] **Step 3: Implement the collector using only safe registry metadata.**
- [ ] **Step 4: Run focused flora CI and full NeoForge CI; fix only API mismatches surfaced by compilation.**
- [ ] **Step 5: Commit runtime collector.**

### Task 5: Optional TFC and Dynamic Trees adapter contracts

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/integration/flora/TfcFloraAdapter.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/integration/flora/DynamicTreesFloraAdapter.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/TfcFloraAdapterTest.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/flora/DynamicTreesAdapterTest.java`

**Interfaces:**
- Consumes: loaded-mod set plus stable resource/tag descriptors supplied by runtime scanning or future public provider registration.
- Produces: optional enrichment contributions only when evidence exists; absence is a no-op and never prevents the generic page.

- [ ] **Step 1: Write failing presence/absence tests.**
- [ ] **Step 2: Verify RED.**
- [ ] **Step 3: Implement fail-soft adapters with no direct TFC/Dynamic Trees class references.**
- [ ] **Step 4: Verify GREEN with both mods represented as present/absent.**
- [ ] **Step 5: Commit optional adapters.**

### Task 6: Atomic runtime flora catalog publication

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumFloraCatalog.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumFloraCatalogEvents.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- Create: `scripts/compendium/verify_flora_catalog_publication.py`
- Create: `.github/workflows/compendium-flora.yml`

**Interfaces:**
- Consumes: collector output and pure providers/groupers.
- Produces: one immutable flora/tree/crop catalog snapshot published once after complete validation; entity catalog remains untouched.

- [ ] **Step 1: Add failing publication validator** requiring server startup registration, candidate-build-before-publish and no per-tick scan.
- [ ] **Step 2: Verify RED.**
- [ ] **Step 3: Implement runtime catalog/events and focused workflow.**
- [ ] **Step 4: Verify focused CI, full NeoForge build, JAR verification and dedicated-server smoke.**
- [ ] **Step 5: Commit publication layer.**

### Task 7: Review, reconcile and merge

- [ ] Review the entire diff against `plans/10-compendio-natural/06-flora-arvores-cultivos.md`.
- [ ] Confirm no production classifier uses file name or translation key as its primary botanical rule.
- [ ] Confirm decorative/ambiguous blocks fail closed and appear in diagnostics rather than being guessed.
- [ ] Confirm TFC/Dynamic Trees absence remains safe.
- [ ] Reconcile any concurrent `main` advancement without rewriting TDD history.
- [ ] Require focused flora CI and full RPG CI GREEN on the reconciled head.
- [ ] Merge the implementation PR with exact expected head SHA.
- [ ] Require post-merge focused/full CI GREEN on `main`.
- [ ] Close `06-flora-arvores-cultivos.md` as `✅-06-flora-arvores-cultivos.md` in a separate documentation PR only after implementation is integrated and verified.
