package dev.gustavopere.rpgskilltree.core;

import java.util.OptionalDouble;

public final class CanonicalFuryServiceTest {
    public static void main(String[] args) {
        frozenBaseGainIgnoresLegacyProviderAmount();
        confirmedProductionIsCappedAndCreditedOnce();
        targetSwitchBonusUsesTheLastCreditedTarget();
        invalidAndDerivedProductionIsRejected();
        consumersRequireThresholdAndConsumeOnce();
        System.out.println("CanonicalFuryServiceTest: PASS");
    }

    private static void frozenBaseGainIgnoresLegacyProviderAmount() {
        var state = new NotionCombatPerkState();
        var service = new CanonicalFuryService(30_000L, 64);

        require(service.produce(production(root("r1"), "mob-a", 1, OptionalDouble.empty()), state, 1_000L)
            == CanonicalFuryService.ProductionStatus.APPLIED, "frozen A0010 no longer depends on provider amount");
        require(close(state.fury("p"), 8.8D), "rank one uses canonical base eight");

        var other = new NotionCombatPerkState();
        require(service.produce(production(root("r2"), "mob-a", 2, OptionalDouble.of(99.0D)), other, 2_000L)
            == CanonicalFuryService.ProductionStatus.APPLIED, "legacy amount field cannot override frozen base");
        require(close(other.fury("p"), 9.6D), "rank two uses canonical base eight regardless of provider field");
    }

    private static void confirmedProductionIsCappedAndCreditedOnce() {
        var state = new NotionCombatPerkState();
        state.addFury("p", 95.0D, 500L);
        var service = new CanonicalFuryService(30_000L, 64);
        var action = root("axe-1");
        var request = production(action, "mob-a", 2, OptionalDouble.empty());

        require(service.produce(request, state, 1_000L) == CanonicalFuryService.ProductionStatus.APPLIED,
            "confirmed direct hit produces fury");
        require(close(state.fury("p"), 100.0D), "fury cap is one hundred");
        require(service.produce(request.withAction(action.withSource("epicfight:damage_post_duplicate")), state, 1_001L)
            == CanonicalFuryService.ProductionStatus.DUPLICATE, "same hit cannot receive double credit");
        require(close(state.fury("p"), 100.0D), "duplicate changes nothing");
    }

    private static void targetSwitchBonusUsesTheLastCreditedTarget() {
        var rankOneState = new NotionCombatPerkState();
        var service = new CanonicalFuryService(30_000L, 64);
        service.produce(production(root("r1-a"), "mob-a", 1, OptionalDouble.empty()), rankOneState, 1_000L);
        service.produce(production(root("r1-b"), "mob-b", 1, OptionalDouble.empty()), rankOneState, 2_000L);
        require(close(rankOneState.fury("p"), 22.0D), "rank one grants 8.8 then 13.2 on target switch");

        var rankTwoState = new NotionCombatPerkState();
        service.produce(production(root("r2-a"), "mob-a", 2, OptionalDouble.empty()), rankTwoState, 3_000L);
        service.produce(production(root("r2-b"), "mob-b", 2, OptionalDouble.empty()), rankTwoState, 4_000L);
        require(close(rankTwoState.fury("p"), 24.0D), "rank two grants 9.6 then 14.4 on target switch");
    }

    private static void invalidAndDerivedProductionIsRejected() {
        var service = new CanonicalFuryService(30_000L, 64);
        var state = new NotionCombatPerkState();
        var root = root("axe-root");

        require(service.produce(new CanonicalFuryService.ProductionRequest(
            root, "mob", true, false, true, true, true, 1, OptionalDouble.empty()), state, 1_000L)
            == CanonicalFuryService.ProductionStatus.INELIGIBLE, "ineligible/fake actor cannot produce Fury");
        require(service.produce(new CanonicalFuryService.ProductionRequest(
            root("indirect"), "mob", true, true, false, true, true, 1, OptionalDouble.empty()), state, 2_000L)
            == CanonicalFuryService.ProductionStatus.INELIGIBLE, "indirect action cannot produce Fury");
        require(service.produce(new CanonicalFuryService.ProductionRequest(
            root("passive"), "mob", true, true, true, false, true, 1, OptionalDouble.empty()), state, 3_000L)
            == CanonicalFuryService.ProductionStatus.INELIGIBLE, "invalid target cannot produce Fury");
        require(service.produce(production(root("proc").child("secondary"), "mob", 1, OptionalDouble.empty()), state, 4_000L)
            == CanonicalFuryService.ProductionStatus.INELIGIBLE, "proc-depth action cannot produce Fury");
        require(close(state.fury("p"), 0.0D), "rejected actions leave Fury unchanged");
    }

    private static void consumersRequireThresholdAndConsumeOnce() {
        var state = new NotionCombatPerkState();
        state.addFury("p", 100.0D, 500L);
        var service = new CanonicalFuryService(30_000L, 64);
        var action = root("axe-1");
        var request = new CanonicalFuryService.ConsumptionRequest(
            action, true, true, true, "A0012-heavy", 100.0D, 40.0D
        );

        require(service.consume(request, state, 1_000L) == CanonicalFuryService.ConsumptionStatus.APPLIED,
            "qualified consumer spends exact fury");
        require(close(state.fury("p"), 60.0D), "consumer cost");
        require(service.consume(request, state, 1_001L) == CanonicalFuryService.ConsumptionStatus.DUPLICATE,
            "same action cannot spend twice");
        require(service.consume(request.withAction(root("axe-2")), state, 2_000L)
            == CanonicalFuryService.ConsumptionStatus.INSUFFICIENT_RESOURCE, "threshold is checked before effect");
        require(service.consume(request.withAction(root("axe-3").child("rpgskilltree:proc")), state, 3_000L)
            == CanonicalFuryService.ConsumptionStatus.INELIGIBLE, "secondary proc cannot consume fury");
    }

    private static CanonicalFuryService.ProductionRequest production(
        CanonicalActionIdentity action,
        String targetId,
        int rank,
        OptionalDouble legacyBaseGain
    ) {
        return new CanonicalFuryService.ProductionRequest(
            action, targetId, true, true, true, true, true, rank, legacyBaseGain
        );
    }

    private static CanonicalActionIdentity root(String actionId) {
        return CanonicalActionIdentity.root("p", actionId, "epicfight:damage_post");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
