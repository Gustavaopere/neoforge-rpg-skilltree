package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AshLayerPlacementTest {
    @Test
    void eligibleBareSurfacePlacesFirstLayer() {
        assertEquals(
                AshLayerPlacement.Action.PLACE_FIRST_LAYER,
                AshLayerPlacement.decide(true, true, false, false, true, 0));
    }

    @Test
    void existingAshIncrementsUntilEightLayersThenStops() {
        assertEquals(
                AshLayerPlacement.Action.INCREMENT_EXISTING,
                AshLayerPlacement.decide(true, false, false, false, false, 4));
        assertEquals(
                AshLayerPlacement.Action.NONE,
                AshLayerPlacement.decide(true, false, false, false, false, 8));
    }

    @Test
    void protectedOrUnloadedSurfaceNeverChanges() {
        assertEquals(
                AshLayerPlacement.Action.NONE,
                AshLayerPlacement.decide(false, true, false, false, true, 0));
        assertEquals(
                AshLayerPlacement.Action.NONE,
                AshLayerPlacement.decide(true, true, true, false, true, 0));
    }
}
