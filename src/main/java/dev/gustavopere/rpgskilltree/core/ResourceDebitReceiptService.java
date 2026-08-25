package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Bounded exact receipt store with an atomic conditional single-claim boundary. */
public final class ResourceDebitReceiptService {
    private static final long RETENTION_TICKS = 12_000L;
    private final int maxReceipts;
    private final CanonicalEventLedger recordClaims;
    private final LinkedHashMap<Key, Entry> receipts = new LinkedHashMap<>();

    public ResourceDebitReceiptService(int maxReceipts) {
        if (maxReceipts <= 0) throw new IllegalArgumentException("maxReceipts must be positive");
        this.maxReceipts = maxReceipts;
        this.recordClaims = new CanonicalEventLedger(maxReceipts * 2);
    }

    public synchronized boolean record(ResourceDebitReceipt receipt, long nowTick) {
        Objects.requireNonNull(receipt);
        requireTick(nowTick);
        if (!ProcGuard.mayTriggerSecondaryEffect(receipt.action().origin())) return false;
        removeExpired(nowTick);
        Key key = Key.of(receipt.action(), receipt.kind());
        if (receipts.containsKey(key)) return false;
        if (!recordClaims.claimPrimaryOnce(receipt.action(),
            "resource_debit_receipt:" + receipt.kind(), nowTick, RETENTION_TICKS)) return false;
        while (receipts.size() >= maxReceipts) {
            Iterator<Map.Entry<Key, Entry>> iterator = receipts.entrySet().iterator();
            iterator.next(); iterator.remove();
        }
        receipts.put(key, new Entry(receipt, nowTick + RETENTION_TICKS, false));
        return true;
    }

    public synchronized Optional<ResourceDebitReceipt> peek(
        CanonicalActionIdentity action,
        ResourceDebitReceipt.Kind kind,
        long nowTick
    ) {
        Objects.requireNonNull(action); Objects.requireNonNull(kind); requireTick(nowTick);
        removeExpired(nowTick);
        Entry entry = receipts.get(Key.of(action, kind));
        return entry == null || entry.claimed ? Optional.empty() : Optional.of(entry.receipt);
    }

    public synchronized Optional<ResourceDebitReceipt> claimIf(
        CanonicalActionIdentity action,
        ResourceDebitReceipt.Kind kind,
        long nowTick,
        ReceiptCommit commit
    ) {
        Objects.requireNonNull(action); Objects.requireNonNull(kind); Objects.requireNonNull(commit);
        requireTick(nowTick);
        removeExpired(nowTick);
        Key key = Key.of(action, kind);
        Entry entry = receipts.get(key);
        if (entry == null || entry.claimed || !entry.receipt.action().sameAction(action)) return Optional.empty();
        if (!commit.commit(entry.receipt)) return Optional.empty();
        receipts.put(key, new Entry(entry.receipt, entry.expiresAt, true));
        return Optional.of(entry.receipt);
    }

    /** Receipts and claims deliberately survive transient lifecycle changes. */
    public synchronized void clearTransient(String actorId) {
        Objects.requireNonNull(actorId);
    }

    @FunctionalInterface
    public interface ReceiptCommit { boolean commit(ResourceDebitReceipt receipt); }

    private void removeExpired(long nowTick) {
        receipts.entrySet().removeIf(entry -> entry.getValue().expiresAt <= nowTick);
    }

    private record Key(String actorId, String actionId, ResourceDebitReceipt.Kind kind) {
        private static Key of(CanonicalActionIdentity action, ResourceDebitReceipt.Kind kind) {
            return new Key(action.actorId(), action.actionId(), kind);
        }
    }

    private record Entry(ResourceDebitReceipt receipt, long expiresAt, boolean claimed) {}

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
