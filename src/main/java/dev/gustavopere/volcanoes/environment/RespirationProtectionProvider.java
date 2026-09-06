package dev.gustavopere.volcanoes.environment;

import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

/**
 * Neutral optional extension point for respiration equipment/services that provide capabilities
 * beyond the core passive filter tags. The local atmosphere is provided so a later integration can
 * resolve resource-backed protection against the actual demand instead of consuming equipment in
 * already-safe air. Resource ownership/consumption remains with the integration implementing this
 * provider; Atmosphere only consumes the returned capability snapshot.
 */
@FunctionalInterface
public interface RespirationProtectionProvider {
    RespirationProtection protectionFor(LivingEntity entity, AtmosphereState atmosphereState);

    static RespirationProtectionProvider none() {
        return (entity, atmosphereState) -> {
            Objects.requireNonNull(entity, "entity");
            Objects.requireNonNull(atmosphereState, "atmosphereState");
            return RespirationProtection.NONE;
        };
    }
}
