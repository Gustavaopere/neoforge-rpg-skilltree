package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.*;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class IronsInvestmentAttributePolicyJUnitTest {
    @Test
    void onlyExactAuditedReleaseIsEligible() {
        assertTrue(IronsInvestmentAttributePolicy.supportsVersion("1.21.1-3.16.3"));
        assertFalse(IronsInvestmentAttributePolicy.supportsVersion("1.21.1-3.16.2"));
        assertFalse(IronsInvestmentAttributePolicy.supportsVersion("1.20.1-3.16.3"));
        assertFalse(IronsInvestmentAttributePolicy.supportsVersion("1.21.1-3.17.0"));
        assertFalse(IronsInvestmentAttributePolicy.supportsVersion(null));
        assertFalse(IronsInvestmentAttributePolicy.supportsVersion(""));
        assertFalse(IronsInvestmentAttributePolicy.operational(false, "1.21.1-3.16.3"));
        assertFalse(IronsInvestmentAttributePolicy.operational(true, "unknown"));
    }

    @Test
    void onlyThreeCanonicalIronAttributesAreProjectedFromIntelligence() {
        var targets = IronsInvestmentAttributePolicy.targets();
        assertEquals(Map.of(
            Benefit.INTELLIGENCE_MAX_MANA, "irons_spellbooks:max_mana",
            Benefit.INTELLIGENCE_MANA_REGEN, "irons_spellbooks:mana_regen",
            Benefit.INTELLIGENCE_SPELL_POWER, "irons_spellbooks:spell_power"
        ), targets);
        assertThrows(UnsupportedOperationException.class, () ->
            targets.put(Benefit.STRENGTH_MELEE_DAMAGE, "irons_spellbooks:spell_power"));
        var ranks = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 300L));
        double mana = AttributeInvestmentBonuses.value(Benefit.INTELLIGENCE_MAX_MANA,
            ranks.rank(AttributeId.INTELLIGENCE));
        assertTrue(mana > 40.0 && mana < 50.0);
        for (Benefit benefit : targets.keySet()) {
            double value = AttributeInvestmentBonuses.value(benefit, ranks.rank(benefit.attribute()));
            assertTrue(Double.isFinite(value) && value > 0.0);
            assertTrue(value <= benefit.theoreticalLimit());
        }
    }
}
