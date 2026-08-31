package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalPendingQueueFailureIsolationTest {
    @Test
    void poisonEntryDoesNotEscapeOrStarveLaterCommittedWork() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(2, 1);
        GeothermalFeaturePlacement poison = placement(GeothermalFeatureType.HOT_SPRING, 0, 0.0);
        GeothermalFeaturePlacement valid = placement(GeothermalFeatureType.FUMAROLE, 64, 0.0);

        assertTrue(queue.enqueue(91L, poison));
        assertTrue(queue.enqueue(91L, valid));

        int firstDrain = assertDoesNotThrow(() -> queue.processCommitted(pending -> {
            if (pending.placement().equals(poison)) {
                throw new IllegalStateException("deterministic persistence conflict");
            }
            return true;
        }));

        assertEquals(0, firstDrain, "a failed acknowledgement must not be reported as persisted");
        assertEquals(2, queue.size(), "the failed entry must remain bounded for retry/diagnostics");

        int secondDrain = assertDoesNotThrow(() -> queue.processCommitted(pending -> {
            if (pending.placement().equals(poison)) {
                throw new IllegalStateException("deterministic persistence conflict");
            }
            return true;
        }));

        assertEquals(1, secondDrain,
                "a poison head must yield its position so later committed work can make progress");
        assertEquals(1, queue.size(), "only the poison entry should remain after the valid item is acknowledged");
    }

    @Test
    void repeatedPoisonMovesToBoundedQuarantineAfterFiniteRetryBudget() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(2, 1);
        GeothermalFeaturePlacement poison = placement(GeothermalFeatureType.HOT_SPRING, 0, 0.0);
        GeothermalFeaturePlacement laterValid = placement(GeothermalFeatureType.GEYSER, 96, 0.0);
        assertTrue(queue.enqueue(91L, poison));

        for (int attempt = 0; attempt < GeothermalPendingQueue.MAX_PROCESSING_FAILURES; attempt++) {
            assertDoesNotThrow(() -> queue.processCommitted(pending -> {
                throw new IllegalStateException("deterministic poison");
            }));
        }

        assertEquals(1, queue.quarantinedCount(),
                "a deterministic poison must stop consuming retry turns after a finite failure budget");
        assertEquals(1, queue.size(), "quarantine must remain inside the queue's configured capacity bound");

        assertTrue(queue.enqueue(91L, laterValid),
                "bounded quarantine must leave remaining capacity available for later valid metadata");
        int acknowledged = assertDoesNotThrow(() -> queue.processCommitted(pending -> true));
        assertEquals(1, acknowledged, "later valid work must still be persistable after poison quarantine");
        assertEquals(1, queue.size(), "only the bounded quarantined diagnostic entry should remain");
    }

    @Test
    void partialSourceAuthorityRetriesDepositWithoutDuplicatingEitherRegistry() {
        long worldSeed = 91L;
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.HOT_SPRING, 0, 1.0);
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(4);
        DepositRegistry deposits = new DepositRegistry();
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();

        GeothermalSource expectedSource = GeothermalSource.fromPlacement(worldSeed, placement);
        GeologicalDeposit expectedDeposit = projector.project(worldSeed, placement).orElseThrow();
        GeologicalDeposit conflictingDeposit = new GeologicalDeposit(
                expectedDeposit.persistenceId(),
                expectedDeposit.resourceTag(),
                expectedDeposit.center().offset(1, 0, 0),
                expectedDeposit.radius(),
                expectedDeposit.richness(),
                expectedDeposit.origin());

        assertTrue(deposits.register(conflictingDeposit));
        var reservation = queue.reserve(worldSeed, placement).orElseThrow();
        assertTrue(queue.commit(reservation, true));

        int firstDrain = assertDoesNotThrow(() -> GeothermalWorldgenRuntime.persistPending(
                queue,
                sources,
                deposits,
                projector));

        assertEquals(0, firstDrain, "deposit conflict must retain the committed queue entry for retry");
        assertEquals(1, queue.size());
        assertEquals(1, sources.size(), "source authority established before the deposit conflict must not duplicate");
        assertEquals(expectedSource, sources.get(expectedSource.persistenceId()).orElseThrow());
        assertEquals(1, deposits.size());
        assertEquals(conflictingDeposit, deposits.get(expectedDeposit.persistenceId()).orElseThrow());

        assertTrue(deposits.remove(expectedDeposit.persistenceId()));

        int secondDrain = assertDoesNotThrow(() -> GeothermalWorldgenRuntime.persistPending(
                queue,
                sources,
                deposits,
                projector));

        assertEquals(1, secondDrain, "retry must finish once the deterministic deposit conflict is removed");
        assertTrue(queue.isEmpty());
        assertEquals(1, sources.size(), "retry must keep exactly one authoritative geothermal source");
        assertEquals(expectedSource, sources.get(expectedSource.persistenceId()).orElseThrow());
        assertEquals(1, deposits.size(), "retry must create exactly one authoritative hydrothermal deposit");
        assertEquals(expectedDeposit, deposits.get(expectedDeposit.persistenceId()).orElseThrow());
    }

    @Test
    void legacyGenericHydrothermalUpgradeDrainsWithoutRetrying() {
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
        BlockPos magmaCenter = field.centerForCell(worldSeed, 0L, 0L);
        BlockPos center = magmaCenter.offset(32, 72, 16);
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                center,
                4,
                0.80,
                0.30,
                1.0);
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector(resolver);
        GeologicalDeposit expectedDeposit = projector.project(worldSeed, placement).orElseThrow();
        assertNotEquals(GeologyResourceTags.MINERAL_RESOURCES.location(), expectedDeposit.resourceTag(),
                "fixture must project an exact canonical hydrothermal metal identity");

        GeologicalDeposit legacyDeposit = new GeologicalDeposit(
                expectedDeposit.persistenceId(),
                GeologyResourceTags.MINERAL_RESOURCES.location(),
                expectedDeposit.center(),
                expectedDeposit.radius(),
                expectedDeposit.richness(),
                expectedDeposit.origin());
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(4);
        DepositRegistry deposits = new DepositRegistry();

        assertTrue(deposits.register(legacyDeposit));
        var reservation = queue.reserve(worldSeed, placement).orElseThrow();
        assertTrue(queue.commit(reservation, true));

        int acknowledged = assertDoesNotThrow(() -> GeothermalWorldgenRuntime.persistPending(
                queue,
                sources,
                deposits,
                projector));

        assertEquals(1, acknowledged,
                "physically proven legacy generic metadata must migrate to the exact identity and acknowledge in one turn");
        assertTrue(queue.isEmpty(), "successful migration must not remain in retry/quarantine state");
        assertEquals(1, deposits.size(), "migration must preserve one stable deposit UUID");
        assertEquals(expectedDeposit, deposits.get(expectedDeposit.persistenceId()).orElseThrow());
    }

    @Test
    void exactPreexistingSourceAndDepositAreAuthoritativeAndDrainIdempotently() {
        long worldSeed = 91L;
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.HOT_SPRING, 128, 1.0);
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(4);
        DepositRegistry deposits = new DepositRegistry();
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();

        GeothermalSource expectedSource = GeothermalSource.fromPlacement(worldSeed, placement);
        GeologicalDeposit expectedDeposit = projector.project(worldSeed, placement).orElseThrow();
        assertTrue(sources.register(expectedSource));
        assertTrue(deposits.register(expectedDeposit));
        var reservation = queue.reserve(worldSeed, placement).orElseThrow();
        assertTrue(queue.commit(reservation, true));

        int acknowledged = assertDoesNotThrow(() -> GeothermalWorldgenRuntime.persistPending(
                queue,
                sources,
                deposits,
                projector));

        assertEquals(1, acknowledged,
                "physically proven exact preexisting source and deposit must remain authoritative and idempotent");
        assertTrue(queue.isEmpty());
        assertEquals(1, sources.size());
        assertEquals(1, deposits.size());
        assertEquals(expectedSource, sources.get(expectedSource.persistenceId()).orElseThrow());
        assertEquals(expectedDeposit, deposits.get(expectedDeposit.persistenceId()).orElseThrow());
    }

    @Test
    void placementWithoutProjectedDepositDrainsWhenSourceIsAuthoritative() {
        long worldSeed = 91L;
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.FUMAROLE, 192, 0.0);
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(4);
        DepositRegistry deposits = new DepositRegistry();
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();

        assertTrue(projector.project(worldSeed, placement).isEmpty(),
                "fixture must not require hydrothermal deposit authority");
        assertTrue(queue.enqueue(worldSeed, placement));

        int acknowledged = assertDoesNotThrow(() -> GeothermalWorldgenRuntime.persistPending(
                queue,
                sources,
                deposits,
                projector));

        assertEquals(1, acknowledged,
                "absence of an expected deposit must not block otherwise authoritative source metadata");
        assertTrue(queue.isEmpty());
        assertEquals(1, sources.size());
        assertEquals(0, deposits.size());
    }

    private static GeothermalFeaturePlacement placement(
            GeothermalFeatureType type,
            int x,
            double hydrothermalDepositChance
    ) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(type);
        return new GeothermalFeaturePlacement(
                type,
                new BlockPos(x, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                hydrothermalDepositChance);
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
