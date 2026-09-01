package dev.gustavopere.volcanoes.compat.minecolonies;

import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import dev.gustavopere.volcanoes.protection.ProtectedAreaService;
import net.neoforged.fml.ModList;

import java.util.Objects;
import java.util.function.Supplier;

/** Optional MineColonies bootstrap that never resolves host classes while the mod is absent or unverified. */
public final class MineColoniesCompat {
    public static final String MOD_ID = "minecolonies";
    public static final String VERIFIED_ARTIFACT_VERSION = "1.1.1375-1.21.1-snapshot";

    private MineColoniesCompat() {
    }

    public static ProtectedAreaService serviceIfAvailable() {
        try {
            boolean modLoaded = ModList.get().isLoaded(MOD_ID);
            boolean exactVersionLoaded = modLoaded
                    && ExactModVersionGate.isExactlyLoaded(MOD_ID, VERIFIED_ARTIFACT_VERSION);
            return serviceForState(modLoaded, exactVersionLoaded, MineColoniesCompat::createProvider);
        } catch (RuntimeException | LinkageError failure) {
            return ProtectedAreaService.empty();
        }
    }

    static ProtectedAreaService serviceForState(
            boolean modLoaded,
            boolean exactVersionLoaded,
            Supplier<ProtectedAreaService.Provider> factory
    ) {
        Objects.requireNonNull(factory, "factory");

        if (!modLoaded) {
            return ProtectedAreaService.authoritative();
        }
        if (!exactVersionLoaded) {
            return ProtectedAreaService.empty();
        }

        try {
            return ProtectedAreaService.authoritative(
                    Objects.requireNonNull(factory.get(), "provider"));
        } catch (RuntimeException | LinkageError failure) {
            return ProtectedAreaService.empty();
        }
    }

    private static ProtectedAreaService.Provider createProvider() {
        return new MineColoniesProtectedAreaProvider();
    }
}
