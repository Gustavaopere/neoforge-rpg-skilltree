package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Immutable server-side inputs for one complete initial entity-scaling decision. */
public record EntityScalingDecisionRequest(
    TerritoryKey territoryKey,
    NativeAreaLevelPlan nativeAreaPlan,
    List<RelevantPlayerCandidate> playerCandidates,
    RelevantPlayerFilter playerFilter,
    RelevantPlayerLevelPolicy playerLevelPolicy,
    EntityArchetype archetype,
    long variance,
    long deterministicSeed,
    MobRarityPolicy rarityPolicy,
    CanonicalStatSnapshot providerStats,
    Map<EntityArchetype, EntityArchetypeStatPolicy> archetypeStatPolicies,
    MobAffixPolicy affixPolicy,
    EntityBehaviorPolicy behaviorPolicy
) {
    public EntityScalingDecisionRequest {
        Objects.requireNonNull(territoryKey, "territoryKey");
        Objects.requireNonNull(nativeAreaPlan, "nativeAreaPlan");
        Objects.requireNonNull(playerCandidates, "playerCandidates");
        Objects.requireNonNull(playerFilter, "playerFilter");
        Objects.requireNonNull(playerLevelPolicy, "playerLevelPolicy");
        Objects.requireNonNull(archetype, "archetype");
        Objects.requireNonNull(rarityPolicy, "rarityPolicy");
        Objects.requireNonNull(providerStats, "providerStats");
        Objects.requireNonNull(archetypeStatPolicies, "archetypeStatPolicies");
        Objects.requireNonNull(affixPolicy, "affixPolicy");
        Objects.requireNonNull(behaviorPolicy, "behaviorPolicy");
        playerCandidates = List.copyOf(playerCandidates);
        archetypeStatPolicies = Map.copyOf(archetypeStatPolicies);
    }
}
