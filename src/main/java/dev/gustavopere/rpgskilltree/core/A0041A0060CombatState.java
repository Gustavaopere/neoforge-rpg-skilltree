package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient resources for A0041-A0060. Nothing here is persisted. */
public final class A0041A0060CombatState {
    private static final long CLAIM_RETENTION_MILLIS = 30_000L;
    private static final long SCYTHE_RESERVATION_RETENTION_MILLIS = 250L;
    private static final long CROSSBOW_RESERVATION_RETENTION_MILLIS = 500L;
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<String, Long> claims = new HashMap<>();
    private final Map<String, ScytheReservation> scytheReservations = new HashMap<>();
    private final Map<String, CrossbowReservation> crossbowReservations = new HashMap<>();

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
     * A second observation of the same root is neutral, and a second root cannot reserve the same
     * actor+target while the first outcome is unresolved.
     */
    public synchronized boolean reserveScytheCut(String actorId, String targetId, String rootActionId, long now) {
        String actor = require(actorId);
        String target = require(targetId);
        String root = require(rootActionId);
        pruneScytheReservations(now);
        String rootKey = actor + '\0' + root;
        ScytheReservation existing = scytheReservations.get(rootKey);
        if (existing != null) return false;
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

    public synchronized void recordCrossbowHit(String actorId, String rootActionId, String weaponId, long now) {
        Actor actor = actor(actorId);
        actor.lastCrossbowHitRoot = require(rootActionId);
        actor.lastCrossbowHitWeapon = require(weaponId);
        actor.lastCrossbowHitAt = now;
    }

    public synchronized boolean consumeCrossbowHitReceipt(
        String actorId, String weaponId, long windowMillis, long now
    ) {
        Actor actor = actor(actorId);
        String weapon = require(weaponId);
        if (actor.lastCrossbowHitRoot == null || actor.lastCrossbowHitWeapon == null) return false;
        if (actor.lastCrossbowHitAt + windowMillis < now) {
            clearCrossbowHitReceipt(actor);
            return false;
        }
        if (!actor.lastCrossbowHitWeapon.equals(weapon)) return false;
        clearCrossbowHitReceipt(actor);
        return true;
    }

    public synchronized void clearCrossbowHitReceipt(String actorId) {
        clearCrossbowHitReceipt(actor(actorId));
    }

    private static void clearCrossbowHitReceipt(Actor actor) {
        actor.lastCrossbowHitRoot = null;
        actor.lastCrossbowHitWeapon = null;
        actor.lastCrossbowHitAt = 0L;
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

    public synchronized boolean reservePiercingBolt(String actorId, String rootActionId, long now) {
        String actor = require(actorId);
        String root = require(rootActionId);
        pruneCrossbowReservations(now);
        Actor state = actor(actor);
        if (state.cadence < NotionCombatPerkRules.A0053_CADENCE_COST) return false;
        String key = reservationKey(actor, root, "A0053");
        if (crossbowReservations.containsKey(key)) return false;
        crossbowReservations.put(
            key,
            new CrossbowReservation(
                actor,
                root,
                "A0053",
                NotionCombatPerkRules.A0053_CADENCE_COST,
                Math.addExact(now, CROSSBOW_RESERVATION_RETENTION_MILLIS)
            )
        );
        return true;
    }

    public synchronized boolean commitPiercingBolt(String actorId, String rootActionId, long now) {
        return commitCadenceReservation(actorId, rootActionId, "A0053", now, false);
    }

    public synchronized void discardPiercingBolt(String actorId, String rootActionId) {
        crossbowReservations.remove(reservationKey(require(actorId), require(rootActionId), "A0053"));
    }

    public synchronized void armAdjustedMechanism(String actorId, long windowMillis, long now) {
        Actor actor = actor(actorId);
        actor.adjustedMechanismUntil = Math.addExact(now, windowMillis);
        actor.adjustedMechanismReservedRoot = null;
        removeReservations(actorId, "A0054");
    }

    public synchronized boolean reserveAdjustedMechanism(String actorId, String rootActionId, long now) {
        String actorIdChecked = require(actorId);
        String root = require(rootActionId);
        pruneCrossbowReservations(now);
        Actor actor = actor(actorIdChecked);
        if (actor.adjustedMechanismUntil <= now) {
            actor.adjustedMechanismUntil = 0L;
            actor.adjustedMechanismReservedRoot = null;
            removeReservations(actorIdChecked, "A0054");
            return false;
        }
        if (actor.cadence < NotionCombatPerkRules.CADENCE_CAP) return false;
        if (actor.adjustedMechanismReservedRoot != null) return false;
        actor.adjustedMechanismReservedRoot = root;
        crossbowReservations.put(
            reservationKey(actorIdChecked, root, "A0054"),
            new CrossbowReservation(
                actorIdChecked,
                root,
                "A0054",
                NotionCombatPerkRules.CADENCE_CAP,
                Math.addExact(now, CROSSBOW_RESERVATION_RETENTION_MILLIS)
            )
        );
        return true;
    }

    public synchronized boolean commitAdjustedMechanism(String actorId, String rootActionId, long now) {
        String actorIdChecked = require(actorId);
        String root = require(rootActionId);
        pruneCrossbowReservations(now);
        Actor actor = actor(actorIdChecked);
        if (!root.equals(actor.adjustedMechanismReservedRoot) || actor.adjustedMechanismUntil <= now) {
            discardAdjustedMechanism(actorIdChecked, root);
            return false;
        }
        boolean committed = commitCadenceReservation(actorIdChecked, root, "A0054", now, true);
        if (committed) {
            actor.adjustedMechanismUntil = 0L;
            actor.adjustedMechanismReservedRoot = null;
        }
        return committed;
    }

    public synchronized void discardAdjustedMechanism(String actorId, String rootActionId) {
        String actorIdChecked = require(actorId);
        String root = require(rootActionId);
        crossbowReservations.remove(reservationKey(actorIdChecked, root, "A0054"));
        Actor actor = actor(actorIdChecked);
        if (root.equals(actor.adjustedMechanismReservedRoot)) actor.adjustedMechanismReservedRoot = null;
    }

    /** Legacy helper retained for callers outside this lot; it consumes only the armed window, not Cadence. */
    public synchronized boolean consumeAdjustedMechanism(String actorId, long now) {
        Actor actor = actor(actorId);
        if (actor.adjustedMechanismUntil <= now || actor.adjustedMechanismReservedRoot != null) {
            if (actor.adjustedMechanismUntil <= now) actor.adjustedMechanismUntil = 0L;
            return false;
        }
        actor.adjustedMechanismUntil = 0L;
        return true;
    }

    private boolean commitCadenceReservation(
        String actorId, String rootActionId, String consumer, long now, boolean requireFullCadence
    ) {
        String actor = require(actorId);
        String root = require(rootActionId);
        pruneCrossbowReservations(now);
        CrossbowReservation reservation = crossbowReservations.remove(reservationKey(actor, root, consumer));
        if (reservation == null) return false;
        Actor state = actor(actor);
        int required = requireFullCadence ? NotionCombatPerkRules.CADENCE_CAP : reservation.cadenceCost;
        if (state.cadence < required) return false;
        state.cadence -= required;
        return true;
    }

    public synchronized void pruneTransient(long now) {
        claims.entrySet().removeIf(entry -> entry.getValue() <= now);
        pruneScytheReservations(now);
        pruneCrossbowReservations(now);
        for (Actor actor : actors.values()) {
            if (actor.adjustedMechanismUntil > 0L && actor.adjustedMechanismUntil <= now) {
                actor.adjustedMechanismUntil = 0L;
                actor.adjustedMechanismReservedRoot = null;
            }
        }
    }

    private void pruneCrossbowReservations(long now) {
        crossbowReservations.entrySet().removeIf(entry -> {
            CrossbowReservation reservation = entry.getValue();
            if (reservation.expiresAt > now) return false;
            Actor actor = actors.get(reservation.actorId);
            if (actor != null && "A0054".equals(reservation.consumer)
                && reservation.rootActionId.equals(actor.adjustedMechanismReservedRoot)) {
                actor.adjustedMechanismReservedRoot = null;
            }
            return true;
        });
    }

    private static String reservationKey(String actorId, String rootActionId, String consumer) {
        return actorId + '\0' + rootActionId + '\0' + consumer;
    }

    private void removeReservations(String actorId, String consumer) {
        String actor = require(actorId);
        crossbowReservations.entrySet().removeIf(entry -> {
            CrossbowReservation reservation = entry.getValue();
            return reservation.actorId.equals(actor) && reservation.consumer.equals(consumer);
        });
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

    /**
     * Reconciles transient ownership against the effective server-side ranks. This is intentionally
     * idempotent and only clears state whose owning perk/prerequisite is no longer valid.
     */
    public synchronized void reconcileForRanks(String actorId, CombatPerkRanks ranks, long now) {
        String actorIdChecked = require(actorId);
        Objects.requireNonNull(ranks, "ranks");
        pruneTransient(now);
        Actor actor = actor(actorIdChecked);

        boolean cadenceOwned = ranks.rank("A0052") > 0
            && ranks.rank("A0050") >= 2
            && ranks.rank("A0051") >= 2;
        if (!cadenceOwned) {
            actor.cadence = 0;
            clearCrossbowHitReceipt(actor);
            actor.adjustedMechanismUntil = 0L;
            actor.adjustedMechanismReservedRoot = null;
            removeReservations(actorIdChecked, "A0053");
            removeReservations(actorIdChecked, "A0054");
        } else {
            if (ranks.rank("A0053") <= 0) removeReservations(actorIdChecked, "A0053");
            boolean adjustedOwned = ranks.learned("A0054")
                && ranks.rank("A0052") >= 2
                && ranks.rank("A0053") >= 1;
            if (!adjustedOwned) {
                actor.adjustedMechanismUntil = 0L;
                actor.adjustedMechanismReservedRoot = null;
                removeReservations(actorIdChecked, "A0054");
            }
        }

        boolean sequenceOwned = ranks.rank("A0058") > 0 && ranks.rank("A0057") >= 2;
        if (!sequenceOwned) {
            actor.sequence = 0;
            actor.lastSequenceHitAt = 0L;
            actor.sequenceWindowMillis = 0L;
            actor.finalCombinationCooldownUntil = 0L;
        } else if (!ranks.learned("A0060")) {
            actor.finalCombinationCooldownUntil = 0L;
        }
    }

    public synchronized void clearActor(String actorId) {
        String id = require(actorId);
        actors.remove(id);
        String prefix = id + '\0';
        claims.keySet().removeIf(key -> key.startsWith(prefix));
        scytheReservations.entrySet().removeIf(entry -> entry.getValue().actorId.equals(id));
        crossbowReservations.entrySet().removeIf(entry -> entry.getValue().actorId.equals(id));
    }

    public synchronized void clearAll() {
        actors.clear();
        claims.clear();
        scytheReservations.clear();
        crossbowReservations.clear();
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
    private record CrossbowReservation(
        String actorId, String rootActionId, String consumer, int cadenceCost, long expiresAt
    ) {}

    private static final class Actor {
        double focus;
        String battleHarvestKilledTarget;
        long battleHarvestUntil;
        long battleHarvestCooldownUntil;
        String lastCrossbowHitRoot;
        String lastCrossbowHitWeapon;
        long lastCrossbowHitAt;
        int cadence;
        long adjustedMechanismUntil;
        String adjustedMechanismReservedRoot;
        int sequence;
        long lastSequenceHitAt;
        long sequenceWindowMillis;
        long finalCombinationCooldownUntil;
        long preparedShotCooldownUntil;
    }
}
