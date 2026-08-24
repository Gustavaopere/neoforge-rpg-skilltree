package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/** Regression contract for P-0004/P-0005/P-0007 against the certified Epic Fight receipt bridge. */
public final class ExactStaminaPerkIntegrationContractTest {
    private static final Path EPIC_FIGHT_HOOKS = Path.of(
        "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightCombatPerkHooks.java"
    );

    public static void main(String[] args) throws Exception {
        battleHarvestRequiresMasteryGateAtRuntime();
        combatAdapterUsesCertifiedCausalBridge();
        System.out.println("ExactStaminaPerkIntegrationContractTest: PASS");
    }

    private static void battleHarvestRequiresMasteryGateAtRuntime() {
        long now = 10_000L;
        var ranks = CombatPerkRanks.of(Map.of("A0040", 2, "A0041", 2, "A0042", 1));
        var state = new NotionCombatPerkState();
        state.setTargetFlag("p", "victim", NotionCombatPerkState.TargetFlag.REAPING_MATURE, now + 10_000L);

        require(!CombatPerkFinalizationPolicy.activateBattleHarvest(
            CanonicalActionIdentity.root("p", "under-mastery-kill", "test"),
            "p",
            "victim",
            WeaponFamily.SCYTHE,
            true,
            true,
            true,
            ranks,
            state,
            79,
            now
        ), "A0042 must fail closed below scythe mastery 80 even if an invalid state contains the node");
    }

    private static void combatAdapterUsesCertifiedCausalBridge() throws Exception {
        String source = Files.readString(EPIC_FIGHT_HOOKS);
        requireContains(source, "EpicFightExactStaminaReceiptBridge.boundActionForDamage(",
            "multi-hit/multi-target playback must reuse the bridge-bound canonical action");
        requireContains(source, "EpicFightExactStaminaReceiptBridge.bindDamageAction(",
            "the first damage action must be bound to the exact stamina playback");
        requireContains(source, "EpicFightExactStaminaReceiptBridge.receipt(",
            "perk refunds must inspect the exact same-action receipt");
        requireContains(source, "EpicFightExactStaminaReceiptBridge.claimRefundAmount(",
            "perk refunds must use the certified once-per-action claim API");
        requireContains(source, "A0029:posture-break-refund",
            "A0029 must have a stable independent claim consumer id");
        requireContains(source, "A0042:battle-harvest-refund",
            "A0042 must have a stable independent claim consumer id");
        requireContains(source, "EpicFightEventHooks.Entity.KILL_ENTITY.registerEvent(",
            "A0042 activation must use Epic Fight's real server-side death signal");
        requireContains(source, "CombatPerkFinalizationPolicy.activateBattleHarvest(",
            "A0042 kill must arm Harvest through the canonical finalization policy");
        requireContains(source, "CombatPerkFinalizationPolicy.consumeBattleHarvestOnHit(",
            "A0042 next-hit mark transfer must be integrated before refund claiming");

        require(!source.contains("lastStaminaSpend") && !source.contains("lastStaminaCost"),
            "refund integration must never correlate against a player's last stamina spend");
    }

    private static void requireContains(String source, String needle, String message) {
        require(source.contains(needle), message + " (missing " + needle + ")");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
