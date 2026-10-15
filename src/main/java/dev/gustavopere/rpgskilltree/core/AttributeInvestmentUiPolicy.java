package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** UI hints only; the Core server remains authoritative over all mutations. */
public final class AttributeInvestmentUiPolicy {
    private AttributeInvestmentUiPolicy() {}

    public static boolean canPurchase(CoreProgressionSyncState snapshot, AttributeId attribute, boolean pending) {
        Objects.requireNonNull(attribute);
        return snapshot != null && !pending && snapshot.availableCorePoints() > 0L
            && snapshot.attributeRanks().rank(attribute) < Long.MAX_VALUE;
    }

    public static boolean canRefund(CoreProgressionSyncState snapshot, AttributeId attribute, boolean pending) {
        Objects.requireNonNull(attribute);
        return snapshot != null && !pending && snapshot.attributeRanks().rank(attribute) > 0L;
    }

    public static boolean rankChanged(CoreProgressionSyncState snapshot, AttributeId attribute, long previous, boolean refund) {
        Objects.requireNonNull(attribute);
        if (snapshot == null) return false;
        long current = snapshot.attributeRanks().rank(attribute);
        return refund ? current < previous : current > previous;
    }
}
