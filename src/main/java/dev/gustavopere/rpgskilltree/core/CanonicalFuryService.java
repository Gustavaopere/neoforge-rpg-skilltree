package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.OptionalDouble;

/** Canonical producer/cap/consumer boundary for A0010-A0012 Fury. */
public final class CanonicalFuryService {
    public static final double A0010_BASE_GAIN = 8.0D;

    private final long retentionMillis;
    private final CanonicalEventLedger ledger;

    public CanonicalFuryService(long retentionMillis, int maxClaims) {
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
        this.retentionMillis = retentionMillis;
        this.ledger = new CanonicalEventLedger(maxClaims);
    }

    public ProductionStatus produce(ProductionRequest request, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        if (!eligible(request.action(), request.serverAuthoritative(), request.eligibleActor(), request.direct())
            || !request.hostile()
            || !request.confirmedHit()) {
            return ProductionStatus.INELIGIBLE;
        }
        if (request.rank() == 0) return ProductionStatus.NOT_LEARNED;
        if (!ledger.claimPrimaryOnce(request.action(), "fury:producer", nowMillis, retentionMillis)) {
            return ProductionStatus.DUPLICATE;
        }

        // Frozen A0010 contract: base 8 -> rank multiplier -> legitimate target-switch x1.5 -> state cap 100.
        // baseGain remains on the request only for binary/source compatibility with the pre-freeze API and is
        // intentionally ignored so a provider cannot override the canonical producer amount.
        double gain = A0010_BASE_GAIN * (1.0D + 0.10D * request.rank());
        if (state.recordTargetAndWasDifferent(request.action().actorId(), request.targetId())) gain *= 1.50D;
        state.addFury(request.action().actorId(), gain, nowMillis);
        return ProductionStatus.APPLIED;
    }

    public ConsumptionStatus consume(ConsumptionRequest request, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        if (!eligible(request.action(), request.serverAuthoritative(), request.eligibleActor(), request.direct())) {
            return ConsumptionStatus.INELIGIBLE;
        }
        if (!ledger.claimPrimaryOnce(
            request.action(), "fury:consumer/" + request.consumerId(), nowMillis, retentionMillis)) {
            return ConsumptionStatus.DUPLICATE;
        }
        if (state.fury(request.action().actorId()) < request.minimumRequired()) {
            return ConsumptionStatus.INSUFFICIENT_RESOURCE;
        }
        state.consumeFury(request.action().actorId(), request.amount());
        return ConsumptionStatus.APPLIED;
    }

    public void clearActor(String actorId) {
        ledger.clearActor(actorId);
    }

    private static boolean eligible(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct
    ) {
        return serverAuthoritative
            && eligibleActor
            && direct
            && ProcGuard.mayTriggerSecondaryEffect(action.origin());
    }

    public enum ProductionStatus {
        APPLIED,
        DUPLICATE,
        INELIGIBLE,
        NOT_LEARNED,
        NO_GAIN,
        UNSUPPORTED_UNSPECIFIED_BASE_GAIN
    }

    public enum ConsumptionStatus {
        APPLIED,
        DUPLICATE,
        INELIGIBLE,
        INSUFFICIENT_RESOURCE
    }

    public record ProductionRequest(
        CanonicalActionIdentity action,
        String targetId,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        boolean hostile,
        boolean confirmedHit,
        int rank,
        OptionalDouble baseGain
    ) {
        public ProductionRequest {
            Objects.requireNonNull(action);
            Objects.requireNonNull(targetId);
            Objects.requireNonNull(baseGain);
            if (targetId.isBlank()) throw new IllegalArgumentException("targetId must not be blank");
            if (rank < 0 || rank > 2) throw new IllegalArgumentException("rank must be in 0..2");
            if (baseGain.isPresent()) requireFiniteNonNegative(baseGain.getAsDouble(), "baseGain");
        }

        public ProductionRequest withAction(CanonicalActionIdentity value) {
            return new ProductionRequest(
                value, targetId, serverAuthoritative, eligibleActor, direct, hostile, confirmedHit, rank, baseGain
            );
        }
    }

    public record ConsumptionRequest(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        String consumerId,
        double minimumRequired,
        double amount
    ) {
        public ConsumptionRequest {
            Objects.requireNonNull(action);
            Objects.requireNonNull(consumerId);
            if (consumerId.isBlank()) throw new IllegalArgumentException("consumerId must not be blank");
            requireFiniteNonNegative(minimumRequired, "minimumRequired");
            requireFiniteNonNegative(amount, "amount");
            if (amount > minimumRequired) {
                throw new IllegalArgumentException("amount must not exceed minimumRequired");
            }
        }

        public ConsumptionRequest withAction(CanonicalActionIdentity value) {
            return new ConsumptionRequest(
                value, serverAuthoritative, eligibleActor, direct, consumerId, minimumRequired, amount
            );
        }
    }

    private static void requireFiniteNonNegative(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }
}
