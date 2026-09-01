package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HydrothermalOrePlacementPlannerTest {
    private final HydrothermalOrePlacementPlanner planner = new HydrothermalOrePlacementPlanner();

    @Test
    void exactHydrothermalMetalProducesDeterministicBoundedOwnerChunkCandidates() {
        GeologicalDeposit deposit = deposit(
                UUID.fromString("f18fdcca-aa3d-438a-85f1-2bf12644fceb"),
                GeologyResourceTags.COPPER_ORES.location(),
                12.0,
                0.50,
                DepositOrigin.HYDROTHERMAL);
        ChunkPos owner = new ChunkPos(0, 0);

        var first = planner.plan(deposit, owner).orElseThrow();
        var second = planner.plan(deposit, owner).orElseThrow();

        assertEquals(first, second, "same deposit identity must produce the same physical plan");
        assertEquals(12, first.targetBlocks(),
                "physical vein budget is deposit diameter scaled by canonical richness");
        assertTrue(first.candidates().size() >= first.targetBlocks());
        assertTrue(first.candidates().size() <= HydrothermalOrePlacementPlanner.MAX_PROBES_PER_DEPOSIT);
        assertEquals(first.candidates().size(), first.candidates().stream().distinct().count(),
                "candidate probes must not duplicate positions");
        for (BlockPos candidate : first.candidates()) {
            assertEquals(owner, new ChunkPos(candidate), "physical producer must never write another chunk");
            assertTrue(candidate.distSqr(deposit.center()) <= deposit.radius() * deposit.radius(),
                    "candidate must stay inside geological deposit volume");
        }
    }

    @Test
    void stableDepositIdentityParticipatesInCandidateSelection() {
        GeologicalDeposit firstDeposit = deposit(
                UUID.fromString("dc793138-e7c0-4c91-940e-a5624507624d"),
                GeologyResourceTags.IRON_ORES.location(),
                8.0,
                0.75,
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit secondDeposit = deposit(
                UUID.fromString("a1aa4c15-9798-4d40-9595-a2fedf579176"),
                GeologyResourceTags.IRON_ORES.location(),
                8.0,
                0.75,
                DepositOrigin.HYDROTHERMAL);

        List<BlockPos> first = planner.plan(firstDeposit, new ChunkPos(0, 0)).orElseThrow().candidates();
        List<BlockPos> second = planner.plan(secondDeposit, new ChunkPos(0, 0)).orElseThrow().candidates();

        assertNotEquals(first, second, "different stable deposits must not share an identical vein layout");
    }

    @Test
    void onlyExactStage01HydrothermalMetalsArePhysicallyOwned() {
        ChunkPos owner = new ChunkPos(0, 0);
        assertTrue(planner.plan(deposit(UUID.randomUUID(), GeologyResourceTags.COPPER_ORES.location(), 8.0, 0.5,
                DepositOrigin.HYDROTHERMAL), owner).isPresent());
        assertTrue(planner.plan(deposit(UUID.randomUUID(), GeologyResourceTags.IRON_ORES.location(), 8.0, 0.5,
                DepositOrigin.HYDROTHERMAL), owner).isPresent());
        assertTrue(planner.plan(deposit(UUID.randomUUID(), GeologyResourceTags.GOLD_ORES.location(), 8.0, 0.5,
                DepositOrigin.HYDROTHERMAL), owner).isPresent());

        assertFalse(planner.plan(deposit(UUID.randomUUID(), GeologyResourceTags.MINERAL_RESOURCES.location(), 8.0, 0.5,
                DepositOrigin.HYDROTHERMAL), owner).isPresent(),
                "generic mineral metadata must never imply a physical metal owner");
        assertFalse(planner.plan(deposit(UUID.randomUUID(), GeologyResourceTags.COPPER_ORES.location(), 8.0, 0.5,
                DepositOrigin.MAGMATIC), owner).isPresent(),
                "this producer owns hydrothermal deposits only");
        assertFalse(planner.plan(deposit(UUID.randomUUID(), GeologyResourceTags.COPPER_ORES.location(), 8.0, 0.0,
                DepositOrigin.HYDROTHERMAL), owner).isPresent(),
                "zero-richness metadata cannot claim physical ownership");
    }

    private static GeologicalDeposit deposit(
            UUID id,
            net.minecraft.resources.ResourceLocation resourceTag,
            double radius,
            double richness,
            DepositOrigin origin
    ) {
        return new GeologicalDeposit(
                id,
                resourceTag,
                new BlockPos(8, 48, 8),
                radius,
                richness,
                origin);
    }
}
