package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure fail-closed entry point for deterministic entity behavior selection. */
public final class EntityBehaviorService {
    private EntityBehaviorService() {}

    public static EntityBehaviorSelection resolve(EntityBehaviorContext context, EntityBehaviorPolicy policy) {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(policy, "policy");
        EntityBehaviorSelection selection = policy.select(context);
        if (selection == null) {
            throw new IllegalStateException("entity behavior policy returned null");
        }
        return selection;
    }
}
