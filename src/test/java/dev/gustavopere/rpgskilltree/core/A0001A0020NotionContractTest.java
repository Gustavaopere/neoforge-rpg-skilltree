package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** Fresh Notion contract for the closed A0001-A0020 implementation batch. */
public final class A0001A0020NotionContractTest {
    public static void main(String[] args) {
        catalogIsExactlyClosedBatch();
        ranksCostsAndDependenciesMatchNotion();
        familyGatesMatchNotion();
        weaponTrainingCoefficientsMatchNotion();
        transientResourcesRespectCapsAndLifecycle();
        System.out.println("A0001A0020NotionContractTest: PASS");
    }

    private static void catalogIsExactlyClosedBatch() {
        require(NotionCombatPerkCatalog.all().size() == 20, "closed implementation catalog must contain exactly A0001-A0020");
        for (int i = 1; i <= 20; i++) {
            String code = "A%04d".formatted(i);
            require(NotionCombatPerkCatalog.definition(code).isPresent(), "missing " + code);
        }
        require(NotionCombatPerkCatalog.definition("A0021").isEmpty(), "A0021 must not be implemented in this batch");
    }

    private static void ranksCostsAndDependenciesMatchNotion() {
        expect("A0001", 3, 1, Map.of());
        expect("A0002", 3, 1, Map.of("A0001", 2));
        expect("A0003", 3, 1, Map.of("A0001", 1));
        expect("A0004", 1, 1, Map.of("A0003", 2));
        expect("A0005", 1, 1, Map.of("A0002", 2, "A0004", 1));
        expect("A0006", 1, 2, Map.of("A0004", 1, "A0005", 1));
        expect("A0007", 3, 1, Map.of());
        expect("A0008", 3, 1, Map.of("A0007", 2));
        expect("A0009", 3, 1, Map.of("A0007", 1));
        expect("A0010", 2, 1, Map.of("A0009", 2));
        expect("A0011", 2, 1, Map.of("A0008", 2, "A0009", 1));
        expect("A0012", 1, 2, Map.of("A0010", 1, "A0011", 1));
        expect("A0013", 3, 1, Map.of());
        expect("A0014", 3, 1, Map.of("A0013", 2));
        expect("A0015", 3, 1, Map.of("A0013", 1));
        expect("A0016", 2, 1, Map.of("A0015", 2));
        expect("A0017", 2, 1, Map.of("A0014", 2, "A0015", 1));
        expect("A0018", 1, 2, Map.of("A0016", 1, "A0017", 1));
        expect("A0019", 3, 1, Map.of());
        expect("A0020", 3, 1, Map.of("A0019", 2));
    }

    private static void familyGatesMatchNotion() {
        expectRootGate("A0001", "epic_sword", "epicfight:sword", 60);
        expectCapstoneGate("A0006", "epic_sword", "epicfight:sword", 80);
        expectRootGate("A0007", "epic_axe", "epicfight:axe", 60);
        expectCapstoneGate("A0012", "epic_axe", "epicfight:axe", 80);
        expectRootGate("A0013", "epic_spear", "epicfight:spear", 60);
        expectCapstoneGate("A0018", "epic_spear", "epicfight:spear", 80);
        expectRootGate("A0019", "epic_dagger", "epicfight:dagger", 60);
    }

    private static void weaponTrainingCoefficientsMatchNotion() {
        var ranks = CombatPerkRanks.of(Map.of(
            "A0001", 3, "A0002", 3, "A0003", 3,
            "A0007", 3, "A0008", 3, "A0009", 3,
            "A0013", 3, "A0014", 3, "A0015", 3,
            "A0019", 3, "A0020", 3
        ));
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.SWORD, ranks), 1.09D), "A0001 damage");
        require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.SWORD, ranks), 0.06D), "A0002 rhythm");
        require(close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SWORD, ranks), 0.09D), "A0003 crit");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.AXE, ranks), 1.09D), "A0007 damage");
        require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.AXE, ranks), 0.06D), "A0008 rhythm");
        require(close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.AXE, ranks), 0.09D), "A0009 crit");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.SPEAR, ranks), 1.09D), "A0013 damage");
        require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.SPEAR, ranks), 0.06D), "A0014 rhythm");
        require(close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SPEAR, ranks), 0.09D), "A0015 crit");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.DAGGER, ranks), 1.09D), "A0019 damage");
        require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.DAGGER, ranks), 0.06D), "A0020 rhythm");
    }

    private static void transientResourcesRespectCapsAndLifecycle() {
        var state = new NotionCombatPerkState();
        state.addMomentum("player", 99, 1_000L);
        require(state.momentum("player") == 5, "Momentum must clamp to five");
        state.addFury("player", 999.0D);
        require(close(state.fury("player"), 100.0D), "Fury must clamp to 100");
        state.addDistanceControl("player", 99, 1_000L, 7_000L);
        require(state.distanceControl("player") == 3, "Distance Control must clamp to three");
        state.clearTransient("player");
        require(state.momentum("player") == 0 && close(state.fury("player"), 0.0D) && state.distanceControl("player") == 0,
            "death/logout/dimension lifecycle cleanup must clear transient combat state");
    }

    private static void expect(String code, int maxRank, int cost, Map<String, Integer> dependencies) {
        var definition = NotionCombatPerkCatalog.definition(code).orElseThrow();
        require(definition.maxRank() == maxRank, code + " max rank");
        require(definition.rankCost() == cost, code + " rank cost");
        require(definition.dependencies().equals(dependencies), code + " dependencies");
    }

    private static void expectRootGate(String code, String specialization, String mastery, int amount) {
        var node = CombatPerkTreeModel.node(code).orElseThrow();
        require(node.startingPoint(), code + " must be a family root");
        require(node.minCharacterLevel() == 8, code + " minimum level");
        require(node.requiredSpecializations().contains(specialization), code + " gateway");
        require(node.requiredMastery().getOrDefault(mastery, 0) == amount, code + " mastery gate");
    }

    private static void expectCapstoneGate(String code, String specialization, String mastery, int amount) {
        var node = CombatPerkTreeModel.node(code).orElseThrow();
        require(node.requiredSpecializations().contains(specialization), code + " gateway");
        require(node.requiredMastery().getOrDefault(mastery, 0) == amount, code + " capstone mastery");
    }

    private static boolean close(double left, double right) { return Math.abs(left - right) < 1.0E-9D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
