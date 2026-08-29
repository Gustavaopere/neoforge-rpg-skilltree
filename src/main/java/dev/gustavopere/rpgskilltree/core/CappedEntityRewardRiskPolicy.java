package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Composes the configured reward curve with explicit rarity and boss risk multipliers,
 * then applies a final cap so risk rewards cannot grow without bound.
 */
public final class CappedEntityRewardRiskPolicy implements EntityRewardRiskPolicy {
    private static final BigDecimal ONE = BigDecimal.ONE;

    private final CappedLinearScalingCurve rewardCurve;
    private final Map<MobRarityKey, BigDecimal> rarityMultipliers;
    private final BigDecimal bossMultiplier;
    private final BigDecimal maximumMultiplier;

    private CappedEntityRewardRiskPolicy(
        CappedLinearScalingCurve rewardCurve,
        Map<MobRarityKey, BigDecimal> rarityMultipliers,
        BigDecimal bossMultiplier,
        BigDecimal maximumMultiplier
    ) {
        this.rewardCurve = rewardCurve;
        this.rarityMultipliers = rarityMultipliers;
        this.bossMultiplier = bossMultiplier;
        this.maximumMultiplier = maximumMultiplier;
    }

    public static CappedEntityRewardRiskPolicy of(
        ScalingCurveSet curves,
        Map<MobRarityKey, BigDecimal> rarityMultipliers,
        BigDecimal bossMultiplier,
        BigDecimal maximumMultiplier
    ) {
        Objects.requireNonNull(curves, "curves");
        Objects.requireNonNull(rarityMultipliers, "rarityMultipliers");
        requireNonNegative(Objects.requireNonNull(bossMultiplier, "bossMultiplier"), "bossMultiplier");
        requireNonNegative(Objects.requireNonNull(maximumMultiplier, "maximumMultiplier"), "maximumMultiplier");

        HashMap<MobRarityKey, BigDecimal> rarityCopy = new HashMap<>();
        for (Map.Entry<MobRarityKey, BigDecimal> entry : rarityMultipliers.entrySet()) {
            MobRarityKey rarity = Objects.requireNonNull(entry.getKey(), "rarity key");
            BigDecimal multiplier = Objects.requireNonNull(entry.getValue(), "rarity multiplier");
            requireNonNegative(multiplier, "rarity multiplier for " + rarity.serializedId());
            rarityCopy.put(rarity, multiplier);
        }

        return new CappedEntityRewardRiskPolicy(
            curves.curve(ScalingCurveFamily.REWARD),
            Map.copyOf(rarityCopy),
            bossMultiplier,
            maximumMultiplier
        );
    }

    @Override
    public BigDecimal multiplier(EntityScalingState state) {
        Objects.requireNonNull(state, "state");

        BigDecimal levelMultiplier = rewardCurve.multiplier(state.entityLevel());
        if (levelMultiplier.signum() < 0) {
            throw new IllegalStateException("reward curve produced a negative multiplier");
        }

        BigDecimal rarityMultiplier = state.rarity()
            .map(selection -> rarityMultiplier(selection.rarity()))
            .orElse(ONE);
        BigDecimal archetypeMultiplier = state.archetype() == EntityArchetype.BOSS ? bossMultiplier : ONE;

        BigDecimal resolved = levelMultiplier.multiply(rarityMultiplier).multiply(archetypeMultiplier);
        return resolved.compareTo(maximumMultiplier) > 0 ? maximumMultiplier : resolved;
    }

    private BigDecimal rarityMultiplier(MobRarityKey rarity) {
        BigDecimal multiplier = rarityMultipliers.get(rarity);
        if (multiplier == null) {
            throw new IllegalStateException("missing reward multiplier for rarity: " + rarity.serializedId());
        }
        return multiplier;
    }

    private static void requireNonNegative(BigDecimal value, String label) {
        if (value.signum() < 0) {
            throw new IllegalArgumentException(label + " must be non-negative");
        }
    }
}
