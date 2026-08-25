package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class FrozenPhysicalProtectionPolicyTest {
    public static void main(String[] args) {
        axeArmorFallbackSpendsFuryForPenetrationOnly();
        maceTraumaRequiresProvenPhysicalProtection();
        armorCrackedFailsClosedWithoutRealTargetDebuff();
        System.out.println("FrozenPhysicalProtectionPolicyTest: PASS");
    }

    private static void axeArmorFallbackSpendsFuryForPenetrationOnly() {
        var state = new NotionCombatPerkState();
        state.addFury("p", 50.0D, 500L);
        var ranks = CombatPerkRanks.of(Map.of("A0011", 2));
        var armorOnly = context("axe-armor", WeaponFamily.AXE, false, true, 1_000L);

        var result = CombatPerkAttackPolicy.beforeHit(armorOnly, ranks, state);
        require(close(result.armorNegationPoints(), 10.0D), "A0011 Armor>0 fallback keeps only rank2 penetration");
        require(close(result.guardPressureMultiplier(), 1.0D), "A0011 Armor-only fallback cannot fabricate guard pressure");
        require(close(result.impactMultiplier(), 1.0D), "A0011 Armor-only fallback cannot fabricate impact");
        require(close(state.fury("p"), 30.0D), "A0011 Armor fallback consumes exactly 20 Fury");

        var unprotected = new NotionCombatPerkState();
        unprotected.addFury("p", 50.0D, 500L);
        var noDefense = CombatPerkAttackPolicy.beforeHit(
            context("axe-none", WeaponFamily.AXE, false, false, 1_000L), ranks, unprotected);
        require(close(noDefense.armorNegationPoints(), 0.0D), "A0011 Armor=0/no provider defense gives no penetration");
        require(close(unprotected.fury("p"), 50.0D), "A0011 cannot spend Fury against an unprotected target");
    }

    private static void maceTraumaRequiresProvenPhysicalProtection() {
        var ranks = CombatPerkRanks.of(Map.of("A0034", 2));
        var state = new NotionCombatPerkState();
        CombatPerkAttackPolicy.afterConfirmedHit(
            context("mace-none", WeaponFamily.MACE, false, false, 1_000L), ranks, state);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 1_000L) == 0,
            "A0034 Armor=0/no guard/no physical reduction cannot gain Trauma");

        CombatPerkAttackPolicy.afterConfirmedHit(
            context("mace-armor", WeaponFamily.MACE, false, true, 2_000L), ranks, state);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 2_000L) == 1,
            "A0034 proven physical protection can gain Trauma");

        CombatPerkAttackPolicy.afterConfirmedHit(
            context("mace-guard", WeaponFamily.MACE, true, true, 3_000L), ranks, state);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 3_000L) == 2,
            "A0034 provider-confirmed guard also qualifies");
    }

    private static void armorCrackedFailsClosedWithoutRealTargetDebuff() {
        var ranks = CombatPerkRanks.of(Map.of("A0035", 2));
        var state = new NotionCombatPerkState();
        state.addTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 3, 3, 1_000L, 8_000L);
        var result = CombatPerkAttackPolicy.beforeHit(
            context("mace-crack-attempt", WeaponFamily.MACE, false, true, 1_500L), ranks, state);

        require(close(result.armorNegationPoints(), 0.0D), "A0035 never converts target armor debuff into personal penetration");
        require(!state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, 1_500L),
            "A0035 core fails closed until a runtime can apply the real target armor debuff");
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 1_500L) == 3,
            "fail-closed A0035 does not consume Trauma");

        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, 6_000L);
        var flagged = CombatPerkAttackPolicy.beforeHit(
            context("mace-existing-crack", WeaponFamily.MACE, false, true, 2_000L), ranks, state);
        require(close(flagged.armorNegationPoints(), 0.0D),
            "even an active Armor Cracked flag cannot become attacker-local armorNegation");
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        String actionId,
        WeaponFamily family,
        boolean providerGuardOrPosture,
        boolean provenPhysicalProtection,
        long nowMillis
    ) {
        return new CombatPerkAttackPolicy.AttackContext(
            CanonicalActionIdentity.root("p", actionId, "test"),
            "p",
            "mob",
            family,
            true,
            true,
            providerGuardOrPosture,
            false,
            false,
            false,
            false,
            provenPhysicalProtection,
            1.0D,
            false,
            0.0D,
            nowMillis
        );
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
