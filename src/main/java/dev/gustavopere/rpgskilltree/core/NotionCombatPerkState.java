package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient state used only by A0001-A0020. No field is persisted. */
public final class NotionCombatPerkState {
    private static final long PREPARED_SWORD_COMMIT_TTL_MILLIS = 30_000L;
    private static final long PREPARED_COMBAT_COMMIT_TTL_MILLIS = 30_000L;

    public enum PreparedSwordCommit {
        NONE,
        OPENING,
        RIPOSTE
    }

    public enum PreparedAxeCommit {
        NONE,
        RUPTURE
    }

    public enum PreparedSpearCommit {
        NONE,
        INTERCEPT,
        LINE
    }

    private final Map<String, ActorState> actors = new HashMap<>();
    private final Map<String, Long> claims = new HashMap<>();

    public synchronized int momentum(String actorId) { return actor(actorId).momentum; }
    public synchronized int momentum(String actorId, long nowMillis) { decayMomentum(actorId, nowMillis); return actor(actorId).momentum; }

    /** Every eligible gain, including one at cap, restarts A0004's five-second inactivity grace. */
    public synchronized int addMomentum(String actorId, int amount, long nowMillis) {
        if (amount <= 0) return momentum(actorId, nowMillis);
        ActorState state = actor(actorId);
        decayMomentum(actorId, nowMillis);
        state.momentum = Math.min(NotionCombatPerkRules.MOMENTUM_CAP, state.momentum + amount);
        state.nextMomentumDecayAt = Math.addExact(nowMillis, NotionCombatPerkRules.MOMENTUM_INACTIVITY_MILLIS);
        return state.momentum;
    }

    public synchronized int consumeMomentum(String actorId, int amount) {
        if (amount <= 0) return 0;
        ActorState state = actor(actorId);
        int consumed = Math.min(amount, state.momentum);
        state.momentum -= consumed;
        if (state.momentum == 0) state.nextMomentumDecayAt = 0L;
        return consumed;
    }

    /** Losses intentionally do not restart the inactivity timer. */
    public synchronized int loseMomentum(String actorId, int amount) { return consumeMomentum(actorId, amount); }

    /** After five seconds without an eligible gain, A0004 loses one charge per elapsed second. */
    public synchronized void decayMomentum(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        if (state.momentum <= 0 || state.nextMomentumDecayAt <= 0L || nowMillis < state.nextMomentumDecayAt) return;
        long elapsed = nowMillis - state.nextMomentumDecayAt;
        long steps = 1L + elapsed / NotionCombatPerkRules.MOMENTUM_DECAY_INTERVAL_MILLIS;
        int lost = (int)Math.min((long)state.momentum, steps);
        state.momentum -= lost;
        if (state.momentum <= 0) {
            state.momentum = 0;
            state.nextMomentumDecayAt = 0L;
        } else {
            state.nextMomentumDecayAt = Math.addExact(
                state.nextMomentumDecayAt,
                Math.multiplyExact(steps, NotionCombatPerkRules.MOMENTUM_DECAY_INTERVAL_MILLIS)
            );
        }
    }

    public synchronized boolean sameSwordSequenceTarget(String actorId, String targetId) {
        String target = require(targetId, "targetId");
        return target.equals(actor(actorId).lastSwordMomentumTarget);
    }

    public synchronized void recordSwordSequenceTarget(String actorId, String targetId) {
        actor(actorId).lastSwordMomentumTarget = require(targetId, "targetId");
    }

    public synchronized boolean openingCooldownReady(String actorId, String targetId, long nowMillis) {
        return actor(actorId).openingCooldownUntilByTarget.getOrDefault(require(targetId, "targetId"), 0L) <= nowMillis;
    }

    public synchronized void startOpeningCooldown(String actorId, String targetId, long nowMillis) {
        actor(actorId).openingCooldownUntilByTarget.put(
            require(targetId, "targetId"),
            Math.addExact(nowMillis, NotionCombatPerkRules.A0005_TARGET_COOLDOWN_MILLIS)
        );
    }

    /**
     * A0005/A0006 prepare their irreversible state changes in PRE, but commit them only after
     * provider POST confirms effective damage. A bounded preparation is safe to discard.
     */
    public synchronized void prepareOpeningCommit(String actorId, String targetId, String rootActionId, long nowMillis) {
        ActorState state = actor(actorId);
        prunePreparedSwordCommits(state, nowMillis);
        String root = require(rootActionId, "rootActionId");
        state.preparedSwordCommits.putIfAbsent(
            root,
            new PreparedSwordAction(
                PreparedSwordCommit.OPENING,
                require(targetId, "targetId"),
                Math.addExact(nowMillis, PREPARED_SWORD_COMMIT_TTL_MILLIS)
            )
        );
    }

    public synchronized void prepareRiposteCommit(String actorId, String targetId, String rootActionId, long nowMillis) {
        ActorState state = actor(actorId);
        prunePreparedSwordCommits(state, nowMillis);
        String root = require(rootActionId, "rootActionId");
        state.preparedSwordCommits.putIfAbsent(
            root,
            new PreparedSwordAction(
                PreparedSwordCommit.RIPOSTE,
                require(targetId, "targetId"),
                Math.addExact(nowMillis, PREPARED_SWORD_COMMIT_TTL_MILLIS)
            )
        );
    }

    /** Atomically commits the prepared A0005/A0006 resource mutation for one confirmed root action. */
    public synchronized PreparedSwordCommit commitPreparedSwordAction(
        String actorId,
        String targetId,
        String rootActionId,
        long nowMillis
    ) {
        ActorState state = actor(actorId);
        prunePreparedSwordCommits(state, nowMillis);
        PreparedSwordAction prepared = state.preparedSwordCommits.remove(require(rootActionId, "rootActionId"));
        if (prepared == null || !prepared.targetId.equals(require(targetId, "targetId"))) {
            return PreparedSwordCommit.NONE;
        }

        if (prepared.kind == PreparedSwordCommit.OPENING) {
            if (momentum(actorId, nowMillis) < NotionCombatPerkRules.A0005_MIN_MOMENTUM
                || !openingCooldownReady(actorId, targetId, nowMillis)) {
                return PreparedSwordCommit.NONE;
            }
            if (consumeMomentum(actorId, NotionCombatPerkRules.A0005_MOMENTUM_COST)
                != NotionCombatPerkRules.A0005_MOMENTUM_COST) {
                return PreparedSwordCommit.NONE;
            }
            startOpeningCooldown(actorId, targetId, nowMillis);
            return PreparedSwordCommit.OPENING;
        }

        if (prepared.kind == PreparedSwordCommit.RIPOSTE) {
            if (!riposteActive(actorId, nowMillis) || momentum(actorId, nowMillis) < 5) {
                return PreparedSwordCommit.NONE;
            }
            state.riposteUntil = 0L;
            if (consumeMomentum(actorId, 5) != 5) {
                return PreparedSwordCommit.NONE;
            }
            return PreparedSwordCommit.RIPOSTE;
        }
        return PreparedSwordCommit.NONE;
    }

    public synchronized void discardPreparedSwordAction(String actorId, String rootActionId) {
        actor(actorId).preparedSwordCommits.remove(require(rootActionId, "rootActionId"));
    }

    public synchronized double fury(String actorId) { return actor(actorId).fury; }
    public synchronized double addFury(String actorId, double amount) {
        if (!Double.isFinite(amount) || amount < 0.0D) throw new IllegalArgumentException("Fury gain must be finite and non-negative");
        ActorState state = actor(actorId);
        state.fury = Math.min(NotionCombatPerkRules.FURY_CAP, state.fury + amount);
        return state.fury;
    }
    public synchronized boolean consumeFury(String actorId, double amount, double minimumBeforeSpend) {
        if (!Double.isFinite(amount) || !Double.isFinite(minimumBeforeSpend)
            || amount < 0.0D || minimumBeforeSpend < amount) {
            throw new IllegalArgumentException("invalid Fury spend");
        }
        ActorState state = actor(actorId);
        if (state.fury + 1.0E-9D < minimumBeforeSpend) return false;
        state.fury = Math.max(0.0D, state.fury - amount);
        return true;
    }

    /** Fury visible to PRE after bounded A0011 reservations from still-uncommitted root actions. */
    public synchronized double availableFuryForAxe(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        prunePreparedAxeCommits(state, nowMillis);
        double reserved = state.preparedAxeCommits.size() * NotionCombatPerkRules.A0011_FURY_COST;
        return Math.max(0.0D, state.fury - reserved);
    }

    public synchronized boolean prepareRuptureCommit(String actorId, String targetId, String rootActionId, long nowMillis) {
        ActorState state = actor(actorId);
        prunePreparedAxeCommits(state, nowMillis);
        String root = require(rootActionId, "rootActionId");
        String target = require(targetId, "targetId");
        PreparedAxeAction existing = state.preparedAxeCommits.get(root);
        if (existing != null) return existing.kind == PreparedAxeCommit.RUPTURE && existing.targetId.equals(target);
        if (availableFuryForAxe(actorId, nowMillis) + 1.0E-9D < NotionCombatPerkRules.A0011_MIN_FURY) return false;
        state.preparedAxeCommits.put(
            root,
            new PreparedAxeAction(
                PreparedAxeCommit.RUPTURE,
                target,
                Math.addExact(nowMillis, PREPARED_COMBAT_COMMIT_TTL_MILLIS)
            )
        );
        return true;
    }

    public synchronized PreparedAxeCommit commitPreparedAxeAction(
        String actorId,
        String targetId,
        String rootActionId,
        long nowMillis
    ) {
        ActorState state = actor(actorId);
        prunePreparedAxeCommits(state, nowMillis);
        PreparedAxeAction prepared = state.preparedAxeCommits.remove(require(rootActionId, "rootActionId"));
        if (prepared == null || !prepared.targetId.equals(require(targetId, "targetId"))) return PreparedAxeCommit.NONE;
        if (prepared.kind == PreparedAxeCommit.RUPTURE
            && consumeFury(actorId, NotionCombatPerkRules.A0011_FURY_COST, NotionCombatPerkRules.A0011_MIN_FURY)) {
            return PreparedAxeCommit.RUPTURE;
        }
        return PreparedAxeCommit.NONE;
    }

    public synchronized void discardPreparedAxeAction(String actorId, String rootActionId) {
        actor(actorId).preparedAxeCommits.remove(require(rootActionId, "rootActionId"));
    }

    public synchronized boolean switchedAxeTarget(String actorId, String targetId) {
        ActorState state = actor(actorId);
        String target = require(targetId, "targetId");
        boolean switched = state.lastAxeTarget != null && !state.lastAxeTarget.equals(target);
        state.lastAxeTarget = target;
        return switched;
    }

    /** Tracks the threshold transition that owns A0012's Queda de Ritmo penalty. */
    public synchronized boolean updateFrenzyState(String actorId, boolean learned, int mastery, long nowMillis) {
        ActorState state = actor(actorId);
        if (!learned) {
            state.frenzyActive = false;
            state.rhythmDropUntil = 0L;
            return false;
        }
        boolean active = state.fury + 1.0E-9D >= NotionCombatPerkRules.A0012_FRENZY_THRESHOLD;
        if (state.frenzyActive && !active) {
            state.rhythmDropUntil = Math.max(
                state.rhythmDropUntil,
                Math.addExact(nowMillis, NotionCombatPerkRules.frenzyDropDurationMillis(mastery))
            );
        }
        state.frenzyActive = active;
        return active;
    }

    public synchronized boolean frenzyActive(String actorId) {
        return actor(actorId).frenzyActive;
    }

    public synchronized boolean rhythmDropActive(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        if (state.rhythmDropUntil <= nowMillis) {
            state.rhythmDropUntil = 0L;
            return false;
        }
        return true;
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

    /** Distance Control visible to PRE after bounded A0017/A0018 reservations. */
    public synchronized int availableDistanceControl(String actorId, long nowMillis) {
        ActorState state = actor(actorId);
        int current = distanceControl(actorId, nowMillis);
        prunePreparedSpearCommits(state, nowMillis);
        int reserved = 0;
        for (PreparedSpearAction prepared : state.preparedSpearCommits.values()) {
            reserved += prepared.kind == PreparedSpearCommit.LINE ? 3 : 1;
        }
        return Math.max(0, current - reserved);
    }

    public synchronized boolean interceptWindowActive(String actorId, String targetId, long nowMillis) {
        ActorState state = actor(actorId);
        pruneSpearWindows(state, nowMillis);
        return state.interceptUntilByTarget.getOrDefault(require(targetId, "targetId"), 0L) > nowMillis;
    }

    public synchronized boolean lineWindowActive(String actorId, String targetId, long nowMillis) {
        ActorState state = actor(actorId);
        pruneSpearWindows(state, nowMillis);
        return state.lineUntilByTarget.getOrDefault(require(targetId, "targetId"), 0L) > nowMillis;
    }

    public synchronized boolean prepareInterceptCommit(String actorId, String targetId, String rootActionId, long nowMillis) {
        return prepareSpearCommit(actorId, targetId, rootActionId, PreparedSpearCommit.INTERCEPT, 1, nowMillis);
    }

    public synchronized boolean prepareLineCommit(String actorId, String targetId, String rootActionId, long nowMillis) {
        return prepareSpearCommit(actorId, targetId, rootActionId, PreparedSpearCommit.LINE, 3, nowMillis);
    }

    private boolean prepareSpearCommit(
        String actorId,
        String targetId,
        String rootActionId,
        PreparedSpearCommit kind,
        int cost,
        long nowMillis
    ) {
        ActorState state = actor(actorId);
        pruneSpearWindows(state, nowMillis);
        prunePreparedSpearCommits(state, nowMillis);
        String root = require(rootActionId, "rootActionId");
        String target = require(targetId, "targetId");
        PreparedSpearAction existing = state.preparedSpearCommits.get(root);
        if (existing != null) return existing.kind == kind && existing.targetId.equals(target);
        boolean windowActive = kind == PreparedSpearCommit.LINE
            ? state.lineUntilByTarget.getOrDefault(target, 0L) > nowMillis
            : state.interceptUntilByTarget.getOrDefault(target, 0L) > nowMillis;
        if (!windowActive || availableDistanceControl(actorId, nowMillis) < cost) return false;
        for (PreparedSpearAction pending : state.preparedSpearCommits.values()) {
            if (pending.targetId.equals(target)) return false;
        }
        state.preparedSpearCommits.put(
            root,
            new PreparedSpearAction(kind, target, Math.addExact(nowMillis, PREPARED_COMBAT_COMMIT_TTL_MILLIS))
        );
        return true;
    }

    public synchronized PreparedSpearCommit commitPreparedSpearAction(
        String actorId,
        String targetId,
        String rootActionId,
        long nowMillis
    ) {
        ActorState state = actor(actorId);
        pruneSpearWindows(state, nowMillis);
        prunePreparedSpearCommits(state, nowMillis);
        PreparedSpearAction prepared = state.preparedSpearCommits.remove(require(rootActionId, "rootActionId"));
        if (prepared == null || !prepared.targetId.equals(require(targetId, "targetId"))) return PreparedSpearCommit.NONE;

        if (prepared.kind == PreparedSpearCommit.LINE) {
            if (distanceControl(actorId, nowMillis) < 3 || !lineWindowActive(actorId, targetId, nowMillis)) {
                return PreparedSpearCommit.NONE;
            }
            if (!consumeLineWindow(actorId, targetId, nowMillis)) return PreparedSpearCommit.NONE;
            if (consumeDistanceControl(actorId, 3, nowMillis) != 3) return PreparedSpearCommit.NONE;
            return PreparedSpearCommit.LINE;
        }

        if (prepared.kind == PreparedSpearCommit.INTERCEPT) {
            if (distanceControl(actorId, nowMillis) < 1 || !interceptWindowActive(actorId, targetId, nowMillis)) {
                return PreparedSpearCommit.NONE;
            }
            if (!consumeInterceptWindow(actorId, targetId, nowMillis)) return PreparedSpearCommit.NONE;
            if (consumeDistanceControl(actorId, 1, nowMillis) != 1) return PreparedSpearCommit.NONE;
            return PreparedSpearCommit.INTERCEPT;
        }
        return PreparedSpearCommit.NONE;
    }

    public synchronized void discardPreparedSpearAction(String actorId, String rootActionId) {
        actor(actorId).preparedSpearCommits.remove(require(rootActionId, "rootActionId"));
    }

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
    public synchronized boolean riposteActive(String actorId, long nowMillis) {
        return actor(actorId).riposteUntil > nowMillis;
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
        pruneSpearWindows(state, nowMillis);
        prunePreparedSpearCommits(state, nowMillis);
        Boolean previous = state.spearInsideByTarget.put(target, insideIdealRange);
        if (!Boolean.FALSE.equals(previous) || !insideIdealRange) return;

        // A0017 additionally requires an advancing hostile target.
        if (targetAdvancing) {
            state.interceptUntilByTarget.put(target, Math.addExact(nowMillis, NotionCombatPerkRules.A0017_WINDOW_MILLIS));
        }
        // A0018 requires only a reliable outside->inside crossing plus three unreserved charges.
        if (availableDistanceControl(actorId, nowMillis) >= 3
            && state.lineLockoutUntilByTarget.getOrDefault(target, 0L) <= nowMillis) {
            state.lineUntilByTarget.put(
                target,
                Math.addExact(nowMillis, NotionCombatPerkRules.interceptionMasteryWindowMillis(mastery))
            );
        }
    }
    public synchronized boolean consumeInterceptWindow(String actorId, String targetId, long nowMillis) {
        Long until = actor(actorId).interceptUntilByTarget.remove(require(targetId, "targetId"));
        return until != null && until > nowMillis;
    }
    public synchronized boolean consumeLineWindow(String actorId, String targetId, long nowMillis) {
        ActorState state = actor(actorId);
        String target = require(targetId, "targetId");
        Long until = state.lineUntilByTarget.remove(target);
        if (until == null || until <= nowMillis) return false;
        state.lineLockoutUntilByTarget.put(target, Math.addExact(nowMillis, NotionCombatPerkRules.A0018_TARGET_LOCKOUT_MILLIS));
        return true;
    }

    public synchronized void tickTransient(String actorId, long nowMillis) {
        decayMomentum(actorId, nowMillis);
        distanceControl(actorId, nowMillis);
        ActorState state = actor(actorId);
        if (state.rhythmDropUntil <= nowMillis) state.rhythmDropUntil = 0L;
        prunePreparedSwordCommits(state, nowMillis);
        prunePreparedAxeCommits(state, nowMillis);
        pruneSpearWindows(state, nowMillis);
        prunePreparedSpearCommits(state, nowMillis);
    }

    public synchronized void clearTransient(String actorId) {
        actors.remove(require(actorId, "actorId"));
        String prefix = actorId + '\u0000';
        claims.keySet().removeIf(key -> key.startsWith(prefix));
    }
    public synchronized void clearAll() { actors.clear(); claims.clear(); }

    private static void prunePreparedSwordCommits(ActorState state, long nowMillis) {
        state.preparedSwordCommits.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static void prunePreparedAxeCommits(ActorState state, long nowMillis) {
        state.preparedAxeCommits.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static void prunePreparedSpearCommits(ActorState state, long nowMillis) {
        state.preparedSpearCommits.entrySet().removeIf(entry -> {
            PreparedSpearAction prepared = entry.getValue();
            if (prepared.expiresAtMillis <= nowMillis) return true;
            long windowUntil = prepared.kind == PreparedSpearCommit.LINE
                ? state.lineUntilByTarget.getOrDefault(prepared.targetId, 0L)
                : state.interceptUntilByTarget.getOrDefault(prepared.targetId, 0L);
            return windowUntil <= nowMillis;
        });
    }

    private static void pruneSpearWindows(ActorState state, long nowMillis) {
        state.interceptUntilByTarget.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
        state.lineUntilByTarget.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
        state.lineLockoutUntilByTarget.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
    }

    private ActorState actor(String actorId) { return actors.computeIfAbsent(require(actorId, "actorId"), ignored -> new ActorState()); }
    private static String require(String value, String name) { Objects.requireNonNull(value); if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank"); return value; }

    private record PreparedSwordAction(PreparedSwordCommit kind, String targetId, long expiresAtMillis) {}
    private record PreparedAxeAction(PreparedAxeCommit kind, String targetId, long expiresAtMillis) {}
    private record PreparedSpearAction(PreparedSpearCommit kind, String targetId, long expiresAtMillis) {}

    private static final class ActorState {
        int momentum;
        long nextMomentumDecayAt;
        String lastSwordMomentumTarget;
        final Map<String, Long> openingCooldownUntilByTarget = new HashMap<>();
        final Map<String, PreparedSwordAction> preparedSwordCommits = new HashMap<>();
        double fury;
        String lastAxeTarget;
        final Map<String, PreparedAxeAction> preparedAxeCommits = new HashMap<>();
        boolean frenzyActive;
        long rhythmDropUntil;
        int distanceControl;
        long distanceControlExpiresAt;
        long riposteUntil;
        long riposteCooldownUntil;
        final Map<String, Boolean> spearInsideByTarget = new HashMap<>();
        final Map<String, Long> interceptUntilByTarget = new HashMap<>();
        final Map<String, Long> lineUntilByTarget = new HashMap<>();
        final Map<String, Long> lineLockoutUntilByTarget = new HashMap<>();
        final Map<String, PreparedSpearAction> preparedSpearCommits = new HashMap<>();
    }
}
