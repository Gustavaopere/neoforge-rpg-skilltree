package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import java.util.Optional;

/** Frozen Notion contract for A0004 Momentum loss/decay semantics. */
public final class FrozenA0004MomentumPolicyTest {
    private static final String HEAVY_CONSUMER = "rpgskilltree:a0004_momentum_heavy";

    public static void main(String[] args) {
        confirmedMissLosesOneImmediatelyAndDuplicateCallbackDoesNotDoubleCharge();
        confirmedHeavyLosesTwoImmediatelyAndPreservesInactivityTimer();
        inactivityStartsAfterFiveSecondsAndGainRefreshesTimer();
        lossesClampAtZero();
        duplicateHeavyReceiptCallbackCannotDoubleCharge();
        System.out.println("FrozenA0004MomentumPolicyTest: PASS");
    }

    private static void confirmedMissLosesOneImmediatelyAndDuplicateCallbackDoesNotDoubleCharge() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 4, 1_000L);

        require(CombatPerkTransitionPolicy.onConfirmedMiss("p", WeaponFamily.SWORD, ranks, state, 2_000L),
            "confirmed sword miss must resolve A0004");
        require(state.momentum("p") == 3, "confirmed sword miss removes exactly one Momentum immediately");

        require(!CombatPerkTransitionPolicy.onConfirmedMiss("p", WeaponFamily.SWORD, ranks, state, 2_000L),
            "duplicate callback for the same confirmed miss must be rejected");
        require(state.momentum("p") == 3, "duplicate miss callback cannot remove a second Momentum");

        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 5_999L);
        require(state.momentum("p") == 3, "miss loss must not refresh or shorten the original five-second inactivity timer");
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 6_000L);
        require(state.momentum("p") == 2, "inactivity decay still starts five seconds after the last valid gain");
    }

    private static void confirmedHeavyLosesTwoImmediatelyAndPreservesInactivityTimer() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 5, 1_000L);

        require(CombatPerkTransitionPolicy.applyA0004ConfirmedHeavyImpact("p", ranks, state, 2_000L),
            "claimed heavy-impact receipt must resolve A0004");
        require(state.momentum("p") == 3, "confirmed heavy impact removes exactly two Momentum immediately");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 5_999L);
        require(state.momentum("p") == 3, "heavy loss must preserve the original inactivity timer");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 6_000L);
        require(state.momentum("p") == 2, "original inactivity timer still decays one Momentum per second");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 7_000L);
        require(state.momentum("p") == 1, "inactivity decay continues at one Momentum per second");
    }

    private static void inactivityStartsAfterFiveSecondsAndGainRefreshesTimer() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 2, 1_000L);
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 5_999L);
        require(state.momentum("p") == 2, "Momentum cannot decay before five seconds without a gain");
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 6_000L);
        require(state.momentum("p") == 1, "five seconds without a gain starts one-per-second decay");

        state.addMomentum("p", 1, 6_100L);
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 11_099L);
        require(state.momentum("p") == 2, "a valid gain refreshes the inactivity timer even after decay began");
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 11_100L);
        require(state.momentum("p") == 1, "refreshed inactivity timer starts exactly five seconds after the gain");
    }

    private static void lossesClampAtZero() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 1, 1_000L);

        require(CombatPerkTransitionPolicy.applyA0004ConfirmedHeavyImpact("p", ranks, state, 2_000L),
            "heavy impact with one Momentum still resolves");
        require(state.momentum("p") == 0, "heavy impact clamps Momentum at zero instead of underflowing");
        require(!CombatPerkTransitionPolicy.onConfirmedMiss("p", WeaponFamily.SWORD, ranks, state, 3_000L),
            "miss at zero has no additional loss to apply");
        require(state.momentum("p") == 0, "miss cannot underflow Momentum below zero");
    }

    private static void duplicateHeavyReceiptCallbackCannotDoubleCharge() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 5, 1_000L);
        Object victim = new Object();
        Object source = new Object();
        var receipt = new HeavyImpactReceiptCorrelation.Receipt(
            "p", HeavyImpactReceiptCorrelation.ImpactKind.KNOCKDOWN
        );
        var window = new HeavyImpactReceiptWindow(victim, source, Optional.of(receipt));

        if (window.claim(victim, source, HEAVY_CONSUMER).isPresent()) {
            CombatPerkTransitionPolicy.applyA0004ConfirmedHeavyImpact("p", ranks, state, 2_000L);
        }
        require(state.momentum("p") == 3, "first claimed heavy receipt removes exactly two Momentum");

        if (window.claim(victim, source, HEAVY_CONSUMER).isPresent()) {
            CombatPerkTransitionPolicy.applyA0004ConfirmedHeavyImpact("p", ranks, state, 2_000L);
        }
        require(state.momentum("p") == 3, "duplicate heavy callback cannot claim the same consumer twice");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
