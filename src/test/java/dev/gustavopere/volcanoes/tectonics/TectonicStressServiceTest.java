package dev.gustavopere.volcanoes.tectonics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TectonicStressServiceTest {
    @Test
    void sampleExposesCanonicalTectonicSampleWithPersistedStress() {
        TectonicRegionState state = new TectonicRegionState();
        state.putStress(0L, 0L, 0.42);
        TectonicStressService service = new TectonicStressService(convergentField(), state);

        TectonicSample sample = service.sample(99L, 100.0, 100.0);

        assertEquals(1L, sample.plateId());
        assertEquals(2L, sample.neighborPlateId());
        assertEquals(TectonicContext.CONVERGENT, sample.context());
        assertEquals(0.42, sample.stress(), 0.0);
        assertTrue(sample.volcanicPotential() >= 0.8);
        assertEquals(128.0, sample.boundaryDistanceBlocks(), 0.0);
        assertEquals(1.0, sample.motionX(), 1.0e-12);
        assertEquals(0.0, sample.motionZ(), 1.0e-12);
    }

    @Test
    void hotspotInteriorMapsToHotspotContext() {
        PlateField hotspotField = (seed, x, z) -> plateSample(
                PlateVector.fromAngle(0.0),
                PlateVector.fromAngle(0.0),
                new PlateVector(1.0, 0.0),
                8_000.0,
                1.0);
        TectonicStressService service = new TectonicStressService(
                hotspotField,
                new TectonicRegionState());

        TectonicSample sample = service.sample(1L, 0.0, 0.0);

        assertEquals(TectonicContext.HOTSPOT, sample.context());
        assertTrue(sample.volcanicPotential() >= 0.9);
    }

    @Test
    void stressUpdatesOnlyOnLongCadenceAndTouchesAtMostConfiguredCells() {
        TectonicRegionState state = new TectonicRegionState();
        for (int i = 0; i < 10; i++) {
            state.putStress(i, 0L, 0.0);
        }
        TectonicStressService service = new TectonicStressService(
                convergentField(),
                state,
                600,
                3);

        assertEquals(0, service.tick(123L, 599L));
        assertEquals(0, countPositiveStress(state));

        assertEquals(3, service.tick(123L, 600L));
        assertEquals(3, countPositiveStress(state));
        assertTrue(state.entries().stream().allMatch(entry -> entry.stress() >= 0.0 && entry.stress() <= 1.0));
    }

    @Test
    void repeatedCadenceGraduallyBuildsAndClampsStress() {
        TectonicRegionState state = new TectonicRegionState();
        state.putStress(0L, 0L, 0.0);
        TectonicStressService service = new TectonicStressService(
                convergentField(),
                state,
                400,
                1);

        double previous = state.stressAt(0L, 0L);
        for (int step = 1; step <= 40; step++) {
            assertEquals(1, service.tick(7L, step * 400L));
            double current = state.stressAt(0L, 0L);
            assertTrue(current >= previous);
            assertTrue(current <= 1.0);
            previous = current;
        }
        assertTrue(previous > 0.8, "sustained convergent loading should build substantial stress");
    }

    private static int countPositiveStress(TectonicRegionState state) {
        return (int) state.entries().stream().filter(entry -> entry.stress() > 0.0).count();
    }

    private static PlateField convergentField() {
        return (seed, x, z) -> plateSample(
                PlateVector.fromAngle(0.0),
                PlateVector.fromAngle(Math.PI),
                new PlateVector(1.0, 0.0),
                128.0,
                0.0);
    }

    private static PlateSample plateSample(
            PlateVector motion,
            PlateVector neighborMotion,
            PlateVector boundaryNormal,
            double boundaryDistance,
            double hotspotIntensity
    ) {
        return new PlateSample(
                new PlateId(1L),
                0.0,
                0.0,
                motion,
                new PlateId(2L),
                neighborMotion,
                boundaryNormal,
                boundaryDistance,
                hotspotIntensity);
    }
}
