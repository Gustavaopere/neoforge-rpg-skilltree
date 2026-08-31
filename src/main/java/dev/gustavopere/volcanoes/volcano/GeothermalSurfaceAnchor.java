package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.Objects;

/** Pure sampled-surface anchoring kept outside Minecraft Feature bootstrap. */
final class GeothermalSurfaceAnchor {
    private GeothermalSurfaceAnchor() {
    }

    static GeothermalFeaturePlacement anchor(
            GeothermalFeaturePlacement planned,
            BlockPos sampledSurface
    ) {
        Objects.requireNonNull(planned, "planned");
        Objects.requireNonNull(sampledSurface, "sampledSurface");
        if (planned.center().getX() != sampledSurface.getX()
                || planned.center().getZ() != sampledSurface.getZ()) {
            throw new IllegalArgumentException("sampled surface must preserve planner-owned X/Z");
        }
        return new GeothermalFeaturePlacement(
                planned.type(),
                sampledSurface.immutable(),
                planned.radiusBlocks(),
                planned.heatSeverity(),
                planned.gasSeverity(),
                planned.hydrothermalDepositChance());
    }
}
