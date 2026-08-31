package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Present-host acceptance for the verified Destroy 0.4.1 pollution API. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class DestroyIntegrationGameTests {
    private DestroyIntegrationGameTests() {
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void exactDestroyHostBindsVerifiedPollutionApi(GameTestHelper helper) {
        if (!ModList.get().isLoaded(DestroyCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        helper.assertTrue(
                ExactModVersionGate.isExactlyLoaded(
                        DestroyCompat.MOD_ID,
                        DestroyCompat.VERIFIED_ARTIFACT_VERSION),
                "present-host acceptance must run against exact Destroy 0.4.1");
        helper.assertTrue(
                DestroyCompat.installIfAvailable(DestroyNeoForgePollutionWriter::createVerified),
                "exact Destroy host must expose the verified Volcanoes pollution API surface");
        helper.succeed();
    }
}
