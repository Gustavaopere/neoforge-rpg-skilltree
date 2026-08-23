package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Pure species/faction/fear policy used by Identity 2 and future AI adapters. */
public final class MorphEcologyPolicy {
    private MorphEcologyPolicy() {}

    public static MorphPerceivedIdentity perceivedIdentity(
        MorphFormDescriptor form,
        Map<String, Set<String>> factionsByEntity,
        Map<String, Set<String>> traitsByEntity
    ) {
        Objects.requireNonNull(form);
        Objects.requireNonNull(factionsByEntity);
        Objects.requireNonNull(traitsByEntity);
        return new MorphPerceivedIdentity(
            form.entityId(),
            form.category(),
            factionsByEntity.getOrDefault(form.entityId(), Set.of()),
            traitsByEntity.getOrDefault(form.entityId(), Set.of())
        );
    }

    public static Set<String> factionsFor(String entityId, Map<String, Set<String>> factionsByEntity) {
        Objects.requireNonNull(entityId);
        Objects.requireNonNull(factionsByEntity);
        if (entityId.isBlank()) throw new IllegalArgumentException("entityId must not be blank");
        return factionsByEntity.getOrDefault(entityId, Set.of());
    }

    /**
     * Resolves the observer's reaction to a morphed player's perceived identity.
     * Explicit hostility memory wins, then fear, hostility, alliance and neutral.
     * Equal species falls back to alliance when no stronger explicit relation exists.
     */
    public static MorphFactionDisposition disposition(
        String observerSpeciesId,
        Set<String> observerFactions,
        MorphPerceivedIdentity perceived,
        Map<String, MorphFactionRelations> relations,
        MorphHostilityMemory hostilityMemory,
        long nowMillis
    ) {
        Objects.requireNonNull(observerSpeciesId);
        Objects.requireNonNull(observerFactions);
        Objects.requireNonNull(perceived);
        Objects.requireNonNull(relations);
        Objects.requireNonNull(hostilityMemory);
        if (observerSpeciesId.isBlank()) throw new IllegalArgumentException("observerSpeciesId must not be blank");
        if (hostilityMemory.compromisesAny(observerFactions, nowMillis)) {
            return MorphFactionDisposition.HOSTILE;
        }

        boolean allied = false;
        boolean hostile = false;
        boolean fear = false;
        for (String observerFaction : observerFactions) {
            MorphFactionRelations relation = relations.get(observerFaction);
            if (relation == null) continue;
            if (!intersectionEmpty(relation.fears(), perceived.factions())) fear = true;
            if (!intersectionEmpty(relation.enemies(), perceived.factions())) hostile = true;
            if (!intersectionEmpty(relation.allies(), perceived.factions())) allied = true;
        }

        if (fear) return MorphFactionDisposition.FEAR;
        if (hostile) return MorphFactionDisposition.HOSTILE;
        if (allied) return MorphFactionDisposition.ALLY;
        if (observerSpeciesId.equals(perceived.speciesId())) return MorphFactionDisposition.ALLY;
        return MorphFactionDisposition.NEUTRAL;
    }

    private static boolean intersectionEmpty(Set<String> left, Set<String> right) {
        for (String value : left) {
            if (right.contains(value)) return false;
        }
        return true;
    }
}
