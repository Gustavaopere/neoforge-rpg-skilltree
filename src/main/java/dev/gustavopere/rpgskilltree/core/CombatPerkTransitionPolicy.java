package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Locale;
import java.util.Objects;

/** Frozen transition rules for A0004 Momentum, A0016 Distance Control and A0022 Flow. */
public final class CombatPerkTransitionPolicy {
    public static final long FLOW_WINDOW_MILLIS = 2_500L;
    public static final double FLOW_MIN_DISPLACEMENT = 1.5D;
    public static final double FLOW_MIN_ANGLE_DEGREES = 60.0D;

    private CombatPerkTransitionPolicy() {}

    public static void tick(String actorId, CombatPerkRanks ranks, NotionCombatPerkState state,
                            boolean inCombat, boolean relevantHorizontalMovement, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (ranks.learned("A0004")) state.decayMomentum(actorId, nowMillis);
        if (ranks.rank("A0022") > 0) state.tickStationaryFlow(actorId, inCombat, relevantHorizontalMovement, nowMillis);
    }

    /**
     * Provider-neutral compatibility entry point. The actor/family/server-tick tuple is a fail-closed
     * miss identity so a duplicated callback in the same server tick cannot charge the same transition twice.
     * Provider adapters with a stronger action identity should call the action-aware overload below.
     */
    public static boolean onConfirmedMiss(String actorId, WeaponFamily family, CombatPerkRanks ranks,
                                          NotionCombatPerkState state, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(family);
        CanonicalActionIdentity action = CanonicalActionIdentity.root(
            actorId,
            "confirmed-miss/" + family.name().toLowerCase(Locale.ROOT) + "/" + nowMillis,
            "provider:confirmed-miss"
        );
        return onConfirmedMiss(action, actorId, family, ranks, state, nowMillis);
    }

    /** Applies one frozen miss transition for one canonical provider action. */
    public static boolean onConfirmedMiss(CanonicalActionIdentity action, String actorId, WeaponFamily family,
                                          CombatPerkRanks ranks, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(action);
        requireActor(actorId);
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (!action.actorId().equals(actorId)) return false;

        return switch (family) {
            case SWORD -> ranks.learned("A0004")
                && state.claimPrimaryOnce(action, "A0004:momentum-miss", nowMillis)
                && state.loseMomentumClamped(actorId, 1) > 0;
            case SPEAR -> ranks.rank("A0016") > 0
                && state.claimPrimaryOnce(action, "A0016:distance-control-miss", nowMillis)
                && state.loseDistanceControlClamped(actorId, 1, nowMillis) > 0;
            default -> false;
        };
    }

    /** Applies the A0004 consumer after its own certified heavy-impact receipt claim. */
    public static boolean applyA0004ConfirmedHeavyImpact(String actorId, CombatPerkRanks ranks,
                                                         NotionCombatPerkState state, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        return ranks.learned("A0004") && state.loseMomentumClamped(actorId, 2) > 0;
    }

    /** Applies the A0016 consumer after its own certified heavy-impact receipt claim. */
    public static boolean applyA0016ConfirmedHeavyImpact(String actorId, CombatPerkRanks ranks,
                                                         NotionCombatPerkState state, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        return ranks.rank("A0016") > 0 && state.loseDistanceControlClamped(actorId, 1, nowMillis) > 0;
    }

    /** Applies the A0022 consumer after its own certified heavy-impact receipt claim. */
    public static boolean applyA0022ConfirmedHeavyImpact(String actorId, CombatPerkRanks ranks,
                                                         NotionCombatPerkState state, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        return ranks.rank("A0022") > 0 && state.loseFlowClamped(actorId, 2, nowMillis) > 0;
    }

    /** Applies the A0046 consumer after its own certified heavy-impact receipt claim. */
    public static boolean applyA0046ConfirmedHeavyImpact(String actorId, CombatPerkRanks ranks,
                                                         NotionCombatPerkState state, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (ranks.rank("A0046") <= 0) return false;
        return state.focusService().applyHeavyImpactLoss(actorId, true, true, state, nowMillis);
    }

    /**
     * Legacy aggregate entry point retained for existing callers/tests. Runtime provider wiring must use the
     * individual receipt consumers above so each perk owns an independent idempotent claim.
     */
    public static boolean onConfirmedHeavyImpact(String actorId, boolean hostileSource, CombatPerkRanks ranks,
                                                 NotionCombatPerkState state, long nowMillis) {
        requireActor(actorId);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (!hostileSource) return false;
        boolean applicable = false;
        applicable |= applyA0004ConfirmedHeavyImpact(actorId, ranks, state, nowMillis);
        applicable |= applyA0016ConfirmedHeavyImpact(actorId, ranks, state, nowMillis);
        applicable |= applyA0022ConfirmedHeavyImpact(actorId, ranks, state, nowMillis);
        // A0046 is intentionally excluded from the legacy aggregate. It is owned by its own causal receipt claim.
        return applicable;
    }

    /** Records trusted server-side positional samples using the frozen A0022 fallback thresholds. */
    public static boolean recordFlowPositionSample(String actorId, String targetId, double attackerX,
                                                   double attackerZ, double targetX, double targetZ,
                                                   boolean trustedMovement, NotionCombatPerkState state,
                                                   long nowMillis) {
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

        var current = new NotionCombatPerkState.FlowPositionSample(attackerX, attackerZ, targetX, targetZ, nowMillis);
        var previous = state.flowPositionBaseline(actorId, targetId);
        if (previous.isEmpty() || nowMillis - previous.get().atMillis() > FLOW_WINDOW_MILLIS) {
            state.setFlowPositionBaseline(actorId, targetId, current);
            return false;
        }
        var baseline = previous.get();
        double displacement = Math.hypot(attackerX - baseline.attackerX(), attackerZ - baseline.attackerZ());
        double angle = relativeAngleDegrees(baseline, current);
        if (displacement + 1.0E-9D < FLOW_MIN_DISPLACEMENT || angle + 1.0E-9D < FLOW_MIN_ANGLE_DEGREES) return false;

        state.armFlowReposition(actorId, targetId, Math.addExact(nowMillis, FLOW_WINDOW_MILLIS));
        state.setFlowPositionBaseline(actorId, targetId, current);
        return true;
    }

    /** One confirmed dagger result grants at most one Flow even when several movement facts overlap. */
    public static boolean consumeFlowOpportunity(CanonicalActionIdentity action, String actorId, String targetId,
                                                 WeaponFamily family, boolean direct, boolean hostile,
                                                 CombatPerkRanks ranks, NotionCombatPerkState state,
                                                 long nowMillis) {
        Objects.requireNonNull(action);
        requireActor(actorId);
        requireTarget(targetId);
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        int rank = ranks.rank("A0022");
        if (rank <= 0 || family != WeaponFamily.DAGGER || !direct || !hostile || !action.actorId().equals(actorId)) return false;

        boolean dodge = state.hasActorFlag(actorId, NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, nowMillis);
        boolean reposition = state.hasFlowReposition(actorId, targetId, nowMillis);
        if (!dodge && !reposition) return false;
        if (!state.claimPrimaryOnce(action, "A0022:flow-gain", nowMillis)) return false;

        if (dodge) state.consumeActorFlag(actorId, NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, nowMillis);
        if (reposition) state.consumeFlowReposition(actorId, targetId, nowMillis);
        // A0024 uses its own shorter dodge token. This hit has resolved the dodge opportunity,
        // so clear it too to prevent the legacy post-hit path from granting a second Flow stack.
        if (state.hasActorFlag(actorId, NotionCombatPerkState.ActorFlag.RECENT_DODGE, nowMillis)) {
            state.consumeActorFlag(actorId, NotionCombatPerkState.ActorFlag.RECENT_DODGE, nowMillis);
        }
        state.addFlow(actorId, 1, nowMillis, rank >= 2 ? 7_000L : 5_000L);
        return true;
    }

    private static double relativeAngleDegrees(NotionCombatPerkState.FlowPositionSample first,
                                               NotionCombatPerkState.FlowPositionSample second) {
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
