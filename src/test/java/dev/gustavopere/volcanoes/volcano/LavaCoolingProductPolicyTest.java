package dev.gustavopere.volcanoes.volcano;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gustavopere.volcanoes.geology.RockCategory;
import dev.gustavopere.volcanoes.geology.RockProfile;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class LavaCoolingProductPolicyTest {
    private static final RockProfile BASALT = new RockProfile(
            "basalt", RockCategory.IGNEOUS_EXTRUSIVE,
            0.82, 0.18, 1.9, 1.25, 0.85, 0.30);
    private static final RockProfile GRANITE = new RockProfile(
            "granite", RockCategory.IGNEOUS_INTRUSIVE,
            0.90, 0.08, 2.8, 0.65, 0.90, 0.35);

    @Test
    void dryCoolingUsesConfiguredRockOrGlassyProductFamilies() {
        LavaCoolingProductPolicy policy = new LavaCoolingProductPolicy();

        LavaCoolingProductPolicy.Product basaltic = policy.select(sample(BASALT), false, false);
        LavaCoolingProductPolicy.Product glassy = policy.select(sample(GRANITE), false, false);

        assertEquals(ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/basaltic"), basaltic.tagId());
        assertEquals(ResourceLocation.fromNamespaceAndPath("minecraft", "basalt"), basaltic.vanillaFallback());
        assertEquals(ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/glassy"), glassy.tagId());
        assertEquals(ResourceLocation.fromNamespaceAndPath("minecraft", "obsidian"), glassy.vanillaFallback());
    }

    @Test
    void waterQuenchingPreservesVanillaSourceVersusFlowingFallbacks() {
        LavaCoolingProductPolicy policy = new LavaCoolingProductPolicy();
        LavaEnvironmentSample environment = sample(BASALT);

        LavaCoolingProductPolicy.Product source = policy.select(environment, true, true);
        LavaCoolingProductPolicy.Product flowing = policy.select(environment, true, false);

        assertEquals(ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/glassy"), source.tagId());
        assertEquals(ResourceLocation.fromNamespaceAndPath("minecraft", "obsidian"), source.vanillaFallback());
        assertEquals(ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/rubble"), flowing.tagId());
        assertEquals(ResourceLocation.fromNamespaceAndPath("minecraft", "cobblestone"), flowing.vanillaFallback());
    }

    @Test
    void bundledCoolingTagsAreExtensibleAndHaveSafeVanillaBaselines() {
        assertTag("basaltic", Set.of("minecraft:basalt"));
        assertTag("glassy", Set.of("minecraft:obsidian"));
        assertTag("rubble", Set.of("minecraft:cobblestone"));
    }

    private static LavaEnvironmentSample sample(RockProfile profile) {
        return new LavaFlowResolver((seed, x, y, z) -> profile).sample(1L, 0, 64, 0);
    }

    private static void assertTag(String name, Set<String> expectedValues) {
        JsonObject json = resource("data/volcanoes/tags/block/lava_cooling/" + name + ".json");
        assertFalse(json.get("replace").getAsBoolean());
        assertEquals(expectedValues, values(json));
    }

    private static JsonObject resource(String path) {
        var stream = LavaCoolingProductPolicyTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "missing bundled lava cooling tag: " + path);
        try (stream; var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("failed to read bundled lava cooling tag: " + path, exception);
        }
    }

    private static Set<String> values(JsonObject json) {
        JsonArray values = json.getAsJsonArray("values");
        return StreamSupport.stream(values.spliterator(), false)
                .map(element -> element.getAsString())
                .collect(Collectors.toUnmodifiableSet());
    }
}
