package dev.gustavopere.volcanoes.volcano;

/**
 * Fail-closed eligibility policy for ash surface deposition.
 *
 * <p>The caller owns tag lookup, claim/colony protection lookup, block-entity detection and target
 * replaceability. An unloaded chunk is never eligible, so this policy cannot authorize force-loading.</p>
 */
public final class AshDepositionPolicy {
    private AshDepositionPolicy() {
    }

    public static AshDepositionPolicy safeDefaults() {
        return new AshDepositionPolicy();
    }

    public boolean canDeposit(
            boolean chunkLoaded,
            boolean taggedReplaceableSurface,
            boolean protectedRegion,
            boolean hasBlockEntity,
            boolean targetFreeOrReplaceable
    ) {
        return chunkLoaded
                && taggedReplaceableSurface
                && !protectedRegion
                && !hasBlockEntity
                && targetFreeOrReplaceable;
    }
}
