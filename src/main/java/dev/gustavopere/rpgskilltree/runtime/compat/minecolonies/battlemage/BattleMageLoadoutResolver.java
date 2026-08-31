package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.api.inventory.InventoryCitizen;
import io.redspace.ironsspellbooks.api.item.ISpellbook;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;

/**
 * Resolves the Battle Mage repertoire from the MineColonies-owned citizen inventory.
 *
 * <p>The resolver deliberately retains only the inventory and selected slot. It never copies an
 * authoritative spellbook or spell list: every {@link Loadout#bookStack()} and
 * {@link Loadout#activeSpells()} call re-reads the real MineColonies slot and the real Iron's
 * spell-container component. Book removal or replacement therefore becomes visible immediately.</p>
 */
public final class BattleMageLoadoutResolver {
    private BattleMageLoadoutResolver() {
    }

    /**
     * Finds the first usable Iron's spellbook in deterministic inventory-slot order.
     */
    public static Optional<Loadout> resolve(AbstractEntityCitizen citizen) {
        if (citizen == null) {
            return Optional.empty();
        }

        InventoryCitizen inventory = citizen.getInventoryCitizen();
        OptionalInt slot = findFirstMatchingSlot(inventory, BattleMageLoadoutResolver::isUsableSpellbook);
        return slot.isPresent() ? Optional.of(new Loadout(inventory, slot.getAsInt())) : Optional.empty();
    }

    static OptionalInt findFirstMatchingSlot(InventoryCitizen inventory, Predicate<ItemStack> predicate) {
        Objects.requireNonNull(inventory, "inventory");
        Objects.requireNonNull(predicate, "predicate");

        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            if (predicate.test(inventory.getStackInSlot(slot))) {
                return OptionalInt.of(slot);
            }
        }
        return OptionalInt.empty();
    }

    static boolean isUsableSpellbook(ItemStack stack) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ISpellbook)) {
            return false;
        }
        if (!ISpellContainer.isSpellContainer(stack)) {
            return false;
        }

        ISpellContainer container = ISpellContainer.get(stack);
        return container != null && !container.isEmpty() && !container.getActiveSpells().isEmpty();
    }

    /**
     * Live reference to one MineColonies inventory slot. No provider-owned state is duplicated.
     */
    public record Loadout(InventoryCitizen inventory, int slot) {
        public Loadout {
            Objects.requireNonNull(inventory, "inventory");
            if (slot < 0 || slot >= inventory.getSlots()) {
                throw new IllegalArgumentException("slot outside citizen inventory: " + slot);
            }
        }

        public ItemStack bookStack() {
            return inventory.getStackInSlot(slot);
        }

        /**
         * Returns a fresh view of the currently active provider SpellData values in this slot.
         */
        public List<SpellData> activeSpells() {
            ItemStack stack = bookStack();
            if (!isUsableSpellbook(stack)) {
                return List.of();
            }

            ISpellContainer container = ISpellContainer.get(stack);
            if (container == null) {
                return List.of();
            }
            return container.getActiveSpells().stream()
                .map(spellSlot -> spellSlot.spellData())
                .filter(Objects::nonNull)
                .toList();
        }

        public boolean isStillUsable() {
            return isUsableSpellbook(bookStack());
        }
    }
}
