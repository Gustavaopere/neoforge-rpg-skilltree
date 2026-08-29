package dev.gustavopere.rpgskilltree.compendium.provider.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import java.util.List;
import java.util.Optional;

public record EcologyAdapterContribution(String adapterId, List<CompendiumRelation> relations) {
    public EcologyAdapterContribution {
        if (adapterId == null || adapterId.trim().isEmpty()) throw new IllegalArgumentException("adapterId must not be blank");
        adapterId = adapterId.trim();
        relations = List.copyOf(relations == null ? List.of() : relations);
    }

    public static Optional<EcologyAdapterContribution> optional(
        boolean modLoaded,
        String adapterId,
        List<CompendiumRelation> relations
    ) {
        return modLoaded ? Optional.of(new EcologyAdapterContribution(adapterId, relations)) : Optional.empty();
    }
}
