package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class StrataServiceTest {
    private static final int MIN_Y = -64;
    private static final int MAX_Y_EXCLUSIVE = 320;

    @Test
    void columnCacheIsRegionKeyedAndBounded() {
        DeterministicStrataSampler sampler = new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE);
        StrataService service = new StrataService(
                0x5EEDL,
                sampler,
                RockProfileRegistry::vanillaDefaults,
                2);

        GeologyColumn first = service.columnAt(64, 64);
        GeologyColumn sameRegion = service.columnAt(4_096, 4_096);
        assertSame(first, sameRegion, "coordinates in the same geology region should reuse one cached column");

        service.columnAt(8_192, 0);
        service.columnAt(16_384, 0);
        assertEquals(2, service.cachedRegionCount());

        GeologyColumn reloadedFirst = service.columnAt(64, 64);
        assertNotSame(first, reloadedFirst, "least-recently-used region should be evicted once capacity is exceeded");
        assertEquals(first, reloadedFirst, "eviction must not change deterministic geology");
    }

    @Test
    void profileAtResolvesTheCurrentRegistrySnapshotWithoutResamplingTheColumn() {
        long worldSeed = 0x51A7A5EEDL;
        DeterministicStrataSampler sampler = new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE);
        AtomicReference<RockProfileRegistry> registry = new AtomicReference<>(RockProfileRegistry.vanillaDefaults());
        StrataService service = new StrataService(worldSeed, sampler, registry::get, 8);
        BlockPos pos = new BlockPos(1_234, 72, -5_678);

        GeologyColumn column = service.columnAt(pos.getX(), pos.getZ());
        String profileId = column.profileIdAt(pos.getY());
        assertEquals(profileId, service.profileAt(pos).id());
        assertEquals(1, service.cachedRegionCount());

        RockProfile replacement = new RockProfile(
                profileId,
                RockCategory.METAMORPHIC,
                0.67,
                0.12,
                2.4,
                0.78,
                0.81,
                0.22);
        registry.set(RockProfileRegistry.builder().profile(replacement).build());

        assertSame(replacement, service.profileAt(pos));
        assertSame(column, service.columnAt(pos.getX(), pos.getZ()),
                "registry reloads must not invalidate deterministic virtual columns");
    }

    @Test
    void missingProfileIdFallsBackToGenericStone() {
        StrataService service = new StrataService(
                123L,
                new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE),
                () -> RockProfileRegistry.builder().build(),
                4);

        assertSame(RockProfile.GENERIC_STONE, service.profileAt(new BlockPos(0, 64, 0)));
    }

    @Test
    void rejectsNonPositiveCacheCapacity() {
        DeterministicStrataSampler sampler = new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new StrataService(1L, sampler, RockProfileRegistry::vanillaDefaults, 0));
    }
}
