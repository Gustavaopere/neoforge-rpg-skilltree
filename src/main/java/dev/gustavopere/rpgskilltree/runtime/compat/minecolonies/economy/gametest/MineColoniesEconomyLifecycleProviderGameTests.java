package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Verifies the deferred MineColonies economy lifecycle actually installed before gameplay. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class MineColoniesEconomyLifecycleProviderGameTests {
    private static final String LIFECYCLE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyLifecycleEvents";

    private MineColoniesEconomyLifecycleProviderGameTests() {}

    @GameTest(template = "foundation_empty", timeoutTicks = 100)
    public static void lifecycleIsInstalledAfterCommonSetup(GameTestHelper helper) {
        if (!ModList.get().isLoaded("minecolonies")) {
            helper.succeed();
            return;
        }
        try {
            Class<?> lifecycle = Class.forName(LIFECYCLE);
            boolean installed = (boolean) lifecycle.getMethod("isInstalled").invoke(null);
            helper.assertTrue(installed,
                "MineColonies economy lifecycle must be installed before provider-present gameplay");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("MineColonies economy lifecycle probe failed", failure);
        }
    }
}
