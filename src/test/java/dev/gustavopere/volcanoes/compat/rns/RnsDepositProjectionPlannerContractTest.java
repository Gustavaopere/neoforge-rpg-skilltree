package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RnsDepositProjectionPlannerContractTest {
    private static final ResourceLocation COPPER = id("c", "ores/copper");
    private static final ResourceLocation IRON = id("c", "ores/iron");
    private static final ResourceLocation GOLD = id("c", "ores/gold");
    private static final ResourceLocation TIN = id("c", "ores/tin");
    private static final ResourceLocation NICKEL = id("c", "ores/nickel");
    private static final ResourceLocation ZINC = id("c", "ores/zinc");
    private static final ResourceLocation SILVER = id("c", "ores/silver");

    @Test
    void mapsOnlyPhysicallyProducedVolcanoesMetalFamiliesAndRejectsUnsupportedOrNonVolcanicDeposits() {
        GeologicalDeposit copper = deposit(1, COPPER, new BlockPos(16, 40, 16), DepositOrigin.MAGMATIC);
        GeologicalDeposit iron = deposit(2, IRON, new BlockPos(48, 20, 16), DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit gold = deposit(3, GOLD, new BlockPos(80, 20, 16), DepositOrigin.MAGMATIC);
        GeologicalDeposit tin = deposit(4, TIN, new BlockPos(112, 20, 16), DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit nickel = deposit(5, NICKEL, new BlockPos(144, 20, 16), DepositOrigin.MAGMATIC);
        GeologicalDeposit zinc = deposit(6, ZINC, new BlockPos(176, 20, 16), DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit silver = deposit(7, SILVER, new BlockPos(208, 20, 16), DepositOrigin.MAGMATIC);
        GeologicalDeposit sedimentaryCopper = deposit(8, COPPER, new BlockPos(240, 20, 16), DepositOrigin.SEDIMENTARY);
        GeologicalDeposit unsupportedBismuth = deposit(
                9, id("c", "ores/bismuth"), new BlockPos(272, 20, 16), DepositOrigin.HYDROTHERMAL);

        RnsDepositProjectionPlanner.Plan plan = RnsDepositProjectionPlanner.plan(List.of(
                unsupportedBismuth,
                sedimentaryCopper,
                silver,
                zinc,
                nickel,
                tin,
                gold,
                iron,
                copper));

        assertEquals(List.of(
                projection(copper, "deposit_copper"),
                projection(iron, "deposit_iron"),
                projection(gold, "deposit_gold")
        ), plan.projections());
        assertTrue(plan.collisions().isEmpty());
    }

    @Test
    void planningIsDeterministicAcrossInputOrder() {
        GeologicalDeposit gold = deposit(9, GOLD, new BlockPos(80, 20, 80), DepositOrigin.MAGMATIC);
        GeologicalDeposit copper = deposit(1, COPPER, new BlockPos(16, 20, 16), DepositOrigin.HYDROTHERMAL);

        assertEquals(
                RnsDepositProjectionPlanner.plan(List.of(gold, copper)),
                RnsDepositProjectionPlanner.plan(List.of(copper, gold)));
    }

    @Test
    void sameRnsTypeAndChunkFailsClosedAsExplicitCollision() {
        GeologicalDeposit first = deposit(1, COPPER, new BlockPos(17, 30, 17), DepositOrigin.MAGMATIC);
        GeologicalDeposit second = deposit(2, COPPER, new BlockPos(30, 45, 30), DepositOrigin.HYDROTHERMAL);

        RnsDepositProjectionPlanner.Plan plan = RnsDepositProjectionPlanner.plan(List.of(first, second));

        assertTrue(plan.projections().isEmpty());
        assertEquals(1, plan.collisions().size());
        assertEquals(id("create_rns", "deposit_copper"), plan.collisions().getFirst().rnsDepositId());
        assertEquals(new ChunkPos(first.center()), plan.collisions().getFirst().chunk());
        assertEquals(List.of(first.persistenceId(), second.persistenceId()), plan.collisions().getFirst().sourceIds());
    }

    @Test
    void sameRnsTypeInDifferentChunksDoesNotCollide() {
        GeologicalDeposit first = deposit(1, COPPER, new BlockPos(17, 30, 17), DepositOrigin.MAGMATIC);
        GeologicalDeposit second = deposit(2, COPPER, new BlockPos(33, 30, 17), DepositOrigin.HYDROTHERMAL);

        RnsDepositProjectionPlanner.Plan plan = RnsDepositProjectionPlanner.plan(List.of(first, second));

        assertEquals(2, plan.projections().size());
        assertTrue(plan.collisions().isEmpty());
    }

    private static RnsDepositProjectionPlanner.Projection projection(GeologicalDeposit deposit, String rnsPath) {
        return new RnsDepositProjectionPlanner.Projection(
                deposit.persistenceId(), id("create_rns", rnsPath), deposit.center());
    }

    private static GeologicalDeposit deposit(
            int id,
            ResourceLocation resourceTag,
            BlockPos center,
            DepositOrigin origin
    ) {
        return new GeologicalDeposit(new UUID(0L, id), resourceTag, center, 32.0, 0.75, origin);
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
