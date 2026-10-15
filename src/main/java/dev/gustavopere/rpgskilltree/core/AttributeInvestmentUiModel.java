package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Pure, server-snapshot-driven interaction model for the six Core attributes.
 * This class never awards ranks, computes authoritative prices or edits the ledger.
 */
public final class AttributeInvestmentUiModel {
    public static final int REQUEST_TIMEOUT_TICKS = 100;

    public enum Status {
        READY("screen.rpgskilltree.rank_ready"),
        PENDING("screen.rpgskilltree.rank_pending"),
        UPDATED("screen.rpgskilltree.rank_updated"),
        UNCONFIRMED("screen.rpgskilltree.rank_unconfirmed");

        private final String translationKey;

        Status(String translationKey) {
            this.translationKey = translationKey;
        }

        public String translationKey() {
            return translationKey;
        }
    }

    public record Layout(int left, int width, int rowHeight, int firstRowY, int footerY) {
        public int rowY(int index) {
            if (index < 0 || index >= AttributeId.values().length) {
                throw new IllegalArgumentException("invalid attribute row");
            }
            return firstRowY + index * rowHeight;
        }

        public int purchaseX() {
            return left + width - 94;
        }

        public int refundX() {
            return left + width - 47;
        }

        public int labelWidth() {
            return Math.max(24, width - 108);
        }

        public int backWidth() {
            return Math.min(86, width / 3);
        }

        public int statusX() {
            return left + Math.min(94, width / 3 + 8);
        }
    }

    public record Row(AttributeId attribute, long rank, boolean purchaseEnabled,
                      boolean refundEnabled, int y) {}

    public record Request(AttributeId attribute, boolean refund, String transactionId) {}

    private record Pending(AttributeId attribute, long previousRank, boolean refund) {}

    private Pending pending;
    private int pendingTicks;
    private Status status = Status.READY;

    public static Layout layout(int width, int height) {
        int panelWidth = Math.max(130, Math.min(336, width - 16));
        int left = Math.max(8, (width - panelWidth) / 2);
        int rowHeight = Math.max(18, Math.min(23, (height - 74) / AttributeId.values().length));
        int firstRowY = 44;
        int footerY = Math.min(height - 24, firstRowY + AttributeId.values().length * rowHeight + 8);
        return new Layout(left, panelWidth, rowHeight, firstRowY, footerY);
    }

    public List<Row> rows(CoreProgressionSyncState snapshot, Layout layout) {
        Objects.requireNonNull(layout);
        List<Row> result = new ArrayList<>();
        int index = 0;
        for (AttributeId attribute : AttributeId.values()) {
            long rank = snapshot == null ? 0L : snapshot.attributeRanks().rank(attribute);
            result.add(new Row(attribute, rank, canPurchase(snapshot, attribute),
                canRefund(snapshot, attribute), layout.rowY(index++)));
        }
        return List.copyOf(result);
    }

    public boolean canPurchase(CoreProgressionSyncState snapshot, AttributeId attribute) {
        return AttributeInvestmentUiPolicy.canPurchase(snapshot, attribute, pending != null);
    }

    public boolean canRefund(CoreProgressionSyncState snapshot, AttributeId attribute) {
        return AttributeInvestmentUiPolicy.canRefund(snapshot, attribute, pending != null);
    }

    public Optional<Request> request(CoreProgressionSyncState snapshot, AttributeId attribute,
                                      boolean refund, String transactionId) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(transactionId);
        if (transactionId.isBlank()) throw new IllegalArgumentException("missing transaction id");
        if (refund ? !canRefund(snapshot, attribute) : !canPurchase(snapshot, attribute)) {
            return Optional.empty();
        }
        pending = new Pending(attribute, snapshot.attributeRanks().rank(attribute), refund);
        pendingTicks = 0;
        status = Status.PENDING;
        return Optional.of(new Request(attribute, refund, transactionId));
    }

    public void tick(CoreProgressionSyncState snapshot) {
        if (pending == null) return;
        if (AttributeInvestmentUiPolicy.rankChanged(
            snapshot, pending.attribute(), pending.previousRank(), pending.refund())) {
            pending = null;
            status = Status.UPDATED;
        } else if (++pendingTicks >= REQUEST_TIMEOUT_TICKS) {
            pending = null;
            status = Status.UNCONFIRMED;
        }
    }

    public Status status() {
        return status;
    }

    public boolean isPending() {
        return pending != null;
    }
}
