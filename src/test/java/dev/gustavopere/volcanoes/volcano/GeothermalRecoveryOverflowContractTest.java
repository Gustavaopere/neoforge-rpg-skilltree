package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalRecoveryOverflowContractTest {
    @Test
    void loadedReceiptChunksAreNeverDroppedWhenMoreThanOldRecoveryQueueCapacityAreTracked() {
        GeothermalLoadedChunkRecoveryTracker tracker = new GeothermalLoadedChunkRecoveryTracker();
        int receiptChunks = 2_049;

        for (long chunk = 0; chunk < receiptChunks; chunk++) {
            tracker.track(chunk);
        }

        assertEquals(receiptChunks, tracker.size(),
                "tracking must be bounded by currently loaded receipt-bearing chunks, not by the old 2048 recovery queue capacity");

        Set<Long> observed = new HashSet<>();
        int hardTickBudget = 2;
        for (int tick = 0; tick < Math.ceilDiv(receiptChunks, hardTickBudget); tick++) {
            List<Long> batch = tracker.nextBatch(hardTickBudget);
            assertTrue(batch.size() <= hardTickBudget,
                    "each tick must respect the hard chunk-recovery budget");
            observed.addAll(batch);
        }

        assertEquals(receiptChunks, observed.size(),
                "rotating bounded batches must eventually visit every still-loaded receipt chunk without dropping overflow");
        assertEquals(receiptChunks, tracker.size(),
                "visiting unresolved chunks must rotate them rather than removing durable recovery work");
    }

    @Test
    void resolveUnloadAndDuplicateLoadKeepTrackerCardinalityExact() {
        GeothermalLoadedChunkRecoveryTracker tracker = new GeothermalLoadedChunkRecoveryTracker();
        tracker.track(10L);
        tracker.track(10L);
        tracker.track(11L);

        assertEquals(2, tracker.size(), "duplicate load events must not duplicate recovery work");
        assertTrue(tracker.untrack(10L));
        assertFalse(tracker.untrack(10L));
        assertEquals(List.of(11L), tracker.nextBatch(2));
        assertEquals(1, tracker.size());
    }

    @Test
    void runtimeUsesLoadedChunksAndOneSharedPersistenceBudget() throws IOException {
        String runtime = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenRuntime.java"));
        String mod = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenFeature.java"));
        String attachments = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanoAttachments.java"));

        assertTrue(attachments.contains("MAX_DURABLE_HANDOFFS_PER_CHUNK = 8"),
                "the two-chunk recovery budget assumes a hard maximum of eight durable receipts per owner chunk");
        assertTrue(feature.contains("VolcanoAttachments.MAX_DURABLE_HANDOFFS_PER_CHUNK"),
                "worldgen and durable codec must share one receipt-count authority");
        assertTrue(runtime.contains("GeothermalLoadedChunkRecoveryTracker"),
                "runtime must retain loaded receipt-chunk identities for retry instead of relying on a fixed admission queue");
        assertTrue(runtime.contains("MAX_RECOVERY_CHUNKS_PER_TICK = 2"),
                "two chunks times eight durable receipts keeps recovery admission within the existing 16-item persistence budget");
        assertTrue(runtime.contains("GeothermalPersistenceTurnBudget.allocate("),
                "transient retries and durable recovery must be allocated from one explicit shared turn budget");
        assertFalse(runtime.contains("RECOVERY_CAPACITY"),
                "recovery must not own a second fixed-capacity metadata queue that can silently reject durable receipts");
        assertFalse(runtime.contains("persistRecovered("),
                "recovery must stage through the shared persistence turn rather than creating a second 16-item drain");
        assertTrue(runtime.contains("getChunkNow("),
                "server-tick recovery must consult only already-loaded chunks and never force-load world data");
        assertTrue(runtime.contains("onChunkUnload(ChunkEvent.Unload event)"),
                "unloaded chunks must be removed from the in-memory tracker so tracker memory follows engine-loaded state");
        assertTrue(mod.contains("GeothermalWorldgenRuntime::onChunkUnload"),
                "the unload cleanup hook must be registered on the game event bus");

        int firstUntrack = runtime.indexOf(".untrack(");
        int secondUntrack = firstUntrack < 0 ? -1 : runtime.indexOf(".untrack(", firstUntrack + 1);
        assertTrue(firstUntrack >= 0 && secondUntrack > firstUntrack,
                "runtime must untrack both unloaded chunks and successfully reconciled chunks so resolved receipts stop consuming sweep work");

        int loadStart = runtime.indexOf("onChunkLoad(ChunkEvent.Load event)");
        assertTrue(loadStart >= 0, "chunk-load hook source must be locatable for overflow audit");
        int nextMethod = runtime.indexOf("\n    public static", loadStart + "onChunkLoad(ChunkEvent.Load event)".length());
        String loadHook = nextMethod > loadStart
                ? runtime.substring(loadStart, nextMethod)
                : runtime.substring(loadStart);
        assertFalse(loadHook.contains("recovery.enqueue("),
                "ChunkEvent.Load must never drop durable receipts by bulk-offering them into a fixed-capacity queue");
    }
}
