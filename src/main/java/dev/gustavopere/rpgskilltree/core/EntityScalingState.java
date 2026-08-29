package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Persistable, auditable result of one entity's initial scaling decision. */
public record EntityScalingState(
    TerritoryKey territory,
    EntityLevelResolution levelResolution,
    long variance,
    Optional<MobRaritySelection> rarity,
    long deterministicSeed,
    Optional<EntityEffectiveStatsSnapshot> effectiveStats,
    MobAffixSelection affixes,
    EntityBehaviorSelection behaviors
) {
    public EntityScalingState {
        Objects.requireNonNull(territory, "territory");
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(rarity, "rarity");
        Objects.requireNonNull(effectiveStats, "effectiveStats");
        Objects.requireNonNull(affixes, "affixes");
        Objects.requireNonNull(behaviors, "behaviors");

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

    /** Source-compatible constructor for states created before effective-stat persistence existed. */
    public EntityScalingState(
        TerritoryKey territory,
        EntityLevelResolution levelResolution,
        long variance,
        Optional<MobRaritySelection> rarity,
        long deterministicSeed,
        MobAffixSelection affixes,
        EntityBehaviorSelection behaviors
    ) {
        this(
            territory,
            levelResolution,
            variance,
            rarity,
            deterministicSeed,
            Optional.empty(),
            affixes,
            behaviors
        );
    }

    /** Source-compatible constructor for states created before affix persistence existed. */
    public EntityScalingState(
        TerritoryKey territory,
        EntityLevelResolution levelResolution,
        long variance,
        Optional<MobRaritySelection> rarity,
        long deterministicSeed
    ) {
        this(
            territory,
            levelResolution,
            variance,
            rarity,
            deterministicSeed,
            Optional.empty(),
            MobAffixSelection.empty(),
            EntityBehaviorSelection.empty()
        );
    }

    /** Source-compatible constructor for states created after affix but before behavior persistence. */
    public EntityScalingState(
        TerritoryKey territory,
        EntityLevelResolution levelResolution,
        long variance,
        Optional<MobRaritySelection> rarity,
        long deterministicSeed,
        MobAffixSelection affixes
    ) {
        this(
            territory,
            levelResolution,
            variance,
            rarity,
            deterministicSeed,
            Optional.empty(),
            affixes,
            EntityBehaviorSelection.empty()
        );
    }

    public EntityArchetype archetype() {
        return levelResolution.archetype();
    }

    public long entityLevel() {
        return levelResolution.finalLevel();
    }
}
