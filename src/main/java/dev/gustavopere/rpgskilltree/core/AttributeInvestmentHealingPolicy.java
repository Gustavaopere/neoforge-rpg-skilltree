package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Changes the amount of one already-authorized healing event, not its source,
 * frequency or attribution. Never calls heal() itself.
 */
public final class AttributeInvestmentHealingPolicy {
    private AttributeInvestmentHealingPolicy() {}

    public static float adjust(float originalAmount, AttributeRanks ranks) {
        Objects.requireNonNull(ranks, "ranks");
        if (!Float.isFinite(originalAmount) || originalAmount < 0.0F) {
            throw new IllegalArgumentException("healing amount must be finite and non-negative");
        }
        double bonus = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CONSTITUTION_HEALING_RECEIVED,
            ranks.rank(AttributeId.CONSTITUTION)
        );
        return (float) Math.min(Float.MAX_VALUE, originalAmount * (1.0D + bonus));
    }
}
