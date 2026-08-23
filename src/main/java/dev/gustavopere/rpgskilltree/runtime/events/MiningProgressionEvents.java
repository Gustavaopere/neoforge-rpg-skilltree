package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.GameplayXpPolicy;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class MiningProgressionEvents {
    private MiningProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        if (player.isCreative()) return;

        BlockState state = event.getState();
        if (!state.is(Tags.Blocks.ORES)) return;

        String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        boolean rare = state.is(Tags.Blocks.ORES_DIAMOND)
            || state.is(Tags.Blocks.ORES_EMERALD)
            || state.is(Tags.Blocks.ORES_NETHERITE_SCRAP);
        PlayerProgressionRuntime.applyXp(player, GameplayXpPolicy.oreMined(blockId, rare));
    }
}
