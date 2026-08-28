package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/** Versioned codec for the single optional-Core canonical player attachment. */
public final class CanonicalPlayerAttachmentDataCodec {
    public static final int CURRENT_VERSION = 1;
    private static final int MAX_SECTION_BYTES = 16 * 1024 * 1024;

    private CanonicalPlayerAttachmentDataCodec() {}

    public static byte[] encode(CanonicalPlayerAttachmentData attachment) {
        if (attachment == null) throw new IllegalArgumentException("attachment must not be null");
        byte[] core = CoreProgressionAttachmentDataCodec.encode(attachment.coreProgression());
        byte[] compatibility = ProgressionStateCodec.encode(attachment.compatibilityProgression());
        validateSectionLength(core.length, "Core progression attachment");
        validateSectionLength(compatibility.length, "compatibility progression");

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                13 + core.length + compatibility.length
            );
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                out.writeBoolean(attachment.hasLegacyMigrationSource());
                writeSection(out, core);
                writeSection(out, compatibility);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static CanonicalPlayerAttachmentData decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded attachment must not be null");
        byte[] current = CanonicalPlayerAttachmentMigrations.toCurrent(encoded);
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(current))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException(
                    "canonical migration registry did not normalize payload to current version: " + version
                );
            }
            boolean legacySource = in.readBoolean();
            byte[] core = readSection(in, "Core progression attachment");
            byte[] compatibility = readSection(in, "compatibility progression");
            if (in.available() != 0) {
                throw new IllegalArgumentException("canonical player attachment contains trailing bytes");
            }
            return new CanonicalPlayerAttachmentData(
                CoreProgressionAttachmentDataCodec.decode(core),
                ProgressionStateCodec.decode(compatibility),
                legacySource
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid canonical player attachment payload", exception);
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
