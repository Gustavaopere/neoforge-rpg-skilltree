package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** End-to-end pure contract for frozen A0016 Distance Control semantics. */
public final class FrozenA0016DistanceControlCycleTest {
    public static void main(String[] args) {
        gainRequiresSeventyToOneHundredPercentEffectiveReach();
        gainCapsAtThreeRefreshesSharedTtlAndSurvivesTargetSwitch();
        confirmedMissAndHeavyLoseOneWithoutRefreshingTtl();
        duplicateCallbacksCannotDoubleGainOrDoubleMiss();
        a0018HasPriorityOverA0017();
        multiplayerStateIsActorLocal();
        System.out.println("FrozenA0016DistanceControlCycleTest: PASS");
    }

    private static void gainRequiresSeventyToOneHundredPercentEffectiveReach() {
        require(CombatPositionPolicy.isIdealSpearRange(2.8D, 3.0D, 1.0D), "70% boundary is eligible");
        require(CombatPositionPolicy.isIdealSpearRange(4.0D, 3.0D, 1.0D), "100% boundary is eligible");
        require(!CombatPositionPolicy.isIdealSpearRange(2.799D, 3.0D, 1.0D), "below 70% cannot generate Distance Control");
        require(!CombatPositionPolicy.isIdealSpearRange(4.001D, 3.0D, 1.0D), "beyond effective reach cannot generate Distance Control");

        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 1));
        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "near", "near-hit", false, false, 1_000L), ranks, state);
        require(state.distanceControl("p", 1_000L) == 0, "non-ideal confirmed spear hit grants zero stacks");
        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "ideal", "ideal-hit", true, false, 1_100L), ranks, state);
        require(state.distanceControl("p", 1_100L) == 1, "ideal confirmed spear hit grants exactly one stack");
    }

    private static void gainCapsAtThreeRefreshesSharedTtlAndSurvivesTargetSwitch() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "mob-a", "a1", true, false, 1_000L), ranks, state);
        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "mob-b", "b1", true, false, 2_000L), ranks, state);
        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "mob-c", "c1", true, false, 3_000L), ranks, state);
        require(state.distanceControl("p", 3_000L) == 3, "Distance Control belongs to the player across target switches and caps at three");

        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "mob-d", "d1", true, false, 4_000L), ranks, state);
        require(state.distanceControl("p", 4_000L) == 3, "eligible gain at cap cannot exceed three");
        require(state.distanceControl("p", 10_999L) == 3, "eligible gain at cap still refreshes rank-two shared TTL");
        require(state.distanceControl("p", 11_000L) == 0, "rank-two state expires seven seconds after last eligible gain");
    }

    private static void confirmedMissAndHeavyLoseOneWithoutRefreshingTtl() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 1));
        state.addDistanceControl("p", 3, 1_000L, 5_000L);
        require(CombatPerkTransitionPolicy.onConfirmedMiss("p", WeaponFamily.SPEAR, ranks, state, 2_000L),
            "confirmed spear miss removes one stack");
        require(state.distanceControl("p", 2_000L) == 2, "miss loss is exactly one");
        require(CombatPerkTransitionPolicy.applyA0016ConfirmedHeavyImpact("p", ranks, state, 3_000L),
            "confirmed heavy impact removes one stack");
        require(state.distanceControl("p", 3_000L) == 1, "heavy loss is exactly one");
        require(state.distanceControl("p", 5_999L) == 1, "losses preserve original shared expiry");
        require(state.distanceControl("p", 6_000L) == 0, "losses never refresh rank-one TTL");
    }

    private static void duplicateCallbacksCannotDoubleGainOrDoubleMiss() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 1));
        CanonicalActionIdentity hit = CanonicalActionIdentity.root("p", "same-hit", "test");
        CombatPerkAttackPolicy.AttackContext hitContext = context(hit, "p", "mob", true, false, 1_000L);
        CombatPerkAttackPolicy.afterConfirmedHit(hitContext, ranks, state);
        CombatPerkAttackPolicy.afterConfirmedHit(hitContext, ranks, state);
        require(state.distanceControl("p", 1_000L) == 1, "duplicate confirmed-hit callback cannot grant a second stack");

        state.addDistanceControl("p", 2, 1_100L, 5_000L);
        CanonicalActionIdentity miss = CanonicalActionIdentity.root("p", "same-miss", "test");
        require(CombatPerkTransitionPolicy.onConfirmedMiss(miss, "p", WeaponFamily.SPEAR, ranks, state, 2_000L),
            "first canonical miss applies");
        require(!CombatPerkTransitionPolicy.onConfirmedMiss(miss.withSource("duplicate"), "p", WeaponFamily.SPEAR, ranks, state, 2_000L),
            "duplicate callback for same canonical miss is rejected");
        require(state.distanceControl("p", 2_000L) == 2, "duplicate miss cannot remove a second stack");
    }

    private static void a0018HasPriorityOverA0017() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2, "A0017", 2, "A0018", 1));
        state.addDistanceControl("p", 3, 1_000L, 7_000L);
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.INTERCEPTION_WINDOW, 5_000L);

        CombatPerkAttackPolicy.HitModifiers result = CombatPerkAttackPolicy.beforeHit(
            context("p", "mob", "priority", true, true, 2_000L), ranks, state
        );
        require(state.distanceControl("p", 2_000L) == 0, "A0018 consumes its three-stack mastery cost before A0017 can consume one");
        require(close(result.damageMultiplier(), 1.15D), "A0018 applies its mastery damage modifier");
        require(close(result.impactMultiplier(), 1.40D), "A0018 impact replaces the lower A0017 pressure path");
        require(close(result.guardPressureMultiplier(), 1.40D), "A0018 guard pressure has priority over A0017");
    }

    private static void multiplayerStateIsActorLocal() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(context("p", "mob", "p-hit", true, false, 1_000L), ranks, state);
        CombatPerkAttackPolicy.afterConfirmedHit(context("q", "mob", "q-hit", true, false, 1_000L), ranks, state);
        CombatPerkAttackPolicy.afterConfirmedHit(context("q", "mob2", "q-hit2", true, false, 1_100L), ranks, state);
        require(state.distanceControl("p", 1_100L) == 1, "player p keeps only its own stack");
        require(state.distanceControl("q", 1_100L) == 2, "player q owns its independent Distance Control state");
        CombatPerkTransitionPolicy.applyA0016ConfirmedHeavyImpact("p", ranks, state, 1_200L);
        require(state.distanceControl("p", 1_200L) == 0, "heavy loss mutates only the struck actor");
        require(state.distanceControl("q", 1_200L) == 2, "player q is untouched by player p heavy loss");
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        String actor, String target, String actionId, boolean idealRange, boolean advancing, long nowMillis
    ) {
        return context(CanonicalActionIdentity.root(actor, actionId, "test"), actor, target, idealRange, advancing, nowMillis);
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        CanonicalActionIdentity action, String actor, String target, boolean idealRange, boolean advancing, long nowMillis
    ) {
        return new CombatPerkAttackPolicy.AttackContext(
            action, actor, target, WeaponFamily.SPEAR,
            true, true, false, false, idealRange, advancing, false, false,
            1.0D, false, 0.0D, nowMillis
        );
    }

    private static boolean close(double a, double b) {
        return Math.abs(a - b) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
