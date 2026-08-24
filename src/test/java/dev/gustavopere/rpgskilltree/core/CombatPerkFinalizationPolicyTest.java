package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class CombatPerkFinalizationPolicyTest {
    public static void main(String[] args) {
        boneBreakerRequiresCrackedHeavyMaceAndUsesMasteryCooldown();
        System.out.println("CombatPerkFinalizationPolicyTest: PASS");
    }

    private static void boneBreakerRequiresCrackedHeavyMaceAndUsesMasteryCooldown() {
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        var state = new NotionCombatPerkState();
        long now = 1_000L;
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 5_000L);
        var action = CanonicalActionIdentity.root("p", "mace-heavy-1", "test");

        var applied = CombatPerkFinalizationPolicy.activateBoneBreaker(
            action, "p", "mob", WeaponFamily.MACE, true, true, true, false,
            ranks, state, 80, now
        );
        require(applied.isPresent(), "A0036 activates on confirmed direct heavy mace hit against cracked target");
        require(close(applied.get().outgoingPhysicalDamageMultiplier(), 0.92D), "A0036 normal target deals 8% less physical damage");
        require(close(applied.get().movementSpeedMultiplier(), 0.90D), "A0036 normal target has 10% less movement speed");
        require(applied.get().expiresAtMillis() == now + 3_000L, "A0036 lasts exactly three seconds");
        require(!state.cooldownReady("p", "mob", "A0036", now + 11_999L), "A0036 mastery 80 cooldown is twelve seconds");
        require(state.cooldownReady("p", "mob", "A0036", now + 12_000L), "A0036 mastery 80 cooldown expires at twelve seconds");
        require(state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 4_999L), "A0036 does not consume or extend Armor Cracked");

        var bossState = new NotionCombatPerkState();
        bossState.setTargetFlag("p", "boss", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 5_000L);
        var boss = CombatPerkFinalizationPolicy.activateBoneBreaker(
            CanonicalActionIdentity.root("p", "mace-heavy-boss", "test"),
            "p", "boss", WeaponFamily.MACE, true, true, true, true,
            ranks, bossState, 100, now
        );
        require(boss.isPresent(), "A0036 activates against boss");
        require(close(boss.get().outgoingPhysicalDamageMultiplier(), 0.96D), "boss receives half damage penalty");
        require(close(boss.get().movementSpeedMultiplier(), 0.95D), "boss receives half movement penalty");
        require(!bossState.cooldownReady("p", "boss", "A0036", now + 9_999L), "mastery 100 cooldown is ten seconds");
        require(bossState.cooldownReady("p", "boss", "A0036", now + 10_000L), "mastery 100 cooldown expiry");

        var wrongFamily = new NotionCombatPerkState();
        wrongFamily.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 5_000L);
        require(CombatPerkFinalizationPolicy.activateBoneBreaker(
            CanonicalActionIdentity.root("p", "hammer-heavy", "test"),
            "p", "mob", WeaponFamily.HAMMER, true, true, true, false,
            ranks, wrongFamily, 80, now
        ).isEmpty(), "A0036 never treats Hammer as Mace");

        var notHeavy = new NotionCombatPerkState();
        notHeavy.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 5_000L);
        require(CombatPerkFinalizationPolicy.activateBoneBreaker(
            CanonicalActionIdentity.root("p", "mace-light", "test"),
            "p", "mob", WeaponFamily.MACE, true, true, false, false,
            ranks, notHeavy, 80, now
        ).isEmpty(), "A0036 requires heavy confirmation");

        require(CombatPerkFinalizationPolicy.activateBoneBreaker(
            CanonicalActionIdentity.root("p", "mace-uncracked", "test"),
            "p", "other", WeaponFamily.MACE, true, true, true, false,
            ranks, new NotionCombatPerkState(), 80, now
        ).isEmpty(), "A0036 requires Armor Cracked before the hit");
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 1.0E-9D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
