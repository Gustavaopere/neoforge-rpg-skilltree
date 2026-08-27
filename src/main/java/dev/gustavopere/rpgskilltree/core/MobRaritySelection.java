package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** One selected rarity and its explicit post-floor level contribution. */
public record MobRaritySelection(
    MobRarityKey rarity,
    long levelBonus
) {
    public MobRaritySelection {
        Objects.requireNonNull(rarity, "rarity");
        if (levelBonus < 0L) {
            throw new IllegalArgumentException("mob rarity levelBonus must be non-negative");
        }
    }

    public EntityLevelAdjustment toLevelAdjustment(long variance) {
        return new EntityLevelAdjustment(variance, levelBonus);
    }
}
