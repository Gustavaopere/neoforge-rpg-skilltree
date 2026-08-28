package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Data-driven criterion that maps one trusted trigger to monotonic discovery progress. */
public record DiscoveryCriterion(
    String criterionId,
    CompendiumEntryId entryId,
    DiscoveryTriggerType trigger,
    DiscoveryState targetState,
    Optional<String> objectiveId,
    List<DiscoveryRewardDefinition> rewards
) {
    public DiscoveryCriterion {
        criterionId = requireText(criterionId, "criterionId");
        Objects.requireNonNull(entryId, "entryId");
        Objects.requireNonNull(trigger, "trigger");
        Objects.requireNonNull(targetState, "targetState");
        if (targetState == DiscoveryState.UNKNOWN) {
            throw new IllegalArgumentException("criterion targetState must be at least SEEN");
        }
        objectiveId = normalizeOptionalText(objectiveId, "objectiveId");
        rewards = rewards == null ? List.of() : List.copyOf(rewards);
        HashSet<String> rewardIds = new HashSet<>();
        for (DiscoveryRewardDefinition reward : rewards) {
            Objects.requireNonNull(reward, "reward");
            if (!rewardIds.add(reward.rewardId())) {
                throw new IllegalArgumentException("duplicate discovery reward id in criterion: " + reward.rewardId());
            }
        }
    }

    private static Optional<String> normalizeOptionalText(Optional<String> value, String label) {
        if (value == null || value.isEmpty()) return Optional.empty();
        return Optional.of(requireText(value.orElseThrow(), label));
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
        return value.trim();
    }
}
