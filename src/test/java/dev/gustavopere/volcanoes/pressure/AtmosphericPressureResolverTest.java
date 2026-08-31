package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmosphericPressureResolverTest {
    private static final double EPSILON = 1.0e-9;

    @Test
    void highestPriorityApplicableProviderIsTheOnlyAuthority() {
        AtomicInteger lowerCalls = new AtomicInteger();
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 1.0);
        resolver.register(new TestProvider("lower", 10, (dimensionId, altitudeY) -> {
            lowerCalls.incrementAndGet();
            return OptionalDouble.of(0.95);
        }));
        resolver.register(new TestProvider("higher", 100, (dimensionId, altitudeY) -> OptionalDouble.of(0.72)));

        assertEquals(0.72, resolver.pressureAtm("minecraft:overworld", 300.0), EPSILON);
        assertEquals(0, lowerCalls.get(), "lower-priority providers must not be sampled after authority is selected");
    }

    @Test
    void equalPriorityProvidersUseNormalizedIdAsDeterministicTieBreaker() {
        AtmosphericPressureResolver firstOrder = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 1.0);
        firstOrder.register(new TestProvider("zeta", 50, (dimensionId, altitudeY) -> OptionalDouble.of(0.7)));
        firstOrder.register(new TestProvider(" alpha ", 50, (dimensionId, altitudeY) -> OptionalDouble.of(0.8)));

        AtmosphericPressureResolver reverseOrder = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 1.0);
        reverseOrder.register(new TestProvider("alpha", 50, (dimensionId, altitudeY) -> OptionalDouble.of(0.8)));
        reverseOrder.register(new TestProvider("zeta", 50, (dimensionId, altitudeY) -> OptionalDouble.of(0.7)));

        assertEquals(0.8, firstOrder.pressureAtm("minecraft:overworld", 64.0), EPSILON);
        assertEquals(0.8, reverseOrder.pressureAtm("minecraft:overworld", 64.0), EPSILON);
    }

    @Test
    void invalidLookupInputsAreRejectedBeforeProvidersRun() {
        AtomicInteger providerCalls = new AtomicInteger();
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 1.0);
        resolver.register(new TestProvider("external", 100, (dimensionId, altitudeY) -> {
            providerCalls.incrementAndGet();
            return OptionalDouble.of(0.8);
        }));

        assertThrows(NullPointerException.class, () -> resolver.pressureAtm(null, 64.0));
        assertThrows(IllegalArgumentException.class, () -> resolver.pressureAtm("   ", 64.0));
        assertThrows(IllegalArgumentException.class,
                () -> resolver.pressureAtm(" minecraft:overworld ", 64.0));
        assertThrows(IllegalArgumentException.class,
                () -> resolver.pressureAtm("minecraft:overworld", Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> resolver.pressureAtm("minecraft:overworld", Double.POSITIVE_INFINITY));
        assertEquals(0, providerCalls.get(), "invalid queries must never reach external authorities");
    }

    @Test
    void providersMayDeclineAndBuiltInFallbackRemainsAuthoritative() {
        AtomicInteger fallbackCalls = new AtomicInteger();
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> {
            fallbackCalls.incrementAndGet();
            return 0.88;
        });
        resolver.register(new TestProvider("not-applicable", 100, (dimensionId, altitudeY) -> OptionalDouble.empty()));

        assertEquals(0.88, resolver.pressureAtm("minecraft:the_end", 128.0), EPSILON);
        assertEquals(1, fallbackCalls.get());
    }

    @Test
    void registrationAfterConstructionChangesAuthorityWithoutRebuildingConsumers() {
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 1.0);
        AtmosphericPressureLookup consumerReference = resolver;

        assertEquals(1.0, consumerReference.pressureAtm("minecraft:overworld", 64.0), EPSILON);

        resolver.register(new TestProvider("external", 50, (dimensionId, altitudeY) -> OptionalDouble.of(0.81)));

        assertEquals(0.81, consumerReference.pressureAtm("minecraft:overworld", 64.0), EPSILON);
    }

    @Test
    void normalizedDuplicateProviderIdsAreRejected() {
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 1.0);
        resolver.register(new TestProvider(" host ", 10, (dimensionId, altitudeY) -> OptionalDouble.empty()));

        assertThrows(
                IllegalArgumentException.class,
                () -> resolver.register(new TestProvider(
                        "host",
                        20,
                        (dimensionId, altitudeY) -> OptionalDouble.empty())));
    }

    @Test
    void providerLinkageFailureFallsBackToBuiltInWithoutTryingLowerExternalAuthority() {
        AtomicInteger lowerCalls = new AtomicInteger();
        AtomicInteger fallbackCalls = new AtomicInteger();
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> {
            fallbackCalls.incrementAndGet();
            return 0.91;
        });
        resolver.register(new TestProvider("lower", 10, (dimensionId, altitudeY) -> {
            lowerCalls.incrementAndGet();
            return OptionalDouble.of(0.72);
        }));
        resolver.register(new TestProvider("broken", 100, (dimensionId, altitudeY) -> {
            throw new NoSuchMethodError("simulated optional atmospheric host API drift");
        }));

        assertEquals(0.91, resolver.pressureAtm("minecraft:overworld", 64.0), EPSILON);
        assertEquals(0, lowerCalls.get(), "broken higher authority must not fall through to another external model");
        assertEquals(1, fallbackCalls.get(), "built-in pressure must be the fail-closed authority");
    }

    @Test
    void nullProviderResultFallsBackToBuiltInWithoutTryingLowerExternalAuthority() {
        AtomicInteger lowerCalls = new AtomicInteger();
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> 0.93);
        resolver.register(new TestProvider("lower", 10, (dimensionId, altitudeY) -> {
            lowerCalls.incrementAndGet();
            return OptionalDouble.of(0.70);
        }));
        resolver.register(new TestProvider("broken-null", 100, (dimensionId, altitudeY) -> null));

        assertEquals(0.93, resolver.pressureAtm("minecraft:overworld", 64.0), EPSILON);
        assertEquals(0, lowerCalls.get(), "null authoritative response must not switch to another external model");
    }

    @Test
    void invalidApplicableProviderFallsBackToBuiltInWithoutTryingLowerExternalAuthority() {
        for (double invalidPressure : new double[]{
                Double.NaN,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                -0.01
        }) {
            AtomicInteger lowerCalls = new AtomicInteger();
            AtomicInteger fallbackCalls = new AtomicInteger();
            AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> {
                fallbackCalls.incrementAndGet();
                return 0.94;
            });
            resolver.register(new TestProvider("lower", 10, (dimensionId, altitudeY) -> {
                lowerCalls.incrementAndGet();
                return OptionalDouble.of(0.70);
            }));
            resolver.register(new TestProvider(
                    "broken-" + Double.toString(invalidPressure),
                    100,
                    (dimensionId, altitudeY) -> OptionalDouble.of(invalidPressure)));

            assertEquals(0.94, resolver.pressureAtm("minecraft:overworld", 64.0), EPSILON);
            assertEquals(0, lowerCalls.get(), "invalid selected authority must not switch to another external model");
            assertEquals(1, fallbackCalls.get(), "built-in pressure must be the fail-closed authority");
        }
    }

    @Test
    void invalidFallbackIsRejectedToo() {
        AtmosphericPressureResolver resolver = new AtmosphericPressureResolver((dimensionId, altitudeY) -> -1.0);

        assertThrows(
                IllegalStateException.class,
                () -> resolver.pressureAtm("minecraft:overworld", 64.0));
    }

    private record TestProvider(
            String id,
            int priority,
            Sampler sampler
    ) implements AtmosphericPressureProvider {
        @Override
        public OptionalDouble pressureAtm(String dimensionId, double altitudeY) {
            return sampler.sample(dimensionId, altitudeY);
        }
    }

    @FunctionalInterface
    private interface Sampler {
        OptionalDouble sample(String dimensionId, double altitudeY);
    }
}
