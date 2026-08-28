package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Initializes entity rarity once and resumes persisted state without rerolling. */
public final class EntityScalingBootstrap {
    private EntityScalingBootstrap() {}

    public static EntityScalingState initializeOrResume(
        Optional<EntityScalingState> persisted,
        MobRarityContext context,
        MobRarityPolicy policy
    ) {
        Objects.requireNonNull(persisted, "persisted");
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(policy, "policy");

        if (persisted.isPresent()) {
            return persisted.get();
        }

        MobRaritySelection selection = MobRarityService.resolve(context, policy);
        return new EntityScalingState(selection, context.deterministicSeed());
    }
}
