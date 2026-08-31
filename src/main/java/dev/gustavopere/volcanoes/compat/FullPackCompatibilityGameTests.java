package dev.gustavopere.volcanoes.compat;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.compat.coldsweat.ColdSweatCompat;
import dev.gustavopere.volcanoes.compat.create.CreateRespirationCompat;
import dev.gustavopere.volcanoes.compat.destroy.DestroyCompat;
import dev.gustavopere.volcanoes.compat.minecolonies.MineColoniesCompat;
import dev.gustavopere.volcanoes.compat.rns.RnsCompat;
import dev.gustavopere.volcanoes.compat.sable.SablePressureCompat;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Fail-closed sentinel used only by the Stage 07 full-pack acceptance workflow. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class FullPackCompatibilityGameTests {
    private static final String ENABLE_ENV = "VOLCANOES_FULL_PACK_ACCEPTANCE";

    private FullPackCompatibilityGameTests() {
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void fullPackRequiresEveryVerifiedIntegrationHost(GameTestHelper helper) {
        if (!Boolean.parseBoolean(System.getenv(ENABLE_ENV))) {
            helper.succeed();
            return;
        }

        assertExact(helper, CreateRespirationCompat.MOD_ID, CreateRespirationCompat.VERIFIED_ARTIFACT_VERSION);
        assertExact(helper, SablePressureCompat.MOD_ID, SablePressureCompat.VERIFIED_ARTIFACT_VERSION);
        assertExact(helper, SablePressureCompat.AERONAUTICS_MOD_ID, SablePressureCompat.VERIFIED_AERONAUTICS_VERSION);
        assertExact(helper, DestroyCompat.MOD_ID, DestroyCompat.VERIFIED_ARTIFACT_VERSION);
        assertExact(helper, "petrolpark", "1.5.0");
        assertExact(helper, "jei", "19.39.0.371");
        assertExact(helper, ColdSweatCompat.MOD_ID, ColdSweatCompat.VERIFIED_ARTIFACT_VERSION);
        assertExact(helper, RnsCompat.MOD_ID, RnsCompat.SUPPORTED_VERSION);
        assertExact(helper, RnsCompat.KUBEJS_MOD_ID, RnsCompat.SUPPORTED_KUBEJS_VERSION);
        assertExact(helper, MineColoniesCompat.MOD_ID, MineColoniesCompat.VERIFIED_ARTIFACT_VERSION);
        helper.succeed();
    }

    private static void assertExact(GameTestHelper helper, String modId, String version) {
        helper.assertTrue(
                ExactModVersionGate.isExactlyLoaded(modId, version),
                "full-pack acceptance requires exact " + modId + " version " + version);
    }
}
