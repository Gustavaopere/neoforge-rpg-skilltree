package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public final class MasteryLaneCatalogTest {
    private MasteryLaneCatalogTest() {}

    public static void main(String[] args) {
        fixedLanesAreCanonicalAndStable();
        dynamicFamiliesUseCanonicalFactories();
        boundedFamiliesFailClosedOnUnknownMembers();
        malformedDynamicMembersFailClosed();
        System.out.println("MasteryLaneCatalogTest: PASS");
    }

    private static void fixedLanesAreCanonicalAndStable() {
        require(MasteryLaneCatalog.MAGIC_CASTING.equals("magic:casting"), "magic casting id drifted");
        require(MasteryLaneCatalog.IRONS_CASTING.equals("irons:casting"), "Iron's casting id drifted");
        require(MasteryLaneCatalog.ARS_CASTING.equals("ars:casting"), "Ars casting id drifted");
        require(MasteryLaneCatalog.CREATE_ENGINEERING.equals("create:engineering"), "Create engineering id drifted");
        require(MasteryLaneCatalog.EPICFIGHT_WEAPON.equals("epicfight:weapon"), "Epic Fight weapon id drifted");
        require(MasteryLaneCatalog.isCanonical(MasteryLaneCatalog.OCCULT_PRACTICE), "occult practice must be canonical");
        require(MasteryLaneCatalog.isCanonical(MasteryLaneCatalog.SUMMONING_PRACTICE), "summoning practice must be canonical");
        require(!MasteryLaneCatalog.isCanonical("unknown:practice"), "unknown lane must fail closed");
    }

    private static void dynamicFamiliesUseCanonicalFactories() {
        require(MasteryLaneCatalog.ironsDiscipline("fire").equals("irons:fire"), "Iron's discipline factory drifted");
        require(MasteryLaneCatalog.ars("projectile").equals("ars:projectile"), "Ars family factory drifted");
        require(MasteryLaneCatalog.goety("necromancy").equals("goety:necromancy"), "Goety family factory drifted");
        require(MasteryLaneCatalog.malumSpirit("arcane").equals("malum:spirit/arcane"), "Malum spirit factory drifted");
        require(MasteryLaneCatalog.create("kinetics").equals("create:kinetics"), "Create family factory drifted");
        require(MasteryLaneCatalog.epicFightWeaponCategory("greatsword").equals("epicfight:greatsword"), "Epic Fight category factory drifted");

        for (String lane : Set.of(
            MasteryLaneCatalog.ironsDiscipline("fire"),
            MasteryLaneCatalog.ars("control"),
            MasteryLaneCatalog.goety("storm"),
            MasteryLaneCatalog.malumSpirit("sacred"),
            MasteryLaneCatalog.create("automation"),
            MasteryLaneCatalog.epicFightWeaponCategory("bow")
        )) {
            require(MasteryLaneCatalog.isCanonical(lane), "factory output must be canonical: " + lane);
        }
    }

    private static void boundedFamiliesFailClosedOnUnknownMembers() {
        expectRejected(() -> MasteryLaneCatalog.ars("teleport"), "unknown Ars lane must fail closed");
        expectRejected(() -> MasteryLaneCatalog.goety("alchemy"), "unknown Goety lane must fail closed");
        expectRejected(() -> MasteryLaneCatalog.create("throughput"), "unknown Create lane must fail closed");
    }

    private static void malformedDynamicMembersFailClosed() {
        expectRejected(() -> MasteryLaneCatalog.ironsDiscipline(""), "blank Iron's discipline must fail closed");
        expectRejected(() -> MasteryLaneCatalog.malumSpirit("spirit:arcane"), "nested Malum spirit id must fail closed");
        expectRejected(() -> MasteryLaneCatalog.epicFightWeaponCategory("great sword"), "invalid Epic Fight category must fail closed");
    }

    private static void expectRejected(Runnable action, String message) {
        boolean rejected = false;
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, message);
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
