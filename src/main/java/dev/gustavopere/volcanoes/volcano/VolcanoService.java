package dev.gustavopere.volcanoes.volcano;

import java.util.Optional;

@FunctionalInterface
public interface VolcanoService {
    Optional<VolcanoSite> nearest(long worldSeed, double x, double z, double maxDistanceBlocks);

    static VolcanoService fallback() {
        return (worldSeed, x, z, maxDistanceBlocks) -> Optional.empty();
    }
}
