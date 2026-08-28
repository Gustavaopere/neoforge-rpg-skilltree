package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record DiscoveryRecord(
    CompendiumEntryId entryId,
    DiscoveryState state,
    long firstGameTime,
    Optional<DiscoveryOrigin> firstOrigin,
    Set<String> variantIds,
    Set<String> completedObjectiveIds,
    Set<String> claimedRewardIds
) {
    public DiscoveryRecord {
        Objects.requireNonNull(entryId, "entryId");
        Objects.requireNonNull(state, "state");
        if (state == DiscoveryState.UNKNOWN) {
            throw new IllegalArgumentException("persisted discovery record must be at least SEEN");
        }
        if (firstGameTime < 0L) {
            throw new IllegalArgumentException("firstGameTime must not be negative");
        }
        firstOrigin = firstOrigin == null ? Optional.empty() : firstOrigin;
        variantIds = immutableTextSet(variantIds, "variantId");
        completedObjectiveIds = immutableTextSet(completedObjectiveIds, "objectiveId");
        claimedRewardIds = immutableTextSet(claimedRewardIds, "rewardId");
    }

    public DiscoveryRecord advanceTo(DiscoveryState target) {
        Objects.requireNonNull(target, "target");
        DiscoveryState next = state.max(target);
        if (next == state) return this;
        return new DiscoveryRecord(
            entryId,
            next,
            firstGameTime,
            firstOrigin,
            variantIds,
            completedObjectiveIds,
            claimedRewardIds
        );
    }

    private static Set<String> immutableTextSet(Set<String> values, String label) {
        if (values == null || values.isEmpty()) return Set.of();
        LinkedHashSet<String> normalized = new LinkedHashSet<>();
        for (String value : values) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException(label + " must not be blank");
            }
            normalized.add(value.trim());
        }
        return Set.copyOf(normalized);
    }
}
