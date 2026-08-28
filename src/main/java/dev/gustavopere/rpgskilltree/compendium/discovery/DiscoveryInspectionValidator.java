package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;

/** Pure validation helper for server-observed inspection facts. */
public final class DiscoveryInspectionValidator {
    private DiscoveryInspectionValidator() {}

    public static boolean isValid(
        CompendiumEntryId claimedEntryId,
        CompendiumEntryId observedEntryId,
        double distanceSquared,
        double maximumDistanceSquared,
        boolean requiredToolPresent,
        boolean serverStateValid
    ) {
        if (claimedEntryId == null || observedEntryId == null) return false;
        if (!claimedEntryId.equals(observedEntryId)) return false;
        if (!requiredToolPresent || !serverStateValid) return false;
        if (!Double.isFinite(distanceSquared) || !Double.isFinite(maximumDistanceSquared)) return false;
        if (distanceSquared < 0.0D || maximumDistanceSquared < 0.0D) return false;
        return distanceSquared <= maximumDistanceSquared;
    }
}
