package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient state for A0097 Primeira Defesa. */
public final class A0081A0100DefenseState {
    public static final long OPENING_DELAY_MILLIS = 10_000L;
    public static final long RESERVATION_RETENTION_MILLIS = 1_000L;

    private final Map<String, Long> lastEligibleHostileDamage = new HashMap<>();
    private final Map<String, OpeningReservation> openingReservations = new HashMap<>();

    /** Starts the ten-second preparation clock without fabricating a hostile-damage receipt. */
    public synchronized void ensureActor(String actorId, long nowMillis) {
        actorId = requireActor(actorId);
        requireTime(nowMillis);
        lastEligibleHostileDamage.putIfAbsent(actorId, nowMillis);
        expireReservation(actorId, nowMillis);
    }

    public synchronized void recordEligibleHostileDamage(String actorId, long nowMillis) {
        lastEligibleHostileDamage.put(requireActor(actorId), requireTime(nowMillis));
    }

    public synchronized boolean openingReady(String actorId, long nowMillis) {
        actorId = requireActor(actorId);
        requireTime(nowMillis);
        expireReservation(actorId, nowMillis);
        Long last = lastEligibleHostileDamage.get(actorId);
        return last != null
            && nowMillis - last >= OPENING_DELAY_MILLIS
            && !openingReservations.containsKey(actorId);
    }

    /**
     * Reserves the prepared defense for one causal root without restarting the preparation timer.
     * Repeating the same root is idempotent; a concurrent different root fails closed.
     */
    public synchronized boolean reserveOpeningDefense(String actorId, String rootActionId, long nowMillis) {
        actorId = requireActor(actorId);
        rootActionId = requireRoot(rootActionId);
        requireTime(nowMillis);
        expireReservation(actorId, nowMillis);

        OpeningReservation existing = openingReservations.get(actorId);
        if (existing != null) return existing.rootActionId().equals(rootActionId);
        if (!openingReady(actorId, nowMillis)) return false;
        openingReservations.put(actorId, new OpeningReservation(rootActionId, nowMillis));
        return true;
    }

    /** Commits exactly the reserved effective hit and restarts the ten-second preparation timer. */
    public synchronized boolean commitOpeningDefense(String actorId, String rootActionId, long nowMillis) {
        actorId = requireActor(actorId);
        rootActionId = requireRoot(rootActionId);
        requireTime(nowMillis);
        expireReservation(actorId, nowMillis);
        OpeningReservation reservation = openingReservations.get(actorId);
        if (reservation == null || !reservation.rootActionId().equals(rootActionId)) return false;
        openingReservations.remove(actorId);
        lastEligibleHostileDamage.put(actorId, nowMillis);
        return true;
    }

    /** Rolls back only the matching causal root; duplicate/foreign callbacks cannot consume it. */
    public synchronized boolean rollbackOpeningDefense(String actorId, String rootActionId) {
        actorId = requireActor(actorId);
        rootActionId = requireRoot(rootActionId);
        OpeningReservation reservation = openingReservations.get(actorId);
        if (reservation == null || !reservation.rootActionId().equals(rootActionId)) return false;
        openingReservations.remove(actorId);
        return true;
    }

    public synchronized void clearActor(String actorId) {
        actorId = requireActor(actorId);
        lastEligibleHostileDamage.remove(actorId);
        openingReservations.remove(actorId);
    }

    public synchronized void clearAll() {
        lastEligibleHostileDamage.clear();
        openingReservations.clear();
    }

    private void expireReservation(String actorId, long nowMillis) {
        OpeningReservation reservation = openingReservations.get(actorId);
        if (reservation != null && nowMillis - reservation.reservedAtMillis() > RESERVATION_RETENTION_MILLIS) {
            openingReservations.remove(actorId);
        }
    }

    private static String requireActor(String actorId) {
        Objects.requireNonNull(actorId, "actorId");
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId");
        return actorId;
    }

    private static String requireRoot(String rootActionId) {
        Objects.requireNonNull(rootActionId, "rootActionId");
        if (rootActionId.isBlank()) throw new IllegalArgumentException("rootActionId");
        return rootActionId;
    }

    private static long requireTime(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis");
        return nowMillis;
    }

    private record OpeningReservation(String rootActionId, long reservedAtMillis) {}
}
