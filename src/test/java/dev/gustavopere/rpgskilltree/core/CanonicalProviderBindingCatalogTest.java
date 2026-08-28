package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class CanonicalProviderBindingCatalogTest {
    public static void main(String[] args) {
        catalogIsOrderIndependentAndImmutable();
        bindingIdsAreGloballyUnique();
        missingStatsFailClosed();
        resolutionStillRequiresInjectedAvailabilityAndSelection();
        System.out.println("CanonicalProviderBindingCatalogTest: PASS");
    }

    private static void catalogIsOrderIndependentAndImmutable() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        CanonicalProviderBinding healthVanilla = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health", health, "minecraft:max_health"
        );
        CanonicalProviderBinding healthAlt = CanonicalProviderBinding.of(
            "rpgskilltree:alt_max_health", health, "example_attributes:max_health"
        );
        CanonicalProviderBinding damageVanilla = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_attack_damage", damage, "minecraft:attack_damage"
        );

        CanonicalProviderBindingCatalog first = CanonicalProviderBindingCatalog.of(
            List.of(healthAlt, damageVanilla, healthVanilla)
        );
        CanonicalProviderBindingCatalog second = CanonicalProviderBindingCatalog.of(
            List.of(healthVanilla, healthAlt, damageVanilla)
        );

        eq(first.fingerprint(), second.fingerprint());
        eq(List.of(healthAlt, healthVanilla), first.definitionsFor(health));
        eq(List.of(damageVanilla), first.definitionsFor(damage));
        eq(healthVanilla, first.requireBinding("rpgskilltree:vanilla_max_health"));
        expect(UnsupportedOperationException.class, () -> first.definitions().clear());
        expect(UnsupportedOperationException.class, () -> first.definitionsFor(health).add(healthAlt));
    }

    private static void bindingIdsAreGloballyUnique() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatKey defense = CanonicalStatKey.of("rpgskilltree:defense");
        CanonicalProviderBinding first = CanonicalProviderBinding.of(
            "rpgskilltree:shared", health, "minecraft:max_health"
        );
        CanonicalProviderBinding duplicateAcrossAnotherStat = CanonicalProviderBinding.of(
            "rpgskilltree:shared", defense, "minecraft:armor"
        );

        expect(IllegalArgumentException.class, () -> CanonicalProviderBindingCatalog.of(
            List.of(first, duplicateAcrossAnotherStat)
        ));
    }

    private static void missingStatsFailClosed() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalProviderBinding binding = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health", health, "minecraft:max_health"
        );
        CanonicalProviderBindingCatalog catalog = CanonicalProviderBindingCatalog.of(List.of(binding));

        expect(IllegalStateException.class, () -> catalog.definitionsFor(
            CanonicalStatKey.of("rpgskilltree:missing")
        ));
        expect(IllegalArgumentException.class, () -> catalog.requireBinding("rpgskilltree:missing"));
        expect(IllegalArgumentException.class, () -> CanonicalProviderBindingCatalog.of(List.of()));
    }

    private static void resolutionStillRequiresInjectedAvailabilityAndSelection() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalProviderBinding vanilla = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health", health, "minecraft:max_health"
        );
        CanonicalProviderBinding alternate = CanonicalProviderBinding.of(
            "rpgskilltree:alternate_max_health", health, "example_attributes:max_health"
        );
        CanonicalProviderBindingCatalog catalog = CanonicalProviderBindingCatalog.of(List.of(vanilla, alternate));

        CanonicalProviderBindingResolution resolved = catalog.resolve(
            health,
            binding -> binding.equals(alternate),
            (stat, available) -> available.get(0)
        );
        eq(alternate, resolved.requireSelected());

        CanonicalProviderBindingResolution unavailable = catalog.resolve(
            health,
            binding -> false,
            (stat, available) -> { throw new AssertionError("selection must not run"); }
        );
        eq(false, unavailable.isResolved());
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
