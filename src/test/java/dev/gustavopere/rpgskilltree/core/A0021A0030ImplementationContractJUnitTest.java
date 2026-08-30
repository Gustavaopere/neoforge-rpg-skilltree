package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0021A0030ImplementationContractJUnitTest {
    private static final double EPSILON = 1.0E-9D;

    @Test
    void a0022IdleDecayDoesNotRequireALiveCombatTarget() {
        var state = new A0021A0040CombatState();
        for (int i = 0; i < 4; i++) state.addFlow("player", 2, 0L);
        state.recordHorizontalMovement("player", 0L);

        state.tickFlow("player", false, 2_999L);
        assertEquals(4, state.flow("player", 2_999L));

        state.tickFlow("player", false, 3_000L);
        assertEquals(3, state.flow("player", 3_000L),
            "A0022 must start losing one Flow per second after 3 s idle even without a target");

        state.tickFlow("player", false, 4_000L);
        assertEquals(2, state.flow("player", 4_000L));
    }

    @Test
    void a0022StrongHostileStaggerRemovesTwoFlow() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2));
        for (int i = 0; i < 4; i++) state.addFlow("player", 2, 0L);

        A0021A0040CombatPolicy.onConfirmedHeavyStagger("player", ranks, state, 1_000L);

        assertEquals(2, state.flow("player", 1_000L),
            "the provider-native LONG/KNOCKDOWN/NEUTRALIZE receipt must remove exactly two Flow");
    }

    @Test
    void a0022FallbackGeometryUsesServerPositionsAndCanBeInvalidated() {
        var state = new A0021A0040CombatState();

        assertFalse(state.sampleFallbackReposition(
            "player", "target", 2.0D, 0.0D, 0.0D, 0.0D, 1_000L),
            "the first sample establishes the server-side baseline only");
        assertTrue(state.sampleFallbackReposition(
            "player", "target", 0.0D, 2.0D, 0.0D, 0.0D, 1_100L),
            "a >1.5 block displacement with a 90 degree target-relative angle change must qualify");
        assertTrue(state.repositionActive("player", 1_100L));

        state.invalidateFallbackReposition("player");
        assertFalse(state.repositionActive("player", 1_100L),
            "teleport/knockback invalidation must cancel the geometric receipt");
    }

    @Test
    void a0025AwardsTenXpOnlyForANewProviderNativeHostileType() {
        var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", true);
        assertEquals(1, first.size());
        assertEquals("epicfight:heavy", first.getFirst().laneId());
        assertEquals(10, first.getFirst().experience());

        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", false).isEmpty(),
            "repeating the same hostile entity type must grant zero mastery");
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, false, true, 4.0D, "minecraft:skeleton", true).isEmpty(),
            "indirect damage must not grant A0025 mastery");
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.DAGGER, true, true, 4.0D, "minecraft:skeleton", true).isEmpty(),
            "A0025 discovery must stay on the provider-native HAMMER family");
    }

    @Test
    void a0025DiscoveryKeyIsStablePerEntityType() {
        assertEquals(
            "mastery/epicfight:heavy/entity_type/minecraft:zombie",
            A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.HAMMER, "minecraft:zombie").orElseThrow());
        assertTrue(A0021A0040MasteryPolicy.discoveryKey(WeaponFamily.DAGGER, "minecraft:zombie").isEmpty());
    }

    @Test
    void a0028DoesNotSubstituteDamageOrImpactWhenGuardPressureIsUnavailable() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0028", 2));
        for (int i = 0; i < 3; i++) state.addAbalo("player", "target", 0L);

        var result = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0028", false, false, true, 1_000L), ranks, state, 0);

        assertEquals(1.0D, result.damageMultiplier(), EPSILON);
        assertEquals(1.0D, result.impactMultiplier(), EPSILON);
        assertEquals(1.0D, result.guardPressureMultiplier(), EPSILON);
        assertEquals(3, state.abalo("player", "target", 1_000L),
            "A0028 charges remain state-only when no safe guard-pressure receipt exists");
    }

    @Test
    void a0029CannotConsumeAbaloWithoutAConfirmedHeavyReceipt() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0029", 2));
        for (int i = 0; i < 3; i++) state.addAbalo("player", "target", 0L);

        var result = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0029", false, true, true, 1_000L), ranks, state, 0);

        assertEquals(1.0D, result.guardPressureMultiplier(), EPSILON);
        assertEquals(1.0D, result.impactMultiplier(), EPSILON);
        assertEquals(3, state.abalo("player", "target", 1_000L),
            "A0029 must remain fail-closed until the provider proves the hit was heavy");
    }

    @Test
    void a0030CannotActivateWithoutAConfirmedGuardBreakReceipt() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0030", 1));

        var result = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0030", true, true, true, 1_000L), ranks, state, 80);

        assertEquals(1.0D, result.damageMultiplier(), EPSILON);
        assertEquals(1.0D, result.impactMultiplier(), EPSILON,
            "heavy alone is insufficient; A0030 also requires a prior causal guard-break receipt");
    }

    private static A0021A0040CombatPolicy.HitFacts hammerFacts(
        String root,
        boolean heavyConfirmed,
        boolean guardPressureAvailable,
        boolean impactAvailable,
        long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player",
            "target",
            root,
            WeaponFamily.HAMMER,
            true,
            true,
            true,
            false,
            false,
            false,
            heavyConfirmed,
            true,
            guardPressureAvailable,
            impactAvailable,
            true,
            true,
            0.75D,
            false,
            now
        );
    }
}
