package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class CombatPerkDefensePolicyTest {
    public static void main(String[] args) {
        swordDodgeBuildsMomentumAndArmsRiposte();
        riposteUsesGlobalCooldown();
        daggerDodgeOpensRecentDodgeWindow();
        daggerCapstoneConsumesFlowOnNextAttack();
        shadowDanceWindowScalesWithMastery();
        System.out.println("CombatPerkDefensePolicyTest: PASS");
    }

    private static void swordDodgeBuildsMomentumAndArmsRiposte() {
        var state = new NotionCombatPerkState();
        state.addMomentum("p", 4, 1000L);
        var ranks = CombatPerkRanks.of(Map.of("A0004", 1, "A0006", 1));

        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.SWORD, ranks, state, 80, 2000L);

        require(state.momentum("p") == 5, "technical defense may add one momentum");
        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 4999L), "A0006 riposte window");
        require(!state.cooldownReady("p", "p", "A0006", 11999L), "A0006 base cooldown");
        require(state.cooldownReady("p", "p", "A0006", 12000L), "A0006 cooldown expiry");
    }

    private static void riposteUsesGlobalCooldown() {
        var state = new NotionCombatPerkState();
        state.addMomentum("p", 5, 1000L);
        var ranks = CombatPerkRanks.of(Map.of("A0006", 1));

        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.SWORD, ranks, state, 100, 2000L);
        require(!state.cooldownReady("p", "p", "A0006", 9999L), "mastery100 reduces cooldown to eight seconds");
        require(state.cooldownReady("p", "p", "A0006", 10000L), "mastery100 cooldown expiry");

        require(state.consumeActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 2500L), "consume first riposte");
        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.SWORD, ranks, state, 100, 3000L);
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 3000L), "cooldown blocks second riposte window");
    }

    private static void daggerDodgeOpensRecentDodgeWindow() {
        var state = new NotionCombatPerkState();
        var ranks = CombatPerkRanks.of(Map.of("A0022", 1));

        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.DAGGER, ranks, state, 60, 1000L);

        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.RECENT_DODGE, 2999L), "A0022 dodge window lasts two seconds");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.RECENT_DODGE, 3000L), "A0022 dodge window expires exactly");
    }

    private static void daggerCapstoneConsumesFlowOnNextAttack() {
        var state = new NotionCombatPerkState();
        state.addFlow("p", 4, 1000L);
        var ranks = CombatPerkRanks.of(Map.of("A0022", 2, "A0024", 1));
        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.DAGGER, ranks, state, 80, 1000L);

        var trigger = daggerContext(2000L, false);
        CombatPerkAttackPolicy.beforeHit(trigger, ranks, state);

        require(state.flow("p") == 0, "A0024 consumes all four flow");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.RECENT_DODGE, 2000L), "A0024 consumes dodge trigger");
        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 5999L), "A0024 shadow dance base window");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 6000L), "A0024 base window expires at four seconds");

        CombatPerkAttackPolicy.afterConfirmedHit(trigger, ranks, state);
        require(state.flow("p") == 0, "capstone trigger cannot regenerate flow from same dodge");

        var flank = daggerContext(3000L, true);
        var result = CombatPerkAttackPolicy.beforeHit(flank, ranks, state);
        require(close(result.damageMultiplier(), 1.15D), "shadow dance first flank damage");
        require(close(result.impactMultiplier(), 1.20D), "shadow dance first flank impact");
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 3000L), "shadow dance benefit consumed once");
    }

    private static void shadowDanceWindowScalesWithMastery() {
        var ranks = CombatPerkRanks.of(Map.of("A0024", 1));

        var mastery90 = new NotionCombatPerkState();
        mastery90.addFlow("p", 4, 0L);
        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.DAGGER, ranks, mastery90, 90, 0L);
        CombatPerkAttackPolicy.beforeHit(daggerContext(1000L, false), ranks, mastery90);
        require(mastery90.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 5499L), "mastery90 gives 4.5 second window");
        require(!mastery90.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 5500L), "mastery90 window expires exactly");

        var mastery100 = new NotionCombatPerkState();
        mastery100.addFlow("p", 4, 0L);
        CombatPerkDefensePolicy.onSuccessfulDodge("p", CombatPerkDefinition.WeaponFamily.DAGGER, ranks, mastery100, 100, 0L);
        CombatPerkAttackPolicy.beforeHit(daggerContext(1000L, false), ranks, mastery100);
        require(mastery100.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 5999L), "mastery100 gives five second window");
        require(!mastery100.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 6000L), "mastery100 window expires exactly");
    }

    private static CombatPerkAttackPolicy.AttackContext daggerContext(long nowMillis, boolean flank) {
        return new CombatPerkAttackPolicy.AttackContext(
            "p", "mob", CombatPerkDefinition.WeaponFamily.DAGGER,
            true, true, false, false, false, false, flank, false,
            1.0D, false, 0.0D, nowMillis
        );
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
