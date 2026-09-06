package dev.gustavopere.volcanoes.volcano;

/**
 * Fail-closed policy for limited natural terrain interaction by pyroclastic flows.
 *
 * <p>The caller resolves protection-authority readiness, loaded-chunk state, natural-terrain tags,
 * claim/colony protection and block entities. Recursive destruction is intentionally absent.</p>
 */
public final class PyroclasticTerrainPolicy {
    private PyroclasticTerrainPolicy() {
    }

    public static PyroclasticTerrainPolicy safeDefaults() {
        return new PyroclasticTerrainPolicy();
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
