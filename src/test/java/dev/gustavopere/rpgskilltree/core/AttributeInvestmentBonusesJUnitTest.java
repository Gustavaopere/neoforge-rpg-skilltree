package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AttributeInvestmentBonusesJUnitTest {
    @Test
    void allEighteenBenefitsHaveThreePerAttributeAndStableBreakpoints() {
        assertEquals(18, AttributeInvestmentBonuses.Benefit.values().length);
        for (AttributeId attribute : AttributeId.values()) {
            int count = 0;
            for (var benefit : AttributeInvestmentBonuses.Benefit.values()) {
                if (benefit.attribute() != attribute) continue;
                count++;
                assertEquals(0.0, AttributeInvestmentBonuses.value(benefit, 0L));
                assertEquals(0.0, AttributeInvestmentBonuses.value(benefit, benefit.interval() - 1L));
                assertEquals(benefit.firstGain(),
                    AttributeInvestmentBonuses.value(benefit, benefit.interval()));
                assertEquals(benefit.firstGain(),
                    AttributeInvestmentBonuses.value(benefit, benefit.interval() * 2L - 1L));
            }
            assertEquals(3, count, attribute.name());
        }
    }

    @Test
    void hugeRanksNeverOverflowAndMarginalReturnsDiminish() {
        long[] ranks = {0L, 1L, 30L, 100L, 300L, 1_000L, 1_000_000L, Long.MAX_VALUE};
        for (var benefit : AttributeInvestmentBonuses.Benefit.values()) {
            double previous = 0.0;
            for (long rank : ranks) {
                double value = AttributeInvestmentBonuses.value(benefit, rank);
                assertTrue(Double.isFinite(value), benefit.name());
                assertTrue(value >= previous, benefit.name() + " rank " + rank);
                assertTrue(value <= benefit.theoreticalLimit(), benefit.name());
                previous = value;
            }
            double first = AttributeInvestmentBonuses.value(benefit, benefit.interval());
            double second = AttributeInvestmentBonuses.value(benefit, 2L * benefit.interval());
            double third = AttributeInvestmentBonuses.value(benefit, 3L * benefit.interval());
            assertTrue(second > first && third > second, benefit.name());
            assertTrue(second - first > third - second, benefit.name());
        }
    }

    @Test
    void projectionIsPureAndRejectsNegativeRank() {
        AttributeRanks ranks = AttributeRanks.of(Map.of(
            AttributeId.STRENGTH, 100L, AttributeId.CONSTITUTION, 300L));
        var first = AttributeInvestmentBonuses.resolve(ranks);
        assertEquals(first, AttributeInvestmentBonuses.resolve(ranks));
        assertEquals(18, first.size());
        assertEquals(0.0, first.get(AttributeInvestmentBonuses.Benefit.INTELLIGENCE_MAX_MANA));
        assertThrows(UnsupportedOperationException.class,
            () -> first.put(AttributeInvestmentBonuses.Benefit.AGILITY_MOVEMENT_SPEED, 1.0));
        assertThrows(IllegalArgumentException.class, () ->
            AttributeInvestmentBonuses.value(AttributeInvestmentBonuses.Benefit.CHARISMA_ALLY_HEALING, -1));
    }
}
