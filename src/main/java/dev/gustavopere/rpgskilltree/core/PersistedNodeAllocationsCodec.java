package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Versioned deterministic binary codec for persisted v5 node allocations. */
public final class PersistedNodeAllocationsCodec {
    public static final int CURRENT_VERSION = 1;
    private static final int MAX_STRING_BYTES = 4_096;

    private PersistedNodeAllocationsCodec() {}

    public static byte[] encode(PersistedNodeAllocations allocations) {
        if (allocations == null) throw new IllegalArgumentException("allocations must not be null");
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);

                List<String> activeIds = allocations.activeAllocations().keySet().stream().sorted().toList();
                if (activeIds.size() > PersistedNodeAllocations.MAX_ALLOCATIONS) {
                    throw new IllegalArgumentException("too many active node allocations");
                }
                out.writeInt(activeIds.size());
                for (String nodeId : activeIds) {
                    writeAllocation(out, allocations.activeAllocations().get(nodeId));
                }

                List<String> quarantinedIds = allocations.quarantinedAllocations().keySet().stream().sorted().toList();
                if (Math.addExact(activeIds.size(), quarantinedIds.size()) > PersistedNodeAllocations.MAX_ALLOCATIONS) {
                    throw new IllegalArgumentException("too many persisted node allocations");
                }
                out.writeInt(quarantinedIds.size());
                for (String nodeId : quarantinedIds) {
                    QuarantinedNodeAllocation quarantined = allocations.quarantinedAllocations().get(nodeId);
                    writeString(out, quarantined.reason());
                    out.writeLong(quarantined.quarantinedAtRulesVersion());
                    writeAllocation(out, quarantined.allocation());
                }
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("failed to encode persisted node allocations", exception);
        }
    }

    public static PersistedNodeAllocations decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded allocations must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported persisted node allocations version: " + version);
            }

            int activeCount = readCount(in, PersistedNodeAllocations.MAX_ALLOCATIONS, "active allocation");
            Map<String, NodeAllocation> active = new HashMap<>();
            for (int index = 0; index < activeCount; index++) {
                NodeAllocation allocation = readAllocation(in);
                if (active.put(allocation.nodeId(), allocation) != null) {
                    throw new IllegalArgumentException("duplicate active node allocation: " + allocation.nodeId());
                }
            }

            int remainingCapacity = PersistedNodeAllocations.MAX_ALLOCATIONS - activeCount;
            int quarantinedCount = readCount(in, remainingCapacity, "quarantined allocation");
            Map<String, QuarantinedNodeAllocation> quarantined = new HashMap<>();
            for (int index = 0; index < quarantinedCount; index++) {
                String reason = readString(in);
                long quarantinedAtRulesVersion = in.readLong();
                NodeAllocation allocation = readAllocation(in);
                QuarantinedNodeAllocation value = new QuarantinedNodeAllocation(
                    allocation,
                    reason,
                    quarantinedAtRulesVersion
                );
                if (quarantined.put(allocation.nodeId(), value) != null) {
                    throw new IllegalArgumentException("duplicate quarantined node allocation: " + allocation.nodeId());
                }
            }

            if (in.available() != 0) {
                throw new IllegalArgumentException("persisted node allocations payload contains trailing bytes");
            }
            return PersistedNodeAllocations.of(active, quarantined);
        } catch (EOFException exception) {
            throw new IllegalArgumentException("truncated persisted node allocations payload", exception);
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid persisted node allocations payload", exception);
        }
    }

    private static void writeAllocation(DataOutputStream out, NodeAllocation allocation) throws IOException {
        writeString(out, allocation.nodeId());
        List<NodeAllocationBatch> batches = allocation.batches();
        if (batches.isEmpty() || batches.size() > NodeAllocation.MAX_BATCHES) {
            throw new IllegalArgumentException("invalid node allocation batch count: " + batches.size());
        }
        out.writeInt(batches.size());
        for (NodeAllocationBatch batch : batches) {
            out.writeInt(batch.rankCount());
            out.writeLong(batch.paidCostPerRank());
            writeString(out, batch.currencyId());
            writeString(out, batch.sourceTreeId());
            writeString(out, batch.provenance().serializedId());
            out.writeLong(batch.rulesVersion());
        }
    }

    private static NodeAllocation readAllocation(DataInputStream in) throws IOException {
        String nodeId = readString(in);
        int batchCount = readCount(in, NodeAllocation.MAX_BATCHES, "node allocation batch");
        if (batchCount == 0) throw new IllegalArgumentException("node allocation must contain at least one batch");
        List<NodeAllocationBatch> batches = new ArrayList<>(batchCount);
        for (int index = 0; index < batchCount; index++) {
            int rankCount = in.readInt();
            long paidCostPerRank = in.readLong();
            String currencyId = readString(in);
            String sourceTreeId = readString(in);
            ProgressionProvenanceId provenance = ProgressionProvenanceId.of(readString(in));
            long rulesVersion = in.readLong();
            batches.add(new NodeAllocationBatch(
                rankCount,
                paidCostPerRank,
                currencyId,
                sourceTreeId,
                provenance,
                rulesVersion
            ));
        }
        return NodeAllocation.of(nodeId, batches);
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
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);
        if (encoded.length > MAX_STRING_BYTES) throw new IllegalArgumentException("serialized string is too long");
        out.writeInt(encoded.length);
        out.write(encoded);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("invalid serialized string length: " + length);
        }
        byte[] encoded = in.readNBytes(length);
        if (encoded.length != length) throw new EOFException("truncated serialized string");
        String value = new String(encoded, StandardCharsets.UTF_8);
        if (value.isBlank()) throw new IllegalArgumentException("serialized string must not be blank");
        return value;
    }
}
