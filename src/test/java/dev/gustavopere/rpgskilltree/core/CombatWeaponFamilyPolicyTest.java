package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;

public final class CombatWeaponFamilyPolicyTest {
    public static void main(String[] args) {
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("sword").orElseThrow() == WeaponFamily.SWORD, "sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("UCHIGATANA").orElseThrow() == WeaponFamily.SWORD, "uchigatana parent sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("tachi").orElseThrow() == WeaponFamily.SWORD, "tachi parent sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("longsword").orElseThrow() == WeaponFamily.SWORD, "longsword parent sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("axe").orElseThrow() == WeaponFamily.AXE, "axe");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("spear").orElseThrow() == WeaponFamily.SPEAR, "spear");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("dagger").orElseThrow() == WeaponFamily.DAGGER, "dagger");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("ranged").isEmpty(), "ranged does not distinguish bow from crossbow");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("greatsword").isEmpty(), "greatsword is outside A0001-A0050");

        require(CombatWeaponFamilyPolicy.fromFallbackTag("rpgskilltree:hammers").orElseThrow() == WeaponFamily.HAMMER, "hammer tag");
        require(CombatWeaponFamilyPolicy.fromFallbackTag("rpgskilltree:maces").orElseThrow() == WeaponFamily.MACE, "mace tag");
        require(CombatWeaponFamilyPolicy.fromFallbackTag("rpgskilltree:scythes").orElseThrow() == WeaponFamily.SCYTHE, "scythe tag");
        require(CombatWeaponFamilyPolicy.fromFallbackTag("rpgskilltree:bows").orElseThrow() == WeaponFamily.BOW, "bow tag");
        require(CombatWeaponFamilyPolicy.fromFallbackTag("rpgskilltree:crossbows").orElseThrow() == WeaponFamily.CROSSBOW, "crossbow tag");
        require(CombatWeaponFamilyPolicy.fromFallbackTag("minecraft:swords").isEmpty(), "foreign tags are not guessed");

        System.out.println("CombatWeaponFamilyPolicyTest: PASS");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
