# ADR 015 — Persisted and extensible identifier convention

Status: Accepted  
Date: 2026-08-28

## Context

The RPG Skill Tree persists and exchanges identifiers for nodes, trees, classes, masteries, specializations, provider bindings, rewards, discoveries and other extensible concepts. Historical code contains a mixture of namespaced identifiers and schema-specific legacy strings. Display names, enum ordinals, class names and provider JAR filenames are not stable save identities.

Minecraft and NeoForge use `ResourceLocation` as the canonical namespaced identifier model. Persisted identifiers are part of the save/API contract and cannot be renamed casually.

## Decision

For every **new** persisted or addon-facing extensible identifier:

1. the canonical textual form is a fully namespaced `namespace:path` identifier compatible with Minecraft `ResourceLocation` syntax;
2. identifiers owned by this mod use namespace `rpgskilltree`;
3. provider-owned identities preserve the provider namespace when that provider identity is semantically authoritative;
4. storage and network projections serialize the canonical full textual form, never a display name, translated name, ordinal, Java class name or JAR filename;
5. parsing/normalization happens at the boundary; internal code must not repeatedly reinterpret free-form strings as different identities;
6. unknown namespaced persisted IDs are retained or quarantined according to the owning schema's migration/reconciliation policy, not silently discarded;
7. renaming or removing a persisted ID requires an explicit alias/migration/reconciliation path before the old ID may stop resolving.

Legacy unnamespaced values already present in historical schemas are not silently rewritten by this ADR. They are accepted only through their existing schema-specific compatibility/migration path until the corresponding persistence plan migrates them.

Closed taxonomies may still use enums internally when they are intentionally non-extensible and are not exposed as addon-defined persisted identity. An enum ordinal is never a disk/network identity.

## Alternatives considered

### Continue free-form strings

Rejected. It permits namespace collisions, makes addon ownership ambiguous and encourages accidental coupling to display text.

### Force every historical ID to migrate immediately

Rejected for this foundation slice. A bulk migration without per-schema fixtures would violate the project's save-safety invariants. Existing legacy IDs are handled by the persistence/migration stages.

### Use numeric IDs

Rejected. Registry ordering and generated numeric identifiers are not stable enough to be save/API identities across datapack or mod changes.

## Consequences

- New data models and public APIs have a single identity convention.
- Provider integrations can coexist without ID collisions.
- Existing legacy identifiers remain explicit technical debt rather than being silently reinterpreted.
- Save migrations must preserve aliases/provenance when canonical IDs change.
- Validators may progressively reject newly introduced unnamespaced extensible IDs while allowing documented legacy fixtures.

## Verification required

- Foundation CI continues to freeze the environment and metadata contract.
- New persisted/addon-facing schemas must include namespaced-ID validation in their own tests/validators.
- Migration fixtures must cover any future rename/removal of a persisted identifier.

Related canonical decisions: `plans/DECISIONS.md` D003, D009, D015 and D016 where applicable.
