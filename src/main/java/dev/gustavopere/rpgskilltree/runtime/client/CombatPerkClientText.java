package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkCatalog;
import java.util.Optional;

/** Presentation bridge for server-authoritative semantic combat perks. */
public final class CombatPerkClientText {
    private static final String COMBAT_TREE_ID = "rpgskilltree:runtime/combat_perks";
    private static final String COMBAT_TREE_TITLE_PT_BR = "Perks de Combate";

    private CombatPerkClientText() {}

    public static Optional<String> nodeDisplayName(String nodeId) {
        return CombatPerkNodeBinding.catalogCode(nodeId)
            .flatMap(NotionCombatPerkCatalog::definition)
            .map(definition -> definition.name());
    }

    public static Optional<String> treeDisplayName(String treeId) {
        return COMBAT_TREE_ID.equals(treeId)
            ? Optional.of(COMBAT_TREE_TITLE_PT_BR)
            : Optional.empty();
    }
}
