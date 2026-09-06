package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PressureNeoForgeRuntimeClassificationTest {
    @Test
    void skyExposedAirIsAFreeSurface() {
        assertEquals(
                WaterCellKind.OPEN_AIR,
                PressureNeoForgeRuntime.classifyWaterCell(false, true, true));
    }

    @Test
    void subterraneanAirIsNotAssumedToEqualizeWithExternalAtmosphere() {
        assertEquals(
                WaterCellKind.BLOCKED,
                PressureNeoForgeRuntime.classifyWaterCell(false, true, false));
    }

    @Test
    void waterClassificationWinsEvenWhenThePositionCanSeeSky() {
        assertEquals(
                WaterCellKind.WATER,
                PressureNeoForgeRuntime.classifyWaterCell(true, false, true));
    }

    @Test
    void blockDepthIsConvertedToContinuousEyeDepth() {
        WaterDepthSample discrete = new WaterDepthSample(1.0, true);

        WaterDepthSample continuous = PressureNeoForgeRuntime.continuousEyeDepth(discrete, 10.9);

        assertEquals(0.1, continuous.depthMeters(), 1.0e-9);
        assertEquals(true, continuous.surfaceResolved());
    }

    @Test
    void continuousDepthSupportsNegativeWorldCoordinatesAndNeverGoesNegative() {
        WaterDepthSample discrete = new WaterDepthSample(1.0, false);

        WaterDepthSample negativeY = PressureNeoForgeRuntime.continuousEyeDepth(discrete, -10.25);
        WaterDepthSample shallow = PressureNeoForgeRuntime.continuousEyeDepth(
                new WaterDepthSample(0.25, true),
                10.9);

        assertEquals(0.25, negativeY.depthMeters(), 1.0e-9);
        assertEquals(false, negativeY.surfaceResolved());
        assertEquals(0.0, shallow.depthMeters(), 1.0e-9);
    }

    @Test
    void partialWaterOnlyCountsWhenEyesAreBelowTheActualFluidSurface() {
        assertEquals(true, PressureNeoForgeRuntime.isEyeSubmergedInTaggedWater(true, 10.70, 10, 0.80));
        assertEquals(false, PressureNeoForgeRuntime.isEyeSubmergedInTaggedWater(true, 10.80, 10, 0.80));
        assertEquals(false, PressureNeoForgeRuntime.isEyeSubmergedInTaggedWater(true, 10.90, 10, 0.80));
        assertEquals(false, PressureNeoForgeRuntime.isEyeSubmergedInTaggedWater(false, 10.20, 10, 0.80));
    }

    @Test
    void externallyOpenPartialWaterUsesItsActualLocalFreeSurfaceHeight() {
        Optional<WaterDepthSample> local = PressureNeoForgeRuntime.localOpenSurfaceDepth(
                true,
                10.70,
                10,
                0.80,
                WaterCellKind.OPEN_AIR);

        assertEquals(true, local.isPresent());
        assertEquals(0.10, local.orElseThrow().depthMeters(), 1.0e-9);
        assertEquals(true, local.orElseThrow().surfaceResolved());
    }

    @Test
    void localPartialSurfaceIsNotTrustedUnderBlockedSubterraneanAir() {
        assertEquals(
                Optional.empty(),
                PressureNeoForgeRuntime.localOpenSurfaceDepth(
                        true,
                        10.70,
                        10,
                        0.80,
                        WaterCellKind.BLOCKED));
    }
}
