package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** Full frozen contract for A0022 Flow, including forced-movement fail-closed semantics. */
public final class FrozenA0022FlowCycleTest {
    public static void main(String[] args) {
        validDodgeAndA0024UseIndependentWindows();
        positionalFallbackRequiresDistanceAngleAndTrustedMovement();
        knockbackOrTeleportQuarantineInvalidatesPositionalOpportunity();
        gainCapsAtFourAndRefreshesFiveOrSevenSecondTtl();
        heavyAndStationaryLossesPreserveTtl();
        duplicateHitCallbackCannotDoubleGain();
        lifecycleAndMultiplayerRemainActorLocal();
        System.out.println("FrozenA0022FlowCycleTest: PASS");
    }

    private static void validDodgeAndA0024UseIndependentWindows() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2, "A0024", 1));
        CombatPerkDefensePolicy.onSuccessfulDodge("p", WeaponFamily.DAGGER, ranks, state, 80, 1_000L);
        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.RECENT_DODGE, 2_999L), "A0024 token remains two seconds");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.RECENT_DODGE, 3_000L), "A0024 token expires independently");
        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 3_499L), "A0022 dodge window lasts 2.5 seconds");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 3_500L), "A0022 dodge window expires at 2.5 seconds");
    }

    private static void positionalFallbackRequiresDistanceAngleAndTrustedMovement() {
        var state = new NotionCombatPerkState();
        long start = 10_000L;
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 2.0D, 0.0D, 0.0D, 0.0D, true, state, start),
            "first trusted sample establishes baseline only");
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 0.8D, 0.0D, 0.0D, 0.0D, true, state, start + 500L),
            "less than 1.5 blocks cannot arm Flow");
        require(CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 0.0D, 2.0D, 0.0D, 0.0D, true, state, start + 1_000L),
            "at least 1.5 blocks plus at least 60 degrees arms positional Flow");

        var untrusted = new NotionCombatPerkState();
        CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 2, 0, 0, 0, true, untrusted, start);
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 0, 2, 0, 0, false, untrusted, start + 500L),
            "forced/untrusted movement cannot arm Flow");
        require(!untrusted.hasFlowReposition("p", "mob", start + 500L), "untrusted movement clears positional opportunity");
    }

    private static void knockbackOrTeleportQuarantineInvalidatesPositionalOpportunity() {
        var state = new NotionCombatPerkState();
        long start = 20_000L;
        CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 2, 0, 0, 0, true, state, start);
        state.blockFlowReposition("p", start + 250L, CombatPerkTransitionPolicy.FLOW_WINDOW_MILLIS);
        require(state.flowRepositionBlocked("p", start + 2_749L), "forced movement quarantine covers the full 2.5-second Flow window");
        require(!CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 0, 2, 0, 0, true, state, start + 500L),
            "even geometrically valid movement is rejected while knockback/teleport quarantine is active");
        require(!state.hasFlowReposition("p", "mob", start + 500L), "forced movement cannot leave a stale positional token");
        require(!state.flowRepositionBlocked("p", start + 2_750L), "quarantine expires exactly at its deadline");

        require(!CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 2, 0, 0, 0, true, state, start + 2_750L),
            "first post-quarantine movement creates a fresh baseline");
        require(CombatPerkTransitionPolicy.recordFlowPositionSample("p", "mob", 0, 2, 0, 0, true, state, start + 3_250L),
            "fresh trusted movement after quarantine can arm Flow normally");
    }

    private static void gainCapsAtFourAndRefreshesFiveOrSevenSecondTtl() {
        var rankOne = new NotionCombatPerkState();
        var r1 = CombatPerkRanks.of(Map.of("A0022", 1));
        for (int i = 0; i < 5; i++) {
            long now = 1_000L + i * 100L;
            rankOne.setActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, now + 2_500L);
            CombatPerkTransitionPolicy.consumeFlowOpportunity(
                CanonicalActionIdentity.root("p", "r1-" + i, "test"), "p", "mob", WeaponFamily.DAGGER,
                true, true, r1, rankOne, now
            );
        }
        require(rankOne.flow("p", 1_400L) == 4, "Flow caps at four");
        require(rankOne.flow("p", 6_399L) == 4, "rank-one last eligible gain refreshes five-second TTL even at cap");
        require(rankOne.flow("p", 6_400L) == 0, "rank-one Flow expires five seconds after last gain");

        var rankTwo = new NotionCombatPerkState();
        var r2 = CombatPerkRanks.of(Map.of("A0022", 2));
        rankTwo.setActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 5_000L);
        require(CombatPerkTransitionPolicy.consumeFlowOpportunity(
            CanonicalActionIdentity.root("p", "r2", "test"), "p", "mob", WeaponFamily.DAGGER,
            true, true, r2, rankTwo, 2_000L), "rank-two opportunity grants Flow");
        require(rankTwo.flow("p", 8_999L) == 1, "rank-two Flow lasts seven seconds");
        require(rankTwo.flow("p", 9_000L) == 0, "rank-two Flow expires exactly at seven seconds");
    }

    private static void heavyAndStationaryLossesPreserveTtl() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2));
        state.addFlow("p", 4, 1_000L, 7_000L);
        require(CombatPerkTransitionPolicy.applyA0022ConfirmedHeavyImpact("p", ranks, state, 2_000L), "heavy receipt removes Flow");
        require(state.flow("p", 2_000L) == 2, "heavy removes exactly two Flow");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, true, 2_100L);
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 5_099L);
        require(state.flow("p", 5_099L) == 2, "stationary loss waits full three seconds");
        CombatPerkTransitionPolicy.tick("p", ranks, state, true, false, 5_100L);
        require(state.flow("p", 5_100L) == 1, "after three seconds stationary Flow loses one per second");
        require(state.flow("p", 7_999L) == 1, "heavy/stationary losses never refresh shared TTL");
        require(state.flow("p", 8_000L) == 0, "original rank-two TTL remains authoritative");
    }

    private static void duplicateHitCallbackCannotDoubleGain() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 1));
        state.setActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 5_000L);
        CanonicalActionIdentity action = CanonicalActionIdentity.root("p", "same-hit", "test");
        require(CombatPerkTransitionPolicy.consumeFlowOpportunity(action, "p", "mob", WeaponFamily.DAGGER, true, true, ranks, state, 2_000L),
            "first confirmed hit consumes Flow opportunity");
        state.setActorFlag("p", NotionCombatPerkState.ActorFlag.FLOW_DODGE_WINDOW, 5_000L);
        require(!CombatPerkTransitionPolicy.consumeFlowOpportunity(action.withSource("duplicate"), "p", "mob", WeaponFamily.DAGGER, true, true, ranks, state, 2_001L),
            "duplicate callback for same canonical hit cannot grant a second Flow");
        require(state.flow("p", 2_001L) == 1, "duplicate callback leaves Flow unchanged");
    }

    private static void lifecycleAndMultiplayerRemainActorLocal() {
        var state = new NotionCombatPerkState();
        state.addFlow("p", 4, 1_000L, 7_000L);
        state.addFlow("q", 2, 1_000L, 7_000L);
        state.blockFlowReposition("p", 1_000L, 2_500L);
        state.clearTransientPreservingGuards("p");
        require(state.flow("p", 1_100L) == 0, "Flow clears on death/respawn/dimension transient lifecycle");
        require(state.flow("q", 1_100L) == 2, "another player's Flow remains isolated");
        require(!state.flowRepositionBlocked("p", 1_100L), "movement quarantine is transient entity-bound state and clears with lifecycle");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
