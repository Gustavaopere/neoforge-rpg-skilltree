package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class SkillGraph {
    public record Edge(String from, String to) {
        public Edge {
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);
            if (from.isBlank() || to.isBlank()) throw new IllegalArgumentException("edge nodes must not be blank");
        }
    }

    private final Map<String, Set<String>> neighbors;

    private SkillGraph(Map<String, Set<String>> neighbors) {
        Map<String, Set<String>> copy = new HashMap<>();
        neighbors.forEach((node, next) -> copy.put(node, Set.copyOf(next)));
        this.neighbors = Map.copyOf(copy);
    }

    public static SkillGraph undirected(Collection<Edge> edges) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (Edge edge : edges) {
            graph.computeIfAbsent(edge.from(), ignored -> new HashSet<>()).add(edge.to());
            graph.computeIfAbsent(edge.to(), ignored -> new HashSet<>()).add(edge.from());
        }
        return new SkillGraph(graph);
    }

    public Set<String> neighbors(String nodeId) {
        return neighbors.getOrDefault(nodeId, Set.of());
    }

    public boolean contains(String nodeId) {
        return neighbors.containsKey(nodeId);
    }

    public Set<String> nodes() {
        return neighbors.keySet();
    }
}
