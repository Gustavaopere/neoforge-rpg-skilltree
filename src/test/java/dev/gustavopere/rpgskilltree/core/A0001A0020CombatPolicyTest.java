package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class A0001A0020CombatPolicyTest {
    public static void main(String[] args) {
        momentumUsesFiveSecondGraceThenOnePerSecondDecay();
        openingConsumesTwoMomentumAndRespectsPerTargetCooldown();
        furyGenerationUsesRankThenTargetSwitchMultiplier();
        ruptureSpendsFuryOnlyForDefendedOrArmoredTargets();
        frenzyBaselineFailsClosedWithoutCausalTradeoffs();
        spearWindowsConsumeDistanceControlAndApplyTargetLockout();
        System.out.println("A0001A0020CombatPolicyTest: PASS");
    }

    private static void momentumUsesFiveSecondGraceThenOnePerSecondDecay() {
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        var state = new NotionCombatPerkState();
        for (int i = 0; i < 3; i++) {
            var facts = facts("sword-" + i, WeaponFamily.SWORD, "target", false, false, false, false, 0L);
            A0001A0020CombatPolicy.afterConfirmedHit(facts, ranks, state, false);
        }
        require(state.momentum("player", 4_999L) == 3, "A0004 must keep all charges during five-second grace");
        require(state.momentum("player", 5_000L) == 2, "A0004 must lose first charge at five seconds");
        require(state.momentum("player", 6_000L) == 1, "A0004 must lose one charge per second");
        require(state.momentum("player", 7_000L) == 0, "A0004 decay must clamp at zero");
    }

    private static void openingConsumesTwoMomentumAndRespectsPerTargetCooldown() {
        var ranks = CombatPerkRanks.of(Map.of("A0005", 1));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 3, 0L);
        state.recordSwordSequenceTarget("player", "target");
        var first = A0001A0020CombatPolicy.beforeHit(
            facts("open-1", WeaponFamily.SWORD, "target", true, true, false, false, 1_000L), ranks, state);
        require(close(first.physicalPenetrationFraction(), 0.12D), "A0005 penetration must be 12%");
        require(close(first.impactMultiplier(), 1.08D), "A0005 impact must be +8%");
        require(state.momentum("player", 1_000L) == 1, "A0005 must consume two Momentum");

        state.addMomentum("player", 3, 1_100L);
        var cooldown = A0001A0020CombatPolicy.beforeHit(
            facts("open-2", WeaponFamily.SWORD, "target", true, true, false, false, 2_000L), ranks, state);
        require(close(cooldown.physicalPenetrationFraction(), 0.0D), "A0005 must not reactivate inside target cooldown");
        require(state.momentum("player", 2_000L) == 4, "blocked A0005 activation must not consume Momentum");

        var afterCooldown = A0001A0020CombatPolicy.beforeHit(
            facts("open-3", WeaponFamily.SWORD, "target", true, true, false, false, 7_000L), ranks, state);
        require(close(afterCooldown.physicalPenetrationFraction(), 0.12D), "A0005 must reactivate after six seconds");
    }

    private static void furyGenerationUsesRankThenTargetSwitchMultiplier() {
        var ranks = CombatPerkRanks.of(Map.of("A0010", 2));
        var state = new NotionCombatPerkState();
        A0001A0020CombatPolicy.afterConfirmedHit(
            facts("axe-1", WeaponFamily.AXE, "target-a", false, false, false, false, 0L), ranks, state, false);
        require(close(state.fury("player"), 9.6D), "A0010 rank 2 normal gain");
        A0001A0020CombatPolicy.afterConfirmedHit(
            facts("axe-2", WeaponFamily.AXE, "target-b", false, false, false, false, 100L), ranks, state, false);
        require(close(state.fury("player"), 24.0D), "A0010 target switch must apply x1.5 after rank multiplier");
    }

    private static void ruptureSpendsFuryOnlyForDefendedOrArmoredTargets() {
        var ranks = CombatPerkRanks.of(Map.of("A0011", 2));
        var state = new NotionCombatPerkState();
        state.addFury("player", 40.0D);
        var guarded = A0001A0020CombatPolicy.beforeHit(
            facts("rupture-1", WeaponFamily.AXE, "guarded", true, true, false, false, 0L), ranks, state);
        require(close(guarded.impactMultiplier(), 1.35D), "A0011 rank 2 impact");
        require(close(guarded.physicalPenetrationFraction(), 0.10D), "A0011 rank 2 penetration");
        require(close(state.fury("player"), 20.0D), "A0011 must spend 20 Fury");

        state.addFury("player", 20.0D);
        var undefended = A0001A0020CombatPolicy.beforeHit(
            facts("rupture-2", WeaponFamily.AXE, "plain", false, false, false, false, 100L), ranks, state);
        require(close(undefended.physicalPenetrationFraction(), 0.0D), "A0011 must not grant free penetration on undefended target");
        require(close(state.fury("player"), 40.0D), "A0011 must not spend Fury on ineligible target");

        var armored = A0001A0020CombatPolicy.beforeHit(
            facts("rupture-3", WeaponFamily.AXE, "armored", false, true, false, false, 200L), ranks, state);
        require(close(armored.impactMultiplier(), 1.0D), "armor fallback must omit provider guard pressure");
        require(close(armored.physicalPenetrationFraction(), 0.10D), "armor fallback must keep safe penetration");
    }

    private static void frenzyBaselineFailsClosedWithoutCausalTradeoffs() {
        require(!NotionCombatPerkRules.frenzyBaselineAvailable(true, false, true), "A0012 must fail closed without thermal receipt");
        require(!NotionCombatPerkRules.frenzyBaselineAvailable(true, true, false), "A0012 must fail closed without hunger/exhaustion receipt");
        require(NotionCombatPerkRules.frenzyBaselineAvailable(true, true, true), "A0012 baseline may activate only with all mandatory causal tradeoffs");
        require(!NotionCombatPerkRules.frenzyThirstSurchargeAvailable(false), "A0012 may not infer thirst from exhaustion");
    }

    private static void spearWindowsConsumeDistanceControlAndApplyTargetLockout() {
        var capstoneRanks = CombatPerkRanks.of(Map.of("A0016", 2, "A0017", 2, "A0018", 1));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 3, 0L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, capstoneRanks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, true, capstoneRanks, state, 80, 200L);
        var capstone = A0001A0020CombatPolicy.beforeHit(
            facts("spear-cap", WeaponFamily.SPEAR, "target", false, false, false, true, 300L), capstoneRanks, state);
        require(close(capstone.damageMultiplier(), 1.15D), "A0013 rank 0 plus A0018 should produce 1.15 damage multiplier");
        require(close(capstone.impactMultiplier(), 1.40D), "A0018 impact must be +40%");
        require(state.distanceControl("player", 300L) == 0, "A0018 must consume all three Distance Control charges");

        state.addDistanceControl("player", 3, 400L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, capstoneRanks, state, 80, 500L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, true, capstoneRanks, state, 80, 600L);
        var locked = A0001A0020CombatPolicy.beforeHit(
            facts("spear-lock", WeaponFamily.SPEAR, "target", false, false, false, true, 700L), capstoneRanks, state);
        require(close(locked.damageMultiplier(), 1.0D), "A0018 target lockout must prevent a second capstone window");
        require(close(locked.impactMultiplier(), 1.35D), "A0017 may still consume its independent intercept window during A0018 lockout");

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
        boolean heavy,
        boolean idealRange,
        long now
    ) {
        return new A0001A0020CombatPolicy.HitFacts(
            "player", target, action, family, true, true, true, guarded, armored, heavy,
            idealRange, false, true, true, now
        );
    }

    private static boolean close(double left, double right) { return Math.abs(left - right) < 1.0E-9D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
