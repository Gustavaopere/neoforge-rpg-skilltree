package dev.gustavopere.rpgskilltree.core;

/** Selects zero or more mob affixes from deterministic post-scaling context. */
@FunctionalInterface
public interface MobAffixPolicy {
    MobAffixSelection select(MobAffixContext context);
}
