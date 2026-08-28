package dev.gustavopere.rpgskilltree.core;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Deterministic sequential migration engine for the outer canonical player attachment payload.
 *
 * <p>Each registered step must migrate exactly N -> N+1. Missing steps, downgrades,
 * future versions and malformed/oversized payloads fail closed.</p>
 */
public final class CanonicalPlayerAttachmentMigrationChain {
    private final int targetVersion;
    private final int maxPayloadBytes;
    private final Map<Integer, CanonicalPlayerAttachmentMigrationStep> stepsBySourceVersion;

    public CanonicalPlayerAttachmentMigrationChain(
        int targetVersion,
        int maxPayloadBytes,
        List<CanonicalPlayerAttachmentMigrationStep> steps
    ) {
        if (targetVersion <= 0) {
            throw new IllegalArgumentException("targetVersion must be positive");
        }
        if (maxPayloadBytes < Integer.BYTES) {
            throw new IllegalArgumentException("maxPayloadBytes must fit a version header");
        }
        Objects.requireNonNull(steps, "steps");

        HashMap<Integer, CanonicalPlayerAttachmentMigrationStep> indexed = new HashMap<>();
        for (CanonicalPlayerAttachmentMigrationStep step : steps) {
            Objects.requireNonNull(step, "migration step");
            if (step.toVersion() > targetVersion) {
                throw new IllegalArgumentException("migration step exceeds target version: " + step.toVersion());
            }
            if (indexed.put(step.fromVersion(), step) != null) {
                throw new IllegalArgumentException(
                    "duplicate migration source version: " + step.fromVersion()
                );
            }
        }

        this.targetVersion = targetVersion;
        this.maxPayloadBytes = maxPayloadBytes;
        this.stepsBySourceVersion = Map.copyOf(indexed);
    }

    public byte[] migrateToCurrent(byte[] encoded) {
        validatePayload(encoded);
        int sourceVersion = encodedVersion(encoded);
        if (sourceVersion <= 0) {
            throw new IllegalArgumentException("canonical attachment version must be positive");
        }
        if (sourceVersion > targetVersion) {
            throw new IllegalArgumentException(
                "canonical attachment version " + sourceVersion
                    + " is newer than supported version " + targetVersion
            );
        }

        byte[] current = encoded.clone();
        int version = sourceVersion;
        while (version < targetVersion) {
            CanonicalPlayerAttachmentMigrationStep step = stepsBySourceVersion.get(version);
            if (step == null) {
                throw new IllegalArgumentException(
                    "no canonical attachment migration path from version " + version
                        + " to " + (version + 1)
                );
            }
            byte[] migrated = step.migrate(current);
            validatePayload(migrated);
            int migratedVersion = encodedVersion(migrated);
            if (migratedVersion != step.toVersion()) {
                throw new IllegalArgumentException(
                    "canonical attachment migration from version " + version
                        + " produced version " + migratedVersion
                        + " instead of " + step.toVersion()
                );
            }
            current = migrated;
            version = migratedVersion;
        }
        return current;
    }

    public int targetVersion() {
        return targetVersion;
    }

    private void validatePayload(byte[] encoded) {
        Objects.requireNonNull(encoded, "encoded");
        if (encoded.length < Integer.BYTES) {
            throw new IllegalArgumentException("canonical attachment payload is too short for a version header");
        }
        if (encoded.length > maxPayloadBytes) {
            throw new IllegalArgumentException(
                "canonical attachment payload exceeds migration limit: " + encoded.length
            );
        }
    }

    private static int encodedVersion(byte[] encoded) {
        return ByteBuffer.wrap(encoded, 0, Integer.BYTES).getInt();
    }
}
