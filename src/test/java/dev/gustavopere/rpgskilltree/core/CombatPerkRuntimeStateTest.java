package dev.gustavopere.rpgskilltree.core;

public final class CombatPerkRuntimeStateTest {
    public static void main(String[] args) {
        targetCountersAreCappedConsumedAndExpired();
        targetFlagsAndCooldownsAreIndependent();
        lastTargetTrackingDetectsAxeSwitches();
        actorWindowsExpireAndConsumeOnce();
        System.out.println("CombatPerkRuntimeStateTest: PASS");
    }

    private static void targetCountersAreCappedConsumedAndExpired() {
        var state = new NotionCombatPerkState();
        state.addTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 4, 3, 1000L, 6000L);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 2000L) == 3, "shock cap");
        state.consumeTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 2, 2000L);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 2000L) == 1, "shock consume");
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 8000L) == 0, "shock expiry");

        state.addTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 2, 3, 9000L, 8000L);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 10000L) == 2, "trauma separate from shock");
    }

    private static void targetFlagsAndCooldownsAreIndependent() {
        var state = new NotionCombatPerkState();
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 9000L);
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MATURE, 9000L);
        require(state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 2000L), "mark active");
        require(state.consumeTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MATURE, 2000L), "mature consume");
        require(!state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MATURE, 2000L), "mature consumed once");
        require(state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 2000L), "mark remains independent");

        require(state.cooldownReady("p", "mob", "guard_opening", 1000L), "cooldown initially ready");
        state.startCooldown("p", "mob", "guard_opening", 1000L, 6000L);
        require(!state.cooldownReady("p", "mob", "guard_opening", 6999L), "cooldown active");
        require(state.cooldownReady("p", "mob", "guard_opening", 7000L), "cooldown expires");
    }

    private static void lastTargetTrackingDetectsAxeSwitches() {
        var state = new NotionCombatPerkState();
        require(!state.recordTargetAndWasDifferent("p", "a"), "first target is not a switch");
        require(!state.recordTargetAndWasDifferent("p", "a"), "same target is not a switch");
        require(state.recordTargetAndWasDifferent("p", "b"), "different target is a switch");
    }

    private static void actorWindowsExpireAndConsumeOnce() {
        var state = new NotionCombatPerkState();
        state.setActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 4000L);
        require(state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 3000L), "actor flag active");
        require(state.consumeActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 3000L), "actor flag consumed");
        require(!state.consumeActorFlag("p", NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, 3000L), "actor flag one-shot");

        state.setActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 5000L);
        require(!state.hasActorFlag("p", NotionCombatPerkState.ActorFlag.SHADOW_DANCE, 5001L), "actor flag expires");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
