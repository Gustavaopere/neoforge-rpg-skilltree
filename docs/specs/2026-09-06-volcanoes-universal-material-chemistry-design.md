# Volcanoes Universal Material Chemistry & Visualization — Design Specification

## Status

Approved design for planning. This document defines the architecture and evidence rules for auditing the complete NeoForge 1.21.1 modpack for material-bearing mods, classifying every discovered candidate material, integrating real chemistry with Destroy when technically valid, and rendering scientifically correct or explicitly arcane representations in the RPG/Volcanoes client UX.

## 1. Goal

Build a pack-wide material knowledge and visualization subsystem under the consolidated `Gustavaopere/neoforge-rpg-skilltree` runtime, with Volcanoes acting as the geological/material discovery integration surface and Destroy remaining the authority for its own molecular/reaction domain.

The subsystem must:

1. audit the complete current modlist for mods that add materials or material-like resources;
2. classify every discovered candidate material using explicit scientific or fictional categories;
3. represent real materials only with real formulae/compositions supported by evidence;
4. integrate exact real substances with Destroy without creating a parallel chemistry engine;
5. provide structural/formula/composition visualization inspired by Destroy and Molecule Drawings;
6. provide a separate, clearly non-scientific arcane notation for fictional materials;
7. enforce coverage through machine-checkable manifests and tests;
8. fail closed when identity, composition, authority, or provenance cannot be proven.

## 2. Environment and canonical authorities

- Minecraft: `1.21.1`.
- Loader: NeoForge.
- Java: `21`.
- Runtime repository: `Gustavaopere/neoforge-rpg-skilltree`.
- Volcanoes standalone is historical only; new runtime work belongs in the consolidated repository.
- Destroy installed runtime baseline: `destroy-1.21.1-0.4.1.jar`.
- Destroy is the chemical authority for Destroy-native species, reactions, mixtures, hazards, and thermodynamic data exposed by the installed port.
- Volcanoes remains the authority for its own geology, deposits, tectonics, vulcanism, atmosphere, pressure, and environmental integrations.
- The universal catalog is an identity/metadata/visualization layer. It must not replace provider-owned gameplay state.

## 3. Core design rule: truth before coverage

Coverage is required, but fabricated chemistry is forbidden.

A candidate material may reach one of these states:

- `SUPPORTED_REAL`: scientifically identified and fully supported for its category.
- `SUPPORTED_FICTIONAL`: explicitly fictional and represented only through arcane metadata/visualization.
- `SUPPORTED_HYBRID`: fictional material with a documented real sub-composition plus a fictional component; real and fictional portions stay separate.
- `BLOCKED_IDENTITY`: the material cannot be identified precisely enough.
- `BLOCKED_COMPOSITION`: identity is known but the exact formula/composition required for representation is not proven.
- `BLOCKED_PROVIDER`: provider contract or registry identity cannot be proven safely.
- `BLOCKED_LICENSE`: required implementation reuse is not legally/provenance-safe; independent implementation may still proceed.

No `BLOCKED_*` entry receives guessed chemistry.

## 4. Material taxonomy

Every canonical material identity must use exactly one primary category:

- `REAL_ELEMENT`
- `REAL_MOLECULE`
- `REAL_MINERAL`
- `REAL_ALLOY`
- `REAL_MIXTURE`
- `REAL_POLYMER`
- `FICTIONAL_MATERIAL`
- `HYBRID_MATERIAL`
- `UNKNOWN`

### 4.1 Category semantics

`REAL_ELEMENT`
: A chemical element, represented by element symbol and atomic metadata.

`REAL_MOLECULE`
: A discrete chemical species for which molecular formula and, when applicable, atom/bond graph can be represented.

`REAL_MINERAL`
: A mineralogical species or well-defined mineral group. Use mineral/empirical formula and mineral composition rules. Do not pretend a crystal lattice is a discrete molecule.

`REAL_ALLOY`
: A metallic material defined by composition ranges or exact provider-declared composition. Display composition, not a fake molecular graph.

`REAL_MIXTURE`
: A mixture of components. Display component identities and fractions when the provider exposes or defines them.

`REAL_POLYMER`
: A polymer represented by repeat unit, repeat notation, and structural graph when scientifically justified.

`FICTIONAL_MATERIAL`
: A fictional material that must never be mapped into real chemistry merely by name similarity.

`HYBRID_MATERIAL`
: A fictional material whose provider explicitly defines one or more real constituents while also containing an irreducibly fictional constituent.

`UNKNOWN`
: Candidate not yet resolved. It must remain blocked from scientific rendering and Destroy integration.

## 5. Canonical identity model

The system must separate a material identity from the provider resources that represent it.

Example:

```text
canonical material: copper
category: REAL_ELEMENT
formula: Cu

provider representations:
- mod_a:copper_ingot
- mod_b:copper_dust
- mod_c:molten_copper
```

Provider resources are aliases to a canonical identity only when equivalence is proven.

### 5.1 Identity fields

Each canonical entry must be able to express:

- stable catalog ID;
- primary category;
- display name;
- formula or symbolic representation where applicable;
- structural data where applicable;
- composition data where applicable;
- evidence/provenance references;
- provider aliases;
- Destroy mapping state;
- visualization state;
- fictional/arcane metadata when applicable;
- audit status and blockers.

### 5.2 No name-based equivalence

The system must not infer equivalence from registry path, translation key, or English display name alone.

Examples of prohibited inference:

- `*_steel` => a specific steel chemistry;
- `sulfur_*` => sulfur element without confirming the actual material role;
- `oil` => a fixed hydrocarbon mixture;
- `mana_steel` => iron/carbon steel;
- `durasteel` => any real alloy.

## 6. Provider audit scope

The audit starts from the complete current modlist, not from a hand-picked list of chemistry mods.

A mod is material-relevant when it adds or authoritatively defines any of the following:

- elements;
- ores/minerals;
- metals;
- alloys;
- gems/crystals;
- powders/dusts;
- salts;
- chemicals;
- gases;
- chemical fluids;
- fuels/petrochemicals;
- polymers;
- geological resources;
- industrial feedstocks;
- magical/fictional materials;
- material-bearing intermediates whose identity matters to gameplay or visualization.

The audit must also record mods reviewed and found to add no eligible materials, so the final coverage statement is pack-wide rather than only candidate-wide.

## 7. Audit procedure per mod

For every mod in the modlist:

1. confirm JAR name and installed version;
2. reconcile the matching Notion record;
3. determine whether the mod adds candidate materials;
4. enumerate candidate material resources through authoritative registry/data/code evidence;
5. identify provider-native material metadata, tags, compositions, species registries, recipes, or APIs;
6. deduplicate aliases only with evidence;
7. classify each canonical material;
8. validate real formula/composition against authoritative provider data and, where provider data is insufficient, an appropriate external scientific source;
9. identify exact Destroy mapping when one exists;
10. classify visualization requirements;
11. record provenance and confidence;
12. mark unresolved entries blocked rather than guessing.

## 8. Destroy integration model

Every real material candidate receives one Destroy relationship state:

- `DESTROY_NATIVE`
- `DESTROY_EXACT_ALIAS`
- `SCIENTIFIC_DISPLAY_ONLY`
- `NO_DESTROY_MAPPING`
- `BLOCKED_DESTROY_MAPPING`

Fictional materials use `ARCANE_DISPLAY_ONLY` and never become Destroy species unless the provider itself defines a real chemical identity that is independently proven.

### 8.1 `DESTROY_NATIVE`

The material is already represented by a Destroy species/material identity. Destroy data is authoritative for fields exposed by the installed version.

### 8.2 `DESTROY_EXACT_ALIAS`

Another mod exposes the exact same real substance as a Destroy species. The bridge links the provider resource to the Destroy identity without duplicating species or reaction state.

### 8.3 `SCIENTIFIC_DISPLAY_ONLY`

The material is real and scientifically representable but does not belong in Destroy's molecular/reaction model. Typical examples include many minerals and alloys.

### 8.4 No synthetic species for coverage

The bridge must not create fake Destroy species solely to make coverage numbers look complete.

## 9. Scientific visualization model

The client renderer selects a representation based on category.

### 9.1 Elements

Display:

- symbol;
- optional atomic number/atomic mass if sourced;
- provider-specific physical state only if authoritative at the current context.

### 9.2 Molecules

Display:

- molecular formula;
- 2D structural graph when atom/bond topology is available;
- CPK/element coloring mode;
- optional monochrome/theme mode;
- provider-native properties such as molar mass, boiling point, density, hazards only when authoritative data is exposed.

### 9.3 Minerals

Display:

- mineralogical/empirical formula;
- element stoichiometry;
- optional crystallographic/mineral metadata only when sourced;
- no fake discrete molecular structure for extended lattices.

### 9.4 Alloys

Display:

- exact composition or provider-declared composition range;
- mass fraction and/or atomic/count representation only when the source defines enough information;
- pie/bar composition visualization;
- no molecular formula.

### 9.5 Mixtures

Display:

- component identities;
- fraction basis: mole, mass, volume, or provider-native unit;
- clear labeling of basis;
- dynamic values only when obtained from provider runtime state.

### 9.6 Polymers

Display:

- repeat-unit formula;
- repeat-unit structural graph;
- `[ ]n` or equivalent polymer notation;
- no invented chain length.

## 10. Fictional material visualization: Arcane Material Signature

Fictional materials receive a separate representational language so the UI remains rich without presenting invented chemistry as science.

### 10.1 Rules

- Arcane signatures are explicitly fictional symbols, not chemical formulae.
- They must not use fake element symbols or pseudo-IUPAC notation.
- The tooltip/UI must label them as `Arcane Signature`, `Runic Signature`, or another clearly fictional localized term.
- Signatures are data-driven.
- Signature generation may use deterministic layout, but symbol assignment must come from curated material metadata or an explicitly defined fictional system, not from hashing that implies lore meaning accidentally.

### 10.2 Data model

An arcane signature may contain:

- rune/sigil IDs;
- nodes;
- edges/connections;
- rings/brackets;
- elemental/magical school affinity when the provider actually defines it;
- display theme;
- provenance/lore source.

Example concept:

```text
Durasteel
Classification: Fictional Material
Arcane Signature: [rune graph]
Scientific Formula: not applicable
```

## 11. Hybrid materials

When a fictional material has provider-documented real constituents, the system may display both layers without collapsing them into one formula.

Example conceptual output:

```text
Known real constituents:
- Fe
- C

Arcane constituent:
- [sigil]

Overall chemical formula: not applicable
```

The real fraction is scientific; the fictional fraction remains arcane.

## 12. Data-driven resource model

The material catalog and visualization definitions should be data-driven wherever practical so updates do not require Java edits for every new material.

Recommended logical resource groups:

```text
data/<modid>/materials/catalog/
data/<modid>/materials/providers/
data/<modid>/materials/scientific/
data/<modid>/materials/arcane/
assets/<modid>/materials/visuals/
```

Exact paths and codecs must follow existing repository conventions discovered during implementation planning; this document defines responsibility, not premature package names.

## 13. Runtime architecture

The subsystem is split into focused units:

1. **Material Catalog** — canonical identities and provider aliases.
2. **Scientific Material Data** — formula, structure, composition, source metadata.
3. **Arcane Material Data** — fictional signatures and lore-safe metadata.
4. **Provider Adapters** — optional bridges that enumerate/resolve material candidates from installed mods.
5. **Destroy Chemical Bridge** — maps exact canonical identities to Destroy-native identities.
6. **Material Visualization Service** — chooses category-correct render models.
7. **Client Renderers** — molecule, polymer, mineral, alloy, mixture, element, and arcane presentation.
8. **Coverage/Audit Service** — validates manifests against provider enumeration and reports gaps.

No unit may silently assume provider availability. Optional integrations must fail closed and disable only their dependent feature.

## 14. Provider adapter contract

Each provider adapter should expose a small read-only boundary conceptually equivalent to:

```text
providerId()
enumerateCandidates()
resolveProviderIdentity(resource)
providerEvidence(resource)
```

The exact Java signatures must be derived from existing repository conventions during implementation planning.

Adapters must not mutate provider gameplay state.

## 15. Destroy bridge contract

The Destroy bridge must:

- detect installed Destroy version safely;
- enumerate Destroy species if the installed API/registry makes this possible;
- expose stable Destroy identity references without duplicating them;
- map aliases only after exact identity validation;
- preserve Destroy authority for reaction/mixture/hazard data;
- fail closed if the relevant API changes;
- support a coverage test comparing enumerable Destroy identities against mapped/renderable entries.

If full enumeration is not technically possible in the installed port, the final documentation must state the exact verified coverage boundary instead of claiming 100% of Destroy.

## 16. Molecule Drawings / Molecules inspiration boundary

The target UX may borrow concepts such as:

- 2D atom/bond structural rendering;
- CPK/material/themed color modes;
- polymer brackets/repeat notation;
- composition charts;
- data-driven structure definitions.

Before copying source code or data:

1. inspect the exact repository/version license;
2. identify copyright/provenance obligations;
3. record any reused code/data in third-party notices;
4. prefer provider-native or independently authored data when license or provenance is unclear.

If reuse is blocked, implement an independent renderer from the published behavior/specification rather than copying source.

## 17. Provenance requirements

Every scientific datum required for identity or rendering must carry source provenance sufficient for audit.

Acceptable evidence hierarchy:

1. installed provider source/data/API for provider identity and declared composition;
2. official project documentation when source data is not explicit;
3. recognized scientific databases/standards/reference literature for real-world formula/composition;
4. curated project decision only for fictional/arcane semantics, never for real chemistry.

Conflicting sources result in a blocked entry until reconciled.

## 18. Coverage model

Coverage is measured separately at four levels:

### 18.1 Mod audit coverage

```text
mods audited / mods in current canonical modlist
```

Target: `100%`.

### 18.2 Candidate classification coverage

```text
classified candidates / discovered material candidates
```

Target: `100%`, including blocked classifications.

### 18.3 Supported representation coverage

```text
supported real + supported fictional + supported hybrid / eligible non-blocked candidates
```

Target: `100%`.

### 18.4 Destroy coverage

If Destroy identities are enumerable:

```text
Destroy identities with catalog/visual mapping / enumerable Destroy identities
```

Target: `100%`.

Blocked or non-enumerable API conditions must be reported distinctly and must prevent a false 100% claim.

## 19. Automated validation gates

The implementation must provide tests or build-time validation for:

- duplicate canonical IDs;
- conflicting aliases;
- malformed formula metadata;
- invalid category/representation combinations;
- real material without required scientific provenance;
- fictional material with scientific formula field populated;
- hybrid material collapsing fictional content into a fake chemical formula;
- Destroy alias pointing to a missing Destroy identity;
- provider adapter returning an uncatalogued required material;
- arcane signature referencing missing symbols;
- renderer selection for every supported category;
- deterministic data reload behavior;
- optional-provider fail-closed behavior;
- client/server classloading separation;
- complete mod-audit manifest coverage against the canonical modlist snapshot used for the audit.

## 20. UI integration targets

The first-class client integration targets are:

- item/fluid/material tooltips where safe and non-invasive;
- JEI ingredient/category views when extension hooks are available;
- Volcanoes/RPG encyclopedia or material-inspection surfaces;
- Destroy-native molecule displays by augmentation, not replacement, where compatible;
- optional Ponder integration when it adds explanatory value and does not duplicate provider content.

The implementation must avoid excessive tooltip size. Large graphs/charts should support expanded/secondary views when required.

## 21. Performance and networking

- Static scientific/arcane definitions should be loaded client-side from data/resources where possible.
- Dynamic provider state must be sampled through bounded APIs.
- No global per-tick scan of registries, worlds, inventories, or recipes.
- Provider enumeration belongs to load/reload/audit phases, not hot gameplay loops.
- Server-to-client synchronization must transmit only runtime data not reconstructible from shared data packs/resources.
- Render caches must be invalidated deterministically on resource reload.

## 22. Compatibility principles

- Optional mod adapters stay optional.
- No hard dependency should be introduced for a provider merely to render its materials unless the pack already requires that provider.
- Duplicate items representing the same proven material share canonical identity but retain provider ownership.
- Tags may help discover aliases but are not sufficient evidence by themselves for chemical equivalence.
- KubeJS/recipe unification must not be treated as proof of chemical identity unless the project explicitly establishes it.

## 23. Audit artifacts

The implementation project must maintain at least these versioned artifacts:

1. `docs/audits/...material-provider-audit.md` — pack-wide mod/provider audit.
2. `docs/audits/...material-catalog-coverage.md` — candidate/material coverage report.
3. `docs/integrations/...destroy-material-bridge.md` — Destroy API/version/identity contract and verified coverage boundary.
4. machine-readable catalog/manifest resources used by tests.
5. provenance/third-party notices for copied or adapted visual assets/code/data.

## 24. Definition of done

The subsystem is complete only when all applicable statements below are true and evidenced:

- 100% of mods in the selected canonical modlist snapshot have an audit disposition.
- 100% of discovered candidate materials have a classification or explicit blocked state.
- No real material uses an invented formula or composition.
- No fictional material is represented as real chemistry.
- Every supported fictional material has an explicit arcane representation or an intentionally documented non-rendering state approved by design.
- Every supported real material has a category-correct scientific representation.
- Destroy-native/exact-alias mappings preserve Destroy authority.
- If Destroy enumeration is technically possible, every enumerable Destroy identity is covered by the catalog/visualization gate.
- Optional providers fail closed.
- All catalog validation tests pass.
- NeoForge build passes on Java 21.
- Client smoke confirms rendering/resource reload without classloading failures.
- Dedicated-server smoke confirms no client-only class leakage.
- CI is green for the implementation PR.
- Final implementation is merged to `main` and `main` is revalidated after merge.

## 25. Non-goals

This project does not:

- replace Destroy's reaction engine;
- invent chemistry for gameplay coverage;
- normalize all provider materials into Destroy species;
- redesign provider recipes by default;
- force scientific notation onto magical materials;
- infer lore from random rune hashes;
- add GregTech/GTCEu as a dependency merely to obtain Molecule Drawings behavior.

## 26. Design decision summary

The chosen architecture is **canonical catalog + provider adapters + evidence-backed scientific data + Destroy authority bridge + category-specific renderer + separate arcane signature system**.

This approach is preferred over hardcoding per mod or guessing chemistry from names because it provides auditable correctness, deterministic coverage, safe optional integration, and a maintainable path for future modlist updates.
