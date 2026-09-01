package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.performance.PerformanceConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Bounded coordinator for specialized volcanic lava work. */
public final class VolcanicLavaController {
    private static final int DEFAULT_MAX_BLOCKS_PER_ERUPTION = 96;
    private static final int DEFAULT_MAX_QUEUED_POSITIONS_PER_CHUNK = 64;

    private final LavaFlowResolver flowResolver;
    private final int maxBlocksPerTick;
    private final int maxBlocksPerEruption;
    private final int maxQueuedPositionsPerChunk;
    private final Map<ChunkPos, ArrayDeque<BlockPos>> deferredByChunk = new HashMap<>();

    public VolcanicLavaController(
            LavaFlowResolver flowResolver,
            int maxBlocksPerTick,
            int maxBlocksPerEruption,
            int maxQueuedPositionsPerChunk
    ) {
        this.flowResolver = Objects.requireNonNull(flowResolver, "flowResolver");
        if (maxBlocksPerTick <= 0) {
            throw new IllegalArgumentException("maxBlocksPerTick must be positive");
        }
        if (maxBlocksPerEruption <= 0) {
            throw new IllegalArgumentException("maxBlocksPerEruption must be positive");
        }
        if (maxQueuedPositionsPerChunk <= 0) {
            throw new IllegalArgumentException("maxQueuedPositionsPerChunk must be positive");
        }
        this.maxBlocksPerTick = maxBlocksPerTick;
        this.maxBlocksPerEruption = maxBlocksPerEruption;
        this.maxQueuedPositionsPerChunk = maxQueuedPositionsPerChunk;
    }

    /** Canonical runtime factory using the server-configured Stage 07 lava budget. */
    public static VolcanicLavaController configured(LavaFlowResolver flowResolver) {
        return configured(flowResolver, PerformanceConfig.current());
    }

    /** Deterministic overload for tests and integration hosts that already hold a config snapshot. */
    public static VolcanicLavaController configured(
            LavaFlowResolver flowResolver,
            PerformanceConfig.Budgets budgets
    ) {
        Objects.requireNonNull(budgets, "budgets");
        return new VolcanicLavaController(
                flowResolver,
                budgets.lavaSpecializationBlocksPerTick(),
                DEFAULT_MAX_BLOCKS_PER_ERUPTION,
                DEFAULT_MAX_QUEUED_POSITIONS_PER_CHUNK);
    }

    public FlowPlan planStep(
            long worldSeed,
            int x,
            int y,
            int z,
            int requestedBlocks,
            int blocksChangedThisTick,
            int blocksChangedThisEruption,
            boolean targetChunkLoaded
    ) {
        if (requestedBlocks < 0) {
            throw new IllegalArgumentException("requestedBlocks must be non-negative");
        }
        if (blocksChangedThisTick < 0) {
            throw new IllegalArgumentException("blocksChangedThisTick must be non-negative");
        }
        if (blocksChangedThisEruption < 0) {
            throw new IllegalArgumentException("blocksChangedThisEruption must be non-negative");
        }

        LavaEnvironmentSample environment = flowResolver.sample(worldSeed, x, y, z);
        if (environment.usesVanillaFallback()) {
            return new FlowPlan(FlowMode.VANILLA, environment, 0, 0);
        }

        int tickRemaining = Math.max(0, maxBlocksPerTick - blocksChangedThisTick);
        int eruptionRemaining = Math.max(0, maxBlocksPerEruption - blocksChangedThisEruption);
        int budget = Math.min(requestedBlocks, Math.min(tickRemaining, eruptionRemaining));
        if (targetChunkLoaded) {
            return new FlowPlan(FlowMode.SPECIALIZED, environment, budget, 0);
        }
        return new FlowPlan(FlowMode.DEFERRED, environment, 0, budget);
    }

    public synchronized boolean enqueueDeferred(ChunkPos chunk, BlockPos position) {
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(position, "position");
        ArrayDeque<BlockPos> queue = deferredByChunk.computeIfAbsent(chunk, ignored -> new ArrayDeque<>());
        if (queue.size() >= maxQueuedPositionsPerChunk) {
            return false;
        }
        queue.addLast(position.immutable());
        return true;
    }

    public synchronized int queuedForChunk(ChunkPos chunk) {
        Objects.requireNonNull(chunk, "chunk");
        ArrayDeque<BlockPos> queue = deferredByChunk.get(chunk);
        return queue == null ? 0 : queue.size();
    }

    public synchronized List<BlockPos> drainDeferred(ChunkPos chunk, int maxPositions) {
        Objects.requireNonNull(chunk, "chunk");
        if (maxPositions < 0) {
            throw new IllegalArgumentException("maxPositions must be non-negative");
        }
        if (maxPositions == 0) {
            return List.of();
        }
        ArrayDeque<BlockPos> queue = deferredByChunk.get(chunk);
        if (queue == null || queue.isEmpty()) {
            return List.of();
        }
        int count = Math.min(maxPositions, queue.size());
        List<BlockPos> drained = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            drained.add(queue.removeFirst());
        }
        if (queue.isEmpty()) {
            deferredByChunk.remove(chunk);
        }
        return List.copyOf(drained);
    }

    public enum FlowMode {
        VANILLA,
        SPECIALIZED,
        DEFERRED
    }

    public record FlowPlan(
            FlowMode mode,
            LavaEnvironmentSample environment,
            int immediateBlockBudget,
            int deferredBlockBudget
    ) {
        public FlowPlan {
            mode = Objects.requireNonNull(mode, "mode");
            environment = Objects.requireNonNull(environment, "environment");
            if (immediateBlockBudget < 0 || deferredBlockBudget < 0) {
                throw new IllegalArgumentException("flow budgets must be non-negative");
            }
        }
    }
}
