package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalActivityServiceTest {
    @Test
    void tectonicPotentialRewardsHotspotsAndVolcanicallyProductiveBoundaries() {
        GeothermalActivityService service = new GeothermalActivityService(2_048.0);

        double interior = service.potential(sample(TectonicContext.INTERIOR, 0.10, 0.10, 5_000.0));
        double transform = service.potential(sample(TectonicContext.TRANSFORM, 0.35, 0.30, 96.0));
        double divergent = service.potential(sample(TectonicContext.DIVERGENT, 0.35, 0.55, 128.0));
        double convergent = service.potential(sample(TectonicContext.CONVERGENT, 0.45, 0.65, 96.0));
        double hotspot = service.potential(sample(TectonicContext.HOTSPOT, 0.20, 0.80, 5_000.0));

        assertTrue(transform > interior);
        assertTrue(divergent > transform);
        assertTrue(convergent > divergent);
        assertTrue(hotspot > convergent);
    }

    @Test
    void activeHotMagmaRaisesPotentialMostAtShortRange() {
        GeothermalActivityService service = new GeothermalActivityService(2_048.0);
        TectonicSample interior = sample(TectonicContext.INTERIOR, 0.20, 0.20, 5_000.0);
        MagmaChamber chamber = new MagmaChamber(
                new MagmaComposition(0.52, 0.48),
                8.0,
                220.0,
                0.12,
                1_350.0,
                0.20);

        double tectonicOnly = service.potential(interior);
        double far = service.potential(interior, 1_600.0, chamber);
        double near = service.potential(interior, 128.0, chamber);

        assertTrue(far > tectonicOnly);
        assertTrue(near > far);
    }

    @Test
    void potentialRemainsBoundedAndDistanceValidationFailsClosed() {
        GeothermalActivityService service = new GeothermalActivityService(2_048.0);
        TectonicSample hotspot = sample(TectonicContext.HOTSPOT, 1.0, 1.0, 0.0);
        MagmaChamber chamber = new MagmaChamber(
                new MagmaComposition(0.72, 0.90),
                40.0,
                500.0,
                0.80,
                1_650.0,
                1.0);

        double saturated = service.potential(hotspot, 0.0, chamber);
        assertTrue(saturated >= 0.0 && saturated <= 1.0);
        assertThrows(IllegalArgumentException.class, () -> service.potential(hotspot, -1.0, chamber));
        assertThrows(IllegalArgumentException.class, () -> new GeothermalActivityService(0.0));
    }

    private static TectonicSample sample(
            TectonicContext context,
            double stress,
            double volcanicPotential,
            double boundaryDistance
    ) {
        return new TectonicSample(
                11L,
                12L,
                context,
                stress,
                volcanicPotential,
                boundaryDistance,
                0.0,
                0.0);
    }
}
