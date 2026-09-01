package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Contract tests for the exact A0031-A0040 lot after Chat 3 reconciliation. */
final class A0031A0040ImplementationContractJUnitTest {
    @Test
    void maceAndScytheMasteryAreFiniteDistinctTypeDiscovery() {
        for (var family : new WeaponFamily[]{WeaponFamily.MACE, WeaponFamily.SCYTHE}) {
            assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
                family, true, true, 4.0D, "repeat-hit").isEmpty());
            String entityType = family == WeaponFamily.MACE ? "minecraft:zombie" : "minecraft:skeleton";
            var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
                family, true, true, 4.0D, entityType, true);
            assertEquals(1, first.size());
            assertEquals(10, first.getFirst().experience());
            assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
                family, true, true, 4.0D, entityType, false).isEmpty());
        }
    }

    @Test
    void a0035CommitsTraumaAndSunderOnlyAfterConfirmedDamage() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2));
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, i);
        var facts = maceFacts("a0035-root", false, true, false, 1_000L);

        var prepared = A0021A0040CombatPolicy.beforeHit(facts, ranks, state, 80);
        assertTrue(prepared.applyArmorSunder());
        assertEquals(3, state.trauma("player", "target", 1_000L));
        assertFalse(state.isSundered("player", "target", 1_000L));

        var committed = A0021A0040CombatPolicy.afterConfirmedHit(facts, ranks, state);
        assertTrue(committed.armorSunderCommitted());
        assertEquals(0, state.trauma("player", "target", 1_000L));
        assertTrue(state.isSundered("player", "target", 1_000L));
    }

    @Test
    void a0036RequiresSunderToPreexistCurrentRootAndCommitsCooldownInPost() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2, "A0036", 1));
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, i);

        var sameRoot = A0021A0040CombatPolicy.beforeHit(
            maceFacts("same-root", true, true, false, 1_000L), ranks, state, 80);
        assertTrue(sameRoot.applyArmorSunder());
        assertFalse(sameRoot.applyBonebreaker());
        A0021A0040CombatPolicy.afterConfirmedHit(
            maceFacts("same-root", true, true, false, 1_000L), ranks, state);

        var nextRootFacts = maceFacts("next-root", true, true, false, 1_001L);
        var nextRoot = A0021A0040CombatPolicy.beforeHit(nextRootFacts, ranks, state, 80);
        assertTrue(nextRoot.applyBonebreaker());
        assertTrue(state.bonebreakerReady("player", "target", 1_001L));
        var committed = A0021A0040CombatPolicy.afterConfirmedHit(nextRootFacts, ranks, state);
        assertTrue(committed.bonebreakerCommitted());
        assertFalse(state.bonebreakerReady("player", "target", 1_001L));
    }

    @Test
    void bossScalingAndA0040BoundedLifecycleRemainExact() {
        var bossState = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2));
        for (int i = 0; i < 3; i++) bossState.addTrauma("player", "boss", 2, i);
        var bossFacts = new A0021A0040CombatPolicy.HitFacts(
            "player", "boss", "boss-root", WeaponFamily.MACE,
            true, true, true, false, false, false, false, true,
            false, true, true, true, 0.75D, true, 1_000L);
        assertEquals(0.06D, A0021A0040CombatPolicy.beforeHit(bossFacts, ranks, bossState, 80).armorSunderFraction(), 1.0E-9D);

        var reapState = new A0021A0040CombatState();
        reapState.applyReapingMark("player", "target", 1, 0.75D, 0L);
        assertEquals(0, reapState.pruneExpiredReapingMarks(7_999L));
        assertEquals(1, reapState.pruneExpiredReapingMarks(8_000L));
        assertEquals(0, reapState.pruneExpiredReapingMarks(9_000L));
    }

    private static A0021A0040CombatPolicy.HitFacts maceFacts(
        String root,
        boolean heavyConfirmed,
        boolean actualDamage,
        boolean boss,
        long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player", "target", root, WeaponFamily.MACE,
            true, true, actualDamage, false, false, false, heavyConfirmed,
            true, false, true, true, true, 0.75D, boss, now);
    }
}
