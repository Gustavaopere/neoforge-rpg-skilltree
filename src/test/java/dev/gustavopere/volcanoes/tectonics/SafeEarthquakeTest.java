package dev.gustavopere.volcanoes.tectonics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SafeEarthquakeTest {
    @Test
    void stressReleaseEmitsSafeEventAndReducesPersistentRegionalStress() {
        TectonicRegionState state = new TectonicRegionState();
        state.putStress(0L, 0L, 0.92);
        TectonicStressService service = new TectonicStressService(fixedConvergentField(), state);

        Optional<SeismicEvent> released = service.tryReleaseStress(42L, 128.0, 128.0);

        assertTrue(released.isPresent());
        SeismicEvent event = released.orElseThrow();
        assertEquals(128.0, event.epicenterX());
        assertEquals(128.0, event.epicenterZ());
        assertTrue(event.magnitude() > 0.0);
        assertTrue(event.radiusBlocks() > 0.0);
        assertTrue(event.decayExponent() > 0.0);
        assertFalse(event.damagePolicy().terrainDamage());
        assertFalse(event.damagePolicy().structureDamage());
        assertTrue(state.stressAt(0L, 0L) < 0.92, "stress release must reduce persisted regional stress");
    }

    @Test
    void subcriticalStressDoesNotEmitEvent() {
        TectonicRegionState state = new TectonicRegionState();
        state.putStress(0L, 0L, 0.45);
        TectonicStressService service = new TectonicStressService(fixedConvergentField(), state);

        assertTrue(service.tryReleaseStress(42L, 64.0, 64.0).isEmpty());
        assertEquals(0.45, state.stressAt(0L, 0L), 1.0e-12);
    }

    @Test
    void seismicIntensityDecaysWithDistanceAndStopsAtRadius() {
        SeismicEvent event = new SeismicEvent(
                0.0,
                0.0,
                6.0,
                1_200.0,
                1.75,
                SeismicDamagePolicy.safeDefaults());

        double center = event.intensityAt(0.0, 0.0);
        double near = event.intensityAt(200.0, 0.0);
        double far = event.intensityAt(900.0, 0.0);

        assertTrue(center > near);
        assertTrue(near > far);
        assertTrue(far > 0.0);
        assertEquals(0.0, event.intensityAt(1_200.0, 0.0), 1.0e-12);
        assertEquals(0.0, event.intensityAt(2_000.0, 0.0), 1.0e-12);
    }

    @Test
    void dispatcherPerturbsMagmaAndGeothermalWithoutBlockDamage() {
        AtomicInteger magmaPerturbations = new AtomicInteger();
        AtomicInteger geothermalPerturbations = new AtomicInteger();
        List<SeismicPerturbationSink> sinks = List.of(
                event -> magmaPerturbations.incrementAndGet(),
                event -> geothermalPerturbations.incrementAndGet());
        SeismicEventDispatcher dispatcher = new SeismicEventDispatcher(sinks);
        SeismicEvent event = new SeismicEvent(
                100.0,
                -50.0,
                5.4,
                900.0,
                1.5,
                SeismicDamagePolicy.safeDefaults());

        SeismicDispatchResult result = dispatcher.dispatch(event);

        assertEquals(1, magmaPerturbations.get());
        assertEquals(1, geothermalPerturbations.get());
        assertEquals(2, result.perturbationSinksNotified());
        assertFalse(result.terrainModified());
        assertFalse(result.structureModified());
    }

    @Test
    void defaultAndOptInDamagePoliciesKeepProtectedAndPlayerStructuresSafe() {
        SeismicDamageDecider safe = new SeismicDamageDecider(SeismicDamagePolicy.safeDefaults());
        assertFalse(safe.canDamageNaturalBlock(true, false, false));
        assertFalse(safe.canDamageNaturalBlock(true, true, false));
        assertFalse(safe.canDamageStructure(false));

        SeismicDamageDecider optIn = new SeismicDamageDecider(new SeismicDamagePolicy(true, true));
        assertTrue(optIn.canDamageNaturalBlock(true, false, false));
        assertFalse(optIn.canDamageNaturalBlock(false, false, false), "non-natural block must remain excluded");
        assertFalse(optIn.canDamageNaturalBlock(true, true, false), "protected region must remain excluded");
        assertFalse(optIn.canDamageNaturalBlock(true, false, true), "block entity must remain excluded");
        assertTrue(optIn.canDamageStructure(false));
        assertFalse(optIn.canDamageStructure(true), "protected/player structure must remain excluded");
    }

    @Test
    void entityEffectProfileCarriesShakeSoundAndMovementInstability() {
        SeismicEvent event = new SeismicEvent(
                0.0,
                0.0,
                5.8,
                1_000.0,
                1.5,
                SeismicDamagePolicy.safeDefaults());
        SeismicEntityEffectProfile profile = SeismicEntityEffectProfile.at(event, 150.0, 0.0);

        assertTrue(profile.intensity() > 0.0);
        assertTrue(profile.shakeAmplitude() > 0.0);
        assertTrue(profile.soundVolume() > 0.0);
        assertTrue(profile.movementInstabilityTicks() > 0);
    }

    private static PlateField fixedConvergentField() {
        PlateSample sample = new PlateSample(
                new PlateId(1L),
                0.0,
                0.0,
                new PlateVector(1.0, 0.0),
                new PlateId(2L),
                new PlateVector(-1.0, 0.0),
                new PlateVector(1.0, 0.0),
                64.0,
                0.0);
        return (worldSeed, x, z) -> sample;
    }
}
