package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.AntiFarmDecision;
import dev.gustavopere.rpgskilltree.core.GameplaySemanticXpPolicy;
import dev.gustavopere.rpgskilltree.core.SemanticAction;
import dev.gustavopere.rpgskilltree.core.SemanticActionAuthorship;
import dev.gustavopere.rpgskilltree.core.SemanticActionContext;
import dev.gustavopere.rpgskilltree.core.SemanticActionType;
import dev.gustavopere.rpgskilltree.runtime.GameplaySemanticXpRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class ExplorationProgressionEvents {
    private static final int BIOME_SAMPLE_INTERVAL_TICKS = 100;
    private static final String ORIGIN_ID = "neoforge:exploration";

    private ExplorationProgressionEvents() {}

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        creditCurrentDimension(player);
        creditCurrentBiome(player);
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        creditCurrentDimension(player);
        creditCurrentBiome(player);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % BIOME_SAMPLE_INTERVAL_TICKS != 0) return;
        creditCurrentBiome(player);
    }

    private static void creditCurrentDimension(ServerPlayer player) {
        String dimensionId = player.level().dimension().location().toString();
        GameplaySemanticXpRuntime.applyFirstCompletion(
            player,
            "dimension:" + dimensionId,
            new SemanticAction(
                SemanticActionType.DIMENSION_DISCOVERED,
                dimensionId,
                new ActionOrigin(ORIGIN_ID, 0),
                SemanticActionAuthorship.DIRECT_PLAYER,
                SemanticActionContext.empty()
            ),
            ignored -> AntiFarmDecision.allow(),
            GameplaySemanticXpPolicy.INSTANCE
        );
    }

    private static void creditCurrentBiome(ServerPlayer player) {
        player.level().getBiome(player.blockPosition()).unwrapKey().ifPresent(key -> {
            String biomeId = key.location().toString();
            GameplaySemanticXpRuntime.applyFirstCompletion(
                player,
                "biome:" + biomeId,
                new SemanticAction(
                    SemanticActionType.BIOME_DISCOVERED,
                    biomeId,
                    new ActionOrigin(ORIGIN_ID, 0),
                    SemanticActionAuthorship.DIRECT_PLAYER,
                    SemanticActionContext.empty()
                ),
                ignored -> AntiFarmDecision.allow(),
                GameplaySemanticXpPolicy.INSTANCE
            );
        });
    }
}
