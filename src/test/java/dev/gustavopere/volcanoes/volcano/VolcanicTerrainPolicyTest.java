package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicTerrainPolicyTest {
    @Test
    void bombTerrainMutationRequiresExplicitProtectionAuthorityAndNaturalSafeTarget() {
        VolcanicBombImpactPolicy policy = VolcanicBombImpactPolicy.safeDefaults();

        assertFalse(policy.canMutate(false, true, true, false, false));
        assertTrue(policy.canMutate(true, true, true, false, false));
        assertFalse(policy.canMutate(true, false, true, false, false));
        assertFalse(policy.canMutate(true, true, false, false, false));
        assertFalse(policy.canMutate(true, true, true, true, false));
        assertFalse(policy.canMutate(true, true, true, false, true));
    }

    @Test
    void pyroclasticTerrainMutationUsesTheSameFailClosedAuthorityBoundary() {
        PyroclasticTerrainPolicy policy = PyroclasticTerrainPolicy.safeDefaults();

        assertFalse(policy.canMutate(false, true, true, false, false));
        assertTrue(policy.canMutate(true, true, true, false, false));
        assertFalse(policy.canMutate(true, false, true, false, false));
        assertFalse(policy.canMutate(true, true, false, false, false));
        assertFalse(policy.canMutate(true, true, true, true, false));
        assertFalse(policy.canMutate(true, true, true, false, true));
    }
}
