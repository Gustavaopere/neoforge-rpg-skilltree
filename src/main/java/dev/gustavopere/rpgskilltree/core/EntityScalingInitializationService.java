package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure composition boundary from already-resolved scaling inputs to one persisted entity scaling state. */
public final class EntityScalingInitializationService {
    private EntityScalingInitializationService() {}

    public static EntityScalingState resolve(EntityScalingInitializationInput input) {
        Objects.requireNonNull(input, "input");

        EntityLevelContext levelContext = new EntityLevelContext(
            input.nativeAreaLevel(),
            input.relevantPlayerLevel(),
            input.archetype()
        );

        long rarityBonus = input.rarity()
            .map(MobRaritySelection::levelBonus)
            .orElse(0L);

        EntityLevelResolution levelResolution = EntityLevelService.resolve(
            levelContext,
            new EntityLevelAdjustment(input.variance(), rarityBonus)
        );

        return new EntityScalingState(
            input.territory(),
            levelResolution,
            input.variance(),
            input.rarity(),
            input.deterministicSeed()
        );
    }
}
