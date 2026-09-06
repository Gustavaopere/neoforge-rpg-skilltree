package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalHydrothermalOreWorldgenWiringTest {
    @Test
    void productionFeatureRealizesHydrothermalOreBeforeRecordingAuthority() throws IOException {
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenFeature.java"));

        assertTrue(feature.contains("HydrothermalDepositProjector"),
                "production worldgen must use the canonical deterministic hydrothermal projector");
        assertTrue(feature.contains("HydrothermalOreWorldgenProducer"),
                "production worldgen must use the physically verified ore producer");
        assertTrue(feature.contains("hydrothermalDepositPhysicallyRealized"),
                "production worldgen must carry one explicit physical-authority result");
        assertTrue(feature.contains("ORE_PRODUCER.prepare(level, chunk, projectedDeposit)"),
                "the exact projected deposit must be prepared against the real worldgen level");
        assertTrue(feature.contains("ORE_PRODUCER.apply(level, prepared)"),
                "prepared ore mutation must be applied before metadata can claim exact authority");
        assertTrue(feature.contains("GeothermalChunkHandoff.generated(\n                    worldSeed, placement, hydrothermalDepositPhysicallyRealized)"),
                "the durable crash-recovery receipt must persist physical authority");
        assertTrue(feature.contains("GeothermalWorldgenRuntime.commitGenerated(\n                    level.getLevel(), reservation, hydrothermalDepositPhysicallyRealized)"),
                "the transient persistence queue must receive the same physical authority bit");

        int surfaceMutation = feature.indexOf("applyCurrentChunk(level, chunk, placement)");
        int physicalPreparation = feature.indexOf("ORE_PRODUCER.prepare(level, chunk, projectedDeposit)");
        int physicalApplication = feature.indexOf("ORE_PRODUCER.apply(level, prepared)");
        int durableReceipt = feature.indexOf("ownerChunk.setData(VolcanoAttachments.GEOTHERMAL_HANDOFFS");
        int transientCommit = feature.indexOf("GeothermalWorldgenRuntime.commitGenerated(");

        assertTrue(surfaceMutation >= 0 && surfaceMutation < physicalPreparation,
                "surface expression must succeed before hydrothermal mineralization is attempted");
        assertTrue(physicalPreparation < physicalApplication,
                "physical ore placement must be prepared before it mutates the world");
        assertTrue(physicalApplication < durableReceipt,
                "physical mineralization proof must exist before the durable receipt is written");
        assertTrue(durableReceipt < transientCommit,
                "crash-recovery authority must be durable before transient metadata is committed");
    }
}
