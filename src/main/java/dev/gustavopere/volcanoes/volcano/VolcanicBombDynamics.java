package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;

/** Pure ballistic evolution for one volcanic bomb; collision remains a level-aware concern. */
public final class VolcanicBombDynamics {
    private static final double GRAVITY_PER_TICK = 0.04;
    private static final double DRAG = 0.99;

    private VolcanicBombDynamics() {
    }

    public static VolcanicBombState step(VolcanicBombState state) {
        Objects.requireNonNull(state, "state");
        if (!state.active()) {
            return state;
        }
        Vec3 nextPosition = state.position().add(state.velocity());
        Vec3 nextVelocity = new Vec3(
                state.velocity().x * DRAG,
                (state.velocity().y - GRAVITY_PER_TICK) * DRAG,
                state.velocity().z * DRAG);
        return new VolcanicBombState(
                state.volcanoId(),
                nextPosition,
                nextVelocity,
                state.ageTicks() + 1L,
                state.maxLifetimeTicks());
    }
}
