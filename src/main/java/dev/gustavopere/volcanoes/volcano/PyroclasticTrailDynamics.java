package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/** Pure deterministic decay for stationary pyroclastic trail samples. */
public final class PyroclasticTrailDynamics {
    private static final double RADIUS_DECAY = 0.970;
    private static final double HEAT_DECAY = 0.960;
    private static final double PARTICULATE_DECAY = 0.980;

    private PyroclasticTrailDynamics() {
    }

    public static PyroclasticTrailState step(PyroclasticTrailState state) {
        Objects.requireNonNull(state, "state");
        long nextAge = state.ageTicks() + 1L;
        if (!state.active() || nextAge >= state.maxLifetimeTicks()) {
            return terminated(state, nextAge);
        }

        PyroclasticTrailState next = new PyroclasticTrailState(
                state.volcanoId(),
                state.position(),
                state.radiusBlocks() * RADIUS_DECAY,
                state.heatSeverity() * HEAT_DECAY,
                state.particulateSeverity() * PARTICULATE_DECAY,
                nextAge,
                state.maxLifetimeTicks());
        return next.active() ? next : terminated(next, nextAge);
    }

    private static PyroclasticTrailState terminated(PyroclasticTrailState state, long ageTicks) {
        return new PyroclasticTrailState(
                state.volcanoId(),
                state.position(),
                0.0,
                0.0,
                0.0,
                ageTicks,
                state.maxLifetimeTicks());
    }
}
