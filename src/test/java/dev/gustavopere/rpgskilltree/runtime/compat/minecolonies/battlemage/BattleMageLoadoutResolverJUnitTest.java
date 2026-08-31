package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.api.inventory.InventoryCitizen;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import org.junit.jupiter.api.Test;

final class BattleMageLoadoutResolverJUnitTest {
    @Test
    void exposesCitizenOwnedInventoryResolverSurface() throws Exception {
        assertEquals(
            Optional.class,
            BattleMageLoadoutResolver.class.getMethod("resolve", AbstractEntityCitizen.class).getReturnType()
        );
    }

    @Test
    void scansInventorySlotsDeterministicallyWithoutBootstrappingMinecraftRegistries() {
        OptionalInt slot = BattleMageLoadoutResolver.findFirstMatchingIndex(10, index -> index == 7);

        assertTrue(slot.isPresent());
        assertEquals(7, slot.getAsInt());
        assertFalse(BattleMageLoadoutResolver.findFirstMatchingIndex(10, index -> false).isPresent());
    }

    @Test
    void loadoutRetainsOnlyLiveInventoryAndSlotIdentity() {
        RecordComponent[] components = BattleMageLoadoutResolver.Loadout.class.getRecordComponents();
        assertEquals(2, components.length);
        assertEquals(
            Arrays.asList(InventoryCitizen.class, int.class),
            Arrays.stream(components).map(RecordComponent::getType).toList()
        );
    }
}
