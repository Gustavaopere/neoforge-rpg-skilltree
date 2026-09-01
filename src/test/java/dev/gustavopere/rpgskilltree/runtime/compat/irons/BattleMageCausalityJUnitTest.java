package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BattleMageCausalityJUnitTest {
    @Test
    void autonomousMobCastsNeverEnterPlayerMasteryLane() {
        assertFalse(IronMasterySourcePolicy.counts(false, false, false, "MOB"));
        assertFalse(IronMasterySourcePolicy.counts(false, false, false, "COMMAND"));
        assertTrue(IronMasterySourcePolicy.counts(false, false, false, "SPELLBOOK"));
        assertTrue(IronMasterySourcePolicy.counts(false, false, false, "SCROLL"));
    }

    @Test
    void invalidPlayerContextsRemainFailClosed() {
        assertFalse(IronMasterySourcePolicy.counts(true, false, false, "SPELLBOOK"));
        assertFalse(IronMasterySourcePolicy.counts(false, true, false, "SPELLBOOK"));
        assertFalse(IronMasterySourcePolicy.counts(false, false, true, "SPELLBOOK"));
    }
}
