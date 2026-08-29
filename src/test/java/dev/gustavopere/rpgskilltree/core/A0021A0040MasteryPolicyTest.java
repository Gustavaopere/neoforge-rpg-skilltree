package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;

public final class A0021A0040MasteryPolicyTest {
    private A0021A0040MasteryPolicyTest() {}

    public static void main(String[] args) {
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.HAMMER).orElseThrow().equals("epicfight:heavy"), "hammer mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.MACE).orElseThrow().equals("combat:mace"), "mace mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.SCYTHE).orElseThrow().equals("combat:scythe"), "scythe mastery");
        require(A0021A0040MasteryPolicy.canonicalGateMastery(WeaponFamily.DAGGER).isEmpty(), "dagger remains provider-owned");

        var awards = A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.MACE, true, true, 4.0D, "hit-1");
        require(awards.size() == 1, "one canonical mastery award per confirmed hit");
        require(awards.getFirst().laneId().equals("combat:mace"), "mace key");
        require(awards.getFirst().experience() == 3, "mace award amount follows existing Epic Fight family-hit rate");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.MACE, false, true, 4.0D, "hit-2").isEmpty(), "indirect rejected");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.SCYTHE, true, true, 0.0D, "hit-3").isEmpty(), "zero damage rejected");
        require(A0021A0040MasteryPolicy.forConfirmedDirectHit(WeaponFamily.HAMMER, true, false, 4.0D, "hit-4").isEmpty(), "non-hostile rejected");
        System.out.println("A0021A0040MasteryPolicyTest: PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
