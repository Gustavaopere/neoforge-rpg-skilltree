package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class CanonicalProviderBindingCatalogTest {
    public static void main(String[] args) {
        catalogIsDeterministicAndGroupedByCanonicalStat();
        emptyCatalogIsValidAndMissingResolutionFailsClosed();
        duplicateBindingIdsAreRejectedGlobally();
        catalogDelegatesResolutionWithoutChoosingPrecedence();
        catalogViewsAreImmutable();
        System.out.println("CanonicalProviderBindingCatalogTest: PASS");
    }

    private static void catalogIsDeterministicAndGroupedByCanonicalStat() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        CanonicalProviderBinding zHealth = CanonicalProviderBinding.of(
            "rpgskilltree:z_health", health, "example:z_health"
        );
        CanonicalProviderBinding aHealth = CanonicalProviderBinding.of(
            "rpgskilltree:a_health", health, "minecraft:max_health"
        );
        CanonicalProviderBinding damageBinding = CanonicalProviderBinding.of(
            "rpgskilltree:damage", damage, "minecraft:attack_damage"
        );

        CanonicalProviderBindingCatalog catalog = CanonicalProviderBindingCatalog.of(
            List.of(zHealth, damageBinding, aHealth)
        );

        eq(List.of(aHealth, damageBinding, zHealth), catalog.allBindings());
        eq(List.of(aHealth, zHealth), catalog.definitions(health));
        eq(List.of(damageBinding), catalog.definitions(damage));
        eq(List.of(), catalog.definitions(CanonicalStatKey.of("rpgskilltree:defense")));
    }

    private static void emptyCatalogIsValidAndMissingResolutionFailsClosed() {
        CanonicalProviderBindingCatalog empty = CanonicalProviderBindingCatalog.empty();
        eq(List.of(), empty.allBindings());
        eq(List.of(), empty.definitions(CanonicalStatKey.of("rpgskilltree:max_health")));
        expect(IllegalStateException.class, () -> empty.resolve(
            CanonicalStatKey.of("rpgskilltree:max_health"),
            binding -> true,
            (stat, available) -> available.get(0)
        ));
    }

    private static void duplicateBindingIdsAreRejectedGlobally() {
        CanonicalProviderBinding first = CanonicalProviderBinding.of(
            "rpgskilltree:duplicate",
            CanonicalStatKey.of("rpgskilltree:max_health"),
            "minecraft:max_health"
        );
        CanonicalProviderBinding second = CanonicalProviderBinding.of(
            "rpgskilltree:duplicate",
            CanonicalStatKey.of("rpgskilltree:defense"),
            "minecraft:armor"
        );
        expect(IllegalArgumentException.class, () -> CanonicalProviderBindingCatalog.of(List.of(first, second)));
    }

    private static void catalogDelegatesResolutionWithoutChoosingPrecedence() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalProviderBinding vanilla = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_health", health, "minecraft:max_health"
        );
        CanonicalProviderBinding provider = CanonicalProviderBinding.of(
            "rpgskilltree:provider_health", health, "example:max_health"
        );
        CanonicalProviderBindingCatalog catalog = CanonicalProviderBindingCatalog.of(List.of(vanilla, provider));

        CanonicalProviderBindingResolution resolution = catalog.resolve(
            health,
            binding -> true,
            (stat, available) -> available.stream()
                .filter(provider::equals)
                .findFirst()
                .orElseThrow()
        );
        eq(provider, resolution.requireSelected());

        expect(IllegalStateException.class, () -> catalog.resolve(
            health,
            binding -> true,
            (stat, available) -> null
        ));
    }

    private static void catalogViewsAreImmutable() {
        CanonicalStatKey speed = CanonicalStatKey.of("rpgskilltree:movement_speed");
        CanonicalProviderBinding binding = CanonicalProviderBinding.of(
            "rpgskilltree:speed", speed, "minecraft:movement_speed"
        );
        CanonicalProviderBindingCatalog catalog = CanonicalProviderBindingCatalog.of(List.of(binding));
        expect(UnsupportedOperationException.class, () -> catalog.allBindings().clear());
        expect(UnsupportedOperationException.class, () -> catalog.definitions(speed).add(binding));
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
