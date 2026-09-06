package dev.gustavopere.rpgskilltree.itemization.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationReloader;
import net.neoforged.fml.common.EventBusSubscriber;
import org.junit.jupiter.api.Test;

final class EquipmentClassificationWiringTest {
    @Test
    void equipmentClassificationReloaderIsAutoRegisteredOnTheGameBus() {
        EventBusSubscriber subscriber = EquipmentClassificationReloader.class.getAnnotation(EventBusSubscriber.class);

        assertNotNull(subscriber, "equipment classification reload listener must be auto-registered");
        assertEquals(RpgSkillTreeMod.MOD_ID, subscriber.modid());
        assertEquals(EventBusSubscriber.Bus.GAME, subscriber.bus());
    }
}
