package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;

/** Routes a resolved entity level through its archetype policy and canonical Effective Stats service. */
public final class EntityStatScalingService {
    private EntityStatScalingService() {}

    public static EntityStatScalingResult resolve(
        EntityLevelResolution levelResolution,
        CanonicalStatSnapshot providerStats,
        Map<EntityArchetype, EntityArchetypeStatPolicy> archetypePolicies
    ) {
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(providerStats, "providerStats");
        Objects.requireNonNull(archetypePolicies, "archetypePolicies");

        EntityArchetypeStatPolicy archetypePolicy = archetypePolicies.get(levelResolution.archetype());
        if (archetypePolicy == null) {
            throw new IllegalStateException("missing entity archetype stat policy: " + levelResolution.archetype());
        }

        EntityStatScalingContext context = new EntityStatScalingContext(levelResolution, providerStats);
        Map<CanonicalStatKey, EffectiveStatPolicy> policies = archetypePolicy.policiesFor(context);
        if (policies == null) {
            throw new IllegalStateException("entity archetype stat policy returned null: " + levelResolution.archetype());
        }

        RpgEffectiveStats effective = RpgEffectiveStatsService.resolve(
            levelResolution.finalLevel(),
            providerStats,
            policies
        );
        return new EntityStatScalingResult(levelResolution, effective);
    }
}
