package dev.gustavopere.volcanoes.compat;

import dev.gustavopere.volcanoes.compat.coldsweat.ColdSweatCompat;
import dev.gustavopere.volcanoes.compat.create.CreateRespirationCompat;
import dev.gustavopere.volcanoes.compat.curios.CuriosEquipmentCompat;
import dev.gustavopere.volcanoes.compat.destroy.DestroyPollutionRuntime;
import dev.gustavopere.volcanoes.compat.minecolonies.MineColoniesCompat;
import dev.gustavopere.volcanoes.compat.rns.RnsIntegrationRuntime;
import dev.gustavopere.volcanoes.compat.sable.SablePressureCompat;
import dev.gustavopere.volcanoes.environment.AtmosphereRuntime;
import dev.gustavopere.volcanoes.pressure.CanonicalRespirationProtectionAdapter;
import dev.gustavopere.volcanoes.pressure.PressureNeoForgeRuntime;
import dev.gustavopere.volcanoes.pressure.PressureRespirationProtectionBridge;
import dev.gustavopere.volcanoes.protection.ProtectedAreaVolcanicProtectionBridge;
import dev.gustavopere.volcanoes.volcano.VolcanicHazardWorldRuntime;

/** Installs optional integrations without making any host mod a runtime dependency of Volcanoes. */
public final class OptionalIntegrationBootstrap {
    private OptionalIntegrationBootstrap() {
    }

    public static void install() {
        VolcanicHazardWorldRuntime.setProtectionService(
                new ProtectedAreaVolcanicProtectionBridge(MineColoniesCompat.serviceIfAvailable()));
        PressureNeoForgeRuntime.registerEquipmentProtectionAdapter(
                CanonicalRespirationProtectionAdapter.create());
        AtmosphereRuntime.installRespirationProtectionProvider(
                PressureRespirationProtectionBridge::protectionFor);
        ColdSweatCompat.installIfAvailable();
        CuriosEquipmentCompat.installIfAvailable();
        CreateRespirationCompat.installIfAvailable();
        SablePressureCompat.installIfAvailable();
        DestroyPollutionRuntime.installIfAvailable();
        RnsIntegrationRuntime.register();
    }
}
