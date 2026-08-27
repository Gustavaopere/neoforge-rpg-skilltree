package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

/** Binary codec for the compact client-facing Core progression projection. */
public final class CoreProgressionSyncStateCodec {
    public static final int CURRENT_VERSION = 2;
    private static final int FINGERPRINT_BYTES = 32;
    private static final int MAX_BIG_INTEGER_BYTES = 128;
    private static final int MAX_ATTRIBUTE_ID_BYTES = 64;

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
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(192);
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
                writeAttributeRanks(out, state.attributeRanks());
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
            AttributeRanks attributeRanks = readAttributeRanks(in);
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
                attributeRanks,
                rulesVersion,
                HexFormat.of().formatHex(fingerprint)
            );
        } catch (IOException invalidPayload) {
            throw new IllegalArgumentException("invalid Core progression sync payload", invalidPayload);
        }
    }

    private static void writeAttributeRanks(DataOutputStream out, AttributeRanks ranks) throws IOException {
        List<Map.Entry<AttributeId, Long>> nonZero = ranks.asMap().entrySet().stream()
            .filter(entry -> entry.getValue() > 0L)
            .sorted(Map.Entry.comparingByKey((left, right) -> left.serializedId().compareTo(right.serializedId())))
            .toList();
        out.writeInt(nonZero.size());
        for (Map.Entry<AttributeId, Long> entry : nonZero) {
            writeAttributeId(out, entry.getKey().serializedId());
            out.writeLong(entry.getValue());
        }
    }

    private static AttributeRanks readAttributeRanks(DataInputStream in) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > AttributeId.values().length) {
            throw new IllegalArgumentException("invalid attribute rank count: " + count);
        }
        EnumMap<AttributeId, Long> ranks = new EnumMap<>(AttributeId.class);
        for (int i = 0; i < count; i++) {
            AttributeId attribute = parseAttributeId(readAttributeId(in));
            long rank = in.readLong();
            if (rank <= 0L) throw new IllegalArgumentException("synced attribute rank must be positive");
            if (ranks.put(attribute, rank) != null) {
                throw new IllegalArgumentException("duplicate synced attribute rank: " + attribute.serializedId());
            }
        }
        return AttributeRanks.of(ranks);
    }

    private static void writeAttributeId(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length == 0 || bytes.length > MAX_ATTRIBUTE_ID_BYTES) {
            throw new IllegalArgumentException("invalid attribute id length");
        }
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readAttributeId(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_ATTRIBUTE_ID_BYTES) {
            throw new IllegalArgumentException("invalid attribute id length: " + length);
        }
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) throw new EOFException("truncated attribute id");
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static AttributeId parseAttributeId(String serializedId) {
        for (AttributeId attribute : AttributeId.values()) {
            if (attribute.serializedId().equals(serializedId)) return attribute;
        }
        throw new IllegalArgumentException("unknown attribute id: " + serializedId);
    }
}
