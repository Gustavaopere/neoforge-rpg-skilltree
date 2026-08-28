# Canonical Provider Binding Checkpoint

Branch: `feat/rpg-canonical-provider-binding`

Purpose: introduce the provider-neutral `Canonical Stat -> Provider Binding` layer required by the RPG architecture without rewriting audited Axxxx perk content or choosing provider precedence prematurely.

This slice owns:

- stable namespaced binding identities;
- explicit canonical-stat to provider-target mapping;
- provider availability filtering;
- injected binding-selection policy;
- fail-closed missing/invalid selection behavior;
- immutable/auditable resolution results.

This slice deliberately does **not** choose final provider precedence, global stat caps, stacking groups, balance formulas, or migrate the existing 66 concrete node effects. Those remain separate follow-up decisions/slices.
