package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/** Central fail-closed eligibility guard for any future opt-in seismic block damage. */
public final class SeismicDamageDecider {
    private final SeismicDamagePolicy policy;

    public SeismicDamageDecider(SeismicDamagePolicy policy) {
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    public boolean canDamageNaturalBlock(
            boolean naturalOrReplaceable,
            boolean protectedRegion,
            boolean hasBlockEntity
    ) {
        return policy.terrainDamage()
                && naturalOrReplaceable
                && !protectedRegion
                && !hasBlockEntity;
    }

    public boolean canDamageStructure(boolean protectedOrPlayerStructure) {
        return policy.structureDamage() && !protectedOrPlayerStructure;
    }
}
