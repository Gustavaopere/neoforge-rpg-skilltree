package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Persistable, auditable result of one entity's initial scaling decision. */
public record EntityScalingState(
    TerritoryKey territory,
    EntityLevelResolution levelResolution,
    long variance,
    Optional<MobRaritySelection> rarity,
    long deterministicSeed
) {
    public EntityScalingState {
        Objects.requireNonNull(territory, "territory");
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(rarity, "rarity");

        long expectedBaseFloor = levelResolution.relevantPlayerLevel().isPresent()
            ? Math.max(levelResolution.nativeAreaLevel(), levelResolution.relevantPlayerLevel().getAsLong())
            : levelResolution.nativeAreaLevel();
        if (levelResolution.baseFloor() != expectedBaseFloor) {
            throw new IllegalArgumentException("entity scaling baseFloor is inconsistent with native/relevant levels");
        }

        long rarityBonus = rarity.map(MobRaritySelection::levelBonus).orElse(0L);
        long expectedRolled;
        try {
            expectedRolled = Math.addExact(Math.addExact(expectedBaseFloor, variance), rarityBonus);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("entity scaling adjustment overflows long", exception);
        }
        if (levelResolution.rolledLevel() != expectedRolled) {
            throw new IllegalArgumentException("entity scaling rolledLevel is inconsistent with persisted adjustments");
        }

        long expectedFinal = levelResolution.relevantPlayerLevel().isPresent()
            ? Math.max(levelResolution.relevantPlayerLevel().getAsLong(), expectedRolled)
            : Math.max(0L, expectedRolled);
        if (levelResolution.finalLevel() != expectedFinal) {
            throw new IllegalArgumentException("entity scaling finalLevel is inconsistent with floor semantics");
        }
    }

    public EntityArchetype archetype() {
        return levelResolution.archetype();
    }

    public long entityLevel() {
        return levelResolution.finalLevel();
    }
}
