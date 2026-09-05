package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class NativeColonyEconomyBindingJUnitTest {
    private static final UUID OWNER = UUID.fromString("00000000-0000-0000-0000-000000001501");
    private static final NativeColonyBinding NATIVE = new NativeColonyBinding(
        ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
        7,
        OWNER,
        new BlockPos(10, 64, 10)
    );

    @Test
    void sameLiveNativeBindingResolvesSameEconomyUuidAcrossReload() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey first = data.resolveOrCreateBinding(NATIVE);

        ColonyEconomySavedData loaded = ColonyEconomySavedData.decodeForTest(data.encodeForTest());
        EconomyColonyKey restored = loaded.resolveOrCreateBinding(NATIVE);

        assertEquals(first, restored);
    }

    @Test
    void deletingBindingArchivesOldEconomyAndRecycledNativeIdGetsNewUuid() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        EconomyColonyKey first = data.resolveOrCreateBinding(NATIVE);

        data.archiveBinding(NATIVE);
        assertTrue(data.binding(NATIVE).isEmpty());
        assertTrue(data.isArchived(first));

        EconomyColonyKey recycled = data.resolveOrCreateBinding(NATIVE);
        assertNotEquals(first, recycled);
        assertTrue(data.isArchived(first));
    }

    @Test
    void sameNativeIdWithDifferentOwnerFailsClosedInsteadOfInheritingMoney() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        data.resolveOrCreateBinding(NATIVE);
        NativeColonyBinding changedOwner = new NativeColonyBinding(
            NATIVE.dimensionId(),
            NATIVE.colonyId(),
            UUID.fromString("00000000-0000-0000-0000-000000001502"),
            NATIVE.townHallPos()
        );

        assertThrows(EconomyPersistenceException.class, () -> data.resolveOrCreateBinding(changedOwner));
    }

    @Test
    void sameNativeIdWithDifferentTownHallFailsClosedInsteadOfInheritingMoney() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        data.resolveOrCreateBinding(NATIVE);
        NativeColonyBinding movedTownHall = new NativeColonyBinding(
            NATIVE.dimensionId(),
            NATIVE.colonyId(),
            NATIVE.ownerUuid(),
            NATIVE.townHallPos().offset(32, 0, 0)
        );

        assertThrows(EconomyPersistenceException.class, () -> data.resolveOrCreateBinding(movedTownHall));
    }
}
