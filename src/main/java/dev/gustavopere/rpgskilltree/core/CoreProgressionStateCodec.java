package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Versioned binary codec for the uncapped RPG Core foundation state. */
public final class CoreProgressionStateCodec {
    public static final int CURRENT_VERSION = 2;
    private static final int LEGACY_VERSION_WITHOUT_ATTRIBUTES = 1;
    private static final int MAX_STRING_BYTES = 4_096;
    private static final int MAX_CREDIT_SOURCES = 16_384;

    private CoreProgressionStateCodec() {}

    public static byte[] encode(CoreProgressionState state) {
        if (state == null) throw new IllegalArgumentException("state must not be null");
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                out.writeLong(state.characterProgression().level());
                out.writeLong(state.characterProgression().xpIntoLevel());
                out.writeLong(state.rulesVersion());
                writeString(out, state.rulesFingerprint());
                out.writeInt(state.migrationSourceFormatVersion());
                out.writeLong(state.discardedLegacyCapXp());
                writeCheckpoint(out, state.corePoints().checkpoint());
                writeAttributeRanks(out, state.attributeRanks());
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static CoreProgressionState decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded state must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            int version = in.readInt();
            if (version != LEGACY_VERSION_WITHOUT_ATTRIBUTES && version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported core progression state version: " + version);
            }
            CharacterProgressionState character = new CharacterProgressionState(in.readLong(), in.readLong());
            long rulesVersion = in.readLong();
            String fingerprint = readString(in);
            int migrationSourceVersion = in.readInt();
            long discardedLegacyCapXp = in.readLong();
            CorePointLedger ledger = CorePointLedger.restore(readCheckpoint(in));
            AttributeRanks attributeRanks = version >= 2 ? readAttributeRanks(in) : AttributeRanks.empty();
            if (in.available() != 0) {
                throw new IllegalArgumentException("core progression state contains trailing bytes");
            }
            return new CoreProgressionState(
                character,
                ledger,
                attributeRanks,
                rulesVersion,
                fingerprint,
                migrationSourceVersion,
                discardedLegacyCapXp
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid core progression state payload", exception);
        }
    }

    private static void writeAttributeRanks(DataOutputStream out, AttributeRanks ranks) throws IOException {
        List<Map.Entry<AttributeId, Long>> nonZero = ranks.asMap().entrySet().stream()
            .filter(entry -> entry.getValue() > 0L)
            .sorted(Map.Entry.comparingByKey((left, right) -> left.serializedId().compareTo(right.serializedId())))
            .toList();
        out.writeInt(nonZero.size());
        for (Map.Entry<AttributeId, Long> entry : nonZero) {
            writeString(out, entry.getKey().serializedId());
            out.writeLong(entry.getValue());
        }
    }

    private static AttributeRanks readAttributeRanks(DataInputStream in) throws IOException {
        int count = readCount(in, AttributeId.values().length, "attribute rank");
        EnumMap<AttributeId, Long> ranks = new EnumMap<>(AttributeId.class);
        for (int i = 0; i < count; i++) {
            AttributeId attribute = parseAttributeId(readString(in));
            long rank = in.readLong();
            if (rank <= 0L) {
                throw new IllegalArgumentException("persisted attribute rank must be positive");
            }
            if (ranks.put(attribute, rank) != null) {
                throw new IllegalArgumentException("duplicate persisted attribute rank: " + attribute.serializedId());
            }
        }
        return AttributeRanks.of(ranks);
    }

    private static AttributeId parseAttributeId(String serializedId) {
        for (AttributeId attribute : AttributeId.values()) {
            if (attribute.serializedId().equals(serializedId)) return attribute;
        }
        throw new IllegalArgumentException("unknown attribute id: " + serializedId);
    }

    private static void writeCheckpoint(DataOutputStream out, CorePointLedgerCheckpoint checkpoint) throws IOException {
        List<String> sourceIds = checkpoint.creditTotalsBySource().keySet().stream().sorted().toList();
        if (sourceIds.size() > MAX_CREDIT_SOURCES) {
            throw new IllegalArgumentException("too many Core Point credit provenance sources");
        }
        out.writeInt(sourceIds.size());
        for (String sourceId : sourceIds) {
            writeString(out, sourceId);
            out.writeLong(checkpoint.creditTotalsBySource().get(sourceId));
        }

        out.writeLong(checkpoint.allocated(CorePointAllocation.ATTRIBUTE));
        out.writeLong(checkpoint.allocated(CorePointAllocation.MAIN_PERK));

        List<CorePointTransaction> recent = checkpoint.recentTransactions();
        out.writeInt(recent.size());
        for (CorePointTransaction transaction : recent) {
            writeString(out, transaction.transactionId());
            writeString(out, transaction.kind().name());
            out.writeLong(transaction.amount());
            writeString(out, transaction.sourceId());
            writeString(out, transaction.allocation().name());
            out.writeLong(transaction.rulesVersion());
        }
    }

    private static CorePointLedgerCheckpoint readCheckpoint(DataInputStream in) throws IOException {
        int sourceCount = readCount(in, MAX_CREDIT_SOURCES, "credit source");
        Map<String, Long> creditsBySource = new HashMap<>();
        for (int i = 0; i < sourceCount; i++) {
            String sourceId = readString(in);
            long amount = in.readLong();
            if (amount <= 0L) throw new IllegalArgumentException("credit source total must be positive");
            if (creditsBySource.put(sourceId, amount) != null) {
                throw new IllegalArgumentException("duplicate credit source id: " + sourceId);
            }
        }

        long attributeAllocated = in.readLong();
        long mainPerkAllocated = in.readLong();
        if (attributeAllocated < 0L || mainPerkAllocated < 0L) {
            throw new IllegalArgumentException("persisted allocation totals must be non-negative");
        }
        Map<CorePointAllocation, Long> allocated = new HashMap<>();
        if (attributeAllocated > 0L) allocated.put(CorePointAllocation.ATTRIBUTE, attributeAllocated);
        if (mainPerkAllocated > 0L) allocated.put(CorePointAllocation.MAIN_PERK, mainPerkAllocated);

        int recentCount = readCount(in, CorePointLedger.RECENT_TRANSACTION_LIMIT, "recent transaction");
        List<CorePointTransaction> recent = new ArrayList<>(recentCount);
        for (int i = 0; i < recentCount; i++) {
            String transactionId = readString(in);
            CorePointTransactionKind kind = parseTransactionKind(readString(in));
            long amount = in.readLong();
            String sourceId = readString(in);
            CorePointAllocation allocation = parseAllocation(readString(in));
            long transactionRulesVersion = in.readLong();
            recent.add(new CorePointTransaction(
                transactionId,
                kind,
                amount,
                sourceId,
                allocation,
                transactionRulesVersion
            ));
        }
        return new CorePointLedgerCheckpoint(creditsBySource, allocated, recent);
    }

    private static CorePointTransactionKind parseTransactionKind(String name) {
        try {
            return CorePointTransactionKind.valueOf(name);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown Core Point transaction kind: " + name, exception);
        }
    }

    private static CorePointAllocation parseAllocation(String name) {
        try {
            return CorePointAllocation.valueOf(name);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown Core Point allocation: " + name, exception);
        }
    }

    private static int readCount(DataInputStream in, int maximum, String label) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > maximum) {
            throw new IllegalArgumentException("invalid " + label + " count: " + count);
        }
        return count;
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("serialized string must not be blank");
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) throw new IllegalArgumentException("serialized string too long");
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("invalid serialized string length: " + length);
        }
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) throw new IllegalArgumentException("truncated serialized string");
        String value = new String(bytes, StandardCharsets.UTF_8);
        if (value.isBlank()) throw new IllegalArgumentException("serialized string must not be blank");
        return value;
    }
}
