package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.List;

/** Read-only integration surface for persisted volcano sites. */
public interface VolcanicRegionService {
    List<VolcanoSite> all();
    List<VolcanoSite> nearby(BlockPos center, double radius);
}
