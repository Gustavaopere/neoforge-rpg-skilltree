package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/** Versioned outer codec composing the Core and compatibility state codecs. */
public final class CanonicalPlayerStateCodec {
    public static final int CURRENT_VERSION = 1;
    private static final int MAX_SECTION_BYTES = 16 * 1024 * 1024;

    private CanonicalPlayerStateCodec() {}

    public static byte[] encode(CanonicalPlayerState state) {
        if (state == null) throw new IllegalArgumentException("state must not be null");
        byte[] core = CoreProgressionStateCodec.encode(state.coreProgression());
        byte[] compatibility = ProgressionStateCodec.encode(state.compatibilityProgression());
        validateSectionLength(core.length, "Core progression");
        validateSectionLength(compatibility.length, "compatibility progression");

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                12 + core.length + compatibility.length
            );
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                writeSection(out, core);
                writeSection(out, compatibility);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static CanonicalPlayerState decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded state must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported canonical player state version: " + version);
            }
            byte[] core = readSection(in, "Core progression");
            byte[] compatibility = readSection(in, "compatibility progression");
            if (in.available() != 0) {
                throw new IllegalArgumentException("canonical player state contains trailing bytes");
            }
            return new CanonicalPlayerState(
                CoreProgressionStateCodec.decode(core),
                ProgressionStateCodec.decode(compatibility)
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid canonical player state payload", exception);
        }
    }

    private static void writeSection(DataOutputStream out, byte[] bytes) throws IOException {
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static byte[] readSection(DataInputStream in, String label) throws IOException {
        int length = in.readInt();
        validateSectionLength(length, label);
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) {
            throw new IllegalArgumentException("truncated " + label + " section");
        }
        return bytes;
    }

    private static void validateSectionLength(int length, String label) {
        if (length <= 0 || length > MAX_SECTION_BYTES) {
            throw new IllegalArgumentException("invalid " + label + " section length: " + length);
        }
    }
}
