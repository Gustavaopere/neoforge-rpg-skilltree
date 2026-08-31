package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalPhysicalDepositAuthorityTest {
    private static GeothermalFeaturePlacement placement() {
        return new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                new BlockPos(8, 72, 8),
                4,
                0.85,
                0.35,
                1.0);
    }

    @Test
    void transientQueueCarriesPhysicalDepositAuthorityOnlyWhenExplicitlyCommitted() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(4, 4);
        var reservation = queue.reserve(91L, placement()).orElseThrow();

        assertTrue(queue.commit(reservation, true));
        AtomicReference<GeothermalPendingQueue.Pending> seen = new AtomicReference<>();
        assertTrue(queue.processCommitted(pending -> {
            seen.set(pending);
            return true;
        }) == 1);
        assertTrue(seen.get().hydrothermalDepositPhysicallyRealized());
    }

    @Test
    void legacyQueueCommitDefaultsToNoPhysicalDepositAuthority() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(4, 4);
        var reservation = queue.reserve(91L, placement()).orElseThrow();

        assertTrue(queue.commit(reservation));
        AtomicReference<GeothermalPendingQueue.Pending> seen = new AtomicReference<>();
        queue.processCommitted(pending -> {
            seen.set(pending);
            return true;
        });
        assertFalse(seen.get().hydrothermalDepositPhysicallyRealized());
    }

    @Test
    void durableReceiptRoundTripsPhysicalAuthorityAndLegacyConstructorFailsClosed() {
        GeothermalChunkHandoff proven = GeothermalChunkHandoff.generated(91L, placement(), true);
        var encoded = GeothermalChunkHandoff.CODEC.encodeStart(NbtOps.INSTANCE, proven).getOrThrow();
        var restored = GeothermalChunkHandoff.CODEC.parse(NbtOps.INSTANCE, encoded).getOrThrow();

        assertTrue(restored.hydrothermalDepositPhysicallyRealized());
        assertFalse(new GeothermalChunkHandoff(91L, placement()).hydrothermalDepositPhysicallyRealized());
    }

    @Test
    void exactMetalsRequirePhysicalProofWhileGenericMineralMetadataDoesNotClaimWorldgen() {
        assertFalse(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.COPPER_ORES.location(), false));
        assertFalse(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.IRON_ORES.location(), false));
        assertFalse(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.GOLD_ORES.location(), false));

        assertTrue(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.COPPER_ORES.location(), true));
        assertTrue(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.IRON_ORES.location(), true));
        assertTrue(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.GOLD_ORES.location(), true));

        assertTrue(GeothermalWorldgenRuntime.depositMetadataAdmissible(
                GeologyResourceTags.MINERAL_RESOURCES.location(), false));
    }
}
