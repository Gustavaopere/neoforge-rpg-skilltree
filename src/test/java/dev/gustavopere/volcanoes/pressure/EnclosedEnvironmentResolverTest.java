package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.environment.AtmosphereState;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

final class EnclosedEnvironmentResolverTest {
    @Test
    void highestPriorityApplicableProviderWinsAndMayCarryInternalAtmosphere() {
        EnclosedEnvironment low = EnclosedEnvironment.protectedDry(1.2, Optional.empty());
        AtmosphereState internalAtmosphere = AtmosphereState.standardOverworld();
        EnclosedEnvironment high = EnclosedEnvironment.protectedDry(0.8, Optional.of(internalAtmosphere));

        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(List.of(
                provider(10, Optional.of(low)),
                provider(100, Optional.of(high))), 20, 32);

        Optional<EnclosedEnvironment> resolved = resolver.resolve(query(), 0);

        assertTrue(resolved.isPresent());
        assertEquals(0.8, resolved.orElseThrow().internalPressureAtm(), 1.0e-9);
        assertSame(internalAtmosphere, resolved.orElseThrow().internalAtmosphere().orElseThrow());
    }

    @Test
    void absentProvidersFallBackToOutsideEnvironment() {
        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(
                List.of(provider(10, Optional.empty())), 20, 32);

        assertTrue(resolver.resolve(query(), 0).isEmpty());
    }

    @Test
    void providersRegisteredAfterConstructionInvalidateCacheAndReorderAuthority() {
        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(List.of(), 100, 32);
        EnclosedEnvironmentQuery query = query();
        EnclosedEnvironment low = EnclosedEnvironment.protectedDry(1.2, Optional.empty());
        EnclosedEnvironment high = EnclosedEnvironment.protectedDry(0.7, Optional.empty());

        assertTrue(resolver.resolve(query, 0).isEmpty(), "outside fallback should be cached initially");
        assertEquals(1, resolver.cachedSubjectCount());

        resolver.register(provider(10, Optional.of(low)));
        assertEquals(0, resolver.cachedSubjectCount(), "registration must invalidate stale outside results");
        assertEquals(1.2, resolver.resolve(query, 1).orElseThrow().internalPressureAtm(), 1.0e-9);

        resolver.register(provider(100, Optional.of(high)));
        assertEquals(0, resolver.cachedSubjectCount(), "new higher-priority authority must invalidate prior cache");
        assertEquals(0.7, resolver.resolve(query, 2).orElseThrow().internalPressureAtm(), 1.0e-9);
    }

    @Test
    void unreliableOrFloodedApplicableProviderFailsClosedInsteadOfTryingLowerPriority() {
        AtomicInteger lowerCalls = new AtomicInteger();
        EnclosedEnvironmentProvider lower = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 10;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                lowerCalls.incrementAndGet();
                return Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
            }
        };

        EnclosedEnvironmentResolver unreliable = new EnclosedEnvironmentResolver(List.of(
                lower,
                provider(100, Optional.of(new EnclosedEnvironment(true, true, false, 1.0, Optional.empty())))), 20, 32);
        assertTrue(unreliable.resolve(query(), 0).isEmpty());
        assertEquals(0, lowerCalls.get());

        EnclosedEnvironmentResolver flooded = new EnclosedEnvironmentResolver(List.of(
                lower,
                provider(100, Optional.of(new EnclosedEnvironment(true, false, true, 1.0, Optional.empty())))), 20, 32);
        assertTrue(flooded.resolve(query(), 0).isEmpty());
        assertEquals(0, lowerCalls.get());
    }

    @Test
    void providerRuntimeFailureFailsClosedInsteadOfTryingLowerPriority() {
        AtomicInteger lowerCalls = new AtomicInteger();
        EnclosedEnvironmentProvider lower = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 10;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                lowerCalls.incrementAndGet();
                return Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
            }
        };
        EnclosedEnvironmentProvider broken = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 100;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                throw new IllegalStateException("host API unavailable");
            }
        };

        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(List.of(lower, broken), 20, 32);

        assertTrue(resolver.resolve(query(), 0).isEmpty());
        assertEquals(0, lowerCalls.get(), "a broken authoritative provider must fail closed, not fall through");
    }

    @Test
    void vehicleCacheIsScopedByOccupantAndSampledBlockAndExpiresAfterBoundedTtl() {
        AtomicInteger calls = new AtomicInteger();
        EnclosedEnvironmentProvider provider = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 50;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                calls.incrementAndGet();
                return Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
            }
        };
        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(List.of(provider), 10, 32);
        UUID entityA = UUID.randomUUID();
        UUID entityB = UUID.randomUUID();
        UUID vehicle = UUID.randomUUID();

        EnclosedEnvironmentQuery first = new EnclosedEnvironmentQuery(
                entityA, Optional.of(vehicle), "minecraft:overworld", 0.2, 64.1, 0.2);
        EnclosedEnvironmentQuery sameOccupantSameBlock = new EnclosedEnvironmentQuery(
                entityA, Optional.of(vehicle), "minecraft:overworld", 0.8, 64.9, 0.8);
        EnclosedEnvironmentQuery differentOccupantSameVehicleSameBlock = new EnclosedEnvironmentQuery(
                entityB, Optional.of(vehicle), "minecraft:overworld", 0.8, 64.9, 0.8);
        EnclosedEnvironmentQuery sameOccupantDifferentBlock = new EnclosedEnvironmentQuery(
                entityA, Optional.of(vehicle), "minecraft:overworld", 5.0, 64.0, 5.0);

        assertTrue(resolver.resolve(first, 0).isPresent());
        assertTrue(resolver.resolve(sameOccupantSameBlock, 5).isPresent());
        assertEquals(1, calls.get(), "same occupant, vehicle and sampled block may reuse the bounded cache");

        assertTrue(resolver.resolve(differentOccupantSameVehicleSameBlock, 5).isPresent());
        assertEquals(2, calls.get(), "different occupants must not inherit a provider decision from each other");

        assertTrue(resolver.resolve(sameOccupantDifferentBlock, 5).isPresent());
        assertEquals(3, calls.get(), "moving to another block must re-query spatially sensitive providers");

        assertTrue(resolver.resolve(sameOccupantDifferentBlock, 16).isPresent());
        assertEquals(4, calls.get(), "expired cache must re-query providers");

        resolver.invalidateVehicle(vehicle);
        resolver.resolve(sameOccupantDifferentBlock, 17);
        assertEquals(5, calls.get());
    }

    @Test
    void entityCacheDoesNotFollowPlayerAcrossDifferentBlocks() {
        AtomicInteger calls = new AtomicInteger();
        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(List.of(new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 10;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                calls.incrementAndGet();
                return query.x() < 1.0
                        ? Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()))
                        : Optional.empty();
            }
        }), 20, 32);
        UUID entity = UUID.randomUUID();

        EnclosedEnvironmentQuery inside = new EnclosedEnvironmentQuery(
                entity, Optional.empty(), "minecraft:overworld", 0.2, 64.0, 0.2);
        EnclosedEnvironmentQuery outside = new EnclosedEnvironmentQuery(
                entity, Optional.empty(), "minecraft:overworld", 2.0, 64.0, 0.2);

        assertTrue(resolver.resolve(inside, 0).isPresent());
        assertTrue(resolver.resolve(outside, 1).isEmpty());
        assertEquals(2, calls.get(), "entity movement across blocks must not inherit sealed-state cache");
    }

    @Test
    void environmentContractRejectsInvalidPressure() {
        assertThrows(IllegalArgumentException.class,
                () -> new EnclosedEnvironment(true, true, true, -0.1, Optional.empty()));
    }

    private static EnclosedEnvironmentQuery query() {
        return new EnclosedEnvironmentQuery(
                UUID.randomUUID(), Optional.empty(), "minecraft:overworld", 0.0, 64.0, 0.0);
    }

    private static EnclosedEnvironmentProvider provider(int priority, Optional<EnclosedEnvironment> result) {
        return new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return priority;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                return result;
            }
        };
    }
}
