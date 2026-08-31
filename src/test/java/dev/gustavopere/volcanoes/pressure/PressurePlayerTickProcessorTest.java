package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

final class PressurePlayerTickProcessorTest {
    private static final PressureExposureConfig IMMEDIATE = new PressureExposureConfig(
            0, 0.50, 1.00, 2.00, 0.80, 0.50, 2.0);

    @Test
    void dryPlayerSkipsWaterLookupAndStillResetsExposure() {
        AtomicInteger waterQueries = new AtomicInteger();
        AtomicReference<PressureEntityEffectPlan> applied = new AtomicReference<>();
        PressurePlayerTickProcessor processor = new PressurePlayerTickProcessor(
                coordinator(),
                (context, tick) -> {
                    waterQueries.incrementAndGet();
                    return new WaterDepthSample(40.0, true);
                });
        PressurePlayerTickSnapshot player = snapshot(false);

        PressureEntityUpdate update = processor.tick(player, 10L, applied::set);

        assertEquals(0, waterQueries.get());
        assertEquals(PressureExposureStage.NORMAL, update.exposure().stage());
        assertEquals(PressureEntityEffectPlan.none(), applied.get());
    }

    @Test
    void immersedPlayerSamplesWaterOnceAndAppliesEffectsOnce() {
        AtomicInteger waterQueries = new AtomicInteger();
        AtomicInteger effectApplications = new AtomicInteger();
        AtomicReference<PressureEntityEffectPlan> applied = new AtomicReference<>();
        PressurePlayerTickProcessor processor = new PressurePlayerTickProcessor(
                coordinator(),
                (context, tick) -> {
                    waterQueries.incrementAndGet();
                    return new WaterDepthSample(40.0, true);
                });

        PressureEntityUpdate update = processor.tick(snapshot(true), 20L, plan -> {
            effectApplications.incrementAndGet();
            applied.set(plan);
        });

        assertEquals(1, waterQueries.get());
        assertEquals(1, effectApplications.get());
        assertEquals(PressureExposureStage.BAROTRAUMA, update.exposure().stage());
        assertEquals(update.effects(), applied.get());
    }

    @Test
    void contextualAtmosphereOverrideSamplesResolvedWaterSurfaceAndWinsOverBuiltInCurve() {
        AtomicReference<Double> sampledY = new AtomicReference<>();
        PressurePlayerTickProcessor processor = new PressurePlayerTickProcessor(
                coordinator(),
                (context, tick) -> new WaterDepthSample(40.0, true),
                (context, atmosphericSampleY) -> {
                    sampledY.set(atmosphericSampleY);
                    return OptionalDouble.of(0.72);
                });

        PressureEntityUpdate update = processor.tick(snapshot(true), 25L, ignored -> { });

        assertEquals(60.0, sampledY.get(), 1.0e-9,
                "contextual host atmosphere must be sampled at the resolved water surface");
        assertEquals(0.72, update.externalPressure().atmosphericAtm(), 1.0e-9,
                "a valid contextual host atmosphere must override the built-in dimension curve");
        assertTrue(update.externalPressure().totalExternalAtm() > update.externalPressure().atmosphericAtm(),
                "hydrostatic pressure must still be layered on top of the contextual atmosphere");
    }

    @Test
    void brokenContextualAtmosphereFallsBackWithoutCrashingPressureTick() {
        PressurePlayerTickProcessor processor = new PressurePlayerTickProcessor(
                coordinator(),
                (context, tick) -> fail("dry player must not query water depth"),
                (context, atmosphericSampleY) -> {
                    throw new LinkageError("optional host changed");
                });

        PressureEntityUpdate update = processor.tick(snapshot(false), 26L, ignored -> { });

        assertEquals(1.0, update.externalPressure().atmosphericAtm(), 1.0e-9);
        assertEquals(PressureExposureStage.NORMAL, update.exposure().stage());
    }

    @Test
    void hostResolvedContributionReachesCoordinatorAndDebitsOnce() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution hostSuit = ProtectionContribution.consumable(
                "host:pressure-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 5.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        AtomicReference<PressureEntityEffectPlan> applied = new AtomicReference<>();
        PressurePlayerTickProcessor processor = new PressurePlayerTickProcessor(
                coordinator(),
                (context, tick) -> new WaterDepthSample(40.0, true));
        PressurePlayerTickSnapshot player = new PressurePlayerTickSnapshot(
                context(),
                true,
                List.of(hostSuit));

        PressureEntityUpdate update = processor.tick(player, 30L, applied::set);

        assertEquals(PressureExposureStage.NORMAL, update.exposure().stage());
        assertEquals(0.0, update.environment().protectedOverpressureAtm(), 1.0e-9);
        assertEquals(1, debits.get());
        assertEquals(PressureEntityEffectPlan.none(), applied.get());
    }

    @Test
    void malformedSelectedPressureAuthorityFallsBackThroughPlayerProcessorWithoutTryingLowerExternalProvider() {
        AtomicInteger lowerCalls = new AtomicInteger();
        AtomicInteger fallbackCalls = new AtomicInteger();
        AtmosphericPressureResolver pressureResolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> {
            fallbackCalls.incrementAndGet();
            return 0.83;
        });
        pressureResolver.register(new AtmosphericPressureProvider() {
            @Override
            public String id() {
                return "lower";
            }

            @Override
            public int priority() {
                return 10;
            }

            @Override
            public OptionalDouble pressureAtm(String dimensionId, double altitudeY) {
                lowerCalls.incrementAndGet();
                return OptionalDouble.of(0.70);
            }
        });
        pressureResolver.register(new AtmosphericPressureProvider() {
            @Override
            public String id() {
                return "broken";
            }

            @Override
            public int priority() {
                return 100;
            }

            @Override
            public OptionalDouble pressureAtm(String dimensionId, double altitudeY) {
                return OptionalDouble.of(Double.NEGATIVE_INFINITY);
            }
        });
        PressureEntityCoordinator coordinator = new PressureEntityCoordinator(
                PressureService.fallback(),
                pressureResolver,
                new EnclosedEnvironmentResolver(List.of(), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                IMMEDIATE,
                1_000.0);
        PressurePlayerTickProcessor processor = new PressurePlayerTickProcessor(
                coordinator,
                (context, tick) -> fail("dry player must not query water depth"));

        PressureEntityUpdate update = processor.tick(snapshot(false), 40L, ignored -> { });

        assertEquals(0.83, update.externalPressure().atmosphericAtm(), 1.0e-9);
        assertEquals(0, lowerCalls.get(),
                "malformed selected authority must not fall through to a different external pressure model");
        assertEquals(1, fallbackCalls.get(), "built-in pressure must be sampled exactly once");
        assertEquals(PressureExposureStage.NORMAL, update.exposure().stage());
    }

    private static PressureEntityCoordinator coordinator() {
        return new PressureEntityCoordinator(
                PressureService.fallback(),
                (dimensionId, altitudeY) -> 1.0,
                new EnclosedEnvironmentResolver(List.of(), 5, 64),
                new EquipmentProtectionResolver(List.of()),
                new PressureExposureTracker(),
                IMMEDIATE,
                1_000.0);
    }

    private static PressurePlayerTickSnapshot snapshot(boolean immersedInWater) {
        return new PressurePlayerTickSnapshot(context(), immersedInWater);
    }

    private static PressureEntityContext context() {
        return new PressureEntityContext(
                UUID.randomUUID(),
                Optional.empty(),
                "minecraft:overworld",
                0.5,
                20.0,
                0.5,
                List.of());
    }
}
