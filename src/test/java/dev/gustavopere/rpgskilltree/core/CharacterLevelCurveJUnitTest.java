package dev.gustavopere.rpgskilltree.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class CharacterLevelCurveJUnitTest {
    @Test
    void defaultCurvePreservesLevelBoundaries() {
        CharacterLevelCurve curve = CharacterLevelCurve.defaultCurve();

        assertEquals(100, curve.maxLevel());
        assertEquals(0L, curve.xpRequiredForLevel(1));
        assertEquals(100L, curve.xpToNextLevel(1));
        assertEquals(1, curve.levelForTotalXp(99L));
        assertEquals(2, curve.levelForTotalXp(100L));
        assertEquals(0L, curve.xpToNextLevel(curve.maxLevel()));
    }

    @Test
    void rejectsInvalidProgressionInputs() {
        CharacterLevelCurve curve = CharacterLevelCurve.defaultCurve();

        assertThrows(IllegalArgumentException.class, () -> curve.levelForTotalXp(-1L));
        assertThrows(IllegalArgumentException.class, () -> curve.xpRequiredForLevel(0));
        assertThrows(IllegalArgumentException.class, () -> curve.xpRequiredForLevel(101));
    }
}
