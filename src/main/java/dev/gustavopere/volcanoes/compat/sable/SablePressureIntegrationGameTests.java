package dev.gustavopere.volcanoes.compat.sable;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Present-host acceptance for the verified Sable moving-sublevel pressure adapter. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class SablePressureIntegrationGameTests {
    private SablePressureIntegrationGameTests() {
    }

    @GameTest(template = "pressure_runtime_empty", timeoutTicks = 40)
    public static void exactSableHostInstallsPressureAdapter(GameTestHelper helper) {
        if (!ModList.get().isLoaded(SablePressureCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        helper.assertTrue(
                ExactModVersionGate.isExactlyLoaded(
                        SablePressureCompat.MOD_ID,
                        SablePressureCompat.VERIFIED_ARTIFACT_VERSION),
                "present-host acceptance must run against exact Sable 2.0.5");
        if (ModList.get().isLoaded(SablePressureCompat.AERONAUTICS_MOD_ID)) {
            helper.assertTrue(
                    ExactModVersionGate.isExactlyLoaded(
                            SablePressureCompat.AERONAUTICS_MOD_ID,
                            SablePressureCompat.VERIFIED_AERONAUTICS_VERSION),
                    "present Aeronautics host must match the verified 1.3.1 contract");
        }
        helper.assertTrue(SablePressureCompat.installIfAvailable(),
                "exact Sable host must install the Volcanoes moving-sublevel pressure adapter");
        helper.succeed();
    }
}
