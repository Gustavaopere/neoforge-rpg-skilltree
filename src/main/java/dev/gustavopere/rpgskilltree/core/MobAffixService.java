package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure fail-closed entry point for deterministic mob affix selection. */
public final class MobAffixService {
    private MobAffixService() {}

    public static MobAffixSelection resolve(MobAffixContext context, MobAffixPolicy policy) {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(policy, "policy");
        MobAffixSelection selection = policy.select(context);
        if (selection == null) {
            throw new IllegalStateException("mob affix policy returned null");
        }
        return selection;
    }
}
