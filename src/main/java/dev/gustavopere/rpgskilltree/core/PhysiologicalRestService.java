package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative A0142 rest clock. */
public final class PhysiologicalRestService {
    private static final long REST_TICKS = 200L;
    private final int maxPlayers;
    private final Map<String, Long> lastHostile = new HashMap<>();
    private final Map<String, Long> lastPhysicalBodyCost = new HashMap<>();

    public PhysiologicalRestService(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        this.maxPlayers = maxPlayers;
    }

    public synchronized void recordHostileCombat(String playerId, long nowTick) {
        touch(playerId, nowTick);
        lastHostile.put(playerId, nowTick);
    }

    public synchronized void recordPhysicalBodyCost(String playerId, long nowTick) {
        touch(playerId, nowTick);
        lastPhysicalBodyCost.put(playerId, nowTick);
    }

    public synchronized void invalidateLifecycle(String playerId, long nowTick) {
        recordHostileCombat(playerId, nowTick);
        lastPhysicalBodyCost.remove(playerId);
    }

    public synchronized boolean resting(String playerId, long nowTick) {
        requireId(playerId);
        requireTick(nowTick);
        Long hostile = lastHostile.get(playerId);
        if (hostile == null || nowTick - hostile < REST_TICKS) return false;
        return lastPhysicalBodyCost.getOrDefault(playerId, -1L) != nowTick;
    }

    private void touch(String playerId, long nowTick) {
        requireId(playerId);
        requireTick(nowTick);
        if (!lastHostile.containsKey(playerId) && lastHostile.size() >= maxPlayers) {
            throw new IllegalStateException("physiological rest resident capacity exceeded");
        }
    }

    private static void requireId(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException("playerId must not be blank");
    }

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
