package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

/** Optional cross-stage protection boundary for claims, colonies and explicit protected structures. */
@FunctionalInterface
public interface VolcanicProtectionService {
    boolean isProtected(ServerLevel level, BlockPos pos);

    /**
     * Whether this provider is authoritative enough to permit destructive natural-terrain mutation.
     *
     * <p>The default is deliberately fail-closed. Stage 03 may always apply non-destructive ash
     * deposition, entity exposure and presentation, but bomb/pyroclastic terrain mutation requires
     * an explicit protection authority that can prove protected areas before changing blocks.</p>
     */
    default boolean allowsTerrainMutation() {
        return false;
    }

    static VolcanicProtectionService none() {
        return (level, pos) -> false;
    }
}
