package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.neoforged.fml.ModList;

import java.util.Objects;

/** Exact-version gate that keeps Destroy-linked classes unresolved unless the verified host is present. */
public final class DestroyCompat {
    public static final String MOD_ID = "destroy";
    public static final String VERIFIED_ARTIFACT_VERSION = "0.4.1";

    private DestroyCompat() {
    }

    public static boolean installIfAvailable(Runnable verifiedHostInstaller) {
        Objects.requireNonNull(verifiedHostInstaller, "verifiedHostInstaller");
        try {
            boolean loaded = ModList.get().isLoaded(MOD_ID);
            boolean exact = loaded && ExactModVersionGate.isExactlyLoaded(MOD_ID, VERIFIED_ARTIFACT_VERSION);
            return installForState(loaded, exact, verifiedHostInstaller);
        } catch (RuntimeException | LinkageError optionalIntegrationFailure) {
            return false;
        }
    }

    static boolean installForState(boolean modLoaded, boolean exactVersionLoaded, Runnable installer) {
        Objects.requireNonNull(installer, "installer");
        if (!modLoaded || !exactVersionLoaded) {
            return false;
        }
        try {
            installer.run();
            return true;
        } catch (RuntimeException | LinkageError optionalIntegrationFailure) {
            return false;
        }
    }
}
