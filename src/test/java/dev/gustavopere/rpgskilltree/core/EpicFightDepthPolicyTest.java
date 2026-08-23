package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class EpicFightDepthPolicyTest {
    public static void main(String[] args) {
        PassiveNodeProgress empty = PassiveNodeProgress.empty();
        checkClose(10.0F, EpicFightStaminaPolicy.adjustedCost(empty, "DODGE", 10.0F), "empty tree must not discount stamina");

        PassiveNodeProgress martial = PassiveNodeProgress.of(Map.of("rpgskilltree:martial_000", 1));
        checkClose(9.5F, EpicFightStaminaPolicy.adjustedCost(martial, "GUARD", 10.0F), "martial entry should discount native stamina costs");

        PassiveNodeProgress agility = PassiveNodeProgress.of(Map.of("rpgskilltree:agility_000", 1));
        checkClose(9.0F, EpicFightStaminaPolicy.adjustedCost(agility, "DODGE", 10.0F), "agility entry should discount dodge stamina");
        checkClose(10.0F, EpicFightStaminaPolicy.adjustedCost(agility, "GUARD", 10.0F), "agility discount must not leak into guard");

        PassiveNodeProgress hybrid = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:martial_000", 1,
            "rpgskilltree:martial_036", 3,
            "rpgskilltree:agility_000", 1,
            "rpgskilltree:agility_036", 3
        ));
        checkClose(7.0F, EpicFightStaminaPolicy.adjustedCost(hybrid, "MOVER", 10.0F), "deep martial/agility investment should stack but remain bounded");

        CombatAction hit = new CombatAction(new ActionOrigin("epicfight:damage_post", 0), "epicfight", "sword", "weapon_hit", java.util.Set.of("hit"), 8.0D);
        var hitAwards = MasteryPolicies.forEpicFight(hit);
        check(hitAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:weapon")), "weapon hit mastery must remain intact");
        check(hitAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:sword")), "weapon category mastery must remain intact");

        CombatAction guard = new CombatAction(new ActionOrigin("epicfight:skill_consume", 0), "epicfight", "skill", "epicfight:guard", java.util.Set.of("skill", "stamina", "guard"), 4.0D);
        var guardAwards = MasteryPolicies.forEpicFight(guard);
        check(guardAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:guard")), "guard stamina use should train guard mastery");
        check(guardAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:stamina")), "real stamina expenditure should train stamina mastery");

        CombatAction dodge = new CombatAction(new ActionOrigin("epicfight:dodge_success", 0), "epicfight", "dodge", "successful_dodge", java.util.Set.of("dodge_success"), 0.0D);
        var dodgeAwards = MasteryPolicies.forEpicFight(dodge);
        check(dodgeAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:dodge")), "successful dodge should train dodge mastery");
        check(dodgeAwards.stream().anyMatch(a -> a.laneId().equals("agility:practice")), "successful dodge should feed unified agility practice");

        CombatAction proc = new CombatAction(new ActionOrigin("epicfight:skill_consume", 1), "epicfight", "skill", "addon:proc", java.util.Set.of("skill", "stamina"), 3.0D);
        check(MasteryPolicies.forEpicFight(proc).isEmpty(), "proc-depth actions must not produce mastery loops");

        System.out.println("EpicFightDepthPolicyTest PASS");
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static void checkClose(float expected, float actual, String message) {
        if (Math.abs(expected - actual) > 0.0001F) {
            throw new AssertionError(message + ": expected=" + expected + " actual=" + actual);
        }
    }
}
