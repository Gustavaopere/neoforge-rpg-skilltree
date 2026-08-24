package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Ephemeral server-authoritative combat resources for Notion combat perks.
 *
 * <p>This state is intentionally not part of ProgressionState and must not be serialized as player progression.
 */
public final class NotionCombatPerkState {
    private static final int MAX_MOMENTUM = 5;
    private static final double MAX_FURY = 100.0D;
    private static final int MAX_DISTANCE_CONTROL = 3;
    private static final int MAX_FLOW = 4;
    private static final double MAX_FOCUS = 100.0D;

    private final Map<String, ActorState> actors = new HashMap<>();

    public enum TargetCounter {
        SHOCK,
        TRAUMA
    }

    public enum TargetFlag {
        REAPING_MARK,
        REAPING_MATURE,
        ARMOR_CRACKED,
        DEMOLISH_WINDOW,
        INTERCEPTION_WINDOW
    }

    public enum ActorFlag {
        PERFECT_RIPOSTE,
        SHADOW_DANCE,
        BATTLE_HARVEST,
        PREPARED_SHOT
    }

    public synchronized void addMomentum(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        state.momentum = Math.min(MAX_MOMENTUM, state.momentum + amount);
        state.lastMomentumChange = nowMillis;
    }

    public synchronized int momentum(String actorId) {
        return actorOrEmpty(actorId).momentum;
    }

    public synchronized void consumeMomentum(String actorId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        if (amount > state.momentum) throw new IllegalArgumentException("insufficient momentum");
        state.momentum -= amount;
    }

    public synchronized void addFury(String actorId, double amount, long nowMillis) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        state.fury = Math.min(MAX_FURY, state.fury + amount);
        state.lastFuryChange = nowMillis;
    }

    public synchronized double fury(String actorId) {
        return actorOrEmpty(actorId).fury;
    }

    public synchronized void consumeFury(String actorId, double amount) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        if (amount > state.fury) throw new IllegalArgumentException("insufficient fury");
        state.fury -= amount;
    }

    public synchronized void addDistanceControl(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        state.distanceControl = Math.min(MAX_DISTANCE_CONTROL, state.distanceControl + amount);
        state.lastDistanceControlChange = nowMillis;
    }

    public synchronized int distanceControl(String actorId) {
        return actorOrEmpty(actorId).distanceControl;
    }

    public synchronized void consumeDistanceControl(String actorId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        if (amount > state.distanceControl) throw new IllegalArgumentException("insufficient distance control");
        state.distanceControl -= amount;
    }

    public synchronized void addFlow(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        state.flow = Math.min(MAX_FLOW, state.flow + amount);
        state.lastFlowChange = nowMillis;
    }

    public synchronized int flow(String actorId) {
        return actorOrEmpty(actorId).flow;
    }

    public synchronized void consumeFlow(String actorId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        if (amount > state.flow) throw new IllegalArgumentException("insufficient flow");
        state.flow -= amount;
    }

    public synchronized void addFocus(String actorId, double amount, long nowMillis) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        state.focus = Math.min(MAX_FOCUS, state.focus + amount);
        state.lastFocusChange = nowMillis;
    }

    public synchronized double focus(String actorId) {
        return actorOrEmpty(actorId).focus;
    }

    public synchronized void consumeFocus(String actorId, double amount) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        if (amount > state.focus) throw new IllegalArgumentException("insufficient focus");
        state.focus -= amount;
    }

    public synchronized void addTargetCounter(
        String actorId,
        String targetId,
        TargetCounter counter,
        int amount,
        int cap,
        long nowMillis,
        long durationMillis
    ) {
        Objects.requireNonNull(counter);
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        if (cap <= 0) throw new IllegalArgumentException("cap must be positive");
        if (durationMillis <= 0) throw new IllegalArgumentException("duration must be positive");
        TargetState target = target(actorId, targetId);
        TimedCounter current = target.counters.get(counter);
        int currentValue = current != null && current.expiresAtMillis > nowMillis ? current.value : 0;
        target.counters.put(counter, new TimedCounter(Math.min(cap, currentValue + amount), safeAdd(nowMillis, durationMillis)));
    }

    public synchronized int targetCounter(String actorId, String targetId, TargetCounter counter, long nowMillis) {
        Objects.requireNonNull(counter);
        TargetState target = targetOrNull(actorId, targetId);
        if (target == null) return 0;
        TimedCounter current = target.counters.get(counter);
        return current != null && current.expiresAtMillis > nowMillis ? current.value : 0;
    }

    public synchronized void consumeTargetCounter(
        String actorId,
        String targetId,
        TargetCounter counter,
        int amount,
        long nowMillis
    ) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        int current = targetCounter(actorId, targetId, counter, nowMillis);
        if (amount > current) throw new IllegalArgumentException("insufficient target counter");
        TargetState target = target(actorId, targetId);
        TimedCounter previous = target.counters.get(counter);
        if (current == amount) target.counters.remove(counter);
        else target.counters.put(counter, new TimedCounter(current - amount, previous.expiresAtMillis));
    }

    public synchronized void setTargetFlag(String actorId, String targetId, TargetFlag flag, long expiresAtMillis) {
        Objects.requireNonNull(flag);
        TargetState target = target(actorId, targetId);
        target.flags.put(flag, expiresAtMillis);
    }

    public synchronized boolean hasTargetFlag(String actorId, String targetId, TargetFlag flag, long nowMillis) {
        Objects.requireNonNull(flag);
        TargetState target = targetOrNull(actorId, targetId);
        if (target == null) return false;
        Long expiresAt = target.flags.get(flag);
        return expiresAt != null && expiresAt > nowMillis;
    }

    public synchronized boolean consumeTargetFlag(String actorId, String targetId, TargetFlag flag, long nowMillis) {
        if (!hasTargetFlag(actorId, targetId, flag, nowMillis)) return false;
        target(actorId, targetId).flags.remove(flag);
        return true;
    }

    public synchronized void clearTargetFlag(String actorId, String targetId, TargetFlag flag) {
        Objects.requireNonNull(flag);
        TargetState target = targetOrNull(actorId, targetId);
        if (target != null) target.flags.remove(flag);
    }

    public synchronized void setActorFlag(String actorId, ActorFlag flag, long expiresAtMillis) {
        Objects.requireNonNull(flag);
        actor(actorId).flags.put(flag, expiresAtMillis);
    }

    public synchronized boolean hasActorFlag(String actorId, ActorFlag flag, long nowMillis) {
        Objects.requireNonNull(flag);
        Long expiresAt = actorOrEmpty(actorId).flags.get(flag);
        return expiresAt != null && expiresAt > nowMillis;
    }

    public synchronized boolean consumeActorFlag(String actorId, ActorFlag flag, long nowMillis) {
        if (!hasActorFlag(actorId, flag, nowMillis)) return false;
        actor(actorId).flags.remove(flag);
        return true;
    }

    public synchronized boolean cooldownReady(String actorId, String targetId, String key, long nowMillis) {
        String cooldownKey = cooldownKey(targetId, key);
        Long until = actorOrEmpty(actorId).cooldowns.get(cooldownKey);
        return until == null || until <= nowMillis;
    }

    public synchronized void startCooldown(
        String actorId,
        String targetId,
        String key,
        long nowMillis,
        long durationMillis
    ) {
        if (durationMillis <= 0) throw new IllegalArgumentException("duration must be positive");
        actor(actorId).cooldowns.put(cooldownKey(targetId, key), safeAdd(nowMillis, durationMillis));
    }

    public synchronized boolean recordTargetAndWasDifferent(String actorId, String targetId) {
        ActorState state = actor(actorId);
        String target = requireTargetId(targetId);
        boolean different = state.lastTargetId != null && !state.lastTargetId.equals(target);
        state.lastTargetId = target;
        return different;
    }

    public synchronized void clear(String actorId) {
        actors.remove(requireActorId(actorId));
    }

    private ActorState actor(String actorId) {
        return actors.computeIfAbsent(requireActorId(actorId), ignored -> new ActorState());
    }

    private ActorState actorOrEmpty(String actorId) {
        ActorState state = actors.get(requireActorId(actorId));
        return state == null ? ActorState.EMPTY : state;
    }

    private TargetState target(String actorId, String targetId) {
        return actor(actorId).targets.computeIfAbsent(requireTargetId(targetId), ignored -> new TargetState());
    }

    private TargetState targetOrNull(String actorId, String targetId) {
        ActorState state = actors.get(requireActorId(actorId));
        return state == null ? null : state.targets.get(requireTargetId(targetId));
    }

    private static String cooldownKey(String targetId, String key) {
        Objects.requireNonNull(key);
        if (key.isBlank()) throw new IllegalArgumentException("cooldown key must not be blank");
        return requireTargetId(targetId) + '\u0000' + key;
    }

    private static String requireActorId(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        return actorId;
    }

    private static String requireTargetId(String targetId) {
        Objects.requireNonNull(targetId);
        if (targetId.isBlank()) throw new IllegalArgumentException("targetId must not be blank");
        return targetId;
    }

    private static void requireFiniteNonNegative(double amount) {
        if (!Double.isFinite(amount) || amount < 0.0D) {
            throw new IllegalArgumentException("amount must be finite and non-negative");
        }
    }

    private static long safeAdd(long left, long right) {
        return Math.addExact(left, right);
    }

    private record TimedCounter(int value, long expiresAtMillis) {}

    private static final class TargetState {
        final EnumMap<TargetCounter, TimedCounter> counters = new EnumMap<>(TargetCounter.class);
        final EnumMap<TargetFlag, Long> flags = new EnumMap<>(TargetFlag.class);
    }

    private static final class ActorState {
        private static final ActorState EMPTY = new ActorState();

        int momentum;
        double fury;
        int distanceControl;
        int flow;
        double focus;
        long lastMomentumChange;
        long lastFuryChange;
        long lastDistanceControlChange;
        long lastFlowChange;
        long lastFocusChange;
        String lastTargetId;
        final EnumMap<ActorFlag, Long> flags = new EnumMap<>(ActorFlag.class);
        final Map<String, Long> cooldowns = new HashMap<>();
        final Map<String, TargetState> targets = new HashMap<>();
    }
}
