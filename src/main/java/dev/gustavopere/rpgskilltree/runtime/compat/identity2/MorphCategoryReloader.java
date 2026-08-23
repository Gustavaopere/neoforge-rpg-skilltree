package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.MorphFactionRelations;
import dev.gustavopere.rpgskilltree.core.MorphFormCategory;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Loads form classification plus explicit ecological faction relationships. */
public final class MorphCategoryReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public MorphCategoryReloader() { super(GSON, "morph_categories"); }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new MorphCategoryReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager manager,
        @NotNull ProfilerFiller profiler
    ) {
        Map<String, MorphFormCategory> overrides = new LinkedHashMap<>();
        Set<String> blacklist = new LinkedHashSet<>();
        Map<String, Set<String>> factionsByEntity = new LinkedHashMap<>();
        Map<String, Set<String>> traitsByEntity = new LinkedHashMap<>();
        Map<String, MorphFactionRelations> factionRelations = new LinkedHashMap<>();
        Integer hostilityMemorySeconds = null;

        for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
            ResourceLocation resourceId = resource.getKey();
            if (!resource.getValue().isJsonObject()) {
                throw new IllegalArgumentException(resourceId + ": morph category root must be an object");
            }
            JsonObject root = resource.getValue().getAsJsonObject();
            if (root.has("overrides")) {
                JsonObject values = requireObject(root, "overrides", resourceId);
                for (Map.Entry<String, JsonElement> entry : values.entrySet()) {
                    MorphFormCategory category;
                    try {
                        category = MorphFormCategory.valueOf(entry.getValue().getAsString());
                    } catch (RuntimeException exception) {
                        throw new IllegalArgumentException(resourceId + ": invalid morph category for " + entry.getKey(), exception);
                    }
                    putUnique(overrides, entry.getKey(), category, resourceId, "override");
                }
            }
            if (root.has("blacklist")) {
                blacklist.addAll(readStringSet(root.get("blacklist"), resourceId, "blacklist"));
            }
            if (root.has("entity_factions")) {
                readStringSetMap(requireObject(root, "entity_factions", resourceId), factionsByEntity, resourceId, "entity_factions");
            }
            if (root.has("entity_traits")) {
                readStringSetMap(requireObject(root, "entity_traits", resourceId), traitsByEntity, resourceId, "entity_traits");
            }
            if (root.has("faction_relations")) {
                JsonObject relations = requireObject(root, "faction_relations", resourceId);
                for (Map.Entry<String, JsonElement> entry : relations.entrySet()) {
                    if (!entry.getValue().isJsonObject()) {
                        throw new IllegalArgumentException(resourceId + ": faction relation for " + entry.getKey() + " must be an object");
                    }
                    JsonObject relation = entry.getValue().getAsJsonObject();
                    MorphFactionRelations parsed = new MorphFactionRelations(
                        readOptionalStringSet(relation, "allies", resourceId),
                        readOptionalStringSet(relation, "enemies", resourceId),
                        readOptionalStringSet(relation, "fears", resourceId)
                    );
                    putUnique(factionRelations, entry.getKey(), parsed, resourceId, "faction relation");
                }
            }
            if (root.has("hostility_memory_seconds")) {
                int configured;
                try {
                    configured = root.get("hostility_memory_seconds").getAsInt();
                } catch (RuntimeException exception) {
                    throw new IllegalArgumentException(resourceId + ": invalid hostility_memory_seconds", exception);
                }
                if (configured <= 0) throw new IllegalArgumentException(resourceId + ": hostility_memory_seconds must be > 0");
                if (hostilityMemorySeconds != null && hostilityMemorySeconds != configured) {
                    throw new IllegalArgumentException(resourceId + ": conflicting hostility_memory_seconds values");
                }
                hostilityMemorySeconds = configured;
            }
        }

        validateRelationTargets(factionsByEntity, factionRelations);
        MorphCategoryCatalog.replace(
            overrides,
            blacklist,
            factionsByEntity,
            traitsByEntity,
            factionRelations,
            hostilityMemorySeconds == null
                ? MorphCategoryCatalog.DEFAULT_HOSTILITY_MEMORY_SECONDS
                : hostilityMemorySeconds
        );
    }

    private static void validateRelationTargets(
        Map<String, Set<String>> factionsByEntity,
        Map<String, MorphFactionRelations> relations
    ) {
        Set<String> knownFactions = new LinkedHashSet<>(relations.keySet());
        factionsByEntity.values().forEach(knownFactions::addAll);
        for (Map.Entry<String, MorphFactionRelations> entry : relations.entrySet()) {
            Set<String> targets = new LinkedHashSet<>();
            targets.addAll(entry.getValue().allies());
            targets.addAll(entry.getValue().enemies());
            targets.addAll(entry.getValue().fears());
            for (String target : targets) {
                if (!knownFactions.contains(target)) {
                    throw new IllegalArgumentException("unknown morph faction relation target " + entry.getKey() + " -> " + target);
                }
            }
        }
    }

    private static JsonObject requireObject(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.get(key).isJsonObject()) throw new IllegalArgumentException(resourceId + ": " + key + " must be an object");
        return root.getAsJsonObject(key);
    }

    private static Set<String> readOptionalStringSet(JsonObject root, String key, ResourceLocation resourceId) {
        return root.has(key) ? readStringSet(root.get(key), resourceId, key) : Set.of();
    }

    private static Set<String> readStringSet(JsonElement element, ResourceLocation resourceId, String field) {
        if (!element.isJsonArray()) throw new IllegalArgumentException(resourceId + ": " + field + " must be an array");
        JsonArray array = element.getAsJsonArray();
        Set<String> values = new LinkedHashSet<>();
        for (JsonElement value : array) {
            String text = value.getAsString();
            if (text.isBlank()) throw new IllegalArgumentException(resourceId + ": blank value in " + field);
            if (!values.add(text)) throw new IllegalArgumentException(resourceId + ": duplicate value " + text + " in " + field);
        }
        return Set.copyOf(values);
    }

    private static void readStringSetMap(
        JsonObject object,
        Map<String, Set<String>> target,
        ResourceLocation resourceId,
        String field
    ) {
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            putUnique(target, entry.getKey(), readStringSet(entry.getValue(), resourceId, field + "." + entry.getKey()), resourceId, field);
        }
    }

    private static <T> void putUnique(Map<String, T> target, String key, T value, ResourceLocation resourceId, String field) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException(resourceId + ": blank key in " + field);
        if (target.putIfAbsent(key, value) != null) {
            throw new IllegalArgumentException(resourceId + ": duplicate " + field + " key " + key);
        }
    }
}
