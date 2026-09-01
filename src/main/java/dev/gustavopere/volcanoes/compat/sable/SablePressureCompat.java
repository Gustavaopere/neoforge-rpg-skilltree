package dev.gustavopere.volcanoes.compat.sable;

import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.neoforged.fml.ModList;

import java.util.Objects;

/** Exact-version gate for Sable's moving sub-level atmospheric-pressure API. */
public final class SablePressureCompat {
    public static final String MOD_ID = "sable";
    public static final String VERIFIED_ARTIFACT_VERSION = "2.0.5";
    public static final String AERONAUTICS_MOD_ID = "aeronautics_bundled";
    public static final String VERIFIED_AERONAUTICS_VERSION = "1.3.2";

    private SablePressureCompat() {
    }

    public static boolean installIfAvailable() {
        try {
            if (!ModList.get().isLoaded(MOD_ID)) {
                return false;
            }
            if (!ExactModVersionGate.isExactlyLoaded(MOD_ID, VERIFIED_ARTIFACT_VERSION)) {
                return false;
            }
            return installForState(true, true, SablePressureCompat::installVerifiedHost);
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

    /**
     * Aeronautics 1.3.2 is the verified consumer of Sable 2.0.5 in the target pack, but it does not
     * publish a generic cabin sealing/leak/flood contract. Consequently this integration never
     * synthesizes a protected enclosed environment from Aeronautics state.
     */
    public static boolean verifiedAeronauticsHasGenericCabinSealApi() {
        return false;
    }

    private static void installVerifiedHost() {
        SablePressureIntegration.install();
    }
}
