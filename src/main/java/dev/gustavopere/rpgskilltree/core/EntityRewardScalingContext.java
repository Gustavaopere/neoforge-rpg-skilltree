package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Immutable context for resolving one entity's bounded reward-risk multiplier. */
public record EntityRewardScalingContext(
    EntityLevelResolution levelResolution,
    Optional<MobRaritySelection> rarity
) {
    public EntityRewardScalingContext {
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(rarity, "rarity");
    }

    public EntityArchetype archetype() {
        return levelResolution.archetype();
    }

    public long entityLevel() {
        return levelResolution.finalLevel();
    }
}
