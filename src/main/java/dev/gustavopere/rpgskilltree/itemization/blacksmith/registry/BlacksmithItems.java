package dev.gustavopere.rpgskilltree.itemization.blacksmith.registry;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Minimal item registry required by the Productive Metalworks casting adapter. */
public final class BlacksmithItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RpgSkillTreeMod.MOD_ID);

    public static final DeferredItem<Item> BLADE_CAST = ITEMS.registerSimpleItem("blade_cast");
    public static final DeferredItem<Item> BLADE = ITEMS.registerSimpleItem("blade");

    private BlacksmithItems() {}

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
