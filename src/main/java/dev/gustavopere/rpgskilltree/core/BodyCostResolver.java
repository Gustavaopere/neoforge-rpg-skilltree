package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/** One causal body-cost resolution per canonical action and channel, capped exactly at 30%. */
public final class BodyCostResolver {
    public static final double MAX_SAVING_FRACTION = 0.30D;
    private final CanonicalEventLedger claims;

    public BodyCostResolver(int maxClaims) {
        claims = new CanonicalEventLedger(maxClaims);
    }

    public synchronized Resolution resolve(Request request, List<Saving> savings, long nowTick) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(savings);
        if (request.attribution() != Attribution.EXACT
            || request.cause() == Cause.UNATTRIBUTED
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) {
            return new Resolution(request.confirmedCost(), 0.0D, false, false);
        }
        boolean claimed = claims.claimPrimaryOnce(request.action(),
            "body_cost:" + request.channel(), nowTick, 1_200L);
        if (!claimed) return new Resolution(request.confirmedCost(), 0.0D, true, false);

        LinkedHashMap<String, Saving> distinct = new LinkedHashMap<>();
        for (Saving saving : savings) {
            Objects.requireNonNull(saving);
            distinct.putIfAbsent(saving.sourceId(), saving);
        }
        double requested = distinct.values().stream().mapToDouble(Saving::fraction).sum();
        double fraction = Math.min(MAX_SAVING_FRACTION, requested);
        return new Resolution(request.confirmedCost() * (1.0D - fraction), fraction, false, true);
    }

    public enum Channel { METABOLIC, HYDRATION }

    public enum Attribution { EXACT, UNATTRIBUTED, AMBIGUOUS }

    public enum Cause {
        SPRINT,
        JUMP,
        SWIM,
        CLIMB,
        MINE,
        FORESTRY,
        MELEE,
        RANGED,
        CAST,
        CARRY,
        WORK_HOT,
        WORK_COLD,
        THERMAL_HOT,
        THERMAL_COLD,
        BASAL,
        UNATTRIBUTED
    }

    public record Request(
        CanonicalActionIdentity action,
        Channel channel,
        Cause cause,
        double confirmedCost,
        Attribution attribution
    ) {
        public Request {
            Objects.requireNonNull(action);
            Objects.requireNonNull(channel);
            Objects.requireNonNull(cause);
            Objects.requireNonNull(attribution);
            if (!Double.isFinite(confirmedCost) || confirmedCost < 0.0D) {
                throw new IllegalArgumentException("confirmedCost must be finite and non-negative");
            }
        }
    }

    public record Saving(String sourceId, double fraction) {
        public Saving {
            requireId(sourceId, "sourceId");
            if (!Double.isFinite(fraction) || fraction < 0.0D || fraction > MAX_SAVING_FRACTION) {
                throw new IllegalArgumentException("saving fraction must be in [0,0.30]");
            }
        }
    }

    public record Resolution(double finalCost, double savingFraction, boolean duplicate, boolean supported) {}

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }
}
