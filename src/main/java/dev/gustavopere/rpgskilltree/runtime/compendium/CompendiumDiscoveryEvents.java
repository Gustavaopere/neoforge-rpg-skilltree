package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryCriterion;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryOrigin;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoverySignal;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryState;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryTriggerType;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Generic trusted NeoForge feeds for Stage 10.04 discovery.
 *
 * <p>These feeds derive identities exclusively from server-observed entities/world state.
 * They intentionally do not scan nearby entities; richer inspection/photo/structure
 * semantics remain adapter-owned and enter through {@link CompendiumDiscoveryRuntime}.</p>
 */
public final class CompendiumDiscoveryEvents {
    private static final int BIOME_SAMPLE_INTERVAL_TICKS = 100;

    private CompendiumDiscoveryEvents() {}

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (event.getEntity() instanceof Player) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        ResourceLocation targetId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
        if (targetId == null) return;
        CompendiumEntryId entryId = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, targetId.toString());
        applyBuiltin(player, entryId, DiscoveryTriggerType.DEFEAT, DiscoveryState.STUDIED, "defeat");
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getTarget() instanceof LivingEntity target)) return;
        if (target instanceof Player) return;

        ResourceLocation targetId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        if (targetId == null) return;
        CompendiumEntryId entryId = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, targetId.toString());
        applyBuiltin(player, entryId, DiscoveryTriggerType.INTERACTION, DiscoveryState.SEEN, "interact");
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        discoverCurrentDimension(player);
        discoverCurrentBiome(player);
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        discoverCurrentDimension(player);
        discoverCurrentBiome(player);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % BIOME_SAMPLE_INTERVAL_TICKS != 0) return;
        discoverCurrentBiome(player);
    }

    private static void discoverCurrentDimension(ServerPlayer player) {
        ResourceLocation dimensionId = player.level().dimension().location();
        CompendiumEntryId entryId = CompendiumEntryId.of(
            CompendiumEntryKind.DIMENSION,
            dimensionId.toString()
        );
        applyBuiltin(
            player,
            entryId,
            DiscoveryTriggerType.DIMENSION_ENTRY,
            DiscoveryState.SEEN,
            "dimension_entry"
        );
    }

    private static void discoverCurrentBiome(ServerPlayer player) {
        player.level().getBiome(player.blockPosition()).unwrapKey().ifPresent(key -> {
            CompendiumEntryId entryId = CompendiumEntryId.of(
                CompendiumEntryKind.BIOME,
                key.location().toString()
            );
            if (CompendiumDiscoveryRuntime.progress(player).record(entryId)
                .filter(record -> record.completedObjectiveIds().contains("biome_entry"))
                .isPresent()) {
                return;
            }
            applyBuiltin(
                player,
                entryId,
                DiscoveryTriggerType.BIOME_ENTRY,
                DiscoveryState.SEEN,
                "biome_entry"
            );
        });
    }

    private static void applyBuiltin(
        ServerPlayer player,
        CompendiumEntryId entryId,
        DiscoveryTriggerType trigger,
        DiscoveryState targetState,
        String objectiveId
    ) {
        DiscoveryCriterion criterion = new DiscoveryCriterion(
            "builtin:" + trigger.name().toLowerCase(Locale.ROOT) + ":" + entryId.serializedId(),
            entryId,
            trigger,
            targetState,
            Optional.of(objectiveId),
            List.of()
        );
        DiscoverySignal signal = new DiscoverySignal(
            entryId,
            trigger,
            player.level().getGameTime(),
            Optional.of(origin(player)),
            Optional.empty()
        );
        CompendiumDiscoveryRuntime.apply(player, criterion, signal);
    }

    private static DiscoveryOrigin origin(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        return new DiscoveryOrigin(
            player.level().dimension().location().toString(),
            pos.getX() >> 4,
            pos.getZ() >> 4
        );
    }
}
