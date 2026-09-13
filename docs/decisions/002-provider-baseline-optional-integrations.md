# ADR 002 — RPG Core has no external gameplay hard dependency

Status: Accepted  
Date: 2026-09-12

## Context

The physical modpack contains many gameplay providers, but the RPG Skill Tree already has an optional-adapter architecture and must remain classloading-safe when a provider disappears. The class-oriented redesign also contains identities whose live mechanics may depend on a provider, notably Mage/Iron's and Sorcerer/Ars.

The physical modlist remains authority for which integrations are expected in the target pack at a given implementation cycle.

## Decision

No external gameplay provider becomes a **hard startup dependency** of the RPG Core.

Providers are classified per integration/content rule as:

1. **target-pack supported** — physically installed and intentionally integrated;
2. **optional supported** — adapter exists and fails closed when absent;
3. **experimental/planned** — no active gameplay promise until validated;
4. **removed/legacy** — historical IDs may remain migration inputs but cannot authorize live content.

A class or specialization may still declare a provider as a **runtime eligibility requirement**. This blocks that identity/content when the provider is unavailable without preventing the RPG Core from loading.

Baseline examples for the current physical pack:

- Mage requires Iron's for live Mage identity mechanics;
- Sorcerer requires Ars Nouveau for live Sorcerer identity mechanics;
- Engineer remains a core class while Create-backed perks/specialists are provider-gated;
- Druid/Metamorph use the first-party Form Engine; creature mods are adapters, not core dependencies;
- AE2, Oritech, TFC, Identity/Identity2 and Woodwalkers are removed/legacy in the current snapshot and cannot remain active requirements.

## Consequences

- Provider absence never leaves a purchasable no-op node.
- Provider-specific content is unavailable/fail-closed with diagnostics.
- Removing a provider does not make a valid save unable to log in; persisted provider-derived state follows provenance/quarantine rules.
- Exact supported provider versions are pinned from the current physical modlist for each implementation/validation cycle, not hardcoded forever in this ADR.

## Migration/compatibility impact

Legacy provider IDs remain readable. Removed-provider specialization IDs are retained only for migration/diagnostics unless a future explicit mapping exists.

## Tests/verification required

- core-only startup;
- provider-present lane at exact physical version;
- provider-absent startup and save load;
- provider removed after save;
- unavailable node/gateway cannot be purchased;
- no reflection/classloading crash when optional provider is absent.
