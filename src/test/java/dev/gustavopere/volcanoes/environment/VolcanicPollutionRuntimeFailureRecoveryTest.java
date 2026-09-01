package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicPollutionRuntimeFailureRecoveryTest {
    @Test
    void transientHostFailureDoesNotPermanentlyDisablePollutionForLevel() throws Exception {
        AtomicInteger publicationAttempts = new AtomicInteger();
        PollutionAdapter adapter = new PollutionAdapter() {
            @Override
            public boolean isAuthoritative() {
                return true;
            }

            @Override
            public void publish(PollutionEmission emission) {
                if (publicationAttempts.incrementAndGet() == 1) {
                    throw new IllegalStateException("transient host failure");
                }
            }

            @Override
            public Optional<PollutionLoad> sampleExternalOnly(
                    String dimensionId,
                    double x,
                    double y,
                    double z
            ) {
                return Optional.empty();
            }
        };
        PollutionEmission emission = new PollutionEmission(
                UUID.fromString("00000000-0000-0000-0000-000000000401"),
                "minecraft:overworld",
                10.0,
                80.0,
                -4.0,
                new PollutionLoad(0.5, 0.25, 0.1));
        Object state = newLevelState(new PollutionCoordinator(adapter));

        assertFalse(route(state, emission));
        assertTrue(route(state, emission));
        assertEquals(2, publicationAttempts.get(),
                "the same host adapter must be retried after a transient failure");
    }

    private static Object newLevelState(PollutionCoordinator coordinator) throws ReflectiveOperationException {
        Class<?> type = Class.forName(
                "dev.gustavopere.volcanoes.environment.VolcanicPollutionRuntime$LevelState");
        Constructor<?> constructor = type.getDeclaredConstructor(PollutionCoordinator.class);
        constructor.setAccessible(true);
        return constructor.newInstance(coordinator);
    }

    private static boolean route(Object state, PollutionEmission emission) throws ReflectiveOperationException {
        Method route = state.getClass().getDeclaredMethod("route", PollutionEmission.class);
        route.setAccessible(true);
        return (boolean) route.invoke(state, emission);
    }
}
