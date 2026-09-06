package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.geology.DepositLifecycleSink;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Reconciles the authoritative Volcanoes deposit lifecycle into transient RNS custom deposits.
 *
 * <p>Every lifecycle notification recomputes the complete deterministic projection plan. This is
 * deliberate: RNS identifies a deposit by structure key plus chunk rather than by the Volcanoes
 * persistence UUID, so incrementally accepting the first arrival would make collisions dependent on
 * replay/event order. Failed host mutations remain in {@code applied} and are retried by later
 * lifecycle notifications instead of being reported as reconciled.</p>
 */
public final class RnsDepositLifecycleBridge implements DepositLifecycleSink {
    private static final Comparator<RnsDepositProjectionPlanner.Projection> PROJECTION_ORDER =
            Comparator.comparing(projection -> projection.sourceId().toString());

    private final RnsDepositProjectionWriter writer;
    private final Map<UUID, GeologicalDeposit> authoritative = new LinkedHashMap<>();
    private final Map<UUID, RnsDepositProjectionPlanner.Projection> applied = new LinkedHashMap<>();
    private RnsDepositProjectionPlanner.Plan lastPlan = new RnsDepositProjectionPlanner.Plan(List.of(), List.of());

    public RnsDepositLifecycleBridge(RnsDepositProjectionWriter writer) {
        this.writer = RnsDepositProjectionWriter.failSafe(Objects.requireNonNull(writer, "writer"));
    }

    @Override
    public synchronized void upsert(GeologicalDeposit deposit) {
        Objects.requireNonNull(deposit, "deposit");
        authoritative.put(deposit.persistenceId(), deposit);
        reconcile();
    }

    @Override
    public synchronized void remove(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        authoritative.remove(persistenceId);
        reconcile();
    }

    /** Retries reconciliation against the current authoritative snapshot. */
    public synchronized void reconcile() {
        lastPlan = RnsDepositProjectionPlanner.plan(List.copyOf(authoritative.values()));
        Map<UUID, RnsDepositProjectionPlanner.Projection> desired = new LinkedHashMap<>();
        for (RnsDepositProjectionPlanner.Projection projection : lastPlan.projections()) {
            desired.put(projection.sourceId(), projection);
        }

        List<RnsDepositProjectionPlanner.Projection> removals = applied.values().stream()
                .filter(projection -> !projection.equals(desired.get(projection.sourceId())))
                .sorted(PROJECTION_ORDER)
                .toList();
        for (RnsDepositProjectionPlanner.Projection projection : removals) {
            if (writer.ensureAbsent(projection)) {
                applied.remove(projection.sourceId());
            }
        }

        for (RnsDepositProjectionPlanner.Projection projection : lastPlan.projections()) {
            RnsDepositProjectionPlanner.Projection existingForSource = applied.get(projection.sourceId());
            if (projection.equals(existingForSource)) {
                continue;
            }
            if (existingForSource != null) {
                // A previous projection for this source could not be removed. Do not create a second one.
                continue;
            }
            if (hostIdentityAlreadyApplied(projection)) {
                // A stale projection occupies the RNS (structure, chunk) identity. Fail closed until removed.
                continue;
            }
            if (writer.ensurePresent(projection)) {
                applied.put(projection.sourceId(), projection);
            }
        }
    }

    public synchronized int appliedProjectionCount() {
        return applied.size();
    }

    public synchronized int authoritativeSourceCount() {
        return authoritative.size();
    }

    public synchronized int collisionCount() {
        return lastPlan.collisions().size();
    }

    private boolean hostIdentityAlreadyApplied(RnsDepositProjectionPlanner.Projection desired) {
        RnsDepositProjectionPlanner.RnsIdentity identity = RnsDepositProjectionPlanner.identity(desired);
        return applied.values().stream()
                .anyMatch(existing -> RnsDepositProjectionPlanner.identity(existing).equals(identity));
    }
}
