package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Synchronous TAKE_DAMAGE_POST consumption window for one already-correlated heavy-impact receipt.
 *
 * <p>Identity matching is reference-exact for both victim and DamageSource. The window never searches by time,
 * actor id, damage amount, impact amount, or previous/next event. It remains readable until explicitly closed,
 * while claims are idempotent per consumer id for this one operation.
 */
public final class HeavyImpactReceiptWindow implements AutoCloseable {
    private final Object victimIdentity;
    private final Object sourceIdentity;
    private final Optional<HeavyImpactReceiptCorrelation.Receipt> receipt;
    private final Set<String> claimedConsumers = new HashSet<>();
    private boolean open = true;

    public HeavyImpactReceiptWindow(
        Object victimIdentity,
        Object sourceIdentity,
        Optional<HeavyImpactReceiptCorrelation.Receipt> receipt
    ) {
        this.victimIdentity = Objects.requireNonNull(victimIdentity, "victimIdentity");
        this.sourceIdentity = Objects.requireNonNull(sourceIdentity, "sourceIdentity");
        this.receipt = Objects.requireNonNull(receipt, "receipt");
    }

    /** Returns the receipt without consuming it when both operation identities match exactly. */
    public Optional<HeavyImpactReceiptCorrelation.Receipt> peek(Object victimIdentity, Object sourceIdentity) {
        if (!matches(victimIdentity, sourceIdentity)) return Optional.empty();
        return receipt;
    }

    /**
     * Returns the receipt at most once for the given consumer id while this operation window remains open.
     * Different consumers are independent by design.
     */
    public Optional<HeavyImpactReceiptCorrelation.Receipt> claim(
        Object victimIdentity,
        Object sourceIdentity,
        String consumerId
    ) {
        String validatedConsumerId = requireText(consumerId, "consumerId");
        if (!matches(victimIdentity, sourceIdentity) || receipt.isEmpty()) return Optional.empty();
        if (!claimedConsumers.add(validatedConsumerId)) return Optional.empty();
        return receipt;
    }

    /** Reference-exact operation identity match; closed windows always fail closed. */
    public boolean matches(Object victimIdentity, Object sourceIdentity) {
        return open && this.victimIdentity == victimIdentity && this.sourceIdentity == sourceIdentity;
    }

    public boolean isOpen() {
        return open;
    }

    /** Permanently revokes the receipt at TAKE_DAMAGE_POST cleanup. Idempotent. */
    @Override
    public void close() {
        open = false;
        claimedConsumers.clear();
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
