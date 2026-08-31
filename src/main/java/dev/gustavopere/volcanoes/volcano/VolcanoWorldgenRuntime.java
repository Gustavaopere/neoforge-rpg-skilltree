package dev.gustavopere.volcanoes.volcano;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Server-thread bridge from newly generated chunks to persistent volcano-site metadata.
 *
 * <p>Chunk load only enqueues coordinates. SavedData is touched later from a level tick and only
 * when the canonical resolver says the loaded chunk owns a volcano candidate.</p>
 */
public final class VolcanoWorldgenRuntime {
    private static final int MAX_REGISTRATIONS_PER_TICK = 16;
    private static final VolcanoWorldgenResolver RESOLVER =
            VolcanoWorldgenResolver.createDefault(VolcanoWorldgenFeature.MAX_FOOTPRINT_RADIUS_BLOCKS);
    private static final Map<ServerLevel, VolcanoRegistrationQueue<ChunkPos>> QUEUES =
            Collections.synchronizedMap(new WeakHashMap<>());

    private VolcanoWorldgenRuntime() {
    }

    static boolean shouldQueueSiteRegistration(boolean newChunk, ResourceKey<Level> dimension) {
        Objects.requireNonNull(dimension, "dimension");
        return newChunk && Level.OVERWORLD.equals(dimension);
    }

    static boolean persistOwnedSite(
            VolcanoSavedData data,
            VolcanoWorldgenResolver resolver,
            long worldSeed,
            ChunkPos ownerChunk
    ) {
        return persistOwnedSite(
                data,
                resolver,
                worldSeed,
                ownerChunk,
                VolcanicTerrainHintProvider.none());
    }

    static boolean persistOwnedSite(
            VolcanoSavedData data,
            VolcanoWorldgenResolver resolver,
            long worldSeed,
            ChunkPos ownerChunk,
            VolcanicTerrainHintProvider terrainHints
    ) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(resolver, "resolver");
        Objects.requireNonNull(ownerChunk, "ownerChunk");
        Objects.requireNonNull(terrainHints, "terrainHints");

        return resolver.siteOwnedByChunk(worldSeed, ownerChunk, terrainHints)
                .filter(site -> data.get(site.persistenceId()).isEmpty())
                .filter(site -> data.nearby(
                        site.center(),
                        VolcanoWorldgenResolver.DEFAULT_PERSISTED_SPACING_BLOCKS).isEmpty())
                .map(data::register)
                .orElse(false);
    }

    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        if (!shouldQueueSiteRegistration(event.isNewChunk(), level.dimension())) {
            return;
        }
        queueFor(level).enqueue(level.getGameTime(), event.getChunk().getPos());
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)
                || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }

        VolcanoRegistrationQueue<ChunkPos> queue;
        synchronized (QUEUES) {
            queue = QUEUES.get(level);
        }
        if (queue == null || queue.isEmpty()) {
            return;
        }

        long worldSeed = level.getSeed();
        VolcanicTerrainHintProvider terrainHints = VolcanoWorldgenTerrainHints.forLevel(level);
        for (ChunkPos chunk : queue.drainReady(level.getGameTime())) {
            // Resolve first so ordinary new chunks without volcano centers never create SavedData.
            if (RESOLVER.siteOwnedByChunk(worldSeed, chunk, terrainHints).isEmpty()) {
                continue;
            }
            persistOwnedSite(
                    VolcanoSavedData.get(level),
                    RESOLVER,
                    worldSeed,
                    chunk,
                    terrainHints);
        }
    }

    private static VolcanoRegistrationQueue<ChunkPos> queueFor(ServerLevel level) {
        synchronized (QUEUES) {
            return QUEUES.computeIfAbsent(
                    level,
                    ignored -> new VolcanoRegistrationQueue<>(MAX_REGISTRATIONS_PER_TICK));
        }
    }
}
