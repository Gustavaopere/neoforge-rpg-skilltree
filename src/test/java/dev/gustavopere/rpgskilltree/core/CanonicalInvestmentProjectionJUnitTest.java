package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

final class CanonicalInvestmentProjectionJUnitTest {
    @Test
    void canonicalProjectionContract() {
        assertDoesNotThrow(() -> CanonicalInvestmentProjectionContract.main(new String[0]));
    }
}
