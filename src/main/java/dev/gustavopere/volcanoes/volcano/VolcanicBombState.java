package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.UUID;

/** Immutable tick state for one server-authoritative volcanic bomb trajectory. */
public record VolcanicBombState(
        UUID volcanoId,
        Vec3 position,
        Vec3 velocity,
        long ageTicks,
        long maxLifetimeTicks
) {
    public VolcanicBombState {
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        position = requireFinite("position", position);
        velocity = requireFinite("velocity", velocity);
        if (ageTicks < 0L) {
            throw new IllegalArgumentException("ageTicks must be non-negative");
        }
        if (maxLifetimeTicks <= 0L) {
            throw new IllegalArgumentException("maxLifetimeTicks must be positive");
        }
    }

    public static VolcanicBombState fromLaunch(VolcanicBombLaunch launch) {
        Objects.requireNonNull(launch, "launch");
        return new VolcanicBombState(
                launch.volcanoId(),
                launch.origin(),
                launch.velocity(),
                0L,
                launch.maxLifetimeTicks());
    }

    public boolean active() {
        return ageTicks < maxLifetimeTicks;
    }

    private static Vec3 requireFinite(String name, Vec3 value) {
        Objects.requireNonNull(value, name);
        if (!Double.isFinite(value.x) || !Double.isFinite(value.y) || !Double.isFinite(value.z)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }
}
