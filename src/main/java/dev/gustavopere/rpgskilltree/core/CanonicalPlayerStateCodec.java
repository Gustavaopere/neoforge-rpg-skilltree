package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/** Versioned outer codec composing canonical and compatibility player-state sections. */
public final class CanonicalPlayerStateCodec {
    public static final int CURRENT_VERSION = 3;
    private static final int MAX_SECTION_BYTES = 16 * 1024 * 1024;
    private static final int MAX_ID_BYTES = 4_096;

    private CanonicalPlayerStateCodec() {}

    public static byte[] encode(CanonicalPlayerState state) {
        if (state == null) throw new IllegalArgumentException("state must not be null");
        byte[] core = CoreProgressionStateCodec.encode(state.coreProgression());
        byte[] compatibility = ProgressionStateCodec.encode(state.compatibilityProgression());
        byte[] origin = encodeOriginClass(state.originClass());
        validateSectionLength(core.length, "Core progression");
        validateSectionLength(compatibility.length, "compatibility progression");
        validateSectionLength(origin.length, "origin class");

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                16 + core.length + compatibility.length + origin.length
            );
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                writeSection(out, core);
                writeSection(out, compatibility);
                writeSection(out, origin);
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
            if (version < 1 || version > CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported canonical player state version: " + version);
            }
            byte[] core = readSection(in, "Core progression");
            byte[] compatibility = readSection(in, "compatibility progression");
            OriginClassState origin = version >= 3
                ? decodeOriginClass(readSection(in, "origin class"))
                : OriginClassState.empty();
            if (in.available() != 0) {
                throw new IllegalArgumentException("canonical player state contains trailing bytes");
            }
            return new CanonicalPlayerState(
                CoreProgressionStateCodec.decode(core),
                ProgressionStateCodec.decode(compatibility),
                origin
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid canonical player state payload", exception);
        }
    }

    private static byte[] encodeOriginClass(OriginClassState state) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                if (state.selectedClassId().isEmpty()) {
                    out.writeBoolean(false);
                } else {
                    out.writeBoolean(true);
                    writeId(out, state.selectedClassId().orElseThrow());
                }
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static OriginClassState decodeOriginClass(byte[] encoded) {
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            boolean selected = in.readBoolean();
            OriginClassState state = selected
                ? OriginClassState.empty().select(readId(in))
                : OriginClassState.empty();
            if (in.available() != 0) {
                throw new IllegalArgumentException("origin class section contains trailing bytes");
            }
            return state;
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid origin class section", exception);
        }
    }

    private static void writeId(DataOutputStream out, String value) throws IOException {
        byte[] bytes = ProgressionProvenanceId.requireNamespacedId(value, "origin class id")
            .getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_ID_BYTES) {
            throw new IllegalArgumentException("origin class id too long");
        }
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readId(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_ID_BYTES) {
            throw new IllegalArgumentException("invalid origin class id length: " + length);
        }
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) {
            throw new IllegalArgumentException("truncated origin class id");
        }
        return ProgressionProvenanceId.requireNamespacedId(
            new String(bytes, StandardCharsets.UTF_8),
            "origin class id"
        );
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
