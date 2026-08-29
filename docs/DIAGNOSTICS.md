# Runtime diagnostics

Runtime operational messages use a stable prefix:

`[rpgskilltree/<category>/<event>]`

The SLF4J logger remains owned by the emitting class. The prefix is an operational taxonomy for grep, support reports and CI evidence; it is not a replacement logging framework.

## Categories

- `bootstrap` — mod startup, lifecycle and required environment state.
- `compat` — optional-provider presence, version gating and integration fallback.
- `progression` — canonical progression mutation/runtime failures.
- `effects` — application or removal of gameplay effects/modifiers.
- `compendium` — Compendium catalog publication, runtime inventory and related operational work.

Event IDs are stable `lower_snake_case` tokens. They should identify the machine-searchable event rather than repeat the full human message.

## Severity

### INFO

Use `INFO` for bounded normal lifecycle events and summaries that are useful to operators, such as optional-provider summaries or a catalog being published. Do not emit per-tick/per-entity informational spam.

### WARN

Use `WARN` when the runtime can continue safely but behavior is degraded, disabled or using a deliberate fallback. A warning must state what was not applied and why when that context is known.

### ERROR

Use `ERROR` when an operation that should have completed failed or when a committed/runtime transition encounters an unexpected failure. Include the originating exception. Correctness-sensitive callers should continue to fail closed rather than logging an error and silently accepting partial state.

## Current canonical events

- `[rpgskilltree/compat/optional_providers]` — bounded provider presence/version summary.
- `[rpgskilltree/compat/epicfight_version_unsupported]` — Epic Fight hook disabled because the loaded version is outside the hook's explicitly supported contract.
- `[rpgskilltree/progression/mutation_listener_failed]` — a post-commit progression observer failed.
- `[rpgskilltree/effects/attribute_effect_unavailable]` — a purchased/resolved node effect targets an unavailable registry/player attribute and is not applied.
- `[rpgskilltree/compendium/runtime_inventory_written]` — runtime inventory report was written.
- `[rpgskilltree/compendium/runtime_inventory_write_failed]` — runtime inventory collection/write failed and is propagated.
- `[rpgskilltree/compendium/entity_catalog_published]` — entity catalog publication completed.
- `[rpgskilltree/compendium/flora_catalog_published]` — flora/tree/crop catalog publication completed.
- `[rpgskilltree/compendium/world_catalog_published]` — world geography catalog publication completed.

## Logging constraints

Diagnostics must remain bounded and operationally useful. Do not log full NBT/state snapshots, secrets, authentication data or unbounded user-controlled payloads. Prefer stable identifiers and counts. Repeated degradation on hot paths should be deduplicated/rate-limited by the owning subsystem rather than flooding logs.

When adding a new operational event, choose the existing category when possible, assign a stable `lower_snake_case` event ID, use the weakest severity that accurately represents the condition, and add/update tests or validators when the event is part of an operational contract.