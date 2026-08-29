package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.RelevantPlayerCandidate;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerCandidateMerger;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerPresence;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerSearchPolicy;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerSpatialIndex;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerSpatialQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

/** Bounded NeoForge player sampling/cache boundary for initial world-scaling decisions. */
public final class RelevantPlayerCandidateRuntime {
    private static final Object LOCK = new Object();
    private static final LinkedHashMap<CacheKey, CacheEntry> CACHE = new LinkedHashMap<>(16, 0.75F, true);
    private static volatile PartyCandidateSource partyCandidateSource;
    private static AutoCloseable mutationSubscription;
    private static long cacheHits, cacheMisses, playersSampled, saturatedBuilds;
    private static long spatialQueries, spatialPlayersScanned, spatialCellsVisited;

    private RelevantPlayerCandidateRuntime() {}

    public static void initialize() {
        synchronized (LOCK) {
            if (mutationSubscription != null) return;
            mutationSubscription = ProgressionMutationEvents.subscribe(event -> {
                if (event.section() == ProgressionMutationEvent.Section.CORE) invalidateAll();
            });
        }
    }

    public static QueryResult query(
        ServerLevel level,
        LivingEntity encounter,
        RelevantPlayerSearchPolicy searchPolicy,
        RuntimeLimits limits
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(encounter, "encounter");
        Objects.requireNonNull(searchPolicy, "searchPolicy");
        Objects.requireNonNull(limits, "limits");
        if (encounter.level() != level) {
            throw new IllegalArgumentException("encounter must belong to the supplied ServerLevel");
        }

        CacheEntry cached = indexFor(level, searchPolicy, limits);
        RelevantPlayerSpatialQuery spatial = cached.index().query(
            floorToInt(encounter.getX()), floorToInt(encounter.getY()), floorToInt(encounter.getZ()), searchPolicy
        );
        recordSpatialQuery(spatial);

        List<RelevantPlayerCandidate> candidates = spatial.candidates();
        PartyCandidateSource source = partyCandidateSource;
        if (source != null) {
            List<RelevantPlayerCandidate> party = List.copyOf(Objects.requireNonNull(
                source.candidates(level, encounter, searchPolicy), "party candidate source result"
            ));
            if (party.size() > searchPolicy.maxCandidates()) {
                throw new IllegalArgumentException("party candidate source exceeded maxCandidates");
            }
            candidates = RelevantPlayerCandidateMerger.merge(
                spatial.candidates(), party, searchPolicy.maxCandidates()
            );
        }
        return new QueryResult(
            candidates, spatial.indexedPlayers(), spatial.scannedPlayers(), spatial.visitedCells(), cached.saturated()
        );
    }

    public static void installPartyCandidateSource(PartyCandidateSource source) {
        partyCandidateSource = Objects.requireNonNull(source, "source");
    }

    public static void clearPartyCandidateSource() {
        partyCandidateSource = null;
    }

    public static void invalidateAll() {
        synchronized (LOCK) {
            CACHE.clear();
        }
    }

    public static Metrics metrics() {
        synchronized (LOCK) {
            return new Metrics(cacheHits, cacheMisses, playersSampled, saturatedBuilds,
                spatialQueries, spatialPlayersScanned, spatialCellsVisited, CACHE.size());
        }
    }

    public static void resetMetrics() {
        synchronized (LOCK) {
            cacheHits = cacheMisses = playersSampled = saturatedBuilds = 0L;
            spatialQueries = spatialPlayersScanned = spatialCellsVisited = 0L;
        }
    }

    private static CacheEntry indexFor(
        ServerLevel level,
        RelevantPlayerSearchPolicy searchPolicy,
        RuntimeLimits limits
    ) {
        CacheKey key = new CacheKey(
            level.dimension().location().toString(), searchPolicy.cellSizeBlocks(),
            searchPolicy.cacheTtlTicks(), limits.maxIndexedPlayers()
        );
        long now = level.getGameTime();
        synchronized (LOCK) {
            CacheEntry existing = CACHE.get(key);
            if (existing != null && now < existing.expiresAtTick()) {
                cacheHits = Math.addExact(cacheHits, 1L);
                return existing;
            }
            if (existing != null) CACHE.remove(key);
        }

        CacheEntry loaded = buildIndex(level, searchPolicy, limits, now);
        synchronized (LOCK) {
            cacheMisses = Math.addExact(cacheMisses, 1L);
            playersSampled = Math.addExact(playersSampled, loaded.sampledPlayers());
            if (loaded.saturated()) saturatedBuilds = Math.addExact(saturatedBuilds, 1L);
            CACHE.put(key, loaded);
            trimCache(limits.maxCacheEntries());
        }
        return loaded;
    }

    private static CacheEntry buildIndex(
        ServerLevel level,
        RelevantPlayerSearchPolicy searchPolicy,
        RuntimeLimits limits,
        long now
    ) {
        int probeLimit = Math.addExact(limits.maxIndexedPlayers(), 1);
        List<ServerPlayer> players = level.getPlayers(
            player -> player.isAlive() && !player.isSpectator(), probeLimit
        );
        if (players.size() > limits.maxIndexedPlayers()) {
            return new CacheEntry(
                RelevantPlayerSpatialIndex.build(List.of(), searchPolicy.cellSizeBlocks()),
                expiresAt(now, searchPolicy.cacheTtlTicks()), players.size(), true
            );
        }

        ArrayList<RelevantPlayerPresence> presences = new ArrayList<>(players.size());
        for (ServerPlayer player : players) {
            long playerLevel = CorePlayerProgressionRuntime.queryProgression(player).level();
            presences.add(new RelevantPlayerPresence(
                player.getUUID().toString(), playerLevel,
                floorToInt(player.getX()), floorToInt(player.getY()), floorToInt(player.getZ())
            ));
        }
        presences.sort((left, right) -> left.playerId().compareTo(right.playerId()));
        return new CacheEntry(
            RelevantPlayerSpatialIndex.build(presences, searchPolicy.cellSizeBlocks()),
            expiresAt(now, searchPolicy.cacheTtlTicks()), players.size(), false
        );
    }

    private static void recordSpatialQuery(RelevantPlayerSpatialQuery query) {
        synchronized (LOCK) {
            spatialQueries = Math.addExact(spatialQueries, 1L);
            spatialPlayersScanned = Math.addExact(spatialPlayersScanned, query.scannedPlayers());
            spatialCellsVisited = Math.addExact(spatialCellsVisited, query.visitedCells());
        }
    }

    private static void trimCache(int maxEntries) {
        Iterator<Map.Entry<CacheKey, CacheEntry>> iterator = CACHE.entrySet().iterator();
        while (CACHE.size() > maxEntries && iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    private static long expiresAt(long now, long ttl) {
        return Long.MAX_VALUE - now < ttl ? Long.MAX_VALUE : now + ttl;
    }

    private static int floorToInt(double value) {
        if (!Double.isFinite(value) || value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("entity coordinate is outside supported integer world bounds");
        }
        return (int) Math.floor(value);
    }

    @FunctionalInterface
    public interface PartyCandidateSource {
        List<RelevantPlayerCandidate> candidates(
            ServerLevel level, LivingEntity encounter, RelevantPlayerSearchPolicy searchPolicy
        );
    }

    public record RuntimeLimits(int maxIndexedPlayers, int maxCacheEntries) {
        public RuntimeLimits {
            if (maxIndexedPlayers <= 0 || maxIndexedPlayers == Integer.MAX_VALUE) {
                throw new IllegalArgumentException("maxIndexedPlayers must be positive and leave probe headroom");
            }
            if (maxCacheEntries <= 0) throw new IllegalArgumentException("maxCacheEntries must be positive");
        }
    }

    public record QueryResult(
        List<RelevantPlayerCandidate> candidates,
        int indexedPlayers,
        int scannedPlayers,
        long visitedCells,
        boolean saturatedIndex
    ) {
        public QueryResult {
            candidates = List.copyOf(Objects.requireNonNull(candidates, "candidates"));
            if (indexedPlayers < 0 || scannedPlayers < 0 || scannedPlayers > indexedPlayers) {
                throw new IllegalArgumentException("invalid relevant-player query counters");
            }
            if (visitedCells < 0L) throw new IllegalArgumentException("visitedCells must be non-negative");
        }
    }

    public record Metrics(
        long cacheHits, long cacheMisses, long playersSampled, long saturatedBuilds,
        long spatialQueries, long spatialPlayersScanned, long spatialCellsVisited, int cacheEntries
    ) {}

    private record CacheKey(String dimensionId, int cellSizeBlocks, long cacheTtlTicks, int maxIndexedPlayers) {}
    private record CacheEntry(RelevantPlayerSpatialIndex index, long expiresAtTick, int sampledPlayers, boolean saturated) {}
}
