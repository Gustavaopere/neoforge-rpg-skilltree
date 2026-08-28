package dev.gustavopere.rpgskilltree.compendium.provider.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraKind;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraSpeciesFacts;

/** Crop-specific entry boundary; random growth time is omitted unless a deterministic value is explicitly supplied. */
public final class CropProvider {
    private CropProvider() {}

    public static CompendiumEntry create(FloraSpeciesFacts species) {
        if (species.floraKind() != FloraKind.CROP) {
            throw new IllegalArgumentException("CropProvider requires FloraKind.CROP");
        }
        return FloraRegistryProvider.buildEntry(species);
    }
}
