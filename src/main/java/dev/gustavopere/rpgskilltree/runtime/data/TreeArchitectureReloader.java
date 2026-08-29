package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.ReloadDiagnostics;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TreeArchitectureReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeArchitectureReloader.class);

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
        ReloadDiagnostics.run(LOGGER, "tree_architecture", resources, () -> {
            try {
                List<SkillTreeDataReloadTransaction.TreeEntry> definitions = new ArrayList<>();
                for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
                    ResourceLocation source = resource.getKey();
                    JsonObject root = object(source, "<root>", "root", resource.getValue());
                    JsonArray trees = root.getAsJsonArray("trees");
                    if (trees == null) continue;
                    for (JsonElement treeElement : trees) {
                        TreeArchitectureCatalog.TreeDefinition definition = readTree(source, treeElement);
                        definitions.add(new SkillTreeDataReloadTransaction.TreeEntry(source, definition));
                    }
                }
                SkillTreeDataReloadTransaction.stageTrees(definitions);
            } catch (RuntimeException failure) {
                SkillTreeDataReloadTransaction.abort();
                throw failure;
            }
        });
    }

    private static TreeArchitectureCatalog.TreeDefinition readTree(ResourceLocation source, JsonElement treeElement) {
        JsonObject tree = object(source, "<unknown>", "tree", treeElement);
        String rawId = string(source, "<unknown>", "id", required(tree, source, "<unknown>", "id"));
        ResourceLocation id = resourceLocation(source, rawId, "id", tree.get("id"));
        String subject = id.toString();
        String type = string(source, subject, "type", required(tree, source, subject, "type"));
        Set<String> domains = readStringSet(source, subject, "domains", tree.getAsJsonArray("domains"));
        String provider = tree.has("provider")
            ? string(source, subject, "provider", tree.get("provider"))
            : "rpgskilltree";
        List<TreeArchitectureCatalog.BranchDefinition> branches = readBranches(
            source,
            subject,
            tree.getAsJsonArray("branches")
        );
        TreeArchitectureCatalog.GateDefinition gate = readGate(source, subject, tree.getAsJsonObject("gate"));
        Set<ResourceLocation> bridges = readResourceSet(source, subject, "bridges", tree.getAsJsonArray("bridges"));
        Set<String> tags = readStringSet(source, subject, "tags", tree.getAsJsonArray("tags"));
        try {
            return new TreeArchitectureCatalog.TreeDefinition(id, type, domains, provider, branches, gate, bridges, tags);
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, "tree", failure);
        }
    }

    private static List<TreeArchitectureCatalog.BranchDefinition> readBranches(
        ResourceLocation source,
        String subject,
        JsonArray values
    ) {
        if (values == null) return List.of();
        List<TreeArchitectureCatalog.BranchDefinition> result = new ArrayList<>();
        for (JsonElement value : values) {
            JsonObject branch = object(source, subject, "branches", value);
            String branchId = string(source, subject, "branches.id", required(branch, source, subject, "id"));
            String label = string(source, subject, "branches.label", required(branch, source, subject, "label"));
            String role = branch.has("role") ? string(source, subject, "branches.role", branch.get("role")) : "branch";
            int order = branch.has("order") ? integer(source, subject, "branches.order", branch.get("order")) : result.size();
            if (order < 0) throw error(source, subject, "branches.order", "must be non-negative");
            Set<String> tags = readStringSet(source, subject, "branches.tags", branch.getAsJsonArray("tags"));
            String catalogCode = branch.has("catalogCode")
                ? string(source, subject, "branches.catalogCode", branch.get("catalogCode"))
                : null;
            try {
                result.add(new TreeArchitectureCatalog.BranchDefinition(branchId, label, role, order, tags, catalogCode));
            } catch (RuntimeException failure) {
                throw SkillTreeDataValidationException.wrap(source, subject, "branches", failure);
            }
        }
        return List.copyOf(result);
    }

    private static TreeArchitectureCatalog.GateDefinition readGate(
        ResourceLocation source,
        String subject,
        JsonObject gate
    ) {
        if (gate == null) return TreeArchitectureCatalog.GateDefinition.none();
        int minimumLevel = gate.has("minimumCharacterLevel")
            ? integer(source, subject, "gate.minimumCharacterLevel", gate.get("minimumCharacterLevel"))
            : 1;
        if (minimumLevel < 1) throw error(source, subject, "gate.minimumCharacterLevel", "must be >= 1");
        try {
            return new TreeArchitectureCatalog.GateDefinition(
                minimumLevel,
                readStringSet(source, subject, "gate.requiredClasses", gate.getAsJsonArray("requiredClasses")),
                readIntMap(source, subject, "gate.requiredMastery", gate.getAsJsonObject("requiredMastery")),
                readStringSet(source, subject, "gate.requiredSpecializations", gate.getAsJsonArray("requiredSpecializations")),
                readStringSet(source, subject, "gate.requiredTags", gate.getAsJsonArray("requiredTags"))
            );
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(source, subject, "gate", failure);
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

    private static ResourceLocation resourceLocation(ResourceLocation source, String subject, String field, JsonElement value) {
        try {
            return ResourceLocation.parse(string(source, subject, field, value));
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

    private static Set<ResourceLocation> readResourceSet(
        ResourceLocation source,
        String subject,
        String field,
        JsonArray values
    ) {
        if (values == null) return Set.of();
        Set<ResourceLocation> result = new HashSet<>();
        for (JsonElement value : values) result.add(resourceLocation(source, subject, field, value));
        return Set.copyOf(result);
    }

    private static Map<String, Integer> readIntMap(
        ResourceLocation source,
        String subject,
        String field,
        JsonObject values
    ) {
        if (values == null) return Map.of();
        Map<String, Integer> result = new HashMap<>();
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
