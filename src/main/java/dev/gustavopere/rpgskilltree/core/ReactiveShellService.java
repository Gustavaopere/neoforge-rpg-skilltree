package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A0105 canonical direct-hit counter and temporary relative armor/toughness window. */
public final class ReactiveShellService {
    private static final long COUNT_WINDOW_TICKS = 80L;
    private static final long ACTIVE_TICKS = 120L;
    private static final long COOLDOWN_TICKS = 400L;
    private final CanonicalEventLedger claims;
    private final Map<String, ArrayDeque<Long>> hits = new HashMap<>();
    private final Map<String, Long> activeUntil = new HashMap<>();
    private final Map<String, Long> cooldownUntil = new HashMap<>();

    public ReactiveShellService(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        claims = new CanonicalEventLedger(maxPlayers * 8);
    }

    public synchronized boolean record(Hit hit, int rank, long nowTick) {
        Objects.requireNonNull(hit);
        requireTick(nowTick);
        if (rank <= 0 || !hit.hostileEligible() || !hit.directEligible()
            || !ProcGuard.mayTriggerSecondaryEffect(hit.action().origin())) return false;
        if (!claims.claimPrimaryOnce(hit.action(), "reactive_shell:" + hit.playerId(), nowTick, 400L)) return false;
        if (activeUntil.getOrDefault(hit.playerId(), 0L) > nowTick
            || cooldownUntil.getOrDefault(hit.playerId(), 0L) > nowTick) return false;
        ArrayDeque<Long> playerHits = hits.computeIfAbsent(hit.playerId(), ignored -> new ArrayDeque<>());
        while (!playerHits.isEmpty() && nowTick - playerHits.peekFirst() > COUNT_WINDOW_TICKS) {
            playerHits.removeFirst();
        }
        playerHits.addLast(nowTick);
        if (playerHits.size() < 3) return false;
        hits.remove(hit.playerId());
        activeUntil.put(hit.playerId(), Math.addExact(nowTick, ACTIVE_TICKS));
        cooldownUntil.put(hit.playerId(), Math.addExact(nowTick, COOLDOWN_TICKS));
        return true;
    }

    public synchronized Bonuses bonuses(String playerId, double baseArmor, double baseToughness, long nowTick) {
        requireId(playerId, "playerId");
        requireFiniteNonNegative(baseArmor, "baseArmor");
        requireFiniteNonNegative(baseToughness, "baseToughness");
        requireTick(nowTick);
        if (activeUntil.getOrDefault(playerId, 0L) <= nowTick) return new Bonuses(0.0D, 0.0D);
        return new Bonuses(baseArmor * 0.15D, baseToughness * 0.08D);
    }

    public record Hit(String playerId, CanonicalActionIdentity action, boolean hostileEligible, boolean directEligible) {
        public Hit {
            requireId(playerId, "playerId");
            Objects.requireNonNull(action);
        }
    }

    public record Bonuses(double armor, double toughness) {}

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requireFiniteNonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0.0D) throw new IllegalArgumentException(field + " must be non-negative");
    }

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
