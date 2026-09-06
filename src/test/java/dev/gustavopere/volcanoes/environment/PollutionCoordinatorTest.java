package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

final class PollutionCoordinatorTest {
    @Test
    void authoritativeExternalAdapterPreventsInternalDoubleCounting() {
        AtomicInteger externalPublishes = new AtomicInteger();
        AtomicInteger internalFallback = new AtomicInteger();
        PollutionAdapter adapter = new PollutionAdapter() {
            @Override
            public boolean isAuthoritative() {
                return true;
            }

            @Override
            public void publish(PollutionEmission emission) {
                externalPublishes.incrementAndGet();
            }

            @Override
            public Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z) {
                return Optional.empty();
            }
        };
        PollutionCoordinator coordinator = new PollutionCoordinator(adapter);
        PollutionEmission emission = emission(
                UUID.fromString("00000000-0000-0000-0000-000000000201"),
                new PollutionLoad(20.0, 4.0, 2.0));

        assertEquals(PollutionRoute.EXTERNAL_AUTHORITY,
                coordinator.route(emission, ignored -> internalFallback.incrementAndGet()));
        assertEquals(1, externalPublishes.get());
        assertEquals(0, internalFallback.get());

        assertEquals(PollutionRoute.DUPLICATE,
                coordinator.route(emission, ignored -> internalFallback.incrementAndGet()));
        assertEquals(1, externalPublishes.get());
        assertEquals(0, internalFallback.get());
    }

    @Test
    void standaloneFallbackOwnsPollutionWhenExternalAuthorityIsAbsent() {
        AtomicInteger internalFallback = new AtomicInteger();
        PollutionCoordinator coordinator = new PollutionCoordinator(PollutionAdapter.none());
        PollutionEmission emission = emission(
                UUID.fromString("00000000-0000-0000-0000-000000000202"),
                new PollutionLoad(15.0, 3.0, 1.0));

        assertEquals(PollutionRoute.INTERNAL_FALLBACK,
                coordinator.route(emission, ignored -> internalFallback.incrementAndGet()));
        assertEquals(1, internalFallback.get());
    }

    @Test
    void deduplicationMemoryRemainsBoundedWithoutExplicitForgetCalls() {
        PollutionCoordinator coordinator = new PollutionCoordinator(PollutionAdapter.none(), 3);
        AtomicInteger fallbackCalls = new AtomicInteger();

        for (int index = 0; index < 10; index++) {
            PollutionEmission emission = emission(
                    new UUID(0L, 10_000L + index),
                    PollutionLoad.none());
            assertEquals(PollutionRoute.INTERNAL_FALLBACK,
                    coordinator.route(emission, ignored -> fallbackCalls.incrementAndGet()));
        }
        assertEquals(10, fallbackCalls.get());
        assertEquals(3, coordinator.rememberedEmissionCount());

        PollutionEmission newest = emission(new UUID(0L, 10_009L), PollutionLoad.none());
        assertEquals(PollutionRoute.DUPLICATE,
                coordinator.route(newest, ignored -> fallbackCalls.incrementAndGet()));
        assertEquals(10, fallbackCalls.get());

        PollutionEmission evictedOldest = emission(new UUID(0L, 10_000L), PollutionLoad.none());
        assertEquals(PollutionRoute.INTERNAL_FALLBACK,
                coordinator.route(evictedOldest, ignored -> fallbackCalls.incrementAndGet()));
        assertEquals(11, fallbackCalls.get());
        assertEquals(3, coordinator.rememberedEmissionCount());
    }

    @Test
    void failedExternalPublishRollsBackDedupReservationSoEmissionCanRetry() {
        AtomicInteger publishAttempts = new AtomicInteger();
        PollutionAdapter adapter = new PollutionAdapter() {
            @Override
            public boolean isAuthoritative() {
                return true;
            }

            @Override
            public void publish(PollutionEmission emission) {
                if (publishAttempts.incrementAndGet() == 1) {
                    throw new IllegalStateException("simulated external failure");
                }
            }

            @Override
            public Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z) {
                return Optional.empty();
            }
        };
        PollutionCoordinator coordinator = new PollutionCoordinator(adapter, 4);
        PollutionEmission emission = emission(
                UUID.fromString("00000000-0000-0000-0000-000000000203"),
                PollutionLoad.none());

        assertThrows(IllegalStateException.class, () -> coordinator.route(emission, ignored -> fail()));
        assertEquals(0, coordinator.rememberedEmissionCount());

        assertEquals(PollutionRoute.EXTERNAL_AUTHORITY,
                coordinator.route(emission, ignored -> fail()));
        assertEquals(2, publishAttempts.get());
        assertEquals(1, coordinator.rememberedEmissionCount());
        assertEquals(PollutionRoute.DUPLICATE,
                coordinator.route(emission, ignored -> fail()));
    }

    @Test
    void failedInternalFallbackAlsoRollsBackDedupReservation() {
        AtomicInteger fallbackAttempts = new AtomicInteger();
        PollutionCoordinator coordinator = new PollutionCoordinator(PollutionAdapter.none(), 4);
        PollutionEmission emission = emission(
                UUID.fromString("00000000-0000-0000-0000-000000000204"),
                PollutionLoad.none());

        assertThrows(IllegalArgumentException.class, () -> coordinator.route(emission, ignored -> {
            fallbackAttempts.incrementAndGet();
            throw new IllegalArgumentException("simulated fallback failure");
        }));
        assertEquals(0, coordinator.rememberedEmissionCount());

        assertEquals(PollutionRoute.INTERNAL_FALLBACK,
                coordinator.route(emission, ignored -> fallbackAttempts.incrementAndGet()));
        assertEquals(2, fallbackAttempts.get());
        assertEquals(1, coordinator.rememberedEmissionCount());
    }

    @Test
    void acidRainRequiresBothAcidifyingLoadAndPrecipitation() {
        AcidRainModel model = new AcidRainModel(10.0, 0.10);
        assertFalse(model.isAcidRain(new PollutionLoad(20.0, 0.0, 0.0), 0.0));
        assertFalse(model.isAcidRain(new PollutionLoad(5.0, 0.0, 0.0), 1.0));
        assertTrue(model.isAcidRain(new PollutionLoad(20.0, 0.0, 0.0), 0.5));
    }

    @Test
    void zeroThresholdsStillRequireActualAcidLoadAndPrecipitation() {
        AcidRainModel model = new AcidRainModel(0.0, 0.0);

        assertFalse(model.isAcidRain(PollutionLoad.none(), 0.0));
        assertFalse(model.isAcidRain(new PollutionLoad(1.0, 0.0, 0.0), 0.0));
        assertFalse(model.isAcidRain(PollutionLoad.none(), 0.25));
        assertTrue(model.isAcidRain(new PollutionLoad(1.0, 0.0, 0.0), 0.25));
    }

    private static PollutionEmission emission(UUID id, PollutionLoad load) {
        return new PollutionEmission(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                load);
    }
}
