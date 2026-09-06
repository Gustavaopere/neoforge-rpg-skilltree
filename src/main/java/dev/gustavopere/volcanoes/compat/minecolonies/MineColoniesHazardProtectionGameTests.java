package dev.gustavopere.volcanoes.compat.minecolonies;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.compat.ExactModVersionGate;
import dev.gustavopere.volcanoes.protection.ProtectedAreaVolcanicProtectionBridge;
import dev.gustavopere.volcanoes.volcano.AshDepositionWorldEffects;
import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import dev.gustavopere.volcanoes.volcano.EruptionPhase;
import dev.gustavopere.volcanoes.volcano.VolcanicProtectionService;
import dev.gustavopere.volcanoes.volcano.VolcanicTerrainWorldEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.lang.reflect.Method;
import java.util.UUID;

/** Present-host acceptance for the Stage-03 protected-area contract. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class MineColoniesHazardProtectionGameTests {
    private static final UUID VOLCANO_ID = UUID.fromString("fb50e3ae-6301-4bd8-b4b6-979eb6603a89");

    private MineColoniesHazardProtectionGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 200)
    public static void realMineColoniesClaimProtectsAshBombAndPyroclasticTerrain(GameTestHelper helper) {
        if (!ModList.get().isLoaded(MineColoniesCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        helper.assertTrue(
                ExactModVersionGate.isExactlyLoaded(
                        MineColoniesCompat.MOD_ID,
                        MineColoniesCompat.VERIFIED_ARTIFACT_VERSION),
                "present-host acceptance must run against the exact verified MineColonies version");

        ServerLevel level = helper.getLevel();
        BlockPos colonyCenter = helper.absolutePos(new BlockPos(2, 1, 2));
        Object manager = null;
        Object colony = null;

        try {
            Class<?> managerClass = Class.forName("com.minecolonies.api.colony.IColonyManager");
            manager = managerClass.getMethod("getInstance").invoke(null);
            Method createColony = managerClass.getMethod(
                    "createColony",
                    ServerLevel.class,
                    BlockPos.class,
                    Player.class,
                    String.class,
                    String.class);
            colony = createColony.invoke(
                    manager,
                    level,
                    colonyCenter,
                    FakePlayerFactory.getMinecraft(level),
                    "Volcanoes GameTest",
                    "medievaloak");
            helper.assertTrue(colony != null, "MineColonies must create a real colony for the acceptance probe");

            VolcanicProtectionService protection = new ProtectedAreaVolcanicProtectionBridge(
                    MineColoniesCompat.serviceIfAvailable());
            helper.assertTrue(
                    protection.allowsTerrainMutation(),
                    "exact verified MineColonies must establish authoritative protected-area state");
            helper.assertTrue(
                    protection.isProtected(level, colonyCenter),
                    "the newly created colony center chunk must be reported as protected");

            BlockPos distantUnclaimed = colonyCenter.offset(1024, 0, 0);
            helper.assertTrue(
                    !level.hasChunkAt(distantUnclaimed),
                    "the distant unclaimed control chunk must start unloaded");
            helper.assertTrue(
                    !protection.isProtected(level, distantUnclaimed),
                    "a distant unclaimed chunk must remain mutable; MineColonies protection cannot become world-global");
            helper.assertTrue(
                    !level.hasChunkAt(distantUnclaimed),
                    "MineColonies protection lookup must not force-load an unclaimed chunk");

            verifyAshProtection(helper, level, colonyCenter, protection);
            verifyBombProtection(helper, level, colonyCenter.east(), protection);
            verifyPyroclasticProtection(helper, level, colonyCenter.south(), protection);
        } catch (ReflectiveOperationException failure) {
            helper.fail("MineColonies real-claim acceptance could not exercise the verified public API: " + failure);
        } finally {
            deleteColonyBestEffort(manager, colony, level);
        }

        helper.succeed();
    }

    private static void verifyAshProtection(
            GameTestHelper helper,
            ServerLevel level,
            BlockPos substrate,
            VolcanicProtectionService protection
    ) {
        BlockPos target = substrate.above();
        helper.assertTrue(
                protection.isProtected(level, substrate) && protection.isProtected(level, target),
                "ash acceptance target must itself be inside the real MineColonies claim");
        level.setBlock(substrate, Blocks.DIRT.defaultBlockState(), 3);
        level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);

        AshPlumeEmission emission = new AshPlumeEmission(
                AshPlumeEmission.sourceIdFor(VOLCANO_ID),
                VOLCANO_ID,
                substrate,
                EruptionPhase.SUSTAINED,
                0.75,
                0.75,
                0.55,
                0.49,
                200L);
        int changed = AshDepositionWorldEffects.apply(
                level,
                emission,
                1,
                level.getGameTime(),
                protection);

        helper.assertTrue(changed == 0, "ash deposition must not mutate a real MineColonies claim");
        helper.assertTrue(level.getBlockState(substrate).is(Blocks.DIRT),
                "ash protection must preserve the claimed substrate");
        helper.assertTrue(level.getBlockState(target).isAir(),
                "ash protection must not place a layer inside the claim");
    }

    private static void verifyBombProtection(
            GameTestHelper helper,
            ServerLevel level,
            BlockPos target,
            VolcanicProtectionService protection
    ) {
        helper.assertTrue(
                protection.isProtected(level, target),
                "bomb acceptance target must itself be inside the real MineColonies claim");
        level.setBlock(target, Blocks.DIRT.defaultBlockState(), 3);
        int changed = VolcanicTerrainWorldEffects.applyBombImpact(level, target, 1, protection);

        helper.assertTrue(changed == 0, "volcanic bombs must not mutate a real MineColonies claim");
        helper.assertTrue(level.getBlockState(target).is(Blocks.DIRT),
                "bomb protection must preserve claimed natural terrain");
    }

    private static void verifyPyroclasticProtection(
            GameTestHelper helper,
            ServerLevel level,
            BlockPos target,
            VolcanicProtectionService protection
    ) {
        helper.assertTrue(
                protection.isProtected(level, target),
                "pyroclastic acceptance target must itself be inside the real MineColonies claim");
        level.setBlock(target, Blocks.DIRT.defaultBlockState(), 3);
        int changed = VolcanicTerrainWorldEffects.applyPyroclasticSurface(level, target, 1, protection);

        helper.assertTrue(changed == 0, "pyroclastic flows must not mutate a real MineColonies claim");
        helper.assertTrue(level.getBlockState(target).is(Blocks.DIRT),
                "pyroclastic protection must preserve claimed natural terrain");
    }

    private static void deleteColonyBestEffort(Object manager, Object colony, ServerLevel level) {
        if (manager == null || colony == null) {
            return;
        }
        try {
            int colonyId = (int) colony.getClass().getMethod("getID").invoke(colony);
            manager.getClass()
                    .getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                    .invoke(manager, colonyId, false, level);
        } catch (ReflectiveOperationException ignored) {
            // Ephemeral test-world teardown is best effort.
        }
    }
}
