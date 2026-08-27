package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CanonicalEffectiveStatsFoundationTest {
    public static void main(String[] args) {
        canonicalKeysAreStableNamespacedIds();
        providerSnapshotKeepsExactCanonicalValues();
        effectivePoliciesAreExplicitAndSupportHugeProgressionLevels();
        missingOrInvalidResolutionNeverFailsSilently();
        System.out.println("CanonicalEffectiveStatsFoundationTest: PASS");
    }

    private static void canonicalKeysAreStableNamespacedIds() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        eq("rpgskilltree:max_health", health.serializedId());
        eq("rpgskilltree", health.namespace());
        eq("max_health", health.path());
        eq(health, CanonicalStatKey.of(health.serializedId()));

        expect(IllegalArgumentException.class, () -> CanonicalStatKey.of("max_health"));
        expect(IllegalArgumentException.class, () -> CanonicalStatKey.of("RpgSkillTree:max_health"));
        expect(IllegalArgumentException.class, () -> CanonicalStatKey.of("rpgskilltree:MaxHealth"));
        expect(IllegalArgumentException.class, () -> CanonicalStatKey.of("rpgskilltree:"));
        expect(IllegalArgumentException.class, () -> CanonicalStatKey.of(":max_health"));
        expect(IllegalArgumentException.class, () -> CanonicalStatKey.of("rpgskilltree:max health"));
    }

    private static void providerSnapshotKeepsExactCanonicalValues() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey luck = CanonicalStatKey.of("rpgskilltree:luck");
        Map<CanonicalStatKey, BigDecimal> source = new HashMap<>();
        source.put(health, new BigDecimal("20.000"));
        source.put(luck, new BigDecimal("-1.5"));

        CanonicalStatSnapshot snapshot = CanonicalStatSnapshot.of(source);
        decimalEq("20.000", snapshot.value(health));
        decimalEq("-1.5", snapshot.value(luck));
        eq(2, snapshot.values().size());

        source.put(health, new BigDecimal("999"));
        decimalEq("20.000", snapshot.value(health));
        expect(IllegalArgumentException.class, () -> snapshot.value(CanonicalStatKey.of("rpgskilltree:missing")));
        expect(UnsupportedOperationException.class, () -> snapshot.values().put(health, BigDecimal.ONE));
    }

    private static void effectivePoliciesAreExplicitAndSupportHugeProgressionLevels() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(
            health, new BigDecimal("40"),
            damage, new BigDecimal("12.5")
        ));
        long hugeLevel = 5_000_000_000L;

        Map<CanonicalStatKey, EffectiveStatPolicy> policies = Map.of(
            health, context -> {
                eq(hugeLevel, context.progressionLevel());
                eq(health, context.statKey());
                return context.providerValue().multiply(new BigDecimal("1.25"));
            },
            damage, context -> context.providerValue().add(new BigDecimal("2.5"))
        );

        RpgEffectiveStats effective = RpgEffectiveStatsService.resolve(hugeLevel, provider, policies);
        eq(hugeLevel, effective.progressionLevel());
        decimalEq("50", effective.value(health));
        decimalEq("15.0", effective.value(damage));
        eq(2, effective.values().size());
        expect(UnsupportedOperationException.class, () -> effective.values().put(health, BigDecimal.ZERO));
    }

    private static void missingOrInvalidResolutionNeverFailsSilently() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey defense = CanonicalStatKey.of("rpgskilltree:defense");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(
            health, new BigDecimal("20"),
            defense, new BigDecimal("3")
        ));

        expect(IllegalStateException.class, () -> RpgEffectiveStatsService.resolve(
            100L,
            provider,
            Map.of(health, context -> context.providerValue())
        ));
        expect(IllegalStateException.class, () -> RpgEffectiveStatsService.resolve(
            100L,
            provider,
            Map.of(
                health, context -> context.providerValue(),
                defense, context -> null
            )
        ));
        expect(IllegalArgumentException.class, () -> RpgEffectiveStatsService.resolve(
            -1L,
            provider,
            Map.of(
                health, context -> context.providerValue(),
                defense, context -> context.providerValue()
            )
        ));
        expect(IllegalArgumentException.class, () -> CanonicalStatSnapshot.of(Map.of()));
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
