package dev.gustavopere.rpgskilltree.compendium.provider;

import dev.gustavopere.rpgskilltree.compendium.api.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ProviderMergeTest {
    public static void main(String[] args) {
        absentProviderPreservesBaseEntry();
        higherPriorityWinsExplicitly();
        equalPriorityConflictIsDeterministicAndDiagnostic();
        inputOrderDoesNotChangeResult();
        duplicateProviderIdentityIsRejected();
        System.out.println("ProviderMergeTest: PASS");
    }

    private static void absentProviderPreservesBaseEntry() {
        CompendiumEntry base = baseEntry();
        ProviderResult result = ProviderMerger.merge(base, List.of());
        eq(base, result.entry());
        eq(List.of(), result.diagnostics());
    }

    private static void higherPriorityWinsExplicitly() {
        CompendiumEntry base = baseEntry();
        ProviderContribution low = contribution("registry", 10, 20.0D);
        ProviderContribution high = contribution("adapter", 100, 30.0D);
        ProviderResult result = ProviderMerger.merge(base, List.of(low, high));
        eq(30.0D, fact(result.entry(), "stats", "base_health").value());
        eq(List.of(), result.diagnostics());
    }

    private static void equalPriorityConflictIsDeterministicAndDiagnostic() {
        ProviderContribution a = contribution("a_provider", 50, 22.0D);
        ProviderContribution z = contribution("z_provider", 50, 40.0D);
        ProviderResult result = ProviderMerger.merge(baseEntry(), List.of(z, a));
        eq(22.0D, fact(result.entry(), "stats", "base_health").value());
        eq(1, result.diagnostics().size());
        eq("FACT_CONFLICT", result.diagnostics().getFirst().code());
    }

    private static void inputOrderDoesNotChangeResult() {
        ProviderContribution a = contribution("a_provider", 50, 22.0D);
        ProviderContribution z = contribution("z_provider", 50, 40.0D);
        ProviderResult first = ProviderMerger.merge(baseEntry(), List.of(a, z));
        ProviderResult second = ProviderMerger.merge(baseEntry(), List.of(z, a));
        eq(first.entry(), second.entry());
        eq(first.diagnostics(), second.diagnostics());
    }

    private static void duplicateProviderIdentityIsRejected() {
        ProviderContribution first = contribution("same_provider", 50, 22.0D);
        ProviderContribution second = contribution("same_provider", 50, 40.0D);
        throwsIllegal(() -> ProviderMerger.merge(baseEntry(), List.of(first, second)));
    }

    private static ProviderContribution contribution(String providerId, int priority, double health) {
        return new ProviderContribution(
            providerId,
            priority,
            Map.of("stats", List.of(new CompendiumFact<>(
                "base_health", health, "hp", FactSource.ADAPTER, FactConfidence.EXACT,
                FactVisibility.DISCOVERED_ONLY, null
            ))),
            Set.of(),
            List.of()
        );
    }

    private static CompendiumFact<?> fact(CompendiumEntry entry, String sectionId, String factKey) {
        return entry.sections().stream()
            .filter(section -> section.sectionId().equals(sectionId))
            .flatMap(section -> section.facts().stream())
            .filter(candidate -> candidate.factKey().equals(factKey))
            .findFirst()
            .orElseThrow();
    }

    private static CompendiumEntry baseEntry() {
        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie"),
            "minecraft", "entity.minecraft.zombie", Set.of("fauna"), List.of(), List.of(),
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "minecraft:zombie"), 1
        );
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
