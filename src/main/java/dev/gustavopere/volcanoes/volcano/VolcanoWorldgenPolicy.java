package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.level.ChunkPos;

import java.util.Objects;

/** Server-side guard for promoting deterministic worldgen sites into persistent SavedData. */
public final class VolcanoWorldgenPolicy {
    private VolcanoWorldgenPolicy() {
    }

    public static boolean shouldRegisterSite(
            boolean newlyGeneratedChunk,
            ChunkPos loadedChunk,
            VolcanoSite site
    ) {
        Objects.requireNonNull(loadedChunk, "loadedChunk");
        Objects.requireNonNull(site, "site");
        return newlyGeneratedChunk && loadedChunk.equals(new ChunkPos(site.center()));
    }
}
