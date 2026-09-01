package dev.gustavopere.volcanoes.compat.rns;

import com.bmaster.createrns.compat.kubejs.RNSKubeJSPluginBridge;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Exact RNS/KubeJS-linked implementation resolved only after host/version gates pass. */
final class RnsHostIntegration {
    private static final String ASSEMBLER_CLASS =
            "com.bmaster.createrns.compat.kubejs.RNSKubeJSAssembler";

    private RnsHostIntegration() {
    }

    /**
     * Verifies the exact-host coexistence contract.
     *
     * <p>Volcanoes does not replace RNS metal-family worldgen globally. RNS must keep the
     * Cu/Fe/Au structures selected and native while the lifecycle bridge contributes only custom
     * prospecting locations for already-authoritative Volcanoes hydrothermal deposits.</p>
     */
    static boolean projectionReady() {
        if (!RNSKubeJSPluginBridge.isManagingDeposits()) {
            return false;
        }
        Set<ResourceLocation> selected = RNSKubeJSPluginBridge.getSelectedStructureIds();
        Set<ResourceLocation> nativeWorldgen = selectedOverworldNativeWorldgenIds();
        return RnsCompat.projectionReady(true, selected, nativeWorldgen);
    }

    static Optional<RnsDepositLifecycleBridge> install(ServerLevel level, DepositRegistry registry) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(registry, "registry");
        if (!projectionReady()) {
            return Optional.empty();
        }

        RnsDepositLifecycleBridge bridge = new RnsDepositLifecycleBridge(
                new RnsHostDepositProjectionWriter(level));
        if (!registry.registerLifecycleSink(bridge)) {
            return Optional.empty();
        }
        return Optional.of(bridge);
    }

    /**
     * Reads the exact-version KubeJS selection before datapack-pack precedence is applied.
     *
     * <p>The public RNS bridge intentionally exposes only the scannable selection, not each
     * selection's {@code enableWorldgen} flag. Because this adapter is pinned to one exact RNS
     * artifact, it may inspect that artifact's startup builder fail-closed. This is more reliable
     * than the final StructureSet in NeoForge GameTestServer, which force-enables mutually-exclusive
     * RNS frequency packs at once.</p>
     */
    private static Set<ResourceLocation> selectedOverworldNativeWorldgenIds() {
        try {
            Class<?> assemblerClass = Class.forName(ASSEMBLER_CLASS);
            Object assembler = assemblerClass.getMethod("fromCurrentEvents").invoke(null);

            Field structureSetEventField = assemblerClass.getDeclaredField("structureSetEvent");
            structureSetEventField.setAccessible(true);
            Object structureSetEvent = structureSetEventField.get(assembler);
            if (structureSetEvent == null) {
                return Set.of();
            }

            Method configuredOverworld = structureSetEvent.getClass()
                    .getDeclaredMethod("configuredOverworld");
            configuredOverworld.setAccessible(true);
            Object overworldBuilder = configuredOverworld.invoke(structureSetEvent);
            if (overworldBuilder == null) {
                return Set.of();
            }

            Method selectedStructures = overworldBuilder.getClass()
                    .getDeclaredMethod("selectedStructures");
            selectedStructures.setAccessible(true);
            List<?> selections = (List<?>) selectedStructures.invoke(overworldBuilder);

            Set<ResourceLocation> nativeWorldgen = new LinkedHashSet<>();
            for (Object selection : selections) {
                Method id = selection.getClass().getDeclaredMethod("id");
                Method enableWorldgen = selection.getClass().getDeclaredMethod("enableWorldgen");
                id.setAccessible(true);
                enableWorldgen.setAccessible(true);
                if ((boolean) enableWorldgen.invoke(selection)) {
                    nativeWorldgen.add((ResourceLocation) id.invoke(selection));
                }
            }
            return Set.copyOf(nativeWorldgen);
        } catch (ReflectiveOperationException | ClassCastException exactHostApiMismatch) {
            throw new IllegalStateException(
                    "Unable to verify exact RNS KubeJS projection coexistence", exactHostApiMismatch);
        }
    }
}
