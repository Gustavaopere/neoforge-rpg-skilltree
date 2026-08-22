package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class PassiveNodeProgress {
    private final Map<String, Integer> ranks;

    private PassiveNodeProgress(Map<String, Integer> ranks) {
        Map<String, Integer> copy = new HashMap<>();
        ranks.forEach((id, rank) -> {
            Objects.requireNonNull(id);
            Objects.requireNonNull(rank);
            if (id.isBlank()) throw new IllegalArgumentException("node id must not be blank");
            if (rank <= 0) throw new IllegalArgumentException("stored node rank must be positive");
            if (copy.put(id, rank) != null) throw new IllegalArgumentException("duplicate node id: " + id);
        });
        this.ranks = Map.copyOf(copy);
    }

    public static PassiveNodeProgress empty() {
        return new PassiveNodeProgress(Map.of());
    }

    public static PassiveNodeProgress of(Map<String, Integer> ranks) {
        Objects.requireNonNull(ranks);
        return new PassiveNodeProgress(ranks);
    }

    public int rank(String nodeId) {
        return ranks.getOrDefault(nodeId, 0);
    }

    public boolean learned(String nodeId) {
        return rank(nodeId) > 0;
    }

    public Set<String> learnedNodeIds() {
        return ranks.keySet();
    }

    public Map<String, Integer> ranks() {
        return ranks;
    }

    public PassiveNodeProgress increase(String nodeId, int maxRank) {
        Objects.requireNonNull(nodeId);
        if (nodeId.isBlank()) throw new IllegalArgumentException("node id must not be blank");
        if (maxRank <= 0) throw new IllegalArgumentException("maxRank must be positive");
        int current = rank(nodeId);
        if (current >= maxRank) throw new IllegalArgumentException("node rank is already capped: " + nodeId);
        Map<String, Integer> next = new HashMap<>(ranks);
        next.put(nodeId, current + 1);
        return new PassiveNodeProgress(next);
    }

    public PassiveNodeProgress decrease(String nodeId) {
        Objects.requireNonNull(nodeId);
        int current = rank(nodeId);
        if (current <= 0) throw new IllegalArgumentException("node is not learned: " + nodeId);
        Map<String, Integer> next = new HashMap<>(ranks);
        if (current == 1) next.remove(nodeId);
        else next.put(nodeId, current - 1);
        return new PassiveNodeProgress(next);
    }

    public PassiveNodeProgress without(Set<String> nodeIds) {
        Objects.requireNonNull(nodeIds);
        Map<String, Integer> next = new HashMap<>(ranks);
        nodeIds.forEach(next::remove);
        return new PassiveNodeProgress(next);
    }
}
