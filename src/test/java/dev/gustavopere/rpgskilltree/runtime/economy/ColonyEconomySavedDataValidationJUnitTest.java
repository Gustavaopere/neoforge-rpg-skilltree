package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyCommand;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransactionKind;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class ColonyEconomySavedDataValidationJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000002201")
    );
    private static final EconomyColonyKey OTHER = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000002202")
    );
    private static final NativeColonyBinding NATIVE = new NativeColonyBinding(
        ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
        7,
        UUID.fromString("00000000-0000-0000-0000-000000002203"),
        new BlockPos(10, 64, 10)
    );

    @Test
    void rootSchemaAndRequiredFieldsFailClosed() {
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(null));

        CompoundTag future = validEmpty();
        future.putInt("schema", 2);
        assertThrows(UnsupportedEconomySchemaException.class, () -> ColonyEconomySavedData.decodeForTest(future));

        CompoundTag legacy = validEmpty();
        legacy.putInt("schema", 0);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(legacy));

        for (String required : new String[] {"economies", "native_bindings", "native_fingerprints", "archived_economies"}) {
            CompoundTag missing = validEmpty();
            missing.remove(required);
            assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(missing));
        }

        CompoundTag wrongEconomiesType = validEmpty();
        wrongEconomiesType.putString("economies", "not-a-list");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(wrongEconomiesType));

        CompoundTag wrongBindingsType = validEmpty();
        wrongBindingsType.putString("native_bindings", "not-a-compound");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(wrongBindingsType));
    }

    @Test
    void economyAndTransactionListStructureFailsClosed() {
        CompoundTag wrongEconomyElement = validEmpty();
        ListTag wrongEconomies = new ListTag();
        wrongEconomies.add(StringTag.valueOf("not-a-compound"));
        wrongEconomyElement.put("economies", wrongEconomies);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(wrongEconomyElement));

        CompoundTag duplicate = validMinted();
        ListTag duplicateEconomies = duplicate.getList("economies", Tag.TAG_COMPOUND);
        duplicateEconomies.add(duplicateEconomies.getCompound(0).copy());
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(duplicate));

        CompoundTag missingState = validMinted();
        missingState.getList("economies", Tag.TAG_COMPOUND).getCompound(0).remove("state");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(missingState));

        CompoundTag wrongTransactionElement = validMinted();
        CompoundTag economy = wrongTransactionElement.getList("economies", Tag.TAG_COMPOUND).getCompound(0);
        ListTag badTransactions = new ListTag();
        badTransactions.add(StringTag.valueOf("not-a-transaction"));
        economy.put("transactions", badTransactions);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(wrongTransactionElement));
    }

    @Test
    void nativeBindingAndFingerprintIndexesFailClosed() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey economyKey = data.resolveOrCreateBinding(NATIVE);

        CompoundTag invalidBindingType = data.encodeForTest();
        invalidBindingType.getCompound("native_bindings").putInt(NATIVE.persistentKey(), 7);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(invalidBindingType));

        CompoundTag invalidBindingUuid = data.encodeForTest();
        invalidBindingUuid.getCompound("native_bindings").putString(NATIVE.persistentKey(), "not-a-uuid");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(invalidBindingUuid));

        CompoundTag missingFingerprint = data.encodeForTest();
        missingFingerprint.getCompound("native_fingerprints").remove(NATIVE.persistentKey());
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(missingFingerprint));

        CompoundTag mismatchedFingerprintKey = data.encodeForTest();
        CompoundTag fingerprints = mismatchedFingerprintKey.getCompound("native_fingerprints");
        CompoundTag fingerprint = fingerprints.getCompound(NATIVE.persistentKey()).copy();
        fingerprints.remove(NATIVE.persistentKey());
        fingerprints.put("minecraft:overworld#999", fingerprint);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(mismatchedFingerprintKey));

        CompoundTag malformedFingerprint = data.encodeForTest();
        malformedFingerprint.getCompound("native_fingerprints")
            .getCompound(NATIVE.persistentKey())
            .putString("dimension", "bad id");
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(malformedFingerprint));

        CompoundTag livePointsToArchived = data.encodeForTest();
        livePointsToArchived.getList("archived_economies", Tag.TAG_STRING)
            .add(StringTag.valueOf(economyKey.value().toString()));
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(livePointsToArchived));
    }

    @Test
    void duplicateLiveIdentityAndMalformedArchiveFailClosed() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey economyKey = data.resolveOrCreateBinding(NATIVE);
        NativeColonyBinding second = new NativeColonyBinding(
            NATIVE.dimensionId(),
            8,
            NATIVE.ownerUuid(),
            NATIVE.townHallPos().offset(32, 0, 0)
        );

        CompoundTag duplicateIdentity = data.encodeForTest();
        duplicateIdentity.getCompound("native_bindings")
            .putString(second.persistentKey(), economyKey.value().toString());
        duplicateIdentity.getCompound("native_fingerprints")
            .put(second.persistentKey(), fingerprint(second));
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(duplicateIdentity));

        CompoundTag badArchive = validEmpty();
        badArchive.getList("archived_economies", Tag.TAG_STRING).add(StringTag.valueOf("not-a-uuid"));
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(badArchive));
    }

    @Test
    void ledgerOwnershipAndTailReconciliationFailClosed() {
        CompoundTag wrongOwner = validMinted();
        CompoundTag economy = wrongOwner.getList("economies", Tag.TAG_COMPOUND).getCompound(0);
        economy.getList("transactions", Tag.TAG_COMPOUND).getCompound(0)
            .putString("colony_key", OTHER.value().toString());
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(wrongOwner));

        CompoundTag wrongTail = validMinted();
        CompoundTag state = wrongTail.getList("economies", Tag.TAG_COMPOUND).getCompound(0).getCompound("state");
        state.putLong("issued_supply", 21L);
        state.putLong("treasury_balance", 21L);
        assertThrows(EconomyPersistenceException.class, () -> ColonyEconomySavedData.decodeForTest(wrongTail));
    }

    @Test
    void bindingMutationApiRejectsNullAndUnknownInputs() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        assertThrows(IllegalArgumentException.class, () -> ColonyEconomySavedData.get(null));
        assertThrows(IllegalArgumentException.class, () -> data.binding(null));
        assertThrows(IllegalArgumentException.class, () -> data.resolveOrCreateBinding(null));
        assertThrows(IllegalArgumentException.class, () -> data.archiveBinding(null));
        assertTrue(data.archiveBinding(NATIVE).isEmpty());
    }

    private static CompoundTag validEmpty() {
        return new ColonyEconomySavedData().encodeForTest();
    }

    private static CompoundTag validMinted() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        repository.apply(
            COLONY,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000002204"),
                "saved-data-validation:mint",
                EconomyTransactionKind.MINT,
                20L
            ),
            100L
        );
        return data.encodeForTest();
    }

    private static CompoundTag fingerprint(NativeColonyBinding binding) {
        CompoundTag tag = new CompoundTag();
        tag.putString("dimension", binding.dimensionId().toString());
        tag.putInt("colony_id", binding.colonyId());
        tag.putString("owner_uuid", binding.ownerUuid().toString());
        tag.putInt("town_hall_x", binding.townHallPos().getX());
        tag.putInt("town_hall_y", binding.townHallPos().getY());
        tag.putInt("town_hall_z", binding.townHallPos().getZ());
        return tag;
    }
}
