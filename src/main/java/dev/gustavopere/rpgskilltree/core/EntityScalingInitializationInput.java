package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

/** Already-resolved, balance-neutral inputs required to make one persistent entity scaling decision. */
public record EntityScalingInitializationInput(
    TerritoryKey territory,
    long nativeAreaLevel,
    OptionalLong relevantPlayerLevel,
    EntityArchetype archetype,
    long variance,
    Optional<MobRaritySelection> rarity,
    long deterministicSeed,
    MobAffixSelection affixes,
    EntityBehaviorSelection behaviors
) {
    public EntityScalingInitializationInput {
        Objects.requireNonNull(territory, "territory");
        Objects.requireNonNull(relevantPlayerLevel, "relevantPlayerLevel");
        Objects.requireNonNull(archetype, "archetype");
        Objects.requireNonNull(rarity, "rarity");
        Objects.requireNonNull(affixes, "affixes");
        Objects.requireNonNull(behaviors, "behaviors");
        if (nativeAreaLevel < 0L) {
            throw new IllegalArgumentException("nativeAreaLevel must be non-negative");
        }
        if (relevantPlayerLevel.isPresent() && relevantPlayerLevel.getAsLong() < 0L) {
            throw new IllegalArgumentException("relevantPlayerLevel must be non-negative when present");
        }
    }

    /** Source-compatible constructor for callers that have not resolved affix/behavior selections yet. */
    public EntityScalingInitializationInput(
        TerritoryKey territory,
        long nativeAreaLevel,
        OptionalLong relevantPlayerLevel,
        EntityArchetype archetype,
        long variance,
        Optional<MobRaritySelection> rarity,
        long deterministicSeed
    ) {
        this(
            territory,
            nativeAreaLevel,
            relevantPlayerLevel,
            archetype,
            variance,
            rarity,
            deterministicSeed,
            MobAffixSelection.empty(),
            EntityBehaviorSelection.empty()
        );
    }
}
