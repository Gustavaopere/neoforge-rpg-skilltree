package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Server-thread bridge from generated geothermal expressions to persistent metadata and heat queries.
 *
 * <p>Worldgen reserves transient bounded work and writes a chunk-local durable receipt only after
 * physical mutation succeeds. Chunk events merely track loaded receipt owners; SavedData authority
 * is reconciled later on the server tick under one shared persistence-attempt budget.</p>
 */
public final class GeothermalWorldgenRuntime {
    private static final int PENDING_CAPACITY = 2_048;
    private static final int MAX_PERSISTENCE_PER_TICK = 16;
    private static final int MAX_RECOVERY_CHUNKS_PER_TICK = 2;
    private static final int HEAT_CELL_SIZE_BLOCKS = 32;
    private static final double MAX_HEAT_SOURCE_RADIUS_BLOCKS = 64.0;
    private static final double MAX_HEAT_QUERY_RADIUS_BLOCKS = 256.0;
    private static final int MAX_NATIVE_EFFECT_OBSERVERS_PER_TICK = 32;
    static final int MAX_GEYSER_PULSES_PER_TICK = 8;
    private static final int MAX_GEYSER_ENTITY_EXPOSURES_PER_TICK = 16;
    static final long GEYSER_PULSE_HEAT_TTL_TICKS = 40L;
    static final int MAX_OTHER_DYNAMIC_HEAT_SOURCES = 32;
    static final int MAX_DYNAMIC_HEAT_SOURCES = MAX_GEYSER_PULSES_PER_TICK
            * Math.toIntExact(GEYSER_PULSE_HEAT_TTL_TICKS)
            + MAX_OTHER_DYNAMIC_HEAT_SOURCES;
    static final int MAX_HEAT_SOURCES = GeothermalSourceRegistry.DEFAULT_MAX_SOURCES + MAX_DYNAMIC_HEAT_SOURCES;
    private static final int MAX_EXPIRED_REMOVALS_PER_TICK = 32;

    private static final HydrothermalDepositProjector DEPOSIT_PROJECTOR = new HydrothermalDepositProjector();
    private static final Map<ServerLevel, GeothermalPendingQueue> PENDING =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<ServerLevel, GeothermalLoadedChunkRecoveryTracker> RECOVERY_TRACKERS =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<ServerLevel, RuntimeState> STATES =
            Collections.synchronizedMap(new WeakHashMap<>());

    private GeothermalWorldgenRuntime() {
    }

    public static boolean enqueueGenerated(
            ServerLevel level,
            long worldSeed,
            GeothermalFeaturePlacement placement
    ) {
        Optional<GeothermalPendingQueue.Reservation> reservation = reserveGenerated(level, worldSeed, placement);
        return reservation.filter(value -> commitGenerated(level, value)).isPresent();
    }

    public static Optional<GeothermalPendingQueue.Reservation> reserveGenerated(
            ServerLevel level,
            long worldSeed,
            GeothermalFeaturePlacement placement
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(placement, "placement");
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return Optional.empty();
        }
        return queueFor(level).reserve(worldSeed, placement);
    }

    public static boolean commitGenerated(ServerLevel level, GeothermalPendingQueue.Reservation reservation) {
        return commitGenerated(level, reservation, false);
    }

    public static boolean commitGenerated(
            ServerLevel level,
            GeothermalPendingQueue.Reservation reservation,
            boolean hydrothermalDepositPhysicallyRealized
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(reservation, "reservation");
        GeothermalPendingQueue queue = existingQueue(level);
        return queue != null && queue.commit(reservation, hydrothermalDepositPhysicallyRealized);
    }

    public static boolean cancelGenerated(ServerLevel level, GeothermalPendingQueue.Reservation reservation) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(reservation, "reservation");
        GeothermalPendingQueue queue = existingQueue(level);
        return queue != null && queue.cancel(reservation);
    }

    public static List<VolcanicHeatSource> heatSourcesNear(
            ServerLevel level,
            BlockPos center,
            double radiusBlocks,
            int maxResults
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(center, "center");
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return List.of();
        }
        RuntimeState state = stateFor(level);
        return state.heatIndex().nearby(center, radiusBlocks, maxResults, level.getGameTime());
    }

    public static boolean upsertHeatSource(ServerLevel level, VolcanicHeatSource source) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(source, "source");
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return false;
        }
        return stateFor(level).heatIndex().upsert(source);
    }

    public static boolean removeHeatSource(ServerLevel level, UUID sourceId) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(sourceId, "sourceId");
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return false;
        }
        RuntimeState state = existingState(level);
        return state != null && state.heatIndex().remove(sourceId);
    }

    /** Chunk load only tracks an already-loaded owner carrying durable receipts. */
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)
                || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        if (!event.getChunk().hasData(VolcanoAttachments.GEOTHERMAL_HANDOFFS)) {
            return;
        }
        List<GeothermalChunkHandoff> handoffs =
                event.getChunk().getData(VolcanoAttachments.GEOTHERMAL_HANDOFFS);
        if (!handoffs.isEmpty()) {
            recoveryTrackerFor(level).track(event.getChunk().getPos().toLong());
        }
    }

    /** Tracker memory follows engine-loaded state; the durable receipt remains in the chunk. */
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)
                || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        GeothermalLoadedChunkRecoveryTracker tracker = existingRecoveryTracker(level);
        if (tracker != null) {
            tracker.untrack(event.getChunk().getPos().toLong());
        }
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)
                || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }

        persistPersistenceTurn(level);

        RuntimeState state = stateFor(level);
        long gameTick = level.getGameTime();
        state.heatIndex().expire(gameTick, MAX_EXPIRED_REMOVALS_PER_TICK);
        tickNativeGeysers(level, state, gameTick);
    }

    /** Legacy test/diagnostic entry point for the transient queue alone. */
    static int persistQueued(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return 0;
        }
        GeothermalPendingQueue queue = existingQueue(level);
        if (queue == null || queue.isEmpty()) {
            return 0;
        }
        RuntimeState state = stateFor(level);
        return persistPending(queue, state.sources(), DepositRegistry.get(level), DEPOSIT_PROJECTOR);
    }

    private static int persistPersistenceTurn(ServerLevel level) {
        GeothermalPendingQueue queue = existingQueue(level);
        GeothermalLoadedChunkRecoveryTracker recovery = existingRecoveryTracker(level);
        boolean hasTransientWork = queue != null && queue.hasProcessableCommittedWork();
        boolean hasRecoveryWork = recovery != null && recovery.size() > 0;
        GeothermalPersistenceTurnBudget.Allocation allocation =
                GeothermalPersistenceTurnBudget.allocate(hasTransientWork, hasRecoveryWork);
        if (allocation.transientAttempts() == 0 && allocation.recoveryChunks() == 0) {
            return 0;
        }

        RuntimeState state = stateFor(level);
        DepositRegistry deposits = DepositRegistry.get(level);
        int acknowledged = 0;
        if (queue != null && allocation.transientAttempts() > 0) {
            acknowledged += persistPending(
                    queue,
                    state.sources(),
                    deposits,
                    DEPOSIT_PROJECTOR,
                    allocation.transientAttempts());
        }
        if (recovery != null && allocation.recoveryChunks() > 0) {
            acknowledged += recoverLoadedReceiptChunks(
                    level,
                    recovery,
                    state.sources(),
                    deposits,
                    DEPOSIT_PROJECTOR,
                    allocation.recoveryChunks());
        }
        return acknowledged;
    }

    static int persistPending(
            GeothermalPendingQueue queue,
            GeothermalSourceRegistry sources,
            DepositRegistry deposits,
            HydrothermalDepositProjector projector
    ) {
        return persistPending(queue, sources, deposits, projector, MAX_PERSISTENCE_PER_TICK);
    }

    static int persistPending(
            GeothermalPendingQueue queue,
            GeothermalSourceRegistry sources,
            DepositRegistry deposits,
            HydrothermalDepositProjector projector,
            int maxAttempts
    ) {
        Objects.requireNonNull(queue, "queue");
        Objects.requireNonNull(sources, "sources");
        Objects.requireNonNull(deposits, "deposits");
        Objects.requireNonNull(projector, "projector");

        return queue.processCommitted(pending -> persistGenerated(
                sources,
                deposits,
                projector,
                pending.worldSeed(),
                pending.placement(),
                pending.hydrothermalDepositPhysicallyRealized()).metadataAuthoritative(), maxAttempts);
    }

    static PersistResult persistHandoff(
            GeothermalSourceRegistry sources,
            DepositRegistry deposits,
            HydrothermalDepositProjector projector,
            GeothermalChunkHandoff handoff
    ) {
        Objects.requireNonNull(handoff, "handoff");
        return persistGenerated(
                sources,
                deposits,
                projector,
                handoff.worldSeed(),
                handoff.placement(),
                handoff.hydrothermalDepositPhysicallyRealized());
    }

    private static int recoverLoadedReceiptChunks(
            ServerLevel level,
            GeothermalLoadedChunkRecoveryTracker tracker,
            GeothermalSourceRegistry sources,
            DepositRegistry deposits,
            HydrothermalDepositProjector projector,
            int allocatedChunks
    ) {
        int acknowledged = 0;
        int chunkBudget = Math.min(MAX_RECOVERY_CHUNKS_PER_TICK, allocatedChunks);
        for (long packedChunk : tracker.nextBatch(chunkBudget)) {
            int chunkX = ChunkPos.getX(packedChunk);
            int chunkZ = ChunkPos.getZ(packedChunk);
            LevelChunk chunk = level.getChunkSource().getChunkNow(chunkX, chunkZ);
            if (chunk == null) {
                tracker.untrack(packedChunk);
                continue;
            }

            List<GeothermalChunkHandoff> durable = chunk.hasData(VolcanoAttachments.GEOTHERMAL_HANDOFFS)
                    ? chunk.getData(VolcanoAttachments.GEOTHERMAL_HANDOFFS)
                    : List.of();
            if (durable.isEmpty()) {
                tracker.untrack(packedChunk);
                continue;
            }

            GeothermalDurableRecoveryBatch.Result result = GeothermalDurableRecoveryBatch.process(
                    durable,
                    VolcanoAttachments.MAX_DURABLE_HANDOFFS_PER_CHUNK,
                    handoff -> {
                        PersistResult persisted = persistHandoff(sources, deposits, projector, handoff);
                        if (!persisted.metadataAuthoritative()) {
                            return false;
                        }
                        GeothermalPendingQueue transientQueue = existingQueue(level);
                        if (transientQueue != null) {
                            transientQueue.acknowledgeResolved(handoff.sourceId());
                        }
                        return true;
                    });
            acknowledged += result.acknowledged();

            if (!result.remaining().equals(durable)) {
                removeHandoff(chunk, result.remaining());
            }
            if (result.remaining().isEmpty()) {
                tracker.untrack(packedChunk);
            }
        }
        return acknowledged;
    }

    private static void removeHandoff(LevelChunk chunk, List<GeothermalChunkHandoff> remaining) {
        chunk.setData(VolcanoAttachments.GEOTHERMAL_HANDOFFS, List.copyOf(remaining));
    }

    static PersistResult persistGenerated(
            GeothermalSourceRegistry sources,
            DepositRegistry deposits,
            HydrothermalDepositProjector projector,
            long worldSeed,
            GeothermalFeaturePlacement placement
    ) {
        return persistGenerated(sources, deposits, projector, worldSeed, placement, false);
    }

    static PersistResult persistGenerated(
            GeothermalSourceRegistry sources,
            DepositRegistry deposits,
            HydrothermalDepositProjector projector,
            long worldSeed,
            GeothermalFeaturePlacement placement,
            boolean hydrothermalDepositPhysicallyRealized
    ) {
        Objects.requireNonNull(sources, "sources");
        Objects.requireNonNull(deposits, "deposits");
        Objects.requireNonNull(projector, "projector");
        Objects.requireNonNull(placement, "placement");

        GeothermalSource source = GeothermalSource.fromPlacement(worldSeed, placement);
        boolean sourceRegistered = sources.register(source);
        boolean sourceAuthoritative = sources.get(source.persistenceId())
                .filter(source::equals)
                .isPresent();
        if (!sourceAuthoritative) {
            return new PersistResult(sourceRegistered, false, false, false);
        }

        var projectedDeposit = projector.project(worldSeed, placement);
        if (projectedDeposit.isEmpty()) {
            return new PersistResult(sourceRegistered, true, false, true);
        }

        var expectedDeposit = projectedDeposit.orElseThrow();
        if (!depositMetadataAdmissible(
                expectedDeposit.resourceTag(), hydrothermalDepositPhysicallyRealized)) {
            if (deposits.get(expectedDeposit.persistenceId()).filter(expectedDeposit::equals).isPresent()) {
                deposits.remove(expectedDeposit.persistenceId());
            }
            boolean depositAuthoritative = deposits.get(expectedDeposit.persistenceId()).isEmpty();
            return new PersistResult(sourceRegistered, true, false, depositAuthoritative);
        }

        boolean depositRegistered = deposits.register(expectedDeposit);
        boolean depositAuthoritative = deposits.get(expectedDeposit.persistenceId())
                .filter(expectedDeposit::equals)
                .isPresent();
        return new PersistResult(sourceRegistered, true, depositRegistered, depositAuthoritative);
    }

    static boolean depositMetadataAdmissible(
            ResourceLocation resourceTag,
            boolean hydrothermalDepositPhysicallyRealized
    ) {
        Objects.requireNonNull(resourceTag, "resourceTag");
        if (resourceTag.equals(GeologyResourceTags.COPPER_ORES.location())
                || resourceTag.equals(GeologyResourceTags.IRON_ORES.location())
                || resourceTag.equals(GeologyResourceTags.GOLD_ORES.location())) {
            return hydrothermalDepositPhysicallyRealized;
        }
        return true;
    }

    private static void tickNativeGeysers(ServerLevel level, RuntimeState state, long gameTick) {
        var players = level.players();
        if (players.isEmpty()) {
            return;
        }
        List<Integer> observerIndices = GeothermalObserverSampler.sampleIndices(
                players.size(), gameTick, MAX_NATIVE_EFFECT_OBSERVERS_PER_TICK);
        List<BlockPos> observers = new ArrayList<>(observerIndices.size());
        for (int observerIndex : observerIndices) {
            observers.add(players.get(observerIndex).blockPosition().immutable());
        }
        long detectionWindowTicks = GeothermalObserverSampler.sweepTicks(
                players.size(), MAX_NATIVE_EFFECT_OBSERVERS_PER_TICK);
        List<GeothermalSource> due = GeothermalNativeEffects.dueGeysers(
                state.heatIndex(),
                state.sources(),
                observers,
                gameTick,
                MAX_NATIVE_EFFECT_OBSERVERS_PER_TICK,
                MAX_GEYSER_PULSES_PER_TICK,
                detectionWindowTicks,
                source -> GeothermalPulseAdmission.isPending(
                        source,
                        gameTick,
                        state.activatedAtGameTick(),
                        state.lastGeyserPulseTicks()));
        if (due.isEmpty()) {
            return;
        }

        Set<UUID> exposedEntities = new HashSet<>();
        for (GeothermalSource source : due) {
            if (!GeothermalPulseAdmission.isPending(
                    source,
                    gameTick,
                    state.activatedAtGameTick(),
                    state.lastGeyserPulseTicks())) {
                continue;
            }
            long scheduledPulseTick = GeothermalGeyserCycle.forSource(source).latestPulseTickAtOrBefore(gameTick);
            state.lastGeyserPulseTicks().put(source.persistenceId(), scheduledPulseTick);
            state.heatIndex().upsert(GeothermalGeyserPulseProjector.project(
                    source,
                    gameTick,
                    GEYSER_PULSE_HEAT_TTL_TICKS));
            renderGeyserPulse(level, source);
            if (exposedEntities.size() < MAX_GEYSER_ENTITY_EXPOSURES_PER_TICK) {
                exposeGeyserPulse(
                        level,
                        source,
                        exposedEntities,
                        MAX_GEYSER_ENTITY_EXPOSURES_PER_TICK - exposedEntities.size());
            }
        }
    }

    private static void renderGeyserPulse(ServerLevel level, GeothermalSource source) {
        double x = source.center().getX() + 0.5;
        double y = source.center().getY() + 1.0;
        double z = source.center().getZ() + 0.5;
        double spread = Math.max(0.35, source.radiusBlocks() * 0.35);
        level.sendParticles(ParticleTypes.SPLASH, x, y, z, 24, spread, 1.5, spread, 0.18);
        level.sendParticles(ParticleTypes.CLOUD, x, y + 0.8, z, 8, spread * 0.6, 1.0, spread * 0.6, 0.04);
    }

    private static int exposeGeyserPulse(
            ServerLevel level,
            GeothermalSource source,
            Set<UUID> exposedEntities,
            int budget
    ) {
        if (budget <= 0) {
            return 0;
        }
        double radius = Math.max(1.5, source.radiusBlocks() + 0.5);
        double x = source.center().getX() + 0.5;
        double y = source.center().getY() + 1.0;
        double z = source.center().getZ() + 0.5;
        AABB box = new AABB(
                x - radius,
                y - 0.5,
                z - radius,
                x + radius,
                y + Math.max(3.0, radius),
                z + radius);
        int affected = 0;
        for (LivingEntity entity : VolcanicEntityQueryBudget.collect(
                MAX_GEYSER_ENTITY_EXPOSURES_PER_TICK,
                level,
                box,
                entity -> entity.isAlive() && !exposedEntities.contains(entity.getUUID()))) {
            if (affected >= budget) {
                break;
            }
            if (!exposedEntities.add(entity.getUUID())) {
                continue;
            }
            entity.hurt(
                    level.damageSources().inFire(),
                    (float) (0.5 + source.heatSeverity() * 1.5));
            entity.push(0.0, 0.25 + source.heatSeverity() * 0.25, 0.0);
            affected++;
        }
        return affected;
    }

    private static GeothermalPendingQueue queueFor(ServerLevel level) {
        synchronized (PENDING) {
            return PENDING.computeIfAbsent(
                    level,
                    ignored -> new GeothermalPendingQueue(PENDING_CAPACITY, MAX_PERSISTENCE_PER_TICK));
        }
    }

    private static GeothermalPendingQueue existingQueue(ServerLevel level) {
        synchronized (PENDING) {
            return PENDING.get(level);
        }
    }

    private static GeothermalLoadedChunkRecoveryTracker recoveryTrackerFor(ServerLevel level) {
        synchronized (RECOVERY_TRACKERS) {
            return RECOVERY_TRACKERS.computeIfAbsent(level, ignored -> new GeothermalLoadedChunkRecoveryTracker());
        }
    }

    private static GeothermalLoadedChunkRecoveryTracker existingRecoveryTracker(ServerLevel level) {
        synchronized (RECOVERY_TRACKERS) {
            return RECOVERY_TRACKERS.get(level);
        }
    }

    private static RuntimeState stateFor(ServerLevel level) {
        synchronized (STATES) {
            return STATES.computeIfAbsent(level, GeothermalWorldgenRuntime::createState);
        }
    }

    private static RuntimeState existingState(ServerLevel level) {
        synchronized (STATES) {
            return STATES.get(level);
        }
    }

    private static RuntimeState createState(ServerLevel level) {
        GeothermalSourceRegistry sources = GeothermalSourceRegistry.get(level);
        VolcanicHeatSourceIndex heatIndex = new VolcanicHeatSourceIndex(
                HEAT_CELL_SIZE_BLOCKS,
                MAX_HEAT_SOURCE_RADIUS_BLOCKS,
                MAX_HEAT_QUERY_RADIUS_BLOCKS,
                MAX_HEAT_SOURCES);
        Map<UUID, Long> lastGeyserPulseTicks = new HashMap<>();
        sources.registerLifecycleSink(new GeothermalHeatIndexSink(heatIndex));
        sources.registerLifecycleSink(new GeothermalPulseHistorySink(lastGeyserPulseTicks));
        return new RuntimeState(sources, heatIndex, lastGeyserPulseTicks, level.getGameTime());
    }

    private record RuntimeState(
            GeothermalSourceRegistry sources,
            VolcanicHeatSourceIndex heatIndex,
            Map<UUID, Long> lastGeyserPulseTicks,
            long activatedAtGameTick
    ) {
        private RuntimeState {
            Objects.requireNonNull(sources, "sources");
            Objects.requireNonNull(heatIndex, "heatIndex");
            Objects.requireNonNull(lastGeyserPulseTicks, "lastGeyserPulseTicks");
            if (activatedAtGameTick < 0L) {
                throw new IllegalArgumentException("activatedAtGameTick must be non-negative");
            }
        }
    }

    record PersistResult(
            boolean sourceRegistered,
            boolean sourceAuthoritative,
            boolean depositRegistered,
            boolean depositAuthoritative
    ) {
        boolean metadataAuthoritative() {
            return sourceAuthoritative && depositAuthoritative;
        }
    }
}
