package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;

public final class A0021A0040MasteryPolicyTest {
    private A0021A0040MasteryPolicyTest() {}

    public static void main(String[] args) {
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.HAMMER).orElseThrow().equals("epicfight:heavy"), "hammer mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.MACE).orElseThrow().equals("combat:mace"), "mace mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.SCYTHE).orElseThrow().equals("combat:scythe"), "scythe mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.DAGGER).isEmpty(), "dagger remains provider-owned");

        finiteDiscovery(WeaponFamily.HAMMER, "epicfight:heavy", "minecraft:zombie");
        finiteDiscovery(WeaponFamily.MACE, "combat:mace", "minecraft:skeleton");
        finiteDiscovery(WeaponFamily.SCYTHE, "combat:scythe", "minecraft:creeper");

        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.HAMMER, true, true, 4.0D, "hit-1").isEmpty(),
            "hammer repeatable hit mastery is disabled");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.MACE, true, true, 4.0D, "hit-2").isEmpty(),
            "mace repeatable hit mastery is disabled");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.SCYTHE, true, true, 4.0D, "hit-3").isEmpty(),
            "scythe repeatable hit mastery is disabled");
        require(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.MACE, false, true, 4.0D, "minecraft:zombie", true).isEmpty(), "indirect discovery rejected");
        require(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.SCYTHE, true, true, 0.0D, "minecraft:zombie", true).isEmpty(), "zero damage discovery rejected");
        System.out.println("A0021A0040MasteryPolicyTest: PASS");
    }

    private static void finiteDiscovery(WeaponFamily family, String lane, String entityType) {
        var first = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            family, true, true, 4.0D, entityType, true);
        require(first.size() == 1, family + " grants one finite discovery award");
        require(first.getFirst().laneId().equals(lane), family + " lane");
        require(first.getFirst().experience() == 10, family + " discovery gives ten XP");
        require(A0021A0040MasteryPolicy.discoveryKey(family, entityType).orElseThrow()
            .equals("mastery/" + lane + "/entity_type/" + entityType), family + " discovery key");
        require(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            family, true, true, 4.0D, entityType, false).isEmpty(), family + " repeat type rejected");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
