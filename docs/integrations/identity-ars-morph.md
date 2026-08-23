# Identity 2 / Ars Morph integration

## Goal
Morphing is a progression-gated mechanic, not a globally available utility. The RPG Skill Tree owns progression gates, form taxonomy and ecological/social semantics; the morph provider remains authoritative for the actual transformation, entity rendering and provider-native acquisition state.

## Druid
Druid owns the natural side of the taxonomy: `DRUID_LAND`, `DRUID_AQUATIC`, `DRUID_FLYING` and later `DRUID_MAGICAL_NATURAL`. Acquisition design favors Wild Communion, observation, taming/bonding and ecological mastery rather than requiring kills.

The permission vocabulary and access policy exist in core code. Granting those permissions from purchased Druid nodes is deliberately deferred until the perk/topology reconciliation supplies stable canonical node metadata.

## Metamorph
Metamorph owns non-natural shapeshifting: `METAMORPH_HUMANOID`, `METAMORPH_MONSTER` and later `METAMORPH_ABERRATION`. Identity 2 remains the intended backend for the actual form.

As with Druid, the permission model exists independently from the tree, but the final node-to-permission grants are deferred until perk reconciliation.

## Verified Identity 2 boundary
Identity 2 is an optional `compileOnly` integration. Runtime registration occurs only when `identity2` is loaded.

The 1.21.1 Identity 2 public API exposes `IdentityApi.getCurrentMorphId(Entity)` and `IdentityApi.isMorphed(Entity)`. RPG Skill Tree uses that public API for current-form projection instead of reading Identity 2 internal NBT or using reflection.

A stable pre-transformation veto hook is still an integration question. `MorphIdentityAccess.canMorph(...)` is the central policy boundary inside RPG Skill Tree, but Identity 2 activation is not claimed as fully gated until that provider-side hook is wired and verified. Until then, provider-native acquisition remains authoritative rather than being destructively replaced.

## Data-driven form taxonomy
`data/rpgskilltree/morph_categories/` owns the extensible morph classification/ecology data. Runtime classification produces a `MorphFormDescriptor`, then applies `MorphAccessPolicy`.

Technical entities and explicitly blacklisted forms are denied before class permissions are considered. Ender Dragon and Wither remain blocked by default. A problematic modded entity should be blacklisted individually instead of disabling its entire originating mod.

## Ecological identity
A morph projects a perceived species, faction set and ecological traits. The default mappings are deliberately conservative: an absent relationship is `NEUTRAL`; generic hostile mobs are not automatically treated as allies merely because both are hostile to players.

The runtime currently supports:
- explicit ally/enemy/fear faction dispositions;
- cancellation of target acquisition when a morphed player is perceived as an ally or a feared form;
- species-level fallback where appropriate;
- a temporary hostility memory when the morphed player attacks an entity perceived as allied to the current form;
- a default 45-second disguise-compromise window, configurable through morph ecology data.

Examples encoded only where vanilla semantics are clear include village/illager hostility, creeper fear of feline forms, skeleton fear of wolves and phantom fear of feline forms.

## Ars Morph
Ars-facing morph support remains a separate adapter concern. This document does not claim that an Ars transformation path is currently intercepted. Any future Ars adapter must reuse the same `MorphFormDescriptor`, `MorphAccessPolicy` and ecology layer rather than implementing a parallel permission system.
