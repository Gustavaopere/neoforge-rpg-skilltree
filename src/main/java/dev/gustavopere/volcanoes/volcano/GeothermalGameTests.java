package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Dedicated-server acceptance test for geothermal worldgen metadata reconciliation. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class GeothermalGameTests {
    private GeothermalGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void generatedExpressionPersistsAndFeedsHeatIndex(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        long worldSeed = level.getSeed();
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.HOT_SPRING);
        BlockPos center = helper.absolutePos(new BlockPos(0, 1, 0));
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                center,
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                1.0);
        GeothermalSource expected = GeothermalSource.fromPlacement(worldSeed, placement);
        GeologicalDeposit expectedDeposit = new HydrothermalDepositProjector()
                .project(worldSeed, placement)
                .orElseThrow();

        GeothermalPendingQueue.Reservation reservation = GeothermalWorldgenRuntime
                .reserveGenerated(level, worldSeed, placement)
                .orElseThrow();
        helper.assertTrue(GeothermalWorldgenRuntime.commitGenerated(level, reservation, true),
                "physically realized geothermal expression must enter the committed handoff queue with proof");
        helper.assertTrue(GeothermalWorldgenRuntime.persistQueued(level) == 1,
                "server tick reconciliation must drain one queued expression");
        helper.assertTrue(GeothermalSourceRegistry.get(level).get(expected.persistenceId()).orElseThrow().equals(expected),
                "generated geothermal source must persist in level SavedData");
        helper.assertTrue(DepositRegistry.get(level).get(expectedDeposit.persistenceId()).orElseThrow().equals(expectedDeposit),
                "physically realized hydrothermal fixture must reconcile the exact deterministic geological deposit");
        helper.assertTrue(VolcanicHeatService.nearby(level, center, 8.0, 8).stream()
                        .anyMatch(source -> source.sourceId().equals(expected.persistenceId())),
                "persistent geothermal source must be queryable through the bounded heat service");

        GeothermalSourceRegistry restored = GeothermalSourceRegistry.fromTag(GeothermalSourceRegistry.get(level).toTag());
        helper.assertTrue(restored.get(expected.persistenceId()).orElseThrow().equals(expected),
                "geothermal source must survive persistence round-trip");
        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void unrealizedExactDepositRevokesLegacyMetadata(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        long worldSeed = 0x6A09E667F3BCC909L;
        TectonicService tectonics = lowInteriorTectonics();
        GeothermalActivityService activity = new GeothermalActivityService(512.0);
        VolcanoCandidateField field = new VolcanoCandidateField(512, 128);
        VolcanoWorldgenResolver volcanoes = new VolcanoWorldgenResolver(
                field,
                tectonics,
                new VolcanoSitePlanner(256.0, 0.0),
                512);
        GeothermalWorldgenResolver resolver = new GeothermalWorldgenResolver(
                tectonics,
                volcanoes,
                activity);
        BlockPos center = field.centerForCell(worldSeed, 0L, 0L).offset(32, 72, 16);
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.HOT_SPRING);
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                center,
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                1.0);
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector(resolver);
        GeologicalDeposit legacyExactDeposit = projector.project(worldSeed, placement).orElseThrow();
        helper.assertTrue(!GeothermalWorldgenRuntime.depositMetadataAdmissible(
                        legacyExactDeposit.resourceTag(), false),
                "legacy migration fixture must deterministically project one exact Cu/Fe/Au identity requiring physical proof");

        DepositRegistry deposits = DepositRegistry.get(level);
        deposits.register(legacyExactDeposit);
        helper.assertTrue(deposits.get(legacyExactDeposit.persistenceId()).isPresent(),
                "fixture must emulate an exact deposit persisted by a pre-physical-production release");

        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalPendingQueue.Reservation reservation = queue.reserve(worldSeed, placement).orElseThrow();
        helper.assertTrue(queue.commit(reservation, false),
                "unrealized expression must retain an explicit no-physical-proof handoff");
        helper.assertTrue(GeothermalWorldgenRuntime.persistPending(
                        queue,
                        GeothermalSourceRegistry.get(level),
                        deposits,
                        projector) == 1,
                "reconciliation must consume the legacy expression after revoking inadmissible exact metadata");
        helper.assertTrue(deposits.get(legacyExactDeposit.persistenceId()).isEmpty(),
                "an exact legacy deposit without current physical proof must be revoked instead of remaining prospectable");
        helper.succeed();
    }

    private static TectonicService lowInteriorTectonics() {
        return (seed, x, z) -> new TectonicSample(
                17L,
                19L,
                TectonicContext.INTERIOR,
                0.10,
                0.20,
                8_192.0,
                0.0,
                0.0);
    }
}
