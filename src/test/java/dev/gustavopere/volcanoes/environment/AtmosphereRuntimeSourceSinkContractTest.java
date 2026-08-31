package dev.gustavopere.volcanoes.environment;

import net.minecraft.server.level.ServerLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtmosphereRuntimeSourceSinkContractTest {
    @Test
    void runtimeExposesNeutralPerLevelSourceSinkWithoutConcreteFieldCoupling() throws Exception {
        assertEquals(
                AtmosphericSourceSink.class,
                AtmosphereRuntime.class
                        .getMethod("sourceSinkFor", ServerLevel.class)
                        .getReturnType());
    }
}
