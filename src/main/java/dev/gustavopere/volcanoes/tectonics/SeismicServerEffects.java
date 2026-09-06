package dev.gustavopere.volcanoes.tectonics;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Objects;

/** Applies non-destructive entity-facing consequences for one seismic event on the logical server. */
public final class SeismicServerEffects {
    private SeismicServerEffects() {
    }

    public static int apply(ServerLevel level, SeismicEvent event) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(event, "event");

        int affectedPlayers = 0;
        for (ServerPlayer player : level.players()) {
            SeismicEntityEffectProfile profile = SeismicEntityEffectProfile.at(
                    event,
                    player.getX(),
                    player.getZ());
            if (profile.intensity() <= 0.0) {
                continue;
            }

            PacketDistributor.sendToPlayer(
                    player,
                    new SeismicShakePayload(
                            (float) profile.shakeAmplitude(),
                            profile.movementInstabilityTicks()));

            player.playNotifySound(
                    SoundEvents.WARDEN_SONIC_BOOM,
                    SoundSource.AMBIENT,
                    (float) profile.soundVolume(),
                    (float) (0.72 + profile.intensity() * 0.18));

            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    profile.movementInstabilityTicks(),
                    0,
                    false,
                    false,
                    false));

            double angle = deterministicAngle(player, event);
            double horizontalImpulse = Math.min(0.12, profile.intensity() * 0.10);
            player.push(
                    Math.cos(angle) * horizontalImpulse,
                    0.0,
                    Math.sin(angle) * horizontalImpulse);
            affectedPlayers++;
        }
        return affectedPlayers;
    }

    private static double deterministicAngle(ServerPlayer player, SeismicEvent event) {
        long mixed = player.getUUID().getMostSignificantBits()
                ^ player.getUUID().getLeastSignificantBits()
                ^ Double.doubleToLongBits(event.epicenterX())
                ^ Long.rotateLeft(Double.doubleToLongBits(event.epicenterZ()), 19);
        mixed ^= mixed >>> 33;
        mixed *= 0xff51afd7ed558ccdL;
        mixed ^= mixed >>> 33;
        double unit = (mixed >>> 11) * 0x1.0p-53;
        return unit * Math.PI * 2.0;
    }
}
