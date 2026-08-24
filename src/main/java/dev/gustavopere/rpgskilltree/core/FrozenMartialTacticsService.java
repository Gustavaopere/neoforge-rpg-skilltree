package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;

/** Canonical root-action state for frozen MARTIAL tactics A0071-A0080. */
public final class FrozenMartialTacticsService {
    private static final long CLAIM_RETENTION_MILLIS = 30_000L;
    private static final int MAX_TARGETS = 8_192;
    private final CanonicalEventLedger attackClaims = new CanonicalEventLedger(8_192);
    private final Map<String, ActorState> actors = new HashMap<>();
    private final Map<TargetKey, TargetState> targets = new HashMap<>();

    public synchronized void confirmDirectHostileDamage(
        String actorId,
        boolean serverAuthoritative,
        boolean directHostile,
        boolean excludedSource,
        long nowMillis
    ) {
        requireTime(nowMillis);
        if (!serverAuthoritative || !directHostile || excludedSource) return;
        actor(actorId).retaliationUntilMillis = Math.addExact(nowMillis, 3_000L);
    }

    public synchronized AttackEffect resolveAttack(AttackRequest request, FrozenCombatPerkRanks ranks, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(ranks);
        requireTime(nowMillis);
        if (!Double.isFinite(request.targetHealthFractionBefore()) || request.targetHealthFractionBefore() < 0.0D
            || request.targetHealthFractionBefore() > 1.0D) throw new IllegalArgumentException("target health must be in [0,1]");
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.direct() || !request.physical()
            || !request.hostileTarget() || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) return AttackEffect.none();
        if (!attackClaims.claimPrimaryOnce(request.action(), "frozen:a0071-a0080/tactics", nowMillis, CLAIM_RETENTION_MILLIS)) {
            return AttackEffect.duplicateResult();
        }

        ActorState actor = actor(request.action().actorId());
        TargetState target = target(request.action().actorId(), request.targetId(), nowMillis);
        double bonus = 0.0D;
        double impact = 0.0D;
        boolean executionPrimed = false;
        boolean executionConsumed = false;
        boolean openingPrimed = false;

        if (request.elite()) bonus += 0.03D * ranks.rank("A0071");
        if (actor.retaliationUntilMillis > nowMillis) bonus += 0.04D * ranks.rank("A0072");
        if (request.selfPropelledSprinting()) bonus += 0.04D * ranks.rank("A0078");
        if (request.stationary()) bonus += 0.05D * ranks.rank("A0079");
        bonus += stanceModifiers(request.action().actorId()).damageMultiplier() - 1.0D;

        if (ranks.learned("A0073")) {
            if (target.executionUntilMillis > nowMillis && target.executionOpener != null
                && !target.executionOpener.sameAction(request.action())) {
                target.executionUntilMillis = 0L;
                target.executionOpener = null;
                target.executionFinal = request.action();
                target.executionCooldownUntilMillis = Math.addExact(nowMillis, 8_000L);
                bonus += request.boss() ? 0.09D : 0.18D;
                if (request.impactProviderAvailable()) impact += 0.20D;
                executionConsumed = true;
            } else if (target.executionUntilMillis <= nowMillis && target.executionCooldownUntilMillis <= nowMillis
                && request.targetHealthFractionBefore() < 0.20D) {
                target.executionOpener = request.action();
                target.executionUntilMillis = Math.addExact(nowMillis, 3_000L);
                executionPrimed = true;
            }
        }

        if (ranks.learned("A0074")) {
            if (target.openingUntilMillis > nowMillis && target.openingAction != null
                && !target.openingAction.sameAction(request.action())) {
                target.openingUntilMillis = 0L;
                target.openingAction = null;
                bonus += 0.10D;
                if (request.impactProviderAvailable()) impact += 0.20D;
            } else if (target.openingUntilMillis <= nowMillis && target.openingCooldownUntilMillis <= nowMillis
                && (target.lastAttackMillis < 0L || nowMillis - target.lastAttackMillis >= 8_000L)) {
                target.openingAction = request.action();
                target.openingUntilMillis = Math.addExact(nowMillis, 4_000L);
                target.openingCooldownUntilMillis = Math.addExact(nowMillis, 12_000L);
                openingPrimed = true;
            }
        }
        target.lastAttackMillis = nowMillis;
        target.lastTouchedMillis = nowMillis;

        if (actor.opportunityUntilMillis > 0L && actor.opportunityUntilMillis <= nowMillis) {
            actor.opportunityCooldownUntilMillis = Math.max(actor.opportunityCooldownUntilMillis, Math.addExact(actor.opportunityUntilMillis, 5_000L));
            actor.opportunityUntilMillis = 0L;
        }
        if (ranks.learned("A0080") && actor.opportunityUntilMillis > nowMillis) {
            bonus += 0.15D;
            actor.opportunityUntilMillis = 0L;
            actor.opportunityCooldownUntilMillis = Math.addExact(nowMillis, 5_000L);
        }
        return new AttackEffect(false, 1.0D + bonus, impact, executionPrimed, executionConsumed, openingPrimed);
    }

    public synchronized OptionalDouble claimExecutionKillRefund(
        String actorId,
        String targetId,
        CanonicalActionIdentity action,
        CanonicalStaminaService stamina,
        long nowMillis
    ) {
        Objects.requireNonNull(stamina);
        requireTime(nowMillis);
        if (!claimExecutionKillAction(actorId, targetId, action)) return OptionalDouble.empty();
        return stamina.refundAmount(action, "A0073", 0.10D, nowMillis);
    }

    /** Consumes only the target/action proof; the runtime must then use the exact Epic Fight receipt bridge. */
    public synchronized boolean claimExecutionKillAction(
        String actorId,
        String targetId,
        CanonicalActionIdentity action
    ) {
        Objects.requireNonNull(action);
        TargetState target = targets.get(new TargetKey(actorId, targetId));
        if (target == null || target.executionFinal == null || !target.executionFinal.sameAction(action)) return false;
        target.executionFinal = null;
        return true;
    }

    public synchronized boolean confirmDodge(String actorId, boolean server, boolean eligibleActor, boolean hostileAttackAvoided, long nowMillis) {
        requireTime(nowMillis);
        ActorState actor = actor(actorId);
        if (!server || !eligibleActor || !hostileAttackAvoided || actor.opportunityCooldownUntilMillis > nowMillis
            || actor.opportunityUntilMillis > nowMillis) return false;
        actor.opportunityUntilMillis = Math.addExact(nowMillis, 3_000L);
        return true;
    }

    public synchronized boolean setStance(String actorId, Stance requested, boolean ownsAggressive, boolean ownsCautious, long nowMillis) {
        Objects.requireNonNull(requested);
        requireTime(nowMillis);
        ActorState actor = actor(actorId);
        if (requested == actor.stance) return true;
        if (actor.stanceSwitchLockedUntilMillis > nowMillis) return false;
        if (requested == Stance.AGGRESSIVE && !ownsAggressive || requested == Stance.CAUTIOUS && !ownsCautious) return false;
        actor.stance = requested;
        actor.stanceSwitchLockedUntilMillis = Math.addExact(nowMillis, 1_500L);
        return true;
    }

    public synchronized void revalidateStance(String actorId, boolean ownsAggressive, boolean ownsCautious) {
        ActorState actor = actors.get(actorId);
        if (actor == null) return;
        if (actor.stance == Stance.AGGRESSIVE && !ownsAggressive || actor.stance == Stance.CAUTIOUS && !ownsCautious) actor.stance = Stance.NONE;
    }

    public synchronized Stance stance(String actorId) { return actor(actorId).stance; }

    public synchronized StanceModifiers stanceModifiers(String actorId) {
        return switch (actor(actorId).stance) {
            case AGGRESSIVE -> new StanceModifiers(1.08D, 0.95D);
            case CAUTIOUS -> new StanceModifiers(0.95D, 1.08D);
            case NONE -> new StanceModifiers(1.0D, 1.0D);
        };
    }

    public synchronized double directPhysicalDamageTakenMultiplier(String actorId) {
        return switch (actor(actorId).stance) {
            case AGGRESSIVE -> 1.05D;
            case CAUTIOUS -> 0.92D;
            case NONE -> 1.0D;
        };
    }

    /** Clears volatile windows but retains per-target cooldowns and event claims. */
    public synchronized void clearTransient(String actorId) {
        ActorState actor = actors.get(actorId);
        if (actor != null) {
            actor.retaliationUntilMillis = 0L;
            actor.opportunityUntilMillis = 0L;
        }
        for (var entry : targets.entrySet()) if (entry.getKey().actorId.equals(actorId)) {
            entry.getValue().executionUntilMillis = 0L;
            entry.getValue().executionOpener = null;
            entry.getValue().executionFinal = null;
            entry.getValue().openingUntilMillis = 0L;
            entry.getValue().openingAction = null;
        }
    }

    private ActorState actor(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        return actors.computeIfAbsent(actorId, ignored -> new ActorState());
    }
    private TargetState target(String actorId, String targetId, long nowMillis) {
        TargetKey key = new TargetKey(actorId, targetId);
        TargetState existing = targets.get(key);
        if (existing != null) return existing;
        targets.entrySet().removeIf(entry -> nowMillis - entry.getValue().lastTouchedMillis > 60_000L);
        while (targets.size() >= MAX_TARGETS) {
            Iterator<Map.Entry<TargetKey, TargetState>> iterator = targets.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
        TargetState created = new TargetState();
        created.lastTouchedMillis = nowMillis;
        targets.put(key, created);
        return created;
    }
    private static void requireTime(long nowMillis) { if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative"); }

    public enum Stance { NONE, AGGRESSIVE, CAUTIOUS }
    public record StanceModifiers(double damageMultiplier, double physicalResistanceMultiplier) {}
    public record AttackRequest(
        CanonicalActionIdentity action,
        String targetId,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        boolean physical,
        boolean hostileTarget,
        double targetHealthFractionBefore,
        boolean elite,
        boolean boss,
        boolean impactProviderAvailable,
        boolean selfPropelledSprinting,
        boolean stationary
    ) { public AttackRequest { Objects.requireNonNull(action); Objects.requireNonNull(targetId); } }
    public record AttackEffect(boolean duplicate, double damageMultiplier, double impactBonus, boolean executionPrimed, boolean executionConsumed, boolean openingPrimed) {
        static AttackEffect none() { return new AttackEffect(false, 1.0D, 0.0D, false, false, false); }
        static AttackEffect duplicateResult() { return new AttackEffect(true, 1.0D, 0.0D, false, false, false); }
        public boolean active() { return damageMultiplier != 1.0D || impactBonus > 0.0D || executionPrimed || executionConsumed || openingPrimed; }
    }

    private record TargetKey(String actorId, String targetId) {}
    private static final class ActorState {
        long retaliationUntilMillis;
        long opportunityUntilMillis;
        long opportunityCooldownUntilMillis;
        long stanceSwitchLockedUntilMillis;
        Stance stance = Stance.NONE;
    }
    private static final class TargetState {
        long executionUntilMillis;
        long executionCooldownUntilMillis;
        CanonicalActionIdentity executionOpener;
        CanonicalActionIdentity executionFinal;
        long openingUntilMillis;
        long openingCooldownUntilMillis;
        long lastAttackMillis = -1L;
        CanonicalActionIdentity openingAction;
        long lastTouchedMillis;
    }
}
