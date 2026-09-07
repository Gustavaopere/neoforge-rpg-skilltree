package dev.gustavopere.rpgskilltree.runtime.compat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class EidolonProviderIdentityJUnitTest {
    @Test
    void eidolonRepraisedUsesInstalledRuntimeModId() {
        assertEquals("eidolon_repraised", OptionalIntegrations.Provider.EIDOLON.modId());
    }
}
