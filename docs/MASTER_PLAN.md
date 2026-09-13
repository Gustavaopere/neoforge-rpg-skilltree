# MASTER PLAN — RPG Skill Tree

Status: **canonical execution plan**  
Target: **Minecraft 1.21.1 + NeoForge 21.1.x + Java 21**

This plan is the consolidated successor to the historical audits in `docs/audits/`. It is ordered by dependency and risk. Do not skip foundation phases to add content volume.

---

## Current baseline

The original foundation audit used `87a8ef224af52e1a613bce892a5f3e6732691466`. The current architecture closure is reconciled against `main@594cbeea70cb3e16d698933f35391e05eddd7c60` and the physical modlist snapshot of **2026-09-08: 595 top-level entries, NeoForge 21.1.248**.

The class/tree redesign in PR #428 is documentation/architecture only. Runtime authority remains whatever is implemented and tested in `main`; this plan must not be read as evidence that the redesign is already live.

Current architecture direction:

- Tree 1 = permanent base attributes;
- Tree 2 = one connected graph oriented around the 23 audited class identities, not 11 player-facing domain islands;
- Tree 3 = class-centric specialization trees using separate Specialist Point provenance;
- 16 classes may be selected as origin and 7 are acquired as confluence classes through gateways;
- additional classes are formal identities granted by `Class Gateway` requirements, not inferred merely from buying nearby perks;
- no hard cap on number of classes; topology, global progressive cost, permanent base-attribute requirements, Mastery and provider gates create the soft cap;
- provider schools/disciplines remain mechanical/mastery concepts and do not automatically become character classes/specializations;
- external providers are optional/fail-closed unless an explicit baseline contract says otherwise;
- Druid/Metamorph use a first-party Form Engine plan because Identity/Identity2/Woodwalkers are absent from the current physical pack;
- AE2/Oritech specialization definitions are legacy/retired while those providers remain absent from the physical modlist.

Confirmed strengths already present in the historical/current foundation include:

- Java-pure/immutable core foundations;
- server-authoritative purchases/respec;
- versioned progression codec v1–v4;
- player Data Attachment persistence;
- owner-directed networking;
- deterministic data generators/validators;
- dedicated server core smoke passes;
- optional integrations physically separated from core.

Confirmed foundation work still includes save migration, atomic rules authority, canonical stats, mutation/sync hardening, provider runtime validation and sufficient runtime test coverage. Accepted ADRs resolve product/architecture policy; they do not substitute for implementation evidence.

---

# Phase 0 — Reproducible baseline and strict 1.21.1 correctness

## Goal

Make the repository reproducible and eliminate known defects that can cause current gameplay to silently fail on Minecraft 1.21.1.

## Required work

### Build/test baseline

- Add Gradle Wrapper pinned to the approved Gradle version (current CI baseline: 8.14).
- Switch CI/local canonical commands to `./gradlew`.
- Integrate JUnit 5 with Gradle while preserving existing dependency-free tests until migrated.
- Configure NeoForge GameTest support and `runGameTestServer`.
- Make generated-data verification use `git diff --exit-code`, not only `git diff --check`.
- Add workflow timeouts/concurrency where useful.

### 1.21.1 resource fixes

- Move the boss entity tag from plural registry folder semantics to the correct 1.21 singular path.
- Make Cataclysm tag entries optional (`required: false`) unless Cataclysm becomes a required dependency.
- Fix all post-1.21.1 vanilla attribute IDs in `node_effects/main.json` to valid 1.21.1 targets.
- Make validators detect invalid registry/resource IDs for the target version where feasible.
- Replace silent missing-attribute skips with explicit validation/unavailability diagnostics.

### Small client correctness

- Move tree key handling to normal `KeyMapping` consumption (`consumeClick`) during client tick when client input work is touched.

## Tests

- Regression for boss tag loading.
- Runtime/GameTest attribute application for max health, armor, attack damage/speed, movement speed, luck and knockback resistance.
- Core-only dedicated server.
- Optional tag/provider absence.
- Generator drift gate.
- Wrapper build from clean checkout.

## Done when

- Clean checkout builds without system Gradle.
- CI fails if generators change committed outputs.
- No loaded vanilla effect targets an attribute ID invalid in 1.21.1.
- Boss tag resolves under the correct registry folder.
- Missing required effect targets are actionable failures or explicit unavailable nodes, never silent no-ops.

---

# Phase 1 — ProgressionState v5, save safety and reconciliation

## Goal

Make save evolution safe when nodes, ranks, costs, class identities, providers or specialization sources change.

## Accepted policy

ADR 004, ADR 006, ADR 007 and ADR 015 now define the relevant persistence policy:

- new v5 node acquisitions retain historical acquisition batches including exact paid cost, currency, source tree, provenance and rules version;
- refunds use persisted historical facts in deterministic LIFO order;
- unknown/removed allocations are quarantined rather than silently deleted;
- legacy v1–v4 migration never fabricates unknown historical facts as truth;
- specialization/unlock provenance is explicit;
- new persisted/extensible identities are namespaced.

These policies are **accepted design**, not yet evidence that the complete v5 codec/migration runtime is implemented.

## Required work

Implement:

- explicit v4→v5 migration;
- idempotent migration matrix for all supported versions;
- historical paid-cost refund using persisted allocation batches;
- administrative reconciliation path for unknown/removed nodes independent of voluntary respec;
- aliases/migrations for renamed IDs;
- policy enforcement for ranks above new `maxRank`;
- explicit specialization/unlock provenance;
- separate semantic/economic schema version from binary disk format and network protocol;
- bounds on persisted collections;
- diagnostic recovery/quarantine/export path for corrupt state;
- no silent automatic reset.

The merged foundation already preserves migrated Industrialist, Logistician and Prospector specialization IDs. Do not regress that behavior. The remaining gap is runtime materialization of the generic provenance/migration model, not an unresolved provenance policy.

## Tests

- v1→v5, v2→v5, v3→v5, v4→v5 fixtures.
- Migration executed twice.
- Encode/decode deterministic round trip.
- Unknown/removed node.
- Renamed node via alias.
- Cost increase/decrease after purchase.
- `maxRank` reduction.
- Provider installed then removed.
- External/chosen/node-granted/mastery-granted/migration specialization provenance.
- Death/respawn/clone/End-return as applicable.
- Truncated/corrupt/oversized state.

## Done when

No supported valid save loses progression or prevents login because a definition disappeared. Economic refunds follow persisted acquisition facts or an explicit legacy migration basis; missing evidence is never invented.

---

# Phase 2 — Atomic ProgressionRulesSnapshot and server→client rule authority

## Goal

Create one coherent, revisioned effective ruleset on the server and stop maintaining authoritative gameplay rules independently in client assets.

## Required work

Introduce an immutable `ProgressionRulesSnapshot` containing at least:

- node definitions;
- graph/edges;
- costs/currencies;
- access requirements;
- node effects;
- contribution/affinity metadata;
- tree architecture and class regions;
- origin/gateway class definitions;
- specializations/provenance policies;
- mastery definitions;
- gateways/tree unlocks;
- provider requirements;
- canonical stat bindings;
- client-safe projection;
- revision/hash.

Reload pipeline:

```text
parse
→ normalize IDs
→ local validation
→ cross-validation
→ provider/binding validation
→ compile snapshot
→ atomic publish
→ reconcile online players
→ sync revision/view
```

If any stage fails, retain the previous known-good snapshot.

Client work:

- Replace gameplay-critical classpath `getResourceAsStream` catalogs with server-projected rules.
- Keep local assets only for textures/icons/translations and genuinely non-authoritative presentation metadata.
- Clear client progression/rule caches on disconnect.
- Detect rule revision change.

## Tests

- Valid reload.
- Invalid partial reload keeps old snapshot.
- Cross-reference failure.
- Duplicate namespaced ID.
- Datapack override.
- Cost/requirement change reflected identically client/server.
- Effect removal from online player.
- Reconnect to another server with different revision.
- Multiple players receive only authorized state.

## Done when

There is exactly one effective gameplay rules revision. The client presents that revision and cannot independently disagree about authoritative costs/access.

---

# Phase 3 — Canonical stat runtime and deterministic effects

## Goal

Make the canonical attribute model real rather than an unused core abstraction.

## Required work

All gameplay effects should target a canonical stat, then resolve to a vanilla/provider binding.

Conceptual examples:

```text
rpgskilltree:max_health
  → minecraft:<validated 1.21.1 max-health binding>

rpgskilltree:spell_power
  → <validated provider binding>
```

Each effect/binding must define or inherit:

- canonical stat ID;
- provider/target binding;
- operation;
- stacking group;
- deterministic ordering;
- cap/floor policy;
- optional-provider policy;
- stable modifier identity;
- source node/revision.

Implement:

- canonical binding validation at snapshot construction;
- explicit unavailable/fallback policy;
- deterministic stacking and caps;
- stable namespaced modifier IDs;
- cleanup of removed/reloaded effects without unbounded historical bookkeeping;
- provider absence behavior that prevents useless purchases.

D011 remains the important open balance/precedence ADR for exact global/stat-specific caps and provider precedence. Do not freeze those semantics implicitly in implementation.

## Tests

- Vanilla bindings.
- Provider binding present/absent.
- Additive/multiplicative ordering.
- Cap/floor behavior.
- Hybrid nodes targeting same stat.
- Purchase/upgrade/respec.
- Login/death/reload.
- Provider removed after save.
- Modifier collision prevention.

## Done when

Every declared effect either resolves deterministically or prevents/invalidates the rule with a diagnostic. There are no silent target failures.

---

# Phase 4 — Central mutation pipeline, coalesced sync, dedupe and security

## Goal

Support frequent combat/spell/mastery events without rebuilding all effects or sending full state for every event.

## Required work

Introduce `ProgressionMutationService` as the central mutation boundary:

```text
current state + rules snapshot + validated semantic intent
  → next state + audit result + dirty reasons
```

Dirty reasons may include:

- persistent state changed;
- effects changed;
- class/gateway state changed;
- tree availability changed;
- mastery-only display changed;
- client rules revision changed.

Implement:

- effect refresh only when effect-affecting state changes;
- sync coalescing per player/tick;
- delta or scoped sync where useful;
- C2S rate limits/backpressure;
- invalid-request handling without unlimited full resync amplification;
- `SemanticAction` normalization for provider/vanilla events;
- fingerprint/event identity;
- dedupe window;
- propagated `procDepth`;
- centralized fake-player/creative/spectator policy;
- centralized anti-farm/cooldown policy;
- aggregated diagnostics rather than hot-path log spam.

## Tests

- XP/mastery event with no rank change does not refresh attributes.
- Multiple awards in one tick coalesce sync.
- Spam/replay of C2S request.
- Vanilla + provider duplicate observation of same action.
- Secondary proc recursion.
- Provider cancellation versus confirmed outcome.
- Multiplayer attribution.
- Synthetic load/performance tests for many players/events.

## Done when

A common mastery award is O(progress mutation) rather than O(all effects + full packet) and each semantic action rewards at most once according to explicit policy.

---

# Phase 5 — Origin classes, class gateways, Mastery and specialist progression become live

## Goal

Replace transitional emergent-class inference with the approved class-oriented architecture while preserving build freedom.

## Canonical class contract

The current design authority is `plans/04-classes-masteries-specializations/07-class-oriented-progression-overhaul.md` plus `08-class-specialization-readiness-audit.md`.

- The player chooses one **Origin Class** from the 16 origin-eligible classes.
- Origin determines the Tree 2 starting position and any audited origin signature/affinity rule.
- Tree 2 remains one connected graph. Class regions are neighborhoods, not isolated subtrees.
- Buying perks in another region does **not** automatically grant that class.
- Formal additional class identity is granted only through the corresponding **Class Gateway** and requirements.
- Seven audited class identities are confluence classes and are not selectable as origin.
- There is no hardcoded maximum class count.
- The soft cap is systemic: path distance, progressive global Tree 2 cost, permanent Tree 1 attribute requirements, Mastery, provider gates and opportunity cost.
- Requirement checks such as Intelligence/Strength use permanent Tree 1 base investment, not equipment or temporary buffs.
- Legacy 11 `ProgressionDomain` values remain internal compatibility/analytics taxonomy only; they do not dictate visible Tree 2 topology.
- Provider schools/disciplines are requirements/masteries, not automatically class identities.

## Required work

- Persist/migrate origin-class choice deliberately with namespaced identity.
- Implement server-authoritative origin selection and start position.
- Compile one connected Tree 2 graph with shared nodes represented by one gameplay ID.
- Implement progressive global Tree 2 cost and origin-affinity pricing exactly from the effective rules snapshot.
- Persist the amount actually paid for deterministic respec.
- Implement permanent base-attribute requirements.
- Implement `Class Gateway` ownership/access and explicit secondary-class grants.
- Preserve multiple formal class identities without a hard class-count cap.
- Formalize Mastery XP lanes/levels and provider-native semantic actions.
- Implement Specialist Points as a separate, provenance-aware currency awarded only from declared mastery/milestone/quest/specialist sources.
- Implement the audited class-centric Tree 3 catalog; provider schools/disciplines feed requirements but are not counted as character specializations.
- Enforce specialization `choice_group` only where explicitly declared; do not invent a global specialization cap.
- Migrate legacy emergent/hybrid class state deliberately rather than silently recomputing identity under the new rules.

## Tests

- origin selection and immutable/explicit respec policy;
- every one of the 16 allowed origins starts at the intended region;
- all 7 confluence-only classes cannot be chosen as origin;
- cross-region perk purchase does not grant a class;
- class gateway grants exactly the intended formal identity;
- two/three/more class identities are possible when requirements are actually paid, with no hidden hard cap;
- base-attribute gate ignores equipment/buffs;
- shared node is charged/applied once;
- dynamic-cost purchase and historical-cost refund;
- provider-required gateway available/unavailable behavior;
- chosen/node/mastery/provider/migration specialization provenance;
- Specialist Points cannot be manufactured from general Character Level points;
- legacy save migration.

## Done when

One server-authoritative model explains origin, additional formal classes, shared Tree 2 investment, Mastery, Specialist Points and Tree 3 access without competing class authorities.

---

# Phase 6 — Permanent custom tree engine and UX

## Goal

Implement the custom tree presentation/authoring architecture accepted by ADR 001 after server rules are authoritative.

## Accepted decision

The engine-choice gate is closed. The RPG Skill Tree owns its permanent UI/authoring pipeline and consumes the server-projected canonical rules snapshot. Pufferfish's Skills is not RPG progression authority. Mine and Slash remains clean-room engineering reference only.

The implementation **vertical slice is still mandatory** before scaling the complete graph. It must prove:

- two neighboring class regions in one connected graph;
- one shared gameplay node rendered once;
- one Class Gateway;
- one specialization gateway/Tree 3 transition;
- progressive/dynamic cost and origin affinity;
- permanent base-attribute requirement;
- purchase/respec round trip;
- server→client rule revision sync;
- multiplayer/server authority;
- pan/zoom, hitboxes and navigation.

If the authoring source uses `grid -> parser -> graph`, the compiled canonical graph must still pass server-side validation before publication.

## Required work

- render server-projected rule view;
- show origin/class identities, gateway state, Mastery, currencies and provider availability;
- explain unmet requirements and actual current purchase cost;
- show specialization navigation/breadcrumbs;
- localize new text;
- use normal key mapping consumption;
- profile 512/750/1000+ nodes before introducing advanced culling/spatial-index complexity;
- ensure client layout metadata cannot create gameplay IDs or authority absent from the server snapshot.

## Tests

- client startup;
- GUI scales/resolutions;
- 512/750/1000+ node profiling;
- keyboard/mouse navigation;
- rule revision/reconnect;
- provider unavailable;
- class gateway and specialist navigation;
- shared-node rendering identity;
- screenshot/manual regression where automation is impractical.

## Done when

The custom UI never invents authoritative gameplay rules and the origin→Tree 2 region→Class Gateway→Tree 3 flow is complete and understandable.

---

# Phase 7 — Provider SPI and integration hardening

## Goal

Turn current optional adapters into a sustainable integration architecture.

## Accepted provider policy

ADR 002 applies: external gameplay providers are optional integrations unless explicitly promoted to a baseline dependency. Missing or unsupported providers must fail closed or make their dependent content explicitly unavailable; they must not leave purchasable no-ops.

## Required work

- Small provider descriptor/factory boundary.
- Isolate provider classloading.
- Declare supported optional version ranges in metadata where appropriate.
- Convert provider events to `SemanticAction`.
- Centralize creative/spectator/fake-player policy.
- Require confirmed outcome when available.
- Fail visibly on reflection/API drift.
- Fail closed or clearly unavailable for authorization-critical mixins.
- Clean caches/maps on logout/reload.
- Preserve provider-native semantics/resources.

Prioritize physically present/current integrations with verified exact-version contracts, including Iron's Spellbooks, Ars Nouveau, Epic Fight, Goety, Malum, Eidolon and Create. Druid/Metamorph transformation itself is first-party; creature/provider adapters for forms remain optional capabilities and must be validated independently.

AE2, Oritech, Identity/Identity2 and Woodwalkers are not current physical-provider targets while absent from the modlist. Their legacy IDs may remain migration inputs but must not authorize current gameplay.

## Tests

Matrix at minimum:

- core only;
- provider isolated;
- important provider combinations;
- dedicated server;
- client login;
- provider removed after save;
- valid/cancelled action;
- duplicate event;
- creative/spectator/fake player;
- multiplayer attribution;
- unsupported provider version/fail-visible behavior.

## Done when

Adding/removing one supported provider cannot break startup, save, rule snapshot or UI, and its progression only fires from confirmed normalized actions.

---

# Phase 8 — Class-oriented content completion, Form Engine and current integrations

## Goal

After foundations are stable, turn the audited structural definitions into complete gameplay without reviving removed-provider assumptions.

Recommended order:

1. implement the class-oriented Tree 2 vertical slice and its v5/save/rules dependencies;
2. complete navigable/effective Iron's, Ars and Epic Fight disciplines as shared provider-native Mastery inputs;
3. complete/harden Goety, Malum, Eidolon and other physically present occult/magic adapters;
4. implement Create progression using ADR 010 semantic outcomes, never machine ticks/rotation presence;
5. implement the first-party Form Engine for Druid/Metamorph, then add creature-provider adapters only after exact-version validation;
6. materialize the class-centric Tree 3 specialization catalog and Specialist Point sources;
7. migrate/retire the 25 historical specialization definitions according to the readiness audit;
8. keep AE2/Oritech/Identity/Woodwalkers content unavailable/legacy while those providers remain absent; reintroduction requires a fresh physical-modlist/API audit, not silent substitution;
9. expand broader content/balance only after real runtime evidence exists.

### Encyclopedia / bestiary — creature location and tracking

Plan an exploration function tied to creatures already discovered/unlocked in the encyclopedia:

- each creature entry can explain **where it can be found**, including dimension, biome, habitat/environment, related structures and relevant spawn conditions when those data are available;
- add a **Procurar / Seguir** action to select a discovered creature as the current search target;
- when possible, identify a suitable known/searchable location for that creature and create a **waypoint/marker on the map** so the player can travel toward it;
- integrate the marker with the map/waypoint solution adopted by the modpack;
- keep habitat/search guidance distinct from an exact live-entity position: the guide should not imply that a mob is currently at a coordinate unless that position is actually known by the game/system.

Intended flow:

```text
discover creature
→ open encyclopedia entry
→ see habitat/spawn information
→ choose Procurar / Seguir
→ receive map marker
→ travel and search for the creature
```

A provider/tree/specialization is not “implemented” merely because JSON or a plan exists. It must have:

- reachable gateway/path;
- earnable mastery/progression source where required;
- real effects/mechanics;
- persistence/migration policy;
- client presentation;
- provider-present runtime tests where applicable;
- provider-absent safe behavior.

---

# Phase 9 — Beta/release readiness

## Goal

Produce a reproducible, migratable, supportable release.

## Required work

- Explicit license file and source/reference policy.
- Reproducible release workflow.
- Version/changelog alignment.
- Checksums/artifacts.
- Save backup/migration guide.
- Supported provider matrix tied to physical versions.
- Client + dedicated server acceptance pass.
- Multiplayer soak/playtest.
- Performance profiling on realistic modpack.
- Rollback/recovery documentation.

## Done when

- no known P0/P1 foundation defects remain;
- save migration is tested;
- release build is reproducible;
- supported integrations have real runtime evidence;
- documentation matches shipped behavior.

---

# Architecture decisions still open

See [`docs/decisions/README.md`](decisions/README.md).

The architecture closure in PR #428 accepts D001, D002, D003, D006, D007, D008, D010, D012 and D014. D004/D005/D015 already have accepted persistence foundations. Do not reopen those implicitly during implementation; use a new/revised ADR when evidence requires a deliberate change.

The material open decisions are:

- **D011 — canonical stat global caps and provider precedence**: blocks the final Phase 3 balance contract;
- **D013 — public integration API for other mods**: intentionally not urgent and does not block core stabilization without a real consumer.

D009 remains a provisional one-JAR packaging direction and is revisited only if concrete classpath/licensing/version-matrix evidence requires it.

Do not guess unresolved semantics during implementation. Record the decision explicitly before freezing a contract.
