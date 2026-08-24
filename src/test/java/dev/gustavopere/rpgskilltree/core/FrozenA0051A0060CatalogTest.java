package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

/** RED contract for the first frozen A0051-A0100 implementation cohort. */
public final class FrozenA0051A0060CatalogTest {
    public static void main(String[] args) {
        catalogMatchesFrozenNotionCohort();
        combatFistGateIsCanonicalAndFailClosed();
        fistClassificationIsExplicitAndExcludesEmptyHands();
        System.out.println("FrozenA0051A0060CatalogTest: PASS");
    }

    private static void catalogMatchesFrozenNotionCohort() {
        for (int i = 51; i <= 60; i++) {
            String code = "A%04d".formatted(i);
            require(FrozenA0051A0100Catalog.definition(code).isPresent(), "missing frozen node " + code);
        }
        require(FrozenA0051A0100Catalog.definition("A0050").isEmpty(), "old batch must stay separate");

        var a51 = FrozenA0051A0100Catalog.definition("A0051").orElseThrow();
        require(a51.name().equals("Precisão com Bestas"), "A0051 name");
        require(a51.maxRank() == 3 && a51.rankCost() == 1, "A0051 rank contract");
        require(a51.dependencies().equals(Map.of("A0049", 1)), "A0051 dependency");
        require(a51.requiredSpecializations().equals(Set.of("epic_crossbow")), "A0051 specialization");

        var a52 = FrozenA0051A0100Catalog.definition("A0052").orElseThrow();
        require(a52.dependencies().equals(Map.of("A0050", 2, "A0051", 2)), "A0052 ranked dependencies");
        require(a52.maxRank() == 2, "A0052 max rank");

        var a54 = FrozenA0051A0100Catalog.definition("A0054").orElseThrow();
        require(a54.rankCost() == 2 && a54.maxRank() == 1, "A0054 capstone rank contract");
        require(a54.dependencies().equals(Map.of("A0052", 2, "A0053", 1)), "A0054 dependencies");
        require(a54.requiredMastery().equals(Map.of("combat:crossbow", 80)), "A0054 mastery");

        var a55 = FrozenA0051A0100Catalog.definition("A0055").orElseThrow();
        require(a55.requiredSpecializations().equals(Set.of(CombatFistPolicy.SPECIALIZATION_ID)), "A0055 combat_fist");
        require(a55.minimumCharacterLevel() == 8, "A0055 level gate");
        require(a55.requiredMastery().equals(Map.of(CombatFistPolicy.MASTERY_ID, 60)), "A0055 mastery gate");

        var a59 = FrozenA0051A0100Catalog.definition("A0059").orElseThrow();
        require(a59.dependencies().equals(Map.of("A0056", 2, "A0058", 1)), "A0059 dependencies");
        require(a59.fallback() == FrozenCombatPerkDefinition.Fallback.FAIL_CLOSED, "A0059 provider fallback");

        var a60 = FrozenA0051A0100Catalog.definition("A0060").orElseThrow();
        require(a60.dependencies().equals(Map.of("A0058", 2, "A0059", 1)), "A0060 dependencies");
        require(a60.rankCost() == 2 && a60.maxRank() == 1, "A0060 capstone rank contract");
        require(a60.requiredMastery().equals(Map.of(CombatFistPolicy.MASTERY_ID, 80)), "A0060 mastery gate");
    }

    private static void combatFistGateIsCanonicalAndFailClosed() {
        require(!CombatFistPolicy.gateSatisfied(false, true, 8, 60), "missing registry definition must fail closed");
        require(!CombatFistPolicy.gateSatisfied(true, true, 7, 60), "level 7 must fail");
        require(!CombatFistPolicy.gateSatisfied(true, true, 8, 59), "mastery 59 must fail");
        require(!CombatFistPolicy.gateSatisfied(true, false, 8, 60), "locked specialization must fail");
        require(CombatFistPolicy.gateSatisfied(true, true, 8, 60), "exact frozen gate must pass");
    }

    private static void fistClassificationIsExplicitAndExcludesEmptyHands() {
        require(!CombatFistPolicy.isFistWeapon(true, true, CombatFistPolicy.ProviderCategory.FIST),
            "empty hands never count by default");
        require(CombatFistPolicy.isFistWeapon(false, false, CombatFistPolicy.ProviderCategory.FIST),
            "explicit provider fist category counts");
        require(CombatFistPolicy.isFistWeapon(false, true, CombatFistPolicy.ProviderCategory.GENERIC_WEAPON),
            "curated tag outranks a generic provider category");
        require(!CombatFistPolicy.isFistWeapon(false, false, CombatFistPolicy.ProviderCategory.GENERIC_WEAPON),
            "generic weapon category cannot become fist");
        require(!CombatFistPolicy.isFistWeapon(false, false, CombatFistPolicy.ProviderCategory.UNKNOWN),
            "ambiguous modded weapon fails closed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
