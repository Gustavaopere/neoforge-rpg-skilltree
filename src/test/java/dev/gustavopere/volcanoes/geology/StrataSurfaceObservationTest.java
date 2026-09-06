package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class StrataSurfaceObservationTest {
    private static final int MIN_Y = -64;
    private static final int MAX_Y_EXCLUSIVE = 320;
    private static final int SURFACE_Y = 96;

    @Test
    void strongNearbyRockConsensusMayBiasOnlyShallowQueries() {
        StrataService service = new StrataService(
                0xB1A5L,
                new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE),
                RockProfileRegistry::vanillaDefaults,
                8);

        BlockPos shallow = new BlockPos(256, 80, -512);
        RockProfile virtual = service.profileAt(shallow);
        RockProfile observed = alternateRecognizedRock(virtual);
        RockProfile minority = recognizedRock("observed:minority", RockCategory.METAMORPHIC);
        List<RockProfile> strongConsensus = List.of(observed, observed, observed, minority);

        assertEquals(
                observed,
                service.profileAtWithSurfaceObservations(shallow, SURFACE_Y, strongConsensus));

        BlockPos deep = new BlockPos(shallow.getX(), 60, shallow.getZ());
        assertEquals(
                service.profileAt(deep),
                service.profileAtWithSurfaceObservations(deep, SURFACE_Y, strongConsensus),
                "surface observations must never override deep virtual geology");
    }

    @Test
    void weakOrGenericObservationsDoNotOverrideVirtualGeology() {
        StrataService service = new StrataService(
                0xB1A5L,
                new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE),
                RockProfileRegistry::vanillaDefaults,
                8);
        BlockPos pos = new BlockPos(256, 80, -512);
        RockProfile virtual = service.profileAt(pos);
        RockProfile basalt = recognizedRock("observed:basalt", RockCategory.IGNEOUS_EXTRUSIVE);
        RockProfile granite = recognizedRock("observed:granite", RockCategory.IGNEOUS_INTRUSIVE);

        assertEquals(
                virtual,
                service.profileAtWithSurfaceObservations(
                        pos,
                        SURFACE_Y,
                        List.of(basalt, basalt, granite, granite)));

        assertEquals(
                virtual,
                service.profileAtWithSurfaceObservations(
                        pos,
                        SURFACE_Y,
                        List.of(
                                RockProfile.GENERIC_STONE,
                                RockProfile.GENERIC_STONE,
                                RockProfile.GENERIC_STONE)));
    }

    private static RockProfile alternateRecognizedRock(RockProfile virtual) {
        return "observed:basalt".equals(virtual.id())
                ? recognizedRock("observed:granite", RockCategory.IGNEOUS_INTRUSIVE)
                : recognizedRock("observed:basalt", RockCategory.IGNEOUS_EXTRUSIVE);
    }

    private static RockProfile recognizedRock(String id, RockCategory category) {
        return new RockProfile(id, category, 0.72, 0.18, 2.3, 0.82, 0.77, 0.31);
    }
}
