package dev.gustavopere.rpgskilltree.compendium.discovery;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Pure, deterministic discovery transition engine. */
public final class DiscoveryRuntime {
    private DiscoveryRuntime() {}

    public static DiscoveryTransition apply(
        DiscoveryProgress progress,
        DiscoveryCriterion criterion,
        DiscoverySignal signal
    ) {
        Objects.requireNonNull(progress, "progress");
        Objects.requireNonNull(criterion, "criterion");
        Objects.requireNonNull(signal, "signal");

        if (!criterion.entryId().equals(signal.entryId())) {
            throw new IllegalArgumentException("discovery signal entry does not match criterion entry");
        }
        if (criterion.trigger() != signal.trigger()) {
            throw new IllegalArgumentException("discovery signal trigger does not match criterion trigger");
        }

        Optional<DiscoveryRecord> existing = progress.record(criterion.entryId());
        DiscoveryState previousState = existing.map(DiscoveryRecord::state).orElse(DiscoveryState.UNKNOWN);
        DiscoveryState currentState = previousState.max(criterion.targetState());

        Set<String> existingVariants = existing.map(DiscoveryRecord::variantIds).orElse(Set.of());
        Set<String> existingObjectives = existing.map(DiscoveryRecord::completedObjectiveIds).orElse(Set.of());
        Set<String> existingClaims = existing.map(DiscoveryRecord::claimedRewardIds).orElse(Set.of());

        LinkedHashSet<String> newVariantIds = new LinkedHashSet<>();
        signal.variantId().ifPresent(variantId -> {
            if (!existingVariants.contains(variantId)) newVariantIds.add(variantId);
        });

        LinkedHashSet<String> newObjectiveIds = new LinkedHashSet<>();
        criterion.objectiveId().ifPresent(objectiveId -> {
            if (!existingObjectives.contains(objectiveId)) newObjectiveIds.add(objectiveId);
        });

        ArrayList<DiscoveryRewardDefinition> newRewards = new ArrayList<>();
        for (DiscoveryRewardDefinition reward : criterion.rewards()) {
            if (!existingClaims.contains(reward.rewardId())) newRewards.add(reward);
        }

        boolean changed = previousState != currentState
            || !newVariantIds.isEmpty()
            || !newObjectiveIds.isEmpty()
            || !newRewards.isEmpty();

        if (!changed) {
            return new DiscoveryTransition(
                progress,
                previousState,
                currentState,
                Set.of(),
                Set.of(),
                List.of()
            );
        }

        LinkedHashSet<String> variants = new LinkedHashSet<>(existingVariants);
        variants.addAll(newVariantIds);
        LinkedHashSet<String> objectives = new LinkedHashSet<>(existingObjectives);
        objectives.addAll(newObjectiveIds);
        LinkedHashSet<String> claims = new LinkedHashSet<>(existingClaims);
        for (DiscoveryRewardDefinition reward : newRewards) claims.add(reward.rewardId());

        DiscoveryRecord nextRecord = new DiscoveryRecord(
            criterion.entryId(),
            currentState,
            existing.map(DiscoveryRecord::firstGameTime).orElse(signal.gameTime()),
            existing.map(DiscoveryRecord::firstOrigin).orElse(signal.origin()),
            variants,
            objectives,
            claims
        );
        DiscoveryProgress nextProgress = progress.withRecord(nextRecord);

        return new DiscoveryTransition(
            nextProgress,
            previousState,
            currentState,
            newVariantIds,
            newObjectiveIds,
            newRewards
        );
    }
}
