package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class DiscoveryCompletionTest {
    public static void main(String[] args) {
        summarizesGlobalCategoryAndNamespaceCounts();
        exclusionsAndOrphanedProgressDoNotInflateDenominator();
        System.out.println("DiscoveryCompletionTest: PASS");
    }

    private static void summarizesGlobalCategoryAndNamespaceCounts() {
        CompendiumEntry pig = entry("minecraft:pig", "minecraft", Set.of("fauna"));
        CompendiumEntry zombie = entry("minecraft:zombie", "minecraft", Set.of("fauna", "hostile"));
        CompendiumEntry beast = entry("example:beast", "example", Set.of("fauna"));
        DiscoveryProgress progress = DiscoveryProgress.empty()
            .withRecord(record(pig.id(), DiscoveryState.SEEN))
            .withRecord(record(zombie.id(), DiscoveryState.STUDIED));

        DiscoveryCompletionSummary summary = DiscoveryCompletionService.summarize(
            List.of(pig, zombie, beast), progress, Set.of()
        );

        eq(new DiscoveryCompletionCount(3, 2), summary.global());
        eq(new DiscoveryCompletionCount(3, 2), summary.byCategory().get("fauna"));
        eq(new DiscoveryCompletionCount(1, 1), summary.byCategory().get("hostile"));
        eq(new DiscoveryCompletionCount(2, 2), summary.byNamespace().get("minecraft"));
        eq(new DiscoveryCompletionCount(1, 0), summary.byNamespace().get("example"));
    }

    private static void exclusionsAndOrphanedProgressDoNotInflateDenominator() {
        CompendiumEntry pig = entry("minecraft:pig", "minecraft", Set.of("fauna"));
        CompendiumEntry zombie = entry("minecraft:zombie", "minecraft", Set.of("fauna", "hostile"));
        CompendiumEntry beast = entry("example:beast", "example", Set.of("fauna"));
        CompendiumEntryId orphan = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "removedmod:lost_creature");
        DiscoveryProgress progress = DiscoveryProgress.empty()
            .withRecord(record(pig.id(), DiscoveryState.SEEN))
            .withRecord(record(zombie.id(), DiscoveryState.SEEN))
            .withRecord(record(orphan, DiscoveryState.MASTERED));

        DiscoveryCompletionSummary summary = DiscoveryCompletionService.summarize(
            List.of(pig, zombie, beast), progress, Set.of(zombie.id())
        );

        eq(new DiscoveryCompletionCount(2, 1), summary.global());
        eq(new DiscoveryCompletionCount(2, 1), summary.byCategory().get("fauna"));
        eq(null, summary.byCategory().get("hostile"));
        eq(new DiscoveryCompletionCount(1, 1), summary.byNamespace().get("minecraft"));
        eq(new DiscoveryCompletionCount(1, 0), summary.byNamespace().get("example"));
        yes(progress.record(orphan).isPresent());
    }

    private static CompendiumEntry entry(String resourceId, String sourceModId, Set<String> categories) {
        CompendiumEntryId id = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, resourceId);
        return new CompendiumEntry(
            id,
            sourceModId,
            "entity." + sourceModId + "." + id.path().replace('/', '.'),
            categories,
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, resourceId),
            1
        );
    }

    private static DiscoveryRecord record(CompendiumEntryId id, DiscoveryState state) {
        return new DiscoveryRecord(id, state, 1L, Optional.empty(), Set.of(), Set.of(), Set.of());
    }

    private static void yes(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
