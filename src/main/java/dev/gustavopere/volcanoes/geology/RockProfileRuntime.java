package dev.gustavopere.volcanoes.geology;

import com.google.gson.JsonElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Process-wide immutable rock-profile snapshot used by world-facing systems. */
public final class RockProfileRuntime {
    private static final RockProfileReloadState STATE =
            new RockProfileReloadState(RockProfileRegistry.vanillaDefaults());

    private RockProfileRuntime() {
    }

    public static RockProfileRegistry current() {
        return STATE.current();
    }

    public static RockProfile resolve(BlockState state) {
        Objects.requireNonNull(state, "state");
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        List<ResourceLocation> tags = state.getTags()
                .map(tag -> tag.location())
                .toList();
        return resolve(blockId, tags);
    }

    static RockProfile resolve(ResourceLocation blockId, List<ResourceLocation> tags) {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(tags, "tags");
        return current().resolve(blockId, tags);
    }

    static void reload(Map<ResourceLocation, JsonElement> definitions) {
        STATE.reload(definitions);
    }
}
