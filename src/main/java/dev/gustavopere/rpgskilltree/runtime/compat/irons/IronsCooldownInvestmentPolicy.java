package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;

/**
 * Adjusts an existing Iron's cast cooldown once, after Iron's native items,
 * equipment and soft-cap calculation. Does not create new cooldown state.
 *
 * <p>Iron's 3.16.3 truncates its native base cooldown to integer ticks.
 * Rounding to nearest here avoids a sub-tick investment producing an
 * unintended whole-tick reduction, and guards every positive cooldown
 * against reaching zero.</p>
 */
public final class IronsCooldownInvestmentPolicy {
    private IronsCooldownInvestmentPolicy() {}

    public static int adjustedCooldown(int effectiveCooldown, long investedDetermination) {
        if (effectiveCooldown < 0) throw new IllegalArgumentException("cooldown ticks must not be negative");
        if (investedDetermination < 0) throw new IllegalArgumentException("rank must be non-negative");
        if (effectiveCooldown <= 1) return effectiveCooldown;

        double fraction = AttributeInvestmentBonuses.value(
            Benefit.DETERMINATION_COOLDOWN_RECOVERY, investedDetermination);
        if (fraction == 0.0) return effectiveCooldown;

        long adjusted = Math.round(effectiveCooldown * (1.0 - fraction));
        return (int) Math.max(1L, adjusted);
    }
}
