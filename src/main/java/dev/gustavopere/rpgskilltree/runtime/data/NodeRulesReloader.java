package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.CombatPerkTreeModel;
import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.ReloadDiagnostics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NodeRulesReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeRulesReloader.class);
    private static final ResourceLocation MAIN_TREE = ResourceLocation.parse("rpgskilltree:main");
    private static final ResourceLocation COMBAT_MODEL_SOURCE = ResourceLocation.parse("rpgskilltree:runtime/combat_perk_tree_model");

    public NodeRulesReloader() { super(GSON, "node_rules"); }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) { event.addListener(new NodeRulesReloader()); }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        ReloadDiagnostics.run(LOGGER, "node_rules", resources, () -> {
            SkillTreeDataReloadTransaction.begin();
            try {
                SkillTreeDataReloadTransaction.stageNodeRules(load(resources));
            } catch (RuntimeException failure) {
                SkillTreeDataReloadTransaction.abort();
                throw failure;
            }
        });
    }

    private static List<SkillTreeDataReloadTransaction.NodeRuleEntry> load(Map<ResourceLocation, JsonElement> resources) {
        List<SkillTreeDataReloadTransaction.NodeRuleEntry> rules = new ArrayList<>();
        for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
            ResourceLocation source = resource.getKey();
            JsonObject root = object(source, "<root>", "root", resource.getValue());
            ResourceLocation treeId = resourceLocation(source, "<root>", "treeId", required(root, source, "<root>", "treeId"));
            JsonArray nodes = root.getAsJsonArray("nodes");
            if (nodes == null) continue;
            for (JsonElement nodeElement : nodes) {
                rules.add(readNode(source, treeId, nodeElement));
            }
        }
        addClosedCombatRules(rules);
        return List.copyOf(rules);
    }

    private static SkillTreeDataReloadTransaction.NodeRuleEntry readNode(
        ResourceLocation source,
        ResourceLocation treeId,
        JsonElement nodeElement
    ) {
        JsonObject node = object(source, "<unknown>", "node", nodeElement);
        String rawId = string(source, "<unknown>", "id", required(node, source, "<unknown>", "id"));
        ResourceLocation id = resourceLocation(source, rawId, "id", node.get("id"));
        String subject = id.toString();

        int maxRank = integer(source, subject, "maxRank", required(node, source, subject, "maxRank"));
        if (maxRank <= 0) throw error(source, subject, "maxRank", "must be positive");
        int costPerRank = integer(source, subject, "costPerRank", required(node, source, subject, "costPerRank"));
        if (costPerRank <= 0) throw error(source, subject, "costPerRank", "must be positive");
        boolean startingPoint = bool(source, subject, "startingPoint", required(node, source, subject, "startingPoint"));

        var finalTriadDomain = node.has("finalTriadDomain")
            ? enumValue(source, subject, "finalTriadDomain", dev.gustavopere.rpgskilltree.core.ProgressionDomain.class, node.get("finalTriadDomain"))
            : null;
        int finalTriadSlot = node.has("finalTriadSlot")
            ? integer(source, subject, "finalTriadSlot", node.get("finalTriadSlot"))
            : -1;

        NodePurchaseDefinition definition;
        try {
            definition = new NodePurchaseDefinition(subject, maxRank, costPerRank, startingPoint, finalTriadDomain, finalTriadSlot);
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, "purchase", failure);
        }

        Set<String> requiredClasses = readStringSet(source, subject, "requiredClasses", node.getAsJsonArray("requiredClasses"));
        Map<String, Integer> requiredMastery = readIntMap(source, subject, "requiredMastery", node.getAsJsonObject("requiredMastery"));
        Set<String> requiredSpecializations = readStringSet(source, subject, "requiredSpecializations", node.getAsJsonArray("requiredSpecializations"));
        Set<String> requiredClassChoices = readStringSet(source, subject, "requiredClassChoices", node.getAsJsonArray("requiredClassChoices"));
        Set<String> requiredNodes = readStringSet(source, subject, "requiredNodes", node.getAsJsonArray("requiredNodes"));
        Map<String, Integer> requiredNodeRanks = readIntMap(source, subject, "requiredNodeRanks", node.getAsJsonObject("requiredNodeRanks"));
        Set<String> requiredDiscoveries = readStringSet(source, subject, "requiredDiscoveries", node.getAsJsonArray("requiredDiscoveries"));
        int minCharacterLevel = node.has("minCharacterLevel")
            ? integer(source, subject, "minCharacterLevel", node.get("minCharacterLevel"))
            : 1;
        if (minCharacterLevel < 1) throw error(source, subject, "minCharacterLevel", "must be >= 1");

        NodeAccessRequirement requirement;
        try {
            requirement = new NodeAccessRequirement(
                minCharacterLevel,
                requiredClasses,
                requiredMastery,
                requiredSpecializations,
                requiredClassChoices,
                requiredNodes,
                requiredNodeRanks,
                requiredDiscoveries
            );
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, "requirements", failure);
        }

        NodeSpecializationGrant specializationGrant = null;
        if (node.has("grantsSpecialization")) {
            JsonObject grant = object(source, subject, "grantsSpecialization", node.get("grantsSpecialization"));
            String grantId = string(source, subject, "grantsSpecialization.id", required(grant, source, subject, "id"));
            int requiredRank = grant.has("requiredRank")
                ? integer(source, subject, "grantsSpecialization.requiredRank", grant.get("requiredRank"))
                : definition.maxRank();
            try {
                specializationGrant = new NodeSpecializationGrant(subject, grantId, requiredRank);
            } catch (RuntimeException failure) {
                throw SkillTreeDataValidationException.wrap(source, subject, "grantsSpecialization", failure);
            }
        }

        Set<ResourceLocation> neighbors = new HashSet<>();
        JsonArray neighborArray = node.getAsJsonArray("neighbors");
        if (neighborArray != null) {
            for (JsonElement value : neighborArray) {
                neighbors.add(resourceLocation(source, subject, "neighbors", value));
            }
        }

        TreeRuleCatalog.NodeRule rule = new TreeRuleCatalog.NodeRule(id, definition, requirement, specializationGrant, neighbors);
        return new SkillTreeDataReloadTransaction.NodeRuleEntry(source, treeId, rule);
    }

    private static void addClosedCombatRules(List<SkillTreeDataReloadTransaction.NodeRuleEntry> rules) {
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            ResourceLocation id = ResourceLocation.parse(node.nodeId());
            NodePurchaseDefinition definition = new NodePurchaseDefinition(node.nodeId(), node.maxRank(), node.costPerRank(), node.startingPoint());
            // Combat gateway IDs are semantic outer-tree identities, not SpecializationProgressionState ids.
            // The live acquisition contract is the learned Martial gateway node plus mastery/dependency ranks.
            NodeAccessRequirement requirement = new NodeAccessRequirement(node.minCharacterLevel(), Set.of(), node.requiredMastery(),
                Set.of(), Set.of(), Set.of(), node.requiredNodeRanks(), Set.of());
            Set<ResourceLocation> neighbors = new HashSet<>();
            node.neighbors().forEach(value -> neighbors.add(ResourceLocation.parse(value)));
            TreeRuleCatalog.NodeRule rule = new TreeRuleCatalog.NodeRule(id, definition, requirement, null, neighbors);
            rules.add(new SkillTreeDataReloadTransaction.NodeRuleEntry(COMBAT_MODEL_SOURCE, MAIN_TREE, rule));
        }
    }

    private static JsonElement required(JsonObject object, ResourceLocation source, String subject, String field) {
        JsonElement value = object.get(field);
        if (value == null || value.isJsonNull()) throw error(source, subject, field, "field is required");
        return value;
    }

    private static JsonObject object(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return value.getAsJsonObject();
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static String string(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            String result = value.getAsString();
            if (result.isBlank()) throw new IllegalArgumentException("must not be blank");
            return result;
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static int integer(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return value.getAsInt();
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static boolean bool(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return value.getAsBoolean();
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static ResourceLocation resourceLocation(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return ResourceLocation.parse(string(source, subject, field, value));
        } catch (SkillTreeDataValidationException failure) {
            throw failure;
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static <E extends Enum<E>> E enumValue(
        ResourceLocation source,
        String subject,
        String field,
        Class<E> type,
        JsonElement value
    ) {
        try {
            return Enum.valueOf(type, string(source, subject, field, value));
        } catch (SkillTreeDataValidationException failure) {
            throw failure;
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, field, failure);
        }
    }

    private static Set<String> readStringSet(
        ResourceLocation source,
        String subject,
        String field,
        JsonArray values
    ) {
        if (values == null) return Set.of();
        Set<String> result = new HashSet<>();
        for (JsonElement value : values) result.add(string(source, subject, field, value));
        return Set.copyOf(result);
    }

    private static Map<String, Integer> readIntMap(
        ResourceLocation source,
        String subject,
        String field,
        JsonObject values
    ) {
        if (values == null) return Map.of();
        java.util.HashMap<String, Integer> result = new java.util.HashMap<>();
        for (Map.Entry<String, JsonElement> entry : values.entrySet()) {
            if (entry.getKey().isBlank()) throw error(source, subject, field, "map key must not be blank");
            result.put(entry.getKey(), integer(source, subject, field, entry.getValue()));
        }
        return Map.copyOf(result);
    }

    private static SkillTreeDataValidationException error(
        ResourceLocation source,
        String subject,
        String field,
        String detail
    ) {
        return new SkillTreeDataValidationException(source, subject, field, detail);
    }
}
