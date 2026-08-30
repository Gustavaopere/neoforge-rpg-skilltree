package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;

public final class A0021A0040MasteryPolicyTest {
    private A0021A0040MasteryPolicyTest() {}

    public static void main(String[] args) {
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.HAMMER).orElseThrow().equals("epicfight:heavy"), "hammer mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.MACE).orElseThrow().equals("combat:mace"), "mace mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.SCYTHE).orElseThrow().equals("combat:scythe"), "scythe mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.DAGGER).isEmpty(), "dagger remains provider-owned");

        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.HAMMER, true, true, 4.0D, "hit-1").isEmpty(),
            "hammer repeatable hit mastery is disabled");
        var hammerDiscovery = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", true);
        require(hammerDiscovery.size() == 1, "one finite hammer discovery award");
        require(hammerDiscovery.getFirst().laneId().equals("epicfight:heavy"), "hammer lane");
        require(hammerDiscovery.getFirst().experience() == 10, "hammer discovery gives ten XP");
        require(A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            WeaponFamily.HAMMER, true, true, 4.0D, "minecraft:zombie", false).isEmpty(), "repeat type rejected");

        var mace = A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.MACE, true, true, 4.0D, "hit-2");
        require(mace.size() == 1 && mace.getFirst().experience() == 3, "future mace lot remains untouched");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.MACE, false, true, 4.0D, "hit-3").isEmpty(), "indirect rejected");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.SCYTHE, true, true, 0.0D, "hit-4").isEmpty(), "zero damage rejected");
        System.out.println("A0021A0040MasteryPolicyTest: PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
