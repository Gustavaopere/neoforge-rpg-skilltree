package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.util.HexFormat;

/** Binary codec for the compact client-facing Core progression projection. */
public final class CoreProgressionSyncStateCodec {
    public static final int CURRENT_VERSION = 1;
    private static final int FINGERPRINT_BYTES = 32;
    private static final int MAX_BIG_INTEGER_BYTES = 128;

    private CoreProgressionSyncStateCodec() {}

    public static byte[] encode(CoreProgressionSyncState state) {
        if (state == null) throw new IllegalArgumentException("sync state must not be null");
        byte[] nextXp = state.xpToNextLevel().toByteArray();
        if (nextXp.length == 0 || nextXp.length > MAX_BIG_INTEGER_BYTES) {
            throw new IllegalArgumentException("next-level XP representation is too large");
        }
        byte[] fingerprint;
        try {
            fingerprint = HexFormat.of().parseHex(state.rulesFingerprint());
        } catch (IllegalArgumentException invalidHex) {
            throw new IllegalArgumentException("invalid rules fingerprint", invalidHex);
        }
        if (fingerprint.length != FINGERPRINT_BYTES) {
            throw new IllegalArgumentException("rules fingerprint must contain 32 bytes");
        }

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(128);
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                out.writeLong(state.level());
                out.writeLong(state.xpIntoLevel());
                out.writeInt(nextXp.length);
                out.write(nextXp);
                out.writeLong(state.totalCorePoints());
                out.writeLong(state.attributeAllocated());
                out.writeLong(state.mainPerkAllocated());
                out.writeLong(state.availableCorePoints());
                out.writeLong(state.mainPerkBudget());
                out.writeLong(state.rulesVersion());
                out.write(fingerprint);
            }
            return bytes.toByteArray();
        } catch (IOException impossible) {
            throw new UncheckedIOException(impossible);
        }
    }

    public static CoreProgressionSyncState decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded sync state must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported Core progression sync version: " + version);
            }
            long level = in.readLong();
            long xpIntoLevel = in.readLong();
            int nextXpLength = in.readInt();
            if (nextXpLength <= 0 || nextXpLength > MAX_BIG_INTEGER_BYTES) {
                throw new IllegalArgumentException("invalid next-level XP byte length: " + nextXpLength);
            }
            byte[] nextXpBytes = in.readNBytes(nextXpLength);
            if (nextXpBytes.length != nextXpLength) throw new EOFException("truncated next-level XP");
            BigInteger xpToNextLevel = new BigInteger(nextXpBytes);

            long totalCorePoints = in.readLong();
            long attributeAllocated = in.readLong();
            long mainPerkAllocated = in.readLong();
            long availableCorePoints = in.readLong();
            long mainPerkBudget = in.readLong();
            long rulesVersion = in.readLong();

            byte[] fingerprint = in.readNBytes(FINGERPRINT_BYTES);
            if (fingerprint.length != FINGERPRINT_BYTES) throw new EOFException("truncated rules fingerprint");
            if (in.available() != 0) {
                throw new IllegalArgumentException("Core progression sync payload contains trailing bytes");
            }

            return new CoreProgressionSyncState(
                level,
                xpIntoLevel,
                xpToNextLevel,
                totalCorePoints,
                attributeAllocated,
                mainPerkAllocated,
                availableCorePoints,
                mainPerkBudget,
                rulesVersion,
                HexFormat.of().formatHex(fingerprint)
            );
        } catch (IOException invalidPayload) {
            throw new IllegalArgumentException("invalid Core progression sync payload", invalidPayload);
        }
    }
}
