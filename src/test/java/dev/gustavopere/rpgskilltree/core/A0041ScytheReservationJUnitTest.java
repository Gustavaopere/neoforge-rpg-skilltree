package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class A0041ScytheReservationJUnitTest {
    private static final CombatPerkRanks RANKS = CombatPerkRanks.of(Map.of("A0041", 2));

    @Test
    void preReservesAndPostCommitConsumesExactlyOnce() {
        A0021A0040CombatState legacy = matureMark("target", 1_000L);
        A0041A0060CombatState state = new A0041A0060CombatState();

        var first = A0041A0060CombatPolicy.scytheCut(
            "player", "target", "root-1", RANKS, legacy, state, 0.40D, true, 1_100L
        );
        assertTrue(first.applied());
        assertTrue(legacy.reapMarked("player", "target", 1_101L), "PRE must not mutate the mark");

        var concurrent = A0041A0060CombatPolicy.scytheCut(
            "player", "target", "root-2", RANKS, legacy, state, 0.40D, true, 1_110L
        );
        assertFalse(concurrent.applied(), "one mature mark cannot be reserved by two roots");

        assertTrue(A0041A0060CombatPolicy.commitScytheCut(
            "player", "target", "root-1", legacy, state, 0.35D, 1_120L
        ));
        assertFalse(legacy.reapMarked("player", "target", 1_121L), "confirmed POST consumes the mark");
        assertFalse(A0041A0060CombatPolicy.commitScytheCut(
            "player", "target", "root-1", legacy, state, 0.35D, 1_122L
        ), "the same root cannot commit twice");
    }

    @Test
    void rollbackPreservesMatureMarkForFutureRoot() {
        A0021A0040CombatState legacy = matureMark("target", 2_000L);
        A0041A0060CombatState state = new A0041A0060CombatState();

        assertTrue(A0041A0060CombatPolicy.scytheCut(
            "player", "target", "root-cancel", RANKS, legacy, state, 0.40D, true, 2_100L
        ).applied());
        A0041A0060CombatPolicy.rollbackScytheCut("player", "root-cancel", state);
        assertTrue(legacy.reapMarked("player", "target", 2_101L));

        assertTrue(A0041A0060CombatPolicy.scytheCut(
            "player", "target", "root-next", RANKS, legacy, state, 0.40D, true, 2_102L
        ).applied(), "rollback must release the target for a later root");
    }

    private static A0021A0040CombatState matureMark(String target, long now) {
        A0021A0040CombatState legacy = new A0021A0040CombatState();
        legacy.applyReapingMark("player", target, 2, 0.70D, now);
        assertTrue(legacy.reapMature("player", target, 0.40D, now + 50L));
        return legacy;
    }
}
