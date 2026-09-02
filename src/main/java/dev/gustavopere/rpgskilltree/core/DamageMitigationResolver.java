package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Canonical combiner for RPG-owned incoming damage mitigation contributions.
 *
 * <p>Contributions compose multiplicatively in encounter order. A contribution id may affect one
 * damage sequence at most once; aliases or duplicate adapters therefore cannot double-apply the
 * same perk. Fractions are clamped to [0, 1] and incoming damage is never allowed below zero.</p>
 */
public final class DamageMitigationResolver {
    private DamageMitigationResolver() {}

    public record Contribution(String id, double reductionFraction) {
        public Contribution {
            Objects.requireNonNull(id, "id");
            if (id.isBlank()) throw new IllegalArgumentException("id must not be blank");
            if (!Double.isFinite(reductionFraction)) {
                throw new IllegalArgumentException("reductionFraction must be finite");
            }
        }
    }

    public record Result(double damage, List<String> appliedIds) {
        public Result {
            if (!Double.isFinite(damage) || damage < 0.0D) {
                throw new IllegalArgumentException("damage must be finite and non-negative");
            }
            appliedIds = List.copyOf(Objects.requireNonNull(appliedIds, "appliedIds"));
        }
    }

    public static Result resolve(double incomingDamage, List<Contribution> contributions) {
        if (!Double.isFinite(incomingDamage)) {
            throw new IllegalArgumentException("incomingDamage must be finite");
        }
        Objects.requireNonNull(contributions, "contributions");

        double damage = Math.max(0.0D, incomingDamage);
        Set<String> seen = new LinkedHashSet<>();
        List<String> applied = new ArrayList<>();

        for (Contribution contribution : contributions) {
            Objects.requireNonNull(contribution, "contribution");
            if (!seen.add(contribution.id())) continue;

            double reduction = Math.max(0.0D, Math.min(1.0D, contribution.reductionFraction()));
            damage *= 1.0D - reduction;
            if (damage < 0.0D) damage = 0.0D;
            applied.add(contribution.id());
        }
        return new Result(damage, applied);
    }
}
