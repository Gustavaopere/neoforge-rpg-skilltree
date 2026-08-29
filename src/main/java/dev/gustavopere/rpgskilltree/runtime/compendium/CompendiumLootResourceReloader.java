package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootSnapshot;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Builds immutable Compendium loot summaries from data-pack JSON without rolling loot tables. */
public final class CompendiumLootResourceReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static final String DIRECTORY = "loot_table/entities";

    public CompendiumLootResourceReloader() {
        super(GSON, DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new CompendiumLootResourceReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        LinkedHashMap<String, Map<String, Object>> documents = new LinkedHashMap<>();
        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> documents.put(toTableId(entry.getKey()), decodeObject(entry.getKey(), entry.getValue())));

        CompendiumLootSnapshot candidate = CompendiumLootSnapshot.stage(documents);
        RuntimeCompendiumLootCatalog.publish(candidate);
    }

    private static String toTableId(ResourceLocation resourceId) {
        String path = resourceId.getPath();
        if (path.endsWith(".json")) path = path.substring(0, path.length() - 5);
        if (path.startsWith("loot_table/")) path = path.substring("loot_table/".length());
        if (!path.startsWith("entities/")) path = "entities/" + path;
        return resourceId.getNamespace() + ":" + path;
    }

    private static Map<String, Object> decodeObject(ResourceLocation resourceId, JsonElement value) {
        if (value == null || !value.isJsonObject()) {
            throw new IllegalArgumentException("loot document root must be an object: " + resourceId);
        }
        return decodeJsonObject(value.getAsJsonObject());
    }

    private static Map<String, Object> decodeJsonObject(JsonObject object) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            result.put(entry.getKey(), decodeJson(entry.getValue()));
        }
        return Map.copyOf(result);
    }

    private static List<Object> decodeJsonArray(JsonArray array) {
        ArrayList<Object> result = new ArrayList<>(array.size());
        for (JsonElement value : array) result.add(decodeJson(value));
        return List.copyOf(result);
    }

    private static Object decodeJson(JsonElement value) {
        if (value == null || value.isJsonNull()) return null;
        if (value.isJsonObject()) return decodeJsonObject(value.getAsJsonObject());
        if (value.isJsonArray()) return decodeJsonArray(value.getAsJsonArray());
        if (value.getAsJsonPrimitive().isBoolean()) return value.getAsBoolean();
        if (value.getAsJsonPrimitive().isNumber()) return value.getAsNumber();
        return value.getAsString();
    }
}
