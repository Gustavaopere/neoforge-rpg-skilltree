package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public final class GoetyCommandPolicyTest {
    public static void main(String[] args) {
        GoetyCommandAction single = new GoetyCommandAction(
            new ActionOrigin("goety:command_entity", 0),
            "goety",
            "command_entity",
            "minecraft:zombie",
            Set.of("confirmed_command", "entity_target", "single_command"),
            1
        );
        var singleAwards = MasteryPolicies.forGoetyCommand(single);
        check(experience(singleAwards, "goety:commanding") == 3, "single confirmed command should train Goety commanding");
        check(experience(singleAwards, "summoning:practice") == 2, "single confirmed command should train shared summoning practice");

        GoetyCommandAction group = new GoetyCommandAction(
            new ActionOrigin("goety:order_entity", 0),
            "goety",
            "order_entity",
            "minecraft:skeleton",
            Set.of("confirmed_command", "entity_target", "group_order"),
            8
        );
        var groupAwards = MasteryPolicies.forGoetyCommand(group);
        check(experience(groupAwards, "goety:commanding") == 5, "group command breadth must be capped");
        check(experience(groupAwards, "summoning:practice") == 3, "group command summoning reward must be capped");

        GoetyCommandAction unconfirmed = new GoetyCommandAction(
            new ActionOrigin("goety:command_block", 0),
            "goety",
            "command_block",
            "minecraft:stone",
            Set.of("block_target"),
            1
        );
        check(MasteryPolicies.forGoetyCommand(unconfirmed).isEmpty(), "unconfirmed clicks must never grant command mastery");

        GoetyCommandAction proc = new GoetyCommandAction(
            new ActionOrigin("goety:command_entity", 1),
            "goety",
            "command_entity",
            "minecraft:zombie",
            Set.of("confirmed_command", "entity_target"),
            1
        );
        check(MasteryPolicies.forGoetyCommand(proc).isEmpty(), "derived command callbacks must not create mastery loops");

        System.out.println("GoetyCommandPolicyTest PASS");
    }

    private static int experience(java.util.List<MasteryAward> awards, String lane) {
        return awards.stream().filter(a -> a.laneId().equals(lane)).mapToInt(MasteryAward::experience).sum();
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
