package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;

/** Pure deterministic dynamics for a finite, terrain-following pyroclastic flow. */
public final class PyroclasticFlowDynamics {
    public static final double MAX_HORIZONTAL_SPEED = 1.40;
    private static final double DRAG = 0.94;
    private static final double SLOPE_ACCELERATION = 0.12;
    private static final double RADIUS_DECAY = 0.995;
    private static final double HEAT_DECAY = 0.990;
    private static final double PARTICULATE_DECAY = 0.995;

    private PyroclasticFlowDynamics() {
    }

    public static PyroclasticFlowState step(
            PyroclasticFlowState state,
            PyroclasticSlopeSample slope,
            boolean blocked
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(slope, "slope");

        long nextAge = state.ageTicks() + 1L;
        if (blocked || nextAge >= state.maxLifetimeTicks() || !state.active()) {
            return terminated(state, nextAge);
        }

        Vec3 downhill = downhill(slope);
        Vec3 dragged = new Vec3(state.velocity().x * DRAG, 0.0, state.velocity().z * DRAG);
        Vec3 accelerated = dragged.add(downhill.scale(SLOPE_ACCELERATION));
        Vec3 bounded = boundHorizontal(accelerated, MAX_HORIZONTAL_SPEED);
        Vec3 nextPosition = state.position().add(bounded.x, 0.0, bounded.z);

        double radius = state.radiusBlocks() * RADIUS_DECAY;
        double heat = state.heatSeverity() * HEAT_DECAY;
        double particulates = state.particulateSeverity() * PARTICULATE_DECAY;
        PyroclasticFlowState next = new PyroclasticFlowState(
                state.volcanoId(),
                nextPosition,
                bounded,
                radius,
                heat,
                particulates,
                nextAge,
                state.maxLifetimeTicks());
        return next.active() ? next : terminated(next, nextAge);
    }

    private static Vec3 downhill(PyroclasticSlopeSample slope) {
        double x = slope.westY() - slope.eastY();
        double z = slope.northY() - slope.southY();
        Vec3 vector = new Vec3(x, 0.0, z);
        double length = vector.horizontalDistance();
        return length <= 1.0e-9 ? Vec3.ZERO : vector.scale(1.0 / length);
    }

    private static Vec3 boundHorizontal(Vec3 velocity, double maxSpeed) {
        double speed = velocity.horizontalDistance();
        if (speed <= maxSpeed || speed <= 1.0e-9) {
            return new Vec3(velocity.x, 0.0, velocity.z);
        }
        double scale = maxSpeed / speed;
        return new Vec3(velocity.x * scale, 0.0, velocity.z * scale);
    }

    private static PyroclasticFlowState terminated(PyroclasticFlowState state, long ageTicks) {
        return new PyroclasticFlowState(
                state.volcanoId(),
                state.position(),
                Vec3.ZERO,
                0.0,
                0.0,
                0.0,
                ageTicks,
                state.maxLifetimeTicks());
    }
}
