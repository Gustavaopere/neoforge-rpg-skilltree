package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

/** Dedicated-server acceptance tests for bounded volcanic hazard world effects. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class VolcanicHazardGameTests {
    private static final UUID VOLCANO_ID = UUID.fromString("5fd2c7c1-f41e-4dc5-9b65-d837c416d187");

    private VolcanicHazardGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void ashDepositsOneBoundedLayerOnTaggedLoadedTerrain(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos fixtureColumn = helper.absolutePos(new BlockPos(1, 1, 1));
        int originalTopY = level.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                fixtureColumn.getX(),
                fixtureColumn.getZ());
        BlockPos substrate = new BlockPos(fixtureColumn.getX(), originalTopY, fixtureColumn.getZ());
        BlockPos target = substrate.above();
        level.setBlock(substrate, Blocks.DIRT.defaultBlockState(), 3);
        level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);

        AshPlumeEmission emission = new AshPlumeEmission(
                AshPlumeEmission.sourceIdFor(VOLCANO_ID),
                VOLCANO_ID,
                target,
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
                VolcanicProtectionService.none());

        helper.assertTrue(changed == 1, "one ash block token must change at most and exactly one eligible column");
        helper.assertTrue(
                level.getBlockState(target).is(VolcanoBlocks.ASH_LAYER.get()),
                "tagged loaded terrain must receive the registered ash layer");
        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void bombTerrainInteractionIsBoundedAndRequiresProtectionAuthority(GameTestHelper helper) {
        BlockPos firstRelative = new BlockPos(1, 1, 1);
        BlockPos secondRelative = new BlockPos(2, 1, 1);
        helper.setBlock(firstRelative, Blocks.DIRT);
        helper.setBlock(secondRelative, Blocks.DIRT);

        BlockPos first = helper.absolutePos(firstRelative);
        int denied = VolcanicTerrainWorldEffects.applyBombImpact(
                helper.getLevel(),
                first,
                1,
                VolcanicProtectionService.none());
        helper.assertTrue(denied == 0, "missing protection authority must fail closed");
        helper.assertTrue(helper.getBlockState(firstRelative).is(Blocks.DIRT),
                "fail-closed bomb terrain interaction must preserve natural terrain");

        int changed = VolcanicTerrainWorldEffects.applyBombImpact(
                helper.getLevel(),
                first,
                1,
                authoritativeProtection());

        helper.assertTrue(changed == 1, "one bomb terrain token must mutate exactly one eligible block");
        helper.assertTrue(helper.getBlockState(firstRelative).isAir(),
                "authoritative bounded bomb impact may crater one natural block");
        helper.assertTrue(helper.getBlockState(secondRelative).is(Blocks.DIRT),
                "one bomb terrain token must not spill into adjacent natural terrain");
        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void pyroclasticTerrainInteractionIsBoundedAndRequiresProtectionAuthority(GameTestHelper helper) {
        BlockPos firstRelative = new BlockPos(1, 1, 1);
        BlockPos secondRelative = new BlockPos(2, 1, 1);
        helper.setBlock(firstRelative, Blocks.DIRT);
        helper.setBlock(secondRelative, Blocks.DIRT);

        BlockPos first = helper.absolutePos(firstRelative);
        int denied = VolcanicTerrainWorldEffects.applyPyroclasticSurface(
                helper.getLevel(),
                first,
                1,
                VolcanicProtectionService.none());
        helper.assertTrue(denied == 0, "missing protection authority must fail closed for flow terrain work");
        helper.assertTrue(helper.getBlockState(firstRelative).is(Blocks.DIRT),
                "fail-closed pyroclastic terrain interaction must preserve natural terrain");

        int changed = VolcanicTerrainWorldEffects.applyPyroclasticSurface(
                helper.getLevel(),
                first,
                1,
                authoritativeProtection());

        helper.assertTrue(changed == 1, "one flow terrain token must mutate exactly one eligible block");
        helper.assertTrue(helper.getBlockState(firstRelative).is(Blocks.COARSE_DIRT),
                "bounded pyroclastic terrain interaction must scorch soil instead of recursively destroying it");
        helper.assertTrue(helper.getBlockState(secondRelative).is(Blocks.DIRT),
                "one flow terrain token must not spill into adjacent natural terrain");
        helper.succeed();
    }

    private static VolcanicProtectionService authoritativeProtection() {
        return new VolcanicProtectionService() {
            @Override
            public boolean isProtected(ServerLevel level, BlockPos pos) {
                return false;
            }

            @Override
            public boolean allowsTerrainMutation() {
                return true;
            }
        };
    }
}
