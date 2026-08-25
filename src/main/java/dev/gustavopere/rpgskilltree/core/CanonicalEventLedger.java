package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Bounded, transient idempotency ledger shared by root-action consumers. */
public final class CanonicalEventLedger {
    private final int maxClaims;
    private final LinkedHashMap<ClaimKey, Long> claims = new LinkedHashMap<>();

    public CanonicalEventLedger(int maxClaims) {
        if (maxClaims <= 0) throw new IllegalArgumentException("maxClaims must be positive");
        this.maxClaims = maxClaims;
    }

    /**
     * Claims a root-only consumer once for an actor/action pair.
     *
     * <p>Rejected proc-depth claims are not recorded, so a forged secondary callback cannot consume
     * the legitimate root claim.
     */
    public synchronized boolean claimPrimaryOnce(
        CanonicalActionIdentity action,
        String consumerId,
        long nowMillis,
        long retentionMillis
    ) {
        Objects.requireNonNull(action);
        requireConsumerId(consumerId);
        requireTime(nowMillis, retentionMillis);
        if (!ProcGuard.mayTriggerSecondaryEffect(action.origin())) return false;

        removeExpired(nowMillis);
        ClaimKey key = new ClaimKey(action.actorId(), action.actionId(), consumerId);
        if (claims.containsKey(key)) return false;
        makeRoom();
        claims.put(key, Math.addExact(nowMillis, retentionMillis));
        return true;
    }

    public synchronized void clearActor(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        claims.keySet().removeIf(key -> key.actorId.equals(actorId));
    }

    public synchronized void clear() {
        claims.clear();
    }

    private void removeExpired(long nowMillis) {
        claims.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
    }

    private void makeRoom() {
        while (claims.size() >= maxClaims) {
            Iterator<Map.Entry<ClaimKey, Long>> iterator = claims.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
    }

    private static void requireConsumerId(String consumerId) {
        Objects.requireNonNull(consumerId);
        if (consumerId.isBlank()) throw new IllegalArgumentException("consumerId must not be blank");
    }

    private static void requireTime(long nowMillis, long retentionMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
    }

    private record ClaimKey(String actorId, String actionId, String consumerId) {}
}
