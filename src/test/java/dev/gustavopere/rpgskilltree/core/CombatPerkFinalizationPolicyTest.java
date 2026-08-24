package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class CombatPerkFinalizationPolicyTest {
    public static void main(String[] args) {
        boneBreakerRequiresCrackedHeavyMaceAndUsesMasteryCooldown();
        battleHarvestRequiresLegitimateRootKillAndConsumesOnceOnDifferentTarget();
        battleHarvestCooldownScalesWithMastery();
        shockNeverTransfersBetweenTargets();
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

    private static void battleHarvestRequiresLegitimateRootKillAndConsumesOnceOnDifferentTarget() {
        long now = 10_000L;
        var ranks = CombatPerkRanks.of(Map.of("A0040", 2, "A0042", 1));

        var noMature = new NotionCombatPerkState();
        require(!CombatPerkFinalizationPolicy.activateBattleHarvest(
            CanonicalActionIdentity.root("p", "kill-no-mark", "test"), "p", "victim",
            WeaponFamily.SCYTHE, true, true, true, ranks, noMature, 80, now
        ), "victim without Mature Reaping Mark cannot arm Harvest");

        var procState = matureVictim("p", "victim", now);
        require(!CombatPerkFinalizationPolicy.activateBattleHarvest(
            CanonicalActionIdentity.root("p", "proc-kill", "test").child("proc"), "p", "victim",
            WeaponFamily.SCYTHE, true, true, true, ranks, procState, 80, now
        ), "kill caused by proc/derived damage cannot arm Harvest");

        var derivedChain = matureVictim("p", "victim", now);
        require(!CombatPerkFinalizationPolicy.activateBattleHarvest(
            CanonicalActionIdentity.root("p", "chain-kill", "test").child("proc1").child("proc2"), "p", "victim",
            WeaponFamily.SCYTHE, true, true, true, ranks, derivedChain, 80, now
        ), "proc chain cannot arm Harvest");

        var state = matureVictim("p", "victim", now);
        var kill = CanonicalActionIdentity.root("p", "kill-1", "test");
        require(CombatPerkFinalizationPolicy.activateBattleHarvest(
            kill, "p", "victim", WeaponFamily.SCYTHE, true, true, true, ranks, state, 80, now
        ), "legitimate direct scythe kill of mature-marked victim arms Harvest");
        require(!CombatPerkFinalizationPolicy.activateBattleHarvest(
            kill.withSource("duplicate-callback"), "p", "victim", WeaponFamily.SCYTHE,
            true, true, true, ranks, state, 80, now
        ), "duplicate callback for same death is idempotent");
        require(state.hasBattleHarvest("p", now + 5_999L), "Harvest lasts six seconds");
        require(!state.hasBattleHarvest("p", now + 6_000L), "Harvest expires at six seconds");

        require(!CombatPerkFinalizationPolicy.consumeBattleHarvestOnHit(
            CanonicalActionIdentity.root("p", "same-target-hit", "test"), "p", "victim",
            WeaponFamily.SCYTHE, true, true, ranks, state, now + 1_000L
        ), "attempt on the killed target does not consume Harvest");
        require(state.hasBattleHarvest("p", now + 1_000L), "same-target attempt leaves Harvest armed");

        require(CombatPerkFinalizationPolicy.consumeBattleHarvestOnHit(
            CanonicalActionIdentity.root("p", "other-target-hit", "test"), "p", "next",
            WeaponFamily.SCYTHE, true, true, ranks, state, now + 1_500L
        ), "next direct scythe hit on a different target consumes Harvest");
        require(state.hasTargetFlag("p", "next", NotionCombatPerkState.TargetFlag.REAPING_MARK, now + 1_500L),
            "Harvest immediately transfers Reaping Mark");
        require(!state.hasBattleHarvest("p", now + 1_500L), "Harvest consumes exactly once");
        require(!CombatPerkFinalizationPolicy.consumeBattleHarvestOnHit(
            CanonicalActionIdentity.root("p", "third-hit", "test"), "p", "third",
            WeaponFamily.SCYTHE, true, true, ranks, state, now + 2_000L
        ), "a consumed Harvest cannot chain again");
    }

    private static void battleHarvestCooldownScalesWithMastery() {
        requireHarvestCooldown(80, 10_000L);
        requireHarvestCooldown(90, 9_000L);
        requireHarvestCooldown(100, 8_000L);
    }

    private static void requireHarvestCooldown(int mastery, long expected) {
        long now = 1_000L;
        var state = matureVictim("p", "victim", now);
        var ranks = CombatPerkRanks.of(Map.of("A0040", 1, "A0042", 1));
        require(CombatPerkFinalizationPolicy.activateBattleHarvest(
            CanonicalActionIdentity.root("p", "kill-" + mastery, "test"), "p", "victim",
            WeaponFamily.SCYTHE, true, true, true, ranks, state, mastery, now
        ), "Harvest activation for mastery " + mastery);
        require(!state.actorCooldownReady("p", "A0042", now + expected - 1L), "Harvest cooldown active");
        require(state.actorCooldownReady("p", "A0042", now + expected), "Harvest cooldown exact expiry");
    }

    private static void shockNeverTransfersBetweenTargets() {
        long now = 1_000L;
        var state = new NotionCombatPerkState();
        state.addTargetCounter("p", "first", NotionCombatPerkState.TargetCounter.SHOCK, 3, 3, now, 6_000L);

        require(state.targetCounter("p", "first", NotionCombatPerkState.TargetCounter.SHOCK, now + 500L) == 3,
            "first target keeps its independent Shock record when combat changes targets");
        require(state.targetCounter("p", "second", NotionCombatPerkState.TargetCounter.SHOCK, now + 500L) == 0,
            "second target receives zero Shock without its own eligible hit");

        state.addTargetCounter("p", "second", NotionCombatPerkState.TargetCounter.SHOCK, 1, 3, now + 500L, 6_000L);
        require(state.targetCounter("p", "first", NotionCombatPerkState.TargetCounter.SHOCK, now + 1_000L) == 3,
            "a hit on another target cannot consume or transfer the first target's Shock");
        require(state.targetCounter("p", "second", NotionCombatPerkState.TargetCounter.SHOCK, now + 1_000L) == 1,
            "second target tracks only its own eligible hits");
        require(state.targetCounter("p", "first", NotionCombatPerkState.TargetCounter.SHOCK, now + 6_000L) == 0,
            "first target expires six seconds after its own last gain");
        require(state.targetCounter("p", "second", NotionCombatPerkState.TargetCounter.SHOCK, now + 6_499L) == 1,
            "second target uses its own six-second TTL");
        require(state.targetCounter("p", "second", NotionCombatPerkState.TargetCounter.SHOCK, now + 6_500L) == 0,
            "second target expires from its own last gain");
    }

    private static NotionCombatPerkState matureVictim(String actor, String victim, long now) {
        var state = new NotionCombatPerkState();
        state.setTargetFlag(actor, victim, NotionCombatPerkState.TargetFlag.REAPING_MARK, now + 10_000L);
        state.setTargetFlag(actor, victim, NotionCombatPerkState.TargetFlag.REAPING_MATURE, now + 10_000L);
        return state;
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 1.0E-9D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
