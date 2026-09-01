package dev.gustavopere.volcanoes.tectonics;

import net.minecraft.nbt.CompoundTag;
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
    void legacySchemaLoadIsMarkedDirtyForCurrentSchemaRewrite() {
        TectonicRegionState source = new TectonicRegionState();
        assertTrue(source.putStress(4L, -2L, 0.75));

        CompoundTag legacy = source.toTag();
        legacy.putInt("schema_version", 1);

        TectonicRegionState restored = TectonicRegionState.fromTag(legacy);

        assertEquals(0.75, restored.stressAt(4L, -2L), 0.0);
        assertTrue(restored.isDirty(), "schema v1 load must schedule persistence of schema v2");
        assertEquals(2, restored.toTag().getInt("schema_version"));
    }

    @Test
    void unversionedLegacyLoadIsMarkedDirtyForCurrentSchemaRewrite() {
        TectonicRegionState source = new TectonicRegionState();
        assertTrue(source.putStress(4L, -2L, 0.75));

        CompoundTag unversioned = source.toTag();
        unversioned.remove("schema_version");

        TectonicRegionState restored = TectonicRegionState.fromTag(unversioned);

        assertEquals(0.75, restored.stressAt(4L, -2L), 0.0);
        assertTrue(restored.isDirty(), "unversioned legacy load must schedule persistence of schema v2");
        assertEquals(2, restored.toTag().getInt("schema_version"));
    }

    @Test
    void malformedRegionsContainerIsPreservedReadOnly() {
        CompoundTag malformed = new CompoundTag();
        malformed.putInt("schema_version", 2);
        malformed.putString("regions", "not-a-list");

        TectonicRegionState restored = TectonicRegionState.fromTag(malformed);

        assertEquals(0, restored.size());
        assertFalse(restored.isDirty(), "malformed top-level payload must not be normalized destructively");
        assertEquals(malformed, restored.toTag(), "malformed regions payload must round-trip opaquely");
        assertFalse(restored.putStress(4L, -2L, 0.75), "malformed payload must remain read-only/fail-closed");
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
