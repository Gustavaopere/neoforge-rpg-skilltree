package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.BossIdentity;
import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class BossProgressionEvents {
    private BossProgressionEvents() {}

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!BossRewardKeyResolver.isBoss(event.getEntity())) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        BossIdentity identity = BossRewardKeyResolver.identity(event.getEntity());
        PlayerProgressionRuntime.creditBoss(
            player,
            identity,
            BossRewardKeyResolver.rewardDefinition(identity)
        );
    }
}
