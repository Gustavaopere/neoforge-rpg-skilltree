package dev.gustavopere.rpgskilltree.runtime.compat.create;

import dev.gustavopere.rpgskilltree.core.CreateAdvancementMasteryPolicy;
import dev.gustavopere.rpgskilltree.runtime.CreateMasteryMilestoneRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

/** Server-authoritative bridge from audited Create advancements to finite engineering mastery. */
public final class CreateProgressionEvents {
    private CreateProgressionEvents() {}

    @SubscribeEvent
    public static void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        var id = event.getAdvancement().id();
        CreateAdvancementMasteryPolicy.confirmed(id.getNamespace(), id.getPath())
            .ifPresent(milestone -> CreateMasteryMilestoneRuntime.awardIfNew(player, milestone));
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }
}
