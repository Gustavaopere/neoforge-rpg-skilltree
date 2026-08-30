package dev.gustavopere.rpgskilltree.runtime.compat.coldsweat;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import java.lang.reflect.Method;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

/**
 * Narrow, fail-closed bridge for A0012 against Cold Sweat 2.4.2.
 *
 * <p>The integration deliberately uses only the public Temperature API contract:
 * {@code Temperature.add(LivingEntity, Temperature.Trait, double)} with {@code Trait.CORE}.
 * No second temperature resource is created by RPG Skill Tree.</p>
 */
public final class ColdSweatFrenzyBridge {
    public static final String SUPPORTED_VERSION_PREFIX = "2.4.2";
    private static final String TEMPERATURE_CLASS = "com.momosoftworks.coldsweat.api.util.Temperature";
    private static final String TRAIT_CLASS = "com.momosoftworks.coldsweat.api.util.Temperature$Trait";

    private static volatile Resolution resolution;

    private ColdSweatFrenzyBridge() {}

    public static boolean supportsVersion(String version) {
        return version != null && version.startsWith(SUPPORTED_VERSION_PREFIX);
    }

    public static boolean available() {
        if (!OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.COLD_SWEAT)) return false;
        if (!supportsVersion(OptionalIntegrations.version(OptionalIntegrations.Provider.COLD_SWEAT))) return false;
        return resolve().available();
    }

    /** Adds a causal amount to Cold Sweat's canonical CORE body-temperature trait. */
    public static boolean addCoreHeat(ServerPlayer player, double amount) {
        if (player == null || amount <= 0.0D || !available()) return false;
        Resolution resolved = resolve();
        try {
            resolved.add().invoke(null, player, resolved.core(), amount);
            return true;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return false;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Resolution resolve() {
        Resolution observed = resolution;
        if (observed != null) return observed;
        synchronized (ColdSweatFrenzyBridge.class) {
            if (resolution != null) return resolution;
            try {
                Class<?> temperature = Class.forName(TEMPERATURE_CLASS, false, ColdSweatFrenzyBridge.class.getClassLoader());
                Class<?> trait = Class.forName(TRAIT_CLASS, false, ColdSweatFrenzyBridge.class.getClassLoader());
                Object core = Enum.valueOf((Class<? extends Enum>) trait.asSubclass(Enum.class), "CORE");
                Method add = temperature.getMethod("add", LivingEntity.class, trait, double.class);
                resolution = new Resolution(add, core, true);
            } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
                resolution = Resolution.unavailable();
            }
            return resolution;
        }
    }

    private record Resolution(Method add, Object core, boolean available) {
        private static Resolution unavailable() {
            return new Resolution(null, null, false);
        }
    }
}
