package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.CombatPerkAvailabilityRuntime;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Chat 3 regression coverage for the A0071-A0080 availability and pure-policy contract. */
final class A0071A0080Chat3CoverageJUnitTest {
    @Test
    void structurallyBlockedNodesRemainFailClosedAndLegacyRanksAreMasked() {
        for (String code : new String[] {"A0072", "A0075", "A0077", "A0080"}) {
            assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable(code), code);
        }
        for (String code : new String[] {"A0071", "A0073", "A0074", "A0076", "A0078", "A0079"}) {
            assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable(code), code);
        }

        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(
            CombatPerkRanks.of(Map.of(
                "A0071", 5,
                "A0072", 1,
                "A0075", 1,
                "A0077", 1,
                "A0080", 1
            ))
        );
        assertEquals(5, effective.rank("A0071"));
        assertEquals(0, effective.rank("A0072"));
        assertEquals(0, effective.rank("A0075"));
        assertEquals(0, effective.rank("A0077"));
        assertEquals(0, effective.rank("A0080"));
    }

    @Test
    void eliteBonusNeverStacksOnBossClassification() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0070", 5, "A0071", 5));
        A0061A0080CombatState state = new A0061A0080CombatState();
        A0061A0080CombatPolicy.HitFacts both = new A0061A0080CombatPolicy.HitFacts(
            "player", "target", "root", 0.50D,
            true, true, false, false, false, true, true, 1_000L
        );
        assertEquals(1.15D, A0061A0080CombatPolicy.beforePhysicalHit(both, ranks, state).damageMultiplier(), 1.0e-12);
    }

    @Test
    void aggressiveStanceUsesApprovedTradeoffAndSwapCooldown() {
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.switchStance("player", A0061A0080CombatState.Stance.AGGRESSIVE, 1_000L));
        assertEquals(1.08D, A0061A0080CombatPolicy.stanceDamageMultiplier(state.stance("player")), 1.0e-12);
        assertEquals(-0.05D, A0061A0080CombatPolicy.stancePhysicalResistanceDelta(state.stance("player")), 1.0e-12);
        assertFalse(state.switchStance("player", A0061A0080CombatState.Stance.NONE, 2_499L));
        assertTrue(state.switchStance("player", A0061A0080CombatState.Stance.NONE, 2_500L));
    }

    @Test
    void movementAndStationaryBonusesUseApprovedPerRankValues() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0078", 3, "A0079", 3));
        assertEquals(1.12D, A0061A0080CombatPolicy.movementDamageMultiplier(ranks, true), 1.0e-12);
        assertEquals(1.15D, A0061A0080CombatPolicy.stationaryDamageMultiplier(ranks, true), 1.0e-12);
        assertEquals(1.0D, A0061A0080CombatPolicy.movementDamageMultiplier(ranks, false), 1.0e-12);
        assertEquals(1.0D, A0061A0080CombatPolicy.stationaryDamageMultiplier(ranks, false), 1.0e-12);
    }
}
