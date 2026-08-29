package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure canonical formulas for the fresh Notion batch A0081-A0100. */
public final class A0081A0100CombatPolicy {
    private A0081A0100CombatPolicy() {}

    public static double sustainCoefficient(CombatPerkRanks ranks, boolean weapon, boolean magic,
                                             boolean elemental, boolean periodic) {
        Objects.requireNonNull(ranks);
        double best = 0.0D;
        if (weapon) best = Math.max(best, 0.006D * ranks.rank("A0082"));
        if (magic) best = Math.max(best, 0.006D * ranks.rank("A0083"));
        if (elemental) best = Math.max(best, 0.005D * ranks.rank("A0084"));
        if (periodic) best = Math.max(best, 0.0035D * ranks.rank("A0085"));
        if (best <= 0.0D && ranks.learned("A0086") && (weapon || magic || elemental || periodic)) {
            best = 0.01D;
        }
        return best;
    }

    public static double maxHealthMultiplier(CombatPerkRanks ranks) { return relative(ranks,"A0088",0.02D); }
    public static double armorMultiplier(CombatPerkRanks ranks) { return relative(ranks,"A0089",0.02D); }
    public static double toughnessMultiplier(CombatPerkRanks ranks) { return relative(ranks,"A0090",0.02D); }
    public static double knockbackResistanceDelta(CombatPerkRanks ranks) {
        Objects.requireNonNull(ranks);
        return Math.min(0.15D, 0.03D * ranks.rank("A0091"));
    }

    /** A0092 and A0096 are independent multiplicative contributors; preImpactHealthFraction is sampled before the hit. */
    public static double physicalDamageMultiplier(CombatPerkRanks ranks, double preImpactHealthFraction) {
        Objects.requireNonNull(ranks);
        if (!Double.isFinite(preImpactHealthFraction) || preImpactHealthFraction < 0.0D) {
            throw new IllegalArgumentException("preImpactHealthFraction");
        }
        double result = 1.0D - 0.02D * ranks.rank("A0092");
        if (preImpactHealthFraction < 0.30D) result *= 1.0D - 0.04D * ranks.rank("A0096");
        return Math.max(0.0D, result);
    }

    public static double openingDefenseMultiplier(String actorId, CombatPerkRanks ranks,
                                                   A0081A0100DefenseState state, long nowMillis) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (ranks.rank("A0097") <= 0 || !state.openingReady(actorId, nowMillis)) return 1.0D;
        return 1.0D - 0.05D * ranks.rank("A0097");
    }

    /** Caller must supply only an authoritative self-propelled movement state (vanilla sprint is valid). */
    public static double movingDefenseMultiplier(CombatPerkRanks ranks, boolean authoritativeSelfPropelledSprint) {
        Objects.requireNonNull(ranks);
        return authoritativeSelfPropelledSprint ? 1.0D - 0.03D * ranks.rank("A0098") : 1.0D;
    }

    /** Caller must reuse the shared StationaryStateService; this method never defines a second threshold. */
    public static double stationaryDefenseMultiplier(CombatPerkRanks ranks, boolean canonicalStationary) {
        Objects.requireNonNull(ranks);
        return canonicalStationary ? 1.0D - 0.04D * ranks.rank("A0099") : 1.0D;
    }

    public static double guardCostMultiplier(CombatPerkRanks ranks, boolean causalGuardCostContract) {
        Objects.requireNonNull(ranks);
        return causalGuardCostContract ? Math.max(0.90D, 1.0D - 0.02D * ranks.rank("A0093")) : 1.0D;
    }

    public static double guardRecoveryMultiplier(CombatPerkRanks ranks, boolean causalBreakAndRecoveryContract) {
        Objects.requireNonNull(ranks);
        return causalBreakAndRecoveryContract ? 1.0D + 0.03D * ranks.rank("A0094") : 1.0D;
    }

    /** Represents remaining interruption/control-by-impact severity, not knockback or damage. */
    public static double interruptionMultiplier(CombatPerkRanks ranks, boolean semanticInterruptionContract) {
        Objects.requireNonNull(ranks);
        return semanticInterruptionContract ? Math.max(0.85D, 1.0D - 0.03D * ranks.rank("A0095")) : 1.0D;
    }

    public static double antiCriticalDamage(double baseDamage, double additionalCriticalDamage,
                                            CombatPerkRanks ranks, boolean legitimatelyCritical,
                                            boolean decomposedByCausalResolver) {
        finiteNonNegative(baseDamage,"baseDamage");
        finiteNonNegative(additionalCriticalDamage,"additionalCriticalDamage");
        Objects.requireNonNull(ranks);
        if (!legitimatelyCritical || !decomposedByCausalResolver || ranks.rank("A0100") <= 0) {
            return baseDamage + additionalCriticalDamage;
        }
        double criticalRemainder = 1.0D - 0.04D * ranks.rank("A0100");
        return baseDamage + additionalCriticalDamage * criticalRemainder;
    }

    public static double preserveHealthRatio(double oldHealth, double oldMaxHealth, double newMaxHealth) {
        finiteNonNegative(oldHealth,"oldHealth"); finitePositive(oldMaxHealth,"oldMaxHealth"); finitePositive(newMaxHealth,"newMaxHealth");
        return Math.min(newMaxHealth, newMaxHealth * Math.min(1.0D, oldHealth / oldMaxHealth));
    }

    private static double relative(CombatPerkRanks ranks,String code,double perRank) {
        Objects.requireNonNull(ranks);
        return 1.0D + perRank * ranks.rank(code);
    }
    private static void finiteNonNegative(double v,String n){ if(!Double.isFinite(v)||v<0.0D) throw new IllegalArgumentException(n); }
    private static void finitePositive(double v,String n){ if(!Double.isFinite(v)||v<=0.0D) throw new IllegalArgumentException(n); }
}
