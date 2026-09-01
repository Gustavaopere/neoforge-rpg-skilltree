package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalCrashRecoveryWiringTest {
    @Test
    void modRegistersSerializableBoundedGeothermalChunkAttachment() throws IOException {
        String mod = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));
        String attachments = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanoAttachments.java"));

        assertTrue(mod.contains("VolcanoAttachments.register(modBus);"),
                "the durable geothermal chunk handoff attachment must be registered on the mod bus");
        assertTrue(attachments.contains("GEOTHERMAL_HANDOFFS"),
                "attachment registry must expose a dedicated geothermal handoff type");
        assertTrue(attachments.contains("MAX_DURABLE_HANDOFFS_PER_CHUNK = 8"),
                "owner chunks must have a hard bounded durable receipt count");
        assertTrue(attachments.contains("GeothermalChunkHandoff.CODEC.listOf(0, MAX_DURABLE_HANDOFFS_PER_CHUNK)"),
                "the persisted attachment codec itself must reject oversized receipt lists");
    }

    @Test
    void successfulWorldgenWritesDurableReceiptIntoOwningChunk() throws IOException {
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenFeature.java"));

        assertTrue(feature.contains("VolcanoAttachments.GEOTHERMAL_HANDOFFS"),
                "successful terrain mutation must record a durable chunk-local geothermal handoff");
        assertTrue(feature.contains("VolcanoAttachments.MAX_DURABLE_HANDOFFS_PER_CHUNK"),
                "worldgen must enforce the same receipt bound as the persisted attachment codec");
        assertTrue(feature.contains("setData("),
                "the handoff must be written through ChunkAccess attachment data so the chunk is marked unsaved");
        assertTrue(feature.contains("GeothermalChunkHandoff"),
                "the durable receipt must carry the exact generated placement rather than require retrogen inference");
    }

    @Test
    void chunkLoadTracksReceiptBearingChunkWithoutBecomingSavedDataAuthority() throws IOException {
        String mod = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));
        String runtime = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenRuntime.java"));

        assertTrue(mod.contains("GeothermalWorldgenRuntime::onChunkLoad"),
                "restart recovery must observe chunks carrying durable handoffs");
        assertTrue(runtime.contains("onChunkLoad(ChunkEvent.Load event)"),
                "chunk-load recovery hook must exist on the geothermal runtime");
        assertTrue(runtime.contains("VolcanoAttachments.GEOTHERMAL_HANDOFFS"),
                "chunk-load recovery must read the durable receipt attachment");
        assertTrue(runtime.contains("GeothermalLoadedChunkRecoveryTracker"),
                "durable recovery must track loaded receipt-bearing chunks instead of admitting receipts into a fixed queue");
        assertFalse(runtime.contains("RECOVERY_CAPACITY"),
                "durable recovery must not reintroduce a fixed-capacity receipt queue that can drop overflow");

        int hookStart = runtime.indexOf("onChunkLoad(ChunkEvent.Load event)");
        assertTrue(hookStart >= 0, "chunk-load hook source must be locatable for authority audit");
        int nextMethod = runtime.indexOf("\n    public static", hookStart + "onChunkLoad(ChunkEvent.Load event)".length());
        String hookBody = nextMethod > hookStart
                ? runtime.substring(hookStart, nextMethod)
                : runtime.substring(hookStart);
        assertTrue(hookBody.contains(".track("),
                "ChunkEvent.Load may only stage the owning loaded chunk for bounded later recovery");
        assertFalse(hookBody.contains("GeothermalSourceRegistry.get("),
                "ChunkEvent.Load must not become SavedData authority; reconciliation stays on the later server tick");
        assertFalse(hookBody.contains("DepositRegistry.get("),
                "ChunkEvent.Load must not touch deposit SavedData directly");
        assertFalse(hookBody.contains("enqueue("),
                "ChunkEvent.Load must not bulk-admit durable receipts into the transient metadata queue");
    }
}
