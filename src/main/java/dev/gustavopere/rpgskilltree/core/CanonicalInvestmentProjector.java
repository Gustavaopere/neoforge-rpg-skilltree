package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Pure read-only projection from canonical progression state into the historical
 * {@link InvestmentState} boundary used by emergent archetype resolution.
 *
 * <p>No node-id, graph-position, provider, or topology inference is permitted.
 * Every purchased node must have explicit metadata. Missing metadata is reported
 * and makes the projection incomplete.</p>
 */
public final class CanonicalInvestmentProjector {
    private CanonicalInvestmentProjector() {}

    public static CanonicalInvestmentProjection project(
        ProgressionState state,
        Map<String, NodeInvestmentMetadata> nodeMetadata,
        Collection<MasteryInvestmentMetadata> masteryMetadata
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(nodeMetadata, "nodeMetadata");
        Objects.requireNonNull(masteryMetadata, "masteryMetadata");

        List<NodeInvestment> investments = new ArrayList<>();
        Set<String> missingNodeIds = new HashSet<>();

        state.passiveNodes().ranks().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                String nodeId = entry.getKey();
                int rank = entry.getValue();
                NodeInvestmentMetadata metadata = nodeMetadata.get(nodeId);
                if (metadata == null) {
                    missingNodeIds.add(nodeId);
                    return;
                }
                Map<ProgressionDomain, Integer> scaledWeights = metadata.domainWeightsPerRank().entrySet().stream()
                    .collect(java.util.stream.Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        weight -> Math.multiplyExact(weight.getValue(), rank)
                    ));
                investments.add(new NodeInvestment(nodeId, scaledWeights, metadata.tags()));
            });

        masteryMetadata.stream()
            .sorted(Comparator
                .comparing(MasteryInvestmentMetadata::laneId)
                .thenComparingInt(MasteryInvestmentMetadata::minimumExperience))
            .filter(metadata -> state.mastery().experience(metadata.laneId()) >= metadata.minimumExperience())
            .forEach(metadata -> investments.add(new NodeInvestment(
                "mastery:" + metadata.laneId() + ":" + metadata.minimumExperience(),
                metadata.domainWeights(),
                metadata.tags()
            )));

        return new CanonicalInvestmentProjection(
            InvestmentState.of(investments),
            missingNodeIds
        );
    }
}
