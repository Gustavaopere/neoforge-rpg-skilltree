package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class A0073A0080ReservationReceiptContextJUnitTest {
    @AfterEach
    void clearContext() {
        A0061A0080ReservationReceiptContext.clear();
    }

    @Test
    void projectilePolicyPublishesExactRootAndReservationFlags() {
        A0061A0080CombatState state = new A0061A0080CombatState();
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0073", 1, "A0074", 1));

        assertTrue(state.armExecution("p", "t", "opening-root", 1_000L));
        var execution = A0061A0080CombatPolicy.execution(
            "p", "t", "projectile-root", 0.50D, false, ranks, state, false, 1_100L
        );
        var firstBlood = A0061A0080CombatPolicy.firstBlood(
            "p", "t", "projectile-root", 0.90D, ranks, state, false, 1_100L
        );

        assertTrue(execution.applied());
        assertFalse(firstBlood.applied());
        var receipt = A0061A0080ReservationReceiptContext.take("p", "t");
        assertEquals("projectile-root", receipt.rootActionId());
        assertTrue(receipt.executionReserved());
        assertTrue(receipt.firstBloodTracked());
        assertEquals(A0061A0080CombatState.FirstBloodReservation.OPENER, receipt.firstBloodReservation());
        assertNull(A0061A0080ReservationReceiptContext.take("p", "t"));
    }

    @Test
    void receiptCannotBeTakenByDifferentActorOrTarget() {
        A0061A0080ReservationReceiptContext.begin("p", "t", "root");
        A0061A0080ReservationReceiptContext.markExecutionReserved();
        assertNull(A0061A0080ReservationReceiptContext.take("other", "t"));

        A0061A0080ReservationReceiptContext.begin("p", "t", "root2");
        A0061A0080ReservationReceiptContext.markExecutionReserved();
        assertNull(A0061A0080ReservationReceiptContext.take("p", "other-target"));
    }
}
