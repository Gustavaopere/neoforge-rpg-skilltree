package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class ArsNouveauVersionContractJUnitTest {
    @Test
    void exactPackVersionIsTheOnlySupportedProviderContract() {
        assertTrue(ArsNouveauVersionContract.supports("5.13.1"));
        assertTrue(ArsNouveauVersionContract.supports(" 5.13.1 "));

        assertFalse(ArsNouveauVersionContract.supports("5.13.0"));
        assertFalse(ArsNouveauVersionContract.supports("5.14.0"));
        assertFalse(ArsNouveauVersionContract.supports("unknown"));
        assertFalse(ArsNouveauVersionContract.supports("absent"));
        assertFalse(ArsNouveauVersionContract.supports(null));
        assertFalse(ArsNouveauVersionContract.supports(""));
    }
}
