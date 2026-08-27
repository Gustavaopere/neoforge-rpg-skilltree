package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;

/** Binary envelope codec for the optional/uninitialized Core player attachment. */
public final class CoreProgressionAttachmentDataCodec {
    private static final byte UNINITIALIZED = 0;
    private static final byte INITIALIZED = 1;

    private CoreProgressionAttachmentDataCodec() {}

    public static byte[] encode(CoreProgressionAttachmentData attachment) {
        if (attachment == null) throw new IllegalArgumentException("attachment must not be null");
        if (!attachment.isInitialized()) return new byte[] {UNINITIALIZED};

        CoreProgressionState state = attachment.state().orElseThrow();
        byte[] core = CoreProgressionStateCodec.encode(state);
        byte[] encoded = new byte[Math.addExact(core.length, 1)];
        encoded[0] = INITIALIZED;
        System.arraycopy(core, 0, encoded, 1, core.length);
        return encoded;
    }

    public static CoreProgressionAttachmentData decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded attachment must not be null");
        if (encoded.length == 0) throw new IllegalArgumentException("encoded attachment must not be empty");

        return switch (encoded[0]) {
            case UNINITIALIZED -> {
                if (encoded.length != 1) {
                    throw new IllegalArgumentException("uninitialized attachment contains trailing bytes");
                }
                yield CoreProgressionAttachmentData.uninitialized();
            }
            case INITIALIZED -> {
                if (encoded.length == 1) {
                    throw new IllegalArgumentException("initialized attachment is missing Core state payload");
                }
                CoreProgressionState state = CoreProgressionStateCodec.decode(
                    Arrays.copyOfRange(encoded, 1, encoded.length)
                );
                yield CoreProgressionAttachmentData.initialized(state);
            }
            default -> throw new IllegalArgumentException(
                "unknown Core progression attachment marker: " + encoded[0]
            );
        };
    }
}
