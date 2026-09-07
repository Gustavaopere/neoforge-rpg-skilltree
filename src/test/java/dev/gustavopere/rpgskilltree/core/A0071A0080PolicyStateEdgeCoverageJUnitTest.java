package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class A0071A0080PolicyStateEdgeCoverageJUnitTest {
    @AfterEach
    void clearReceipt() {
        A0061A0080ReservationReceiptContext.clear();
    }

    @Test
    void valueObjectsRejectInvalidInputsAndPhysicalPolicyCoversNeutralAndStackedBranches() {
        assertThrows(IllegalArgumentException.class, () -> new A0061A0080CombatPolicy.HitFacts(
            "actor", "target", "root", Double.NaN, false, false, false, false, false, true, true, 0L));
        assertThrows(IllegalArgumentException.class, () -> new A0061A0080CombatPolicy.PhysicalModifiers(0.0D, 0.0D, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new A0061A0080CombatPolicy.PhysicalModifiers(1.0D, -0.1D, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new A0061A0080CombatPolicy.SpecialResult(false, 1.0D, 0.0D, 0.0D));

        A0061A0080CombatState state = new A0061A0080CombatState();
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of(
            "A0061", 1, "A0065", 2, "A0066", 3, "A0068", 1, "A0069", 1,
            "A0070", 1, "A0071", 1, "A0072", 1, "A0078", 1, "A0079", 1));

        var ineligible = new A0061A0080CombatPolicy.HitFacts(
            "actor", "target", "root", 0.5D, false, false, false, false, false, false, true, 1_000L);
        assertEquals(A0061A0080CombatPolicy.PhysicalModifiers.neutral(),
            A0061A0080CombatPolicy.beforePhysicalHit(ineligible, ranks, state));

        state.refreshRetaliation("actor", 1_000L);
        assertTrue(state.switchStance("actor", A0061A0080CombatState.Stance.AGGRESSIVE, 1_000L));
        var lowElite = new A0061A0080CombatPolicy.HitFacts(
            "actor", "target", "root", 0.2D, false, true, true, true, false, true, true, 1_100L);
        var low = A0061A0080CombatPolicy.beforePhysicalHit(lowElite, ranks, state);
        assertEquals(1.23D, low.damageMultiplier(), 1.0e-12);
        assertEquals(0.04D, low.penetrationFraction(), 1.0e-12);
        assertEquals(1.09D, low.impactMultiplier(), 1.0e-12);

        var highBoss = new A0061A0080CombatPolicy.HitFacts(
            "actor", "target", "root2", 0.9D, true, true, false, false, false, true, true, 5_000L);
        assertEquals(1.17D, A0061A0080CombatPolicy.beforePhysicalHit(highBoss, ranks, state).damageMultiplier(), 1.0e-12);
    }

    @Test
    void scalarCombatModifiersAndRetaliationCoverPositiveNegativeAndDeduplicatedPaths() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0062", 2, "A0063", 3, "A0064", 4, "A0067", 2, "A0072", 2));
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertEquals(0.04D, A0061A0080CombatPolicy.criticalChanceBonus(ranks), 1.0e-12);
        assertEquals(1.15D, A0061A0080CombatPolicy.criticalDamageMultiplier(ranks, true), 1.0e-12);
        assertEquals(1.0D, A0061A0080CombatPolicy.criticalDamageMultiplier(ranks, false), 1.0e-12);
        assertEquals(1.08D, A0061A0080CombatPolicy.attackSpeedMultiplier(ranks), 1.0e-12);
        assertEquals(0.08D, A0061A0080CombatPolicy.offensiveInterruptionResistanceFraction(ranks), 1.0e-12);

        assertFalse(A0061A0080CombatPolicy.onDirectHostileDamageTaken("actor", "evt0", 2.0D, false, ranks, state, 1_000L));
        assertFalse(A0061A0080CombatPolicy.onDirectHostileDamageTaken("actor", "evt1", Double.NaN, true, ranks, state, 1_000L));
        assertFalse(A0061A0080CombatPolicy.onDirectHostileDamageTaken("actor", "evt2", 0.0D, true, ranks, state, 1_000L));
        assertTrue(A0061A0080CombatPolicy.onDirectHostileDamageTaken("actor", "evt3", 2.0D, true, ranks, state, 1_000L));
        assertFalse(A0061A0080CombatPolicy.onDirectHostileDamageTaken("actor", "evt3", 2.0D, true, ranks, state, 1_001L));
        assertEquals(1.08D, A0061A0080CombatPolicy.retaliationDamageMultiplier("actor", ranks, state, 1_100L), 1.0e-12);
        assertEquals(1.0D, A0061A0080CombatPolicy.retaliationDamageMultiplier("actor", ranks, state, 4_001L), 1.0e-12);
    }

    @SuppressWarnings("deprecation")
    @Test
    void executionCoversLegacyImpactAndReservationCommitRollbackLifecycle() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0073", 1));
        A0061A0080CombatState legacy = new A0061A0080CombatState();

        assertFalse(A0061A0080CombatPolicy.execution("a", "t", "open", 0.19D, false, ranks, legacy, true, 1_000L).applied());
        var finisher = A0061A0080CombatPolicy.execution("a", "t", "finish", 0.10D, false, ranks, legacy, true, 1_100L);
        assertTrue(finisher.applied());
        assertEquals(1.18D, finisher.damageMultiplier(), 1.0e-12);
        assertEquals(1.20D, finisher.impactMultiplier(), 1.0e-12);
        assertTrue(legacy.executionCoolingDown("a", "t", 1_101L));

        A0061A0080CombatState reserved = new A0061A0080CombatState();
        assertTrue(reserved.armExecution("a", "t", "open", 10_000L));
        var reservedResult = A0061A0080CombatPolicy.execution("a", "t", "finish", 0.10D, true, ranks, reserved, false, 10_100L);
        assertTrue(reservedResult.applied());
        assertEquals(1.09D, reservedResult.damageMultiplier(), 1.0e-12);
        var receipt = A0061A0080ReservationReceiptContext.take("a", "t");
        assertTrue(receipt.executionReserved());
        assertTrue(reserved.commitExecution("a", "t", "finish", 10_101L));
        assertTrue(reserved.executionCoolingDown("a", "t", 10_102L));

        A0061A0080CombatState candidate = new A0061A0080CombatState();
        var opener = A0061A0080CombatPolicy.execution("a", "t", "root", 0.19D, false, ranks, candidate, false, 20_000L);
        assertFalse(opener.applied());
        var armReceipt = A0061A0080ReservationReceiptContext.take("a", "t");
        assertTrue(armReceipt.executionArmCandidate());
        assertTrue(candidate.armExecutionConfirmed("a", "t", "root", 20_001L));
        assertTrue(candidate.executionWindowActive("a", "t", 20_002L));
        candidate.rollbackExecution("a", "t", "other");
        assertFalse(candidate.reserveExecution("a", "t", "root", 20_003L));
    }

    @SuppressWarnings("deprecation")
    @Test
    void firstBloodCoversLegacyAndReservationOpenerFinisherLifecycle() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0074", 1));
        A0061A0080CombatState legacy = new A0061A0080CombatState();
        assertFalse(A0061A0080CombatPolicy.firstBlood("a", "t", "open", 0.90D, ranks, legacy, true, 1_000L).applied());
        var finish = A0061A0080CombatPolicy.firstBlood("a", "t", "finish", 0.50D, ranks, legacy, true, 1_100L);
        assertTrue(finish.applied());
        assertEquals(1.10D, finish.damageMultiplier(), 1.0e-12);
        assertEquals(1.20D, finish.impactMultiplier(), 1.0e-12);

        A0061A0080CombatState state = new A0061A0080CombatState();
        var opener = A0061A0080CombatPolicy.firstBlood("a", "t", "r1", 0.90D, ranks, state, false, 10_000L);
        assertFalse(opener.applied());
        var openerReceipt = A0061A0080ReservationReceiptContext.take("a", "t");
        assertEquals(A0061A0080CombatState.FirstBloodReservation.OPENER, openerReceipt.firstBloodReservation());
        assertTrue(state.commitFirstBlood("a", "t", "r1", A0061A0080CombatState.FirstBloodReservation.OPENER, 10_001L));
        assertTrue(state.firstBloodWindowActive("a", "t", 10_002L));

        var finisher = A0061A0080CombatPolicy.firstBlood("a", "t", "r2", 0.50D, ranks, state, false, 10_100L);
        assertTrue(finisher.applied());
        var finisherReceipt = A0061A0080ReservationReceiptContext.take("a", "t");
        assertEquals(A0061A0080CombatState.FirstBloodReservation.FINISHER, finisherReceipt.firstBloodReservation());
        assertTrue(state.commitFirstBlood("a", "t", "r2", A0061A0080CombatState.FirstBloodReservation.FINISHER, 10_101L));
        assertFalse(state.firstBloodWindowActive("a", "t", 10_102L));

        A0061A0080CombatState lowHealth = new A0061A0080CombatState();
        assertFalse(A0061A0080CombatPolicy.firstBlood("a", "t", "low", 0.50D, ranks, lowHealth, false, 20_000L).applied());
        var lowReceipt = A0061A0080ReservationReceiptContext.take("a", "t");
        assertEquals(A0061A0080CombatState.FirstBloodReservation.NONE, lowReceipt.firstBloodReservation());
        lowHealth.rollbackFirstBlood("a", "t", "low");
        lowHealth.recordConfirmedAttack("a", "t", 20_001L);
    }

    @Test
    void sustainedRhythmStanceAndOpportunityCoverActivationExpiryAndFailClosedInputs() {
        A0061A0080CombatState state = new A0061A0080CombatState();
        CombatPerkRanks sustained = CombatPerkRanks.of(Map.of("A0075", 1));
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "one", sustained, state, true, true, true, 1_000L));
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "two", sustained, state, true, true, true, 1_100L));
        assertTrue(A0061A0080CombatPolicy.recordMartialAction("a", "three", sustained, state, true, true, true, 1_200L));
        assertEquals(1.10D, A0061A0080CombatPolicy.sustainedStaminaRegenMultiplier("a", state, 1_201L), 1.0e-12);
        assertEquals(1.15D, A0061A0080CombatPolicy.sustainedThermalActivityMultiplier("a", state, 1_201L), 1.0e-12);
        assertEquals(1.10D, A0061A0080CombatPolicy.sustainedExhaustionMultiplier("a", state, 1_201L), 1.0e-12);
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "four", sustained, state, true, true, true, 1_300L));
        assertEquals(1.0D, A0061A0080CombatPolicy.sustainedStaminaRegenMultiplier("a", state, 7_201L), 1.0e-12);

        A0061A0080CombatState reset = new A0061A0080CombatState();
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "one", sustained, reset, true, true, true, 1_000L));
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "two", sustained, reset, true, true, true, 1_100L));
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "bad", sustained, reset, false, true, true, 1_200L));
        assertFalse(A0061A0080CombatPolicy.recordMartialAction("a", "three", sustained, reset, true, true, true, 1_300L));

        assertEquals(0.95D, A0061A0080CombatPolicy.stanceDamageMultiplier(A0061A0080CombatState.Stance.CAUTIOUS), 1.0e-12);
        assertEquals(0.08D, A0061A0080CombatPolicy.stancePhysicalResistanceDelta(A0061A0080CombatState.Stance.CAUTIOUS), 1.0e-12);
        assertEquals(1.0D, A0061A0080CombatPolicy.stanceDamageMultiplier(A0061A0080CombatState.Stance.NONE), 1.0e-12);
        assertEquals(0.0D, A0061A0080CombatPolicy.stancePhysicalResistanceDelta(A0061A0080CombatState.Stance.NONE), 1.0e-12);

        CombatPerkRanks opportunity = CombatPerkRanks.of(Map.of("A0080", 1));
        assertFalse(A0061A0080CombatPolicy.onConfirmedDodgeAvoidance("a", "d0", false, opportunity, state, 30_000L));
        assertTrue(A0061A0080CombatPolicy.onConfirmedDodgeAvoidance("a", "d1", true, opportunity, state, 30_000L));
        assertFalse(A0061A0080CombatPolicy.onConfirmedDodgeAvoidance("a", "d1", true, opportunity, state, 30_001L));
        assertEquals(1.15D, A0061A0080CombatPolicy.consumeOpportunityDamageMultiplier("a", "hit", opportunity, state, 30_100L), 1.0e-12);
        var receipt = A0061A0080ReservationReceiptContext.take("a", "anything");
        assertTrue(receipt.opportunityReserved());
        assertTrue(state.commitOpportunity("a", "hit", 30_101L));
        assertEquals(1.0D, A0061A0080CombatPolicy.consumeOpportunityDamageMultiplier("a", "second", opportunity, state, 30_102L), 1.0e-12);
    }

    @Test
    void stateExpiryRollbackAndCleanupRemainBoundedAndActorScoped() {
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.claimOnce("a", "e", "c", 1_000L));
        assertFalse(state.claimOnce("a", "e", "c", 1_001L));
        assertTrue(state.claimOnce("a", "e", "c", 31_001L));

        assertTrue(state.armOpportunity("a", 40_000L));
        assertTrue(state.reserveOpportunity("a", "root", 40_100L));
        state.rollbackOpportunity("a", "wrong");
        assertFalse(state.reserveOpportunity("a", "other", 40_101L));
        state.rollbackOpportunity("a", "root");
        assertTrue(state.reserveOpportunity("a", "other", 40_102L));
        assertFalse(state.commitOpportunity("a", "wrong", 40_103L));
        assertTrue(state.commitOpportunity("a", "other", 40_104L));
        assertFalse(state.armOpportunity("a", 40_105L));
        assertTrue(state.armOpportunity("a", 45_105L));

        state.recordConfirmedAttack("a", "target", 50_000L);
        state.recordConfirmedAttack("b", "target", 50_000L);
        state.clearTarget("target");
        state.clearActor("a");
        state.clearAll();
        assertEquals(A0061A0080CombatState.Stance.NONE, state.stance("a"));
    }
}
