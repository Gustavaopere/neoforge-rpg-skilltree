package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.*;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class EpicFightInvestmentAttributePolicyJUnitTest {
    @Test
    void onlyExactDeclaredEpicFightReleaseCanApplyStaminaInvestment() {
        assertTrue(EpicFightInvestmentAttributePolicy.operational(true, "21.17.3.1"));
        assertFalse(EpicFightInvestmentAttributePolicy.operational(false, "21.17.3.1"));
        assertFalse(EpicFightInvestmentAttributePolicy.operational(true, "21.17.3"));
        assertFalse(EpicFightInvestmentAttributePolicy.operational(true, "21.17.4"));
        assertFalse(EpicFightInvestmentAttributePolicy.operational(true, "unknown"));
        assertFalse(EpicFightInvestmentAttributePolicy.operational(true, null));
    }

    @Test
    void staminaRegenUsesNativeEpicFightPlayerAttributeAndDiminishingReturns() {
        assertEquals("epicfight:stamina_regen", EpicFightInvestmentAttributePolicy.target());
        assertEquals("rpgskilltree:investment/epicfight/determination_stamina_regen",
            EpicFightInvestmentAttributePolicy.modifierId());
        assertEquals(Benefit.DETERMINATION_STAMINA_REGEN,
            EpicFightInvestmentAttributePolicy.benefit());
        assertEquals(0.0, AttributeInvestmentBonuses.value(
            EpicFightInvestmentAttributePolicy.benefit(), 4L), 1e-9);
        assertEquals(0.004, AttributeInvestmentBonuses.value(
            EpicFightInvestmentAttributePolicy.benefit(), 5L), 1e-9);
        var huge = AttributeRanks.of(Map.of(AttributeId.DETERMINATION, Long.MAX_VALUE));
        var value = AttributeInvestmentBonuses.value(
            EpicFightInvestmentAttributePolicy.benefit(),
            huge.rank(AttributeId.DETERMINATION));
        assertTrue(Double.isFinite(value) && value > 0.0 && value <= 0.100000001);
    }
}
