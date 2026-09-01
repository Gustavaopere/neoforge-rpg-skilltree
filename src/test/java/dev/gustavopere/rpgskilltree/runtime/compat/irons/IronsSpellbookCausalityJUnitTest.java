package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.redspace.ironsspellbooks.api.spells.CastSource;
import org.junit.jupiter.api.Test;

final class IronsSpellbookCausalityJUnitTest {
    @Test
    void autonomousMobCastNeverEntersPlayerMasteryLane() {
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(CastSource.MOB));
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(CastSource.COMMAND));
    }

    @Test
    void playerSpellbookAndScrollLanesRemainCanonicalExactlyOnceSources() {
        assertTrue(IronsSpellbookProgressionEvents.countsForMastery(CastSource.SPELLBOOK));
        assertTrue(IronsSpellbookProgressionEvents.countsForMastery(CastSource.SCROLL));
    }
}
