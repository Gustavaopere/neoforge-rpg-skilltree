package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RespirationPolicyTest {
    @Test
    void taggedFiltersRemainSeparateAndNeverCreateOxygen() {
        RespirationProtection particulate = TaggedRespirationProtectionProvider.fromMatches(true, false, false);
        assertEquals(1.0, particulate.particulateFilterEfficiency(), 1.0e-9);
        assertEquals(0.0, particulate.acidGasFilterEfficiency(), 1.0e-9);
        assertEquals(0.0, particulate.toxicGasFilterEfficiency(), 1.0e-9);
        assertEquals(0.0, particulate.oxygenSupplyPartialPressureAtm(), 1.0e-9);

        RespirationProtection allFilters = TaggedRespirationProtectionProvider.fromMatches(true, true, true);
        assertEquals(1.0, allFilters.particulateFilterEfficiency(), 1.0e-9);
        assertEquals(1.0, allFilters.acidGasFilterEfficiency(), 1.0e-9);
        assertEquals(1.0, allFilters.toxicGasFilterEfficiency(), 1.0e-9);
        assertEquals(0.0, allFilters.oxygenSupplyPartialPressureAtm(), 1.0e-9);
    }

    @Test
    void creativeAbilityOrTaggedSubjectsAreExemptThroughOnePolicyDecision() {
        assertFalse(RespirationSubjectPolicy.exempt(false, false));
        assertTrue(RespirationSubjectPolicy.exempt(true, false));
        assertTrue(RespirationSubjectPolicy.exempt(false, true));
        assertTrue(RespirationSubjectPolicy.exempt(true, true));
    }

    @Test
    void genericDamageInvulnerabilityDoesNotImplyRespiratoryExemption() {
        assertFalse(RespirationSubjectPolicy.exempt(false, false, false));
        assertFalse(RespirationSubjectPolicy.exempt(false, true, false));
        assertTrue(RespirationSubjectPolicy.exempt(true, false, false));
        assertTrue(RespirationSubjectPolicy.exempt(false, false, true));
    }

    @Test
    void coreTagIdsAreStableAndDoNotDefineAnOxygenSupplyTag() {
        assertEquals("volcanoes:respiration/particulate_filters", AtmosphereTags.PARTICULATE_FILTERS.location().toString());
        assertEquals("volcanoes:respiration/acid_gas_filters", AtmosphereTags.ACID_GAS_FILTERS.location().toString());
        assertEquals("volcanoes:respiration/toxic_gas_filters", AtmosphereTags.TOXIC_GAS_FILTERS.location().toString());
        assertEquals("volcanoes:respiration/does_not_breathe", AtmosphereTags.DOES_NOT_BREATHE.location().toString());
    }
}
