package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LavaHeatSourceProducerContractTest {
    @Test
    void lavaSourceIdentityIsStablePerVolcano() {
        UUID volcano = UUID.fromString("0d6f406d-6c07-4fa2-a585-86fd06ad1ea9");

        assertEquals(
                LavaHeatSourceProjector.sourceId(volcano),
                LavaHeatSourceProjector.sourceId(volcano));
        assertNotEquals(
                LavaHeatSourceProjector.sourceId(volcano),
                LavaHeatSourceProjector.sourceId(UUID.fromString("433d40ff-9ed7-4575-89c9-59c0f75b3601")));
    }

    @Test
    void activeEruptionProjectsOneBoundedTtlLavaHeatSource() {
        UUID volcano = UUID.fromString("0d6f406d-6c07-4fa2-a585-86fd06ad1ea9");
        BlockPos source = new BlockPos(32, 90, -48);
        EruptionSignal signal = signal(volcano, source, EruptionPhase.SUSTAINED, 0.8);

        VolcanicHeatSource projected = LavaHeatSourceProjector.fromSignal(signal, 1_000L, 400L);

        assertEquals(LavaHeatSourceProjector.sourceId(volcano), projected.sourceId());
        assertEquals(VolcanicHeatSource.Kind.LAVA, projected.kind());
        assertEquals(source, projected.center());
        assertTrue(projected.radiusBlocks() > 0.0);
        assertTrue(projected.radiusBlocks() <= 64.0);
        assertEquals(0.8, projected.severity(), 1.0e-12);
        assertEquals(1_400L, projected.expiresAtTick());
    }

    @Test
    void existingHazardConsumerPublishesAndRetiresLavaWithoutASecondEruptionSink() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanicHazardWorldRuntime.java"));

        assertTrue(source.contains("publishLavaHeat(level, signal, gameTick)"));
        assertTrue(source.contains("LavaHeatSourceProjector.fromSignal("));
        assertTrue(source.contains("LavaHeatSourceProjector.sourceId(volcanoId)"));
        assertEquals(1, occurrences(source, "VolcanoLifecycleRuntime.registerEruptionSink("));
    }

    private static EruptionSignal signal(
            UUID volcano,
            BlockPos source,
            EruptionPhase phase,
            double intensity
    ) {
        MagmaChamber chamber = new MagmaChamber(
                MagmaComposition.forType(VolcanoType.SHIELD),
                10.0,
                200.0,
                0.20,
                1_300.0,
                0.0);
        EruptionProfile profile = new EruptionProfile(
                1.0,
                96,
                320,
                600L,
                200L,
                2_400L,
                800L);
        return new EruptionSignal(
                volcano,
                source,
                phase,
                profile,
                chamber,
                0.5,
                intensity);
    }

    private static int occurrences(String value, String needle) {
        int count = 0;
        int offset = 0;
        while ((offset = value.indexOf(needle, offset)) >= 0) {
            count++;
            offset += needle.length();
        }
        return count;
    }
}
