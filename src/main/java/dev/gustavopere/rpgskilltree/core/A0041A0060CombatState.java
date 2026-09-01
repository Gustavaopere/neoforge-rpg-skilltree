package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient resources for A0041-A0060. Nothing here is persisted. */
public final class A0041A0060CombatState {
    private static final long CLAIM_RETENTION_MILLIS = 30_000L;
    private static final long SCYTHE_RESERVATION_RETENTION_MILLIS = 250L;
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<String, Long> claims = new HashMap<>();
    private final Map<String, ScytheReservation> scytheReservations = new HashMap<>();

    public synchronized boolean claimOnce(String actorId, String rootActionId, String consumer, long now) {
        require(actorId); require(rootActionId); require(consumer);
        claims.entrySet().removeIf(entry -> entry.getValue() <= now);
        String key = actorId + '\0' + rootActionId + '\0' + consumer;
        if (claims.containsKey(key)) return false;
        claims.put(key, Math.addExact(now, CLAIM_RETENTION_MILLIS));
        return true;
    }

    /**
     * Reserves one mature A0041 mark for exactly one causal root without mutating the legacy mark.
     * A second root cannot reserve the same actor+target while the first outcome is unresolved.
     */
    public synchronized boolean reserveScytheCut(String actorId, String targetId, String rootActionId, long now) {
        String actor = require(actorId);
        String target = require(targetId);
        String root = require(rootActionId);
        pruneScytheReservations(now);
        String rootKey = actor + '\0' + root;
        ScytheReservation existing = scytheReservations.get(rootKey);
        if (existing != null) return existing.targetId.equals(target);
        boolean targetReserved = scytheReservations.values().stream()
            .anyMatch(reservation -> reservation.actorId.equals(actor) && reservation.targetId.equals(target));
        if (targetReserved) return false;
        scytheReservations.put(
            rootKey,
            new ScytheReservation(actor, target, root, Math.addExact(now, SCYTHE_RESERVATION_RETENTION_MILLIS))
        );
        return true;
    }

    public synchronized boolean commitScytheCutReservation(
        String actorId, String targetId, String rootActionId, long now
    ) {
        String actor = require(actorId);
        String target = require(targetId);
        String root = require(rootActionId);
        pruneScytheReservations(now);
        ScytheReservation reservation = scytheReservations.remove(actor + '\0' + root);
        return reservation != null
            && reservation.actorId.equals(actor)
            && reservation.targetId.equals(target);
    }

    /** Returns the reserved root for this actor+target and consumes the reservation. */
    public synchronized String takeScytheCutReservationForTarget(String actorId, String targetId, long now) {
        String actor = require(actorId);
        String target = require(targetId);
        pruneScytheReservations(now);
        String key = null;
        ScytheReservation matched = null;
        for (Map.Entry<String, ScytheReservation> entry : scytheReservations.entrySet()) {
            ScytheReservation reservation = entry.getValue();
            if (reservation.actorId.equals(actor) && reservation.targetId.equals(target)) {
                key = entry.getKey();
                matched = reservation;
                break;
            }
        }
        if (key == null) return null;
        scytheReservations.remove(key);
        return matched.rootActionId;
    }

    public synchronized void discardScytheCutReservation(String actorId, String rootActionId) {
        scytheReservations.remove(require(actorId) + '\0' + require(rootActionId));
    }

    public synchronized void discardScytheCutReservationForTarget(String actorId, String targetId) {
        String actor = require(actorId);
        String target = require(targetId);
        scytheReservations.entrySet().removeIf(entry -> {
            ScytheReservation reservation = entry.getValue();
            return reservation.actorId.equals(actor) && reservation.targetId.equals(target);
        });
    }

    private void pruneScytheReservations(long now) {
        scytheReservations.entrySet().removeIf(entry -> entry.getValue().expiresAt <= now);
    }

    public synchronized double focus(String actorId) {
        return actor(actorId).focus;
    }

    public synchronized double addFocus(String actorId, double amount) {
        if (!Double.isFinite(amount)) throw new IllegalArgumentException("amount");
        Actor actor = actor(actorId);
        actor.focus = clamp(actor.focus + amount, 0.0D, NotionCombatPerkRules.FOCUS_CAP);
        return actor.focus;
    }

    public synchronized double consumeFocus(String actorId, double amount) {
        if (!Double.isFinite(amount) || amount < 0.0D) throw new IllegalArgumentException("amount");
        Actor actor = actor(actorId);
        double used = Math.min(amount, actor.focus);
        actor.focus -= used;
        return used;
    }

    public synchronized void loseFocus(String actorId, double amount) {
        consumeFocus(actorId, Math.max(0.0D, amount));
    }

    public synchronized boolean battleHarvestReady(String actorId, long now) {
        return actor(actorId).battleHarvestCooldownUntil <= now;
    }

    public synchronized boolean armBattleHarvest(String actorId, String killedTarget, long cooldownMillis, long now) {
        Actor actor = actor(actorId);
        if (actor.battleHarvestCooldownUntil > now) return false;
        actor.battleHarvestKilledTarget = require(killedTarget);
        actor.battleHarvestUntil = Math.addExact(now, NotionCombatPerkRules.A0042_WINDOW_MILLIS);
        actor.battleHarvestCooldownUntil = Math.addExact(now, cooldownMillis);
        return true;
    }

    public synchronized boolean consumeBattleHarvest(String actorId, String targetId, long now) {
        Actor actor = actor(actorId);
        String target = require(targetId);
        if (actor.battleHarvestUntil <= now || actor.battleHarvestKilledTarget == null) {
            actor.battleHarvestUntil = 0L;
            actor.battleHarvestKilledTarget = null;
            return false;
        }
        if (actor.battleHarvestKilledTarget.equals(target)) return false;
        actor.battleHarvestUntil = 0L;
        actor.battleHarvestKilledTarget = null;
        return true;
    }

    public synchronized void recordCrossbowHit(String actorId, String rootActionId, long now) {
        Actor actor = actor(actorId);
        actor.lastCrossbowHitRoot = require(rootActionId);
        actor.lastCrossbowHitAt = now;
    }

    public synchronized boolean consumeCrossbowHitReceipt(String actorId, long windowMillis, long now) {
        Actor actor = actor(actorId);
        if (actor.lastCrossbowHitRoot == null || actor.lastCrossbowHitAt + windowMillis < now) {
            actor.lastCrossbowHitRoot = null;
            actor.lastCrossbowHitAt = 0L;
            return false;
        }
        actor.lastCrossbowHitRoot = null;
        actor.lastCrossbowHitAt = 0L;
        return true;
    }

    public synchronized int cadence(String actorId) {
        return actor(actorId).cadence;
    }

    public synchronized int addCadence(String actorId) {
        Actor actor = actor(actorId);
        actor.cadence = Math.min(NotionCombatPerkRules.CADENCE_CAP, actor.cadence + 1);
        return actor.cadence;
    }

    public synchronized int consumeCadence(String actorId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount");
        Actor actor = actor(actorId);
        int used = Math.min(amount, actor.cadence);
        actor.cadence -= used;
        return used;
    }

    public synchronized void loseCadence(String actorId, int amount) {
        consumeCadence(actorId, Math.max(0, amount));
    }

    public synchronized void armAdjustedMechanism(String actorId, long windowMillis, long now) {
        Actor actor = actor(actorId);
        actor.adjustedMechanismUntil = Math.addExact(now, windowMillis);
        actor.cadence = 0;
    }

    public synchronized boolean consumeAdjustedMechanism(String actorId, long now) {
        Actor actor = actor(actorId);
        if (actor.adjustedMechanismUntil <= now) {
            actor.adjustedMechanismUntil = 0L;
            return false;
        }
        actor.adjustedMechanismUntil = 0L;
        return true;
    }

    public synchronized int sequence(String actorId, long now) {
        Actor actor = actor(actorId);
        if (actor.sequence <= 0) return 0;
        if (actor.sequenceWindowMillis > 0L && actor.lastSequenceHitAt + actor.sequenceWindowMillis < now) {
            actor.sequence = 0;
            actor.lastSequenceHitAt = 0L;
        }
        return actor.sequence;
    }

    public synchronized int addSequence(String actorId, int rank, long now) {
        Actor actor = actor(actorId);
        long window = NotionCombatPerkRules.sequenceWindowMillis(rank);
        if (actor.sequence > 0 && actor.lastSequenceHitAt + window < now) actor.sequence = 0;
        actor.sequence = Math.min(NotionCombatPerkRules.SEQUENCE_CAP, actor.sequence + 1);
        actor.lastSequenceHitAt = now;
        actor.sequenceWindowMillis = window;
        return actor.sequence;
    }

    public synchronized int consumeSequence(String actorId, int amount, long now) {
        int current = sequence(actorId, now);
        Actor actor = actor(actorId);
        int used = Math.min(Math.max(0, amount), current);
        actor.sequence -= used;
        if (actor.sequence == 0) {
            actor.lastSequenceHitAt = 0L;
            actor.sequenceWindowMillis = 0L;
        }
        return used;
    }

    public synchronized void resetSequence(String actorId) {
        Actor actor = actor(actorId);
        actor.sequence = 0;
        actor.lastSequenceHitAt = 0L;
        actor.sequenceWindowMillis = 0L;
    }

    public synchronized boolean finalCombinationReady(String actorId, long now) {
        return actor(actorId).finalCombinationCooldownUntil <= now;
    }

    public synchronized void startFinalCombinationCooldown(String actorId, long cooldownMillis, long now) {
        actor(actorId).finalCombinationCooldownUntil = Math.addExact(now, cooldownMillis);
    }

    public synchronized boolean preparedShotReady(String actorId, long now) {
        return actor(actorId).preparedShotCooldownUntil <= now;
    }

    public synchronized void startPreparedShotCooldown(String actorId, long cooldownMillis, long now) {
        actor(actorId).preparedShotCooldownUntil = Math.addExact(now, cooldownMillis);
    }

    public synchronized void clearActor(String actorId) {
        String id = require(actorId);
        actors.remove(id);
        String prefix = id + '\0';
        claims.keySet().removeIf(key -> key.startsWith(prefix));
        scytheReservations.entrySet().removeIf(entry -> entry.getValue().actorId.equals(id));
    }

    public synchronized void clearAll() {
        actors.clear();
        claims.clear();
        scytheReservations.clear();
    }

    private Actor actor(String actorId) {
        return actors.computeIfAbsent(require(actorId), ignored -> new Actor());
    }

    private static String require(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException("blank id");
        return value;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private record ScytheReservation(String actorId, String targetId, String rootActionId, long expiresAt) {}

    private static final class Actor {
        double focus;
        String battleHarvestKilledTarget;
        long battleHarvestUntil;
        long battleHarvestCooldownUntil;
        String lastCrossbowHitRoot;
        long lastCrossbowHitAt;
        int cadence;
        long adjustedMechanismUntil;
        int sequence;
        long lastSequenceHitAt;
        long sequenceWindowMillis;
        long finalCombinationCooldownUntil;
        long preparedShotCooldownUntil;
    }
}
