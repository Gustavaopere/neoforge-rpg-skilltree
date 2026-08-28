package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Canonical pure inputs for one world/entity scaling resolution. */
public record WorldEntityScalingRequest(
    TerritoryKey territoryKey,
    NativeAreaLevelPlan nativeAreaPlan,
    List<RelevantPlayerCandidate> playerCandidates,
    RelevantPlayerFilter playerFilter,
    RelevantPlayerLevelPolicy playerLevelPolicy,
    EntityArchetype archetype,
    EntityLevelAdjustment adjustment,
    CanonicalStatSnapshot providerStats,
    Map<EntityArchetype, EntityArchetypeStatPolicy> archetypeStatPolicies
) {
    public WorldEntityScalingRequest {
        Objects.requireNonNull(territoryKey, "territoryKey");
        Objects.requireNonNull(nativeAreaPlan, "nativeAreaPlan");
        Objects.requireNonNull(playerCandidates, "playerCandidates");
        Objects.requireNonNull(playerFilter, "playerFilter");
        Objects.requireNonNull(playerLevelPolicy, "playerLevelPolicy");
        Objects.requireNonNull(archetype, "archetype");
        Objects.requireNonNull(adjustment, "adjustment");
        Objects.requireNonNull(providerStats, "providerStats");
        Objects.requireNonNull(archetypeStatPolicies, "archetypeStatPolicies");
        playerCandidates = List.copyOf(playerCandidates);
        archetypeStatPolicies = Map.copyOf(archetypeStatPolicies);
    }
}
