package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class FrozenA0010FuryPolicyTest {
    public static void main(String[] args) {
        frozenBaseGainAndTargetSwitchValues();
        duplicateAndDerivedCallbacksCannotGrantAgain();
        System.out.println("FrozenA0010FuryPolicyTest: PASS");
    }

    private static void frozenBaseGainAndTargetSwitchValues() {
        var rankOneState = new NotionCombatPerkState();
        var rankOne = CombatPerkRanks.of(Map.of("A0010", 1));
        CombatPerkAttackPolicy.afterConfirmedHit(context("r1-a", "mob-a", 1_000L), rankOne, rankOneState);
        require(close(rankOneState.fury("p"), 8.8D), "A0010 R1 normal hit must grant 8.8 Fury");
        CombatPerkAttackPolicy.afterConfirmedHit(context("r1-b", "mob-b", 2_000L), rankOne, rankOneState);
        require(close(rankOneState.fury("p"), 22.0D), "A0010 R1 legitimate target switch must add 13.2 Fury");

        var rankTwoState = new NotionCombatPerkState();
        var rankTwo = CombatPerkRanks.of(Map.of("A0010", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(context("r2-a", "mob-a", 1_000L), rankTwo, rankTwoState);
        require(close(rankTwoState.fury("p"), 9.6D), "A0010 R2 normal hit must grant 9.6 Fury");
        CombatPerkAttackPolicy.afterConfirmedHit(context("r2-b", "mob-b", 2_000L), rankTwo, rankTwoState);
        require(close(rankTwoState.fury("p"), 24.0D), "A0010 R2 legitimate target switch must add 14.4 Fury");

        rankTwoState.addFury("p", 90.0D, 2_500L);
        CombatPerkAttackPolicy.afterConfirmedHit(context("r2-cap", "mob-b", 3_000L), rankTwo, rankTwoState);
        require(close(rankTwoState.fury("p"), 100.0D), "A0010 must clamp Fury at 100");
    }

    private static void duplicateAndDerivedCallbacksCannotGrantAgain() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0010", 1));
        var root = CanonicalActionIdentity.root("p", "axe-root", "epicfight:damage_post");
        var hit = context(root, "mob-a", 1_000L);
        CombatPerkAttackPolicy.afterConfirmedHit(hit, ranks, state);
        require(close(state.fury("p"), 8.8D), "root offensive action grants once");

        CombatPerkAttackPolicy.afterConfirmedHit(
            hit.withAction(root.withSource("epicfight:damage_post_duplicate")), ranks, state);
        require(close(state.fury("p"), 8.8D), "duplicate callback cannot grant Fury twice");

        var derived = context(root.child("rpgskilltree:proc"), "mob-b", 2_000L);
        CombatPerkAttackPolicy.afterConfirmedHit(derived, ranks, state);
        require(close(state.fury("p"), 8.8D), "derived/proc action cannot grant Fury");
    }

    private static CombatPerkAttackPolicy.AttackContext context(String actionId, String targetId, long nowMillis) {
        return context(CanonicalActionIdentity.root("p", actionId, "epicfight:damage_post"), targetId, nowMillis);
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        CanonicalActionIdentity action,
        String targetId,
        long nowMillis
    ) {
        return new CombatPerkAttackPolicy.AttackContext(
            action,
            "p",
            targetId,
            WeaponFamily.AXE,
            true,
            true,
            false,
            false,
            false,
            false,
            false,
            false,
            1.0D,
            false,
            0.0D,
            nowMillis
        );
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
