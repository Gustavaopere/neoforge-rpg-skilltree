package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/** Dedicated-server acceptance tests for Stage 05 world-facing pressure behavior. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class PressureGameTests {
    private static final UUID ENTITY_ID = UUID.fromString("515b9311-87fe-43d4-bf08-1c6d8d7f8c35");

    private PressureGameTests() {
    }

    @GameTest(template = "pressure_runtime_empty", timeoutTicks = 100)
    public static void connectedWaterDepthUsesOnlyExternallyExposedSurface(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        WaterVolumeProbe probe = worldProbe(level);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(128, 16, 20L);
        String dimensionId = level.dimension().location().toString();

        // Build the open-water fixture above the Test Level's real motion-blocking surface.
        // GameTest structures are placed near the bottom of a generated test world, so a fixed
        // relative Y can still be underneath world geometry and is not proof of external exposure.
        BlockPos openColumn = helper.absolutePos(new BlockPos(1, 1, 1));
        int exteriorBaseY = level.getHeight(
                Heightmap.Types.MOTION_BLOCKING,
                openColumn.getX(),
                openColumn.getZ());
        BlockPos openOrigin = new BlockPos(openColumn.getX(), exteriorBaseY, openColumn.getZ());
        level.setBlockAndUpdate(openOrigin, Blocks.WATER.defaultBlockState());
        level.setBlockAndUpdate(openOrigin.above(), Blocks.WATER.defaultBlockState());
        level.setBlockAndUpdate(openOrigin.above(2), Blocks.WATER.defaultBlockState());
        level.setBlockAndUpdate(openOrigin.above(3), Blocks.AIR.defaultBlockState());

        BlockPos openAir = openOrigin.above(3);
        helper.assertTrue(level.getBlockState(openAir).isAir(),
                "open-surface candidate must be air");
        helper.assertTrue(!level.getFluidState(openAir).is(FluidTags.WATER),
                "open-surface candidate must not contain water");
        helper.assertTrue(PressureNeoForgeRuntime.classifyWaterCell(level, openAir) == WaterCellKind.OPEN_AIR,
                "open air above the exterior water column must classify as external surface");

        WaterDepthSample open = lookup.sample(
                probe,
                dimensionId,
                openOrigin.getX(),
                openOrigin.getY(),
                openOrigin.getZ(),
                level.getGameTime());

        helper.assertTrue(open.surfaceResolved(),
                "open water column must resolve the externally exposed free surface");
        helper.assertTrue(Math.abs(open.depthMeters() - 3.0) < 1.0e-9,
                "open water depth must equal the vertical water column, not graph distance");

        BlockPos caveCenter = new BlockPos(4, 2, 4);
        for (int x = 3; x <= 5; x++) {
            for (int y = 1; y <= 5; y++) {
                for (int z = 3; z <= 5; z++) {
                    helper.setBlock(new BlockPos(x, y, z), Blocks.STONE);
                }
            }
        }
        helper.setBlock(caveCenter, Blocks.WATER);
        helper.setBlock(caveCenter.above(), Blocks.WATER);
        helper.setBlock(caveCenter.above(2), Blocks.AIR);

        BlockPos caveOrigin = helper.absolutePos(caveCenter);
        BlockPos caveUpperWater = helper.absolutePos(caveCenter.above());
        BlockPos caveAir = helper.absolutePos(caveCenter.above(2));
        BlockPos caveRoof = helper.absolutePos(caveCenter.above(3));

        helper.assertTrue(level.getFluidState(caveOrigin).is(FluidTags.WATER),
                "cave origin must contain water before depth sampling");
        helper.assertTrue(level.getFluidState(caveUpperWater).is(FluidTags.WATER),
                "cave upper cell must contain water before depth sampling");
        helper.assertTrue(level.getBlockState(caveAir).isAir(),
                "cave pocket must be air before depth sampling");
        helper.assertTrue(level.getBlockState(caveRoof).is(Blocks.STONE),
                "cave air pocket must have a stone roof");

        int caveMotionBlockingHeight = level.getHeight(
                Heightmap.Types.MOTION_BLOCKING,
                caveAir.getX(),
                caveAir.getZ());
        helper.assertTrue(caveMotionBlockingHeight > caveAir.getY(),
                "motion-blocking heightmap must place the roof above cave air");
        helper.assertTrue(PressureNeoForgeRuntime.classifyWaterCell(level, caveAir) == WaterCellKind.BLOCKED,
                "roofed cave air pocket must classify as blocked, not external surface");
        helper.assertTrue(probe.cellAt(dimensionId, caveOrigin.getX(), caveOrigin.getY(), caveOrigin.getZ())
                        == WaterCellKind.WATER,
                "world probe must classify cave origin as water");
        helper.assertTrue(probe.cellAt(dimensionId, caveAir.getX(), caveAir.getY(), caveAir.getZ())
                        == WaterCellKind.BLOCKED,
                "world probe must preserve the blocked cave-air classification");

        WaterDepthSample trapped = lookup.sample(
                probe,
                dimensionId,
                caveOrigin.getX(),
                caveOrigin.getY(),
                caveOrigin.getZ(),
                level.getGameTime());

        helper.assertTrue(!trapped.surfaceResolved(),
                "sealed subterranean air pocket must not be treated as external water surface");
        helper.assertTrue(Math.abs(trapped.depthMeters() - 1.0) < 1.0e-9,
                "unresolved cave water must retain only the proven vertical head");
        helper.succeed();
    }

    @GameTest(template = "pressure_runtime_empty", timeoutTicks = 100)
    public static void sealedEnvironmentIsAuthoritativeOnlyWhenHostStateIsReliable(GameTestHelper helper) {
        BlockPos subject = helper.absolutePos(new BlockPos(3, 2, 3));
        String dimensionId = helper.getLevel().dimension().location().toString();
        EnclosedEnvironmentQuery query = new EnclosedEnvironmentQuery(
                ENTITY_ID,
                Optional.empty(),
                dimensionId,
                subject.getX() + 0.5,
                subject.getY() + 0.5,
                subject.getZ() + 0.5);

        EnclosedEnvironmentProvider reliable = provider(
                100,
                EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
        EnclosedEnvironmentResolver reliableResolver = new EnclosedEnvironmentResolver(List.of(reliable), 5L, 16);
        Optional<EnclosedEnvironment> reliableInterior = reliableResolver.resolve(
                query,
                helper.getLevel().getGameTime());

        ProtectionSnapshot noProtection = new ProtectionSnapshot(Map.of());
        PressureEnvironmentResult protectedResult = PressureEnvironmentResolver.resolve(
                new PressureSample(1.0, 2.0),
                reliableInterior,
                noProtection,
                noProtection.beginUpdate());

        helper.assertTrue(protectedResult.sealedInterior(),
                "reliable dry sealed host state must replace external pressure with interior pressure");
        helper.assertTrue(Math.abs(protectedResult.experiencedPressureAtm() - 1.0) < 1.0e-9,
                "reliable sealed interior must expose its reported internal pressure");
        helper.assertTrue(protectedResult.protectedOverpressureAtm() == 0.0,
                "reliable sealed interior must prevent external hydrostatic exposure");

        EnclosedEnvironmentProvider unreliableHighPriority = provider(
                200,
                new EnclosedEnvironment(true, true, false, 1.0, Optional.empty()));
        EnclosedEnvironmentProvider lowerPrioritySafe = provider(
                100,
                EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
        EnclosedEnvironmentResolver failClosedResolver = new EnclosedEnvironmentResolver(
                List.of(lowerPrioritySafe, unreliableHighPriority),
                5L,
                16);
        Optional<EnclosedEnvironment> failClosed = failClosedResolver.resolve(
                query,
                helper.getLevel().getGameTime());

        helper.assertTrue(failClosed.isEmpty(),
                "authoritative unreliable host state must fail closed instead of falling through to weaker provider");

        PressureEnvironmentResult externalResult = PressureEnvironmentResolver.resolve(
                new PressureSample(1.0, 2.0),
                failClosed,
                noProtection,
                noProtection.beginUpdate());
        helper.assertTrue(!externalResult.sealedInterior(),
                "unreliable sealed state must leave entity exposed to outside pressure");
        helper.assertTrue(Math.abs(externalResult.experiencedPressureAtm() - 3.0) < 1.0e-9,
                "fail-closed sealed resolution must preserve physical external pressure");
        helper.assertTrue(Math.abs(externalResult.protectedOverpressureAtm() - 2.0) < 1.0e-9,
                "fail-closed sealed resolution must preserve external hydrostatic exposure");
        helper.succeed();
    }

    private static EnclosedEnvironmentProvider provider(int priority, EnclosedEnvironment environment) {
        return new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return priority;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                return Optional.of(environment);
            }
        };
    }

    private static WaterVolumeProbe worldProbe(ServerLevel level) {
        String expectedDimensionId = level.dimension().location().toString();
        return new WaterVolumeProbe() {
            @Override
            public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
                return expectedDimensionId.equals(dimensionId)
                        && level.getChunkSource().hasChunk(Math.floorDiv(blockX, 16), Math.floorDiv(blockZ, 16));
            }

            @Override
            public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
                if (!isColumnLoaded(dimensionId, blockX, blockZ)) {
                    return WaterCellKind.BLOCKED;
                }
                return PressureNeoForgeRuntime.classifyWaterCell(
                        level,
                        new BlockPos(blockX, blockY, blockZ));
            }
        };
    }
}
