package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.redspace.ironsspellbooks.api.spells.CastSource;
import org.junit.jupiter.api.Test;

final class IronsSpellbookProgressionEventsJUnitTest {
    @Test
    void onlyRealSurvivalSpellbookAndScrollCastsCountForMastery() {
        assertTrue(IronsSpellbookProgressionEvents.countsForMastery(false, false, false, CastSource.SPELLBOOK));
        assertTrue(IronsSpellbookProgressionEvents.countsForMastery(false, false, false, CastSource.SCROLL));

        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(true, false, false, CastSource.SPELLBOOK));
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(false, true, false, CastSource.SPELLBOOK));
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(false, false, true, CastSource.SPELLBOOK));
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(false, false, false, CastSource.COMMAND));
    }
}
