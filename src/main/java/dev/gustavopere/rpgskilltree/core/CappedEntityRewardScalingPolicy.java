package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Resolves a bounded reward-risk multiplier from independent level, archetype and rarity factors.
 *
 * <p>This core policy intentionally does not award XP or mutate loot. It produces one auditable
 * multiplier that runtime adapters may consume without coupling reward growth to health, damage,
 * defense or utility curves.</p>
 */
public final class CappedEntityRewardScalingPolicy {
    private final ScalingCurveSet curves;
    private final Map<EntityArchetype, BigDecimal> archetypeMultipliers;
    private final Map<MobRarityKey, BigDecimal> rarityMultipliers;
    private final BigDecimal noRarityMultiplier;
    private final BigDecimal maximumMultiplier;

    private CappedEntityRewardScalingPolicy(
        ScalingCurveSet curves,
        Map<EntityArchetype, BigDecimal> archetypeMultipliers,
        Map<MobRarityKey, BigDecimal> rarityMultipliers,
        BigDecimal noRarityMultiplier,
        BigDecimal maximumMultiplier
    ) {
        this.curves = curves;
        this.archetypeMultipliers = archetypeMultipliers;
        this.rarityMultipliers = rarityMultipliers;
        this.noRarityMultiplier = noRarityMultiplier;
        this.maximumMultiplier = maximumMultiplier;
    }

    public static CappedEntityRewardScalingPolicy of(
        ScalingCurveSet curves,
        Map<EntityArchetype, BigDecimal> archetypeMultipliers,
        Map<MobRarityKey, BigDecimal> rarityMultipliers,
        BigDecimal noRarityMultiplier,
        BigDecimal maximumMultiplier
    ) {
        Objects.requireNonNull(curves, "curves");
        Objects.requireNonNull(archetypeMultipliers, "archetypeMultipliers");
        Objects.requireNonNull(rarityMultipliers, "rarityMultipliers");
        noRarityMultiplier = requireNonNegative(noRarityMultiplier, "noRarityMultiplier");
        maximumMultiplier = requireNonNegative(maximumMultiplier, "maximumMultiplier");
        if (maximumMultiplier.signum() == 0) {
            throw new IllegalArgumentException("maximumMultiplier must be positive");
        }

        EnumMap<EntityArchetype, BigDecimal> archetypeCopy = new EnumMap<>(EntityArchetype.class);
        for (EntityArchetype archetype : EntityArchetype.values()) {
            BigDecimal multiplier = archetypeMultipliers.get(archetype);
            if (multiplier == null) {
                throw new IllegalArgumentException("missing reward archetype multiplier: " + archetype);
            }
            archetypeCopy.put(archetype, requireNonNegative(multiplier, "archetype multiplier"));
        }
        if (archetypeMultipliers.size() != EntityArchetype.values().length) {
            throw new IllegalArgumentException("reward archetype multipliers must contain exactly one entry per archetype");
        }

        HashMap<MobRarityKey, BigDecimal> rarityCopy = new HashMap<>();
        for (Map.Entry<MobRarityKey, BigDecimal> entry : rarityMultipliers.entrySet()) {
            MobRarityKey key = Objects.requireNonNull(entry.getKey(), "rarity key");
            rarityCopy.put(key, requireNonNegative(entry.getValue(), "rarity multiplier"));
        }

        return new CappedEntityRewardScalingPolicy(
            curves,
            Map.copyOf(archetypeCopy),
            Map.copyOf(rarityCopy),
            noRarityMultiplier,
            maximumMultiplier
        );
    }

    public EntityRewardScalingResult resolve(EntityRewardScalingContext context) {
        Objects.requireNonNull(context, "context");

        BigDecimal levelMultiplier = curves.curve(ScalingCurveFamily.REWARD).multiplier(context.entityLevel());
        BigDecimal archetypeMultiplier = archetypeMultipliers.get(context.archetype());
        if (archetypeMultiplier == null) {
            throw new IllegalStateException("missing reward archetype multiplier: " + context.archetype());
        }

        BigDecimal rarityMultiplier = context.rarity()
            .map(MobRaritySelection::rarity)
            .map(rarity -> {
                BigDecimal multiplier = rarityMultipliers.get(rarity);
                if (multiplier == null) {
                    throw new IllegalStateException("missing reward rarity multiplier: " + rarity.serializedId());
                }
                return multiplier;
            })
            .orElse(noRarityMultiplier);

        BigDecimal uncapped = levelMultiplier
            .multiply(archetypeMultiplier)
            .multiply(rarityMultiplier);
        BigDecimal bounded = uncapped.min(maximumMultiplier);
        return new EntityRewardScalingResult(
            levelMultiplier,
            archetypeMultiplier,
            rarityMultiplier,
            uncapped,
            bounded
        );
    }

    private static BigDecimal requireNonNegative(BigDecimal value, String label) {
        Objects.requireNonNull(value, label);
        if (value.signum() < 0) {
            throw new IllegalArgumentException(label + " must be non-negative");
        }
        return value;
    }
}
