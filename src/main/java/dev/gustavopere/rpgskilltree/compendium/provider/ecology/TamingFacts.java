package dev.gustavopere.rpgskilltree.compendium.provider.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import java.util.List;

public record TamingFacts(
    Boolean tameable,
    List<CompendiumRelationTarget> methods,
    Boolean tamed,
    String ownerId
) {
    public TamingFacts {
        methods = List.copyOf(methods == null ? List.of() : methods);
        if (ownerId != null) {
            ownerId = ownerId.trim();
            if (ownerId.isEmpty()) ownerId = null;
        }
        if (tamed == null && ownerId != null) throw new IllegalArgumentException("ownerId requires instance tame state");
        if (Boolean.FALSE.equals(tamed) && ownerId != null) throw new IllegalArgumentException("untamed instance cannot have ownerId");
    }

    public static TamingFacts species(boolean tameable, List<CompendiumRelationTarget> methods) {
        return new TamingFacts(tameable, methods, null, null);
    }

    public static TamingFacts instance(boolean tamed, String ownerId) {
        return new TamingFacts(null, List.of(), tamed, ownerId);
    }
}
