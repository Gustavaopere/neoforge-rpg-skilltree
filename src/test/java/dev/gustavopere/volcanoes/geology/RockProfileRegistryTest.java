package dev.gustavopere.volcanoes.geology;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class RockProfileRegistryTest {
    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    @Test
    void completeRockProfileValidatesAndExposesPhysicalProperties() {
        RockProfile profile = new RockProfile(
                "volcanoes:test",
                RockCategory.IGNEOUS_EXTRUSIVE,
                0.82,
                0.18,
                1.9,
                1.25,
                0.85,
                0.30);

        assertEquals(0.82, profile.hardness(), 1.0e-9);
        assertEquals(0.30, profile.hydrothermalReactivity(), 1.0e-9);
        assertThrows(IllegalArgumentException.class, () -> new RockProfile(
                "volcanoes:bad", RockCategory.GENERIC, 1.01, 0.2, 1.0, 1.0, 0.5, 0.5));
        assertThrows(IllegalArgumentException.class, () -> new RockProfile(
                "volcanoes:bad", RockCategory.GENERIC, 0.5, 0.2, 1.0, 1.0, 0.5, -0.01));
    }

    @Test
    void vanillaDefaultsResolveBasaltTuffGraniteAndStoneDeterministically() {
        RockProfileRegistry registry = RockProfileRegistry.vanillaDefaults();

        RockProfile basalt = registry.resolve(id("minecraft", "basalt"), List.of());
        RockProfile tuff = registry.resolve(id("minecraft", "tuff"), List.of());
        RockProfile granite = registry.resolve(id("minecraft", "granite"), List.of());
        RockProfile stone = registry.resolve(id("minecraft", "stone"), List.of());

        assertEquals(RockCategory.IGNEOUS_EXTRUSIVE, basalt.category());
        assertEquals(RockCategory.VOLCANIC_FRAGMENTAL, tuff.category());
        assertEquals(RockCategory.IGNEOUS_INTRUSIVE, granite.category());
        assertEquals(RockCategory.GENERIC, stone.category());
        assertNotEquals(basalt.lavaFlowMultiplier(), granite.lavaFlowMultiplier());
        assertNotEquals(tuff.hydrothermalReactivity(), granite.hydrothermalReactivity());

        assertSame(basalt, registry.resolve(id("minecraft", "basalt"), List.of()));
    }

    @Test
    void directBlockBindingWinsOverTagAndUnknownBlocksUseGenericStone() {
        RockProfile direct = new RockProfile(
                "volcanoes:direct", RockCategory.METAMORPHIC,
                0.75, 0.10, 2.0, 0.8, 0.9, 0.4);
        RockProfile tagged = new RockProfile(
                "volcanoes:tagged", RockCategory.SEDIMENTARY,
                0.35, 0.60, 1.2, 1.1, 0.3, 0.8);

        ResourceLocation block = id("example", "rock");
        ResourceLocation tag = id("example", "sedimentary_rocks");
        RockProfileRegistry registry = RockProfileRegistry.builder()
                .profile(direct)
                .profile(tagged)
                .bindBlock(block, direct.id())
                .bindTag(tag, tagged.id())
                .build();

        assertEquals(direct, registry.resolve(block, List.of(tag)));
        assertEquals(tagged, registry.resolve(id("example", "other_rock"), List.of(tag)));
        assertEquals(RockProfile.GENERIC_STONE,
                registry.resolve(id("unknown", "mystery_rock"), List.of()));
    }
}
