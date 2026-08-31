package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.Optional;

/**
 * One bounded emission path joining persisted eruption progress, work budgeting and consumer fanout.
 *
 * <p>Work counts are scheduling tokens, not direct world mutations. Concrete lava, ash, gas and
 * projectile consumers remain responsible for applying only the work represented by the shared grant.
 * Demand scales deterministically with the current physical eruption signal and all overflow remains
 * bounded by {@link EruptionScheduler}.</p>
 */
public final class EruptionEffectRuntime {
    private static final EruptionScheduler.WorkGrant NO_WORK =
            new EruptionScheduler.WorkGrant(0, 0, 0, 0, 0, 0);
    private static final EruptionDispatcher.DispatchResult NO_DISPATCH =
            new EruptionDispatcher.DispatchResult(0, 0);

    private final EruptionRuntimeCoordinator coordinator;
    private final EruptionScheduler scheduler;
    private final EruptionDispatcher dispatcher;

    public EruptionEffectRuntime(
            EruptionRuntimeCoordinator coordinator,
            EruptionScheduler scheduler,
            EruptionDispatcher dispatcher
    ) {
        this.coordinator = Objects.requireNonNull(coordinator, "coordinator");
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
        this.dispatcher = Objects.requireNonNull(dispatcher, "dispatcher");
    }

    public EmissionResult update(
            VolcanoSavedData data,
            VolcanoSite site,
            MagmaChamber chamber,
            long gameTick,
            long elapsedTicks
    ) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(site, "site");
        Objects.requireNonNull(chamber, "chamber");

        Optional<EruptionSignal> signal = coordinator.update(data, site, chamber, gameTick, elapsedTicks);
        if (signal.isEmpty()) {
            if (site.state() != VolcanoState.ERUPTING) {
                scheduler.clear(site.persistenceId());
            }
            return EmissionResult.NONE;
        }

        EruptionSignal snapshot = signal.orElseThrow();
        if (snapshot.phase() == EruptionPhase.DORMANT) {
            scheduler.clear(snapshot.volcanoId());
            EruptionDispatcher.DispatchResult dispatch = dispatcher.dispatch(snapshot, NO_WORK);
            return new EmissionResult(true, NO_WORK, dispatch);
        }

        EruptionScheduler.WorkGrant drained = scheduler.drain(snapshot.volcanoId(), gameTick);
        EruptionScheduler.WorkGrant submitted = scheduler.submit(
                snapshot.volcanoId(),
                gameTick,
                requestedBlockWork(snapshot),
                requestedEntityWork(snapshot));
        EruptionScheduler.WorkGrant combined = new EruptionScheduler.WorkGrant(
                drained.immediateBlocks() + submitted.immediateBlocks(),
                drained.immediateEntities() + submitted.immediateEntities(),
                scheduler.queuedBlocks(snapshot.volcanoId()),
                scheduler.queuedEntities(snapshot.volcanoId()),
                submitted.droppedBlocks(),
                submitted.droppedEntities());
        EruptionDispatcher.DispatchResult dispatch = dispatcher.dispatch(snapshot, combined);
        return new EmissionResult(true, combined, dispatch);
    }

    private static int requestedBlockWork(EruptionSignal signal) {
        if (signal.intensity() <= 0.0) {
            return 0;
        }
        double scaled = signal.profile().outerRadiusBlocks() * signal.intensity();
        return Math.max(2, (int) Math.ceil(scaled));
    }

    private static int requestedEntityWork(EruptionSignal signal) {
        if (signal.intensity() <= 0.0) {
            return 0;
        }
        return Math.max(1, (int) Math.ceil(signal.intensity() * 4.0));
    }

    public record EmissionResult(
            boolean signalEmitted,
            EruptionScheduler.WorkGrant workGrant,
            EruptionDispatcher.DispatchResult dispatch
    ) {
        private static final EmissionResult NONE = new EmissionResult(false, NO_WORK, NO_DISPATCH);

        public EmissionResult {
            workGrant = Objects.requireNonNull(workGrant, "workGrant");
            dispatch = Objects.requireNonNull(dispatch, "dispatch");
        }
    }
}
