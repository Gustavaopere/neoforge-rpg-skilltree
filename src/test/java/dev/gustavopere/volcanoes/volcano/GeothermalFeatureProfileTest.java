package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalFeatureProfileTest {
    @Test
    void defaultsCoverEveryPlannedFeatureTypeWithPhysicalConstraints() {
        assertEquals(
                EnumSet.of(
                        GeothermalFeatureType.HOT_SPRING,
                        GeothermalFeatureType.GEYSER,
                        GeothermalFeatureType.FUMAROLE,
                        GeothermalFeatureType.SULFUROUS_VENT,
                        GeothermalFeatureType.MUD_POT),
                EnumSet.allOf(GeothermalFeatureType.class));

        for (GeothermalFeatureType type : GeothermalFeatureType.values()) {
            GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(type);
            assertEquals(type, profile.type());
            assertTrue(profile.minimumPotential() > 0.0 && profile.minimumPotential() <= 1.0);
            assertTrue(profile.minimumSpacingBlocks() > 0);
            assertTrue(profile.radiusBlocks() > 0);
            assertTrue(profile.heatSeverity() >= 0.0 && profile.heatSeverity() <= 1.0);
            assertTrue(profile.gasSeverity() >= 0.0 && profile.gasSeverity() <= 1.0);
            assertTrue(profile.hydrothermalDepositChance() >= 0.0 && profile.hydrothermalDepositChance() <= 1.0);
        }
    }

    @Test
    void waterAndGasRequirementsMatchFeaturePhysics() {
        assertTrue(GeothermalFeatureProfile.defaults(GeothermalFeatureType.HOT_SPRING).requiresWater());
        assertTrue(GeothermalFeatureProfile.defaults(GeothermalFeatureType.GEYSER).requiresWater());
        assertTrue(GeothermalFeatureProfile.defaults(GeothermalFeatureType.MUD_POT).requiresWater());

        assertFalse(GeothermalFeatureProfile.defaults(GeothermalFeatureType.FUMAROLE).requiresWater());
        assertFalse(GeothermalFeatureProfile.defaults(GeothermalFeatureType.SULFUROUS_VENT).requiresWater());

        assertTrue(GeothermalFeatureProfile.defaults(GeothermalFeatureType.FUMAROLE).gasSeverity() > 0.0);
        assertTrue(GeothermalFeatureProfile.defaults(GeothermalFeatureType.SULFUROUS_VENT).gasSeverity() >
                GeothermalFeatureProfile.defaults(GeothermalFeatureType.FUMAROLE).gasSeverity());
        assertTrue(GeothermalFeatureProfile.defaults(GeothermalFeatureType.GEYSER).heatSeverity() >
                GeothermalFeatureProfile.defaults(GeothermalFeatureType.MUD_POT).heatSeverity());
    }

    @Test
    void malformedProfilesFailClosed() {
        assertThrows(IllegalArgumentException.class, () -> new GeothermalFeatureProfile(
                GeothermalFeatureType.HOT_SPRING, 1.1, 128, 3, 0.5, 0.2, true, 0.1));
        assertThrows(IllegalArgumentException.class, () -> new GeothermalFeatureProfile(
                GeothermalFeatureType.HOT_SPRING, 0.5, 0, 3, 0.5, 0.2, true, 0.1));
        assertThrows(IllegalArgumentException.class, () -> new GeothermalFeatureProfile(
                GeothermalFeatureType.HOT_SPRING, 0.5, 128, 0, 0.5, 0.2, true, 0.1));
        assertThrows(IllegalArgumentException.class, () -> new GeothermalFeatureProfile(
                GeothermalFeatureType.HOT_SPRING, 0.5, 128, 3, -0.1, 0.2, true, 0.1));
    }
}
