package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** Consumer-side contract after the certified P-0002 heavy-impact receipt has been claimed. */
public final class CombatPerkHeavyImpactConsumerPolicyTest {
    public static void main(String[] args) {
        a0004LosesExactlyTwoMomentumImmediately();
        a0016LosesExactlyOneDistanceControl();
        a0022LosesExactlyTwoFlow();
        a0046LosesExactlyTwentyFiveFocus();
        unlearnedConsumersDoNothing();
        System.out.println("CombatPerkHeavyImpactConsumerPolicyTest: PASS");
    }

    private static void a0004LosesExactlyTwoMomentumImmediately() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 5, 1_000L);

        require(CombatPerkTransitionPolicy.applyA0004ConfirmedHeavyImpact("p", ranks, state, 2_000L),
            "claimed A0004 heavy receipt must resolve");
        require(state.momentum("p") == 3, "A0004 heavy receipt removes exactly two Momentum immediately");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 5_999L);
        require(state.momentum("p") == 3, "A0004 heavy loss preserves the original inactivity timer");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 6_000L);
        require(state.momentum("p") == 2, "original five-second inactivity timer remains authoritative after heavy loss");
    }

    private static void a0016LosesExactlyOneDistanceControl() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2));
        state.addDistanceControl("p", 3, 1_000L, 7_000L);

        require(CombatPerkTransitionPolicy.applyA0016ConfirmedHeavyImpact("p", ranks, state, 2_000L),
            "claimed A0016 heavy receipt must resolve");
        require(state.distanceControl("p", 2_000L) == 2, "A0016 heavy receipt removes exactly one stack");
        require(state.distanceControl("p", 7_999L) == 2, "A0016 heavy loss must preserve existing shared expiry");
        require(state.distanceControl("p", 8_000L) == 0, "A0016 heavy loss must not refresh expiry");
    }

    private static void a0022LosesExactlyTwoFlow() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2));
        state.addFlow("p", 4, 1_000L, 7_000L);

        require(CombatPerkTransitionPolicy.applyA0022ConfirmedHeavyImpact("p", ranks, state, 2_000L),
            "claimed A0022 heavy receipt must resolve");
        require(state.flow("p", 2_000L) == 2, "A0022 heavy receipt removes exactly two Flow");
        require(state.flow("p", 7_999L) == 2, "A0022 heavy loss must preserve existing shared expiry");
        require(state.flow("p", 8_000L) == 0, "A0022 heavy loss must not refresh expiry");
    }

    private static void a0046LosesExactlyTwentyFiveFocus() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0046", 1));
        state.addFocus("p", 100.0D, 1_000L);

        require(CombatPerkTransitionPolicy.applyA0046ConfirmedHeavyImpact("p", ranks, state, 2_000L),
            "claimed A0046 heavy receipt must resolve");
        require(close(state.focus("p"), 75.0D), "A0046 heavy receipt removes exactly 25 Focus");
    }

    private static void unlearnedConsumersDoNothing() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of());
        state.addMomentum("p", 5, 1_000L);
        state.addDistanceControl("p", 3, 1_000L, 7_000L);
        state.addFlow("p", 4, 1_000L, 7_000L);
        state.addFocus("p", 100.0D, 1_000L);

        require(!CombatPerkTransitionPolicy.applyA0004ConfirmedHeavyImpact("p", ranks, state, 2_000L), "unlearned A0004 is inert");
        require(!CombatPerkTransitionPolicy.applyA0016ConfirmedHeavyImpact("p", ranks, state, 2_000L), "unlearned A0016 is inert");
        require(!CombatPerkTransitionPolicy.applyA0022ConfirmedHeavyImpact("p", ranks, state, 2_000L), "unlearned A0022 is inert");
        require(!CombatPerkTransitionPolicy.applyA0046ConfirmedHeavyImpact("p", ranks, state, 2_000L), "unlearned A0046 is inert");
        require(state.momentum("p") == 5, "unlearned heavy consumers cannot alter Momentum");
        require(state.distanceControl("p", 2_000L) == 3, "unlearned heavy consumers cannot alter Distance Control");
        require(state.flow("p", 2_000L) == 4, "unlearned heavy consumers cannot alter Flow");
        require(close(state.focus("p"), 100.0D), "unlearned heavy consumers cannot alter Focus");
    }

    private static boolean close(double a, double b) {
        return Math.abs(a - b) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
