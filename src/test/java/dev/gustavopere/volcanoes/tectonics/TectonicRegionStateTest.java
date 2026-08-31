package dev.gustavopere.volcanoes.tectonics;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TectonicRegionStateTest {
    @Test
    void stressRoundTripAndSerializationOrderAreDeterministic() {
        TectonicRegionState first = new TectonicRegionState();
        assertTrue(first.putStress(4L, -2L, 0.75));
        assertTrue(first.putStress(-3L, 9L, 0.25));
        assertFalse(first.putStress(4L, -2L, 0.75));

        TectonicRegionState second = new TectonicRegionState();
        second.putStress(-3L, 9L, 0.25);
        second.putStress(4L, -2L, 0.75);

        assertEquals(first.toTag(), second.toTag());

        TectonicRegionState restored = TectonicRegionState.fromTag(first.toTag());
        assertEquals(2, restored.size());
        assertEquals(0.75, restored.stressAt(4L, -2L), 0.0);
        assertEquals(0.25, restored.stressAt(-3L, 9L), 0.0);
        assertEquals(0.0, restored.stressAt(99L, 99L), 0.0);
    }

    @Test
    void rejectsStressOutsideNormalizedRange() {
        TectonicRegionState state = new TectonicRegionState();

        assertThrows(IllegalArgumentException.class, () -> state.putStress(0L, 0L, -0.01));
        assertThrows(IllegalArgumentException.class, () -> state.putStress(0L, 0L, 1.01));
        assertThrows(IllegalArgumentException.class, () -> state.putStress(0L, 0L, Double.NaN));
    }

    @Test
    void regionStateIsRealLevelSavedData() throws Exception {
        assertEquals(SavedData.class, TectonicRegionState.class.getSuperclass());
        assertNotNull(TectonicRegionState.class.getMethod("get", ServerLevel.class));
    }
}
