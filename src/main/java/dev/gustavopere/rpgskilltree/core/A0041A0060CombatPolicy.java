package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure A0041-A0060 combat policy. Runtime adapters pass only facts they can prove. */
public final class A0041A0060CombatPolicy {
    private A0041A0060CombatPolicy() {}

    public record CombatResult(
        boolean applied,
        boolean finalCombination,
        double damageMultiplier,
        double impactMultiplier,
        double guardPressureMultiplier,
        double penetrationFraction,
        double staminaRefundFraction
    ) {
        public static CombatResult neutral() {
            return new CombatResult(false, false, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D);
        }
    }

    /** Shot commitment state. Costs are paid before this record is returned active. */
    public record BowShot(
        boolean active,
        boolean prepared,
        double launchSpeedMultiplier,
        double penetrationFraction,
        double damageMultiplier,
        double minHitDistance
    ) {
        public static BowShot neutral() {
            return new BowShot(false, false, 1.0D, 0.0D, 1.0D, Double.POSITIVE_INFINITY);
        }
    }

    public static CombatResult scytheCut(
        String actorId, String targetId, String rootActionId, CombatPerkRanks ranks,
        A0021A0040CombatState legacy, A0041A0060CombatState state,
        double healthFraction, boolean impactAvailable, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        Objects.requireNonNull(legacy); Objects.requireNonNull(targetId);
        int rank = ranks.rank("A0041");
        if (rank <= 0 || healthFraction > NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION) return CombatResult.neutral();
        if (!legacy.reapMature(actorId, targetId, healthFraction, now)) return CombatResult.neutral();
        if (!state.claimOnce(actorId, rootActionId, "A0041:consume", now)) return CombatResult.neutral();
        if (!legacy.consumeMatureReap(actorId, targetId, healthFraction, now)) return CombatResult.neutral();
        return new CombatResult(
            true, false, NotionCombatPerkRules.reapCutDamageMultiplier(rank),
            impactAvailable ? NotionCombatPerkRules.reapCutImpactMultiplier(rank) : 1.0D,
            1.0D, 0.0D, 0.0D
        );
    }

    public static boolean armBattleHarvestOnKill(
        String actorId, String targetId, CombatPerkRanks ranks,
        A0021A0040CombatState legacy, A0041A0060CombatState state,
        int mastery, boolean legitimateKill, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(legacy); Objects.requireNonNull(state);
        if (!ranks.learned("A0042") || mastery < 80 || !legitimateKill) return false;
        if (!legacy.reapMature(actorId, targetId, 0.0D, now)) return false;
        return state.armBattleHarvest(actorId, targetId, battleHarvestCooldownMillis(mastery), now);
    }

    public static boolean consumeBattleHarvestOnHit(
        String actorId, String targetId, CombatPerkRanks ranks, A0041A0060CombatState state, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        return ranks.learned("A0042") && state.consumeBattleHarvest(actorId, targetId, now);
    }

    public static double focusStableGain(int rank, long millis) {
        if (millis <= 0L) return 0.0D;
        return NotionCombatPerkRules.focusStableGainPerSecond(rank) * millis / 1_000.0D;
    }

    public static double focusDistantHitGain(int rank) {
        return NotionCombatPerkRules.focusDistantHitGain(rank);
    }

    public static void drainFocusWhileSprinting(String actorId, A0041A0060CombatState state, long millis) {
        Objects.requireNonNull(state);
        if (millis > 0L) state.loseFocus(actorId, 12.0D * millis / 1_000.0D);
    }

    public static void loseFocusForAbruptAim(String actorId, A0041A0060CombatState state) {
        Objects.requireNonNull(state); state.loseFocus(actorId, 10.0D);
    }

    public static void loseFocusForHighDrawCancel(String actorId, A0041A0060CombatState state) {
        Objects.requireNonNull(state); state.loseFocus(actorId, 15.0D);
    }

    public static void loseFocusForHeavyImpact(String actorId, A0041A0060CombatState state) {
        Objects.requireNonNull(state); state.loseFocus(actorId, 25.0D);
    }

    public static BowShot tryDominatedShot(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state,
        boolean fullyDrawn, long stableAimMillis, boolean projectileSpeedAvailable,
        boolean penetrationAvailable, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        int rank = ranks.rank("A0047");
        boolean anySafeComponent = projectileSpeedAvailable || penetrationAvailable;
        if (rank <= 0 || !fullyDrawn || stableAimMillis < NotionCombatPerkRules.A0047_STABLE_AIM_MILLIS
            || state.focus(actorId) < NotionCombatPerkRules.A0047_FOCUS_COST || !anySafeComponent) return BowShot.neutral();
        if (!state.claimOnce(actorId, rootActionId, "A0047:commit", now)) return BowShot.neutral();
        state.consumeFocus(actorId, NotionCombatPerkRules.A0047_FOCUS_COST);
        return new BowShot(
            true, false,
            projectileSpeedAvailable ? NotionCombatPerkRules.dominatedShotLaunchSpeedMultiplier(rank) : 1.0D,
            penetrationAvailable ? NotionCombatPerkRules.dominatedShotPenetrationFraction(rank) : 0.0D,
            1.0D, NotionCombatPerkRules.A0047_MIN_DISTANCE
        );
    }

    public static BowShot tryPreparedShot(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state,
        int mastery, boolean fullyDrawn, long stableAimMillis, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        if (!ranks.learned("A0048") || mastery < 80 || !fullyDrawn
            || stableAimMillis < NotionCombatPerkRules.A0048_STABLE_AIM_MILLIS
            || state.focus(actorId) < NotionCombatPerkRules.A0048_MIN_FOCUS
            || !state.preparedShotReady(actorId, now)) return BowShot.neutral();
        if (!state.claimOnce(actorId, rootActionId, "A0048:commit", now)) return BowShot.neutral();
        state.consumeFocus(actorId, NotionCombatPerkRules.A0048_FOCUS_COST);
        state.startPreparedShotCooldown(actorId, preparedShotCooldownMillis(mastery), now);
        return new BowShot(true, true, 1.0D, 0.15D, 1.20D, NotionCombatPerkRules.A0048_MIN_DISTANCE);
    }

    public static CombatResult resolveBowHit(BowShot shot, double distance, boolean penetrationAvailable) {
        Objects.requireNonNull(shot);
        if (!shot.active() || distance < shot.minHitDistance()) return CombatResult.neutral();
        return new CombatResult(
            true, false, shot.damageMultiplier(), 1.0D, 1.0D,
            penetrationAvailable ? shot.penetrationFraction() : 0.0D, 0.0D
        );
    }

    public static void recordCrossbowHit(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        if (ranks.rank("A0052") <= 0 || !state.claimOnce(actorId, rootActionId, "A0052:hit", now)) return;
        state.recordCrossbowHit(actorId, rootActionId, now);
    }

    public static boolean onCrossbowReloadComplete(
        String actorId, String weaponId, CombatPerkRanks ranks, A0041A0060CombatState state,
        boolean nativeAmmoConsumed, long now
    ) {
        Objects.requireNonNull(weaponId); Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        int rank = ranks.rank("A0052");
        if (rank <= 0 || !nativeAmmoConsumed) return false;
        if (!state.consumeCrossbowHitReceipt(actorId, NotionCombatPerkRules.cadenceReloadWindowMillis(rank), now)) return false;
        state.addCadence(actorId);
        return true;
    }

    public static CombatResult tryPiercingBolt(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state,
        boolean fullyLoaded, boolean penetrationAvailable, boolean impactAvailable, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        int rank = ranks.rank("A0053");
        if (rank <= 0 || !fullyLoaded || state.cadence(actorId) < NotionCombatPerkRules.A0053_CADENCE_COST
            || (!penetrationAvailable && !impactAvailable)) return CombatResult.neutral();
        if (!state.claimOnce(actorId, rootActionId, "A0053:consume", now)) return CombatResult.neutral();
        state.consumeCadence(actorId, NotionCombatPerkRules.A0053_CADENCE_COST);
        return new CombatResult(
            true, false, 1.0D,
            impactAvailable ? NotionCombatPerkRules.piercingBoltImpactMultiplier(rank) : 1.0D,
            1.0D,
            penetrationAvailable ? NotionCombatPerkRules.piercingBoltPenetrationFraction(rank) : 0.0D,
            0.0D
        );
    }

    public static void onCrossbowFailure(String actorId, A0041A0060CombatState state) {
        Objects.requireNonNull(state); state.loseCadence(actorId, 1);
    }

    public static boolean armAdjustedMechanismOnReload(
        String actorId, CombatPerkRanks ranks, A0041A0060CombatState state,
        int mastery, boolean nativeReload, long now
    ) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!ranks.learned("A0054") || mastery < 80 || !nativeReload || state.cadence(actorId) < NotionCombatPerkRules.CADENCE_CAP) return false;
        state.armAdjustedMechanism(actorId, NotionCombatPerkRules.adjustedMechanismWindowMillis(mastery), now);
        return true;
    }

    public static CombatResult tryAdjustedCrossbowShot(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        if (!ranks.learned("A0054") || !state.consumeAdjustedMechanism(actorId, now)) return CombatResult.neutral();
        if (!state.claimOnce(actorId, rootActionId, "A0054:shot", now)) return CombatResult.neutral();
        return new CombatResult(true, false, 1.15D, 1.0D, 1.0D, 0.0D, 0.0D);
    }

    public static void afterConfirmedFistHit(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        int rank = ranks.rank("A0058");
        if (rank <= 0 || !state.claimOnce(actorId, rootActionId, "A0058:gain", now)) return;
        state.addSequence(actorId, rank, now);
    }

    public static void breakFistSequence(String actorId, A0041A0060CombatState state) {
        Objects.requireNonNull(state); state.resetSequence(actorId);
    }

    public static CombatResult beforeFistHeavy(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state,
        int mastery, boolean heavyConfirmed, boolean guardPressureAvailable, boolean impactAvailable, long now
    ) {
        requireCommon(actorId, rootActionId, ranks, state);
        if (!heavyConfirmed) return CombatResult.neutral();
        int sequence = state.sequence(actorId, now);

        if (ranks.learned("A0060") && mastery >= 80 && sequence >= NotionCombatPerkRules.SEQUENCE_CAP
            && state.finalCombinationReady(actorId, now)) {
            if (!state.claimOnce(actorId, rootActionId, "A0060:consume", now)) return CombatResult.neutral();
            state.consumeSequence(actorId, NotionCombatPerkRules.SEQUENCE_CAP, now);
            state.startFinalCombinationCooldown(actorId, finalCombinationCooldownMillis(mastery), now);
            return new CombatResult(
                true, true, 1.18D, impactAvailable ? 1.25D : 1.0D,
                1.0D, 0.0D,
                0.0D // No causal Epic Fight stamina-debit receipts are currently exposed safely.
            );
        }

        int rank = ranks.rank("A0059");
        if (rank <= 0 || sequence < NotionCombatPerkRules.A0059_SEQUENCE_COST
            || (!guardPressureAvailable && !impactAvailable)) return CombatResult.neutral();
        if (!state.claimOnce(actorId, rootActionId, "A0059:consume", now)) return CombatResult.neutral();
        state.consumeSequence(actorId, NotionCombatPerkRules.A0059_SEQUENCE_COST, now);
        return new CombatResult(
            true, false, 1.0D,
            impactAvailable ? NotionCombatPerkRules.rhythmBreakImpactMultiplier(rank) : 1.0D,
            guardPressureAvailable ? NotionCombatPerkRules.rhythmBreakPressureMultiplier(rank) : 1.0D,
            0.0D, 0.0D
        );
    }

    public static long battleHarvestCooldownMillis(int mastery) {
        return NotionCombatPerkRules.battleHarvestCooldownMillis(mastery);
    }

    public static long preparedShotCooldownMillis(int mastery) {
        return NotionCombatPerkRules.preparedShotCooldownMillis(mastery);
    }

    public static long finalCombinationCooldownMillis(int mastery) {
        return NotionCombatPerkRules.finalCombinationCooldownMillis(mastery);
    }

    private static void requireCommon(
        String actorId, String rootActionId, CombatPerkRanks ranks, A0041A0060CombatState state
    ) {
        Objects.requireNonNull(actorId); Objects.requireNonNull(rootActionId);
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (actorId.isBlank() || rootActionId.isBlank()) throw new IllegalArgumentException("blank action identity");
    }
}
