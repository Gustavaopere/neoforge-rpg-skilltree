package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class ConfluenceBridgeReconciliationJUnitTest {
    @Test
    void paidBridgeIsTrackedAndRefundedWhenRespecBreaksOneRequiredDomain() {
        ClassUnlockDefinition geomancer = new ClassUnlockDefinition(
            "geomancer",
            Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MINING),
            false,
            10
        );
        FinalTriadProgress bothDomains = FinalTriadProgress.of(Map.of(
            ProgressionDomain.ARCANE, List.of(3, 3, 3),
            ProgressionDomain.MINING, List.of(3, 3, 3)
        ));
        ProgressionState eligible = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.ADMIN, 20))
            .withFinalTriads(bothDomains);

        ClassUnlockMutationResult unlocked = ProgressionService.unlockClass(eligible, geomancer);
        assertTrue(unlocked.unlockedNow());
        assertEquals(10, unlocked.bridgePointsSpent());
        assertEquals(10, unlocked.state().passivePoints().available());
        assertTrue(unlocked.state().classProgression().bridgePaid("geomancer"));

        FinalTriadProgress afterRespec = FinalTriadProgress.of(Map.of(
            ProgressionDomain.ARCANE, List.of(3, 3, 3)
        ));
        ProgressionState brokenPath = unlocked.state().withFinalTriads(afterRespec);
        PaidClassReconcileResult reconciled = ProgressionService.reconcilePaidClasses(
            brokenPath,
            List.of(geomancer)
        );

        assertFalse(reconciled.state().classProgression().isUnlocked("geomancer"));
        assertFalse(reconciled.state().classProgression().bridgePaid("geomancer"));
        assertEquals(Set.of("geomancer"), reconciled.removedClassIds());
        assertEquals(10, reconciled.bridgePointsRefunded());
        assertEquals(20, reconciled.state().passivePoints().available());

        ClassUnlockMutationResult unlockedAgain = ProgressionService.unlockClass(
            reconciled.state().withFinalTriads(bothDomains),
            geomancer
        );
        assertTrue(unlockedAgain.unlockedNow());
        assertEquals(10, unlockedAgain.bridgePointsSpent());
        assertEquals(10, unlockedAgain.state().passivePoints().available());
    }

    @Test
    void repeatingTheSamePaidClassUnlockNeverChargesBridgeTwice() {
        ClassUnlockDefinition spellblade = new ClassUnlockDefinition(
            "spellblade",
            Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MARTIAL),
            false,
            10
        );
        ProgressionState eligible = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.ADMIN, 20))
            .withFinalTriads(FinalTriadProgress.of(Map.of(
                ProgressionDomain.ARCANE, List.of(3, 3, 3),
                ProgressionDomain.MARTIAL, List.of(3, 3, 3)
            )));

        ClassUnlockMutationResult first = ProgressionService.unlockClass(eligible, spellblade);
        ClassUnlockMutationResult repeated = ProgressionService.unlockClass(first.state(), spellblade);

        assertTrue(first.state().classProgression().bridgePaid("spellblade"));
        assertFalse(repeated.unlockedNow());
        assertEquals(0, repeated.bridgePointsSpent());
        assertEquals(first.state().passivePoints().spent(), repeated.state().passivePoints().spent());
    }
}
