package dev.gustavopere.rpgskilltree.core;

/** Selects one mob rarity from deterministic provider-neutral context. */
@FunctionalInterface
public interface MobRarityPolicy {
    MobRaritySelection select(MobRarityContext context);
}
