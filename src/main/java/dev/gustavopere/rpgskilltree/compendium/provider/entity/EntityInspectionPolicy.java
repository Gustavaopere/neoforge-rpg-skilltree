package dev.gustavopere.rpgskilltree.compendium.provider.entity;

/** Server-side gate for bounded entity inspection requests. */
public record EntityInspectionPolicy(double maximumDistanceSquared, boolean requireLineOfSight) {
    public EntityInspectionPolicy {
        if (!Double.isFinite(maximumDistanceSquared) || maximumDistanceSquared < 0.0D) {
            throw new IllegalArgumentException("maximumDistanceSquared must be finite and non-negative");
        }
    }

    public boolean allows(double distanceSquared, boolean hasLineOfSight) {
        if (!Double.isFinite(distanceSquared) || distanceSquared < 0.0D) return false;
        if (distanceSquared > maximumDistanceSquared) return false;
        return !requireLineOfSight || hasLineOfSight;
    }
}
