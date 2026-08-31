package dev.gustavopere.volcanoes.volcano;

/** Pure placement decision for one bounded ash-deposition column. */
public final class AshLayerPlacement {
    public enum Action {
        NONE,
        PLACE_FIRST_LAYER,
        INCREMENT_EXISTING
    }

    private AshLayerPlacement() {
    }

    public static Action decide(
            boolean chunkLoaded,
            boolean taggedReplaceableSurface,
            boolean protectedRegion,
            boolean hasBlockEntity,
            boolean targetFreeOrReplaceable,
            int existingAshLayers
    ) {
        if (existingAshLayers < 0 || existingAshLayers > 8) {
            throw new IllegalArgumentException("existingAshLayers must be within [0, 8]");
        }
        if (!chunkLoaded || protectedRegion || hasBlockEntity) {
            return Action.NONE;
        }
        if (existingAshLayers > 0) {
            return existingAshLayers < 8 ? Action.INCREMENT_EXISTING : Action.NONE;
        }
        return taggedReplaceableSurface && targetFreeOrReplaceable
                ? Action.PLACE_FIRST_LAYER
                : Action.NONE;
    }
}
