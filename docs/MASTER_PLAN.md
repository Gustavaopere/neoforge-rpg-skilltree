# MASTER PLAN — RPG Skill Tree

Status: **canonical execution plan**  
Target: **Minecraft 1.21.1 + NeoForge 21.1.x + Java 21**

This plan is the consolidated successor to the historical audits in `docs/audits/`. It is ordered by dependency and risk. Do not skip foundation phases to add content volume.

---

## Current baseline

The code baseline audited with Minecraft skills was `87a8ef224af52e1a613bce892a5f3e6732691466`. Subsequent commits at the time this plan was created only added audit/canonical documentation.

Confirmed strengths:

- Java-pure/immutable core foundations;
- server-authoritative purchases/respec;
- versioned progression codec v1–v4;
- player Data Attachment persistence;
- owner-directed networking;
- deterministic data generators/validators;
- dedicated server core smoke passes;
- optional integrations physically separated from core.

Confirmed open blockers include 1.21.1 resource/attribute mismatches, unsafe node-removal reconciliation, client/server rules divergence, missing atomic rule snapshot, canonical stats not controlling runtime, refresh/full-sync hot paths, missing generic dedupe, and insufficient runtime test infrastructure.

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
- Fix all 34 post-1.21.1 vanilla attribute IDs in `node_effects/main.json` to the valid 1.21.1 targets.
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

Make save evolution safe when nodes, ranks, costs, integrations or specialization sources change.

## Required work

Design `ProgressionState v5` around persisted facts rather than derived identity.

An allocation should conceptually retain:

```text
Allocation {
  nodeId
  rank
  paidCost
  currencyId
  sourceTreeId
  provenance
  rulesVersion
}
```

Implement:

- explicit v4→v5 migration;
- idempotent migration matrix for all supported versions;
- historical paid-cost refund instead of current-rule refund;
- administrative reconciliation path for unknown/removed nodes independent of voluntary respec;
- aliases/migrations for renamed IDs;
- policy for ranks above new `maxRank`;
- explicit specialization/unlock provenance;
- separate semantic/economic schema version from binary disk format and network protocol;
- bounds on persisted collections;
- diagnostic recovery/quarantine/export path for corrupt state;
- no silent automatic reset.

## Important current nuance

The merged foundation already preserves migrated Industrialist, Logistician and Prospector specialization IDs. Do not regress that behavior. General provenance is still missing and is the real remaining problem.

## Tests

- v1→v5, v2→v5, v3→v5, v4→v5 fixtures.
- Migration executed twice.
- Encode/decode deterministic round trip.
- Unknown/removed node.
- Renamed node via alias.
- Cost increase/decrease after purchase.
- `maxRank` reduction.
- Provider installed then removed.
- External/chosen/node-granted specialization provenance.
- Death/respawn/clone/End-return as applicable.
- Truncated/corrupt/oversized state.

## Done when

No supported valid save loses progression or prevents login because a definition disappeared. Economic refunds follow one documented, deterministic rule based on persisted acquisition facts.

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
- contribution metadata;
- tree architecture;
- archetypes/classes;
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
- Keep local assets only for textures/icons/translations and genuinely non-authoritative layout presentation.
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
  → minecraft:generic.max_health

rpgskilltree:spell_power
  → irons_spellbooks:<supported binding>
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
- class resolution changed;
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

# Phase 5 — Emergent class/mastery/gateway model becomes the live model

## Goal

Replace transitional legacy identity paths with the intended progression architecture without hard locks.

## Required work

- Give purchased nodes canonical contribution metadata.
- Build `InvestmentState` from live allocations.
- Derive Primary Class and ordered Secondary Classes.
- Preserve deterministic specificity/requirement/display-priority ordering.
- Define hybrid behavior without irreversible hard locks.
- Define tie rules.
- Separate derived labels from persistent choices/achievements.
- Use explicit specialization provenance.
- Formalize mastery XP lanes/levels.
- Separate general points from specialist/subtree currencies.
- Define how mastery grants/unlocks specialist points or gateways.
- Implement real gateway ownership and access.
- Remove transitional hidden bridge surcharges only after physical buyable bridge corridors exist.
- Migrate legacy class state deliberately.

## Tests

- No-class player.
- Primary/secondary resolution.
- Ties.
- Hybrid builds.
- Respec changes derived class correctly.
- Persistent choice survives intended respec.
- Node-granted specialization disappears appropriately.
- External/chosen specialization remains according to provenance.
- Wrong currency rejected.
- Gateway mastery + node + discovery combinations.
- Legacy save migration.

## Done when

One deterministic model explains class identity, specialization, mastery and tree access. There is no competing persisted class authority that can disagree with current investment.

---

# Phase 6 — Final tree engine and UX

## Goal

Choose and stabilize the presentation engine after server rules are authoritative.

## Decision gate

Evaluate a vertical slice for the available Passive Skill Tree port/API/license versus the custom UI. Do not migrate the full 512-node experience before the slice proves:

- purchase/respec;
- rule revision sync;
- one gateway;
- one specialist subtree;
- multiplayer/server authority;
- compatible licensing/API stability for NeoForge 1.21.1.

If custom UI remains:

- formalize it as permanent;
- render server-projected rule view;
- add gateway navigation/breadcrumbs;
- show mastery/currencies/provider availability;
- explain unmet requirements;
- localize new text;
- use normal key mapping consumption;
- add viewport culling/spatial indexes only when profiling shows need.

## Tests

- Client startup.
- GUI scales/resolutions.
- 512+ nodes.
- Keyboard/mouse navigation.
- Rule revision/reconnect.
- Provider unavailable.
- Gateway/subtree navigation.
- Screenshot/manual regression where automation is impractical.

## Done when

The UI never invents authoritative gameplay rules and a main-tree→gateway→subtree flow is complete and understandable.

---

# Phase 7 — Provider SPI and integration hardening

## Goal

Turn current optional adapters into a sustainable integration architecture.

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

Prioritize existing integrations:

1. Iron's Spellbooks;
2. Ars Nouveau;
3. Epic Fight;
4. Goety;
5. Malum;
6. Eidolon;
7. Identity/morph.

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

# Phase 8 — Existing content completion, then new integrations

## Goal

Only after the foundations are stable, turn structural definitions into complete gameplay.

Recommended order:

1. complete navigable/effective Iron's, Ars and Epic Fight progression;
2. complete/harden Goety, Malum, Eidolon and Identity content;
3. implement Create with meaningful outcome-based progression, not machine-tick XP;
4. implement AE2/Oritech/other technology providers only after verified 1.21.1 hooks exist;
5. expand Druid/Metamorph and other RPG systems;
6. add broader content/balance passes.

A provider/tree is not “implemented” merely because JSONs exist. It must have:

- reachable gateway;
- earnable mastery/progression source;
- real effects;
- persistence/migration policy;
- client presentation;
- provider-present runtime tests;
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
- Supported provider matrix.
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

# Decisions that block later phases

See [`docs/decisions/README.md`](decisions/README.md). The most important unresolved decisions are:

- Passive Skill Tree versus permanent custom UI;
- provider baseline: required versus optional;
- `ProgressionDomain` closed enum versus namespaced extensibility;
- exact v5 schema and unknown-node policy;
- specialization provenance and respec semantics;
- morph hostility persistence;
- Python versus NeoForge datagen authority;
- future public API/capability strategy;
- canonical stat global caps;
- Create “meaningful action” semantics.

Do not guess these during implementation. Record an ADR first.