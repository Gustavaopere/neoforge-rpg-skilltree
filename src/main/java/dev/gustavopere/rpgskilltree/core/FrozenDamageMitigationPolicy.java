package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Frozen A0101-A0103 classification policy; delivery and damage nature remain independent. */
public final class FrozenDamageMitigationPolicy {
    private FrozenDamageMitigationPolicy() {}

    public static List<DamageMitigationResolver.Modifier> modifiers(
        FrozenSurvivalPerkRanks ranks,
        Facts facts
    ) {
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(facts);
        ArrayList<DamageMitigationResolver.Modifier> result = new ArrayList<>(2);
        if (facts.projectileDelivery() && facts.physicalNature()) {
            add(result, "A0101", "physical_projectile", ranks.rank("A0101"));
        }
        if (facts.magicNature()) add(result, "A0102", "magic_resistance", ranks.rank("A0102"));
        if (facts.environmentalDelivery()
            && facts.explicitNonElementalEnvironment()
            && !facts.thermalOrPhysiological()) {
            add(result, "A0103", "non_elemental_environment", ranks.rank("A0103"));
        }
        return List.copyOf(result);
    }

    private static void add(
        List<DamageMitigationResolver.Modifier> result,
        String source,
        String canonicalId,
        int rank
    ) {
        if (rank > 0) result.add(new DamageMitigationResolver.Modifier(source, canonicalId, 0.02D * rank));
    }

    public record Facts(
        boolean projectileDelivery,
        boolean magicNature,
        boolean physicalNature,
        boolean environmentalDelivery,
        boolean thermalOrPhysiological,
        boolean explicitNonElementalEnvironment
    ) {}
}
