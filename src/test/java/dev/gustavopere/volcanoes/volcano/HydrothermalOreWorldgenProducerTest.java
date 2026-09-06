package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HydrothermalOreWorldgenProducerTest {
    private final HydrothermalOrePlacementPlanner planner = new HydrothermalOrePlacementPlanner();
    private final HydrothermalOreWorldgenProducer producer = new HydrothermalOreWorldgenProducer(planner);

    @Test
    void exactDepositRequiresFullTargetHostCoverageBeforeItIsPrepared() {
        GeologicalDeposit deposit = deposit(GeologyResourceTags.COPPER_ORES.location());
        ChunkPos owner = new ChunkPos(0, 0);
        var plan = planner.plan(deposit, owner).orElseThrow();
        Map<BlockPos, HydrothermalOreWorldgenProducer.HostRock> hosts = new HashMap<>();
        for (int index = 0; index < plan.targetBlocks(); index++) {
            hosts.put(plan.candidates().get(index),
                    (index & 1) == 0
                            ? HydrothermalOreWorldgenProducer.HostRock.STONE
                            : HydrothermalOreWorldgenProducer.HostRock.DEEPSLATE);
        }

        var prepared = producer.prepare(
                deposit,
                owner,
                position -> hosts.getOrDefault(position, HydrothermalOreWorldgenProducer.HostRock.NONE))
                .orElseThrow();

        assertEquals(plan.targetBlocks(), prepared.replacements().size());
        for (var replacement : prepared.replacements()) {
            if (replacement.expectedHost() == HydrothermalOreWorldgenProducer.HostRock.STONE) {
                assertEquals(HydrothermalOreWorldgenProducer.OreVariant.COPPER, replacement.variant());
            } else {
                assertEquals(HydrothermalOreWorldgenProducer.OreVariant.DEEPSLATE_COPPER, replacement.variant());
            }
        }
    }

    @Test
    void partialHostCoverageFailsClosedInsteadOfClaimingAWeakPhysicalDeposit() {
        GeologicalDeposit deposit = deposit(GeologyResourceTags.IRON_ORES.location());
        ChunkPos owner = new ChunkPos(0, 0);
        var plan = planner.plan(deposit, owner).orElseThrow();
        Map<BlockPos, HydrothermalOreWorldgenProducer.HostRock> hosts = new HashMap<>();
        for (int index = 0; index < plan.targetBlocks() - 1; index++) {
            hosts.put(plan.candidates().get(index), HydrothermalOreWorldgenProducer.HostRock.STONE);
        }

        assertFalse(producer.prepare(
                deposit,
                owner,
                position -> hosts.getOrDefault(position, HydrothermalOreWorldgenProducer.HostRock.NONE)).isPresent(),
                "exact metadata must not become physically authoritative with only partial host coverage");
    }

    @Test
    void oreVariantsAreMappedForAllOwnedStage01Metals() {
        assertEquals(HydrothermalOreWorldgenProducer.OreVariant.COPPER,
                HydrothermalOreWorldgenProducer.variantFor(
                        GeologyResourceTags.COPPER_ORES.location(), HydrothermalOreWorldgenProducer.HostRock.STONE));
        assertEquals(HydrothermalOreWorldgenProducer.OreVariant.DEEPSLATE_COPPER,
                HydrothermalOreWorldgenProducer.variantFor(
                        GeologyResourceTags.COPPER_ORES.location(), HydrothermalOreWorldgenProducer.HostRock.DEEPSLATE));
        assertEquals(HydrothermalOreWorldgenProducer.OreVariant.IRON,
                HydrothermalOreWorldgenProducer.variantFor(
                        GeologyResourceTags.IRON_ORES.location(), HydrothermalOreWorldgenProducer.HostRock.STONE));
        assertEquals(HydrothermalOreWorldgenProducer.OreVariant.DEEPSLATE_IRON,
                HydrothermalOreWorldgenProducer.variantFor(
                        GeologyResourceTags.IRON_ORES.location(), HydrothermalOreWorldgenProducer.HostRock.DEEPSLATE));
        assertEquals(HydrothermalOreWorldgenProducer.OreVariant.GOLD,
                HydrothermalOreWorldgenProducer.variantFor(
                        GeologyResourceTags.GOLD_ORES.location(), HydrothermalOreWorldgenProducer.HostRock.STONE));
        assertEquals(HydrothermalOreWorldgenProducer.OreVariant.DEEPSLATE_GOLD,
                HydrothermalOreWorldgenProducer.variantFor(
                        GeologyResourceTags.GOLD_ORES.location(), HydrothermalOreWorldgenProducer.HostRock.DEEPSLATE));
    }

    @Test
    void unsupportedOrGenericResourceNeverGetsPreparedByPhysicalProducer() {
        GeologicalDeposit generic = new GeologicalDeposit(
                UUID.fromString("361bbf85-4f72-41e9-a4f6-c1d42505b08f"),
                GeologyResourceTags.MINERAL_RESOURCES.location(),
                new BlockPos(8, 48, 8),
                8.0,
                0.50,
                DepositOrigin.HYDROTHERMAL);

        assertTrue(producer.prepare(generic, new ChunkPos(0, 0), ignored -> HydrothermalOreWorldgenProducer.HostRock.STONE)
                .isEmpty());
    }

    private static GeologicalDeposit deposit(net.minecraft.resources.ResourceLocation resourceTag) {
        return new GeologicalDeposit(
                UUID.fromString("f87e10d8-bc8b-40fe-bf12-cb4d4426cbd1"),
                resourceTag,
                new BlockPos(8, 48, 8),
                8.0,
                0.50,
                DepositOrigin.HYDROTHERMAL);
    }
}
