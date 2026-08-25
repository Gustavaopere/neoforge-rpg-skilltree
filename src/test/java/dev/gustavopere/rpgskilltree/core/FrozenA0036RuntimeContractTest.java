package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** Frozen A0036 runtime-causality contract from Notion. */
public final class FrozenA0036RuntimeContractTest {
    public static void main(String[] args) {
        requiresPreHitArmorCrackedSnapshot();
        enforcesMasteryGateAndCooldownBands();
        remainsFailClosedForNonHeavyOrWrongFamily();
        System.out.println("FrozenA0036RuntimeContractTest: PASS");
    }

    private static void requiresPreHitArmorCrackedSnapshot() {
        long now = 5_000L;
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        var state = new NotionCombatPerkState();

        // Simulates A0035 being created by this same confirmed hit. The live state now has the flag,
        // but the PRE snapshot was false, so A0036 must not activate from the newly-created crack.
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 5_000L);
        require(CombatPerkFinalizationPolicy.activateBoneBreakerFromPreHitSnapshot(
            CanonicalActionIdentity.root("p", "same-hit-crack", "test"),
            "p", "mob", WeaponFamily.MACE,
            true, true, true, false, false,
            ranks, state, 80, now
        ).isEmpty(), "A0036 must require Armor Cracked to exist before the activating hit");

        var valid = CombatPerkFinalizationPolicy.activateBoneBreakerFromPreHitSnapshot(
            CanonicalActionIdentity.root("p", "precracked-heavy", "test"),
            "p", "mob", WeaponFamily.MACE,
            true, true, true, true, false,
            ranks, state, 80, now
        );
        require(valid.isPresent(), "pre-hit Armor Cracked snapshot permits canonical A0036 activation");
        require(state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 4_999L),
            "A0036 must neither consume nor shorten Armor Cracked");
    }

    private static void enforcesMasteryGateAndCooldownBands() {
        long now = 10_000L;
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        require(!activate(ranks, new NotionCombatPerkState(), 79, now, "m79"),
            "mastery below 80 cannot activate A0036 even if the node is present");

        var m80 = new NotionCombatPerkState();
        require(activate(ranks, m80, 80, now, "m80"), "mastery 80 activates A0036");
        require(!m80.cooldownReady("p", "mob", "A0036", now + 11_999L), "mastery 80 cooldown is 12s");
        require(m80.cooldownReady("p", "mob", "A0036", now + 12_000L), "mastery 80 cooldown expires at 12s");

        var m90 = new NotionCombatPerkState();
        require(activate(ranks, m90, 90, now, "m90"), "mastery 90 activates A0036");
        require(!m90.cooldownReady("p", "mob", "A0036", now + 10_999L), "mastery 90 cooldown is 11s");
        require(m90.cooldownReady("p", "mob", "A0036", now + 11_000L), "mastery 90 cooldown expires at 11s");

        var m100 = new NotionCombatPerkState();
        require(activate(ranks, m100, 100, now, "m100"), "mastery 100 activates A0036");
        require(!m100.cooldownReady("p", "mob", "A0036", now + 9_999L), "mastery 100 cooldown is 10s");
        require(m100.cooldownReady("p", "mob", "A0036", now + 10_000L), "mastery 100 cooldown expires at 10s");
    }

    private static void remainsFailClosedForNonHeavyOrWrongFamily() {
        long now = 20_000L;
        var ranks = CombatPerkRanks.of(Map.of("A0036", 1));
        var state = new NotionCombatPerkState();
        require(CombatPerkFinalizationPolicy.activateBoneBreakerFromPreHitSnapshot(
            CanonicalActionIdentity.root("p", "light", "test"), "p", "mob", WeaponFamily.MACE,
            true, true, false, true, false, ranks, state, 80, now
        ).isEmpty(), "provider-heavy proof is mandatory");
        require(CombatPerkFinalizationPolicy.activateBoneBreakerFromPreHitSnapshot(
            CanonicalActionIdentity.root("p", "hammer", "test"), "p", "mob", WeaponFamily.HAMMER,
            true, true, true, true, false, ranks, state, 80, now
        ).isEmpty(), "hammer can never satisfy the MACE contract");
    }

    private static boolean activate(
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int mastery,
        long now,
        String actionId
    ) {
        state.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, now + 5_000L);
        return CombatPerkFinalizationPolicy.activateBoneBreakerFromPreHitSnapshot(
            CanonicalActionIdentity.root("p", actionId, "test"), "p", "mob", WeaponFamily.MACE,
            true, true, true, true, false, ranks, state, mastery, now
        ).isPresent();
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
