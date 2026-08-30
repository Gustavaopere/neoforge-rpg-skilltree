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

        CombatAction repeatedHit = new CombatAction(
            new ActionOrigin("epicfight:damage_post", 0), "epicfight", "sword", "weapon_hit",
            java.util.Set.of("hit"), 8.0D);
        check(MasteryPolicies.forEpicFight(repeatedHit).isEmpty(),
            "ordinary repeated weapon hits must not farm mastery");

        CombatAction hitMilestone = new CombatAction(
            new ActionOrigin("epicfight:damage_post", 0), "epicfight", "sword", "weapon_hit:zombie",
            java.util.Set.of("hit", "milestone"), 8.0D);
        var hitAwards = MasteryPolicies.forEpicFight(hitMilestone);
        check(hitAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:weapon") && a.experience() == 5),
            "weapon milestone must award bounded generic weapon mastery");
        check(hitAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:sword") && a.experience() == 10),
            "weapon milestone must award ten category mastery");

        CombatAction guard = new CombatAction(
            new ActionOrigin("epicfight:skill_consume", 0), "epicfight", "skill", "epicfight:guard",
            java.util.Set.of("skill", "stamina", "guard", "milestone"), 4.0D);
        var guardAwards = MasteryPolicies.forEpicFight(guard);
        check(guardAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:guard") && a.experience() == 10),
            "each deduplicated hostile-type guard milestone must award ten guard mastery");
        check(guardAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:stamina")),
            "valid guard milestones should also train stamina mastery");
        check(6 * 10 >= 60, "six finite hostile-type guard discoveries must reach the specialization gate");
        check(8 * 10 >= 80, "eight finite hostile-type guard discoveries must reach the deeper guard gate");

        CombatAction dodge = new CombatAction(
            new ActionOrigin("epicfight:dodge_success", 0), "epicfight", "dodge", "successful_dodge",
            java.util.Set.of("dodge_success", "milestone"), 0.0D);
        var dodgeAwards = MasteryPolicies.forEpicFight(dodge);
        check(dodgeAwards.stream().anyMatch(a -> a.laneId().equals("epicfight:dodge")), "first successful dodge milestone should train dodge mastery");
        check(dodgeAwards.stream().anyMatch(a -> a.laneId().equals("agility:practice")), "first successful dodge milestone should feed unified agility practice");

        CombatAction proc = new CombatAction(
            new ActionOrigin("epicfight:skill_consume", 1), "epicfight", "skill", "addon:proc",
            java.util.Set.of("skill", "stamina", "milestone"), 3.0D);
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