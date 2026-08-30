package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.server.packs.resources.ResourceManager;

/**
 * Server-authoritative Stage 10.10 editorial snapshot.
 *
 * <p>Candidates are fully decoded and validated before publication. Publication is one volatile
 * reference replacement, so readers never observe a partially built corpus. Only editorial
 * validation failures are recoverable; programming failures are intentionally allowed to escape.</p>
 */
public final class RuntimeCompendiumEditorialCatalog {
    private static volatile CompendiumEditorialSnapshot CURRENT = CompendiumEditorialSnapshot.empty();

    private RuntimeCompendiumEditorialCatalog() {}

    public static CompendiumEditorialSnapshot snapshot() {
        return CURRENT;
    }

    public static PublicationResult tryPublish(Supplier<CompendiumEditorialSnapshot> candidateFactory) {
        Objects.requireNonNull(candidateFactory, "candidateFactory");
        try {
            CompendiumEditorialSnapshot candidate = Objects.requireNonNull(candidateFactory.get(), "candidate");
            CURRENT = candidate;
            return new PublicationResult(true, candidate, "");
        } catch (CompendiumEditorialValidationException failure) {
            return new PublicationResult(false, CURRENT, failure.getMessage());
        }
    }

    public static PublicationResult loadAndPublish(
        ResourceManager resourceManager,
        Collection<CompendiumEntry> technicalEntries
    ) {
        Objects.requireNonNull(resourceManager, "resourceManager");
        Objects.requireNonNull(technicalEntries, "technicalEntries");
        return tryPublish(() -> CompendiumEditorialResourceLoader.load(resourceManager, technicalEntries));
    }

    /** Compatibility primitive for callers that already hold a fully validated snapshot. */
    public static CompendiumEditorialSnapshot publish(CompendiumEditorialSnapshot candidate) {
        CompendiumEditorialSnapshot validated = Objects.requireNonNull(candidate, "candidate");
        CURRENT = validated;
        return validated;
    }

    static void resetForTests() {
        CURRENT = CompendiumEditorialSnapshot.empty();
    }

    public record PublicationResult(
        boolean published,
        CompendiumEditorialSnapshot snapshot,
        String diagnostic
    ) {
        public PublicationResult {
            Objects.requireNonNull(snapshot, "snapshot");
            diagnostic = Objects.requireNonNull(diagnostic, "diagnostic");
        }
    }
}
