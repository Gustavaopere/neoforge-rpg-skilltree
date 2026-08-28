package dev.gustavopere.rpgskilltree.compendium.discovery;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Immutable result of applying one criterion to one trusted discovery signal. */
public record DiscoveryTransition(
    DiscoveryProgress progress,
    DiscoveryState previousState,
    DiscoveryState currentState,
    Set<String> newVariantIds,
    Set<String> newObjectiveIds,
    List<DiscoveryRewardDefinition> newRewards
) {
    public DiscoveryTransition {
        Objects.requireNonNull(progress, "progress");
        Objects.requireNonNull(previousState, "previousState");
        Objects.requireNonNull(currentState, "currentState");
        newVariantIds = newVariantIds == null ? Set.of() : Set.copyOf(newVariantIds);
        newObjectiveIds = newObjectiveIds == null ? Set.of() : Set.copyOf(newObjectiveIds);
        newRewards = newRewards == null ? List.of() : List.copyOf(newRewards);
    }

    public boolean changed() {
        return previousState != currentState
            || !newVariantIds.isEmpty()
            || !newObjectiveIds.isEmpty()
            || !newRewards.isEmpty();
    }
}
