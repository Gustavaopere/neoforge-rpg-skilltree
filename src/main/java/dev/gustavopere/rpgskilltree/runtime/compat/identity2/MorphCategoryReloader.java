package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

public final class MorphCategoryReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public MorphCategoryReloader() { super(GSON, "morph_categories"); }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new MorphCategoryReloader());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        Map<String, MorphFormCategory> overrides = new LinkedHashMap<>();
        Set<String> blacklist = new LinkedHashSet<>();
        for (JsonElement element : resources.values()) {
            JsonObject root = element.getAsJsonObject();
            if (root.has("overrides")) {
                root.getAsJsonObject("overrides").entrySet().forEach(entry ->
                    overrides.put(entry.getKey(), MorphFormCategory.valueOf(entry.getValue().getAsString()))
                );
            }
            if (root.has("blacklist")) {
                root.getAsJsonArray("blacklist").forEach(value -> blacklist.add(value.getAsString()));
            }
        }
        MorphCategoryCatalog.replace(overrides, blacklist);
    }
}
