package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Persistent identity of one itemized equipment instance.
 *
 * <p>The UUID, deterministic generation seed and schema version are canonical persisted decisions.
 * Translated/player-facing strings never belong in this identity.</p>
 */
public record ItemizationIdentity(UUID instanceId, long deterministicSeed, int schemaVersion) {
    public ItemizationIdentity {
        Objects.requireNonNull(instanceId, "instanceId");
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be >= 1");
        }
    }

    public static ItemizationIdentity of(UUID instanceId, long deterministicSeed, int schemaVersion) {
        return new ItemizationIdentity(instanceId, deterministicSeed, schemaVersion);
    }
}
