package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** A0104 threshold-crossing pulse scheduler with transient pulses and persistent cooldown. */
public final class SecondWindService {
    private static final long COOLDOWN_TICKS = 1_200L;
    private static final long CLAIM_RETENTION = 1_200L;
    private final int maxPlayers;
    private final CanonicalEventLedger damageClaims;
    private final Map<String, Window> windows = new HashMap<>();
    private final Map<String, Long> cooldownUntil = new HashMap<>();

    public SecondWindService(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        this.maxPlayers = maxPlayers;
        damageClaims = new CanonicalEventLedger(maxPlayers * 8);
    }

    public synchronized boolean onDamage(Damage damage, int rank, long nowTick) {
        Objects.requireNonNull(damage);
        requireTick(nowTick);
        if (rank <= 0 || !damage.hostileEligible() || !damage.directEligible()
            || !ProcGuard.mayTriggerSecondaryEffect(damage.action().origin())) return false;
        if (!damageClaims.claimPrimaryOnce(
            damage.action(), "second_wind:" + damage.playerId(), nowTick, CLAIM_RETENTION)) return false;

        Window active = windows.get(damage.playerId());
        if (active != null) {
            active.cancelNext();
            return false;
        }
        if (cooldownUntil.getOrDefault(damage.playerId(), 0L) > nowTick) return false;
        boolean crossed = damage.healthBefore() > damage.maxHealth() * 0.25D
            && damage.healthAfter() < damage.maxHealth() * 0.25D;
        if (!crossed) return false;
        makeRoom(damage.playerId());
        windows.put(damage.playerId(), Window.start(nowTick));
        cooldownUntil.put(damage.playerId(), Math.addExact(nowTick, COOLDOWN_TICKS));
        return true;
    }

    public synchronized Optional<Double> claimPulse(String playerId, long nowTick) {
        requireId(playerId, "playerId");
        requireTick(nowTick);
        Window window = windows.get(playerId);
        if (window == null || window.pending.isEmpty() || window.pending.peekFirst() > nowTick) {
            return Optional.empty();
        }
        window.pending.removeFirst();
        if (window.pending.isEmpty()) windows.remove(playerId);
        return Optional.of(0.024D);
    }

    public synchronized void clearTransient(String playerId) {
        requireId(playerId, "playerId");
        windows.remove(playerId);
    }

    private void makeRoom(String playerId) {
        if (windows.size() < maxPlayers || windows.containsKey(playerId)) return;
        String evicted = windows.keySet().iterator().next();
        windows.remove(evicted);
    }

    public record Damage(
        String playerId,
        CanonicalActionIdentity action,
        double healthBefore,
        double healthAfter,
        double maxHealth,
        boolean hostileEligible,
        boolean directEligible
    ) {
        public Damage {
            requireId(playerId, "playerId");
            Objects.requireNonNull(action);
            if (!Double.isFinite(maxHealth) || maxHealth <= 0.0D
                || !Double.isFinite(healthBefore) || !Double.isFinite(healthAfter)) {
                throw new IllegalArgumentException("health values must be finite and maxHealth positive");
            }
        }
    }

    private static final class Window {
        private final ArrayDeque<Long> pending;

        private Window(ArrayDeque<Long> pending) { this.pending = pending; }

        private static Window start(long tick) {
            ArrayDeque<Long> pulses = new ArrayDeque<>(5);
            for (int i = 1; i <= 5; i++) pulses.add(Math.addExact(tick, i * 20L));
            return new Window(pulses);
        }

        private void cancelNext() {
            if (!pending.isEmpty()) pending.removeFirst();
        }
    }

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
