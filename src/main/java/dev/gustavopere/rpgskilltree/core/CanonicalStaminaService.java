package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

/** Exact action-to-cost correlation for stamina effects; pre-consume intents never authorize refunds. */
public final class CanonicalStaminaService {
    private final long retentionMillis;
    private final int maxCosts;
    private final LinkedHashMap<ActionKey, TimedCost> costs = new LinkedHashMap<>();
    private final CanonicalEventLedger refunds;

    public CanonicalStaminaService(long retentionMillis, int maxCosts) {
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
        if (maxCosts <= 0) throw new IllegalArgumentException("maxCosts must be positive");
        this.retentionMillis = retentionMillis;
        this.maxCosts = maxCosts;
        this.refunds = new CanonicalEventLedger(Math.multiplyExact(maxCosts, 2));
    }

    public synchronized CostStatus observe(CostObservation observation, long nowMillis) {
        Objects.requireNonNull(observation);
        requireNow(nowMillis);
        prune(nowMillis);
        CanonicalActionIdentity action = observation.action();
        if (!observation.serverAuthoritative()
            || !observation.eligibleActor()
            || !ProcGuard.mayTriggerSecondaryEffect(action.origin())) {
            return CostStatus.INELIGIBLE;
        }
        if (observation.stage() == ObservationStage.PRE_CONSUME_INTENT) {
            return CostStatus.UNSUPPORTED_PRE_CONSUME_ONLY;
        }
        ActionKey key = ActionKey.of(action);
        if (costs.containsKey(key)) return CostStatus.DUPLICATE;
        makeRoom();
        costs.put(
            key,
            new TimedCost(observation.exactCost(), observation.evidenceId(), Math.addExact(nowMillis, retentionMillis))
        );
        return CostStatus.RECORDED;
    }

    /** Read-only inspection of a confirmed exact post-consume receipt. This does not claim a refund. */
    public synchronized Optional<ExactCostReceipt> receipt(CanonicalActionIdentity action, long nowMillis) {
        Objects.requireNonNull(action);
        requireNow(nowMillis);
        if (!ProcGuard.mayTriggerSecondaryEffect(action.origin())) return Optional.empty();
        prune(nowMillis);
        TimedCost cost = costs.get(ActionKey.of(action));
        return cost == null
            ? Optional.empty()
            : Optional.of(new ExactCostReceipt(cost.exactCost, cost.evidenceId));
    }

    public synchronized OptionalDouble refundAmount(
        CanonicalActionIdentity action,
        String consumerId,
        double fraction,
        long nowMillis
    ) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(consumerId);
        requireNow(nowMillis);
        if (consumerId.isBlank()) throw new IllegalArgumentException("consumerId must not be blank");
        if (!Double.isFinite(fraction) || fraction <= 0.0D || fraction > 1.0D) {
            throw new IllegalArgumentException("fraction must be finite and in (0,1]");
        }
        if (!ProcGuard.mayTriggerSecondaryEffect(action.origin())) return OptionalDouble.empty();
        prune(nowMillis);
        TimedCost cost = costs.get(ActionKey.of(action));
        if (cost == null) return OptionalDouble.empty();
        if (!refunds.claimPrimaryOnce(action, "stamina:refund/" + consumerId, nowMillis, retentionMillis)) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(cost.exactCost * fraction);
    }

    public synchronized void clearActor(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        costs.keySet().removeIf(key -> key.actorId.equals(actorId));
        refunds.clearActor(actorId);
    }

    private void prune(long nowMillis) {
        costs.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private void makeRoom() {
        while (costs.size() >= maxCosts) {
            Iterator<Map.Entry<ActionKey, TimedCost>> iterator = costs.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    public enum ObservationStage {
        PRE_CONSUME_INTENT,
        POST_CONSUME_CONFIRMED
    }

    public enum CostStatus {
        RECORDED,
        DUPLICATE,
        INELIGIBLE,
        UNSUPPORTED_PRE_CONSUME_ONLY
    }

    public record ExactCostReceipt(double exactCost, String evidenceId) {
        public ExactCostReceipt {
            if (!Double.isFinite(exactCost) || exactCost < 0.0D) {
                throw new IllegalArgumentException("exactCost must be finite and non-negative");
            }
            Objects.requireNonNull(evidenceId);
            if (evidenceId.isBlank()) throw new IllegalArgumentException("evidenceId must not be blank");
        }
    }

    public record CostObservation(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        double exactCost,
        String evidenceId,
        ObservationStage stage
    ) {
        public CostObservation {
            Objects.requireNonNull(action);
            Objects.requireNonNull(evidenceId);
            Objects.requireNonNull(stage);
            if (!Double.isFinite(exactCost) || exactCost < 0.0D) {
                throw new IllegalArgumentException("exactCost must be finite and non-negative");
            }
            if (evidenceId.isBlank()) throw new IllegalArgumentException("evidenceId must not be blank");
        }
    }

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) {
            return new ActionKey(action.actorId(), action.actionId());
        }
    }

    private record TimedCost(double exactCost, String evidenceId, long expiresAtMillis) {}
}
