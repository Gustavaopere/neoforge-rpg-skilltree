package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/** Atomic runtime snapshot for datapack-driven pressure exposure gameplay thresholds. */
public final class PressureExposureConfigRuntime {
    private static final ResourceLocation DEFAULT_ID =
            ResourceLocation.fromNamespaceAndPath("volcanoes", "default");
    private static final AtomicReference<PressureExposureConfig> CURRENT =
            new AtomicReference<>(PressureExposureConfig.defaults());

    private PressureExposureConfigRuntime() {
    }

    public static PressureExposureConfig current() {
        return CURRENT.get();
    }

    static void reload(Map<ResourceLocation, JsonElement> definitions) {
        Objects.requireNonNull(definitions, "definitions");
        JsonElement canonical = definitions.get(DEFAULT_ID);
        PressureExposureConfig next = canonical == null
                ? PressureExposureConfig.defaults()
                : PressureExposureConfigDataLoader.parse(canonical);
        CURRENT.set(next);
    }

    static void resetToDefaults() {
        CURRENT.set(PressureExposureConfig.defaults());
    }
}
