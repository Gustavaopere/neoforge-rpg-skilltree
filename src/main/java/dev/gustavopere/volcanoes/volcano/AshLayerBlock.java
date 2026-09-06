package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** Thin stackable volcanic-ash deposit using vanilla snow-layer placement semantics. */
public final class AshLayerBlock extends SnowLayerBlock {
    public AshLayerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
