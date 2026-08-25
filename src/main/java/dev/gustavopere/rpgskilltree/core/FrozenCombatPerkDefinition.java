package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/** Immutable implementation contract for one node in the frozen A0051-A0100 batch. */
public record FrozenCombatPerkDefinition(
    String code,
    String name,
    Domain domain,
    Family family,
    Kind kind,
    int maxRank,
    int rankCost,
    Map<String, Integer> dependencies,
    int minimumCharacterLevel,
    Set<String> requiredSpecializations,
    Map<String, Integer> requiredMastery,
    Set<Domain> requiredGateways,
    SpecialGate specialGate,
    Fallback fallback
) {
    private static final Pattern CODE = Pattern.compile("A\\d{4}");

    public FrozenCombatPerkDefinition {
        Objects.requireNonNull(code);
        Objects.requireNonNull(name);
        Objects.requireNonNull(domain);
        Objects.requireNonNull(family);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(dependencies);
        Objects.requireNonNull(requiredSpecializations);
        Objects.requireNonNull(requiredMastery);
        Objects.requireNonNull(requiredGateways);
        Objects.requireNonNull(specialGate);
        Objects.requireNonNull(fallback);
        if (!CODE.matcher(code).matches()) throw new IllegalArgumentException("invalid catalog code: " + code);
        if (name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (maxRank <= 0 || rankCost <= 0) throw new IllegalArgumentException("rank and cost must be positive");
        if (minimumCharacterLevel <= 0) throw new IllegalArgumentException("minimum level must be positive");
        dependencies = Map.copyOf(dependencies);
        requiredSpecializations = Set.copyOf(requiredSpecializations);
        requiredMastery = Map.copyOf(requiredMastery);
        requiredGateways = Set.copyOf(requiredGateways);
        dependencies.forEach((dependency, rank) -> {
            if (!CODE.matcher(dependency).matches() || rank == null || rank <= 0 || dependency.equals(code)) {
                throw new IllegalArgumentException("invalid dependency for " + code + ": " + dependency);
            }
        });
        requiredMastery.forEach((lane, amount) -> {
            if (lane == null || lane.isBlank() || amount == null || amount < 0) {
                throw new IllegalArgumentException("invalid mastery requirement for " + code);
            }
        });
    }

    public enum Domain { MARTIAL, VITALITY, AGILITY, ARCANE, OCCULT }

    public enum Family { CROSSBOW, FIST, MARTIAL_OFFENSE, MARTIAL_SUSTAIN, SUSTAIN, VITALITY_DEFENSE }

    public enum Kind { BRANCH, NOTABLE, BRIDGE, CAPSTONE, KEYSTONE }

    public enum SpecialGate {
        NONE,
        ANY_PHYSICAL_WEAPON,
        ARCANE_DIRECT_DAMAGE_BRANCH,
        ELEMENTAL_AFFINITY,
        ATTRIBUTABLE_PERIODIC_SOURCE,
        DODGE_BRANCH,
        GUARD_CORRIDOR
    }

    /** Canonical fallback never changes the semantic axis of an effect. */
    public enum Fallback { NATIVE, SAFE_COMPONENT_ONLY, FAIL_CLOSED }
}
