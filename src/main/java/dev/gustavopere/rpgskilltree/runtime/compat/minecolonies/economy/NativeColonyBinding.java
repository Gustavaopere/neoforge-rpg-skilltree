package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import java.util.Objects;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

/** Stable provider-side lookup tuple for a live MineColonies colony; not the monetary identity. */
public record NativeColonyBinding(
    ResourceLocation dimensionId,
    int colonyId,
    UUID ownerUuid,
    BlockPos townHallPos
) {
    public NativeColonyBinding {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(ownerUuid, "ownerUuid");
        Objects.requireNonNull(townHallPos, "townHallPos");
        if (colonyId < 0) {
            throw new IllegalArgumentException("colonyId must be non-negative");
        }
        townHallPos = townHallPos.immutable();
    }

    /** Lookup key only. Fingerprint equality must still be checked before reusing monetary identity. */
    public String persistentKey() {
        return dimensionId + "#" + colonyId;
    }
}
