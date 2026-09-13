package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;
import java.util.UUID;

/** Immutable monetary identity for one colony economy. */
public record EconomyColonyKey(UUID value) {
    public EconomyColonyKey {
        Objects.requireNonNull(value, "value");
    }
}
