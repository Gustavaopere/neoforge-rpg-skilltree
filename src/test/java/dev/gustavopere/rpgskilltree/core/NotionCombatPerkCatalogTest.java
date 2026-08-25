package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class NotionCombatPerkCatalogTest {
    public static void main(String[] args) {
        catalogContainsExactlyAuditedBatch();
        dependenciesAndRanksAreExact();
        transientResourcesRemainIndependentAndCapped();
        trainingRulesAreWeaponSpecific();
        System.out.println("NotionCombatPerkCatalogTest: PASS");
    }

    private static void catalogContainsExactlyAuditedBatch() {
        var all = NotionCombatPerkCatalog.all();
        require(all.size() == 50, "catalog must contain exactly A0001-A0050");
        for (int i = 1; i <= 50; i++) {
            String code = "A%04d".formatted(i);
            require(NotionCombatPerkCatalog.definition(code).isPresent(), "missing " + code);
        }
        require(NotionCombatPerkCatalog.definition("A0051").isEmpty(), "A0051 must remain outside this batch");

        require(NotionCombatPerkCatalog.definition("A0001").orElseThrow().maxRank() == 3, "A0001 ranks");
        require(NotionCombatPerkCatalog.definition("A0006").orElseThrow().rankCost() == 2, "A0006 capstone cost");
        require(NotionCombatPerkCatalog.definition("A0012").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.AXE, "A0012 family");
        require(NotionCombatPerkCatalog.definition("A0018").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.SPEAR, "A0018 family");
        require(NotionCombatPerkCatalog.definition("A0024").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.DAGGER, "A0024 family");
        require(NotionCombatPerkCatalog.definition("A0030").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.HAMMER, "A0030 family");
        require(NotionCombatPerkCatalog.definition("A0036").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.MACE, "A0036 family");
        require(NotionCombatPerkCatalog.definition("A0042").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.SCYTHE, "A0042 family");
        require(NotionCombatPerkCatalog.definition("A0048").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.BOW, "A0048 family");
        require(NotionCombatPerkCatalog.definition("A0050").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.CROSSBOW, "A0050 family");
    }

    private static void dependenciesAndRanksAreExact() {
        require(NotionCombatPerkCatalog.definition("A0002").orElseThrow().dependencies().equals(Map.of("A0001", 2)), "A0002 dependency");
        require(NotionCombatPerkCatalog.definition("A0011").orElseThrow().dependencies().equals(Map.of("A0008", 2, "A0009", 1)), "A0011 dependencies");
        require(NotionCombatPerkCatalog.definition("A0048").orElseThrow().dependencies().equals(Map.of("A0046", 1, "A0047", 1)), "A0048 dependencies");

        var ranks = CombatPerkRanks.of(Map.of("A0001", 3, "A0002", 2, "A0004", 1, "A0010", 1));
        require(ranks.rank("A0001") == 3, "rank lookup");
        require(ranks.dependenciesSatisfied(NotionCombatPerkCatalog.definition("A0002").orElseThrow()), "dependency gate");
        require(!ranks.dependenciesSatisfied(NotionCombatPerkCatalog.definition("A0006").orElseThrow()), "capstone dependency gate");
    }

    private static void transientResourcesRemainIndependentAndCapped() {
        var runtime = new NotionCombatPerkState();
        runtime.addMomentum("player", 7, 1000L);
        runtime.addFury("player", 140.0, 1000L);
        runtime.addDistanceControl("player", 5, 1000L, 7_000L);
        runtime.addFlow("player", 9, 1000L, 7_000L);
        runtime.addFocus("player", 130.0, 1000L);

        require(runtime.momentum("player") == 5, "momentum cap");
        require(runtime.fury("player") == 100.0, "fury cap");
        require(runtime.distanceControl("player", 1000L) == 3, "distance-control cap");
        require(runtime.flow("player", 1000L) == 4, "flow cap");
        require(runtime.focus("player") == 100.0, "focus cap");

        runtime.consumeMomentum("player", 2);
        runtime.consumeFury("player", 20.0);
        require(runtime.momentum("player") == 3, "momentum consumption");
        require(runtime.fury("player") == 80.0, "fury consumption");
        require(runtime.distanceControl("player", 1000L) == 3, "momentum/fury must not consume distance control");
        require(runtime.flow("player", 1000L) == 4, "momentum/fury must not consume flow");
        require(runtime.focus("player") == 100.0, "momentum/fury must not consume focus");
    }

    private static void trainingRulesAreWeaponSpecific() {
        var ranks = CombatPerkRanks.of(Map.ofEntries(
            Map.entry("A0001", 3), Map.entry("A0002", 2), Map.entry("A0003", 1),
            Map.entry("A0007", 1), Map.entry("A0008", 3), Map.entry("A0009", 2),
            Map.entry("A0043", 2), Map.entry("A0044", 1), Map.entry("A0045", 3),
            Map.entry("A0049", 3), Map.entry("A0050", 2)
        ));

        require(close(NotionCombatPerkRules.baseDamageMultiplier(CombatPerkDefinition.WeaponFamily.SWORD, ranks), 1.09), "A0001 sword damage");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(CombatPerkDefinition.WeaponFamily.AXE, ranks), 1.03), "A0007 axe damage");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(CombatPerkDefinition.WeaponFamily.BOW, ranks), 1.06), "A0043 bow damage");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(CombatPerkDefinition.WeaponFamily.CROSSBOW, ranks), 1.09), "A0049 crossbow damage");

        require(close(NotionCombatPerkRules.rhythmBonus(CombatPerkDefinition.WeaponFamily.SWORD, ranks), 0.04), "A0002 sword rhythm");
        require(close(NotionCombatPerkRules.rhythmBonus(CombatPerkDefinition.WeaponFamily.AXE, ranks), 0.06), "A0008 axe rhythm");
        require(close(NotionCombatPerkRules.rhythmBonus(CombatPerkDefinition.WeaponFamily.CROSSBOW, ranks), 0.04), "A0050 crossbow rhythm");

        require(close(NotionCombatPerkRules.criticalChanceBonus(CombatPerkDefinition.WeaponFamily.SWORD, ranks), 0.03), "A0003 sword crit");
        require(close(NotionCombatPerkRules.criticalChanceBonus(CombatPerkDefinition.WeaponFamily.AXE, ranks), 0.06), "A0009 axe crit");
        require(close(NotionCombatPerkRules.criticalChanceBonus(CombatPerkDefinition.WeaponFamily.BOW, ranks), 0.09), "A0045 bow crit");
        require(close(NotionCombatPerkRules.criticalChanceBonus(CombatPerkDefinition.WeaponFamily.CROSSBOW, ranks), 0.0), "crossbow crit is outside A0001-A0050");
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000001;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
