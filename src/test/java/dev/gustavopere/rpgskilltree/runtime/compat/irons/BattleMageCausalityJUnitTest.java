package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.redspace.ironsspellbooks.api.spells.CastSource;
import org.junit.jupiter.api.Test;

final class BattleMageCausalityJUnitTest {
    @Test
    void autonomousMobCastsNeverEnterPlayerMasteryLane() {
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(CastSource.MOB));
        assertFalse(IronsSpellbookProgressionEvents.countsForMastery(CastSource.COMMAND));
        assertTrue(IronsSpellbookProgressionEvents.countsForMastery(CastSource.SPELLBOOK));
        assertTrue(IronsSpellbookProgressionEvents.countsForMastery(CastSource.SCROLL));
    }
}
