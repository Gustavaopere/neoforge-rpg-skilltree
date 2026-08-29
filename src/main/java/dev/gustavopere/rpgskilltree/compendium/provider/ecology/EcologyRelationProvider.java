package dev.gustavopere.rpgskilltree.compendium.provider.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;

public final class EcologyRelationProvider {
    private EcologyRelationProvider() {}

    public static CompendiumRelation relation(
        CompendiumRelationType type,
        CompendiumRelationTarget target,
        FactSource source,
        FactConfidence confidence,
        String evidenceId
    ) {
        return new CompendiumRelation(type, target, source, confidence, evidenceId);
    }
}
