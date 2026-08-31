package dev.gustavopere.volcanoes;

import dev.gustavopere.volcanoes.compat.OptionalIntegrationBootstrap;
import dev.gustavopere.volcanoes.environment.AtmosphereConfig;
import dev.gustavopere.volcanoes.environment.AtmosphereNetworking;
import dev.gustavopere.volcanoes.environment.AtmosphereRuntime;
import dev.gustavopere.volcanoes.environment.VolcanicGasAtmosphereRuntime;
import dev.gustavopere.volcanoes.environment.VolcanicPollutionRuntime;
import dev.gustavopere.volcanoes.pressure.PressureNeoForgeRuntime;
import dev.gustavopere.volcanoes.tectonics.SeismicNetworking;
import dev.gustavopere.volcanoes.tectonics.TectonicRuntime;
import dev.gustavopere.volcanoes.volcano.GeothermalWorldgenRuntime;
import dev.gustavopere.volcanoes.volcano.VolcanicHazardWorldRuntime;
import dev.gustavopere.volcanoes.volcano.VolcanoAdminCommands;
import dev.gustavopere.volcanoes.volcano.VolcanoAttachments;
import dev.gustavopere.volcanoes.volcano.VolcanoBlocks;
import dev.gustavopere.volcanoes.volcano.VolcanoItems;
import dev.gustavopere.volcanoes.volcano.VolcanoLifecycleRuntime;
import dev.gustavopere.volcanoes.volcano.VolcanoWorldgenRegistry;
import dev.gustavopere.volcanoes.volcano.VolcanoWorldgenRuntime;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Native Volcanoes subsystem bootstrap for the single {@code rpgskilltree} mod.
 *
 * <p>{@link #MOD_ID} intentionally remains {@code volcanoes}: it is a stable data/resource namespace,
 * not a second NeoForge mod id. Existing worlds, registries, datapacks, networking identifiers and
 * integrations may already persist this namespace.</p>
 */
public final class VolcanoesMod {
    public static final String MOD_ID = "volcanoes";
    public static final String MINECRAFT_LINE = "1.21.1";

    private static boolean initialized;

    private VolcanoesMod() {
    }

    public static void initialize(IEventBus modBus, ModContainer container) {
        if (initialized) {
            throw new IllegalStateException("Volcanoes subsystem initialized more than once");
        }
        initialized = true;

        // Preserve the standalone configuration filename even though the owning ModContainer is now
        // rpgskilltree. Existing installations can therefore keep volcanoes-server.toml unchanged.
        container.registerConfig(ModConfig.Type.SERVER, AtmosphereConfig.SPEC, "volcanoes-server.toml");
        VolcanoBlocks.register(modBus);
        VolcanoItems.register(modBus);
        VolcanoAttachments.register(modBus);
        VolcanoWorldgenRegistry.register(modBus);
        modBus.addListener(SeismicNetworking::register);
        modBus.addListener(AtmosphereNetworking::register);
        VolcanoLifecycleRuntime.registerSeismicBridge();
        VolcanicHazardWorldRuntime.register();
        AtmosphereRuntime.registerAshBridge();
        VolcanicGasAtmosphereRuntime.register();
        VolcanicPollutionRuntime.register();
        OptionalIntegrationBootstrap.install();
        NeoForge.EVENT_BUS.addListener(VolcanoAdminCommands::register);
        NeoForge.EVENT_BUS.addListener(TectonicRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(VolcanoWorldgenRuntime::onChunkLoad);
        NeoForge.EVENT_BUS.addListener(VolcanoWorldgenRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(GeothermalWorldgenRuntime::onChunkLoad);
        NeoForge.EVENT_BUS.addListener(GeothermalWorldgenRuntime::onChunkUnload);
        NeoForge.EVENT_BUS.addListener(GeothermalWorldgenRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(VolcanoLifecycleRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(VolcanicHazardWorldRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onPlayerLoggedOut);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onPlayerClone);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onPlayerChangedDimension);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onChunkLoad);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onChunkUnload);
        NeoForge.EVENT_BUS.addListener(PressureNeoForgeRuntime::onLevelUnload);
        // Create's native diving listener runs at NORMAL priority. Atmosphere runs afterward so the
        // native listener observes vanilla above-water breathing before Atmosphere applies hypoxia,
        // preventing native Create and the shared protection transaction from both debiting air.
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, AtmosphereRuntime::onLivingBreathe);
        NeoForge.EVENT_BUS.addListener(AtmosphereRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(AtmosphereRuntime::onLevelUnload);
        NeoForge.EVENT_BUS.addListener(AtmosphereRuntime::onPlayerLoggedOut);
        NeoForge.EVENT_BUS.addListener(VolcanicGasAtmosphereRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(VolcanicGasAtmosphereRuntime::onLevelUnload);
        NeoForge.EVENT_BUS.addListener(VolcanicPollutionRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(VolcanicPollutionRuntime::onLevelUnload);
    }
}
