package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.Objects;
import java.util.UUID;

/** Explicit identity transitions used by later lifecycle adapters. */
public final class ItemizationIdentityPolicy {
    private ItemizationIdentityPolicy() {}

    /** Smithing, repair and compatible upgrades that evolve the same equipment preserve the canonical identity. */
    public static ItemizationIdentity preserveForEvolution(ItemizationIdentity current) {
        return Objects.requireNonNull(current, "current");
    }

    /**
     * A real independent copy of an item stack must receive a distinct instance identity and deterministic seed.
     * This prevents two independently mutable item instances from sharing canonical identity or deterministic state.
     */
    public static ItemizationIdentity forkForTrueCopy(
        ItemizationIdentity original,
        UUID copiedInstanceId,
        long copiedDeterministicSeed
    ) {
        Objects.requireNonNull(original, "original");
        Objects.requireNonNull(copiedInstanceId, "copiedInstanceId");
        if (original.instanceId().equals(copiedInstanceId)) {
            throw new IllegalArgumentException("a true item copy must use a distinct instanceId");
        }
        if (original.deterministicSeed() == copiedDeterministicSeed) {
            throw new IllegalArgumentException("a true item copy must use a distinct deterministic seed");
        }
        return ItemizationIdentity.of(copiedInstanceId, copiedDeterministicSeed, original.schemaVersion());
    }
}
