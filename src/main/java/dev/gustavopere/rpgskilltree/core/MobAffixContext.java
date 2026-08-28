package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Deterministic post-scaling inputs used to select mob affixes. */
public record MobAffixContext(
    EntityStatScalingResult scaledEntity,
    MobRaritySelection rarity,
    long deterministicSeed
) {
    public MobAffixContext {
        Objects.requireNonNull(scaledEntity, "scaledEntity");
        Objects.requireNonNull(rarity, "rarity");
    }

    public EntityArchetype archetype() {
        return scaledEntity.archetype();
    }

    public long entityLevel() {
        return scaledEntity.entityLevel();
    }
}
