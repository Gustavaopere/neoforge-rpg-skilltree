package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Pure, capped contributions from invested Core attribute ranks.
 *
 * <p>These values are never persisted or awarded per tick. A benefit's value is
 * recomputed from its authoritative long rank and may be projected to a native
 * attribute only when that provider's integration is verified.</p>
 */
public final class AttributeInvestmentBonuses {
    private AttributeInvestmentBonuses() {}

    /** Benefit amounts are native units: flat HP/mana or dimensionless fractions (not percent numbers). */
    public enum Benefit {
        STRENGTH_MELEE_DAMAGE(AttributeId.STRENGTH, 5, .004, .96),
        STRENGTH_MANUAL_MINING(AttributeId.STRENGTH, 8, .003, .96),
        STRENGTH_KNOCKBACK_RESISTANCE(AttributeId.STRENGTH, 12, .004, .96),
        CONSTITUTION_MAX_HEALTH(AttributeId.CONSTITUTION, 4, .5, .96),
        CONSTITUTION_HEALING_RECEIVED(AttributeId.CONSTITUTION, 8, .004, .96),
        CONSTITUTION_DAMAGE_OVER_TIME(AttributeId.CONSTITUTION, 12, .004, .96),
        AGILITY_MOVEMENT_SPEED(AttributeId.AGILITY, 5, .0032, .96),
        AGILITY_ATTACK_SPEED(AttributeId.AGILITY, 7, .0028, .96),
        AGILITY_PROJECTILE_SPEED(AttributeId.AGILITY, 10, .0032, .96),
        INTELLIGENCE_MAX_MANA(AttributeId.INTELLIGENCE, 3, 1., .98),
        INTELLIGENCE_MANA_REGEN(AttributeId.INTELLIGENCE, 5, .002, .96),
        INTELLIGENCE_SPELL_POWER(AttributeId.INTELLIGENCE, 10, .004, .96),
        DETERMINATION_STAMINA_REGEN(AttributeId.DETERMINATION, 5, .004, .96),
        DETERMINATION_CONTROL_DURATION(AttributeId.DETERMINATION, 8, .004, .96),
        DETERMINATION_COOLDOWN_RECOVERY(AttributeId.DETERMINATION, 10, .002, .96),
        CHARISMA_VILLAGER_DISCOUNT(AttributeId.CHARISMA, 5, .0032, .96),
        CHARISMA_COMPANION_DAMAGE(AttributeId.CHARISMA, 8, .004, .96),
        CHARISMA_ALLY_HEALING(AttributeId.CHARISMA, 10, .004, .96);

        private final AttributeId attribute;
        private final int interval;
        private final double firstGain;
        private final double retention;

        Benefit(AttributeId attribute, int interval, double firstGain, double retention) {
            this.attribute = attribute;
            this.interval = interval;
            this.firstGain = firstGain;
            this.retention = retention;
        }

        public AttributeId attribute() { return attribute; }
        public int interval() { return interval; }
        public double firstGain() { return firstGain; }
        public double retention() { return retention; }
        public double theoreticalLimit() { return firstGain / (1.0 - retention); }
    }

    public static double value(Benefit benefit, long investedRank) {
        Objects.requireNonNull(benefit, "benefit");
        if (investedRank < 0) throw new IllegalArgumentException("rank must be non-negative");
        long milestones = investedRank / benefit.interval();
        if (milestones == 0) return 0.0;
        if (milestones == 1) return benefit.firstGain();
        double computed = benefit.theoreticalLimit() * (1.0 - Math.pow(benefit.retention(), (double) milestones));
        return Math.max(0.0, Math.min(benefit.theoreticalLimit(), computed));
    }

    /** All eighteen contributions, including zero-valued entries, from one immutable rank state. */
    public static Map<Benefit, Double> resolve(AttributeRanks ranks) {
        Objects.requireNonNull(ranks, "ranks");
        EnumMap<Benefit, Double> result = new EnumMap<>(Benefit.class);
        for (Benefit benefit : Benefit.values()) {
            result.put(benefit, value(benefit, ranks.rank(benefit.attribute())));
        }
        return Map.copyOf(result);
    }
}
