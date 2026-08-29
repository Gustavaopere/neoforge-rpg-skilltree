package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/**
 * Deterministic weighted rarity resolver driven entirely by caller-provided rules.
 *
 * <p>The same context and deterministic seed always produce the same selection. Rarity policy
 * remains separate from archetype classification: archetype is only an eligibility input to rules,
 * never rewritten by this policy.</p>
 */
public final class DeterministicWeightedMobRarityPolicy implements MobRarityPolicy {
    private final List<MobRarityRule> rules;
    private final MobRaritySelection defaultFallback;
    private final MobRaritySelection bossFallback;

    public DeterministicWeightedMobRarityPolicy(
        List<MobRarityRule> rules,
        MobRaritySelection defaultFallback,
        MobRaritySelection bossFallback
    ) {
        this.rules = List.copyOf(Objects.requireNonNull(rules, "rules"));
        this.defaultFallback = Objects.requireNonNull(defaultFallback, "defaultFallback");
        this.bossFallback = Objects.requireNonNull(bossFallback, "bossFallback");
    }

    @Override
    public MobRaritySelection select(MobRarityContext context) {
        Objects.requireNonNull(context, "context");

        long totalWeight = 0L;
        boolean matched = false;
        for (MobRarityRule rule : rules) {
            if (!rule.matches(context)) continue;
            matched = true;
            try {
                totalWeight = Math.addExact(totalWeight, rule.weight());
            } catch (ArithmeticException overflow) {
                throw new IllegalStateException("matching rarity weights overflow", overflow);
            }
        }

        if (!matched) {
            return context.archetype() == EntityArchetype.BOSS ? bossFallback : defaultFallback;
        }

        long cursor = Math.floorMod(context.deterministicSeed(), totalWeight);
        for (MobRarityRule rule : rules) {
            if (!rule.matches(context)) continue;
            if (cursor < rule.weight()) return rule.selection();
            cursor -= rule.weight();
        }
        throw new IllegalStateException("weighted rarity selection exhausted without a result");
    }

    public List<MobRarityRule> rules() {
        return rules;
    }

    public MobRaritySelection defaultFallback() {
        return defaultFallback;
    }

    public MobRaritySelection bossFallback() {
        return bossFallback;
    }
}
