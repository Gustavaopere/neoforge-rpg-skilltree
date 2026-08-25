package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

/** Frozen P-0021 provider/modlist boundary contract for A0001-A0050. */
public final class FrozenProviderBoundaryContractTest {
    public static void main(String[] args) throws Exception {
        epicFightPresentAndAbsentFailClosed();
        womAndAddonsOnlyParticipateThroughUnequivocalEpicFightCategory();
        curatedRpgTagOverridesGenericProviderAndAmbiguityFailsClosed();
        oneClassificationProducesOneFamilyLane();
        unrelatedOrVisualProvidersCannotCreateCombatFacts();
        runtimeUsesConfirmedEpicFightDodgeOnly();
        System.out.println("FrozenProviderBoundaryContractTest: PASS");
    }

    static void epicFightPresentAndAbsentFailClosed() {
        eq(Optional.of(WeaponFamily.SWORD), CombatWeaponFamilyPolicy.fromEpicFightCategory("epicfight:sword"));
        eq(Optional.of(WeaponFamily.AXE), CombatWeaponFamilyPolicy.resolve(
            Set.of(), CombatWeaponFamilyPolicy.fromEpicFightCategory("epicfight:axe")));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.resolve(Set.of(), Optional.empty()));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.fromEpicFightCategory(""));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.fromEpicFightCategory(null));
    }

    static void womAndAddonsOnlyParticipateThroughUnequivocalEpicFightCategory() {
        eq(Optional.of(WeaponFamily.SWORD), CombatWeaponFamilyPolicy.fromEpicFightCategory("wom:sword"));
        eq(Optional.of(WeaponFamily.SPEAR), CombatWeaponFamilyPolicy.fromEpicFightCategory("addon_namespace:spear"));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.fromEpicFightCategory("wom:greatsword"));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.fromEpicFightCategory("weapon_of_miracles"));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.fromEpicFightCategory("addon:unknown_weapon"));
    }

    static void curatedRpgTagOverridesGenericProviderAndAmbiguityFailsClosed() {
        eq(Optional.of(WeaponFamily.SCYTHE), CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.SCYTHE), Optional.of(WeaponFamily.SWORD)));
        eq(Optional.of(WeaponFamily.MACE), CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.MACE), Optional.empty()));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.SWORD, WeaponFamily.AXE), Optional.of(WeaponFamily.SWORD)));
        eq(Optional.empty(), CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.BOW, WeaponFamily.CROSSBOW), Optional.empty()));
    }

    static void oneClassificationProducesOneFamilyLane() {
        Optional<WeaponFamily> family = CombatWeaponFamilyPolicy.resolve(
            Set.of(WeaponFamily.DAGGER),
            CombatWeaponFamilyPolicy.fromEpicFightCategory("wom:sword")
        );
        eq(Optional.of(WeaponFamily.DAGGER), family);
        eq("epicfight:dagger", CombatWeaponMasteryPolicy.masteryLane(family.orElseThrow()));

        var awards = CombatWeaponMasteryPolicy.forConfirmedHit(
            new ActionOrigin("provider-boundary", 0),
            family.orElseThrow(),
            "provider-boundary"
        );
        eq(2, awards.size());
        eq(1L, awards.stream().filter(a -> a.lane().equals("epicfight:dagger")).count());
        eq(0L, awards.stream().filter(a -> a.lane().equals("epicfight:sword")).count());

        var procAwards = CombatWeaponMasteryPolicy.forConfirmedHit(
            new ActionOrigin("provider-boundary-proc", 1),
            family.orElseThrow(),
            "provider-boundary-proc"
        );
        eq(0, procAwards.size());
    }

    static void unrelatedOrVisualProvidersCannotCreateCombatFacts() {
        for (String raw : new String[] {
            "punchy", "ydm_weapon_master", "parcool", "epic_parcool",
            "tfc", "terrafirmacraft", "cold_sweat", "tfc_cold_sweat",
            "protection_pixel", "visual_only"
        }) {
            eq(Optional.empty(), CombatWeaponFamilyPolicy.fromEpicFightCategory(raw));
        }
    }

    static void runtimeUsesConfirmedEpicFightDodgeOnly() throws Exception {
        Path sourcePath = Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightCombatPerkHooks.java"
        );
        String source = Files.readString(sourcePath);
        contains(source, "EpicFightEventHooks.Entity.ON_DODGE.registerEvent(");
        contains(source, "EpicFightCombatPerkHooks::onSuccessfulDodge");
        contains(source, "CombatWeaponFamilyPolicy.resolve(explicitFamilies, fallback)");

        String lower = source.toLowerCase(java.util.Locale.ROOT);
        for (String forbidden : new String[] {
            "parcool", "epic_parcool", "punchy", "ydm", "weaponsofmiracles",
            "weapons_of_miracles", "terrafirmacraft", "cold_sweat", "coldsweat"
        }) {
            if (lower.contains(forbidden)) {
                throw new AssertionError("provider must not inject direct combat facts here: " + forbidden);
            }
        }
    }

    private static void contains(String text, String expected) {
        if (!text.contains(expected)) throw new AssertionError("missing runtime contract fragment: " + expected);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
