package dev.gustavopere.rpgskilltree.core.economy;

/** Deterministic server-side parameters for the V1 economy model. */
public record EconomyParameters(
    long baseCapacity,
    long workerWeight,
    long buildingLevelWeight,
    long warehouseBonus,
    int warehouseCap,
    double beta,
    double minPriceIndex,
    double maxPriceIndex,
    double maxStepUp,
    double maxStepDown
) {
    public EconomyParameters {
        if (baseCapacity <= 0L) {
            throw new IllegalArgumentException("baseCapacity must be positive");
        }
        if (workerWeight < 0L || buildingLevelWeight < 0L || warehouseBonus < 0L) {
            throw new IllegalArgumentException("capacity weights must be non-negative");
        }
        if (warehouseCap < 0) {
            throw new IllegalArgumentException("warehouseCap must be non-negative");
        }
        requireFinitePositive(beta, "beta");
        requireFinitePositive(minPriceIndex, "minPriceIndex");
        requireFinitePositive(maxPriceIndex, "maxPriceIndex");
        requireFinitePositive(maxStepUp, "maxStepUp");
        requireFinitePositive(maxStepDown, "maxStepDown");
        if (minPriceIndex > maxPriceIndex) {
            throw new IllegalArgumentException("minPriceIndex must not exceed maxPriceIndex");
        }
    }

    public static EconomyParameters defaults() {
        return new EconomyParameters(
            2L,
            2L,
            1L,
            2L,
            2,
            1.4306765580733933D,
            25.0D,
            1_000.0D,
            5.0D,
            3.0D
        );
    }

    private static void requireFinitePositive(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }
}
