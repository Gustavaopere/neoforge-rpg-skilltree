package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTargetKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Builds a client page without leaking undiscovered or administrative facts. */
public final class CompendiumPageModelFactory {
    private CompendiumPageModelFactory() {}

    public static Optional<CompendiumPageModel> create(
        CompendiumEntry entry,
        CompendiumClientEntry clientEntry,
        boolean admin
    ) {
        Objects.requireNonNull(entry, "entry");
        Objects.requireNonNull(clientEntry, "clientEntry");
        if (!entry.id().equals(clientEntry.id())) {
            throw new IllegalArgumentException("client entry identity does not match canonical entry");
        }

        boolean discovered = clientEntry.discovered();
        if (!admin && !discovered && entry.visibilityPolicy() == VisibilityPolicy.HIDE_ENTRY_UNTIL_DISCOVERED) {
            return Optional.empty();
        }

        boolean detailsVisible = admin
            || discovered
            || entry.visibilityPolicy() == VisibilityPolicy.VISIBLE;

        List<CompendiumSection> sections = detailsVisible
            ? visibleSections(entry.sections(), discovered, admin)
            : List.of();
        List<CompendiumRelation> relations = detailsVisible
            ? entryRelations(entry.relations())
            : List.of();

        return Optional.of(new CompendiumPageModel(
            entry.id(),
            clientEntry.displayName(),
            entry.sourceModId(),
            discovered,
            detailsVisible,
            sections,
            relations
        ));
    }

    private static List<CompendiumSection> visibleSections(
        List<CompendiumSection> sections,
        boolean discovered,
        boolean admin
    ) {
        List<CompendiumSection> result = new ArrayList<>();
        for (CompendiumSection section : sections) {
            List<CompendiumFact<?>> facts = new ArrayList<>();
            for (CompendiumFact<?> fact : section.facts()) {
                if (!fact.isConfirmed()) continue;
                if (!visible(fact.visibility(), discovered, admin)) continue;
                facts.add(fact);
            }
            if (!facts.isEmpty()) result.add(new CompendiumSection(section.sectionId(), facts));
        }
        return List.copyOf(result);
    }

    private static boolean visible(FactVisibility visibility, boolean discovered, boolean admin) {
        return switch (visibility) {
            case PUBLIC -> true;
            case DISCOVERED_ONLY -> discovered || admin;
            case ADMIN_ONLY -> admin;
        };
    }

    private static List<CompendiumRelation> entryRelations(List<CompendiumRelation> relations) {
        List<CompendiumRelation> result = new ArrayList<>();
        for (CompendiumRelation relation : relations) {
            if (relation.target().kind() == CompendiumRelationTargetKind.ENTRY) result.add(relation);
        }
        return List.copyOf(result);
    }
}
