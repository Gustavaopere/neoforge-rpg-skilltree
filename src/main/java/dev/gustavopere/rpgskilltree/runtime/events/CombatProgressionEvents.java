package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.AntiFarmDecision;
import dev.gustavopere.rpgskilltree.core.GameplaySemanticXpPolicy;
import dev.gustavopere.rpgskilltree.core.SemanticAction;
import dev.gustavopere.rpgskilltree.core.SemanticActionAuthorship;
import dev.gustavopere.rpgskilltree.core.SemanticActionContext;
import dev.gustavopere.rpgskilltree.core.SemanticActionType;
import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import dev.gustavopere.rpgskilltree.runtime.GameplaySemanticXpRuntime;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/** Awards repeatable semantic XP for hostile combat while boss first-kill rewards remain separate. */
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
        SemanticActionType actionType = boss
            ? SemanticActionType.BOSS_DEFEATED
            : SemanticActionType.HOSTILE_KILLED;
        SemanticAction action = new SemanticAction(
            actionType,
            entityId,
            new ActionOrigin("rpgskilltree:neoforge/living_death", 0),
            SemanticActionAuthorship.DIRECT_PLAYER,
            new SemanticActionContext(
                OptionalLong.empty(),
                Map.of(
                    GameplaySemanticXpPolicy.MAX_HEALTH_METRIC,
                    (double) event.getEntity().getMaxHealth()
                ),
                Set.of()
            )
        );

        GameplaySemanticXpRuntime.apply(
            player,
            action,
            ignored -> AntiFarmDecision.allow(),
            GameplaySemanticXpPolicy.INSTANCE
        );
    }
}
