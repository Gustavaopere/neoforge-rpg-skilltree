package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Immutable persisted partition of active versus quarantined v5 node allocations. */
public final class PersistedNodeAllocations {
    public static final int MAX_ALLOCATIONS = 16_384;

    private final Map<String, NodeAllocation> active;
    private final Map<String, QuarantinedNodeAllocation> quarantined;

    private PersistedNodeAllocations(
        Map<String, NodeAllocation> active,
        Map<String, QuarantinedNodeAllocation> quarantined
    ) {
        Objects.requireNonNull(active, "active");
        Objects.requireNonNull(quarantined, "quarantined");
        if (Math.addExact(active.size(), quarantined.size()) > MAX_ALLOCATIONS) {
            throw new IllegalArgumentException("too many persisted node allocations");
        }

        HashMap<String, NodeAllocation> activeCopy = new HashMap<>();
        for (Map.Entry<String, NodeAllocation> entry : active.entrySet()) {
            String key = ProgressionProvenanceId.requireNamespacedId(entry.getKey(), "node id");
            NodeAllocation allocation = Objects.requireNonNull(entry.getValue(), "active allocation");
            if (!key.equals(allocation.nodeId())) {
                throw new IllegalArgumentException("active allocation key does not match node id: " + key);
            }
            activeCopy.put(key, allocation);
        }

        HashMap<String, QuarantinedNodeAllocation> quarantineCopy = new HashMap<>();
        for (Map.Entry<String, QuarantinedNodeAllocation> entry : quarantined.entrySet()) {
            String key = ProgressionProvenanceId.requireNamespacedId(entry.getKey(), "node id");
            QuarantinedNodeAllocation allocation = Objects.requireNonNull(entry.getValue(), "quarantined allocation");
            if (!key.equals(allocation.allocation().nodeId())) {
                throw new IllegalArgumentException("quarantine allocation key does not match node id: " + key);
            }
            if (activeCopy.containsKey(key)) {
                throw new IllegalArgumentException("node allocation cannot be active and quarantined: " + key);
            }
            quarantineCopy.put(key, allocation);
        }

        this.active = Map.copyOf(activeCopy);
        this.quarantined = Map.copyOf(quarantineCopy);
    }

    public static PersistedNodeAllocations empty() {
        return new PersistedNodeAllocations(Map.of(), Map.of());
    }

    public static PersistedNodeAllocations of(
        Map<String, NodeAllocation> active,
        Map<String, QuarantinedNodeAllocation> quarantined
    ) {
        return new PersistedNodeAllocations(active, quarantined);
    }

    public Optional<NodeAllocation> active(String nodeId) {
        Objects.requireNonNull(nodeId, "nodeId");
        return Optional.ofNullable(active.get(nodeId));
    }

    public Optional<QuarantinedNodeAllocation> quarantined(String nodeId) {
        Objects.requireNonNull(nodeId, "nodeId");
        return Optional.ofNullable(quarantined.get(nodeId));
    }

    public Map<String, NodeAllocation> activeAllocations() {
        return active;
    }

    public Map<String, QuarantinedNodeAllocation> quarantinedAllocations() {
        return quarantined;
    }

    public PersistedNodeAllocations withActive(NodeAllocation allocation) {
        Objects.requireNonNull(allocation, "allocation");
        String nodeId = allocation.nodeId();
        if (quarantined.containsKey(nodeId)) {
            throw new IllegalArgumentException("quarantined allocation must be restored before activation: " + nodeId);
        }
        HashMap<String, NodeAllocation> next = new HashMap<>(active);
        next.put(nodeId, allocation);
        return new PersistedNodeAllocations(next, quarantined);
    }

    public PersistedNodeAllocations quarantine(String nodeId, String reason, long rulesVersion) {
        ProgressionProvenanceId.requireNamespacedId(nodeId, "node id");
        NodeAllocation allocation = active.get(nodeId);
        if (allocation == null) {
            if (quarantined.containsKey(nodeId)) return this;
            throw new IllegalArgumentException("active allocation does not exist: " + nodeId);
        }
        HashMap<String, NodeAllocation> nextActive = new HashMap<>(active);
        nextActive.remove(nodeId);
        HashMap<String, QuarantinedNodeAllocation> nextQuarantined = new HashMap<>(quarantined);
        nextQuarantined.put(nodeId, new QuarantinedNodeAllocation(allocation, reason, rulesVersion));
        return new PersistedNodeAllocations(nextActive, nextQuarantined);
    }

    public PersistedNodeAllocations restore(String nodeId) {
        ProgressionProvenanceId.requireNamespacedId(nodeId, "node id");
        QuarantinedNodeAllocation allocation = quarantined.get(nodeId);
        if (allocation == null) throw new IllegalArgumentException("quarantined allocation does not exist: " + nodeId);
        HashMap<String, QuarantinedNodeAllocation> nextQuarantined = new HashMap<>(quarantined);
        nextQuarantined.remove(nodeId);
        HashMap<String, NodeAllocation> nextActive = new HashMap<>(active);
        nextActive.put(nodeId, allocation.allocation());
        return new PersistedNodeAllocations(nextActive, nextQuarantined);
    }
}
