package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

final class PressureEntityCoordinatorTest {
    private static final PressureExposureConfig IMMEDIATE_HAZARD = new PressureExposureConfig(
            0, 0.50, 1.00, 2.00, 0.80, 0.50, 2.0);

    @Test
    void openWaterUsesConnectedSurfaceAltitudeAndFeedsHydrostaticExposure() {
        AtomicReference<Double> sampledAltitude = new AtomicReference<>();
        AtmosphericPressureLookup atmosphere = (dimensionId, altitudeY) -> {
            sampledAltitude.set(altitudeY);
            return 0.90;
        };
        PressureEntityCoordinator coordinator = new PressureEntityCoordinator(
                PressureService.fallback(),
                atmosphere,
                new EnclosedEnvironmentResolver(List.of(), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                IMMEDIATE_HAZARD,
                1_000.0);
        PressureEntityContext context = context(40.0);

        PressureEntityUpdate update = coordinator.update(
                context,
                new WaterDepthSample(30.0, true),
                200L);

        assertEquals(70.0, sampledAltitude.get(), 1.0e-9,
                "atmospheric baseline must be sampled at the connected water surface");
        assertEquals(0.90, update.externalPressure().atmosphericAtm(), 1.0e-9);
        assertTrue(update.externalPressure().hydrostaticAtm() > 2.0);
        assertEquals(PressureExposureStage.BAROTRAUMA, update.exposure().stage());
        assertFalse(update.environment().sealedInterior());
    }

    @Test
    void unresolvedWaterHeadDoesNotInventAFreeSurfaceAltitude() {
        AtomicReference<Double> sampledAltitude = new AtomicReference<>();
        AtmosphericPressureLookup atmosphere = (dimensionId, altitudeY) -> {
            sampledAltitude.set(altitudeY);
            return 1.0;
        };
        PressureEntityCoordinator coordinator = new PressureEntityCoordinator(
                PressureService.fallback(),
                atmosphere,
                new EnclosedEnvironmentResolver(List.of(), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                IMMEDIATE_HAZARD,
                1_000.0);

        PressureEntityUpdate update = coordinator.update(
                context(40.0),
                new WaterDepthSample(30.0, false),
                225L);

        assertEquals(40.0, sampledAltitude.get(), 1.0e-9,
                "unresolved water head must not be treated as a proven atmospheric surface");
        assertTrue(update.externalPressure().hydrostaticAtm() > 2.0,
                "the proven water head still contributes hydrostatic pressure");
    }

    @Test
    void hostResolvedPressureRatingFlowsThroughCoordinatorWithoutRewritingPhysicalPressure() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution hostSuit = ProtectionContribution.consumable(
                "host:pressure-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 4.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        PressureEntityCoordinator coordinator = new PressureEntityCoordinator(
                PressureService.fallback(),
                (dimensionId, altitudeY) -> 1.0,
                new EnclosedEnvironmentResolver(List.of(), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                IMMEDIATE_HAZARD,
                1_000.0);

        PressureEntityUpdate update = coordinator.update(
                context(20.0),
                new WaterDepthSample(30.0, true),
                250L,
                List.of(hostSuit));

        assertTrue(update.externalPressure().hydrostaticAtm() > 2.0);
        assertEquals(update.externalPressure().totalExternalAtm(), update.environment().externalPressureAtm(), 1.0e-9);
        assertEquals(update.externalPressure().totalExternalAtm(), update.environment().experiencedPressureAtm(), 1.0e-9);
        assertEquals(0.0, update.environment().protectedOverpressureAtm(), 1.0e-9);
        assertEquals(PressureExposureStage.NORMAL, update.exposure().stage());
        assertEquals(1, debits.get(), "one accepted coordinator update must debit the host contribution once");
    }

    @Test
    void reliableDryInteriorSuppressesExternalWaterExposureBeforeEquipment() {
        EnclosedEnvironmentProvider cabin = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 100;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                return Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
            }
        };
        PressureEntityCoordinator coordinator = new PressureEntityCoordinator(
                PressureService.fallback(),
                (dimensionId, altitudeY) -> 1.0,
                new EnclosedEnvironmentResolver(List.of(cabin), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                IMMEDIATE_HAZARD,
                1_000.0);

        PressureEntityUpdate update = coordinator.update(
                context(20.0),
                new WaterDepthSample(50.0, true),
                300L);

        assertTrue(update.environment().sealedInterior());
        assertEquals(0.0, update.environment().protectedOverpressureAtm(), 1.0e-9);
        assertEquals(PressureExposureStage.NORMAL, update.exposure().stage());
        assertEquals(PressureEntityEffectPlan.none(), update.effects());
    }

    @Test
    void alreadyConstructedCoordinatorReadsLatestExposureConfiguration() {
        AtomicReference<PressureExposureConfig> config = new AtomicReference<>(IMMEDIATE_HAZARD);
        PressureEntityCoordinator coordinator = new PressureEntityCoordinator(
                PressureService.fallback(),
                (dimensionId, altitudeY) -> 1.0,
                new EnclosedEnvironmentResolver(List.of(), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                config::get,
                1_000.0);
        PressureEntityContext context = context(20.0);

        PressureEntityUpdate hazardous = coordinator.update(
                context,
                new WaterDepthSample(30.0, true),
                400L);
        assertEquals(PressureExposureStage.BAROTRAUMA, hazardous.exposure().stage());

        config.set(new PressureExposureConfig(
                0, 10.0, 11.0, 12.0, 0.8, 0.5, 2.0));
        PressureEntityUpdate reconfigured = coordinator.update(
                context,
                new WaterDepthSample(30.0, true),
                401L);
        assertEquals(PressureExposureStage.NORMAL, reconfigured.exposure().stage());
    }

    private static PressureEntityContext context(double y) {
        return new PressureEntityContext(
                UUID.randomUUID(),
                Optional.empty(),
                "minecraft:overworld",
                10.5,
                y,
                -4.5,
                List.of());
    }
}
