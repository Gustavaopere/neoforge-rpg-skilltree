package dev.gustavopere.rpgskilltree.core;

/** Signed post-floor adjustment components. Final ranges are external/data-driven policy. */
public record EntityLevelAdjustment(long variance, long rarityBonus) {
    public static final EntityLevelAdjustment NONE = new EntityLevelAdjustment(0L, 0L);
}
