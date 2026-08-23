package dev.gustavopere.rpgskilltree.core;

public final class EpicFightWeaponCategoryTest {
    public static void main(String[] args) {
        require(EpicFightWeaponCategory.normalize("SWORD").equals("sword"), "base category");
        require(EpicFightWeaponCategory.normalize("LONG SWORD").equals("long_sword"), "spaces");
        require(EpicFightWeaponCategory.normalize("addon:SPEAR").equals("addon/spear"), "addon namespace");
        require(EpicFightWeaponCategory.normalize("  TACHI  ").equals("tachi"), "trim");
        System.out.println("EpicFightWeaponCategoryTest PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
