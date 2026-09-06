package dev.gustavopere.rpgskilltree.runtime.compat.create;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.CreateAdvancementMasteryPolicy;
import dev.gustavopere.rpgskilltree.runtime.CreateMasteryMilestoneRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

/**
 * Server-authoritative bridge from audited Create advancements to finite engineering mastery.
 *
 * <p>This class intentionally references no Create API classes. The NeoForge advancement event is
 * the semantic boundary, while the optional-provider/version checks keep unsupported Create builds
 * fail-closed before any mastery policy is evaluated.
 */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class CreateProgressionEvents {
    private CreateProgressionEvents() {}

    @SubscribeEvent
    public static void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
        if (!providerSupported()) return;
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        var id = event.getAdvancement().id();
        CreateAdvancementMasteryPolicy.confirmed(id.getNamespace(), id.getPath())
            .ifPresent(milestone -> CreateMasteryMilestoneRuntime.awardIfNew(player, milestone));
    }

    static boolean providerSupported() {
        return OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CREATE)
            && CreateVersionContract.supportsVersion(OptionalIntegrations.version(OptionalIntegrations.Provider.CREATE));
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }
}
