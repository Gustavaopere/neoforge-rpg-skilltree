package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
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

public final class NodeRulesReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public NodeRulesReloader() {
        super(GSON, "node_rules");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new NodeRulesReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<TreeRuleCatalog.NodeRule> rules = new ArrayList<>();
        for (JsonElement element : resources.values()) {
            JsonObject root = element.getAsJsonObject();
            JsonArray nodes = root.getAsJsonArray("nodes");
            if (nodes == null) continue;
            for (JsonElement nodeElement : nodes) {
                JsonObject node = nodeElement.getAsJsonObject();
                ResourceLocation id = ResourceLocation.parse(node.get("id").getAsString());
                var finalTriadDomain = node.has("finalTriadDomain")
                    ? dev.gustavopere.rpgskilltree.core.ProgressionDomain.valueOf(node.get("finalTriadDomain").getAsString())
                    : null;
                int finalTriadSlot = node.has("finalTriadSlot") ? node.get("finalTriadSlot").getAsInt() : -1;
                NodePurchaseDefinition definition = new NodePurchaseDefinition(
                    id.toString(),
                    node.get("maxRank").getAsInt(),
                    node.get("costPerRank").getAsInt(),
                    node.get("startingPoint").getAsBoolean(),
                    finalTriadDomain,
                    finalTriadSlot
                );
                Set<String> requiredClasses = readStringSet(node.getAsJsonArray("requiredClasses"));
                Map<String, Integer> requiredMastery = readIntMap(node.getAsJsonObject("requiredMastery"));
                Set<String> requiredSpecializations = readStringSet(node.getAsJsonArray("requiredSpecializations"));
                Set<String> requiredClassChoices = readStringSet(node.getAsJsonArray("requiredClassChoices"));
                Set<String> requiredNodes = readStringSet(node.getAsJsonArray("requiredNodes"));
                Map<String, Integer> requiredNodeRanks = readIntMap(node.getAsJsonObject("requiredNodeRanks"));
                Set<String> requiredDiscoveries = readStringSet(node.getAsJsonArray("requiredDiscoveries"));
                NodeAccessRequirement requirement = new NodeAccessRequirement(
                    node.has("minCharacterLevel") ? node.get("minCharacterLevel").getAsInt() : 1,
                    requiredClasses,
                    requiredMastery,
                    requiredSpecializations,
                    requiredClassChoices,
                    requiredNodes,
                    requiredNodeRanks,
                    requiredDiscoveries
                );
                NodeSpecializationGrant specializationGrant = null;
                if (node.has("grantsSpecialization")) {
                    JsonObject grant = node.getAsJsonObject("grantsSpecialization");
                    specializationGrant = new NodeSpecializationGrant(
                        id.toString(),
                        grant.get("id").getAsString(),
                        grant.has("requiredRank") ? grant.get("requiredRank").getAsInt() : definition.maxRank()
                    );
                }
                Set<ResourceLocation> neighbors = new HashSet<>();
                JsonArray neighborArray = node.getAsJsonArray("neighbors");
                if (neighborArray != null) {
                    neighborArray.forEach(value -> neighbors.add(ResourceLocation.parse(value.getAsString())));
                }
                rules.add(new TreeRuleCatalog.NodeRule(id, definition, requirement, specializationGrant, neighbors));
            }
        }
        TreeRuleCatalog.replace(rules);
    }

    private static Set<String> readStringSet(JsonArray values) {
        if (values == null) return Set.of();
        Set<String> result = new HashSet<>();
        values.forEach(value -> result.add(value.getAsString()));
        return Set.copyOf(result);
    }

    private static Map<String, Integer> readIntMap(JsonObject values) {
        if (values == null) return Map.of();
        java.util.HashMap<String, Integer> result = new java.util.HashMap<>();
        values.entrySet().forEach(entry -> result.put(entry.getKey(), entry.getValue().getAsInt()));
        return Map.copyOf(result);
    }
}
