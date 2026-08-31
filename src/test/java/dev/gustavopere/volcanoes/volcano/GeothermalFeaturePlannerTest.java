package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalFeaturePlannerTest {
    @Test
    void planningIsDeterministicAndGloballyRespectsLargestProfileSpacing() {
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        long seed = 0x243F6A8885A308D3L;

        var center = planner.candidateCenter(seed, 7L, -11L);
        var same = planner.candidateCenter(seed, 7L, -11L);
        assertEquals(center, same);

        int requiredSpacing = EnumSet.allOf(GeothermalFeatureType.class).stream()
                .map(GeothermalFeatureProfile::defaults)
                .mapToInt(GeothermalFeatureProfile::minimumSpacingBlocks)
                .max()
                .orElseThrow();

        for (long dx = -1; dx <= 1; dx++) {
            for (long dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) {
                    continue;
                }
                var neighbor = planner.candidateCenter(seed, 7L + dx, -11L + dz);
                double distance = Math.hypot(
                        (double) center.getX() - neighbor.getX(),
                        (double) center.getZ() - neighbor.getZ());
                assertTrue(distance >= requiredSpacing,
                        () -> "geothermal candidates must preserve global spacing, got " + distance);
            }
        }
    }

    @Test
    void onlyOwningChunkCanPlanTheCandidate() {
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        long seed = 123456789L;
        var center = planner.candidateCenter(seed, 2L, 3L);
        ChunkPos owner = new ChunkPos(center);

        Optional<GeothermalFeaturePlacement> placement = planner.plan(
                seed, owner, 0.95, true, true);
        assertTrue(placement.isPresent());
        assertEquals(center.getX(), placement.orElseThrow().center().getX());
        assertEquals(center.getZ(), placement.orElseThrow().center().getZ());

        ChunkPos other = new ChunkPos(owner.x + 1, owner.z);
        assertFalse(planner.plan(seed, other, 0.95, true, true).isPresent());
    }

    @Test
    void causalAndEnvironmentalPredicatesFailClosed() {
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        long seed = 987654321L;
        var center = planner.candidateCenter(seed, -4L, 9L);
        ChunkPos owner = new ChunkPos(center);

        assertFalse(planner.plan(seed, owner, 0.10, true, true).isPresent(),
                "low geothermal potential must not place a feature");
        assertFalse(planner.plan(seed, owner, 1.00, true, false).isPresent(),
                "unsuitable terrain must fail closed");

        Optional<GeothermalFeaturePlacement> dry = planner.plan(seed, owner, 1.00, false, true);
        assertTrue(dry.isPresent(), "dry terrain may still support a gas-only geothermal feature");
        assertFalse(GeothermalFeatureProfile.defaults(dry.orElseThrow().type()).requiresWater());
    }

    @Test
    void selectedTypeIsStableAndMeetsItsOwnThresholds() {
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        long seed = 0x13198A2E03707344L;
        var center = planner.candidateCenter(seed, 5L, 5L);
        ChunkPos owner = new ChunkPos(center);

        GeothermalFeaturePlacement first = planner.plan(seed, owner, 0.88, true, true).orElseThrow();
        GeothermalFeaturePlacement second = planner.plan(seed, owner, 0.88, true, true).orElseThrow();
        assertEquals(first, second);

        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(first.type());
        assertTrue(0.88 >= profile.minimumPotential());
        assertEquals(profile.radiusBlocks(), first.radiusBlocks());
        assertEquals(profile.heatSeverity(), first.heatSeverity());
        assertEquals(profile.gasSeverity(), first.gasSeverity());
        assertEquals(profile.hydrothermalDepositChance(), first.hydrothermalDepositChance());
    }
}
