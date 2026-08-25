package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** FIFO correlation of TFC exhaustion contributions to later base hydration charges. */
public final class TfcExhaustionHydrationLedger {
    private static final double EPSILON = 0.000000001D;
    private final int maxSegments;
    private final CanonicalEventLedger claims;
    private final Map<String, ArrayDeque<Segment>> buckets = new HashMap<>();

    public TfcExhaustionHydrationLedger(int maxSegments) {
        if (maxSegments <= 0) throw new IllegalArgumentException("maxSegments must be positive");
        this.maxSegments = maxSegments;
        claims = new CanonicalEventLedger(maxSegments * 2);
    }

    public synchronized boolean recordKnown(
        String playerId,
        CanonicalActionIdentity action,
        BodyCostResolver.Cause cause,
        double exactExhaustion,
        long nowTick
    ) {
        requireId(playerId, "playerId");
        Objects.requireNonNull(action);
        Objects.requireNonNull(cause);
        requirePositive(exactExhaustion, "exactExhaustion");
        if (!action.actorId().equals(playerId)
            || cause == BodyCostResolver.Cause.UNATTRIBUTED
            || !ProcGuard.mayTriggerSecondaryEffect(action.origin())) return false;
        if (!claims.claimPrimaryOnce(action, "tfc_exhaustion:" + playerId, nowTick, 12_000L)) return false;
        append(playerId, new Segment(
            Optional.of(action.actionId()), cause, BodyCostResolver.Attribution.EXACT, exactExhaustion));
        return true;
    }

    /** Appends observed real bucket growth for which no canonical action contribution is known. */
    public synchronized void recordUnattributed(String playerId, double exactExhaustion) {
        requireId(playerId, "playerId");
        requirePositive(exactExhaustion, "exactExhaustion");
        append(playerId, Segment.unattributed(exactExhaustion));
    }

    public synchronized HydrationAllocation consume(
        String playerId,
        double exhaustionConsumed,
        double baseHydrationCost,
        double thermalHotHydrationCost
    ) {
        requireId(playerId, "playerId");
        requirePositive(exhaustionConsumed, "exhaustionConsumed");
        requireNonNegative(baseHydrationCost, "baseHydrationCost");
        requireNonNegative(thermalHotHydrationCost, "thermalHotHydrationCost");
        ArrayDeque<Segment> bucket = buckets.computeIfAbsent(playerId, ignored -> new ArrayDeque<>());
        ArrayList<BaseShare> shares = new ArrayList<>();
        double remaining = exhaustionConsumed;
        while (remaining > EPSILON && !bucket.isEmpty()) {
            Segment segment = bucket.removeFirst();
            double taken = Math.min(remaining, segment.exhaustion);
            addShare(shares, segment, taken, baseHydrationCost * taken / exhaustionConsumed);
            remaining -= taken;
            double leftover = segment.exhaustion - taken;
            if (leftover > EPSILON) bucket.addFirst(segment.withExhaustion(leftover));
        }
        if (remaining > EPSILON) {
            addShare(shares, Segment.unattributed(remaining), remaining,
                baseHydrationCost * remaining / exhaustionConsumed);
        }
        if (bucket.isEmpty()) buckets.remove(playerId);
        return new HydrationAllocation(List.copyOf(shares), thermalHotHydrationCost,
            BodyCostResolver.Cause.THERMAL_HOT);
    }

    /** Outstanding real TFC bucket state is not transient and cannot be erased by lifecycle hopping. */
    public synchronized void clearTransient(String playerId) {
        requireId(playerId, "playerId");
    }

    private void append(String playerId, Segment segment) {
        ArrayDeque<Segment> bucket = buckets.computeIfAbsent(playerId, ignored -> new ArrayDeque<>());
        Segment tail = bucket.peekLast();
        if (tail != null && tail.sameIdentity(segment)) {
            bucket.removeLast();
            bucket.addLast(tail.withExhaustion(tail.exhaustion + segment.exhaustion));
            return;
        }
        if (bucket.size() >= maxSegments) {
            Segment displaced = bucket.removeLast();
            bucket.addLast(Segment.unattributed(displaced.exhaustion + segment.exhaustion));
            return;
        }
        bucket.addLast(segment);
    }

    private static void addShare(
        List<BaseShare> shares,
        Segment segment,
        double exhaustionShare,
        double hydrationCost
    ) {
        if (!shares.isEmpty()) {
            BaseShare tail = shares.get(shares.size() - 1);
            if (tail.actionId.equals(segment.actionId)
                && tail.cause == segment.cause
                && tail.attribution == segment.attribution) {
                shares.set(shares.size() - 1, new BaseShare(
                    tail.actionId, tail.cause, tail.attribution,
                    tail.exhaustionShare + exhaustionShare, tail.hydrationCost + hydrationCost));
                return;
            }
        }
        shares.add(new BaseShare(segment.actionId, segment.cause, segment.attribution,
            exhaustionShare, hydrationCost));
    }

    public record BaseShare(
        Optional<String> actionId,
        BodyCostResolver.Cause cause,
        BodyCostResolver.Attribution attribution,
        double exhaustionShare,
        double hydrationCost
    ) {
        public BaseShare {
            actionId = Objects.requireNonNull(actionId);
            Objects.requireNonNull(cause);
            Objects.requireNonNull(attribution);
        }
    }

    public record HydrationAllocation(
        List<BaseShare> baseShares,
        double thermalHotHydrationCost,
        BodyCostResolver.Cause thermalCause
    ) {
        public HydrationAllocation {
            baseShares = List.copyOf(baseShares);
            Objects.requireNonNull(thermalCause);
            if (thermalCause != BodyCostResolver.Cause.THERMAL_HOT) {
                throw new IllegalArgumentException("thermal lane must remain THERMAL_HOT");
            }
        }
    }

    private record Segment(
        Optional<String> actionId,
        BodyCostResolver.Cause cause,
        BodyCostResolver.Attribution attribution,
        double exhaustion
    ) {
        private static Segment unattributed(double exhaustion) {
            return new Segment(Optional.empty(), BodyCostResolver.Cause.UNATTRIBUTED,
                BodyCostResolver.Attribution.UNATTRIBUTED, exhaustion);
        }

        private Segment withExhaustion(double value) {
            return new Segment(actionId, cause, attribution, value);
        }

        private boolean sameIdentity(Segment other) {
            return actionId.equals(other.actionId) && cause == other.cause && attribution == other.attribution;
        }
    }

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requirePositive(double value, String field) {
        if (!Double.isFinite(value) || value <= 0.0D) {
            throw new IllegalArgumentException(field + " must be finite and positive");
        }
    }

    private static void requireNonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(field + " must be finite and non-negative");
        }
    }
}
