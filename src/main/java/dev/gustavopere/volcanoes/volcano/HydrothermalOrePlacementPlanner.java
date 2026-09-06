package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Pure deterministic planner for sparse physical hydrothermal ore materialization.
 *
 * <p>The geological deposit remains the large influence/prospecting volume. This planner only
 * chooses a small, bounded subset of positions inside that volume in the feature-owning chunk;
 * host validation and mutation are deliberately left to the worldgen producer.</p>
 */
public final class HydrothermalOrePlacementPlanner {
    public static final int MAX_TARGET_BLOCKS = 32;
    public static final int MAX_PROBES_PER_DEPOSIT = 256;
    static final int MAX_SEARCH_RADIUS_BLOCKS = 16;

    private static final Set<ResourceLocation> OWNED_METALS = Set.of(
            GeologyResourceTags.COPPER_ORES.location(),
            GeologyResourceTags.IRON_ORES.location(),
            GeologyResourceTags.GOLD_ORES.location());

    private static final Comparator<ScoredPosition> BEST_FIRST = (left, right) -> {
        int score = Long.compareUnsigned(left.score(), right.score());
        if (score != 0) {
            return score;
        }
        return Long.compare(left.position().asLong(), right.position().asLong());
    };
    private static final Comparator<ScoredPosition> WORST_FIRST = BEST_FIRST.reversed();

    public Optional<Plan> plan(GeologicalDeposit deposit, ChunkPos ownerChunk) {
        Objects.requireNonNull(deposit, "deposit");
        Objects.requireNonNull(ownerChunk, "ownerChunk");
        if (deposit.origin() != DepositOrigin.HYDROTHERMAL
                || deposit.richness() <= 0.0
                || !OWNED_METALS.contains(deposit.resourceTag())) {
            return Optional.empty();
        }

        int targetBlocks = Math.min(
                MAX_TARGET_BLOCKS,
                Math.max(1, (int) Math.ceil(deposit.radius() * 2.0 * deposit.richness())));
        int searchRadius = Math.min(MAX_SEARCH_RADIUS_BLOCKS, Math.max(1, (int) Math.ceil(deposit.radius())));
        double radiusSquared = deposit.radius() * deposit.radius();
        PriorityQueue<ScoredPosition> selected = new PriorityQueue<>(MAX_PROBES_PER_DEPOSIT + 1, WORST_FIRST);

        int minX = Math.max(ownerChunk.getMinBlockX(), deposit.center().getX() - searchRadius);
        int maxX = Math.min(ownerChunk.getMaxBlockX(), deposit.center().getX() + searchRadius);
        int minZ = Math.max(ownerChunk.getMinBlockZ(), deposit.center().getZ() - searchRadius);
        int maxZ = Math.min(ownerChunk.getMaxBlockZ(), deposit.center().getZ() + searchRadius);
        int minY = deposit.center().getY() - searchRadius;
        int maxY = deposit.center().getY() + searchRadius;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos position = new BlockPos(x, y, z);
                    if (position.distSqr(deposit.center()) > radiusSquared) {
                        continue;
                    }
                    ScoredPosition candidate = new ScoredPosition(position, score(deposit, ownerChunk, position));
                    if (selected.size() < MAX_PROBES_PER_DEPOSIT) {
                        selected.add(candidate);
                    } else if (BEST_FIRST.compare(candidate, selected.peek()) < 0) {
                        selected.poll();
                        selected.add(candidate);
                    }
                }
            }
        }

        if (selected.size() < targetBlocks) {
            return Optional.empty();
        }
        List<ScoredPosition> ordered = new ArrayList<>(selected);
        ordered.sort(BEST_FIRST);
        List<BlockPos> candidates = ordered.stream().map(ScoredPosition::position).toList();
        return Optional.of(new Plan(deposit.resourceTag(), targetBlocks, candidates));
    }

    public static boolean ownsExactMetal(GeologicalDeposit deposit) {
        Objects.requireNonNull(deposit, "deposit");
        return deposit.origin() == DepositOrigin.HYDROTHERMAL
                && deposit.richness() > 0.0
                && OWNED_METALS.contains(deposit.resourceTag());
    }

    private static long score(GeologicalDeposit deposit, ChunkPos ownerChunk, BlockPos position) {
        long seed = deposit.persistenceId().getMostSignificantBits()
                ^ Long.rotateLeft(deposit.persistenceId().getLeastSignificantBits(), 29)
                ^ Long.rotateLeft(ownerChunk.toLong(), 11)
                ^ position.asLong();
        return mix64(seed);
    }

    private static long mix64(long value) {
        value ^= value >>> 30;
        value *= 0xbf58476d1ce4e5b9L;
        value ^= value >>> 27;
        value *= 0x94d049bb133111ebL;
        return value ^ (value >>> 31);
    }

    public record Plan(ResourceLocation resourceTag, int targetBlocks, List<BlockPos> candidates) {
        public Plan {
            Objects.requireNonNull(resourceTag, "resourceTag");
            candidates = List.copyOf(Objects.requireNonNull(candidates, "candidates"));
            if (targetBlocks <= 0 || targetBlocks > MAX_TARGET_BLOCKS) {
                throw new IllegalArgumentException("targetBlocks must be within the bounded physical vein budget");
            }
            if (candidates.size() < targetBlocks || candidates.size() > MAX_PROBES_PER_DEPOSIT) {
                throw new IllegalArgumentException("candidate count must cover the target within the probe cap");
            }
        }
    }

    private record ScoredPosition(BlockPos position, long score) {
    }
}
