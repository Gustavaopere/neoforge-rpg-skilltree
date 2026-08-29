package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Immutable definition for the currently closed combat catalog A0001-A0080. */
public record CombatPerkDefinition(
    String code,
    String name,
    WeaponFamily weaponFamily,
    int maxRank,
    int rankCost,
    Map<String, Integer> dependencies,
    Set<String> providerCapabilities
) {
    public CombatPerkDefinition {
        require(code, "code");
        require(name, "name");
        Objects.requireNonNull(weaponFamily);
        if (!code.matches("A00(?:0[1-9]|[1-7][0-9]|80)")) {
            throw new IllegalArgumentException("catalog code outside A0001-A0080: " + code);
        }
        if (maxRank <= 0) throw new IllegalArgumentException("maxRank must be positive");
        if (rankCost <= 0) throw new IllegalArgumentException("rankCost must be positive");
        Objects.requireNonNull(dependencies);
        Objects.requireNonNull(providerCapabilities);
        dependencies.forEach((dependency, rank) -> {
            require(dependency, "dependency");
            if (rank == null || rank <= 0) throw new IllegalArgumentException("dependency rank must be positive");
        });
        if (providerCapabilities.stream().anyMatch(value -> value == null || value.isBlank())) {
            throw new IllegalArgumentException("provider capabilities must not be blank");
        }
        dependencies = Map.copyOf(dependencies);
        providerCapabilities = Set.copyOf(providerCapabilities);
    }

    public enum WeaponFamily {
        SWORD, AXE, SPEAR, DAGGER, HAMMER, MACE, SCYTHE, BOW, CROSSBOW, FIST, MARTIAL
    }

    private static void require(String value, String name) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
    }
}
