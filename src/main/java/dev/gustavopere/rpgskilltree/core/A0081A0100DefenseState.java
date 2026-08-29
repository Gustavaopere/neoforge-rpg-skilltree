package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Minimal server-authoritative transient state needed by A0097. */
public final class A0081A0100DefenseState {
    private static final long OPENING_DELAY_MILLIS = 10_000L;
    private final Map<String, Long> lastEligibleHostileDamage = new HashMap<>();

    public synchronized void recordEligibleHostileDamage(String actorId, long nowMillis) {
        lastEligibleHostileDamage.put(requireActor(actorId), requireTime(nowMillis));
    }

    public synchronized boolean openingReady(String actorId, long nowMillis) {
        actorId = requireActor(actorId); requireTime(nowMillis);
        Long last = lastEligibleHostileDamage.get(actorId);
        return last == null || nowMillis - last >= OPENING_DELAY_MILLIS;
    }

    /** Consuming the preparation is semantically the same as beginning the next ten-second hostile-damage interval. */
    public synchronized boolean consumeOpeningDefense(String actorId, long nowMillis) {
        actorId = requireActor(actorId); requireTime(nowMillis);
        if (!openingReady(actorId, nowMillis)) return false;
        lastEligibleHostileDamage.put(actorId, nowMillis);
        return true;
    }

    public synchronized void clearActor(String actorId) { lastEligibleHostileDamage.remove(requireActor(actorId)); }
    public synchronized void clearAll() { lastEligibleHostileDamage.clear(); }

    private static String requireActor(String actorId){ Objects.requireNonNull(actorId); if(actorId.isBlank()) throw new IllegalArgumentException("actorId"); return actorId; }
    private static long requireTime(long now){ if(now<0L) throw new IllegalArgumentException("nowMillis"); return now; }
}
