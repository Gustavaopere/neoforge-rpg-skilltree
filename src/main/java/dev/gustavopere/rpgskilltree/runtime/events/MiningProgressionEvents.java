package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.GameplaySemanticXpPolicy;
import dev.gustavopere.rpgskilltree.core.SemanticAction;
import dev.gustavopere.rpgskilltree.core.SemanticActionAuthorship;
import dev.gustavopere.rpgskilltree.core.SemanticActionContext;
import dev.gustavopere.rpgskilltree.core.SemanticActionType;
import dev.gustavopere.rpgskilltree.runtime.GameplaySemanticXpRuntime;
import dev.gustavopere.rpgskilltree.runtime.mining.PlayerPlacedOreData;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
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

        PlayerPlacedOreData oreData = PlayerPlacedOreData.get(level);
        if (player.isCreative()) {
            oreData.consume(event.getPos());
            return;
        }

        String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        boolean rare = state.is(Tags.Blocks.ORES_DIAMOND)
            || state.is(Tags.Blocks.ORES_EMERALD)
            || state.is(Tags.Blocks.ORES_NETHERITE_SCRAP);
        Set<String> tags = rare
            ? Set.of(GameplaySemanticXpPolicy.RARE_ORE_TAG)
            : Set.of();
        SemanticAction action = new SemanticAction(
            SemanticActionType.ORE_MINED,
            blockId,
            new ActionOrigin("rpgskilltree:neoforge/block_break", 0),
            SemanticActionAuthorship.DIRECT_PLAYER,
            new SemanticActionContext(
                OptionalLong.of(event.getPos().asLong()),
                Map.of(),
                tags
            )
        );

        try {
            GameplaySemanticXpRuntime.apply(
                player,
                action,
                oreData.antiFarmService(),
                GameplaySemanticXpPolicy.INSTANCE
            );
        } finally {
            oreData.consume(event.getPos());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (event.getAffectedBlocks().isEmpty()) return;

        PlayerPlacedOreData.get(level).removeAll(event.getAffectedBlocks());
    }
}
