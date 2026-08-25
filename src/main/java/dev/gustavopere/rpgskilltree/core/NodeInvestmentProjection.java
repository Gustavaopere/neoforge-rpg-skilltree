package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Builds the semantic investment view used by specialization and archetype resolvers. */
public final class NodeInvestmentProjection {
    private static final String DOMAIN_TAG_PREFIX = "rpgskilltree:domain/";

    private NodeInvestmentProjection() {}

    public static InvestmentState from(
        PassiveNodeProgress progress,
        Map<String, Set<String>> tagsByNode
    ) {
        Objects.requireNonNull(progress);
        Objects.requireNonNull(tagsByNode);

        var investments = new ArrayList<NodeInvestment>();
        progress.learnedNodeIds().stream().sorted().forEach(nodeId -> {
            Set<String> tags = tagsByNode.getOrDefault(nodeId, Set.of());
            Map<ProgressionDomain, Integer> weights = domainWeights(nodeId, progress.rank(nodeId), tags);
            if (!tags.isEmpty() || !weights.isEmpty()) {
                investments.add(new NodeInvestment(nodeId, weights, tags));
            }
        });
        return InvestmentState.of(investments);
    }

    private static Map<ProgressionDomain, Integer> domainWeights(String nodeId, int rank, Set<String> tags) {
        if (rank <= 0) return Map.of();
        EnumMap<ProgressionDomain, Integer> weights = new EnumMap<>(ProgressionDomain.class);
        for (String tag : tags) {
            if (!tag.startsWith(DOMAIN_TAG_PREFIX)) continue;
            String raw = tag.substring(DOMAIN_TAG_PREFIX.length());
            ProgressionDomain domain = parseDomain(raw);
            if (domain != null) weights.put(domain, rank);
        }
        if (!weights.isEmpty()) return Map.copyOf(weights);

        // The generated main tree uses stable server IDs rpgskilltree:<domain>_NNN.
        // Older checked-in node_rules predate exported domain tags, so reconstruct only this
        // exact structural convention. Unknown/custom IDs contribute no domain investment.
        if (nodeId.startsWith("rpgskilltree:")) {
            String path = nodeId.substring("rpgskilltree:".length());
            for (ProgressionDomain domain : ProgressionDomain.values()) {
                String prefix = domain.name().toLowerCase(Locale.ROOT) + "_";
                if (path.startsWith(prefix) && numericSuffix(path.substring(prefix.length()))) {
                    weights.put(domain, rank);
                    break;
                }
            }
        }
        return Map.copyOf(weights);
    }

    private static ProgressionDomain parseDomain(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return ProgressionDomain.valueOf(raw.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException unknown) {
            return null;
        }
    }

    private static boolean numericSuffix(String value) {
        if (value.isEmpty()) return false;
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) return false;
        }
        return true;
    }
}
