package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Neutral Stage 03 facade for bounded volcanic heat queries and source lifecycle updates.
 *
 * <p>Optional integrations such as Cold Sweat consume this surface rather than scanning blocks or
 * depending on geothermal worldgen internals.</p>
 */
public final class VolcanicHeatService {
    private VolcanicHeatService() {
    }

    public static List<VolcanicHeatSource> nearby(
            ServerLevel level,
            BlockPos center,
            double radiusBlocks,
            int maxResults
    ) {
        return GeothermalWorldgenRuntime.heatSourcesNear(
                Objects.requireNonNull(level, "level"),
                Objects.requireNonNull(center, "center"),
                radiusBlocks,
                maxResults);
    }

    public static boolean upsert(ServerLevel level, VolcanicHeatSource source) {
        return GeothermalWorldgenRuntime.upsertHeatSource(
                Objects.requireNonNull(level, "level"),
                Objects.requireNonNull(source, "source"));
    }

    public static boolean remove(ServerLevel level, UUID sourceId) {
        return GeothermalWorldgenRuntime.removeHeatSource(
                Objects.requireNonNull(level, "level"),
                Objects.requireNonNull(sourceId, "sourceId"));
    }
}
