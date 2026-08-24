package dev.gustavopere.rpgskilltree.core;

import java.util.OptionalDouble;

public final class CanonicalFuryServiceTest {
    public static void main(String[] args) {
        unspecifiedBaseGainDisablesOnlyProduction();
        confirmedProductionIsCappedAndCreditedOnce();
        targetSwitchBonusUsesTheLastCreditedTarget();
        consumersRequireThresholdAndConsumeOnce();
        System.out.println("CanonicalFuryServiceTest: PASS");
    }

    private static void unspecifiedBaseGainDisablesOnlyProduction() {
        var state = new NotionCombatPerkState();
        var service = new CanonicalFuryService(30_000L, 64);
        var action = root("axe-1");
        var request = production(action, "mob-a", 2, OptionalDouble.empty());

        require(service.produce(request, state, 1_000L) == CanonicalFuryService.ProductionStatus.UNSUPPORTED_UNSPECIFIED_BASE_GAIN,
            "A0010 cannot invent a base gain");
        require(close(state.fury("p"), 0.0D), "undefined production changes no resource");

        require(service.produce(production(root("axe-2"), "mob-a", 2, OptionalDouble.of(10.0D)), state, 2_000L)
            == CanonicalFuryService.ProductionStatus.APPLIED, "a later canonical producer can be wired independently");
        require(close(state.fury("p"), 12.0D), "rank two adds twenty percent");
    }

    private static void confirmedProductionIsCappedAndCreditedOnce() {
        var state = new NotionCombatPerkState();
        var service = new CanonicalFuryService(30_000L, 64);
        var action = root("axe-1");
        var request = production(action, "mob-a", 2, OptionalDouble.of(90.0D));

        require(service.produce(request, state, 1_000L) == CanonicalFuryService.ProductionStatus.APPLIED,
            "confirmed direct hit produces fury");
        require(close(state.fury("p"), 100.0D), "fury cap is one hundred");
        require(service.produce(request.withAction(action.withSource("epicfight:damage_post_duplicate")), state, 1_001L)
            == CanonicalFuryService.ProductionStatus.DUPLICATE, "same hit cannot receive double credit");
        require(close(state.fury("p"), 100.0D), "duplicate changes nothing");
    }

    private static void targetSwitchBonusUsesTheLastCreditedTarget() {
        var state = new NotionCombatPerkState();
        var service = new CanonicalFuryService(30_000L, 64);

        service.produce(production(root("axe-1"), "mob-a", 1, OptionalDouble.of(10.0D)), state, 1_000L);
        service.produce(production(root("axe-2"), "mob-a", 1, OptionalDouble.of(10.0D)), state, 2_000L);
        service.produce(production(root("axe-3"), "mob-b", 1, OptionalDouble.of(10.0D)), state, 3_000L);

        require(close(state.fury("p"), 38.5D), "only the credited target switch receives plus fifty percent");
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
        OptionalDouble baseGain
    ) {
        return new CanonicalFuryService.ProductionRequest(
            action, targetId, true, true, true, true, true, rank, baseGain
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
