package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

import alexthw.eidolon_repraised.common.tile.BrazierTileEntity;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.EidolonRitualAction;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Optional Eidolon: Repraised adapter.
 *
 * The brazier interaction is only an intent. Progression is awarded after the
 * provider's own persisted state proves that a ritual was selected and reached
 * ritualDone before the brazier completed. Failed/extinguished rituals are not
 * rewarded.
 */
public final class EidolonRitualProgressionEvents {
    public static final String RITUAL_GATEWAY_DISCOVERY = "eidolon:ritual:completed";

    private static final Map<UUID, PendingRitual> PENDING = new HashMap<>();
    private static final long MAX_TRACKING_AGE_TICKS = 20L * 60L * 20L;

    private EidolonRitualProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBrazierIgniteIntent(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        ItemStack held = player.getItemInHand(event.getHand());
        if (!(held.getItem() instanceof FlintAndSteelItem)) return;
        if (!(player.level().getBlockEntity(event.getPos()) instanceof BrazierTileEntity brazier)) return;

        CompoundTag state = snapshot(brazier, player.level());
        if (state.getBoolean("burning")) return;

        PENDING.put(player.getUUID(), new PendingRitual(
            player.level().dimension(),
            event.getPos().immutable(),
            null,
            player.level().getGameTime()
        ));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        PendingRitual pending = PENDING.get(player.getUUID());
        if (pending == null) return;

        long age = player.level().getGameTime() - pending.startedAt();
        if (!eligible(player) || age < 0L || age > MAX_TRACKING_AGE_TICKS || player.level().dimension() != pending.dimension()) {
            PENDING.remove(player.getUUID());
            return;
        }
        if (!(player.level().getBlockEntity(pending.pos()) instanceof BrazierTileEntity brazier)) {
            PENDING.remove(player.getUUID());
            return;
        }

        CompoundTag state = snapshot(brazier, player.level());
        boolean burning = state.getBoolean("burning");
        String ritualId = state.contains("ritual") ? state.getString("ritual") : "";
        boolean ritualDone = state.getBoolean("ritualDone");

        if (pending.ritualId() == null) {
            if (!ritualId.isBlank()) {
                PENDING.put(player.getUUID(), pending.withRitual(ritualId));
            } else if (!burning && age > 5L) {
                PENDING.remove(player.getUUID());
            }
            return;
        }

        if (burning) return;
        PENDING.remove(player.getUUID());
        if (!ritualDone) return;

        confirm(player, pending.ritualId());
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PENDING.remove(event.getEntity().getUUID());
    }

    private static void confirm(ServerPlayer player, String ritualId) {
        String discoveryKey = "eidolon:ritual:first/" + ritualId;
        var before = PlayerProgressionRuntime.get(player);
        boolean firstCompletion = !before.discoveries().contains(discoveryKey);

        Set<String> tags = classify(ritualId);
        EidolonRitualAction action = new EidolonRitualAction(
            new ActionOrigin("eidolon:ritual", 0),
            ritualId,
            tags,
            firstCompletion
        );
        var afterMastery = PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forEidolonRitual(action));
        var discoveries = afterMastery.discoveries();
        if (!discoveries.contains(RITUAL_GATEWAY_DISCOVERY)) {
            discoveries = discoveries.add(RITUAL_GATEWAY_DISCOVERY);
        }
        if (firstCompletion && !discoveries.contains(discoveryKey)) {
            discoveries = discoveries.add(discoveryKey);
        }
        if (discoveries != afterMastery.discoveries()) {
            PlayerProgressionRuntime.set(player, afterMastery.withDiscoveries(discoveries));
        }
    }

    private static Set<String> classify(String ritualId) {
        String path = ritualId.toLowerCase(java.util.Locale.ROOT);
        Set<String> tags = new HashSet<>();
        tags.add("confirmed_ritual");
        if (path.contains("summon")) tags.add("summoning");
        if (path.contains("purif") || path.contains("daylight") || path.contains("heal")) tags.add("holy");
        return Set.copyOf(tags);
    }

    private static CompoundTag snapshot(BrazierTileEntity brazier, Level level) {
        return brazier.saveWithoutMetadata(level.registryAccess());
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private record PendingRitual(ResourceKey<Level> dimension, BlockPos pos, String ritualId, long startedAt) {
        PendingRitual withRitual(String id) {
            return new PendingRitual(dimension, pos, id, startedAt);
        }
    }
}
