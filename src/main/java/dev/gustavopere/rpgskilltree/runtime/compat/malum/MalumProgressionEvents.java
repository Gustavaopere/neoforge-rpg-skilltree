package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import com.sammy.malum.core.systems.events.CollectSpiritEvent;
import com.sammy.malum.core.systems.events.ModifySpiritSpoilsEvent;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpiritPracticeAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

/** Optional Malum adapter using Malum's public spirit-system events. */
public final class MalumProgressionEvents {
    private MalumProgressionEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpiritSpoils(ModifySpiritSpoilsEvent event) {
        if (!(event.getAttacker() instanceof ServerPlayer player)) {
            return;
        }
        if (player instanceof FakePlayer || player.isCreative() || player.isSpectator()) {
            return;
        }

        LivingEntity target = event.getEntity();
        ResourceLocation targetId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        if (targetId == null) {
            return;
        }

        Optional<SpiritPracticeAction> action = MalumMasteryLogic.reapingAction(
            targetId,
            MalumSpiritDataReader.read(target)
        );
        action.ifPresent(spiritAction ->
            PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forMalum(spiritAction))
        );
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpiritCollected(CollectSpiritEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (player instanceof FakePlayer || player.isCreative() || player.isSpectator()) {
            return;
        }

        PlayerProgressionRuntime.awardMastery(
            player,
            MasteryPolicies.forMalum(MalumMasteryLogic.collectionAction())
        );
    }
}
