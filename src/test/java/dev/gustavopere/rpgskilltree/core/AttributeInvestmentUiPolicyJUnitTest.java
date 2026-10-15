package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AttributeInvestmentUiPolicyJUnitTest {
    @Test
    void absentSnapshotOrPendingMutationDisablesActions() {
        for (AttributeId attribute : AttributeId.values()) {
            assertFalse(AttributeInvestmentUiPolicy.canPurchase(null, attribute, false));
            assertFalse(AttributeInvestmentUiPolicy.canRefund(null, attribute, false));
            CoreProgressionSyncState baseline = snapshot(5L, 0L, attribute);
            assertTrue(AttributeInvestmentUiPolicy.canPurchase(baseline, attribute, false));
            assertFalse(AttributeInvestmentUiPolicy.canPurchase(baseline, attribute, true));
            assertFalse(AttributeInvestmentUiPolicy.canRefund(baseline, attribute, true));
            assertFalse(AttributeInvestmentUiPolicy.canRefund(baseline, attribute, false));
            assertFalse(AttributeInvestmentUiPolicy.rankChanged(null, attribute, 0L, false));
        }
    }

    @Test
    void authoritativeBalanceRankAndDirectionGateChanges() {
        AttributeId attribute = AttributeId.CHARISMA;
        CoreProgressionSyncState noPoints = snapshot(0L, 4L, attribute);
        assertFalse(AttributeInvestmentUiPolicy.canPurchase(noPoints, attribute, false));
        assertTrue(AttributeInvestmentUiPolicy.canRefund(noPoints, attribute, false));

        CoreProgressionSyncState maximum = snapshot(0L, Long.MAX_VALUE, attribute);
        assertFalse(AttributeInvestmentUiPolicy.canPurchase(maximum, attribute, false));
        assertTrue(AttributeInvestmentUiPolicy.canRefund(maximum, attribute, false));

        CoreProgressionSyncState unchanged = snapshot(4L, 10L, attribute);
        CoreProgressionSyncState purchased = snapshot(3L, 11L, attribute);
        CoreProgressionSyncState refunded = snapshot(5L, 9L, attribute);
        assertFalse(AttributeInvestmentUiPolicy.rankChanged(unchanged, attribute, 10L, false));
        assertFalse(AttributeInvestmentUiPolicy.rankChanged(unchanged, attribute, 10L, true));
        assertTrue(AttributeInvestmentUiPolicy.rankChanged(purchased, attribute, 10L, false));
        assertTrue(AttributeInvestmentUiPolicy.rankChanged(refunded, attribute, 10L, true));
        assertFalse(AttributeInvestmentUiPolicy.rankChanged(purchased, attribute, 10L, true));
        assertFalse(AttributeInvestmentUiPolicy.rankChanged(refunded, attribute, 10L, false));
        assertFalse(AttributeInvestmentUiPolicy.rankChanged(purchased, AttributeId.STRENGTH, 0L, false));
    }

    private static CoreProgressionSyncState snapshot(long available, long rank, AttributeId attribute) {
        long allocated = rank;
        long total = Math.addExact(available, allocated);
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(attribute, rank));
        return new CoreProgressionSyncState(
            1L, 0L, BigInteger.TEN, total, allocated, 0L, available, 0L,
            ranks, 1L, "0".repeat(64)
        );
    }
}
