package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class A0061A0080CombatPolicy {
    private A0061A0080CombatPolicy() {}

    public record HitFacts(String actorId, String targetId, String rootActionId, double preImpactHealthFraction,
                           boolean boss, boolean elite, boolean sprinting, boolean stationary, boolean critical,
                           boolean directPhysical, boolean eligible, long now) {
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

    public static PhysicalModifiers beforePhysicalHit(HitFacts f, CombatPerkRanks r, A0061A0080CombatState s) {
        if (!f.directPhysical() || !f.eligible()) return PhysicalModifiers.neutral();
        double bonus = 0.02D * r.rank("A0061");
        if (f.preImpactHealthFraction() < 0.35D) bonus += 0.04D * r.rank("A0068");
        if (f.preImpactHealthFraction() > 0.85D) bonus += 0.04D * r.rank("A0069");
        if (f.boss()) bonus += 0.03D * r.rank("A0070");
        else if (f.elite()) bonus += 0.03D * r.rank("A0071");
        if (s.retaliationActive(f.actorId(), f.now())) bonus += 0.04D * r.rank("A0072");
        bonus += stanceDamageMultiplier(s.stance(f.actorId())) - 1.0D;
        bonus += movementDamageMultiplier(r, f.sprinting()) - 1.0D;
        bonus += stationaryDamageMultiplier(r, f.stationary()) - 1.0D;
        return new PhysicalModifiers(
            Math.max(0.0D, 1.0D + bonus),
            0.02D * r.rank("A0065"),
            1.0D + 0.03D * r.rank("A0066")
        );
    }

    public static double criticalChanceBonus(CombatPerkRanks r) { return 0.02D * r.rank("A0062"); }
    public static double criticalDamageMultiplier(CombatPerkRanks r, boolean canonical) { return canonical ? 1.0D + 0.05D * r.rank("A0063") : 1.0D; }
    public static double attackSpeedMultiplier(CombatPerkRanks r) { return 1.0D + 0.02D * r.rank("A0064"); }
    public static double offensiveInterruptionResistanceFraction(CombatPerkRanks r) { return 0.04D * r.rank("A0067"); }

    public static boolean onDirectHostileDamageTaken(String actor, String event, double damage, boolean direct,
                                                      CombatPerkRanks ranks, A0061A0080CombatState state, long now) {
        if (!direct || !Double.isFinite(damage) || damage <= 0.0D || ranks.rank("A0072") <= 0) return false;
        if (!state.claimOnce(actor, event, "A0072:retaliation", now)) return false;
        state.refreshRetaliation(actor, now);
        return true;
    }

    public static double retaliationDamageMultiplier(String actor, CombatPerkRanks ranks, A0061A0080CombatState state, long now) {
        return state.retaliationActive(actor, now) ? 1.0D + 0.04D * ranks.rank("A0072") : 1.0D;
    }

    public static SpecialResult execution(String actor, String target, String root, double hp, boolean boss,
                                          CombatPerkRanks ranks, A0061A0080CombatState state,
                                          boolean impactAvailable, long now) {
        if (ranks.rank("A0073") <= 0) return SpecialResult.neutral();
        if (impactAvailable) {
            if (state.consumeExecution(actor, target, root, now)) {
                return new SpecialResult(true, boss ? 1.09D : 1.18D, 1.20D, 0.0D);
            }
            if (hp < 0.20D && !state.executionCoolingDown(actor, target, now)) state.armExecution(actor, target, root, now);
            return SpecialResult.neutral();
        }

        A0061A0080ReservationReceiptContext.begin(actor, target, root);
        boolean reserved = state.reserveExecution(actor, target, root, now);
        if (reserved) {
            A0061A0080ReservationReceiptContext.markExecutionReserved();
            return new SpecialResult(true, boss ? 1.09D : 1.18D, 1.0D, 0.0D);
        }
        if (hp < 0.20D
            && !state.executionWindowActive(actor, target, now)
            && !state.executionCoolingDown(actor, target, now)
            && state.reserveExecutionArmCandidate(actor, target, root, now)) {
            A0061A0080ReservationReceiptContext.markExecutionArmCandidate();
        }
        return SpecialResult.neutral();
    }

    public static SpecialResult firstBlood(String actor, String target, String root, double hp,
                                           CombatPerkRanks ranks, A0061A0080CombatState state,
                                           boolean impactAvailable, long now) {
        if (ranks.rank("A0074") <= 0) {
            if (impactAvailable) state.recordAttackWithoutFirstBloodArm(actor, target, now);
            return SpecialResult.neutral();
        }
        if (impactAvailable) {
            if (state.firstBloodWindowActive(actor, target, now)) {
                var stage = state.firstBloodStage(actor, target, root, now);
                return stage == A0061A0080CombatState.FirstBloodStage.CONSUMED
                    ? new SpecialResult(true, 1.10D, 1.20D, 0.0D)
                    : SpecialResult.neutral();
            }
            if (hp >= 0.85D) state.firstBloodStage(actor, target, root, now);
            else state.recordAttackWithoutFirstBloodArm(actor, target, now);
            return SpecialResult.neutral();
        }

        A0061A0080ReservationReceiptContext.begin(actor, target, root);
        var reservation = state.reserveFirstBlood(actor, target, root, hp, now);
        A0061A0080ReservationReceiptContext.markFirstBlood(reservation);
        if (reservation == A0061A0080CombatState.FirstBloodReservation.FINISHER) {
            return new SpecialResult(true, 1.10D, 1.0D, 0.0D);
        }
        if (reservation == A0061A0080CombatState.FirstBloodReservation.NONE) {
            state.markFirstBloodHitPending(actor, target, root, now);
        }
        return SpecialResult.neutral();
    }

    public static boolean recordMartialAction(String actor, String family, CombatPerkRanks ranks,
                                              A0061A0080CombatState state, boolean stamina,
                                              boolean thermal, boolean exhaustion, long now) {
        if (ranks.rank("A0075") <= 0) return false;
        if (!stamina || !thermal || !exhaustion) {
            state.resetSustainedQualifiers(actor);
            return false;
        }
        return state.recordSustainedAction(actor, family, now);
    }

    public static double sustainedStaminaRegenMultiplier(String actor, A0061A0080CombatState state, long now) { return state.sustainedRhythmActive(actor, now) ? 1.10D : 1.0D; }
    public static double sustainedThermalActivityMultiplier(String actor, A0061A0080CombatState state, long now) { return state.sustainedRhythmActive(actor, now) ? 1.15D : 1.0D; }
    public static double sustainedExhaustionMultiplier(String actor, A0061A0080CombatState state, long now) { return state.sustainedRhythmActive(actor, now) ? 1.10D : 1.0D; }

    public static double stanceDamageMultiplier(A0061A0080CombatState.Stance stance) {
        return switch (stance) { case AGGRESSIVE -> 1.08D; case CAUTIOUS -> 0.95D; case NONE -> 1.0D; };
    }
    public static double stancePhysicalResistanceDelta(A0061A0080CombatState.Stance stance) {
        return switch (stance) { case AGGRESSIVE -> -0.05D; case CAUTIOUS -> 0.08D; case NONE -> 0.0D; };
    }
    public static double movementDamageMultiplier(CombatPerkRanks ranks, boolean sprint) { return sprint ? 1.0D + 0.04D * ranks.rank("A0078") : 1.0D; }
    public static double stationaryDamageMultiplier(CombatPerkRanks ranks, boolean stationary) { return stationary ? 1.0D + 0.05D * ranks.rank("A0079") : 1.0D; }

    public static boolean onConfirmedDodgeAvoidance(String actor, String event, boolean avoided,
                                                     CombatPerkRanks ranks, A0061A0080CombatState state, long now) {
        if (!avoided || ranks.rank("A0080") <= 0) return false;
        if (!state.claimOnce(actor, event, "A0080:dodge", now)) return false;
        return state.armOpportunity(actor, now);
    }

    public static double consumeOpportunityDamageMultiplier(String actor, String root, CombatPerkRanks ranks,
                                                             A0061A0080CombatState state, long now) {
        if (ranks.rank("A0080") <= 0) return 1.0D;
        A0061A0080ReservationReceiptContext.begin(actor, null, root);
        boolean reserved = state.reserveOpportunity(actor, root, now);
        if (reserved) A0061A0080ReservationReceiptContext.markOpportunityReserved();
        return reserved ? 1.15D : 1.0D;
    }

    private static boolean finitePositive(double v) { return Double.isFinite(v) && v > 0.0D; }
    private static boolean finiteNonNegative(double v) { return Double.isFinite(v) && v >= 0.0D; }
    private static String require(String v) { Objects.requireNonNull(v); if (v.isBlank()) throw new IllegalArgumentException("blank id"); return v; }
}
