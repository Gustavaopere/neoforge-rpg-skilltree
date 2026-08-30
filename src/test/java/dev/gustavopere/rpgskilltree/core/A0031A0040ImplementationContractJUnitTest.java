package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0031A0040ImplementationContractJUnitTest {
    @Test
    void a0031MaceMasteryUsesFiniteDistinctHostileTypeDiscovery() {
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.MACE, true, true, 4.0D, "repeat-hit").isEmpty(),
            "MACE mastery must not be farmable through repeatable damage hits");

        var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, true, true, 4.0D, "minecraft:zombie", true);
        assertEquals(1, first.size());
        assertEquals("combat:mace", first.getFirst().laneId());
        assertEquals(10, first.getFirst().experience());
        assertEquals(
            "mastery/combat:mace/entity_type/minecraft:zombie",
            A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.MACE, "minecraft:zombie").orElseThrow());

        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, true, true, 4.0D, "minecraft:zombie", false).isEmpty(),
            "repeating the same hostile type must grant zero combat:mace mastery");
    }

    @Test
    void a0037ScytheMasteryUsesFiniteDistinctHostileTypeDiscovery() {
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.SCYTHE, true, true, 4.0D, "repeat-hit").isEmpty(),
            "SCYTHE mastery must not be farmable through repeatable damage hits");

        var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.SCYTHE, true, true, 4.0D, "minecraft:skeleton", true);
        assertEquals(1, first.size());
        assertEquals("combat:scythe", first.getFirst().laneId());
        assertEquals(10, first.getFirst().experience());
        assertEquals(
            "mastery/combat:scythe/entity_type/minecraft:skeleton",
            A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.SCYTHE, "minecraft:skeleton").orElseThrow());
    }

    @Test
    void a0035CommitsTraumaAndSunderOnlyAfterConfirmedDamage() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2));
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, 0L);
        var facts = maceFacts("a0035-root", false, true, false, 1_000L);

        var prepared = A0021A0040CombatPolicy.beforeHit(facts, ranks, state, 80);
        assertTrue(prepared.applyArmorSunder());
        assertEquals(3, state.trauma("player", "target", 1_000L),
            "PRE may prepare A0035 but must not consume Trauma");
        assertFalse(state.isSundered("player", "target", 1_000L),
            "PRE must not claim Sundered before the root action deals real damage");

        A0021A0040CombatPolicy.afterConfirmedHit(facts, ranks, state);
        assertEquals(0, state.trauma("player", "target", 1_000L));
        assertTrue(state.isSundered("player", "target", 1_000L),
            "the same confirmed root action must commit Trauma consumption and Sundered state");
    }

    @Test
    void a0036RequiresSunderToPreexistTheCurrentRootAction() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2, "A0036", 1));
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, 0L);

        var prepared = A0021A0040CombatPolicy.beforeHit(
            maceFacts("same-root", true, true, false, 1_000L), ranks, state, 80);

        assertTrue(prepared.applyArmorSunder(), "A0035 may be prepared by this root");
        assertFalse(prepared.applyBonebreaker(),
            "the same root must not create Armadura Fendida and immediately activate Quebra-Ossos");
    }

    @Test
    void a0036CooldownStartsOnlyAfterAConfirmedEligibleHit() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        state.markSundered("player", "target", 2, 0L);
        var facts = maceFacts("pre-sundered-root", true, true, false, 1_000L);

        var prepared = A0021A0040CombatPolicy.beforeHit(facts, ranks, state, 80);
        assertTrue(prepared.applyBonebreaker());
        assertTrue(state.bonebreakerReady("player", "target", 1_000L),
            "PRE must not start the irreversible per-target cooldown");

        A0021A0040CombatPolicy.afterConfirmedHit(facts, ranks, state);
        assertFalse(state.bonebreakerReady("player", "target", 1_000L),
            "a confirmed eligible root action must commit the cooldown");
    }

    @Test
    void a0040ExpiredMarksArePrunedWithoutRequeryingTheTarget() {
        var state = new A0021A0040CombatState();
        state.applyReapingMark("player", "target", 1, 0.75D, 0L);

        assertEquals(0, state.pruneExpiredReapingMarks(7_999L));
        assertEquals(1, state.pruneExpiredReapingMarks(8_000L),
            "bounded lifecycle pruning must remove an expired mark without consulting its UUID again");
        assertEquals(0, state.pruneExpiredReapingMarks(9_000L),
            "the same expired mark must not be removed twice");
    }

    private static A0021A0040CombatPolicy.HitFacts maceFacts(
        String root,
        boolean heavyConfirmed,
        boolean actualDamage,
        boolean boss,
        long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player",
            "target",
            root,
            WeaponFamily.MACE,
            true,
            true,
            actualDamage,
            false,
            false,
            false,
            heavyConfirmed,
            true,
            false,
            true,
            true,
            true,
            0.75D,
            boss,
            now
        );
    }
}
