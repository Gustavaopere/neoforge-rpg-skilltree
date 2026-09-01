package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient state for A0061-A0080. Nothing here is persisted. */
public final class A0061A0080CombatState {
    public enum Stance { NONE, AGGRESSIVE, CAUTIOUS }
    public enum FirstBloodStage { NONE, ARMED, CONSUMED }
    public enum FirstBloodReservation { NONE, OPENER, FINISHER }

    public static final long RETALIATION_WINDOW_MILLIS = 3_000L;
    public static final long EXECUTION_WINDOW_MILLIS = 3_000L;
    public static final long EXECUTION_TARGET_COOLDOWN_MILLIS = 8_000L;
    public static final long FIRST_BLOOD_WINDOW_MILLIS = 4_000L;
    public static final long FIRST_BLOOD_IDLE_MILLIS = 8_000L;
    public static final long FIRST_BLOOD_TARGET_COOLDOWN_MILLIS = 12_000L;
    public static final long SUSTAINED_QUALIFYING_WINDOW_MILLIS = 8_000L;
    public static final long SUSTAINED_ACTIVE_MILLIS = 6_000L;
    public static final long SUSTAINED_COOLDOWN_AFTER_ACTIVE_MILLIS = 12_000L;
    public static final long STANCE_SWAP_COOLDOWN_MILLIS = 1_500L;
    public static final long OPPORTUNITY_WINDOW_MILLIS = 3_000L;
    public static final long OPPORTUNITY_COOLDOWN_MILLIS = 5_000L;
    public static final long PENDING_HIT_RETENTION_MILLIS = 1_000L;
    private static final long CLAIM_RETENTION_MILLIS = 30_000L;

    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<String, TargetState> targets = new HashMap<>();
    private final Map<String, Long> claims = new HashMap<>();

    public synchronized boolean claimOnce(String actorId, String eventId, String consumer, long now) {
        String actor = require(actorId);
        String event = require(eventId);
        String use = require(consumer);
        claims.entrySet().removeIf(entry -> entry.getValue() <= now);
        String key = actor + '\0' + event + '\0' + use;
        if (claims.containsKey(key)) return false;
        claims.put(key, Math.addExact(now, CLAIM_RETENTION_MILLIS));
        return true;
    }

    public synchronized void refreshRetaliation(String actorId, long now) {
        actor(actorId).retaliationUntil = Math.addExact(now, RETALIATION_WINDOW_MILLIS);
    }

    public synchronized boolean retaliationActive(String actorId, long now) {
        return actor(actorId).retaliationUntil > now;
    }

    /** Legacy eager mutation retained only for source compatibility with historical tests. */
    @Deprecated
    public synchronized boolean armExecution(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        if (target.executionCooldownUntil > now || target.executionUntil > now
            || target.executionReservedRoot != null || target.executionArmCandidateRoot != null) return false;
        target.executionRoot = require(rootActionId);
        target.executionUntil = Math.addExact(now, EXECUTION_WINDOW_MILLIS);
        return true;
    }

    /** Legacy eager mutation; runtime adapters must use reservation/commit. */
    @Deprecated
    public synchronized boolean consumeExecution(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        if (target.executionUntil <= now || target.executionRoot == null || target.executionRoot.equals(require(rootActionId))) return false;
        target.executionRoot = null;
        target.executionUntil = 0L;
        target.executionCooldownUntil = Math.addExact(now, EXECUTION_TARGET_COOLDOWN_MILLIS);
        return true;
    }

    public synchronized boolean reserveExecution(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        String root = require(rootActionId);
        if (target.executionUntil <= now || target.executionRoot == null || target.executionRoot.equals(root)) return false;
        if (target.executionReservedRoot != null && !target.executionReservedRoot.equals(root)) return false;
        target.executionReservedRoot = root;
        target.executionPendingUntil = Math.addExact(now, PENDING_HIT_RETENTION_MILLIS);
        return true;
    }

    public synchronized boolean reserveExecutionArmCandidate(
        String actorId, String targetId, String rootActionId, long now
    ) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        if (target.executionCooldownUntil > now || target.executionUntil > now || target.executionReservedRoot != null) return false;
        String root = require(rootActionId);
        if (target.executionArmCandidateRoot != null && !target.executionArmCandidateRoot.equals(root)) return false;
        target.executionArmCandidateRoot = root;
        target.executionPendingUntil = Math.addExact(now, PENDING_HIT_RETENTION_MILLIS);
        return true;
    }

    public synchronized boolean commitExecution(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        String root = require(rootActionId);
        if (!root.equals(target.executionReservedRoot)) return false;
        target.executionReservedRoot = null;
        target.executionPendingUntil = 0L;
        if (target.executionUntil <= now || target.executionRoot == null || target.executionRoot.equals(root)) return false;
        target.executionRoot = null;
        target.executionUntil = 0L;
        target.executionCooldownUntil = Math.addExact(now, EXECUTION_TARGET_COOLDOWN_MILLIS);
        return true;
    }

    public synchronized void rollbackExecution(String actorId, String targetId, String rootActionId) {
        TargetState target = target(actorId, targetId);
        String root = require(rootActionId);
        if (root.equals(target.executionReservedRoot)) target.executionReservedRoot = null;
        if (root.equals(target.executionArmCandidateRoot)) target.executionArmCandidateRoot = null;
        if (target.executionReservedRoot == null && target.executionArmCandidateRoot == null) target.executionPendingUntil = 0L;
    }

    public synchronized boolean armExecutionConfirmed(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        if (target.executionCooldownUntil > now || target.executionUntil > now || target.executionReservedRoot != null) return false;
        target.executionArmCandidateRoot = null;
        target.executionPendingUntil = 0L;
        target.executionRoot = require(rootActionId);
        target.executionUntil = Math.addExact(now, EXECUTION_WINDOW_MILLIS);
        return true;
    }

    /** Commits whichever projectile execution transition was reserved by the canonical PRE root. */
    public synchronized boolean commitPendingExecution(String actorId, String targetId, boolean enabled, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        if (!enabled) {
            clearExecutionPending(target);
            return false;
        }
        if (target.executionReservedRoot != null) {
            String root = target.executionReservedRoot;
            return commitExecution(actorId, targetId, root, now);
        }
        if (target.executionArmCandidateRoot != null) {
            String root = target.executionArmCandidateRoot;
            return armExecutionConfirmed(actorId, targetId, root, now);
        }
        return false;
    }

    public synchronized void rollbackPendingExecution(String actorId, String targetId) {
        clearExecutionPending(target(actorId, targetId));
    }

    public synchronized boolean executionCoolingDown(String actorId, String targetId, long now) {
        return target(actorId, targetId).executionCooldownUntil > now;
    }

    public synchronized boolean executionWindowActive(String actorId, String targetId, long now) {
        TargetState target = target(actorId, targetId);
        expireExecution(target, now);
        return target.executionUntil > now && target.executionRoot != null;
    }

    public synchronized boolean firstBloodWindowActive(String actorId, String targetId, long now) {
        TargetState target = target(actorId, targetId);
        expireFirstBlood(target, now);
        return target.firstBloodUntil > now && target.firstBloodRoot != null;
    }

    /** Legacy eager mutation retained only for source compatibility with historical tests. */
    @Deprecated
    public synchronized FirstBloodStage firstBloodStage(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireFirstBlood(target, now);
        String root = require(rootActionId);
        if (target.firstBloodUntil > now && target.firstBloodRoot != null && !target.firstBloodRoot.equals(root)) {
            target.firstBloodRoot = null;
            target.firstBloodUntil = 0L;
            target.firstBloodCooldownUntil = Math.addExact(now, FIRST_BLOOD_TARGET_COOLDOWN_MILLIS);
            target.lastAttackAt = now;
            return FirstBloodStage.CONSUMED;
        }
        boolean idleEligible = target.lastAttackAt == Long.MIN_VALUE || now - target.lastAttackAt >= FIRST_BLOOD_IDLE_MILLIS;
        boolean canArm = target.firstBloodCooldownUntil <= now && target.firstBloodUntil <= now && idleEligible;
        target.lastAttackAt = now;
        if (!canArm) return FirstBloodStage.NONE;
        target.firstBloodRoot = root;
        target.firstBloodUntil = Math.addExact(now, FIRST_BLOOD_WINDOW_MILLIS);
        return FirstBloodStage.ARMED;
    }

    @Deprecated
    public synchronized void recordAttackWithoutFirstBloodArm(String actorId, String targetId, long now) {
        target(actorId, targetId).lastAttackAt = now;
    }

    public synchronized void markFirstBloodHitPending(String actorId, String targetId, String rootActionId, long now) {
        TargetState target = target(actorId, targetId);
        expireFirstBlood(target, now);
        target.firstBloodHistoryPendingRoot = require(rootActionId);
        target.firstBloodPendingUntil = Math.addExact(now, PENDING_HIT_RETENTION_MILLIS);
    }

    public synchronized FirstBloodReservation reserveFirstBlood(
        String actorId,
        String targetId,
        String rootActionId,
        double preImpactHealthFraction,
        long now
    ) {
        TargetState target = target(actorId, targetId);
        expireFirstBlood(target, now);
        String root = require(rootActionId);
        target.firstBloodHistoryPendingRoot = root;
        target.firstBloodPendingUntil = Math.addExact(now, PENDING_HIT_RETENTION_MILLIS);

        if (target.firstBloodReservedRoot != null) {
            return target.firstBloodReservedRoot.equals(root)
                ? target.firstBloodReservation
                : FirstBloodReservation.NONE;
        }

        if (target.firstBloodUntil > now && target.firstBloodRoot != null && !target.firstBloodRoot.equals(root)) {
            target.firstBloodReservedRoot = root;
            target.firstBloodReservation = FirstBloodReservation.FINISHER;
            return FirstBloodReservation.FINISHER;
        }

        boolean idleEligible = target.lastAttackAt == Long.MIN_VALUE || now - target.lastAttackAt >= FIRST_BLOOD_IDLE_MILLIS;
        boolean canArm = preImpactHealthFraction >= 0.85D
            && target.firstBloodCooldownUntil <= now
            && target.firstBloodUntil <= now
            && idleEligible;
        if (!canArm) return FirstBloodReservation.NONE;

        target.firstBloodReservedRoot = root;
        target.firstBloodReservation = FirstBloodReservation.OPENER;
        return FirstBloodReservation.OPENER;
    }

    public synchronized boolean commitFirstBlood(
        String actorId,
        String targetId,
        String rootActionId,
        FirstBloodReservation reservation,
        long now
    ) {
        Objects.requireNonNull(reservation, "reservation");
        TargetState target = target(actorId, targetId);
        expireFirstBlood(target, now);
        String root = require(rootActionId);
        if (!root.equals(target.firstBloodReservedRoot) || target.firstBloodReservation != reservation) return false;

        target.firstBloodReservedRoot = null;
        target.firstBloodReservation = FirstBloodReservation.NONE;
        target.firstBloodHistoryPendingRoot = null;
        target.firstBloodPendingUntil = 0L;
        target.lastAttackAt = now;

        if (reservation == FirstBloodReservation.OPENER) {
            if (target.firstBloodCooldownUntil > now || target.firstBloodUntil > now) return false;
            target.firstBloodRoot = root;
            target.firstBloodUntil = Math.addExact(now, FIRST_BLOOD_WINDOW_MILLIS);
            return true;
        }
        if (reservation == FirstBloodReservation.FINISHER) {
            if (target.firstBloodUntil <= now || target.firstBloodRoot == null || target.firstBloodRoot.equals(root)) return false;
            target.firstBloodRoot = null;
            target.firstBloodUntil = 0L;
            target.firstBloodCooldownUntil = Math.addExact(now, FIRST_BLOOD_TARGET_COOLDOWN_MILLIS);
            return true;
        }
        return false;
    }

    public synchronized void rollbackFirstBlood(String actorId, String targetId, String rootActionId) {
        TargetState target = target(actorId, targetId);
        String root = require(rootActionId);
        if (root.equals(target.firstBloodReservedRoot)) {
            target.firstBloodReservedRoot = null;
            target.firstBloodReservation = FirstBloodReservation.NONE;
        }
        if (root.equals(target.firstBloodHistoryPendingRoot)) target.firstBloodHistoryPendingRoot = null;
        if (target.firstBloodReservedRoot == null && target.firstBloodHistoryPendingRoot == null) target.firstBloodPendingUntil = 0L;
    }

    public synchronized boolean commitPendingFirstBlood(String actorId, String targetId, boolean enabled, long now) {
        TargetState target = target(actorId, targetId);
        expireFirstBlood(target, now);
        if (!enabled) {
            clearFirstBloodPending(target);
            return false;
        }
        if (target.firstBloodReservedRoot != null) {
            String root = target.firstBloodReservedRoot;
            FirstBloodReservation reservation = target.firstBloodReservation;
            return commitFirstBlood(actorId, targetId, root, reservation, now);
        }
        if (target.firstBloodHistoryPendingRoot != null) {
            target.lastAttackAt = now;
            target.firstBloodHistoryPendingRoot = null;
            target.firstBloodPendingUntil = 0L;
            return true;
        }
        return false;
    }

    public synchronized void rollbackPendingFirstBlood(String actorId, String targetId) {
        clearFirstBloodPending(target(actorId, targetId));
    }

    public synchronized void recordConfirmedAttack(String actorId, String targetId, long now) {
        target(actorId, targetId).lastAttackAt = now;
    }

    public synchronized boolean recordSustainedAction(String actorId, String familyId, long now) {
        Actor actor = actor(actorId);
        expireSustained(actor, now);
        if (actor.sustainedCooldownUntil > now || actor.sustainedUntil > now) return false;
        actor.sustainedFamilies.entrySet().removeIf(entry -> now - entry.getValue() > SUSTAINED_QUALIFYING_WINDOW_MILLIS);
        actor.sustainedFamilies.put(require(familyId), now);
        if (actor.sustainedFamilies.size() < 3) return false;
        actor.sustainedFamilies.clear();
        actor.sustainedUntil = Math.addExact(now, SUSTAINED_ACTIVE_MILLIS);
        actor.sustainedCooldownUntil = Math.addExact(actor.sustainedUntil, SUSTAINED_COOLDOWN_AFTER_ACTIVE_MILLIS);
        return true;
    }

    public synchronized void resetSustainedQualifiers(String actorId) {
        actor(actorId).sustainedFamilies.clear();
    }

    public synchronized boolean sustainedRhythmActive(String actorId, long now) {
        Actor actor = actor(actorId);
        expireSustained(actor, now);
        return actor.sustainedUntil > now;
    }

    public synchronized boolean switchStance(String actorId, Stance requested, long now) {
        Objects.requireNonNull(requested);
        Actor actor = actor(actorId);
        if (requested == actor.stance) return true;
        if (actor.stanceChangedAt != Long.MIN_VALUE && now - actor.stanceChangedAt < STANCE_SWAP_COOLDOWN_MILLIS) return false;
        actor.stance = requested;
        actor.stanceChangedAt = now;
        return true;
    }

    public synchronized void resetStance(String actorId) {
        Actor actor = actor(actorId);
        actor.stance = Stance.NONE;
        actor.stanceChangedAt = Long.MIN_VALUE;
    }

    public synchronized Stance stance(String actorId) { return actor(actorId).stance; }

    public synchronized boolean armOpportunity(String actorId, long now) {
        Actor actor = actor(actorId);
        expireOpportunity(actor, now);
        if (actor.opportunityCooldownUntil > now || actor.opportunityUntil > now) return false;
        actor.opportunityUntil = Math.addExact(now, OPPORTUNITY_WINDOW_MILLIS);
        return true;
    }

    /** Legacy eager mutation retained only for source compatibility. */
    @Deprecated
    public synchronized boolean consumeOpportunity(String actorId, long now) {
        Actor actor = actor(actorId);
        expireOpportunity(actor, now);
        if (actor.opportunityUntil <= now) return false;
        actor.opportunityUntil = 0L;
        actor.opportunityCooldownUntil = Math.addExact(now, OPPORTUNITY_COOLDOWN_MILLIS);
        return true;
    }

    public synchronized boolean reserveOpportunity(String actorId, String rootActionId, long now) {
        Actor actor = actor(actorId);
        expireOpportunity(actor, now);
        String root = require(rootActionId);
        if (actor.opportunityUntil <= now) return false;
        if (actor.opportunityReservedRoot != null && !actor.opportunityReservedRoot.equals(root)) return false;
        actor.opportunityReservedRoot = root;
        actor.opportunityPendingUntil = Math.addExact(now, PENDING_HIT_RETENTION_MILLIS);
        return true;
    }

    public synchronized boolean commitOpportunity(String actorId, String rootActionId, long now) {
        Actor actor = actor(actorId);
        expireOpportunity(actor, now);
        String root = require(rootActionId);
        if (!root.equals(actor.opportunityReservedRoot)) return false;
        actor.opportunityReservedRoot = null;
        actor.opportunityPendingUntil = 0L;
        if (actor.opportunityUntil <= now) return false;
        actor.opportunityUntil = 0L;
        actor.opportunityCooldownUntil = Math.addExact(now, OPPORTUNITY_COOLDOWN_MILLIS);
        return true;
    }

    public synchronized void rollbackOpportunity(String actorId, String rootActionId) {
        Actor actor = actor(actorId);
        String root = require(rootActionId);
        if (root.equals(actor.opportunityReservedRoot)) {
            actor.opportunityReservedRoot = null;
            actor.opportunityPendingUntil = 0L;
        }
    }

    public synchronized boolean commitPendingOpportunity(String actorId, boolean enabled, long now) {
        Actor actor = actor(actorId);
        expireOpportunity(actor, now);
        if (!enabled || actor.opportunityReservedRoot == null) {
            actor.opportunityReservedRoot = null;
            actor.opportunityPendingUntil = 0L;
            return false;
        }
        String root = actor.opportunityReservedRoot;
        return commitOpportunity(actorId, root, now);
    }

    public synchronized void rollbackPendingOpportunity(String actorId) {
        Actor actor = actor(actorId);
        actor.opportunityReservedRoot = null;
        actor.opportunityPendingUntil = 0L;
    }

    public synchronized void rollbackPendingPhysicalHit(String actorId, String targetId) {
        rollbackPendingExecution(actorId, targetId);
        rollbackPendingFirstBlood(actorId, targetId);
        rollbackPendingOpportunity(actorId);
    }

    public synchronized void clearTarget(String targetId) {
        String suffix = "\0" + require(targetId);
        targets.keySet().removeIf(key -> key.endsWith(suffix));
    }

    public synchronized void clearActor(String actorId) {
        String actor = require(actorId);
        actors.remove(actor);
        String prefix = actor + '\0';
        targets.keySet().removeIf(key -> key.startsWith(prefix));
        claims.keySet().removeIf(key -> key.startsWith(prefix));
    }

    public synchronized void clearAll() {
        actors.clear();
        targets.clear();
        claims.clear();
    }

    private Actor actor(String actorId) { return actors.computeIfAbsent(require(actorId), ignored -> new Actor()); }
    private TargetState target(String actorId, String targetId) {
        String key = require(actorId) + '\0' + require(targetId);
        return targets.computeIfAbsent(key, ignored -> new TargetState());
    }

    private static void expireExecution(TargetState target, long now) {
        if (target.executionUntil > 0L && target.executionUntil <= now) {
            target.executionRoot = null;
            target.executionUntil = 0L;
        }
        if (target.executionPendingUntil > 0L && target.executionPendingUntil <= now) {
            clearExecutionPending(target);
        }
    }

    private static void clearExecutionPending(TargetState target) {
        target.executionReservedRoot = null;
        target.executionArmCandidateRoot = null;
        target.executionPendingUntil = 0L;
    }

    private static void expireFirstBlood(TargetState target, long now) {
        if (target.firstBloodUntil > 0L && target.firstBloodUntil <= now) {
            target.firstBloodRoot = null;
            target.firstBloodUntil = 0L;
        }
        if (target.firstBloodPendingUntil > 0L && target.firstBloodPendingUntil <= now) {
            clearFirstBloodPending(target);
        }
    }

    private static void clearFirstBloodPending(TargetState target) {
        target.firstBloodReservedRoot = null;
        target.firstBloodReservation = FirstBloodReservation.NONE;
        target.firstBloodHistoryPendingRoot = null;
        target.firstBloodPendingUntil = 0L;
    }

    private static void expireSustained(Actor actor, long now) {
        if (actor.sustainedUntil > 0L && actor.sustainedUntil <= now) actor.sustainedUntil = 0L;
    }

    private static void expireOpportunity(Actor actor, long now) {
        if (actor.opportunityUntil > 0L && actor.opportunityUntil <= now) {
            long expiredAt = actor.opportunityUntil;
            actor.opportunityUntil = 0L;
            actor.opportunityCooldownUntil = Math.max(
                actor.opportunityCooldownUntil,
                Math.addExact(expiredAt, OPPORTUNITY_COOLDOWN_MILLIS)
            );
        }
        if (actor.opportunityPendingUntil > 0L && actor.opportunityPendingUntil <= now) {
            actor.opportunityReservedRoot = null;
            actor.opportunityPendingUntil = 0L;
        }
    }

    private static String require(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException("blank id");
        return value;
    }

    private static final class Actor {
        long retaliationUntil;
        final LinkedHashMap<String, Long> sustainedFamilies = new LinkedHashMap<>();
        long sustainedUntil;
        long sustainedCooldownUntil;
        Stance stance = Stance.NONE;
        long stanceChangedAt = Long.MIN_VALUE;
        long opportunityUntil;
        long opportunityCooldownUntil;
        String opportunityReservedRoot;
        long opportunityPendingUntil;
    }

    private static final class TargetState {
        String executionRoot;
        long executionUntil;
        long executionCooldownUntil;
        String executionReservedRoot;
        String executionArmCandidateRoot;
        long executionPendingUntil;
        long lastAttackAt = Long.MIN_VALUE;
        String firstBloodRoot;
        long firstBloodUntil;
        long firstBloodCooldownUntil;
        String firstBloodReservedRoot;
        FirstBloodReservation firstBloodReservation = FirstBloodReservation.NONE;
        String firstBloodHistoryPendingRoot;
        long firstBloodPendingUntil;
    }
}
