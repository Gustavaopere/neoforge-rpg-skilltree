package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Converts one already-partitioned eruption work snapshot into one deterministic Stage 03 hazard batch.
 *
 * <p>This class is deliberately server-level agnostic. It spends the hazard sink's immediate work
 * tokens exactly once: block tokens become ash-deposition candidates, while entity tokens are split
 * by {@link VolcanicHazardAllocation} between bomb launches and at most one pyroclastic-flow seed.
 * Concrete world adapters remain responsible for loaded-chunk/protection checks before mutation.</p>
 */
public final class VolcanicHazardBatchPlanner {
    private final AshDepositionPlanner ashPlanner;
    private final VolcanicBombPlanner bombPlanner;

    public VolcanicHazardBatchPlanner() {
        this(new AshDepositionPlanner(), new VolcanicBombPlanner());
    }

    VolcanicHazardBatchPlanner(AshDepositionPlanner ashPlanner, VolcanicBombPlanner bombPlanner) {
        this.ashPlanner = Objects.requireNonNull(ashPlanner, "ashPlanner");
        this.bombPlanner = Objects.requireNonNull(bombPlanner, "bombPlanner");
    }

    public Plan plan(VolcanicHazardQueue.HazardWork work, long gameTick) {
        Objects.requireNonNull(work, "work");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }

        EruptionSignal signal = work.signal();
        VolcanicHazardAllocation allocation = VolcanicHazardAllocation.from(signal, work.workGrant());
        EruptionScheduler.WorkGrant ashGrant = immediateGrant(allocation.ashBlockWork(), 0);
        EruptionScheduler.WorkGrant bombGrant = immediateGrant(0, allocation.bombEntityWork());

        AshPlumeEmission ashEmission = AshPlumeEmission.from(signal);
        List<BlockPos> ashCandidates = ashPlanner.candidates(ashEmission, ashGrant, gameTick);
        List<VolcanicBombLaunch> bombLaunches = bombPlanner.launches(signal, bombGrant, gameTick);
        Optional<PyroclasticFlowState> flowSeed = PyroclasticFlowPlanner.seed(
                signal,
                allocation.flowSpawnWork(),
                gameTick);

        return new Plan(allocation, ashCandidates, bombLaunches, flowSeed);
    }

    private static EruptionScheduler.WorkGrant immediateGrant(int blocks, int entities) {
        return new EruptionScheduler.WorkGrant(blocks, entities, 0, 0, 0, 0);
    }

    public record Plan(
            VolcanicHazardAllocation allocation,
            List<BlockPos> ashCandidates,
            List<VolcanicBombLaunch> bombLaunches,
            Optional<PyroclasticFlowState> flowSeed
    ) {
        public Plan {
            allocation = Objects.requireNonNull(allocation, "allocation");
            ashCandidates = List.copyOf(Objects.requireNonNull(ashCandidates, "ashCandidates"));
            bombLaunches = List.copyOf(Objects.requireNonNull(bombLaunches, "bombLaunches"));
            flowSeed = Objects.requireNonNull(flowSeed, "flowSeed");
        }
    }
}
