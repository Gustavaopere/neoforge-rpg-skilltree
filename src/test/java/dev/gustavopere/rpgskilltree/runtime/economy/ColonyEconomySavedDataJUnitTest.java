package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyCommand;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class ColonyEconomySavedDataJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001401")
    );
    private static final NativeColonyBinding NATIVE = new NativeColonyBinding(
        ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
        41,
        UUID.fromString("00000000-0000-0000-0000-000000001410"),
        new BlockPos(8, 64, 8)
    );

    @Test
    void saveReloadPreservesStateAndReplayProtection() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        EconomyCommand mint = new EconomyCommand(
            UUID.fromString("00000000-0000-0000-0000-000000001402"),
            "restart-safe:mint:saved-data",
            EconomyTransactionKind.MINT,
            20L
        );

        EconomyMutationResult first = repository.apply(COLONY, mint, 5_000L);
        assertEquals(EconomyMutationResult.Status.APPLIED, first.status());

        CompoundTag encoded = data.encodeForTest();
        ColonyEconomySavedData loaded = ColonyEconomySavedData.decodeForTest(encoded);
        ColonyEconomyRepository restored = new ColonyEconomyRepository(loaded);

        assertEquals(first.state(), restored.find(COLONY).orElseThrow());
        assertEquals(1, restored.transactions(COLONY).size());

        EconomyMutationResult duplicate = restored.apply(COLONY, mint, 6_000L);
        assertEquals(EconomyMutationResult.Status.DUPLICATE, duplicate.status());
        assertEquals(first.state(), duplicate.state());
        assertEquals(1, restored.transactions(COLONY).size());
    }

    @Test
    void rejectedMutationDoesNotCreatePersistentEconomyEntry() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        EconomyCommand retire = new EconomyCommand(
            UUID.fromString("00000000-0000-0000-0000-000000001403"),
            "retire:without-funds",
            EconomyTransactionKind.RETIRE,
            1L
        );

        EconomyMutationResult result = repository.apply(COLONY, retire, 10L);

        assertEquals(EconomyMutationResult.Status.INSUFFICIENT_TREASURY, result.status());
        assertTrue(repository.find(COLONY).isEmpty());
        assertTrue(repository.transactions(COLONY).isEmpty());
    }

    @Test
    void settlementStateCanBeStoredWithoutForgingLedgerTransaction() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        ColonyEconomyState settled = new ColonyEconomyState(
            COLONY,
            0L,
            0L,
            0L,
            0L,
            0L,
            95.0D,
            0.10D,
            25L,
            12_000L,
            ColonyEconomyStateCodec.CURRENT_SCHEMA
        );

        repository.storeSettledState(settled);
        repository.storeSettledState(settled);

        assertEquals(settled, repository.find(COLONY).orElseThrow());
        assertTrue(repository.transactions(COLONY).isEmpty());
    }

    @Test
    void nullInputsAndUnknownBindingsFailClosed() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();

        assertThrows(IllegalArgumentException.class, () -> ColonyEconomySavedData.get(null));
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(null));
        assertThrows(IllegalArgumentException.class, () -> data.binding(null));
        assertThrows(IllegalArgumentException.class, () -> data.resolveOrCreateBinding(null));
        assertThrows(IllegalArgumentException.class, () -> data.archiveBinding(null));
        assertTrue(data.binding(NATIVE).isEmpty());
        assertTrue(data.archiveBinding(NATIVE).isEmpty());
    }

    @Test
    void rootSchemaAndRequiredRootFieldsAreStrict() {
        CompoundTag missingSchema = new CompoundTag();
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(missingSchema));

        CompoundTag future = emptyRoot();
        future.putInt("schema", 2);
        assertThrows(UnsupportedEconomySchemaException.class, () -> ColonyEconomySavedData.decodeForTest(future));

        CompoundTag legacy = emptyRoot();
        legacy.putInt("schema", 0);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(legacy));

        CompoundTag missingEconomies = emptyRoot();
        missingEconomies.remove("economies");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(missingEconomies));
    }

    @Test
    void malformedBindingAndArchiveIndexesAreRejected() {
        CompoundTag invalidBindingUuid = emptyRoot();
        invalidBindingUuid.getCompound("native_bindings").putString("minecraft:overworld#41", "not-a-uuid");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(invalidBindingUuid));

        CompoundTag invalidArchivedUuid = emptyRoot();
        invalidArchivedUuid.getList("archived_economies", StringTag.TAG_STRING).add(StringTag.valueOf("not-a-uuid"));
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(invalidArchivedUuid));

        CompoundTag mismatchedIndexes = emptyRoot();
        mismatchedIndexes.getCompound("native_bindings").putString(
            NATIVE.persistentKey(),
            UUID.fromString("00000000-0000-0000-0000-000000001411").toString()
        );
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(mismatchedIndexes));
    }

    @Test
    void malformedFingerprintPayloadIsRejectedBeforeBindingCanBeTrusted() {
        CompoundTag root = emptyRoot();
        CompoundTag fingerprints = root.getCompound("native_fingerprints");
        CompoundTag fingerprint = new CompoundTag();
        fingerprint.putString("dimension", "bad dimension id");
        fingerprint.putInt("colony_id", 41);
        fingerprint.putString("owner_uuid", NATIVE.ownerUuid().toString());
        fingerprint.putInt("town_hall_x", 8);
        fingerprint.putInt("town_hall_y", 64);
        fingerprint.putInt("town_hall_z", 8);
        fingerprints.put(NATIVE.persistentKey(), fingerprint);

        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(root));
    }

    @Test
    void duplicateEconomyRecordsAreRejected() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        repository.apply(
            COLONY,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000001412"),
                "duplicate-record:mint",
                EconomyTransactionKind.MINT,
                5L
            ),
            1L
        );
        CompoundTag encoded = data.encodeForTest();
        ListTag economies = encoded.getList("economies", CompoundTag.TAG_COMPOUND);
        economies.add(economies.getCompound(0).copy());

        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(encoded));
    }

    @Test
    void liveBindingCannotAlsoBeArchivedInPersistedState() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey key = data.resolveOrCreateBinding(NATIVE);
        CompoundTag encoded = data.encodeForTest();
        encoded.getList("archived_economies", StringTag.TAG_STRING).add(StringTag.valueOf(key.value().toString()));

        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(encoded));
    }

    private static CompoundTag emptyRoot() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("schema", 1);
        tag.put("economies", new ListTag());
        tag.put("native_bindings", new CompoundTag());
        tag.put("native_fingerprints", new CompoundTag());
        tag.put("archived_economies", new ListTag());
        return tag;
    }
}
