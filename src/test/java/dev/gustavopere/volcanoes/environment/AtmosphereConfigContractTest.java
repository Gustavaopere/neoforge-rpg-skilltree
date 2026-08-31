package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.volcanoes.VolcanoesMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AtmosphereConfigContractTest {
    @Test
    void persistencePolicyCanBeBuiltFromServerConfigValues() {
        AtmospherePersistencePolicy policy = AtmosphereConfig.persistencePolicy(true, 1234);
        assertEquals(true, policy.enabled());
        assertEquals(1234, policy.maxSources());
        assertNotNull(AtmosphereConfig.SPEC);
    }

    @Test
    void persistenceCapacityCannotExceedRuntimeSourceCapacity() {
        assertEquals(16_384, AtmosphereConfig.MAX_ACTIVE_SOURCES);
        assertEquals(16_384, AtmosphereConfig.persistencePolicy(true, 16_384).maxSources());
        assertThrows(IllegalArgumentException.class,
                () -> AtmosphereConfig.persistencePolicy(true, 16_385));
    }

    @Test
    void unifiedModConstructorOwnsContainerAndVolcanoesInitializerAcceptsIt() throws Exception {
        assertNotNull(RpgSkillTreeMod.class.getConstructor(IEventBus.class, ModContainer.class));
        assertNotNull(VolcanoesMod.class.getMethod("initialize", IEventBus.class, ModContainer.class));
    }
}
