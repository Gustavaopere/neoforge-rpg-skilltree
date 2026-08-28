package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Persisted economic history for one learned node. */
public final class NodeAllocation {
    public static final int MAX_BATCHES = 16_384;

    private final String nodeId;
    private final List<NodeAllocationBatch> batches;
    private final int rank;
    private final long paidCost;

    private NodeAllocation(String nodeId, List<NodeAllocationBatch> batches) {
        this.nodeId = ProgressionProvenanceId.requireNamespacedId(nodeId, "node id");
        Objects.requireNonNull(batches, "batches");
        if (batches.isEmpty()) throw new IllegalArgumentException("node allocation must contain at least one batch");
        if (batches.size() > MAX_BATCHES) throw new IllegalArgumentException("too many node allocation batches");

        ArrayList<NodeAllocationBatch> normalized = new ArrayList<>(batches.size());
        for (NodeAllocationBatch batch : batches) {
            Objects.requireNonNull(batch, "node allocation batch");
            if (!normalized.isEmpty() && normalized.getLast().sameTerms(batch)) {
                NodeAllocationBatch merged = normalized.removeLast().withAdditionalRanks(batch.rankCount());
                normalized.add(merged);
            } else {
                normalized.add(batch);
            }
        }
        if (normalized.size() > MAX_BATCHES) throw new IllegalArgumentException("too many node allocation batches");

        int computedRank = 0;
        long computedPaidCost = 0L;
        for (NodeAllocationBatch batch : normalized) {
            computedRank = Math.addExact(computedRank, batch.rankCount());
            computedPaidCost = Math.addExact(computedPaidCost, batch.totalPaidCost());
        }

        this.batches = List.copyOf(normalized);
        this.rank = computedRank;
        this.paidCost = computedPaidCost;
    }

    public static NodeAllocation of(String nodeId, List<NodeAllocationBatch> batches) {
        return new NodeAllocation(nodeId, batches);
    }

    public String nodeId() {
        return nodeId;
    }

    public List<NodeAllocationBatch> batches() {
        return batches;
    }

    public int rank() {
        return rank;
    }

    public long paidCost() {
        return paidCost;
    }

    public NodeAllocation acquire(NodeAllocationBatch batch) {
        Objects.requireNonNull(batch, "batch");
        ArrayList<NodeAllocationBatch> next = new ArrayList<>(batches);
        if (!next.isEmpty() && next.getLast().sameTerms(batch)) {
            next.set(next.size() - 1, next.getLast().withAdditionalRanks(batch.rankCount()));
        } else {
            if (next.size() >= MAX_BATCHES) throw new IllegalArgumentException("too many node allocation batches");
            next.add(batch);
        }
        return new NodeAllocation(nodeId, next);
    }

    public NodeRankRefund refundLastRank() {
        NodeAllocationBatch last = batches.getLast();
        NodeAllocationBatch refundedRank = last.oneRank();
        ArrayList<NodeAllocationBatch> next = new ArrayList<>(batches);
        next.removeLast();
        if (last.rankCount() > 1) {
            next.add(new NodeAllocationBatch(
                last.rankCount() - 1,
                last.paidCostPerRank(),
                last.currencyId(),
                last.sourceTreeId(),
                last.provenance(),
                last.rulesVersion()
            ));
        }
        Optional<NodeAllocation> remaining = next.isEmpty()
            ? Optional.empty()
            : Optional.of(new NodeAllocation(nodeId, next));
        return new NodeRankRefund(remaining, refundedRank);
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof NodeAllocation allocation
            && nodeId.equals(allocation.nodeId)
            && batches.equals(allocation.batches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, batches);
    }

    @Override
    public String toString() {
        return "NodeAllocation{" + nodeId + ", rank=" + rank + ", paidCost=" + paidCost + '}';
    }
}
