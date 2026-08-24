package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class NotionCombatPerkCatalogTest {
    public static void main(String[] args) {
        var all = NotionCombatPerkCatalog.all();
        require(all.size() == 50, "catalog must contain exactly A0001-A0050");
        for (int i = 1; i <= 50; i++) {
            String code = "A%04d".formatted(i);
            require(NotionCombatPerkCatalog.definition(code).isPresent(), "missing " + code);
        }
        require(NotionCombatPerkCatalog.definition("A0051").isEmpty(), "A0051 must remain outside this batch");

        require(NotionCombatPerkCatalog.definition("A0001").orElseThrow().maxRank() == 3, "A0001 ranks");
        require(NotionCombatPerkCatalog.definition("A0006").orElseThrow().rankCost() == 2, "A0006 capstone cost");
        require(NotionCombatPerkCatalog.definition("A0002").orElseThrow().dependencies().equals(Map.of("A0001", 2)), "A0002 dependency");
        require(NotionCombatPerkCatalog.definition("A0011").orElseThrow().dependencies().equals(Map.of("A0008", 2, "A0009", 1)), "A0011 dependencies");
        require(NotionCombatPerkCatalog.definition("A0050").orElseThrow().weaponFamily() == CombatPerkDefinition.WeaponFamily.CROSSBOW, "A0050 family");

        var ranks = CombatPerkRanks.of(Map.of("A0001", 3, "A0002", 2, "A0004", 1, "A0010", 1));
        require(ranks.rank("A0001") == 3, "rank lookup");
        require(ranks.dependenciesSatisfied(NotionCombatPerkCatalog.definition("A0002").orElseThrow()), "dependency gate");
        require(!ranks.dependenciesSatisfied(NotionCombatPerkCatalog.definition("A0006").orElseThrow()), "capstone dependency gate");

        var runtime = new NotionCombatPerkState();
        runtime.addMomentum("player", 3, 1000L);
        runtime.addFury("player", 40.0, 1000L);
        require(runtime.momentum("player") == 3, "momentum state");
        require(runtime.fury("player") == 40.0, "fury state");
        runtime.consumeMomentum("player", 2);
        require(runtime.momentum("player") == 1, "momentum consumption");
        require(runtime.fury("player") == 40.0, "momentum must never consume fury");

        require(Math.abs(NotionCombatPerkRules.baseDamageMultiplier(CombatPerkDefinition.WeaponFamily.SWORD, ranks) - 1.09) < 0.000001, "A0001 sword damage");
        require(Math.abs(NotionCombatPerkRules.baseDamageMultiplier(CombatPerkDefinition.WeaponFamily.AXE, ranks) - 1.0) < 0.000001, "sword perk must not buff axe");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
