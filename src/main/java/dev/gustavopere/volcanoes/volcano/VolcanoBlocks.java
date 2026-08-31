package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Runtime block registry for volcanic gameplay surfaces. */
public final class VolcanoBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(VolcanoesMod.MOD_ID);

    public static final DeferredBlock<AshLayerBlock> ASH_LAYER = BLOCKS.registerBlock(
            "ash_layer",
            AshLayerBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(0.1F)
                    .sound(SoundType.SAND)
                    .replaceable()
                    .noOcclusion());

    private VolcanoBlocks() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }
}
