package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.api.inventory.InventoryCitizen;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    void scansTheRealCitizenInventoryDeterministically() {
        InventoryCitizen inventory = new InventoryCitizen("battle-mage-test", false);
        ItemStack first = new ItemStack(Items.STICK);
        ItemStack selected = new ItemStack(Items.BLAZE_ROD);
        inventory.setStackInSlot(1, first);
        inventory.setStackInSlot(7, selected);

        OptionalInt slot = BattleMageLoadoutResolver.findFirstMatchingSlot(
            inventory,
            stack -> stack.is(Items.BLAZE_ROD)
        );

        assertTrue(slot.isPresent());
        assertEquals(7, slot.getAsInt());
        assertFalse(BattleMageLoadoutResolver.findFirstMatchingSlot(inventory, stack -> stack.is(Items.DIAMOND)).isPresent());
    }

    @Test
    void resolvedLoadoutTracksTheLiveSlotInsteadOfCopyingTheBook() {
        InventoryCitizen inventory = new InventoryCitizen("battle-mage-test", false);
        ItemStack original = new ItemStack(Items.BOOK);
        inventory.setStackInSlot(4, original);

        BattleMageLoadoutResolver.Loadout loadout = new BattleMageLoadoutResolver.Loadout(inventory, 4);
        assertSame(original, loadout.bookStack());

        ItemStack replacement = new ItemStack(Items.WRITABLE_BOOK);
        inventory.setStackInSlot(4, replacement);
        assertSame(replacement, loadout.bookStack());
    }
}
