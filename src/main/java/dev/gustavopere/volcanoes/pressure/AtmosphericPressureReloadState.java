package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/** Atomic immutable snapshot holder for datapack-driven atmospheric pressure definitions. */
public final class AtmosphericPressureReloadState {
    private final AtomicReference<AtmosphericPressureRegistry> current;

    public AtmosphericPressureReloadState(AtmosphericPressureRegistry initial) {
        current = new AtomicReference<>(Objects.requireNonNull(initial, "initial"));
    }

    public AtmosphericPressureRegistry current() {
        return current.get();
    }

    public void reload(Map<ResourceLocation, JsonElement> definitions) {
        AtmosphericPressureRegistry next = AtmosphericPressureDataLoader.load(definitions);
        current.set(next);
    }
}
