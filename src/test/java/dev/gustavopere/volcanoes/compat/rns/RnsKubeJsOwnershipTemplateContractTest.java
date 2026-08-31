package dev.gustavopere.volcanoes.compat.rns;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RnsKubeJsOwnershipTemplateContractTest {
    private static final Path TEMPLATE = Path.of(
            "integration-templates/kubejs/startup_scripts/volcanoes_rns_worldgen.js");

    @Test
    void supportedStartupHookKeepsAllRnsFamiliesScannableAndNativeUnderCoexistence() throws Exception {
        assertTrue(Files.isRegularFile(TEMPLATE), "canonical RNS startup template must be deployable");
        String source = Files.readString(TEMPLATE);

        assertTrue(source.contains("Create: Rock & Stone 1.3.1-1.21.1-6"));
        assertTrue(source.contains("StartupEvents.rnsEnableDeposits"));
        for (String metal : new String[]{"copper", "iron", "gold", "tin", "nickel", "zinc", "silver"}) {
            assertTrue(source.contains(".deposit('create_rns:deposit_" + metal + "', true)"),
                    "coexistence must retain native RNS worldgen for " + metal);
            assertFalse(source.contains(".deposit('create_rns:deposit_" + metal + "', false)"),
                    "Volcanoes custom prospecting projection must never globally disable " + metal);
        }
        assertTrue(source.contains("CustomServerDepositLocation"),
                "template rationale must document that Volcanoes contributes prospecting metadata only");
        assertTrue(source.contains(".deposit('create_rns:deposit_coal', true)"));

        assertFalse(source.contains(".spacing("),
                "coexistence must preserve the active RNS frequency pack spacing");
        assertFalse(source.contains(".separation("),
                "coexistence must preserve the active RNS frequency pack separation");
        assertFalse(source.contains(".salt("),
                "coexistence must preserve the active RNS frequency pack salt");
    }
}
