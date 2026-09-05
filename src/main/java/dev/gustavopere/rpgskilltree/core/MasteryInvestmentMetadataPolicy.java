package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Canonical validation boundary for explicit Mastery-to-investment contributions.
 *
 * <p>No contribution is inferred from a lane namespace, provider or name. Callers
 * must supply explicit {@link MasteryInvestmentMetadata}; this policy only verifies
 * that the referenced lane is canonical and that one lane/threshold identity is
 * defined at most once.</p>
 */
public final class MasteryInvestmentMetadataPolicy {
    private MasteryInvestmentMetadataPolicy() {}

    public static List<MasteryInvestmentMetadata> validate(
        Collection<MasteryInvestmentMetadata> metadata
    ) {
        Objects.requireNonNull(metadata, "metadata");

        List<MasteryInvestmentMetadata> validated = new ArrayList<>();
        Set<ThresholdIdentity> identities = new HashSet<>();
        for (MasteryInvestmentMetadata entry : metadata) {
            Objects.requireNonNull(entry, "mastery investment metadata entry");
            if (!MasteryLaneCatalog.isCanonical(entry.laneId())) {
                throw new IllegalArgumentException("non-canonical mastery lane: " + entry.laneId());
            }
            ThresholdIdentity identity = new ThresholdIdentity(entry.laneId(), entry.minimumExperience());
            if (!identities.add(identity)) {
                throw new IllegalArgumentException(
                    "duplicate mastery investment threshold: " + entry.laneId() + "@" + entry.minimumExperience()
                );
            }
            validated.add(entry);
        }

        validated.sort(Comparator
            .comparing(MasteryInvestmentMetadata::laneId)
            .thenComparingInt(MasteryInvestmentMetadata::minimumExperience));
        return List.copyOf(validated);
    }

    private record ThresholdIdentity(String laneId, int minimumExperience) {}
}
