package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure fail-closed entry point for deterministic mob rarity selection. */
public final class MobRarityService {
    private MobRarityService() {}

    public static MobRaritySelection resolve(MobRarityContext context, MobRarityPolicy policy) {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(policy, "policy");
        MobRaritySelection selection = policy.select(context);
        if (selection == null) {
            throw new IllegalStateException("mob rarity policy returned null");
        }
        return selection;
    }
}
