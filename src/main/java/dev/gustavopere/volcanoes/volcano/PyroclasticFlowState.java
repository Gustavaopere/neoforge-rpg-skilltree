package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.UUID;

/** Immutable server-side state for one finite pyroclastic flow head/trail sample. */
public record PyroclasticFlowState(
        UUID volcanoId,
        Vec3 position,
        Vec3 velocity,
        double radiusBlocks,
        double heatSeverity,
        double particulateSeverity,
        long ageTicks,
        long maxLifetimeTicks
) {
    public PyroclasticFlowState {
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        position = requireFinite("position", position);
        velocity = requireFinite("velocity", velocity);
        radiusBlocks = requireNonNegative("radiusBlocks", radiusBlocks);
        heatSeverity = requireUnit("heatSeverity", heatSeverity);
        particulateSeverity = requireUnit("particulateSeverity", particulateSeverity);
        if (ageTicks < 0L) {
            throw new IllegalArgumentException("ageTicks must be non-negative");
        }
        if (maxLifetimeTicks <= 0L) {
            throw new IllegalArgumentException("maxLifetimeTicks must be positive");
        }
    }

    public boolean active() {
        return ageTicks < maxLifetimeTicks
                && radiusBlocks >= 0.25
                && (heatSeverity >= 0.01 || particulateSeverity >= 0.01);
    }

    private static Vec3 requireFinite(String name, Vec3 value) {
        Objects.requireNonNull(value, name);
        if (!Double.isFinite(value.x) || !Double.isFinite(value.y) || !Double.isFinite(value.z)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
