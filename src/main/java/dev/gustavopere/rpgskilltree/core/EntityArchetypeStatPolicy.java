package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** Selects canonical effective-stat policies for one entity archetype/context. */
@FunctionalInterface
public interface EntityArchetypeStatPolicy {
    Map<CanonicalStatKey, EffectiveStatPolicy> policiesFor(EntityStatScalingContext context);
}
