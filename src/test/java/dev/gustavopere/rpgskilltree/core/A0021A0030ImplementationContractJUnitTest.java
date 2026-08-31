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
        assertEquals(3, state.flow("player", 3_000L));
        state.tickFlow("player", false, 4_000L);
        assertEquals(2, state.flow("player", 4_000L));
    }

    @Test
    void a0022StrongHostileStaggerRemovesTwoFlow() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2));
        for (int i = 0; i < 4; i++) state.addFlow("player", 2, 0L);
        A0021A0040CombatPolicy.onConfirmedHeavyStagger("player", ranks, state, 1_000L);
        assertEquals(2, state.flow("player", 1_000L));
    }

    @Test
    void a0022FallbackGeometryUsesServerPositionsAndCanBeInvalidated() {
        var state = new A0021A0040CombatState();
        assertFalse(state.sampleFallbackReposition("player", "target", 2.0D, 0.0D, 0.0D, 0.0D, 1_000L));
        assertTrue(state.sampleFallbackReposition("player", "target", 0.0D, 2.0D, 0.0D, 0.0D, 1_100L));
        assertTrue(state.repositionActive("player", 1_100L));
        state.invalidateFallbackReposition("player");
        assertFalse(state.repositionActive("player", 1_100L));
    }

    @Test
    void a0022KnockbackSuppressesSamplingUntilForcedMotionSettles() {
        var state = new A0021A0040CombatState();
        state.beginForcedRepositionSuppression("player");
        assertTrue(state.fallbackRepositionSuppressed("player"));
        assertFalse(state.sampleFallbackReposition("player", "target", 2.0D, 0.0D, 0.0D, 0.0D, 1_000L));
        for (int tick = 0; tick < 4; tick++) assertTrue(state.updateForcedRepositionSuppression("player", 0.04D));
        assertFalse(state.sampleFallbackReposition("player", "target", 0.0D, 2.0D, 0.0D, 0.0D, 1_200L));
        assertTrue(state.updateForcedRepositionSuppression("player", 0.0D));
        assertTrue(state.updateForcedRepositionSuppression("player", 0.0D));
        assertFalse(state.updateForcedRepositionSuppression("player", 0.0D));
        assertFalse(state.fallbackRepositionSuppressed("player"));
        assertFalse(state.sampleFallbackReposition("player", "target", 2.0D, 0.0D, 0.0D, 0.0D, 1_400L));
        assertTrue(state.sampleFallbackReposition("player", "target", 0.0D, 2.0D, 0.0D, 0.0D, 1_500L));
    }

    @Test
    void a0023ReservesFlowAndCooldownUntilEffectiveDamagePost() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0023", 2));
        state.addFlow("player", 2, 0L);
        state.addFlow("player", 2, 0L);

        var cancelledPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("a0023-cancel", true, true, false, true, 1_000L), ranks, state, 0);
        assertEquals(1.25D, cancelledPre.damageMultiplier(), EPSILON);
        assertEquals(0.10D, cancelledPre.physicalPenetrationFraction(), EPSILON);
        assertEquals(4, state.flow("player", 1_000L));
        assertTrue(state.blindSpotReady("player", "target", 1_000L));
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0023-cancel", false, true, false, true, 1_050L), ranks, state);
        assertEquals(4, state.flow("player", 1_050L));
        assertTrue(state.blindSpotReady("player", "target", 1_050L));

        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("a0023-confirm", true, true, false, true, 1_100L), ranks, state, 0);
        assertEquals(1.25D, confirmedPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0023-confirm", true, true, false, true, 1_150L), ranks, state);
        assertEquals(2, state.flow("player", 1_150L));
        assertFalse(state.blindSpotReady("player", "target", 1_150L));
    }

    @Test
    void a0024ActivationAndFirstHitCommitOnlyAfterEffectiveDamagePost() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2, "A0024", 1));
        for (int i = 0; i < 4; i++) state.addFlow("player", 2, 0L);
        A0021A0040CombatPolicy.onConfirmedDodge("player", ranks, state, 100L);

        var cancelledPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("a0024-cancel", true, false, true, true, 101L), ranks, state, 90);
        assertEquals(1.15D, cancelledPre.damageMultiplier(), EPSILON);
        assertEquals(1.20D, cancelledPre.impactMultiplier(), EPSILON);
        assertEquals(4, state.flow("player", 101L));
        assertFalse(state.consumeDanceMove("player", 102L));
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0024-cancel", false, false, true, true, 102L), ranks, state);
        assertEquals(4, state.flow("player", 102L));

        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("a0024-confirm", true, false, true, true, 103L), ranks, state, 90);
        assertEquals(1.15D, confirmedPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0024-confirm", true, false, true, true, 104L), ranks, state);
        assertEquals(1, state.flow("player", 104L),
            "A0024 commits its four-Flow cost before the same confirmed reposition hit grants A0022 Flow");
        assertTrue(state.consumeDanceMove("player", 105L));
        assertFalse(state.consumeDanceHit("player", 105L));
    }

    @Test
    void a0024ExistingDanceHitReservationRollsBackOnCancelledHit() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0024", 1));
        state.activateDance("player", 90, 0L);
        var firstPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("dance-hit-cancel", true, false, false, true, 100L), ranks, state, 90);
        assertEquals(1.15D, firstPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("dance-hit-cancel", false, false, false, true, 110L), ranks, state);
        var secondPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("dance-hit-confirm", true, false, false, true, 120L), ranks, state, 90);
        assertEquals(1.15D, secondPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("dance-hit-confirm", true, false, false, true, 130L), ranks, state);
        assertFalse(state.consumeDanceHit("player", 131L));
    }

    @Test
    void a0025AwardsTenXpOnlyForANewProviderNativeHostileType() {
        var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", true);
        assertEquals(1, first.size());
        assertEquals("epicfight:heavy", first.getFirst().laneId());
        assertEquals(10, first.getFirst().experience());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", false).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, false, true, 4.0D, "minecraft:skeleton", true).isEmpty());
        assertTrue(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.DAGGER, true, true, 4.0D, "minecraft:skeleton", true).isEmpty());
    }

    @Test
    void a0025DiscoveryKeyIsStablePerEntityType() {
        assertEquals("mastery/epicfight:heavy/entity_type/minecraft:zombie",
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
        assertEquals(3, state.abalo("player", "target", 1_000L));
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
        assertEquals(3, state.abalo("player", "target", 1_000L));
    }

    @Test
    void a0029ReservesAbaloUntilEffectiveDamagePostOnceHeavyExists() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0029", 2));
        for (int i = 0; i < 3; i++) state.addAbalo("player", "target", 0L);
        var cancelledPre = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0029-cancel", true, true, true, 1_000L), ranks, state, 80);
        assertEquals(1.45D, cancelledPre.guardPressureMultiplier(), EPSILON);
        assertEquals(1.15D, cancelledPre.impactMultiplier(), EPSILON);
        assertEquals(3, state.abalo("player", "target", 1_000L));
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0029-cancel", true, true, true, false, 1_050L), ranks, state);
        assertEquals(3, state.abalo("player", "target", 1_050L));
        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0029-confirm", true, true, true, 1_100L), ranks, state, 80);
        assertEquals(1.45D, confirmedPre.guardPressureMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0029-confirm", true, true, true, true, 1_150L), ranks, state);
        assertEquals(0, state.abalo("player", "target", 1_150L));
    }

    @Test
    void a0030CannotActivateWithoutAConfirmedGuardBreakReceipt() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0030", 1));
        var result = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0030", true, true, true, 1_000L), ranks, state, 80);
        assertEquals(1.0D, result.damageMultiplier(), EPSILON);
        assertEquals(1.0D, result.impactMultiplier(), EPSILON);
    }

    @Test
    void a0030DemolitionWindowCommitsOnlyAfterEffectiveDamagePost() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0030", 1));
        A0021A0040CombatPolicy.onConfirmedGuardBreak("player", "target", ranks, state, 80, 100L);
        var cancelledPre = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0030-cancel", true, false, true, 200L), ranks, state, 80);
        assertEquals(1.20D, cancelledPre.damageMultiplier(), EPSILON);
        assertEquals(1.25D, cancelledPre.impactMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0030-cancel", true, false, true, false, 250L), ranks, state);
        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0030-confirm", true, false, true, 300L), ranks, state, 80);
        assertEquals(1.20D, confirmedPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0030-confirm", true, false, true, true, 350L), ranks, state);
        var afterCommit = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0030-after", true, false, true, 400L), ranks, state, 80);
        assertEquals(1.0D, afterCommit.damageMultiplier(), EPSILON);
    }

    private static A0021A0040CombatPolicy.HitFacts daggerFacts(
        String root, boolean actualDamage, boolean critical, boolean reposition, boolean flank, long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player", "target", root, WeaponFamily.DAGGER, true, true, actualDamage,
            critical, reposition, flank, false, false, false, true, true, false, 0.75D, false, now
        );
    }

    private static A0021A0040CombatPolicy.HitFacts hammerFacts(
        String root, boolean heavyConfirmed, boolean guardPressureAvailable, boolean impactAvailable, long now
    ) {
        return hammerFacts(root, heavyConfirmed, guardPressureAvailable, impactAvailable, true, now);
    }

    private static A0021A0040CombatPolicy.HitFacts hammerFacts(
        String root, boolean heavyConfirmed, boolean guardPressureAvailable, boolean impactAvailable,
        boolean actualDamage, long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player", "target", root, WeaponFamily.HAMMER, true, true, actualDamage,
            false, false, false, heavyConfirmed, true, guardPressureAvailable, impactAvailable,
            true, true, 0.75D, false, now
        );
    }
}
