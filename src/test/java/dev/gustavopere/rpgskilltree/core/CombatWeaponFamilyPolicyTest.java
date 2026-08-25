package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Optional;
import java.util.Set;

public final class CombatWeaponFamilyPolicyTest {
    public static void main(String[] args) {
        providerMappingsRemainStable();
        allNineExplicitRpgFamiliesResolve();
        explicitRpgClassificationBeatsProviderFallback();
        conflictingExplicitFamiliesFailClosed();
        System.out.println("CombatWeaponFamilyPolicyTest: PASS");
    }

    private static void providerMappingsRemainStable() {
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("sword").orElseThrow() == WeaponFamily.SWORD, "sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("UCHIGATANA").orElseThrow() == WeaponFamily.SWORD, "uchigatana parent sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("tachi").orElseThrow() == WeaponFamily.SWORD, "tachi parent sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("longsword").orElseThrow() == WeaponFamily.SWORD, "longsword parent sword");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("axe").orElseThrow() == WeaponFamily.AXE, "axe");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("spear").orElseThrow() == WeaponFamily.SPEAR, "spear");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("dagger").orElseThrow() == WeaponFamily.DAGGER, "dagger");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("ranged").isEmpty(), "ranged does not distinguish bow from crossbow");
        require(CombatWeaponFamilyPolicy.fromEpicFightCategory("greatsword").isEmpty(), "greatsword is outside A0001-A0050");
    }

    private static void allNineExplicitRpgFamiliesResolve() {
        String[] tags = {
            "rpgskilltree:swords", "rpgskilltree:axes", "rpgskilltree:spears",
            "rpgskilltree:daggers", "rpgskilltree:hammers", "rpgskilltree:maces",
            "rpgskilltree:scythes", "rpgskilltree:bows", "rpgskilltree:crossbows"
        };
        WeaponFamily[] expected = WeaponFamily.values();
        require(tags.length == expected.length, "test covers exactly nine A0001-A0050 weapon families");
        for (int i = 0; i < tags.length; i++) {
            WeaponFamily explicit = CombatWeaponFamilyPolicy.fromFallbackTag(tags[i]).orElseThrow();
            require(explicit == expected[i], "tag family mismatch for " + tags[i]);
            require(CombatWeaponFamilyPolicy.resolve(Set.of(explicit), Optional.empty()).orElseThrow() == expected[i],
                "explicit family resolves without provider for " + tags[i]);
        }
        require(CombatWeaponFamilyPolicy.fromFallbackTag("minecraft:swords").isEmpty(), "foreign tags are not guessed");
    }

    private static void explicitRpgClassificationBeatsProviderFallback() {
        require(CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.MACE), Optional.of(WeaponFamily.SWORD)
        ).orElseThrow() == WeaponFamily.MACE, "specific mace tag overrides generic/provider sword classification");
        require(CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.SCYTHE), Optional.of(WeaponFamily.AXE)
        ).orElseThrow() == WeaponFamily.SCYTHE, "specific scythe tag overrides provider fallback");
        require(CombatWeaponFamilyPolicy.resolve(
            Set.of(), Optional.of(WeaponFamily.SPEAR)
        ).orElseThrow() == WeaponFamily.SPEAR, "provider remains fallback when no RPG tag exists");
    }

    private static void conflictingExplicitFamiliesFailClosed() {
        require(CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.MACE, WeaponFamily.HAMMER), Optional.of(WeaponFamily.SWORD)
        ).isEmpty(), "two distinct explicit RPG families fail closed regardless of provider");
        require(CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.BOW, WeaponFamily.CROSSBOW), Optional.empty()
        ).isEmpty(), "bow/crossbow tag conflict fails closed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
