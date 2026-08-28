package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityInstanceSnapshot;
import dev.gustavopere.rpgskilltree.compendium.integration.rpg.RpgEntityScalingCompendiumProvider;
import dev.gustavopere.rpgskilltree.core.EntityScalingSnapshot;
import java.util.Map;
import java.util.Objects;

/** Reads the already-persisted canonical scaling decision; it never recalculates level or rarity. */
public final class RuntimeRpgEntityScalingCompendiumAdapter {
    private RuntimeRpgEntityScalingCompendiumAdapter() {}

    public static CompendiumSection createSection(
        EntityScalingSnapshot snapshot,
        EntityInstanceSnapshot currentInstance
    ) {
        Objects.requireNonNull(snapshot, "snapshot");
        Map<String, Double> effectiveAttributes = currentInstance == null
            ? Map.of()
            : currentInstance.currentAttributes();
        return RpgEntityScalingCompendiumProvider.createSection(
            snapshot.entityLevel(),
            snapshot.rarity().serializedId(),
            snapshot.archetype().name(),
            effectiveAttributes
        );
    }
}
