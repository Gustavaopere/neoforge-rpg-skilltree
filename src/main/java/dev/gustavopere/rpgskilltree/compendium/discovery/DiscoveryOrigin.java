package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;

public record DiscoveryOrigin(String dimensionId, int chunkX, int chunkZ) {
    public DiscoveryOrigin {
        dimensionId = CompendiumEntryId.of(CompendiumEntryKind.DIMENSION, dimensionId).resourceLocation();
    }
}
