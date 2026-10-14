package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

final class IronsCooldownInvestmentPolicyJUnitTest {
    @Test
    void onlyMilestonesReducePositiveCooldowns() {
        assertEquals(1000, IronsCooldownInvestmentPolicy.adjustedCooldown(1000, 0L));
        assertEquals(1000, IronsCooldownInvestmentPolicy.adjustedCooldown(1000, 9L));
        assertEquals(998, IronsCooldownInvestmentPolicy.adjustedCooldown(1000, 10L));
        assertEquals(998, IronsCooldownInvestmentPolicy.adjustedCooldown(1000, 19L));
        assertEquals(996, IronsCooldownInvestmentPolicy.adjustedCooldown(1000, 20L));
    }

    @Test
    void neverCreatesZeroCooldownOrChangesExistingZero() {
        assertEquals(1, IronsCooldownInvestmentPolicy.adjustedCooldown(1, Long.MAX_VALUE));
        assertEquals(2, IronsCooldownInvestmentPolicy.adjustedCooldown(2, Long.MAX_VALUE));
        assertEquals(0, IronsCooldownInvestmentPolicy.adjustedCooldown(0, Long.MAX_VALUE));
        assertThrows(IllegalArgumentException.class,
            () -> IronsCooldownInvestmentPolicy.adjustedCooldown(-1, 10L));
        assertThrows(IllegalArgumentException.class,
            () -> IronsCooldownInvestmentPolicy.adjustedCooldown(1, -1L));
    }

    @Test
    void infiniteRanksStayBoundedAndLongCooldownNeverOverflows() {
        int prior = 1000;
        for (long rank : new long[]{0L, 10L, 30L, 100L, 300L, 1000L, Long.MAX_VALUE}) {
            int result = IronsCooldownInvestmentPolicy.adjustedCooldown(1000, rank);
            assertTrue(result <= prior);
            assertTrue(result >= 950, "at most 5% native cooldown reduction");
            prior = result;
        }
        assertEquals(950, IronsCooldownInvestmentPolicy.adjustedCooldown(1000, Long.MAX_VALUE));
        assertTrue(IronsCooldownInvestmentPolicy.adjustedCooldown(Integer.MAX_VALUE, Long.MAX_VALUE) > 0);
    }
}
