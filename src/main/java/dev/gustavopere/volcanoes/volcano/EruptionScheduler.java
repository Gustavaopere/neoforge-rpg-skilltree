package dev.gustavopere.volcanoes.volcano;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Server-agnostic work-budget coordinator for eruption consumers.
 *
 * <p>Budgets are global for each server tick so concurrent eruptions cannot multiply the configured
 * block/entity workload. Overflow is retained as bounded numeric backlog per eruption; concrete
 * positions/entities remain owned by downstream lava, ash, gas and integration adapters.</p>
 */
public final class EruptionScheduler {
    private final int maxBlockWorkPerTick;
    private final int maxEntityWorkPerTick;
    private final int maxQueuedBlocksPerEruption;
    private final int maxQueuedEntitiesPerEruption;
    private final Map<UUID, Backlog> backlogByEruption = new HashMap<>();

    private long currentTick = Long.MIN_VALUE;
    private int usedBlockWork;
    private int usedEntityWork;

    public EruptionScheduler(
            int maxBlockWorkPerTick,
            int maxEntityWorkPerTick,
            int maxQueuedBlocksPerEruption,
            int maxQueuedEntitiesPerEruption
    ) {
        if (maxBlockWorkPerTick <= 0
                || maxEntityWorkPerTick <= 0
                || maxQueuedBlocksPerEruption <= 0
                || maxQueuedEntitiesPerEruption <= 0) {
            throw new IllegalArgumentException("eruption work limits must be positive");
        }
        this.maxBlockWorkPerTick = maxBlockWorkPerTick;
        this.maxEntityWorkPerTick = maxEntityWorkPerTick;
        this.maxQueuedBlocksPerEruption = maxQueuedBlocksPerEruption;
        this.maxQueuedEntitiesPerEruption = maxQueuedEntitiesPerEruption;
    }

    public synchronized WorkGrant submit(
            UUID eruptionId,
            long serverTick,
            int requestedBlocks,
            int requestedEntities
    ) {
        Objects.requireNonNull(eruptionId, "eruptionId");
        requireWork(requestedBlocks, requestedEntities);
        beginTick(serverTick);

        int immediateBlocks = Math.min(requestedBlocks, maxBlockWorkPerTick - usedBlockWork);
        int immediateEntities = Math.min(requestedEntities, maxEntityWorkPerTick - usedEntityWork);
        usedBlockWork += immediateBlocks;
        usedEntityWork += immediateEntities;

        int overflowBlocks = requestedBlocks - immediateBlocks;
        int overflowEntities = requestedEntities - immediateEntities;
        Backlog backlog = backlogByEruption.computeIfAbsent(eruptionId, ignored -> new Backlog());

        int queuedBlocks = Math.min(overflowBlocks, maxQueuedBlocksPerEruption - backlog.blocks);
        int queuedEntities = Math.min(overflowEntities, maxQueuedEntitiesPerEruption - backlog.entities);
        backlog.blocks += queuedBlocks;
        backlog.entities += queuedEntities;
        removeIfEmpty(eruptionId, backlog);

        return new WorkGrant(
                immediateBlocks,
                immediateEntities,
                queuedBlocks,
                queuedEntities,
                overflowBlocks - queuedBlocks,
                overflowEntities - queuedEntities);
    }

    public synchronized WorkGrant drain(UUID eruptionId, long serverTick) {
        Objects.requireNonNull(eruptionId, "eruptionId");
        beginTick(serverTick);
        Backlog backlog = backlogByEruption.get(eruptionId);
        if (backlog == null) {
            return WorkGrant.EMPTY;
        }

        int immediateBlocks = Math.min(backlog.blocks, maxBlockWorkPerTick - usedBlockWork);
        int immediateEntities = Math.min(backlog.entities, maxEntityWorkPerTick - usedEntityWork);
        backlog.blocks -= immediateBlocks;
        backlog.entities -= immediateEntities;
        usedBlockWork += immediateBlocks;
        usedEntityWork += immediateEntities;
        removeIfEmpty(eruptionId, backlog);

        return new WorkGrant(immediateBlocks, immediateEntities, 0, 0, 0, 0);
    }

    /** Discards deferred numeric work owned by an eruption that has been retired. */
    public synchronized boolean clear(UUID eruptionId) {
        Objects.requireNonNull(eruptionId, "eruptionId");
        return backlogByEruption.remove(eruptionId) != null;
    }

    public synchronized int queuedBlocks(UUID eruptionId) {
        Objects.requireNonNull(eruptionId, "eruptionId");
        Backlog backlog = backlogByEruption.get(eruptionId);
        return backlog == null ? 0 : backlog.blocks;
    }

    public synchronized int queuedEntities(UUID eruptionId) {
        Objects.requireNonNull(eruptionId, "eruptionId");
        Backlog backlog = backlogByEruption.get(eruptionId);
        return backlog == null ? 0 : backlog.entities;
    }

    private void beginTick(long serverTick) {
        if (serverTick < 0L) {
            throw new IllegalArgumentException("serverTick must be non-negative");
        }
        if (currentTick != Long.MIN_VALUE && serverTick < currentTick) {
            throw new IllegalArgumentException("serverTick must not move backwards");
        }
        if (serverTick != currentTick) {
            currentTick = serverTick;
            usedBlockWork = 0;
            usedEntityWork = 0;
        }
    }

    private static void requireWork(int requestedBlocks, int requestedEntities) {
        if (requestedBlocks < 0 || requestedEntities < 0) {
            throw new IllegalArgumentException("requested eruption work must be non-negative");
        }
    }

    private void removeIfEmpty(UUID eruptionId, Backlog backlog) {
        if (backlog.blocks == 0 && backlog.entities == 0) {
            backlogByEruption.remove(eruptionId);
        }
    }

    private static final class Backlog {
        private int blocks;
        private int entities;
    }

    public record WorkGrant(
            int immediateBlocks,
            int immediateEntities,
            int queuedBlocks,
            int queuedEntities,
            int droppedBlocks,
            int droppedEntities
    ) {
        private static final WorkGrant EMPTY = new WorkGrant(0, 0, 0, 0, 0, 0);

        public WorkGrant {
            if (immediateBlocks < 0
                    || immediateEntities < 0
                    || queuedBlocks < 0
                    || queuedEntities < 0
                    || droppedBlocks < 0
                    || droppedEntities < 0) {
                throw new IllegalArgumentException("eruption work grant values must be non-negative");
            }
        }
    }
}
