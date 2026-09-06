package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.environment.RespirationProtection;
import dev.gustavopere.volcanoes.environment.RespirationThresholds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

/** Bridges the shared Pressure protection transaction into Atmosphere respiration. */
public final class PressureRespirationProtectionBridge {
    private static final RespirationThresholds THRESHOLDS = RespirationThresholds.defaults();

    private PressureRespirationProtectionBridge() {
    }

    public static RespirationProtection protectionFor(
            LivingEntity entity,
            AtmosphereState atmosphere
    ) {
        Objects.requireNonNull(atmosphere, "atmosphere");
        if (!(entity instanceof ServerPlayer player)
                || !(player.level() instanceof ServerLevel level)) {
            return RespirationProtection.NONE;
        }
        return fromSession(
                PressureNeoForgeRuntime.protectionSession(player, level.getGameTime()),
                atmosphere);
    }

    static RespirationProtection fromSession(
            ProtectionUseSession session,
            AtmosphereState atmosphere
    ) {
        Objects.requireNonNull(session, "session");
        Objects.requireNonNull(atmosphere, "atmosphere");

        boolean particulateDemand = atmosphere.particulatesMgM3() >= THRESHOLDS.particulatesMgM3()
                || atmosphere.smokeMgM3() >= THRESHOLDS.smokeMgM3();
        boolean acidDemand = atmosphere.sulfurDioxidePpm() >= THRESHOLDS.acidGasPpm();
        boolean toxicDemand = atmosphere.toxicGasPpm() >= THRESHOLDS.toxicGasPpm();
        boolean oxygenDemand = atmosphere.oxygenPartialPressureAtm()
                < THRESHOLDS.minimumOxygenPartialPressureAtm();

        double particulate = particulateDemand
                ? unit(session.activatedRating(ProtectionCapability.PARTICULATE_FILTER, 1.0))
                : 0.0;
        double acid = acidDemand
                ? unit(session.activatedRating(ProtectionCapability.ACID_GAS_FILTER, 1.0))
                : 0.0;
        double toxic = toxicDemand
                ? unit(session.activatedRating(ProtectionCapability.TOXIC_GAS_FILTER, 1.0))
                : 0.0;
        double oxygen = oxygenDemand
                ? session.activatedRating(
                        ProtectionCapability.OXYGEN_SUPPLY,
                        THRESHOLDS.minimumOxygenPartialPressureAtm())
                : 0.0;

        if (particulate <= 0.0 && acid <= 0.0 && toxic <= 0.0 && oxygen <= 0.0) {
            return RespirationProtection.NONE;
        }
        return RespirationProtection.of(particulate, acid, toxic, oxygen);
    }

    private static double unit(double rating) {
        return Math.max(0.0, Math.min(1.0, rating));
    }
}
