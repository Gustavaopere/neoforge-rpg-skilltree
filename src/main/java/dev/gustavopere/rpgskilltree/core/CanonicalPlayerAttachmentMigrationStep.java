package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.function.UnaryOperator;

/** One explicit sequential migration from canonical attachment schema N to N+1. */
public record CanonicalPlayerAttachmentMigrationStep(
    int fromVersion,
    int toVersion,
    UnaryOperator<byte[]> migration
) {
    public CanonicalPlayerAttachmentMigrationStep {
        if (fromVersion <= 0) {
            throw new IllegalArgumentException("fromVersion must be positive");
        }
        if (toVersion != fromVersion + 1) {
            throw new IllegalArgumentException("canonical attachment migrations must advance exactly one version");
        }
        Objects.requireNonNull(migration, "migration");
    }

    public byte[] migrate(byte[] encoded) {
        Objects.requireNonNull(encoded, "encoded");
        return migration.apply(encoded.clone());
    }
}
