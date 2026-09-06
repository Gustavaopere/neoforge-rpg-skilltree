package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

/** Dedicated-server acceptance tests for bounded physical hydrothermal ore materialization. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class HydrothermalOreGameTests {
    private HydrothermalOreGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void exactCopperDepositMaterializesFullBoundedTarget(GameTestHelper helper) {
        assertExactDepositMaterializesFullBoundedTarget(
                helper,
                GeologyResourceTags.COPPER_ORES.location(),
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE);
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void exactIronDepositMaterializesFullBoundedTarget(GameTestHelper helper) {
        assertExactDepositMaterializesFullBoundedTarget(
                helper,
                GeologyResourceTags.IRON_ORES.location(),
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE);
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void exactGoldDepositMaterializesFullBoundedTarget(GameTestHelper helper) {
        assertExactDepositMaterializesFullBoundedTarget(
                helper,
                GeologyResourceTags.GOLD_ORES.location(),
                Blocks.GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE);
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void insufficientNaturalHostsFailClosedBeforeMutation(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        GeologicalDeposit deposit = fixtureDeposit(helper, GeologyResourceTags.IRON_ORES.location());
        ChunkPos owner = new ChunkPos(deposit.center());
        HydrothermalOrePlacementPlanner.Plan plan = new HydrothermalOrePlacementPlanner()
                .plan(deposit, owner)
                .orElseThrow();

        for (BlockPos candidate : plan.candidates()) {
            level.setBlockAndUpdate(candidate, Blocks.BEDROCK.defaultBlockState());
        }
        for (int index = 0; index < plan.targetBlocks() - 1; index++) {
            level.setBlockAndUpdate(plan.candidates().get(index), Blocks.STONE.defaultBlockState());
        }
        BlockPos protectedCandidate = plan.candidates().get(plan.targetBlocks() - 1);

        HydrothermalOreWorldgenProducer producer = new HydrothermalOreWorldgenProducer();
        helper.assertTrue(producer.prepare(level, owner, deposit).isEmpty(),
                "fewer than the complete bounded target of natural hosts must prevent physical ownership");
        helper.assertTrue(level.getBlockState(protectedCandidate).is(Blocks.BEDROCK),
                "fail-closed preparation must never mutate the protected/non-host position");
        for (int index = 0; index < plan.targetBlocks() - 1; index++) {
            helper.assertTrue(level.getBlockState(plan.candidates().get(index)).is(Blocks.STONE),
                    "read-only preparation must not mutate otherwise eligible hosts");
        }
        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void hostChangeAfterPrepareAbortsBeforeAnyOreWrite(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        GeologicalDeposit deposit = fixtureDeposit(helper, GeologyResourceTags.GOLD_ORES.location());
        ChunkPos owner = new ChunkPos(deposit.center());
        HydrothermalOrePlacementPlanner.Plan plan = new HydrothermalOrePlacementPlanner()
                .plan(deposit, owner)
                .orElseThrow();
        for (BlockPos candidate : plan.candidates()) {
            level.setBlockAndUpdate(candidate, Blocks.STONE.defaultBlockState());
        }

        HydrothermalOreWorldgenProducer producer = new HydrothermalOreWorldgenProducer();
        HydrothermalOreWorldgenProducer.PreparedPlacement prepared = producer
                .prepare(level, owner, deposit)
                .orElseThrow();
        BlockPos changedHost = prepared.replacements().get(prepared.replacements().size() - 1).position();
        level.setBlockAndUpdate(changedHost, Blocks.BEDROCK.defaultBlockState());

        helper.assertTrue(!producer.apply(level, prepared),
                "host drift after preparation must abort the physical write batch");
        helper.assertTrue(level.getBlockState(changedHost).is(Blocks.BEDROCK),
                "changed non-host must remain untouched");
        for (HydrothermalOreWorldgenProducer.Replacement replacement : prepared.replacements()) {
            if (!replacement.position().equals(changedHost)) {
                helper.assertTrue(level.getBlockState(replacement.position()).is(Blocks.STONE),
                        "prevalidation must occur before any ore block is written");
            }
        }
        helper.succeed();
    }

    private static void assertExactDepositMaterializesFullBoundedTarget(
            GameTestHelper helper,
            ResourceLocation resourceTag,
            Block stoneOre,
            Block deepslateOre
    ) {
        ServerLevel level = helper.getLevel();
        GeologicalDeposit deposit = fixtureDeposit(helper, resourceTag);
        ChunkPos owner = new ChunkPos(deposit.center());
        HydrothermalOrePlacementPlanner.Plan plan = new HydrothermalOrePlacementPlanner()
                .plan(deposit, owner)
                .orElseThrow();

        for (int index = 0; index < plan.candidates().size(); index++) {
            Block host = (index & 1) == 0 ? Blocks.STONE : Blocks.DEEPSLATE;
            level.setBlockAndUpdate(plan.candidates().get(index), host.defaultBlockState());
        }

        HydrothermalOreWorldgenProducer producer = new HydrothermalOreWorldgenProducer();
        HydrothermalOreWorldgenProducer.PreparedPlacement prepared = producer
                .prepare(level, owner, deposit)
                .orElseThrow();
        helper.assertTrue(prepared.depositId().equals(deposit.persistenceId()),
                "physical placement must retain the exact geological deposit identity");
        helper.assertTrue(prepared.resourceTag().equals(resourceTag),
                "physical placement must retain the exact Stage 01 metal identity");
        helper.assertTrue(prepared.replacements().size() == plan.targetBlocks(),
                "prepared physical vein must cover the complete bounded target");
        helper.assertTrue(producer.apply(level, prepared),
                "prepared physical vein must apply after host revalidation");

        for (HydrothermalOreWorldgenProducer.Replacement replacement : prepared.replacements()) {
            Block expected = switch (replacement.expectedHost()) {
                case STONE -> stoneOre;
                case DEEPSLATE -> deepslateOre;
                case NONE -> throw new IllegalStateException("prepared replacement cannot use a NONE host");
            };
            helper.assertTrue(level.getBlockState(replacement.position()).is(expected),
                    "physical producer must materialize the exact ore family for the prepared host");
        }
        helper.succeed();
    }

    private static GeologicalDeposit fixtureDeposit(
            GameTestHelper helper,
            ResourceLocation resourceTag
    ) {
        BlockPos anchor = helper.absolutePos(new BlockPos(0, 1, 0));
        ChunkPos owner = new ChunkPos(anchor);
        BlockPos center = new BlockPos(owner.getMiddleBlockX(), anchor.getY() + 8, owner.getMiddleBlockZ());
        return new GeologicalDeposit(
                UUID.nameUUIDFromBytes((resourceTag + "|physical-fixture").getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                resourceTag,
                center,
                4.0,
                0.50,
                DepositOrigin.HYDROTHERMAL);
    }
}
