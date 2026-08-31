package dev.gustavopere.rpgskilltree.core;

/** Current runtime availability of a specialization's external provider contract. */
public record SpecializationAvailability(
    boolean providerLoaded,
    boolean runtimeAdapterComplete
) {
    public boolean gatewayAvailable() {
        return providerLoaded && runtimeAdapterComplete;
    }

    public static SpecializationAvailability internal() {
        return new SpecializationAvailability(true, true);
    }
}
