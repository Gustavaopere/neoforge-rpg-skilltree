package dev.gustavopere.rpgskilltree.core.economy;

/** Deterministic server-side parameters for the audited V1 economy model. */
public record EconomyParameters(
    long baseQ,
    long workerWeight,
    long buildingLevelWeight,
    double warehouseBonus,
    int warehouseCap,
    long minQ,
    double beta,
    double minPriceIndex,
    double maxPriceIndex,
    double maxStepUp,
    double maxStepDown
) {
    public EconomyParameters {
        if (baseQ <= 0L || minQ <= 0L) {
            throw new IllegalArgumentException("Q baselines must be positive");
        }
        if (workerWeight < 0L || buildingLevelWeight < 0L) {
            throw new IllegalArgumentException("capacity weights must be non-negative");
        }
        if (!Double.isFinite(warehouseBonus) || warehouseBonus < 0.0D) {
            throw new IllegalArgumentException("warehouseBonus must be finite and non-negative");
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
            0.10D,
            2,
            1L,
            0.50D,
            50.0D,
            500.0D,
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
