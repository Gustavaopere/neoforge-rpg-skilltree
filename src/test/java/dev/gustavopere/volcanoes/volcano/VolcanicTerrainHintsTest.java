package dev.gustavopere.volcanoes.volcano;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicTerrainHintsTest {
    @Test
    void recognizesCommonTfcAndExtensionBiomeTags() {
        assertTrue(VolcanicTerrainHints.isKnownBiomeTagId(id("c", "is_volcanic")));
        assertTrue(VolcanicTerrainHints.isKnownBiomeTagId(id("tfc", "is_rift")));
        assertTrue(VolcanicTerrainHints.isKnownBiomeTagId(id("volcanoes", "is_volcanic")));
        assertFalse(VolcanicTerrainHints.isKnownBiomeTagId(id("minecraft", "is_forest")));
    }

    @Test
    void doesNotHardCodeExternalBiomeIdsInCore() {
        assertFalse(VolcanicTerrainHints.isKnownBiomeId(id("terralith", "volcanic_crater")));
        assertFalse(VolcanicTerrainHints.isKnownBiomeId(id("terralith", "volcanic_peaks")));
        assertFalse(VolcanicTerrainHints.isKnownBiomeId(id("terralith", "caldera")));
        assertFalse(VolcanicTerrainHints.isKnownBiomeId(id("terralith", "basalt_cliffs")));
        assertFalse(VolcanicTerrainHints.isKnownBiomeId(id("minecraft", "plains")));
    }

    @Test
    void volcanicTerrainHintOnlyStrengthensLocalShape() {
        assertTrue(VolcanicTerrainHints.shapeMultiplier(true) > VolcanicTerrainHints.shapeMultiplier(false));
        assertTrue(VolcanicTerrainHints.shapeMultiplier(false) >= 1.0);
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
