package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** Semantic provider boundaries for A0091/A0093-A0095/A0100. */
public final class FrozenA0091A0100ProviderPolicyTest {
    public static void main(String[] args) {
        baseFirmIsOnlyKnockbackResistance();
        guardAndInterruptionBenefitsRequireExactProviderFacts();
        antiCriticalTouchesOnlyAProvenCriticalPortion();
        System.out.println("FrozenA0091A0100ProviderPolicyTest: PASS");
    }

    private static void baseFirmIsOnlyKnockbackResistance() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0091", 5));
        require(close(FrozenVitalityDefensePolicy.knockbackResistance(ranks), 0.15D), "A0091 exact attribute amount");
        require(close(FrozenVitalityDefensePolicy.interruptionResistance(ranks, true), 0.0D), "A0091 never leaks into interruption resistance");
    }

    private static void guardAndInterruptionBenefitsRequireExactProviderFacts() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0093", 5, "A0094", 4, "A0095", 5));
        require(close(FrozenVitalityDefensePolicy.guardCostMultiplier(ranks, true, true), 0.90D), "A0093 exact real guard cost");
        require(close(FrozenVitalityDefensePolicy.guardCostMultiplier(ranks, false, true), 1.0D), "unrecognized guard fail-closed");
        require(close(FrozenVitalityDefensePolicy.guardBreakRecoveryMultiplier(ranks, true), 1.12D), "A0094 confirmed break provider");
        require(close(FrozenVitalityDefensePolicy.guardBreakRecoveryMultiplier(ranks, false), 1.0D), "A0094 absent provider inactive");
        require(close(FrozenVitalityDefensePolicy.interruptionResistance(ranks, true), 0.15D), "A0095 exact interruption provider");
        require(close(FrozenVitalityDefensePolicy.interruptionResistance(ranks, false), 0.0D), "A0095 never falls back to knockback");
    }

    private static void antiCriticalTouchesOnlyAProvenCriticalPortion() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0100", 4));
        require(close(FrozenVitalityDefensePolicy.antiCriticalDamage(20, 10, ranks, true), 28.4D), "rank 4 preserves 84% of critical portion only");
        require(close(FrozenVitalityDefensePolicy.antiCriticalDamage(20, 10, ranks, false), 30.0D), "unproven provider leaves damage unchanged");
        require(close(FrozenVitalityDefensePolicy.antiCriticalDamage(30, 0, ranks, true), 30.0D), "ordinary damage receives no universal reduction");
    }

    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
