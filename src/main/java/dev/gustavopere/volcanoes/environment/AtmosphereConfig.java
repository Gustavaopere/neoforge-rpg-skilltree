package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.performance.PerformanceConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

/** Server-owned configuration for independent Atmosphere and performance runtime policies. */
public final class AtmosphereConfig {
    static final int MAX_ACTIVE_SOURCES = AtmosphericSourceIndex.DEFAULT_MAX_SOURCES;
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue PERSISTENCE_ENABLED;
    private static final ModConfigSpec.IntValue MAX_PERSISTED_SOURCES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("atmosphere");
        builder.push("persistence");
        PERSISTENCE_ENABLED = builder
                .comment("Persist Atmosphere sources whose source contract marks them persistent.")
                .define("enabled", true);
        MAX_PERSISTED_SOURCES = builder
                .comment("Maximum persistent Atmosphere sources stored per server level.")
                .defineInRange("maxSources", 4_096, 1, MAX_ACTIVE_SOURCES);
        builder.pop();
        builder.pop();
        PerformanceConfig.define(builder);
        SPEC = builder.build();
    }

    private AtmosphereConfig() {
    }

    public static AtmospherePersistencePolicy persistencePolicy() {
        return persistencePolicy(PERSISTENCE_ENABLED.get(), MAX_PERSISTED_SOURCES.get());
    }

    static AtmospherePersistencePolicy persistencePolicy(boolean enabled, int maxSources) {
        if (maxSources > MAX_ACTIVE_SOURCES) {
            throw new IllegalArgumentException(
                    "maxSources must not exceed active Atmosphere source capacity " + MAX_ACTIVE_SOURCES);
        }
        return new AtmospherePersistencePolicy(enabled, maxSources);
    }
}
