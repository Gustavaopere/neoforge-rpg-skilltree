package dev.gustavopere.rpgskilltree.core;

import java.util.Optional;
import java.util.OptionalLong;

/** Canonical causal pipeline for one complete, persistable initial entity-scaling decision. */
public final class EntityScalingDecisionService {
    private EntityScalingDecisionService() {}

    public static EntityScalingDecisionResult resolve(EntityScalingDecisionRequest request) {
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

        MobRaritySelection rarity = MobRarityService.resolve(
            new MobRarityContext(levelContext, request.deterministicSeed()),
            request.rarityPolicy()
        );
        EntityLevelResolution entityLevel = EntityLevelService.resolve(
            levelContext,
            new EntityLevelAdjustment(request.variance(), rarity.levelBonus())
        );
        EntityStatScalingResult scaledStats = EntityStatScalingService.resolve(
            entityLevel,
            request.providerStats(),
            request.archetypeStatPolicies()
        );
        MobAffixSelection affixes = MobAffixService.resolve(
            new MobAffixContext(scaledStats, rarity, request.deterministicSeed()),
            request.affixPolicy()
        );
        EntityBehaviorSelection behaviors = EntityBehaviorService.resolve(
            new EntityBehaviorContext(scaledStats, rarity, affixes, request.deterministicSeed()),
            request.behaviorPolicy()
        );

        EntityScalingState state = new EntityScalingState(
            request.territoryKey(),
            entityLevel,
            request.variance(),
            Optional.of(rarity),
            request.deterministicSeed(),
            Optional.of(EntityEffectiveStatsSnapshot.from(scaledStats.effectiveStats())),
            affixes,
            behaviors
        );
        return new EntityScalingDecisionResult(
            nativeArea,
            relevantPlayers,
            rarity,
            scaledStats,
            affixes,
            behaviors,
            state
        );
    }
}
