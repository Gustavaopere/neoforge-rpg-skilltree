package dev.gustavopere.rpgskilltree.runtime.data;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/** Shared fail-closed availability rule for data-driven definitions backed by optional mods. */
public final class ProviderAvailabilityPolicy {
    private ProviderAvailabilityPolicy() {}

    public static boolean allAvailable(
        Collection<String> requiredProviderMods,
        Predicate<String> isLoaded
    ) {
        Objects.requireNonNull(requiredProviderMods, "requiredProviderMods");
        Objects.requireNonNull(isLoaded, "isLoaded");
        for (String providerMod : requiredProviderMods) {
            Objects.requireNonNull(providerMod, "providerMod");
            if (providerMod.isBlank()) {
                throw new IllegalArgumentException("provider mod id must not be blank");
            }
            if (!isLoaded.test(providerMod)) return false;
        }
        return true;
    }
}
