package dev.gustavopere.rpgskilltree.core;

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

    public synchronized void addFlow(String actorId, int amount, long nowMillis) {
        if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
        ActorState state = actor(actorId);
        state.flow = Math.min(MAX_FLOW, state.flow + amount);
        state.lastFlowChange = nowMillis;
    }

    public synchronized int flow(String actorId) {
        return actorOrEmpty(actorId).flow;
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

    private static String requireActorId(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        return actorId;
    }

    private static void requireFiniteNonNegative(double amount) {
        if (!Double.isFinite(amount) || amount < 0.0D) {
            throw new IllegalArgumentException("amount must be finite and non-negative");
        }
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
    }
}
