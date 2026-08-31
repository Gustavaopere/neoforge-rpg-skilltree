package dev.gustavopere.volcanoes.compat.minecolonies;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MineColoniesProtectedAreaProviderArchitectureTest {
    @Test
    void providerUsesClaimDataInsteadOfWorldChunkLookup() throws Exception {
        Path source = Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/minecolonies/",
                "MineColoniesProtectedAreaProvider.java");
        String text = Files.readString(source);

        assertTrue(text.contains("getClaimData(dimension, new ChunkPos(pos))"));
        assertFalse(text.contains("getColonyByPosFromDim"));
        assertFalse(text.contains("getChunkAt"));
    }
}
