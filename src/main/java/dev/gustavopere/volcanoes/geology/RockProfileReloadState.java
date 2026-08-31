package dev.gustavopere.volcanoes.geology;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/** Holds the currently published immutable rock-profile snapshot. */
public final class RockProfileReloadState {
    private final AtomicReference<RockProfileRegistry> current;

    public RockProfileReloadState(RockProfileRegistry initial) {
        this.current = new AtomicReference<>(Objects.requireNonNull(initial, "initial"));
    }

    public RockProfileRegistry current() {
        return current.get();
    }

    /**
     * Parses a complete replacement snapshot before publishing it. If parsing throws, the old snapshot remains active.
     */
    public void reload(Map<ResourceLocation, JsonElement> definitions) {
        RockProfileRegistry next = RockProfileDataLoader.load(definitions);
        current.set(next);
    }
}
