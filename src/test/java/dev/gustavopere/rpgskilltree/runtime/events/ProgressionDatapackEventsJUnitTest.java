package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import org.junit.jupiter.api.Test;

final class ProgressionDatapackEventsJUnitTest {
    @Test
    void datapackSyncIsRegisteredAsGameBusEventHook() throws Exception {
        Method method = ProgressionDatapackEvents.class.getDeclaredMethod(
            "onDatapackSync", OnDatapackSyncEvent.class
        );
        assertNotNull(method.getAnnotation(SubscribeEvent.class));
    }

    @Test
    void relevantPlayersAreReconciledExactlyOnce() {
        List<String> reconciled = new ArrayList<>();
        Consumer<String> reconcile = reconciled::add;

        ProgressionDatapackEvents.reconcileRelevant(
            Stream.of("alpha", "beta", "gamma"), reconcile
        );

        assertEquals(List.of("alpha", "beta", "gamma"), reconciled);
    }
}
