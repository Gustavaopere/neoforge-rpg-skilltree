# Volcanoes Universal Material Chemistry Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Audit the complete NeoForge 1.21.1 modpack for material-bearing providers, build an evidence-backed canonical material catalog, integrate exact real substances with Destroy without duplicating its chemistry authority, and render category-correct scientific or explicitly arcane material representations throughout RPG/Volcanoes client surfaces.

**Architecture:** The implementation introduces a data-driven canonical material catalog with provider aliases, a strict scientific/fictional taxonomy, optional read-only provider adapters, a Destroy bridge for exact native identities, category-specific render models, and deterministic coverage validation. Provider-owned gameplay remains authoritative; the catalog stores identity, provenance, and visualization metadata only. Fictional materials use a separate arcane signature model and are never injected into real chemistry.

**Tech Stack:** Minecraft 1.21.1, NeoForge, Java 21, existing `Gustavaopere/neoforge-rpg-skilltree` runtime, Gson/Codec-style data loading as already used by the project, NeoForge optional-mod detection/events, JUnit/GameTest infrastructure already present in the repository, Destroy 0.4.1 integration, JEI integration only through hooks proven available in the installed runtime.

**Spec:** `docs/specs/2026-09-06-volcanoes-universal-material-chemistry-design.md`

## Global Constraints

- Minecraft version is exactly `1.21.1`.
- Loader is NeoForge.
- Java version is `21`.
- Runtime work belongs in `Gustavaopere/neoforge-rpg-skilltree`; the historical standalone Volcanoes repository is not a target.
- Installed Destroy baseline is `destroy-1.21.1-0.4.1.jar` and Destroy remains authoritative for Destroy-native species, reactions, mixtures, hazards, and thermodynamic data it exposes.
- Never invent a real formula, composition, atom/bond graph, species mapping, provider identity, API, registry, tag, method, or version.
- Fictional materials must never receive fake chemistry. Use explicit arcane/runic representation instead.
- Hybrid materials may expose documented real constituents and fictional constituents separately, but must not collapse them into one synthetic chemical formula.
- Provider aliases require evidence of equivalence; names and tags alone are insufficient proof.
- Optional integrations fail closed and must not create hard dependencies unless the pack already requires that dependency.
- No global per-tick registry/world/recipe scans.
- Client render code must not leak into dedicated-server classloading.
- Scientific data must retain provenance.
- Copied/adapted third-party code or data requires license/provenance review and notice updates before inclusion.
- Final implementation is not complete until applicable tests/build/smokes are green, the PR is merged, and `main` is revalidated post-merge.

---

## Planned File Structure

The implementation should use the following focused structure. If an existing package with the same responsibility is discovered at execution time, place the new types there rather than creating a duplicate package; the public type names and responsibilities below remain the contract.

### Core catalog

- `src/main/java/com/gustavo/rpgskilltree/material/MaterialKind.java` — primary taxonomy enum.
- `src/main/java/com/gustavo/rpgskilltree/material/MaterialSupportState.java` — supported/blocked state enum.
- `src/main/java/com/gustavo/rpgskilltree/material/DestroyMappingKind.java` — Destroy relationship enum.
- `src/main/java/com/gustavo/rpgskilltree/material/CanonicalMaterialId.java` — stable validated material ID value object.
- `src/main/java/com/gustavo/rpgskilltree/material/ProviderMaterialRef.java` — provider namespace/resource identity.
- `src/main/java/com/gustavo/rpgskilltree/material/ScientificSourceRef.java` — provenance record.
- `src/main/java/com/gustavo/rpgskilltree/material/MaterialDefinition.java` — immutable canonical material record.
- `src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalog.java` — read-only lookup/index API.
- `src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalogLoader.java` — data/resource loading and validation orchestration.
- `src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidator.java` — invariant enforcement.

### Scientific representation

- `src/main/java/com/gustavo/rpgskilltree/material/science/ChemicalFormula.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/AtomNode.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/BondEdge.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/MolecularGraph.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/CompositionBasis.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/CompositionComponent.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/MaterialComposition.java`
- `src/main/java/com/gustavo/rpgskilltree/material/science/PolymerRepeatUnit.java`

### Arcane representation

- `src/main/java/com/gustavo/rpgskilltree/material/arcane/ArcaneRuneId.java`
- `src/main/java/com/gustavo/rpgskilltree/material/arcane/ArcaneSignatureNode.java`
- `src/main/java/com/gustavo/rpgskilltree/material/arcane/ArcaneSignatureEdge.java`
- `src/main/java/com/gustavo/rpgskilltree/material/arcane/ArcaneMaterialSignature.java`

### Provider integration

- `src/main/java/com/gustavo/rpgskilltree/material/provider/MaterialProviderAdapter.java` — read-only adapter interface.
- `src/main/java/com/gustavo/rpgskilltree/material/provider/MaterialProviderRegistry.java` — optional adapter registration/lookup.
- `src/main/java/com/gustavo/rpgskilltree/material/provider/MaterialCandidate.java` — normalized audit/runtime candidate.
- `src/main/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialAdapter.java`
- `src/main/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialBridge.java`
- Additional provider adapters are added only after each mod's real API/registry contract is proven during the audit.

### Visualization

- `src/main/java/com/gustavo/rpgskilltree/material/client/MaterialRenderModel.java`
- `src/main/java/com/gustavo/rpgskilltree/material/client/MaterialRenderModelFactory.java`
- `src/main/java/com/gustavo/rpgskilltree/material/client/MaterialTooltipAugmenter.java`
- `src/main/java/com/gustavo/rpgskilltree/material/client/render/Molecule2DRenderer.java`
- `src/main/java/com/gustavo/rpgskilltree/material/client/render/CompositionChartRenderer.java`
- `src/main/java/com/gustavo/rpgskilltree/material/client/render/ArcaneSignatureRenderer.java`
- JEI/Ponder-specific classes are created only after hook/API verification and must live under client-only optional integration packages.

### Resources and manifests

- `src/main/resources/data/rpgskilltree/materials/catalog/*.json`
- `src/main/resources/data/rpgskilltree/materials/providers/*.json`
- `src/main/resources/data/rpgskilltree/materials/scientific/*.json`
- `src/main/resources/data/rpgskilltree/materials/arcane/*.json`
- `src/main/resources/data/rpgskilltree/materials/audit/modlist-audit.json`
- `src/main/resources/assets/rpgskilltree/materials/runes/*.json`

### Tests and documentation

- `src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java`
- `src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java`
- `src/test/java/com/gustavo/rpgskilltree/material/MaterialRenderModelFactoryTest.java`
- `src/test/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialBridgeTest.java`
- `src/test/java/com/gustavo/rpgskilltree/material/arcane/ArcaneMaterialSignatureTest.java`
- `docs/audits/2026-09-06-material-provider-audit.md`
- `docs/audits/2026-09-06-material-catalog-coverage.md`
- `docs/integrations/2026-09-06-destroy-material-bridge.md`
- `THIRD_PARTY_NOTICES.md` when third-party source/data is reused.

---

### Task 1: Freeze the canonical audit baseline and enumerate the modlist

**Files:**
- Create: `docs/audits/2026-09-06-material-provider-audit.md`
- Create: `src/main/resources/data/rpgskilltree/materials/audit/modlist-audit.json`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java`

**Interfaces:**
- Consumes: current canonical `modlist.txt` supplied to the project plus matching Notion records.
- Produces: a machine-readable set of audited mod IDs/JAR identities and a human-readable provider audit ledger.

- [ ] **Step 1: Write a failing baseline coverage test**

Create `MaterialCoverageTest` with a test that loads `modlist-audit.json`, asserts every audit entry has nonblank `jar`, `modId`, `version`, and `disposition`, and compares the manifest count against the exact canonical modlist snapshot count captured during execution.

```java
@Test
void everyCanonicalModHasAuditDisposition() {
    ModlistAudit audit = ModlistAudit.load(TEST_RESOURCE_MANAGER);
    assertEquals(EXPECTED_CANONICAL_MOD_COUNT, audit.entries().size());
    assertTrue(audit.entries().stream().allMatch(ModAuditEntry::isComplete));
}
```

Do not guess `EXPECTED_CANONICAL_MOD_COUNT`; derive it from the canonical modlist snapshot used by the implementation run and commit that number together with the audit snapshot metadata.

- [ ] **Step 2: Run the focused test and confirm it fails**

Run the project's existing unit-test command for `MaterialCoverageTest`. Expected result: failure because `ModlistAudit`, `ModAuditEntry`, and/or the manifest do not exist yet.

- [ ] **Step 3: Audit every modlist row into one of four dispositions**

Use these exact disposition values in the JSON schema:

```text
MATERIAL_PROVIDER
MATERIAL_CONSUMER_ONLY
NO_ELIGIBLE_MATERIALS
BLOCKED_AUDIT
```

For each mod, record:

```json
{
  "jar": "example-1.0.0.jar",
  "modId": "example",
  "version": "1.0.0",
  "disposition": "MATERIAL_PROVIDER",
  "notionRecord": "https://app.notion.com/...",
  "evidence": ["provider source/data path or official documentation reference"],
  "notes": "why this disposition is correct"
}
```

The human-readable Markdown audit must include the same mod set and totals by disposition.

- [ ] **Step 4: Implement the minimal audit manifest loader used by tests**

Add small immutable records under the core material package only if the project does not already have a generic audit manifest loader:

```java
public record ModAuditEntry(
    String jar,
    String modId,
    String version,
    ModAuditDisposition disposition,
    String notionRecord,
    List<String> evidence,
    String notes
) {
    public boolean isComplete() {
        return !jar.isBlank() && !modId.isBlank() && !version.isBlank() && disposition != null;
    }
}
```

`ModlistAudit` must expose `List<ModAuditEntry> entries()` and fail on duplicate `modId + version + jar` tuples.

- [ ] **Step 5: Run the focused test and fix only baseline-manifest defects**

Expected: PASS with the committed canonical count and zero incomplete entries.

- [ ] **Step 6: Commit**

```bash
git add docs/audits/2026-09-06-material-provider-audit.md src/main/resources/data/rpgskilltree/materials/audit/modlist-audit.json src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java src/main/java/com/gustavo/rpgskilltree/material

git commit -m "docs: freeze material provider audit baseline"
```

---

### Task 2: Introduce the canonical taxonomy and identity value objects

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/MaterialKind.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/MaterialSupportState.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/DestroyMappingKind.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/CanonicalMaterialId.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/ProviderMaterialRef.java`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java`

**Interfaces:**
- Produces: exact enums/value objects used by every later task.

- [ ] **Step 1: Write failing tests for taxonomy and ID validation**

Tests must assert:

```java
assertThrows(IllegalArgumentException.class, () -> new CanonicalMaterialId(""));
assertThrows(IllegalArgumentException.class, () -> new CanonicalMaterialId("Copper"));
assertEquals("copper", new CanonicalMaterialId("copper").value());
assertEquals("destroy:sulfur_dioxide", new ProviderMaterialRef("destroy", "sulfur_dioxide").asString());
```

Canonical IDs must use lowercase `[a-z0-9_./-]+`; provider references must use valid lowercase namespace/path semantics compatible with Minecraft resource IDs.

- [ ] **Step 2: Run focused tests and confirm failure**

Expected: FAIL because the types do not exist.

- [ ] **Step 3: Implement exact enums**

```java
public enum MaterialKind {
    REAL_ELEMENT,
    REAL_MOLECULE,
    REAL_MINERAL,
    REAL_ALLOY,
    REAL_MIXTURE,
    REAL_POLYMER,
    FICTIONAL_MATERIAL,
    HYBRID_MATERIAL,
    UNKNOWN
}
```

```java
public enum MaterialSupportState {
    SUPPORTED_REAL,
    SUPPORTED_FICTIONAL,
    SUPPORTED_HYBRID,
    BLOCKED_IDENTITY,
    BLOCKED_COMPOSITION,
    BLOCKED_PROVIDER,
    BLOCKED_LICENSE
}
```

```java
public enum DestroyMappingKind {
    DESTROY_NATIVE,
    DESTROY_EXACT_ALIAS,
    SCIENTIFIC_DISPLAY_ONLY,
    NO_DESTROY_MAPPING,
    BLOCKED_DESTROY_MAPPING,
    ARCANE_DISPLAY_ONLY
}
```

- [ ] **Step 4: Implement immutable validated IDs**

Use records with constructor validation and no registry lookups.

- [ ] **Step 5: Run tests**

Expected: PASS.

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java
git commit -m "feat: add canonical material taxonomy"
```

---

### Task 3: Add scientific and provenance data primitives

**Files:**
- Create all files listed under **Scientific representation** and `ScientificSourceRef.java`.
- Test: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java`

**Interfaces:**
- Produces:
  - `ChemicalFormula(String plainText, String displayText)`
  - `MolecularGraph(List<AtomNode> atoms, List<BondEdge> bonds)`
  - `MaterialComposition(CompositionBasis basis, List<CompositionComponent> components)`
  - `PolymerRepeatUnit(ChemicalFormula formula, MolecularGraph graph)`
  - `ScientificSourceRef(String sourceType, String citation, String locator)`

- [ ] **Step 1: Write failing invariant tests**

Required cases:

```java
assertThrows(IllegalArgumentException.class,
    () -> new MaterialComposition(CompositionBasis.MASS_FRACTION, List.of()));

assertThrows(IllegalArgumentException.class,
    () -> new BondEdge(0, 0, 1));

assertThrows(IllegalArgumentException.class,
    () -> new MolecularGraph(List.of(new AtomNode("C", 0, 0)), List.of(new BondEdge(0, 1, 1))));
```

Also test composition fractions sum to `1.0 ± 1e-6` when the basis is fractional and exact fractions are supplied.

- [ ] **Step 2: Run tests and confirm failure**

- [ ] **Step 3: Implement the minimal immutable scientific model**

Use element symbols as strings only after validating syntax `[A-Z][a-z]?`; do not embed a periodic table database in this task.

`BondEdge` must store integer atom indices and an integer order of `1`, `2`, or `3` initially. Aromatic/display-special bonds are not added until a real required provider case proves the need.

`CompositionBasis` values:

```java
MASS_FRACTION,
MOLE_FRACTION,
VOLUME_FRACTION,
ATOMIC_COUNT,
PROVIDER_NATIVE
```

- [ ] **Step 4: Run tests**

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/science src/main/java/com/gustavo/rpgskilltree/material/ScientificSourceRef.java src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java
git commit -m "feat: add scientific material data model"
```

---

### Task 4: Add the arcane signature model for fictional materials

**Files:**
- Create all files listed under **Arcane representation**.
- Create: `src/main/resources/assets/rpgskilltree/materials/runes/base_runes.json`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/arcane/ArcaneMaterialSignatureTest.java`

**Interfaces:**
- Produces `ArcaneMaterialSignature(List<ArcaneSignatureNode> nodes, List<ArcaneSignatureEdge> edges, String theme, List<ScientificSourceRef> loreSources)`.

- [ ] **Step 1: Write failing tests**

Tests must reject:

- empty signatures;
- edges referencing nonexistent nodes;
- duplicate node IDs;
- blank rune IDs;
- scientific formula fields because `ArcaneMaterialSignature` has no such field.

- [ ] **Step 2: Run tests and confirm failure**

- [ ] **Step 3: Implement the arcane graph types**

Use normalized rune IDs such as `rpgskilltree:thurisaz`, `rpgskilltree:algiz`, `rpgskilltree:othala`, but treat these as project-owned fictional display symbols. Do not claim historical rune meanings unless lore explicitly defines them.

- [ ] **Step 4: Add the initial rune registry resource**

The JSON must define only visual IDs/layout metadata, not chemistry. Example schema:

```json
{
  "runes": [
    {"id": "rpgskilltree:thurisaz", "glyph": "ᚦ"},
    {"id": "rpgskilltree:algiz", "glyph": "ᛉ"},
    {"id": "rpgskilltree:othala", "glyph": "ᛟ"}
  ]
}
```

- [ ] **Step 5: Run tests**

Expected: PASS.

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/arcane src/main/resources/assets/rpgskilltree/materials/runes/base_runes.json src/test/java/com/gustavo/rpgskilltree/material/arcane/ArcaneMaterialSignatureTest.java
git commit -m "feat: add arcane material signatures"
```

---

### Task 5: Define `MaterialDefinition` and strict catalog validation

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/MaterialDefinition.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidator.java`
- Modify: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java`

**Interfaces:**
- Produces `MaterialDefinition` with these fields:

```java
CanonicalMaterialId id;
String displayName;
MaterialKind kind;
MaterialSupportState supportState;
DestroyMappingKind destroyMappingKind;
List<ProviderMaterialRef> providerRefs;
Optional<ChemicalFormula> formula;
Optional<MolecularGraph> molecularGraph;
Optional<MaterialComposition> composition;
Optional<PolymerRepeatUnit> polymerRepeatUnit;
Optional<ArcaneMaterialSignature> arcaneSignature;
List<ScientificSourceRef> sources;
```

- [ ] **Step 1: Write the full rule matrix as failing tests**

At minimum:

```text
REAL_ELEMENT + SUPPORTED_REAL -> formula required, scientific source required
REAL_MOLECULE + SUPPORTED_REAL -> formula required, scientific source required
REAL_MINERAL + SUPPORTED_REAL -> formula required, scientific source required
REAL_ALLOY + SUPPORTED_REAL -> composition required, scientific source required
REAL_MIXTURE + SUPPORTED_REAL -> composition required, scientific source required
REAL_POLYMER + SUPPORTED_REAL -> polymerRepeatUnit required, scientific source required
FICTIONAL_MATERIAL + SUPPORTED_FICTIONAL -> arcaneSignature required, formula forbidden
HYBRID_MATERIAL + SUPPORTED_HYBRID -> arcaneSignature required; any real formula/composition must have scientific source
UNKNOWN -> only blocked support states allowed
ARCANE_DISPLAY_ONLY -> only FICTIONAL_MATERIAL or HYBRID_MATERIAL
DESTROY_NATIVE/DESTROY_EXACT_ALIAS -> forbidden for FICTIONAL_MATERIAL
```

- [ ] **Step 2: Run tests and confirm failures**

- [ ] **Step 3: Implement `MaterialDefinition` and validator**

Validation must return a structured list of errors for batch catalog loading and provide a `validateOrThrow` convenience method for tests.

- [ ] **Step 4: Run tests**

Expected: PASS for the entire rule matrix.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/MaterialDefinition.java src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidator.java src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java
git commit -m "feat: enforce material truth invariants"
```

---

### Task 6: Build the read-only canonical catalog and alias conflict detection

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalog.java`
- Modify: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java`

**Interfaces:**
- Produces:

```java
Optional<MaterialDefinition> find(CanonicalMaterialId id);
Optional<MaterialDefinition> findByProviderRef(ProviderMaterialRef ref);
Collection<MaterialDefinition> all();
```

- [ ] **Step 1: Write failing tests**

Test duplicate canonical IDs, two canonical materials claiming the same provider ref, stable lookup, and immutable returned collections.

- [ ] **Step 2: Run tests and confirm failure**

- [ ] **Step 3: Implement catalog indexing**

The constructor accepts validated definitions and builds two immutable maps: canonical ID -> definition and provider ref -> canonical ID.

- [ ] **Step 4: Run tests**

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalog.java src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java
git commit -m "feat: add canonical material catalog"
```

---

### Task 7: Implement data-driven JSON loading and reload validation

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalogLoader.java`
- Create: initial JSON fixtures under `src/main/resources/data/rpgskilltree/materials/...`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java`

**Interfaces:**
- Produces a loader that converts resource JSON to `MaterialDefinition` and atomically swaps the active immutable `MaterialCatalog` only when the entire reload validates.

- [ ] **Step 1: Write failing loader tests**

Required fixtures:

- one valid real element;
- one valid molecule;
- one valid alloy;
- one valid fictional material;
- one invalid fictional material containing a formula;
- one duplicate provider alias.

The invalid reload must leave the previous valid catalog active.

- [ ] **Step 2: Run tests and confirm failure**

- [ ] **Step 3: Implement parsing with the project's existing JSON/codec convention**

Do not introduce a new serialization library if the repository already standardizes on one. Parsing errors must include resource path and canonical material ID when known.

- [ ] **Step 4: Register the loader on the appropriate NeoForge data/resource reload lifecycle**

Keep common loading code server-safe. Any visual asset resolution belongs in client initialization, not the common loader.

- [ ] **Step 5: Run unit tests and the project's existing resource/datapack validation task**

Expected: PASS.

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/MaterialCatalogLoader.java src/main/resources/data/rpgskilltree/materials src/test/java/com/gustavo/rpgskilltree/material/MaterialCatalogValidatorTest.java
git commit -m "feat: load material catalog from data"
```

---

### Task 8: Define the provider adapter boundary and registry

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/provider/MaterialCandidate.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/provider/MaterialProviderAdapter.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/provider/MaterialProviderRegistry.java`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java`

**Interfaces:**
- Produces:

```java
public interface MaterialProviderAdapter {
    String providerId();
    boolean isAvailable();
    Collection<MaterialCandidate> enumerateCandidates();
}
```

`MaterialCandidate` must include provider ref, display/debug name, provider evidence locator, and an optional provider-native classification hint. Hints are never accepted as canonical scientific truth without validation.

- [ ] **Step 1: Write failing registry tests**

Test duplicate provider IDs, unavailable adapters, deterministic enumeration ordering, and fail-closed behavior when an optional adapter throws during initialization.

- [ ] **Step 2: Run tests and confirm failure**

- [ ] **Step 3: Implement the registry**

Registry construction must isolate an unavailable/broken optional adapter and report a diagnostic rather than failing the whole game unless that provider is configured as required.

- [ ] **Step 4: Run tests**

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/provider src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java
git commit -m "feat: add material provider adapter boundary"
```

---

### Task 9: Audit Destroy 0.4.1 source/API and freeze the bridge contract

**Files:**
- Create: `docs/integrations/2026-09-06-destroy-material-bridge.md`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialAdapter.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialBridge.java`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialBridgeTest.java`

**Interfaces:**
- Consumes: exact installed Destroy 0.4.1 source/API/registry evidence.
- Produces: a frozen mapping boundary that exposes Destroy identities read-only.

- [ ] **Step 1: Inspect the exact installed-version source before writing production code**

Record in the integration document:

- source repository and exact tag/commit corresponding to installed 0.4.1;
- species/mixture registry types actually present;
- stable identifier type actually used;
- whether complete species enumeration is possible;
- client-safe fields available for formula/graph/properties;
- lifecycle point at which the registry is safe to read;
- API/package/class signatures used by the bridge;
- failure behavior when Destroy is absent or version-incompatible.

Do not proceed from memory or upstream 1.20.1 names if the 1.21.1 port differs.

- [ ] **Step 2: Write failing bridge contract tests against a narrow wrapper**

The tests must cover:

```text
Destroy absent -> adapter unavailable, no crash
Known native identity -> resolves exact canonical mapping
Unknown Destroy identity -> no fabricated mapping
Alias collision -> validation failure
Enumeration supported -> all enumerated identities exposed deterministically
Enumeration unsupported -> bridge reports explicit NON_ENUMERABLE capability
```

- [ ] **Step 3: Implement only the proven API calls**

Keep Destroy classes isolated inside the `provider.destroy` package and guard loading using the repository's established optional-integration pattern.

- [ ] **Step 4: Generate/update the Destroy coverage section in the integration document**

If full enumeration is possible, record exact count and test strategy. If it is not, state the verified boundary and prohibit claims of 100% Destroy coverage.

- [ ] **Step 5: Run unit tests plus a runtime smoke with Destroy present and absent when the existing test harness allows both**

Expected: no classloading failure and deterministic mapping behavior.

- [ ] **Step 6: Commit**

```bash
git add docs/integrations/2026-09-06-destroy-material-bridge.md src/main/java/com/gustavo/rpgskilltree/material/provider/destroy src/test/java/com/gustavo/rpgskilltree/material/provider/destroy/DestroyMaterialBridgeTest.java
git commit -m "feat: bridge canonical materials to Destroy"
```

---

### Task 10: Perform the pack-wide provider material audit and build the canonical catalog

**Files:**
- Modify: `docs/audits/2026-09-06-material-provider-audit.md`
- Create/modify: JSON files under `src/main/resources/data/rpgskilltree/materials/catalog/`, `providers/`, `scientific/`, and `arcane/`.
- Create: `docs/audits/2026-09-06-material-catalog-coverage.md`
- Modify: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java`

**Interfaces:**
- Consumes: Task 1 audit baseline, provider source/data/API evidence, scientific references, and Destroy bridge contract.
- Produces: the complete versioned catalog and coverage report.

- [ ] **Step 1: Process every `MATERIAL_PROVIDER` mod from the audit manifest**

For each provider, enumerate all eligible materials and classify every candidate as one of:

```text
SUPPORTED_REAL
SUPPORTED_FICTIONAL
SUPPORTED_HYBRID
BLOCKED_IDENTITY
BLOCKED_COMPOSITION
BLOCKED_PROVIDER
BLOCKED_LICENSE
```

- [ ] **Step 2: Resolve aliases across providers**

Create provider alias entries only when exact equivalence is evidenced. Preserve separate canonical identities for similar names with different or unproven composition.

- [ ] **Step 3: Populate scientific data only from valid evidence**

For each real material, use the category rules from the spec. Minerals receive mineral formulae; alloys receive composition; polymers receive repeat units; mixtures receive labeled fraction bases; molecules receive molecular formula and graph only when graph topology is known.

- [ ] **Step 4: Populate arcane signatures for every supported fictional material**

Use curated rune/sigil definitions. The signature must have a lore/provenance note explaining whether the symbols are project-original or provider-derived. Never encode a fake chemical formula in the arcane field.

- [ ] **Step 5: Handle hybrid materials explicitly**

Store documented real constituents in scientific composition and the irreducibly fictional portion in `arcaneSignature`. Set overall chemical formula absent unless the complete material is scientifically real.

- [ ] **Step 6: Map exact Destroy aliases**

Only create `DESTROY_NATIVE` or `DESTROY_EXACT_ALIAS` entries when the Task 9 bridge proves the identity. Everything else uses `SCIENTIFIC_DISPLAY_ONLY`, `NO_DESTROY_MAPPING`, `BLOCKED_DESTROY_MAPPING`, or `ARCANE_DISPLAY_ONLY` as appropriate.

- [ ] **Step 7: Write/extend coverage tests**

Tests must compare provider enumeration against catalog aliases for adapters that support enumeration and assert every eligible non-blocked canonical material has the representation required by its category.

- [ ] **Step 8: Run catalog validation until the report has zero unexplained gaps**

Blocked entries are allowed only when the coverage report lists the exact blocker/evidence needed. A blocked entry is not silently counted as supported.

- [ ] **Step 9: Commit in provider-sized batches**

Use one commit per independently reviewable provider family, for example:

```bash
git commit -m "data: catalog TFC geological materials"
git commit -m "data: catalog Create metallurgy materials"
git commit -m "data: catalog magic fictional materials"
```

Do not bundle the entire pack audit into one unreviewable commit.

---

### Task 11: Build category-specific render models independent of Minecraft drawing APIs

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/client/MaterialRenderModel.java`
- Create: `src/main/java/com/gustavo/rpgskilltree/material/client/MaterialRenderModelFactory.java`
- Test: `src/test/java/com/gustavo/rpgskilltree/material/MaterialRenderModelFactoryTest.java`

**Interfaces:**
- Produces a sealed/closed hierarchy of render models:

```text
ElementRenderModel
MoleculeRenderModel
MineralRenderModel
AlloyRenderModel
MixtureRenderModel
PolymerRenderModel
ArcaneRenderModel
HybridRenderModel
BlockedRenderModel
```

- [ ] **Step 1: Write failing category-selection tests**

One test per material kind plus blocked states. Assert a fictional material can never yield `MoleculeRenderModel` and an alloy can never yield a molecular graph model.

- [ ] **Step 2: Run tests and confirm failure**

- [ ] **Step 3: Implement a pure model factory**

No Minecraft rendering classes in the factory. It converts validated `MaterialDefinition` into immutable render data and is therefore unit-testable on both client/server JVMs.

- [ ] **Step 4: Run tests**

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/client/MaterialRenderModel.java src/main/java/com/gustavo/rpgskilltree/material/client/MaterialRenderModelFactory.java src/test/java/com/gustavo/rpgskilltree/material/MaterialRenderModelFactoryTest.java
git commit -m "feat: add material render models"
```

---

### Task 12: Implement the 2D molecule/polymer renderer

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/client/render/Molecule2DRenderer.java`
- Add client-only tests/screenshot fixtures following the repository's existing rendering-test convention.

**Interfaces:**
- Consumes: `MoleculeRenderModel` and `PolymerRenderModel`.
- Produces: deterministic 2D atom/bond rendering with element/CPK and monochrome/theme modes.

- [ ] **Step 1: Complete license/provenance review for Molecule Drawings/Molecules before copying any code or data**

Record exact source version, license, reused files/algorithms/data, and notice obligations. If reuse is not clearly permitted, implement independently.

- [ ] **Step 2: Write deterministic layout/render tests**

Use tiny known graphs: water, carbon dioxide, ethene, benzene only if aromatic semantics are implemented from proven data, and one polymer repeat unit. Test bounding boxes, atom label positions, bond counts, and bracket placement rather than pixel colors unless the existing harness supports stable screenshots.

- [ ] **Step 3: Implement minimal drawing primitives**

Required primitives:

```text
atom label
single bond
double bond
triple bond
repeat-unit bracket
subscript/superscript formula text
```

Do not add aromatic circles, wedge/dash stereochemistry, charges, isotopes, or 3D projection until a catalog entry actually requires them and corresponding data fields exist.

- [ ] **Step 4: Add CPK and monochrome/theme color strategies**

Element colors must come from a centralized table/resource with provenance. Do not scatter literals through renderer code.

- [ ] **Step 5: Run client tests/smoke**

Expected: deterministic output and no server classloading path.

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/client/render/Molecule2DRenderer.java src/test resources THIRD_PARTY_NOTICES.md
git commit -m "feat: render molecular structures in 2d"
```

---

### Task 13: Implement composition charts for alloys, minerals, and mixtures

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/client/render/CompositionChartRenderer.java`
- Extend client rendering tests.

**Interfaces:**
- Consumes: `MineralRenderModel`, `AlloyRenderModel`, `MixtureRenderModel`, and the scientific part of `HybridRenderModel`.

- [ ] **Step 1: Write failing deterministic geometry tests**

Test two-component `50/50`, three-component normalized fractions, `ATOMIC_COUNT`, and rejection of an unlabeled `PROVIDER_NATIVE` basis.

- [ ] **Step 2: Implement normalized chart geometry**

Support pie/donut or the repository's best-fitting chart primitive. The legend must always display component names and basis.

- [ ] **Step 3: Ensure composition ranges are not rendered as false exact values**

If an alloy source provides ranges, render `min–max` textual values or a range-aware chart. Do not collapse ranges to arbitrary midpoints unless the UI explicitly labels an estimate and design is amended.

- [ ] **Step 4: Run tests/smoke**

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/client/render/CompositionChartRenderer.java src/test
git commit -m "feat: render material composition charts"
```

---

### Task 14: Implement the arcane signature renderer

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/client/render/ArcaneSignatureRenderer.java`
- Add rendering tests.

**Interfaces:**
- Consumes: `ArcaneRenderModel` and arcane portion of `HybridRenderModel`.

- [ ] **Step 1: Write deterministic graph-layout tests**

Given the same signature data, node positions and edge routing must remain stable across reloads.

- [ ] **Step 2: Implement data-driven rune rendering**

Render only registered project/provider runes. Missing rune IDs produce a visible diagnostic placeholder in development and a safe blocked/fallback representation in production; they must not crash tooltips.

- [ ] **Step 3: Add explicit fictional labeling**

The rendered panel must include localized text equivalent to `Arcane Signature`/`Assinatura Arcana` so it cannot be mistaken for a chemical formula.

- [ ] **Step 4: Run tests/smoke**

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/client/render/ArcaneSignatureRenderer.java src/test src/main/resources/assets/rpgskilltree
git commit -m "feat: render arcane material signatures"
```

---

### Task 15: Add tooltip/material inspection integration

**Files:**
- Create: `src/main/java/com/gustavo/rpgskilltree/material/client/MaterialTooltipAugmenter.java`
- Modify the project's existing client event/bootstrap registration file identified during execution.
- Add client integration tests or GameTests where supported.

**Interfaces:**
- Consumes: provider ref resolution -> `MaterialCatalog` -> `MaterialRenderModelFactory` -> category renderer.

- [ ] **Step 1: Identify the existing client event registration pattern**

Use the project's current NeoForge client bootstrap/event subscriber style. Do not introduce a second bootstrap system.

- [ ] **Step 2: Write a failing integration test for a known catalogued item**

The test must prove one real material adds scientific information and one fictional material adds arcane information, while an uncatalogued item remains untouched.

- [ ] **Step 3: Implement non-invasive tooltip augmentation**

Default compact tooltip line:

```text
Formula: Fe2O3
```

or

```text
Arcane Signature: [indicator]
```

Large structures/charts should open in the existing encyclopedia/inspection surface or an expanded tooltip interaction only if a proven UI hook already exists. Do not make ordinary tooltips unbounded.

- [ ] **Step 4: Add localization keys in existing `en_us` and `pt_br` resource files**

Include at minimum formula, composition basis, arcane signature, blocked-data diagnostic, and material category labels.

- [ ] **Step 5: Run client tests/smoke**

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/gustavo/rpgskilltree/material/client src/main/resources/assets/rpgskilltree/lang src/test
git commit -m "feat: show material knowledge in tooltips"
```

---

### Task 16: Add Volcanoes/RPG encyclopedia material pages

**Files:**
- Modify the existing encyclopedia/compendium registration and page-model files discovered in the repository.
- Add material-page assets/data under the established encyclopedia resource path.
- Update: `docs/compendium/` documentation if it describes page schemas.

**Interfaces:**
- Consumes: canonical catalog and render models.
- Produces: searchable material inspection pages grouped by category/provider/geological relevance.

- [ ] **Step 1: Inspect the existing encyclopedia implementation and freeze its extension point**

Record the exact page registration/data schema in the implementation commit description or companion documentation.

- [ ] **Step 2: Write tests for page generation**

A supported real material page must show sources/formula/category/provider aliases. A fictional material page must show arcane signature and must not show `Chemical Formula`.

- [ ] **Step 3: Implement material pages using the existing encyclopedia system**

Do not build a second encyclopedia UI.

- [ ] **Step 4: Add source/provenance display**

The player-facing page may show concise source attribution while developer/audit details remain in docs/resources.

- [ ] **Step 5: Run tests/client smoke**

- [ ] **Step 6: Commit**

```bash
git add src/main docs/compendium
git commit -m "feat: add material encyclopedia pages"
```

---

### Task 17: Evaluate and add JEI integration only if the installed hook is proven

**Files:**
- Create JEI optional integration classes only after API verification.
- Update: `docs/integrations/2026-09-06-destroy-material-bridge.md` or create a separate JEI material integration note if needed.

**Interfaces:**
- Consumes: JEI version/API actually installed in the pack.

- [ ] **Step 1: Inspect the installed JEI version and current project JEI integration code**

Record exact plugin interfaces/method signatures used.

- [ ] **Step 2: Decide one of two outcomes**

```text
SUPPORTED: stable hook exists for ingredient/material information
BLOCKED: no safe hook; encyclopedia/tooltips remain the supported UI
```

- [ ] **Step 3: If supported, write a failing integration test or plugin registration assertion**

- [ ] **Step 4: Implement only the proven extension point**

Do not replace Destroy's own JEI molecule/reaction displays. Add links/secondary material information only where it complements them.

- [ ] **Step 5: Run JEI client smoke**

- [ ] **Step 6: Commit the supported implementation or blocked documentation outcome**

```bash
git commit -m "feat: integrate material knowledge with JEI"
```

or

```bash
git commit -m "docs: record JEI material integration blocker"
```

---

### Task 18: Add automatic provider/catalog drift detection

**Files:**
- Modify: `src/test/java/com/gustavo/rpgskilltree/material/MaterialCoverageTest.java`
- Modify: `docs/audits/2026-09-06-material-catalog-coverage.md`
- Add CI script/task using the project's established Gradle verification conventions.

**Interfaces:**
- Consumes: modlist audit manifest, provider adapters, canonical catalog, Destroy enumeration capability.

- [ ] **Step 1: Write failing drift tests**

Cover:

```text
new provider candidate missing from catalog
catalog alias no longer returned by provider
new Destroy identity missing from catalog when enumeration is supported
modlist audit manifest missing a canonical mod
fictional material accidentally gaining formula field
real supported material losing provenance
```

- [ ] **Step 2: Implement a deterministic validation report**

The test/build output must print exact provider/material IDs for gaps.

- [ ] **Step 3: Wire the validation into the existing `check`/CI verification path**

Do not create a separate optional script that CI never runs.

- [ ] **Step 4: Run the full verification task**

Expected: PASS with zero unexplained gaps.

- [ ] **Step 5: Commit**

```bash
git add src/test docs/audits build.gradle gradle.properties .github
git commit -m "test: enforce material catalog coverage"
```

Only stage build/CI files actually modified by the repository's chosen wiring.

---

### Task 19: Client/server safety, reload, and performance validation

**Files:**
- Add/modify tests under existing GameTest/integration test source sets.
- Update: `docs/audits/2026-09-06-material-catalog-coverage.md`
- Update: `docs/TESTING.md` if the new commands/gates need durable documentation.

**Interfaces:**
- Validates the completed subsystem rather than adding new product behavior.

- [ ] **Step 1: Add resource reload test**

Load valid catalog A, reload catalog B, assert B becomes active atomically and stale render caches are invalidated.

- [ ] **Step 2: Add dedicated-server smoke**

Start the dedicated server with client renderer classes absent from common initialization. Expected: no `ClassNotFoundException`, `NoClassDefFoundError`, or client distribution violation.

- [ ] **Step 3: Add full-pack startup smoke**

With the canonical pack integrations present, validate catalog load totals and zero alias conflicts.

- [ ] **Step 4: Add bounded performance assertions/diagnostics**

Measure catalog load/enumeration at reload/startup, not per tick. Confirm no recurring global scans are registered.

- [ ] **Step 5: Run the repository's established validation suite**

At minimum where available:

```text
unit tests
GameTests
NeoForge build/check
full-pack compatibility test
dedicated-server save/reload smoke
client smoke
```

- [ ] **Step 6: Record exact commands, run IDs, totals, and outcomes in the coverage audit**

- [ ] **Step 7: Commit**

```bash
git add src/test docs/audits docs/TESTING.md
git commit -m "test: validate material system runtime safety"
```

---

### Task 20: Provenance, notices, final audit, CI, merge, and post-merge verification

**Files:**
- Modify: `THIRD_PARTY_NOTICES.md` if applicable.
- Modify: `SOURCES.md` if the repository uses it for external technical sources.
- Finalize: both material audit documents and integration documentation.

**Interfaces:**
- Produces the evidence package required to declare the subsystem complete.

- [ ] **Step 1: Perform third-party provenance review**

For every copied/adapted renderer algorithm, data table, glyph asset, or molecular definition, record source, exact version/commit, license, modifications, and required notice. If provenance is uncertain, remove the copied material and replace it with independently authored implementation/data before release.

- [ ] **Step 2: Run the complete catalog coverage report**

The final report must state separate values for:

```text
mod audit coverage
candidate classification coverage
supported representation coverage
Destroy coverage or verified non-enumerable boundary
blocked entries by reason
```

- [ ] **Step 3: Run full CI-equivalent local verification**

Use the exact commands already documented by the repository. Capture success/failure evidence.

- [ ] **Step 4: Push/update the implementation PR and wait for CI**

All required checks must be green. Investigate real failures; do not rerun blindly unless the failure is demonstrably transient.

- [ ] **Step 5: Review the PR against the design spec**

Confirm every requirement in `docs/specs/2026-09-06-volcanoes-universal-material-chemistry-design.md` maps to code, data, test, or an explicit documented blocker permitted by the spec.

- [ ] **Step 6: Merge the implementation PR to `main`**

Use the repository's normal merge method after CI is green and blockers are resolved.

- [ ] **Step 7: Verify `main` after merge**

Record final main SHA, verify required workflows on that SHA, and confirm the material coverage tests still pass from `main`.

- [ ] **Step 8: Final documentation commit only if post-merge evidence requires it**

If a final SHA/run-ID record is maintained in-repo, open the minimal follow-up docs PR, get it green, merge it, and re-confirm `main`.

---

## Audit Execution Order

The pack-wide audit should be processed in provider families so reviewers can reason about one domain at a time. The exact family membership comes from the Task 1 manifest, but the preferred order is:

1. Destroy and Petrolpark ecosystem.
2. Volcanoes/RNS/TFC geological and mineral providers.
3. Create metallurgy/petrochemical/industrial addons, including TFMG and related material systems present in the canonical modlist.
4. Other technology mods that add metals, alloys, chemicals, fuels, gases, polymers, or industrial intermediates.
5. Magic mods that add gems, essences, fictional metals/crystals/materials.
6. Worldgen/adventure/content mods that add ores/minerals/gems/material drops.
7. Remaining mods reviewed as no-material or consumer-only to close 100% mod audit coverage.

Each family ends with its own coverage-test run and reviewable commit set.

## Required Scientific Evidence Policy

For real-world identity/composition, use this precedence:

1. provider source/data/API for what the in-game resource claims to be;
2. provider official documentation for intended identity;
3. authoritative scientific references for real formula/composition;
4. no inference when the first three do not establish the identity.

The scientific source record should distinguish provider identity evidence from real-world chemistry evidence so a mod's fictional simplification cannot silently overwrite scientific truth.

## Required Arcane Evidence Policy

Arcane signatures are presentation/lore artifacts, not chemistry. Every signature must declare one of:

```text
PROJECT_ORIGINAL
PROVIDER_LORE_DERIVED
PROVIDER_VISUAL_DERIVED
```

Provider-derived symbols require provenance/license review. Project-original rune combinations must not claim historical occult/rune meanings unless explicitly researched and intended.

## Coverage Claim Rules

The project may say **"100% of the canonical modlist audited"** only when every modlist row has a manifest disposition.

The project may say **"100% of discovered material candidates classified"** when every candidate is supported or explicitly blocked.

The project may say **"100% representation coverage"** only for non-blocked eligible materials that satisfy their category-specific representation requirements.

The project may say **"100% Destroy coverage"** only when the installed Destroy version exposes a complete enumerable identity set and the automated comparison is green. Otherwise the wording must be **"100% of the verified enumerable/known Destroy boundary"** with the boundary documented.

## Plan Self-Review

- Spec coverage: all design areas are mapped to Tasks 1–20, including modlist audit, taxonomy, provenance, Destroy authority, fictional runes, hybrid materials, data loading, provider adapters, rendering, UI integration, drift detection, performance, CI, merge, and post-merge verification.
- Placeholder scan: no `TBD`, `TODO`, or unspecified "write tests" step is used; tasks define required assertions or decisions.
- Type consistency: the catalog, provider, Destroy, scientific, arcane, and render-model type names are stable across tasks.
- Scope control: provider-specific adapters beyond Destroy are intentionally created only after the pack-wide audit proves each provider's real integration contract; this is a fail-closed requirement rather than an implementation placeholder.
