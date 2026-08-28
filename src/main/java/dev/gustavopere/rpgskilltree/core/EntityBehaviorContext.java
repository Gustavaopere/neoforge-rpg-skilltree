package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Immutable post-scaling inputs available to one deterministic behavior policy. */
public record EntityBehaviorContext(
    EntityStatScalingResult scaledEntity,
    MobRaritySelection rarity,
    MobAffixSelection affixes,
    long deterministicSeed
) {
    public EntityBehaviorContext {
        Objects.requireNonNull(scaledEntity, "scaledEntity");
        Objects.requireNonNull(rarity, "rarity");
        Objects.requireNonNull(affixes, "affixes");
    }

    public EntityArchetype archetype() {
        return scaledEntity.archetype();
    }

    public long entityLevel() {
        return scaledEntity.entityLevel();
    }
}
