package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.GameplayXpPolicy;
import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/** Awards repeatable character XP for hostile combat while boss passive points remain first-kill-only. */
public final class CombatProgressionEvents {
    private CombatProgressionEvents() {}

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        boolean boss = BossRewardKeyResolver.isBoss(event.getEntity());
        if (!(event.getEntity() instanceof Enemy) && !boss) return;

        String entityId = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE
            .getKey(event.getEntity().getType())
            .toString();
        PlayerProgressionRuntime.applyXp(
            player,
            GameplayXpPolicy.combatKill(entityId, event.getEntity().getMaxHealth(), boss)
        );
    }
}
