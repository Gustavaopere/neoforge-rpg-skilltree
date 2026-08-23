# Identity 2 / Ars Morph integration

## Goal
Morphing is a class-gated mechanic, not a globally available utility.

## Druid
The Druid class owns natural forms. Its class tree grants `DRUID_LAND`, `DRUID_AQUATIC`, `DRUID_FLYING` and later `DRUID_MAGICAL_NATURAL` permissions. Acquisition rules may favor observation/taming/bonding rather than mandatory kills.

## Metamorph
The Metamorph class owns non-natural shapeshifting. Its class tree grants `METAMORPH_HUMANOID`, `METAMORPH_MONSTER` and later `METAMORPH_ABERRATION` permissions.

## Runtime boundary
The adapter must intercept Identity 2 / Ars Morph eligibility or activation at a stable API/event boundary. It classifies the target `LivingEntity` into `MorphFormCategory`, applies datapack tags, and asks `MorphAccessPolicy`. Technical entities and `rpgskilltree:morph_blacklist` are denied before any class permission is considered.

If a specific modded entity does not behave safely as a form, blacklist it rather than special-casing the whole originating mod.
