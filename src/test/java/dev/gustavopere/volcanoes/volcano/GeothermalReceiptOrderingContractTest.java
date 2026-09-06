package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalReceiptOrderingContractTest {
    @Test
    void durableReceiptBridgesPhysicalMutationBeforeTransientMetadataCommit() throws IOException {
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenFeature.java"));

        int mutation = feature.indexOf("boolean changed = applyCurrentChunk(");
        int failedMutationGuard = mutation < 0 ? -1 : feature.indexOf("if (!changed)", mutation);
        int durableReceiptWrite = failedMutationGuard < 0 ? -1 : feature.indexOf("setData(", failedMutationGuard);
        int transientCommit = durableReceiptWrite < 0
                ? -1
                : feature.indexOf("commitGenerated(", durableReceiptWrite);

        assertTrue(mutation >= 0 && failedMutationGuard > mutation,
                "durable handoff ordering must be anchored after the physical mutation and its success guard");
        assertTrue(durableReceiptWrite > failedMutationGuard,
                "failed or no-op terrain placement must never manufacture a durable geothermal receipt");
        assertTrue(transientCommit > durableReceiptWrite,
                "the durable receipt must be persisted before transient metadata commit so a crash in that window remains recoverable");
    }

    @Test
    void durableReceiptIsRemovedOnlyAfterExactMetadataAuthority() throws IOException {
        String runtime = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenRuntime.java"));

        int persist = runtime.indexOf("persistHandoff(");
        int authority = persist < 0 ? -1 : runtime.indexOf("metadataAuthoritative()", persist);
        int removal = authority < 0 ? -1 : runtime.indexOf("removeHandoff", authority);

        assertTrue(persist >= 0,
                "durable recovery must route receipts through the exact metadata persistence contract");
        assertTrue(authority > persist,
                "recovery must prove exact source/deposit authority before acknowledging the receipt");
        assertTrue(removal > authority,
                "a durable receipt may only be removed after exact metadata authority has been proven");
        assertFalse(runtime.substring(persist, authority).contains("removeHandoff"),
                "the receipt must remain durable while metadata authority is incomplete or conflicting");
    }
}
