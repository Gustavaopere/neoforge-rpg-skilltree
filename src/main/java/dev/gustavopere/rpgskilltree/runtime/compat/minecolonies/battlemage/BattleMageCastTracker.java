package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.inventory.InventoryCitizen;
import com.minecolonies.core.entity.citizen.EntityCitizen;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import net.minecraft.world.item.ItemStack;

final class BattleMageCastTracker {
    private static final Map<EntityCitizen, Context> ACTIVE = Collections.synchronizedMap(new WeakHashMap<>());

    private BattleMageCastTracker() {}

    static void markStarted(EntityCitizen caster, BattleMageLoadoutResolver.Loadout loadout, SpellData spellData) {
        Objects.requireNonNull(caster, "caster");
        Objects.requireNonNull(loadout, "loadout");
        Objects.requireNonNull(spellData, "spellData");
        ACTIVE.put(caster, new Context(loadout.slot(), loadout.bookStack(), spellData.getSpell().getSpellId(), spellData.getLevel()));
    }

    static boolean ownsCast(EntityCitizen caster) {
        return caster != null && ACTIVE.containsKey(caster);
    }

    static boolean contextStillValid(EntityCitizen caster) {
        Context context = caster == null ? null : ACTIVE.get(caster);
        if (context == null) return false;

        InventoryCitizen inventory = caster.getInventoryCitizen();
        if (context.slot() < 0 || context.slot() >= inventory.getSlots()) return false;

        ItemStack current = inventory.getStackInSlot(context.slot());
        if (current != context.bookIdentity() || !BattleMageLoadoutResolver.isUsableSpellbook(current)) return false;

        BattleMageLoadoutResolver.Loadout live = new BattleMageLoadoutResolver.Loadout(inventory, context.slot());
        return live.activeSpells().stream().anyMatch(spellData ->
            spellData != null
                && spellData.getSpell() != null
                && context.spellId().equals(spellData.getSpell().getSpellId())
                && context.spellLevel() == spellData.getLevel()
        );
    }

    static void clear(EntityCitizen caster) {
        if (caster != null) ACTIVE.remove(caster);
    }

    private record Context(int slot, ItemStack bookIdentity, String spellId, int spellLevel) {
        private Context {
            Objects.requireNonNull(bookIdentity, "bookIdentity");
            Objects.requireNonNull(spellId, "spellId");
        }
    }
}
