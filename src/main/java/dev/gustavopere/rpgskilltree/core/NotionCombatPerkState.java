package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient state used only by A0001-A0020. No field is persisted. */
public final class NotionCombatPerkState {
    private final Map<String, ActorState> actors = new HashMap<>();
    private final Map<String, Long> claims = new HashMap<>();

    public synchronized int momentum(String actorId) { return actor(actorId).momentum; }
    public synchronized int momentum(String actorId, long nowMillis) { decayMomentum(actorId, nowMillis); return actor(actorId).momentum; }

    public synchronized int addMomentum(String actorId, int amount, long nowMillis) {
        if (amount <= 0) return momentum(actorId, nowMillis);
        ActorState state = actor(actorId);
        state.momentum = Math.min(NotionCombatPerkRules.MOMENTUM_CAP, state.momentum + amount);
        state.momentumExpiresAt = Math.addExact(nowMillis, NotionCombatPerkRules.MOMENTUM_WINDOW_MILLIS);
        return state.momentum;
    }

    public synchronized int consumeMomentum(String actorId, int amount) {
        if (amount <= 0) return 0;
        ActorState state = actor(actorId);
        int consumed = Math.min(amount, state.momentum);
        state.momentum -= consumed;
        if (state.momentum == 0) state.momentumExpiresAt = 0L;
        return consumed;
    }

    public synchronized int loseMomentum(String actorId, int amount) { return consumeMomentum(actorId, amount); }

    public synchronized void decayMomentum(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        if (state.momentum > 0 && state.momentumExpiresAt <= nowMillis) {
            state.momentum = 0;
            state.momentumExpiresAt = 0L;
        }
    }

    public synchronized double fury(String actorId) { return actor(actorId).fury; }
    public synchronized double addFury(String actorId, double amount) {
        if (!Double.isFinite(amount) || amount < 0.0D) throw new IllegalArgumentException("Fury gain must be finite and non-negative");
        ActorState state = actor(actorId);
        state.fury = Math.min(NotionCombatPerkRules.FURY_CAP, state.fury + amount);
        return state.fury;
    }
    public synchronized boolean consumeFury(String actorId, double amount, double minimumBeforeSpend) {
        if (amount < 0.0D || minimumBeforeSpend < amount) throw new IllegalArgumentException("invalid Fury spend");
        ActorState state = actor(actorId);
        if (state.fury + 1.0E-9D < minimumBeforeSpend) return false;
        state.fury = Math.max(0.0D, state.fury - amount);
        return true;
    }
    public synchronized boolean switchedAxeTarget(String actorId, String targetId) {
        ActorState state = actor(actorId);
        boolean switched = state.lastAxeTarget != null && !state.lastAxeTarget.equals(targetId);
        state.lastAxeTarget = require(targetId, "targetId");
        return switched;
    }

    public synchronized int distanceControl(String actorId) { return actor(actorId).distanceControl; }
    public synchronized int distanceControl(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        if (state.distanceControl > 0 && state.distanceControlExpiresAt <= nowMillis) {
            state.distanceControl = 0;
            state.distanceControlExpiresAt = 0L;
        }
        return state.distanceControl;
    }
    public synchronized int addDistanceControl(String actorId, int amount, long nowMillis, long windowMillis) {
        if (windowMillis <= 0L) throw new IllegalArgumentException("windowMillis must be positive");
        ActorState state = actor(actorId);
        distanceControl(actorId, nowMillis);
        if (amount > 0) {
            state.distanceControl = Math.min(NotionCombatPerkRules.DISTANCE_CONTROL_CAP, state.distanceControl + amount);
            state.distanceControlExpiresAt = Math.addExact(nowMillis, windowMillis);
        }
        return state.distanceControl;
    }
    public synchronized int consumeDistanceControl(String actorId, int amount, long nowMillis) {
        ActorState state = actor(actorId);
        distanceControl(actorId, nowMillis);
        int consumed = Math.min(Math.max(0, amount), state.distanceControl);
        state.distanceControl -= consumed;
        if (state.distanceControl == 0) state.distanceControlExpiresAt = 0L;
        return consumed;
    }
    public synchronized int loseDistanceControl(String actorId, int amount, long nowMillis) { return consumeDistanceControl(actorId, amount, nowMillis); }

    public synchronized boolean claimOnce(String actorId, String rootActionId, String consumerId, long nowMillis) {
        require(actorId, "actorId"); require(rootActionId, "rootActionId"); require(consumerId, "consumerId");
        claims.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
        String key = actorId + '\u0000' + rootActionId + '\u0000' + consumerId;
        if (claims.containsKey(key)) return false;
        claims.put(key, Math.addExact(nowMillis, 30_000L));
        return true;
    }

    public synchronized void armRiposte(String actorId, long nowMillis, long durationMillis, long cooldownMillis) {
        ActorState state = actor(actorId);
        if (state.riposteCooldownUntil > nowMillis) return;
        state.riposteUntil = Math.addExact(nowMillis, durationMillis);
        state.riposteCooldownUntil = Math.addExact(nowMillis, cooldownMillis);
    }
    public synchronized boolean consumeRiposte(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        boolean active = state.riposteUntil > nowMillis;
        state.riposteUntil = 0L;
        return active;
    }
    public synchronized boolean riposteCooldownReady(String actorId, long nowMillis) { return actor(actorId).riposteCooldownUntil <= nowMillis; }

    public synchronized void recordSpearRange(String actorId, String targetId, boolean insideIdealRange, boolean targetAdvancing,
                                              int mastery, long nowMillis) {
        ActorState state = actor(actorId);
        String target = require(targetId, "targetId");
        Boolean previous = state.spearInsideByTarget.put(target, insideIdealRange);
        if (Boolean.FALSE.equals(previous) && insideIdealRange && targetAdvancing) {
            state.interceptUntilByTarget.put(target, Math.addExact(nowMillis, NotionCombatPerkRules.A0017_WINDOW_MILLIS));
            if (distanceControl(actorId, nowMillis) >= 3 && state.lineLockoutUntilByTarget.getOrDefault(target, 0L) <= nowMillis) {
                state.lineUntilByTarget.put(target, Math.addExact(nowMillis, NotionCombatPerkRules.interceptionMasteryWindowMillis(mastery)));
                state.lineLockoutUntilByTarget.put(target, Math.addExact(nowMillis, NotionCombatPerkRules.A0018_TARGET_LOCKOUT_MILLIS));
            }
        }
    }
    public synchronized boolean consumeInterceptWindow(String actorId, String targetId, long nowMillis) {
        Long until = actor(actorId).interceptUntilByTarget.remove(require(targetId, "targetId"));
        return until != null && until > nowMillis;
    }
    public synchronized boolean consumeLineWindow(String actorId, String targetId, long nowMillis) {
        Long until = actor(actorId).lineUntilByTarget.remove(require(targetId, "targetId"));
        return until != null && until > nowMillis;
    }

    public synchronized void clearTransient(String actorId) {
        actors.remove(require(actorId, "actorId"));
        String prefix = actorId + '\u0000';
        claims.keySet().removeIf(key -> key.startsWith(prefix));
    }
    public synchronized void clearAll() { actors.clear(); claims.clear(); }

    private ActorState actor(String actorId) { return actors.computeIfAbsent(require(actorId, "actorId"), ignored -> new ActorState()); }
    private static String require(String value, String name) { Objects.requireNonNull(value); if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank"); return value; }

    private static final class ActorState {
        int momentum;
        long momentumExpiresAt;
        double fury;
        String lastAxeTarget;
        int distanceControl;
        long distanceControlExpiresAt;
        long riposteUntil;
        long riposteCooldownUntil;
        final Map<String, Boolean> spearInsideByTarget = new HashMap<>();
        final Map<String, Long> interceptUntilByTarget = new HashMap<>();
        final Map<String, Long> lineUntilByTarget = new HashMap<>();
        final Map<String, Long> lineLockoutUntilByTarget = new HashMap<>();
    }
}
