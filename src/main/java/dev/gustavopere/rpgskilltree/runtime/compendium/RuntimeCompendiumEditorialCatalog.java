package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import java.util.Objects;

/**
 * Server-authoritative Stage 10.10 editorial snapshot.
 *
 * <p>Candidates are fully decoded and validated before reaching this boundary. Publication is one
 * volatile reference replacement, so readers never observe a partially built corpus and a rejected
 * candidate leaves the last good snapshot untouched.</p>
 */
public final class RuntimeCompendiumEditorialCatalog {
    private static volatile CompendiumEditorialSnapshot CURRENT = CompendiumEditorialSnapshot.empty();

    private RuntimeCompendiumEditorialCatalog() {}

    public static CompendiumEditorialSnapshot snapshot() {
        return CURRENT;
    }

    public static CompendiumEditorialSnapshot publish(CompendiumEditorialSnapshot candidate) {
        CompendiumEditorialSnapshot validated = Objects.requireNonNull(candidate, "candidate");
        CURRENT = validated;
        return validated;
    }

    static void resetForTests() {
        CURRENT = CompendiumEditorialSnapshot.empty();
    }
}
