package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleSupplier;

/**
 * Resolves and retains one critical boolean per canonical action.
 *
 * <p>The provider's existing decision and perk chance are inputs to the same resolution. Duplicate
 * callbacks return the retained boolean and never advance the RNG again.
 */
public final class CanonicalCriticalService {
    private final DoubleSupplier randomUnit;
    private final long retentionMillis;
    private final int maxDecisions;
    private final LinkedHashMap<ActionKey, TimedDecision> decisions = new LinkedHashMap<>();

    public CanonicalCriticalService(DoubleSupplier randomUnit, long retentionMillis, int maxDecisions) {
        this.randomUnit = Objects.requireNonNull(randomUnit);
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
        if (maxDecisions <= 0) throw new IllegalArgumentException("maxDecisions must be positive");
        this.retentionMillis = retentionMillis;
        this.maxDecisions = maxDecisions;
    }

    /** Returns only the canonical decision; rejected requests deterministically return false. */
    public synchronized boolean resolve(CanonicalCriticalRequest request, long nowMillis) {
        Objects.requireNonNull(request);
        requireNow(nowMillis);
        CanonicalActionIdentity action = request.action();
        if (!request.serverAuthoritative()
            || !request.eligibleActor()
            || !request.direct()
            || !ProcGuard.mayTriggerSecondaryEffect(action.origin())) {
            return false;
        }

        removeExpired(nowMillis);
        ActionKey key = new ActionKey(action.actorId(), action.actionId());
        TimedDecision previous = decisions.get(key);
        if (previous != null) return previous.criticalHit;

        boolean criticalHit = request.providerCritical();
        if (!criticalHit && request.bonusChance() > 0.0D) {
            double roll = randomUnit.getAsDouble();
            if (!Double.isFinite(roll) || roll < 0.0D || roll >= 1.0D) {
                throw new IllegalStateException("critical RNG must return a finite value in [0,1)");
            }
            criticalHit = roll < request.bonusChance();
        }

        makeRoom();
        decisions.put(key, new TimedDecision(criticalHit, Math.addExact(nowMillis, retentionMillis)));
        return criticalHit;
    }

    /** Reads the retained consumer boolean without performing a roll. */
    public synchronized Optional<Boolean> decision(CanonicalActionIdentity action, long nowMillis) {
        Objects.requireNonNull(action);
        requireNow(nowMillis);
        removeExpired(nowMillis);
        TimedDecision decision = decisions.get(new ActionKey(action.actorId(), action.actionId()));
        return decision == null ? Optional.empty() : Optional.of(decision.criticalHit);
    }

    public synchronized void clearActor(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        decisions.keySet().removeIf(key -> key.actorId.equals(actorId));
    }

    public synchronized void clear() {
        decisions.clear();
    }

    private void removeExpired(long nowMillis) {
        decisions.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private void makeRoom() {
        while (decisions.size() >= maxDecisions) {
            Iterator<Map.Entry<ActionKey, TimedDecision>> iterator = decisions.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    private record ActionKey(String actorId, String actionId) {}

    private record TimedDecision(boolean criticalHit, long expiresAtMillis) {}
}
