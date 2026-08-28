package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class CanonicalProviderBindingFoundationTest {
    public static void main(String[] args) {
        bindingIdentityIsStableAndNamespaced();
        providerAvailabilityIsExplicit();
        ambiguousBindingsRequireInjectedSelectionPolicy();
        missingOrInvalidBindingsFailClosed();
        resolutionCollectionsAreImmutable();
        System.out.println("CanonicalProviderBindingFoundationTest: PASS");
    }

    private static void bindingIdentityIsStableAndNamespaced() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalProviderBinding binding = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health",
            health,
            "minecraft:max_health"
        );

        eq("rpgskilltree:vanilla_max_health", binding.bindingId());
        eq(health, binding.canonicalStat());
        eq("minecraft:max_health", binding.providerTargetId());
        eq(binding, CanonicalProviderBinding.of(
            binding.bindingId(),
            CanonicalStatKey.of("rpgskilltree:max_health"),
            binding.providerTargetId()
        ));

        expect(IllegalArgumentException.class, () -> CanonicalProviderBinding.of(
            "vanilla_max_health", health, "minecraft:max_health"
        ));
        expect(IllegalArgumentException.class, () -> CanonicalProviderBinding.of(
            "RpgSkillTree:vanilla_max_health", health, "minecraft:max_health"
        ));
        expect(IllegalArgumentException.class, () -> CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health", health, "max_health"
        ));
        expect(IllegalArgumentException.class, () -> CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health", health, "Minecraft:max_health"
        ));
    }

    private static void providerAvailabilityIsExplicit() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalProviderBinding vanilla = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_max_health", health, "minecraft:max_health"
        );
        CanonicalProviderBinding alternate = CanonicalProviderBinding.of(
            "rpgskilltree:alternate_max_health", health, "example_attributes:max_health"
        );

        CanonicalProviderBindingResolution resolution = CanonicalProviderBindingResolver.resolve(
            health,
            List.of(vanilla, alternate),
            binding -> binding.equals(alternate),
            (stat, available) -> {
                eq(health, stat);
                eq(List.of(alternate), available);
                return available.get(0);
            }
        );

        eq(List.of(vanilla, alternate), resolution.definitions());
        eq(List.of(alternate), resolution.availableBindings());
        eq(true, resolution.isResolved());
        eq(alternate, resolution.requireSelected());
    }

    private static void ambiguousBindingsRequireInjectedSelectionPolicy() {
        CanonicalStatKey damage = CanonicalStatKey.of("rpgskilltree:attack_damage");
        CanonicalProviderBinding vanilla = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_attack_damage", damage, "minecraft:attack_damage"
        );
        CanonicalProviderBinding provider = CanonicalProviderBinding.of(
            "rpgskilltree:provider_attack_damage", damage, "example_attributes:attack_damage"
        );

        CanonicalProviderBindingResolution chosen = CanonicalProviderBindingResolver.resolve(
            damage,
            List.of(vanilla, provider),
            binding -> true,
            (stat, available) -> available.get(1)
        );
        eq(provider, chosen.requireSelected());

        expect(IllegalStateException.class, () -> CanonicalProviderBindingResolver.resolve(
            damage,
            List.of(vanilla, provider),
            binding -> true,
            (stat, available) -> null
        ));
        expect(IllegalStateException.class, () -> CanonicalProviderBindingResolver.resolve(
            damage,
            List.of(vanilla, provider),
            binding -> true,
            (stat, available) -> CanonicalProviderBinding.of(
                "rpgskilltree:outside", damage, "other:attack_damage"
            )
        ));
    }

    private static void missingOrInvalidBindingsFailClosed() {
        CanonicalStatKey defense = CanonicalStatKey.of("rpgskilltree:defense");
        CanonicalProviderBinding armor = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_armor", defense, "minecraft:armor"
        );

        CanonicalProviderBindingResolution unavailable = CanonicalProviderBindingResolver.resolve(
            defense,
            List.of(armor),
            binding -> false,
            (stat, available) -> {
                throw new AssertionError("selection policy must not run without available bindings");
            }
        );
        eq(false, unavailable.isResolved());
        eq(List.of(), unavailable.availableBindings());
        expect(IllegalStateException.class, unavailable::requireSelected);

        expect(IllegalStateException.class, () -> CanonicalProviderBindingResolver.resolve(
            CanonicalStatKey.of("rpgskilltree:missing"),
            List.of(armor),
            binding -> true,
            (stat, available) -> available.get(0)
        ));

        CanonicalProviderBinding duplicate = CanonicalProviderBinding.of(
            armor.bindingId(), defense, "example_attributes:armor"
        );
        expect(IllegalArgumentException.class, () -> CanonicalProviderBindingResolver.resolve(
            defense,
            List.of(armor, duplicate),
            binding -> true,
            (stat, available) -> available.get(0)
        ));
    }

    private static void resolutionCollectionsAreImmutable() {
        CanonicalStatKey speed = CanonicalStatKey.of("rpgskilltree:movement_speed");
        CanonicalProviderBinding binding = CanonicalProviderBinding.of(
            "rpgskilltree:vanilla_movement_speed", speed, "minecraft:movement_speed"
        );
        CanonicalProviderBindingResolution resolution = CanonicalProviderBindingResolver.resolve(
            speed,
            List.of(binding),
            candidate -> true,
            (stat, available) -> available.get(0)
        );

        expect(UnsupportedOperationException.class, () -> resolution.definitions().add(binding));
        expect(UnsupportedOperationException.class, () -> resolution.availableBindings().clear());
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
