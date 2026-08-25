package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/** Immutable projection of one frozen A0101-A0150 Notion node. */
public record FrozenSurvivalPerkDefinition(
    String code,
    String name,
    Domain domain,
    Family family,
    Kind kind,
    int maxRank,
    int rankCost,
    Map<String, Integer> dependencies,
    Set<Domain> requiredGateways,
    SpecialGate specialGate,
    Fallback fallback
) {
    private static final Pattern CODE = Pattern.compile("A\\d{4}");

    public FrozenSurvivalPerkDefinition {
        Objects.requireNonNull(code);
        Objects.requireNonNull(name);
        Objects.requireNonNull(domain);
        Objects.requireNonNull(family);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(dependencies);
        Objects.requireNonNull(requiredGateways);
        Objects.requireNonNull(specialGate);
        Objects.requireNonNull(fallback);
        if (!CODE.matcher(code).matches()) throw new IllegalArgumentException("invalid catalog code: " + code);
        if (name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (maxRank <= 0 || rankCost <= 0) throw new IllegalArgumentException("rank and cost must be positive");
        dependencies = Map.copyOf(dependencies);
        requiredGateways = Set.copyOf(requiredGateways);
        dependencies.forEach((dependency, rank) -> {
            if (!CODE.matcher(dependency).matches() || rank == null || rank <= 0 || dependency.equals(code)) {
                throw new IllegalArgumentException("invalid dependency for " + code + ": " + dependency);
            }
        });
    }

    public enum Domain { MARTIAL, VITALITY, AGILITY, ARCANE, SURVIVAL, ENGINEERING, LOGISTICS, MINING }

    public enum Family {
        DAMAGE_MITIGATION,
        EMERGENCY_DEFENSE,
        LOAD_DEFENSE,
        MAINTENANCE,
        METABOLIC,
        HYDRATION,
        ACCLIMATION,
        NUTRITION,
        ARCANE_FUNDAMENTALS,
        ARCANE_TECHNIQUE
    }

    public enum Kind { TRUNK, BRANCH, BRIDGE, NOTABLE, KEYSTONE, CAPSTONE }

    public enum SpecialGate {
        NONE,
        IMPACT_STAMINA_PROVIDER,
        HEAVY_LOAD_PROVIDER,
        ATTUNEMENT_SOCKET,
        FORESTRY_ACCESS,
        PHYSICAL_METABOLIC_RANK_TWO,
        THREE_DISTINCT_METABOLIC,
        TWO_DISTINCT_METABOLIC,
        ARCANE_RESERVE_OR,
        BODY_PROVIDER,
        HYDRATION_PROVIDER,
        THERMAL_PROVIDER,
        NUTRITION_PROVIDER,
        ARCANE_PROVIDER,
        RESOURCE_DEBIT_PROVIDER
    }

    /** A fallback may preserve only the exact semantic component named by Notion. */
    public enum Fallback { NATIVE, SAFE_COMPONENT_ONLY, FAIL_CLOSED }
}
