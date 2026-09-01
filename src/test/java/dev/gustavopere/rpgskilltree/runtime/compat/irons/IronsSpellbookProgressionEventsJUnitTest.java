package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronMasterySourcePolicy.CastKind;
import org.junit.jupiter.api.Test;

final class IronsSpellbookProgressionEventsJUnitTest {
    @Test
    void onlyRealSurvivalSpellbookAndScrollCastsCountForMastery() {
        assertTrue(IronMasterySourcePolicy.counts(false, false, false, CastKind.SPELLBOOK));
        assertTrue(IronMasterySourcePolicy.counts(false, false, false, CastKind.SCROLL));

        assertFalse(IronMasterySourcePolicy.counts(true, false, false, CastKind.SPELLBOOK));
        assertFalse(IronMasterySourcePolicy.counts(false, true, false, CastKind.SPELLBOOK));
        assertFalse(IronMasterySourcePolicy.counts(false, false, true, CastKind.SPELLBOOK));
        assertFalse(IronMasterySourcePolicy.counts(false, false, false, CastKind.OTHER));
    }
}
