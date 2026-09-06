package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.Objects;

/** Host-neutral positive terrain hint used during deterministic volcano-site admission. */
@FunctionalInterface
public interface VolcanicTerrainHintProvider {
    boolean isVolcanic(BlockPos center);

    static VolcanicTerrainHintProvider none() {
        return center -> {
            Objects.requireNonNull(center, "center");
            return false;
        };
    }
}
