package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Exact-host diagnostic for the supported RNS KubeJS projection surface. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class RnsKubeJsOwnershipDiagnosticsGameTests {
    private static final String BRIDGE_CLASS = "com.bmaster.createrns.compat.kubejs.RNSKubeJSPluginBridge";
    private static final String ASSEMBLER_CLASS = "com.bmaster.createrns.compat.kubejs.RNSKubeJSAssembler";

    private RnsKubeJsOwnershipDiagnosticsGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 80)
    public static void exactHostKeepsNativeWorldgenWhileCustomProjectionBecomesReady(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        Class<?> bridgeClass = Class.forName(BRIDGE_CLASS);
        Method isManaging = bridgeClass.getMethod("isManagingDeposits");
        Method selectedIds = bridgeClass.getMethod("getSelectedStructureIds");

        boolean managing = (boolean) isManaging.invoke(null);
        @SuppressWarnings("unchecked")
        Set<ResourceLocation> selected = (Set<ResourceLocation>) selectedIds.invoke(null);
        Set<ResourceLocation> nativeWorldgen = configuredOverworldWorldgenIds();

        helper.assertTrue(managing,
                "RNS KubeJS bridge must report the explicit Stage 06 deposit selection");
        for (ResourceLocation depositId : RnsCompat.projectedDepositIds()) {
            helper.assertTrue(selected.contains(depositId),
                    "Volcanoes projected family must remain selected/scannable: " + depositId);
        }
        for (ResourceLocation depositId : RnsCompat.requiredNativeWorldgenDepositIds()) {
            helper.assertTrue(nativeWorldgen.contains(depositId),
                    "safe coexistence must preserve native RNS worldgen for protected family: " + depositId);
        }

        helper.assertTrue(RnsCompat.projectionReady(managing, selected, nativeWorldgen),
                "exact-host projection must become ready without globally disabling native RNS deposits");
        helper.assertTrue(
                RnsCompat.decide(true, RnsCompat.SUPPORTED_VERSION, true)
                        == RnsCompat.InstallDecision.ACTIVE,
                "exact supported host with coexistence-ready projection must activate the lifecycle bridge");

        helper.succeed();
    }

    private static Set<ResourceLocation> configuredOverworldWorldgenIds() throws Exception {
        Class<?> assemblerClass = Class.forName(ASSEMBLER_CLASS);
        Object assembler = assemblerClass.getMethod("fromCurrentEvents").invoke(null);

        Field structureSetEventField = assemblerClass.getDeclaredField("structureSetEvent");
        structureSetEventField.setAccessible(true);
        Object structureSetEvent = structureSetEventField.get(assembler);
        if (structureSetEvent == null) {
            return Set.of();
        }

        Method configuredOverworld = structureSetEvent.getClass().getDeclaredMethod("configuredOverworld");
        configuredOverworld.setAccessible(true);
        Object overworldBuilder = configuredOverworld.invoke(structureSetEvent);
        if (overworldBuilder == null) {
            return Set.of();
        }

        Method selectedStructures = overworldBuilder.getClass().getDeclaredMethod("selectedStructures");
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
    }
}
