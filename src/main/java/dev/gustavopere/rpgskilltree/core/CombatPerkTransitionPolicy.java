package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;

/** Frozen transition rules for A0004 Momentum, A0016 Distance Control and A0022 Flow. */
public final class CombatPerkTransitionPolicy {
    public static final long FLOW_WINDOW_MILLIS = 2_500L;
    public static final double FLOW_MIN_DISPLACEMENT = 1.5D;
    public static final double FLOW_MIN_ANGLE_DEGREES = 60.0D;

    private CombatPerkTransitionPolicy() {}

    public static void tick(
        String actorId,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        boolean inCombat,
        boolean relevantHorizontalMovement,
        long nowMillis
    ) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (ranks.learned("A0004")) state.decayMomentum(actorId, nowMillis);
        if (ranks.rank("A0022") > 0) {
            state.tickStationaryFlow(actorId, inCombat, relevantHorizontalMovement, nowMillis);
        }
    }

    public static boolean onConfirmedMiss(
        String actorId,
        WeaponFamily family,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        long nowMillis
    ) {
        requireActor(actorId);
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        return switch (family) {
            case SWORD -> ranks.learned("A0004") && state.loseMomentumClamped(actorId, 1) > 0;
            case SPEAR -> ranks.rank("A0016") > 0 && state.loseDistanceControlClamped(actorId, 1, nowMillis) > 0;
            default -> false;
        };
    }

    /** Applies losses only from a provider-confirmed hostile heavy stagger/impact. */
    public static boolean onConfirmedHeavyImpact(
        String actorId,
        boolean hostileSource,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        long nowMillis
    ) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (!hostileSource) return false;
        boolean applicable = false;
        if (ranks.learned("A0004")) {
            applicable = true;
            state.loseMomentumClamped(actorId, 2);
        }
        if (ranks.rank("A0016") > 0) {
            applicable = true;
            state.loseDistanceControlClamped(actorId, 1, nowMillis);
        }
        if (ranks.rank("A0022") > 0) {
            applicable = true;
            state.loseFlowClamped(actorId, 2, nowMillis);
        }
        if (ranks.rank("A0046") > 0) {
            applicable = true;
            state.focusService().applyHeavyImpactLoss(actorId, true, true, state, nowMillis);
        }
        return applicable;
    }

    /**
     * Records trusted server-side positional samples. A target-relative opportunity is armed only
     * when both frozen fallback requirements are met inside 2.5 seconds.
     */
    public static boolean recordFlowPositionSample(
        String actorId,
        String targetId,
        double attackerX,
        double attackerZ,
        double targetX,
        double targetZ,
        boolean trustedMovement,
        NotionCombatPerkState state,
        long nowMillis
    ) {
        requireActor(actorId);
        requireTarget(targetId);
        requireFinite(attackerX, "attackerX");
        requireFinite(attackerZ, "attackerZ");
        requireFinite(targetX, "targetX");
        requireFinite(targetZ, "targetZ");
        Objects.requireNonNull(state);

        if (!trustedMovement) {
            state.clearFlowPositionTracking(actorId, targetId);
            return false;
        }

        var current = new NotionCombatPerkState.FlowPositionSample(
            attackerX, attackerZ, targetX, targetZ, nowMillis
        );
        var previous = state.flowPositionBaseline(actorId, targetId);
        if (previous.isEmpty() || nowMillis - previous.get().atMillis() > FLOW_WINDOW_MILLIS) {
            state.setFlowPositionBaseline(actorId, targetId, current);
            return false;
        }

        var baseline = previous.get();
        double dx = attackerX - baseline.attackerX();
        double dz = attackerZ - baseline.attackerZ();
        double displacement = Math.hypot(dx, dz);
        double angle = relativeAngleDegrees(baseline, current);
        if (displacement + 1.0E-9D < FLOW_MIN_DISPLACEMENT || angle + 1.0E-9D < FLOW_MIN_ANGLE_DEGREES) {
            return false;
        }

        state.armFlowReposition(actorId, targetId, Math.addExact(nowMillis, FLOW_WINDOW_MILLIS));
        state.setFlowPositionBaseline(actorId, targetId, current);
        return true;
    }

    /** One confirmed dagger result grants at most one Flow even when several movement facts overlap. */
    public static boolean consumeFlowOpportunity(
        CanonicalActionIdentity action,
        String actorId,
        String targetId,
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        long nowMillis
    ) {
        Objects.requireNonNull(action);
        requireActor(actorId);
        requireTarget(targetId);
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        int rank = ranks.rank("A0022");
        if (rank <= 0 || family != WeaponFamily.DAGGER || !direct || !hostile || !action.actorId().equals(actorId)) {
            return false;
        }

        boolean dodge = state.hasActorFlag(actorId, NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, nowMillis);
        boolean reposition = state.hasFlowReposition(actorId, targetId, nowMillis);
        if (!dodge && !reposition) return false;
        if (!state.claimPrimaryOnce(action, "A0022:flow-gain", nowMillis)) return false;

        if (dodge) state.consumeActorFlag(actorId, NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, nowMillis);
        if (reposition) state.consumeFlowReposition(actorId, targetId, nowMillis);
        state.addFlow(actorId, 1, nowMillis, rank >= 2 ? 7_000L : 5_000L);
        return true;
    }

    private static double relativeAngleDegrees(
        NotionCombatPerkState.FlowPositionSample first,
        NotionCombatPerkState.FlowPositionSample second
    ) {
        double ax = first.attackerX() - first.targetX();
        double az = first.attackerZ() - first.targetZ();
        double bx = second.attackerX() - second.targetX();
        double bz = second.attackerZ() - second.targetZ();
        double al = Math.hypot(ax, az);
        double bl = Math.hypot(bx, bz);
        if (al <= 1.0E-9D || bl <= 1.0E-9D) return 0.0D;
        double cosine = Math.max(-1.0D, Math.min(1.0D, (ax * bx + az * bz) / (al * bl)));
        return Math.toDegrees(Math.acos(cosine));
    }

    private static void requireActor(String actorId) {
        if (actorId == null || actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
    }

    private static void requireTarget(String targetId) {
        if (targetId == null || targetId.isBlank()) throw new IllegalArgumentException("targetId must not be blank");
    }

    private static void requireFinite(double value, String name) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException(name + " must be finite");
    }
}
