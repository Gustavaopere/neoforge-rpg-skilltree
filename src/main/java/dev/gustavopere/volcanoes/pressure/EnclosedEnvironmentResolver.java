package dev.gustavopere.volcanoes.pressure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/** Priority-ordered, bounded-cache resolution for optional enclosed-environment providers. */
public final class EnclosedEnvironmentResolver {
    private static final Comparator<EnclosedEnvironmentProvider> BY_PRIORITY_DESCENDING =
            Comparator.comparingInt(EnclosedEnvironmentProvider::priority).reversed();

    private volatile List<EnclosedEnvironmentProvider> providers;
    private final long ttlTicks;
    private final int maxCacheEntries;
    private final LinkedHashMap<CacheKey, CacheEntry> cache = new LinkedHashMap<>(64, 0.75f, true);
    private final Set<SubjectInvalidation> pendingInvalidations = ConcurrentHashMap.newKeySet();
    private final AtomicBoolean fullInvalidationPending = new AtomicBoolean();

    public EnclosedEnvironmentResolver(
            List<? extends EnclosedEnvironmentProvider> providers,
            long ttlTicks,
            int maxCacheEntries
    ) {
        Objects.requireNonNull(providers, "providers");
        if (ttlTicks < 0) {
            throw new IllegalArgumentException("ttlTicks must be non-negative");
        }
        if (maxCacheEntries <= 0) {
            throw new IllegalArgumentException("maxCacheEntries must be positive");
        }
        this.providers = sortedProviders(providers);
        this.ttlTicks = ttlTicks;
        this.maxCacheEntries = maxCacheEntries;
    }

    /**
     * Registers an optional provider during integration setup and invalidates all cached decisions.
     * Provider sorting happens here, never on the entity sampling path.
     */
    public synchronized void register(EnclosedEnvironmentProvider provider) {
        EnclosedEnvironmentProvider checked = Objects.requireNonNull(provider, "provider");
        ArrayList<EnclosedEnvironmentProvider> next = new ArrayList<>(providers);
        next.add(checked);
        next.sort(BY_PRIORITY_DESCENDING);
        providers = List.copyOf(next);
        cache.clear();
        pendingInvalidations.clear();
        fullInvalidationPending.set(false);
    }

    public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query, long gameTick) {
        Objects.requireNonNull(query, "query");
        drainPendingInvalidations();

        CacheKey key = CacheKey.from(query);
        CacheEntry cached = cache.get(key);
        if (cached != null) {
            if (gameTick <= cached.expiresAtTick) {
                return cached.environment;
            }
            cache.remove(key);
        }

        Optional<EnclosedEnvironment> resolved = resolveUncached(query);
        put(key, new CacheEntry(resolved, saturatingAdd(gameTick, ttlTicks)));
        return resolved;
    }

    /** Thread-safe host-event boundary; the resolve-owned LRU is mutated by the next resolve pass. */
    public void invalidateEntity(UUID entityId) {
        enqueueInvalidation(new SubjectInvalidation(SubjectKind.ENTITY, Objects.requireNonNull(entityId, "entityId")));
    }

    /** Thread-safe host-event boundary; the resolve-owned LRU is mutated by the next resolve pass. */
    public void invalidateVehicle(UUID vehicleId) {
        enqueueInvalidation(new SubjectInvalidation(SubjectKind.VEHICLE, Objects.requireNonNull(vehicleId, "vehicleId")));
    }

    public void clear() {
        cache.clear();
        pendingInvalidations.clear();
        fullInvalidationPending.set(false);
    }

    int cachedSubjectCount() {
        return cache.size();
    }

    private void enqueueInvalidation(SubjectInvalidation invalidation) {
        if (fullInvalidationPending.get()) {
            return;
        }

        pendingInvalidations.add(invalidation);
        if (pendingInvalidations.size() > maxCacheEntries) {
            fullInvalidationPending.set(true);
            pendingInvalidations.clear();
        }
    }

    private void drainPendingInvalidations() {
        if (fullInvalidationPending.getAndSet(false)) {
            cache.clear();
        }

        for (SubjectInvalidation invalidation : pendingInvalidations) {
            if (!pendingInvalidations.remove(invalidation)) {
                continue;
            }
            removeEntriesMatching(invalidation);
        }

        // Preserve invalidations that race with the drain. A new overflow marker clears the cache,
        // while keys added after that marker was consumed remain for this pass or the next one.
        if (fullInvalidationPending.getAndSet(false)) {
            cache.clear();
        }
    }

    private void removeEntriesMatching(SubjectInvalidation invalidation) {
        Iterator<Map.Entry<CacheKey, CacheEntry>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            CacheKey key = iterator.next().getKey();
            boolean matches = switch (invalidation.kind) {
                case ENTITY -> key.entityId.equals(invalidation.id);
                case VEHICLE -> key.vehicleId.filter(invalidation.id::equals).isPresent();
            };
            if (matches) {
                iterator.remove();
            }
        }
    }

    private Optional<EnclosedEnvironment> resolveUncached(EnclosedEnvironmentQuery query) {
        for (EnclosedEnvironmentProvider provider : providers) {
            Optional<EnclosedEnvironment> result;
            try {
                result = provider.resolve(query);
            } catch (RuntimeException | LinkageError providerFailure) {
                return Optional.empty();
            }
            if (result == null) {
                return Optional.empty();
            }
            if (result.isEmpty()) {
                continue;
            }
            EnclosedEnvironment environment = result.orElseThrow();
            if (!environment.protectsFromExternalPressure()) {
                return Optional.empty();
            }
            return Optional.of(environment);
        }
        return Optional.empty();
    }

    private void put(CacheKey key, CacheEntry entry) {
        cache.put(key, entry);
        while (cache.size() > maxCacheEntries) {
            Iterator<Map.Entry<CacheKey, CacheEntry>> iterator = cache.entrySet().iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }

    private static List<EnclosedEnvironmentProvider> sortedProviders(
            List<? extends EnclosedEnvironmentProvider> providers
    ) {
        ArrayList<EnclosedEnvironmentProvider> sorted = new ArrayList<>(providers);
        if (sorted.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("providers must not contain null");
        }
        sorted.sort(BY_PRIORITY_DESCENDING);
        return List.copyOf(sorted);
    }

    private static long saturatingAdd(long value, long increment) {
        if (increment > 0 && value > Long.MAX_VALUE - increment) {
            return Long.MAX_VALUE;
        }
        return value + increment;
    }

    private enum SubjectKind {
        ENTITY,
        VEHICLE
    }

    private record SubjectInvalidation(SubjectKind kind, UUID id) {
        private SubjectInvalidation {
            kind = Objects.requireNonNull(kind, "kind");
            id = Objects.requireNonNull(id, "id");
        }
    }

    private record CacheKey(
            UUID entityId,
            Optional<UUID> vehicleId,
            String dimensionId,
            int blockX,
            int blockY,
            int blockZ
    ) {
        private CacheKey {
            entityId = Objects.requireNonNull(entityId, "entityId");
            vehicleId = Objects.requireNonNull(vehicleId, "vehicleId");
            dimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        }

        private static CacheKey from(EnclosedEnvironmentQuery query) {
            return new CacheKey(
                    query.entityId(),
                    query.vehicleId(),
                    query.dimensionId(),
                    floorBlock(query.x()),
                    floorBlock(query.y()),
                    floorBlock(query.z()));
        }

        private static int floorBlock(double coordinate) {
            return (int) Math.floor(coordinate);
        }
    }

    private record CacheEntry(Optional<EnclosedEnvironment> environment, long expiresAtTick) {
        private CacheEntry {
            environment = Objects.requireNonNull(environment, "environment");
        }
    }
}
