package dev.gustavopere.rpgskilltree.core;

import java.util.List;

/**
 * Canonical registry for outer player-attachment schema migrations.
 *
 * <p>Version 1 is the first canonical envelope, so no historical outer-envelope
 * migrations exist yet. When CURRENT_VERSION advances, every released N -> N+1
 * step must be registered here before the new schema ships.</p>
 */
public final class CanonicalPlayerAttachmentMigrations {
    private static final int MAX_PAYLOAD_BYTES = (32 * 1024 * 1024) + 64;
    private static final CanonicalPlayerAttachmentMigrationChain CHAIN =
        new CanonicalPlayerAttachmentMigrationChain(
            CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION,
            MAX_PAYLOAD_BYTES,
            List.of()
        );

    private CanonicalPlayerAttachmentMigrations() {}

    public static byte[] toCurrent(byte[] encoded) {
        return CHAIN.migrateToCurrent(encoded);
    }
}
