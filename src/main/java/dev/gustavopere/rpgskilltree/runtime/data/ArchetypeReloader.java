package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ArchetypeDefinition;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.util.ArrayList;
import java.util.EnumMap;
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

/** Loads emergent archetype definitions without yet coupling them to purchased-node investment. */
public final class ArchetypeReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public ArchetypeReloader() {
        super(GSON, "archetypes");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ArchetypeReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<ArchetypeDefinition> definitions = new ArrayList<>();
        for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
            if (!resource.getValue().isJsonObject()) {
                throw new IllegalArgumentException(resource.getKey() + ": archetype root must be an object");
            }
            JsonObject root = resource.getValue().getAsJsonObject();
            String id = requiredString(root, "id", resource.getKey());
            int priority = requiredInt(root, "priority", resource.getKey());

            EnumMap<ProgressionDomain, Integer> minimumDomainScores = new EnumMap<>(ProgressionDomain.class);
            JsonObject scores = requiredObject(root, "minimum_domain_scores", resource.getKey());
            for (Map.Entry<String, JsonElement> entry : scores.entrySet()) {
                ProgressionDomain domain;
                try {
                    domain = ProgressionDomain.valueOf(entry.getKey());
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException(resource.getKey() + ": unknown progression domain " + entry.getKey(), exception);
                }
                int score = entry.getValue().getAsInt();
                if (score < 0) {
                    throw new IllegalArgumentException(resource.getKey() + ": negative minimum score for " + entry.getKey());
                }
                minimumDomainScores.put(domain, score);
            }

            Set<String> requiredTags = stringSet(root, "required_tags", resource.getKey());
            Set<String> forbiddenTags = stringSet(root, "forbidden_tags", resource.getKey());
            Set<String> overlap = new HashSet<>(requiredTags);
            overlap.retainAll(forbiddenTags);
            if (!overlap.isEmpty()) {
                throw new IllegalArgumentException(resource.getKey() + ": tags cannot be both required and forbidden: " + overlap);
            }

            int compatibilitySpecificity = minimumDomainScores.size() + requiredTags.size() + forbiddenTags.size();
            int specificityScore = root.has("specificity_score")
                ? requiredInt(root, "specificity_score", resource.getKey())
                : compatibilitySpecificity;
            if (specificityScore < 0) {
                throw new IllegalArgumentException(resource.getKey() + ": specificity_score must be >= 0");
            }

            definitions.add(new ArchetypeDefinition(
                id,
                priority,
                specificityScore,
                minimumDomainScores,
                requiredTags,
                forbiddenTags
            ));
        }
        ArchetypeCatalog.replace(definitions);
    }

    private static JsonObject requiredObject(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key) || !root.get(key).isJsonObject()) {
            throw new IllegalArgumentException(resourceId + ": missing object " + key);
        }
        return root.getAsJsonObject(key);
    }

    private static String requiredString(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
            throw new IllegalArgumentException(resourceId + ": missing string " + key);
        }
        String value = root.get(key).getAsString();
        if (value.isBlank()) throw new IllegalArgumentException(resourceId + ": blank " + key);
        return value;
    }

    private static int requiredInt(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
            throw new IllegalArgumentException(resourceId + ": missing integer " + key);
        }
        try {
            return root.get(key).getAsInt();
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(resourceId + ": invalid integer " + key, exception);
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
            String text = value.getAsString();
            if (text.isBlank()) throw new IllegalArgumentException(resourceId + ": blank tag in " + key);
            if (!result.add(text)) throw new IllegalArgumentException(resourceId + ": duplicate tag " + text + " in " + key);
        }
        return Set.copyOf(result);
    }
}
