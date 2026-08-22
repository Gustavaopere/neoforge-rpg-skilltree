package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;

public record TreeDisplayState(
    int availablePoints,
    Map<String, NodeDisplayState> nodes
) {
    public TreeDisplayState {
        if (availablePoints < 0) throw new IllegalArgumentException("availablePoints must be >= 0");
        Objects.requireNonNull(nodes);
        nodes = Map.copyOf(nodes);
    }
}
