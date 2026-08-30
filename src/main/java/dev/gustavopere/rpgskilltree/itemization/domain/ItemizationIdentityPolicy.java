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
     * This prevents one UUID from falsely claiming uniqueness for two independently mutable item instances.
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
        return ItemizationIdentity.of(copiedInstanceId, copiedDeterministicSeed, original.schemaVersion());
    }
}
