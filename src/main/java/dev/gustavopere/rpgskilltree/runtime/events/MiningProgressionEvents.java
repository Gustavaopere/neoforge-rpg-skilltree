package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.GameplayXpPolicy;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.mining.PlayerPlacedOreData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

public final class MiningProgressionEvents {
    private MiningProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        if (!event.getPlacedBlock().is(Tags.Blocks.ORES)) return;

        PlayerPlacedOreData.get(level).mark(event.getPos());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BlockState state = event.getState();
        if (!state.is(Tags.Blocks.ORES)) return;

        boolean playerPlaced = PlayerPlacedOreData.get(level).consume(event.getPos());
        if (player.isCreative() || playerPlaced) return;

        String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        boolean rare = state.is(Tags.Blocks.ORES_DIAMOND)
            || state.is(Tags.Blocks.ORES_EMERALD)
            || state.is(Tags.Blocks.ORES_NETHERITE_SCRAP);
        PlayerProgressionRuntime.applyXp(player, GameplayXpPolicy.oreMined(blockId, rare));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (event.getAffectedBlocks().isEmpty()) return;

        PlayerPlacedOreData.get(level).removeAll(event.getAffectedBlocks());
    }
}
