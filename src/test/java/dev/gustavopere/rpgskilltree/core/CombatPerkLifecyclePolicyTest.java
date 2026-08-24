package dev.gustavopere.rpgskilltree.core;

/** P-0013 contract: entity/dimension recreation clears effects/correlations but preserves guards. */
public final class CombatPerkLifecyclePolicyTest {
    public static void main(String[] args) {
        transientClearDropsCombatStateButPreservesCooldownsAndClaims();
        focusTransientClearPreservesCooldownButDropsPreparation();
        staminaTransientClearDropsOldReceiptButPreservesRefundClaimGuard();
        fullSessionClearDropsEverything();
        System.out.println("CombatPerkLifecyclePolicyTest: PASS");
    }

    private static void transientClearDropsCombatStateButPreservesCooldownsAndClaims() {
        var state = new NotionCombatPerkState();
        state.addMomentum("p", 4, 1_000L);
        state.addFury("p", 50.0D, 1_000L);
        state.addDistanceControl("p", 3, 1_000L, 7_000L);
        state.addFlow("p", 4, 1_000L, 7_000L);
        state.addFocus("p", 80.0D, 1_000L);
        state.addTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 3, 3, 1_000L, 6_000L);
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 7_000L);
        state.setActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 7_000L);
        state.startCooldown("p", "mob", "A0036", 1_000L, 12_000L);
        state.startActorCooldown("p", "A0042", 1_000L, 10_000L);
        var action = CanonicalActionIdentity.root("p", "life-claim", "test");
        require(state.claimPrimaryOnce(action, "consumer", 1_000L), "initial claim records");

        state.clearTransientPreservingGuards("p");

        require(state.momentum("p") == 0, "Momentum clears on recreation/dimension");
        require(state.fury("p") == 0.0D, "Fury clears on recreation/dimension");
        require(state.distanceControl("p", 2_000L) == 0, "Distance Control clears");
        require(state.flow("p", 2_000L) == 0, "Flow clears");
        require(state.focus("p") == 0.0D, "Focus clears");
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 2_000L) == 0, "target counters clear");
        require(!state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 2_000L), "target flags clear");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 2_000L), "actor windows clear");
        require(!state.cooldownReady("p", "mob", "A0036", 2_000L), "per-target cooldown survives recreation");
        require(!state.actorCooldownReady("p", "A0042", 2_000L), "actor cooldown survives recreation");
        require(!state.claimPrimaryOnce(action, "consumer", 2_000L), "canonical claim survives recreation and cannot resolve twice");
    }

    private static void focusTransientClearPreservesCooldownButDropsPreparation() {
        var state = new NotionCombatPerkState();
        var focus = state.focusService();
        state.addFocus("p", 100.0D, 0L);
        var prep = CanonicalActionIdentity.root("p", "prep", "test");
        require(focus.beginPreparation(prep, true, true, 0L) == CanonicalFocusService.PreparationStatus.STARTED,
            "preparation starts");
        require(focus.armPreparation(prep, true, state, CanonicalFocusService.REQUIRED_STABLE_AIM_MILLIS)
            == CanonicalFocusService.PreparationStatus.ARMED, "preparation arms");
        var shot = CanonicalActionIdentity.root("p", "shot", "test");
        require(focus.release(new CanonicalFocusService.ReleaseRequest(shot, true, true, true, 8_000L), state,
            CanonicalFocusService.REQUIRED_STABLE_AIM_MILLIS) == CanonicalFocusService.ReleaseStatus.PREPARED_CONSUMED,
            "prepared release creates cooldown");

        focus.clearTransientActorPreservingGuards("p");

        require(focus.beginPreparation(CanonicalActionIdentity.root("p", "after", "test"), true, true, 2_000L)
            == CanonicalFocusService.PreparationStatus.COOLDOWN_ACTIVE,
            "Focus cooldown cannot be bypassed by player recreation/dimension");
        require(focus.projectileAction("old-projectile", 2_000L).isEmpty(), "old projectile/preparation correlations are gone");
    }

    private static void staminaTransientClearDropsOldReceiptButPreservesRefundClaimGuard() {
        var service = new CanonicalStaminaService(30_000L, 32);
        var action = CanonicalActionIdentity.root("p", "stamina", "test");
        require(service.observe(new CanonicalStaminaService.CostObservation(
            action, true, true, 20.0D, "receipt", CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED
        ), 1_000L) == CanonicalStaminaService.CostStatus.RECORDED, "receipt records");
        require(service.refundAmount(action, "perk", 0.10D, 1_001L).isPresent(), "first refund claim records");

        service.clearTransientActorPreservingGuards("p");

        require(service.receipt(action, 1_002L).isEmpty(), "old stamina receipt cannot cross recreation/dimension");
        require(service.observe(new CanonicalStaminaService.CostObservation(
            action, true, true, 20.0D, "receipt-2", CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED
        ), 1_003L) == CanonicalStaminaService.CostStatus.RECORDED, "same identity can be observed only as a fresh transient receipt");
        require(service.refundAmount(action, "perk", 0.10D, 1_004L).isEmpty(),
            "preserved refund claim prevents a second payout for the same canonical action");
    }

    private static void fullSessionClearDropsEverything() {
        var state = new NotionCombatPerkState();
        state.startCooldown("p", "mob", "A0036", 1_000L, 12_000L);
        var action = CanonicalActionIdentity.root("p", "session", "test");
        require(state.claimPrimaryOnce(action, "consumer", 1_000L), "claim records");
        state.clear("p");
        require(state.cooldownReady("p", "mob", "A0036", 1_001L), "full logout/session clear drops cooldown");
        require(state.claimPrimaryOnce(action, "consumer", 1_001L), "full session clear drops old claims");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
