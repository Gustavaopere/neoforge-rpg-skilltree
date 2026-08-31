package dev.gustavopere.volcanoes.geology;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

final class RockProfileDataLoaderTest {
    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    @Test
    void jsonDefinitionsBuildAnImmutableRegistrySnapshot() {
        Map<ResourceLocation, JsonElement> definitions = new LinkedHashMap<>();
        definitions.put(id("example", "fast_basalt"), json("""
                {
                  "category": "IGNEOUS_EXTRUSIVE",
                  "hardness": 0.82,
                  "permeability": 0.18,
                  "thermal_conductivity": 1.9,
                  "lava_flow_multiplier": 1.40,
                  "erosion_resistance": 0.85,
                  "hydrothermal_reactivity": 0.30,
                  "blocks": ["minecraft:basalt"],
                  "tags": ["example:volcanic_rocks"]
                }
                """));

        RockProfileRegistry registry = RockProfileDataLoader.load(definitions);
        RockProfile basalt = registry.resolve(id("minecraft", "basalt"), List.of());

        assertEquals("example:fast_basalt", basalt.id());
        assertEquals(1.40, basalt.lavaFlowMultiplier(), 1.0e-9);
        assertSame(basalt, registry.resolve(id("example", "tagged_rock"), List.of(id("example", "volcanic_rocks"))));
        assertEquals(RockProfile.GENERIC_STONE,
                registry.resolve(id("unknown", "mystery"), List.of()));
    }

    @Test
    void successfulReloadAtomicallyChangesBehavior() {
        RockProfileReloadState state = new RockProfileReloadState(RockProfileRegistry.vanillaDefaults());
        double original = state.current().resolve(id("minecraft", "basalt"), List.of()).lavaFlowMultiplier();

        state.reload(Map.of(id("example", "slow_basalt"), json("""
                {
                  "category": "IGNEOUS_EXTRUSIVE",
                  "hardness": 0.82,
                  "permeability": 0.18,
                  "thermal_conductivity": 1.9,
                  "lava_flow_multiplier": 0.55,
                  "erosion_resistance": 0.85,
                  "hydrothermal_reactivity": 0.30,
                  "blocks": ["minecraft:basalt"]
                }
                """)));

        double reloaded = state.current().resolve(id("minecraft", "basalt"), List.of()).lavaFlowMultiplier();
        assertNotEquals(original, reloaded);
        assertEquals(0.55, reloaded, 1.0e-9);
    }

    @Test
    void failedReloadKeepsPreviousSnapshotAndConflictingBindingsAreRejected() {
        RockProfileReloadState state = new RockProfileReloadState(RockProfileRegistry.vanillaDefaults());
        RockProfileRegistry before = state.current();

        Map<ResourceLocation, JsonElement> conflicting = new LinkedHashMap<>();
        conflicting.put(id("example", "one"), json(profileJson("minecraft:basalt", 0.70)));
        conflicting.put(id("example", "two"), json(profileJson("minecraft:basalt", 1.30)));

        assertThrows(IllegalArgumentException.class, () -> state.reload(conflicting));
        assertSame(before, state.current());
    }

    @Test
    void malformedOrOutOfRangeDefinitionsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> RockProfileDataLoader.load(Map.of(
                id("example", "bad"),
                json("""
                        {
                          "category": "IGNEOUS_EXTRUSIVE",
                          "hardness": 2.0,
                          "permeability": 0.2,
                          "thermal_conductivity": 1.0,
                          "lava_flow_multiplier": 1.0,
                          "erosion_resistance": 0.5,
                          "hydrothermal_reactivity": 0.3
                        }
                        """))));
    }

    private static JsonElement json(String value) {
        return JsonParser.parseString(value);
    }

    private static String profileJson(String block, double lavaFlowMultiplier) {
        return """
                {
                  "category": "GENERIC",
                  "hardness": 0.5,
                  "permeability": 0.3,
                  "thermal_conductivity": 2.0,
                  "lava_flow_multiplier": %s,
                  "erosion_resistance": 0.5,
                  "hydrothermal_reactivity": 0.4,
                  "blocks": ["%s"]
                }
                """.formatted(lavaFlowMultiplier, block);
    }
}
