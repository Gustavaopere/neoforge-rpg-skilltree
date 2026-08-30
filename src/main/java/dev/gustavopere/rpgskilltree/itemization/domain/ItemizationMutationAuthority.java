package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.Objects;
import java.util.Optional;

/**
 * Server-authoritative initialization boundary for canonical itemization state.
 *
 * <p>There is intentionally no survival reroll operation. Once a state exists, attempting to initialize
 * another rank/item-power/modifier decision fails closed instead of silently replacing it.</p>
 */
public final class ItemizationMutationAuthority {
    private ItemizationMutationAuthority() {}

    public static ItemizationState initialize(Optional<ItemizationState> current, ItemizationState generated) {
        Objects.requireNonNull(current, "current");
        Objects.requireNonNull(generated, "generated");
        if (current.isPresent()) {
            throw new IllegalStateException("item is already itemized; canonical generation is immutable");
        }
        return generated;
    }
}
