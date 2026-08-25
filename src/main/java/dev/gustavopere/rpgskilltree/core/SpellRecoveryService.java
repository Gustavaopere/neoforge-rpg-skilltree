package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A0149 two-distinct-spell sequence with transient window and persistent internal cooldown. */
public final class SpellRecoveryService {
    private static final long WINDOW_TICKS = 100L;
    private static final long COOLDOWN_TICKS = 160L;
    private final CanonicalEventLedger claims;
    private final Map<String, Window> windows = new HashMap<>();
    private final Map<String, Long> cooldownUntil = new HashMap<>();

    public SpellRecoveryService(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        claims = new CanonicalEventLedger(maxPlayers * 8);
    }

    public synchronized Resolution onCast(Cast cast, int rank, long nowTick) {
        Objects.requireNonNull(cast);
        if (rank <= 0 || !cast.realPlayerOwner()
            || !cast.action().actorId().equals(cast.playerId())
            || !ProcGuard.mayTriggerSecondaryEffect(cast.action().origin())) return Resolution.none();
        if (!claims.claimPrimaryOnce(cast.action(), "spell_recovery:" + cast.playerId(), nowTick, 1_200L)) {
            return new Resolution(1.0D, false, false, true);
        }
        if (cooldownUntil.getOrDefault(cast.playerId(), 0L) > nowTick) {
            return new Resolution(1.0D, false, true, false);
        }
        Window window = windows.get(cast.playerId());
        if (window != null && nowTick > window.expiresAt) {
            windows.remove(cast.playerId());
            window = null;
        }
        if (window == null) {
            if (cast.nonInstant()) windows.put(cast.playerId(), new Window(cast.spellId(), nowTick + WINDOW_TICKS));
            return Resolution.none();
        }
        if (window.firstSpellId.equals(cast.spellId()) || !cast.cooldownReducible()) return Resolution.none();
        windows.remove(cast.playerId());
        cooldownUntil.put(cast.playerId(), nowTick + COOLDOWN_TICKS);
        return new Resolution(0.85D, true, false, false);
    }

    public synchronized void clearTransient(String playerId) {
        requireId(playerId, "playerId");
        windows.remove(playerId);
    }

    public record Cast(
        String playerId,
        CanonicalActionIdentity action,
        String spellId,
        boolean nonInstant,
        boolean cooldownReducible,
        boolean realPlayerOwner
    ) {
        public Cast {
            requireId(playerId, "playerId");
            Objects.requireNonNull(action);
            requireId(spellId, "spellId");
        }
    }

    public record Resolution(
        double cooldownMultiplier,
        boolean consumedWindow,
        boolean internalCooldown,
        boolean duplicate
    ) {
        private static Resolution none() { return new Resolution(1.0D, false, false, false); }
    }

    private record Window(String firstSpellId, long expiresAt) {}

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }
}
