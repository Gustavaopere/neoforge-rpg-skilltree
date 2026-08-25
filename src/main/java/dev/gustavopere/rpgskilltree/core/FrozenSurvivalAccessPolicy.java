package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Purchase-time structural gates for A0101-A0150; effect providers remain runtime fail-closed. */
public final class FrozenSurvivalAccessPolicy {
    private static final Set<String> PHYSICAL_METABOLIC = nodeIds(
        "A0115", "A0117", "A0119", "A0121", "A0123", "A0125", "A0127", "A0129", "A0133"
    );
    private static final Set<String> DISTINCT_METABOLIC = nodeIds(
        "A0115", "A0117", "A0119", "A0121", "A0123", "A0125", "A0127", "A0129", "A0131", "A0133",
        "A0135", "A0137"
    );
    private static final Set<String> PROFESSIONAL_OR_CLIMATIC = nodeIds(
        "A0123", "A0125", "A0127", "A0129", "A0131", "A0135", "A0137"
    );

    private FrozenSurvivalAccessPolicy() {}

    public static boolean satisfied(
        FrozenSurvivalPerkDefinition.SpecialGate gate,
        PassiveNodeProgress progress,
        Set<String> unlockedSpecializations
    ) {
        Objects.requireNonNull(gate);
        Objects.requireNonNull(progress);
        Objects.requireNonNull(unlockedSpecializations);
        return switch (gate) {
            case NONE,
                 IMPACT_STAMINA_PROVIDER,
                 HEAVY_LOAD_PROVIDER,
                 BODY_PROVIDER,
                 HYDRATION_PROVIDER,
                 THERMAL_PROVIDER,
                 NUTRITION_PROVIDER,
                 ARCANE_PROVIDER,
                 RESOURCE_DEBIT_PROVIDER -> true;
            case ATTUNEMENT_SOCKET -> unlockedSpecializations.contains("attunement_socket");
            case FORESTRY_ACCESS -> unlockedSpecializations.contains("tfc_forestry");
            case PHYSICAL_METABOLIC_RANK_TWO -> PHYSICAL_METABOLIC.stream()
                .anyMatch(nodeId -> progress.rank(nodeId) >= 2);
            case THREE_DISTINCT_METABOLIC -> countLearned(progress, DISTINCT_METABOLIC) >= 3
                && PROFESSIONAL_OR_CLIMATIC.stream().anyMatch(nodeId -> progress.rank(nodeId) > 0);
            case TWO_DISTINCT_METABOLIC -> countLearned(progress, DISTINCT_METABOLIC) >= 2;
            case ARCANE_RESERVE_OR -> progress.rank(FrozenSurvivalPerkNodeBinding.nodeId("A0144")) > 0
                || progress.rank(FrozenSurvivalPerkNodeBinding.nodeId("A0145")) > 0;
        };
    }

    private static int countLearned(PassiveNodeProgress progress, Set<String> nodeIds) {
        return (int)nodeIds.stream().filter(nodeId -> progress.rank(nodeId) > 0).count();
    }

    private static Set<String> nodeIds(String... codes) {
        java.util.LinkedHashSet<String> result = new java.util.LinkedHashSet<>();
        for (String code : codes) result.add("rpgskilltree:frozen/" + code.toLowerCase(java.util.Locale.ROOT));
        return Set.copyOf(result);
    }
}
