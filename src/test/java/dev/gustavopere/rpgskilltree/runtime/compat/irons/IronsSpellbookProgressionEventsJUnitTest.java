package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class IronsSpellbookProgressionEventsJUnitTest {
    @Test
    void onlyRealSurvivalSpellbookAndScrollCastsCountForMastery() {
        assertTrue(IronMasterySourcePolicy.counts(false, false, false, "SPELLBOOK"));
        assertTrue(IronMasterySourcePolicy.counts(false, false, false, "SCROLL"));

        assertFalse(IronMasterySourcePolicy.counts(true, false, false, "SPELLBOOK"));
        assertFalse(IronMasterySourcePolicy.counts(false, true, false, "SPELLBOOK"));
        assertFalse(IronMasterySourcePolicy.counts(false, false, true, "SPELLBOOK"));
        assertFalse(IronMasterySourcePolicy.counts(false, false, false, "COMMAND"));
        assertFalse(IronMasterySourcePolicy.counts(false, false, false, null));
    }
}
