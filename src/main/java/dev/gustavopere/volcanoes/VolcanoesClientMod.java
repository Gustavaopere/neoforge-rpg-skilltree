package dev.gustavopere.volcanoes;

import dev.gustavopere.volcanoes.tectonics.SeismicNetworking;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Physical-client-only registration for rendering the packet-driven seismic camera shake. */
@Mod(value = VolcanoesMod.MOD_ID, dist = Dist.CLIENT)
public final class VolcanoesClientMod {
    public VolcanoesClientMod(IEventBus modBus) {
        NeoForge.EVENT_BUS.addListener(VolcanoesClientMod::onCameraAngles);
    }

    private static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
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
