package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** One canonical A0092/A0096-A0099 mitigation decision per received damage action. */
public final class FrozenVitalityDefenseService {
    private static final long OPENING_DELAY_MILLIS = 10_000L;
    private static final long CLAIM_RETENTION_MILLIS = 30_000L;
    private final CanonicalEventLedger events = new CanonicalEventLedger(16_384);
    private final Map<String, Long> lastHostileDamage = new HashMap<>();

    /**
     * Starts the authoritative no-hostile-damage clock without requiring a sacrificial first hit.
     * Re-observation never rearms the clock, so ticks, respawns and dimension changes cannot reset it.
     */
    public synchronized void observeActor(String actorId, long nowMillis) {
        Objects.requireNonNull(actorId);
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis");
        lastHostileDamage.putIfAbsent(actorId, nowMillis);
    }

    public synchronized Resolution resolve(Request request, FrozenCombatPerkRanks ranks, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(ranks);
        validate(request, nowMillis);
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.mitigable()) {
            return Resolution.ineligible();
        }
        if (!events.claimPrimaryOnce(
            request.action(), "frozen:vitality-defense", nowMillis, CLAIM_RETENTION_MILLIS)) {
            return Resolution.duplicate();
        }

        double multiplier = 1.0D;
        if (request.physical()) {
            multiplier *= 1.0D - 0.02D * ranks.rank("A0092");
            if (request.hostile() && request.preImpactHealthFraction() < 0.30D) {
                multiplier *= 1.0D - 0.04D * ranks.rank("A0096");
            }
        }

        if (request.hostile()) {
            String actorId = request.action().actorId();
            long last = lastHostileDamage.getOrDefault(actorId, nowMillis);
            lastHostileDamage.put(actorId, nowMillis);
            if (nowMillis - last >= OPENING_DELAY_MILLIS) {
                multiplier *= 1.0D - 0.05D * ranks.rank("A0097");
            }
            if (request.sprinting() && !request.forcedDisplacement()) {
                multiplier *= 1.0D - 0.03D * ranks.rank("A0098");
            }
            if (request.stationary()) {
                multiplier *= 1.0D - 0.04D * ranks.rank("A0099");
            }
        }
        return new Resolution(Status.RESOLVED, multiplier);
    }

    /** Hostile timers and event claims survive lifecycle transitions to prevent re-arm bypasses. */
    public synchronized void clearTransient(String actorId) {
        Objects.requireNonNull(actorId);
    }

    private static void validate(Request request, long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis");
        if (!Double.isFinite(request.preImpactHealthFraction())
            || request.preImpactHealthFraction() < 0.0D || request.preImpactHealthFraction() > 1.0D) {
            throw new IllegalArgumentException("preImpactHealthFraction");
        }
    }

    public enum Status { RESOLVED, DUPLICATE, INELIGIBLE }

    public record Request(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean hostile,
        boolean physical,
        boolean mitigable,
        boolean sprinting,
        boolean forcedDisplacement,
        boolean stationary,
        double preImpactHealthFraction
    ) {
        public Request { Objects.requireNonNull(action); }
    }

    public record Resolution(Status status, double damageMultiplier) {
        static Resolution duplicate() { return new Resolution(Status.DUPLICATE, 1.0D); }
        static Resolution ineligible() { return new Resolution(Status.INELIGIBLE, 1.0D); }
    }
}
