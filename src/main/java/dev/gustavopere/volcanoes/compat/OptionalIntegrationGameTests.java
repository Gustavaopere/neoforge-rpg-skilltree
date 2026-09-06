package dev.gustavopere.volcanoes.compat;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.compat.coldsweat.ColdSweatCompat;
import dev.gustavopere.volcanoes.compat.create.CreateRespirationCompat;
import dev.gustavopere.volcanoes.compat.destroy.DestroyCompat;
import dev.gustavopere.volcanoes.compat.destroy.DestroyPollutionRuntime;
import dev.gustavopere.volcanoes.compat.sable.SablePressureCompat;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Dedicated-server acceptance for fail-closed optional-host absence. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class OptionalIntegrationGameTests {
    private OptionalIntegrationGameTests() {
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void absentOptionalHostsDisableOnlyTheirAdapters(GameTestHelper helper) {
        if (!ModList.get().isLoaded(CreateRespirationCompat.MOD_ID)) {
            helper.assertTrue(!CreateRespirationCompat.installIfAvailable(),
                    "absent Create must leave the respiration adapter disabled without failing core startup");
        }
        if (!ModList.get().isLoaded(ColdSweatCompat.MOD_ID)) {
            helper.assertTrue(!ColdSweatCompat.installIfAvailable(),
                    "absent Cold Sweat must leave the heat adapter disabled without failing core startup");
        }
        if (!ModList.get().isLoaded(SablePressureCompat.MOD_ID)) {
            helper.assertTrue(!SablePressureCompat.installIfAvailable(),
                    "absent Sable must leave the vehicle-pressure adapter disabled without failing core startup");
        }
        if (!ModList.get().isLoaded(DestroyCompat.MOD_ID)) {
            helper.assertTrue(!DestroyPollutionRuntime.installIfAvailable(),
                    "absent Destroy must leave the pollution adapter disabled without failing core startup");
        }

        // Base CI intentionally omits every representative host, so all branches above are exercised there.
        // Exact/full-pack workflows may load every host; in that case present-host GameTests own activation proof.
        helper.succeed();
    }
}
