package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

/** Client-only lifecycle hooks for state that must not survive a server/world connection. */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, value = Dist.CLIENT)
public final class AtmosphereClientEvents {
    private AtmosphereClientEvents() {
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        AtmosphereNetworking.clientState().reset();
    }
}
