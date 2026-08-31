package dev.gustavopere.volcanoes.compat.create;

import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.neoforged.fml.ModList;

import java.util.Objects;

/** Host-neutral exact-version gate for the optional Create respiration integration. */
public final class CreateRespirationCompat {
    public static final String MOD_ID = "create";
    public static final String VERIFIED_ARTIFACT_VERSION = "6.0.10";

    private CreateRespirationCompat() {
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
            return installForState(true, true, CreateRespirationCompat::installVerifiedHost);
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
        CreateRespirationIntegration.install();
    }
}
