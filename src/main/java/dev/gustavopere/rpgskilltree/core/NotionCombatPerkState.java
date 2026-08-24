package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

/** Ephemeral server-authoritative combat resources for Notion combat perks. */
public final class NotionCombatPerkState {
    private static final int MAX_MOMENTUM = 5;
    private static final double MAX_FURY = 100.0D;
    private static final int MAX_DISTANCE_CONTROL = 3;
    private static final int MAX_FLOW = 4;
    private static final double MAX_FOCUS = 100.0D;
    private static final long ACTION_RETENTION_MILLIS = 30_000L;
    private static final int MAX_ACTION_CLAIMS = 8_192;

    private final Map<String, ActorState> actors = new HashMap<>();
    private final CanonicalEventLedger actionLedger = new CanonicalEventLedger(MAX_ACTION_CLAIMS);
    private final CanonicalFuryService furyService = new CanonicalFuryService(ACTION_RETENTION_MILLIS, MAX_ACTION_CLAIMS);
    private final CanonicalFocusService focusService = new CanonicalFocusService(ACTION_RETENTION_MILLIS, 2_048);
    private final CanonicalStaminaService staminaService = new CanonicalStaminaService(ACTION_RETENTION_MILLIS, 2_048);

    public enum TargetCounter { SHOCK, TRAUMA }

    public enum TargetFlag {
        REAPING_MARK,
        REAPING_MATURE,
        ARMOR_CRACKED,
        DEMOLISH_WINDOW,
        INTERCEPTION_WINDOW,
        POSTURE_BREAK_PENDING
    }

    public enum ActorFlag {
        PERFECT_RIPOSTE,
        SHADOW_DANCE,
        SHADOW_DANCE_MASTERY_90,
        SHADOW_DANCE_MASTERY_100,
        BATTLE_HARVEST,
        PREPARED_SHOT,
        RECENT_DODGE,
        FLOW_DODGE_WINDOW,
        SUPPRESS_MOMENTUM_ON_RESULT
    }

    public record FlowPositionSample(
        double attackerX,
        double attackerZ,
        double targetX,
        double targetZ,
        long atMillis
    ) {}

    public boolean claimPrimaryOnce(CanonicalActionIdentity action, String consumerId, long nowMillis) {
        return actionLedger.claimPrimaryOnce(action, consumerId, nowMillis, ACTION_RETENTION_MILLIS);
    }

    public CanonicalFuryService furyService() { return furyService; }
    public CanonicalFocusService focusService() { return focusService; }
    public CanonicalStaminaService staminaService() { return staminaService; }

    public synchronized void addMomentum(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        state.momentum = Math.min(MAX_MOMENTUM, state.momentum + amount);
        state.lastMomentumChange = nowMillis;
        state.nextMomentumDecayAt = safeAdd(nowMillis, 5_000L);
    }

    public synchronized int momentum(String actorId) { return actorOrEmpty(actorId).momentum; }

    public synchronized void consumeMomentum(String actorId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        if (amount > state.momentum) throw new IllegalArgumentException("insufficient momentum");
        state.momentum -= amount;
        if (state.momentum == 0) state.nextMomentumDecayAt = 0L;
    }

    public synchronized int loseMomentumClamped(String actorId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        int lost = Math.min(amount, state.momentum);
        state.momentum -= lost;
        if (state.momentum == 0) state.nextMomentumDecayAt = 0L;
        return lost;
    }

    public synchronized int decayMomentum(String actorId, long nowMillis) {
        ActorState state = actorOrEmpty(actorId);
        if (state.momentum <= 0 || state.nextMomentumDecayAt <= 0L || nowMillis < state.nextMomentumDecayAt) return 0;
        int lost = 0;
        while (state.momentum > 0 && nowMillis >= state.nextMomentumDecayAt) {
            state.momentum--;
            lost++;
            state.nextMomentumDecayAt = safeAdd(state.nextMomentumDecayAt, 1_000L);
        }
        if (state.momentum == 0) state.nextMomentumDecayAt = 0L;
        return lost;
    }

    public synchronized void addFury(String actorId, double amount, long nowMillis) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        state.fury = Math.min(MAX_FURY, state.fury + amount);
        state.lastFuryChange = nowMillis;
    }

    public synchronized double fury(String actorId) { return actorOrEmpty(actorId).fury; }

    public synchronized void consumeFury(String actorId, double amount) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        if (amount > state.fury) throw new IllegalArgumentException("insufficient fury");
        state.fury -= amount;
    }

    public synchronized void addDistanceControl(String actorId, int amount, long nowMillis, long durationMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        if (durationMillis <= 0) throw new IllegalArgumentException("duration must be positive");
        ActorState state = actor(actorId);
        if (state.distanceControlExpiresAt <= nowMillis) state.distanceControl = 0;
        state.distanceControl = Math.min(MAX_DISTANCE_CONTROL, state.distanceControl + amount);
        state.lastDistanceControlChange = nowMillis;
        state.distanceControlExpiresAt = safeAdd(nowMillis, durationMillis);
    }

    public synchronized int distanceControl(String actorId, long nowMillis) {
        ActorState state = actorOrEmpty(actorId);
        if (state.distanceControlExpiresAt <= nowMillis) {
            state.distanceControl = 0;
            state.distanceControlExpiresAt = 0L;
        }
        return state.distanceControl;
    }

    public synchronized void consumeDistanceControl(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        int current = distanceControl(actorId, nowMillis);
        ActorState state = actor(actorId);
        if (amount > current) throw new IllegalArgumentException("insufficient distance control");
        state.distanceControl -= amount;
        if (state.distanceControl == 0) state.distanceControlExpiresAt = 0L;
    }

    public synchronized int loseDistanceControlClamped(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        int current = distanceControl(actorId, nowMillis);
        int lost = Math.min(amount, current);
        ActorState state = actor(actorId);
        state.distanceControl -= lost;
        if (state.distanceControl == 0) state.distanceControlExpiresAt = 0L;
        return lost;
    }

    public synchronized void addFlow(String actorId, int amount, long nowMillis, long durationMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        if (durationMillis <= 0) throw new IllegalArgumentException("duration must be positive");
        ActorState state = actor(actorId);
        if (state.flowExpiresAt <= nowMillis) state.flow = 0;
        state.flow = Math.min(MAX_FLOW, state.flow + amount);
        state.lastFlowChange = nowMillis;
        state.flowExpiresAt = safeAdd(nowMillis, durationMillis);
    }

    public synchronized int flow(String actorId, long nowMillis) {
        ActorState state = actorOrEmpty(actorId);
        if (state.flowExpiresAt <= nowMillis) {
            state.flow = 0;
            state.flowExpiresAt = 0L;
        }
        return state.flow;
    }

    public synchronized void consumeFlow(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        int current = flow(actorId, nowMillis);
        ActorState state = actor(actorId);
        if (amount > current) throw new IllegalArgumentException("insufficient flow");
        state.flow -= amount;
        if (state.flow == 0) state.flowExpiresAt = 0L;
    }

    public synchronized int loseFlowClamped(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        int current = flow(actorId, nowMillis);
        int lost = Math.min(amount, current);
        ActorState state = actor(actorId);
        state.flow -= lost;
        if (state.flow == 0) state.flowExpiresAt = 0L;
        return lost;
    }

    public synchronized int tickStationaryFlow(String actorId, boolean inCombat, boolean relevantMovement, long nowMillis) {
        ActorState state = actor(actorId);
        if (!inCombat) {
            state.stationaryCombatTracking = false;
            state.nextStationaryFlowDecayAt = 0L;
            return 0;
        }
        if (!state.stationaryCombatTracking || relevantMovement) {
            state.stationaryCombatTracking = true;
            state.nextStationaryFlowDecayAt = safeAdd(nowMillis, 3_000L);
            return 0;
        }
        int current = flow(actorId, nowMillis);
        if (current <= 0 || nowMillis < state.nextStationaryFlowDecayAt) return 0;
        int lost = 0;
        while (state.flow > 0 && nowMillis >= state.nextStationaryFlowDecayAt) {
            state.flow--;
            lost++;
            state.nextStationaryFlowDecayAt = safeAdd(state.nextStationaryFlowDecayAt, 1_000L);
        }
        if (state.flow == 0) state.flowExpiresAt = 0L;
        return lost;
    }

    public synchronized void armFlowReposition(String actorId, String targetId, long expiresAtMillis) {
        target(actorId, targetId).flowRepositionExpiresAt = expiresAtMillis;
    }

    public synchronized boolean hasFlowReposition(String actorId, String targetId, long nowMillis) {
        TargetState target = targetOrNull(actorId, targetId);
        return target != null && target.flowRepositionExpiresAt > nowMillis;
    }

    public synchronized boolean consumeFlowReposition(String actorId, String targetId, long nowMillis) {
        if (!hasFlowReposition(actorId, targetId, nowMillis)) return false;
        target(actorId, targetId).flowRepositionExpiresAt = 0L;
        return true;
    }

    public synchronized Optional<FlowPositionSample> flowPositionBaseline(String actorId, String targetId) {
        TargetState target = targetOrNull(actorId, targetId);
        return target == null ? Optional.empty() : Optional.ofNullable(target.flowPositionBaseline);
    }

    public synchronized void setFlowPositionBaseline(String actorId, String targetId, FlowPositionSample sample) {
        target(actorId, targetId).flowPositionBaseline = Objects.requireNonNull(sample);
    }

    public synchronized void clearFlowPositionTracking(String actorId, String targetId) {
        TargetState target = targetOrNull(actorId, targetId);
        if (target != null) {
            target.flowPositionBaseline = null;
            target.flowRepositionExpiresAt = 0L;
        }
    }

    public synchronized void addFocus(String actorId, double amount, long nowMillis) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        state.focus = Math.min(MAX_FOCUS, state.focus + amount);
        state.lastFocusChange = nowMillis;
    }

    public synchronized double focus(String actorId) { return actorOrEmpty(actorId).focus; }

    public synchronized void consumeFocus(String actorId, double amount) {
        requireFiniteNonNegative(amount);
        ActorState state = actor(actorId);
        if (amount > state.focus) throw new IllegalArgumentException("insufficient focus");
        state.focus -= amount;
    }

    public synchronized void addTargetCounter(String actorId, String targetId, TargetCounter counter, int amount, int cap, long nowMillis, long durationMillis) {
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

    public synchronized void consumeTargetCounter(String actorId, String targetId, TargetCounter counter, int amount, long nowMillis) {
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
        target(actorId, targetId).flags.put(flag, expiresAtMillis);
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
        Long until = actorOrEmpty(actorId).cooldowns.get(cooldownKey(targetId, key));
        return until == null || until <= nowMillis;
    }

    public synchronized void startCooldown(String actorId, String targetId, String key, long nowMillis, long durationMillis) {
        if (durationMillis <= 0) throw new IllegalArgumentException("duration must be positive");
        actor(actorId).cooldowns.put(cooldownKey(targetId, key), safeAdd(nowMillis, durationMillis));
    }

    public synchronized boolean actorCooldownReady(String actorId, String key, long nowMillis) {
        Long until = actorOrEmpty(actorId).actorCooldowns.get(requireKey(key));
        return until == null || until <= nowMillis;
    }

    public synchronized void startActorCooldown(String actorId, String key, long nowMillis, long durationMillis) {
        if (durationMillis <= 0) throw new IllegalArgumentException("duration must be positive");
        actor(actorId).actorCooldowns.put(requireKey(key), safeAdd(nowMillis, durationMillis));
    }

    public synchronized void armBattleHarvest(String actorId, String sourceTargetId, long expiresAtMillis) {
        ActorState state = actor(actorId);
        state.battleHarvestSourceTargetId = requireTargetId(sourceTargetId);
        state.battleHarvestExpiresAt = expiresAtMillis;
    }

    public synchronized boolean hasBattleHarvest(String actorId, long nowMillis) {
        ActorState state = actorOrEmpty(actorId);
        return state.battleHarvestSourceTargetId != null && state.battleHarvestExpiresAt > nowMillis;
    }

    public synchronized boolean consumeBattleHarvestForDifferentTarget(String actorId, String targetId, long nowMillis) {
        ActorState state = actorOrEmpty(actorId);
        String target = requireTargetId(targetId);
        if (state.battleHarvestSourceTargetId == null || state.battleHarvestExpiresAt <= nowMillis || state.battleHarvestSourceTargetId.equals(target)) return false;
        state.battleHarvestSourceTargetId = null;
        state.battleHarvestExpiresAt = 0L;
        return true;
    }

    public synchronized boolean recordTargetAndWasDifferent(String actorId, String targetId) {
        ActorState state = actor(actorId);
        String target = requireTargetId(targetId);
        boolean different = state.lastTargetId != null && !state.lastTargetId.equals(target);
        state.lastTargetId = target;
        return different;
    }

    public synchronized OptionalDouble recordTargetDistance(String actorId, String targetId, double distance) {
        if (!Double.isFinite(distance) || distance < 0.0D) throw new IllegalArgumentException("distance must be finite and non-negative");
        TargetState target = target(actorId, targetId);
        double previous = target.lastDistance;
        target.lastDistance = distance;
        return Double.isNaN(previous) ? OptionalDouble.empty() : OptionalDouble.of(previous);
    }

    public synchronized void clear(String actorId) {
        String validatedActorId = requireActorId(actorId);
        actors.remove(validatedActorId);
        actionLedger.clearActor(validatedActorId);
        furyService.clearActor(validatedActorId);
        focusService.clearActor(validatedActorId);
        staminaService.clearActor(validatedActorId);
    }

    private ActorState actor(String actorId) { return actors.computeIfAbsent(requireActorId(actorId), ignored -> new ActorState()); }
    private ActorState actorOrEmpty(String actorId) { ActorState state = actors.get(requireActorId(actorId)); return state == null ? ActorState.EMPTY : state; }
    private TargetState target(String actorId, String targetId) { return actor(actorId).targets.computeIfAbsent(requireTargetId(targetId), ignored -> new TargetState()); }
    private TargetState targetOrNull(String actorId, String targetId) { ActorState state = actors.get(requireActorId(actorId)); return state == null ? null : state.targets.get(requireTargetId(targetId)); }

    private static String cooldownKey(String targetId, String key) { return requireTargetId(targetId) + '\u0000' + requireKey(key); }
    private static String requireKey(String key) { Objects.requireNonNull(key); if (key.isBlank()) throw new IllegalArgumentException("cooldown key must not be blank"); return key; }
    private static String requireActorId(String actorId) { Objects.requireNonNull(actorId); if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank"); return actorId; }
    private static String requireTargetId(String targetId) { Objects.requireNonNull(targetId); if (targetId.isBlank()) throw new IllegalArgumentException("targetId must not be blank"); return targetId; }
    private static void requireFiniteNonNegative(double amount) { if (!Double.isFinite(amount) || amount < 0.0D) throw new IllegalArgumentException("amount must be finite and non-negative"); }
    private static long safeAdd(long left, long right) { return Math.addExact(left, right); }

    private record TimedCounter(int value, long expiresAtMillis) {}

    private static final class TargetState {
        final EnumMap<TargetCounter, TimedCounter> counters = new EnumMap<>(TargetCounter.class);
        final EnumMap<TargetFlag, Long> flags = new EnumMap<>(TargetFlag.class);
        double lastDistance = Double.NaN;
        long flowRepositionExpiresAt;
        FlowPositionSample flowPositionBaseline;
    }

    private static final class ActorState {
        private static final ActorState EMPTY = new ActorState();
        int momentum;
        double fury;
        int distanceControl;
        int flow;
        double focus;
        long lastMomentumChange;
        long nextMomentumDecayAt;
        long lastFuryChange;
        long lastDistanceControlChange;
        long distanceControlExpiresAt;
        long lastFlowChange;
        long flowExpiresAt;
        boolean stationaryCombatTracking;
        long nextStationaryFlowDecayAt;
        long lastFocusChange;
        long battleHarvestExpiresAt;
        String battleHarvestSourceTargetId;
        String lastTargetId;
        final EnumMap<ActorFlag, Long> flags = new EnumMap<>(ActorFlag.class);
        final Map<String, Long> cooldowns = new HashMap<>();
        final Map<String, Long> actorCooldowns = new HashMap<>();
        final Map<String, TargetState> targets = new HashMap<>();
    }
}
