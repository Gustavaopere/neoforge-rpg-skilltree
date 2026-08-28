package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class PersistedNodeAllocationsCodecTest {
    public static void main(String[] args) {
        roundTripPreservesActiveAndQuarantinedEconomicHistory();
        encodingIsDeterministicAcrossMapInsertionOrder();
        malformedPayloadsFailClosed();
        duplicateOrOverlappingNodeIdsAreRejected();
        System.out.println("PersistedNodeAllocationsCodecTest: PASS");
    }

    private static void roundTripPreservesActiveAndQuarantinedEconomicHistory() {
        PersistedNodeAllocations source = fixture();
        byte[] encoded = PersistedNodeAllocationsCodec.encode(source);
        PersistedNodeAllocations decoded = PersistedNodeAllocationsCodec.decode(encoded);

        eq(source.activeAllocations(), decoded.activeAllocations());
        eq(source.quarantinedAllocations(), decoded.quarantinedAllocations());
        eq(3, decoded.active("rpgskilltree:martial_001").orElseThrow().rank());
        eq(13L, decoded.active("rpgskilltree:martial_001").orElseThrow().paidCost());
        eq("missing_definition", decoded.quarantined("future_provider:removed_node").orElseThrow().reason());
        eq(22L, decoded.quarantined("future_provider:removed_node").orElseThrow().quarantinedAtRulesVersion());
    }

    private static void encodingIsDeterministicAcrossMapInsertionOrder() {
        NodeAllocation martial = martialAllocation();
        NodeAllocation arcane = NodeAllocation.of(
            "rpgskilltree:arcane_001",
            List.of(batch(1, 4L, 9L))
        );
        QuarantinedNodeAllocation removed = removedAllocation();

        LinkedHashMap<String, NodeAllocation> firstActive = new LinkedHashMap<>();
        firstActive.put(martial.nodeId(), martial);
        firstActive.put(arcane.nodeId(), arcane);
        LinkedHashMap<String, NodeAllocation> secondActive = new LinkedHashMap<>();
        secondActive.put(arcane.nodeId(), arcane);
        secondActive.put(martial.nodeId(), martial);

        PersistedNodeAllocations first = PersistedNodeAllocations.of(
            firstActive,
            Map.of(removed.allocation().nodeId(), removed)
        );
        PersistedNodeAllocations second = PersistedNodeAllocations.of(
            secondActive,
            Map.of(removed.allocation().nodeId(), removed)
        );

        if (!Arrays.equals(
            PersistedNodeAllocationsCodec.encode(first),
            PersistedNodeAllocationsCodec.encode(second)
        )) {
            throw new AssertionError("allocation codec must be deterministic across map insertion order");
        }
    }

    private static void malformedPayloadsFailClosed() {
        byte[] valid = PersistedNodeAllocationsCodec.encode(fixture());

        byte[] unsupportedVersion = valid.clone();
        unsupportedVersion[3] = 99;
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(unsupportedVersion));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        trailing[trailing.length - 1] = 1;
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(trailing));

        byte[] truncated = Arrays.copyOf(valid, valid.length - 1);
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(truncated));

        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(null));
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.encode(null));
    }

    private static void duplicateOrOverlappingNodeIdsAreRejected() {
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(payloadWithDuplicateActiveId()));
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(payloadWithActiveAndQuarantinedSameId()));
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(payloadWithTooManyActiveAllocations()));
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocationsCodec.decode(payloadWithTooManyBatches()));
    }

    private static PersistedNodeAllocations fixture() {
        NodeAllocation martial = martialAllocation();
        NodeAllocation arcane = NodeAllocation.of(
            "rpgskilltree:arcane_001",
            List.of(batch(1, 4L, 9L))
        );
        QuarantinedNodeAllocation removed = removedAllocation();
        return PersistedNodeAllocations.of(
            Map.of(martial.nodeId(), martial, arcane.nodeId(), arcane),
            Map.of(removed.allocation().nodeId(), removed)
        );
    }

    private static NodeAllocation martialAllocation() {
        return NodeAllocation.of(
            "rpgskilltree:martial_001",
            List.of(
                batch(2, 3L, 7L),
                batch(1, 7L, 8L)
            )
        );
    }

    private static QuarantinedNodeAllocation removedAllocation() {
        return new QuarantinedNodeAllocation(
            NodeAllocation.of(
                "future_provider:removed_node",
                List.of(new NodeAllocationBatch(
                    2,
                    11L,
                    "rpgskilltree:core_progression",
                    "future_provider:specialist_tree",
                    ProgressionProvenanceId.of("rpgskilltree:legacy_migration"),
                    19L
                ))
            ),
            "missing_definition",
            22L
        );
    }

    private static NodeAllocationBatch batch(int ranks, long paidCostPerRank, long rulesVersion) {
        return new NodeAllocationBatch(
            ranks,
            paidCostPerRank,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            ProgressionProvenanceId.of("rpgskilltree:purchase"),
            rulesVersion
        );
    }

    private static byte[] payloadWithDuplicateActiveId() {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(1);
                out.writeInt(2);
                writeSingleRankAllocation(out, "rpgskilltree:dup");
                writeSingleRankAllocation(out, "rpgskilltree:dup");
                out.writeInt(0);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static byte[] payloadWithActiveAndQuarantinedSameId() {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(1);
                out.writeInt(1);
                writeSingleRankAllocation(out, "rpgskilltree:same");
                out.writeInt(1);
                writeString(out, "missing_definition");
                out.writeLong(2L);
                writeSingleRankAllocation(out, "rpgskilltree:same");
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static byte[] payloadWithTooManyActiveAllocations() {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(1);
                out.writeInt(PersistedNodeAllocations.MAX_ALLOCATIONS + 1);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static byte[] payloadWithTooManyBatches() {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(1);
                out.writeInt(1);
                writeString(out, "rpgskilltree:too_many_batches");
                out.writeInt(NodeAllocation.MAX_BATCHES + 1);
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static void writeSingleRankAllocation(DataOutputStream out, String nodeId) throws IOException {
        writeString(out, nodeId);
        out.writeInt(1);
        out.writeInt(1);
        out.writeLong(1L);
        writeString(out, "rpgskilltree:core_progression");
        writeString(out, "rpgskilltree:main_tree");
        writeString(out, "rpgskilltree:purchase");
        out.writeLong(1L);
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(encoded.length);
        out.write(encoded);
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
