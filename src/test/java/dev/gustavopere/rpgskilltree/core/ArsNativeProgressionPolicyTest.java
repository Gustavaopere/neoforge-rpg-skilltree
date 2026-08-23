package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class ArsNativeProgressionPolicyTest {
    public static void main(String[] args) {
        arcaneNodesFeedNativeArsMana();
        sorcererIdentityChangesManaEconomy();
        summoningBranchControlsFamiliars();
        arsMasteryScalesWithRealSpellCost();
        System.out.println("ArsNativeProgressionPolicyTest: PASS");
    }

    private static void arcaneNodesFeedNativeArsMana() {
        PassiveNodeProgress nodes = PassiveNodeProgress.of(Map.of(
            ArsNativeProgressionPolicy.ARCANE_AWAKENING, 1,
            ArsNativeProgressionPolicy.ARCANE_REGEN, 1,
            ArsNativeProgressionPolicy.ARCANE_MANA_CAPSTONE, 1
        ));
        require(ArsNativeProgressionPolicy.adjustMaxMana(100, nodes, false) == 155,
            "shared Arcane mana nodes should affect Ars max mana");
        require(close(ArsNativeProgressionPolicy.adjustManaRegen(10.0D, nodes, false), 10.3D),
            "Arcane regen node should affect Ars native regen");
    }

    private static void sorcererIdentityChangesManaEconomy() {
        PassiveNodeProgress nodes = PassiveNodeProgress.of(Map.of(
            ArsNativeProgressionPolicy.ARCANE_AWAKENING, 1,
            ArsNativeProgressionPolicy.ARCANE_REGEN, 1
        ));
        require(ArsNativeProgressionPolicy.adjustMaxMana(100, nodes, true) == 132,
            "Sorcerer should gain a 10 percent native mana identity bonus after flat Arcane mana");
        require(close(ArsNativeProgressionPolicy.adjustManaRegen(10.0D, nodes, true), 10.8D),
            "Sorcerer should add five percentage points of native regen on top of the Arcane node");
    }

    private static void summoningBranchControlsFamiliars() {
        require(!ArsNativeProgressionPolicy.canSummonFamiliar(PassiveNodeProgress.empty()),
            "familiars should require entering the Summoning branch");
        require(ArsNativeProgressionPolicy.canSummonFamiliar(PassiveNodeProgress.of(Map.of(
            ArsNativeProgressionPolicy.SUMMONING_ENTRY, 1
        ))), "Summoning entry node should unlock familiar summoning");
    }

    private static void arsMasteryScalesWithRealSpellCost() {
        SpellAction cheap = new SpellAction(
            new ActionOrigin("ars:test", 0), "ars", "form>effect", "composition", Set.of("projectile"), 10
        );
        MasteryState cheapState = MasteryAwardService.apply(MasteryState.empty(), MasteryPolicies.forArs(cheap));
        require(cheapState.experience("ars:casting") == 3, "cheap Ars casts keep current provider floor");
        require(cheapState.experience("ars:projectile") == 3, "cheap Ars composition keeps current lane floor");

        SpellAction costly = new SpellAction(
            new ActionOrigin("ars:test", 0), "ars", "form>augment>effect", "composition", Set.of("projectile", "amplification"), 120
        );
        MasteryState costlyState = MasteryAwardService.apply(MasteryState.empty(), MasteryPolicies.forArs(costly));
        require(costlyState.experience("ars:casting") == 5, "costly Ars casts should deepen provider mastery");
        require(costlyState.experience("ars:projectile") == 5, "costly compositions should deepen used lanes");
        require(costlyState.experience("ars:amplification") == 5, "all actual composition lanes share bounded intensity");
        require(costlyState.experience("magic:casting") == 2, "shared casting lane remains provider-neutral");
    }

    private static boolean close(double a, double b) {
        return Math.abs(a - b) < 0.000001D;
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
