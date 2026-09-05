package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

final class MasteryInvestmentMetadataPolicyJUnitTest {
    @Test
    void canonicalInvestmentMetadataPolicyContract() {
        assertDoesNotThrow(() -> MasteryInvestmentMetadataPolicyTest.main(new String[0]));
    }
}
