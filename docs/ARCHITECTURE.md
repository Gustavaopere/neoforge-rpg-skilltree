# Architecture

## Goal
Build a private NeoForge 1.21.1 progression mod around the Passive Skill Tree UI/runtime, with a large Path of Exile-style main tree and specialized mastery trees.

## Core rules
- One visible progression language: Passive Skill Tree-style nodes, connections, icons, tooltips and editor/data format.
- Classes are emergent labels, not character-creation locks. A player can qualify for several archetypes at once.
- Hybrid archetypes rank above generic parents when their investment requirements are met.
- Generic passive points and mastery XP are separate currencies.
- A specialized tree requires: main-tree investment + gateway node/tag + relevant mastery XP.
- All numeric bonuses pass through canonical stat IDs to avoid duplicate meanings across mods.
- Proc-generated actions carry a depth marker so echoes/duplicates cannot recursively generate themselves or farm mastery XP.
- Native provider progression remains authoritative where it already represents a real mechanic; the RPG tree specializes and bridges it instead of cloning it.

## Main tree
The current blueprint budgets **512 nodes** on a 4608x4608 canvas across **11 universal regions**: Martial, Agility, Vitality, Healing, Arcane, Engineering, Mining, Survival, Summoning, Occult and Logistics.

The topology is deliberately physical. Adjacent regions form natural confluences; non-adjacent identities should ultimately use paid bridge corridors made of buyable nodes. The central wheel, domain fans, hybrid corridors and outer keystones are generated deterministically from `tree_blueprints/main.json`.

The three outermost nodes of each domain are the persistent 3/3/3 final triad used by the current class-completion compatibility rules.

## Semantic tree architecture
The purchase graph and the semantic architecture are separate layers:

- `data/rpgskilltree/node_rules/` owns buyable node IDs, ranks, point cost, access requirements, specialization grants and graph neighbors.
- `data/rpgskilltree/tree_architecture/` owns tree identity, type, provider, domains, ordered branches, gateway requirements, tags and intentional cross-tree bridges.
- `TreeRuleCatalog` is the runtime purchase/graph catalog.
- `TreeArchitectureCatalog` is the runtime semantic catalog.
- `NodeRulesReloader` and `TreeArchitectureReloader` reload the two layers independently from datapacks.

This split allows one stable purchase engine to serve many specialist trees while the planner/UI can reason about concepts such as “Fire -> Ignite -> Flameborne -> Lava” instead of treating every node as an anonymous coordinate.

The first semantic pass materializes **83 tree definitions** covering the main tree, Iron schools, Ars composition families, combat weapons, TFC survival, technology/logistics providers, occult/summoning systems, morph/ecology and hybrid identities.

## Emergent archetype architecture
`data/rpgskilltree/archetypes/` is loaded by `ArchetypeReloader` into `ArchetypeCatalog`. Archetype definitions carry explicit `specificity_score`, domain thresholds and required/forbidden tags. Resolution exposes one Primary Class and ordered Secondary Classes using the deterministic ordering:

1. specificity score;
2. degree of the currently represented requirements satisfied;
3. display priority;
4. stable archetype ID.

`ClassResolutionQueryService` is the pure read-only boundary for this modern resolver. It snapshots the supplied archetype definitions, rejects duplicate IDs and can resolve either an already-authoritative `InvestmentState` or a canonical `ProgressionState` projected through explicit contribution metadata. `ClassResolutionRuntime` remains a read boundary and does not persist or mutate player class state.

Canonical purchased-node contribution metadata is now derived only from the explicit tags already carried by `data/rpgskilltree/skills/`: each purchased rank contributes one point to every declared `rpgskilltree:domain/<domain>` tag, while `rpgskilltree:domain/core` is explicitly neutral. `SkillInvestmentMetadataParser` never inspects node IDs, coordinates or graph topology. Unknown domain tags reject the reload instead of being guessed. `ClassInvestmentMetadataCatalog` publishes this metadata with the same skill-tree revision, and canonical class resolution rejects revision mismatch rather than mixing snapshots.

The modern resolver is intentionally **not a second live player-class authority**. `data/rpgskilltree/classes/`, `ClassRulesReloader` and `ClassRuleCatalog` remain the authoritative persisted/reconciled class path. Mastery-to-archetype contribution thresholds are accepted only as explicit `MasteryInvestmentMetadata`; Stage 04.03 must define their canonical semantics before the runtime invents any automatic Mastery tag/weight mapping.

## Data-driven eligibility definitions
`data/rpgskilltree/specializations/` is loaded by `SpecializationReloader` into `SpecializationCatalog`. These definitions describe eligible classes, mastery requirements and required investment tags. The core `SpecializationResolver` can evaluate them from a trustworthy `InvestmentState`.

`data/rpgskilltree/tree_unlocks/` is loaded independently by `TreeUnlockReloader` into `TreeUnlockCatalog`. These definitions describe domain-score, tag and mastery gates for specialist trees and are consumed by the core `TreeUnlockResolver` contract.

Purchased-node domain scores and tags now have a canonical read-only projection from the skill resources. Mastery remains a separate canonical ledger; any conversion of Mastery thresholds into archetype tags/weights must stay explicit until Stage 04.03 defines that mapping. Loading eligibility datasets does not by itself make them player-state authorities.

`data/rpgskilltree/progression/defaults.json` remains a validated declaration of the current stable progression defaults rather than a hot-reloadable economy. Character level is derived from total stored XP and level gains award passive points; changing those rules during a datapack reload would require an explicit save/economy migration policy that does not currently exist in the master design.

## Bridge transition boundary
The master design prefers physical Bridge Node corridors over an invisible class surcharge. The legacy class runtime still contains `nonAdjacentBridgeCost`, `UnlockClassPayload` and `PlayerProgressionRuntime.unlockPaidClass(...)` for non-adjacent classes.

That legacy path cannot be removed safely before the corresponding buyable bridge corridors exist: removing it first would make those classes unreachable. It remains a compatibility mechanism until the perk topology is reconciled, at which point bridge spending should occur through ordinary node purchases and the hidden surcharge path can be deleted.

## Specialization reconciliation boundary
`SpecializationProgressionState` currently stores only unlocked IDs; it does not persist the source/provenance of each unlock. Therefore generic reconciliation cannot safely distinguish an external/persistent specialization from a stale node-granted specialization whose datapack grant was removed.

For the current v4 save format, `ProgressionService.reconcileNodeSpecializations(...)` takes the conservative compatibility path: it explicitly preserves the stable IDs produced by the semantic class-to-specialization migration (`industrialist`, `logistician`, `prospector`) and reconstructs other current specializations from active node grants. This prevents migrated player progress from disappearing while also preventing removed gateway definitions from leaving permanent stale unlocks.

A future generalized mixture of node-owned, mastery-owned and external specializations requires explicit specialization provenance in persisted state or another authoritative source model. That schema decision is deferred rather than guessed.

The three migrated identities deliberately do not yet receive invented zero-cost specialization gates. Their future acquisition requirements depend on the canonical investment/mastery signals that will be finalized with the perk metadata.

## Specialized trees
### Iron's Spellbooks
Fire, Ice, Lightning, Holy, Ender, Blood, Evocation, Nature and Eldritch. School practice comes from the real provider school/action and gateway mastery, not from a second invented spell-school system.

### Ars Nouveau
Projectile, Touch, Self, AoE, Amplification, Duration, Control, Summoning, Movement and Manipulation/Automation. These trees are derived from spell composition/glyph semantics rather than pretending Ars uses Iron-style schools.

### Combat
Sword, Greatsword, Spear, Axe, Dagger, Hammer, Bow, Crossbow, Polearm, Guard/Stagger and Mobility. Epic Fight remains the combat-state authority where applicable; ParCool remains the parkour authority.

### TFC / Survival
Metabolism, nutrition, climate, food preservation, farming, forestry, fauna, prospecting and Beneath expedition. These branches specialize real TFC/Cold Sweat/Firmalife systems and must not remove the underlying survival loop.

### Technology / Logistics
Create Kinetics/Processing/Contraptions, TFMG, New Age, Nuclear, AE2 Networks/Autocrafting/Spatial, Applied Create, Oritech Power/Processing/Mining/Logistics, Create Aeronautics and Technomancy.

Machine automation never grants passive mastery per tick. Progress comes from meaningful configuration, first-use/milestone or validated outcomes.

### Summoning / Occult
Goety Soul/Servants/Necromancy, Malum Spirits/Spirit Knight, Eidolon Ritual/Theurgy, Neo Vitae Blood, Occultism Spirits and the distinct sustain families for weapon leech, spell vampirism, DoT siphon and universal vampirism.

Provider resources remain separate: Soul Energy is not mana, Essentia Vitae is not Soul Energy, Malum spirits are not generic mana, and universal vampirism has lower coefficients/caps than narrow leech specialists.

### Morph / Ecology
Druid Wild Shape, Metamorph Assimilation, Morph Ecology and Beastmaster share one form taxonomy/ecology layer. Identity 2 is the intended morph backend; RPG Skill Tree owns gates, form categories, mastery and ecological/social rules rather than copying per-entity identity NBT.

The Identity 2 1.21.1 integration uses its public current-morph API when the provider is loaded. Perceived species/factions/traits and temporary hostility memory are data-driven through `morph_categories/`. The obsolete duplicate `morph/form_rules.json` manifest was removed so there is no second pretend configuration source for the core Druid/Metamorph permission invariant.

Final Druid/Metamorph permission grants remain dependent on perk reconciliation. Identity 2 1.21.1 exposes current-morph/tick APIs but no public pre-transformation authorization callback was found, so a provider-side pre-transform veto is not claimed complete.

### Hybrid classes and identities
Canonical hybrid/emergent class work currently includes Spellblade, Paladin, Geomancer, Technomancer, Necromancer, Warlock and Beastmaster, with Death Knight still planned/conditional on a sufficiently deep Martial + Occult integration.

Battlemage and Arcane Archer are **not canonical class archetypes** in the modern resolver. Any existing semantic-tree references to those names are compatibility/planning artifacts to reconcile with the parallel perk topology, not authority to recreate them as classes. Artificer remains deliberately unresolved because the master design currently contains contradictory class-taxonomy statements; code must not guess that decision.

## Attunement
Tree nodes may unlock dedicated Attunement slots. These should be implemented as real Curios-backed storage where possible so equipped items keep native tick/equip/unequip/attribute behavior. The normal Curios inventory is not consumed. Respecs must eject overflow safely and deterministically.

## Validation
`scripts/validate-data.py` validates both legacy progression data and the semantic architecture. Architecture validation rejects:
- duplicate tree IDs;
- unknown progression domains;
- invalid tree types;
- missing/duplicate branches;
- invalid branch order;
- unknown required classes or specializations;
- invalid mastery gates;
- malformed tags/bridges;
- bridges to unknown trees or to the tree itself.

Foundation validation additionally protects the final taxonomy from reintroducing demoted class IDs, requires explicit specificity for built-in archetypes, validates morph ecology references and keeps class-gate references internally consistent. Runtime reloaders independently reject malformed/duplicate archetype, specialization and specialist-tree unlock definitions.

## Save compatibility
Stable namespaced IDs are required for nodes, archetypes, mastery lanes, canonical stats and semantic trees. Renames use aliases/migrations instead of silently deleting player progression.

The current binary save format remains v4. Semantic migrations run idempotently during decode, including the reclassification of Industrialist, Logistician and Prospector from legacy class IDs to specialization IDs. Compatibility tests exercise all supported binary versions (v1 through v4) and verify preservation of earned progression across decode/migration.
