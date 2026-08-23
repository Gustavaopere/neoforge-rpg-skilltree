package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
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

public final class TreeArchitectureReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public TreeArchitectureReloader() {
        super(GSON, "tree_architecture");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new TreeArchitectureReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<TreeArchitectureCatalog.TreeDefinition> definitions = new ArrayList<>();
        for (JsonElement element : resources.values()) {
            JsonObject root = element.getAsJsonObject();
            JsonArray trees = root.getAsJsonArray("trees");
            if (trees == null) continue;
            for (JsonElement treeElement : trees) {
                definitions.add(readTree(treeElement.getAsJsonObject()));
            }
        }
        TreeArchitectureCatalog.replace(definitions);
    }

    private static TreeArchitectureCatalog.TreeDefinition readTree(JsonObject tree) {
        ResourceLocation id = ResourceLocation.parse(tree.get("id").getAsString());
        String type = tree.get("type").getAsString();
        Set<String> domains = readStringSet(tree.getAsJsonArray("domains"));
        String provider = tree.has("provider") ? tree.get("provider").getAsString() : "rpgskilltree";
        List<TreeArchitectureCatalog.BranchDefinition> branches = readBranches(tree.getAsJsonArray("branches"));
        TreeArchitectureCatalog.GateDefinition gate = readGate(tree.getAsJsonObject("gate"));
        Set<ResourceLocation> bridges = readResourceSet(tree.getAsJsonArray("bridges"));
        Set<String> tags = readStringSet(tree.getAsJsonArray("tags"));
        return new TreeArchitectureCatalog.TreeDefinition(id, type, domains, provider, branches, gate, bridges, tags);
    }

    private static List<TreeArchitectureCatalog.BranchDefinition> readBranches(JsonArray values) {
        if (values == null) return List.of();
        List<TreeArchitectureCatalog.BranchDefinition> result = new ArrayList<>();
        for (JsonElement value : values) {
            JsonObject branch = value.getAsJsonObject();
            result.add(new TreeArchitectureCatalog.BranchDefinition(
                branch.get("id").getAsString(),
                branch.get("label").getAsString(),
                branch.has("role") ? branch.get("role").getAsString() : "branch",
                branch.has("order") ? branch.get("order").getAsInt() : result.size(),
                readStringSet(branch.getAsJsonArray("tags"))
            ));
        }
        return List.copyOf(result);
    }

    private static TreeArchitectureCatalog.GateDefinition readGate(JsonObject gate) {
        if (gate == null) return TreeArchitectureCatalog.GateDefinition.none();
        return new TreeArchitectureCatalog.GateDefinition(
            gate.has("minimumCharacterLevel") ? gate.get("minimumCharacterLevel").getAsInt() : 1,
            readStringSet(gate.getAsJsonArray("requiredClasses")),
            readIntMap(gate.getAsJsonObject("requiredMastery")),
            readStringSet(gate.getAsJsonArray("requiredSpecializations")),
            readStringSet(gate.getAsJsonArray("requiredTags"))
        );
    }

    private static Set<String> readStringSet(JsonArray values) {
        if (values == null) return Set.of();
        Set<String> result = new HashSet<>();
        values.forEach(value -> result.add(value.getAsString()));
        return Set.copyOf(result);
    }

    private static Set<ResourceLocation> readResourceSet(JsonArray values) {
        if (values == null) return Set.of();
        Set<ResourceLocation> result = new HashSet<>();
        values.forEach(value -> result.add(ResourceLocation.parse(value.getAsString())));
        return Set.copyOf(result);
    }

    private static Map<String, Integer> readIntMap(JsonObject values) {
        if (values == null) return Map.of();
        Map<String, Integer> result = new HashMap<>();
        values.entrySet().forEach(entry -> result.put(entry.getKey(), entry.getValue().getAsInt()));
        return Map.copyOf(result);
    }
}
