# Architecture Decisions — Open Queue

This directory tracks decisions that must be resolved explicitly before the corresponding implementation phase. Do not let a future agent silently guess these choices.

When a decision is made, create an ADR in this directory and update this index with status, date and consequences.

---

## D001 — Tree engine: Passive Skill Tree versus permanent custom UI

**Status:** ACCEPTED — custom RPG Skill Tree UI and authoring; see [ADR 001](001-tree-engine-custom-ui.md)  
**Blocks:** no longer blocks Phase 6 engine choice; implementation vertical slice remains mandatory

The RPG Skill Tree keeps its own UI/authoring pipeline as a consumer of server-authoritative canonical rules. Pufferfish's Skills remains installed for its own consumers but is not progression authority for this project. Mine and Slash is an engineering reference only, clean-room.

---

## D002 — Provider baseline: required versus genuinely optional mods

**Status:** ACCEPTED — external providers are optional integrations unless an explicit baseline contract says otherwise; see [ADR 002](002-provider-baseline-optional-integrations.md)  
**Blocks:** no design blocker; constrains Phase 3/7 provider validation

Provider-backed rules must fail closed or become explicitly unavailable when the provider/contract is absent. Presence in historical docs is not enough; the physical current modlist remains authority for installed versions.

---

## D003 — Progression domains: closed enum versus namespaced extensibility

**Status:** ACCEPTED — closed internal macro taxonomy; see [ADR 003](003-progression-domains-closed-internal-taxonomy.md)  
**Blocks:** no v5 schema blocker from domain extensibility

The 11 legacy `ProgressionDomain` values remain internal compatibility/analytics taxonomy. They are not the player-facing Tree 2 topology and are not addon-extensible persistent identities. Tree 2 is class-oriented and graph-driven.

---

## D004 — ProgressionState v5 exact schema

**Status:** PARTIALLY ACCEPTED — allocation economics are defined by [ADR 004](004-progression-state-v5-allocation-economics.md); specialization provenance is defined by [ADR 007](007-specialization-provenance-respec.md); domain extensibility is defined by [ADR 003](003-progression-domains-closed-internal-taxonomy.md)  
**Blocks:** Phase 1 implementation details still need complete codec/migration materialization

Accepted facts now include historical acquisition batches, quarantine for unknown nodes, namespaced IDs, explicit specialization provenance and closed internal domains. Remaining work is implementation/migration mechanics, not an unresolved product taxonomy decision.

---

## D005 — Unknown/removed node policy

**Status:** ACCEPTED FOR v5 NODE ALLOCATIONS — quarantine/retain, never silently delete; see [ADR 004](004-progression-state-v5-allocation-economics.md)  
**Blocks:** no design blocker; Phase 1 implementation required

Unknown or removed v5 node allocations retain complete acquisition history in quarantine. They grant no live effects/access while quarantined but remain serializable/exportable/restorable by alias, migration or administrative reconciliation.

---

## D006 — Refund and economic migration policy

**Status:** ACCEPTED — exact historical paid cost for v5; legacy migration may infer only from an explicit documented basis and must never label inferred data as historical truth; see [ADR 006](006-legacy-allocation-migration-refunds.md) and [ADR 004](004-progression-state-v5-allocation-economics.md)  
**Blocks:** no policy blocker; migration tables/fixtures remain implementation work

New acquisitions refund from persisted historical batches in deterministic LIFO order. Legacy v1–v4 state is migrated by an explicit rule-aware process; missing evidence is quarantined/admin-reconciled rather than fabricated.

---

## D007 — Specialization provenance and respec semantics

**Status:** ACCEPTED — see [ADR 007](007-specialization-provenance-respec.md)  
**Blocks:** no design blocker; Phase 1/5 implementation required

Specialization/unlock facts carry explicit provenance such as player choice, node-granted, mastery/milestone-granted, provider/external and migration/legacy. Respec removal follows provenance instead of treating every specialization as equivalent.

---

## D008 — Morph hostility persistence

**Status:** ACCEPTED — form identity does not copy/persist arbitrary entity faction/AI/hostility authority; see [ADR 008](008-form-disposition-hostility.md)  
**Blocks:** no design blocker; Form Engine implementation must follow explicit disposition rules

The first-party Form Engine reproduces only declared capabilities. Hostility/disguise behavior is derived from explicit form/disposition rules and bounded runtime state, not copied entity NBT/AI/faction state.

---

## D009 — Integration packaging

**Status:** PROVISIONAL DIRECTION: single JAR with strict logical boundaries  
**Blocks:** long-term Provider SPI only

Keep one JAR until real classpath/dependency, licensing or version-matrix pressure justifies companion modules/mods. Revisit only with concrete evidence.

---

## D010 — Create progression semantics

**Status:** ACCEPTED — semantic outcomes, never passive ticking; see [ADR 010](010-create-meaningful-progression.md)  
**Blocks:** no design blocker; Create adapters still require exact-hook validation

Engineering mastery may be granted from attributable processing/automation/contraption outcomes and explicit discoveries/milestones. Machine rotation or tick presence alone never grants progression.

---

## D011 — Canonical stat global caps and provider precedence

**Status:** OPEN  
**Blocks:** Phase 3 balance contract

Still define global versus stat-specific caps, stacking groups, additive/multiplicative order, provider precedence, fallback/unavailable behavior and whether hard safety caps live in code or datapack rules.

---

## D012 — Datagen authority: Python versus NeoForge providers

**Status:** ACCEPTED — Python remains canonical generator for the resource families it already owns; see [ADR 012](012-datagen-authority.md)  
**Blocks:** no design blocker; implementation may migrate a family only through an explicit authority handoff

Never maintain two independent generators for the same output. NeoForge datagen may own non-overlapping resources or receive a family only after the Python authority for that family is deliberately retired.

---

## D013 — Public integration API for other mods

**Status:** OPEN / NOT URGENT  
**Blocks:** external addon ecosystem, not core stabilization

Introduce a public read/query API only when a real consumer exists. Do not add a capability merely to duplicate the canonical Data Attachment.

---

## D014 — Artificer taxonomy

**Status:** ACCEPTED — Artificer is not a 24th top-level class; see [ADR 014](014-artificer-taxonomy.md)  
**Blocks:** no class-taxonomy blocker

Artificer remains a compatibility/future specialization concept under technology/engineering unless a later explicit design reopens the taxonomy. Existing stray JSON/history must not activate a new class implicitly.

---

## D015 — Persisted and extensible identifiers are namespaced

**Status:** ACCEPTED — see [ADR 015](015-persisted-resource-id-convention.md)  
**Blocks:** no Foundation blocker; constrains future persistence/addon schemas

New persisted or addon-facing extensible identities use canonical full `namespace:path` form compatible with Minecraft `ResourceLocation` syntax. Existing legacy unnamespaced values remain explicit compatibility/migration inputs until their owning schema migrates them deliberately.

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
