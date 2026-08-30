package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class A0001A0020ProviderBoundaryJUnitTest {
    @Test
    void indirectCompanionOrMagicSourceCannotCarryMartialBonuses() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of(
            "A0007", 3,
            "A0009", 3,
            "A0010", 2,
            "A0011", 2,
            "A0012", 1
        ));
        NotionCombatPerkState state = new NotionCombatPerkState();
        state.addFury("player", 100.0D);

        var facts = new A0001A0020CombatPolicy.HitFacts(
            "player", "target", "indirect-root", WeaponFamily.AXE,
            false, true, true,
            true, true, true,
            false, true,
            true, true, true,
            1_000L
        );

        var modifiers = A0001A0020CombatPolicy.beforeHit(facts, ranks, state);
        assertEquals(1.0D, modifiers.damageMultiplier(), 1.0E-9D);
        assertEquals(0.0D, modifiers.criticalChanceBonus(), 1.0E-9D);
        assertEquals(1.0D, modifiers.impactMultiplier(), 1.0E-9D);
        assertEquals(1.0D, modifiers.guardPressureMultiplier(), 1.0E-9D);
        assertEquals(0.0D, modifiers.physicalPenetrationFraction(), 1.0E-9D);
        assertFalse(modifiers.frenzyTradeoff());
        assertEquals(100.0D, state.fury("player"), 1.0E-9D);

        A0001A0020CombatPolicy.afterConfirmedHit(facts, ranks, state, false);
        assertEquals(100.0D, state.fury("player"), 1.0E-9D);
    }

    @Test
    void nonHostileTargetCannotCarrySpearBonusesOrState() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of(
            "A0013", 3,
            "A0015", 3,
            "A0016", 2,
            "A0017", 2,
            "A0018", 1
        ));
        NotionCombatPerkState state = new NotionCombatPerkState();

        var facts = new A0001A0020CombatPolicy.HitFacts(
            "player", "ally", "friendly-root", WeaponFamily.SPEAR,
            true, false, true,
            true, true, true,
            true, true,
            true, true, false,
            2_000L
        );

        var modifiers = A0001A0020CombatPolicy.beforeHit(facts, ranks, state);
        assertEquals(1.0D, modifiers.damageMultiplier(), 1.0E-9D);
        assertEquals(0.0D, modifiers.criticalChanceBonus(), 1.0E-9D);
        assertEquals(1.0D, modifiers.impactMultiplier(), 1.0E-9D);
        assertEquals(0, state.distanceControl("player", 2_000L));

        A0001A0020CombatPolicy.afterConfirmedHit(facts, ranks, state, false);
        assertEquals(0, state.distanceControl("player", 2_000L));
    }
}
