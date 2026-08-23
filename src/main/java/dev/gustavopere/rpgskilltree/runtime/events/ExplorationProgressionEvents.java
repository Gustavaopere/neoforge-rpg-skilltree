package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.GameplayXpPolicy;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class ExplorationProgressionEvents {
    private static final int BIOME_SAMPLE_INTERVAL_TICKS = 100;

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
        PlayerProgressionRuntime.creditDiscovery(
            player,
            "dimension:" + dimensionId,
            GameplayXpPolicy.dimensionDiscovery(dimensionId)
        );
    }

    private static void creditCurrentBiome(ServerPlayer player) {
        player.level().getBiome(player.blockPosition()).unwrapKey().ifPresent(key -> {
            String biomeId = key.location().toString();
            PlayerProgressionRuntime.creditDiscovery(
                player,
                "biome:" + biomeId,
                GameplayXpPolicy.biomeDiscovery(biomeId)
            );
        });
    }
}
