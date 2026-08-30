package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.A0001A0020CombatPolicy.HitFacts;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0001A0010ImplementationContractJUnitTest {
    private static final double EPSILON = 1.0E-9D;

    @Test
    void a0005DefersMomentumSpendAndCooldownUntilConfirmedDamagePost() {
        var ranks = CombatPerkRanks.of(Map.of("A0005", 1));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 3, 1_000L);
        state.recordSwordSequenceTarget("player", "target-a");

        var failedPreFacts = guardedSwordFacts("opening-failed", true, 1_500L);
        var failedPre = A0001A0020CombatPolicy.beforeHit(failedPreFacts, ranks, state);

        assertEquals(0.12D, failedPre.physicalPenetrationFraction(), EPSILON,
            "an eligible A0005 PRE may prepare penetration for the provider hit");
        assertEquals(1.08D, failedPre.impactMultiplier(), EPSILON,
            "an eligible native-defense A0005 PRE may prepare impact");
        assertEquals(3, state.momentum("player", 1_500L),
            "A0005 must not irreversibly spend Momentum before effective damage is confirmed");
        assertTrue(state.openingCooldownReady("player", "target-a", 1_500L),
            "A0005 target cooldown must not start before effective damage is confirmed");

        var failedPostFacts = guardedSwordFacts("opening-failed", false, 1_500L);
        A0001A0020CombatPolicy.afterConfirmedHit(failedPostFacts, ranks, state, failedPre.suppressMomentumGain());
        assertEquals(3, state.momentum("player", 1_500L),
            "zero/cancelled A0005 damage must leave Momentum untouched");
        assertTrue(state.openingCooldownReady("player", "target-a", 1_500L),
            "zero/cancelled A0005 damage must not leave a ghost cooldown");

        var validFacts = guardedSwordFacts("opening-valid", true, 1_600L);
        var validPre = A0001A0020CombatPolicy.beforeHit(validFacts, ranks, state);
        assertEquals(0.12D, validPre.physicalPenetrationFraction(), EPSILON,
            "a later valid root action must still be able to prepare A0005 after a failed hit");
        assertEquals(3, state.momentum("player", 1_600L),
            "A0005 must remain transactional through the valid PRE stage");

        A0001A0020CombatPolicy.afterConfirmedHit(validFacts, ranks, state, validPre.suppressMomentumGain());
        assertEquals(1, state.momentum("player", 1_600L),
            "confirmed A0005 damage must commit the two-Momentum spend exactly once");
        assertFalse(state.openingCooldownReady("player", "target-a", 1_600L),
            "confirmed A0005 damage must commit the per-target cooldown");
    }

    @Test
    void a0006DefersRiposteAndFiveMomentumSpendUntilConfirmedDamagePost() {
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1, "A0006", 1));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 4, 1_000L);

        boolean armed = A0001A0020CombatPolicy.onConfirmedTechnicalDefense(
            "player", "defense-1", WeaponFamily.SWORD, ranks, state, 80, 1_000L);

        assertTrue(armed, "confirmed technical defense must arm A0006 once Momentum reaches 5");
        assertEquals(5, state.momentum("player", 1_000L), "A0004 defense gain must occur before the A0006 gate");

        var failedFacts = facts("riposte-failed", WeaponFamily.SWORD, true, true, true, true, 1_500L);
        var failedPre = A0001A0020CombatPolicy.beforeHit(failedFacts, ranks, state);

        assertEquals(1.20D, failedPre.damageMultiplier(), EPSILON,
            "eligible critical A0006 PRE must prepare the +20% critical-damage component");
        assertEquals(1.20D, failedPre.impactMultiplier(), EPSILON,
            "eligible A0006 PRE must prepare provider impact when available");
        assertTrue(failedPre.suppressMomentumGain(),
            "a prepared A0006 consumer must suppress same-result Momentum if the hit later confirms");
        assertEquals(5, state.momentum("player", 1_500L),
            "A0006 must not consume five Momentum in PRE before effective damage is known");

        var failedPostFacts = facts("riposte-failed", WeaponFamily.SWORD, true, true, false, true, 1_500L);
        A0001A0020CombatPolicy.afterConfirmedHit(
            failedPostFacts, ranks, state, failedPre.suppressMomentumGain());
        assertEquals(5, state.momentum("player", 1_500L),
            "zero/cancelled A0006 damage must preserve all five Momentum and the armed opportunity");

        var validFacts = facts("riposte-valid", WeaponFamily.SWORD, true, true, true, true, 1_600L);
        var validPre = A0001A0020CombatPolicy.beforeHit(validFacts, ranks, state);
        assertEquals(1.20D, validPre.damageMultiplier(), EPSILON,
            "a failed consumer must not destroy the A0006 window before a later valid hit");
        assertEquals(5, state.momentum("player", 1_600L),
            "the valid A0006 PRE stage must still be non-destructive");

        A0001A0020CombatPolicy.afterConfirmedHit(validFacts, ranks, state, validPre.suppressMomentumGain());
        assertEquals(0, state.momentum("player", 1_600L),
            "confirmed A0006 damage must atomically commit the five-Momentum spend");
    }

    @Test
    void a0010RequiresConfirmedDirectHostileAxeDamageAndDeduplicatesRootAction() {
        var ranks = CombatPerkRanks.of(Map.of("A0010", 2));

        assertNoFury(ranks, facts("no-damage", WeaponFamily.AXE, true, true, false, false, 100L),
            "A0010 must not gain Fury without effective damage");
        assertNoFury(ranks, facts("indirect", WeaponFamily.AXE, false, true, true, false, 100L),
            "A0010 must not gain Fury from indirect/unattributed damage");
        assertNoFury(ranks, facts("non-hostile", WeaponFamily.AXE, true, false, true, false, 100L),
            "A0010 must not gain Fury from non-hostile targets");
        assertNoFury(ranks, facts("wrong-family", WeaponFamily.SWORD, true, true, true, false, 100L),
            "A0010 must fail closed for a non-axe family");

        var state = new NotionCombatPerkState();
        var first = facts("axe-root-1", WeaponFamily.AXE, true, true, true, false, 1_000L);
        A0001A0020CombatPolicy.afterConfirmedHit(first, ranks, state, false);
        assertEquals(9.6D, state.fury("player"), EPSILON, "rank 2 A0010 must grant 9.6 Fury for the first valid hit");

        A0001A0020CombatPolicy.afterConfirmedHit(first, ranks, state, false);
        assertEquals(9.6D, state.fury("player"), EPSILON, "duplicate callbacks for the same root action must be idempotent");

        A0001A0020CombatPolicy.afterConfirmedHit(
            facts("axe-root-2", WeaponFamily.AXE, true, true, true, false, 1_100L), ranks, state, false);
        assertEquals(19.2D, state.fury("player"), EPSILON, "a new root action on the same target must grant the normal amount once");

        A0001A0020CombatPolicy.afterConfirmedHit(
            facts("axe-root-3", WeaponFamily.AXE, true, true, true, false, "target-b", 1_200L), ranks, state, false);
        assertEquals(33.6D, state.fury("player"), EPSILON, "switching targets must apply the x1.5 A0010 multiplier after rank scaling");
    }

    private static void assertNoFury(CombatPerkRanks ranks, HitFacts facts, String message) {
        var state = new NotionCombatPerkState();
        A0001A0020CombatPolicy.afterConfirmedHit(facts, ranks, state, false);
        assertEquals(0.0D, state.fury("player"), EPSILON, message);
    }

    private static HitFacts guardedSwordFacts(String rootActionId, boolean actualDamage, long nowMillis) {
        return new HitFacts(
            "player",
            "target-a",
            rootActionId,
            WeaponFamily.SWORD,
            true,
            true,
            actualDamage,
            true,
            false,
            true,
            false,
            false,
            true,
            true,
            false,
            nowMillis
        );
    }

    private static HitFacts facts(
        String rootActionId,
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        boolean actualDamage,
        boolean critical,
        long nowMillis
    ) {
        return facts(rootActionId, family, direct, hostile, actualDamage, critical, "target-a", nowMillis);
    }

    private static HitFacts facts(
        String rootActionId,
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        boolean actualDamage,
        boolean critical,
        String targetId,
        long nowMillis
    ) {
        return new HitFacts(
            "player",
            targetId,
            rootActionId,
            family,
            direct,
            hostile,
            actualDamage,
            false,
            false,
            true,
            false,
            critical,
            true,
            true,
            false,
            nowMillis
        );
    }
}
