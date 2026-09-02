package dev.gustavopere.rpgskilltree.core;

import java.nio.ByteBuffer;
import java.util.List;

/** Canonical registry for outer player-attachment schema migrations. */
public final class CanonicalPlayerAttachmentMigrations {
    private static final int MAX_PAYLOAD_BYTES = (32 * 1024 * 1024) + 128;
    private static final CanonicalPlayerAttachmentMigrationStep V1_TO_V2 =
        new CanonicalPlayerAttachmentMigrationStep(1, 2, CanonicalPlayerAttachmentMigrations::migrateV1ToV2);
    private static final CanonicalPlayerAttachmentMigrationChain CHAIN =
        new CanonicalPlayerAttachmentMigrationChain(
            CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION,
            MAX_PAYLOAD_BYTES,
            List.of(V1_TO_V2)
        );

    private CanonicalPlayerAttachmentMigrations() {}

    public static byte[] toCurrent(byte[] encoded) {
        return CHAIN.migrateToCurrent(encoded);
    }

    /**
     * V1 already contains a complete canonical header + Core + compatibility sections. V2 keeps
     * those bytes intact, advances the version header and appends an empty fixed-width cooldown
     * section so old saves cannot accidentally inherit an active cooldown.
     */
    private static byte[] migrateV1ToV2(byte[] encoded) {
        if (encoded.length < Integer.BYTES) {
            throw new IllegalArgumentException("canonical v1 attachment is missing its version header");
        }
        int version = ByteBuffer.wrap(encoded, 0, Integer.BYTES).getInt();
        if (version != 1) {
            throw new IllegalArgumentException("expected canonical attachment version 1 but found " + version);
        }

        byte[] emptyCooldowns = CombatPerkCooldownStateCodec.encode(CombatPerkCooldownState.empty());
        ByteBuffer migrated = ByteBuffer.allocate(encoded.length + Integer.BYTES + emptyCooldowns.length);
        migrated.putInt(2);
        migrated.put(encoded, Integer.BYTES, encoded.length - Integer.BYTES);
        migrated.putInt(emptyCooldowns.length);
        migrated.put(emptyCooldowns);
        return migrated.array();
    }
}
