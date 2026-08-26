# RPG Core Foundation — Infinite Progression & Integration Contracts

**Date:** 2026-08-26  
**Target:** Minecraft 1.21.1 / NeoForge / Java 21  
**Branch:** `feat/rpg-core-foundation`  
**Base:** `main@d2fb13064262f5b24dba5f70c1d245ed7a1e5fdb`  
**Status:** Approved architectural direction; implementation baseline  

## 1. Purpose

This specification turns the consolidated Infinite RPG progression design into an implementation boundary that can be developed in parallel with the ongoing perk audit/rework.

The RPG Core owns character level, RPG XP, progression-point accounting, fundamental attributes, canonical/effective stat resolution, world/entity level foundations, quest-facing contracts, persistence, synchronization, migration, observability and tests.

The RPG Core does **not** own the content or final behavior of individual perk IDs (`A0001`, `A0002`, ...), specialist-tree content, quest authoring, or balance constants that the Notion design explicitly leaves open for playtesting.

This document refines the progression/runtime assumptions in `2026-08-22-unified-rpg-skill-tree-design.md`. It does not invalidate that document's tree, mastery, integration-adapter or UI principles unless a rule here explicitly replaces an older bounded-progression assumption.

## 2. Source-of-truth constraints

The implementation must remain consistent with the consolidated Notion architecture and the 2026-08-25 modlist/provider audit.

Hard constraints:

- Character Level starts at **0**.
- There is no gameplay `MAX_LEVEL`.
- "Infinite" means uncapped by design, not mathematically unbounded; implementation uses finite machine representations with explicit overflow handling.
- RPG XP remains separate from vanilla XP.
- The XP curve is calculated from one source of truth and is not a finite table ending at Level 100.
- Fundamental attributes have unlimited ranks by design.
- Fundamental attributes and the main perk tree spend the same **Core Progression Point** pool.
- The finite main perk tree has a separate allocation **Perk Budget** so infinite levels cannot eventually buy every perk.
- Specialist progression uses separate **Specialist Points** and mastery/gateway requirements.
- Mastery represents real use and is not replaced by Character Level or purchase points.
- Server state is authoritative.
- Integrations consume semantic/canonical contracts rather than writing arbitrary third-party or vanilla attributes from scattered perk implementations.
- Project MMO, Paragon, Player Stats, Skills Mastery Reimagined and Improved Mobs are not dependencies of this architecture and must not be reintroduced through old documentation assumptions.

## 3. Non-goals for this branch

The following are deliberately excluded from the first RPG Core branch:

- rewriting or freezing individual audited perks;
- changing the current `Axxxx` catalog while other branches are auditing it;
- implementing final Fúria, Momentum, Foco, Flow, Guarda/Postura, Calibração, Overclock or other perk-domain mechanics;
- implementing specialist-tree content;
- implementing a full quest engine or quest UI;
- choosing final XP coefficients, Core Point frequency, Perk Budget size, attribute coefficients, diminishing-return constants, territory-cell size or mob rarity ranges;
- replacing every provider integration at once;
- restoring removed provider mods to satisfy legacy code.

The core may expose stable primitives needed by those systems, but it must not make their unfinished content decisions for them.

## 4. Architectural invariants

### 4.1 Server authority

All progression mutations occur on the logical server. Clients receive immutable/snapshot state sufficient for UI and presentation. Client packets may request an action, but never submit an authoritative level, XP balance, point balance, rank or effective stat.

### 4.2 Determinism and replay safety

Given the same persisted state, rules snapshot and accepted semantic action, progression resolution must be deterministic.

Events that can be emitted more than once by NeoForge or an integration must have stable deduplication/idempotency semantics where double application would be invalid.

### 4.3 Checked arithmetic

Persistent counters use `long` where appropriate. Every addition, multiplication, curve evaluation and conversion that can overflow must use checked or explicitly saturating arithmetic according to the API contract. Silent wraparound is forbidden.

A technical `long` ceiling is an implementation boundary, not a gameplay maximum.

### 4.4 Versioned rules

All persisted progression state that depends on rules must be interpretable against a versioned `ProgressionRulesSnapshot` (or semantically equivalent model). Save migrations are explicit, monotonic and idempotent.

### 4.5 No direct perk-to-provider coupling

The preferred path is:

```text
Attribute / Perk / Specialist / Equipment / State
                    ↓
             Canonical Stat
                    ↓
        Effective Stat Resolver
                    ↓
 Provider bridge OR RPG-owned effective behavior
```

A provider-specific adapter may exist, but its API must not leak through unrelated perk code.

## 5. Character progression

### 5.1 State model

The core needs a server-side character progression state containing at least:

- current `long level`, initialized to `0`;
- XP progress inside the current level;
- progression schema/rules version;
- Core Progression Point accounting reference/state;
- migration metadata required to make migrations idempotent.

Historical total XP may be recorded for telemetry/migration if it has a justified use, but it must not become a second conflicting source of truth.

### 5.2 InfiniteLevelCurve

`InfiniteLevelCurve` (or equivalent interface) is the only authority for XP cost at a level.

Required properties:

- defined for every reachable non-negative level;
- positive XP requirement for advancing from every reachable level;
- monotonic/non-pathological growth under the selected rules snapshot;
- no pure exponential such as `1.05^level`;
- no precomputed Level-100 threshold table;
- deterministic integer result suitable for persistence;
- explicit overflow behavior.

The exact curve coefficients remain configuration/balance data. Tests validate mathematical invariants independently of the final balance constants.

### 5.3 XP grant semantics

One XP grant may cross zero, one or many levels. The service returns a structured result containing the before/after state and level transitions needed by downstream events/UI.

The implementation must not assume one grant equals at most one level. Extremely large legitimate grants must not require an unsafe unbounded per-level loop when a bounded/aggregated resolution is possible.

Negative XP is not accepted through the ordinary reward path. Administrative rollback/migration uses a distinct privileged operation with explicit semantics.

## 6. Core Progression Points and ledger

Character progression awards a shared general-purpose currency: **Core Progression Points**.

The ledger must distinguish at minimum:

- stable transaction identity;
- source/reason;
- amount;
- earn, spend, refund/respec and migration transaction classes;
- destination/allocation category when spending;
- rules/schema version;
- enough provenance to prevent accidental duplicate first-completion rewards.

The point balance is derived or maintained under ledger invariants so it cannot become negative through normal gameplay operations.

### 6.1 Main perk budget

The main perk tree spends Core Progression Points but is constrained by a separate Perk Budget.

This creates two independent checks:

1. does the player have enough Core Progression Points?;
2. does the requested main-tree allocation fit inside the current Perk Budget?

Attribute spending is not constrained by the finite main-tree Perk Budget.

Perk Budget increases are typed progression rewards so future quests, bosses and milestones can expand build breadth in a controlled way.

### 6.2 Respec

Respec changes allocation and returns eligible Core Progression Points according to ledger rules. It must not erase real mastery history or specialist progression merely because a main-tree allocation changed.

## 7. Universal semantic XP

Gameplay sources do not grant RPG XP directly from scattered event handlers.

Required flow:

```text
NeoForge event / integration adapter / quest adapter
                        ↓
                 SemanticAction
                        ↓
              authorship/origin validation
                        ↓
                 AntiFarmService
                        ↓
                    XpPolicy
                        ↓
          CharacterProgressionService
```

### 7.1 SemanticAction

The model must be extensible and namespaced/data-friendly where practical. Initial action families include:

- advancement completion;
- quest completion;
- hostile/passive kills;
- boss defeat;
- ore/mineral discovery or mining;
- crafting;
- processing/machine completion;
- farming;
- construction;
- biome/structure/dimension discovery;
- other validated adapters.

An action carries the information required for policy without forcing the core to depend on a specific provider class: actor, origin, semantic type, contextual key/fingerprint, target/context metadata and automation/authorship classification where relevant.

### 7.2 XpPolicy

`XpPolicy` converts an accepted semantic action into a progression reward. It must support rewards expressed relative to the next-level cost so the same policy remains meaningful at Level 5 and Level 5,000.

Absolute XP may still exist for carefully justified cases, but it is not the only representation.

### 7.3 AntiFarmService

Anti-farm is a first-class policy boundary, not ad-hoc checks in event handlers.

It must be able to enforce:

- placed-block provenance so `place -> break -> place` does not mint discovery/mining XP;
- first-completion keys for quests/advancements;
- first-kill/special-reward keys where policy requires them;
- repeated-action diminishing returns by configurable context/window;
- distinction between real player authorship and automation;
- entity-farm classification hooks where necessary;
- deduplication of duplicate provider/NeoForge notifications.

Anti-farm rejection must be observable in debug/telemetry without spamming normal gameplay logs.

## 8. Fundamental attributes

The six approved attributes are:

1. `STRENGTH` — Força;
2. `CONSTITUTION` — Constituição;
3. `AGILITY` — Agilidade;
4. `INTELLIGENCE` — Inteligência;
5. `DETERMINATION` — Determinação;
6. `CHARISMA` — Carisma.

`DETERMINATION` replaces the earlier Wisdom/Sabedoria concept.

### 8.1 Rank model

Each attribute has a non-negative invested rank with no gameplay cap. Attribute cost is resolved by a versioned attribute-cost curve/policy and paid from the shared Core Progression Point pool.

The core must keep these concepts separate:

- invested rank;
- raw/base contribution produced by the rank curve;
- modifiers from perks/specialists/equipment/states;
- final RPG Effective Stat;
- provider projection, if any.

This separation is essential for migration, respec, balance changes and high-level numerical stability.

### 8.2 Diminishing returns

Percent-like defensive/offensive effects that would approach invalid or trivializing values use rating/curve conversion or other explicit diminishing-return models. Unlimited ranks must not imply 100%+ avoidance/resistance or unbounded direct provider attributes.

The final coefficients remain balance configuration, not hard-coded architectural constants.

## 9. Canonical and RPG Effective Stats

Infinite progression cannot be represented by blindly writing huge values into vanilla `Attribute`s or third-party mod attributes.

The core therefore owns a canonical/effective stat layer.

A canonical stat definition identifies:

- stable namespaced ID;
- value domain/unit semantics;
- aggregation rules;
- clamping/diminishing-return semantics where required;
- optional provider bridge;
- whether the authoritative behavior is RPG-owned instead.

The resolver combines attribute contributions and later perk/specialist/equipment/state modifiers into stable effective values.

Provider bridges are narrow adapters. Epic Fight, Iron's Spellbooks, Ars Nouveau, Apothic or other integrations are only used when the installed NeoForge 1.21.1 provider exposes a verified stable hook. Provider names/APIs must not be assumed before implementation verification.

## 10. World Threat and territory level

### 10.1 Native Area Level

`TerritoryResolver` resolves a deterministic native threat for a location. Inputs may include:

- dimension;
- biome;
- structure/context;
- distance/location;
- datapack danger tags/overrides;
- deterministic noise;
- world milestones when explicitly configured.

The same location must remain recognizably stable. The implementation should compute stable territory keys and avoid persisting millions of cells when the result can be deterministically recomputed.

### 10.2 Relevant player level

Multiplayer locality is mandatory. The server-wide maximum player level must never be used as the default floor for every mob.

A `RelevantPlayerResolver` (name not normative) selects only players relevant to the local spawn/encounter/combat context according to a testable policy.

## 11. Entity leveling

The central floor is:

```text
base = max(nativeAreaLevel, relevantPlayerLevel)
rolled = base + variance + rarityBonus
final = max(relevantPlayerLevel, rolled)
```

Variance and rarity are applied after the floor and may not push an entity below the relevant player floor.

The exact variance values remain balance data.

### 11.1 Entity classification

Relevant `LivingEntity` instances are classified before scaling. Initial semantic archetypes include:

- hostile;
- neutral;
- passive;
- civilian;
- villager;
- MineColonies colonist;
- guard;
- tamed;
- companion;
- summon;
- boss;
- special.

Technical/decorative/special entities can be explicitly excluded by policy/tag.

Non-hostile scaling is defensive/survival-oriented where appropriate; a passive animal does not become an offensive combat monster merely because the world is high level.

Tamed entities, companions and summons require ownership/summoner-aware policies. Bosses may override the generic curve to preserve encounter mechanics.

## 12. Quest-facing contracts

This branch prepares quests without implementing a quest engine.

### 12.1 Inputs

A quest integration can emit `QUEST_COMPLETED` as a `SemanticAction` with a stable completion key and validated player authorship.

### 12.2 Typed rewards

The core exposes typed reward operations for at least:

- RPG XP;
- Core Progression Points;
- controlled Perk Budget increase;
- Specialist Points or a specialist-facing reward hook when that subsystem is present;
- future milestone/unlock rewards through explicit types rather than arbitrary state mutation.

### 12.3 Queries

Stable read APIs should allow a quest/provider layer to query, without mutating internals:

- Character Level and current XP progress;
- Core Progression Point balance;
- Perk Budget used/available;
- fundamental attribute ranks/effective values;
- mastery/milestone information through existing canonical services;
- specialist unlock/progression through its owning service when implemented.

## 13. Persistence, synchronization and migration

### 13.1 Persistence

Persistence follows the server-side state mechanism already established by the project/NeoForge 1.21.1 runtime after the implementation collision scan. Serialization is versioned and must round-trip deterministically.

### 13.2 Synchronization

Server-to-client snapshots include only the data needed for UI/presentation. Mutation packets are requests and are validated server-side against current authoritative state and rules.

### 13.3 Legacy migration

The current runtime's bounded Level 1-100 model is legacy input, not the new invariant.

Migration requirements:

- explicit migration ID/version;
- idempotent application;
- no silent point duplication/loss;
- deterministic result;
- test fixtures for representative and boundary saves;
- logging/diagnostics sufficient to audit a migrated player;
- migration occurs before the new state is treated as authoritative.

The exact Level-1 legacy -> Level-0/1-new conversion policy is **not invented here**. The consolidated design explicitly leaves that conversion to be defined before production save migration. The implementation may build the migration framework and tests before choosing that balance mapping.

## 14. Events and observability

The core publishes semantic post-mutation events/snapshots such as:

- level advanced;
- XP granted/rejected with reason category;
- Core Point balance/allocation changed;
- attribute rank changed;
- effective stat snapshot invalidated/changed;
- entity level assigned/resolved.

Events carry enough data to update UI/integrations without letting listeners mutate internal state by reference.

Administrative/debug tooling should expose inspectable state, rule version, recent reward decisions and entity/territory resolution. Telemetry is intended for balancing and anti-farm diagnosis, not for changing progression rules implicitly.

## 15. Concurrency and atomicity

Gameplay mutation services execute on the logical server thread or an explicitly serialized equivalent. A progression transaction is atomic from the perspective of a player: XP, level transitions, awarded Core Points and emitted state snapshot cannot expose a half-applied state to listeners.

Persistence/network serialization operates on immutable/copy snapshots where needed to avoid concurrent mutation hazards.

## 16. Package/module boundary

The repository already separates `core/` and `runtime/`. New implementation extends those existing responsibilities rather than creating a parallel engine.

Conceptual ownership:

- `core`: pure progression models, curves, policies, ledger, attribute/effective-stat math, territory/entity-level math and provider-neutral contracts;
- `runtime`: NeoForge event wiring, player/entity storage, networking, command registration, provider adapters and lifecycle hooks;
- `test`: pure invariant tests plus GameTests/runtime tests where Minecraft wiring matters.

Exact package names are selected after collision-checking existing classes; this document does not force a package rename of current audited code.

## 17. Testing contract

No phase is considered complete from compilation alone.

### 17.1 Pure/unit tests

At minimum:

- Level 0 initialization;
- curve positivity and configured monotonicity;
- XP grants just below/at/above boundaries;
- grants crossing many levels;
- checked-overflow boundaries;
- Core Point earn/spend/refund invariants;
- Perk Budget independent from total Core Point balance;
- attribute rank/cost/refund invariants;
- diminishing-return math never exceeds its declared domain;
- semantic-action idempotency/deduplication;
- anti-farm placed-block and first-completion cases;
- deterministic territory resolution;
- entity floor rule;
- multiplayer relevant-player locality;
- serialization round-trip;
- migration idempotency.

### 17.2 Runtime/GameTests

Use runtime tests where they add evidence that pure tests cannot provide:

- NeoForge event -> SemanticAction routing;
- server-authoritative player mutation;
- save/load of attached/persistent state;
- server -> client synchronization contract;
- entity spawn/classification/level assignment;
- relevant multiplayer encounter behavior where the harness supports it;
- dedicated-server smoke remains clean.

## 18. Implementation sequence

The implementation follows the causal order from the consolidated design while allowing the perk-audit branches to continue independently.

### Phase A1 — schema and pure progression

- versioned progression state;
- `ProgressionRulesSnapshot`;
- `InfiniteLevelCurve` contract and implementation;
- multi-level XP resolution;
- overflow-safe math;
- pure tests.

### Phase A2 — Core Point ledger and Perk Budget

- typed ledger transactions;
- balance/allocation invariants;
- respec/refund primitives;
- Perk Budget state/reward contract;
- persistence tests.

### Phase A3 — persistence/network/migration skeleton

- authoritative player state storage;
- codecs/serialization;
- immutable sync snapshot;
- migration registry/framework;
- legacy migration fixtures without inventing unresolved balance mapping.

### Phase B — universal semantic XP

- `SemanticAction` contract/bus;
- `XpPolicy`;
- `AntiFarmService`;
- advancement/quest adapter contracts first;
- combat, exploration, mining/crafting/processing/construction adapters incrementally;
- telemetry and anti-cheese tests.

### Phase C — fundamental attributes and effective stats

- six attribute IDs/ranks;
- cost curves;
- Core Point spending integration;
- canonical/effective stat resolver;
- diminishing-return primitives;
- verified provider bridges only where needed.

### Phase D — perk consumption contracts only on this branch

The RPG Core may expose the stable APIs required by the main perk tree, but concrete `Axxxx` rewrites stay with the perk branches until their audit/specification is frozen and synchronized.

### Phase E — world threat and entity leveling

- deterministic territory resolver;
- relevant-player resolver;
- entity classification;
- effective entity level;
- archetype scaling profiles;
- multiplayer and ecology-preservation tests.

### Phase F — quest readiness

- stable query facade;
- typed quest rewards;
- completion idempotency;
- no quest authoring engine yet.

## 19. Parallel-development contract

While perk branches are active:

- this branch does not edit individual perk specifications or catalog IDs;
- it does not merge perk branches opportunistically;
- perk branches should depend only on stable RPG Core interfaces once those interfaces land;
- if a perk audit changes a required capability, the core adds/adjusts a provider-neutral contract rather than embedding the perk itself;
- conflicts are resolved by explicit synchronization, never by silently overwriting audited content.

## 20. Definition of done for the RPG Core program

The structural RPG system is ready for higher-level content only when all of the following are true:

- uncapped Level-0 progression is authoritative and persisted;
- RPG XP is separate from vanilla XP;
- large grants and overflow cases are safe;
- Core Progression Points, ledger and Perk Budget are auditable;
- universal semantic XP and anti-farm boundaries exist;
- six unlimited-rank attributes feed canonical/effective stats;
- client state is synchronized from the server authority;
- legacy save migration is defined and tested before production release;
- world threat and entity level logic is deterministic and multiplayer-local;
- passive/civilian/tamed/summoned entities have appropriate defensive scaling policies;
- quest systems can query progression and issue typed rewards without mutating internals;
- no individual perk audit was silently overwritten;
- unit tests, relevant GameTests and dedicated-server smoke are green.

## 21. Explicitly open balance decisions

These remain configuration/playtest work and are not blockers for the architecture:

- final XP curve coefficients;
- exact Core Progression Point award cadence;
- initial Perk Budget and controlled expansion values;
- per-rank attribute coefficients;
- diminishing-return constants;
- territory-cell dimensions and threat weights;
- normal/veteran/elite/champion variance and rarity ranges;
- final legacy Level 1-100 conversion mapping.

The implementation must make these values replaceable/versioned rather than baking provisional numbers into save semantics.
