package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A0106 post-prior-mitigation emergency window and single fatal safeguard. */
public final class EmergencyGuardService {
    private static final long ACTIVE_TICKS = 60L;
    private static final long COOLDOWN_TICKS = 3_600L;
    private final CanonicalEventLedger claims;
    private final Map<String, Window> windows = new HashMap<>();
    private final Map<String, Long> cooldownUntil = new HashMap<>();

    public EmergencyGuardService(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        claims = new CanonicalEventLedger(maxPlayers * 8);
    }

    public synchronized Resolution resolve(Damage damage, int rank, long nowTick) {
        Objects.requireNonNull(damage);
        requireTick(nowTick);
        if (rank <= 0 || !damage.hostileEligible()
            || !ProcGuard.mayTriggerSecondaryEffect(damage.action().origin())) return unchanged(damage);
        if (!claims.claimPrimaryOnce(damage.action(), "emergency_guard:" + damage.playerId(), nowTick, COOLDOWN_TICKS)) {
            return new Resolution(damage.incomingDamage(), false, false, true);
        }

        Window window = windows.get(damage.playerId());
        boolean active = window != null && window.untilTick > nowTick;
        boolean activated = false;
        if (!active
            && cooldownUntil.getOrDefault(damage.playerId(), 0L) <= nowTick
            && damage.health() - damage.incomingDamage() < damage.maxHealth() * 0.15D) {
            window = new Window(Math.addExact(nowTick, ACTIVE_TICKS), true);
            windows.put(damage.playerId(), window);
            cooldownUntil.put(damage.playerId(), Math.addExact(nowTick, COOLDOWN_TICKS));
            active = true;
            activated = true;
        }
        if (!active) return unchanged(damage);

        double finalDamage = damage.incomingDamage() * 0.65D;
        boolean fatalSaveConsumed = false;
        if (window.fatalSaveAvailable && finalDamage > damage.health() - 1.0D) {
            finalDamage = Math.max(0.0D, damage.health() - 1.0D);
            window.fatalSaveAvailable = false;
            fatalSaveConsumed = true;
        }
        return new Resolution(finalDamage, activated, fatalSaveConsumed, false);
    }

    public synchronized void clearTransient(String playerId) {
        requireId(playerId, "playerId");
        windows.remove(playerId);
    }

    private static Resolution unchanged(Damage damage) {
        return new Resolution(damage.incomingDamage(), false, false, false);
    }

    public record Damage(
        String playerId,
        CanonicalActionIdentity action,
        double maxHealth,
        double health,
        double incomingDamage,
        boolean hostileEligible
    ) {
        public Damage {
            requireId(playerId, "playerId");
            Objects.requireNonNull(action);
            if (!Double.isFinite(maxHealth) || maxHealth <= 0.0D
                || !Double.isFinite(health) || health < 0.0D
                || !Double.isFinite(incomingDamage) || incomingDamage < 0.0D) {
                throw new IllegalArgumentException("invalid damage values");
            }
        }
    }

    public record Resolution(double finalDamage, boolean activated, boolean fatalSaveConsumed, boolean duplicate) {}

    private static final class Window {
        private final long untilTick;
        private boolean fatalSaveAvailable;

        private Window(long untilTick, boolean fatalSaveAvailable) {
            this.untilTick = untilTick;
            this.fatalSaveAvailable = fatalSaveAvailable;
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
