package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class CombatPerkControlPolicyTest {
    public static void main(String[] args) {
        confirmedHammerBreakOpensDemolisherWindow();
        demolisherCooldownScalesWithMastery();
        unrelatedBreaksDoNothing();
        System.out.println("CombatPerkControlPolicyTest: PASS");
    }

    private static void confirmedHammerBreakOpensDemolisherWindow() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0030", 1));

        boolean opened = CombatPerkControlPolicy.onConfirmedPostureBreak(
            "p", "mob", CombatPerkDefinition.WeaponFamily.HAMMER, ranks, state, 80, 1000L
        );

        require(opened, "A0030 should open on confirmed hammer posture break");
        require(state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.DEMOLISH_WINDOW, 4999L), "window lasts four seconds");
        require(!state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.DEMOLISH_WINDOW, 5000L), "window expires exactly");
        require(!state.cooldownReady("p", "mob", "A0030", 12999L), "base cooldown active");
        require(state.cooldownReady("p", "mob", "A0030", 13000L), "base cooldown expires at twelve seconds");

        boolean reopened = CombatPerkControlPolicy.onConfirmedPostureBreak(
            "p", "mob", CombatPerkDefinition.WeaponFamily.HAMMER, ranks, state, 80, 2000L
        );
        require(!reopened, "per-target cooldown prevents reopening");
    }

    private static void demolisherCooldownScalesWithMastery() {
        var ranks = CombatPerkRanks.of(Map.of("A0030", 1));

        var mastery90 = new NotionCombatPerkState();
        require(CombatPerkControlPolicy.onConfirmedPostureBreak(
            "p", "a", CombatPerkDefinition.WeaponFamily.HAMMER, ranks, mastery90, 90, 1000L
        ), "mastery90 opens window");
        require(!mastery90.cooldownReady("p", "a", "A0030", 11999L), "mastery90 eleven-second cooldown active");
        require(mastery90.cooldownReady("p", "a", "A0030", 12000L), "mastery90 cooldown expiry");

        var mastery100 = new NotionCombatPerkState();
        require(CombatPerkControlPolicy.onConfirmedPostureBreak(
            "p", "b", CombatPerkDefinition.WeaponFamily.HAMMER, ranks, mastery100, 100, 1000L
        ), "mastery100 opens window");
        require(!mastery100.cooldownReady("p", "b", "A0030", 10999L), "mastery100 ten-second cooldown active");
        require(mastery100.cooldownReady("p", "b", "A0030", 11000L), "mastery100 cooldown expiry");
    }

    private static void unrelatedBreaksDoNothing() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0030", 1));
        require(!CombatPerkControlPolicy.onConfirmedPostureBreak(
            "p", "mob", CombatPerkDefinition.WeaponFamily.MACE, ranks, state, 100, 1000L
        ), "non-hammer cannot open A0030");
        require(!state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.DEMOLISH_WINDOW, 1000L), "no unrelated window");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
