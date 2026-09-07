package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.CombatPerkAvailabilityRuntime;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class A0061A0070Chat3CoverageJUnitTest {
    @Test
    void a0067IsStructurallyUnavailableAndLegacyRankIsMasked() {
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0067"));
        assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0061"));

        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(
            CombatPerkRanks.of(Map.of("A0061", 5, "A0067", 1))
        );
        assertEquals(5, effective.rank("A0061"));
        assertEquals(0, effective.rank("A0067"));
        assertEquals(0.0D, A0061A0080CombatPolicy.offensiveInterruptionResistanceFraction(effective));
    }

    @Test
    void persistedRanksMaskUnavailableNodeAtRuntimeAvailabilityBoundary() {
        ProgressionState persisted = ProgressionState.empty().withPassiveNodes(
            PassiveNodeProgress.of(Map.of(
                "rpgskilltree:combat/a0061", 5,
                "rpgskilltree:combat/a0067", 1
            ))
        );

        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(persisted.passiveNodes());
        assertEquals(5, effective.rank("A0061"));
        assertEquals(0, effective.rank("A0067"));
    }

    @Test
    void woundedAndIntactThresholdsUseStrictPreImpactBoundaries() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0068", 3, "A0069", 3));
        A0061A0080CombatState state = new A0061A0080CombatState();

        var exactWoundedBoundary = A0061A0080CombatPolicy.beforePhysicalHit(
            hit("w35", 0.35D, false, false), ranks, state
        );
        assertEquals(1.0D, exactWoundedBoundary.damageMultiplier());

        var belowWoundedBoundary = A0061A0080CombatPolicy.beforePhysicalHit(
            hit("w34", Math.nextDown(0.35D), false, false), ranks, state
        );
        assertEquals(1.12D, belowWoundedBoundary.damageMultiplier(), 1.0e-12);

        var exactIntactBoundary = A0061A0080CombatPolicy.beforePhysicalHit(
            hit("i85", 0.85D, false, false), ranks, state
        );
        assertEquals(1.0D, exactIntactBoundary.damageMultiplier());

        var aboveIntactBoundary = A0061A0080CombatPolicy.beforePhysicalHit(
            hit("i86", Math.nextUp(0.85D), false, false), ranks, state
        );
        assertEquals(1.12D, aboveIntactBoundary.damageMultiplier(), 1.0e-12);
    }

    @Test
    void bossTakesPrecedenceOverEliteAndNeverDoubleStacks() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0070", 5, "A0071", 5));
        A0061A0080CombatState state = new A0061A0080CombatState();

        var bothFlags = A0061A0080CombatPolicy.beforePhysicalHit(
            hit("boss-elite", 0.50D, true, true), ranks, state
        );
        assertEquals(1.15D, bothFlags.damageMultiplier(), 1.0e-12);
    }

    @Test
    void criticalDamageOnlyComposesAfterCanonicalCriticalResolution() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0063", 3));
        assertEquals(1.0D, A0061A0080CombatPolicy.criticalDamageMultiplier(ranks, false));
        assertEquals(1.15D, A0061A0080CombatPolicy.criticalDamageMultiplier(ranks, true), 1.0e-12);
    }

    @Test
    void physicalBasePenetrationAndImpactRemainIndependentContributions() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0061", 5, "A0065", 4, "A0066", 4));
        var result = A0061A0080CombatPolicy.beforePhysicalHit(
            hit("martial", 0.50D, false, false), ranks, new A0061A0080CombatState()
        );
        assertEquals(1.10D, result.damageMultiplier(), 1.0e-12);
        assertEquals(0.08D, result.penetrationFraction(), 1.0e-12);
        assertEquals(1.12D, result.impactMultiplier(), 1.0e-12);
    }

    private static A0061A0080CombatPolicy.HitFacts hit(
        String root,
        double healthFraction,
        boolean boss,
        boolean elite
    ) {
        return new A0061A0080CombatPolicy.HitFacts(
            "player", "target", root, healthFraction, boss, elite,
            false, false, false, true, true, 1_000L
        );
    }
}
