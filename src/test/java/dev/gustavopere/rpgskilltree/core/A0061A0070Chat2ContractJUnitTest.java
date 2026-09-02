package dev.gustavopere.rpgskilltree.core;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.CombatPerkAvailabilityRuntime;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

final class A0061A0070Chat2ContractJUnitTest {
    @Test
    void offensiveFirmnessIsUnavailableWithoutProviderNativeAttackWindowBinding() {
        assertFalse(
            CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0067"),
            "A0067 must be fail-closed/non-purchasable until Epic Fight exposes a safe attack-window lifetime binding"
        );
    }

    @Test
    void bossClassifierPublishesOnlyTheApprovedOptionalEnshroudedIdentity() throws IOException {
        try (InputStream input = getClass().getResourceAsStream("/data/rpgskilltree/tags/entity_type/bosses.json")) {
            assertNotNull(input, "boss classifier tag must be present in the runtime resources");
            String json = new String(input.readAllBytes(), UTF_8);
            assertTrue(
                json.contains("\"id\": \"enshrouded:shroud_lich\"")
                    && json.contains("\"required\": false"),
                "A0070 may bridge Enshrouded only through the approved optional shroud_lich registry identity"
            );
        }
    }
}
