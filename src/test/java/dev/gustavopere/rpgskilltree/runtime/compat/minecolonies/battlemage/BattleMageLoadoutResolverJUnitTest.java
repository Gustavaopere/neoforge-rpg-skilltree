package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.api.inventory.InventoryCitizen;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.Test;

final class BattleMageLoadoutResolverJUnitTest {
    @Test
    void exposesCitizenOwnedInventoryResolverSurface() throws Exception {
        assertEquals(
            Optional.class,
            BattleMageLoadoutResolver.class.getMethod("resolve", AbstractEntityCitizen.class).getReturnType()
        );
        assertTrue(BattleMageLoadoutResolver.resolve(null).isEmpty());
    }

    @Test
    void scansInventorySlotsDeterministicallyWithoutBootstrappingMinecraftRegistries() {
        List<Integer> visited = new ArrayList<>();
        OptionalInt slot = BattleMageLoadoutResolver.findFirstMatchingIndex(10, index -> {
            visited.add(index);
            return index == 7;
        });

        assertTrue(slot.isPresent());
        assertEquals(7, slot.getAsInt());
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7), visited);
        assertFalse(BattleMageLoadoutResolver.findFirstMatchingIndex(10, index -> false).isPresent());
        assertFalse(BattleMageLoadoutResolver.findFirstMatchingIndex(0, index -> true).isPresent());
    }

    @Test
    void deterministicScanRejectsInvalidArguments() {
        assertThrows(
            IllegalArgumentException.class,
            () -> BattleMageLoadoutResolver.findFirstMatchingIndex(-1, index -> true)
        );
        assertThrows(
            NullPointerException.class,
            () -> BattleMageLoadoutResolver.findFirstMatchingIndex(1, null)
        );
    }

    @Test
    void unusableBookChecksFailClosedBeforeProviderContainerLookup() {
        assertFalse(BattleMageLoadoutResolver.isUsableSpellbook(null));
        assertFalse(BattleMageLoadoutResolver.isUsableSpellbook(ItemStack.EMPTY));
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
