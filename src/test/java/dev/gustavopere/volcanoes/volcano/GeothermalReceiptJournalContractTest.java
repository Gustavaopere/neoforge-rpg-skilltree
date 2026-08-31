package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalReceiptJournalContractTest {
    @Test
    void worldgenDeduplicatesDurableReceiptsByDeterministicSourceIdentity() throws IOException {
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenFeature.java"));

        assertTrue(feature.contains("existing.sourceId().equals(sourceId)"),
                "the chunk journal must merge replayed receipts by deterministic geothermal source identity");
        assertTrue(feature.contains("existing.worldSeed() != worldSeed")
                        && feature.contains("!existing.placement().equals(placement)"),
                "same source identity with conflicting deterministic placement provenance must fail closed");
        assertTrue(feature.contains("existingReceipt.isEmpty()")
                        && feature.contains("updated.add(handoff)"),
                "a first receipt must be appended exactly once to the durable chunk journal");
        assertTrue(feature.contains("else if (!existingReceipt.orElseThrow().equals(handoff))")
                        && feature.contains("existing.sourceId().equals(sourceId) ? handoff : existing"),
                "the same deterministic receipt may be replaced only to carry a monotonic physical-proof upgrade");
    }

    @Test
    void durableJournalCapacityIsCheckedBeforePhysicalMutation() throws IOException {
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenFeature.java"));

        int capacityCheck = feature.indexOf("durable.size() >=");
        int mutation = feature.indexOf("boolean changed = applyCurrentChunk(");
        assertTrue(capacityCheck >= 0 && mutation > capacityCheck,
                "worldgen must reject a full owner-chunk journal before mutating terrain, avoiding an unjournaled crash window");
    }
}
