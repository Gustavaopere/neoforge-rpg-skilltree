package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Regression gates added by Chat 3 before reconciling the A0031-A0040 implementation. */
final class A0031A0040Chat3RegressionJUnitTest {
    @Test
    void a0023PostCommitSemanticsMustSurviveTheA0031A0040Merge() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0023", 2));
        state.addFlow("player", 2, 0L);
        state.addFlow("player", 2, 0L);
        var facts = facts("blind-root", WeaponFamily.DAGGER, true, true, false, true, 1_000L);

        var prepared = A0021A0040CombatPolicy.beforeHit(facts, ranks, state, 0);
        assertTrue(prepared.physicalPenetrationFraction() > 0.0D);
        assertEquals(2, state.flow("player", 1_000L),
            "A0023 must reserve in PRE; the already-merged causal contract cannot regress to PRE consumption");

        A0021A0040CombatPolicy.afterConfirmedHit(facts, ranks, state);
        assertEquals(0, state.flow("player", 1_000L),
            "A0023 must consume the reserved Fluxo only after confirmed damage");
    }

    @Test
    void a0035CancelledRootMustDiscardItsReservation() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2));
        for (int i = 0; i < 3; i++) state.addTrauma("player", "target", 2, i);
        var cancelled = facts("sunder-root", WeaponFamily.MACE, false, false, false, true, 1_000L);

        assertTrue(A0021A0040CombatPolicy.beforeHit(cancelled, ranks, state, 80).applyArmorSunder());
        A0021A0040CombatPolicy.afterConfirmedHit(cancelled, ranks, state);
        assertEquals(3, state.trauma("player", "target", 1_000L));
        assertFalse(state.isSundered("player", "target", 1_000L));

        assertTrue(state.prepareSunder("player", "target", "sunder-root", 2, 1_001L),
            "terminal cancellation must release the root reservation instead of retaining it until TTL");
    }

    @Test
    void a0036CancelledRootMustDiscardItsReservationAndCooldown() {
        var state = new A0021A0040CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        state.markSundered("player", "target", 2, 0L);
        var cancelled = facts("bone-root", WeaponFamily.MACE, false, false, true, true, 1_000L);

        assertTrue(A0021A0040CombatPolicy.beforeHit(cancelled, ranks, state, 80).applyBonebreaker());
        A0021A0040CombatPolicy.afterConfirmedHit(cancelled, ranks, state);
        assertTrue(state.bonebreakerReady("player", "target", 1_000L),
            "cancelled/zero-damage roots must not start the A0036 cooldown");
        assertTrue(state.prepareBonebreaker("player", "other-target", "bone-root", 80, 1_001L),
            "terminal cancellation must release the A0036 root reservation instead of retaining it until TTL");
    }

    @Test
    void legacyRepeatableMasteryGuardsRemainFailClosedForProviderOwnedFamilies() {
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.DAGGER, true, true, 4.0D, "").isEmpty(), "blank root rejected");
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.DAGGER, false, true, 4.0D, "legacy-root").isEmpty(), "indirect hit rejected");
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.DAGGER, true, false, 4.0D, "legacy-root").isEmpty(), "non-hostile hit rejected");
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.DAGGER, true, true, Double.NaN, "legacy-root").isEmpty(), "non-finite damage rejected");
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.DAGGER, true, true, 0.0D, "legacy-root").isEmpty(), "zero damage rejected");
        assertTrue(A0021A0040MasteryPolicy.forConfirmedDirectHit(
            WeaponFamily.DAGGER, true, true, 4.0D, "legacy-root").isEmpty(),
            "provider-owned family must not receive a synthetic gate-mastery award");
    }

    private static A0021A0040CombatPolicy.HitFacts facts(
        String root,
        WeaponFamily family,
        boolean actualDamage,
        boolean flankOrRear,
        boolean heavy,
        boolean providerAvailable,
        long now
    ) {
        return new A0021A0040CombatPolicy.HitFacts(
            "player", "target", root, family,
            true, true, actualDamage, true,
            false, flankOrRear, heavy,
            true,
            providerAvailable, providerAvailable, providerAvailable, providerAvailable,
            0.75D, false, now
        );
    }
}
