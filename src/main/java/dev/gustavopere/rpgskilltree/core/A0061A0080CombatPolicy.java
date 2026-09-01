package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure, provider-independent rules for Notion perks A0061-A0080. */
public final class A0061A0080CombatPolicy {
    private A0061A0080CombatPolicy() {}

    public record HitFacts(
        String actorId,
        String targetId,
        String rootActionId,
        double preImpactHealthFraction,
        boolean boss,
        boolean elite,
        boolean sprinting,
        boolean stationary,
        boolean critical,
        boolean directPhysical,
        boolean eligible,
        long now
    ) {
        public HitFacts {
            require(actorId); require(targetId); require(rootActionId);
            if (!Double.isFinite(preImpactHealthFraction)) throw new IllegalArgumentException("preImpactHealthFraction");
        }
    }

    public record PhysicalModifiers(double damageMultiplier, double penetrationFraction, double impactMultiplier) {
        public PhysicalModifiers {
            if (!finitePositive(damageMultiplier) || !finiteNonNegative(penetrationFraction) || !finitePositive(impactMultiplier)) {
                throw new IllegalArgumentException("invalid physical modifiers");
            }
        }
        public static PhysicalModifiers neutral() { return new PhysicalModifiers(1.0D, 0.0D, 1.0D); }
    }

    public record SpecialResult(boolean applied, double damageMultiplier, double impactMultiplier, double staminaRefundFraction) {
        public SpecialResult {
            if (!finitePositive(damageMultiplier) || !finitePositive(impactMultiplier) || !finiteNonNegative(staminaRefundFraction)) {
                throw new IllegalArgumentException("invalid special result");
            }
        }
        public static SpecialResult neutral() { return new SpecialResult(false, 1.0D, 1.0D, 0.0D); }
    }

    public static PhysicalModifiers beforePhysicalHit(HitFacts facts, CombatPerkRanks ranks, A0061A0080CombatState state) {
        Objects.requireNonNull(facts); Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!facts.directPhysical() || !facts.eligible()) return PhysicalModifiers.neutral();

        double bonus = 0.02D * ranks.rank("A0061");
        if (facts.preImpactHealthFraction() < 0.35D) bonus += 0.04D * ranks.rank("A0068");
        if (facts.preImpactHealthFraction() > 0.85D) bonus += 0.04D * ranks.rank("A0069");
        if (facts.boss()) bonus += 0.03D * ranks.rank("A0070");
        else if (facts.elite()) bonus += 0.03D * ranks.rank("A0071");
        if (state.retaliationActive(facts.actorId(), facts.now())) bonus += 0.04D * ranks.rank("A0072");

        bonus += stanceDamageMultiplier(state.stance(facts.actorId())) - 1.0D;
        bonus += movementDamageMultiplier(ranks, facts.sprinting()) - 1.0D;
        bonus += stationaryDamageMultiplier(ranks, facts.stationary()) - 1.0D;

        double penetration = 0.02D * ranks.rank("A0065");
        double impact = 1.0D + 0.03D * ranks.rank("A0066");
        return new PhysicalModifiers(Math.max(0.0D, 1.0D + bonus), penetration, impact);
    }

    public static double criticalChanceBonus(CombatPerkRanks ranks) {
        Objects.requireNonNull(ranks);
        return 0.02D * ranks.rank("A0062");
    }

    public static double criticalDamageMultiplier(CombatPerkRanks ranks, boolean canonicalCritical) {
        Objects.requireNonNull(ranks);
        return canonicalCritical ? 1.0D + 0.05D * ranks.rank("A0063") : 1.0D;
    }

    public static double attackSpeedMultiplier(CombatPerkRanks ranks) {
        Objects.requireNonNull(ranks);
        return 1.0D + 0.02D * ranks.rank("A0064");
    }

    public static double offensiveInterruptionResistanceFraction(CombatPerkRanks ranks) {
        Objects.requireNonNull(ranks);
        return 0.04D * ranks.rank("A0067");
    }

    public static boolean onDirectHostileDamageTaken(
        String actorId, String causalEventId, double postMitigationDamage, boolean directHostile,
        CombatPerkRanks ranks, A0061A0080CombatState state, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!directHostile || !Double.isFinite(postMitigationDamage) || postMitigationDamage <= 0.0D || ranks.rank("A0072") <= 0) return false;
        if (!state.claimOnce(actorId, causalEventId, "A0072:retaliation", now)) return false;
        state.refreshRetaliation(actorId, now);
        return true;
    }

    public static double retaliationDamageMultiplier(String actorId, CombatPerkRanks ranks, A0061A0080CombatState state, long now) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        return state.retaliationActive(actorId, now) ? 1.0D + 0.04D * ranks.rank("A0072") : 1.0D;
    }

    /**
     * A0073. Provider-Impact paths keep the historical eager helper only for compatibility tests;
     * runtime projectile paths (impactAvailable=false) use bounded reservation and are committed by
     * the POST bridge. Epic Fight runtime uses the explicit reserve/commit methods directly.
     */
    public static SpecialResult execution(
        String actorId, String targetId, String rootActionId, double preImpactHealthFraction, boolean boss,
        CombatPerkRanks ranks, A0061A0080CombatState state, boolean impactAvailable, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (ranks.rank("A0073") <= 0) return SpecialResult.neutral();

        if (impactAvailable) {
            if (state.consumeExecution(actorId, targetId, rootActionId, now)) {
                return new SpecialResult(true, boss ? 1.09D : 1.18D, 1.20D, 0.0D);
            }
            if (preImpactHealthFraction < 0.20D && !state.executionCoolingDown(actorId, targetId, now)) {
                state.armExecution(actorId, targetId, rootActionId, now);
            }
            return SpecialResult.neutral();
        }

        if (state.reserveExecution(actorId, targetId, rootActionId, now)) {
            return new SpecialResult(true, boss ? 1.09D : 1.18D, 1.0D, 0.0D);
        }
        if (preImpactHealthFraction < 0.20D
            && !state.executionWindowActive(actorId, targetId, now)
            && !state.executionCoolingDown(actorId, targetId, now)) {
            state.reserveExecutionArmCandidate(actorId, targetId, rootActionId, now);
        }
        return SpecialResult.neutral();
    }

    /**
     * A0074. Vanilla projectile PRE only reserves/history-marks; POST performs the irreversible
     * lastAttack/arm/consume transition. Provider-Impact legacy behavior remains isolated to the
     * compatibility branch used by historical pure tests.
     */
    public static SpecialResult firstBlood(
        String actorId, String targetId, String rootActionId, double preImpactHealthFraction,
        CombatPerkRanks ranks, A0061A0080CombatState state, boolean impactAvailable, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (ranks.rank("A0074") <= 0) {
            if (impactAvailable) state.recordAttackWithoutFirstBloodArm(actorId, targetId, now);
            return SpecialResult.neutral();
        }

        if (impactAvailable) {
            if (state.firstBloodWindowActive(actorId, targetId, now)) {
                A0061A0080CombatState.FirstBloodStage stage = state.firstBloodStage(actorId, targetId, rootActionId, now);
                if (stage == A0061A0080CombatState.FirstBloodStage.CONSUMED) {
                    return new SpecialResult(true, 1.10D, 1.20D, 0.0D);
                }
                return SpecialResult.neutral();
            }
            if (preImpactHealthFraction >= 0.85D) {
                state.firstBloodStage(actorId, targetId, rootActionId, now);
            } else {
                state.recordAttackWithoutFirstBloodArm(actorId, targetId, now);
            }
            return SpecialResult.neutral();
        }

        A0061A0080CombatState.FirstBloodReservation reservation = state.reserveFirstBlood(
            actorId,
            targetId,
            rootActionId,
            preImpactHealthFraction,
            now
        );
        if (reservation == A0061A0080CombatState.FirstBloodReservation.FINISHER) {
            return new SpecialResult(true, 1.10D, 1.0D, 0.0D);
        }
        if (reservation == A0061A0080CombatState.FirstBloodReservation.NONE) {
            state.markFirstBloodHitPending(actorId, targetId, rootActionId, now);
        }
        return SpecialResult.neutral();
    }

    /**
     * Records one semantic martial action for A0075. The feature is all-or-nothing: if any required
     * operational provider is unavailable, qualifiers are cleared and no partial benefit survives.
     */
    public static boolean recordMartialAction(
        String actorId, String actionFamilyId, CombatPerkRanks ranks, A0061A0080CombatState state,
        boolean staminaRegenAvailable, boolean thermalActivityAvailable, boolean exhaustionAvailable, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (ranks.rank("A0075") <= 0) return false;
        if (!staminaRegenAvailable || !thermalActivityAvailable || !exhaustionAvailable) {
            state.resetSustainedQualifiers(actorId);
            return false;
        }
        return state.recordSustainedAction(actorId, actionFamilyId, now);
    }

    public static double sustainedStaminaRegenMultiplier(String actorId, A0061A0080CombatState state, long now) {
        Objects.requireNonNull(state);
        return state.sustainedRhythmActive(actorId, now) ? 1.10D : 1.0D;
    }

    public static double sustainedThermalActivityMultiplier(String actorId, A0061A0080CombatState state, long now) {
        Objects.requireNonNull(state);
        return state.sustainedRhythmActive(actorId, now) ? 1.15D : 1.0D;
    }

    public static double sustainedExhaustionMultiplier(String actorId, A0061A0080CombatState state, long now) {
        Objects.requireNonNull(state);
        return state.sustainedRhythmActive(actorId, now) ? 1.10D : 1.0D;
    }

    public static double stanceDamageMultiplier(A0061A0080CombatState.Stance stance) {
        Objects.requireNonNull(stance);
        return switch (stance) {
            case AGGRESSIVE -> 1.08D;
            case CAUTIOUS -> 0.95D;
            case NONE -> 1.0D;
        };
    }

    public static double stancePhysicalResistanceDelta(A0061A0080CombatState.Stance stance) {
        Objects.requireNonNull(stance);
        return switch (stance) {
            case AGGRESSIVE -> -0.05D;
            case CAUTIOUS -> 0.08D;
            case NONE -> 0.0D;
        };
    }

    public static double movementDamageMultiplier(CombatPerkRanks ranks, boolean genuineServerSprint) {
        Objects.requireNonNull(ranks);
        return genuineServerSprint ? 1.0D + 0.04D * ranks.rank("A0078") : 1.0D;
    }

    public static double stationaryDamageMultiplier(CombatPerkRanks ranks, boolean canonicalStationary) {
        Objects.requireNonNull(ranks);
        return canonicalStationary ? 1.0D + 0.05D * ranks.rank("A0079") : 1.0D;
    }

    public static boolean onConfirmedDodgeAvoidance(
        String actorId, String dodgeEventId, boolean confirmedAvoidedHostileAttack,
        CombatPerkRanks ranks, A0061A0080CombatState state, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!confirmedAvoidedHostileAttack || ranks.rank("A0080") <= 0) return false;
        if (!state.claimOnce(actorId, dodgeEventId, "A0080:dodge", now)) return false;
        return state.armOpportunity(actorId, now);
    }

    /** A0080 PRE reservation; irreversible consumption belongs to the confirmed POST boundary. */
    public static double consumeOpportunityDamageMultiplier(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0061A0080CombatState state, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (ranks.rank("A0080") <= 0) return 1.0D;
        return state.reserveOpportunity(actorId, rootActionId, now) ? 1.15D : 1.0D;
    }

    private static boolean finitePositive(double value) { return Double.isFinite(value) && value > 0.0D; }
    private static boolean finiteNonNegative(double value) { return Double.isFinite(value) && value >= 0.0D; }
    private static String require(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException("blank id");
        return value;
    }
}
