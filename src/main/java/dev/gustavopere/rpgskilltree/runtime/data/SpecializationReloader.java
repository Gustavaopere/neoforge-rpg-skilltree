package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.SpecializationAvailability;
import dev.gustavopere.rpgskilltree.core.SpecializationDefinition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Loads data-driven specialization eligibility definitions independently from node grants. */
public final class SpecializationReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public SpecializationReloader() {
        super(GSON, "specializations");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new SpecializationReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        load(
            resources,
            provider -> provider.equals("rpgskilltree") || ModList.get().isLoaded(provider),
            SpecializationProviderRuntimePolicy::hasCompleteAdapter
        );
    }

    static void load(
        Map<ResourceLocation, JsonElement> resources,
        Predicate<String> isProviderLoaded,
        Predicate<String> hasCompleteAdapter
    ) {
        Objects.requireNonNull(resources, "resources");
        Objects.requireNonNull(isProviderLoaded, "isProviderLoaded");
        Objects.requireNonNull(hasCompleteAdapter, "hasCompleteAdapter");

        List<SpecializationDefinition> definitions = new ArrayList<>();
        Map<String, SpecializationAvailability> availability = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> resource : resources.entrySet()) {
            if (!resource.getValue().isJsonObject()) {
                throw new IllegalArgumentException(resource.getKey() + ": specialization root must be an object");
            }
            JsonObject root = resource.getValue().getAsJsonObject();
            String id = requiredString(root, "specialization_id", resource.getKey());
            String provider = requiredString(root, "provider", resource.getKey());
            Set<String> eligibleClasses = stringSet(root, "eligible_class_ids", resource.getKey());
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
                    int required;
                    try {
                        required = entry.getValue().getAsInt();
                    } catch (RuntimeException exception) {
                        throw new IllegalArgumentException(resource.getKey() + ": invalid mastery requirement for " + entry.getKey(), exception);
                    }
                    if (required < 0) {
                        throw new IllegalArgumentException(resource.getKey() + ": mastery requirement must be >= 0 for " + entry.getKey());
                    }
                    mastery.put(entry.getKey(), required);
                }
            }

            definitions.add(new SpecializationDefinition(
                id,
                provider,
                eligibleClasses,
                mastery,
                requiredTags
            ));
            availability.put(id, new SpecializationAvailability(
                provider.equals("rpgskilltree") || isProviderLoaded.test(provider),
                hasCompleteAdapter.test(provider)
            ));
        }
        SpecializationCatalog.replace(definitions, availability);
    }

    private static String requiredString(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
            throw new IllegalArgumentException(resourceId + ": missing string " + key);
        }
        String value = root.get(key).getAsString();
        if (value.isBlank()) throw new IllegalArgumentException(resourceId + ": blank " + key);
        return value;
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
