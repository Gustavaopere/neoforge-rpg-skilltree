package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Builds the complete Effective Stat policy map for one entity snapshot from explicit stat-family
 * routing and an explicitly supplied curve set.
 */
public final class CurveBackedEntityArchetypeStatPolicy implements EntityArchetypeStatPolicy {
    private final CanonicalStatScalingFamilyCatalog familyCatalog;
    private final ScalingCurveSet curveSet;

    private CurveBackedEntityArchetypeStatPolicy(
        CanonicalStatScalingFamilyCatalog familyCatalog,
        ScalingCurveSet curveSet
    ) {
        this.familyCatalog = familyCatalog;
        this.curveSet = curveSet;
    }

    public static CurveBackedEntityArchetypeStatPolicy of(
        CanonicalStatScalingFamilyCatalog familyCatalog,
        ScalingCurveSet curveSet
    ) {
        return new CurveBackedEntityArchetypeStatPolicy(
            Objects.requireNonNull(familyCatalog, "familyCatalog"),
            Objects.requireNonNull(curveSet, "curveSet")
        );
    }

    @Override
    public Map<CanonicalStatKey, EffectiveStatPolicy> policiesFor(EntityStatScalingContext context) {
        Objects.requireNonNull(context, "context");
        HashMap<CanonicalStatKey, EffectiveStatPolicy> policies = new HashMap<>();
        for (CanonicalStatKey key : context.providerStats().values().keySet()) {
            ScalingCurveFamily family = familyCatalog.family(key);
            policies.put(key, EffectiveStatCurvePolicy.of(curveSet.curve(family)));
        }
        return Map.copyOf(policies);
    }
}
