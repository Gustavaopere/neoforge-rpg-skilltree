package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Persisted entity-scaling identity required to keep rarity stable across reloads. */
public record EntityScalingState(
    MobRaritySelection raritySelection,
    long deterministicSeed
) {
    public EntityScalingState {
        Objects.requireNonNull(raritySelection, "raritySelection");
    }
}
