package dev.gustavopere.volcanoes.volcano;

/**
 * Fail-closed terrain mutation policy for volcanic bomb impacts.
 *
 * <p>The caller resolves protection-authority readiness, chunk state, natural-block tags,
 * claim/colony protection and block entities. This policy never authorizes force-loading or
 * protected-structure damage.</p>
 */
public final class VolcanicBombImpactPolicy {
    private VolcanicBombImpactPolicy() {
    }

    public static VolcanicBombImpactPolicy safeDefaults() {
        return new VolcanicBombImpactPolicy();
    }

    public boolean canMutate(
            boolean protectionAuthority,
            boolean chunkLoaded,
            boolean naturalTerrain,
            boolean protectedRegion,
            boolean hasBlockEntity
    ) {
        return protectionAuthority
                && chunkLoaded
                && naturalTerrain
                && !protectedRegion
                && !hasBlockEntity;
    }
}
