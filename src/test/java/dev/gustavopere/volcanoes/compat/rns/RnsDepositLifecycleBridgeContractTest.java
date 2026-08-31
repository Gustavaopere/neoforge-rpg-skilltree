package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RnsDepositLifecycleBridgeContractTest {
    private static final ResourceLocation COPPER = id("c", "ores/copper");
    private static final ResourceLocation IRON = id("c", "ores/iron");

    @Test
    void lifecycleRecomputesWholePlanSoCollisionsNeverDependOnArrivalOrder() {
        GeologicalDeposit first = deposit(1, COPPER, new BlockPos(17, 30, 17));
        GeologicalDeposit second = deposit(2, COPPER, new BlockPos(30, 40, 30));
        RecordingWriter writer = new RecordingWriter();
        RnsDepositLifecycleBridge bridge = new RnsDepositLifecycleBridge(writer);

        bridge.upsert(first);
        assertEquals(1, writer.present.size());

        bridge.upsert(second);
        assertTrue(writer.present.isEmpty(),
                "two authoritative sources colliding on the same RNS (structure, chunk) must project neither");

        bridge.remove(second.persistenceId());
        assertEquals(Map.of(key(first, "deposit_copper"), first.persistenceId()), writer.present,
                "removing one side of a collision must make the sole remaining source projectable again");

        bridge.remove(first.persistenceId());
        assertTrue(writer.present.isEmpty());
    }

    @Test
    void deterministicReplayProducesSameProjectionStateRegardlessOfRegistrationOrder() {
        GeologicalDeposit copper = deposit(2, COPPER, new BlockPos(16, 30, 16));
        GeologicalDeposit iron = deposit(1, IRON, new BlockPos(48, 30, 16));

        RecordingWriter forwardWriter = new RecordingWriter();
        RnsDepositLifecycleBridge forward = new RnsDepositLifecycleBridge(forwardWriter);
        forward.upsert(copper);
        forward.upsert(iron);

        RecordingWriter reverseWriter = new RecordingWriter();
        RnsDepositLifecycleBridge reverse = new RnsDepositLifecycleBridge(reverseWriter);
        reverse.upsert(iron);
        reverse.upsert(copper);

        assertEquals(forwardWriter.present, reverseWriter.present);
    }

    @Test
    void restoredDepositRegistryReplaysAuthoritativeProjectionAfterRestart() {
        GeologicalDeposit deposit = deposit(7, COPPER, new BlockPos(64, 25, 64));
        DepositRegistry beforeRestart = new DepositRegistry();
        beforeRestart.register(deposit);

        DepositRegistry restored = DepositRegistry.fromTag(beforeRestart.toTag());
        RecordingWriter writer = new RecordingWriter();
        RnsDepositLifecycleBridge bridge = new RnsDepositLifecycleBridge(writer);

        assertTrue(restored.registerLifecycleSink(bridge));
        assertEquals(Map.of(key(deposit, "deposit_copper"), deposit.persistenceId()), writer.present,
                "SavedData replay must restore the RNS projection without a second geological source");
    }

    @Test
    void failedHostRemovalIsNotTreatedAsSuccessfulReconciliation() {
        GeologicalDeposit first = deposit(1, COPPER, new BlockPos(17, 30, 17));
        GeologicalDeposit colliding = deposit(2, COPPER, new BlockPos(30, 30, 30));
        RecordingWriter writer = new RecordingWriter();
        RnsDepositLifecycleBridge bridge = new RnsDepositLifecycleBridge(writer);

        bridge.upsert(first);
        writer.failRemovals = true;
        bridge.upsert(colliding);

        assertEquals(Map.of(key(first, "deposit_copper"), first.persistenceId()), writer.present,
                "bridge must not lie about host state when removeCustomDeposit fails");
        assertEquals(1, bridge.appliedProjectionCount(),
                "failed removal must remain tracked for a later reconciliation attempt");
    }

    private static GeologicalDeposit deposit(int id, ResourceLocation tag, BlockPos center) {
        return new GeologicalDeposit(new UUID(0L, id), tag, center, 24.0, 0.7, DepositOrigin.HYDROTHERMAL);
    }

    private static String key(GeologicalDeposit deposit, String name) {
        return "create_rns:" + name + "@" + new ChunkPos(deposit.center());
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    private static final class RecordingWriter implements RnsDepositProjectionWriter {
        private final Map<String, UUID> present = new LinkedHashMap<>();
        private boolean failRemovals;

        @Override
        public boolean ensurePresent(RnsDepositProjectionPlanner.Projection projection) {
            present.put(key(projection), projection.sourceId());
            return true;
        }

        @Override
        public boolean ensureAbsent(RnsDepositProjectionPlanner.Projection projection) {
            if (failRemovals) {
                return false;
            }
            present.remove(key(projection));
            return true;
        }

        private static String key(RnsDepositProjectionPlanner.Projection projection) {
            return projection.rnsDepositId() + "@" + new ChunkPos(projection.center());
        }
    }
}
