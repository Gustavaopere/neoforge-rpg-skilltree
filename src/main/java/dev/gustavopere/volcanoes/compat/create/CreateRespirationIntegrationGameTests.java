package dev.gustavopere.volcanoes.compat.create;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Present-host acceptance for the verified Create respiration adapter. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class CreateRespirationIntegrationGameTests {
    private CreateRespirationIntegrationGameTests() {
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void exactCreateHostInstallsRespirationAdapter(GameTestHelper helper) {
        if (!ModList.get().isLoaded(CreateRespirationCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        helper.assertTrue(
                ExactModVersionGate.isExactlyLoaded(
                        CreateRespirationCompat.MOD_ID,
                        CreateRespirationCompat.VERIFIED_ARTIFACT_VERSION),
                "present-host acceptance must run against exact Create 6.0.10");
        helper.assertTrue(CreateRespirationCompat.installIfAvailable(),
                "exact Create host must install the Volcanoes respiration adapter");
        helper.succeed();
    }
}
