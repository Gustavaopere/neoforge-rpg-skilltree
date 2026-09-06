package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Runtime item registry for volcanic gameplay blocks. */
public final class VolcanoItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VolcanoesMod.MOD_ID);

    public static final DeferredItem<BlockItem> ASH_LAYER = ITEMS.registerSimpleBlockItem(VolcanoBlocks.ASH_LAYER);

    private VolcanoItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
