package dev.gustavopere.volcanoes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ProjectContractTest {
    @Test
    void exposesStableModAndMinecraftIdentifiers() {
        assertEquals("volcanoes", VolcanoesMod.MOD_ID);
        assertEquals("1.21.1", VolcanoesMod.MINECRAFT_LINE);
    }
}
