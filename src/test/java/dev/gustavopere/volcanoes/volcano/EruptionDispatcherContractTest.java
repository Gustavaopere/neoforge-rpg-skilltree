package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EruptionDispatcherContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("589f4075-58bb-40a8-af70-c3c9f04d5654");

    @Test
    void fanoutIsStableIdempotentAndIsolatesFailingConsumers() {
        EruptionSignal signal = signal();
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(0, 0, 0, 0, 0, 0);
        List<String> calls = new ArrayList<>();

        EruptionSink first = (snapshot, work) -> {
            assertEquals(signal, snapshot);
            assertEquals(grant, work);
            calls.add("first");
        };
        EruptionSink broken = (snapshot, work) -> {
            calls.add("broken");
            throw new IllegalStateException("optional adapter failed");
        };
        EruptionSink last = (snapshot, work) -> calls.add("last");

        EruptionDispatcher dispatcher = new EruptionDispatcher();
        assertTrue(dispatcher.register(first));
        assertFalse(dispatcher.register(first));
        assertTrue(dispatcher.register(broken));
        assertTrue(dispatcher.register(last));

        EruptionDispatcher.DispatchResult result = dispatcher.dispatch(signal, grant);

        assertEquals(List.of("first", "broken", "last"), calls);
        assertEquals(2, result.delivered());
        assertEquals(1, result.failed());
        assertEquals(3, dispatcher.size());

        assertTrue(dispatcher.unregister(broken));
        assertFalse(dispatcher.unregister(broken));
        calls.clear();

        EruptionDispatcher.DispatchResult afterRemoval = dispatcher.dispatch(signal, grant);
        assertEquals(List.of("first", "last"), calls);
        assertEquals(2, afterRemoval.delivered());
        assertEquals(0, afterRemoval.failed());
        assertEquals(2, dispatcher.size());
    }

    @Test
    void workGrantIsPartitionedAcrossConsumersWithoutMultiplyingGlobalBudget() {
        EruptionSignal signal = signal();
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(8, 5, 4, 2, 1, 2);
        List<EruptionScheduler.WorkGrant> grants = new ArrayList<>();

        EruptionDispatcher dispatcher = new EruptionDispatcher();
        dispatcher.register((snapshot, work) -> grants.add(work));
        dispatcher.register((snapshot, work) -> grants.add(work));
        dispatcher.register((snapshot, work) -> grants.add(work));

        EruptionDispatcher.DispatchResult result = dispatcher.dispatch(signal, grant);

        assertEquals(3, result.delivered());
        assertEquals(0, result.failed());
        assertEquals(3, grants.size());
        assertEquals(new EruptionScheduler.WorkGrant(3, 2, 2, 1, 1, 1), grants.get(0));
        assertEquals(new EruptionScheduler.WorkGrant(3, 2, 1, 1, 0, 1), grants.get(1));
        assertEquals(new EruptionScheduler.WorkGrant(2, 1, 1, 0, 0, 0), grants.get(2));
        assertEquals(grant.immediateBlocks(), grants.stream().mapToInt(EruptionScheduler.WorkGrant::immediateBlocks).sum());
        assertEquals(grant.immediateEntities(), grants.stream().mapToInt(EruptionScheduler.WorkGrant::immediateEntities).sum());
        assertEquals(grant.queuedBlocks(), grants.stream().mapToInt(EruptionScheduler.WorkGrant::queuedBlocks).sum());
        assertEquals(grant.queuedEntities(), grants.stream().mapToInt(EruptionScheduler.WorkGrant::queuedEntities).sum());
        assertEquals(grant.droppedBlocks(), grants.stream().mapToInt(EruptionScheduler.WorkGrant::droppedBlocks).sum());
        assertEquals(grant.droppedEntities(), grants.stream().mapToInt(EruptionScheduler.WorkGrant::droppedEntities).sum());
    }

    private static EruptionSignal signal() {
        VolcanoSite site = new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(220, 105, -410),
                VolcanoType.CALDERA,
                VolcanoState.ERUPTING,
                TectonicContext.CONVERGENT,
                7L,
                8L,
                0.94);
        MagmaChamber chamber = new MagmaChamber(
                MagmaComposition.forType(VolcanoType.CALDERA),
                13.0,
                345.0,
                0.24,
                1_250.0,
                0.40);
        EruptionController controller = new EruptionController();
        EruptionEvent started = controller.begin(VOLCANO_ID, chamber, 12_000L);
        EruptionEvent sustained = controller.advance(
                started,
                started.profile().precursorsTicks() + started.profile().openingTicks() + 100L);
        return EruptionSignal.from(site, chamber, sustained);
    }
}
