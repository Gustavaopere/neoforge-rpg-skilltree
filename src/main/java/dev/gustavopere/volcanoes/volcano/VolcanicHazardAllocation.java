package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/** Internal partition of the one grant already assigned to the Stage 03 volcanic-hazard sink. */
public record VolcanicHazardAllocation(
        int ashBlockWork,
        int bombTerrainWork,
        int flowTerrainWork,
        int bombEntityWork,
        int flowSpawnWork
) {
    private static final double FLOW_INTENSITY_THRESHOLD = 0.70;
    private static final double FLOW_EXPLOSIVITY_THRESHOLD = 0.62;

    public VolcanicHazardAllocation {
        if (ashBlockWork < 0
                || bombTerrainWork < 0
                || flowTerrainWork < 0
                || bombEntityWork < 0
                || flowSpawnWork < 0) {
            throw new IllegalArgumentException("hazard work allocations must be non-negative");
        }
    }

    public static VolcanicHazardAllocation from(
            EruptionSignal signal,
            EruptionScheduler.WorkGrant workGrant
    ) {
        Objects.requireNonNull(signal, "signal");
        Objects.requireNonNull(workGrant, "workGrant");

        int entityWork = workGrant.immediateEntities();
        int flowWork = shouldSeedFlow(signal, entityWork) ? 1 : 0;
        int bombWork = entityWork - flowWork;

        int blockWork = workGrant.immediateBlocks();
        int terrainCapacity = Math.max(0, blockWork - 1);
        int bombTerrainWork = bombWork > 0 && bombProducingPhase(signal.phase()) && terrainCapacity > 0 ? 1 : 0;
        int flowTerrainWork = flowWork > 0 && terrainCapacity > bombTerrainWork ? 1 : 0;
        int ashBlockWork = blockWork - bombTerrainWork - flowTerrainWork;

        return new VolcanicHazardAllocation(
                ashBlockWork,
                bombTerrainWork,
                flowTerrainWork,
                bombWork,
                flowWork);
    }

    private static boolean bombProducingPhase(EruptionPhase phase) {
        return switch (phase) {
            case OPENING, SUSTAINED, WANING -> true;
            case PRECURSORS, DORMANT -> false;
        };
    }

    private static boolean shouldSeedFlow(EruptionSignal signal, int entityWork) {
        if (entityWork < 2 || signal.phase() != EruptionPhase.SUSTAINED) {
            return false;
        }
        double peak = signal.profile().peakIntensity();
        double relativeIntensity = peak <= 0.0 ? 0.0 : signal.intensity() / peak;
        MagmaChamber chamber = signal.chamber();
        MagmaComposition composition = chamber.composition();
        double explosivity = composition.silicaFraction() * 0.40
                + composition.volatileRichness() * 0.35
                + chamber.gasFraction() * 0.25;
        return relativeIntensity >= FLOW_INTENSITY_THRESHOLD
                && explosivity >= FLOW_EXPLOSIVITY_THRESHOLD;
    }
}
