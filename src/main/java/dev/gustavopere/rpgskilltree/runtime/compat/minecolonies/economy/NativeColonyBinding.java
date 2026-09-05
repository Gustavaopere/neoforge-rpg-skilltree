package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Stable provider-side lookup tuple for a live MineColonies colony; not the monetary identity. */
public record NativeColonyBinding(ResourceLocation dimensionId, int colonyId) {
    public NativeColonyBinding {
        Objects.requireNonNull(dimensionId, "dimensionId");
        if (colonyId < 0) {
            throw new IllegalArgumentException("colonyId must be non-negative");
        }
    }

    public String persistentKey() {
        return dimensionId + "#" + colonyId;
    }
}
