package dev.gustavopere.rpgskilltree.runtime.itemization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Loads data-driven equipment classification overrides from datapacks. */
public final class EquipmentClassificationReloader extends SimpleJsonResourceReloadListener {
    public static final String DIRECTORY = "itemization/equipment_classification";
    private static final Gson GSON = new GsonBuilder().create();

    public EquipmentClassificationReloader() {
        super(GSON, DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new EquipmentClassificationReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        EquipmentClassificationReloadService.reload(resources);
    }
}
