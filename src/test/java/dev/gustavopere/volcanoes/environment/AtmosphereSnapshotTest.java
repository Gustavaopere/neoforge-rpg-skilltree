package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AtmosphereSnapshotTest {
    @Test
    void compressedSnapshotRoundTripsPlayerRelevantStateWithinQuantizationBounds() {
        AtmosphereState state = new AtmosphereState(
                0.73, 0.184, 0.041, 37.5, 82.0, 14.2, 6.4, 0.77, 12.5);
        AtmosphereSnapshot snapshot = AtmosphereSnapshot.from(state);
        AtmosphereState restored = snapshot.toAtmosphereState();

        assertTrue(snapshot.encodedSizeBytes() <= 20);
        assertEquals(state.totalPressureAtm(), restored.totalPressureAtm(), 0.002);
        assertEquals(state.oxygenFraction(), restored.oxygenFraction(), 0.0001);
        assertEquals(state.carbonDioxideFraction(), restored.carbonDioxideFraction(), 0.0001);
        assertEquals(state.sulfurDioxidePpm(), restored.sulfurDioxidePpm(), 0.2);
        assertEquals(state.toxicGasPpm(), restored.toxicGasPpm(), 0.2);
        assertEquals(state.particulatesMgM3(), restored.particulatesMgM3(), 0.05);
        assertEquals(state.smokeMgM3(), restored.smokeMgM3(), 0.05);
        assertEquals(state.relativeHumidity(), restored.relativeHumidity(), 0.005);
        assertEquals(state.thermalModifierC(), restored.thermalModifierC(), 0.1);
    }
}
