package dev.gustavopere.volcanoes.pressure;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.slf4j.Logger;

import java.util.Map;

/** Server datapack reload bridge for pressure exposure thresholds. */
@EventBusSubscriber(modid = VolcanoesMod.MOD_ID)
public final class PressureExposureConfigReloadListener extends SimpleJsonResourceReloadListener {
    public static final String DIRECTORY = "pressure_exposure";

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();

    public PressureExposureConfigReloadListener() {
        super(GSON, DIRECTORY);
    }

    @Override
    protected void apply(
            Map<ResourceLocation, JsonElement> definitions,
            ResourceManager resourceManager,
            ProfilerFiller profiler
    ) {
        try {
            PressureExposureConfigRuntime.reload(definitions);
            LOGGER.info("Loaded Volcanoes pressure exposure configuration");
        } catch (RuntimeException exception) {
            LOGGER.error("Rejected Volcanoes pressure exposure reload; retaining previous snapshot", exception);
        }
    }

    @SubscribeEvent
    public static void register(AddReloadListenerEvent event) {
        event.addListener(new PressureExposureConfigReloadListener());
    }
}
