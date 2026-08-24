package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public final class CombatPerkControlPolicyTest {
    public static void main(String[] args) {
        confirmedHammerBreakOpensDemolisherWindow();
        demolisherCooldownScalesWithMastery();
        unrelatedBreaksDoNothing();
        spearInterceptionWindowRequiresAnInwardRangeCrossing();
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

    private static void spearInterceptionWindowRequiresAnInwardRangeCrossing() {
        var ranks = CombatPerkRanks.of(Map.of("A0018", 1));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("p", 3, 1000L);

        require(!spearRangeUpdate(
            "p", "mob", CombatPerkDefinition.WeaponFamily.SPEAR,
            5.0D, 4.0D, true, ranks, state, 80, 1000L
        ), "first observation outside range only establishes history");
        require(spearRangeUpdate(
            "p", "mob", CombatPerkDefinition.WeaponFamily.SPEAR,
            4.0D, 4.0D, true, ranks, state, 80, 2000L
        ), "outside-to-ideal crossing opens A0018");
        require(
            state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.INTERCEPTION_WINDOW, 4999L),
            "base A0018 window lasts three seconds"
        );
        require(
            !state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.INTERCEPTION_WINDOW, 5000L),
            "base A0018 window expires exactly"
        );
        require(!state.cooldownReady("p", "mob", "A0018", 9999L), "same-target lockout lasts eight seconds");
        require(state.cooldownReady("p", "mob", "A0018", 10000L), "same-target lockout expires exactly");

        var masteryState = new NotionCombatPerkState();
        masteryState.addDistanceControl("p", 3, 1000L);
        require(!spearRangeUpdate(
            "p", "other", CombatPerkDefinition.WeaponFamily.SPEAR,
            5.0D, 4.0D, true, ranks, masteryState, 90, 1000L
        ), "mastery observation establishes history");
        require(spearRangeUpdate(
            "p", "other", CombatPerkDefinition.WeaponFamily.SPEAR,
            3.5D, 4.0D, true, ranks, masteryState, 90, 2000L
        ), "mastery90 crossing opens A0018");
        require(
            masteryState.hasTargetFlag(
                "p", "other", NotionCombatPerkState.TargetFlag.INTERCEPTION_WINDOW, 5499L
            ),
            "mastery90 extends the window to 3.5 seconds"
        );

        var invalid = new NotionCombatPerkState();
        invalid.addDistanceControl("p", 3, 1000L);
        require(!spearRangeUpdate(
            "p", "wrong", CombatPerkDefinition.WeaponFamily.AXE,
            5.0D, 4.0D, true, ranks, invalid, 100, 1000L
        ), "non-spear observation cannot open A0018");
        require(!spearRangeUpdate(
            "p", "wrong", CombatPerkDefinition.WeaponFamily.AXE,
            4.0D, 4.0D, true, ranks, invalid, 100, 2000L
        ), "non-spear crossing cannot open A0018");
    }

    private static boolean spearRangeUpdate(
        String actorId,
        String targetId,
        CombatPerkDefinition.WeaponFamily family,
        double distance,
        double effectiveReach,
        boolean targetAdvancing,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery,
        long nowMillis
    ) {
        try {
            var method = CombatPerkControlPolicy.class.getMethod(
                "onSpearRangeUpdate",
                String.class,
                String.class,
                CombatPerkDefinition.WeaponFamily.class,
                double.class,
                double.class,
                boolean.class,
                CombatPerkRanks.class,
                NotionCombatPerkState.class,
                int.class,
                long.class
            );
            return (boolean) method.invoke(
                null,
                actorId,
                targetId,
                family,
                distance,
                effectiveReach,
                targetAdvancing,
                ranks,
                state,
                weaponMastery,
                nowMillis
            );
        } catch (NoSuchMethodException missingFeature) {
            throw new AssertionError("CombatPerkControlPolicy.onSpearRangeUpdate is missing", missingFeature);
        } catch (IllegalAccessException inaccessible) {
            throw new AssertionError(inaccessible);
        } catch (InvocationTargetException failed) {
            Throwable cause = failed.getCause();
            if (cause instanceof RuntimeException runtime) throw runtime;
            if (cause instanceof Error error) throw error;
            throw new AssertionError(cause);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
