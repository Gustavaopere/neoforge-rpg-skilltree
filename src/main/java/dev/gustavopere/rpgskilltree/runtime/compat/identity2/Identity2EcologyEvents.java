package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphFactionDisposition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/** Runtime ecology hooks that are registered only when Identity 2 is loaded. */
public final class Identity2EcologyEvents {
    private Identity2EcologyEvents() {}

    /**
     * Makes explicit ecological alliance/fear relationships affect hostile target acquisition.
     * Hostility memory naturally overrides the alliance after the disguised player attacks an ally.
     */
    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getNewAboutToBeSetTarget() instanceof ServerPlayer player)) return;
        if (MorphIdentityAccess.currentIdentityId(player).isEmpty()) return;

        ResourceLocation observerId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
        if (observerId == null) return;
        MorphFactionDisposition disposition = MorphIdentityAccess.ecologicalDisposition(player, observerId);
        if (disposition == MorphFactionDisposition.ALLY || disposition == MorphFactionDisposition.FEAR) {
            event.setNewAboutToBeSetTarget(null);
        }
    }

    /** Breaks an allied faction disguise for the configured memory window after real damage. */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        if (MorphIdentityAccess.currentIdentityId(player).isEmpty()) return;

        ResourceLocation targetId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
        if (targetId == null) return;
        if (MorphIdentityAccess.ecologicalDisposition(player, targetId) != MorphFactionDisposition.ALLY) return;

        MorphIdentityAccess.recordHostilityAgainst(player, targetId);
    }
}
