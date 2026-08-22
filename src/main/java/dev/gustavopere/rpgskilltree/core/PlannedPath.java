package dev.gustavopere.rpgskilltree.core;

import java.util.List;

public record PlannedPath(List<String> nodeIds, int pointsRequired) {
    public PlannedPath {
        nodeIds = List.copyOf(nodeIds);
        if (nodeIds.isEmpty()) throw new IllegalArgumentException("planned path must contain at least one node");
        if (pointsRequired < 0) throw new IllegalArgumentException("pointsRequired must be >= 0");
    }
}
