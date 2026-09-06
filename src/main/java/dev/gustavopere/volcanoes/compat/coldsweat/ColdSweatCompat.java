package dev.gustavopere.volcanoes.compat.coldsweat;

import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.neoforged.fml.ModList;

import java.util.Objects;

/** Host-neutral exact-version gate for the optional Cold Sweat integration. */
public final class ColdSweatCompat {
    public static final String MOD_ID = "cold_sweat";
    public static final String VERIFIED_ARTIFACT_VERSION = "2.4.2";

    private ColdSweatCompat() {
    }

    public static boolean installIfAvailable() {
        try {
            boolean modLoaded = ModList.get().isLoaded(MOD_ID);
            if (!modLoaded) {
                return false;
            }
            boolean exactVersionLoaded =
                    ExactModVersionGate.isExactlyLoaded(MOD_ID, VERIFIED_ARTIFACT_VERSION);
            if (!exactVersionLoaded) {
                return false;
            }
            // Indirect through a host-neutral method so the Cold Sweat-linked class is never
            // resolved while the host is absent or version-mismatched.
            return installForState(true, true, ColdSweatCompat::installVerifiedHost);
        } catch (RuntimeException | LinkageError optionalIntegrationFailure) {
            return false;
        }
    }

    static boolean installForState(
            boolean modLoaded,
            boolean exactVersionLoaded,
            Runnable installer
    ) {
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

    private static void installVerifiedHost() {
        ColdSweatIntegration.install();
    }
}
