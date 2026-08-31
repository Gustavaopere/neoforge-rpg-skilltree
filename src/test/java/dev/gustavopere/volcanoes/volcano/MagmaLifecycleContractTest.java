package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.SeismicDamagePolicy;
import dev.gustavopere.volcanoes.tectonics.SeismicEvent;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MagmaLifecycleContractTest {
    private static final long DAY_TICKS = 24_000L;

    @Test
    void compositionProfilesAreValidatedAndDeterministicByVolcanoType() {
        MagmaComposition strato = MagmaComposition.forType(VolcanoType.STRATOVOLCANO);
        MagmaComposition shield = MagmaComposition.forType(VolcanoType.SHIELD);
        MagmaComposition caldera = MagmaComposition.forType(VolcanoType.CALDERA);

        assertTrue(strato.silicaFraction() > shield.silicaFraction());
        assertTrue(caldera.volatileRichness() > shield.volatileRichness());
        assertEquals(strato, MagmaComposition.forType(VolcanoType.STRATOVOLCANO));
        assertEquals(strato, MagmaComposition.fromTag(strato.toTag()));
        assertThrows(IllegalArgumentException.class, () -> new MagmaComposition(-0.01, 0.2));
        assertThrows(IllegalArgumentException.class, () -> new MagmaComposition(0.5, 1.01));
    }

    @Test
    void magmaChamberModelsAndRoundTripsAllRequiredPhysicalState() {
        MagmaChamber chamber = chamber(8.5, 145.0, 0.08, 1_240.0, 0.35);

        assertEquals(8.5, chamber.volumeCubicKilometers());
        assertEquals(145.0, chamber.pressureMegapascals());
        assertEquals(0.08, chamber.gasFraction());
        assertEquals(1_240.0, chamber.temperatureKelvin());
        assertEquals(0.35, chamber.replenishmentCubicKilometersPerDay());
        assertEquals(chamber, MagmaChamber.fromTag(chamber.toTag()));

        assertThrows(IllegalArgumentException.class,
                () -> new MagmaChamber(chamber.composition(), -1.0, 100.0, 0.1, 1_200.0, 0.1));
        assertThrows(IllegalArgumentException.class,
                () -> new MagmaChamber(chamber.composition(), 1.0, 100.0, 1.1, 1_200.0, 0.1));
        assertThrows(IllegalArgumentException.class,
                () -> new MagmaChamber(chamber.composition(), 1.0, 100.0, 0.1, Double.NaN, 0.1));
    }

    @Test
    void lifecycleUsesExplicitHysteresisAndDoesNotFlapAtOneThreshold() {
        VolcanoManager manager = manager(new VolcanoSavedData(), highTectonics());

        assertEquals(VolcanoState.DORMANT,
                manager.nextState(VolcanoState.DORMANT, chamber(8.0, 170.0, 0.06, 1_220.0, 0.3)));
        assertEquals(VolcanoState.ACTIVE,
                manager.nextState(VolcanoState.DORMANT, chamber(8.0, 190.0, 0.06, 1_220.0, 0.3)));
        assertEquals(VolcanoState.ACTIVE,
                manager.nextState(VolcanoState.ACTIVE, chamber(8.0, 150.0, 0.06, 1_220.0, 0.3)));
        assertEquals(VolcanoState.DORMANT,
                manager.nextState(VolcanoState.ACTIVE, chamber(8.0, 110.0, 0.06, 1_220.0, 0.3)));
        assertEquals(VolcanoState.ERUPTING,
                manager.nextState(VolcanoState.ACTIVE, chamber(8.0, 300.0, 0.20, 1_220.0, 0.3)));
        assertEquals(VolcanoState.ERUPTING,
                manager.nextState(VolcanoState.ERUPTING, chamber(8.0, 180.0, 0.08, 1_220.0, 0.3)));
        assertEquals(VolcanoState.ACTIVE,
                manager.nextState(VolcanoState.ERUPTING, chamber(8.0, 150.0, 0.05, 1_220.0, 0.3)));
        assertEquals(VolcanoState.EXTINCT,
                manager.nextState(VolcanoState.EXTINCT, chamber(0.1, 300.0, 0.20, 1_220.0, 0.3)));
    }

    @Test
    void tectonicSupplyAndSeismicIntensityIncreaseBuildupWhileEruptionsRelax() {
        VolcanoManager manager = manager(new VolcanoSavedData(), highTectonics());
        MagmaChamber initial = chamber(6.0, 100.0, 0.04, 1_200.0, 0.30);

        MagmaChamber weak = manager.evolve(
                initial,
                sample(TectonicContext.INTERIOR, 0.05, 0.05),
                0.0,
                DAY_TICKS,
                VolcanoState.DORMANT);
        MagmaChamber strong = manager.evolve(
                initial,
                sample(TectonicContext.HOTSPOT, 0.80, 0.95),
                0.0,
                DAY_TICKS,
                VolcanoState.DORMANT);
        MagmaChamber shaken = manager.evolve(
                initial,
                sample(TectonicContext.INTERIOR, 0.05, 0.05),
                0.8,
                DAY_TICKS,
                VolcanoState.DORMANT);
        MagmaChamber erupting = manager.evolve(
                chamber(10.0, 310.0, 0.22, 1_250.0, 0.30),
                sample(TectonicContext.CONVERGENT, 0.70, 0.90),
                0.0,
                DAY_TICKS,
                VolcanoState.ERUPTING);

        assertTrue(strong.volumeCubicKilometers() > weak.volumeCubicKilometers());
        assertTrue(strong.pressureMegapascals() > weak.pressureMegapascals());
        assertTrue(shaken.pressureMegapascals() > weak.pressureMegapascals());
        assertTrue(erupting.pressureMegapascals() < 310.0);
        assertTrue(erupting.volumeCubicKilometers() < 10.0);
        assertTrue(erupting.gasFraction() < 0.22);
    }

    @Test
    void savedDataPersistsChamberAndCurrentStateUnderTheStableSiteId() {
        UUID id = UUID.fromString("c9c7a474-cf48-4b42-b16c-8c6d152eb27d");
        VolcanoSavedData data = new VolcanoSavedData();
        data.register(site(id, new BlockPos(128, 90, -256), VolcanoType.STRATOVOLCANO, VolcanoState.DORMANT));
        MagmaChamber chamber = chamber(7.5, 205.0, 0.11, 1_230.0, 0.42);

        assertTrue(data.updateLifecycle(id, VolcanoState.ACTIVE, chamber));
        assertFalse(data.updateLifecycle(id, VolcanoState.ACTIVE, chamber));
        assertEquals(VolcanoState.ACTIVE, data.get(id).orElseThrow().state());
        assertEquals(chamber, data.chamber(id).orElseThrow());

        VolcanoSavedData restored = VolcanoSavedData.fromTag(data.toTag());
        assertEquals(VolcanoState.ACTIVE, restored.get(id).orElseThrow().state());
        assertEquals(chamber, restored.chamber(id).orElseThrow());
        assertThrows(IllegalArgumentException.class,
                () -> data.updateLifecycle(UUID.randomUUID(), VolcanoState.ACTIVE, chamber));
    }

    @Test
    void managerInitializesMissingChambersDeterministicallyWithoutChangingSiteIdentity() {
        UUID id = UUID.fromString("a1f0c5ca-9a2c-4ff2-8cb0-227a07a17518");
        VolcanoSavedData first = new VolcanoSavedData();
        VolcanoSavedData second = new VolcanoSavedData();
        VolcanoSite site = site(id, new BlockPos(4_096, 96, 8_192), VolcanoType.CALDERA, VolcanoState.DORMANT);
        first.register(site);
        second.register(site);

        MagmaChamber firstChamber = manager(first, highTectonics()).ensureChamber(id);
        MagmaChamber secondChamber = manager(second, highTectonics()).ensureChamber(id);

        assertEquals(firstChamber, secondChamber);
        assertEquals(id, first.get(id).orElseThrow().persistenceId());
        assertEquals(VolcanoState.DORMANT, first.get(id).orElseThrow().state());
    }

    @Test
    void seismicEventPerturbsOnlySitesInsideItsSpatialInfluence() {
        VolcanoSavedData data = new VolcanoSavedData();
        UUID nearId = UUID.fromString("13c884db-fba0-4fee-8b1e-ad322012b4e1");
        UUID farId = UUID.fromString("a626009f-ea38-49a6-8512-73f2bb066811");
        data.register(site(nearId, new BlockPos(0, 80, 0), VolcanoType.STRATOVOLCANO, VolcanoState.DORMANT));
        data.register(site(farId, new BlockPos(5_000, 80, 0), VolcanoType.SHIELD, VolcanoState.DORMANT));
        VolcanoManager manager = manager(data, highTectonics());
        double nearBefore = manager.ensureChamber(nearId).pressureMegapascals();
        double farBefore = manager.ensureChamber(farId).pressureMegapascals();

        SeismicEvent event = new SeismicEvent(0.0, 0.0, 7.0, 2_000.0, 1.5, SeismicDamagePolicy.safeDefaults());
        assertEquals(1, manager.onSeismicEvent(event));

        assertTrue(data.chamber(nearId).orElseThrow().pressureMegapascals() > nearBefore);
        assertEquals(farBefore, data.chamber(farId).orElseThrow().pressureMegapascals());
    }

    @Test
    void schedulerUsesPriorityQueueCadenceInsteadOfScanningEveryVolcanoEveryTick() {
        VolcanoTickScheduler scheduler = new VolcanoTickScheduler();
        MagmaChamber quiet = chamber(5.0, 80.0, 0.03, 1_180.0, 0.05);
        MagmaChamber nearActive = chamber(5.0, 165.0, 0.07, 1_200.0, 0.25);

        long eruptingInterval = scheduler.intervalTicksFor(VolcanoState.ERUPTING, nearActive);
        long activeInterval = scheduler.intervalTicksFor(VolcanoState.ACTIVE, nearActive);
        long nearActiveInterval = scheduler.intervalTicksFor(VolcanoState.DORMANT, nearActive);
        long dormantInterval = scheduler.intervalTicksFor(VolcanoState.DORMANT, quiet);
        long extinctInterval = scheduler.intervalTicksFor(VolcanoState.EXTINCT, quiet);

        assertTrue(eruptingInterval < activeInterval);
        assertTrue(activeInterval < nearActiveInterval);
        assertTrue(nearActiveInterval < dormantInterval);
        assertTrue(dormantInterval < extinctInterval);

        UUID id = UUID.fromString("bd4ab6ac-f9f6-432d-8f09-bbe7a98a6a67");
        scheduler.schedule(id, VolcanoState.ACTIVE, nearActive, 1_000L);
        scheduler.schedule(id, VolcanoState.ACTIVE, nearActive, 1_000L);
        assertEquals(1, scheduler.size());
        long due = scheduler.nextDueTick(id).orElseThrow();
        assertTrue(scheduler.pollDue(due - 1L, 4).isEmpty());
        assertEquals(List.of(id), scheduler.pollDue(due, 4));
        assertEquals(0, scheduler.size());
    }

    @Test
    void longSimulationIsDeterministicAndShowsBuildupEruptionAndRelaxation() {
        UUID id = UUID.fromString("b83e1ce4-4af8-48a8-a207-6e2357739625");
        VolcanoSavedData first = seededLifecycleData(id);
        VolcanoSavedData second = seededLifecycleData(id);
        VolcanoManager firstManager = manager(first, highTectonics());
        VolcanoManager secondManager = manager(second, highTectonics());

        boolean sawActive = false;
        boolean sawErupting = false;
        boolean sawPostEruptionRelaxation = false;
        double eruptionPeakPressure = 0.0;

        for (int day = 0; day < 80; day++) {
            VolcanoState state = firstManager.advance(77L, id, DAY_TICKS, 0.0);
            VolcanoState secondState = secondManager.advance(77L, id, DAY_TICKS, 0.0);
            assertEquals(state, secondState);
            assertEquals(first.chamber(id).orElseThrow(), second.chamber(id).orElseThrow());

            if (state == VolcanoState.ACTIVE) {
                sawActive = true;
            }
            if (state == VolcanoState.ERUPTING) {
                sawErupting = true;
                eruptionPeakPressure = Math.max(
                        eruptionPeakPressure,
                        first.chamber(id).orElseThrow().pressureMegapascals());
            } else if (sawErupting
                    && first.chamber(id).orElseThrow().pressureMegapascals() < eruptionPeakPressure) {
                sawPostEruptionRelaxation = true;
            }
        }

        assertTrue(sawActive, "high volcanic supply should eventually activate the site");
        assertTrue(sawErupting, "continued buildup should eventually cross the eruption threshold");
        assertTrue(sawPostEruptionRelaxation, "an eruption must vent pressure before the next buildup cycle");
    }

    private static VolcanoSavedData seededLifecycleData(UUID id) {
        VolcanoSavedData data = new VolcanoSavedData();
        data.register(site(id, new BlockPos(1_024, 90, 2_048), VolcanoType.STRATOVOLCANO, VolcanoState.DORMANT));
        data.updateLifecycle(id, VolcanoState.DORMANT, chamber(6.0, 90.0, 0.04, 1_210.0, 0.45));
        return data;
    }

    private static VolcanoManager manager(VolcanoSavedData data, TectonicService tectonics) {
        return new VolcanoManager(data, tectonics);
    }

    private static TectonicService highTectonics() {
        return (seed, x, z) -> sample(TectonicContext.CONVERGENT, 0.80, 0.92);
    }

    private static TectonicSample sample(TectonicContext context, double stress, double potential) {
        return new TectonicSample(41L, 42L, context, stress, potential, 128.0, 1.0, 0.0);
    }

    private static MagmaChamber chamber(
            double volume,
            double pressure,
            double gas,
            double temperature,
            double replenishment
    ) {
        return new MagmaChamber(
                new MagmaComposition(0.62, 0.55),
                volume,
                pressure,
                gas,
                temperature,
                replenishment);
    }

    private static VolcanoSite site(
            UUID id,
            BlockPos center,
            VolcanoType type,
            VolcanoState state
    ) {
        return new VolcanoSite(
                id,
                center,
                type,
                state,
                TectonicContext.CONVERGENT,
                41L,
                42L,
                0.90);
    }
}
