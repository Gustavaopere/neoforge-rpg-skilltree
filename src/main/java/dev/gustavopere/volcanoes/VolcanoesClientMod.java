package dev.gustavopere.volcanoes;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.volcanoes.tectonics.SeismicNetworking;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

/** Physical-client-only registration for rendering the packet-driven seismic camera shake. */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, value = Dist.CLIENT)
public final class VolcanoesClientMod {
    private VolcanoesClientMod() {
    }

    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        long nowNanos = System.nanoTime();
        double amplitude = SeismicNetworking.clientShakeState().amplitudeAt(nowNanos);
        if (amplitude <= 0.0) {
            return;
        }

        double seconds = nowNanos * 1.0e-9;
        float yawOffset = (float) (Math.sin(seconds * 23.0) * amplitude * 1.20);
        float pitchOffset = (float) (Math.sin(seconds * 31.0 + 0.7) * amplitude * 0.95);
        float rollOffset = (float) (Math.sin(seconds * 17.0 + 1.4) * amplitude * 1.40);

        event.setYaw(event.getYaw() + yawOffset);
        event.setPitch(event.getPitch() + pitchOffset);
        event.setRoll(event.getRoll() + rollOffset);
    }
}
