package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.A0001A0020CombatPolicy.HitFacts;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class A0011A0020CausalCommitJUnitTest {
    private static final double EPSILON = 1.0E-9D;

    @Test
    void a0011DefersFurySpendUntilConfirmedDamageAndReservesCostForFrenzyGate() {
        var ranks = CombatPerkRanks.of(Map.of("A0011", 2, "A0012", 1));
        var state = new NotionCombatPerkState();
        state.addFury("player", 80.0D);

        var failedPreFacts = axeFacts("rupture-failed", true, 1_000L);
        var failedPre = A0001A0020CombatPolicy.beforeHit(failedPreFacts, ranks, state);
        assertEquals(1.35D, failedPre.impactMultiplier(), EPSILON,
            "eligible A0011 PRE must still prepare its rank-2 impact modifier");
        assertEquals(0.10D, failedPre.physicalPenetrationFraction(), EPSILON,
            "eligible A0011 PRE must still prepare its rank-2 penetration modifier");
        assertFalse(failedPre.frenzyTradeoff(),
            "A0012 baseline must evaluate effective Fury after the reserved A0011 cost");
        assertEquals(80.0D, state.fury("player"), EPSILON,
            "A0011 must not irreversibly spend Fury before effective damage is confirmed");

        var failedPostFacts = axeFacts("rupture-failed", false, 1_000L);
        A0001A0020CombatPolicy.afterConfirmedHit(failedPostFacts, ranks, state, false);
        assertEquals(80.0D, state.fury("player"), EPSILON,
            "zero/cancelled A0011 damage must leave Fury untouched");

        var validFacts = axeFacts("rupture-valid", true, 1_100L);
        var validPre = A0001A0020CombatPolicy.beforeHit(validFacts, ranks, state);
        assertEquals(80.0D, state.fury("player"), EPSILON,
            "valid A0011 PRE must remain non-destructive");
        A0001A0020CombatPolicy.afterConfirmedHit(validFacts, ranks, state, false);
        assertEquals(60.0D, state.fury("player"), EPSILON,
            "confirmed A0011 damage must commit exactly 20 Fury once");
        A0001A0020CombatPolicy.afterConfirmedHit(validFacts, ranks, state, false);
        assertEquals(60.0D, state.fury("player"), EPSILON,
            "duplicate POST for the same root must not double-spend A0011 Fury");
    }

    @Test
    void a0017DefersWindowAndDistanceControlConsumptionUntilConfirmedDamage() {
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2, "A0017", 2));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 2, 0L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, ranks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, true, ranks, state, 80, 200L);

        var failedPreFacts = spearFacts("intercept-failed", false, 300L);
        var failedPre = A0001A0020CombatPolicy.beforeHit(failedPreFacts, ranks, state);
        assertEquals(1.35D, failedPre.impactMultiplier(), EPSILON,
            "eligible A0017 PRE must prepare the rank-2 impact modifier");
        assertEquals(2, state.distanceControl("player", 300L),
            "A0017 PRE must not spend Distance Control before confirmed damage");

        var failedPostFacts = spearFacts("intercept-failed", false, 300L, false);
        A0001A0020CombatPolicy.afterConfirmedHit(failedPostFacts, ranks, state, false);
        assertEquals(2, state.distanceControl("player", 300L),
            "zero/cancelled A0017 damage must preserve Distance Control");

        var validFacts = spearFacts("intercept-valid", false, 350L);
        var validPre = A0001A0020CombatPolicy.beforeHit(validFacts, ranks, state);
        assertEquals(1.35D, validPre.impactMultiplier(), EPSILON,
            "failed A0017 root must leave the same intercept window available for the next valid hit");
        A0001A0020CombatPolicy.afterConfirmedHit(validFacts, ranks, state, false);
        assertEquals(1, state.distanceControl("player", 350L),
            "confirmed A0017 damage must commit exactly one Distance Control");
    }

    @Test
    void a0018DefersWindowChargesAndTargetLockoutUntilConfirmedDamage() {
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2, "A0018", 1));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 3, 0L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, ranks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, false, ranks, state, 80, 200L);

        var failedPreFacts = spearFacts("line-failed", false, 300L);
        var failedPre = A0001A0020CombatPolicy.beforeHit(failedPreFacts, ranks, state);
        assertEquals(1.15D, failedPre.damageMultiplier(), EPSILON,
            "eligible A0018 PRE must prepare +15% damage");
        assertEquals(1.40D, failedPre.impactMultiplier(), EPSILON,
            "eligible A0018 PRE must prepare +40% impact");
        assertEquals(3, state.distanceControl("player", 300L),
            "A0018 PRE must not spend three Distance Control charges before confirmed damage");

        var failedPostFacts = spearFacts("line-failed", false, 300L, false);
        A0001A0020CombatPolicy.afterConfirmedHit(failedPostFacts, ranks, state, false);
        assertEquals(3, state.distanceControl("player", 300L),
            "zero/cancelled A0018 damage must preserve all three charges");

        var validFacts = spearFacts("line-valid", false, 350L);
        var validPre = A0001A0020CombatPolicy.beforeHit(validFacts, ranks, state);
        assertEquals(1.15D, validPre.damageMultiplier(), EPSILON,
            "failed A0018 root must leave the same line window available for the next valid hit");
        A0001A0020CombatPolicy.afterConfirmedHit(validFacts, ranks, state, false);
        assertEquals(0, state.distanceControl("player", 350L),
            "confirmed A0018 damage must commit all three charges exactly once");

        state.addDistanceControl("player", 3, 400L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", false, false, ranks, state, 80, 500L);
        A0001A0020CombatPolicy.onSpearRangeSample("player", "target", true, false, ranks, state, 80, 600L);
        var locked = A0001A0020CombatPolicy.beforeHit(spearFacts("line-locked", false, 700L), ranks, state);
        assertEquals(1.0D, locked.damageMultiplier(), EPSILON,
            "confirmed A0018 consumption must start the per-target lockout only at POST commit");
    }

    private static HitFacts axeFacts(String root, boolean actualDamage, long nowMillis) {
        return new HitFacts(
            "player", "target", root, WeaponFamily.AXE,
            true, true, actualDamage,
            true, true, true,
            false, false,
            true, true,
            true,
            nowMillis
        );
    }

    private static HitFacts spearFacts(String root, boolean critical, long nowMillis) {
        return spearFacts(root, critical, nowMillis, true);
    }

    private static HitFacts spearFacts(String root, boolean critical, long nowMillis, boolean actualDamage) {
        return new HitFacts(
            "player", "target", root, WeaponFamily.SPEAR,
            true, true, actualDamage,
            false, false, true,
            true, critical,
            true, true,
            false,
            nowMillis
        );
    }
}
