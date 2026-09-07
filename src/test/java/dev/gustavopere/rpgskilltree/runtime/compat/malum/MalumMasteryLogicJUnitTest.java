package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

final class MalumMasteryLogicJUnitTest {
    @Test
    void authorityRequiresRealSurvivalPlayer() {
        assertTrue(MalumMasteryLogic.isEligiblePlayer(false, false, false));
        assertFalse(MalumMasteryLogic.isEligiblePlayer(true, false, false));
        assertFalse(MalumMasteryLogic.isEligiblePlayer(false, true, false));
        assertFalse(MalumMasteryLogic.isEligiblePlayer(false, false, true));
    }

    @Test
    void reapingRequiresPositiveIdentifiedEvidence() {
        assertTrue(MalumMasteryLogic.hasConfirmedSpiritEvidence(List.of("malum:aqueous_spirit"), 1));
        assertFalse(MalumMasteryLogic.hasConfirmedSpiritEvidence(List.of(), 1));
        assertFalse(MalumMasteryLogic.hasConfirmedSpiritEvidence(List.of("malum:aqueous_spirit"), 0));
        assertFalse(MalumMasteryLogic.hasConfirmedSpiritEvidence(List.of("malum:aqueous_spirit"), -1));
    }
}
