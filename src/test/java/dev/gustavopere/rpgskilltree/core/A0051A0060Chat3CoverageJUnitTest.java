package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class A0051A0060Chat3CoverageJUnitTest {
    @Test
    void crossbowHitReceiptsPreserveWeaponIdentityAndExpireBoundedly() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        assertFalse(state.consumeCrossbowHitReceipt("p", "xbow-a", 8_000L, 1_000L));

        state.recordCrossbowHit("p", "root-1", "xbow-a", 1_000L);
        assertFalse(state.consumeCrossbowHitReceipt("p", "xbow-b", 8_000L, 1_100L));
        assertTrue(state.consumeCrossbowHitReceipt("p", "xbow-a", 8_000L, 1_101L));
        assertFalse(state.consumeCrossbowHitReceipt("p", "xbow-a", 8_000L, 1_102L));

        state.recordCrossbowHit("p", "root-2", "xbow-a", 2_000L);
        assertFalse(state.consumeCrossbowHitReceipt("p", "xbow-a", 8_000L, 10_001L));
        assertFalse(state.consumeCrossbowHitReceipt("p", "xbow-a", 8_000L, 10_002L));

        state.recordCrossbowHit("p", "root-3", "xbow-a", 11_000L);
        state.clearCrossbowHitReceipt("p");
        assertFalse(state.consumeCrossbowHitReceipt("p", "xbow-a", 8_000L, 11_001L));
    }

    @Test
    void multishotArbiterRejectsUnknownDuplicateAndTerminalCallbacks() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        long now = 20_000L;

        assertFalse(state.recordCrossbowProjectileSuccess("p", "missing", "a", now));
        assertFalse(state.recordCrossbowProjectileFailure("p", "missing", "a", now));
        assertFalse(state.sealCrossbowRoot("p", "missing", now));

        assertTrue(state.registerCrossbowProjectile("p", "root-success", "a", now));
        assertFalse(state.registerCrossbowProjectile("p", "root-success", "a", now + 1));
        assertFalse(state.recordCrossbowProjectileFailure("p", "root-success", "unknown", now + 2));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "root-success", "unknown", now + 3));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "root-success", "a", now + 4));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "root-success", "a", now + 5));
        assertFalse(state.registerCrossbowProjectile("p", "root-success", "b", now + 6));
        assertFalse(state.recordCrossbowProjectileFailure("p", "root-success", "a", now + 7));
        assertFalse(state.sealCrossbowRoot("p", "root-success", now + 8));

        assertTrue(state.registerCrossbowProjectile("p", "root-fail", "c", now + 100));
        assertFalse(state.recordCrossbowProjectileFailure("p", "root-fail", "c", now + 101));
        assertTrue(state.sealCrossbowRoot("p", "root-fail", now + 102));
        assertFalse(state.registerCrossbowProjectile("p", "root-fail", "d", now + 103));
        assertFalse(state.recordCrossbowProjectileFailure("p", "root-fail", "c", now + 104));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "root-fail", "c", now + 105));
        assertFalse(state.sealCrossbowRoot("p", "root-fail", now + 106));
    }

    @Test
    void multishotOutcomeExpiresAndCannotBeResurrectedByLateCallbacks() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        assertTrue(state.registerCrossbowProjectile("p", "old", "a", 1_000L));
        state.pruneTransient(31_001L);
        assertFalse(state.sealCrossbowRoot("p", "old", 31_002L));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "old", "a", 31_003L));
        assertFalse(state.recordCrossbowProjectileFailure("p", "old", "a", 31_004L));
    }

    @Test
    void piercingBoltReservationSupportsRollbackExpiryAndInsufficientCadence() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        assertFalse(state.reservePiercingBolt("p", "r0", 1_000L));

        state.addCadence("p");
        state.addCadence("p");
        assertTrue(state.reservePiercingBolt("p", "r1", 1_000L));
        assertFalse(state.reservePiercingBolt("p", "r1", 1_001L));
        state.discardPiercingBolt("p", "r1");
        assertFalse(state.commitPiercingBolt("p", "r1", 1_002L));
        assertEquals(2, state.cadence("p"));

        assertTrue(state.reservePiercingBolt("p", "r2", 2_000L));
        assertFalse(state.commitPiercingBolt("p", "r2", 2_501L));
        assertEquals(2, state.cadence("p"));

        assertTrue(state.reservePiercingBolt("p", "r3", 3_000L));
        assertEquals(2, state.consumeCadence("p", 2));
        assertFalse(state.commitPiercingBolt("p", "r3", 3_100L));
        assertEquals(0, state.cadence("p"));
    }

    @Test
    void adjustedMechanismReservationIsSingleRootTransactionalAndBounded() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");

        state.armAdjustedMechanism("p", 2_000L, 10_000L);
        assertTrue(state.reserveAdjustedMechanism("p", "root-a", 10_100L));
        assertFalse(state.reserveAdjustedMechanism("p", "root-b", 10_101L));
        assertFalse(state.consumeAdjustedMechanism("p", 10_102L));
        state.discardAdjustedMechanism("p", "root-b");
        assertFalse(state.reserveAdjustedMechanism("p", "root-b", 10_103L));
        state.discardAdjustedMechanism("p", "root-a");
        assertTrue(state.reserveAdjustedMechanism("p", "root-b", 10_104L));
        assertTrue(state.commitAdjustedMechanism("p", "root-b", 10_105L));
        assertEquals(0, state.cadence("p"));
        assertFalse(state.commitAdjustedMechanism("p", "root-b", 10_106L));

        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");
        state.armAdjustedMechanism("p", 2_000L, 20_000L);
        assertTrue(state.reserveAdjustedMechanism("p", "root-expired-reservation", 20_100L));
        assertFalse(state.commitAdjustedMechanism("p", "root-expired-reservation", 20_601L));
        assertEquals(3, state.cadence("p"));

        state.armAdjustedMechanism("p", 100L, 30_000L);
        assertFalse(state.reserveAdjustedMechanism("p", "root-expired-window", 30_101L));
        assertFalse(state.consumeAdjustedMechanism("p", 30_102L));
    }

    @Test
    void adjustedMechanismLegacyWindowAndLowCadenceBranchesStaySafe() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        state.armAdjustedMechanism("p", 1_000L, 1_000L);
        assertFalse(state.reserveAdjustedMechanism("p", "root", 1_100L));
        assertTrue(state.consumeAdjustedMechanism("p", 1_101L));
        assertFalse(state.consumeAdjustedMechanism("p", 1_102L));
    }

    @Test
    void rankReconciliationRemovesOnlyStateNoLongerOwned() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");
        state.recordCrossbowHit("p", "hit", "xbow", 1_000L);
        assertTrue(state.reservePiercingBolt("p", "pierce", 1_000L));
        state.armAdjustedMechanism("p", 2_000L, 1_000L);
        assertTrue(state.reserveAdjustedMechanism("p", "adjust", 1_001L));

        CombatPerkRanks cadenceWithoutDescendants = CombatPerkRanks.of(Map.of(
            "A0050", 2, "A0051", 2, "A0052", 1
        ));
        state.reconcileForRanks("p", cadenceWithoutDescendants, 1_100L);
        assertEquals(3, state.cadence("p"));
        assertFalse(state.commitPiercingBolt("p", "pierce", 1_101L));
        assertFalse(state.commitAdjustedMechanism("p", "adjust", 1_102L));
        assertTrue(state.consumeCrossbowHitReceipt("p", "xbow", 8_000L, 1_103L));

        state.addSequence("p", 2, 2_000L);
        state.startFinalCombinationCooldown("p", 5_000L, 2_000L);
        CombatPerkRanks sequenceWithoutCapstone = CombatPerkRanks.of(Map.of("A0057", 2, "A0058", 1));
        state.reconcileForRanks("p", sequenceWithoutCapstone, 2_100L);
        assertEquals(1, state.sequence("p", 2_100L));
        assertTrue(state.finalCombinationReady("p", 2_100L));
    }

    @Test
    void rankReconciliationPreservesOwnedAdjustedMechanismAndCapstoneCooldown() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");
        state.armAdjustedMechanism("p", 2_000L, 1_000L);
        assertTrue(state.reserveAdjustedMechanism("p", "adjust", 1_001L));
        state.addSequence("p", 2, 1_000L);
        state.startFinalCombinationCooldown("p", 5_000L, 1_000L);

        CombatPerkRanks owned = CombatPerkRanks.of(Map.of(
            "A0050", 2, "A0051", 2, "A0052", 2, "A0053", 1, "A0054", 1,
            "A0057", 2, "A0058", 1, "A0060", 1
        ));
        state.reconcileForRanks("p", owned, 1_100L);
        assertTrue(state.commitAdjustedMechanism("p", "adjust", 1_101L));
        assertEquals(0, state.cadence("p"));
        assertEquals(1, state.sequence("p", 1_101L));
        assertFalse(state.finalCombinationReady("p", 1_101L));
    }

    @Test
    void clearActorAndClearAllRemoveTransientOwnershipWithoutTouchingOtherActorFirst() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("q");
        state.registerCrossbowProjectile("p", "root-p", "arrow-p", 1_000L);
        state.registerCrossbowProjectile("q", "root-q", "arrow-q", 1_000L);
        assertTrue(state.claimOnce("p", "claim", "consumer", 1_000L));

        state.clearActor("p");
        assertEquals(0, state.cadence("p"));
        assertEquals(1, state.cadence("q"));
        assertFalse(state.sealCrossbowRoot("p", "root-p", 1_001L));
        assertTrue(state.claimOnce("p", "claim", "consumer", 1_001L));
        assertFalse(state.sealCrossbowRoot("q", "root-q", 1_001L));

        state.clearAll();
        assertEquals(0, state.cadence("q"));
        assertFalse(state.sealCrossbowRoot("q", "root-q", 1_002L));
    }

    @Test
    void cadenceAndIdentifiersRejectInvalidInputsWithoutCreatingState() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        assertThrows(IllegalArgumentException.class, () -> state.consumeCadence("p", -1));
        assertThrows(IllegalArgumentException.class, () -> state.registerCrossbowProjectile("p", " ", "a", 1L));
        assertThrows(IllegalArgumentException.class, () -> state.recordCrossbowHit("p", "root", " ", 1L));
        assertThrows(NullPointerException.class, () -> state.reservePiercingBolt(null, "root", 1L));
        assertEquals(0, state.cadence("p"));
    }

    @Test
    void policyRollbacksAndRankLossNeverSpendReservedCadence() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        CombatPerkRanks piercing = CombatPerkRanks.of(Map.of("A0053", 2));
        assertTrue(A0041A0060CombatPolicy.tryPiercingBolt(
            "p", "bolt-rollback", piercing, state, true, true, false, 1_000L
        ).applied());
        A0041A0060CombatPolicy.rollbackPiercingBolt("p", "bolt-rollback", state);
        assertFalse(A0041A0060CombatPolicy.commitPiercingBolt(
            "p", "bolt-rollback", piercing, state, 1_001L
        ));
        assertEquals(2, state.cadence("p"));

        assertTrue(A0041A0060CombatPolicy.tryPiercingBolt(
            "p", "bolt-rank-loss", piercing, state, true, true, false, 2_000L
        ).applied());
        assertFalse(A0041A0060CombatPolicy.commitPiercingBolt(
            "p", "bolt-rank-loss", CombatPerkRanks.empty(), state, 2_001L
        ));
        assertEquals(2, state.cadence("p"));
    }

    @Test
    void policyAdjustedMechanismGatesRollbackAndRankLossAreTransactional() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        CombatPerkRanks capstone = CombatPerkRanks.of(Map.of("A0054", 1));
        assertFalse(A0041A0060CombatPolicy.armAdjustedMechanismOnReload(
            "p", capstone, state, 79, true, 1_000L
        ));
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");
        assertFalse(A0041A0060CombatPolicy.armAdjustedMechanismOnReload(
            "p", capstone, state, 80, false, 1_001L
        ));
        assertTrue(A0041A0060CombatPolicy.armAdjustedMechanismOnReload(
            "p", capstone, state, 80, true, 1_002L
        ));
        assertTrue(A0041A0060CombatPolicy.tryAdjustedCrossbowShot(
            "p", "adjust-rollback", capstone, state, 1_003L
        ).applied());
        A0041A0060CombatPolicy.rollbackAdjustedCrossbowShot("p", "adjust-rollback", state);
        assertFalse(A0041A0060CombatPolicy.commitAdjustedCrossbowShot(
            "p", "adjust-rollback", capstone, state, 1_004L
        ));
        assertEquals(3, state.cadence("p"));

        assertTrue(A0041A0060CombatPolicy.tryAdjustedCrossbowShot(
            "p", "adjust-rank-loss", capstone, state, 1_005L
        ).applied());
        assertFalse(A0041A0060CombatPolicy.commitAdjustedCrossbowShot(
            "p", "adjust-rank-loss", CombatPerkRanks.empty(), state, 1_006L
        ));
        assertEquals(3, state.cadence("p"));
    }

    @Test
    void policyCrossbowHitAndReloadBranchesFailClosedUntilFactsAreProven() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        CombatPerkRanks cadence = CombatPerkRanks.of(Map.of("A0052", 2));
        A0041A0060CombatPolicy.recordCrossbowHit("p", "root-no-rank", "xbow", CombatPerkRanks.empty(), state, 1_000L);
        assertFalse(A0041A0060CombatPolicy.onCrossbowReloadComplete(
            "p", "xbow", cadence, state, true, 1_001L
        ));
        A0041A0060CombatPolicy.recordCrossbowHit("p", "root-blank", " ", cadence, state, 1_002L);
        assertFalse(A0041A0060CombatPolicy.onCrossbowReloadComplete(
            "p", "xbow", cadence, state, true, 1_003L
        ));
        A0041A0060CombatPolicy.recordCrossbowHit("p", "root", "xbow", cadence, state, 1_004L);
        assertFalse(A0041A0060CombatPolicy.onCrossbowReloadComplete(
            "p", "xbow", cadence, state, false, 1_005L
        ));
        assertTrue(A0041A0060CombatPolicy.onCrossbowReloadComplete(
            "p", "xbow", cadence, state, true, 1_006L
        ));
        assertEquals(1, state.cadence("p"));
        A0041A0060CombatPolicy.onCrossbowFailure("p", state);
        assertEquals(0, state.cadence("p"));
    }
}
