# Architecture Decisions — Open Queue

This directory tracks decisions that must be resolved explicitly before the corresponding implementation phase. Do not let a future agent silently guess these choices.

When a decision is made, create an ADR in this directory and update this index with status, date and consequences.

---

## D001 — Tree engine: Passive Skill Tree versus permanent custom UI

**Status:** OPEN  
**Blocks:** Phase 6

Question: should the project integrate/extend a compatible Passive Skill Tree implementation, or formalize the custom tree UI as the permanent engine?

Decision method:

- verify NeoForge 1.21.1 compatibility/API/license;
- build one vertical slice: main tree → gateway → one subtree → purchase/respec → multiplayer sync;
- compare integration cost, maintainability, performance and UX;
- do not migrate all 512 nodes before the slice proves feasibility.

---

## D002 — Provider baseline: required versus genuinely optional mods

**Status:** OPEN  
**Blocks:** Phase 3/7 provider availability rules

The current code is mostly classloading-safe when providers are absent, but some nodes still target provider attributes. Define which providers are:

- mandatory modpack baseline;
- optional but supported;
- experimental/planned.

For optional providers, gameplay must degrade safely: unavailable nodes must be blocked/removed/fallback explicitly rather than remain purchasable no-ops.

---

## D003 — Progression domains: closed enum versus namespaced extensibility

**Status:** OPEN  
**Blocks:** ProgressionState v5 and addon API

Current `ProgressionDomain` is an enum. Decide whether domains are intentionally a closed macro taxonomy or whether addons/providers may define new domains through namespaced IDs.

If external extensibility is a goal, migrate before v5 freezes the persisted contract.

---

## D004 — ProgressionState v5 exact schema

**Status:** OPEN  
**Blocks:** Phase 1

Minimum facts expected for node allocation:

```text
nodeId
rank
paidCost
currencyId
sourceTreeId
provenance
rulesVersion
```

Also decide:

- disk format version versus semantic/economic schema version;
- unknown-ID preservation/quarantine representation;
- specialization/unlock provenance representation;
- bounds for collections;
- migration metadata.

---

## D005 — Unknown/removed node policy

**Status:** OPEN  
**Blocks:** Phase 1

Possible mechanisms may include:

- alias migration;
- administrative removal with historical refund;
- quarantine/orphan retention;
- invisible compatibility holding state.

Invariant: a missing definition must never prevent login, and unknown persisted progression must never be silently discarded.

---

## D006 — Refund and economic migration policy

**Status:** PROVISIONAL DIRECTION: historical paid cost  
**Blocks:** Phase 1

Recommended rule from both audits: persist what was actually paid and refund from acquisition history rather than recalculating from the current datapack cost.

Still decide:

- treatment of legacy v1–v4 purchases without historical paid-cost data;
- policy for max-rank reduction;
- policy for retroactive datapack economic changes;
- whether old economic revisions need explicit migration tables.

---

## D007 — Specialization provenance and respec semantics

**Status:** OPEN  
**Blocks:** Phase 1/5

Current merged code explicitly preserves migrated `industrialist`, `logistician` and `prospector`, but generic provenance is not persisted.

Define sources such as:

- node-granted;
- mastery-granted;
- explicit player choice;
- provider/external;
- migration/legacy achievement.

For each source, define whether respec can remove it and how reload/uninstall reconciliation behaves.

---

## D008 — Morph hostility persistence

**Status:** OPEN  
**Blocks:** Morph hardening

Current hostility/disguise-compromise memory is session/runtime oriented. Decide whether hostility is:

- intentionally temporary/session-local;
- persisted across reconnect/server restart;
- partially persisted with a bounded expiry.

The gameplay design and save schema must agree.

---

## D009 — Integration packaging

**Status:** PROVISIONAL DIRECTION: single JAR with strict logical boundaries  
**Blocks:** long-term Provider SPI

Current recommendation is to keep one JAR until real classpath/dependency conflicts justify companion modules/mods.

Revisit only if:

- compile/runtime provider conflicts become difficult to isolate;
- distribution size/licensing requires separation;
- provider version matrices become unmanageable in one artifact.

---

## D010 — Create progression semantics

**Status:** OPEN  
**Blocks:** Create integration

Define “meaningful use” for engineering mastery without passive/tick farming.

Prefer semantic outcomes such as:

- recipe/processing completion attributable to a player/system ownership context;
- contraption milestone;
- first/qualified automation outcome;
- engineering discovery/achievement.

Do not grant progression simply because a machine ticks or rotates.

---

## D011 — Canonical stat global caps and provider precedence

**Status:** OPEN  
**Blocks:** Phase 3 balance contract

Define:

- global versus stat-specific caps;
- stacking groups;
- additive/multiplicative order;
- provider precedence when multiple mods expose overlapping stats;
- fallback/unavailable behavior;
- whether caps are hard-coded safety limits or datapack definitions.

---

## D012 — Datagen authority: Python versus NeoForge providers

**Status:** OPEN  
**Blocks:** CI/datagen cleanup

Current committed content is primarily generated by Python scripts while `runData` exists without substantive NeoForge providers.

Choose one of these explicit models:

1. Python remains the canonical generator and NeoForge datagen is used only for non-overlapping resources;
2. migrate selected resource families to `GatherDataEvent` providers and retire their Python generator;
3. another documented split with no overlapping authority.

Never maintain two independent generators for the same output.

---

## D013 — Public integration API for other mods

**Status:** OPEN / NOT URGENT  
**Blocks:** external addon ecosystem, not core stabilization

Possible surface:

- read-only `EntityCapability` for progression query;
- service/API registry;
- both, with one canonical underlying view.

Do not add a capability merely to duplicate the Data Attachment. Introduce an API only when a real consumer/use case exists.

---

## D014 — Artificer taxonomy

**Status:** OPEN  
**Blocks:** final emergent-class taxonomy

Existing architecture/history contains inconsistent assumptions about Artificer. Do not activate or delete it merely because JSON exists. Resolve whether it is:

- top-level emergent class;
- specialization;
- provider/gateway identity;
- deprecated compatibility artifact.

Use the intended progression design and node contribution model as authority.

---

# ADR format

When resolving a decision, create a file such as:

```text
001-tree-engine.md
```

Recommended structure:

```markdown
# ADR 001 — Title

Status: Accepted
Date: YYYY-MM-DD

## Context
## Decision
## Alternatives considered
## Consequences
## Migration/compatibility impact
## Tests/verification required
```

Then update this index and `docs/MASTER_PLAN.md` if the accepted decision changes phase requirements.