package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EnclosedInvalidationThreadBoundaryTest {
    @Test
    void hostInvalidationIsDeferredUntilTheNextResolutionPass() {
        AtomicInteger calls = new AtomicInteger();
        UUID entityId = UUID.randomUUID();
        EnclosedEnvironmentResolver resolver = resolver(calls, 32);
        EnclosedEnvironmentQuery query = query(entityId, Optional.empty());

        assertTrue(resolver.resolve(query, 0).isPresent());
        assertEquals(1, resolver.cachedSubjectCount());

        resolver.invalidateEntity(entityId);

        assertEquals(
                1,
                resolver.cachedSubjectCount(),
                "host callbacks must enqueue invalidation rather than mutate the resolve-owned LRU directly");

        assertTrue(resolver.resolve(query, 1).isPresent());
        assertEquals(2, calls.get(), "the next resolve must drain invalidations before reading the cache");
    }

    @Test
    void tooManyPendingSubjectInvalidationsCollapseToBoundedFullClear() {
        AtomicInteger calls = new AtomicInteger();
        UUID entityId = UUID.randomUUID();
        EnclosedEnvironmentResolver resolver = resolver(calls, 2);
        EnclosedEnvironmentQuery query = query(entityId, Optional.empty());

        assertTrue(resolver.resolve(query, 0).isPresent());
        assertEquals(1, calls.get());

        resolver.invalidateEntity(UUID.randomUUID());
        resolver.invalidateEntity(UUID.randomUUID());
        resolver.invalidateVehicle(UUID.randomUUID());

        assertTrue(resolver.resolve(query, 1).isPresent());
        assertEquals(
                2,
                calls.get(),
                "overflowing pending invalidations must conservatively clear the bounded cache");
    }

    private static EnclosedEnvironmentResolver resolver(AtomicInteger calls, int maxCacheEntries) {
        EnclosedEnvironmentProvider provider = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 100;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                calls.incrementAndGet();
                return Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
            }
        };
        return new EnclosedEnvironmentResolver(List.of(provider), 20, maxCacheEntries);
    }

    private static EnclosedEnvironmentQuery query(UUID entityId, Optional<UUID> vehicleId) {
        return new EnclosedEnvironmentQuery(
                entityId,
                vehicleId,
                "minecraft:overworld",
                0.25,
                64.25,
                0.25);
    }
}
