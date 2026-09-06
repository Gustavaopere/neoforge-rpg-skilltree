package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphericSourceSinkContractTest {
    @Test
    void atmosphereFieldExposesNeutralStableSourceLifecyclePort() throws Exception {
        assertTrue(AtmosphericSourceSink.class.isAssignableFrom(AtmosphereField.class));
        assertTrue(AtmosphericSourceSink.class.getMethod("tryUpsert", AtmosphericSource.class).getReturnType()
                == AtmosphericSourceAdmission.class);
        assertTrue(AtmosphericSourceSink.class.getMethod("upsert", AtmosphericSource.class).getReturnType() == void.class);
        assertTrue(AtmosphericSourceSink.class.getMethod("remove", UUID.class).getReturnType() == boolean.class);
    }
}
