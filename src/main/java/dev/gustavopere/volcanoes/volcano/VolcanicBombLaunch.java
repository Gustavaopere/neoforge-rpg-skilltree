package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.UUID;

/** Immutable server-side launch description for one volcanic bomb. */
public record VolcanicBombLaunch(
        UUID volcanoId,
        Vec3 origin,
        Vec3 velocity,
        long maxLifetimeTicks
) {
    public VolcanicBombLaunch {
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        origin = requireFinite("origin", origin);
        velocity = requireFinite("velocity", velocity);
        if (velocity.lengthSqr() <= 0.0) {
            throw new IllegalArgumentException("velocity must be non-zero");
        }
        if (maxLifetimeTicks <= 0L) {
            throw new IllegalArgumentException("maxLifetimeTicks must be positive");
        }
    }

    private static Vec3 requireFinite(String name, Vec3 value) {
        Objects.requireNonNull(value, name);
        if (!Double.isFinite(value.x) || !Double.isFinite(value.y) || !Double.isFinite(value.z)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }
}
