package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public final class SkillPathPlanner {
    private SkillPathPlanner() {}

    private record State(String node, int cost, String pathKey) {}

    public static PlannedPath shortestPath(SkillGraph graph, Set<String> ownedNodes, String target) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(ownedNodes);
        Objects.requireNonNull(target);
        if (!graph.contains(target)) throw new IllegalArgumentException("unknown target node: " + target);
        if (ownedNodes.contains(target)) return new PlannedPath(List.of(target), 0);
        if (ownedNodes.isEmpty()) throw new IllegalArgumentException("at least one owned node is required");

        Comparator<State> order = Comparator.comparingInt(State::cost)
            .thenComparing(State::pathKey)
            .thenComparing(State::node);
        PriorityQueue<State> queue = new PriorityQueue<>(order);
        Map<String, Integer> bestCost = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Map<String, String> bestPathKey = new HashMap<>();

        ownedNodes.stream().filter(graph::contains).sorted().forEach(start -> {
            bestCost.put(start, 0);
            bestPathKey.put(start, start);
            queue.add(new State(start, 0, start));
        });
        if (queue.isEmpty()) throw new IllegalArgumentException("none of the owned nodes exist in the graph");

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.cost() != bestCost.getOrDefault(current.node(), Integer.MAX_VALUE)) continue;
            if (!current.pathKey().equals(bestPathKey.get(current.node()))) continue;
            if (current.node().equals(target)) break;

            List<String> nextNodes = new ArrayList<>(graph.neighbors(current.node()));
            nextNodes.sort(String::compareTo);
            for (String next : nextNodes) {
                int nextCost = current.cost() + (ownedNodes.contains(next) ? 0 : 1);
                String nextKey = current.pathKey() + "\u0000" + next;
                int knownCost = bestCost.getOrDefault(next, Integer.MAX_VALUE);
                String knownKey = bestPathKey.get(next);
                if (nextCost < knownCost || (nextCost == knownCost && (knownKey == null || nextKey.compareTo(knownKey) < 0))) {
                    bestCost.put(next, nextCost);
                    bestPathKey.put(next, nextKey);
                    previous.put(next, current.node());
                    queue.add(new State(next, nextCost, nextKey));
                }
            }
        }

        Integer cost = bestCost.get(target);
        if (cost == null) throw new IllegalArgumentException("target is unreachable from owned nodes: " + target);

        List<String> reversed = new ArrayList<>();
        String cursor = target;
        reversed.add(cursor);
        while (previous.containsKey(cursor)) {
            cursor = previous.get(cursor);
            reversed.add(cursor);
            if (ownedNodes.contains(cursor)) break;
        }
        java.util.Collections.reverse(reversed);
        return new PlannedPath(reversed, cost);
    }
}
