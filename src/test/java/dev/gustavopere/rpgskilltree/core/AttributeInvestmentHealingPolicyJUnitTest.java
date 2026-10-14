package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class AttributeInvestmentHealingPolicyJUnitTest {
    @Test
    void rankThresholdChangesExistingHealExactlyOnceWithoutSecondHeal() {
        AttributeRanks zero = AttributeRanks.empty();
        AttributeRanks first = AttributeRanks.of(Map.of(AttributeId.CONSTITUTION, 8L));
        AttributeRanks before = AttributeRanks.of(Map.of(AttributeId.CONSTITUTION, 7L));
        assertEquals(4.0F, AttributeInvestmentHealingPolicy.adjust(4.0F, zero));
        assertEquals(4.0F, AttributeInvestmentHealingPolicy.adjust(4.0F, before));
        assertEquals(4.016F, AttributeInvestmentHealingPolicy.adjust(4.0F, first), 1e-6F);
        assertEquals(0.0F, AttributeInvestmentHealingPolicy.adjust(0.0F, first));
    }

    @Test
    void hugeRanksRemainAtMostTenPercentAndDoNotOverflowFloat() {
        var huge = AttributeRanks.of(Map.of(AttributeId.CONSTITUTION, Long.MAX_VALUE));
        double theoretical = AttributeInvestmentBonuses.Benefit.CONSTITUTION_HEALING_RECEIVED.theoreticalLimit();
        assertEquals(0.1D, theoretical, 1e-9);
        float result = AttributeInvestmentHealingPolicy.adjust(10.0F, huge);
        assertTrue(result >= 10.0F && result <= 11.0001F);
        assertEquals(Float.MAX_VALUE,
            AttributeInvestmentHealingPolicy.adjust(Float.MAX_VALUE, huge));
        assertThrows(IllegalArgumentException.class,
            () -> AttributeInvestmentHealingPolicy.adjust(-1.0F, huge));
        assertThrows(IllegalArgumentException.class,
            () -> AttributeInvestmentHealingPolicy.adjust(Float.NaN, huge));
    }
}
