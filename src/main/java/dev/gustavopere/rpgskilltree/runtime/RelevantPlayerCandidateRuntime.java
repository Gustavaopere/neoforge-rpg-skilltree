package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.RelevantPlayerCandidate;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerSpatialSelector;
import dev.gustavopere.rpgskilltree.core.RelevantPlayerSpatialSelector.Snapshot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

/**
 * Server-authoritative local-player sampling boundary for initial entity scaling.
 *
 * <p>Spatial scans are cached per 16x16x16 section for a short TTL. The cached value stores
 * player position/level samples, not encounter-relative candidates; exact distance is recomputed
 * for every encounter by {@link RelevantPlayerSpatialSelector}. This avoids stale cell-center
 * distance decisions while still preventing one player-list scan for every mob spawn.</p>
 *
 * <p>If the bounded spatial scan saturates, spatial evidence fails closed to an empty set instead
 * of accepting an arbitrary prefix of the server player list. A party integration may be installed
 * through {@link #installPartyCandidateSource(PartyCandidateSource)} without making any party mod a
 * hard dependency.</p>
 */
public final class RelevantPlayerCandidateRuntime {
    private static final double SECTION_SIZE = 16.0D;
    private static final double SECTION_HALF = SECTION_SIZE / 2.0D;
    private static final double SECTION_HALF_DIAGONAL = Math.sqrt(3.0D * SECTION_HALF * SECTION_HALF);

    private static final Object LOCK = new Object();
    private static final LinkedHashMap<CacheKey, CacheEntry> CACHE = new LinkedHashMap<>(64, 0.75F, true);

    private static volatile PartyCandidateSource partyCandidateSource;
    private static AutoCloseable mutationSubscription;
    private static long cacheHits;
    private static long cacheMisses;
    private static long playersSampled;
    private static long saturatedScans;

    private RelevantPlayerCandidateRuntime() {}

    /** Installs progression-driven invalidation once for the mod lifetime. */
    public static void initialize() {
        synchronized (LOCK) {
            if (mutationSubscription != null) return;
            mutationSubscription = ProgressionMutationEvents.subscribe(event -> {
                if (event.section() == ProgressionMutationEvent.Section.CORE) {
                    invalidateAll();
                }
            });
        }
    }

    public static List<RelevantPlayerCandidate> collect(ServerLevel level, LivingEntity encounter) {
        return collect(level, encounter, Config.DEFAULT);
    }

    public static List<RelevantPlayerCandidate> collect(
        ServerLevel level,
        LivingEntity encounter,
        Config config
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(encounter, "encounter");
        Objects.requireNonNull(config, "config");
        if (encounter.level() != level) {
            throw new IllegalArgumentException("encounter must belong to the supplied ServerLevel");
        }

        List<Snapshot> snapshots = snapshotsFor(level, encounter, config);
        List<RelevantPlayerCandidate> spatial = RelevantPlayerSpatialSelector.select(
            snapshots,
            encounter.getX(),
            encounter.getY(),
            encounter.getZ(),
            config.spatialRadius(),
            config.maxCandidates()
        );

        PartyCandidateSource source = partyCandidateSource;
        if (source == null) return spatial;

        List<RelevantPlayerCandidate> party = List.copyOf(Objects.requireNonNull(
            source.candidates(level, encounter, config.maxCandidates()),
            "party candidate source result"
        ));
        return RelevantPlayerSpatialSelector.mergeParty(spatial, party, config.maxCandidates());
    }

    /** Optional integration point for a real party/team provider. */
    public static void installPartyCandidateSource(PartyCandidateSource source) {
        partyCandidateSource = Objects.requireNonNull(source, "source");
        invalidateAll();
    }

    public static void clearPartyCandidateSource() {
        partyCandidateSource = null;
        invalidateAll();
    }

    /** Invalidates movement/topology/progression-sensitive cached player samples. */
    public static void invalidateAll() {
        synchronized (LOCK) {
            CACHE.clear();
        }
    }

    public static Metrics metrics() {
        synchronized (LOCK) {
            return new Metrics(cacheHits, cacheMisses, playersSampled, saturatedScans, CACHE.size());
        }
    }

    public static void resetMetrics() {
        synchronized (LOCK) {
            cacheHits = 0L;
            cacheMisses = 0L;
            playersSampled = 0L;
            saturatedScans = 0L;
        }
    }

    private static List<Snapshot> snapshotsFor(ServerLevel level, LivingEntity encounter, Config config) {
        int sectionX = sectionCoord(encounter.getX());
        int sectionY = sectionCoord(encounter.getY());
        int sectionZ = sectionCoord(encounter.getZ());
        CacheKey key = new CacheKey(
            level.dimension().location().toString(),
            sectionX,
            sectionY,
            sectionZ,
            Double.doubleToLongBits(config.spatialRadius()),
            config.maxPlayersScanned(),
            config.cacheTtlTicks()
        );
        long now = level.getGameTime();

        synchronized (LOCK) {
            CacheEntry cached = CACHE.get(key);
            if (cached != null && now < cached.expiresAtTick()) {
                cacheHits = Math.addExact(cacheHits, 1L);
                return cached.snapshots();
            }
            if (cached != null) CACHE.remove(key);
        }

        CacheEntry loaded = loadSection(level, sectionX, sectionY, sectionZ, config, now);
        synchronized (LOCK) {
            cacheMisses = Math.addExact(cacheMisses, 1L);
            playersSampled = Math.addExact(playersSampled, loaded.sampledPlayers());
            if (loaded.saturated()) saturatedScans = Math.addExact(saturatedScans, 1L);
            if (config.cacheTtlTicks() > 0L) {
                CACHE.put(key, loaded);
                trimCache(config.maxCacheEntries());
            }
        }
        return loaded.snapshots();
    }

    private static CacheEntry loadSection(
        ServerLevel level,
        int sectionX,
        int sectionY,
        int sectionZ,
        Config config,
        long now
    ) {
        double centerX = sectionX * SECTION_SIZE + SECTION_HALF;
        double centerY = sectionY * SECTION_SIZE + SECTION_HALF;
        double centerZ = sectionZ * SECTION_SIZE + SECTION_HALF;
        double expandedRadius = config.spatialRadius() + SECTION_HALF_DIAGONAL;
        double expandedRadiusSquared = expandedRadius * expandedRadius;

        int probeLimit = Math.addExact(config.maxPlayersScanned(), 1);
        List<ServerPlayer> players = level.getPlayers(
            player -> player.isAlive()
                && !player.isSpectator()
                && distanceSquared(player.getX(), player.getY(), player.getZ(), centerX, centerY, centerZ)
                    <= expandedRadiusSquared,
            probeLimit
        );

        if (players.size() > config.maxPlayersScanned()) {
            return new CacheEntry(expiresAt(now, config.cacheTtlTicks()), List.of(), players.size(), true);
        }

        ArrayList<Snapshot> snapshots = new ArrayList<>(players.size());
        for (ServerPlayer player : players) {
            long levelValue = CorePlayerProgressionRuntime.queryProgression(player).level();
            snapshots.add(new Snapshot(
                player.getUUID().toString(),
                levelValue,
                player.getX(),
                player.getY(),
                player.getZ()
            ));
        }
        snapshots.sort((left, right) -> left.playerId().compareTo(right.playerId()));
        return new CacheEntry(
            expiresAt(now, config.cacheTtlTicks()),
            List.copyOf(snapshots),
            players.size(),
            false
        );
    }

    private static void trimCache(int maxEntries) {
        Iterator<Map.Entry<CacheKey, CacheEntry>> iterator = CACHE.entrySet().iterator();
        while (CACHE.size() > maxEntries && iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    private static long expiresAt(long now, long ttl) {
        if (ttl == 0L) return now;
        if (Long.MAX_VALUE - now < ttl) return Long.MAX_VALUE;
        return now + ttl;
    }

    private static int sectionCoord(double coordinate) {
        return (int) Math.floor(coordinate / SECTION_SIZE);
    }

    private static double distanceSquared(
        double x,
        double y,
        double z,
        double otherX,
        double otherY,
        double otherZ
    ) {
        double dx = x - otherX;
        double dy = y - otherY;
        double dz = z - otherZ;
        return dx * dx + dy * dy + dz * dz;
    }

    @FunctionalInterface
    public interface PartyCandidateSource {
        /**
         * Returns bounded party-relevant candidates for this encounter. Every returned candidate
         * must set {@code partyMember=true}; duplicate IDs are reconciled by the pure selector.
         */
        List<RelevantPlayerCandidate> candidates(ServerLevel level, LivingEntity encounter, int maxCandidates);
    }

    /** Runtime envelope, not a character-balance formula. */
    public record Config(
        double spatialRadius,
        int maxCandidates,
        int maxPlayersScanned,
        long cacheTtlTicks,
        int maxCacheEntries
    ) {
        public static final Config DEFAULT = new Config(96.0D, 16, 64, 20L, 256);

        public Config {
            if (!Double.isFinite(spatialRadius) || spatialRadius <= 0.0D) {
                throw new IllegalArgumentException("spatialRadius must be finite and positive");
            }
            if (maxCandidates <= 0) throw new IllegalArgumentException("maxCandidates must be positive");
            if (maxPlayersScanned < maxCandidates || maxPlayersScanned == Integer.MAX_VALUE) {
                throw new IllegalArgumentException("maxPlayersScanned must be >= maxCandidates and leave probe headroom");
            }
            if (cacheTtlTicks < 0L) throw new IllegalArgumentException("cacheTtlTicks must be non-negative");
            if (maxCacheEntries <= 0) throw new IllegalArgumentException("maxCacheEntries must be positive");
        }
    }

    public record Metrics(
        long cacheHits,
        long cacheMisses,
        long playersSampled,
        long saturatedScans,
        int cacheEntries
    ) {}

    private record CacheKey(
        String dimensionId,
        int sectionX,
        int sectionY,
        int sectionZ,
        long radiusBits,
        int maxPlayersScanned,
        long cacheTtlTicks
    ) {}

    private record CacheEntry(
        long expiresAtTick,
        List<Snapshot> snapshots,
        int sampledPlayers,
        boolean saturated
    ) {}
}
