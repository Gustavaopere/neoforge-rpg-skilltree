package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.Objects;
import java.util.Set;

public record EquipmentAdapterContribution(
    EligibilityOverride eligibility,
    Set<EquipmentCategory> categories
) {
    public EquipmentAdapterContribution {
        eligibility = Objects.requireNonNull(eligibility, "eligibility");
        categories = Set.copyOf(Objects.requireNonNull(categories, "categories"));
    }
}
