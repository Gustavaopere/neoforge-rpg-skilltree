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
    void a0006ConsumesFiveMomentumAndSuppressesSameRiposteHitGain() {
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1, "A0006", 1));
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 4, 1_000L);

        boolean armed = A0001A0020CombatPolicy.onConfirmedTechnicalDefense(
            "player", "defense-1", WeaponFamily.SWORD, ranks, state, 80, 1_000L);

        assertTrue(armed, "confirmed technical defense must arm A0006 once Momentum reaches 5");
        assertEquals(5, state.momentum("player", 1_000L), "A0004 defense gain must occur before the A0006 gate");

        var facts = facts("riposte-1", WeaponFamily.SWORD, true, true, true, true, 1_500L);
        var modifiers = A0001A0020CombatPolicy.beforeHit(facts, ranks, state);

        assertEquals(1.20D, modifiers.damageMultiplier(), EPSILON, "critical A0006 riposte must receive +20% damage");
        assertEquals(1.20D, modifiers.impactMultiplier(), EPSILON, "A0006 must use the provider impact hook when available");
        assertTrue(modifiers.suppressMomentumGain(), "the riposte hit must not regenerate Momentum through A0004");
        assertEquals(0, state.momentum("player", 1_500L), "A0006 must atomically consume all 5 Momentum");

        A0001A0020CombatPolicy.afterConfirmedHit(facts, ranks, state, modifiers.suppressMomentumGain());
        assertEquals(0, state.momentum("player", 1_500L), "the same riposte hit must remain at zero Momentum");

        var duplicate = A0001A0020CombatPolicy.beforeHit(facts, ranks, state);
        assertEquals(1.0D, duplicate.damageMultiplier(), EPSILON, "the same root action must not consume/apply A0006 twice");
        assertFalse(duplicate.suppressMomentumGain(), "a duplicate callback must not claim a second A0006 activation");
    }

    @Test
    void a0010RequiresConfirmedDirectHostileAxeDamageAndDeduplicatesRootAction() {
        var ranks = CombatPerkRanks.of(Map.of("A0010", 2));

        assertNoFury(ranks, facts("no-damage", WeaponFamily.AXE, true, true, false, false, 100L),
            "A0010 must not gain Fury without effective damage");
        assertNoFury(ranks, facts("indirect", WeaponFamily.AXE, false, true, true, false, false, 100L),
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
