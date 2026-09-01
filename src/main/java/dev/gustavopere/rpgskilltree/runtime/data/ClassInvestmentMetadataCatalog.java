package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeInvestmentMetadata;
import java.util.Map;
import java.util.Objects;

/** Revision-bound read model for emergent-class investment metadata. */
public final class ClassInvestmentMetadataCatalog {
    private static volatile Snapshot current = new Snapshot(0L, Map.of());

    private ClassInvestmentMetadataCatalog() {}

    public static Snapshot current() {
        return current;
    }

    static synchronized void install(long skillTreeRevision, Map<String, NodeInvestmentMetadata> nodeMetadata) {
        if (skillTreeRevision < 0L) throw new IllegalArgumentException("skillTreeRevision must be non-negative");
        current = new Snapshot(skillTreeRevision, nodeMetadata);
    }

    public record Snapshot(long skillTreeRevision, Map<String, NodeInvestmentMetadata> nodeMetadata) {
        public Snapshot {
            if (skillTreeRevision < 0L) throw new IllegalArgumentException("skillTreeRevision must be non-negative");
            nodeMetadata = Map.copyOf(Objects.requireNonNull(nodeMetadata, "nodeMetadata"));
        }
    }
}
