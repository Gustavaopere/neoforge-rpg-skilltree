package dev.gustavopere.volcanoes.volcano;

import com.mojang.serialization.DataResult;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalChunkHandoffContractTest {
    @Test
    void durableHandoffRoundTripsExactWorldgenMetadata() {
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                new BlockPos(117, 83, -41),
                4,
                0.81,
                0.37,
                1.0);
        GeothermalChunkHandoff expected = new GeothermalChunkHandoff(928_441L, placement);

        Tag encoded = GeothermalChunkHandoff.CODEC
                .encodeStart(NbtOps.INSTANCE, expected)
                .getOrThrow();
        GeothermalChunkHandoff restored = GeothermalChunkHandoff.CODEC
                .parse(NbtOps.INSTANCE, encoded)
                .getOrThrow();

        assertEquals(expected, restored,
                "chunk-local crash recovery must preserve the exact generated geothermal metadata");
    }

    @Test
    void replayAfterRestartIsIdempotentAcrossSourceDepositAndHeatAuthority() {
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                new BlockPos(32, 79, 48),
                4,
                0.9,
                0.25,
                1.0);
        GeothermalChunkHandoff original = GeothermalChunkHandoff.generated(71_331L, placement, true);
        DataResult<Tag> encoded = GeothermalChunkHandoff.CODEC.encodeStart(NbtOps.INSTANCE, original);
        GeothermalChunkHandoff restored = GeothermalChunkHandoff.CODEC
                .parse(NbtOps.INSTANCE, encoded.getOrThrow())
                .getOrThrow();

        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(8);
        DepositRegistry deposits = new DepositRegistry();
        VolcanicHeatSourceIndex heat = new VolcanicHeatSourceIndex(32, 64.0, 256.0, 16);
        sources.registerLifecycleSink(new GeothermalHeatIndexSink(heat));
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();

        GeothermalWorldgenRuntime.PersistResult first = GeothermalWorldgenRuntime.persistHandoff(
                sources,
                deposits,
                projector,
                restored);
        GeothermalWorldgenRuntime.PersistResult replay = GeothermalWorldgenRuntime.persistHandoff(
                sources,
                deposits,
                projector,
                restored);

        assertTrue(first.metadataAuthoritative());
        assertTrue(replay.metadataAuthoritative(),
                "replaying a durable chunk handoff after restart must recognize existing exact authority");
        assertEquals(1, sources.size(), "restart replay must not duplicate geothermal source authority");
        assertEquals(1, deposits.size(), "restart replay must not duplicate the deterministic hydrothermal deposit");
        assertEquals(1, heat.size(), "source replay must project exactly one effective geothermal heat source");
    }
}
