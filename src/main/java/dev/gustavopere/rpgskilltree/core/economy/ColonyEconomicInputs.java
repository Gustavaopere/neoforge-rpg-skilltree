package dev.gustavopere.rpgskilltree.core.economy;

/** Bounded provider-neutral inputs used to derive economic capacity Q. */
public record ColonyEconomicInputs(
    int adultWorkers,
    int builtLevelPoints,
    int warehouseCount
) {
    public ColonyEconomicInputs {
        if (adultWorkers < 0 || builtLevelPoints < 0 || warehouseCount < 0) {
            throw new IllegalArgumentException("economic inputs must be non-negative");
        }
    }
}
