package dev.gustavopere.rpgskilltree.core;

/** Selects zero or more behavior packages from deterministic post-scaling context. */
@FunctionalInterface
public interface EntityBehaviorPolicy {
    EntityBehaviorSelection select(EntityBehaviorContext context);
}
