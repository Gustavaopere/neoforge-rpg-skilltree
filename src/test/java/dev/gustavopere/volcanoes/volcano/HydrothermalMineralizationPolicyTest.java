package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class HydrothermalMineralizationPolicyTest {
    @Test
    void mapsCanonicalVolcanicChemistryToStage01MetalFamilies() {
        assertEquals(
                GeologyResourceTags.IRON_ORES.location(),
                HydrothermalMineralizationPolicy.resourceFor(Optional.of(VolcanoType.SHIELD)));
        assertEquals(
                GeologyResourceTags.IRON_ORES.location(),
                HydrothermalMineralizationPolicy.resourceFor(Optional.of(VolcanoType.FISSURE)));
        assertEquals(
                GeologyResourceTags.COPPER_ORES.location(),
                HydrothermalMineralizationPolicy.resourceFor(Optional.of(VolcanoType.STRATOVOLCANO)));
        assertEquals(
                GeologyResourceTags.GOLD_ORES.location(),
                HydrothermalMineralizationPolicy.resourceFor(Optional.of(VolcanoType.CALDERA)));
    }

    @Test
    void leavesPurelyTectonicHydrothermalSystemsGeneric() {
        assertEquals(
                GeologyResourceTags.MINERAL_RESOURCES.location(),
                HydrothermalMineralizationPolicy.resourceFor(Optional.empty()));
    }
}
