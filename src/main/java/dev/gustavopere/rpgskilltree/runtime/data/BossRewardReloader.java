package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.BossRewardDefinition;
import dev.gustavopere.rpgskilltree.core.BossRewardRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Loads namespace-level passive-point boss rewards from the datapack boss_rewards directory. */
public final class BossRewardReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public BossRewardReloader() {
        super(GSON, "boss_rewards");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new BossRewardReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        Map<String, BossRewardDefinition> definitions = new HashMap<>(
            BossRewardRegistry.defaults().namespaceDefaults()
        );

        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> applyResource(entry.getKey(), entry.getValue(), definitions));

        BossRewardCatalog.replace(definitions);
    }

    private static void applyResource(
        ResourceLocation resourceId,
        JsonElement element,
        Map<String, BossRewardDefinition> definitions
    ) {
        JsonObject root = element.getAsJsonObject();
        if (!root.has("namespace_defaults")) return;

        JsonObject defaults = root.getAsJsonObject("namespace_defaults");
        for (Map.Entry<String, JsonElement> entry : defaults.entrySet()) {
            String namespace = entry.getKey();
            int points = entry.getValue().getAsInt();
            if (namespace.isBlank()) {
                throw new IllegalArgumentException("blank boss reward namespace in " + resourceId);
            }
            definitions.put(namespace, new BossRewardDefinition(namespace + ":boss", points));
        }
    }
}
