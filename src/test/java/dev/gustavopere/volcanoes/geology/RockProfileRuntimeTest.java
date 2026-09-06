package dev.gustavopere.volcanoes.geology;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class RockProfileRuntimeTest {
    @Test
    void runtimeResolvesCanonicalBlockIdsThroughPureBridge() {
        assertEquals(RockCategory.IGNEOUS_EXTRUSIVE,
                RockProfileRuntime.resolve(ResourceLocation.parse("minecraft:basalt"), List.of()).category());
        assertEquals(RockCategory.VOLCANIC_FRAGMENTAL,
                RockProfileRuntime.resolve(ResourceLocation.parse("minecraft:tuff"), List.of()).category());
        assertEquals(RockCategory.IGNEOUS_INTRUSIVE,
                RockProfileRuntime.resolve(ResourceLocation.parse("minecraft:granite"), List.of()).category());
    }

    @Test
    void runtimeExposesActualBlockStateBridge() throws NoSuchMethodException {
        assertNotNull(RockProfileRuntime.class.getMethod("resolve", BlockState.class));
    }

    @Test
    void reloadListenerUsesTheCanonicalDatapackDirectory() {
        assertEquals("rock_profiles", RockProfileReloadListener.DIRECTORY);
        assertNotNull(new RockProfileReloadListener());
    }

    @Test
    void vanillaProfileJsonShipsInsideTheModDataPack() {
        assertNotNull(getClass().getResource("/data/volcanoes/rock_profiles/basalt.json"));
        assertNotNull(getClass().getResource("/data/volcanoes/rock_profiles/tuff.json"));
        assertNotNull(getClass().getResource("/data/volcanoes/rock_profiles/granite.json"));
        assertNotNull(getClass().getResource("/data/volcanoes/rock_profiles/stone.json"));
    }
}
