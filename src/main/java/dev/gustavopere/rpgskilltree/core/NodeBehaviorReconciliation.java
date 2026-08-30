package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/** Deterministic transition plan from currently applied behavioral effects to the desired set. */
public record NodeBehaviorReconciliation(
    List<ResolvedNodeBehaviorEffect> removals,
    List<ResolvedNodeBehaviorEffect> applications
) {
    public NodeBehaviorReconciliation {
        removals = List.copyOf(Objects.requireNonNull(removals));
        applications = List.copyOf(Objects.requireNonNull(applications));
    }
}
