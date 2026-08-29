package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/**
 * One configurable weighted candidate for rarity selection.
 *
 * <p>An empty archetype set means the rule is eligible for every archetype. This record owns no
 * gameplay balance defaults: callers provide the weight, floor window and selection explicitly.</p>
 */
public record MobRarityRule(
    MobRaritySelection selection,
    long weight,
    long minBaseFloor,
    long maxBaseFloor,
    Set<EntityArchetype> eligibleArchetypes
) {
    public MobRarityRule {
        Objects.requireNonNull(selection, "selection");
        Objects.requireNonNull(eligibleArchetypes, "eligibleArchetypes");
        eligibleArchetypes = Set.copyOf(eligibleArchetypes);
        if (weight <= 0L) {
            throw new IllegalArgumentException("weight must be positive");
        }
        if (minBaseFloor < 0L) {
            throw new IllegalArgumentException("minBaseFloor must be non-negative");
        }
        if (maxBaseFloor < minBaseFloor) {
            throw new IllegalArgumentException("maxBaseFloor must be >= minBaseFloor");
        }
    }

    public boolean matches(MobRarityContext context) {
        Objects.requireNonNull(context, "context");
        long floor = context.baseFloor();
        return floor >= minBaseFloor
            && floor <= maxBaseFloor
            && (eligibleArchetypes.isEmpty() || eligibleArchetypes.contains(context.archetype()));
    }
}
