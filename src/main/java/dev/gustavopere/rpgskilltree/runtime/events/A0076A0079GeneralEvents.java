package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatPolicy;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.MartialStanceRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Always-available fallback for RPG-owned A0076 stance defense and A0079 forced-movement
 * invalidation when the exact Epic Fight bridge is not the event owner.
 *
 * <p>The A0081-A0100 bridge already owns fallback stationary sampling in this configuration, so
 * this class deliberately invalidates only; it never takes a second sample in the same tick.</p>
 */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class A0076A0079GeneralEvents {
    private static final TagKey<DamageType> PHYSICAL_DAMAGE = TagKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "physical")
    );

    private A0076A0079GeneralEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingPhysicalDamage(LivingIncomingDamageEvent event) {
        if (epicFightBridgeOwnsEvents()
            || event.isCanceled()
            || event.getAmount() <= 0.0F
            || !(event.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || !event.getSource().is(PHYSICAL_DAMAGE)) return;

        MartialStanceRuntime.reconcile(player);
        double resistanceDelta = A0061A0080CombatPolicy.stancePhysicalResistanceDelta(
            A0061A0080RuntimeState.state().stance(A0061A0080RuntimeState.actorId(player))
        );
        if (Double.compare(resistanceDelta, 0.0D) != 0) {
            event.setAmount((float) Math.max(0.0D, event.getAmount() * (1.0D - resistanceDelta)));
        }
    }

    /** Reconcile stance and invalidate stationarity for passenger/Create/Sable forced transport. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        if (epicFightBridgeOwnsEvents()) return;
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (eligible(player)) MartialStanceRuntime.reconcile(player);
        }
    }

    @SubscribeEvent
    public static void onTeleport(EntityTeleportEvent event) {
        if (!epicFightBridgeOwnsEvents()
            && event.getEntity() instanceof ServerPlayer player
            && eligible(player)) {
            A0061A0080RuntimeState.stationary().invalidate(A0061A0080RuntimeState.actorId(player));
        }
    }

    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        if (!epicFightBridgeOwnsEvents()
            && event.getEntity() instanceof ServerPlayer player
            && eligible(player)) {
            A0061A0080RuntimeState.stationary().invalidate(A0061A0080RuntimeState.actorId(player));
        }
    }

    static boolean epicFightBridgeOwnsEvents() {
        return OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)
            && EpicFightVersionContract.supportsVersion(
                OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT)
            );
    }

    private static boolean eligible(ServerPlayer player) {
        return player != null
            && !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }
}
