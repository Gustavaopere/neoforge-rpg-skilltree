package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.core.TreeUnlockDefinition;
import java.util.ArrayList;
import java.util.EnumMap;
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

/** Loads data-driven specialist-tree unlock gates without activating them as player state by itself. */
public final class TreeUnlockReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public TreeUnlockReloader() {
        super(GSON, "tree_unlocks");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new TreeUnlockReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<TreeUnlockDefinition> definitions = new ArrayList<>();
        for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
            if (!resource.getValue().isJsonObject()) {
                throw new IllegalArgumentException(resource.getKey() + ": tree unlock root must be an object");
            }
            JsonObject root = resource.getValue().getAsJsonObject();
            String treeId = requiredString(root, "tree_id", resource.getKey());

            Map<ProgressionDomain, Integer> domains = new EnumMap<>(ProgressionDomain.class);
            if (root.has("minimum_domain_scores")) {
                if (!root.get("minimum_domain_scores").isJsonObject()) {
                    throw new IllegalArgumentException(resource.getKey() + ": minimum_domain_scores must be an object");
                }
                for (Map.Entry<String, JsonElement> entry : root.getAsJsonObject("minimum_domain_scores").entrySet()) {
                    ProgressionDomain domain;
                    try {
                        domain = ProgressionDomain.valueOf(entry.getKey());
                    } catch (IllegalArgumentException exception) {
                        throw new IllegalArgumentException(resource.getKey() + ": unknown progression domain " + entry.getKey(), exception);
                    }
                    int required = nonnegativeInt(entry.getValue(), resource.getKey() + ": minimum_domain_scores." + entry.getKey());
                    domains.put(domain, required);
                }
            }

            Set<String> requiredTags = stringSet(root, "required_tags", resource.getKey());
            Map<String, Integer> mastery = new HashMap<>();
            if (root.has("minimum_mastery_experience")) {
                if (!root.get("minimum_mastery_experience").isJsonObject()) {
                    throw new IllegalArgumentException(resource.getKey() + ": minimum_mastery_experience must be an object");
                }
                for (Map.Entry<String, JsonElement> entry : root.getAsJsonObject("minimum_mastery_experience").entrySet()) {
                    if (entry.getKey().isBlank()) {
                        throw new IllegalArgumentException(resource.getKey() + ": mastery lane must not be blank");
                    }
                    mastery.put(entry.getKey(), nonnegativeInt(
                        entry.getValue(),
                        resource.getKey() + ": minimum_mastery_experience." + entry.getKey()
                    ));
                }
            }

            definitions.add(new TreeUnlockDefinition(treeId, domains, requiredTags, mastery));
        }
        TreeUnlockCatalog.replace(definitions);
    }

    private static String requiredString(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
            throw new IllegalArgumentException(resourceId + ": missing string " + key);
        }
        String value = root.get(key).getAsString();
        if (value.isBlank()) throw new IllegalArgumentException(resourceId + ": blank " + key);
        return value;
    }

    private static int nonnegativeInt(JsonElement value, String label) {
        try {
            int parsed = value.getAsInt();
            if (parsed < 0) throw new IllegalArgumentException(label + " must be >= 0");
            return parsed;
        } catch (RuntimeException exception) {
            if (exception instanceof IllegalArgumentException illegalArgumentException
                && illegalArgumentException.getMessage() != null
                && illegalArgumentException.getMessage().startsWith(label)) {
                throw illegalArgumentException;
            }
            throw new IllegalArgumentException(label + " must be a nonnegative integer", exception);
        }
    }

    private static Set<String> stringSet(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key)) return Set.of();
        if (!root.get(key).isJsonArray()) {
            throw new IllegalArgumentException(resourceId + ": " + key + " must be an array");
        }
        JsonArray values = root.getAsJsonArray(key);
        Set<String> result = new HashSet<>();
        for (JsonElement value : values) {
            if (!value.isJsonPrimitive()) {
                throw new IllegalArgumentException(resourceId + ": " + key + " values must be strings");
            }
            String text = value.getAsString();
            if (text.isBlank()) throw new IllegalArgumentException(resourceId + ": blank value in " + key);
            if (!result.add(text)) throw new IllegalArgumentException(resourceId + ": duplicate value " + text + " in " + key);
        }
        return Set.copyOf(result);
    }
}
