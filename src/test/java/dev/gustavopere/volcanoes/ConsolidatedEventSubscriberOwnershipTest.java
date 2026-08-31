package dev.gustavopere.volcanoes;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.volcanoes.environment.AtmosphereClientEvents;
import dev.gustavopere.volcanoes.geology.RockProfileReloadListener;
import dev.gustavopere.volcanoes.pressure.AtmosphericPressureReloadListener;
import dev.gustavopere.volcanoes.pressure.PressureExposureConfigReloadListener;
import net.neoforged.fml.common.EventBusSubscriber;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

final class ConsolidatedEventSubscriberOwnershipTest {
    @Test
    void importedVolcanoesSubscribersBelongToSingleHostMod() {
        for (Class<?> subscriberType : List.of(
                VolcanoesClientMod.class,
                AtmosphereClientEvents.class,
                RockProfileReloadListener.class,
                AtmosphericPressureReloadListener.class,
                PressureExposureConfigReloadListener.class)) {
            EventBusSubscriber subscriber = subscriberType.getAnnotation(EventBusSubscriber.class);
            assertNotNull(subscriber, subscriberType.getName() + " must remain an event-bus subscriber");
            assertEquals(
                    RpgSkillTreeMod.MOD_ID,
                    subscriber.modid(),
                    subscriberType.getName() + " must be discovered under the consolidated host mod id");
            assertNotEquals(
                    VolcanoesMod.MOD_ID,
                    subscriber.modid(),
                    subscriberType.getName() + " must not depend on the removed standalone mod id");
        }
    }
}
