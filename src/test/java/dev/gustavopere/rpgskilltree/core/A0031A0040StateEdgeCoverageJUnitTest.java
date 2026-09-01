package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
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
        assertTrue(state.prepareSunder("player", "target", "root-d", 2, 31_005L));
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
