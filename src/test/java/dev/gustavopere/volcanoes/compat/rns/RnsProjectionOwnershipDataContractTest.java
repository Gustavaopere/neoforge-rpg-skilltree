package dev.gustavopere.volcanoes.compat.rns;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RnsProjectionOwnershipDataContractTest {
    private static final ResourceLocation RNS_COPPER =
            ResourceLocation.fromNamespaceAndPath("create_rns", "deposit_copper");

    @Test
    void ownershipProofSurvivesPersistenceAndAuthorizesOnlyItsExactSource() {
        RnsDepositProjectionPlanner.Projection projection = projection(
                1, new BlockPos(32, 24, 32));
        RnsProjectionOwnershipData ownership = new RnsProjectionOwnershipData();

        assertTrue(ownership.claim(projection));
        assertTrue(ownership.owns(projection));

        RnsProjectionOwnershipData restored = RnsProjectionOwnershipData.fromTag(ownership.toTag());
        assertTrue(restored.owns(projection));
        assertTrue(restored.release(projection));
        assertFalse(restored.owns(projection));
    }

    @Test
    void sameRnsIdentityCannotBeClaimedOrReleasedByAnotherSource() {
        RnsDepositProjectionPlanner.Projection first = projection(
                1, new BlockPos(33, 24, 33));
        RnsDepositProjectionPlanner.Projection otherSourceSameChunk = projection(
                2, new BlockPos(46, 30, 46));
        RnsProjectionOwnershipData ownership = new RnsProjectionOwnershipData();

        assertTrue(ownership.claim(first));
        assertTrue(ownership.claimedByOther(otherSourceSameChunk));
        assertFalse(ownership.claim(otherSourceSameChunk));
        assertFalse(ownership.release(otherSourceSameChunk));
        assertTrue(ownership.owns(first));
    }

    @Test
    void ambiguousPersistedOwnershipFailsClosed() {
        CompoundTag root = new CompoundTag();
        ListTag entries = new ListTag();
        entries.add(entry(1, new BlockPos(64, 24, 64)));
        entries.add(entry(2, new BlockPos(65, 24, 65)));
        root.put("entries", entries);

        RnsProjectionOwnershipData restored = RnsProjectionOwnershipData.fromTag(root);

        assertFalse(restored.owns(projection(1, new BlockPos(64, 24, 64))));
        assertFalse(restored.owns(projection(2, new BlockPos(65, 24, 65))));
    }

    @Test
    void malformedDuplicateAfterValidEntryInvalidatesPriorOwnership() {
        BlockPos center = new BlockPos(96, 24, 96);
        CompoundTag root = new CompoundTag();
        ListTag entries = new ListTag();
        entries.add(entry(3, center));
        entries.add(entryWithoutSource(center));
        root.put("entries", entries);

        RnsProjectionOwnershipData restored = RnsProjectionOwnershipData.fromTag(root);

        assertFalse(restored.owns(projection(3, center)),
                "a parseable duplicate identity with malformed ownership value must invalidate prior authority");
    }

    private static CompoundTag entry(int source, BlockPos center) {
        CompoundTag entry = entryWithoutSource(center);
        entry.putUUID("source_id", new UUID(0L, source));
        return entry;
    }

    private static CompoundTag entryWithoutSource(BlockPos center) {
        CompoundTag entry = new CompoundTag();
        entry.putString("rns_deposit_id", RNS_COPPER.toString());
        entry.putInt("chunk_x", center.getX() >> 4);
        entry.putInt("chunk_z", center.getZ() >> 4);
        entry.putLong("center", center.asLong());
        return entry;
    }

    private static RnsDepositProjectionPlanner.Projection projection(int source, BlockPos center) {
        return new RnsDepositProjectionPlanner.Projection(
                new UUID(0L, source), RNS_COPPER, center);
    }
}
