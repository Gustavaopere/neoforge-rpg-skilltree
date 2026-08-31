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
            "teleport invalidation must cancel the geometric receipt");
    }

    @Test
    void a0022KnockbackSuppressesSamplingUntilForcedMotionSettles() {
        var state = new A0021A0040CombatState();
        state.beginForcedRepositionSuppression("player");

        assertTrue(state.fallbackRepositionSuppressed("player"));
        assertFalse(state.sampleFallbackReposition(
            "player", "target", 2.0D, 0.0D, 0.0D, 0.0D, 1_000L),
            "knockback suppression must prevent even a new fallback baseline while forced motion is active");

        for (int tick = 0; tick < 4; tick++) {
            assertTrue(state.updateForcedRepositionSuppression("player", 0.04D),
                "non-trivial horizontal velocity must keep the forced-motion suppression active");
        }
        assertFalse(state.sampleFallbackReposition(
            "player", "target", 0.0D, 2.0D, 0.0D, 0.0D, 1_200L),
            "the displacement accumulated during knockback must never arm A0022");

        assertTrue(state.updateForcedRepositionSuppression("player", 0.0D));
        assertTrue(state.updateForcedRepositionSuppression("player", 0.0D));
        assertFalse(state.updateForcedRepositionSuppression("player", 0.0D),
            "three quiet server ticks release the conservative forced-motion suppression");
        assertFalse(state.fallbackRepositionSuppressed("player"));

        assertFalse(state.sampleFallbackReposition(
            "player", "target", 2.0D, 0.0D, 0.0D, 0.0D, 1_400L),
            "after suppression ends, the first voluntary sample establishes a fresh baseline");
        assertTrue(state.sampleFallbackReposition(
            "player", "target", 0.0D, 2.0D, 0.0D, 0.0D, 1_500L),
            "voluntary geometry after the fresh baseline must remain eligible");
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
        assertEquals(4, state.flow("player", 1_000L),
            "A0023 PRE may reserve two Flow but must not irreversibly consume them");
        assertTrue(state.blindSpotReady("player", "target", 1_000L),
            "A0023 target cooldown must not start before effective damage POST");

        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0023-cancel", false, true, false, true, 1_050L), ranks, state);
        assertEquals(4, state.flow("player", 1_050L),
            "cancelled/zero-damage A0023 must roll back its reservation");
        assertTrue(state.blindSpotReady("player", "target", 1_050L));

        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("a0023-confirm", true, true, false, true, 1_100L), ranks, state, 0);
        assertEquals(1.25D, confirmedPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0023-confirm", true, true, false, true, 1_150L), ranks, state);
        assertEquals(2, state.flow("player", 1_150L),
            "A0023 must consume exactly two Flow only after effective damage POST");
        assertFalse(state.blindSpotReady("player", "target", 1_150L),
            "A0023 cooldown starts only when the reservation commits");
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
        assertEquals(4, state.flow("player", 101L),
            "A0024 activation PRE must reserve, not consume, all four Flow");
        assertFalse(state.consumeDanceMove("player", 102L),
            "Dança must not become active before the activating hit is confirmed");

        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0024-cancel", false, false, true, true, 102L), ranks, state);
        assertEquals(4, state.flow("player", 102L),
            "cancelled A0024 activation must preserve the four Flow");

        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            daggerFacts("a0024-confirm", true, false, true, true, 103L), ranks, state, 90);
        assertEquals(1.15D, confirmedPre.damageMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("a0024-confirm", true, false, true, true, 104L), ranks, state);
        assertEquals(0, state.flow("player", 104L),
            "confirmed A0024 activation consumes all four Flow at POST");
        assertTrue(state.consumeDanceMove("player", 105L),
            "confirmed activation makes the one-time movement benefit available");
        assertFalse(state.consumeDanceHit("player", 105L),
            "the activating flank/rear hit is the first Dance hit and is consumed exactly once at commit");
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
        assertEquals(1.15D, secondPre.damageMultiplier(), EPSILON,
            "a cancelled hit must not burn the one-time Dance hit benefit");
        A0021A0040CombatPolicy.afterConfirmedHit(
            daggerFacts("dance-hit-confirm", true, false, false, true, 130L), ranks, state);
        assertFalse(state.consumeDanceHit("player", 131L),
            "the confirmed flank/rear hit consumes the benefit exactly once");
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
    void a0029ReservesAbaloUntilEffectiveDamagePostOnceHeavyExists() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0029", 2));
        for (int i = 0; i < 3; i++) state.addAbalo("player", "target", 0L);

        var cancelledPre = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0029-cancel", true, true, true, 1_000L), ranks, state, 80);
        assertEquals(1.45D, cancelledPre.guardPressureMultiplier(), EPSILON);
        assertEquals(1.15D, cancelledPre.impactMultiplier(), EPSILON);
        assertEquals(3, state.abalo("player", "target", 1_000L),
            "A0029 PRE must reserve three Abalo, not consume them");
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0029-cancel", true, true, true, false, 1_050L), ranks, state);
        assertEquals(3, state.abalo("player", "target", 1_050L),
            "cancelled A0029 must roll back the reservation");

        var confirmedPre = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0029-confirm", true, true, true, 1_100L), ranks, state, 80);
        assertEquals(1.45D, confirmedPre.guardPressureMultiplier(), EPSILON);
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0029-confirm", true, true, true, true, 1_150L), ranks, state);
        assertEquals(0, state.abalo("player", "target", 1_150L),
            "A0029 consumes exactly three Abalo only after confirmed effective damage");
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
        assertEquals(1.20D, confirmedPre.damageMultiplier(), EPSILON,
            "cancelled heavy must not consume the Demolition window");
        A0021A0040CombatPolicy.afterConfirmedHit(
            hammerFacts("a0030-confirm", true, false, true, true, 350L), ranks, state);

        var afterCommit = A0021A0040CombatPolicy.beforeHit(
            hammerFacts("a0030-after", true, false, true, 400L), ranks, state, 80);
        assertEquals(1.0D, afterCommit.damageMultiplier(), EPSILON,
            "confirmed heavy consumes the Demolition window exactly once");
    }

    private static A0021A0040CombatPolicy.HitFacts daggerFacts(
        String root,
        boolean actualDamage,
        boolean critical,
        boolean reposition,
        boolean flank,
        long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player",
            "target",
            root,
            WeaponFamily.DAGGER,
            true,
            true,
            actualDamage,
            critical,
            reposition,
            flank,
            false,
            false,
            false,
            true,
            true,
            false,
            0.75D,
            false,
            now
        );
    }

    private static A0021A0040CombatPolicy.HitFacts hammerFacts(
        String root,
        boolean heavyConfirmed,
        boolean guardPressureAvailable,
        boolean impactAvailable,
        long now
    ) {
        return hammerFacts(root, heavyConfirmed, guardPressureAvailable, impactAvailable, true, now);
    }

    private static A0021A0040CombatPolicy.HitFacts hammerFacts(
        String root,
        boolean heavyConfirmed,
        boolean guardPressureAvailable,
        boolean impactAvailable,
        boolean actualDamage,
        long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player",
            "target",
            root,
            WeaponFamily.HAMMER,
            true,
            true,
            actualDamage,
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
