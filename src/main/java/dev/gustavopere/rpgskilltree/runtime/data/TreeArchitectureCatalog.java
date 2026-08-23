package dev.gustavopere.rpgskilltree.runtime.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/**
 * Semantic metadata for the progression graph.
 *
 * <p>The purchase graph remains owned by {@link TreeRuleCatalog}. This catalog answers the
 * higher-level questions needed by the planner/client/data generation layer: which tree a node
 * belongs to, what branches exist, how a tree is gated and which other trees it intentionally
 * bridges to.</p>
 */
public final class TreeArchitectureCatalog {
    public record BranchDefinition(
        String id,
        String label,
        String role,
        int order,
        Set<String> tags
    ) {
        public BranchDefinition {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("branch id is required");
            if (label == null || label.isBlank()) throw new IllegalArgumentException("branch label is required: " + id);
            role = role == null || role.isBlank() ? "branch" : role;
            if (order < 0) throw new IllegalArgumentException("branch order must be non-negative: " + id);
            tags = tags == null ? Set.of() : Set.copyOf(tags);
        }
    }

    public record GateDefinition(
        int minimumCharacterLevel,
        Set<String> requiredClasses,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializations,
        Set<String> requiredTags
    ) {
        public GateDefinition {
            if (minimumCharacterLevel < 1) {
                throw new IllegalArgumentException("minimum character level must be >= 1");
            }
            requiredClasses = requiredClasses == null ? Set.of() : Set.copyOf(requiredClasses);
            requiredMastery = requiredMastery == null ? Map.of() : Map.copyOf(requiredMastery);
            requiredSpecializations = requiredSpecializations == null ? Set.of() : Set.copyOf(requiredSpecializations);
            requiredTags = requiredTags == null ? Set.of() : Set.copyOf(requiredTags);
            requiredMastery.forEach((lane, amount) -> {
                if (lane == null || lane.isBlank() || amount == null || amount < 0) {
                    throw new IllegalArgumentException("invalid mastery gate: " + lane + "=" + amount);
                }
            });
        }

        public static GateDefinition none() {
            return new GateDefinition(1, Set.of(), Map.of(), Set.of(), Set.of());
        }
    }

    public record TreeDefinition(
        ResourceLocation id,
        String type,
        Set<String> domains,
        String provider,
        List<BranchDefinition> branches,
        GateDefinition gate,
        Set<ResourceLocation> bridges,
        Set<String> tags
    ) {
        public TreeDefinition {
            Objects.requireNonNull(id);
            if (type == null || type.isBlank()) throw new IllegalArgumentException("tree type is required: " + id);
            domains = domains == null ? Set.of() : Set.copyOf(domains);
            provider = provider == null ? "rpgskilltree" : provider;
            branches = branches == null ? List.of() : branches.stream()
                .sorted(Comparator.comparingInt(BranchDefinition::order).thenComparing(BranchDefinition::id))
                .toList();
            gate = gate == null ? GateDefinition.none() : gate;
            bridges = bridges == null ? Set.of() : Set.copyOf(bridges);
            tags = tags == null ? Set.of() : Set.copyOf(tags);

            Set<String> branchIds = new HashSet<>();
            for (BranchDefinition branch : branches) {
                if (!branchIds.add(branch.id())) {
                    throw new IllegalArgumentException("duplicate branch " + branch.id() + " in " + id);
                }
            }
        }
    }

    private static volatile Map<ResourceLocation, TreeDefinition> trees = Map.of();

    private TreeArchitectureCatalog() {}

    public static synchronized void replace(List<TreeDefinition> definitions) {
        Objects.requireNonNull(definitions);
        Map<ResourceLocation, TreeDefinition> next = new HashMap<>();
        for (TreeDefinition definition : definitions) {
            if (next.put(definition.id(), definition) != null) {
                throw new IllegalArgumentException("duplicate tree architecture id: " + definition.id());
            }
        }

        Set<ResourceLocation> known = Set.copyOf(next.keySet());
        for (TreeDefinition definition : next.values()) {
            for (ResourceLocation bridge : definition.bridges()) {
                if (!known.contains(bridge)) {
                    throw new IllegalArgumentException("unknown tree bridge: " + definition.id() + " -> " + bridge);
                }
                if (bridge.equals(definition.id())) {
                    throw new IllegalArgumentException("tree cannot bridge to itself: " + definition.id());
                }
            }
        }
        trees = Map.copyOf(next);
    }

    public static Optional<TreeDefinition> definition(ResourceLocation id) {
        return Optional.ofNullable(trees.get(id));
    }

    public static List<TreeDefinition> all() {
        return trees.values().stream()
            .sorted(Comparator.comparing(definition -> definition.id().toString()))
            .toList();
    }

    public static List<TreeDefinition> byDomain(String domain) {
        if (domain == null || domain.isBlank()) return List.of();
        return trees.values().stream()
            .filter(definition -> definition.domains().contains(domain))
            .sorted(Comparator.comparing(definition -> definition.id().toString()))
            .toList();
    }

    public static List<TreeDefinition> byProvider(String provider) {
        if (provider == null || provider.isBlank()) return List.of();
        return trees.values().stream()
            .filter(definition -> provider.equals(definition.provider()))
            .sorted(Comparator.comparing(definition -> definition.id().toString()))
            .toList();
    }

    public static Map<String, Set<String>> bridgeIndex() {
        Map<String, Set<String>> result = new HashMap<>();
        for (TreeDefinition definition : trees.values()) {
            Set<String> bridgeIds = new HashSet<>();
            definition.bridges().forEach(id -> bridgeIds.add(id.toString()));
            result.put(definition.id().toString(), Set.copyOf(bridgeIds));
        }
        return Map.copyOf(result);
    }

    public static int size() {
        return trees.size();
    }

    public static List<String> ids() {
        List<String> ids = new ArrayList<>();
        trees.keySet().forEach(id -> ids.add(id.toString()));
        ids.sort(String::compareTo);
        return List.copyOf(ids);
    }
}
