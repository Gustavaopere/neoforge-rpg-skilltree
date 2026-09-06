package dev.gustavopere.volcanoes.compat.curios;

import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.neoforged.fml.ModList;

import java.util.Objects;

/** Host-neutral exact-version gate for optional Curios equipment discovery. */
public final class CuriosEquipmentCompat {
    public static final String MOD_ID = "curios";
    public static final String VERIFIED_ARTIFACT_VERSION = "9.5.1+1.21.1";

    private CuriosEquipmentCompat() {
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
            return installForState(true, true, CuriosEquipmentCompat::installVerifiedHost);
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
        CuriosEquipmentIntegration.install();
    }
}
