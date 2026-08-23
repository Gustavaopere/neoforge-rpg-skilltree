package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphFactionDisposition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/** Runtime ecology hooks that are registered only when Identity 2 is loaded. */
public final class Identity2EcologyEvents {
    private Identity2EcologyEvents() {}

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
