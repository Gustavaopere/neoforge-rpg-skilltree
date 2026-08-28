package dev.gustavopere.rpgskilltree.core;

import java.util.OptionalLong;

/** Canonical composition order for world threat, multiplayer floor, entity level and effective stats. */
public final class WorldEntityScalingService {
    private WorldEntityScalingService() {}

    public static WorldEntityScalingResult resolve(WorldEntityScalingRequest request) {
        NativeAreaLevelBreakdown nativeArea = NativeAreaThreatResolver.resolve(
            request.territoryKey(),
            request.nativeAreaPlan()
        );

        RelevantPlayerLevelResolution relevantPlayers = RelevantPlayerLevelResolver.resolve(
            request.playerCandidates(),
            request.playerFilter(),
            request.playerLevelPolicy()
        );

        OptionalLong relevantLevel = relevantPlayers.relevantPlayerLevel();
        EntityLevelContext levelContext = relevantLevel.isPresent()
            ? EntityLevelContext.withRelevantPlayer(nativeArea.resolvedLevel(), relevantLevel.getAsLong(), request.archetype())
            : EntityLevelContext.nativeOnly(nativeArea.resolvedLevel(), request.archetype());

        EntityLevelResolution entityLevel = EntityLevelService.resolve(levelContext, request.adjustment());
        EntityStatScalingResult stats = EntityStatScalingService.resolve(
            entityLevel,
            request.providerStats(),
            request.archetypeStatPolicies()
        );

        return new WorldEntityScalingResult(nativeArea, relevantPlayers, entityLevel, stats);
    }
}
