package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** Frozen transition contract for A0004, A0016 and A0022. */
public final class CombatPerkTransitionPolicyTest {
    public static void main(String[] args) {
        momentumDecaysAfterFiveSecondsAndLossesDoNotRefresh();
        distanceControlLossesPreserveSharedExpiry();
        flowUsesTwoPointFiveSecondDodgeWindowAndAtMostOneGain();
        flowPositionalFallbackRequiresRealDisplacementAndSixtyDegrees();
        flowStationaryDecayStartsAfterThreeSecondsAndIsActorLocal();
        heavyImpactUsesFrozenLossesIncludingFocus();
        System.out.println("CombatPerkTransitionPolicyTest: PASS");
    }

    private static void momentumDecaysAfterFiveSecondsAndLossesDoNotRefresh() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1));
        state.addMomentum("p", 3, 1_000L);

        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 5_999L);
        require(state.momentum("p") == 3, "Momentum must not decay before five seconds");
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 6_000L);
        require(state.momentum("p") == 2, "Momentum loses one exactly at the five-second inactivity boundary");

        CombatPerkTransitionPolicy.onConfirmedMiss("p", WeaponFamily.SWORD, ranks, state, 6_100L);
        require(state.momentum("p") == 1, "confirmed sword miss removes one Momentum");
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 7_000L);
        require(state.momentum("p") == 0, "losses must not restart Momentum inactivity timer");

        state.addMomentum("p", 5, 10_000L);
        state.addMomentum("p", 1, 12_000L);
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 16_999L);
        require(state.momentum("p") == 5, "eligible gain at cap still refreshes inactivity timer");
        CombatPerkTransitionPolicy.tick("p", ranks, state, false, false, 17_000L);
        require(state.momentum("p") == 4, "refreshed timer decays from the last eligible gain");
    }

    private static void distanceControlLossesPreserveSharedExpiry() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 1));
        state.addDistanceControl("p", 3, 1_000L, 5_000L);

        CombatPerkTransitionPolicy.onConfirmedMiss("p", WeaponFamily.SPEAR, ranks, state, 3_000L);
        require(state.distanceControl("p", 3_000L) == 2, "confirmed spear miss removes one Distance Control");
        CombatPerkTransitionPolicy.onConfirmedHeavyImpact("p", true, ranks, state, 4_000L);
        require(state.distanceControl("p", 4_000L) == 1, "hostile heavy impact removes one Distance Control");
        require(state.distanceControl("p", 5_999L) == 1, "losses do not refresh or shorten shared TTL");
        require(state.distanceControl("p", 6_000L) == 0, "rank-one Distance Control expires five seconds after last gain");
    }

    private static void flowUsesTwoPointFiveSecondDodgeWindowAndAtMostOneGain() {
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2));
        var state = new NotionCombatPerkState();
        CombatPerkDefensePolicy.onSuccessfulDodge("p", WeaponFamily.DAGGER, ranks, state, 80, 1_000L);
        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 3_499L),
            "A0022 dodge window lasts 2.5 seconds");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 3_500L),
            "A0022 dodge window expires exactly at 2.5 seconds");

        state.setActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 5_000L);
        state.armFlowReposition("p", "mob", 5_000L);
        boolean gained = CombatPerkTransitionPolicy.consumeFlowOpportunity(
            CanonicalActionIdentity.root("p", "dagger-hit", "test"),
            "p", "mob", WeaponFamily.DAGGER, true, true, ranks, state, 3_000L
        );
        require(gained, "one direct dagger hit consumes a valid dodge/reposition opportunity");
        require(state.flow("p", 3_000L) == 1, "simultaneous dodge and reposition still grant only one Flow");
        require(!CombatPerkTransitionPolicy.consumeFlowOpportunity(
            CanonicalActionIdentity.root("p", "dagger-hit-2", "test"),
            "p", "mob", WeaponFamily.DAGGER, true, true, ranks, state, 3_001L
        ), "consumed movement opportunities cannot grant another Flow");
    }

    private static void flowPositionalFallbackRequiresRealDisplacementAndSixtyDegrees() {
        var state = new NotionCombatPerkState();
        long start = 10_000L;
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample(
            "p", "mob", 2.0D, 0.0D, 0.0D, 0.0D, true, state, start
        ), "first sample only establishes a positional baseline");
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample(
            "p", "mob", 1.5D, 0.5D, 0.0D, 0.0D, true, state, start + 1_000L
        ), "sub-1.5-block displacement cannot arm Flow");
        require(CombatPerkTransitionPolicy.recordFlowPositionSample(
            "p", "mob", 0.0D, 2.0D, 0.0D, 0.0D, true, state, start + 2_000L
        ), "real >=1.5-block movement plus >=60-degree target-relative change arms Flow");
        require(state.hasFlowReposition("p", "mob", start + 4_499L), "positional opportunity lasts 2.5 seconds");
        require(!state.hasFlowReposition("p", "mob", start + 4_500L), "positional opportunity expires at 2.5 seconds");

        var forced = new NotionCombatPerkState();
        CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 2, 0, 0, 0, true, forced, start);
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 0, 2, 0, 0, false, forced, start + 500L),
            "untrusted/forced movement can never arm positional Flow");
    }

    private static void flowStationaryDecayStartsAfterThreeSecondsAndIsActorLocal() {
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2));
        var state = new NotionCombatPerkState();
        state.addFlow("p", 4, 1_000L, 20_000L);
        state.addFlow("q", 2, 1_000L, 20_000L);

        CombatPerkTransitionPolicy.tick("p", ranks, state, true, true, 1_000L);
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 3_999L);
        require(state.flow("p", 3_999L) == 4, "stationary Flow does not decay before three seconds");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 4_000L);
        require(state.flow("p", 4_000L) == 3, "stationary Flow loses one after three seconds");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 5_000L);
        require(state.flow("p", 5_000L) == 2, "stationary Flow then loses one per second");
        require(state.flow("q", 5_000L) == 2, "one player's stationary decay never mutates another player");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, true, 5_100L);
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 8_099L);
        require(state.flow("p", 8_099L) == 2, "relevant movement resets stationary timer");
    }

    private static void heavyImpactUsesFrozenLossesIncludingFocus() {
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1, "A0016", 2, "A0022", 2, "A0046", 2));
        var state = new NotionCombatPerkState();
        state.addMomentum("p", 5, 1_000L);
        state.addDistanceControl("p", 3, 1_000L, 7_000L);
        state.addFlow("p", 4, 1_000L, 7_000L);
        state.addFocus("p", 100.0D, 1_000L);

        require(!CombatPerkTransitionPolicy.onConfirmedHeavyImpact("p", false, ranks, state, 2_000L),
            "non-hostile stun source cannot trigger heavy-impact losses");
        require(CombatPerkTransitionPolicy.onConfirmedHeavyImpact("p", true, ranks, state, 2_000L),
            "provider-confirmed hostile heavy impact triggers frozen losses");
        require(state.momentum("p") == 3, "A0004 loses two Momentum");
        require(state.distanceControl("p", 2_000L) == 2, "A0016 loses one Distance Control");
        require(state.flow("p", 2_000L) == 2, "A0022 loses two Flow");
        require(close(state.focus("p"), 75.0D), "A0046 loses exactly 25 Focus");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
