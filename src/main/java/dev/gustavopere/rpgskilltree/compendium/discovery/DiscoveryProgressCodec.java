package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Strict bounded binary codec for per-player Compendium discovery progress. */
public final class DiscoveryProgressCodec {
    public static final int CURRENT_VERSION = 1;
    public static final int MAX_PAYLOAD_BYTES = 64 * 1024 * 1024;

    private static final int MAX_RECORDS = 100_000;
    private static final int MAX_COLLECTION_VALUES = 4_096;
    private static final int MAX_STRING_BYTES = 1_024;

    private DiscoveryProgressCodec() {}

    public static byte[] encode(DiscoveryProgress progress) {
        if (progress == null) throw new IllegalArgumentException("progress must not be null");
        if (progress.records().size() > MAX_RECORDS) {
            throw new IllegalArgumentException("too many discovery records: " + progress.records().size());
        }

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(buffer)) {
                out.writeInt(CURRENT_VERSION);
                List<DiscoveryRecord> records = new ArrayList<>(progress.records().values());
                records.sort(Comparator.comparing(record -> record.entryId().serializedId()));
                out.writeInt(records.size());
                for (DiscoveryRecord record : records) writeRecord(out, record);
            }
            byte[] payload = buffer.toByteArray();
            if (payload.length > MAX_PAYLOAD_BYTES) {
                throw new IllegalArgumentException("discovery payload exceeds maximum size: " + payload.length);
            }
            return payload;
        } catch (IOException exception) {
            throw new IllegalStateException("failed to encode discovery progress", exception);
        }
    }

    public static DiscoveryProgress decode(byte[] payload) {
        if (payload == null) throw new IllegalArgumentException("payload must not be null");
        if (payload.length > MAX_PAYLOAD_BYTES) {
            throw new IllegalArgumentException("discovery payload exceeds maximum size: " + payload.length);
        }

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(payload))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported discovery progress version: " + version);
            }

            int recordCount = readCount(in, MAX_RECORDS, "record");
            LinkedHashMap<CompendiumEntryId, DiscoveryRecord> records = new LinkedHashMap<>(Math.min(recordCount, 16_384));
            for (int i = 0; i < recordCount; i++) {
                DiscoveryRecord record = readRecord(in);
                if (records.put(record.entryId(), record) != null) {
                    throw new IllegalArgumentException("duplicate discovery record: " + record.entryId().serializedId());
                }
            }
            if (in.available() != 0) {
                throw new IllegalArgumentException("discovery progress contains trailing bytes");
            }
            return records.isEmpty() ? DiscoveryProgress.empty() : new DiscoveryProgress(records);
        } catch (EOFException exception) {
            throw new IllegalArgumentException("truncated discovery progress payload", exception);
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid discovery progress payload", exception);
        }
    }

    private static void writeRecord(DataOutputStream out, DiscoveryRecord record) throws IOException {
        writeString(out, record.entryId().serializedId());
        out.writeInt(record.state().ordinal());
        out.writeLong(record.firstGameTime());
        out.writeBoolean(record.firstOrigin().isPresent());
        if (record.firstOrigin().isPresent()) {
            DiscoveryOrigin origin = record.firstOrigin().orElseThrow();
            writeString(out, origin.dimensionId());
            out.writeInt(origin.chunkX());
            out.writeInt(origin.chunkZ());
        }
        writeStringSet(out, record.variantIds(), "variant");
        writeStringSet(out, record.completedObjectiveIds(), "objective");
        writeStringSet(out, record.claimedRewardIds(), "reward claim");
    }

    private static DiscoveryRecord readRecord(DataInputStream in) throws IOException {
        CompendiumEntryId entryId = CompendiumEntryId.parse(readString(in));
        int stateOrdinal = in.readInt();
        DiscoveryState[] states = DiscoveryState.values();
        if (stateOrdinal <= DiscoveryState.UNKNOWN.ordinal() || stateOrdinal >= states.length) {
            throw new IllegalArgumentException("invalid persisted discovery state ordinal: " + stateOrdinal);
        }
        DiscoveryState state = states[stateOrdinal];
        long firstGameTime = in.readLong();
        Optional<DiscoveryOrigin> origin = Optional.empty();
        if (in.readBoolean()) {
            origin = Optional.of(new DiscoveryOrigin(readString(in), in.readInt(), in.readInt()));
        }
        Set<String> variants = readStringSet(in, "variant");
        Set<String> objectives = readStringSet(in, "objective");
        Set<String> claims = readStringSet(in, "reward claim");
        return new DiscoveryRecord(entryId, state, firstGameTime, origin, variants, objectives, claims);
    }

    private static void writeStringSet(DataOutputStream out, Set<String> values, String label) throws IOException {
        if (values.size() > MAX_COLLECTION_VALUES) {
            throw new IllegalArgumentException("too many discovery " + label + " values: " + values.size());
        }
        List<String> sorted = values.stream().sorted().toList();
        out.writeInt(sorted.size());
        for (String value : sorted) writeString(out, value);
    }

    private static Set<String> readStringSet(DataInputStream in, String label) throws IOException {
        int count = readCount(in, MAX_COLLECTION_VALUES, label);
        LinkedHashSet<String> values = new LinkedHashSet<>(Math.min(count, 1_024));
        for (int i = 0; i < count; i++) {
            String value = readString(in);
            if (!values.add(value)) {
                throw new IllegalArgumentException("duplicate discovery " + label + " value: " + value);
            }
        }
        return Set.copyOf(values);
    }

    private static int readCount(DataInputStream in, int maximum, String label) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > maximum) {
            throw new IllegalArgumentException("invalid discovery " + label + " count: " + count);
        }
        return count;
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("serialized discovery string must not be blank");
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("serialized discovery string exceeds maximum bytes: " + bytes.length);
        }
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("invalid serialized discovery string length: " + length);
        }
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) throw new EOFException("truncated serialized discovery string");
        final String value;
        try {
            value = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(bytes))
                .toString();
        } catch (CharacterCodingException exception) {
            throw new IllegalArgumentException("invalid UTF-8 in discovery payload", exception);
        }
        if (value.isBlank()) throw new IllegalArgumentException("serialized discovery string must not be blank");
        return value;
    }
}
