package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class ArchetypeResolver {
    private ArchetypeResolver() {}

    private static final Comparator<ArchetypeMatch> ORDER =
        Comparator.comparingInt(ArchetypeMatch::specificity).reversed()
            .thenComparing(Comparator.comparingInt(ArchetypeMatch::matchedScore).reversed())
            .thenComparing(Comparator.comparingInt(ArchetypeMatch::priority).reversed())
            .thenComparing(ArchetypeMatch::archetypeId);

    public static List<ArchetypeMatch> resolve(
        InvestmentState state,
        Collection<ArchetypeDefinition> definitions
    ) {
        return definitions.stream()
            .filter(definition -> matches(state, definition))
            .map(definition -> new ArchetypeMatch(
                definition.id(),
                definition.priority(),
                definition.specificity(),
                definition.minimumDomainScores().keySet().stream()
                    .mapToInt(state::domainScore)
                    .sum()
            ))
            .sorted(ORDER)
            .toList();
    }

    public static EmergentClassResolution resolveHierarchy(
        InvestmentState state,
        Collection<ArchetypeDefinition> definitions
    ) {
        return EmergentClassResolution.fromOrderedMatches(resolve(state, definitions));
    }

    private static boolean matches(InvestmentState state, ArchetypeDefinition definition) {
        return definition.minimumDomainScores().entrySet().stream()
                .allMatch(entry -> state.domainScore(entry.getKey()) >= entry.getValue())
            && definition.requiredTags().stream().allMatch(state::hasTag)
            && definition.forbiddenTags().stream().noneMatch(state::hasTag);
    }
}
