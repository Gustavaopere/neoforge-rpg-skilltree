package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public final class EntityArchetypeStatScalingTest {
    public static void main(String[] args) {
        archetypePolicyRoutesThroughCanonicalEffectiveStats();
        hugeEntityLevelsRemainRepresentable();
        missingOrIncompletePoliciesFailExplicitly();
        System.out.println("EntityArchetypeStatScalingTest: PASS");
    }

    private static void archetypePolicyRoutesThroughCanonicalEffectiveStats() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(
            health, new BigDecimal("20"),
            damage, new BigDecimal("5")
        ));
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(12L, 50L, EntityArchetype.HOSTILE),
            new EntityLevelAdjustment(3L, 2L)
        );

        EntityArchetypeStatPolicy hostile = context -> {
            eq(EntityArchetype.HOSTILE, context.archetype());
            eq(55L, context.entityLevel());
            eq(provider, context.providerStats());
            return Map.of(
                health, stat -> stat.providerValue().add(BigDecimal.valueOf(context.entityLevel())),
                damage, stat -> stat.providerValue().multiply(new BigDecimal("1.5"))
            );
        };

        EntityStatScalingResult result = EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.HOSTILE, hostile)
        );

        eq(level, result.levelResolution());
        eq(EntityArchetype.HOSTILE, result.archetype());
        eq(55L, result.entityLevel());
        eq(55L, result.effectiveStats().progressionLevel());
        decimalEq("75", result.effectiveStats().value(health));
        decimalEq("7.5", result.effectiveStats().value(damage));
    }

    private static void hugeEntityLevelsRemainRepresentable() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("40")));
        long hugeLevel = 5_000_000_000L;
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(hugeLevel, EntityArchetype.BOSS),
            EntityLevelAdjustment.NONE
        );

        EntityStatScalingResult result = EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.BOSS, context -> {
                eq(hugeLevel, context.entityLevel());
                return Map.of(health, stat -> stat.providerValue());
            })
        );

        eq(hugeLevel, result.entityLevel());
        decimalEq("40", result.effectiveStats().value(health));
    }

    private static void missingOrIncompletePoliciesFailExplicitly() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey defense = CanonicalStatKey.of("rpgskilltree:defense");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(
            health, new BigDecimal("20"),
            defense, new BigDecimal("3")
        ));
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(10L, EntityArchetype.PASSIVE),
            EntityLevelAdjustment.NONE
        );

        expect(IllegalStateException.class, () -> EntityStatScalingService.resolve(level, provider, Map.of()));
        expect(IllegalStateException.class, () -> EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.PASSIVE, context -> null)
        ));
        expect(IllegalStateException.class, () -> EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.PASSIVE, context -> Map.of(
                health, stat -> stat.providerValue()
            ))
        ));
    }

    private static void decimalEq(String expected, BigDecimal actual) {
        BigDecimal expectedDecimal = new BigDecimal(expected);
        if (expectedDecimal.compareTo(actual) != 0) {
            throw new AssertionError(expectedDecimal + " != " + actual);
        }
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
