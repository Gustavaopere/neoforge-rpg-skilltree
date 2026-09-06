package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Edge coverage for the transactional and finite-discovery contracts of A0031-A0040. */
final class A0031A0040StateEdgeCoverageJUnitTest {
    @Test
    void sunderReservationsAreIdempotentExclusiveDiscardableAndExpiring() {
        var state = new A0021A0040CombatState();
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, i);

        assertTrue(state.prepareSunder("player", "target", "root-a", 2, 1_000L));
        assertTrue(state.prepareSunder("player", "target", "root-a", 2, 1_001L));
        assertEquals(0, state.availableTrauma("player", "target", 1_001L));
        assertFalse(state.prepareSunder("player", "target", "root-b", 2, 1_001L));

        state.discardPreparedSunder("player", "root-a");
        assertEquals(3, state.availableTrauma("player", "target", 1_002L));
        assertTrue(state.prepareSunder("player", "target", "root-b", 2, 1_002L));
        assertFalse(state.commitPreparedSunder("player", "other", "root-b", 1_003L));
        assertEquals(3, state.trauma("player", "target", 1_003L));

        assertTrue(state.prepareSunder("player", "target", "root-c", 2, 1_004L));
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, 31_000L + i);
        assertTrue(state.prepareSunder("player", "target", "root-d", 2, 31_005L));
    }

    @Test
    void maceReservationMismatchCleanupAndCooldownBranchesStayFailClosed() {
        var state = new A0021A0040CombatState();
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, i);
        state.markSundered("player", "target", 2, 0L);

        assertTrue(state.prepareSunder("player", "target", "shared-root", 2, 1_000L));
        assertFalse(state.prepareSunder("player", "target", "shared-root", 1, 1_001L));
        assertFalse(state.prepareSunder("player", "other", "shared-root", 2, 1_001L));

        assertTrue(state.prepareBonebreaker("player", "target", "shared-root", 80, 1_000L));
        assertFalse(state.prepareBonebreaker("player", "target", "shared-root", 81, 1_001L));
        assertFalse(state.prepareBonebreaker("player", "other", "shared-root", 80, 1_001L));

        state.clearTarget("target");
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, 2_000L + i);
        assertTrue(state.prepareSunder("player", "target", "shared-root", 2, 2_010L));
        assertTrue(state.prepareBonebreaker("player", "target", "shared-root", 80, 2_010L));
        state.discardPreparedMaceActions("player", "shared-root");
        assertTrue(state.prepareSunder("player", "target", "shared-root", 2, 2_011L));
        assertTrue(state.prepareBonebreaker("player", "target", "shared-root", 80, 2_011L));

        state.discardPreparedSunder("player", "shared-root");
        assertTrue(state.commitPreparedBonebreaker("player", "target", "shared-root", 2_012L));
        assertFalse(state.prepareBonebreaker("player", "target", "cooldown-root", 80, 2_013L));
    }

    @Test
    void bonebreakerReservationsCommitOnlyOnceAndStartCooldownOnlyOnCommit() {
        var state = new A0021A0040CombatState();
        assertTrue(state.prepareBonebreaker("player", "target", "root-a", 80, 1_000L));
        assertTrue(state.prepareBonebreaker("player", "target", "root-a", 80, 1_001L));
        assertFalse(state.prepareBonebreaker("player", "target", "root-b", 80, 1_001L));

        state.discardPreparedBonebreaker("player", "root-a");
        assertTrue(state.prepareBonebreaker("player", "target", "root-b", 80, 1_002L));
        assertFalse(state.commitPreparedBonebreaker("player", "other", "root-b", 1_003L));
        assertTrue(state.bonebreakerReady("player", "target", 1_003L));

        assertTrue(state.prepareBonebreaker("player", "target", "root-c", 80, 1_004L));
        assertTrue(state.commitPreparedBonebreaker("player", "target", "root-c", 1_005L));
        assertFalse(state.bonebreakerReady("player", "target", 1_005L));
        assertFalse(state.commitPreparedBonebreaker("player", "target", "root-c", 1_006L));
    }

    @Test
    void bossBonebreakerUsesTheApprovedReducedScale() {
        var state = new A0021A0040CombatState();
        state.markSundered("player", "boss", 2, 0L);
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        var facts = new A0021A0040CombatPolicy.HitFacts(
            "player", "boss", "boss-bone-root", WeaponFamily.MACE,
            true, true, true, false, false, false, true,
            true, false, true, true, true, 0.75D, true, 1_000L
        );

        var before = A0021A0040CombatPolicy.beforeHit(facts, ranks, state, 80);
        assertTrue(before.applyBonebreaker());
        assertEquals(0.96D, before.outgoingPhysicalDamageMultiplier(), 1.0E-9D);
        assertEquals(0.95D, before.movementSpeedMultiplier(), 1.0E-9D);
        assertTrue(A0021A0040CombatPolicy.afterConfirmedHit(facts, ranks, state).bonebreakerCommitted());
    }

    @Test
    void reapingMarkRequiresRealThresholdCrossingAndSupportsLifecycleCleanup() {
        var state = new A0021A0040CombatState();
        state.applyReapingMark("player", "target", 1, 0.40D, 0L);
        assertFalse(state.reapMature("player", "target", 0.30D, 1L));
        assertFalse(state.consumeMatureReap("player", "target", 0.20D, 2L));

        state.clearTarget("target");
        assertFalse(state.reapMarked("player", "target", 3L));

        state.applyReapingMark("player", "target", 1, 0.75D, 10L);
        state.applyReapingMark("other", "target", 1, 0.80D, 10L);
        state.updateReapingMaturityForTarget("target", 0.49D, 11L);
        assertTrue(state.reapMature("player", "target", 0.49D, 12L));
        assertTrue(state.reapMature("other", "target", 0.49D, 12L));
        assertTrue(state.consumeMatureReap("player", "target", 0.49D, 13L));
        assertFalse(state.reapMarked("player", "target", 13L));

        state.clearAll();
        assertFalse(state.reapMarked("other", "target", 14L));
    }

    @Test
    void reapingExpiryAndClaimPruningAreBoundedWithoutTargetRequery() {
        var state = new A0021A0040CombatState();
        state.applyReapingMark("player", "expired", 1, 0.75D, 0L);
        state.updateReapingMaturityForTarget("expired", 0.49D, 8_000L);
        assertFalse(state.reapMarked("player", "expired", 8_000L));

        assertTrue(state.claimOnce("player", "claim-root", "consumer", 0L));
        assertFalse(state.claimOnce("player", "claim-root", "consumer", 1L));
        state.pruneExpiredReapingMarks(30_000L);
        assertTrue(state.claimOnce("player", "claim-root", "consumer", 30_000L));
    }

    @Test
    void maceStatePartialConsumptionExpiryAndActorCleanupStayBounded() {
        var state = new A0021A0040CombatState();
        assertEquals(1, state.addTrauma("player", "target", 2, 0L));
        assertEquals(2, state.addTrauma("player", "target", 2, 1L));
        assertEquals(1, state.consumeTrauma("player", "target", 1, 2L));
        assertEquals(1, state.trauma("player", "target", 2L));
        assertEquals(1, state.consumeTrauma("player", "target", 99, 3L));
        assertEquals(0, state.trauma("player", "target", 3L));

        state.addTrauma("player", "expiring", 1, 0L);
        assertEquals(0, state.trauma("player", "expiring", 8_000L));

        state.markSundered("player", "target", 1, 10L);
        assertTrue(state.isSundered("player", "target", 11L));
        assertFalse(state.isSundered("player", "target", 8_010L));

        state.startBonebreakerCooldown("player", "target", 80, 20L);
        assertFalse(state.bonebreakerReady("player", "target", 21L));

        assertTrue(state.claimOnce("player", "actor-root", "consumer", 30L));
        state.clearActor("player");
        assertTrue(state.claimOnce("player", "actor-root", "consumer", 31L));
        assertTrue(state.bonebreakerReady("player", "target", 31L));
    }

    @Test
    void reapingRefreshCanMatureDuringApplyAndPruneReturnsExactCount() {
        var state = new A0021A0040CombatState();
        state.applyReapingMark("player", "target", 1, 0.75D, 0L);
        state.applyReapingMark("player", "target", 1, 0.49D, 1L);
        assertTrue(state.reapMature("player", "target", 0.49D, 2L));

        state.applyReapingMark("player", "expired-a", 1, 0.75D, 0L);
        state.applyReapingMark("other", "expired-b", 1, 0.75D, 0L);
        assertEquals(2, state.pruneExpiredReapingMarks(8_000L));
        assertFalse(state.reapMarked("player", "expired-a", 8_000L));
        assertFalse(state.reapMarked("other", "expired-b", 8_000L));
    }

    @Test
    void finiteDiscoveryRejectsInvalidOrRepeatedInputs() {
        assertTrue(A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.DAGGER, "minecraft:zombie").isEmpty());
        assertTrue(A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.MACE, "").isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, false, true, 4.0D, "minecraft:zombie", true).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, true, false, 4.0D, "minecraft:zombie", true).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, true, true, 0.0D, "minecraft:zombie", true).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, true, true, Double.NaN, "minecraft:zombie", true).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, true, true, 4.0D, "minecraft:zombie", false).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.DAGGER, true, true, 4.0D, "minecraft:zombie", true).isEmpty());
    }
}
