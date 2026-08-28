package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Set;

/** Editorial botanical kind mapped onto the canonical Compendium save-key kind. */
public enum FloraKind {
    FLORA(CompendiumEntryKind.FLORA, Set.of("flora")),
    FUNGUS(CompendiumEntryKind.FLORA, Set.of("flora", "fungo")),
    AQUATIC_FLORA(CompendiumEntryKind.FLORA, Set.of("flora", "flora_aquatica")),
    CROP(CompendiumEntryKind.CROP, Set.of("cultivo")),
    TREE_COMPONENT(CompendiumEntryKind.TREE, Set.of("arvore")),
    BLOCK_FEATURE(CompendiumEntryKind.BLOCK_FEATURE, Set.of("elemento_natural"));

    private final CompendiumEntryKind canonicalEntryKind;
    private final Set<String> defaultCategories;

    FloraKind(CompendiumEntryKind canonicalEntryKind, Set<String> defaultCategories) {
        this.canonicalEntryKind = canonicalEntryKind;
        this.defaultCategories = Set.copyOf(defaultCategories);
    }

    public CompendiumEntryKind canonicalEntryKind() {
        return canonicalEntryKind;
    }

    public Set<String> defaultCategories() {
        return defaultCategories;
    }
}
