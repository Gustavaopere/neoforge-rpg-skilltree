package dev.gustavopere.volcanoes.environment;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class AtmosphereClientEventsContractTest {
    @Test
    void clientLogoutResetHookIsPhysicallyClientOnly() throws Exception {
        EventBusSubscriber subscriber = AtmosphereClientEvents.class.getAnnotation(EventBusSubscriber.class);
        assertNotNull(subscriber);
        assertArrayEquals(new Dist[]{Dist.CLIENT}, subscriber.value());
        assertEquals(
                void.class,
                AtmosphereClientEvents.class
                        .getMethod("onLoggingOut", ClientPlayerNetworkEvent.LoggingOut.class)
                        .getReturnType());
    }
}
