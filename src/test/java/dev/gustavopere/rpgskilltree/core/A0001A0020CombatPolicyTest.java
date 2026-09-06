package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class A0001A0020CombatPolicyTest {
    public static void main(String[] args) {
        momentumUsesFiveSecondGraceThenOnePerSecondDecay();
        openingCommitsTwoMomentumAndCooldownOnlyAfterConfirmedHit();
        openingFallbackRequiresConfirmedArmorAndOmitsImpact();
        furyGenerationUsesRankThenTargetSwitchMultiplier();
        ruptureUsesNativeDefenseOrStrictArmorFallback();
        frenzyUsesExplicitCoreCostAndPeakSpend();
        frenzyDropTracksThresholdTransition();
        heavyStaggerConsumesSwordAndSpearCharges();
        spearWindowsConsumeDistanceControlAndApplyTargetLockout();
        System.out.println("A0001A0020CombatPolicyTest: PASS");
    }

    private static void momentumUsesFiveSecondGraceThenOnePerSecondDecay() {
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        var state = new NotionCombatPerkState();
        for (int i = 0; i < 3; i++) {
            var facts = facts("sword-" + i, WeaponFamily.SWORD, "target", false, false, true, false, false, 0L);
            A0001A0020CombatPolicy.afterConfirmedHit(facts, ranks, state, false);
        }
        require(state.momentum("player", 4_999L) == 3, "A0004 must keep all charges during five-second grace");
        require(state.momentum("player", 5_000L) == 2, "A0004 must lose first charge at five seconds");
        require(state.momentum("player", 6_000L) == 1, "A0004 must lose one charge per second");
        require(state.momentum("player", 7_000L) == 0, "A0004 decay must clamp at zero");
    }

    private static void openingCommitsTwoMomentumAndCooldownOnlyAfterConfirmedHit() {
        var ranks = CombatPerkRanks.of(Map.of("A0005", 1));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 3, 0L);
        state.recordSwordSequenceTarget("player", "target");
        var firstFacts = facts("open-1", WeaponFamily.SWORD, "target", true, true, true, false, false, 1_000L);
        var first = A0001A0020CombatPolicy.beforeHit(firstFacts, ranks, state);
        require(close(first.physicalPenetrationFraction(), 0.12D), "A0005 penetration must be 12%");
        require(close(first.impactMultiplier(), 1.08D), "A0005 impact must be +8%");
        require(state.momentum("player", 1_000L) == 3, "A0005 PRE must not consume Momentum before confirmed damage");
        require(state.openingCooldownReady("player", "target", 1_000L), "A0005 PRE must not start target cooldown");
        A0001A0020CombatPolicy.afterConfirmedHit(firstFacts, ranks, state, first.suppressMomentumGain());
        require(state.momentum("player", 1_000L) == 1, "A0005 confirmed POST must consume two Momentum");
        require(!state.openingCooldownReady("player", "target", 1_000L), "A0005 confirmed POST must start target cooldown");

        state.addMomentum("player", 3, 1_100L);
        var cooldown = A0001A0020CombatPolicy.beforeHit(
            facts("open-2", WeaponFamily.SWORD, "target", true, true, true, false, false, 2_000L), ranks, state);
        require(close(cooldown.physicalPenetrationFraction(), 0.0D), "A0005 must not reactivate inside target cooldown");
        require(state.momentum("player", 2_000L) == 4, "blocked A0005 activation must not consume Momentum");

        var afterCooldownFacts = facts("open-3", WeaponFamily.SWORD, "target", true, true, true, false, false, 7_000L);
        var afterCooldown = A0001A0020CombatPolicy.beforeHit(afterCooldownFacts, ranks, state);
        require(close(afterCooldown.physicalPenetrationFraction(), 0.12D), "A0005 must reactivate after six seconds");
        require(state.momentum("player", 7_000L) == 3, "reactivated A0005 PRE must remain non-destructive");
        A0001A0020CombatPolicy.afterConfirmedHit(afterCooldownFacts, ranks, state, afterCooldown.suppressMomentumGain());
        require(state.momentum("player", 7_000L) == 1, "reactivated A0005 must commit two Momentum only after confirmed POST");
    }

    private static void openingFallbackRequiresConfirmedArmorAndOmitsImpact() {
        var ranks = CombatPerkRanks.of(Map.of("A0005", 1));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 5, 0L);
        state.recordSwordSequenceTarget("player", "armored");

        var observableButUnguarded = A0001A0020CombatPolicy.beforeHit(
            facts("open-native-plain", WeaponFamily.SWORD, "armored", false, true, true, false, false, 100L), ranks, state);
        require(close(observableButUnguarded.physicalPenetrationFraction(), 0.0D),
            "A0005 must not use armor fallback when guard/posture is observable and absent");
        require(state.momentum("player", 100L) == 5, "ineligible native-defense target must not consume Momentum");

        var fallbackFacts = facts("open-fallback", WeaponFamily.SWORD, "armored", false, true, false, false, false, 200L);
        var fallback = A0001A0020CombatPolicy.beforeHit(fallbackFacts, ranks, state);
        require(close(fallback.physicalPenetrationFraction(), 0.12D), "A0005 armor fallback must keep penetration");
        require(close(fallback.impactMultiplier(), 1.0D), "A0005 armor fallback must omit impact/guard pressure");
        require(state.momentum("player", 200L) == 5, "eligible fallback PRE must not consume Momentum");
        A0001A0020CombatPolicy.afterConfirmedHit(fallbackFacts, ranks, state, fallback.suppressMomentumGain());
        require(state.momentum("player", 200L) == 3, "eligible fallback must consume two Momentum on confirmed POST");
    }

    private static void furyGenerationUsesRankThenTargetSwitchMultiplier() {
        var ranks = CombatPerkRanks.of(Map.of("A0010", 2));
        var state = new NotionCombatPerkState();
        A0001A0020CombatPolicy.afterConfirmedHit(
            facts("axe-1", WeaponFamily.AXE, "target-a", false, false, true, false, false, 0L), ranks, state, false);
        require(close(state.fury("player"), 9.6D), "A0010 rank 2 normal gain");
        A0001A0020CombatPolicy.afterConfirmedHit(
            facts("axe-2", WeaponFamily.AXE, "target-b", false, false, true, false, false, 100L), ranks, state, false);
        require(close(state.fury("player"), 24.0D), "A0010 target switch must apply x1.5 after rank multiplier");
    }

    private static void ruptureUsesNativeDefenseOrStrictArmorFallback() {
        var ranks = CombatPerkRanks.of(Map.of("A0011", 2));
        var state = new NotionCombatPerkState();
        state.addFury("player", 40.0D);
        var guardedFacts = facts("rupture-1", WeaponFamily.AXE, "guarded", true, true, true, false, false, 0L);
        var guarded = A0001A0020CombatPolicy.beforeHit(guardedFacts, ranks, state);
        require(close(guarded.impactMultiplier(), 1.35D), "A0011 rank 2 impact");
        require(close(guarded.physicalPenetrationFraction(), 0.10D), "A0011 rank 2 penetration");
        require(close(state.fury("player"), 40.0D), "A0011 PRE must reserve rather than spend 20 Fury");
        A0001A0020CombatPolicy.afterConfirmedHit(guardedFacts, ranks, state, false);
        require(close(state.fury("player"), 20.0D), "A0011 confirmed POST must spend 20 Fury");

        state.addFury("player", 20.0D);
        var observableButUnguarded = A0001A0020CombatPolicy.beforeHit(
            facts("rupture-2", WeaponFamily.AXE, "armored", false, true, true, false, false, 100L), ranks, state);
        require(close(observableButUnguarded.physicalPenetrationFraction(), 0.0D),
            "A0011 must not use armor fallback when provider can prove target is unguarded");
        require(close(state.fury("player"), 40.0D), "A0011 must not spend Fury on ineligible target");

        var armoredFallbackFacts = facts("rupture-3", WeaponFamily.AXE, "armored", false, true, false, false, false, 200L);
        var armoredFallback = A0001A0020CombatPolicy.beforeHit(armoredFallbackFacts, ranks, state);
        require(close(armoredFallback.impactMultiplier(), 1.0D), "A0011 armor fallback must omit guard pressure");
        require(close(armoredFallback.physicalPenetrationFraction(), 0.10D), "A0011 armor fallback must keep safe penetration");
        require(close(state.fury("player"), 40.0D), "A0011 armor fallback PRE must remain non-destructive");
        A0001A0020CombatPolicy.afterConfirmedHit(armoredFallbackFacts, ranks, state, false);
        require(close(state.fury("player"), 20.0D), "A0011 armor fallback must commit 20 Fury once after confirmed damage");
    }

    private static void frenzyUsesExplicitCoreCostAndPeakSpend() {
        require(!NotionCombatPerkRules.frenzyBaselineAvailable(true, false),
            "A0012 must fail closed without Cold Sweat CORE bridge");
        require(!NotionCombatPerkRules.frenzyBaselineAvailable(false, true),
            "A0012 must fail closed without provider impact hook");
        require(NotionCombatPerkRules.frenzyBaselineAvailable(true, true),
            "A0012 may activate only with impact and CORE bridge");

        var ranks = CombatPerkRanks.of(Map.of("A0012", 1));
        var baselineState = new NotionCombatPerkState();
        baselineState.addFury("player", 75.0D);
        var baseline = A0001A0020CombatPolicy.beforeHit(
            facts("frenzy", WeaponFamily.AXE, "target", false, false, true, false, true, 0L), ranks, baselineState);
        require(close(baseline.impactMultiplier(), 1.10D), "A0012 Frenzy baseline impact must be +10%");
        require(baseline.frenzyTradeoff(), "A0012 benefit must carry the explicit CORE/exhaustion tradeoff receipt");
        require(close(baselineState.fury("player"), 75.0D), "baseline Frenzy must not spend Fury");

        var peakState = new NotionCombatPerkState();
        peakState.addFury("player", 100.0D);
        var peak = A0001A0020CombatPolicy.beforeHit(
            facts("peak", WeaponFamily.AXE, "guarded", true, true, true, false, true, 100L), ranks, peakState);
        require(close(peak.impactMultiplier(), 1.20D), "A0012 peak must replace baseline with total +20% impact");
        require(close(peak.guardPressureMultiplier(), 1.40D), "A0012 peak must reach total +40% native guard pressure");
        require(peak.frenzyTradeoff(), "A0012 peak must pay the same explicit body cost");
        require(close(peakState.fury("player"), 60.0D), "A0012 peak must spend 40 Fury atomically at 100");
    }

    private static void frenzyDropTracksThresholdTransition() {
        var state = new NotionCombatPerkState();
        state.addFury("player", 100.0D);
        require(state.updateFrenzyState("player", true, 80, 0L), "A0012 must enter Frenzy at 75+ Fury");
        require(state.consumeFury("player", 40.0D, 100.0D), "test peak spend must succeed");
        require(!state.updateFrenzyState("player", true, 80, 1L), "A0012 must leave Frenzy below 75 Fury");
        require(state.rhythmDropActive("player", 6_000L), "A0012 rank-80 Queda de Ritmo must last six seconds");
        require(!state.rhythmDropActive("player", 6_001L), "A0012 Queda de Ritmo must expire after six seconds");
        require(NotionCombatPerkRules.frenzyDropDurationMillis(90) == 5_000L, "mastery 90 drop must be five seconds");
        require(NotionCombatPerkRules.frenzyDropDurationMillis(100) == 4_000L, "mastery 100 drop must be four seconds");
    }

    private static void heavyStaggerConsumesSwordAndSpearCharges() {
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1, "A0016", 2));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 5, 0L);
        state.addDistanceControl("player", 3, 0L, 7_000L);
        A0001A0020CombatPolicy.onConfirmedHostileHeavyStagger("player", ranks, state, 100L);
        require(state.momentum("player", 100L) == 3, "heavy stagger must remove two Momentum");
        require(state.distanceControl("player", 100L) == 2, "heavy stagger must remove one Distance Control");
    }

    private static void spearWindowsConsumeDistanceControlAndApplyTargetLockout() {
        var capstoneRanks = CombatPerkRanks.of(Map.of("A0016", 2, "A0017", 2, "A0018", 1));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 3, 0L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, capstoneRanks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, true, capstoneRanks, state, 80, 200L);
        var capstoneFacts = facts("spear-cap", WeaponFamily.SPEAR, "target", false, false, true, true, false, 300L);
        var capstone = A0001A0020CombatPolicy.beforeHit(capstoneFacts, capstoneRanks, state);
        require(close(capstone.damageMultiplier(), 1.15D), "A0013 rank 0 plus A0018 should produce 1.15 damage multiplier");
        require(close(capstone.impactMultiplier(), 1.40D), "A0018 impact must be +40%");
        require(state.distanceControl("player", 300L) == 3, "A0018 PRE must reserve rather than consume three Distance Control charges");
        A0001A0020CombatPolicy.afterConfirmedHit(capstoneFacts, capstoneRanks, state, capstone.suppressMomentumGain());
        require(state.distanceControl("player", 300L) == 1,
            "A0018 POST must consume three charges before same-hit A0016 grants one new charge");

        state.addDistanceControl("player", 3, 400L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, capstoneRanks, state, 80, 500L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, true, capstoneRanks, state, 80, 600L);
        var lockedFacts = facts("spear-lock", WeaponFamily.SPEAR, "target", false, false, true, true, false, 700L);
        var locked = A0001A0020CombatPolicy.beforeHit(lockedFacts, capstoneRanks, state);
        require(close(locked.damageMultiplier(), 1.0D), "A0018 target lockout must prevent a second capstone window");
        require(close(locked.impactMultiplier(), 1.35D), "A0017 may still reserve its independent intercept window during A0018 lockout");
        require(state.distanceControl("player", 700L) == 3, "A0017 PRE must not consume its reserved Distance Control charge");
        A0001A0020CombatPolicy.afterConfirmedHit(lockedFacts, capstoneRanks, state, locked.suppressMomentumGain());
        require(state.distanceControl("player", 700L) == 3,
            "A0017 POST spend must occur before same-hit A0016 gain, producing a net-zero charge change at cap");

        require(A0001A0020CombatPolicy.isIdealSpearRange(7.0D, 10.0D), "70% reach is ideal");
        require(A0001A0020CombatPolicy.isIdealSpearRange(10.0D, 10.0D), "100% reach is ideal");
        require(!A0001A0020CombatPolicy.isIdealSpearRange(6.99D, 10.0D), "below 70% reach is not ideal");
        require(A0001A0020CombatPolicy.isAdvancingToward(0, 0, 5, 0, -0.1D, 0), "motion toward player must be recognized");
        require(!A0001A0020CombatPolicy.isAdvancingToward(0, 0, 5, 0, 0.1D, 0), "motion away from player must not be recognized");
    }

    private static A0001A0020CombatPolicy.HitFacts facts(
        String action,
        WeaponFamily family,
        String target,
        boolean guarded,
        boolean armored,
        boolean guardHookAvailable,
        boolean idealRange,
        boolean coldSweatCoreAvailable,
        long now
    ) {
        return new A0001A0020CombatPolicy.HitFacts(
            "player", target, action, family, true, true, true, guarded, armored, guardHookAvailable,
            idealRange, false, true, true, coldSweatCoreAvailable, now
        );
    }

    private static boolean close(double left, double right) { return Math.abs(left - right) < 1.0E-9D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
