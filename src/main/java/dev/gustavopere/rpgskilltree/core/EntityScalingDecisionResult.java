package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Auditable output of the complete deterministic initial entity-scaling decision. */
public record EntityScalingDecisionResult(
    NativeAreaLevelBreakdown nativeArea,
    RelevantPlayerLevelResolution relevantPlayers,
    MobRaritySelection rarity,
    EntityStatScalingResult scaledStats,
    MobAffixSelection affixes,
    EntityBehaviorSelection behaviors,
    EntityScalingState state
) {
    public EntityScalingDecisionResult {
        Objects.requireNonNull(nativeArea, "nativeArea");
        Objects.requireNonNull(relevantPlayers, "relevantPlayers");
        Objects.requireNonNull(rarity, "rarity");
        Objects.requireNonNull(scaledStats, "scaledStats");
        Objects.requireNonNull(affixes, "affixes");
        Objects.requireNonNull(behaviors, "behaviors");
        Objects.requireNonNull(state, "state");
        if (!nativeArea.territoryKey().equals(state.territory())) {
            throw new IllegalArgumentException("native-area territory must match persisted entity scaling state");
        }
        if (!scaledStats.levelResolution().equals(state.levelResolution())) {
            throw new IllegalArgumentException("scaled stat level resolution must match persisted entity scaling state");
        }
        if (state.rarity().isEmpty() || !state.rarity().get().equals(rarity)) {
            throw new IllegalArgumentException("persisted rarity must match decision rarity");
        }
        Optional<EntityEffectiveStatsSnapshot> expectedEffectiveStats = Optional.of(
            EntityEffectiveStatsSnapshot.from(scaledStats.effectiveStats())
        );
        if (!state.effectiveStats().equals(expectedEffectiveStats)) {
            throw new IllegalArgumentException("persisted effective stats must match decision effective stats");
        }
        if (!state.affixes().equals(affixes)) {
            throw new IllegalArgumentException("persisted affixes must match decision affixes");
        }
        if (!state.behaviors().equals(behaviors)) {
            throw new IllegalArgumentException("persisted behaviors must match decision behaviors");
        }
    }
}
