package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import java.util.Objects;

/**
 * Visibility-safe client navigation link between two Compendium entries already present in the
 * installed client snapshot.
 */
public record CompendiumRelationLink(
    CompendiumRelation relation,
    CompendiumClientEntry target
) {
    public CompendiumRelationLink {
        Objects.requireNonNull(relation, "relation");
        Objects.requireNonNull(target, "target");
        if (relation.target().entryId() == null || !relation.target().entryId().equals(target.id())) {
            throw new IllegalArgumentException("relation target must match the projected client entry");
        }
    }

    public CompendiumRelationType type() {
        return relation.type();
    }
}
