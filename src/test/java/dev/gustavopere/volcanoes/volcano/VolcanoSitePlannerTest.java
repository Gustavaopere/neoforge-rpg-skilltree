package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoSitePlannerTest {
    @Test
    void placementScoreRewardsVolcanicTectonicsAndTerrainHints() {
        VolcanoSitePlanner planner = new VolcanoSitePlanner(2_048.0, 0.55);

        double interior = planner.placementScore(sample(TectonicContext.INTERIOR, 0.1, 0.10, 8_000.0), false);
        double convergent = planner.placementScore(sample(TectonicContext.CONVERGENT, 0.4, 0.90, 128.0), false);
        double divergent = planner.placementScore(sample(TectonicContext.DIVERGENT, 0.3, 0.68, 160.0), false);
        double hotspot = planner.placementScore(sample(TectonicContext.HOTSPOT, 0.2, 0.95, 6_000.0), false);

        assertTrue(convergent > interior);
        assertTrue(divergent > interior);
        assertTrue(hotspot > interior);
        assertTrue(planner.placementScore(sample(TectonicContext.CONVERGENT, 0.4, 0.90, 128.0), true) > convergent);
    }

    @Test
    void tectonicContextSelectsAllSupportedVolcanoProfiles() {
        VolcanoSitePlanner planner = new VolcanoSitePlanner(2_048.0, 0.55);

        assertEquals(VolcanoType.STRATOVOLCANO,
                planner.typeFor(sample(TectonicContext.CONVERGENT, 0.55, 0.85, 96.0)));
        assertEquals(VolcanoType.CALDERA,
                planner.typeFor(sample(TectonicContext.CONVERGENT, 0.95, 0.98, 64.0)));
        assertEquals(VolcanoType.FISSURE,
                planner.typeFor(sample(TectonicContext.DIVERGENT, 0.40, 0.72, 112.0)));
        assertEquals(VolcanoType.SHIELD,
                planner.typeFor(sample(TectonicContext.HOTSPOT, 0.30, 0.95, 5_000.0)));
    }

    @Test
    void planningIsDeterministicAndRejectsSitesInsideMinimumSpacing() {
        VolcanoSitePlanner planner = new VolcanoSitePlanner(2_048.0, 0.55);
        VolcanoSavedData sites = new VolcanoSavedData();
        TectonicSample sample = sample(TectonicContext.CONVERGENT, 0.60, 0.90, 96.0);
        BlockPos firstCenter = new BlockPos(8_192, 96, -4_096);

        VolcanoSite first = planner.plan(77L, firstCenter, sample, false, sites).orElseThrow();
        VolcanoSite repeated = planner.plan(77L, firstCenter, sample, false, new VolcanoSavedData()).orElseThrow();
        assertEquals(first.persistenceId(), repeated.persistenceId());

        assertTrue(sites.register(first));
        Optional<VolcanoSite> tooClose = planner.plan(
                77L,
                firstCenter.offset(1_000, 0, 0),
                sample,
                true,
                sites);
        assertTrue(tooClose.isEmpty());

        Optional<VolcanoSite> farEnough = planner.plan(
                77L,
                firstCenter.offset(2_500, 0, 0),
                sample,
                false,
                sites);
        assertTrue(farEnough.isPresent());
    }

    @Test
    void savedDataRoundTripsSitesAndFailsClosedOnConflictingIds() {
        UUID id = UUID.fromString("e0ad8d83-b7e5-44cb-9397-b67d13c93737");
        VolcanoSite site = new VolcanoSite(
                id,
                new BlockPos(320, 88, -640),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.DORMANT,
                TectonicContext.CONVERGENT,
                11L,
                12L,
                0.91);
        VolcanoSavedData data = new VolcanoSavedData();

        assertTrue(data.register(site));
        assertFalse(data.register(site));

        VolcanoSavedData restored = VolcanoSavedData.fromTag(data.toTag());
        assertEquals(1, restored.size());
        assertEquals(site, restored.get(id).orElseThrow());
        assertEquals(site, restored.nearby(site.center(), 1.0).getFirst());

        VolcanoSite conflicting = new VolcanoSite(
                id,
                site.center().offset(32, 0, 0),
                site.type(),
                site.state(),
                site.tectonicContext(),
                site.plateId(),
                site.neighborPlateId(),
                site.initialVolcanicPotential());
        assertThrows(IllegalStateException.class, () -> restored.register(conflicting));
    }

    private static TectonicSample sample(
            TectonicContext context,
            double stress,
            double volcanicPotential,
            double boundaryDistance
    ) {
        return new TectonicSample(
                11L,
                12L,
                context,
                stress,
                volcanicPotential,
                boundaryDistance,
                1.0,
                0.0);
    }
}
