package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauVersionContract;
import org.junit.jupiter.api.Test;

final class ClassProviderGameplayAvailabilityJUnitTest {
    @Test
    void arsProviderRequiresLoadedAuditedRuntimeVersion() {
        assertTrue(ClassRulesReloader.providerGameplayAvailable(
            "ars_nouveau", true, ArsNouveauVersionContract.SUPPORTED_VERSION));
        assertFalse(ClassRulesReloader.providerGameplayAvailable(
            "ars_nouveau", true, ""));
        assertFalse(ClassRulesReloader.providerGameplayAvailable(
            "ars_nouveau", true, "unsupported"));
        assertFalse(ClassRulesReloader.providerGameplayAvailable(
            "ars_nouveau", false, ArsNouveauVersionContract.SUPPORTED_VERSION));
    }

    @Test
    void unrelatedProviderKeepsPresenceOnlySemantics() {
        assertTrue(ClassRulesReloader.providerGameplayAvailable("create", true, ""));
        assertTrue(ClassRulesReloader.providerGameplayAvailable("ae2", true, ""));
        assertFalse(ClassRulesReloader.providerGameplayAvailable("create", false, ""));
    }
}
