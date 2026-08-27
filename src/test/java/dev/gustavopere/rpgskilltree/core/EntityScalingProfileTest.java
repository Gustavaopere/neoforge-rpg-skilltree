package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public final class EntityScalingProfileTest {
    public static void main(String[] args) {
        profilesAreArchetypeSpecificAndImmutable();
        passiveProfileCanScaleDefenseWithoutScalingOffense();
        hostileProfileCanUseEntityLevelAtHugeValues();
        missingRequiredProviderStatFailsExplicitly();
        System.out.println("EntityScalingProfileTest: PASS");
    }

    private static void profilesAreArchetypeSpecificAndImmutable() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        EntityScalingProfile profile = EntityScalingProfile.of(
            EntityArchetype.VILLAGER,
            Map.of(health, context -> context.providerValue())
        );
        eq(EntityArchetype.VILLAGER, profile.archetype());
        eq(1, profile.policies().size());
        expect(UnsupportedOperationException.class, () -> profile.policies().put(
            CanonicalStatKey.of("rpgskilltree:attack_damage"),
            context -> context.providerValue()
        ));
        expect(IllegalArgumentException.class, () -> EntityScalingProfile.of(EntityArchetype.SPECIAL, Map.of()));
    }

    private static void passiveProfileCanScaleDefenseWithoutScalingOffense() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey defense = CanonicalStatKey.of("rpgskilltree:defense");
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(
            health, new BigDecimal("10"),
            defense, new BigDecimal("2"),
            damage, new BigDecimal("4")
        ));
        EntityScalingProfile profile = EntityScalingProfile.of(
            EntityArchetype.PASSIVE,
            Map.of(
                health, context -> context.providerValue().multiply(new BigDecimal("2")),
                defense, context -> context.providerValue().add(BigDecimal.ONE)
            )
        );

        EntityScalingResult result = EntityScalingService.resolve(50L, provider, profile);
        eq(EntityArchetype.PASSIVE, result.archetype());
        eq(50L, result.entityLevel());
        decimalEq("20", result.effectiveStats().value(health));
        decimalEq("3", result.effectiveStats().value(defense));
        eq(false, result.effectiveStats().values().containsKey(damage));
    }

    private static void hostileProfileCanUseEntityLevelAtHugeValues() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        long huge = 5_000_000_000L;
        EntityScalingProfile profile = EntityScalingProfile.of(
            EntityArchetype.HOSTILE,
            Map.of(
                health, context -> {
                    eq(huge, context.progressionLevel());
                    return context.providerValue();
                },
                damage, context -> context.providerValue()
            )
        );
        EntityScalingResult result = EntityScalingService.resolve(
            huge,
            CanonicalStatSnapshot.of(Map.of(
                health, new BigDecimal("40"),
                damage, new BigDecimal("8")
            )),
            profile
        );
        eq(huge, result.effectiveStats().progressionLevel());
    }

    private static void missingRequiredProviderStatFailsExplicitly() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey defense = CanonicalStatKey.of("rpgskilltree:defense");
        EntityScalingProfile profile = EntityScalingProfile.of(
            EntityArchetype.CIVILIAN,
            Map.of(
                health, context -> context.providerValue(),
                defense, context -> context.providerValue()
            )
        );
        expect(IllegalArgumentException.class, () -> EntityScalingService.resolve(
            10L,
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            profile
        ));
        expect(IllegalArgumentException.class, () -> EntityScalingService.resolve(
            -1L,
            CanonicalStatSnapshot.of(Map.of(
                health, new BigDecimal("20"),
                defense, new BigDecimal("2")
            )),
            profile
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
