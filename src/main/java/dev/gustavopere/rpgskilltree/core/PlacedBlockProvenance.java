package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Tracks packed block positions known to originate from player placement. */
public final class PlacedBlockProvenance {
    private final Set<Long> positions;

    public PlacedBlockProvenance() {
        this(Set.of());
    }

    public PlacedBlockProvenance(Collection<Long> positions) {
        this.positions = new HashSet<>(positions);
    }

    public boolean mark(long packedPosition) {
        return positions.add(packedPosition);
    }

    public boolean contains(long packedPosition) {
        return positions.contains(packedPosition);
    }

    public boolean consume(long packedPosition) {
        return positions.remove(packedPosition);
    }

    public int removeAll(Collection<Long> destroyedPositions) {
        int removed = 0;
        for (long packedPosition : destroyedPositions) {
            if (positions.remove(packedPosition)) removed++;
        }
        return removed;
    }

    public Set<Long> snapshot() {
        return Set.copyOf(positions);
    }
}
