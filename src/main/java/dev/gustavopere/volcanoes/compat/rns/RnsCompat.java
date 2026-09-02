package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.geology.DepositRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.fml.ModList;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Exact-version and projection-readiness policy for the optional Create: Rock & Stone adapter. */
public final class RnsCompat {
    public static final String MOD_ID = "create_rns";
    public static final String SUPPORTED_VERSION = "1.3.1-1.21.1-6";
    public static final String KUBEJS_MOD_ID = "kubejs";
    public static final String SUPPORTED_KUBEJS_VERSION = "2101.7.2-build.368";

    /**
     * RNS families for which Volcanoes can expose already-physical hydrothermal bodies as custom
     * prospecting locations. These IDs do not transfer native RNS worldgen ownership.
     */
    private static final Set<ResourceLocation> PROJECTED_DEPOSITS = Set.of(
            id("deposit_copper"),
            id("deposit_iron"),
            id("deposit_gold"));

    /**
     * RNS metal families whose native worldgen must stay enabled under the coexistence contract.
     * Cu/Fe/Au are also projected by Volcanoes; Sn/Ni/Zn/Ag remain entirely RNS-owned.
     */
    private static final Set<ResourceLocation> REQUIRED_NATIVE_WORLDGEN_DEPOSITS = Set.of(
            id("deposit_copper"),
            id("deposit_iron"),
            id("deposit_gold"),
            id("deposit_tin"),
            id("deposit_nickel"),
            id("deposit_zinc"),
            id("deposit_silver"));

    private RnsCompat() {
    }

    public static Optional<RnsDepositLifecycleBridge> installIfAvailable(
            ServerLevel level,
            DepositRegistry registry
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(registry, "registry");
        try {
            String rnsVersion = loadedVersion(MOD_ID).orElse(null);
            if (decide(rnsVersion != null, rnsVersion, false) == InstallDecision.ABSENT
                    || !Objects.equals(SUPPORTED_VERSION, rnsVersion)) {
                return Optional.empty();
            }

            String kubeJsVersion = loadedVersion(KUBEJS_MOD_ID).orElse(null);
            if (!Objects.equals(SUPPORTED_KUBEJS_VERSION, kubeJsVersion)) {
                return Optional.empty();
            }

            boolean projectionReady = RnsHostIntegration.projectionReady();
            if (decide(true, rnsVersion, projectionReady) != InstallDecision.ACTIVE) {
                return Optional.empty();
            }
            return RnsHostIntegration.install(level, registry);
        } catch (RuntimeException | LinkageError optionalHostFailure) {
            return Optional.empty();
        }
    }

    public static InstallDecision decide(boolean modLoaded, String actualVersion, boolean projectionReady) {
        if (!modLoaded) {
            return InstallDecision.ABSENT;
        }
        if (!Objects.equals(SUPPORTED_VERSION, actualVersion)) {
            return InstallDecision.VERSION_MISMATCH;
        }
        if (!projectionReady) {
            return InstallDecision.OWNERSHIP_NOT_READY;
        }
        return InstallDecision.ACTIVE;
    }

    static boolean projectionReady(
            boolean managing,
            Set<ResourceLocation> selected,
            Set<ResourceLocation> nativeWorldgen
    ) {
        Objects.requireNonNull(selected, "selected");
        Objects.requireNonNull(nativeWorldgen, "nativeWorldgen");
        return managing
                && selected.containsAll(PROJECTED_DEPOSITS)
                && nativeWorldgen.containsAll(REQUIRED_NATIVE_WORLDGEN_DEPOSITS);
    }

    static Set<ResourceLocation> projectedDepositIds() {
        return PROJECTED_DEPOSITS;
    }

    static Set<ResourceLocation> requiredNativeWorldgenDepositIds() {
        return REQUIRED_NATIVE_WORLDGEN_DEPOSITS;
    }

    private static Optional<String> loadedVersion(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(container -> container.getModInfo().getVersion().toString());
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public enum InstallDecision {
        ABSENT,
        VERSION_MISMATCH,
        OWNERSHIP_NOT_READY,
        ACTIVE
    }
}
